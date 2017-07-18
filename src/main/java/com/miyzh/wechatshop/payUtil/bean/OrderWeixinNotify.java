package com.miyzh.wechatshop.payUtil.bean;

import java.util.Date;

import com.miyzh.framework.base.bean.BaseBean;

public class OrderWeixinNotify extends BaseBean {
	
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long id;
	private String codeId;
	private String orderCode;
	private int notifyFlag;
	private int orderType;
	private String sign;
	private String resultCode;
	private String errCode;
	private String errCodeDes;
	private String openId;
	private String webchatCode;
	
	private Date notifyTime;
	
	private Date cretaTime;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCodeId() {
		return codeId;
	}

	public void setCodeId(String codeId) {
		this.codeId = codeId;
	}

	public String getOrderCode() {
		return orderCode;
	}

	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}

	public int getNotifyFlag() {
		return notifyFlag;
	}

	public void setNotifyFlag(int notifyFlag) {
		this.notifyFlag = notifyFlag;
	}

	public int getOrderType() {
		return orderType;
	}

	public void setOrderType(int orderType) {
		this.orderType = orderType;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public String getResultCode() {
		return resultCode;
	}

	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}

	public String getErrCode() {
		return errCode;
	}

	public void setErrCode(String errCode) {
		this.errCode = errCode;
	}

	public String getErrCodeDes() {
		return errCodeDes;
	}

	public void setErrCodeDes(String errCodeDes) {
		this.errCodeDes = errCodeDes;
	}

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public String getWebchatCode() {
		return webchatCode;
	}

	public void setWebchatCode(String webchatCode) {
		this.webchatCode = webchatCode;
	}

	public Date getNotifyTime() {
		return notifyTime;
	}

	public void setNotifyTime(Date notifyTime) {
		this.notifyTime = notifyTime;
	}

	public Date getCretaTime() {
		return cretaTime;
	}

	public void setCretaTime(Date cretaTime) {
		this.cretaTime = cretaTime;
	}
	
	
	
}
