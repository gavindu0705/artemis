package com.artemis.mongo.persistence;

public interface MongoDao<E extends MongoEntity> extends EntityDao<E, String> {
	
}
