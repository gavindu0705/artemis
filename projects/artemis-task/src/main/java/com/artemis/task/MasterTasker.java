package com.artemis.task;

import java.util.concurrent.atomic.AtomicLong;

import com.artemis.core.log.ALogger;
import com.artemis.service.util.Footprint;

public class MasterTasker extends Thread {
	public static final ALogger LOG = new ALogger(MasterTasker.class);
	public static final int BATCH_LIMIT = 1000;
	private TaskConfig taskConfig = TaskConfig.getInstance();

	public static final AtomicLong META_INC = new AtomicLong(0);
	public static final AtomicLong URLS_INC = new AtomicLong(0);

	@Override
	public void run() {
		// 布隆过滤器初始化
		Footprint.init(taskConfig.getBloomFilePath());

		LOG.print("{} threads will start...", taskConfig.getTaskerThreadSize());
		for (int i = 100; i < 100 + taskConfig.getTaskerThreadSize(); i++) {
			Thread thread = new Thread(new TaskerThread());
			thread.setName("task-" + i);
			thread.start();
			LOG.print("start thread {}", thread.getName());
		}

		LOG.print("all of threads started");
	}

}
