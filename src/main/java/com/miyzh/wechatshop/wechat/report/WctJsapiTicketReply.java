package com.miyzh.wechatshop.wechat.report;

import java.io.Serializable;

public class WctJsapiTicketReply implements Serializable{
	
	private static final long serialVersionUID = 3853029988072001139L;

	private String timestamp;
	
	private String noncestr;
	
	private String signature;

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	public String getNoncestr() {
		return noncestr;
	}

	public void setNoncestr(String noncestr) {
		this.noncestr = noncestr;
	}

	public String getSignature() {
		return signature;
	}

	public void setSignature(String signature) {
		this.signature = signature;
	}

}
