package com.miyzh.framework.util;

/**
 * 签名验证工具类
 * @author:Jiquanwei<br>
 * @date:2015-11-17 下午02:26:50<br>
 * @version:1.0.0<br>
 * 
 */
public class ValidateSignUtil {
	
	/**
	 * 验证签名
	 * @param str
	 * @param userkey
	 * @param token
	 * @return
	 */
	public static final boolean validateSign(String str,String userkey,String token){
		boolean flag = false;
		String firstSign = new MD5Util().getMD5ofStr(str + userkey);
		String scrKey = PropertiesUtil.getPropertyValue("extract_key");
		String secSign = new MD5Util().getMD5ofStr(scrKey + firstSign);
		if(token.equals(secSign)){
			flag = true;
		}
		return flag;
	}
}
