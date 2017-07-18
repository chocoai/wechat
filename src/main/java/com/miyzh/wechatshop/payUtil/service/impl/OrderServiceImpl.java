package com.miyzh.wechatshop.payUtil.service.impl;


import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.esotericsoftware.minlog.Log;
import com.miyzh.clinic.api.ClinicService;
import com.miyzh.clinic.vo.ClinicVo;
import com.miyzh.framework.util.CacheUtil;
import com.miyzh.wechatshop.groupbuy.bean.GroupBuy;
import com.miyzh.wechatshop.groupbuy.service.IWcGroupBuyService;
import com.miyzh.wechatshop.payUtil.bean.OrderWeixinNotify;
import com.miyzh.wechatshop.payUtil.bean.ProductInfo;
import com.miyzh.wechatshop.payUtil.bean.ShopMemberAddress;
import com.miyzh.wechatshop.payUtil.bean.ShopOrder;
import com.miyzh.wechatshop.payUtil.bean.ShopOrderItem;
import com.miyzh.wechatshop.payUtil.bean.ShopOrderTrail;
import com.miyzh.wechatshop.payUtil.bean.ShopSendLog;
import com.miyzh.wechatshop.payUtil.bean.TCodeByMember;
import com.miyzh.wechatshop.payUtil.dao.CoreMemberDao;
import com.miyzh.wechatshop.payUtil.dao.MemberAddressDao;
import com.miyzh.wechatshop.payUtil.dao.OrderDao;
import com.miyzh.wechatshop.payUtil.pay.com.UnifiedOrder;
import com.miyzh.wechatshop.payUtil.pay.com.WxPayDto;
import com.miyzh.wechatshop.payUtil.pay.utils.TenpayUtil;
import com.miyzh.wechatshop.payUtil.service.OrderItemService;
import com.miyzh.wechatshop.payUtil.service.OrderPayService;
import com.miyzh.wechatshop.payUtil.service.OrderService;
import com.miyzh.wechatshop.payUtil.service.OrderTrailService;
import com.miyzh.wechatshop.payUtil.service.ProductService;
import com.miyzh.wechatshop.payUtil.service.SendLogService;
import com.miyzh.wechatshop.payUtil.service.TCodeByMemberService;
import com.miyzh.wechatshop.payUtil.service.UserBuyHistoryService;
import com.miyzh.wechatshop.payUtil.toolBean.BeanTools;
import com.miyzh.wechatshop.user.bean.UserBean;
import com.miyzh.wechatshop.user.service.IUserService;
import com.miyzh.wechatshop.usercenter.bean.UserCenterResultBean;
import com.miyzh.wechatshop.usercenter.service.UserCenterService;

@Service("OrderService")
public class OrderServiceImpl implements OrderService {
	@Autowired
	private IWcGroupBuyService iwcGroupBuyService;
	@Autowired
	private UserBuyHistoryService userBuyHistoryService;
	
	@Autowired
	private UserCenterService userCenterService;
	
	@Autowired
	private TCodeByMemberService tcodeByMemberService;
	
	@Autowired 
	private OrderTrailService orderTrailService;
	
	@Autowired
    private	OrderDao orderDao;
	
	@Autowired
	private OrderPayService orderPayService;
	
	@Autowired
	private IUserService iuserService;
	
	@Autowired
	private OrderItemService orderItemService;
	
	@Autowired 
	private MemberAddressDao memberAddressDao;
	
	
	@Autowired
	private ProductService productService;
	
	@Autowired
	private SendLogService sendLogService;
	

