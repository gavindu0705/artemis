package com.artemis.mongo.dao;

import com.artemis.mongo.persistence.Conf;
import com.artemis.mongo.persistence.DBConfigHolder;
import com.artemis.mongo.persistence.MongoDao;
import com.artemis.mongo.persistence.MongoDaoImpl;
import com.artemis.mongo.po.Errs;

public class ErrsDao extends MongoDaoImpl<Errs> implements MongoDao<Errs>, DBConfigHolder {
	private static ErrsDao instance = new ErrsDao();

	private ErrsDao() {

	}

	public static ErrsDao getInstance() {
		return instance;
	}

	@Override
	public Conf getConf() {
		return MongodbConfigDao.getInstance().getConf("artemis", "errs");
	}
}
