package com.miyzh.wechatshop.user.bean;

import java.io.Serializable;

/**
 * 文件名： ChannelConcernBean.java<br>
 * 描述: 渠道关注<br>
 * 修改人: wumingdi<br>
 * 修改时间： 2016年7月21日 <br>
 */
public class ChannelConcernBean implements Serializable {
	private static final long serialVersionUID = 2619225949025856400L;

	/** 主键 **/
	private String id;

	/** 微信ID **/
	private String openId;

	/** 用户ID **/
	private String userKey;
	/** 渠道ID **/
	private String channelKey;
	/** 关注类型,1-G码 2-T码 **/
	private Integer concernType;
	/** 推荐用户ID **/
	private String recommedUserKey;

	public Integer getConcernType() {
		return concernType;
	}

	public void setConcernType(Integer concernType) {
		this.concernType = concernType;
	}

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUserKey() {
		return userKey;
	}

	public void setUserKey(String userKey) {
		this.userKey = userKey;
	}

	public String getChannelKey() {
		return channelKey;
	}

	public void setChannelKey(String channelKey) {
		this.channelKey = channelKey;
	}

	public String getRecommedUserKey() {
		return recommedUserKey;
	}

	public void setRecommedUserKey(String recommedUserKey) {
		this.recommedUserKey = recommedUserKey;
	}

}
