package com.miyzh.wechatshop.user.bean;

import java.io.Serializable;

/**
 * 文件名： MemberBean.java<br>
 * 描述: 用户管理会员<br>
 * 修改人: wumingdi<br>
 * 修改时间： 2016年7月9日 下午11:36:12 <br>
 */
public class MemberBean implements Serializable {

	private static final long serialVersionUID = -7137680724651371316L;

	private int memberId;
	/** 用户ID **/
	private Integer userId;
	/** 站点ID **/
	private String websiteId;
	/** 创建时间 **/
	private String createTime;
	/** 是否禁用 **/
	private Integer isDisabled;
	/** 是否激活 **/
	private Integer isActive;
	/** 激活码 **/
	private String activationCode;

	/** 用户中心Id **/
	private String usercenterId;
	
	
	
	

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getUsercenterId() {
		return usercenterId;
	}

	public void setUsercenterId(String usercenterId) {
		this.usercenterId = usercenterId;
	}

	public int getMemberId() {
		return memberId;
	}

	public void setMemberId(int memberId) {
		this.memberId = memberId;
	}


	public String getWebsiteId() {
		return websiteId;
	}

	public void setWebsiteId(String websiteId) {
		this.websiteId = websiteId;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public Integer getIsDisabled() {
		return isDisabled;
	}

	public void setIsDisabled(Integer isDisabled) {
		this.isDisabled = isDisabled;
	}

	public Integer getIsActive() {
		return isActive;
	}

	public void setIsActive(Integer isActive) {
		this.isActive = isActive;
	}

	public String getActivationCode() {
		return activationCode;
	}

	public void setActivationCode(String activationCode) {
		this.activationCode = activationCode;
	}

}
