package com.artemis.mongo.dao;

import com.artemis.mongo.persistence.Conf;
import com.artemis.mongo.persistence.DBConfigHolder;
import com.artemis.mongo.persistence.MongoDao;
import com.artemis.mongo.persistence.MongoDaoImpl;
import com.artemis.mongo.po.Metadata;

public class MetadataDao extends MongoDaoImpl<Metadata> implements MongoDao<Metadata>, DBConfigHolder {
	private static MetadataDao instance = new MetadataDao();

	private MetadataDao() {
		super();
	}

	public static MetadataDao getInstance() {
		return instance;
	}

	@Override
	public Conf getConf() {
		return MongodbConfigDao.getInstance().getConf("artemis", "metadata");
	}
}
