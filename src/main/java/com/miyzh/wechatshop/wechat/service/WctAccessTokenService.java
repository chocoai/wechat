package com.miyzh.wechatshop.wechat.service;


import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.miyzh.framework.base.Constant;
import com.miyzh.framework.util.CacheUtil;
import com.miyzh.framework.util.DateUtil;
import com.miyzh.framework.util.HttpClientUtil;
import com.miyzh.framework.util.PropertiesUtil;
import com.miyzh.framework.util.UUIDTools;
import com.miyzh.wechatshop.wechat.bean.WctAccessToken;
import com.miyzh.wechatshop.wechat.dao.impl.WctAccessTokenDao;

/**
 * 文件名：WctAccessTokenService.java<br>
 * 版权：Copyright 2014 Digital Bay Technology Group. Co. Ltd. All Rights Reserved. <br>
 * 描述: 微信公众号唯一票据信息<br>
 * 修改人: guchangpeng<br>
 * 修改时间：2014-07-03 17:57:34<br>
 * 修改内容：新增<br>
 */
@Service("wctAccessTokenService")
public class WctAccessTokenService {
	@Autowired
	private WctAccessTokenDao wctAccessTokenDao;

	public WctAccessToken findWctAccessTokenById(String id) {
		return this.wctAccessTokenDao.findWctAccessTokenById(id);
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void addWctAccessToken(WctAccessToken wctAccessToken) throws Exception {
		this.wctAccessTokenDao.addWctAccessToken(wctAccessToken);
		CacheUtil.removeGroupByKey(CacheUtil.cacheKey.KEY_WCTACCESSTOKEN + Constant.DBTSPLIT
				+ wctAccessToken.getEnvironment());
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void updateWctAccessToken(WctAccessToken wctAccessToken) throws Exception {
		this.wctAccessTokenDao.updateWctAccessToken(wctAccessToken);
		CacheUtil.removeGroupByKey(CacheUtil.cacheKey.KEY_WCTACCESSTOKEN + Constant.DBTSPLIT
				+ wctAccessToken.getEnvironment());
	}

	/**
	 * 根据appid获取公众号token
	 */
	public String toAccessToken(String appid, String appsec) throws Exception {
		WctAccessToken wctAccessToken = queryWctAccessTokenByAppid(appid, false);
		if (null != wctAccessToken) {
			String expiretimeStr = wctAccessToken.getExpiretime();
			if (StringUtils.isNotBlank(expiretimeStr)) {
				Date date = DateUtil.parse(expiretimeStr, DateUtil.DEFAULT_DATETIME_FORMAT);
				long currentTime = System.currentTimeMillis();
				// 超时，做修改
				if ((date.getTime() - currentTime) <= 0) {
					// 再查询一次数据库做判断[目的：处于临界点时，需要使用]
					WctAccessToken constraintWctAccessToken = queryWctAccessTokenByAppid(appid,
							true);
					// 线上有报空，故添加上此段if代码add by cpgu 20151125
					if (null == constraintWctAccessToken) {
						constraintWctAccessToken = new WctAccessToken();
						constraintWctAccessToken.setAccesskey(UUIDTools.getInstance().getUUID());
						setWctAccessToken(constraintWctAccessToken, getHttpWctToken(appid, appsec));// 设置WctAccessToken对象
						constraintWctAccessToken.setEnvironment(appid);
						this.addWctAccessToken(constraintWctAccessToken);

					}
					Date dataDate = DateUtil.parse(constraintWctAccessToken.getExpiretime(),
							DateUtil.DEFAULT_DATETIME_FORMAT);
					// 做修改
					if ((dataDate.getTime() - System.currentTimeMillis()) <= 0) {
						setWctAccessToken(wctAccessToken, getHttpWctToken(appid, appsec));
						wctAccessToken.setAppid(appid);
						wctAccessToken.setEnvironment(appid);
						updateWctAccessToken(wctAccessToken);
					} else {
						wctAccessToken = queryWctAccessTokenByAppid(appid, false);
					}
				}

			}
		} else {
			// 第一次 请求入库
			wctAccessToken = new WctAccessToken();
			wctAccessToken.setAccesskey(UUIDTools.getInstance().getUUID());
			wctAccessToken.setEnvironment(appid);
			setWctAccessToken(wctAccessToken, getHttpWctToken(appid, appsec));// 设置WctAccessToken对象
			addWctAccessToken(wctAccessToken);
		}
		return wctAccessToken == null ? null : wctAccessToken.getAccesstoken();
	}

	private long setWctAccessToken(WctAccessToken wctAccessTokenReply, WctAccessToken wctToken) {
		int openExpiresIn = Integer.parseInt(wctToken.getExpires_in());// 凭证有效时间
		Date date = DateUtil.add(new Date(), openExpiresIn - 1800, Calendar.SECOND);
		wctAccessTokenReply.setExpiretime(DateUtil.getDateTime(date,
				DateUtil.DEFAULT_DATETIME_FORMAT));
		wctAccessTokenReply.setAccesstoken(wctToken.getAccess_token());
		return date.getTime();
	}

	private WctAccessToken getHttpWctToken(String appid, String appsec) {
		StringBuffer uriBuffer = getUri(appid, appsec);
		JSONObject jsonObject = HttpClientUtil.httpReq(uriBuffer.toString(), null, null);
		Assert.notNull(jsonObject);
		return JSON.toJavaObject(jsonObject, WctAccessToken.class);
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

	/**
	 * 根据appid查询公众号唯一票据
	 * 
	 * @param appid
	 *            appid
	 * @param falg
	 *            是否强制
	 * @return WctAccessToken 对象
	 */
	public WctAccessToken queryWctAccessTokenByAppid(String appid, boolean falg) {
		StringBuffer keyBuffer = CacheUtil.getCachekeyMethodPrx(
				CacheUtil.cacheKey.KEY_WCTACCESSTOKEN, appid, Thread.currentThread()
						.getStackTrace()[1].getMethodName(), new Object[] { appid, falg });
		if (falg) {
			WctAccessToken wctAccessToken = this.wctAccessTokenDao
					.queryWctAccessTokenByAppid(appid);
			CacheUtil.put(keyBuffer.toString(), wctAccessToken);
			return wctAccessToken;
		}
		Object object = CacheUtil.getObjectValue(keyBuffer.toString());
		if (null == object) {
			object = this.wctAccessTokenDao.queryWctAccessTokenByAppid(appid);
			CacheUtil.put(keyBuffer.toString(), object);
		}
		return (WctAccessToken) object;
	}

}
