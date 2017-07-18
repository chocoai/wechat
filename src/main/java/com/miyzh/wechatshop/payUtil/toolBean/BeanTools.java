package com.miyzh.wechatshop.payUtil.toolBean;

import java.io.Serializable;
import java.util.List;

import com.miyzh.wechatshop.payUtil.bean.ProductInfo;
import com.miyzh.wechatshop.payUtil.bean.ShopMemberAddress;

public class BeanTools implements Serializable {
	private static final long serialVersionUID = 388683639547231371L;
	private String orderId;
	private String flag;
	private String products;
	private String opendid;
	private String memberid;
	private String groupID;
	private String pageId;
	private List<ProductInfo> productInfo;
	private String total;
	private String status;
	private ShopMemberAddress shopMemberAddress;
	
	private String count;
	private String addressId;
	public String getProducts() {
		return products;
	}
	public void setProducts(String products) {
		this.products = products;
	}
	public String getOpendid() {
		return opendid;
	}
	public void setOpendid(String opendid) {
		this.opendid = opendid;
	}
	public String getGroupID() {
		return groupID;
	}
	public void setGroupID(String groupID) {
		this.groupID = groupID;
	}
	public List<ProductInfo> getProductInfo() {
		return productInfo;
	}
	public void setProductInfo(List<ProductInfo> productInfo) {
		this.productInfo = productInfo;
	}
	public String getTotal() {
		return total;
	}
	public void setTotal(String total) {
		this.total = total;
	}
	public ShopMemberAddress getShopMemberAddress() {
		return shopMemberAddress;
	}
	public void setShopMemberAddress(ShopMemberAddress shopMemberAddress) {
		this.shopMemberAddress = shopMemberAddress;
	}
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	public String getMemberid() {
		return memberid;
	}
	public void setMemberid(String memberid) {
		this.memberid = memberid;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getPageId() {
		return pageId;
	}
	public void setPageId(String pageId) {
		this.pageId = pageId;
	}
	public String getAddressId() {
		return addressId;
	}
	public void setAddressId(String addressId) {
		this.addressId = addressId;
	}
	public String getCount() {
		return count;
	}
	public void setCount(String count) {
		this.count = count;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
}
