package com.miyzh.wechatshop.wechat.bean;

import com.miyzh.framework.base.bean.BaseBean;

/**
 * 文件名：WctUserInfo<br>
 * 描述: 微信公众号唯一票据信息<br>
 * 修改人: wumingdi<br>
 * 修改时间： 2016年7月6日 下午3:21:03 <br>
 */
public class WctUserInfo extends BaseBean {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/** 用户的唯一标识 **/
	private String openid;
	/** 用户昵称 **/
	private String nickname;
	/** 用户的性别，值为1时是男性，值为2时是女性，值为0时是未知 **/
	private Integer sex;
	/** 用户个人资料填写的省份 **/
	private String province;
	/** 普通用户个人资料填写的城市 **/
	private String city;
	/** 国家，如中国为CN **/
	private String country;
	/** 用户头像 **/
	private String headimgurl;
	/** 用户特权信息 **/
	private String privilege;
	/** unionid **/
	private String unionid;
	
	private String accesstoken;

	/**********************/
	private String code;
	
	

	public String getAccesstoken() {
		return accesstoken;
	}

	public void setAccesstoken(String accesstoken) {
		this.accesstoken = accesstoken;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getOpenid() {
		return openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public Integer getSex() {
		return sex;
	}

	public void setSex(Integer sex) {
		this.sex = sex;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getHeadimgurl() {
		return headimgurl;
	}

	public void setHeadimgurl(String headimgurl) {
		this.headimgurl = headimgurl;
	}

	public String getPrivilege() {
		return privilege;
	}

	public void setPrivilege(String privilege) {
		this.privilege = privilege;
	}

	public String getUnionid() {
		return unionid;
	}

	public void setUnionid(String unionid) {
		this.unionid = unionid;
	}

}
