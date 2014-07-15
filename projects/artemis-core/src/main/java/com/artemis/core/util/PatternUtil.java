package com.artemis.core.util;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class PatternUtil {
	public static Map<String, Pattern> PATT_MAP = new HashMap<String, Pattern>();
	
	public static Pattern compile(String pattern) {
		if(PATT_MAP.containsKey(pattern)) {
			return PATT_MAP.get(pattern);
		}
		
		if(PATT_MAP.size() > 10000) {
			PATT_MAP.clear();
		}
		
		Pattern ret = Pattern.compile(pattern);
		PATT_MAP.put(pattern, ret);
		return ret;
	}
}
