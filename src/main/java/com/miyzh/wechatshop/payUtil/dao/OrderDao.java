package com.miyzh.wechatshop.payUtil.dao;


import java.util.List;
import java.util.Map;

import com.miyzh.framework.base.dao.IBaseDao;
import com.miyzh.wechatshop.payUtil.bean.ShopOrder;

public interface OrderDao extends IBaseDao<ShopOrder> {
   public List<ShopOrder> findMemberByType(Map<String,Object> map);
   
   public int saveOrder(ShopOrder shopOrder);
   
   public void orderConfirm(String orderId);
   
   public void orderPay(String orderId);
}
