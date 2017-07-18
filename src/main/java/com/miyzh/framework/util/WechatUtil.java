package com.miyzh.framework.util;

import java.io.UnsupportedEncodingException;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.util.Assert;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.miyzh.framework.base.Constant;

/**
 * 文件名: WechatUtil.java<br>
 * 版权：Copyright 2014 Digital Bay Technology Group. Co. Ltd. All Rights Reserved. <br>
 * 描述: 微信相关util类 <br>
 * 修改人: guchangpeng <br>
 * 修改时间：2014-7-14 下午02:23:40 <br>
 * 修改内容：新增 <br>
 */
public class WechatUtil {

	private static Logger logger = Logger.getLogger(WechatUtil.class);

	/**
	 * 发送消息业务类型
	 * 
	 * @date 2014-07-14
	 */
	public static interface msgBusinessType {
		/** 成功上传小票发送客服消息:1 **/
		public static int TYPE_1 = 1;
		/** 海军贴标重复扫码，发送客服消息 **/
		public static int TYPE_2 = 2;
		/** 图文详情 **/
		public static int TYPE_100 = 100;
		/** 模板消息：10-抢到红包 **/
		public static int TEMPLATE_ROBGIFT = 10;
	}

	/**
	 * 消息类型
	 * 
	 * @date 2014-07-14
	 */
	public static interface msgType {
		/** 发送文本消息 **/
		public static String TYPE_TEXT = "text";
		/** 发送图片消息 **/
		public static String TYPE_IMAGE = "image";
		/** 发送语音消息 **/
		public static String TYPE_VOICE = "voice";
		/** 发送视频消息 **/
		public static String TYPE_VIDEO = "video";
		/** 发送音乐消息 **/
		public static String TYPE_MUSIC = "music";
		/** 发送图文消息 **/
		public static String TYPE_NEWS = "news";
	}

	/**
	 * 发送消息给微信用户
	 * 
	 * @param msgBusinessType
	 *            发送消息业务类型
	 * @param openid
	 *            微信唯一id
	 * @param msgType
	 *            消息类型，默认为文本消息，传入null，为文本类型
	 * @param thirdpParameter
	 *            动态参数，为null、或为""，则表示没有第三方参数内容
	 * @param flag
	 *            业务是否成功 true ：成功 flase：失败
	 * @return 发送是否成功
	 */
	public static boolean sendMsg(int msgBusinessType, String openid,
			String msgType, String thirdpParameter, String accesstoken) {
		// 得到accessTokenReplyJsonObject对象
		String sendText = setMsgText(msgBusinessType, openid, msgType,
				thirdpParameter);// 发送消息内容
		JSONObject sendCustomerServiceJsonObject = HttpClientUtil.httpReq(
				PropertiesUtil.getPropertyValue("send_customer_service_msg")
						+ accesstoken, sendText, null);// 发送客服消息回复报文
		return sendCustomerServiceJsonObject == null ? false : true;
	}

	/**
	 * 设置发送消息内容
	 * 
	 * @param msgBusinessType
	 * @param openid
	 * @param msgType
	 * @return
	 */
	private static String setMsgText(int msgBusinessType, String openid,
			String msgType, String thirdpParameter) {
		msgType = StringUtils.isBlank(msgType) ? WechatUtil.msgType.TYPE_TEXT
				: msgType;
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("touser", openid);
		jsonObject.put("msgtype", msgType);
		JSONObject contentJsonObject = new JSONObject();
		// 文本类型
		if (WechatUtil.msgType.TYPE_TEXT.equals(msgType)) {
			switch (msgBusinessType) {
			case WechatUtil.msgBusinessType.TYPE_1:
				String content = DataDicmUtil
						.getDataDicmValue(
								DataDicmUtil.dataDicCategory.CATEGROY_CODE_WECHAT_MSG,
								DataDicmUtil.dataDic.wechat_msg.UPLOAD_RECEIPT_SERIVCE_MSG);
				contentJsonObject.put("content", content);
				break;
			case WechatUtil.msgBusinessType.TYPE_2:
				contentJsonObject.put("content", thirdpParameter);
				break;
			}
			jsonObject.put("text", contentJsonObject);
		}
		// 图文详情
		else if (WechatUtil.msgType.TYPE_NEWS.equals(msgType)) {
			JSONArray articlesJsonArr = new JSONArray();
			JSONObject articlesJsonObject = new JSONObject();
			switch (msgBusinessType) {
			case WechatUtil.msgBusinessType.TYPE_100:
				thirdpParameter = null == thirdpParameter ? ""
						: thirdpParameter;
				String[] thirdpParameterArr = thirdpParameter
						.split(Constant.THREEPAR_SPLIT_CHART);
				if (null != thirdpParameterArr) {
					// title,description,url,picurl
					if (thirdpParameterArr.length > 0) {
						articlesJsonObject.put("title", thirdpParameterArr[0]);
					}
					if (thirdpParameterArr.length > 1) {
						articlesJsonObject.put("description",
								thirdpParameterArr[1]);
					}
					if (thirdpParameterArr.length > 2) {
						articlesJsonObject.put("url", thirdpParameterArr[2]);
					}
					if (thirdpParameter.length() > 3) {
						articlesJsonObject.put("picurl", thirdpParameterArr[3]);
					}
				}
				articlesJsonArr.add(articlesJsonObject);
				contentJsonObject.put("articles", articlesJsonArr);
				break;
			}
			jsonObject.put("news", contentJsonObject);
		}
		return jsonObject.toJSONString();
	}

