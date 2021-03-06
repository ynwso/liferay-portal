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

package com.liferay.change.tracking.engine.model.impl;

import aQute.bnd.annotation.ProviderType;

import com.liferay.change.tracking.engine.model.CTECollection;
import com.liferay.change.tracking.engine.model.CTECollectionModel;

import com.liferay.expando.kernel.model.ExpandoBridge;
import com.liferay.expando.kernel.util.ExpandoBridgeFactoryUtil;

import com.liferay.petra.string.StringBundler;

import com.liferay.portal.kernel.bean.AutoEscapeBeanHandler;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.impl.BaseModelImpl;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.io.Serializable;

import java.sql.Types;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * The base model implementation for the CTECollection service. Represents a row in the &quot;CTECollection&quot; database table, with each column mapped to a property of this class.
 *
 * <p>
 * This implementation and its corresponding interface {@link CTECollectionModel} exist only as a container for the default property accessors generated by ServiceBuilder. Helper methods and all application logic should be put in {@link CTECollectionImpl}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see CTECollectionImpl
 * @see CTECollection
 * @see CTECollectionModel
 * @generated
 */
@ProviderType
public class CTECollectionModelImpl extends BaseModelImpl<CTECollection>
	implements CTECollectionModel {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. All methods that expect a cte collection model instance should use the {@link CTECollection} interface instead.
	 */
	public static final String TABLE_NAME = "CTECollection";
	public static final Object[][] TABLE_COLUMNS = {
			{ "cteCollectionId", Types.BIGINT },
			{ "companyId", Types.BIGINT },
			{ "userId", Types.BIGINT },
			{ "userName", Types.VARCHAR },
			{ "createDate", Types.TIMESTAMP },
			{ "modifiedDate", Types.TIMESTAMP },
			{ "name", Types.VARCHAR },
			{ "description", Types.VARCHAR },
			{ "status", Types.INTEGER },
			{ "statusByUserId", Types.BIGINT },
			{ "statusByUserName", Types.VARCHAR },
			{ "statusDate", Types.TIMESTAMP }
		};
	public static final Map<String, Integer> TABLE_COLUMNS_MAP = new HashMap<String, Integer>();

	static {
		TABLE_COLUMNS_MAP.put("cteCollectionId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("companyId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("userId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("userName", Types.VARCHAR);
		TABLE_COLUMNS_MAP.put("createDate", Types.TIMESTAMP);
		TABLE_COLUMNS_MAP.put("modifiedDate", Types.TIMESTAMP);
		TABLE_COLUMNS_MAP.put("name", Types.VARCHAR);
		TABLE_COLUMNS_MAP.put("description", Types.VARCHAR);
		TABLE_COLUMNS_MAP.put("status", Types.INTEGER);
		TABLE_COLUMNS_MAP.put("statusByUserId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("statusByUserName", Types.VARCHAR);
		TABLE_COLUMNS_MAP.put("statusDate", Types.TIMESTAMP);
	}

	public static final String TABLE_SQL_CREATE = "create table CTECollection (cteCollectionId LONG not null primary key,companyId LONG,userId LONG,userName VARCHAR(75) null,createDate DATE null,modifiedDate DATE null,name VARCHAR(75) null,description VARCHAR(75) null,status INTEGER,statusByUserId LONG,statusByUserName VARCHAR(75) null,statusDate DATE null)";
	public static final String TABLE_SQL_DROP = "drop table CTECollection";
	public static final String ORDER_BY_JPQL = " ORDER BY cteCollection.cteCollectionId ASC";
	public static final String ORDER_BY_SQL = " ORDER BY CTECollection.cteCollectionId ASC";
	public static final String DATA_SOURCE = "liferayDataSource";
	public static final String SESSION_FACTORY = "liferaySessionFactory";
	public static final String TX_MANAGER = "liferayTransactionManager";
	public static final boolean ENTITY_CACHE_ENABLED = GetterUtil.getBoolean(com.liferay.change.tracking.engine.service.util.ServiceProps.get(
				"value.object.entity.cache.enabled.com.liferay.change.tracking.engine.model.CTECollection"),
			true);
	public static final boolean FINDER_CACHE_ENABLED = GetterUtil.getBoolean(com.liferay.change.tracking.engine.service.util.ServiceProps.get(
				"value.object.finder.cache.enabled.com.liferay.change.tracking.engine.model.CTECollection"),
			true);
	public static final boolean COLUMN_BITMASK_ENABLED = false;
	public static final String MAPPING_TABLE_COLLECTIONS_ENTRIES_NAME = "Collections_Entries";
	public static final Object[][] MAPPING_TABLE_COLLECTIONS_ENTRIES_COLUMNS = {
			{ "companyId", Types.BIGINT },
			{ "cteCollectionId", Types.BIGINT },
			{ "cteEntryId", Types.BIGINT }
		};
	public static final String MAPPING_TABLE_COLLECTIONS_ENTRIES_SQL_CREATE = "create table Collections_Entries (companyId LONG not null,cteCollectionId LONG not null,cteEntryId LONG not null,primary key (cteCollectionId, cteEntryId))";
	public static final boolean FINDER_CACHE_ENABLED_COLLECTIONS_ENTRIES = GetterUtil.getBoolean(com.liferay.change.tracking.engine.service.util.ServiceProps.get(
				"value.object.finder.cache.enabled.Collections_Entries"), true);
	public static final long LOCK_EXPIRATION_TIME = GetterUtil.getLong(com.liferay.change.tracking.engine.service.util.ServiceProps.get(
				"lock.expiration.time.com.liferay.change.tracking.engine.model.CTECollection"));

	public CTECollectionModelImpl() {
	}

	@Override
	public long getPrimaryKey() {
		return _cteCollectionId;
	}

	@Override
	public void setPrimaryKey(long primaryKey) {
		setCteCollectionId(primaryKey);
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _cteCollectionId;
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		setPrimaryKey(((Long)primaryKeyObj).longValue());
	}

	@Override
	public Class<?> getModelClass() {
		return CTECollection.class;
	}

	@Override
	public String getModelClassName() {
		return CTECollection.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("cteCollectionId", getCteCollectionId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("name", getName());
		attributes.put("description", getDescription());
		attributes.put("status", getStatus());
		attributes.put("statusByUserId", getStatusByUserId());
		attributes.put("statusByUserName", getStatusByUserName());
		attributes.put("statusDate", getStatusDate());

		attributes.put("entityCacheEnabled", isEntityCacheEnabled());
		attributes.put("finderCacheEnabled", isFinderCacheEnabled());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long cteCollectionId = (Long)attributes.get("cteCollectionId");

		if (cteCollectionId != null) {
			setCteCollectionId(cteCollectionId);
		}

		Long companyId = (Long)attributes.get("companyId");

		if (companyId != null) {
			setCompanyId(companyId);
		}

		Long userId = (Long)attributes.get("userId");

		if (userId != null) {
			setUserId(userId);
		}

		String userName = (String)attributes.get("userName");

		if (userName != null) {
			setUserName(userName);
		}

		Date createDate = (Date)attributes.get("createDate");

		if (createDate != null) {
			setCreateDate(createDate);
		}

		Date modifiedDate = (Date)attributes.get("modifiedDate");

		if (modifiedDate != null) {
			setModifiedDate(modifiedDate);
		}

		String name = (String)attributes.get("name");

		if (name != null) {
			setName(name);
		}

		String description = (String)attributes.get("description");

		if (description != null) {
			setDescription(description);
		}

		Integer status = (Integer)attributes.get("status");

		if (status != null) {
			setStatus(status);
		}

		Long statusByUserId = (Long)attributes.get("statusByUserId");

		if (statusByUserId != null) {
			setStatusByUserId(statusByUserId);
		}

		String statusByUserName = (String)attributes.get("statusByUserName");

		if (statusByUserName != null) {
			setStatusByUserName(statusByUserName);
		}

		Date statusDate = (Date)attributes.get("statusDate");

		if (statusDate != null) {
			setStatusDate(statusDate);
		}
	}

	@Override
	public long getCteCollectionId() {
		return _cteCollectionId;
	}

	@Override
	public void setCteCollectionId(long cteCollectionId) {
		_cteCollectionId = cteCollectionId;
	}

	@Override
	public long getCompanyId() {
		return _companyId;
	}

	@Override
	public void setCompanyId(long companyId) {
		_companyId = companyId;
	}

	@Override
	public long getUserId() {
		return _userId;
	}

	@Override
	public void setUserId(long userId) {
		_userId = userId;
	}

	@Override
	public String getUserUuid() {
		try {
			User user = UserLocalServiceUtil.getUserById(getUserId());

			return user.getUuid();
		}
		catch (PortalException pe) {
			return "";
		}
	}

	@Override
	public void setUserUuid(String userUuid) {
	}

	@Override
	public String getUserName() {
		if (_userName == null) {
			return "";
		}
		else {
			return _userName;
		}
	}

	@Override
	public void setUserName(String userName) {
		_userName = userName;
	}

	@Override
	public Date getCreateDate() {
		return _createDate;
	}

	@Override
	public void setCreateDate(Date createDate) {
		_createDate = createDate;
	}

	@Override
	public Date getModifiedDate() {
		return _modifiedDate;
	}

	public boolean hasSetModifiedDate() {
		return _setModifiedDate;
	}

	@Override
	public void setModifiedDate(Date modifiedDate) {
		_setModifiedDate = true;

		_modifiedDate = modifiedDate;
	}

	@Override
	public String getName() {
		if (_name == null) {
			return "";
		}
		else {
			return _name;
		}
	}

	@Override
	public void setName(String name) {
		_name = name;
	}

	@Override
	public String getDescription() {
		if (_description == null) {
			return "";
		}
		else {
			return _description;
		}
	}

	@Override
	public void setDescription(String description) {
		_description = description;
	}

	@Override
	public int getStatus() {
		return _status;
	}

	@Override
	public void setStatus(int status) {
		_status = status;
	}

	@Override
	public long getStatusByUserId() {
		return _statusByUserId;
	}

	@Override
	public void setStatusByUserId(long statusByUserId) {
		_statusByUserId = statusByUserId;
	}

	@Override
	public String getStatusByUserUuid() {
		try {
			User user = UserLocalServiceUtil.getUserById(getStatusByUserId());

			return user.getUuid();
		}
		catch (PortalException pe) {
			return "";
		}
	}

	@Override
	public void setStatusByUserUuid(String statusByUserUuid) {
	}

	@Override
	public String getStatusByUserName() {
		if (_statusByUserName == null) {
			return "";
		}
		else {
			return _statusByUserName;
		}
	}

	@Override
	public void setStatusByUserName(String statusByUserName) {
		_statusByUserName = statusByUserName;
	}

	@Override
	public Date getStatusDate() {
		return _statusDate;
	}

	@Override
	public void setStatusDate(Date statusDate) {
		_statusDate = statusDate;
	}

	@Override
	public boolean isApproved() {
		if (getStatus() == WorkflowConstants.STATUS_APPROVED) {
			return true;
		}
		else {
			return false;
		}
	}

	@Override
	public boolean isDenied() {
		if (getStatus() == WorkflowConstants.STATUS_DENIED) {
			return true;
		}
		else {
			return false;
		}
	}

	@Override
	public boolean isDraft() {
		if (getStatus() == WorkflowConstants.STATUS_DRAFT) {
			return true;
		}
		else {
			return false;
		}
	}

	@Override
	public boolean isExpired() {
		if (getStatus() == WorkflowConstants.STATUS_EXPIRED) {
			return true;
		}
		else {
			return false;
		}
	}

	@Override
	public boolean isInactive() {
		if (getStatus() == WorkflowConstants.STATUS_INACTIVE) {
			return true;
		}
		else {
			return false;
		}
	}

	@Override
	public boolean isIncomplete() {
		if (getStatus() == WorkflowConstants.STATUS_INCOMPLETE) {
			return true;
		}
		else {
			return false;
		}
	}

	@Override
	public boolean isPending() {
		if (getStatus() == WorkflowConstants.STATUS_PENDING) {
			return true;
		}
		else {
			return false;
		}
	}

	@Override
	public boolean isScheduled() {
		if (getStatus() == WorkflowConstants.STATUS_SCHEDULED) {
			return true;
		}
		else {
			return false;
		}
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return ExpandoBridgeFactoryUtil.getExpandoBridge(getCompanyId(),
			CTECollection.class.getName(), getPrimaryKey());
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		ExpandoBridge expandoBridge = getExpandoBridge();

		expandoBridge.setAttributes(serviceContext);
	}

	@Override
	public CTECollection toEscapedModel() {
		if (_escapedModel == null) {
			_escapedModel = (CTECollection)ProxyUtil.newProxyInstance(_classLoader,
					_escapedModelInterfaces, new AutoEscapeBeanHandler(this));
		}

		return _escapedModel;
	}

	@Override
	public Object clone() {
		CTECollectionImpl cteCollectionImpl = new CTECollectionImpl();

		cteCollectionImpl.setCteCollectionId(getCteCollectionId());
		cteCollectionImpl.setCompanyId(getCompanyId());
		cteCollectionImpl.setUserId(getUserId());
		cteCollectionImpl.setUserName(getUserName());
		cteCollectionImpl.setCreateDate(getCreateDate());
		cteCollectionImpl.setModifiedDate(getModifiedDate());
		cteCollectionImpl.setName(getName());
		cteCollectionImpl.setDescription(getDescription());
		cteCollectionImpl.setStatus(getStatus());
		cteCollectionImpl.setStatusByUserId(getStatusByUserId());
		cteCollectionImpl.setStatusByUserName(getStatusByUserName());
		cteCollectionImpl.setStatusDate(getStatusDate());

		cteCollectionImpl.resetOriginalValues();

		return cteCollectionImpl;
	}

	@Override
	public int compareTo(CTECollection cteCollection) {
		long primaryKey = cteCollection.getPrimaryKey();

		if (getPrimaryKey() < primaryKey) {
			return -1;
		}
		else if (getPrimaryKey() > primaryKey) {
			return 1;
		}
		else {
			return 0;
		}
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof CTECollection)) {
			return false;
		}

		CTECollection cteCollection = (CTECollection)obj;

		long primaryKey = cteCollection.getPrimaryKey();

		if (getPrimaryKey() == primaryKey) {
			return true;
		}
		else {
			return false;
		}
	}

	@Override
	public int hashCode() {
		return (int)getPrimaryKey();
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return ENTITY_CACHE_ENABLED;
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return FINDER_CACHE_ENABLED;
	}

	@Override
	public void resetOriginalValues() {
		CTECollectionModelImpl cteCollectionModelImpl = this;

		cteCollectionModelImpl._setModifiedDate = false;
	}

	@Override
	public CacheModel<CTECollection> toCacheModel() {
		CTECollectionCacheModel cteCollectionCacheModel = new CTECollectionCacheModel();

		cteCollectionCacheModel.cteCollectionId = getCteCollectionId();

		cteCollectionCacheModel.companyId = getCompanyId();

		cteCollectionCacheModel.userId = getUserId();

		cteCollectionCacheModel.userName = getUserName();

		String userName = cteCollectionCacheModel.userName;

		if ((userName != null) && (userName.length() == 0)) {
			cteCollectionCacheModel.userName = null;
		}

		Date createDate = getCreateDate();

		if (createDate != null) {
			cteCollectionCacheModel.createDate = createDate.getTime();
		}
		else {
			cteCollectionCacheModel.createDate = Long.MIN_VALUE;
		}

		Date modifiedDate = getModifiedDate();

		if (modifiedDate != null) {
			cteCollectionCacheModel.modifiedDate = modifiedDate.getTime();
		}
		else {
			cteCollectionCacheModel.modifiedDate = Long.MIN_VALUE;
		}

		cteCollectionCacheModel.name = getName();

		String name = cteCollectionCacheModel.name;

		if ((name != null) && (name.length() == 0)) {
			cteCollectionCacheModel.name = null;
		}

		cteCollectionCacheModel.description = getDescription();

		String description = cteCollectionCacheModel.description;

		if ((description != null) && (description.length() == 0)) {
			cteCollectionCacheModel.description = null;
		}

		cteCollectionCacheModel.status = getStatus();

		cteCollectionCacheModel.statusByUserId = getStatusByUserId();

		cteCollectionCacheModel.statusByUserName = getStatusByUserName();

		String statusByUserName = cteCollectionCacheModel.statusByUserName;

		if ((statusByUserName != null) && (statusByUserName.length() == 0)) {
			cteCollectionCacheModel.statusByUserName = null;
		}

		Date statusDate = getStatusDate();

		if (statusDate != null) {
			cteCollectionCacheModel.statusDate = statusDate.getTime();
		}
		else {
			cteCollectionCacheModel.statusDate = Long.MIN_VALUE;
		}

		return cteCollectionCacheModel;
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(25);

		sb.append("{cteCollectionId=");
		sb.append(getCteCollectionId());
		sb.append(", companyId=");
		sb.append(getCompanyId());
		sb.append(", userId=");
		sb.append(getUserId());
		sb.append(", userName=");
		sb.append(getUserName());
		sb.append(", createDate=");
		sb.append(getCreateDate());
		sb.append(", modifiedDate=");
		sb.append(getModifiedDate());
		sb.append(", name=");
		sb.append(getName());
		sb.append(", description=");
		sb.append(getDescription());
		sb.append(", status=");
		sb.append(getStatus());
		sb.append(", statusByUserId=");
		sb.append(getStatusByUserId());
		sb.append(", statusByUserName=");
		sb.append(getStatusByUserName());
		sb.append(", statusDate=");
		sb.append(getStatusDate());
		sb.append("}");

		return sb.toString();
	}

	@Override
	public String toXmlString() {
		StringBundler sb = new StringBundler(40);

		sb.append("<model><model-name>");
		sb.append("com.liferay.change.tracking.engine.model.CTECollection");
		sb.append("</model-name>");

		sb.append(
			"<column><column-name>cteCollectionId</column-name><column-value><![CDATA[");
		sb.append(getCteCollectionId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>companyId</column-name><column-value><![CDATA[");
		sb.append(getCompanyId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>userId</column-name><column-value><![CDATA[");
		sb.append(getUserId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>userName</column-name><column-value><![CDATA[");
		sb.append(getUserName());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>createDate</column-name><column-value><![CDATA[");
		sb.append(getCreateDate());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>modifiedDate</column-name><column-value><![CDATA[");
		sb.append(getModifiedDate());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>name</column-name><column-value><![CDATA[");
		sb.append(getName());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>description</column-name><column-value><![CDATA[");
		sb.append(getDescription());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>status</column-name><column-value><![CDATA[");
		sb.append(getStatus());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>statusByUserId</column-name><column-value><![CDATA[");
		sb.append(getStatusByUserId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>statusByUserName</column-name><column-value><![CDATA[");
		sb.append(getStatusByUserName());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>statusDate</column-name><column-value><![CDATA[");
		sb.append(getStatusDate());
		sb.append("]]></column-value></column>");

		sb.append("</model>");

		return sb.toString();
	}

	private static final ClassLoader _classLoader = CTECollection.class.getClassLoader();
	private static final Class<?>[] _escapedModelInterfaces = new Class[] {
			CTECollection.class, ModelWrapper.class
		};
	private long _cteCollectionId;
	private long _companyId;
	private long _userId;
	private String _userName;
	private Date _createDate;
	private Date _modifiedDate;
	private boolean _setModifiedDate;
	private String _name;
	private String _description;
	private int _status;
	private long _statusByUserId;
	private String _statusByUserName;
	private Date _statusDate;
	private CTECollection _escapedModel;
}