package com.miyzh.wechatshop.wechat.service.impl;

import org.springframework.stereotype.Service;

import com.miyzh.framework.util.PropertiesUtil;
import com.miyzh.wechatshop.wechat.bean.RequestInfo;
import com.miyzh.wechatshop.wechat.bean.TextMessage;
import com.miyzh.wechatshop.wechat.service.IWeChatService;

@Service("WTMessageHandler")
public class WTMessageHandler implements IWeChatService {

	@Override
	public Object handler(RequestInfo req) throws Exception {
		TextMessage message = new TextMessage();
		message.setContent(PropertiesUtil
				.getPropertyValue("wechat_subscribe"));
		return message;
	}

}
