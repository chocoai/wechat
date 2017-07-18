package com.miyzh.wechatshop.payUtil.service;


import com.miyzh.wechatshop.payUtil.bean.OrderWeixinNotify;

public interface OrderPayService {

	public void  saveNotify(OrderWeixinNotify OrderWeixinNotify);

	public void update(OrderWeixinNotify OrderWeixinNotify);

	public OrderWeixinNotify findByOrderId(String orderId);
}
