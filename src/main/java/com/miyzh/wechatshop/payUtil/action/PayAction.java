package com.miyzh.wechatshop.payUtil.action;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.miyzh.framework.base.Constant;
import com.miyzh.framework.base.action.BaseAction;
import com.miyzh.framework.base.action.reply.BaseReplyBean;
import com.miyzh.framework.base.action.reply.BaseReplyResult;
import com.miyzh.wechatshop.payUtil.bean.ShopOrder;
import com.miyzh.wechatshop.payUtil.service.OrderService;
import com.miyzh.wechatshop.payUtil.service.ProductService;
import com.miyzh.wechatshop.payUtil.toolBean.BeanTools;
import com.miyzh.wechatshop.user.bean.ThirdUserInfoBean;
import com.miyzh.wechatshop.user.service.IThirdUserInfoService;

@Controller
@RequestMapping("/payaction")
public class PayAction extends BaseAction {


	@Autowired
	private ProductService productService;
	
	@Autowired
	private OrderService orderService;
	
	@Autowired
	private IThirdUserInfoService thirdUserInfoService;
	
	@RequestMapping("/payDetail")
	@ResponseBody
	public String payDetail(HttpServletRequest request, HttpServletResponse response) {
		String openId=null;
		String groupID=null;
		String products=null;
		String addressId=null;
		String recommenduserid=null;
		BaseReplyBean<BeanTools> reply = new BaseReplyBean<BeanTools>();
		BaseReplyResult baseReplyResult = new BaseReplyResult();
		try {
			String jsonObjectStr = parseRequestReportToString(request, response);
			JSONObject jsonObject = getJsonObject(jsonObjectStr);
			openId = jsonObject.getJSONObject("commandinfo").getString("openid");
			groupID = jsonObject.getJSONObject("commandinfo").getString("groupid");
			products = jsonObject.getJSONObject("commandinfo").getString("products");
			addressId = jsonObject.getJSONObject("commandinfo").getString("addressid");
			recommenduserid = jsonObject.getJSONObject("commandinfo").getString("recommenduserid");
			if(recommenduserid!=null){
			recommenduserid=productService.findRecommendUserid(groupID,openId,recommenduserid);
			}
			
			ThirdUserInfoBean thirdUser=thirdUserInfoService.findByOpenIdKey(openId, "");
			if(thirdUser!=null&&thirdUser.getMemberID()!=null){
			 BeanTools beanTools = productService.findByParentId(products, groupID,thirdUser.getMemberID(),addressId);
			baseReplyResult.setCode(Constant.ResultCode.SUCCESS);
			baseReplyResult.setBusinessCode(Constant.ResultCode.SUCCESS);
			reply.setResult(baseReplyResult);
			beanTools.setMemberid(thirdUser.getMemberID());
			reply.setObj(beanTools);
			}else{
				baseReplyResult.setCode(Constant.ResultCode.RESULT_TWO);
				baseReplyResult.setBusinessCode(Constant.ResultCode.RESULT_TWO);
				reply.setResult(baseReplyResult);
			}
		} catch (Exception e) {
			log.error("In the method PayAction.payDetail(HttpServletRequest request,HttpServletResponse response) exists error!",e);
			baseReplyResult.setCode(Constant.ResultCode.FILURE);
			baseReplyResult.setBusinessCode(Constant.ResultCode.FILURE);
			reply.setResult(baseReplyResult);
		}
		responseClient(response, JSON.toJSONString(reply));
		return null;

	}
	
	
	@RequestMapping("/orderSave")
	public String orderSave(HttpServletRequest request, HttpServletResponse response) {
		BeanTools  boTools=new BeanTools();
		String memberId=null; //用户memberID
		String openId=null; //用户memberID
		String total=null;
		String groupID=null;
		String products=null;
		String addressId=null;
		BaseReplyBean<BeanTools> reply = new BaseReplyBean<BeanTools>();
		BaseReplyResult baseReplyResult = new BaseReplyResult();
		try { 
			String jsonObjectStr = parseRequestReportToString(request, response);
			JSONObject jsonObject = getJsonObject(jsonObjectStr);
			memberId = jsonObject.getJSONObject("commandinfo").getString("memberid");
			openId = jsonObject.getJSONObject("commandinfo").getString("openid");
			total = jsonObject.getJSONObject("commandinfo").getString("total");
			groupID = jsonObject.getJSONObject("commandinfo").getString("groupid");
			products = jsonObject.getJSONObject("commandinfo").getString("products");
			addressId = jsonObject.getJSONObject("commandinfo").getString("addressid");
			ThirdUserInfoBean thirdUser=null;
			if(!"".equals(openId)||openId!=null){
				 thirdUser=thirdUserInfoService.findByOpenIdKey(openId, "");
				if(thirdUser!=null&&thirdUser.getMemberID()!=null){
					memberId=thirdUser.getMemberID();
				}else{
					baseReplyResult.setCode(Constant.ResultCode.RESULT_THREE);   //未注册
					baseReplyResult.setBusinessCode(Constant.ResultCode.RESULT_THREE);
					reply.setResult(baseReplyResult);
				}
			}
			
			if (!"".equals(addressId) && addressId != null) {
				if (!"".equals(memberId) && memberId != null) {
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("memberId", memberId);
					map.put("total", total);
					map.put("groupID", groupID);
					map.put("products", products);
					map.put("addressId", addressId);
					map.put("openId", openId);
					map.put("userKey", thirdUser.getUsercenterKey());
				    boTools= orderService.createOrder(map);

					baseReplyResult.setCode(Constant.ResultCode.SUCCESS);
					baseReplyResult.setBusinessCode(Constant.ResultCode.SUCCESS);
					reply.setResult(baseReplyResult);
					reply.setObj(boTools);
				}
			}else{
				baseReplyResult.setCode(Constant.ResultCode.RESULT_TWO);   //未填写地址
				baseReplyResult.setBusinessCode(Constant.ResultCode.RESULT_TWO);
				reply.setResult(baseReplyResult);
			}
			
		} catch (Exception e) {
			log.error("In the method PayAction.orderSave(HttpServletRequest request,HttpServletResponse response) exists error!",e);
			baseReplyResult.setCode(Constant.ResultCode.FILURE);
			baseReplyResult.setBusinessCode(Constant.ResultCode.FILURE);
			reply.setResult(baseReplyResult);
		}
		responseClient(response, JSON.toJSONString(reply));
		return null;

	}
	
