/**
 * <pre>
 * Copyright Digital Bay Technology Group. Co. Ltd.All Rights Reserved.
 *
 * Original Author: guchangpeng
 *
 * ChangeLog:
 * 2015-9-19 by guchangpeng create
 * </pre>
 */
package com.miyzh.framework.util;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * 文件名: BinaryFileUtil.java<br>
 * 版权：Copyright 2014 Digital Bay Technology Group. Co. Ltd. All Rights Reserved. <br>
 * 描述: 二进制文件操作 <br>
 * 修改人: guchangpeng <br>
 * 修改时间：2015-9-19 下午05:46:17 <br>
 * 修改内容：新增 <br>
 * 
 * @author guchangpeng 2015-9-19
 */
public class BinaryFileUtil {

	/**
	 * 依UTF-8的编码格式写文件
	 * 
	 * @author guchangpeng 2015-9-19
	 * @param path
	 *            待写路径
	 * @param content
	 *            内容
	 * @param isAppend
	 *            true :追加 false:不追加
	 */
	public static void writeFile(String path, String content, boolean isAppend) {
		DataOutputStream out = null;
		try {
			File file = new File(path);
			if (!file.getParentFile().exists()) {
				file.mkdirs();
			}
			if (!file.exists()) {
				file.createNewFile();
			}
			out = new DataOutputStream(new FileOutputStream(path, true));
			out.writeBytes(content);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				out.flush();
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 下载文件 .
	 * 
	 * @author guchangpeng 2015-9-19
	 * @param strUrl
	 *            the str url
	 * @param path
	 *            the path
	 */
	public static void downloadFile(String strUrl, String path) {
		URL url = null;
		InputStream is = null;
		OutputStream os = null;
		try {
			url = new URL(strUrl);
			is = url.openStream();
			os = new FileOutputStream(path);
			int bytesRead = 0;
			byte[] buffer = new byte[1024];
			while ((bytesRead = is.read(buffer, 0, 1024)) != -1) {
				os.write(buffer, 0, bytesRead);
			}

		} catch (MalformedURLException e2) {
			e2.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (os != null) {
					os.close();
					os = null;
				}
				if (is != null) {
					is.close();
					is = null;
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
