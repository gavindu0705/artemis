package com.artemis.bkoff.model;

import java.util.List;

import com.artemis.bkoff.vo.TaskVo;
import com.artemis.mongo.po.Func;
import com.artemis.mongo.po.Job;
import com.artemis.mongo.po.Page;
import com.artemis.mongo.po.Seed;
import com.artemis.mongo.po.Task;

public class TaskModel extends BaseModel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4405543478557926498L;

	private String id;
	private Page page;
	private Job job;
	private Task task;
	private Seed seed;

	private String jobId;
	private String pageId;
	private String taskId;

	private List<Job> jobList;
	private List<Page> pageList;
	private List<Task> taskList;
	private List<Seed> seedList;
	private List<Func> funcList;

	private String clazz;
	private List<String> params;
	private List<String> values;

	private List<TaskVo> taskVoList;
	private String flag;

	public List<Job> getJobList() {
		return jobList;
	}

	public void setJobList(List<Job> jobList) {
		this.jobList = jobList;
	}

	public String getJobId() {
		return jobId;
	}

	public void setJobId(String jobId) {
		this.jobId = jobId;
	}

	public String getPageId() {
		return pageId;
	}

	public void setPageId(String pageId) {
		this.pageId = pageId;
	}

	public Page getPage() {
		return page;
	}

	public void setPage(Page page) {
		this.page = page;
	}

	public List<Page> getPageList() {
		return pageList;
	}

	public void setPageList(List<Page> pageList) {
		this.pageList = pageList;
	}

	public Job getJob() {
		return job;
	}

	public void setJob(Job job) {
		this.job = job;
	}

	public Task getTask() {
		return task;
	}

	public void setTask(Task task) {
		this.task = task;
	}

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public List<Task> getTaskList() {
		return taskList;
	}

	public void setTaskList(List<Task> taskList) {
		this.taskList = taskList;
	}

	public List<Seed> getSeedList() {
		return seedList;
	}

	public void setSeedList(List<Seed> seedList) {
		this.seedList = seedList;
	}

	public Seed getSeed() {
		return seed;
	}

	public void setSeed(Seed seed) {
		this.seed = seed;
	}

	public String getClazz() {
		return clazz;
	}

	public void setClazz(String clazz) {
		this.clazz = clazz;
	}

	public List<Func> getFuncList() {
		return funcList;
	}

	public void setFuncList(List<Func> funcList) {
		this.funcList = funcList;
	}

	public List<String> getParams() {
		return params;
	}

	public void setParams(List<String> params) {
		this.params = params;
	}

	public List<String> getValues() {
		return values;
	}

	public void setValues(List<String> values) {
		this.values = values;
	}

	public List<TaskVo> getTaskVoList() {
		return taskVoList;
	}

	public void setTaskVoList(List<TaskVo> taskVoList) {
		this.taskVoList = taskVoList;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

}
