package com.artemis.core;


public enum PriorityEnum {
	NORMAL(0), HIGH(1), HIGHEST(2);
	private final int priority;

	private PriorityEnum(int priority) {
		this.priority = priority;
	}

	public int getPriority() {
		return priority;
	}

}
