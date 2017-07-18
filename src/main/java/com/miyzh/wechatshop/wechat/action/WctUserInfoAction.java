package com.miyzh.wechatshop.wechat.action;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClients;
import org.apache.log4j.Logger;
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
import com.miyzh.wechatshop.wechat.bean.WctUserInfo;
import com.miyzh.wechatshop.wechat.report.WctUserInfoReplyReport;
import com.miyzh.wechatshop.wechat.report.WctUserInfoRequestReport;

/**
 * 文件名：WctUserInfoAction<br>
 * 描述: 微信公众号获取用户信息<br>
 * 修改人: wumingdi<br>
 * 修改时间： 2016年7月6日 下午3:22:05 <br>
 */
@Controller
@RequestMapping("/wctUserInfo")
public class WctUserInfoAction extends BaseAction {

	private Logger log = Logger.getLogger(this.getClass());

	/**
	 * 根据微信code获取snsapi_userinfo信息
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/index")
	@ResponseBody
	public String wctSnsapiUserinfo(HttpServletRequest request,
			HttpServletResponse response) {

		return null;
	}

	private WctUserInfoRequestReport parseRequestReport(String jsonString) {
		JSONObject jsonObject = getJsonObject(jsonString);
		return (WctUserInfoRequestReport) JSON.toJavaObject(jsonObject,
				WctUserInfoRequestReport.class);
	}

	/**
	 * 获取微信用户信息
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getWctUserInfo")
	@ResponseBody
	public String getWctUserInfo(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		String jsonString = parseRequestReportToString(request, response);
		WctUserInfoRequestReport wctUserInfoRequestReport = parseRequestReport(jsonString);

		WctUserInfoReplyReport wctUserInfoReplyReport = null;
		WctUserInfo wctUserInfo = null;
		BaseReplyResult baseReplyResult = new BaseReplyResult();
		baseReplyResult.setCode(Constant.ResultCode.SUCCESS);
		baseReplyResult.setBusinessCode(Constant.ResultCode.SUCCESS);

		try {
			String openId = wctUserInfoRequestReport.getCommandinfo()
					.getOpenid();
			String accessToken = wctUserInfoRequestReport.getCommandinfo()
					.getAccesstoken();

			if (StringUtils.isBlank(openId) && StringUtils.isBlank(accessToken)) {

				baseReplyResult.setBusinessCode(Constant.ResultCode.FILURE);
				baseReplyResult.setMsg("请求参数不完整");
			}

			String url = getUserInfoUrl(
					PropertiesUtil.getPropertyValue("wct_user_info_url"),
					openId, accessToken);
			wctUserInfo = getHttpWctUserInfo(url);

			BaseReplyBean<WctUserInfo> wctUserInfoBaseReplyBean = new BaseReplyBean<WctUserInfo>();
			wctUserInfoReplyReport = new WctUserInfoReplyReport();
			wctUserInfoBaseReplyBean.setObj(wctUserInfo);
			wctUserInfoReplyReport.setReply(wctUserInfoBaseReplyBean);
			wctUserInfoReplyReport.setResult(baseReplyResult);
		} catch (Exception e) {
			log.error("获取openid异常", e);
			baseReplyResult.setBusinessCode(Constant.ResultCode.FILURE);
			baseReplyResult.setMsg("获取openid异常");
			baseReplyResult.setCode(Constant.ResultCode.FILURE);
			wctUserInfoReplyReport = new WctUserInfoReplyReport();
			wctUserInfoReplyReport.setResult(baseReplyResult);
		}

		resReply(response, wctUserInfoReplyReport);
		return null;
	}

	/**
	 * 微信获取openId
	 * 
	 * @param wctAccessTokenCommandInfo
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getWctSnsapiBase")
	@ResponseBody
	private String getWctSnsapiBase(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		String jsonString = parseRequestReportToString(request, response);
		WctUserInfoRequestReport wctUserInfoRequestReport = parseRequestReport(jsonString);

		WctUserInfoReplyReport wctUserInfoReplyReport = null;
		WctUserInfo wctUserInfo = null;
		BaseReplyResult baseReplyResult = new BaseReplyResult();
		baseReplyResult.setCode(Constant.ResultCode.SUCCESS);
		baseReplyResult.setBusinessCode(Constant.ResultCode.SUCCESS);

		try {
			String appId = PropertiesUtil.getPropertyValue("app_id");
			String appSec = PropertiesUtil.getPropertyValue("app_secret");
			String code = wctUserInfoRequestReport.getCommandinfo().getCode();

			if (StringUtils.isBlank(appId) && StringUtils.isBlank(appSec)
					&& StringUtils.isBlank(code)) {

				baseReplyResult.setBusinessCode(Constant.ResultCode.FILURE);
				baseReplyResult.setMsg("请求参数不完整");
			}

			String url = getUrl(
					PropertiesUtil.getPropertyValue("wct_access_token_url"),
					appId, appSec, code);
			wctUserInfo = getHttpWctUserInfo(url);

			BaseReplyBean<WctUserInfo> wctUserInfoBaseReplyBean = new BaseReplyBean<WctUserInfo>();
			wctUserInfoReplyReport = new WctUserInfoReplyReport();
			wctUserInfoBaseReplyBean.setObj(wctUserInfo);
			wctUserInfoReplyReport.setReply(wctUserInfoBaseReplyBean);
			wctUserInfoReplyReport.setResult(baseReplyResult);
		} catch (Exception e) {
			log.error("获取openid异常", e);
			baseReplyResult.setBusinessCode(Constant.ResultCode.FILURE);
			baseReplyResult.setMsg("获取openid异常");
			baseReplyResult.setCode(Constant.ResultCode.FILURE);
			wctUserInfoReplyReport = new WctUserInfoReplyReport();
			wctUserInfoReplyReport.setResult(baseReplyResult);
		}

		resReply(response, wctUserInfoReplyReport);
		return null;
	}

	/**
	 * 获取用户信息url
	 * 
	 * @param url
	 * @param appid
	 * @param appsec
	 * @param code
	 * @return
	 */
	private String getUserInfoUrl(String url, String openId, String accessToken) {
		StringBuffer urlBuffer = new StringBuffer("");
		urlBuffer.append(url);
		urlBuffer.append("&access_token=").append(accessToken);
		urlBuffer.append("&openid=").append(openId);
		return urlBuffer.toString();
	}

