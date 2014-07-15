package com.artemis.core.resovler;

import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.artemis.core.Resolver;
import com.artemis.core.result.StringResult;

/**
 * JqueryÎÄ±¾Ñ¡ÔñÆ÷
 * 
 * @author xiaoyu
 * 
 */
public class JqueryTextSelector implements Resolver {

//	public static void main(String[] args) throws IOException {
//		String content = FileUtils.readFileToString(new File("d:/cities-58.html"), "UTF-8");
//		String selector = "#clist dd a";
//		Document doc = DataUtil.parse(content);
//		Elements elements = doc.body().select(selector);
//		System.out.println(elements.size());
//		if (elements != null && elements.size() > 0) {
//			for (Element e : elements) {
//				String s = e.toString();
////				System.out.println(s);
//				if (s.indexOf("58.com") > -1) {
//					try {
//						String spell = StringUtils.substringBetween(s, "http://", ".58.com").trim();
//						String name = StringUtils.substringBetween(s, ">", "</a>").trim();
//						System.out.println(name + "=" + spell);
//					} catch (Exception e1) {
//						System.out.println("####################" + s);
//						e1.printStackTrace();
//					}
//				}
//			}
//		}
//	}

	@Override
	public StringResult invoke(Map<String, Object> params) {
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
				ret.append(element.text());
			}
		}

		return new StringResult(paramName, ret.toString());
	}

}
