package com.miyzh.wechatshop.payUtil.bean;

import java.io.Serializable;
import java.util.List;


public class ShopOrder implements Serializable  {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6375698563146057311L;

	private int orderId;

	private java.lang.Long code;
	private java.lang.String comments;
	private java.lang.String ip;
	private java.lang.String createTime;
	private java.lang.String shippingTime;
	private java.lang.String finishedTime;
	private java.lang.String lastModified;
	private java.lang.Double productPrice;
	private java.lang.Double freight;
	private java.lang.Double total;
	private java.lang.Integer score;
	private java.lang.Double weight;
	private java.lang.Double couponPrice;
	private java.lang.String productName;
	private java.lang.Integer paymentStatus;
	private java.lang.Integer shippingStatus;
	private java.lang.Integer status;
	private java.lang.String receiveName;
	private java.lang.String receiveAddress;
	private java.lang.String receiveZip;
	private java.lang.String receivePhone;
	private java.lang.String receiveMobile;
	private java.lang.String paymentCode;
	private java.lang.String clinicName;
	private java.lang.String clinicCode;
	private  Integer   discussStatus;
	private Integer printSatus;
    private  String    tgCode;
    private  String    OrderType;
	 
	private Long website;
	private Long sendlog;
	private Long member;
	private Long payment;
	private Long shipping;
	private Long returnOrder;
	private Long ShipmentsId;
	private Long groupBuy;
	private List<ShopOrderItem>  shopOrderItem;

	private String groupName;
	private String piceUrl;
	
	
	private String tcodeId;
	
