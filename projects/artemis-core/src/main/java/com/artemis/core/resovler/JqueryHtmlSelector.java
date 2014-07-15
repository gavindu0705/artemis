package com.artemis.core.resovler;

import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.artemis.core.Resolver;
import com.artemis.core.result.HtmlResult;

/**
 * Jquery HTML¿éÑ¡ÔñÆ÷
 * 
 * @author xiaoyu
 * 
 */
public class JqueryHtmlSelector implements Resolver {
	@Override
	public HtmlResult invoke(Map<String, Object> params) {
		String content = (String) params.get(Resolver.CONENT);
		String selectorStr = (String) params.get(Resolver.SELECTOR);
		String paramName = (String) params.get(Resolver.PARAM_NAME);

		if (StringUtils.isBlank(content) || StringUtils.isBlank(selectorStr)) {
			return null;
		}

		Document doc = Jsoup.parse(content);
		String[] selectors = selectorStr.split(",");
		Elements elements = null;
		for (int i = 0; i < selectors.length; i++) {
			elements = doc.body().select(selectors[i]);
			if (elements != null && elements.size() > 0) {
				break;
			}
		}

		StringBuilder ret = new StringBuilder();
		if (elements != null) {
			for (Element element : elements) {
				ret.append(element.html());
			}
		}
		
		return new HtmlResult(paramName, ret.toString());
	}

}
