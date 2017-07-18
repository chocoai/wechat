package com.miyzh.wechatshop.wechat.report;

import com.miyzh.framework.base.action.request.BaseRequestReport;
import com.miyzh.wechatshop.wechat.bean.WctAccessToken;

/**
 * 文件名: WctAccessToken.java<br>
 * 版权：Copyright 2014 Digital Bay Technology Group. Co. Ltd. All Rights Reserved. <br>
 * 描述: 微信公众号唯一票据信息<br>
 * 修改人: guchangpeng<br>
 * 修改时间：2014-07-03 17:57:34<br>
 * 修改内容：新增<br>
 */
public class WctAccessTokenRequestReport extends BaseRequestReport {
	private WctAccessToken commandInfo;

	public WctAccessToken getCommandInfo() {
		return commandInfo;
	}

	public void setCommandInfo(WctAccessToken commandInfo) {
		this.commandInfo = commandInfo;
	}

}
