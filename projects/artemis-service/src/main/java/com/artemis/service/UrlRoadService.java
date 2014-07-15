package com.artemis.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import com.artemis.core.log.ALogger;
import com.artemis.core.tools.AsyncThread;
import com.artemis.core.tools.AsyncThread.Call;
import com.artemis.mongo.dao.UrlRoadDao;
import com.artemis.mongo.po.UrlRoad;
import com.artemis.mongo.po.Urls.UrlsStatusEnum;

/**
 * 记录一个URL的执行流程
 * 
 * @author xiaoyu
 * 
 */
public class UrlRoadService {
	public static final int MAX_QUEUE_SIZE = 1000000;
	private BlockingQueue<UrlRoad> URL_ROAD_QUEUE = new LinkedBlockingQueue<UrlRoad>(MAX_QUEUE_SIZE);
	public static final ALogger LOG = new ALogger(UrlRoadService.class);
	private static UrlRoadService instance = new UrlRoadService();
	UrlRoadDao urlRoadDao = UrlRoadDao.getInstance();

	private UrlRoadService() {
		new AsyncThread(1000 * 1, new Call() {
			@Override
			public void invoke() {
				urlRoadConsumer();
			}
		});
	}

	public static UrlRoadService getInstance() {
		return instance;
	}

	public void urlRoadConsumer() {
		List<UrlRoad> urlRoads = new ArrayList<UrlRoad>();
		URL_ROAD_QUEUE.drainTo(urlRoads);
		if (urlRoads.size() > 0) {
			this.urlRoadDao.saveList(urlRoads);
		}
	}

	public void addUrlRoad(String jobId, String sessionId, String requestUrl, String refererUrl, UrlsStatusEnum status) {
		UrlRoad urlRoad = new UrlRoad();
		urlRoad.setJobId(jobId);
		urlRoad.setSessionId(sessionId);
		urlRoad.setRequestUrl(requestUrl);
		urlRoad.setRefererUrl(refererUrl);
		urlRoad.setStatus(status.getStatus());
		urlRoad.setCreationDate(new Date());
		while (true) {
			if (URL_ROAD_QUEUE.size() > MAX_QUEUE_SIZE * 0.9) {
				try {
					LOG.print(URL_ROAD_QUEUE.size() + " url road in queue, consumer is going crazy!!! sleep 3 seconds");
					Thread.sleep(3 * 1000);
				} catch (InterruptedException e) {
					LOG.error("", e);
				}
				continue;
			}
			
			URL_ROAD_QUEUE.add(urlRoad);
			break;
		}
	}

}