	@SuppressWarnings("unchecked")
	@Override
	public BeanTools createOrder(Map<String, Object> map) {
		BeanTools beanTools=new BeanTools();
		ShopOrder shopOrder=new ShopOrder();
		String memberId= (String) map.get("memberId");
		String openId= (String) map.get("openId");
		String addressId= (String) map.get("addressId");
		String total= (String) map.get("total");
		String groupID=(String) map.get("groupID");
		String products=(String) map.get("products");
		String userKey=(String) map.get("userKey");
		
		StringBuffer keyBuffer1 = CacheUtil.getCachekeyMethodPrx(CacheUtil.cacheKey.product.KEY_PRODUCTORDER, memberId,
				"createOrder", new Object[] { groupID,addressId,products,openId });
		Object object1 = CacheUtil.getObjectValue(keyBuffer1.toString());
		if (object1 == null) {
		ShopMemberAddress shopMemberAddress=memberAddressDao.findById(addressId);
		if(null!=userKey&&!"".equals(userKey)){
		UserBean userBean=iuserService.findByUserId(userKey);
		if(userBean!=null&&userBean.getClinicId()!=null){
			UserCenterResultBean clinicVo = userCenterService.queryClinicbyClinicId(userBean.getClinicId());
			if("0".equals(clinicVo.getResultCode())){
				Map<String, Object> mapTmp = (Map<String, Object>) clinicVo.getResultObject();
				if(null!=mapTmp){
				//shopOrder.setClinicName(mapTmp.get("clinicname").toString());
				//shopOrder.setClinicCode(mapTmp.get("clinicCode").toString());
				//shopOrder.setTgCode(mapTmp.get("tgCode").toString());  //推广码
				}
			}
		}
		}
		
		shopOrder.setMember(Long.valueOf(memberId));
		shopOrder.setWebsite(Long.valueOf(1));
		shopOrder.setPayment(Long.valueOf(1));
		shopOrder.setShipping(Long.valueOf(3));
		shopOrder.setIp("");
		shopOrder.setDiscussStatus(0);
		shopOrder.setComments("");
		shopOrder.setStatus(1);//未确认
		shopOrder.setPrintSatus(0);
		shopOrder.setShippingStatus(1);//未发货
		shopOrder.setPaymentStatus(1);//未支付
		if(null!=shopMemberAddress){
		shopOrder.setReceiveName(shopMemberAddress.getUsername());
		shopOrder.setReceiveAddress(shopMemberAddress.getDetailaddress());
		shopOrder.setReceiveMobile(shopMemberAddress.getTel());
		shopOrder.setReceivePhone(shopMemberAddress.getAreaCode()!=null?shopMemberAddress.getAreaCode()+"-"+shopMemberAddress.getPhone():shopMemberAddress.getPhone());
		shopOrder.setReceiveZip(shopMemberAddress.getPostCode());
		}

		shopOrder.setOrderType("3");//订单类型
		shopOrder.setScore(0);
		Long date=new Date().getTime()+Long.valueOf(memberId);
		shopOrder.setCode(date);
      
		shopOrder.setTotal(Double.valueOf(total));
		shopOrder.setWeight((double)0);
		shopOrder.setProductPrice(Double.valueOf(total));
		shopOrder.setFreight(Double.valueOf(0));
		shopOrder.setProductName("");
		shopOrder.setGroupBuy(Long.valueOf(groupID));
		
		// 短信内容
		ShopSendLog sendLog = new ShopSendLog();
		sendLog.setSendType(1); // 订单确认
		sendLog.setContent("尊敬的客户，您的订单" + date + "已生效，我们会尽快为您安排发货。请在收到商品时仔细确认货物与订单一致。如有疑问请拨打4008651166转2咨询。");
		sendLog.setStatus(0); // 为发送
		sendLog.setMegEss("");
		sendLog.setSendNum(0);// 默认发送0
		if(null!=shopMemberAddress){
		sendLog.setPhone(shopMemberAddress.getTel());
		}
		sendLog.setSendDate(new Date());
		sendLog.setCredateTmp(new Date());
		
		Long sendId=sendLogService.saveSendLog(sendLog);
		System.out.println(sendId);
		shopOrder.setSendlog(sendId);
		orderDao.saveOrder(shopOrder);
		int orderId=shopOrder.getOrderId();
		
		// 订单操作时间轨迹表
		ShopOrderTrail shopOrderTrail = new ShopOrderTrail();
		shopOrderTrail.setOperateTime(new Date());
		shopOrderTrail.setOperateType("1");
		shopOrderTrail.setMember(Long.valueOf(memberId));
		shopOrderTrail.setOrder(Long.valueOf(orderId));
		orderTrailService.creta(shopOrderTrail);
		
		//订单明细
		List<ShopOrderItem> orderItems = new ArrayList<>();
		ShopOrderItem orderItem = null;
		String[] productList = products.split(","); // 多件商品拆分
		ProductInfo productInfoTmp = null;
		String[] product = null;
		for (int i = 0; i < productList.length; i++) {
			orderItem = new ShopOrderItem();
			product = productList[i].split("@"); // 商品ID与商品数量拆分
			StringBuffer keyBuffer = CacheUtil.getCachekeyMethodPrx(CacheUtil.cacheKey.product.KEY_PRODUCT, product[0],
					Thread.currentThread().getStackTrace()[1].getMethodName(), new Object[] { groupID });
			Object object = CacheUtil.getObjectValue(keyBuffer.toString());
			if (object == null) {
				productInfoTmp = productService.findByProductId( product[0], groupID);
				CacheUtil.put(keyBuffer.toString(), productInfoTmp);
				productInfoTmp.setProductCount(product[1]);
			} else {
				productInfoTmp = (ProductInfo) object;
				productInfoTmp.setProductCount(product[1]);
			}
			// 转存为订单字表
			orderItem.setOrdeR(Long.valueOf(orderId));
			orderItem.setProduct(Long.valueOf(product[0]));
			orderItem.setWebsite(Long.valueOf(1));
			orderItem.setCount(Integer.valueOf(product[1]));
			orderItem.setSalePrice(Double.valueOf(productInfoTmp.getGroupPrice()));
			orderItems.add(orderItem);
		}

		orderItemService.saveOrderItems(orderItems);
		beanTools.setOrderId(String.valueOf(orderId));
		beanTools.setMemberid(String.valueOf(memberId));
		beanTools.setTotal(total);
		beanTools.setOpendid(openId);
		beanTools.setProducts(products);
		beanTools.setAddressId(addressId);
		
		
		tcodeByMemberService.saveTCode(openId, groupID,String.valueOf(orderId)); //二维码生成保存缓存
		
		userBuyHistoryService.saveUserBuyHistory(String.valueOf(shopOrder.getGroupBuy()),userKey,openId); //保存购买关系记录缓存
		
		CacheUtil.put(keyBuffer1.toString(), beanTools,300);
		} else {
			beanTools = (BeanTools) object1;
		}
		
		
		return beanTools;
	}
	
	
	/*
	 * 公共方法
	 * 返回支付id
	 */
	@Override
	public String savePackage(String totalFeel, String orderId,String openId) {
		String urlCode = "";
		String endDate = "";// 订单失效时间
		WxPayDto tpWxPay1 = new WxPayDto();
		UnifiedOrder unifiedOrder = new UnifiedOrder();
		tpWxPay1.setBody("明医商城订单");
		tpWxPay1.setTotalFee(totalFeel);
		tpWxPay1.setOpenId(openId);
		tpWxPay1.setSpbillCreateIp("");
		// 扫码支付 返回二维码
		OrderWeixinNotify orNotify = null;
		orNotify = orderPayService.findByOrderId(orderId);
		String shop_code = ""; // 商城与微信订单号

		if (orNotify != null) {
			String Date = getCurrTime("2");// 当前时间
			if (orNotify.getSign().compareTo(Date) > 0) {
				tpWxPay1.setOrderId(orNotify.getOrderCode());
				tpWxPay1.setEndDate(orNotify.getSign());
			} else {
				shop_code = TenpayUtil.getNonceStr(); // 商城与微信订单号
				endDate = getCurrTime("1");// 订单失效时间
				orNotify.setOrderCode(shop_code);
				orNotify.setSign(endDate);
				orNotify.setErrCodeDes(orNotify.getErrCodeDes()+"@"+orNotify.getOrderCode());  //记录与腾讯所有交互发订单号
				tpWxPay1.setOrderId(shop_code);
				tpWxPay1.setEndDate(endDate);
				orderPayService.update(orNotify); // 更新商城订单号
			}

		} else {
			orNotify = new OrderWeixinNotify();
			endDate = getCurrTime("1");// 订单失效时间
			shop_code = TenpayUtil.getNonceStr(); // 商城与微信订单号
			orNotify.setCodeId(orderId);
			orNotify.setNotifyFlag(0);
			orNotify.setSign(endDate);
			orNotify.setOrderType(Integer.valueOf(3)); // 1线上 2线下3微信
			orNotify.setCretaTime(new Date());
			orNotify.setOrderCode(shop_code);
			orNotify.setErrCodeDes(orNotify.getOrderCode());
			tpWxPay1.setOrderId(shop_code);
			tpWxPay1.setEndDate(endDate);
			orderPayService.saveNotify(orNotify); // 记录通知记录
		}

		urlCode = unifiedOrder.getPackage(tpWxPay1);

		return urlCode;

	}
	
	
	 
