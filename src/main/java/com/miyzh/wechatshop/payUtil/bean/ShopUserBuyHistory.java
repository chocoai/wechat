package com.miyzh.wechatshop.payUtil.bean;

import java.io.Serializable;
import java.util.Date;


public class ShopUserBuyHistory implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long orderId;
	private  Long id;
	private  String userKey;
	private  String recommedUserKey;
	private  String grouponName;
	private  Long grouponKey;
	private  Date cretaDate;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getUserKey() {
		return userKey;
	}
	public void setUserKey(String userKey) {
		this.userKey = userKey;
	}
	public String getRecommedUserKey() {
		return recommedUserKey;
	}
	public void setRecommedUserKey(String recommedUserKey) {
		this.recommedUserKey = recommedUserKey;
	}
	public String getGrouponName() {
		return grouponName;
	}
	public void setGrouponName(String grouponName) {
		this.grouponName = grouponName;
	}
	public Long getGrouponKey() {
		return grouponKey;
	}
	public void setGrouponKey(Long grouponKey) {
		this.grouponKey = grouponKey;
	}
	public Date getCretaDate() {
		return cretaDate;
	}
	public void setCretaDate(Date cretaDate) {
		this.cretaDate = cretaDate;
	}
	public Long getOrderId() {
		return orderId;
	}
	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}
	
	
}
