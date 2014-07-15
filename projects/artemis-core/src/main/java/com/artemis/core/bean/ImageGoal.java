package com.artemis.core.bean;

import java.util.HashMap;
import java.util.Map;

/**
 * 图片抓取目标
 * 
 * @author xiaoyu
 * 
 */
public class ImageGoal implements Goal {
	public final static String CHARSET = "UTF-8";
	public final static int TIMEOUT = 5000;

	private String url;
	private String referer;
	private int timeout;
	private String charset;

	public ImageGoal(String url) {
		this.url = url;
		this.referer = url;
		this.timeout = TIMEOUT;
	}

	public ImageGoal(String url, String charset, String referer) {
		this.url = url;
		this.referer = referer;
		this.timeout = TIMEOUT;
	}

	public ImageGoal(String url, String referer, int timeout) {
		this.url = url;
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

	public String getCharset() {
		return charset;
	}

	public void setCharset(String charset) {
		this.charset = charset;
	}

	public Map<String, Object> getAsParams() {
		Map<String, Object> ret = new HashMap<String, Object>();
		ret.put("url", url);
		ret.put("timeout", timeout);
		ret.put("referer", referer);
		ret.put("charset", charset);
		return ret;
	}

}
