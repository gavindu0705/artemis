package com.artemis.core.resovler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.artemis.core.Resolver;
import com.artemis.core.bean.Link;
import com.artemis.core.result.LinksResult;

/**
 * JQueryÁ´½ÓÑ¡ÔñÆ÷
 * 
 * @author xiaoyu
 * 
 */
public class JqueryAllLinksSelector implements Resolver {

	@Override
	public LinksResult invoke(Map<String, Object> params) {
		String url = (String) params.get(Resolver.URL);
		String content = (String) params.get(Resolver.CONENT);
		String selector = (String) params.get(Resolver.SELECTOR);

		if (StringUtils.isBlank(content) || StringUtils.isBlank(selector)) {
			return null;
		}

		List<Link> ret = new ArrayList<Link>();
		Document doc = Jsoup.parse(content);
		Elements elements = doc.body().select(selector);

		for (Element element : elements) {
			Link link = elementToLink(element, url);
			if (link != null) {
				ret.add(link);
			}
		}

		return new LinksResult(ret);
	}

	private Link elementToLink(Element ele, String referer) {
		Link ret = new Link();
		String url = ele.attr("href");
		if (url.startsWith("http://")) {
			ret.setUrl(url);
		} else if (url.startsWith("/")) {
			ret.setUrl("http://" + StringUtils.substringBetween(referer, "http://", "/") + url);
		} else if (url.startsWith("javascript")) {
			return null;
		} else {
			if (referer != null) {
				if (url.startsWith("../")) {
					ret.setUrl(StringUtils.substringBeforeLast(StringUtils.substringBeforeLast(referer, "/"), "/") + "/"
							+ StringUtils.substringAfter(url, "../"));
				} else if (url.startsWith("./")) {
					ret.setUrl(StringUtils.substringBeforeLast(referer, "/") + "/" + StringUtils.substringAfter(url, "./"));
				} else if (url.startsWith("?")) {
					ret.setUrl(StringUtils.substringBeforeLast(referer, "?") + url);
				} else {
					ret.setUrl(StringUtils.substringBeforeLast(referer, "/") + "/" + url);
				}
			} else {
				ret.setUrl(url);
			}
		}
		ret.setUrl(ret.getUrl().trim());
		Map<String, String> propertiesMap = new HashMap<String, String>();
		propertiesMap.put("id", ele.attr("id"));
		propertiesMap.put("class", ele.attr("class"));
		propertiesMap.put("href", ele.attr("href"));
		propertiesMap.put("html", ele.attr("html"));
		propertiesMap.put("name", ele.attr("name"));
		propertiesMap.put("style", ele.attr("style"));
		propertiesMap.put("target", ele.attr("target"));
		propertiesMap.put("title", ele.attr("title"));
		ret.setPropertiesMap(propertiesMap);
		ret.setReferer(referer);
		return ret;
	}

}
