package com.miyzh.framework.util;

import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import com.miyzh.framework.aliyunoss.OssClientComponent;

public class UrlDownFile {
	public static void main(String[] args) {
		try {
			URL url = new URL(
					"http://wx.qlogo.cn/mmopen/xSJOejcUucGicDKHMy2jkWL4wtIlxXjUPDvYdsDOg9J4moR0j3XObGleW6cibSxiaWhRA4UChZ9XPaqwSbnjNVRB6Kicfw54ZWrc/0");
			// 打开连接
			URLConnection con = url.openConnection();
			// 输入流
			InputStream is = con.getInputStream();

			boolean flag = OssClientComponent.uploadFileOrdinary("yideb/YYYY/MM/DD/UUID.jpg",
					InputStreamToByteUtil.input2byte(is));
			System.out.println(flag);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
