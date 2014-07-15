package com.artemis.mongo.persistence;

public class Conf {
	private final String host;
	private final int port;
	private final String db;
	private final String collection;

	public Conf(String host, int port, String db, String collection) {
		this.host = host;
		this.port = port;
		this.db = db;
		this.collection = collection;
	}

	public String getHost() {
		return host;
	}

	public int getPort() {
		return port;
	}

	public String getDb() {
		return db;
	}

	public String getCollection() {
		return collection;
	}

}
