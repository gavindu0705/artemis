package com.artemis.core;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import com.artemis.core.bean.Harvest;
import com.artemis.core.log.ALogger;

/**
 * 抓取成果持有器
 * 
 * @author xiaoyu
 * 
 */
public class HarvestHolder {
	private BlockingQueue<Harvest> harvestQueue = new LinkedBlockingQueue<Harvest>(10000);
	private static HarvestHolder instance = new HarvestHolder();
	public static final ALogger LOG = new ALogger(HarvestHolder.class);

	private HarvestHolder() {

	}

	public static HarvestHolder getInstance() {
		return instance;
	}

	public void put(Harvest harvest) {
		try {
			harvestQueue.put(harvest);
		} catch (InterruptedException e) {
			LOG.error("harvest queue " + harvestQueue.size(), e);
		}
	}

	public Harvest get() {
		return harvestQueue.poll();
	}

	public int size() {
		return harvestQueue.size();
	}
}