	@RequestMapping("/orderPay")
	public String orderPay(HttpServletRequest request, HttpServletResponse response) {
		BeanTools  boTools=new BeanTools();
		String orderId=null; //用户
		String openId=null;
		String total=null;
		BaseReplyBean<BeanTools> reply = new BaseReplyBean<BeanTools>();
		BaseReplyResult baseReplyResult = new BaseReplyResult();
		try {
			String jsonObjectStr = parseRequestReportToString(request, response);
			JSONObject jsonObject = getJsonObject(jsonObjectStr);
			orderId = jsonObject.getJSONObject("commandinfo").getString("orderid");
			openId = jsonObject.getJSONObject("commandinfo").getString("openid");
			total = jsonObject.getJSONObject("commandinfo").getString("total");
			if(openId!=null){
			boTools.setOpendid(openId);
			boTools.setOrderId(orderId);
			ShopOrder shopOrder =orderService.findById(orderId);
			if(shopOrder!=null&&shopOrder.getPaymentStatus()==1){
			String packageId=orderService.savePackage(total,orderId,openId);
			boTools.setPageId(packageId);
			baseReplyResult.setCode(Constant.ResultCode.SUCCESS);
			baseReplyResult.setBusinessCode(Constant.ResultCode.SUCCESS);
			reply.setResult(baseReplyResult);
			reply.setObj(boTools);
			}else{
				baseReplyResult.setCode(Constant.ResultCode.RESULT_THREE);
				baseReplyResult.setBusinessCode(Constant.ResultCode.RESULT_THREE);
				reply.setResult(baseReplyResult);
			}
			}else{
				baseReplyResult.setCode(Constant.ResultCode.RESULT_TWO);
				baseReplyResult.setBusinessCode(Constant.ResultCode.RESULT_TWO);
				reply.setResult(baseReplyResult);
			}
		} catch (Exception e) {
			log.error("In the method PayAction.orderPay(HttpServletRequest request,HttpServletResponse response) exists error!",e);
			baseReplyResult.setCode(Constant.ResultCode.FILURE);
			baseReplyResult.setBusinessCode(Constant.ResultCode.FILURE);
			reply.setResult(baseReplyResult);
		}
		responseClient(response, JSON.toJSONString(reply));
		return null;


	}

