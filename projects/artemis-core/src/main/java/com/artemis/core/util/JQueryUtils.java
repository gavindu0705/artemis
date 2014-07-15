package com.artemis.core.util;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class JQueryUtils {
	public static Elements select(String selector, String content) {
		content = content.replace('\0', ' ');
		Document doc = Jsoup.parse(content);
		Elements elements = doc.body().select(selector);
		return elements;
	}
}
