package com.artemis.spiders.crawl;

import java.util.Date;

import com.artemis.core.HarvestHolder;
import com.artemis.core.bean.Harvest;
import com.artemis.core.bean.Harvest.HarvestStatusEnum;
import com.artemis.core.log.ALogger;
import com.artemis.mongo.po.FileData;
import com.artemis.service.FileDataService;
import com.artemis.service.UrlsService;

/**
 * 抓取成果收集线程
 * 
 * @author xiaoyu
 * 
 */
public class HarvestCollector extends Thread {
	private UrlsService urlsService = UrlsService.getInstance();
	private HarvestHolder harvestHolder = HarvestHolder.getInstance();
	private FileDataService fileDataService = FileDataService.getInstance();
	public static final ALogger LOG = new ALogger(HarvestCollector.class);

	@Override
	public void run() {
		while (true) {
			Harvest harvest = harvestHolder.get();
			if (harvest == null) {
				try {
					Thread.sleep(1 * 1000);
				} catch (InterruptedException e) {
					LOG.error("", e);
				}
				continue;
			}

			if (harvest.getStatusCode() == HarvestStatusEnum.SUCCESS.getCode()) {
				processSuccess(harvest);
			} else {
				processError(harvest);
			}
		}
	}

	private void processError(Harvest harvest) {
		urlsService.updateUrlsCrawlErr(harvest.getUrl(), HarvestStatusEnum.parse(harvest.getStatusCode()));
	}

	private void processSuccess(Harvest harvest) {
		FileData fileData = new FileData();
		fileData.setId(harvest.getUrl());
		fileData.setReferer(harvest.getReferer());
		fileData.setMimeType(harvest.getMimeType());
		fileData.setContent(harvest.getContent());
		fileData.setCreationDate(new Date());
		fileDataService.saveFileData(fileData);
		urlsService.updateUrlsCrawlSuc(harvest.getUrl());
	}
}
