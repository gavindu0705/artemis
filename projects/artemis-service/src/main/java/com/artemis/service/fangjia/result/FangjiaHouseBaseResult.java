package com.artemis.service.fangjia.result;

import java.util.ArrayList;
import java.util.List;

import com.artemis.core.result.Res;

public class FangjiaHouseBaseResult implements HouseBaseResult {
	private Double area;
	private Integer room;
	private Integer hall;
	private Integer toilet;
	private Integer price;
	private Integer floor;
	private Integer height;
	private String fitment;
	private String direction;
	private String buildDate;
	private String kind;
	private String buildType;

	public Double getArea() {
		return area;
	}

	public void setArea(Double area) {
		this.area = area;
	}

	public Integer getRoom() {
		return room;
	}

	public void setRoom(Integer room) {
		this.room = room;
	}

	public Integer getHall() {
		return hall;
	}

	public void setHall(Integer hall) {
		this.hall = hall;
	}

	public Integer getToilet() {
		return toilet;
	}

	public void setToilet(Integer toilet) {
		this.toilet = toilet;
	}

	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}

	public Integer getFloor() {
		return floor;
	}

	public void setFloor(Integer floor) {
		this.floor = floor;
	}

	public Integer getHeight() {
		return height;
	}

	public void setHeight(Integer height) {
		this.height = height;
	}

	public String getFitment() {
		return fitment;
	}

	public void setFitment(String fitment) {
		this.fitment = fitment;
	}

	public String getDirection() {
		return direction;
	}

	public void setDirection(String direction) {
		this.direction = direction;
	}

	public String getBuildDate() {
		return buildDate;
	}

	public void setBuildDate(String buildDate) {
		this.buildDate = buildDate;
	}

	public String getKind() {
		return kind;
	}

	public void setKind(String kind) {
		this.kind = kind;
	}

	public String getBuildType() {
		return buildType;
	}

	public void setBuildType(String buildType) {
		this.buildType = buildType;
	}

	@Override
	public List<Res> getResult() {
		List<Res> ret = new ArrayList<Res>();
		ret.add(new Res("area", area));
		ret.add(new Res("room", room));
		ret.add(new Res("hall", hall));
		ret.add(new Res("toilet", toilet));
		ret.add(new Res("buildDate", buildDate));
		ret.add(new Res("fitment", fitment));
		ret.add(new Res("height", height));
		ret.add(new Res("floor", floor));
		ret.add(new Res("price", price));
		ret.add(new Res("direction", direction));
		ret.add(new Res("kind", kind));
		ret.add(new Res("build_type", buildType));
		return ret;
	}

}
