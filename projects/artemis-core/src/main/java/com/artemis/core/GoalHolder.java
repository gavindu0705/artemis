package com.artemis.core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import com.artemis.core.bean.Goal;
import com.artemis.core.bean.HtmlGoal;
import com.artemis.core.log.ALogger;

/**
 * 抓取目标持有器
 * 
 * @author xiaoyu
 * 
 */
public class GoalHolder {
	public static final int GOALS_HOLDER_SIZE = 10000;
	private BlockingQueue<HtmlGoal> goalsQueue = new LinkedBlockingQueue<HtmlGoal>(GOALS_HOLDER_SIZE);
	private static GoalHolder instance = new GoalHolder();
	public static final ALogger LOG = new ALogger(GoalHolder.class);

	private GoalHolder() {

	}

	public static GoalHolder getInstance() {
		return instance;
	}

	public void put(HtmlGoal goal) {
		try {
			goalsQueue.put(goal);
		} catch (InterruptedException e) {
			LOG.error("error put queue", e);
		}
	}

	public HtmlGoal get() {
		return goalsQueue.poll();
	}

	@SuppressWarnings("unchecked")
	public List<Goal> get(int c) {
		if (c <= 0) {
			return Collections.EMPTY_LIST;
		}
		List<Goal> ret = new ArrayList<Goal>();
		for (int i = 0; i < c; i++) {
			HtmlGoal g = this.get();
			if (g != null) {
				ret.add(g);
			} else {
				break;
			}
		}
		return ret;
	}

	// public List<Goal> drain(int size) {
	// List<Goal> ret = new ArrayList<Goal>(size);
	// goalsQueue.drainTo(ret, size);
	// return ret;
	// }

	public int size() {
		return goalsQueue.size();
	}

}
