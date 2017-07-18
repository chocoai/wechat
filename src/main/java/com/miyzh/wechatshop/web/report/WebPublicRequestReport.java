package com.miyzh.wechatshop.web.report;

import com.miyzh.framework.base.action.request.BaseRequestReport;
import com.miyzh.wechatshop.web.bean.WebPublic;



/**
* 文件名: WebPublicRequestReport.java
* 版权：Copyright 2014 Digital Bay Technology Group. Co. Ltd. All Rights Reserved. 
* 描述: web端调用接口服务公共接口请求报文对象
* 修改人: guchangpeng
* 修改时间：2014-04-25
* 修改内容：新增
*/
public class WebPublicRequestReport extends BaseRequestReport {

	private WebPublic commandInfo;

	public WebPublic getCommandInfo() {
		return commandInfo;
	}

	public void setCommandInfo(WebPublic commandInfo) {
		this.commandInfo = commandInfo;
	}
	 
}