		/**
		 * 获取当前时间 yyyyMMddHHmmss
		 * @return String
		 */ 
		public static String getCurrTime(String flag) {
			 Calendar calendar = Calendar.getInstance();
			 if("1".equals(flag)){
		     calendar.add(Calendar.SECOND, 120);
			 }
	        return new SimpleDateFormat("yyyyMMddHHmmss").format(calendar.getTime());

		}


		@Override
		public List<ShopOrder> findMemberByType(Long memberId, String status, String paymenStatus, String shippingStatus) {
			Map<String, Object> map = new HashMap<String, Object>();
			List<ShopOrder> shopOrder=null;
			map.put("memberId", memberId);
			map.put("status", status);
			map.put("paymenStatus", paymenStatus);
			map.put("shippingStatus", shippingStatus);
			shopOrder=orderDao.findMemberByType(map);
			List<ShopOrderItem> shopOrderItems=null;
			if(shopOrder!=null&&shopOrder.size()>0){
				for(int i=0;i<shopOrder.size();i++){
				shopOrderItems=new ArrayList<>();
				shopOrderItems=orderItemService.findByOrderId(shopOrder.get(i).getOrderId(),shopOrder.get(i).getGroupBuy());
				shopOrder.get(i).setShopOrderItem(shopOrderItems);
				}
			}
			
			return shopOrder;
		}


