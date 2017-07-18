package com.miyzh.wechatshop.payUtil.bean;

import com.miyzh.framework.base.bean.BaseBean;

public class ProductInfo extends BaseBean {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String  groupID;
	private String  productId;
	private String  groupPrice;
	private String  productName;
	private String  productUrl;
	private String  productCount;
	public String getGroupID() {
		return groupID;
	}
	public void setGroupID(String groupID) {
		this.groupID = groupID;
	}
	public String getGroupPrice() {
		return groupPrice;
	}
	public void setGroupPrice(String groupPrice) {
		this.groupPrice = groupPrice;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getProductUrl() {
		return productUrl;
	}
	public void setProductUrl(String productUrl) {
		this.productUrl = productUrl;
	}
	public String getProductCount() {
		return productCount;
	}
	public void setProductCount(String productCount) {
		this.productCount = productCount;
	}
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	
}
