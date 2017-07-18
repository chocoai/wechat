package com.miyzh.wechatshop.payUtil.service;

import java.util.List;
import java.util.Map;

import com.miyzh.wechatshop.payUtil.bean.ShopOrder;
import com.miyzh.wechatshop.payUtil.bean.ShopSendLog;
import com.miyzh.wechatshop.payUtil.toolBean.BeanTools;

public interface SendLogService {

	
	 public  Long saveSendLog(ShopSendLog sendLog); 
}
