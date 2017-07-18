package com.miyzh.wechatshop.payUtil.action;


import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.miyzh.framework.base.Constant;
import com.miyzh.framework.base.action.BaseAction;
import com.miyzh.framework.base.action.reply.BaseReplyBean;
import com.miyzh.framework.base.action.reply.BaseReplyResult;
import com.miyzh.wechatshop.payUtil.bean.Address;
import com.miyzh.wechatshop.payUtil.bean.ShopMemberAddress;
import com.miyzh.wechatshop.payUtil.service.AddressService;
import com.miyzh.wechatshop.payUtil.service.MemberAddressService;
import com.miyzh.wechatshop.user.bean.ThirdUserInfoBean;
import com.miyzh.wechatshop.user.service.IThirdUserInfoService;

@Controller
@RequestMapping("/memberaddress")
public class MemberAddressAction extends BaseAction {


	@Autowired
	private MemberAddressService memberAddressService;

	@Autowired
	private AddressService addressService;

	@Autowired
	private IThirdUserInfoService thirdUserInfoService;
	
	/**
	 * 查询当前用户默认地址
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/queryDefaultList")
	public String queryDefaultList(HttpServletRequest request, HttpServletResponse response) {
		
		String openId=null;
		BaseReplyBean<ShopMemberAddress> reply = new BaseReplyBean<ShopMemberAddress>();
		BaseReplyResult baseReplyResult = new BaseReplyResult();
		try {
			String jsonObjectStr = parseRequestReportToString(request, response);
			JSONObject jsonObject = getJsonObject(jsonObjectStr);
			openId = jsonObject.getJSONObject("commandinfo").getString("openid");
			ThirdUserInfoBean thirdUser=thirdUserInfoService.findByOpenIdKey(openId, "");
			if(thirdUser!=null&&thirdUser.getMemberID()!=null){
			List<ShopMemberAddress> list = memberAddressService.findByMemberId(Long.valueOf(thirdUser.getMemberID()),"1","");
			baseReplyResult.setCode(Constant.ResultCode.SUCCESS);
			baseReplyResult.setBusinessCode(Constant.ResultCode.SUCCESS);
			reply.setResult(baseReplyResult);
			reply.setObjList(list);
			}else{
				baseReplyResult.setCode(Constant.ResultCode.RESULT_TWO);
				baseReplyResult.setBusinessCode(Constant.ResultCode.RESULT_TWO);
				reply.setResult(baseReplyResult);
			}
		} catch (Exception e) {
			log.error("In the method MemberAddressAction.queryDefaultList(HttpServletRequest request,HttpServletResponse response) exists error!",e);

			baseReplyResult.setCode(Constant.ResultCode.FILURE);
			baseReplyResult.setBusinessCode(Constant.ResultCode.FILURE);
			reply.setResult(baseReplyResult);
		}
		responseClient(response, JSON.toJSONString(reply));
		return null;
	}
	
	/**
	 * 查询当前用户所有地址
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/queryAddressList")
	public String queryAddressList(HttpServletRequest request, HttpServletResponse response) {
		String memberId = null;
		String openId=null;
		BaseReplyBean<ShopMemberAddress> reply = new BaseReplyBean<ShopMemberAddress>();
		BaseReplyResult baseReplyResult = new BaseReplyResult();
		try {
			String jsonObjectStr = parseRequestReportToString(request, response);
			JSONObject jsonObject = getJsonObject(jsonObjectStr);
			openId = jsonObject.getJSONObject("commandinfo").getString("openid");
			ThirdUserInfoBean thirdUser=thirdUserInfoService.findByOpenIdKey(openId, "");
			if(thirdUser!=null&&thirdUser.getMemberID()!=null){
			memberId=thirdUser.getMemberID();
			List<ShopMemberAddress> list = memberAddressService.findByMemberId(Long.valueOf(memberId),"","");
			baseReplyResult.setCode(Constant.ResultCode.SUCCESS);
			baseReplyResult.setBusinessCode(Constant.ResultCode.SUCCESS);
			reply.setArgs(memberId);
			reply.setResult(baseReplyResult);
			reply.setObjList(list);
			}else{
				baseReplyResult.setCode(Constant.ResultCode.RESULT_TWO);
				baseReplyResult.setBusinessCode(Constant.ResultCode.RESULT_TWO);
				reply.setResult(baseReplyResult);
			}
		} catch (Exception e) {
			log.error("In the method MemberAddressAction.queryAddressList(HttpServletRequest request,HttpServletResponse response) exists error!",e);
			baseReplyResult.setCode(Constant.ResultCode.FILURE);
			baseReplyResult.setBusinessCode(Constant.ResultCode.FILURE);
			reply.setResult(baseReplyResult);
		}
		responseClient(response, JSON.toJSONString(reply));
		return null;

	}
	
	
	/**
	 * 查询当前用户某个指定地址
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/queryAddressById")
	public String queryAddressById(HttpServletRequest request, HttpServletResponse response) {
		String addressId = null;
		BaseReplyBean<ShopMemberAddress> reply = new BaseReplyBean<ShopMemberAddress>();
		BaseReplyResult baseReplyResult = new BaseReplyResult();
		try {
			String jsonObjectStr = parseRequestReportToString(request, response);
			JSONObject jsonObject = getJsonObject(jsonObjectStr);
			addressId = jsonObject.getJSONObject("commandinfo").getString("addressId");
			List<ShopMemberAddress> list = memberAddressService.findById(addressId);
			baseReplyResult.setCode(Constant.ResultCode.SUCCESS);
			baseReplyResult.setBusinessCode(Constant.ResultCode.SUCCESS);
			reply.setResult(baseReplyResult);
			reply.setObjList(list);
		} catch (Exception e) {
			log.error("In the method MemberAddressAction.queryAddressById(HttpServletRequest request,HttpServletResponse response) exists error!",e);

			baseReplyResult.setCode(Constant.ResultCode.FILURE);
			baseReplyResult.setBusinessCode(Constant.ResultCode.FILURE);
			reply.setResult(baseReplyResult);
		}
		responseClient(response, JSON.toJSONString(reply));
		return null;

	}
	
	

	/**
	 * 修改地址
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/updateAddress")
	public String updateAddress(HttpServletRequest request, HttpServletResponse response) {

		String provinceId= null;
		String cityId= null;
		String countryId= null;
		String detailAddress= null;
		String tel= null;
		String gender= null;
		String isDefault= null;
		BaseReplyBean<ShopMemberAddress> reply = new BaseReplyBean<ShopMemberAddress>();
		BaseReplyResult baseReplyResult = new BaseReplyResult();
		try {
			String jsonObjectStr = parseRequestReportToString(request, response);
			JSONObject jsonObject = getJsonObject(jsonObjectStr);
			provinceId = jsonObject.getJSONObject("commandinfo").getString("provinceid");
			cityId = jsonObject.getJSONObject("commandinfo").getString("cityid");
			countryId = jsonObject.getJSONObject("commandinfo").getString("countryid");
			detailAddress = jsonObject.getJSONObject("commandinfo").getString("detailAddress");
			tel = jsonObject.getJSONObject("commandinfo").getString("tel");
			gender = jsonObject.getJSONObject("commandinfo").getString("gender");
			isDefault = jsonObject.getJSONObject("commandinfo").getString("isdefault");
            ShopMemberAddress shopMemberAddress =new ShopMemberAddress();
            shopMemberAddress.setProvince(provinceId);
            shopMemberAddress.setCity(cityId);
            shopMemberAddress.setCountry(provinceId);
            shopMemberAddress.setDetailaddress(countryId);
            shopMemberAddress.setTel(tel);
            shopMemberAddress.setDetailaddress(detailAddress);
            shopMemberAddress.setGender(gender);
            shopMemberAddress.setDefault(isDefault);
			 memberAddressService.update(shopMemberAddress);
			baseReplyResult.setCode(Constant.ResultCode.SUCCESS);
			baseReplyResult.setBusinessCode(Constant.ResultCode.SUCCESS);
			reply.setResult(baseReplyResult);
		} catch (Exception e) {
			log.error("In the method MemberAddressAction.updateAddress(HttpServletRequest request,HttpServletResponse response) exists error!",e);
			baseReplyResult.setCode(Constant.ResultCode.FILURE);
			baseReplyResult.setBusinessCode(Constant.ResultCode.FILURE);
			reply.setResult(baseReplyResult);
		}
		responseClient(response, JSON.toJSONString(reply));
		return null;

	
	}
	
	
	/**
	 * 新增地址
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/saveAddress")
	public String saveAddress(HttpServletRequest request, HttpServletResponse response) {
		String memberID= null;
		String provinceId= null;
		String cityId= null;
		String countryId= null;
		String detailAddress= null;
		String tel= null;
		String username=null;
		String isDefault= null;
		BaseReplyBean<ShopMemberAddress> reply = new BaseReplyBean<ShopMemberAddress>();
		BaseReplyResult baseReplyResult = new BaseReplyResult();
		try {
			String jsonObjectStr = parseRequestReportToString(request, response);
			JSONObject jsonObject = getJsonObject(jsonObjectStr);
			memberID = jsonObject.getJSONObject("commandinfo").getString("memberid");
			provinceId = jsonObject.getJSONObject("commandinfo").getString("provinceid");
			cityId = jsonObject.getJSONObject("commandinfo").getString("cityid");
			countryId = jsonObject.getJSONObject("commandinfo").getString("countryid");
			detailAddress = jsonObject.getJSONObject("commandinfo").getString("detailaddress");
			username = jsonObject.getJSONObject("commandinfo").getString("username");
			tel = jsonObject.getJSONObject("commandinfo").getString("tel");
			isDefault = jsonObject.getJSONObject("commandinfo").getString("isdefault");
			  ShopMemberAddress shopMemberAddress =new ShopMemberAddress();
			if (null != memberID
					&& StringUtils
							.isNotBlank(memberID)&&null != username
									&& StringUtils
									.isNotBlank(username)&&null != tel
											&& StringUtils
											.isNotBlank(tel)&&null != detailAddress
													&& StringUtils
													.isNotBlank(detailAddress)) {
				   shopMemberAddress.setMember(Long.valueOf(memberID));
			        shopMemberAddress.setProvince(provinceId);
		            shopMemberAddress.setCity(cityId);
		            shopMemberAddress.setCountry(countryId);
		            shopMemberAddress.setDetailaddress(detailAddress);
		            shopMemberAddress.setUsername(username);
		            shopMemberAddress.setTel(tel);
		            shopMemberAddress.setPhone(tel);
		            shopMemberAddress.setGender("0");
		            shopMemberAddress.setDefault(isDefault);
		            if("1".equals(isDefault)){
		            	memberAddressService.updateByIsDefault(memberID);
		            }
		            memberAddressService.create(shopMemberAddress);
					baseReplyResult.setCode(Constant.ResultCode.SUCCESS);
					baseReplyResult.setBusinessCode(Constant.ResultCode.SUCCESS);
					reply.setResult(baseReplyResult);
				    reply.setObj(shopMemberAddress);
			} else {
				baseReplyResult.setBusinessCode(Constant.ResultCode.RESULT_TWO);
				baseReplyResult.setMsg("请求参数不完整");
			}
       
			reply.setResult(baseReplyResult);
		} catch (Exception e) {
			log.error("In the method MemberAddressAction.saveAddress(HttpServletRequest request,HttpServletResponse response) exists error!",e);
			baseReplyResult.setCode(Constant.ResultCode.FILURE);
			baseReplyResult.setBusinessCode(Constant.ResultCode.FILURE);
			reply.setResult(baseReplyResult);
		}
		responseClient(response, JSON.toJSONString(reply));
		return null;

	}


	
	
	/**
	 * 查询省市区
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/queryPcity")
	public String queryPcity(HttpServletRequest request, HttpServletResponse response) {
		String parentId = null;
		BaseReplyBean<Address> reply = new BaseReplyBean<Address>();
		BaseReplyResult baseReplyResult = new BaseReplyResult();
		try {
			String jsonObjectStr = parseRequestReportToString(request, response);
			JSONObject jsonObject = getJsonObject(jsonObjectStr);
			parentId = jsonObject.getJSONObject("commandinfo").getString("parentid");
			List<Address> list = addressService.findByParentId(parentId);
			if(list!=null){
			baseReplyResult.setCode(Constant.ResultCode.SUCCESS);
			baseReplyResult.setBusinessCode(Constant.ResultCode.SUCCESS);
			reply.setResult(baseReplyResult);
			reply.setObjList(list);
			}else{
				baseReplyResult.setCode(Constant.ResultCode.RESULT_TWO);
				baseReplyResult.setBusinessCode(Constant.ResultCode.RESULT_TWO);
				reply.setResult(baseReplyResult);
			}
		} catch (Exception e) {
			log.error("In the method MemberAddressAction.queryPcity(HttpServletRequest request,HttpServletResponse response) exists error!",e);
			baseReplyResult.setCode(Constant.ResultCode.FILURE);
			baseReplyResult.setBusinessCode(Constant.ResultCode.FILURE);
			reply.setResult(baseReplyResult);
		}
		responseClient(response, JSON.toJSONString(reply));
		return null;

	}
	
	
}
