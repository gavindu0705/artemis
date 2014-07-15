package com.artemis.core.tools;

public class PropertiesKey {
	private String domain;
	private String name;
	private String sign;

	public PropertiesKey(String domain, String name, String sign) {
		this.domain = domain;
		this.name = name;
		this.sign = sign;
	}

	public String getDomain() {
		return domain;
	}

	public String getName() {
		return name;
	}

	public String getSign() {
		return sign;
	}

	public String getKey() {
		return this.domain + "." + this.name + "." + this.sign;
	}

	@Override
	public String toString() {
		return this.domain + "." + name + "." + sign;
	}
}
