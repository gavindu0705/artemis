package com.artemis.spiders.crawl;

import java.util.List;

import com.artemis.core.log.ALogger;
import com.artemis.mongo.po.Urls;
import com.artemis.service.UrlsService;

/**
 * 任务通知者
 * 
 * @author xiaoyu
 * 
 */
public class AdvisorThread extends Thread {
	private UrlsService urlsService = UrlsService.getInstance();
	public static final ALogger LOG = new ALogger(AdvisorThread.class);

	@Override
	public void run() {
		while (true) {
			try {
				List<Urls> urlsList = urlsService.findUrlsCrawlSuc(2000);
				for (Urls u : urlsList) {
					urlsService.urlsToPends(u);
				}
				
				if(urlsList.size() <= 100) {
					Thread.sleep(3 * 1000);
				}
			} catch (InterruptedException e) {
				LOG.error(e);
			}
		}
	}
}
