package com.artemis.core.resovler;

import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.artemis.core.Resolver;
import com.artemis.core.result.HtmlResult;

/**
 * ÎÄµµ»ñÈ¡
 * 
 * @author xiaoyu
 * 
 */
public class PageHtmlSelector implements Resolver {
	@Override
	public HtmlResult invoke(Map<String, Object> params) {
		String content = (String) params.get(Resolver.CONENT);
		String paramName = (String) params.get(Resolver.PARAM_NAME);

		if (StringUtils.isBlank(content) || StringUtils.isBlank(paramName)) {
			return null;
		}

		return new HtmlResult(paramName, content);
	}

}
