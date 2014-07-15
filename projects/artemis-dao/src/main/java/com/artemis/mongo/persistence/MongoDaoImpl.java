package com.artemis.mongo.persistence;

import java.util.ArrayList;
import java.util.List;

import com.artemis.core.log.ALogger;
import com.mongodb.DBObject;
import com.mongodb.WriteConcern;

public abstract class MongoDaoImpl<E extends MongoEntity> extends AbstractMongoDao<E> {
	private static final ALogger LOG = new ALogger(MongoDaoImpl.class);

	public MongoDaoImpl() {
		super();
	}

	@Override
	protected DBObject internalFindOne(DBObject query, DBObject fields) {
		long begin = System.currentTimeMillis();
		try {
			return getDbCollection().findOne(query, fields);
		} finally {
			LOG.performance(begin, System.currentTimeMillis(), "[QUERY] " + this.getDbCollection().getFullName(), query);
		}

	}

	@Override
	protected void internalUpdate(DBObject query, DBObject data, boolean upsert, boolean multi) {
		long begin = System.currentTimeMillis();
		try {
			getDbCollection().update(query, data, upsert, multi, WriteConcern.SAFE);
		} finally {
			LOG.performance(begin, System.currentTimeMillis(), "[UPDATE] " + this.getDbCollection().getFullName(), query);
		}
	}

	@Override
	protected void internalSave(DBObject data) {
		long begin = System.currentTimeMillis();
		try {
			getDbCollection().save(data, WriteConcern.SAFE);
		} finally {
			LOG.performance(begin, System.currentTimeMillis(), "[SAVE] " + this.getDbCollection().getFullName());
		}
	}

	@Override
	protected long internalCount(DBObject query) {
		long begin = System.currentTimeMillis();
		try {
			return getDbCollection().getCount(query);
		} finally {
			LOG.performance(begin, System.currentTimeMillis(), "[COUNT] " + this.getDbCollection().getFullName(), query);
		}
	}

	@Override
	protected void internalRemove(DBObject query) {
		long begin = System.currentTimeMillis();
		try {
			getDbCollection().remove(query, WriteConcern.SAFE);
		} finally {
			LOG.performance(begin, System.currentTimeMillis(), "[REMOVE] " + this.getDbCollection().getFullName(), query);
		}
	}

	@Override
	protected List<E> find(DBObject query, DBObject fields, DBObject sort, int skip, int limit) {
		long begin = System.currentTimeMillis();
		try {
			List<DBObject> objects = getDbCollection().find(query, fields).sort(sort).skip(skip).limit(limit).toArray();
			List<E> ret = new ArrayList<E>(objects.size());
			for (DBObject obj : objects) {
				ret.add(this.dbObjectToEntity(obj));
			}
			return ret;
		} finally {
			LOG.performance(begin, System.currentTimeMillis(), "[QUERY] " + this.getDbCollection().getFullName(), query);
		}
	}

}
