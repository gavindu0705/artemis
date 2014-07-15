package com.artemis.core.result;

public class StringResult implements SingleResult {
	private String name;
	private String value;

	public StringResult(String name, String value) {
		this.name = name;
		this.value = value;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public String getValue() {
		return value;
	}
}
