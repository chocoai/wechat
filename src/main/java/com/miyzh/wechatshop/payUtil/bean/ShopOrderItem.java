package com.miyzh.wechatshop.payUtil.bean;

import com.miyzh.framework.base.bean.BaseBean;

public class ShopOrderItem extends BaseBean {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long id;
	private int count;
	private double salePrice;
	private int discussStatus;
	private Long website;
	private Long product;
	private Long ordeR;
	private String name;
	private String cdfined3;
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public double getSalePrice() {
		return salePrice;
	}

	public void setSalePrice(double salePrice) {
		this.salePrice = salePrice;
	}

	public int getDiscussStatus() {
		return discussStatus;
	}

	public void setDiscussStatus(int discussStatus) {
		this.discussStatus = discussStatus;
	}

	public Long getWebsite() {
		return website;
	}

	public void setWebsite(Long website) {
		this.website = website;
	}

	public Long getProduct() {
		return product;
	}

	public void setProduct(Long product) {
		this.product = product;
	}

	public Long getOrdeR() {
		return ordeR;
	}

	public void setOrdeR(Long ordeR) {
		this.ordeR = ordeR;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCdfined3() {
		return cdfined3;
	}

	public void setCdfined3(String cdfined3) {
		this.cdfined3 = cdfined3;
	}

}
