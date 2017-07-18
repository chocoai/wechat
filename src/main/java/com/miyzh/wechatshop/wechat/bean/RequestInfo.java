package com.miyzh.wechatshop.wechat.bean;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * <pre>
 * 微信请求消息.
 * </pre>
 * 
 * @author 张涛 2014-4-28
 */
@XmlRootElement(name = "xml")
public class RequestInfo {

	/** 开发者微信号. */
	private String toUserName;

	/** 发送方帐号（一个OpenID）. */
	private String fromUserName;

	/** 消息创建时间 （整型）. */
	private String createTime;

	/** 消息类型. */
	private String msgType;

	/** 文本消息内容. */
	private String content;

	/** 消息id，64位整型. */
	private String msgId;

	/** 图片链接. */
	private String picUrl;

	/** 消息媒体id，可以调用多媒体文件下载接口拉取数据. */
	private String mediaId;

	/** 语音格式，如amr，speex等. */
	private String format;

	/** 视频消息缩略图的媒体id，可以调用多媒体文件下载接口拉取数据. */
	private String thumbMediaId;

	/** 地理位置维度. */
	private String location_X;

	/** 地理位置经度. */
	private String location_Y;

	/** 地图缩放大小. */
	private String scale;

	/** 地理位置信息. */
	private String label;

	/** 消息标题. */
	private String title;

	/** 消息描述. */
	private String description;

	/** 消息链接. */
	private String url;

	/** 事件 */
	private String event;
	/** 事件Key. */
	private String eventKey;

	/**
	 * <pre>
	 * Gets the to user name.
	 * </pre>
	 * 
	 * @return the string
	 * @author 张涛 2014-4-28
	 */
	@XmlElement(name = "ToUserName")
	public String getToUserName() {
		return toUserName;
	}

	/**
	 * <pre>
	 * Sets the to user name.
	 * </pre>
	 * 
	 * @param toUserName
	 *            the to user name
	 * @author 张涛 2014-4-28
	 */
	public void setToUserName(String toUserName) {
		this.toUserName = toUserName;
	}

	/**
	 * <pre>
	 * Gets the from user name.
	 * </pre>
	 * 
	 * @return the string
	 * @author 张涛 2014-4-28
	 */
	@XmlElement(name = "FromUserName")
	public String getFromUserName() {
		return fromUserName;
	}

	/**
	 * <pre>
	 * Sets the from user name.
	 * </pre>
	 * 
	 * @param fromUserName
	 *            the from user name
	 * @author 张涛 2014-4-28
	 */
	public void setFromUserName(String fromUserName) {
		this.fromUserName = fromUserName;
	}

	/**
	 * <pre>
	 * Gets the create time.
	 * </pre>
	 * 
	 * @return the string
	 * @author 张涛 2014-4-28
	 */
	@XmlElement(name = "CreateTime")
	public String getCreateTime() {
		return createTime;
	}

	/**
	 * <pre>
	 * Sets the create time.
	 * </pre>
	 * 
	 * @param createTime
	 *            the create time
	 * @author 张涛 2014-4-28
	 */
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	/**
	 * <pre>
	 * Gets the Type.
	 * </pre>
	 * 
	 * @return the Type
	 * @author 张涛 2014-4-28
	 */
	@XmlElement(name = "MsgType")
	public String getMsgType() {
		return msgType;
	}

	/**
	 * <pre>
	 * Sets the Type.
	 * </pre>
	 * 
	 * @param msgType
	 *            the new Type
	 * @author 张涛 2014-4-28
	 */
	public void setMsgType(String msgType) {
		this.msgType = msgType;
	}

	/**
	 * <pre>
	 * Gets the content.
	 * </pre>
	 * 
	 * @return the string
	 * @author 张涛 2014-4-28
	 */
	@XmlElement(name = "Content")
	public String getContent() {
		return content;
	}

	/**
	 * <pre>
	 * Sets the content.
	 * </pre>
	 * 
	 * @param content
	 *            the content
	 * @author 张涛 2014-4-28
	 */
	public void setContent(String content) {
		this.content = content;
	}

	/**
	 * <pre>
	 * Gets the Id.
	 * </pre>
	 * 
	 * @return the Id
	 * @author 张涛 2014-4-28
	 */
	@XmlElement(name = "MsgId")
	public String getMsgId() {
		return msgId;
	}

	/**
	 * <pre>
	 * Sets the Id.
	 * </pre>
	 * 
	 * @param msgId
	 *            the new Id
	 * @author 张涛 2014-4-28
	 */
	public void setMsgId(String msgId) {
		this.msgId = msgId;
	}

