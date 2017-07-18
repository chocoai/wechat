package com.miyzh.wechatshop.wechat.service;

import com.miyzh.wechatshop.wechat.bean.RequestInfo;


public interface IWeChatCommonService {

	/**
	 * <pre>
	 * 公用处理类.
	 * </pre>
	 *
	 * @param info
	 *            the info
	 * @return the string
	 * @author 张涛 2014-4-28
	 * @throws Exception 
	 */
	public String commonHandler(RequestInfo info) throws Exception;

}
