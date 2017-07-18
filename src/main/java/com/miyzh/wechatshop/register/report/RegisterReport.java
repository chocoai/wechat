package com.miyzh.wechatshop.register.report;

import java.io.Serializable;

public class RegisterReport implements Serializable {

	private static final long serialVersionUID = 924523650635110158L;

	/** 用户主键 **/
	private String userId;

	/** 性别，0：男；1：女； **/
	private Integer sex;

	/** 所属人名称 **/
	private String username;

	/** 手机号 **/
	private String phonenum;

	/** 密码 **/
	private String password;

	/** 验证码 **/
	private String vericode;

	private String unionid;

	private String openid;

	/** 诊所id **/
	private String clinicid;

	/** 头像 **/
	private String headurl;

	/** 诊所编码 **/
	private String cliniccode;

	/** 推荐人id **/
	private String recommedid;

	public String getRecommedid() {
		return recommedid;
	}

	public void setRecommedid(String recommedid) {
		this.recommedid = recommedid;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Integer getSex() {
		return sex;
	}

	public void setSex(Integer sex) {
		this.sex = sex;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPhonenum() {
		return phonenum;
	}

	public void setPhonenum(String phonenum) {
		this.phonenum = phonenum;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getVericode() {
		return vericode;
	}

	public void setVericode(String vericode) {
		this.vericode = vericode;
	}

	public String getUnionid() {
		return unionid;
	}

	public void setUnionid(String unionid) {
		this.unionid = unionid;
	}

	public String getOpenid() {
		return openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}

	public String getClinicid() {
		return clinicid;
	}

	public void setClinicid(String clinicid) {
		this.clinicid = clinicid;
	}

	public String getHeadurl() {
		return headurl;
	}

	public void setHeadurl(String headurl) {
		this.headurl = headurl;
	}

	public String getCliniccode() {
		return cliniccode;
	}

	public void setCliniccode(String cliniccode) {
		this.cliniccode = cliniccode;
	}

}
