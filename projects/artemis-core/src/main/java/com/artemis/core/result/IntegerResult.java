package com.artemis.core.result;

public class IntegerResult implements SingleResult {
	private String name;
	private Integer value;

	public IntegerResult(String name, Integer value) {
		this.name = name;
		this.value = value;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public Integer getValue() {
		return value;
	}

}
