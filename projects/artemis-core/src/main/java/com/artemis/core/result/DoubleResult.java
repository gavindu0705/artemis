package com.artemis.core.result;

public class DoubleResult implements SingleResult {
	private String name;
	private Double value;

	public DoubleResult(String name, Double value) {
		this.name = name;
		this.value = value;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public Double getValue() {
		return value;
	}

}
