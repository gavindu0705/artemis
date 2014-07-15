package com.artemis.mongo.dao;

import java.util.List;

import com.artemis.core.bean.Proxy;
import com.artemis.mongo.persistence.Conf;
import com.artemis.mongo.persistence.DBConfigHolder;
import com.artemis.mongo.persistence.MongoDao;
import com.artemis.mongo.persistence.MongoDaoImpl;
import com.artemis.mongo.po.GrandCloud;
import com.mongodb.BasicDBObject;

public class GrandCloudDao extends MongoDaoImpl<GrandCloud> implements MongoDao<GrandCloud>, DBConfigHolder {
	private static GrandCloudDao instance = new GrandCloudDao();

	private GrandCloudDao() {
		super();
	}

	public static GrandCloudDao getInstance() {
		return instance;
	}

	@Override
	public Conf getConf() {
		return MongodbConfigDao.getInstance().getConf("artemis", "grand_cloud");
	}

	public List<GrandCloud> findGrandCloud() {
		return this.findAll();
	}

	public void updateAllUsingToEnabel() {
		for (GrandCloud cloud : this.findAll(new BasicDBObject("status", Proxy.USING))) {
			this.updateById(new BasicDBObject("status", Proxy.ENABLE), cloud.getId());
		}
	}
}
