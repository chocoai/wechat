package com.miyzh.wechatshop.wechat.action;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Calendar;
import java.util.Date;

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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.dubbo.common.URL;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SimplePropertyPreFilter;
import com.miyzh.framework.base.Constant;
import com.miyzh.framework.base.action.BaseAction;
import com.miyzh.framework.base.action.reply.BaseReplyBean;
import com.miyzh.framework.base.action.reply.BaseReplyResult;
import com.miyzh.framework.util.DateUtil;
import com.miyzh.framework.util.HttpClientUtil;
import com.miyzh.framework.util.PropertiesUtil;
import com.miyzh.wechatshop.wechat.bean.WctAccessToken;
import com.miyzh.wechatshop.wechat.report.WctAccessTokenReplyReport;
import com.miyzh.wechatshop.wechat.report.WctAccessTokenRequestReport;
import com.miyzh.wechatshop.wechat.service.WctAccessTokenService;

/**
 * 文件名：WctAccessTokenAction<br>
 * 描述: 微信公众号唯一票据信息<br>
 */
@Controller
@RequestMapping("/wctAccessToken")
public class WctAccessTokenAction extends BaseAction {
	@Autowired
	private WctAccessTokenService wctAccessTokenService;

	/**
	 * 回复报文公用方法
	 * 
	 * @param response
	 * @param userReplyReport
	 * @throws IOException
	 */
	private void resReply(HttpServletResponse response,
			WctAccessTokenReplyReport wctAccessTokenReplyReport,
			SimplePropertyPreFilter filterPro) throws IOException {
		if (wctAccessTokenReplyReport == null) {
			replyIdentifyError(response);
			return;
		}
		wctAccessTokenReplyReport.setReplytime(String.valueOf(System
				.currentTimeMillis()));// replyTime
		String replyReport = JSON.toJSONString(wctAccessTokenReplyReport,
				filterPro);
		responseClient(response, replyReport);
	}

	private WctAccessTokenRequestReport parseRequestReport(String jsonString) {
		JSONObject jsonObject = getJsonObject(jsonString);
		return (WctAccessTokenRequestReport) JSON.toJavaObject(jsonObject,
				WctAccessTokenRequestReport.class);
	}
	
	@ResponseBody
	@RequestMapping("/getQrCode")
	public String getQrCode(HttpServletRequest request, HttpServletResponse response){
		String appId = PropertiesUtil.getPropertyValue("app_id");
		String appSec = PropertiesUtil.getPropertyValue("app_secret");
		String sceneId = null;
		try {
			String jsonObjectStr = parseRequestReportToString(request, response);
			JSONObject jsonObject = getJsonObject(jsonObjectStr);
			sceneId = jsonObject.getString("sceneid");
			String token = wctAccessTokenService.toAccessToken(appId, appSec);
			log.info("token->" + token);
			String param = "{\"action_name\": \"QR_LIMIT_SCENE\",\"action_info\": {\"scene\": {\"scene_id\": \""+sceneId+"\"}}}";
			log.info("生成二维码参数:"+param);
			String url = "https://api.weixin.qq.com/cgi-bin/qrcode/create?access_token="+ token;
			JSONObject obj = HttpClientUtil.httpReq(url, param, null);
			log.info("ticket url->" + obj.toJSONString());
			String ticket = obj.getString("ticket");
			if (ticket != null) {
				responseClient(response, ticket);
			} else {
				log.error("创建二维码失败->" + obj.toJSONString());
			}
		} catch (Exception e) {
			log.error("获取二维码异常,sceneId->"+sceneId, e);
		}
		return null;
	}

	@RequestMapping("/index")
	public String index(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		try {
			String jsonString = parseRequestReportToString(request, response);
			WctAccessTokenReplyReport wctAccessTokenReplyReport = null;
			SimplePropertyPreFilter filterPro = null;
			if (StringUtils.isNotBlank(jsonString)) {
				WctAccessTokenRequestReport wctAccessTokenRequestReport = parseRequestReport(jsonString);
				if (null != wctAccessTokenRequestReport) {
					String commandType = wctAccessTokenRequestReport
							.getCommandtype();
					// 获取微信公众号token
					if (Constant.CommandType.WECHAT.WCT_GETACCESSTOKEN
							.equals(commandType)) {
						wctAccessTokenReplyReport = getAccessToken(
								wctAccessTokenRequestReport.getCommandInfo(),
								filterPro);
					}
				}
			}
			resReply(response, wctAccessTokenReplyReport, filterPro);
		} catch (Exception e) {
			replyError(response);
			log.error(
					"In the method WctAccessTokenAction.index(HttpServletRequest request,HttpServletResponse response) exists error!",
					e);
		}
		return null;
	}

