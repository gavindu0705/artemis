package com.artemis.service.fangjia.resolver;

import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.jsoup.select.Elements;

import com.artemis.core.Resolver;
import com.artemis.core.util.DataUtil;
import com.artemis.core.util.JQueryUtils;
import com.artemis.service.fangjia.result.FangjiaHouseBaseResult;
import com.poseidon.knowbase.KnowbaseServer;
import com.poseidon.util.TypeConverter;

/**
 * 房源基本信息解析
 * 
 * @author duxiaoyu
 * 
 */
public class HouseBaseResolverImpl implements Resolver {
	private KnowbaseServer knowBaseServer = new KnowbaseServer();
	public static final String[] BASE_FILEDS = { "area", "room", "hall", "toilet", "total_price", "rent_price", "floor",
			"build_date", "age", "direction", "height", "fitment", "kind", "build_type" };

	public static void main(String[] args) {
		String s = "产证面积： 108.17㎡          建筑形式：独栋";
		KnowbaseServer knowBaseServer = new KnowbaseServer();
		Map<String, Object> knowBaseData = knowBaseServer.parse(s, BASE_FILEDS, null);
		Map<String, Object> data = knowBaseServer.getResultDataFromKnowBase(knowBaseData);
		System.out.println(data);
	}

	@Override
	public FangjiaHouseBaseResult invoke(Map<String, Object> params) {
		String content = (String) params.get(Resolver.CONENT);
		String selectors = (String) params.get(Resolver.SELECTOR);

		FangjiaHouseBaseResult result = new FangjiaHouseBaseResult();

		for (String selector : StringUtils.split(selectors, ",")) {
			if (StringUtils.isBlank(selector)) {
				continue;
			}
			
			Elements elements = JQueryUtils.select(selector, content);
			String html = DataUtil.removeHtml(elements.html());
			Map<String, Object> knowBaseData = knowBaseServer.parse(html, BASE_FILEDS, null);
			Map<String, Object> data = knowBaseServer.getResultDataFromKnowBase(knowBaseData, true);

			if (data.containsKey("area") && (result.getArea() == null || result.getArea() <= 0)) {
				result.setArea(TypeConverter.convertToDouble((String) data.get("area")));
			}
			if (data.containsKey("room") && (result.getRoom() == null || result.getRoom() <= 0)) {
				result.setRoom(TypeConverter.convertToInteger((String) data.get("room")));
			}
			if (data.containsKey("hall") && (result.getHall() == null || result.getHall() <= 0)) {
				result.setHall(TypeConverter.convertToInteger((String) data.get("hall")));
			}
			if (data.containsKey("toilet") && (result.getToilet() == null || result.getToilet() <= 0)) {
				result.setToilet(TypeConverter.convertToInteger((String) data.get("toilet")));
			}
			if (data.containsKey("total_price") && (result.getPrice() == null || result.getPrice() <= 0)) {
				Integer totalPrice = TypeConverter.convertToInteger((String) data.get("total_price"));
				if (totalPrice != null) {
					if (data.get("total_price").toString().indexOf("万") > 0) {
						totalPrice = totalPrice * 10000;
					}
					result.setPrice(totalPrice);
				}
			} else if (data.containsKey("rent_price") && (result.getPrice() == null || result.getPrice() <= 0)) {
				Integer rentPrice = TypeConverter.convertToInteger((String) data.get("rent_price"));
				result.setPrice(rentPrice);
			}

			if (data.containsKey("floor") && (result.getFloor() == null || result.getFloor() <= 0)) {
				result.setFloor(TypeConverter.convertToInteger((String) data.get("floor")));
			}

			if (data.containsKey("height") && (result.getHeight() == null || result.getHeight() <= 0)) {
				result.setHeight(TypeConverter.convertToInteger((String) data.get("height")));
			}

			if (data.containsKey("build_date") && StringUtils.isBlank(result.getBuildDate())) {
				result.setBuildDate(TypeConverter.convertToString((String) data.get("build_date")));
			}

			if (data.containsKey("direction") && StringUtils.isBlank(result.getDirection())) {
				result.setDirection(TypeConverter.convertToString((String) data.get("direction")));
			}
			if (data.containsKey("fitment") && StringUtils.isBlank(result.getFitment())) {
				result.setFitment(TypeConverter.convertToString((String) data.get("fitment")));
			}

			if (data.containsKey("kind") && StringUtils.isBlank(result.getKind())) {
				result.setKind(TypeConverter.convertToString((String) data.get("kind")));
			}

			if (data.containsKey("build_type") && StringUtils.isBlank(result.getBuildType())) {
				result.setBuildType(TypeConverter.convertToString((String) data.get("build_type")));
			}
		}

		return result;
	}
}
