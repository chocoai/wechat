package com.miyzh.wechatshop.user.bean;

import java.io.Serializable;

/**
 * 文件名： ThirdUserInfoBean.java<br>
 * 描述: 第三方账户表<br>
 * 修改人: wumingdi<br>
 * 修改时间： 2016年7月9日 <br>
 */
public class ThirdUserInfoBean implements Serializable {

	private static final long serialVersionUID = -6195925350207628342L;

	private String openId;
	/** 用户中心主键 **/
	private String usercenterKey;

	/** 微信头像url **/
	private String headUrl;

	/** 是否绑定,0:否；1：是 **/
	private Integer ifBinding;

	private String unionId;

	/** 是否关注,0:否；1：是 **/
	private Integer ifFollow;

	/** 账户类型 1-微信 2-微博 3-QQ **/
	private Integer accountType;

	/** 创建时间 **/
	private String createTime;
	/** 更新时间 **/
	private String updateTime;
	
	/**  **/
	private String memberID;

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public String getUsercenterKey() {
		return usercenterKey;
	}

	public void setUsercenterKey(String usercenterKey) {
		this.usercenterKey = usercenterKey;
	}

	public String getHeadUrl() {
		return headUrl;
	}

	public void setHeadUrl(String headUrl) {
		this.headUrl = headUrl;
	}

	public Integer getIfBinding() {
		return ifBinding;
	}

	public void setIfBinding(Integer ifBinding) {
		this.ifBinding = ifBinding;
	}

	public String getUnionId() {
		return unionId;
	}

	public void setUnionId(String unionId) {
		this.unionId = unionId;
	}

	public Integer getIfFollow() {
		return ifFollow;
	}

	public void setIfFollow(Integer ifFollow) {
		this.ifFollow = ifFollow;
	}

	public Integer getAccountType() {
		return accountType;
	}

	public void setAccountType(Integer accountType) {
		this.accountType = accountType;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

	public String getMemberID() {
		return memberID;
	}

	public void setMemberID(String memberID) {
		this.memberID = memberID;
	}

}
