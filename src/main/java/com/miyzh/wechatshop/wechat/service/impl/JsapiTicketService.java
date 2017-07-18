package com.miyzh.wechatshop.wechat.service.impl;

import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.miyzh.framework.base.Constant;
import com.miyzh.framework.util.CacheUtil;
import com.miyzh.framework.util.DateUtil;
import com.miyzh.framework.util.HttpClientUtil;
import com.miyzh.framework.util.PropertiesUtil;
import com.miyzh.framework.util.SHA1Util;
import com.miyzh.framework.util.UUIDTools;
import com.miyzh.wechatshop.wechat.bean.WctJsapiTicket;
import com.miyzh.wechatshop.wechat.dao.IWctJsapiTicketDao;
import com.miyzh.wechatshop.wechat.report.WctJsapiTicketReply;
import com.miyzh.wechatshop.wechat.service.IJsapiTicketService;
import com.miyzh.wechatshop.wechat.service.WctAccessTokenService;

/**
 * <pre>
 * the JsapiTicketService class for
 * </pre>
 *
 * @author zhangtao 2016年7月22日
 */
@Service
public class JsapiTicketService implements IJsapiTicketService {
	
	private static final String BEFORE_SIGNATURE = "jsapi_ticket=#jsapi_ticket#&noncestr=#noncestr#&timestamp=#timestamp#&url=#url#";
	
	private Logger log = Logger.getLogger(this.getClass());
	
	@Autowired
	private IWctJsapiTicketDao wctJsapiTicketDao;
	
	@Autowired
	private WctAccessTokenService accessTokenService;
	
