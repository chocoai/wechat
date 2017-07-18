package com.miyzh.wechatshop.payUtil.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.miyzh.wechatshop.payUtil.bean.OrderWeixinNotify;
import com.miyzh.wechatshop.payUtil.bean.ShopSendLog;
import com.miyzh.wechatshop.payUtil.dao.OrderWeixinNotifyDao;
import com.miyzh.wechatshop.payUtil.dao.ShopSendLogDao;
import com.miyzh.wechatshop.payUtil.service.OrderPayService;
import com.miyzh.wechatshop.payUtil.service.SendLogService;

@Service("orderPayService")
public class OrderPayServiceImpl implements OrderPayService {
 
	@Autowired
	private OrderWeixinNotifyDao orderWeixinNotifyDao;

	@Override
	public void saveNotify(OrderWeixinNotify OrderWeixinNotify) {
		orderWeixinNotifyDao.create(OrderWeixinNotify);
		
	}

	@Override
	public void update(OrderWeixinNotify OrderWeixinNotify) {
		orderWeixinNotifyDao.update(OrderWeixinNotify);
		
	}

	@Override
	public OrderWeixinNotify findByOrderId(String orderId) {
		// TODO Auto-generated method stub
		return orderWeixinNotifyDao.findById(orderId);
	}

	

	
}
