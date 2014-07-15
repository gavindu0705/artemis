package com.artemis.mongo.persistence;

import java.beans.PropertyDescriptor;
import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;

public abstract class AbstractJpaCompatibleDao<E extends Entity, K extends Serializable> extends AbstractEntityDao<E, K> {

	private final String host;
	private final int port;
	private final String dbName;
	private final String tableName;
	private final Map<String, PropertyInfo> fieldMap;
	private final Map<String, PropertyInfo> propertyMap;
	private String idColumnName;
	private boolean idGeneratedValue;

	public AbstractJpaCompatibleDao() {
		// entity annotation
		javax.persistence.Entity entity = this.getEntityClass().getAnnotation(javax.persistence.Entity.class);
		if (null == entity) {
			throw new RuntimeException("no Entity annotation found of entity class " + this.getEntityClass().getName());
		}
		// System.out.println(this.getClass().getName());
		if (this instanceof DBConfigHolder) {
			Conf conf = ((DBConfigHolder) this).getConf();
			if (conf == null) {
				throw new RuntimeException("no Conf exists in DBConfigHolder " + this.getClass().getName());
			}
			this.host = conf.getHost();
			this.port = conf.getPort();
			this.dbName = conf.getDb();
			this.tableName = conf.getCollection();
		} else {
			// host port annotation
			HostPort hostPort = this.getClass().getAnnotation(HostPort.class);
			if (hostPort == null) {
				throw new RuntimeException("no HostPort annotation found of entity class " + this.getClass().getName());
			}
			host = hostPort.host();
			port = hostPort.port();

			// db annotation
			DB db = this.getClass().getAnnotation(DB.class);
			if (db == null) {
				throw new RuntimeException("no DB annotation found of entity class " + this.getClass().getName());
			}
			dbName = db.name();

			// table annotation
			Table table = this.getClass().getAnnotation(Table.class);
			if (null == table) {
				throw new RuntimeException("no Table annotation found of entity class " + this.getClass().getName());
			}
			tableName = table.name();
			if (StringUtils.isEmpty(tableName)) {
				throw new RuntimeException("name of Table annotation is empty of entity class " + this.getClass().getName());
			}
			if (!tableName.toLowerCase().equals(tableName)) {
				throw new IllegalArgumentException("table name of class  [" + this.getClass().getName()
						+ "] must be lower cased, current is [" + tableName + "]");
			}
		}

		// fields annotation
		PropertyDescriptor[] propertyDescriptors = PropertyUtils.getPropertyDescriptors(this.getEntityClass());
		Map<String, PropertyInfo> _fieldMap = new HashMap<String, PropertyInfo>(propertyDescriptors.length);
		Map<String, PropertyInfo> _propertyMap = new HashMap<String, PropertyInfo>(propertyDescriptors.length);
		boolean idFind = false;
		for (PropertyDescriptor descriptor : propertyDescriptors) {
			Method readMethod = descriptor.getReadMethod();
			if (null != readMethod) {
				Column column = readMethod.getAnnotation(Column.class);
				if (null == column) {
					continue;
				}
				Id id = readMethod.getAnnotation(Id.class);
				if (null != id) {
					if (idFind) {
						throw new RuntimeException("multi Id annotation found of entity class " + this.getEntityClass().getName());
					}
					idFind = true;
					idColumnName = descriptor.getName();
					GeneratedValue generatedValue = readMethod.getAnnotation(GeneratedValue.class);
					idGeneratedValue = generatedValue != null;
				}
				Transient aTransient = readMethod.getAnnotation(Transient.class);
				PropertyInfo info = new PropertyInfo(descriptor, column, null != id, null != aTransient, this.getEntityClass());
				_propertyMap.put(descriptor.getName(), info);
				_fieldMap.put(column.name(), info);
			}
		}
		propertyMap = Collections.unmodifiableMap(_propertyMap);
		fieldMap = Collections.unmodifiableMap(_fieldMap);

	}

	public String getHost() {
		return host;
	}

	public int getPort() {
		return port;
	}

	public String getDbName() {
		return dbName;
	}

	/**
	 * 得到表名
	 * 
	 * @return
	 */
	public String getTableName() {
		return tableName;
	}

	/**
	 * 得到字段映射表，key为实体类的字段名（并非实际数据库的字段名）
	 * 
	 * @return
	 */
	public Map<String, PropertyInfo> getPropertyMap() {
		return propertyMap;
	}

	/**
	 * 得到字段property的映射，property为实体类的字段名（并非实际数据库的字段名）
	 * 
	 * @param property
	 * @return
	 */
	public PropertyInfo getPropertyInfoByProperty(String property) {
		PropertyInfo ret = propertyMap.get(property);
		if (null == ret) {
			throw new IllegalArgumentException("no property named [" + property + "] found of entity class "
					+ this.getEntityClass().getName());
		}
		return ret;
	}

	/**
	 * 得到数据库字段映射表，key为数据库的字段名（并非实体类的字段名）
	 * 
	 * @return
	 */
	public Map<String, PropertyInfo> getFieldMap() {
		return fieldMap;
	}

	/**
	 * 得到数据库字段field的映射，field为数据库的字段名（并非实体类的字段名）
	 * 
	 * @param field
	 * @return
	 */
	public PropertyInfo getPropertyInfoByField(String field) {
		PropertyInfo ret = fieldMap.get(field);
		if (null == ret) {
			throw new IllegalArgumentException("no field named [" + field + "] found of table " + tableName);
		}
		return ret;
	}

	/**
	 * 得到id字段的字段名
	 * 
	 * @return
	 */
	public String getIdColumnName() {
		return idColumnName;
	}

	/**
	 * id字段是否为自动生成
	 * 
	 * @return
	 */
	public boolean isIdGeneratedValue() {
		return idGeneratedValue;
	}

	/**
	 * 实体类字段属性信息
	 * 
	 * @author velna
	 * 
	 */
	public static class PropertyInfo {
		private final PropertyDescriptor propertyDescriptor;
		private final Column column;
		private final Class<?> clazz;
		private final boolean isId;
		private final boolean transients;

		public PropertyInfo(PropertyDescriptor propertyDescriptor, Column column, boolean isId, boolean transients, Class<?> clazz) {
			if (!column.name().toLowerCase().equals(column.name())) {
				// LOG.warn("name of column [" + propertyDescriptor.getName() +
				// "] must be lower cased of class " + clazz.getName()
				// + ", current is [" + column.name() + "]");
			}
			this.isId = isId;
			this.propertyDescriptor = propertyDescriptor;
			this.column = column;
			this.clazz = clazz;
			this.transients = transients;
		}

		/**
		 * 得到字段的描述信息
		 * 
		 * @return
		 */
		public PropertyDescriptor getPropertyDescriptor() {
			return propertyDescriptor;
		}

		/**
		 * 得到Column注解
		 * 
		 * @return
		 */
		public Column getColumn() {
			return column;
		}

		/**
		 * 是否为id字段
		 * 
		 * @return
		 */
		public boolean isID() {
			return isId;
		}

		/**
		 * 得到所属的实体类
		 * 
		 * @return
		 */
		public Class<?> getClazz() {
			return clazz;
		}

		/**
		 * 是否为瞬态的
		 * 
		 * @return
		 */
		public boolean isTransient() {
			return transients;
		}

	}
}
