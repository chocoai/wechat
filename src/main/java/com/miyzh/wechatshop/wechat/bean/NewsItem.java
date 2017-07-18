package com.miyzh.wechatshop.wechat.bean;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * <pre>
 * The Class NewsItem.
 * </pre>
 *
 * @author 张涛 2014-4-28
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "item")
public class NewsItem {

	/** 标题. */
	@XmlElement(name = "Title")
	private String title;

	/** 描述. */
	@XmlElement(name = "Description")
	private String description;

	/** 图片的Url地址. */
	@XmlElement(name = "PicUrl")
	private String picUrl;

	/** 链接地址. */
	@XmlElement(name = "Url")
	private String url;

	/**
	 * <pre>
	 * Gets the title.
	 * </pre>
	 *
	 * @return the string
	 * @author 张涛 2014-4-28
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * <pre>
	 * Sets the title.
	 * </pre>
	 *
	 * @param title
	 *            the title
	 * @author 张涛 2014-4-28
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * <pre>
	 * Gets the description.
	 * </pre>
	 *
	 * @return the string
	 * @author 张涛 2014-4-28
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * <pre>
	 * Sets the description.
	 * </pre>
	 *
	 * @param description
	 *            the description
	 * @author 张涛 2014-4-28
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * <pre>
	 * Gets the pic url.
	 * </pre>
	 *
	 * @return the string
	 * @author 张涛 2014-4-28
	 */
	public String getPicUrl() {
		return picUrl;
	}

	/**
	 * <pre>
	 * Sets the pic url.
	 * </pre>
	 *
	 * @param picUrl
	 *            the pic url
	 * @author 张涛 2014-4-28
	 */
	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}

	/**
	 * <pre>
	 * Gets the url.
	 * </pre>
	 *
	 * @return the string
	 * @author 张涛 2014-4-28
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * <pre>
	 * Sets the url.
	 * </pre>
	 *
	 * @param url
	 *            the url
	 * @author 张涛 2014-4-28
	 */
	public void setUrl(String url) {
		this.url = url;
	}

}