	public int getOrderId() {
		return orderId;
	}
	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}
	public java.lang.Long getCode() {
		return code;
	}
	public void setCode(java.lang.Long code) {
		this.code = code;
	}
	public java.lang.String getComments() {
		return comments;
	}
	public void setComments(java.lang.String comments) {
		this.comments = comments;
	}
	public java.lang.String getIp() {
		return ip;
	}
	public void setIp(java.lang.String ip) {
		this.ip = ip;
	}
	public java.lang.Double getProductPrice() {
		return productPrice;
	}
	public void setProductPrice(java.lang.Double productPrice) {
		this.productPrice = productPrice;
	}
	public java.lang.Double getFreight() {
		return freight;
	}
	public void setFreight(java.lang.Double freight) {
		this.freight = freight;
	}
	public java.lang.Double getTotal() {
		return total;
	}
	public void setTotal(java.lang.Double total) {
		this.total = total;
	}
	public java.lang.Integer getScore() {
		return score;
	}
	public void setScore(java.lang.Integer score) {
		this.score = score;
	}
	public java.lang.Double getWeight() {
		return weight;
	}
	public void setWeight(java.lang.Double weight) {
		this.weight = weight;
	}
	public java.lang.Double getCouponPrice() {
		return couponPrice;
	}
	public void setCouponPrice(java.lang.Double couponPrice) {
		this.couponPrice = couponPrice;
	}
	public java.lang.String getProductName() {
		return productName;
	}
	public void setProductName(java.lang.String productName) {
		this.productName = productName;
	}
	public java.lang.Integer getPaymentStatus() {
		return paymentStatus;
	}
	public void setPaymentStatus(java.lang.Integer paymentStatus) {
		this.paymentStatus = paymentStatus;
	}
	public java.lang.Integer getShippingStatus() {
		return shippingStatus;
	}
	public void setShippingStatus(java.lang.Integer shippingStatus) {
		this.shippingStatus = shippingStatus;
	}
	public java.lang.Integer getStatus() {
		return status;
	}
	public void setStatus(java.lang.Integer status) {
		this.status = status;
	}
	public java.lang.String getReceiveName() {
		return receiveName;
	}
	public void setReceiveName(java.lang.String receiveName) {
		this.receiveName = receiveName;
	}
	public java.lang.String getReceiveAddress() {
		return receiveAddress;
	}
	public void setReceiveAddress(java.lang.String receiveAddress) {
		this.receiveAddress = receiveAddress;
	}
	public java.lang.String getReceiveZip() {
		return receiveZip;
	}
	public void setReceiveZip(java.lang.String receiveZip) {
		this.receiveZip = receiveZip;
	}
	public java.lang.String getReceivePhone() {
		return receivePhone;
	}
	public void setReceivePhone(java.lang.String receivePhone) {
		this.receivePhone = receivePhone;
	}
	public java.lang.String getReceiveMobile() {
		return receiveMobile;
	}
	public void setReceiveMobile(java.lang.String receiveMobile) {
		this.receiveMobile = receiveMobile;
	}
	public java.lang.String getPaymentCode() {
		return paymentCode;
	}
	public void setPaymentCode(java.lang.String paymentCode) {
		this.paymentCode = paymentCode;
	}
	public java.lang.String getClinicName() {
		return clinicName;
	}
	public void setClinicName(java.lang.String clinicName) {
		this.clinicName = clinicName;
	}
	public java.lang.String getClinicCode() {
		return clinicCode;
	}
	public void setClinicCode(java.lang.String clinicCode) {
		this.clinicCode = clinicCode;
	}
	public Integer getDiscussStatus() {
		return discussStatus;
	}
	public void setDiscussStatus(Integer discussStatus) {
		this.discussStatus = discussStatus;
	}
	public Integer getPrintSatus() {
		return printSatus;
	}
	public void setPrintSatus(Integer printSatus) {
		this.printSatus = printSatus;
	}
	public String getTgCode() {
		return tgCode;
	}
	public void setTgCode(String tgCode) {
		this.tgCode = tgCode;
	}
	public String getOrderType() {
		return OrderType;
	}
	public void setOrderType(String orderType) {
		OrderType = orderType;
	}
	public Long getWebsite() {
		return website;
	}
	public void setWebsite(Long website) {
		this.website = website;
	}
	public Long getMember() {
		return member;
	}
	public void setMember(Long member) {
		this.member = member;
	}
	public Long getPayment() {
		return payment;
	}
	public void setPayment(Long payment) {
		this.payment = payment;
	}
	public Long getShipping() {
		return shipping;
	}
	public void setShipping(Long shipping) {
		this.shipping = shipping;
	}
	public Long getReturnOrder() {
		return returnOrder;
	}
	public void setReturnOrder(Long returnOrder) {
		this.returnOrder = returnOrder;
	}
	public Long getShipmentsId() {
		return ShipmentsId;
	}
	public void setShipmentsId(Long shipmentsId) {
		ShipmentsId = shipmentsId;
	}
	public Long getGroupBuy() {
		return groupBuy;
	}
	public void setGroupBuy(Long groupBuy) {
		this.groupBuy = groupBuy;
	}
	public List<ShopOrderItem> getShopOrderItem() {
		return shopOrderItem;
	}
	public void setShopOrderItem(List<ShopOrderItem> shopOrderItem) {
		this.shopOrderItem = shopOrderItem;
	}
	public Long getSendlog() {
		return sendlog;
	}
	public void setSendlog(Long sendlog) {
		this.sendlog = sendlog;
	}
	public String getTcodeId() {
		return tcodeId;
	}
	public void setTcodeId(String tcodeId) {
		this.tcodeId = tcodeId;
	}
	public java.lang.String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(java.lang.String createTime) {
		this.createTime = createTime;
	}
	public java.lang.String getShippingTime() {
		return shippingTime;
	}
	public void setShippingTime(java.lang.String shippingTime) {
		this.shippingTime = shippingTime;
	}
	public java.lang.String getFinishedTime() {
		return finishedTime;
	}
	public void setFinishedTime(java.lang.String finishedTime) {
		this.finishedTime = finishedTime;
	}
	public java.lang.String getLastModified() {
		return lastModified;
	}
	public void setLastModified(java.lang.String lastModified) {
		this.lastModified = lastModified;
	}
	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	public String getPiceUrl() {
		return piceUrl;
	}
	public void setPiceUrl(String piceUrl) {
		this.piceUrl = piceUrl;
	}

	
}
