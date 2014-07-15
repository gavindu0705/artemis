package com.artemis.mongo.persistence;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;


public abstract class AbstractEntityDao<E extends Entity, K extends Serializable> implements EntityDao<E, K> {

	protected final Class<E> entityClass;
	
	@SuppressWarnings("unchecked")
	public AbstractEntityDao() {
		Type type = this.getClass().getGenericSuperclass();
		if (null == type || !(type instanceof ParameterizedType)) {
			throw new RuntimeException("no entity class defined of " + this.getClass().getName());
		}
		ParameterizedType parameterizedType = (ParameterizedType) this.getClass().getGenericSuperclass();
		Type[] ts = parameterizedType.getActualTypeArguments();
		if (null == ts || ts.length == 0) {
			throw new RuntimeException("no entity class defined of " + this.getClass().getName());
		}
		entityClass = (Class<E>) ts[0];
	}

	
	@Override
	public Class<E> getEntityClass() {
		return entityClass;
	}
}
