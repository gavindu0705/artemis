package com.artemis.core;

import java.io.IOException;
import java.util.List;

import com.artemis.core.bean.Goal;
import com.artemis.core.bean.Harvest;

/**
 * 抓取实现策略
 * 
 * @author duxiaoyu
 * 
 */
public interface MutiGoalProxyPolicy extends ProxyPolicy {
	public List<Harvest> apply(List<Goal> goals) throws IOException;
}