	/**
	 * 拼接url
	 * 
	 * @param appid
	 * @param appsec
	 * @param code
	 * @return
	 */
	private String getUrl(String url, String appid, String appsec, String code) {
		StringBuffer urlBuffer = new StringBuffer("");
		urlBuffer.append(url);
		urlBuffer.append("&secret=").append(appsec);
		urlBuffer.append("&appid=").append(appid);
		urlBuffer.append("&code=").append(code);
		return urlBuffer.toString();
	}

	/**
	 * 访问微信url，获取微信token
	 * 
	 * @return WctAccessToken 微信公众号唯一票据信息
	 */
	private WctUserInfo getHttpWctUserInfo(String url) {
		WctUserInfo wctUserInfo = null;
		try {
			String uriBuffer = url;
			HttpClient client = HttpClients.createDefault();
			HttpPost post = new HttpPost(uriBuffer);
			post.addHeader("Content-Type", "application/json");
			HttpResponse response = client.execute(post);
			HttpEntity entity = response.getEntity();
			String resBody = IOUtils.toString(entity.getContent(), "UTF-8");
			int statusCode = response.getStatusLine().getStatusCode();
			if (statusCode == 200) {
				wctUserInfo = JSON.toJavaObject(getJsonObject(resBody),
						WctUserInfo.class);
			}
		} catch (UnsupportedEncodingException e) {
			log.error(
					"In the method WctUserInfoAction.getHttpWctUserInfo() exists UnsupportedEncodingException error!",
					e);
		} catch (ClientProtocolException e) {
			log.error(
					"In the method WctUserInfoAction.getHttpWctUserInfo() exists ClientProtocolException error!",
					e);
		} catch (IllegalStateException e) {
			log.error(
					"In the method WctUserInfoAction.getHttpWctUserInfo() exists IllegalStateException error!",
					e);
		} catch (IOException e) {
			log.error(
					"In the method WctUserInfoAction.getHttpWctUserInfo() exists IOException error!",
					e);
		} catch (Exception e) {
			log.error(
					"In the method WctUserInfoAction.getHttpWctUserInfo() exists error!",
					e);
		}
		return wctUserInfo;
	}

	/**
	 * 回复报文公用方法
	 *
	 * @param response
	 * @param userReplyReport
	 * @throws IOException
	 */
	private void resReply(HttpServletResponse response,
			WctUserInfoReplyReport wctUserInfoReplyReport) throws IOException {
		if (wctUserInfoReplyReport == null) {
			replyIdentifyError(response);
			return;
		}
		wctUserInfoReplyReport.setReplytime(String.valueOf(System
				.currentTimeMillis()));// replyTime
		String replyReport = JSON.toJSONString(wctUserInfoReplyReport);
		responseClient(response, replyReport);
	}

}
