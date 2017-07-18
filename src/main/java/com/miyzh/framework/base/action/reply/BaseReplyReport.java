package com.miyzh.framework.base.action.reply;

/**
 * 文件名：BaseRequestReport<br>
 * 版权：Copyright 2014 Digital Bay Technology Group. Co. Ltd. All Rights Reserved.
 * 描述: 基础回复报文信息<br>
 * 修改人: guchangpeng<br>
 * 修改时间：2014-02-26 20:22:52<br>
 * 修改内容：新增<br>
 */
public class BaseReplyReport {
	private String replytime;

	private BaseReplyResult result;

	public String getReplytime() {
		return replytime;
	}

	public void setReplytime(String replytime) {
		this.replytime = replytime;
	}

	public BaseReplyResult getResult() {
		return result;
	}

	public void setResult(BaseReplyResult result) {
		this.result = result;
	}

}
