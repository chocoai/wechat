package com.miyzh.wechatshop.user.report;

import com.miyzh.framework.base.action.request.BaseRequestReport;

/**
 * 文件名： UserRequestReport.java<br>
 * 描述: 用户请求参数<br>
 * 修改人: wumingdi<br>
 * 修改时间： 2016年7月13日 <br>
 */
public class UserRequestReport extends BaseRequestReport {

	private UserReport commandinfo;

	public UserReport getCommandinfo() {
		return commandinfo;
	}

	public void setCommandinfo(UserReport commandinfo) {
		this.commandinfo = commandinfo;
	}

}
