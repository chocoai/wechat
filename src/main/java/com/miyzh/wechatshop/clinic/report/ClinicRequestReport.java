package com.miyzh.wechatshop.clinic.report;

import com.miyzh.framework.base.action.request.BaseRequestReport;

/**
 * 文件名：UserRequestReport<br>
 * 描述: 注册请求参数<br>
 * 修改人: wumingdi<br>
 * 修改时间： 2016年7月9日 下午1:13:45 <br>
 */
public class ClinicRequestReport extends BaseRequestReport {

	private ClinicReport commandinfo;

	public ClinicReport getCommandinfo() {
		return commandinfo;
	}

	public void setCommandinfo(ClinicReport commandinfo) {
		this.commandinfo = commandinfo;
	}

}
