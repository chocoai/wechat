package com.miyzh.wechatshop.payUtil.service;

import java.util.List;
import java.util.Map;

import com.miyzh.wechatshop.payUtil.bean.ShopOrder;
import com.miyzh.wechatshop.payUtil.toolBean.BeanTools;

public interface OrderService {

	
	//下单保存
	public BeanTools createOrder(Map<String,Object> map);
	
	
	   public List<ShopOrder> findMemberByType(Long memberId,String status,String paymenStatus,String shippingStatus);
	   
	   public ShopOrder findByOrderId(String orderId,String payFlag);
	   
	   public ShopOrder findById(String orderId);
	   
	 public String  savePackage(String totalFeel, String orderId,String openId);
	 
	 
	 public ShopOrder payResult(String orderId,String openId,String products,String addressId);
	 
	 public void orderConfirm(String orderId);
	 
	 public void orderPay(String orderId);
}
