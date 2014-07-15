package com.artemis.service.fangjia.result;

import com.artemis.core.result.MutiResult;

public interface HouseBaseResult extends MutiResult {
	Double getArea();

	Integer getRoom();

	Integer getHall();

	Integer getToilet();

	Integer getPrice();

	String getBuildDate();

	String getDirection();

	String getFitment();

	Integer getHeight();

	Integer getFloor();

}
