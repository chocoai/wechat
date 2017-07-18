package com.miyzh.wechatshop.payUtil.dao;


import java.util.List;
import java.util.Map;

import com.miyzh.framework.base.dao.IBaseDao;
import com.miyzh.wechatshop.payUtil.bean.ShopOrderItem;

public interface OrderItemDao extends IBaseDao<ShopOrderItem> {

	public List<ShopOrderItem> findByOrderId(Map<String,Object> map);
	
	public void saveOrderItems(List<ShopOrderItem> list);
}
