package com.artemis.core.bean;

import java.util.HashMap;
import java.util.Map;

/**
 * 网页抓取目标
 * 
 * @author xiaoyu
 * 
 */
public class HtmlGoal implements Goal {
	public final static String CHARSET = "UTF-8";
	public final static int TIMEOUT = 5000;

	private String url;
	private String charset;
	private String referer;
	private int timeout;

	public HtmlGoal(String url, int timeout) {
		this.url = url;
		this.timeout = timeout;
	}

	public HtmlGoal(String url) {
		this.url = url;
		this.charset = CHARSET;
		this.timeout = TIMEOUT;
	}

	public HtmlGoal(String url, String charset, String referer) {
		this.url = url;
		this.charset = charset;
		this.referer = referer;
		this.timeout = TIMEOUT;
	}

	public HtmlGoal(String url, String referer, int timeout) {
		this.url = url;
		this.referer = referer;
		this.timeout = timeout > 10000 ? 10000 : timeout;
		this.charset = CHARSET;
	}

	public HtmlGoal(String url, String charset, String referer, int timeout) {
		this.url = url;
		this.charset = charset;
		this.referer = referer;
		this.timeout = timeout > 10000 ? 10000 : timeout;
	}

	@Override
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getCharset() {
		return charset;
	}

	public void setCharset(String charset) {
		this.charset = charset;
	}

	public String getReferer() {
		return referer;
	}

	public void setReferer(String referer) {
		this.referer = referer;
	}

	public int getTimeout() {
		return timeout;
	}

	public void setTimeout(int timeout) {
		this.timeout = timeout;
	}

	public Map<String, Object> getAsParams() {
		Map<String, Object> ret = new HashMap<String, Object>();
		ret.put("url", url);
		ret.put("charset", charset);
		ret.put("timeout", timeout);
		ret.put("referer", referer);
		return ret;
	}

}
