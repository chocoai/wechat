package com.miyzh.framework.common.report.protocol;

/**
 * 文件名: CashRedResReportData.java<br>
 * 版权：Copyright 2014 Digital Bay Technology Group. Co. Ltd. All Rights Reserved. <br>
 * 描述: 现金红包响应报文数据 <br>
 * 修改人: guchangpeng <br>
 * 修改时间：2016-2-26 上午10:42:37 <br>
 * 修改内容：新增 <br>
 */
public class CashRedResReportData extends ReportResData {
	private String mch_billno;
	private String mch_id;
	private String wxappid;
	private String re_openid;
	private String total_amount;
	private String send_time;
	private String send_listid;

	public String getMch_billno() {
		return mch_billno;
	}

	public void setMch_billno(String mchBillno) {
		mch_billno = mchBillno;
	}

	public String getMch_id() {
		return mch_id;
	}

	public void setMch_id(String mchId) {
		mch_id = mchId;
	}

	public String getWxappid() {
		return wxappid;
	}

	public void setWxappid(String wxappid) {
		this.wxappid = wxappid;
	}

	public String getRe_openid() {
		return re_openid;
	}

	public void setRe_openid(String reOpenid) {
		re_openid = reOpenid;
	}

	public String getTotal_amount() {
		return total_amount;
	}

	public void setTotal_amount(String totalAmount) {
		total_amount = totalAmount;
	}

	public String getSend_time() {
		return send_time;
	}

	public void setSend_time(String sendTime) {
		send_time = sendTime;
	}

	public String getSend_listid() {
		return send_listid;
	}

	public void setSend_listid(String sendListid) {
		send_listid = sendListid;
	}

}
