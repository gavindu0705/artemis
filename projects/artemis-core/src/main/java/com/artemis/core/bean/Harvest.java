package com.artemis.core.bean;

/**
 * 爬虫的爬取结果
 * 
 * @author xiaoyu
 * 
 */
public class Harvest {
	private String url;
	private int statusCode;
	private String mimeType;
	private byte[] content;
	private String captor;
	private String referer;

	public Harvest() {
	}

	public Harvest(String url, int statusCode) {
		this.url = url;
		this.statusCode = statusCode;
	}

	public Harvest(String url, int statusCode, String captor) {
		this.url = url;
		this.captor = captor;
		this.statusCode = statusCode;
	}

	public Harvest(String url, int statusCode, String mimeType, byte[] content, String captor, String referer) {
		this.url = url;
		this.statusCode = statusCode;
		this.mimeType = mimeType;
		this.content = content;
		this.captor = captor;
		this.referer = referer;
	}

	public String getMimeType() {
		return mimeType;
	}

	public void setMimeType(String mimeType) {
		this.mimeType = mimeType;
	}

	public byte[] getContent() {
		return content;
	}

	public void setContent(byte[] content) {
		this.content = content;
	}

	public int getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getCaptor() {
		return captor;
	}

	public void setCaptor(String captor) {
		this.captor = captor;
	}

	public String getReferer() {
		return referer;
	}

	public void setReferer(String referer) {
		this.referer = referer;
	}

	public enum HarvestStatusEnum {
		NONE(0), // NONE
		ERROR(1), // 抓取错误

		SUCCESS(200), // 成功

		REDIRECT(300), //
		REDIRECT1(301), //
		REDIRECT2(302), //

		NOTFOUND(400), //
		NOTFOUND4(404), //

		SERVER_ERROR(500), //
		SERVER_ERROR3(503), //
		;

		public final int code;

		private HarvestStatusEnum(int code) {
			this.code = code;
		}

		public int getCode() {
			return code;
		}

		public static HarvestStatusEnum parse(int code) {
			for (HarvestStatusEnum e : HarvestStatusEnum.values()) {
				if (e.getCode() == code) {
					return e;
				}
			}
			return HarvestStatusEnum.NONE;
		}
	}
}
