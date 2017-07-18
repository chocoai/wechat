package com.miyzh.wechatshop.user.report;

import java.io.Serializable;

public class UserReport implements Serializable {

	private static final long serialVersionUID = 7430192591389690439L;

	private String openid;
	/** 用户主键 **/
	private String userkey;
	/** 是否会员，0：否 1：是 **/
	private Integer ifmember;
	/** 是否认证会员，0：否； 1：待审核； 2：通过； **/
	private Integer ifmemberauth;
	/** 手机号 **/
	private String phonenum;
	/** 头像，url地址 **/
	private String headurl;
	/** 积分 **/
	private Integer credit;
	/** 地址总数 **/
	private Integer addresscount;
	/** 我的推广二维码总数 **/
	private Integer qrcodecount;
	/** 状态，0：未认证；1：备案待审核；2：认证失败；3：认证通过 **/
	private Integer status;

	/** 用户类型，0：未注册；1：未认证；2：待审核；3：审核不通过；4：认证通过 **/
	private Integer usertype;

	/** 审核意见 **/
	private String certificateOpinion;

	private String unionid;

	public String getUnionid() {
		return unionid;
	}

	public void setUnionid(String unionid) {
		this.unionid = unionid;
	}

	public Integer getUsertype() {
		return usertype;
	}

	public void setUsertype(Integer usertype) {
		this.usertype = usertype;
	}

	public String getCertificateOpinion() {
		return certificateOpinion;
	}

	public void setCertificateOpinion(String certificateOpinion) {
		this.certificateOpinion = certificateOpinion;
	}

	public String getHeadurl() {
		return headurl;
	}

	public void setHeadurl(String headurl) {
		this.headurl = headurl;
	}

	public String getPhonenum() {
		return phonenum;
	}

	public void setPhonenum(String phonenum) {
		this.phonenum = phonenum;
	}

	public Integer getCredit() {
		return credit;
	}

	public void setCredit(Integer credit) {
		this.credit = credit;
	}

	public Integer getAddresscount() {
		return addresscount;
	}

	public void setAddresscount(Integer addresscount) {
		this.addresscount = addresscount;
	}

	public Integer getQrcodecount() {
		return qrcodecount;
	}

	public void setQrcodecount(Integer qrcodecount) {
		this.qrcodecount = qrcodecount;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getIfmember() {
		return ifmember;
	}

	public void setIfmember(Integer ifmember) {
		this.ifmember = ifmember;
	}

	public Integer getIfmemberauth() {
		return ifmemberauth;
	}

	public void setIfmemberauth(Integer ifmemberauth) {
		this.ifmemberauth = ifmemberauth;
	}

	public String getOpenid() {
		return openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}

	public String getUserkey() {
		return userkey;
	}

	public void setUserkey(String userkey) {
		this.userkey = userkey;
	}

}
