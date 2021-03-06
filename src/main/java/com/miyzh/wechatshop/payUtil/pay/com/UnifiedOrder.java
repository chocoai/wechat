package com.miyzh.wechatshop.payUtil.pay.com;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.SortedMap;
import java.util.TreeMap;

import com.miyzh.framework.util.PropertiesUtil;
import com.miyzh.wechatshop.payUtil.pay.utils.GetWxOrderno;
import com.miyzh.wechatshop.payUtil.pay.utils.RequestHandler;
import com.miyzh.wechatshop.payUtil.pay.utils.Sha1Util;
import com.miyzh.wechatshop.payUtil.pay.utils.TenpayUtil;





/**
 * @author weifb
 * 
 */
public class UnifiedOrder {
	private static String appid = PropertiesUtil.getPropertyValue("app_id");	
	private static String appsecret = PropertiesUtil.getPropertyValue("app_secret");	
	private static String partner = "1332506201";
	//这个参数partnerkey是在商户后台配置的一个32位的key,微信商户平台-账户设置-安全设置-api安全
	private static String partnerkey = "0MOPDIPP79M2A9DKM4IB08IFPQ5MLDCW";
	//openId 是微信用户针对公众号的标识，授权的部分这里不解释
	@SuppressWarnings("unused")
	private static String openId = "ouQdUuIUZtLvyd0t5hLRILWJl25k";	 // Key

	private static String notifyurl = PropertiesUtil.getPropertyValue("notifyUrl");			
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		//微信支付jsApi
//				WxPayDto tpWxPay = new WxPayDto();
//				tpWxPay.setOpenId(openId);
//				tpWxPay.setBody("商品信息");
//				tpWxPay.setOrderId(getNonceStr());
//				tpWxPay.setSpbillCreateIp("127.0.0.1");
//				tpWxPay.setTotalFee("0.01");
//			 System.out.println(getPackage(tpWxPay));
			    
