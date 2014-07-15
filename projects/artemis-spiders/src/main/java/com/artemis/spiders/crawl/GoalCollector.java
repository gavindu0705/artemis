package com.artemis.spiders.crawl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

import org.apache.commons.lang.math.RandomUtils;

import com.artemis.core.GoalHolder;
import com.artemis.core.PriorityEnum;
import com.artemis.core.bean.HtmlGoal;
import com.artemis.core.log.ALogger;
import com.artemis.core.util.DataUtil;
import com.artemis.mongo.po.Job;
import com.artemis.mongo.po.SiteConfig;
import com.artemis.mongo.po.Urls;
import com.artemis.mongo.po.Urls.UrlsStatusEnum;
import com.artemis.service.FileDataService;
import com.artemis.service.JobService;
import com.artemis.service.SiteConfigService;
import com.artemis.service.UrlsService;

/**
 * 抓取目标收集线程
 * 
 * @author xiaoyu
 * 
 */
public class GoalCollector extends Thread {
	public static final ALogger LOG = new ALogger(GoalCollector.class);
	private GoalHolder goalsHolder = GoalHolder.getInstance();
	private JobService jobService = JobService.getInstance();
	private UrlsService urlsService = UrlsService.getInstance();
	private FileDataService fileDataService = FileDataService.getInstance();
	private SiteConfigService siteConfigService = SiteConfigService.getInstance();

	private static final AtomicLong CACHE_INC = new AtomicLong(0);
	private static final int BATCH_COUNT = 200;

