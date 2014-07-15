package com.artemis.bkoff.controllers;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.artemis.bkoff.model.TaskModel;
import com.artemis.bkoff.vo.TaskVo;
import com.artemis.mongo.dao.FuncDao;
import com.artemis.mongo.dao.JobDao;
import com.artemis.mongo.dao.PageDao;
import com.artemis.mongo.dao.TaskDao;
import com.artemis.mongo.po.Func;
import com.artemis.mongo.po.Job;
import com.artemis.mongo.po.Page;
import com.artemis.mongo.po.Task;

public class TaskController {
	private JobDao jobDao = JobDao.getInstance();
	private PageDao pageDao = PageDao.getInstance();
	private TaskDao taskDao = TaskDao.getInstance();
	private FuncDao funcDao = FuncDao.getInstance();

	public Object listAction(TaskModel model) {
		Job job = jobDao.findById(model.getJobId());
		Page page = pageDao.findById(model.getPageId());
		List<Task> taskList = taskDao.findTaskByJobPageId(model.getJobId(), model.getPageId());
		model.setJob(job);
		model.setPage(page);
		List<TaskVo> tvList = new ArrayList<TaskVo>();
		for (Task task : taskList) {
			TaskVo tv = new TaskVo();
			tv.setId(task.getId());
			tv.setCaption(task.getCaption());
			tv.setClazz(task.getClazz());
			Func func = funcDao.findFuncByClazz(task.getClazz());
			tv.setClazzName(func.getName());
			tv.setParams(task.getParams());
			tv.setCreationDate(task.getCreationDate());
			tvList.add(tv);
		}
		model.setTaskVoList(tvList);
		return model;
	}

	public Object createAction(TaskModel model) {
		Job job = jobDao.findById(model.getJobId());
		Page page = pageDao.findById(model.getPageId());
		List<Func> funcList = funcDao.findAll();
		model.setJob(job);
		model.setPage(page);
		model.setFuncList(funcList);
		if (model.getTaskId() != null) {
			model.setTask(taskDao.findById(model.getTaskId()));
		}

		if (model.getClazz() == null) {
			model.setClazz(funcList.get(0).getClazz());
			model.setParams(funcList.get(0).getParams());
		} else {
			Func func = funcDao.findFuncByClazz(model.getClazz());
			model.setClazz(func.getClazz());
			model.setParams(func.getParams());
		}
		return model;
	}

	public Object deleteAction(TaskModel model) {
		this.taskDao.deleteById(model.getId());
		return model.redirect("/task/list?jobId=" + model.getJobId() + "&pageId=" + model.getPageId());
	}

	public Object saveAction(TaskModel model) {
		if (model.getTask() != null) {
			Map<String, String> paramsMap = new HashMap<String, String>();
			if (model.getParams() != null) {
				for (int i = 0; i < model.getParams().size(); i++) {
					if (model.getParams().get(i).startsWith("$")) {
						paramsMap.put(model.getParams().get(i).trim(), model.getValues().get(i).trim());
					} else {
						paramsMap.put("$" + model.getParams().get(i).trim(), model.getValues().get(i).trim());
					}
				}
			}
			Task task = model.getTask();
			task.setParams(paramsMap);
			task.setCreationDate(new Date());
			taskDao.save(task);
		}
		return model.redirect("/task/list?jobId=" + model.getJobId() + "&pageId=" + model.getPageId());
	}
}
