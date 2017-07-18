package com.miyzh.wechatshop.payUtil.toolBean;

import java.io.Serializable;

public class UserBeanTools implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String opendid;
	private String recommenduserid;
	private String groupID;
	public String getOpendid() {
		return opendid;
	}
	public void setOpendid(String opendid) {
		this.opendid = opendid;
	}
	public String getRecommenduserid() {
		return recommenduserid;
	}
	public void setRecommenduserid(String recommenduserid) {
		this.recommenduserid = recommenduserid;
	}
	public String getGroupID() {
		return groupID;
	}
	public void setGroupID(String groupID) {
		this.groupID = groupID;
	}
	
}
