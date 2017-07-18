package com.miyzh.framework.util;

import java.io.IOException;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.http.client.ClientProtocolException;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

public class WechatMenu {

	private final static String appId = PropertiesUtil
			.getPropertyValue("app_id");
	private final static String app_secret = PropertiesUtil
			.getPropertyValue("app_secret");

	public static void main(String[] args) throws ClientProtocolException,
			IOException {
		String returnParam;
		String url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid="
				+ appId + "&secret=" + app_secret;
		returnParam = HttpClientUtil.clientUrl(url);
		JSONObject resBodyJSONObject = JSON.parseObject(returnParam);
		// 获取access_token
		String accessToken = resBodyJSONObject.get("access_token").toString();
		// 删除菜单
		String delUrl = "https://api.weixin.qq.com/cgi-bin/menu/delete?access_token="
				+ accessToken;
		returnParam = HttpClientUtil.clientUrl(delUrl);
		System.out.println(returnParam);
		// 组织数据创建菜单
		String url1 = " https://api.weixin.qq.com/cgi-bin/menu/create?access_token="
				+ accessToken;
		// 微信测试号
		String responeJsonStr ="{\"button\":[{\"name\":\"明医购\",\"sub_button\":[{\"type\":\"view\",\"name\":\"正在疯抢\",\"url\":\"https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxf4a7649a7bf71c11&redirect_uri=http%3a%2f%2fshop.miyzh.com%2fMIYZHInterface%2fhtml%2fgroupbuy%2findex.html&response_type=code&scope=snsapi_base&state=123#wechat_redirect\"},{\"type\":\"view\",\"name\":\"往期回顾\",\"url\":\"https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxf4a7649a7bf71c11&redirect_uri=http%3a%2f%2fshop.miyzh.com%2fMIYZHInterface%2fhtml%2fgroupbuy%2fgroupbuy_end.html&response_type=code&scope=snsapi_base&state=1#wechat_redirect\"},{\"type\":\"view\",\"name\":\"精彩预告\",\"url\":\"https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxf4a7649a7bf71c11&redirect_uri=http%3a%2f%2fshop.miyzh.com%2fMIYZHInterface%2fhtml%2fgroupbuy%2fgroupbuy_nostart.html&response_type=code&scope=snsapi_base&state=1#wechat_redirect\"}]},{\"type\":\"view\",\"name\":\"历史回顾\",\"url\":\"http://mp.weixin.qq.com/mp/getmasssendmsg?__biz=MzA5MjUxNDc0NQ==#wechat_webview_type=1&wechat_redirect\"},{\"name\":\"个人中心\",\"sub_button\":[{\"type\":\"view\",\"name\":\"我要注册\",\"url\":\"https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxf4a7649a7bf71c11&redirect_uri=http%3a%2f%2fshop.miyzh.com%2fMIYZHInterface%2fhtml%2fregister%2fregister_baffle.html&response_type=code&scope=snsapi_base&state=123#wechat_redirect\"},{\"type\":\"view\",\"name\":\"我的订单\",\"url\":\"https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxf4a7649a7bf71c11&redirect_uri=http%3A%2F%2Fshop.miyzh.com%2FMIYZHInterface%2Fhtml%2Forder%2Forder_list.html&response_type=code&scope=snsapi_base&state=123#wechat_redirect\"},{\"type\":\"view\",\"name\":\"会员信息\",\"url\":\"https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxf4a7649a7bf71c11&redirect_uri=http%3a%2f%2fshop.miyzh.com%2fMIYZHInterface%2fhtml%2fmember%2fmember_baffle.html&response_type=code&scope=snsapi_base&state=1#wechat_redirect\"},{\"type\":\"media_id\",\"name\":\"注册帮助\",\"media_id\":\"b4mGcuLYhJiXzgtpGcNjNlGG7pvZhlhWGdlVL8l8uKc\"}]}";
//		String responeJsonStr = "{\"button\":[{\"name\":\"明医购\",\"sub_button\":[{\"type\":\"view\",\"name\":\"正在疯抢\",\"url\":\"https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx5c18ba573fac02cc&redirect_uri=http%3A%2F%2Fsc.miyzh.com%2FMIYZHInterface%2Fhtml%2Fgroupbuy%2Findex.html&response_type=code&scope=snsapi_base&state=123#wechat_redirect\"},{\"type\":\"view\",\"name\":\"往期回顾\",\"url\":\"https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx5c18ba573fac02cc&redirect_uri=http%3a%2f%2fsc.miyzh.com%2fMIYZHInterface%2fhtml%2fgroupbuy%2fgroupbuy_end.html&response_type=code&scope=snsapi_base&state=1#wechat_redirect\"},{\"type\":\"view\",\"name\":\"精彩预告\",\"url\":\"https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx5c18ba573fac02cc&redirect_uri=http%3a%2f%2fsc.miyzh.com%2fMIYZHInterface%2fhtml%2fgroupbuy%2fgroupbuy_nostart.html&response_type=code&scope=snsapi_base&state=1#wechat_redirect\"}]},{\"name\":\"客服帮助\",\"sub_button\":[{\"type\":\"view\",\"name\":\"中心布局\",\"url\":\"https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx7edd615f2758242b&redirect_uri=http://103.233.128.195/wechat/app/http/wechat/publicJumpHandler/publicJump?JumpType=M2004&response_type=code&scope=snsapi_base&state=1#wechat_redirect\"}]},{\"name\":\"个人中心\",\"sub_button\":[{\"type\":\"view\",\"name\":\"我要注册\",\"url\":\"https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx5c18ba573fac02cc&redirect_uri=http%3a%2f%2fsc.miyzh.com%2fMIYZHInterface%2fhtml%2fregister%2fregister_baffle.html&response_type=code&scope=snsapi_base&state=123#wechat_redirect\"},{\"type\":\"view\",\"name\":\"我的订单\",\"url\":\"https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx5c18ba573fac02cc&redirect_uri=http%3A%2F%2Fsc.miyzh.com%2FMIYZHInterface%2Fhtml%2Forder%2Forder_list.html&response_type=code&scope=snsapi_base&state=123#wechat_redirect\"},{\"type\":\"view\",\"name\":\"会员信息\",\"url\":\"https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx5c18ba573fac02cc&redirect_uri=http%3a%2f%2fsc.miyzh.com%2fMIYZHInterface%2fhtml%2fmember%2fmember_baffle.html&response_type=code&scope=snsapi_base&state=1#wechat_redirect\"},{\"type\":\"view\",\"name\":\"调查赢奖\",\"url\":\"https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx7edd615f2758242b&redirect_uri=http://103.233.128.195/wechat/app/http/wechat/publicJumpHandler/publicJump?JumpType=M3004&response_type=code&scope=snsapi_base&state=1#wechat_redirect\"}]}]}";
//		String responeJsonStr ="{\"button\":[{\"name\":\"明医购\",\"sub_button\":[{\"type\":\"view\",\"name\":\"正在疯抢\",\"url\":\"https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxf4a7649a7bf71c11&redirect_uri=http%3a%2f%2fshop.miyzh.com%2fMIYZHInterface%2fhtml%2fgroupbuy%2findex.html&response_type=code&scope=snsapi_base&state=123#wechat_redirect\"},{\"type\":\"view\",\"name\":\"往期回顾\",\"url\":\"https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxf4a7649a7bf71c11&redirect_uri=http%3a%2f%2fshop.miyzh.com%2fMIYZHInterface%2fhtml%2fgroupbuy%2fgroupbuy_end.html&response_type=code&scope=snsapi_base&state=1#wechat_redirect\"},{\"type\":\"view\",\"name\":\"精彩预告\",\"url\":\"https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxf4a7649a7bf71c11&redirect_uri=http%3a%2f%2fshop.miyzh.com%2fMIYZHInterface%2fhtml%2fgroupbuy%2fgroupbuy_nostart.html&response_type=code&scope=snsapi_base&state=1#wechat_redirect\"}]},{\"type\":\"view\",\"name\":\"历史回顾\",\"url\":\"http://mp.weixin.qq.com/mp/getmasssendmsg?__biz=MzA5MjUxNDc0NQ==#wechat_webview_type=1&wechat_redirect\"},{\"name\":\"个人中心\",\"sub_button\":[{\"type\":\"view\",\"name\":\"我要注册\",\"url\":\"https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxf4a7649a7bf71c11&redirect_uri=http%3a%2f%2fshop.miyzh.com%2fMIYZHInterface%2fhtml%2fregister%2fregister_baffle.html&response_type=code&scope=snsapi_base&state=123#wechat_redirect\"},{\"type\":\"view\",\"name\":\"我的订单\",\"url\":\"https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxf4a7649a7bf71c11&redirect_uri=http%3A%2F%2Fshop.miyzh.com%2FMIYZHInterface%2Fhtml%2Forder%2Forder_list.html&response_type=code&scope=snsapi_base&state=123#wechat_redirect\"},{\"type\":\"view\",\"name\":\"会员信息\",\"url\":\"https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxf4a7649a7bf71c11&redirect_uri=http%3a%2f%2fshop.miyzh.com%2fMIYZHInterface%2fhtml%2fmember%2fmember_baffle.html&response_type=code&scope=snsapi_base&state=1#wechat_redirect\"},{\"type\":\"view\",\"name\":\"注册帮助\",\"url\":\"http://mp.weixin.qq.com/s?__biz=MzA5MjUxNDc0NQ==&mid=502646594&idx=1&sn=597d246e9eb214d9d671b2562fcf048d#rd\"}]}";
		HttpClient client = new HttpClient();
		PostMethod post = new PostMethod(url1);
		post.setRequestBody(responeJsonStr);
		post.getParams().setContentCharset("utf-8");
		// 发送http请求
		String respStr = "";
		client.executeMethod(post);
		respStr = post.getResponseBodyAsString();
		System.out.println(responeJsonStr);
		System.out.println(respStr);
	}

