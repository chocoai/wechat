package com.miyzh.wechatshop.clinic.report;

import java.util.List;

import com.miyzh.framework.base.action.reply.BaseReplyReport;

/**
 * 文件名：UserReplyReport<br>
 * 描述: 注册回复参数<br>
 * 修改人: wumingdi<br>
 * 修改时间： 2016年7月9日 下午1:27:03 <br>
 */
public class ClinicReplyReport extends BaseReplyReport {

	private List<ClinicReport> reply;

	public List<ClinicReport> getReply() {
		return reply;
	}

	public void setReply(List<ClinicReport> reply) {
		this.reply = reply;
	}

}
