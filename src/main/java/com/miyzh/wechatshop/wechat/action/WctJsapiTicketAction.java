package com.miyzh.wechatshop.wechat.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
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
import com.miyzh.framework.util.PropertiesUtil;
import com.miyzh.wechatshop.wechat.report.WctJsapiTicketReply;
import com.miyzh.wechatshop.wechat.service.IJsapiTicketService;

@Controller
@RequestMapping("/jsapiTicketAction")
public class WctJsapiTicketAction  extends BaseAction {
	
	private Logger log = Logger.getLogger(this.getClass());
	
	@Autowired
	private IJsapiTicketService serivce;
	
	@RequestMapping("/getJsapiTicket")
	@ResponseBody
	public String getJsapiTicket(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String appId = "";
		String url = "";
		BaseReplyBean<WctJsapiTicketReply> reply = new BaseReplyBean<WctJsapiTicketReply>();
		BaseReplyResult baseReplyResult = new BaseReplyResult();
		try {
			String jsonObjectStr = parseRequestReportToString(request, response);
			JSONObject jsonObject = getJsonObject(jsonObjectStr);
			appId = jsonObject.getJSONObject("commandinfo").getString("appid");
			url = jsonObject.getJSONObject("commandinfo").getString("url");
			
			String wxappId = PropertiesUtil.getPropertyValue("app_id");
			String wxappSec = PropertiesUtil.getPropertyValue("app_secret");
			log.info("appId->"+appId);
			log.info("wxappId->"+wxappId);
			if(wxappId.equals(appId)){
				WctJsapiTicketReply jsapiTicket = serivce.toJsapiTicket(wxappId, wxappSec, url);
				
				if(jsapiTicket!=null){
					baseReplyResult.setCode(Constant.ResultCode.SUCCESS);
					baseReplyResult.setBusinessCode(Constant.ResultCode.SUCCESS);
					reply.setResult(baseReplyResult);
					reply.setObj(jsapiTicket);
				}else{
					baseReplyResult.setCode(Constant.ResultCode.SUCCESS);
					baseReplyResult.setBusinessCode(Constant.ResultCode.FILURE);
					reply.setResult(baseReplyResult);
				}
			}else{
				baseReplyResult.setCode(Constant.ResultCode.SUCCESS);
				baseReplyResult.setBusinessCode(Constant.ResultCode.RESULT_TWO);
				reply.setResult(baseReplyResult);
			}
			
		} catch (Exception e) {
			log.error("获取JsapiTicket异常,appId->"+appId,e);
			baseReplyResult.setCode(Constant.ResultCode.FILURE);
			baseReplyResult.setBusinessCode(Constant.ResultCode.FILURE);
			reply.setResult(baseReplyResult);
		}
		responseClient(response, JSON.toJSONString(reply));
		return null;
	}

}
