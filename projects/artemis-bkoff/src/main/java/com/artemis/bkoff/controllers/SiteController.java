package com.artemis.bkoff.controllers;

import java.util.Date;
import java.util.List;

import com.artemis.bkoff.model.SiteModel;
import com.artemis.mongo.dao.SiteConfigDao;
import com.artemis.mongo.po.SiteConfig;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

public class SiteController {
	private SiteConfigDao siteConfigDao = SiteConfigDao.getInstance();

	public Object listAction(SiteModel model) {
		List<SiteConfig> siteConfigList = this.siteConfigDao.findAll();
		model.setSiteConfigList(siteConfigList);
		return model;
	}

	public Object createAction(SiteModel model) {
		if (model.getId() != null) {
			model.setSiteConfig(this.siteConfigDao.findById(model.getId()));
		}
		return model;
	}

	public Object deleteAction(SiteModel model) {
		this.siteConfigDao.deleteById(model.getId());
		return model.redirect("/site/list");
	}

	public Object saveAction(SiteModel model) {
		if (model.getSiteConfig() != null) {
			if (model.getSiteConfig().getId() != null) {
				DBObject data = new BasicDBObject();
				data.put("name", model.getSiteConfig().getName());
				data.put("root", model.getSiteConfig().getRoot());
				data.put("charset", model.getSiteConfig().getCharset());
				data.put("shot_rate", model.getSiteConfig().getShotRate());
				data.put("cloud_rate", model.getSiteConfig().getCloudRate());
				this.siteConfigDao.updateById(data, model.getSiteConfig().getId());
			} else {
				SiteConfig entity = model.getSiteConfig();
				entity.setCreationDate(new Date());
				this.siteConfigDao.save(model.getSiteConfig());
			}
		}
		return model.redirect("/site/list");
	}
}
