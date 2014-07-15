package com.artemis.bkoff.model;

import java.util.List;

import com.artemis.mongo.po.MongodbConfig;

public class ServerConfigModel extends BaseModel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4405543478557926498L;
	private String id;
	private MongodbConfig mongodbConfig;
	private List<MongodbConfig> mongodbConfigList;

	public List<MongodbConfig> getMongodbConfigList() {
		return mongodbConfigList;
	}

	public void setMongodbConfigList(List<MongodbConfig> mongodbConfigList) {
		this.mongodbConfigList = mongodbConfigList;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public MongodbConfig getMongodbConfig() {
		return mongodbConfig;
	}

	public void setMongodbConfig(MongodbConfig mongodbConfig) {
		this.mongodbConfig = mongodbConfig;
	}

}
