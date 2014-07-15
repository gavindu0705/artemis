package com.artemis.core.result;

import java.util.List;

import com.artemis.core.bean.Link;

/**
 * 解析出来的链接结果
 * 
 * @author xiaoyu
 * 
 */
public class LinksResult implements SingleResult {
	private String name;
	private List<Link> value;

	public LinksResult(List<Link> value) {
		this.name = "$a";
		this.value = value;
	}

	public LinksResult(String name, List<Link> value) {
		this.name = name;
		this.value = value;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public List<Link> getValue() {
		return value;
	}

}
