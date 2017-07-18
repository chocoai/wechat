package com.miyzh.wechatshop.payUtil.bean;

import java.util.Date;

import com.miyzh.framework.base.bean.BaseBean;

public class ShopOrderTrail extends BaseBean {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private  Long id;
	private Date operateTime;
	private String operateType;
	private Long member;
	private Long order;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Date getOperateTime() {
		return operateTime;
	}
	public void setOperateTime(Date operateTime) {
		this.operateTime = operateTime;
	}
	public String getOperateType() {
		return operateType;
	}
	public void setOperateType(String operateType) {
		this.operateType = operateType;
	}
	public Long getMember() {
		return member;
	}
	public void setMember(Long member) {
		this.member = member;
	}
	public Long getOrder() {
		return order;
	}
	public void setOrder(Long order) {
		this.order = order;
	}
	
}
