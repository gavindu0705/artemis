package com.artemis.mongo.persistence;


public interface MongoEntity extends Entity {
	public final static String ID = "_id";
	
	String getId();

	void setId(String id);
}
