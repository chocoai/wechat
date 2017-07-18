package com.miyzh.wechatshop.payUtil.pay.com;


public class WxRefundDto {

	private String refundCode;//订单号
	private String wechatCode;//微信订单号
	private String refundMony;//退费金额
	
	/**
	 * @return the refundCode
	 */
	public String getRefundCode() {
		return refundCode;
	}
	/**
	 * @return the wechatCode
	 */
	public String getWechatCode() {
		return wechatCode;
	}
	/**
	 * @return the refundMony
	 */
	public String getRefundMony() {
		return refundMony;
	}
	/**
	 * @param refundCode the refundCode to set
	 */
	public void setRefundCode(String refundCode) {
		this.refundCode = refundCode;
	}
	/**
	 * @param wechatCode the wechatCode to set
	 */
	public void setWechatCode(String wechatCode) {
		this.wechatCode = wechatCode;
	}
	/**
	 * @param refundMony the refundMony to set
	 */
	public void setRefundMony(String refundMony) {
		this.refundMony = refundMony;
	}

	

	
}
