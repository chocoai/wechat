package com.miyzh.wechatshop.wechat.service;

import com.miyzh.wechatshop.wechat.bean.RequestInfo;


/**
 * <pre>
 * 微信消息处理接口.
 * </pre>
 *
 * @author 张涛 2014-4-28
 */
public interface IWeChatService {

	/**
	 * <pre>
	 * 处理微信消息.
	 * </pre>
	 *
	 * @param req
	 *            the req
	 * @author 张涛 2014-4-28
	 * @throws Exception 
	 */
	public Object handler(RequestInfo req) throws Exception;

}
