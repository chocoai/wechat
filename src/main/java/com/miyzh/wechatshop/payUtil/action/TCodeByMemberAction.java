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
import com.miyzh.wechatshop.payUtil.bean.TCodeByMember;
import com.miyzh.wechatshop.payUtil.service.TCodeByMemberService;
import com.miyzh.wechatshop.user.bean.ThirdUserInfoBean;
import com.miyzh.wechatshop.user.service.IThirdUserInfoService;

@Controller
@RequestMapping("/TCodeByMember")
public class TCodeByMemberAction extends BaseAction {

	@Autowired
	private TCodeByMemberService  tCodeByMemberService;
	@Autowired
	private IThirdUserInfoService thirdUserInfoService;
	@RequestMapping("/queryMyQRcodeList")
	public  String  queryMyQRcodeList(HttpServletRequest request, HttpServletResponse response){
		String  memberId = null;
		String  openId = "";
		BaseReplyResult baseReplyResult = new BaseReplyResult();
		BaseReplyBean<TCodeByMember> reply = new BaseReplyBean<TCodeByMember>();
		try {
			String jsonObjectStr = parseRequestReportToString(request, response);
			JSONObject jsonObject = getJsonObject(jsonObjectStr);
			openId = jsonObject.getJSONObject("commandinfo").getString("openid");
			ThirdUserInfoBean  thirdUserInfoBean = thirdUserInfoService.findByOpenIdKey(openId, "");
			if(thirdUserInfoBean!=null){
				memberId = thirdUserInfoBean.getMemberID();
				List<TCodeByMember> list = tCodeByMemberService.findByMemberId(Long.valueOf(memberId));
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
			log.error("获取用户二维码列表,memberid->"+memberId,e);
			baseReplyResult.setCode(Constant.ResultCode.FILURE);
			baseReplyResult.setBusinessCode(Constant.ResultCode.FILURE);
			reply.setResult(baseReplyResult);
		}
		responseClient(response, JSON.toJSONString(reply));
		return null;
	}
	@RequestMapping("/queryMyQRcode")
	public  String  queryMyQRcode(HttpServletRequest request, HttpServletResponse response){
		String  id = null;
		BaseReplyResult baseReplyResult = new BaseReplyResult();
		BaseReplyBean<TCodeByMember> reply = new BaseReplyBean<TCodeByMember>();
		try {
			String jsonObjectStr = parseRequestReportToString(request, response);
			JSONObject jsonObject = getJsonObject(jsonObjectStr);
			id = jsonObject.getJSONObject("commandinfo").getString("id");//二维码主键id
			String  memberId  = jsonObject.getJSONObject("commandinfo").getString("memberid");
			String  groupId = jsonObject.getJSONObject("commandinfo").getString("groupid");
			if(StringUtils.isNotBlank(id)){
				TCodeByMember tCodeByMember = tCodeByMemberService.findById(Long.valueOf(id));
				if(tCodeByMember!=null){
					baseReplyResult.setCode(Constant.ResultCode.SUCCESS);
					baseReplyResult.setBusinessCode(Constant.ResultCode.SUCCESS);
					reply.setResult(baseReplyResult);
					reply.setObj(tCodeByMember);
				}else{
					baseReplyResult.setCode(Constant.ResultCode.RESULT_THREE);
					baseReplyResult.setBusinessCode(Constant.ResultCode.RESULT_THREE);
					reply.setResult(baseReplyResult);
				}
			}else  if (StringUtils.isNotBlank(memberId)&&StringUtils.isNotBlank(groupId)){
				TCodeByMember tCodeByMember = tCodeByMemberService.findByMemberIdGroupId(Long.valueOf(memberId),  Long.valueOf(groupId));
				if(tCodeByMember!=null){
					baseReplyResult.setCode(Constant.ResultCode.SUCCESS);
					baseReplyResult.setBusinessCode(Constant.ResultCode.SUCCESS);
					reply.setResult(baseReplyResult);
					reply.setObj(tCodeByMember);
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
			log.error("获取用户二维码列表,tcodeId->"+id,e);
			baseReplyResult.setCode(Constant.ResultCode.FILURE);
			baseReplyResult.setBusinessCode(Constant.ResultCode.FILURE);
			reply.setResult(baseReplyResult);
		}
		responseClient(response, JSON.toJSONString(reply));
		return null;
	}

	
	@RequestMapping("/saveMyQRcode")
	public  String  saveMyQRcode(HttpServletRequest request, HttpServletResponse response){
		String  groupId = null;
		String opendId=null;
		BaseReplyResult baseReplyResult = new BaseReplyResult();
		BaseReplyBean<TCodeByMember> reply = new BaseReplyBean<TCodeByMember>();
		try {
			String jsonObjectStr = parseRequestReportToString(request, response);
			JSONObject jsonObject = getJsonObject(jsonObjectStr);
			opendId = jsonObject.getJSONObject("commandinfo").getString("opendid");
		    groupId = jsonObject.getJSONObject("commandinfo").getString("groupid");
//			tCodeByMemberService.saveTCode(opendId, groupId);
			baseReplyResult.setCode(Constant.ResultCode.SUCCESS);
			baseReplyResult.setBusinessCode(Constant.ResultCode.SUCCESS);
			reply.setResult(baseReplyResult);
		} catch (Exception e) {
			log.error(e);
			baseReplyResult.setCode(Constant.ResultCode.FILURE);
			baseReplyResult.setBusinessCode(Constant.ResultCode.FILURE);
			reply.setResult(baseReplyResult);
		}
		responseClient(response, JSON.toJSONString(reply));
		return null;
	}
	
	
	
}
