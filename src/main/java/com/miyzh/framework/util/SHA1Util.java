package com.miyzh.framework.util;

import java.security.MessageDigest;

import com.miyzh.wechatshop.payUtil.pay.utils.Sha1Util;

/**
 * 
 * <pre>
 * Copyright (c) Digital Bay Technology Group. Co. Ltd. All Rights Reserved
 *
 * Original Author: sunshunbo
 *
 * ChangeLog:
 * 2015-11-17 by sunshunbo create
 * </pre>
 */
public class SHA1Util {

	/**
	 * <pre>
	 * SHA1加密.
	 * </pre>
	 *
	 * @author sunshunbo  2015-4-2
	 * @param encrypt the encrypt
	 * @param key the key
	 * @return the string
	 */
	public static String getSHA1( String encrypt,String key) {
		try {
			String[] array = new String[] { encrypt,key };
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < array.length; i++) {
				sb.append(array[i]);
			}
			String str = sb.toString();
			// SHA1签名生成
			MessageDigest md = MessageDigest.getInstance("SHA-1");
			md.update(str.getBytes());
			byte[] digest = md.digest();
			
			StringBuffer hexstr = new StringBuffer();
			String shaHex = "";
			for (int i = 0; i < digest.length; i++) {
				shaHex = Integer.toHexString(digest[i] & 0xFF);
				if (shaHex.length() < 2) {
					hexstr.append(0);
				}
				hexstr.append(shaHex);
			}
			return hexstr.toString();
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}
	
}
