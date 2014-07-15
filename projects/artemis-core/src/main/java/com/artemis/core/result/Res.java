package com.artemis.core.result;

public class Res {
	private String name;
	private Object value;
	public Res(String name, Object value) {
		this.name = name;
		this.value = value;
	}

	public String getName() {
		return name;
	}

	public Object getValue() {
		return value;
	}
}