	/**
	 * <pre>
	 * 微信服务器类型
	 * </pre>
	 *
	 * @author zhangtao 2015年10月28日
	 */
	public static interface WECHAT_SERVER_TYPE {
		/** V积分微信服务器 **/
		public static final int VJIFEN = 0;

		/** 虚拟会员卡微信服务器 **/
		public static final int VIRTUALMEMBER = 1;
	}

	/**
	 * 发送模板消息
	 * 
	 * @param msgBusinessType
	 * @param openid
	 * @param thirdpParameter
	 * @param accesstoken
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static boolean sendTemplateMsg(int msgBusinessType, String openid,
			String thirdpParameter, int wechatServerType, String accesstoken)
			throws UnsupportedEncodingException {
		// 主业务接口自己可以获取
		// JSONObject replyJsonObject =
		// getAccessTokenReplyJsonObject(msgBusinessType,wechatServerType);
		// Assert.notNull(replyJsonObject);
		// String accesstoken = replyJsonObject.getString("accesstoken");
		// 得到accessTokenReplyJsonObject对象
		String sendText = setTemplateMsg(msgBusinessType, openid,
				thirdpParameter, wechatServerType);// 发送消息内容
		JSONObject sendCustomerServiceJsonObject = HttpClientUtil.httpReq(
				PropertiesUtil.getPropertyValue("send_template_service_msg")
						+ accesstoken, sendText, null);// 发送客服消息回复报文
		logger.info("微信模板消息发送状态-->" + sendCustomerServiceJsonObject.toString());
		return sendCustomerServiceJsonObject == null ? false : true;
	}

	public static JSONObject getAccessTokenReplyJsonObject(int msgBusinessType,
			int wechatServerType) {
		JSONObject accessTokenJsonObject = HttpClientUtil.httpReq(
				getInterfaceUri(msgBusinessType),
				setInterfaceMsgText(msgBusinessType, wechatServerType), null);// 获取accessToken
		Assert.notNull(accessTokenJsonObject);
		JSONObject replyJsonObject = accessTokenJsonObject
				.getJSONObject("reply");
		return replyJsonObject;
	}

	/**
	 * 根据类型获取接口端url
	 * 
	 * @param type
	 * @return
	 */
	private static String getInterfaceUri(int type) {
		StringBuilder sbBuilder = new StringBuilder(120);
		sbBuilder.append(PropertiesUtil.getPropertyValue("interface_url"));
		// 调用发送客服消息接口
		if (type == WechatUtil.msgBusinessType.TEMPLATE_ROBGIFT) {
			sbBuilder.append(PropertiesUtil
					.getPropertyValue("wechat_accesstoken_url"));
		}
		return sbBuilder.toString();
	}

