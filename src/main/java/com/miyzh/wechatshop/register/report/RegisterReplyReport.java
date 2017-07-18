package com.miyzh.wechatshop.register.report;

import com.miyzh.framework.base.action.reply.BaseReplyReport;

/**
 * 文件名：UserReplyReport<br>
 * 描述: 注册回复参数<br>
 * 修改人: wumingdi<br>
 * 修改时间： 2016年7月9日 下午1:27:03 <br>
 */
public class RegisterReplyReport extends BaseReplyReport {

	private RegisterReport reply;

	public RegisterReport getReply() {
		return reply;
	}

	public void setReply(RegisterReport reply) {
		this.reply = reply;
	}

}