	/* (non-Javadoc)
	 * @see com.miyzh.wechatshop.wechat.service.IJsapiTicketService#toJsapiTicket(java.lang.String, java.lang.String)
	 */
	@Override
	public WctJsapiTicketReply toJsapiTicket(String appid, String appsec,String url) throws Exception {
		WctJsapiTicket wctJsapiTicket = queryWctJsapiTicketByAppid(appid, false);
		if (null != wctJsapiTicket) {
			String expiretimeStr = wctJsapiTicket.getExpiretime();
			if (StringUtils.isNotBlank(expiretimeStr)) {
				Date date = DateUtil.parse(expiretimeStr, DateUtil.DEFAULT_DATETIME_FORMAT);
				long currentTime = System.currentTimeMillis();
				// 超时，做修改
				if ((date.getTime() - currentTime) <= 0) {
					// 再查询一次数据库做判断[目的：处于临界点时，需要使用]
					WctJsapiTicket constraintWctJsapiTicket = queryWctJsapiTicketByAppid(appid,
							true);
					// 线上有报空，故添加上此段if代码add by cpgu 20151125
					if (null == constraintWctJsapiTicket) {
						constraintWctJsapiTicket = new WctJsapiTicket();
						constraintWctJsapiTicket.setId(UUIDTools.getInstance().getUUID());
						setWctJsapiTicket(constraintWctJsapiTicket, getHttpJsapiTicket(appid, appsec));// 设置WctAccessToken对象
						constraintWctJsapiTicket.setAppid(appid);
						this.addWctJsapiTicket(constraintWctJsapiTicket);
					}
					Date dataDate = DateUtil.parse(constraintWctJsapiTicket.getExpiretime(),
							DateUtil.DEFAULT_DATETIME_FORMAT);
					// 做修改
					if ((dataDate.getTime() - System.currentTimeMillis()) <= 0) {
						setWctJsapiTicket(wctJsapiTicket, getHttpJsapiTicket(appid, appsec));
						wctJsapiTicket.setAppid(appid);
						updateWctJsapiTicket(wctJsapiTicket);
					} else {
						wctJsapiTicket = queryWctJsapiTicketByAppid(appid, false);
					}
				}

			}
		} else {
			// 第一次 请求入库
			wctJsapiTicket = new WctJsapiTicket();
			wctJsapiTicket.setId(UUIDTools.getInstance().getUUID());
			wctJsapiTicket.setAppid(appid);
			setWctJsapiTicket(wctJsapiTicket, getHttpJsapiTicket(appid, appsec));// 设置WctAccessToken对象
			addWctJsapiTicket(wctJsapiTicket);
		}
		
		WctJsapiTicketReply wctJsapiTicketReply = null;
		
		if(wctJsapiTicket != null ){
			StringBuffer keyBuffer = CacheUtil.getCachekeyMethodPrx(
					CacheUtil.cacheKey.KEY_JSAPITICKET_SIGNATURE, wctJsapiTicket.getJsapiTicket(), Thread
							.currentThread().getStackTrace()[1].getMethodName(),
					new Object[] {url});
	
			Object object = CacheUtil.getObjectValue(keyBuffer.toString());
			
			if(object==null){
				wctJsapiTicketReply =new WctJsapiTicketReply();
				
				String noncestr = PropertiesUtil.getPropertyValue("js_noncestr");
				String timestamp = System.currentTimeMillis()+"";
				String beforeSignature = BEFORE_SIGNATURE.replace("#noncestr#", noncestr);
				beforeSignature = beforeSignature.replace("#jsapi_ticket#", wctJsapiTicket.getJsapiTicket());
				beforeSignature = beforeSignature.replace("#timestamp#", timestamp);
				beforeSignature = beforeSignature.replace("#url#", url);
				
				log.info("beforeSignature->"+beforeSignature);
				
				String signature = null;
				
				signature = SHA1Util.getSHA1(beforeSignature, "");
				
				log.info("jssignature->"+signature);
				
				wctJsapiTicketReply.setNoncestr(noncestr);
				wctJsapiTicketReply.setTimestamp(timestamp);
				wctJsapiTicketReply.setSignature(signature);
				
				CacheUtil.put(keyBuffer.toString(),wctJsapiTicketReply);
			}else{
				wctJsapiTicketReply = (WctJsapiTicketReply)object;
			}
		}
		
		return wctJsapiTicketReply;
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
	public WctJsapiTicket queryWctJsapiTicketByAppid(String appid, boolean falg) {
		StringBuffer keyBuffer = CacheUtil.getCachekeyMethodPrx(
				CacheUtil.cacheKey.KEY_JSAPITICKET, appid, Thread.currentThread()
						.getStackTrace()[1].getMethodName(), new Object[] {});
		if (falg) {
			WctJsapiTicket wctJsapiTicket = wctJsapiTicketDao
					.queryWctJsapiTicketByAppid(appid);
			CacheUtil.put(keyBuffer.toString(), wctJsapiTicket);
			return wctJsapiTicket;
		}
		Object object = CacheUtil.getObjectValue(keyBuffer.toString());
		if (null == object) {
			object = wctJsapiTicketDao.queryWctJsapiTicketByAppid(appid);
			CacheUtil.put(keyBuffer.toString(), object);
		}
		return (WctJsapiTicket) object;
	}
	
	private long setWctJsapiTicket(WctJsapiTicket wctAccessTokenReply, WctJsapiTicket wctToken) {
		int openExpiresIn = Integer.parseInt(wctToken.getExpires_in());// 凭证有效时间
		Date date = DateUtil.add(new Date(), openExpiresIn - 1800, Calendar.SECOND);
		wctAccessTokenReply.setExpiretime(DateUtil.getDateTime(date,
				DateUtil.DEFAULT_DATETIME_FORMAT));
		wctAccessTokenReply.setJsapiTicket(wctToken.getTicket());
		return date.getTime();
	}
	
	private WctJsapiTicket getHttpJsapiTicket(String appid, String appsec) throws Exception{
		//// https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=[ACCESS_TOKEN]&type=jsapi
		String uriBuffer = PropertiesUtil.getPropertyValue("wct_jsapi_ticket_url");
		uriBuffer = uriBuffer.replace("[ACCESS_TOKEN]", accessTokenService.toAccessToken(appid, appsec));
		JSONObject jsonObject = HttpClientUtil.httpReq(uriBuffer, null, null);
		log.info("getHttpJsapiTicket->"+jsonObject.toJSONString());
		Assert.notNull(jsonObject);
		return JSON.toJavaObject(jsonObject, WctJsapiTicket.class);
	}
	
	public void addWctJsapiTicket(WctJsapiTicket wctJsapiTicket) throws Exception {
		this.wctJsapiTicketDao.addWctJsapiTicket(wctJsapiTicket);
		CacheUtil.removeGroupByKey(CacheUtil.cacheKey.KEY_JSAPITICKET + Constant.DBTSPLIT
				+ wctJsapiTicket.getAppid());
	}
	
	public void updateWctJsapiTicket(WctJsapiTicket wctJsapiTicket) throws Exception {
		this.wctJsapiTicketDao.updateWctAccessToken(wctJsapiTicket);
		CacheUtil.removeGroupByKey(CacheUtil.cacheKey.KEY_JSAPITICKET + Constant.DBTSPLIT
				+ wctJsapiTicket.getAppid());
	}
	
}
