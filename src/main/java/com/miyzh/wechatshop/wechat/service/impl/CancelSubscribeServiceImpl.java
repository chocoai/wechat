package com.miyzh.wechatshop.wechat.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.miyzh.wechatshop.user.bean.ThirdUserInfoBean;
import com.miyzh.wechatshop.user.service.IThirdUserInfoService;
import com.miyzh.wechatshop.wechat.bean.RequestInfo;
import com.miyzh.wechatshop.wechat.bean.TextMessage;
import com.miyzh.wechatshop.wechat.service.ICancelSubscribeService;
import com.miyzh.wechatshop.wechat.service.IWeChatService;

/**
 * 文件名： CancelSubscribeServiceImpl.java<br>
 * 描述: 取消订阅<br>
 * 修改人: wumingdi<br>
 * 修改时间： 2016年7月12日 <br>
 */
@Service("CancelSubscribeServiceImpl")
public class CancelSubscribeServiceImpl implements ICancelSubscribeService,
		IWeChatService {

	@Autowired
	private IThirdUserInfoService thirdUserInfoService;

	@Override
	public Object cancelSubscribe(RequestInfo req) throws Exception {

		// 微信id
		String openId = req.getFromUserName();

		// 第三方账户
		ThirdUserInfoBean thirdUserInfo = new ThirdUserInfoBean();
		thirdUserInfo.setOpenId(openId);
		thirdUserInfo.setIfFollow(0);

		// 更新
		thirdUserInfoService.updateThirdUserInfo(thirdUserInfo);

		TextMessage message = new TextMessage();

		return message;

	}

	@Override
	public Object handler(RequestInfo req) throws Exception{
		return this.cancelSubscribe(req);
	}

}
