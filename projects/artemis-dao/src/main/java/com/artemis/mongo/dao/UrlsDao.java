package com.artemis.mongo.dao;

import com.artemis.mongo.persistence.Conf;
import com.artemis.mongo.persistence.DBConfigHolder;
import com.artemis.mongo.persistence.MongoDao;
import com.artemis.mongo.persistence.MongoDaoImpl;
import com.artemis.mongo.po.Urls;

public class UrlsDao extends MongoDaoImpl<Urls> implements MongoDao<Urls>, DBConfigHolder {
	private static UrlsDao instance = new UrlsDao();

	private UrlsDao() {

	}

	public static UrlsDao getInstance() {
		return instance;
	}

	@Override
	public Conf getConf() {
		return MongodbConfigDao.getInstance().getConf("artemis", "urls");
	}

	@Override
	public void save(Urls entity) {
		super.save(entity);
	}

}
