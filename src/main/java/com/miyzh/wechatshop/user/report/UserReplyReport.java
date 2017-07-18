package com.miyzh.wechatshop.user.report;

import com.miyzh.framework.base.action.reply.BaseReplyReport;

/**
 * 文件名： UserReplyReport.java<br>
 * 描述: 用户回复参数<br>
 * 修改人: wumingdi<br>
 * 修改时间： 2016年7月13日 <br>
 */
public class UserReplyReport extends BaseReplyReport {

	private UserReport reply;

	public UserReport getReply() {
		return reply;
	}

	public void setReply(UserReport reply) {
		this.reply = reply;
	}

}
