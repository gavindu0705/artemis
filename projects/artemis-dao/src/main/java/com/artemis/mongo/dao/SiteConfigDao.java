package com.artemis.mongo.dao;

import com.artemis.mongo.persistence.Conf;
import com.artemis.mongo.persistence.DBConfigHolder;
import com.artemis.mongo.persistence.MongoDao;
import com.artemis.mongo.persistence.MongoDaoImpl;
import com.artemis.mongo.po.SiteConfig;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

public class SiteConfigDao extends MongoDaoImpl<SiteConfig> implements MongoDao<SiteConfig>, DBConfigHolder {
	private static SiteConfigDao instance = new SiteConfigDao();

	private SiteConfigDao() {
	}

	public static SiteConfigDao getInstance() {
		return instance;
	}

	@Override
	public Conf getConf() {
		return MongodbConfigDao.getInstance().getConf("artemis", "site_config");
	}
	
	public SiteConfig findSiteConfigByRoot(String root) {
		DBObject q = new BasicDBObject();
		q.put("root", root);
		return this.findOne(q);
	}
	
}
