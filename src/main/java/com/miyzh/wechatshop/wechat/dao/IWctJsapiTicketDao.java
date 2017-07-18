package com.miyzh.wechatshop.wechat.dao;

import com.miyzh.wechatshop.wechat.bean.WctJsapiTicket;

public interface IWctJsapiTicketDao {
	
	public WctJsapiTicket queryWctJsapiTicketByAppid(String appid);
	
	public void addWctJsapiTicket(WctJsapiTicket t);
	
	public void updateWctAccessToken(WctJsapiTicket t);

}
