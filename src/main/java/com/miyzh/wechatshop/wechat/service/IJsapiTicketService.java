package com.miyzh.wechatshop.wechat.service;

import com.miyzh.wechatshop.wechat.report.WctJsapiTicketReply;

public interface IJsapiTicketService {

	/**
	 * <pre>
	 * the toJsapiTicket method for
	 * 获取js的票据
	 * </pre>
	 *
	 * @param appid
	 * @param appsec
	 * @return
	 * @throws Exception
	 * @author zhangtao 2016年7月22日
	 */
	public WctJsapiTicketReply toJsapiTicket(String appid, String appsec,String url) throws Exception;

}
