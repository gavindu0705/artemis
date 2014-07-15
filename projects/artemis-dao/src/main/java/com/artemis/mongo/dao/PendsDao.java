package com.artemis.mongo.dao;

import com.artemis.mongo.persistence.Conf;
import com.artemis.mongo.persistence.DBConfigHolder;
import com.artemis.mongo.persistence.MongoDao;
import com.artemis.mongo.persistence.MongoDaoImpl;
import com.artemis.mongo.po.Pends;

public class PendsDao extends MongoDaoImpl<Pends> implements MongoDao<Pends>, DBConfigHolder {
	private static PendsDao instance = new PendsDao();

	private PendsDao() {

	}

	public static PendsDao getInstance() {
		return instance;
	}

	@Override
	public Conf getConf() {
		return MongodbConfigDao.getInstance().getConf("artemis", "pends");
	}
}
