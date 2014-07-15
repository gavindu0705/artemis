package com.artemis.bkoff.model;

import java.util.Date;

import org.sothis.core.util.Pager;
import org.sothis.web.mvc.Action;
import org.sothis.web.mvc.ActionContext;
import org.sothis.web.mvc.ModelAndViewSupport;

import com.artemis.bkoff.BkoffConfig;

public class BaseModel extends ModelAndViewSupport {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3242305871007646161L;
	private final static BkoffConfig CONFIG = BkoffConfig.getConfig();
	private final static String BASE_URL = "http://" + CONFIG.getBaseDomain() + "/artemis-bkoff";
	private Pager pager;

	public final String getBaseUrl() {
		return BASE_URL;
	}

	public final String getActionName() {
		Action action = (Action) ActionContext.getContext().get(ActionContext.ACTION);
		return action.getName();
	}

	public final String getControllerName() {
		Action action = (Action) ActionContext.getContext().get(ActionContext.ACTION);
		return action.getController().getName();
	}

	public final String getPackageName() {
		Action action = (Action) ActionContext.getContext().get(ActionContext.ACTION);
		return action.getController().getPackageName();
	}

	public final String getVersion() {
		return String.valueOf(System.currentTimeMillis());
	}

	public final Date getNow() {
		return new Date();
	}

	public String getEnvironment() {
		return CONFIG.getEnvironment();
	}

	public final Pager getPager() {
		return pager;
	}

	public final Pager getPager(int pageSize) {
		if (null == pager) {
			pager = new Pager(pageSize);
		} else {
			pager.setPageSize(pageSize);
		}
		return pager;
	}

	public final Pager getInitPager(int startRow, int pageSize) {
		Pager pager = getPager(pageSize);
		pager.setStartRow(startRow);
		return pager;
	}

	public final Pager getPager(int pageSize, int maxPage) {
		if (null == pager) {
			pager = new Pager(pageSize, maxPage);
		} else {
			pager.setPageSize(pageSize);
			pager.setMaxPage(maxPage);
		}
		return pager;
	}

	public final void setPager(Pager pager) {
		this.pager = pager;
	}

}
