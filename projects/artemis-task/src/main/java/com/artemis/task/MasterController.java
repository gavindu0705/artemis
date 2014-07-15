package com.artemis.task;

import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.artemis.core.log.ALogger;
import com.artemis.service.UrlsService;

public class MasterController extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2857162514285448500L;
	private UrlsService urlsService = UrlsService.getInstance();
	public static final ALogger LOG = new ALogger(MasterController.class);

	@Override
	public void init(ServletConfig config) throws ServletException {
		LOG.print("task starting " + new Date());
		platformCleaner();
		initTaskThreads();
		LOG.print("task start success " + new Date());
	}

	private void platformCleaner() {
		this.urlsService.updatePendsTaskQueToInit();
	}

	private void initTaskThreads() {
		PendsCollector pendsCollector = new PendsCollector();
		pendsCollector.start();
		
		MasterTasker tasker = new MasterTasker();
		tasker.start();
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		super.doGet(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		this.doGet(req, resp);
	}

}
