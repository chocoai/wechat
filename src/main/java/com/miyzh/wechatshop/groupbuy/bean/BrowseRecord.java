package com.miyzh.wechatshop.groupbuy.bean;

/**
 * <pre>
 * the BrowseRecord class for
 * 埋点类
 * </pre>
 *
 * @author zhangtao 2016年7月11日
 */
public class BrowseRecord {

	/** openid. */
	private String openId;
	/** 团购id. */
	private String grouponKey;
	/** 时间. */
	private String browseDate;
	/** 推荐人. */
	private String recommedUserKey;
	/** 类型. */
	private String browseType;
	public String getOpenId() {
		return openId;
	}
	public void setOpenId(String openId) {
		this.openId = openId;
	}
	public String getGrouponKey() {
		return grouponKey;
	}
	public void setGrouponKey(String grouponKey) {
		this.grouponKey = grouponKey;
	}
	public String getBrowseDate() {
		return browseDate;
	}
	public void setBrowseDate(String browseDate) {
		this.browseDate = browseDate;
	}
	public String getRecommedUserKey() {
		return recommedUserKey;
	}
	public void setRecommedUserKey(String recommedUserKey) {
		this.recommedUserKey = recommedUserKey;
	}
	public String getBrowseType() {
		return browseType;
	}
	public void setBrowseType(String browseType) {
		this.browseType = browseType;
	}

}
