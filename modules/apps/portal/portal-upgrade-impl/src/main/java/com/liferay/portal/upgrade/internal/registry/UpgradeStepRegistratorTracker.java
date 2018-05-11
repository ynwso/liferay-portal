/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.portal.upgrade.internal.registry;

import com.liferay.osgi.util.ServiceTrackerFactory;
import com.liferay.portal.kernel.configuration.Configuration;
import com.liferay.portal.kernel.configuration.ConfigurationFactoryUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.module.framework.ModuleServiceLifecycle;
import com.liferay.portal.kernel.upgrade.UpgradeStep;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapDictionary;
import com.liferay.portal.upgrade.registry.UpgradeStepRegistrator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Dictionary;
import java.util.List;
import java.util.Properties;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceReference;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;
import org.osgi.util.tracker.ServiceTracker;
import org.osgi.util.tracker.ServiceTrackerCustomizer;

/**
 * @author Carlos Sierra Andrés
 */
@Component(immediate = true)
public class UpgradeStepRegistratorTracker {

	protected static List<UpgradeInfo> createUpgradeInfos(
		String fromSchemaVersionString, String toSchemaVersionString,
		int buildNumber, UpgradeStep... upgradeSteps) {

		if (ArrayUtil.isEmpty(upgradeSteps)) {
			return Collections.emptyList();
		}

		List<UpgradeInfo> upgradeInfos = new ArrayList<>();

		String upgradeInfoFromSchemaVersionString = fromSchemaVersionString;

		for (int i = 0; i < upgradeSteps.length - 1; i++) {
			UpgradeStep upgradeStep = upgradeSteps[i];

			String upgradeInfoToSchemaVersionString =
				toSchemaVersionString + "-step" + (i - upgradeSteps.length + 1);

			UpgradeInfo upgradeInfo = new UpgradeInfo(
				upgradeInfoFromSchemaVersionString,
				upgradeInfoToSchemaVersionString, buildNumber, upgradeStep);

			upgradeInfos.add(upgradeInfo);

			upgradeInfoFromSchemaVersionString =
				upgradeInfoToSchemaVersionString;
		}

		UpgradeInfo upgradeInfo = new UpgradeInfo(
			upgradeInfoFromSchemaVersionString, toSchemaVersionString,
			buildNumber, upgradeSteps[upgradeSteps.length - 1]);

		upgradeInfos.add(upgradeInfo);

		return upgradeInfos;
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_bundleContext = bundleContext;

		_serviceTracker = ServiceTrackerFactory.open(
			bundleContext, UpgradeStepRegistrator.class,
			new UpgradeStepRegistratorServiceTrackerCustomizer());
	}

	@Deactivate
	protected void deactivate() {
		_serviceTracker.close();
	}

	@Reference(target = ModuleServiceLifecycle.DATABASE_INITIALIZED)
	protected ModuleServiceLifecycle moduleServiceLifecycle;

	private static final Log _log = LogFactoryUtil.getLog(
		UpgradeStepRegistratorTracker.class);

	private BundleContext _bundleContext;
	private ServiceTracker
		<UpgradeStepRegistrator, Collection<ServiceRegistration<UpgradeStep>>>
			_serviceTracker;

	private class UpgradeStepRegistratorServiceTrackerCustomizer
		implements ServiceTrackerCustomizer
			<UpgradeStepRegistrator,
				Collection<ServiceRegistration<UpgradeStep>>> {

		@Override
		public Collection<ServiceRegistration<UpgradeStep>> addingService(
			ServiceReference<UpgradeStepRegistrator> serviceReference) {

			UpgradeStepRegistrator upgradeStepRegistrator =
				_bundleContext.getService(serviceReference);

			if (upgradeStepRegistrator == null) {
				return null;
			}

			List<ServiceRegistration<UpgradeStep>> serviceRegistrations =
				new ArrayList<>();

			Class<? extends UpgradeStepRegistrator> clazz =
				upgradeStepRegistrator.getClass();

			Bundle bundle = FrameworkUtil.getBundle(clazz);

			String bundleSymbolicName = bundle.getSymbolicName();

			int buildNumber = 0;

			try {
				Configuration configuration =
					ConfigurationFactoryUtil.getConfiguration(
						clazz.getClassLoader(), "service");

				Properties properties = configuration.getProperties();

				buildNumber = GetterUtil.getInteger(
					properties.getProperty("build.number"));
			}
			catch (Exception e) {
				if (_log.isDebugEnabled()) {
					_log.debug(
						"Unable to read service.properties for " +
							bundleSymbolicName);
				}
			}

			UpgradeStepRegistry upgradeStepRegistry = new UpgradeStepRegistry(
				buildNumber);

			upgradeStepRegistrator.register(upgradeStepRegistry);

			for (UpgradeInfo upgradeInfo :
					upgradeStepRegistry.getUpgradeInfos()) {

				Dictionary<String, Object> properties =
					new HashMapDictionary<>();

				properties.put("build.number", upgradeInfo.getBuildNumber());
				properties.put(
					"upgrade.bundle.symbolic.name", bundleSymbolicName);
				properties.put("upgrade.db.type", "any");
				properties.put(
					"upgrade.from.schema.version",
					upgradeInfo.getFromSchemaVersionString());
				properties.put(
					"upgrade.to.schema.version",
					upgradeInfo.getToSchemaVersionString());

				ServiceRegistration<UpgradeStep> serviceRegistration =
					_bundleContext.registerService(
						UpgradeStep.class, upgradeInfo.getUpgradeStep(),
						properties);

				serviceRegistrations.add(serviceRegistration);
			}

			return serviceRegistrations;
		}

		@Override
		public void modifiedService(
			ServiceReference<UpgradeStepRegistrator> serviceReference,
			Collection<ServiceRegistration<UpgradeStep>> serviceRegistrations) {
		}

		@Override
		public void removedService(
			ServiceReference<UpgradeStepRegistrator> serviceReference,
			Collection<ServiceRegistration<UpgradeStep>> serviceRegistrations) {

			for (ServiceRegistration<UpgradeStep> serviceRegistration :
					serviceRegistrations) {

				serviceRegistration.unregister();
			}
		}

	}

}