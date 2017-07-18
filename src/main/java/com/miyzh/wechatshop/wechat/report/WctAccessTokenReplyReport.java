package com.miyzh.wechatshop.wechat.report;

import com.miyzh.framework.base.action.reply.BaseReplyBean;
import com.miyzh.framework.base.action.reply.BaseReplyReport;
import com.miyzh.wechatshop.wechat.bean.WctAccessToken;

/**
 * 文件名: WctAccessTokenReplyReport.java<br>
 * 版权：Copyright 2014 Digital Bay Technology Group. Co. Ltd. All Rights Reserved. <br>
 * 描述: 微信公众号唯一票据信息回复报文<br>
 * 修改人: guchangpeng<br>
 * 修改时间：2014-07-03 17:57:34<br>
 * 修改内容：新增<br>
 */
public class WctAccessTokenReplyReport extends BaseReplyReport {
	private BaseReplyBean<WctAccessToken> reply;

	public BaseReplyBean<WctAccessToken> getReply() {
		return reply;
	}

	public void setReply(BaseReplyBean<WctAccessToken> reply) {
		this.reply = reply;
	}

}
