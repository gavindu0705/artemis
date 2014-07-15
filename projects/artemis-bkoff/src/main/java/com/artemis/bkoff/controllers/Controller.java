package com.artemis.bkoff.controllers;

import com.artemis.bkoff.model.IndexModel;
import com.artemis.service.UrlsService;
import com.artemis.service.task.TaskService;

public class Controller {
	private TaskService taskService = TaskService.getInstance();
	private UrlsService urlsService = UrlsService.getInstance();

	public Object indexAction(IndexModel model) {
		model.setUrlsCount(urlsService.findUrlsCrawlInitCount());
		model.setPendsCount(urlsService.findPendsTaskInitCount());
		model.setMetaCount(taskService.findMetadataCount());
		return model;
	}
}