	@Override
	public void run() {
		while (true) {
			try {
				// 如果goals holder里面的数据超过80%，则放慢获取速度
				if (goalsHolder.size() >= BATCH_COUNT * 1.5) {
					Thread.sleep(1 * 1000);
					continue;
				}

				long lstart = System.currentTimeMillis();
				List<Urls> urlsList = fetchUrls();
				LOG.performance(lstart, System.currentTimeMillis(), "[FETCH_URL] size:" + urlsList.size());

				if (urlsList.size() <= 0) {
					LOG.print("no goals, retry after 10s");
					Thread.sleep(10 * 1000);
				} else {
					for (Urls u : urlsList) {
						if (!jobService.isJobRunning(u.getJobId())) {
							continue;
						}

						// 检查是否有缓存页面
						if (fileDataService.isExpired(u.getId(), u.getJobId())) {
							urlsService.updateUrlsCrawlQue(u.getId());
							goalsHolder.put(new HtmlGoal(u.getId(), u.getCharset(), u.getReferer()));
						} else {
							urlsService.updateUrlsCrawlSuc(u.getId());
							if (CACHE_INC.incrementAndGet() % 100 == 0) {
								LOG.print("{} cached - {}", new Object[] { CACHE_INC.get(), u.getId() });
							}
						}
					}
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public static void main(String[] args) {
		GoalCollector sd = new GoalCollector();
		sd.fetchUrls();
	}

	// private List<Urls> getUrls2() {
	// Map<String, SiteConfig> siteConfigMap = new HashMap<String,
	// SiteConfig>(siteConfigService.getSiteConfigMap());
	// int limit = BATCH_COUNT / siteConfigMap.size() <= 0 ? 1 : BATCH_COUNT /
	// siteConfigMap.size();
	//
	// Map<String, List<Urls>> rootUrlsMap = new HashMap<String, List<Urls>>();
	//
	// for (PriorityEnum prio : new PriorityEnum[] { PriorityEnum.HIGHEST,
	// PriorityEnum.HIGH, PriorityEnum.NORMAL }) {
	// Map<String, SiteConfig> tmpSiteConfigMap = new HashMap<String,
	// SiteConfig>(siteConfigMap);
	//
	// for (String root : tmpSiteConfigMap.keySet()) {
	// SiteConfig siteConfig = siteConfigMap.get(root);
	// int dlimit = determineLimit(limit, siteConfig.getShotRate());
	//
	// List<Urls> urlsList = urlsService.findUrlsCrawlInit(root,
	// prio.getPriority(), dlimit);
	// if (urlsList.size() > 0) {
	// rootUrlsMap.put(root, urlsList);
	// siteConfigMap.remove(root);
	// }
	// }
	//
	// List<Urls> defaultUrlsList =
	// urlsService.findUrlsCrawlInit(SiteConfigService.DEFAULT_ROOT,
	// prio.getPriority(), limit);
	// if (defaultUrlsList.size() > 0) {
	// rootUrlsMap.put(SiteConfigService.DEFAULT_ROOT, defaultUrlsList);
	// }
	// }
	//
	// return combineRootUrls(rootUrlsMap);
	// }

	@SuppressWarnings("unchecked")
	private List<Urls> fetchUrls() {
		Map<String, SiteConfig> siteConfigMap = siteConfigService.getSiteConfigMap();
		int runningSize = getRunningJobSize();
		if (runningSize == 0) {
			return Collections.EMPTY_LIST;
		}

		int limit = BATCH_COUNT / runningSize <= 0 ? 1 : BATCH_COUNT / runningSize;
		Map<String, List<Urls>> rootUrlsMap = new HashMap<String, List<Urls>>();
		for (PriorityEnum priority : new PriorityEnum[] { PriorityEnum.HIGHEST, PriorityEnum.HIGH, PriorityEnum.NORMAL }) {
			List<String> roots = new ArrayList<String>(siteConfigMap.keySet());
			for (String root : roots) {
				List<Urls> urlsList = fetchUrls(priority, siteConfigMap.get(root), limit);
				if (urlsList.size() > 0) {
					rootUrlsMap.put(root, urlsList);
					siteConfigMap.remove(root);
				}
			}
		}

		return combineRootUrls(rootUrlsMap);
	}

	private List<Urls> fetchUrls(PriorityEnum priority, SiteConfig siteConfig, int limit) {
		List<Job> runningJobs = this.getRunningJobs(siteConfig.getRoot(), priority);
		List<Urls> ret = new ArrayList<Urls>();
		if (runningJobs.size() <= 0) {
			return ret;
		}

		int dlimit = determineLimit(limit, siteConfig.getShotRate());
		int rlimit = dlimit / runningJobs.size();
		if (rlimit <= 0) {
			rlimit = 1;
		}

		for (Job job : runningJobs) {
			List<Urls> urlsList = this.urlsService.findUrlsByJobId(job.getId(), UrlsStatusEnum.CRAWL_INIT, rlimit);
			ret = DataUtil.combineList(ret, urlsList);
		}

		return ret;
	}

	private List<Urls> combineRootUrls(Map<String, List<Urls>> rootUrlsMap) {
		List<Urls> combineList = new ArrayList<Urls>();
		for (String root : rootUrlsMap.keySet()) {
			if (rootUrlsMap.get(root) == null) {
				continue;
			}
			if (combineList.size() == 0) {
				combineList.addAll(rootUrlsMap.get(root));
			} else {
				combineList = DataUtil.combineList(combineList, rootUrlsMap.get(root));
			}
		}
		return combineList;
	}

	private int determineLimit(int exceptLimit, int shotRate) {
		int ret = 1;
		for (int i = 0; i < exceptLimit; i++) {
			if (RandomUtils.nextInt(100) < shotRate) {
				ret++;
			}
		}
		return ret;
	}

	private int getRunningJobSize() {
		int ret = 0;
		Map<String, Job> jobMap = JobService.getInstance().getJobMap();
		for (String jobId : jobMap.keySet()) {
			if (jobMap.get(jobId).getStatus() == Job.RUNNING) {
				ret++;
			}
		}
		return ret;
	}

	private List<Job> getRunningJobs(String root, PriorityEnum priority) {
		List<Job> ret = new ArrayList<Job>();
		Map<String, Job> jobMap = JobService.getInstance().getJobMap();
		for (String jobId : jobMap.keySet()) {
			Job job = jobMap.get(jobId);
			if (job.getStatus() == Job.RUNNING && job.getRoot().equals(root) && job.getPriority() == priority.getPriority()) {
				ret.add(jobMap.get(jobId));
			}
		}
		return ret;
	}

}
