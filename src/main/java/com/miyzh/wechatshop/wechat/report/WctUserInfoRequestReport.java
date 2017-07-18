package com.miyzh.wechatshop.wechat.report;

import com.miyzh.framework.base.action.request.BaseRequestReport;
import com.miyzh.wechatshop.wechat.bean.WctUserInfo;

/**
 * 文件名：WctUserInfoRequestReport<br>
 * 描述: 微信公众号获取用户信息请求参数<br>
 * 修改人: wumingdi<br>
 * 修改时间： 2016年7月6日 下午3:22:47 <br>
 */
public class WctUserInfoRequestReport extends BaseRequestReport {
	private WctUserInfo commandinfo;

	public WctUserInfo getCommandinfo() {
		return commandinfo;
	}

	public void setCommandinfo(WctUserInfo commandinfo) {
		this.commandinfo = commandinfo;
	}

}
