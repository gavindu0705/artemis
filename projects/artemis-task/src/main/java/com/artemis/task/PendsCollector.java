package com.artemis.task;

import java.util.List;

import com.artemis.core.log.ALogger;
import com.artemis.mongo.po.Pends;
import com.artemis.service.JobService;
import com.artemis.service.UrlsService;

/**
 * 抓取成果收集线程
 * 
 * @author xiaoyu
 * 
 */
public class PendsCollector extends Thread {
	public static final int BATCH_LIMIT = 2000;
	private PendsHolder pendsHolder = PendsHolder.getInstance();
	private JobService jobService = JobService.getInstance();
	private UrlsService urlsService = UrlsService.getInstance();

	public static final ALogger LOG = new ALogger(PendsCollector.class);

	@Override
	public void run() {
		while (true) {
			try {
				if (pendsHolder.size() > 5000) {
					Thread.sleep(1 * 1000);
					LOG.print(pendsHolder.size() + " pends in queue, sleep 1000ms");
					continue;
				}

				List<Pends> pendsList = getInitPends();
				if (pendsList.size() <= 0) {
					Thread.sleep(3 * 1000);
					continue;
				}
				for (Pends pends : pendsList) {
					if (!jobService.isJobRunning(pends.getJobId())) {
						continue;
					}
					pendsHolder.put(pends);
				}
			} catch (InterruptedException e) {
				LOG.error("", e);
			}
		}
	}

	private List<Pends> getInitPends() {
		List<Pends> pendsList = urlsService.findPendsTaskInit(BATCH_LIMIT);
		for (Pends pends : pendsList) {
			urlsService.updatePendsTaskQue(pends.getId());
		}
		return pendsList;
	}

}
