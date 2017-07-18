package com.miyzh.wechatshop.wechat.service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClients;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.miyzh.framework.util.PropertiesUtil;
import com.miyzh.wechatshop.wechat.bean.WctUserInfo;

/**
 * 文件名： WctUserInfoService.java<br>
 * 描述: 微信用户基本信息<br>
 * 修改人: wumingdi<br>
 * 修改时间： 2016年7月23日 <br>
 */
@Service("wctUserInfoService")
public class WctUserInfoService {
	private Logger log = Logger.getLogger(this.getClass());

	/**
	 * 获取微信用户信息
	 * 
	 * @param openId
	 * @param accessToken
	 * @return
	 */
	public WctUserInfo getWctUserInfo(String openId, String accessToken) {
		String url = getUserInfoUrl(
				PropertiesUtil.getPropertyValue("wct_user_info_url"), openId,
				accessToken);
		WctUserInfo wctUserInfo = getHttpWctUserInfo(url);
		return wctUserInfo;

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
	public String getUserInfoUrl(String url, String openId, String accessToken) {
		StringBuffer urlBuffer = new StringBuffer("");
		urlBuffer.append(url);
		urlBuffer.append("&access_token=").append(accessToken);
		urlBuffer.append("&openid=").append(openId);
		return urlBuffer.toString();
	}

	/**
	 * 访问微信url，获取微信token
	 * 
	 * @return WctAccessToken 微信公众号唯一票据信息
	 */
	public WctUserInfo getHttpWctUserInfo(String url) {
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
	 * 得到JSONObject
	 * 
	 * @param jsonString
	 *            json字符串
	 * @return
	 */
	protected JSONObject getJsonObject(String jsonString) {
		if (StringUtils.isBlank(jsonString)) {
			return null;
		}
		log.debug("========requestReport============\n" + jsonString);
		JSONObject jsonObject = JSON.parseObject(jsonString);
		return jsonObject;
	}

}
