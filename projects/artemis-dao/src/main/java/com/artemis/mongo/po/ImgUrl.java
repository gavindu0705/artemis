package com.artemis.mongo.po;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import com.artemis.mongo.persistence.MongoEntity;

@Entity
public class ImgUrl implements MongoEntity {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3778850527623833755L;
	private String id;
	private int status; // 0-´ı×¥È¡ 1-´íÎó 2-³É¹¦ 3-300´íÎó 4-400´íÎó 5-500´íÎó
	private String referer;
	private Date creationDate;

	@Id
	@Column(name = ID)
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Column(name = "status")
	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	@Column(name = "c_date")
	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	@Column(name = "referer")
	public String getReferer() {
		return referer;
	}

	public void setReferer(String referer) {
		this.referer = referer;
	}

}
