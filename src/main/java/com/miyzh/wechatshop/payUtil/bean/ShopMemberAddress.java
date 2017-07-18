package com.miyzh.wechatshop.payUtil.bean;

import com.miyzh.framework.base.bean.BaseBean;

public class ShopMemberAddress extends BaseBean {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private java.lang.Long id;

	// fields
	private java.lang.String username;
	private String gender;
	private java.lang.String detailaddress;
	private java.lang.String postCode;
	private java.lang.String tel;
	private java.lang.String areaCode;
	private java.lang.String phone;
	private java.lang.String extNumber;
	private String isDefault;
	private String  email;
	private Integer  delFlag;

	private Long member;
	private String province;
	private String city;
	private String country;
	public java.lang.Long getId() {
		return id;
	}
	public void setId(java.lang.Long id) {
		this.id = id;
	}
	public java.lang.String getUsername() {
		return username;
	}
	public void setUsername(java.lang.String username) {
		this.username = username;
	}
	public String isGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public java.lang.String getDetailaddress() {
		return detailaddress;
	}
	public void setDetailaddress(java.lang.String detailaddress) {
		this.detailaddress = detailaddress;
	}
	public java.lang.String getPostCode() {
		return postCode;
	}
	public void setPostCode(java.lang.String postCode) {
		this.postCode = postCode;
	}
	public java.lang.String getTel() {
		return tel;
	}
	public void setTel(java.lang.String tel) {
		this.tel = tel;
	}
	public java.lang.String getAreaCode() {
		return areaCode;
	}
	public void setAreaCode(java.lang.String areaCode) {
		this.areaCode = areaCode;
	}
	public java.lang.String getPhone() {
		return phone;
	}
	public void setPhone(java.lang.String phone) {
		this.phone = phone;
	}
	public java.lang.String getExtNumber() {
		return extNumber;
	}
	public void setExtNumber(java.lang.String extNumber) {
		this.extNumber = extNumber;
	}
	public String isDefault() {
		return isDefault;
	}
	public void setDefault(String isDefault) {
		this.isDefault = isDefault;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Integer getDelFlag() {
		return delFlag;
	}
	public void setDelFlag(Integer delFlag) {
		this.delFlag = delFlag;
	}
	public Long getMember() {
		return member;
	}
	public void setMember(Long member) {
		this.member = member;
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
	
	
	
}
