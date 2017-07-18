package com.miyzh.wechatshop.payUtil.bean;

import java.io.Serializable;
import java.util.Date;


public class ShopSendLog implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2112109932775012674L;
	private long id;
	private String phone;
	private Integer status;
	private String content;
	private String megEss;
	private Integer sendNum;
	private Integer sendType;
	private Date credateTmp;
	private Date sendDate;
	
	
	
	
	
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Integer getSendNum() {
		return sendNum;
	}
	public void setSendNum(Integer sendNum) {
		this.sendNum = sendNum;
	}
	public Integer getSendType() {
		return sendType;
	}
	public void setSendType(Integer sendType) {
		this.sendType = sendType;
	}
	public Date getCredateTmp() {
		return credateTmp;
	}
	public void setCredateTmp(Date credateTmp) {
		this.credateTmp = credateTmp;
	}
	public String getMegEss() {
		return megEss;
	}
	public void setMegEss(String megEss) {
		this.megEss = megEss;
	}
	public Date getSendDate() {
		return sendDate;
	}
	public void setSendDate(Date sendDate) {
		this.sendDate = sendDate;
	}
	
}
