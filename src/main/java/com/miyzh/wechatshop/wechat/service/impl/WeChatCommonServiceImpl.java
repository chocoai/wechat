package com.miyzh.wechatshop.wechat.service.impl;

import java.io.IOException;

import org.springframework.stereotype.Service;

import com.miyzh.framework.base.util.BeanFactoryUtil;
import com.miyzh.wechatshop.wechat.bean.AbstractMessage;
import com.miyzh.wechatshop.wechat.bean.RequestInfo;
import com.miyzh.wechatshop.wechat.service.IWeChatCommonService;
import com.miyzh.wechatshop.wechat.service.IWeChatService;
import com.miyzh.wechatshop.wechat.util.IMessageTypeConstant;
import com.miyzh.wechatshop.wechat.util.WeixinMessageUtils;

/**
 * <pre>
 * 微信处理服务.
 * </pre>
 * 
 * @author 张涛 2014-4-28
 */
@Service
public class WeChatCommonServiceImpl implements IWeChatCommonService {

	/** The service. */
	private IWeChatService service;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.digitalchina.ldp.app.wechat.service.IWeChatCommonService#commonHandler
	 * (com.digitalchina.ldp.app.wechat.bean.RequestInfo)
	 */
	@Override
	public String commonHandler(RequestInfo info) throws Exception {
		String msgType = info.getMsgType();
		String returnStr = "";
		String className = null;

		// 文本消息
		if (msgType.equals(IMessageTypeConstant.REQ_MESSAGE_TYPE_TEXT)) {
			className = "WTMessageHandler";
		}
		// 图片消息
		else if (msgType.equals(IMessageTypeConstant.REQ_MESSAGE_TYPE_IMAGE)) {
			// respContent = "您发送的是图片消息！";
		}
		// 地理位置消息
		else if (msgType.equals(IMessageTypeConstant.REQ_MESSAGE_TYPE_LOCATION)) {
			// respContent = "您发送的是地理位置消息！";
		}
		// 链接消息
		else if (msgType.equals(IMessageTypeConstant.REQ_MESSAGE_TYPE_LINK)) {
			// respContent = "您发送的是链接消息！";
		}
		// 音频消息
		else if (msgType.equals(IMessageTypeConstant.REQ_MESSAGE_TYPE_VOICE)) {
			// respContent = "您发送的是音频消息！";
		}
		// 事件推送
		else if (msgType.equals(IMessageTypeConstant.REQ_MESSAGE_TYPE_EVENT)) {
			// 事件类型
			String eventType = info.getEvent();
			// 订阅
			if (eventType.equals(IMessageTypeConstant.EVENT_TYPE_SUBSCRIBE)) {
				// 订阅
				className = "SubscribeServiceImpl";
			}
			// 取消订阅
			else if (eventType
					.equals(IMessageTypeConstant.EVENT_TYPE_UNSUBSCRIBE)) {
				// 取消订阅
				className = "CancelSubscribeServiceImpl";

			}
			// 自定义菜单点击事件
			else if (eventType.equals(IMessageTypeConstant.EVENT_TYPE_CLICK)) {
				// 自定义菜单权没有开放，暂不处理该类消息

			}
		}
		if (className != null) {
			service = (IWeChatService) BeanFactoryUtil
					.getbeanFromWebContext(className);
		}
		Object obj = null;
		if (service != null && null != className) {
			// 返回一个Bean
			obj = service.handler(info);
			AbstractMessage message = (AbstractMessage) obj;
			message.setCreateTime(System.currentTimeMillis());
			message.setFromUserName(info.getToUserName());
			message.setToUserName(info.getFromUserName());
			// 反射这个bean成xmlString
			try {
				returnStr = WeixinMessageUtils.getObjectToXml(message);
			} catch (IOException e) {
				// TODO 异常处理
				e.printStackTrace();
			}
		}

		return returnStr;
	}
}