	/**
	 * <pre>
	 * Gets the pic url.
	 * </pre>
	 * 
	 * @return the string
	 * @author 张涛 2014-4-28
	 */
	@XmlElement(name = "PicUrl")
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
	 * Gets the Id.
	 * </pre>
	 * 
	 * @return the Id
	 * @author 张涛 2014-4-28
	 */
	@XmlElement(name = "MediaId")
	public String getMediaId() {
		return mediaId;
	}

	/**
	 * <pre>
	 * Sets the Id.
	 * </pre>
	 * 
	 * @param mediaId
	 *            the new Id
	 * @author 张涛 2014-4-28
	 */
	public void setMediaId(String mediaId) {
		this.mediaId = mediaId;
	}

	/**
	 * <pre>
	 * Gets the format.
	 * </pre>
	 * 
	 * @return the string
	 * @author 张涛 2014-4-28
	 */
	@XmlElement(name = "getThumbMediaId")
	public String getFormat() {
		return format;
	}

	/**
	 * <pre>
	 * Sets the format.
	 * </pre>
	 * 
	 * @param format
	 *            the format
	 * @author 张涛 2014-4-28
	 */
	public void setFormat(String format) {
		this.format = format;
	}

	/**
	 * <pre>
	 * Gets the thumb media id.
	 * </pre>
	 * 
	 * @return the string
	 * @author 张涛 2014-4-28
	 */
	@XmlElement(name = "ThumbMediaId")
	public String getThumbMediaId() {
		return thumbMediaId;
	}

	/**
	 * <pre>
	 * Sets the thumb media id.
	 * </pre>
	 * 
	 * @param thumbMediaId
	 *            the thumb media id
	 * @author 张涛 2014-4-28
	 */
	public void setThumbMediaId(String thumbMediaId) {
		this.thumbMediaId = thumbMediaId;
	}

	/**
	 * <pre>
	 * Gets the location_ x.
	 * </pre>
	 * 
	 * @return the string
	 * @author 张涛 2014-4-28
	 */
	@XmlElement(name = "Location_X")
	public String getLocation_X() {
		return location_X;
	}

	/**
	 * <pre>
	 * Sets the location_ x.
	 * </pre>
	 * 
	 * @param location_X
	 *            the location_ x
	 * @author 张涛 2014-4-28
	 */
	public void setLocation_X(String location_X) {
		this.location_X = location_X;
	}

	/**
	 * <pre>
	 * Gets the location_ y.
	 * </pre>
	 * 
	 * @return the string
	 * @author 张涛 2014-4-28
	 */
	@XmlElement(name = "Location_Y")
	public String getLocation_Y() {
		return location_Y;
	}

	/**
	 * <pre>
	 * Sets the location_ y.
	 * </pre>
	 * 
	 * @param location_Y
	 *            the location_ y
	 * @author 张涛 2014-4-28
	 */
	public void setLocation_Y(String location_Y) {
		this.location_Y = location_Y;
	}

	/**
	 * <pre>
	 * Gets the scale.
	 * </pre>
	 * 
	 * @return the string
	 * @author 张涛 2014-4-28
	 */
	@XmlElement(name = "Scale")
	public String getScale() {
		return scale;
	}

	/**
	 * <pre>
	 * Sets the scale.
	 * </pre>
	 * 
	 * @param scale
	 *            the scale
	 * @author 张涛 2014-4-28
	 */
	public void setScale(String scale) {
		this.scale = scale;
	}

	/**
	 * <pre>
	 * Gets the label.
	 * </pre>
	 * 
	 * @return the string
	 * @author 张涛 2014-4-28
	 */
	@XmlElement(name = "Label")
	public String getLabel() {
		return label;
	}

	/**
	 * <pre>
	 * Sets the label.
	 * </pre>
	 * 
	 * @param label
	 *            the label
	 * @author 张涛 2014-4-28
	 */
	public void setLabel(String label) {
		this.label = label;
	}

	/**
	 * <pre>
	 * Gets the title.
	 * </pre>
	 * 
	 * @return the string
	 * @author 张涛 2014-4-28
	 */
	@XmlElement(name = "Title")
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
	@XmlElement(name = "Description")
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
	 * Gets the url.
	 * </pre>
	 * 
	 * @return the string
	 * @author 张涛 2014-4-28
	 */
	@XmlElement(name = "Url")
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

	/**
	 * <pre>
	 * Gets the event.
	 * </pre>
	 * 
	 * @return the string
	 * @author 张涛 2014-4-28
	 */
	@XmlElement(name = "Event")
	public String getEvent() {
		return event;
	}

	/**
	 * <pre>
	 * Sets the event.
	 * </pre>
	 * 
	 * @param event
	 *            the event
	 * @author 张涛 2014-4-28
	 */
	public void setEvent(String event) {
		this.event = event;
	}

	@XmlElement(name = "EventKey")
	public String getEventKey() {
		return eventKey;
	}

	public void setEventKey(String eventKey) {
		this.eventKey = eventKey;
	}

}