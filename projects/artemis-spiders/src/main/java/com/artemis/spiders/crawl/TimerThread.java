package com.artemis.spiders.crawl;

import java.util.Date;
import java.util.List;

import com.artemis.core.log.ALogger;
import com.artemis.core.tools.AsyncThread;
import com.artemis.core.tools.AsyncThread.Call;
import com.artemis.mongo.po.Job;
import com.artemis.service.JobService;

/**
 * 定时器
 * 
 * @author xiaoyu
 * 
 */
public class TimerThread extends Thread {
	private JobService jobService = JobService.getInstance();
	public static final ALogger LOG = new ALogger(TimerThread.class);

	@Override
	public void run() {
		new AsyncThread(60 * 1000, new Call() {
			@Override
			public void invoke() {
				List<Job> jobs = jobService.findAllJobs();
				for (Job job : jobs) {
					//判断job是否已经到了该执行的时候了
					if (job.getInterval() > 0 && (job.getStatus() == Job.DONE || job.getStatus() == Job.STOPPING)
							&& isJobTimeUp(job)) {
						LOG.print("job {} {} will starting ...", new Object[] { job.getId(), job.getName() });
						jobService.startJob(job.getId());
					}
				}
			}
		});
	}

	private boolean isJobTimeUp(Job job) {
		if (job.getStartDate() == null) {
			return false;
		}

		if (new Date().getTime() - job.getStartDate().getTime() > job.getInterval() * 1000) {
			return true;
		}
		return false;
	}

}
