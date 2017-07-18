package com.miyzh.wechatshop.payUtil.service.impl;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.miyzh.framework.util.CacheUtil;
import com.miyzh.wechatshop.payUtil.bean.CoreMember;
import com.miyzh.wechatshop.payUtil.bean.OrderWeixinNotify;
import com.miyzh.wechatshop.payUtil.bean.ProductInfo;
import com.miyzh.wechatshop.payUtil.bean.ShopMemberAddress;
import com.miyzh.wechatshop.payUtil.bean.ShopOrder;
import com.miyzh.wechatshop.payUtil.bean.ShopOrderItem;
import com.miyzh.wechatshop.payUtil.bean.ShopOrderTrail;
import com.miyzh.wechatshop.payUtil.bean.ShopSendLog;
import com.miyzh.wechatshop.payUtil.dao.CoreMemberDao;
import com.miyzh.wechatshop.payUtil.dao.MemberAddressDao;
import com.miyzh.wechatshop.payUtil.dao.OrderDao;
import com.miyzh.wechatshop.payUtil.dao.OrderItemDao;
import com.miyzh.wechatshop.payUtil.dao.OrderWeixinNotifyDao;
import com.miyzh.wechatshop.payUtil.dao.ProductDao;
import com.miyzh.wechatshop.payUtil.dao.ShopOrderTrailDao;
import com.miyzh.wechatshop.payUtil.dao.ShopSendLogDao;
import com.miyzh.wechatshop.payUtil.pay.com.UnifiedOrder;
import com.miyzh.wechatshop.payUtil.pay.com.WxPayDto;
import com.miyzh.wechatshop.payUtil.pay.utils.TenpayUtil;
import com.miyzh.wechatshop.payUtil.service.OrderItemService;
import com.miyzh.wechatshop.payUtil.service.OrderService;
import com.miyzh.wechatshop.payUtil.service.OrderTrailService;
import com.miyzh.wechatshop.payUtil.service.ProductService;
import com.miyzh.wechatshop.payUtil.service.SendLogService;
import com.miyzh.wechatshop.payUtil.toolBean.BeanTools;

@Service("orderItemService")
public class OrderItemServiceImpl implements OrderItemService {
 
	@Autowired
	private OrderItemDao orderItemDao;

	@Override
	public void saveOrderItems(List<ShopOrderItem> shopOrderItems) {
		// TODO Auto-generated method stub
		orderItemDao.saveOrderItems(shopOrderItems);
	}

	@Override
	public List<ShopOrderItem> findByOrderId(int orderId,Long groupId) {
		Map<String, Object> mapItem = null;
		List<ShopOrderItem> shopOrderItems=null;
		mapItem=new HashMap<String, Object>();
		mapItem.put("orderId", orderId);
		mapItem.put("groupId", groupId);
	     shopOrderItems=new ArrayList<>();
	   shopOrderItems=orderItemDao.findByOrderId(mapItem);
	   
	   return shopOrderItems;
	}
	
	
	
}