	/**
	 * 获取微信公众号token
	 * 
	 * @param filterPr
	 * @throws Exception
	 */
	private WctAccessTokenReplyReport getAccessToken(
			WctAccessToken wctAccessTokenCommandInfo,
			SimplePropertyPreFilter filterPr) throws Exception {
		String appId = PropertiesUtil.getPropertyValue("app_id");
		String appSec = PropertiesUtil.getPropertyValue("app_secret");
		if (StringUtils.isBlank(appId) && StringUtils.isBlank(appSec)) {
			throw new Exception("this appid with appSec is null!");
		}

		boolean isCompulsoryFlag = false;// 是否需要强制性获取token true:是
		WctAccessToken wctAccessToken = wctAccessTokenService
				.queryWctAccessTokenByAppid(appId, isCompulsoryFlag);

		BaseReplyResult baseReplyResult = new BaseReplyResult();
		long timeMillis = 0l;// 返回的毫秒数
		if (null != wctAccessToken) {
			String expiretimeStr = wctAccessToken.getExpiretime();
			if (StringUtils.isNotBlank(expiretimeStr)) {
				Date date = DateUtil.parse(expiretimeStr,
						DateUtil.DEFAULT_DATETIME_FORMAT);
				timeMillis = date.getTime();
				long currentTime = System.currentTimeMillis();
				// 超时，做修改
				if ((date.getTime() - currentTime) <= 0) {
					// 再查询一次数据库做判断
					WctAccessToken constraintWctAccessToken = wctAccessTokenService
							.queryWctAccessTokenByAppid(appId, true);
					// 线上有报空，故添加上此段if代码add by cpgu 20151125
					if (null == constraintWctAccessToken) {
						constraintWctAccessToken = new WctAccessToken();
						constraintWctAccessToken.setAccesskey(getUUID());
						timeMillis = setWctAccessToken(
								constraintWctAccessToken,
								getHttpWctToken(baseReplyResult, appId, appSec));// 设置WctAccessToken对象
						constraintWctAccessToken.setEnvironment(appId);
						wctAccessTokenService
								.addWctAccessToken(constraintWctAccessToken);
					}

					Date dataDate = DateUtil.parse(
							constraintWctAccessToken.getExpiretime(),
							DateUtil.DEFAULT_DATETIME_FORMAT);
					// 做修改
					if ((dataDate.getTime() - System.currentTimeMillis()) <= 0) {
						timeMillis = setWctAccessToken(wctAccessToken,
								getHttpWctToken(baseReplyResult, appId, appSec));
						wctAccessToken.setEnvironment(appId);
						wctAccessTokenService
								.updateWctAccessToken(wctAccessToken);
					} else {
						wctAccessToken = wctAccessTokenService
								.queryWctAccessTokenByAppid(appId,
										isCompulsoryFlag);
					}
				}

			}
		} else {
			// 第一次 请求入库
			wctAccessToken = new WctAccessToken();
			wctAccessToken.setAccesskey(getUUID());
			timeMillis = setWctAccessToken(wctAccessToken,
					getHttpWctToken(baseReplyResult, appId, appSec));// 设置WctAccessToken对象
			wctAccessToken.setEnvironment(appId);
			wctAccessTokenService.addWctAccessToken(wctAccessToken);
		}
		filterPr = new SimplePropertyPreFilter(new String[] { "accesstoken",
				"expiretime" });
		WctAccessTokenReplyReport wctAccessTokenReplyReport = new WctAccessTokenReplyReport();
		// 回复报文
		setReply(wctAccessToken, wctAccessTokenReplyReport, timeMillis);
		wctAccessTokenReplyReport.setResult(baseReplyResult);
		return wctAccessTokenReplyReport;
	}

	private void setReply(WctAccessToken wctAccessToken,
			WctAccessTokenReplyReport wctAccessTokenReplyReport, long timeMillis) {

		BaseReplyBean<WctAccessToken> wctAccessTokenBaseReplyBean = new BaseReplyBean<WctAccessToken>();
		WctAccessToken reply = new WctAccessToken();
		reply.setExpiretime(String.valueOf(timeMillis));
		reply.setAccesstoken(wctAccessToken.getAccesstoken());
		wctAccessTokenBaseReplyBean.setObj(reply);
		wctAccessTokenReplyReport.setReply(wctAccessTokenBaseReplyBean);
	}

	private long setWctAccessToken(WctAccessToken wctAccessTokenReply,
			WctAccessToken wctToken) {
		int openExpiresIn = Integer.parseInt(wctToken.getExpires_in());// 凭证有效时间
		Date date = DateUtil.add(new Date(), openExpiresIn - 1800,
				Calendar.SECOND);
		wctAccessTokenReply.setExpiretime(DateUtil.getDateTime(date,
				DateUtil.DEFAULT_DATETIME_FORMAT));
		wctAccessTokenReply.setAccesstoken(wctToken.getAccess_token());
		return date.getTime();
	}

