package com.miyzh.wechatshop.payUtil.service;

import java.util.List;
import java.util.Map;

import com.miyzh.wechatshop.payUtil.bean.ShopOrder;
import com.miyzh.wechatshop.payUtil.bean.ShopOrderItem;
import com.miyzh.wechatshop.payUtil.toolBean.BeanTools;

public interface OrderItemService {

	   public void saveOrderItems(List<ShopOrderItem> shopOrderItems);
	   
	   public List<ShopOrderItem> findByOrderId(int orderId,Long groupId);
}
