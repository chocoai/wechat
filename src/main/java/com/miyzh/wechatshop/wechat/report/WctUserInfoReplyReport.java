package com.miyzh.wechatshop.wechat.report;

import com.miyzh.framework.base.action.reply.BaseReplyBean;
import com.miyzh.framework.base.action.reply.BaseReplyReport;
import com.miyzh.wechatshop.wechat.bean.WctUserInfo;

/**
 * 文件名：WctUserInfoReplyReport<br>
 * 描述: 微信公众号获取用户信息回复报文<br>
 * 修改人: wumingdi<br>
 * 修改时间： 2016年7月6日 下午3:24:21 <br>
 */
public class WctUserInfoReplyReport extends BaseReplyReport {
	private BaseReplyBean<WctUserInfo> reply;

	public BaseReplyBean<WctUserInfo> getReply() {
		return reply;
	}

	public void setReply(BaseReplyBean<WctUserInfo> reply) {
		this.reply = reply;
	}

}
