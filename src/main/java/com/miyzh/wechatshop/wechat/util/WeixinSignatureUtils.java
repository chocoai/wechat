package com.miyzh.wechatshop.wechat.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import com.miyzh.framework.util.PropertiesUtil;

/**
 * <pre>
 * 微信验证工具类.
 * </pre>
 *
 * @author 张涛 2014-4-28
 */
public class WeixinSignatureUtils {
	// 与接口配置信息中的Token要一致
	/** The token. */
	private static String token = PropertiesUtil.getPropertyValue("wechat_validate_token");

	/**
	 * 验证签名.
	 *
	 * @param signature
	 *            the signature
	 * @param timestamp
	 *            the timestamp
	 * @param nonce
	 *            the nonce
	 * @return true, if successful
	 * @author 张涛 2014-4-28
	 */
	public static boolean checkSignature(String signature, String timestamp,
			String nonce) {
		String[] arr = new String[] { token, timestamp, nonce };
		// 将token、timestamp、nonce三个参数进行字典序排序
		Arrays.sort(arr);
		StringBuilder content = new StringBuilder();
		for (int i = 0; i < arr.length; i++) {
			content.append(arr[i]);
		}
		MessageDigest md = null;
		String tmpStr = null;
		try {
			md = MessageDigest.getInstance("SHA-1");
			// 将三个参数字符串拼接成一个字符串进行sha1加密
			byte[] digest = md.digest(content.toString().getBytes());
			tmpStr = byteToStr(digest);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		content = null;
		// 将sha1加密后的字符串可与signature对比，标识该请求来源于微信
		return tmpStr != null ? tmpStr.equals(signature.toUpperCase()) : false;
	}

	/**
	 * 将字节数组转换为十六进制字符串.
	 *
	 * @param byteArray
	 *            the byte array
	 * @return the string
	 * @author 张涛 2014-4-28
	 */
	private static String byteToStr(byte[] byteArray) {
		String strDigest = "";
		for (int i = 0; i < byteArray.length; i++) {
			strDigest += byteToHexStr(byteArray[i]);
		}
		return strDigest;
	}

	/**
	 * 将字节转换为十六进制字符串.
	 *
	 * @param mByte
	 *            the m byte
	 * @return the string
	 * @author 张涛 2014-4-28
	 */
	private static String byteToHexStr(byte mByte) {
		char[] Digit = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A',
				'B', 'C', 'D', 'E', 'F' };
		char[] tempArr = new char[2];
		tempArr[0] = Digit[(mByte >>> 4) & 0X0F];
		tempArr[1] = Digit[mByte & 0X0F];
		return new String(tempArr);
	}
}
