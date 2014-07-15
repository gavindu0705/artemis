package com.artemis.mongo.dao;

import com.artemis.mongo.persistence.Conf;
import com.artemis.mongo.persistence.DBConfigHolder;
import com.artemis.mongo.persistence.MongoDao;
import com.artemis.mongo.persistence.MongoDaoImpl;
import com.artemis.mongo.po.UrlRoad;

public class UrlRoadDao extends MongoDaoImpl<UrlRoad> implements MongoDao<UrlRoad>, DBConfigHolder {
	private static UrlRoadDao instance = new UrlRoadDao();

	private UrlRoadDao() {

	}

	public static UrlRoadDao getInstance() {
		return instance;
	}

	@Override
	public Conf getConf() {
		return MongodbConfigDao.getInstance().getConf("artemis", "url_road");
	}

}
