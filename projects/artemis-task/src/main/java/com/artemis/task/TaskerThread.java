package com.artemis.task;

import com.artemis.core.log.ALogger;
import com.artemis.mongo.po.Pends;
import com.artemis.service.JobService;
import com.artemis.service.task.ProductData;
import com.artemis.service.task.TaskService;

public class TaskerThread implements Runnable {
	public static final ALogger LOG = new ALogger(TaskerThread.class);
	private JobService jobService = JobService.getInstance();
	private TaskService taskService = TaskService.getInstance();
	private PendsHolder pendsHolder = PendsHolder.getInstance();

	@Override
	public void run() {
		while (true) {
			Pends pends = pendsHolder.get();
			if (pends == null) {
				try {
					Thread.sleep(3 * 1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				continue;
			}

			// 判断任务是否执行中
			if (!jobService.isJobRunning(pends.getJobId())) {
				LOG.print("job {} is stoped ", pends.getJobId());
				continue;
			}

			// long lstart1 = System.currentTimeMillis();

			// 页面解析出的数据
			ProductData productData = taskService.process(pends);

			// long secs1 = System.currentTimeMillis() - lstart1;
			// long lstart2 = System.currentTimeMillis();

			if (productData != null && productData.getUrl() != null) {
				int urlsCount = taskService.saveUrlsToCrawlInit(productData.getUrls());
				int metaCount = taskService.saveMetadata(productData.getMetadata());
				MasterTasker.URLS_INC.addAndGet(urlsCount);
				MasterTasker.META_INC.addAndGet(metaCount);
			}

			// long secs2 = System.currentTimeMillis() - lstart2;
			// LOG.print("{}/{} q:{} res:{} db:{} {}", new Object[] {
			// MasterTasker.META_INC.get(), MasterTasker.URLS_INC.get(),
			// pendsHolder.size(), secs1, secs2, pends.getId() });
		}
	}
}
