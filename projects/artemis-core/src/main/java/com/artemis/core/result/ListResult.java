package com.artemis.core.result;

import java.util.List;

public class ListResult implements SingleResult {
	private String name;
	private List<Object> value;

	public ListResult(String name, List<Object> value) {
		this.name = name;
		this.value = value;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public Object getValue() {
		return value;
	}
}
