package com.artemis.service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import com.artemis.core.log.ALogger;
import com.artemis.mongo.dao.FileDataDao;
import com.artemis.mongo.dao.JobDao;
import com.artemis.mongo.po.FileData;
import com.artemis.mongo.po.Job;
import com.artemis.mongo.po.Page;
import com.artemis.service.task.TaskService;
import com.mongodb.BasicDBObject;

public class FileDataService {
	private static FileDataService instance = new FileDataService();

	private JobService jobService = JobService.getInstance();
	private TaskService taskService = TaskService.getInstance();
	private FileDataDao fileDataDao = FileDataDao.getInstance();
	public static final ALogger LOG = new ALogger(FileDataService.class);

	private FileDataService() {
	}

	public static FileDataService getInstance() {
		return instance;
	}

	public FileData findByUrl(String url) {
		FileData fileData = this.fileDataDao.findById(url);
		if (fileData != null) {
			try {
				fileData.setContent(unzip(fileData.getContent()));
			} catch (IOException e) {
				LOG.error("", e);
				return null;
			}
		}
		return fileData;
	}

	public void saveFileData(FileData fileData) {
		if (fileData.getContent() != null && fileData.getContent().length > 0) {
			try {
				fileData.setContent(zip(fileData.getContent()));
			} catch (IOException e) {
				LOG.error("", e);
			}
			this.fileDataDao.save(fileData);
		}
	}

//	/**
//	 * 是否存在
//	 * 
//	 * @param url
//	 * @return
//	 */
//	public boolean isExists(String url) {
//		FileData fileData = this.fileDataDao.findOne(new BasicDBObject("_id", url), new String[] { "_id" });
//		if (fileData != null) {
//			return true;
//		}
//		return false;
//	}

	/**
	 * 检查是否有缓存页面
	 * 
	 * @param url
	 * @param jobId
	 * @return
	 */
	public boolean isExpired(String url, String jobId) {
		FileData fileData = this.fileDataDao.findOne(new BasicDBObject("_id", url), new String[] { "_id", "c_date" });
		if (fileData == null) {
			return true;
		}

//		Job job = jobService.findById(jobId);
		Job job = jobService.getJobById(jobId);
		if (job == null) {
			return true;
		}

		Page page = taskService.matchPage(jobId, url);
		if (page == null) {
			return true;
		}

		// 不缓存
		if (page.getExpires() == 0) {
			return true;
		}

		// 永不过期
		if (page.getExpires() == -1) {
			return false;
		}

		if (new Date().getTime() - fileData.getCreationDate().getTime() > page.getExpires() * 60 * 1000) {
			return true;
		}

		return false;
	}

	/**
	 * 压缩
	 * 
	 * @param content
	 * @return
	 * @throws IOException
	 */
	public byte[] zip(byte[] content) throws IOException {
		long begin = System.currentTimeMillis();
		try {
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			GZIPOutputStream outputStream = new GZIPOutputStream(bos);
			outputStream.write(content);
			outputStream.finish();
			return bos.toByteArray();
		} finally {
			LOG.performance(begin, System.currentTimeMillis(), "zip " + content.length);
		}
	}

	/**
	 * 解压
	 * 
	 * @param content
	 * @return
	 * @throws IOException
	 */
	public byte[] unzip(byte[] content) throws IOException {
		long begin = System.currentTimeMillis();
		try {
			byte[] buff = new byte[8192];
			ByteArrayInputStream bis = new ByteArrayInputStream(content);
			GZIPInputStream inputStream = new GZIPInputStream(bis);

			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			while (true) {
				int len = inputStream.read(buff);
				if (len > 0) {
					bos.write(buff, 0, len);
				} else {
					break;
				}
			}
			return bos.toByteArray();
		} finally {
			LOG.performance(begin, System.currentTimeMillis(), "unzip " + content.length);
		}
	}

}