	/**
	 * 访问微信url，获取微信token
	 * 
	 * @return WctAccessToken 微信公众号唯一票据信息
	 */
	private WctAccessToken getHttpWctToken(BaseReplyResult replyResult,
			String appid, String appsec) {
		WctAccessToken ectAccessToken = null;
		try {
			StringBuffer uriBuffer = getUri(appid, appsec);
			HttpClient client = HttpClients.createDefault();
			HttpPost post = new HttpPost(uriBuffer.toString());
			post.addHeader("Content-Type", "application/json");
			HttpResponse response = client.execute(post);
			HttpEntity entity = response.getEntity();
			String resBody = IOUtils.toString(entity.getContent(), "UTF-8");
			int statusCode = response.getStatusLine().getStatusCode();
			if (statusCode == 200) {
				ectAccessToken = JSON.toJavaObject(getJsonObject(resBody),
						WctAccessToken.class);
			} else {
				replyResult.setBusinessCode(Constant.ResultCode.FILURE);
			}
		} catch (UnsupportedEncodingException e) {
			log.error(
					"In the method WctAccessTokenAction.getWctToken() exists UnsupportedEncodingException error!",
					e);
		} catch (ClientProtocolException e) {
			log.error(
					"In the method WctAccessTokenAction.getWctToken() exists ClientProtocolException error!",
					e);
		} catch (IllegalStateException e) {
			log.error(
					"In the method WctAccessTokenAction.getWctToken() exists IllegalStateException error!",
					e);
		} catch (IOException e) {
			log.error(
					"In the method WctAccessTokenAction.getWctToken() exists IOException error!",
					e);
		} catch (Exception e) {
			log.error(
					"In the method WctAccessTokenAction.getWctToken() exists error!",
					e);
		}
		return ectAccessToken;
	}

	private StringBuffer getUri(String appid, String appsec) {
		StringBuffer uriBuffer = new StringBuffer("");
		// https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=
		uriBuffer.append(PropertiesUtil.getPropertyValue("wct_token_url"));
		uriBuffer.append(appid);
		// &secret=
		uriBuffer.append("&secret=").append(appsec);
		return uriBuffer;
	}
	
	public static void main(String[] args) {
		try {
//			StringBuffer uriBuffer = new StringBuffer("");
			// https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=
//			uriBuffer.append("https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=");
//			uriBuffer.append("wxf4a7649a7bf71c11");
//			uriBuffer.append("&secret=").append("95e56ee77c5a276c7e348dda3298b118");
//			HttpClient client = HttpClients.createDefault();
//			HttpPost post = new HttpPost(uriBuffer.toString());
//			post.addHeader("Content-Type", "application/json");
//			HttpResponse response = client.execute(post);
//			HttpEntity entity = response.getEntity();
//			String resBody = IOUtils.toString(entity.getContent(), "UTF-8");
//			int statusCode = response.getStatusLine().getStatusCode();
//			if (statusCode == 200) {
//				JSONObject jsonObject = JSON.parseObject(resBody);
//				String token = jsonObject.getString("access_token");
				String token = "6Sp2S_eKYqmRuas1WzWSO-3aIYrPxxl8lmyBDkWCTDKtxVQdsPxSe26oQTO87J9bwv9fr_VrMK4NHD0pMO59YcN-CZZCpAA5kNlfG1Y5_Gpc4CS28RfVceTmAVPiYN4OGWIiAAAMCO";
				System.out.println("token->"+token);
				
				String param = "{\"action_name\": \"QR_LIMIT_SCENE\",\"action_info\": {\"scene\": {\"scene_id\": 1}}}";
				String url = "https://api.weixin.qq.com/cgi-bin/qrcode/create?access_token="+token;
				JSONObject obj = HttpClientUtil.httpReq(url, param, null);
				System.out.println("ticket url->"+obj.toJSONString());
				String ticket = obj.getString("ticket");
				if(ticket!=null){
					url = "https://mp.weixin.qq.com/cgi-bin/showqrcode?ticket="+URL.encode(ticket).toString();
					obj = HttpClientUtil.httpReq(url, param, null);
					System.out.println("create Obj->"+obj.toJSONString());
					String qrcodeUrl = obj.getString("url");
					System.out.println("qrcodeUrl->"+qrcodeUrl);
				}else{
					System.out.println("创建二维码失败->"+obj.toJSONString());
				}
				
				/**
				 * 获取素材ID
				 */
//				String param = "{\"type\":\"news\",\"offset\":\"0\",\"count\":\"20\"}";
//				String url = "https://api.weixin.qq.com/cgi-bin/material/batchget_material?access_token="+token;
//				JSONObject obj = HttpClientUtil.httpReq(url, param, null);
//				JSONArray item =  obj.getJSONArray("item");
//				JSONArray news_item = null;
//				for (int i = 0; i < item.size(); i++) {
//					System.out.print("title->"+item.getJSONObject(i).getString("name"));
//					System.out.println("url->"+item.getJSONObject(i).getString("url"));
//					System.out.println("id->"+item.getJSONObject(i).getString("media_id"));
//					news_item = item.getJSONObject(i).getJSONObject("content").getJSONArray("news_item");
//					for (int j = 0; j < news_item.size(); j++) {
//						System.out.print("title->"+news_item.getJSONObject(j).getString("title"));
//						System.out.println("url->"+news_item.getJSONObject(j).getString("url"));
//					}
//				}
//			} else {
//				System.out.println("失败");
//			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
