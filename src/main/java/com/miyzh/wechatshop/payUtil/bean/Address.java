package com.miyzh.wechatshop.payUtil.bean;

import com.miyzh.framework.base.bean.BaseBean;

public class Address extends BaseBean {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private java.lang.Long id;

	// fields
	private java.lang.String name;
	private java.lang.String code;
	private java.lang.Integer priority;

	private Long parent;

	public java.lang.Long getId() {
		return id;
	}

	public void setId(java.lang.Long id) {
		this.id = id;
	}

	public java.lang.String getName() {
		return name;
	}

	public void setName(java.lang.String name) {
		this.name = name;
	}

	public java.lang.String getCode() {
		return code;
	}

	public void setCode(java.lang.String code) {
		this.code = code;
	}

	public java.lang.Integer getPriority() {
		return priority;
	}

	public void setPriority(java.lang.Integer priority) {
		this.priority = priority;
	}

	public Long getParent() {
		return parent;
	}

	public void setParent(Long parent) {
		this.parent = parent;
	}
	
	
}
