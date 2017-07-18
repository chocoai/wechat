/**
 * 
 */
package com.miyzh.wechatshop.wechat.bean;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

/**
 * <pre>
 * 微信返回消息父类（通用消息）.
 * </pre>
 *
 * @author 张涛 2014-4-28
 */
@XmlAccessorType(XmlAccessType.FIELD)
public abstract class AbstractMessage {
	// 开发者微信号
	@XmlElement(name = "ToUserName")
	private String toUserName;
	// 发送方帐号（一个OpenID）
	@XmlElement(name = "FromUserName")
	private String fromUserName;
	// 消息创建时间 （整型）
	@XmlElement(name = "CreateTime")
	private long createTime;
	// 消息类型（text/image/location/link）
	@XmlElement(name = "MsgType")
	private String msgType;

	/**
	 * @return the toUserName
	 */
	public String getToUserName() {
		return toUserName;
	}

	/**
	 * @param toUserName
	 *            the toUserName to set
	 */
	public void setToUserName(String toUserName) {
		this.toUserName = toUserName;
	}

	/**
	 * @return the fromUserName
	 */
	public String getFromUserName() {
		return fromUserName;
	}

	/**
	 * @param fromUserName
	 *            the fromUserName to set
	 */
	public void setFromUserName(String fromUserName) {
		this.fromUserName = fromUserName;
	}

	/**
	 * @return the createTime
	 */
	public long getCreateTime() {
		return createTime;
	}

	/**
	 * @param createTime
	 *            the createTime to set
	 */
	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}

	/**
	 * @return the msgType
	 */
	public String getMsgType() {
		return msgType;
	}

	/**
	 * @param msgType
	 *            the msgType to set
	 */
	public void setMsgType(String msgType) {
		this.msgType = msgType;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("AbstractMessage [toUserName=");
		builder.append(toUserName);
		builder.append(", fromUserName=");
		builder.append(fromUserName);
		builder.append(", createTime=");
		builder.append(createTime);
		builder.append(", msgType=");
		builder.append(msgType);
		builder.append("]");
		return builder.toString();
	}
}
