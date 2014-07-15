package com.artemis.bkoff.controllers;

import java.util.Date;

import com.artemis.bkoff.model.ServerConfigModel;
import com.artemis.mongo.dao.MongodbConfigDao;
import com.artemis.mongo.po.MongodbConfig;

public class MongoServerConfigController {
	private MongodbConfigDao mongodbConfigDao = MongodbConfigDao.getInstance();

	public Object listAction(ServerConfigModel model) {
		model.setMongodbConfigList(mongodbConfigDao.findAll());
		return model;
	}

	public Object editAction(ServerConfigModel model) {
		if (model.getId() != null) {
			model.setMongodbConfig(this.mongodbConfigDao.findById(model.getId()));
		}
		return model.forward("/mongoServerConfig/create");
	}

	public Object createAction(ServerConfigModel model) {
		MongodbConfig entity = model.getMongodbConfig();
		entity.setCreationDate(new Date());
		this.mongodbConfigDao.save(entity);
		return model.redirect("/mongoServerConfig/list");
	}

	public Object deleteAction(ServerConfigModel model) {
		if (model.getId() != null) {
			mongodbConfigDao.deleteById(model.getId());
		}
		return model.redirect("/mongoServerConfig/list");
	}
}
