package com.miyzh.wechatshop.usercenter.bean;

import java.io.Serializable;

/**
 * 文件名： UserCenterResultBean.java<br>
 * 描述: 用户中心返回结果<br>
 * 修改人: wumingdi<br>
 * 修改时间： 2016年7月10日 <br>
 */
public class UserCenterResultBean implements Serializable {

	private static final long serialVersionUID = -6519645594200117395L;

	/** 返回代码 **/
	private String resultCode;

	/** 返回消息 **/
	private String resultMsg;

	/** 返回对象 **/
	private Object resultObject;

	public Object getResultObject() {
		return resultObject;
	}

	public void setResultObject(Object resultObject) {
		this.resultObject = resultObject;
	}

	public String getResultCode() {
		return resultCode;
	}

	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}

	public String getResultMsg() {
		return resultMsg;
	}

	public void setResultMsg(String resultMsg) {
		this.resultMsg = resultMsg;
	}

}