	// 正式环境
//	String responeJsonStr ="{\"button\":[{\"name\":\"明医购\",\"sub_button\":[{\"type\":\"view\",\"name\":\"正在疯抢\",\"url\":\"https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxf4a7649a7bf71c11&redirect_uri=http%3a%2f%2fshop.miyzh.com%2fMIYZHInterface%2fhtml%2fgroupbuy%2findex.html&response_type=code&scope=snsapi_base&state=123#wechat_redirect\"},{\"type\":\"view\",\"name\":\"往期回顾\",\"url\":\"https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxf4a7649a7bf71c11&redirect_uri=http%3a%2f%2fshop.miyzh.com%2fMIYZHInterface%2fhtml%2fgroupbuy%2fgroupbuy_end.html&response_type=code&scope=snsapi_base&state=1#wechat_redirect\"},{\"type\":\"view\",\"name\":\"精彩预告\",\"url\":\"https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxf4a7649a7bf71c11&redirect_uri=http%3a%2f%2fshop.miyzh.com%2fMIYZHInterface%2fhtml%2fgroupbuy%2fgroupbuy_nostart.html&response_type=code&scope=snsapi_base&state=1#wechat_redirect\"}]},{\"type\":\"view\",\"name\":\"历史回顾\",\"url\":\"http://mp.weixin.qq.com/mp/getmasssendmsg?__biz=MzA5MjUxNDc0NQ==#wechat_webview_type=1&wechat_redirect\"},{\"name\":\"个人中心\",\"sub_button\":[{\"type\":\"view\",\"name\":\"我要注册\",\"url\":\"https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxf4a7649a7bf71c11&redirect_uri=http%3a%2f%2fshop.miyzh.com%2fMIYZHInterface%2fhtml%2fregister%2fregister_baffle.html&response_type=code&scope=snsapi_base&state=123#wechat_redirect\"},{\"type\":\"view\",\"name\":\"我的订单\",\"url\":\"https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxf4a7649a7bf71c11&redirect_uri=http%3A%2F%2Fshop.miyzh.com%2FMIYZHInterface%2Fhtml%2Forder%2Forder_list.html&response_type=code&scope=snsapi_base&state=123#wechat_redirect\"},{\"type\":\"view\",\"name\":\"会员信息\",\"url\":\"https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxf4a7649a7bf71c11&redirect_uri=http%3a%2f%2fshop.miyzh.com%2fMIYZHInterface%2fhtml%2fmember%2fmember_baffle.html&response_type=code&scope=snsapi_base&state=1#wechat_redirect\"},{\"type\":\"media_id\",\"name\":\"注册帮助\",\"media_id\":\"b4mGcuLYhJiXzgtpGcNjNlGG7pvZhlhWGdlVL8l8uKc\"}]}";

}