	/**
	 * 设置接口消息参数内容
	 * 
	 * @param msgBusinessType
	 * @return
	 */
	private static String setInterfaceMsgText(int type, int wechatServerType) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("commandType", "getAccessToken");
		JSONObject commandInfoJsonObject = new JSONObject();
		if (type == WechatUtil.msgBusinessType.TEMPLATE_ROBGIFT) {
			switch (wechatServerType) {
			case WechatUtil.WECHAT_SERVER_TYPE.VJIFEN:
				commandInfoJsonObject.put("appid",
						PropertiesUtil.getPropertyValue("vjf_appid"));
				commandInfoJsonObject.put("appsec",
						PropertiesUtil.getPropertyValue("vjf_appsec"));
				jsonObject.put("commandInfo", commandInfoJsonObject);
				break;
			case WechatUtil.WECHAT_SERVER_TYPE.VIRTUALMEMBER:
				commandInfoJsonObject.put("appid",
						PropertiesUtil.getPropertyValue("vm_appid"));
				commandInfoJsonObject.put("appsec",
						PropertiesUtil.getPropertyValue("vm_addsec"));
				jsonObject.put("commandInfo", commandInfoJsonObject);
				break;
			default:
				commandInfoJsonObject.put("appid",
						PropertiesUtil.getPropertyValue("vjf_appid"));
				commandInfoJsonObject.put("appsec",
						PropertiesUtil.getPropertyValue("vjf_appsec"));
				jsonObject.put("commandInfo", commandInfoJsonObject);
				break;
			}
		}
		return jsonObject.toJSONString();
	}

	private static String setTemplateMsg(int msgBusinessType, String openid,
			String thirdpParameter, int wechatServerType)
			throws UnsupportedEncodingException {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("touser", openid);
		JSONObject dataJsonObject = new JSONObject();
		switch (msgBusinessType) {
		/**
		 * 领取红包模板消息 {{first.DATA}} <br>
		 * 领取金额：{{keyword1.DATA}} <br>
		 * 领取时间：{{keyword2.DATA}} <br>
		 * {{remark.DATA}}
		 */
		case WechatUtil.msgBusinessType.TEMPLATE_ROBGIFT:
			if (WechatUtil.WECHAT_SERVER_TYPE.VIRTUALMEMBER == wechatServerType) {
				jsonObject
						.put("template_id",
								PropertiesUtil
										.getPropertyValue("virtual_member_redPacket_template_id"));
			} else {
				jsonObject
						.put("template_id",
								DataDicmUtil
										.getDataDicmValue(
												DataDicmUtil.dataDicCategory.CATEGROY_GIFTSPACK_MSGREMIND_CONFIG,
												DataDicmUtil.dataDic.giftspack_msgremind_config.MSGREMIND_TEMPLATE_ID));
			}
			jsonObject
					.put("topcolor",
							DataDicmUtil
									.getDataDicmValue(
											DataDicmUtil.dataDicCategory.CATEGROY_GIFTSPACK_MSGREMIND_CONFIG,
											DataDicmUtil.dataDic.giftspack_msgremind_config.MSGREMIND_TOPCOLOR));
			// url,企业key,奖品名称,领取时间
			if (null != thirdpParameter) {
				String[] thirdArr = thirdpParameter.split("#@");
				if (thirdArr.length > 0) {
					if (!thirdArr[0].equals("#")) {
						jsonObject.put("url", thirdArr[0]);
					}
				}
				JSONObject firstJsonObject = new JSONObject();
				if (thirdArr.length > 1) {
					// put消息内容
					firstJsonObject.put("value", thirdArr[1]);
					firstJsonObject
							.put("color",
									DataDicmUtil
											.getDataDicmValue(
													DataDicmUtil.dataDicCategory.CATEGROY_GIFTSPACK_MSGREMIND_CONFIG,
													DataDicmUtil.dataDic.giftspack_msgremind_config.MSGREMIND_DATA_FIRST_COLOR));
					dataJsonObject.put("first", firstJsonObject);
				}
				JSONObject keyword1JsonObject = new JSONObject();
				// 领取金额【调取奖品名称】
				if (thirdArr.length > 2) {
					keyword1JsonObject.put(
							"value",
							thirdArr[2]
									.replaceAll("<br/>", "")
									.replaceAll("<span style=font-size:14px>",
											"").replaceAll("</span>", ""));
					keyword1JsonObject
							.put("color",
									DataDicmUtil
											.getDataDicmValue(
													DataDicmUtil.dataDicCategory.CATEGROY_GIFTSPACK_MSGREMIND_CONFIG,
													DataDicmUtil.dataDic.giftspack_msgremind_config.MSGREMIND_DATA_FIRST_COLOR));
					dataJsonObject.put("keyword1", keyword1JsonObject);
				}
				JSONObject keyword2JsonObject = new JSONObject();
				if (thirdArr.length > 3) {
					keyword2JsonObject.put("value", thirdArr[3]);
					keyword2JsonObject
							.put("color",
									DataDicmUtil
											.getDataDicmValue(
													DataDicmUtil.dataDicCategory.CATEGROY_GIFTSPACK_MSGREMIND_CONFIG,
													DataDicmUtil.dataDic.giftspack_msgremind_config.MSGREMIND_DATA_FIRST_COLOR));
					dataJsonObject.put("keyword2", keyword2JsonObject);
				}
				jsonObject.put("data", dataJsonObject);
			}
			break;

		default:
			break;
		}
		return jsonObject.toJSONString();
	}

	public static void main(String[] args) {
		String strArr[] = new String[] { "oIwlot8Bgm1ggmP1YpC6RdEbI59k",
				"oIwlot7O3Fhrqsdqu4pNmC7Uwy_8", "oIwlot0h5c3uurDD2A0WhLI8JcfE",
				"oIwlot8B6bQ-8yknhx4k00f73YkE", "oIwlot-2cXYwbD4SbUqIcaqCXcW4",
				"oIwlot2xRMtirl7Qb0_jgd38dNLQ", "oIwlot5mCYlPtZmFYu_EZMkPFCM8",
				"oIwlot1OKDev7ODMZ8hWeQREJUyI", "oIwlot-qjJYzMSBQiezhPFy1OmaA",
				"oIwlot7e0YUq0SIsOG4KNYKJ7C_s", "oIwlot4XQomAMOcwOaa1NfpU57Sk",
				"oIwlot2gDqglpKddxiMOvzRQ6YxE", "oIwlot8oHSUyapVW5j6Rcek8UmCg",
				"oIwlot0bAjGIn44qizqWF6tNFosU", "oIwlot52Nch4x359WiNbjepBV_Lk" };
	}
}
