package com.miyzh.wechatshop.user.bean;

import java.io.Serializable;

/**
 * 文件名： UserBean.java<br>
 * 描述: 用户<br>
 * 修改人: wumingdi<br>
 * 修改时间： 2016年7月9日 下午11:33:16 <br>
 */
public class UserBean implements Serializable {

	private static final long serialVersionUID = 1091794995166762729L;

	private String openId;

	private String userId;

	/** 头像 **/
	private String headUrl;

	/** 登录名 **/
	private String loginName;

	/** 用户名 **/
	private String userName;

	/** 用户区域 **/
	private String userAreaCode;

	/** 手机号 **/
	private String phoneNum;

	/** 密码 **/
	private String password;

	/** 性别 0男1女 **/
	private Integer sex;

	/** 诊所id **/
	private String clinicId;

	/** 诊所编码 **/
	private String clinicCode;

	public String getClinicId() {
		return clinicId;
	}

	public void setClinicId(String clinicId) {
		this.clinicId = clinicId;
	}

	public String getClinicCode() {
		return clinicCode;
	}

	public void setClinicCode(String clinicCode) {
		this.clinicCode = clinicCode;
	}

	public Integer getSex() {
		return sex;
	}

	public void setSex(Integer sex) {
		this.sex = sex;
	}

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getHeadUrl() {
		return headUrl;
	}

	public void setHeadUrl(String headUrl) {
		this.headUrl = headUrl;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserAreaCode() {
		return userAreaCode;
	}

	public void setUserAreaCode(String userAreaCode) {
		this.userAreaCode = userAreaCode;
	}

	public String getPhoneNum() {
		return phoneNum;
	}

	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
