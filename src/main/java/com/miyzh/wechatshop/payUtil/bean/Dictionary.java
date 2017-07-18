package com.miyzh.wechatshop.payUtil.bean;

import com.miyzh.framework.base.bean.BaseBean;

public class Dictionary extends BaseBean {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private java.lang.Long id;

	private java.lang.String name;
	private Integer priority;

	private Long shopDictionaryType;

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

	public Integer getPriority() {
		return priority;
	}

	public void setPriority(Integer priority) {
		this.priority = priority;
	}

	public Long getShopDictionaryType() {
		return shopDictionaryType;
	}

	public void setShopDictionaryType(Long shopDictionaryType) {
		this.shopDictionaryType = shopDictionaryType;
	}
	
}
