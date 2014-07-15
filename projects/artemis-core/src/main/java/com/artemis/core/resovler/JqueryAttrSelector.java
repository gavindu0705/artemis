package com.artemis.core.resovler;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.artemis.core.Resolver;
import com.artemis.core.result.ListResult;
import com.artemis.core.util.JQueryUtils;

/**
 * JQuery Ù–‘—°‘Ò∆˜
 * 
 * @author xiaoyu
 * 
 */
public class JqueryAttrSelector implements Resolver {

	@Override
	public ListResult invoke(Map<String, Object> params) {
		String attr = (String) params.get(Resolver.ATTR_NAME);
		String content = (String) params.get(Resolver.CONENT);
		String selectorStr = (String) params.get(Resolver.SELECTOR);
		String paramName = (String) params.get(Resolver.PARAM_NAME);

		if (StringUtils.isBlank(content) || StringUtils.isBlank(selectorStr)) {
			return null;
		}

		String[] selectors = selectorStr.split(",");
		Elements elements = null;
		for (int i = 0; i < selectors.length; i++) {
			elements = JQueryUtils.select(selectors[i], content);
			if (elements != null && elements.size() > 0) {
				break;
			}
		}

		List<Object> list = new ArrayList<Object>();
		if (elements != null) {
			for (Element element : elements) {
				list.add(element.attr(attr));
			}
		}

		return new ListResult(paramName, list);
	}
}