	@RequestMapping("/payResult")
	public String payResult(HttpServletRequest request, HttpServletResponse response) {
		String orderId=null; //用户memberID
		String openId=null;
		String products=null;
		String addressId=null;
		BaseReplyBean<ShopOrder> reply = new BaseReplyBean<ShopOrder>();
		BaseReplyResult baseReplyResult = new BaseReplyResult();
		try {
			
			String jsonObjectStr = parseRequestReportToString(request, response);
			JSONObject jsonObject = getJsonObject(jsonObjectStr);
			orderId = jsonObject.getJSONObject("commandinfo").getString("orderid");
			openId = jsonObject.getJSONObject("commandinfo").getString("opendid");
			products= jsonObject.getJSONObject("commandinfo").getString("products");
			addressId= jsonObject.getJSONObject("commandinfo").getString("addressid");
			ShopOrder shopOrder=orderService.payResult(orderId, openId, products,addressId);
			
			baseReplyResult.setCode(Constant.ResultCode.SUCCESS);
			baseReplyResult.setBusinessCode(Constant.ResultCode.SUCCESS);
			reply.setResult(baseReplyResult);
			reply.setObj(shopOrder);
		} catch (Exception e) {
			log.error("In the method PayAction.payResult(HttpServletRequest request,HttpServletResponse response) exists error!",e);

			baseReplyResult.setCode(Constant.ResultCode.FILURE);
			baseReplyResult.setBusinessCode(Constant.ResultCode.FILURE);
			reply.setResult(baseReplyResult);
		}
		responseClient(response, JSON.toJSONString(reply));
		return null;

	}
	
	
	@RequestMapping("/queryOrderList")
	public String queryOrderList(HttpServletRequest request, HttpServletResponse response) {
		String memberId=null; //用户memberID
		String openId=null; //用户memberID
		String status=null;
		String paymenStatus=null;
		String shippingStatus=null;
		List<ShopOrder> list=null;
		BaseReplyBean<ShopOrder> reply = new BaseReplyBean<ShopOrder>();
		BaseReplyResult baseReplyResult = new BaseReplyResult();
		try {
			String jsonObjectStr = parseRequestReportToString(request, response);
			JSONObject jsonObject = getJsonObject(jsonObjectStr);
			openId = jsonObject.getJSONObject("commandinfo").getString("openid");
			status = jsonObject.getJSONObject("commandinfo").getString("status");
			paymenStatus = jsonObject.getJSONObject("commandinfo").getString("paymenStatus");
			shippingStatus = jsonObject.getJSONObject("commandinfo").getString("shippingStatus");
			ThirdUserInfoBean thirdUser=thirdUserInfoService.findByOpenIdKey(openId, "");
			if(thirdUser!=null&&thirdUser.getMemberID()!=null){
				memberId=thirdUser.getMemberID();
			 list =orderService.findMemberByType(Long.valueOf(memberId), status, paymenStatus, shippingStatus);
			if(list!=null&&list.size()>0){
			baseReplyResult.setCode(Constant.ResultCode.SUCCESS);
			baseReplyResult.setBusinessCode(Constant.ResultCode.SUCCESS);
			reply.setResult(baseReplyResult);
			reply.setObjList(list);
			}else{
				baseReplyResult.setCode(Constant.ResultCode.RESULT_FOURE);   //无订单
				baseReplyResult.setBusinessCode(Constant.ResultCode.RESULT_FOURE);
				reply.setResult(baseReplyResult);
			}
			}else{
				baseReplyResult.setCode(Constant.ResultCode.RESULT_THREE);   //未注册
				baseReplyResult.setBusinessCode(Constant.ResultCode.RESULT_THREE);
				reply.setResult(baseReplyResult);
			}
		} catch (Exception e) {
			log.error("In the method PayAction.queryOrderList(HttpServletRequest request,HttpServletResponse response) exists error!",e);
			baseReplyResult.setCode(Constant.ResultCode.FILURE);
			baseReplyResult.setBusinessCode(Constant.ResultCode.FILURE);
			reply.setResult(baseReplyResult);
		}
		responseClient(response, JSON.toJSONString(reply));
		return null;


	}
	
	
	
	
	@RequestMapping("/orderView")
	public String orderView(HttpServletRequest request, HttpServletResponse response) {
		String orderid=null; //用户memberID
		BaseReplyBean<ShopOrder> reply = new BaseReplyBean<ShopOrder>();
		BaseReplyResult baseReplyResult = new BaseReplyResult();
		try {
			String jsonObjectStr = parseRequestReportToString(request, response);
			JSONObject jsonObject = getJsonObject(jsonObjectStr);
			orderid = jsonObject.getJSONObject("commandinfo").getString("orderid");
			
			ShopOrder shopOrder =orderService.findByOrderId(orderid,"1");
			
			
			baseReplyResult.setCode(Constant.ResultCode.SUCCESS);
			baseReplyResult.setBusinessCode(Constant.ResultCode.SUCCESS);
			reply.setResult(baseReplyResult);
			reply.setObj(shopOrder);
		} catch (Exception e) {
			log.error("In the method PayAction.orderView(HttpServletRequest request,HttpServletResponse response) exists error!",e);
			baseReplyResult.setCode(Constant.ResultCode.FILURE);
			baseReplyResult.setBusinessCode(Constant.ResultCode.FILURE);
			reply.setResult(baseReplyResult);
		}
		responseClient(response, JSON.toJSONString(reply));
		return null;


	}
	
	
	@RequestMapping("/orderConfirm")
	public String orderConfirm(HttpServletRequest request, HttpServletResponse response) {
		String opendId=null; //用户memberID
		String orderid=null; //用户memberID
		BaseReplyBean<ShopOrder> reply = new BaseReplyBean<ShopOrder>();
		BaseReplyResult baseReplyResult = new BaseReplyResult();
		try {
			String jsonObjectStr = parseRequestReportToString(request, response);
			JSONObject jsonObject = getJsonObject(jsonObjectStr);
			opendId = jsonObject.getJSONObject("commandinfo").getString("opendid");
			orderid = jsonObject.getJSONObject("commandinfo").getString("orderid");
			ThirdUserInfoBean thirdUser=thirdUserInfoService.findByOpenIdKey(opendId, "");
			if(thirdUser!=null&&thirdUser.getMemberID()!=null){
			   orderService.orderConfirm(orderid);
			baseReplyResult.setCode(Constant.ResultCode.SUCCESS);
			baseReplyResult.setBusinessCode(Constant.ResultCode.SUCCESS);
			reply.setResult(baseReplyResult);
			}else{
				baseReplyResult.setCode(Constant.ResultCode.RESULT_TWO);
				baseReplyResult.setBusinessCode(Constant.ResultCode.RESULT_TWO);
				reply.setResult(baseReplyResult);
			}
		} catch (Exception e) {
			log.error("In the method PayAction.orderConfirm(HttpServletRequest request,HttpServletResponse response) exists error!",e);
			baseReplyResult.setCode(Constant.ResultCode.FILURE);
			baseReplyResult.setBusinessCode(Constant.ResultCode.FILURE);
			reply.setResult(baseReplyResult);
		}
		responseClient(response, JSON.toJSONString(reply));
		return null;


	}
	
	

}
