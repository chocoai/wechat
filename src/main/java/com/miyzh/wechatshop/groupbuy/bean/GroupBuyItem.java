package com.miyzh.wechatshop.groupbuy.bean;

import java.io.Serializable;
public  class GroupBuyItem implements Serializable {
	private static final long serialVersionUID = 5366983010252189612L;
	private  Long  id ;
	/** 商品ID*/
	private String productId;
	private  String  standard;
	/** 单位*/
	private  String  unit;
	/** 商品图(手机端) */
	private  String  cdfined3;
	/** 商品详情图(手机端) */
	private  String  cdfined2;
	/** 团购价格*/
	private  Double groupPrice;
	/** 商品原价、销售价格*/
	private  Double salePrice;
	/** 商品描述*/
	private  String description;
	/** 商品名称.*/
	private String name;
	/** 封面图片.*/
	private String coverImg;
	/** 生产厂家.*/
	private String manufacturer;
	/** 批准文号.*/
	private String approveNumber;
	
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getStandard() {
		return standard;
	}

	public void setStandard(String standard) {
		this.standard = standard;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public String getCdfined2() {
		return cdfined2;
	}

	public void setCdfined2(String cdfined2) {
		this.cdfined2 = cdfined2;
	}

	public Double getGroupPrice() {
		return groupPrice;
	}

	public void setGroupPrice(Double groupPrice) {
		this.groupPrice = groupPrice;
	}

	public Double getSalePrice() {
		return salePrice;
	}

	public void setSalePrice(Double salePrice) {
		this.salePrice = salePrice;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCoverImg() {
		return coverImg;
	}

	public void setCoverImg(String coverImg) {
		this.coverImg = coverImg;
	}

	public String getManufacturer() {
		return manufacturer;
	}

	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}

	public String getApproveNumber() {
		return approveNumber;
	}

	public void setApproveNumber(String approveNumber) {
		this.approveNumber = approveNumber;
	}

	public String getCdfined3() {
		return cdfined3;
	}

	public void setCdfined3(String cdfined3) {
		this.cdfined3 = cdfined3;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	

}