			    //扫码支付
			    WxPayDto tpWxPay1 = new WxPayDto();
			    tpWxPay1.setBody("商品信息");
			    tpWxPay1.setOrderId("2222");
			    tpWxPay1.setSpbillCreateIp("127.0.0.1");
			    tpWxPay1.setTotalFee("0.01");
			    tpWxPay1.setEndDate(getCurrTime("1"));
				getCodeurl(tpWxPay1);

	}
	
	
	
	/**
	 * 调取微信申请退款接口
	 */
	@SuppressWarnings("static-access")
	public static String getRefund(WxRefundDto wxRefundDto){
		
		// 1 参数
		// 退费单号
		String orderId = wxRefundDto.getRefundCode();
		// 总金额以分为单位，不带小数点
		String totalFee = getMoney(wxRefundDto.getRefundMony());

		// 商户号
		String mch_id = partner;
		// 随机字符串
		String nonce_str = getNonceStr();

		// 微信订单号
		String transaction_id = wxRefundDto.getWechatCode();

		SortedMap<String, String> packageParams = new TreeMap<String, String>();
		packageParams.put("appid", appid);
		packageParams.put("mch_id", mch_id);
		packageParams.put("nonce_str", nonce_str);
		packageParams.put("transaction_id", transaction_id);
		packageParams.put("out_refund_no", orderId);
		
		packageParams.put("total_fee", totalFee);
		packageParams.put("refund_fee", totalFee);
		packageParams.put("op_user_id", mch_id);


		RequestHandler reqHandler = new RequestHandler(null, null);
		reqHandler.init(appid, appsecret, partnerkey);
		
		String sign = reqHandler.createSign(packageParams);
		String xml = "<xml>" + "<appid>" + appid + "</appid>" + "<mch_id>"
				+ mch_id + "</mch_id>" + "<nonce_str>" + nonce_str
				+ "</nonce_str>" 
				+ "<op_user_id>" + mch_id
				+ "</op_user_id>" 
				+ "<out_refund_no>" + orderId + "</out_refund_no>"
					+ "<transaction_id>" + transaction_id + "</transaction_id>"
				+ "<refund_fee>" + totalFee
				+ "</refund_fee>" + "<total_fee>" + totalFee
				+ "</total_fee>" 
				+ "<sign>" + sign + "</sign>" +"</xml>";
		String resultXml = "";
		
		String createOrderURL = "https://api.mch.weixin.qq.com/secapi/pay/refund";  //申请退款接口地址
		
		
		try {
			resultXml = new GetWxOrderno().getRefundResult(createOrderURL, xml);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("resultXml----------------"+resultXml);
		
		return resultXml;
	}
	
	
	
	
	
	/**
	 * 获取微信扫码支付二维码连接
	 */
	@SuppressWarnings("static-access")
	public static String getCodeurl(WxPayDto tpWxPayDto){
		
		// 1 参数
		// 订单号
		String orderId = tpWxPayDto.getOrderId();
		// 附加数据 原样返回
		String attach = "";
		// 总金额以分为单位，不带小数点
		String totalFee = getMoney(tpWxPayDto.getTotalFee());
		
		// 订单生成的机器 IP
		String spbill_create_ip = tpWxPayDto.getSpbillCreateIp();
		// 这里notify_url是 支付完成后微信发给该链接信息，可以判断会员是否支付成功，改变订单状态等。
		String notify_url = notifyurl;
		String trade_type = "NATIVE";

		// 商户号
		String mch_id = partner;
		// 随机字符串
		String nonce_str = getNonceStr();
		//获取交易结束日期
		String time_expire = tpWxPayDto.getEndDate();

		// 商品描述根据情况修改
		String body = tpWxPayDto.getBody();

		// 商户订单号
		String out_trade_no = orderId;

		SortedMap<String, String> packageParams = new TreeMap<String, String>();
		packageParams.put("appid", appid);
		packageParams.put("mch_id", mch_id);
		packageParams.put("nonce_str", nonce_str);
		packageParams.put("body", body);
		packageParams.put("attach", attach);
		packageParams.put("out_trade_no", out_trade_no);
		packageParams.put("time_expire", time_expire);
		System.out.println(time_expire);
		// 这里写的金额为1 分到时修改
		packageParams.put("total_fee", totalFee);
		packageParams.put("spbill_create_ip", spbill_create_ip);
		packageParams.put("notify_url", notify_url);

		packageParams.put("trade_type", trade_type);

		RequestHandler reqHandler = new RequestHandler(null, null);
		reqHandler.init(appid, appsecret, partnerkey);
		
		String sign = reqHandler.createSign(packageParams);
		String xml = "<xml>" + "<appid>" + appid + "</appid>" + "<mch_id>"
				+ mch_id + "</mch_id>" + "<nonce_str>" + nonce_str
				+ "</nonce_str>" + "<sign>" + sign + "</sign>"
				+ "<body><![CDATA[" + body + "]]></body>" 
				+ "<out_trade_no>" + out_trade_no
				+ "</out_trade_no>" + "<attach>" + attach + "</attach>"
				+ "<total_fee>" + totalFee + "</total_fee>"
					+ "<time_expire>" + time_expire + "</time_expire>"
				+ "<spbill_create_ip>" + spbill_create_ip
				+ "</spbill_create_ip>" + "<notify_url>" + notify_url
				+ "</notify_url>" + "<trade_type>" + trade_type
				+ "</trade_type>" + "</xml>";
		String code_url = "";
		System.out.println(xml);
		String createOrderURL = "https://api.mch.weixin.qq.com/pay/unifiedorder";
		
		
		code_url = new GetWxOrderno().getCodeUrl(createOrderURL, xml);
		System.out.println("code_url----------------"+code_url);
		
		return code_url;
	}
	
	
	/**
	 * 获取请求预支付id报文
	 * @return
	 */
	@SuppressWarnings("static-access")
	public static String getPackage(WxPayDto tpWxPayDto) {
		
		String openId = tpWxPayDto.getOpenId();
		// 1 参数
		// 订单号
		String orderId = tpWxPayDto.getOrderId();
		// 附加数据 原样返回
		String attach = "";
		// 总金额以分为单位，不带小数点
		String totalFee = getMoney(tpWxPayDto.getTotalFee());
		
		// 订单生成的机器 IP
		String spbill_create_ip = tpWxPayDto.getSpbillCreateIp();
		// 这里notify_url是 支付完成后微信发给该链接信息，可以判断会员是否支付成功，改变订单状态等。
		String notify_url = notifyurl;
		String trade_type = "JSAPI";

		// ---必须参数
		// 商户号
		String mch_id = partner;
		// 随机字符串
		String nonce_str = getNonceStr();

		// 商品描述根据情况修改
		String body = tpWxPayDto.getBody();

		// 商户订单号
		String out_trade_no = orderId;

		SortedMap<String, String> packageParams = new TreeMap<String, String>();
		packageParams.put("appid", appid);
		packageParams.put("mch_id", mch_id);
		packageParams.put("nonce_str", nonce_str);
		packageParams.put("body", body);
		packageParams.put("attach", attach);
		packageParams.put("out_trade_no", out_trade_no);

		// 这里写的金额为1 分到时修改
		packageParams.put("total_fee", totalFee);
		packageParams.put("spbill_create_ip", spbill_create_ip);
		packageParams.put("notify_url", notify_url);

		packageParams.put("trade_type", trade_type);
		packageParams.put("openid", openId);

		RequestHandler reqHandler = new RequestHandler(null, null);
		reqHandler.init(appid, appsecret, partnerkey);

		String sign = reqHandler.createSign(packageParams);
		String xml = "<xml>" + "<appid>" + appid + "</appid>" + "<mch_id>"
				+ mch_id + "</mch_id>" + "<nonce_str>" + nonce_str
				+ "</nonce_str>" + "<sign>" + sign + "</sign>"
				+ "<body><![CDATA[" + body + "]]></body>" 
				+ "<out_trade_no>" + out_trade_no
				+ "</out_trade_no>" + "<attach>" + attach + "</attach>"
				+ "<total_fee>" + totalFee + "</total_fee>"
				+ "<spbill_create_ip>" + spbill_create_ip
				+ "</spbill_create_ip>" + "<notify_url>" + notify_url
				+ "</notify_url>" + "<trade_type>" + trade_type
				+ "</trade_type>" + "<openid>" + openId + "</openid>"
				+ "</xml>";
		System.out.println(xml);
		String prepay_id = "";
		String createOrderURL = "https://api.mch.weixin.qq.com/pay/unifiedorder";
		
		
		prepay_id = new GetWxOrderno().getPayNo(createOrderURL, xml);

		System.out.println("获取到的预支付ID：" + prepay_id);
		
		
		//获取prepay_id后，拼接最后请求支付所需要的package
		
		SortedMap<String, String> finalpackage = new TreeMap<String, String>();
		String timestamp = Sha1Util.getTimeStamp();
		String packages = "prepay_id="+prepay_id;
		finalpackage.put("appId", appid);  
		finalpackage.put("timeStamp", timestamp);  
		finalpackage.put("nonceStr", nonce_str);  
		finalpackage.put("package", packages);  
		finalpackage.put("signType", "MD5");
		//要签名
		String finalsign = reqHandler.createSign(finalpackage);
		
		String finaPackage ="{"+ "\"appId\":\"" + appid + "\",\"timeStamp\":\"" + timestamp
		+ "\",\"nonceStr\":\"" + nonce_str + "\",\"package\":\""
		+ packages + "\",\"signType\" : \"MD5" + "\",\"paySign\":\""
		+ finalsign + "\"" +"}";

		System.out.println("V3 jsApi package:"+finaPackage);
		return finaPackage;
	}

	/**
	 * 获取随机字符串
	 * @return
	 */
	public static String getNonceStr() {
		// 随机数
		String currTime = TenpayUtil.getCurrTime();
		// 8位日期
		String strTime = currTime.substring(8, currTime.length());
		// 四位随机数
		String strRandom = TenpayUtil.buildRandom(4) + "";
		// 10位序列号,可以自行调整。
		return strTime + strRandom;
	}

	/**
	 * 元转换成分
	 * @param money
	 * @return
	 */
	public static String getMoney(String amount) {
		if(amount==null){
			return "";
		}
		// 金额转化为分为单位
		String currency =  amount.replaceAll("\\$|\\￥|\\,", "");  //处理包含, ￥ 或者$的金额  
        int index = currency.indexOf(".");  
        int length = currency.length();  
        Long amLong = 0l;  
        if(index == -1){  
            amLong = Long.valueOf(currency+"00");  
        }else if(length - index >= 3){  
            amLong = Long.valueOf((currency.substring(0, index+3)).replace(".", ""));  
        }else if(length - index == 2){  
            amLong = Long.valueOf((currency.substring(0, index+2)).replace(".", "")+0);  
        }else{  
            amLong = Long.valueOf((currency.substring(0, index+1)).replace(".", "")+"00");  
        }  
        return amLong.toString(); 
	}
	
	
	/**
	 * 获取当前时间 yyyyMMddHHmmss
	 * @return String
	 */ 
	public static String getCurrTime(String flag) {
		 Calendar calendar = Calendar.getInstance();
		 if("1".equals(flag)){
	     calendar.add(Calendar.SECOND, 60);
		 }
        return new SimpleDateFormat("yyyyMMddHHmmss").format(calendar.getTime());


	}

}