		//根据订单Id查询订单信息
		@Override
	public ShopOrder findByOrderId(String orderId,String payFlag) {
		ShopOrder shopOrder = null;
		GroupBuy groupBuy=null;
		shopOrder = orderDao.findById(orderId);
		List<ShopOrderItem> shopOrderItems = null;
		if (shopOrder != null) {
			shopOrderItems = new ArrayList<>();
			shopOrderItems = orderItemService.findByOrderId(shopOrder.getOrderId(),shopOrder.getGroupBuy());
			shopOrder.setShopOrderItem(shopOrderItems);
			
			if(!"".equals(payFlag)){
				 groupBuy=iwcGroupBuyService.findById(shopOrder.getGroupBuy().toString());
				 if(groupBuy!=null){
					 shopOrder.setGroupName(groupBuy.getTitle());
					 shopOrder.setPiceUrl(groupBuy.getWtImgurl());
				 }
			}
		}

		
		return shopOrder;
	}


		@Override
	public ShopOrder payResult(String orderId, String openId, String products, String addressId) {
		ShopOrder shopOrder = findByOrderId(orderId,"");
		if(shopOrder.getPaymentStatus()==1){
		orderPay(orderId);//支付状态回写
		}
//		TCodeByMember tcodeByMember=tcodeByMemberService.findByMemberIdGroupId(shopOrder.getMember(), shopOrder.getGroupBuy());
//		if(tcodeByMember!=null){
//		shopOrder.setTcodeId(String.valueOf(tcodeByMember.getId()));
//		}
		StringBuffer keyBuffer1 = CacheUtil.getCachekeyMethodPrx(CacheUtil.cacheKey.product.KEY_PRODUCTORDER,
				String.valueOf(shopOrder.getMember()), "createOrder",
				new Object[] { String.valueOf(shopOrder.getGroupBuy()), addressId, products, openId });
		try {
			Object object1 = CacheUtil.getObjectValue(keyBuffer1.toString());
			if (object1 != null) {
			CacheUtil.removeByKey(CacheUtil.cacheKey.product.KEY_PRODUCTORDER + "_" + keyBuffer1.toString());
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return shopOrder;
	}


		@Override
		public void orderConfirm(String orderId) {
		ShopOrder shopOrder=	orderDao.findById(orderId);
		orderDao.orderConfirm(orderId);  //订单确认
		ShopOrderTrail shopOrderTrail=new ShopOrderTrail();
		shopOrderTrail.setMember(shopOrder.getMember());
		shopOrderTrail.setOperateType("6");
		shopOrderTrail.setOrder(Long.valueOf(shopOrder.getOrderId()));
		orderTrailService.creta(shopOrderTrail);
		}
		
		

		@Override
		public void orderPay(String orderId) {
		orderDao.orderPay(orderId);  //订单支付
		}


		@Override
		public ShopOrder findById(String orderId) {
			// TODO Auto-generated method stub
			return orderDao.findById(orderId);
		}
		
	
	
}
