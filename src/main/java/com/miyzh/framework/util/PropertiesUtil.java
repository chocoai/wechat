package com.miyzh.framework.util;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;

/**
 * 文件名: PropertiesUtil.java<br>
 * 版权：Copyright 2014 Digital Bay Technology Group. Co. Ltd. All Rights Reserved. <br>
 * 描述: 配置文件工具类 <br>
 * 修改人: guchangpeng <br>
 * 修改时间：2014-9-27 下午02:22:45 <br>
 * 修改内容：新增 <br>
 */
public class PropertiesUtil {

	private static Properties properties = new Properties();
	static {
		InputStream is = null;
		try {
			is = PropertiesUtil.class.getClassLoader().getResourceAsStream(
					"common.properties");
			properties.load(is);
			is = PropertiesUtil.class.getClassLoader().getResourceAsStream(
					"wechat.properties");
			BufferedReader bf = new BufferedReader(new InputStreamReader(is));
			properties.load(bf);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

	public static String getPropertyValue(String key) {
		return properties.getProperty(key);
	}

	/**
	 * 更新properties文件的键值对 如果该主键已经存在，更新该主键的值； 如果该主键不存在，则插件一对键值。
	 * 
	 * @param keyname
	 *            键名
	 * @param keyvalue
	 *            键值
	 */
	public static void updateProperties(String keyname, String keyvalue) {
		String path = PropertiesUtil.class.getResource("/").toString();
		System.out.println(path);
		String rootPath = path.substring(0, path.lastIndexOf("/classes"));
		System.out.println(rootPath);
		String propertyFilePath = rootPath + "/classes/common.properties";
		System.out.println(propertyFilePath);
		propertyFilePath = propertyFilePath.substring(6);
		System.out.println(propertyFilePath);
		Properties prop = new Properties();// 属性集合对象
		FileInputStream fis;
		try {
			fis = new FileInputStream(propertyFilePath);// 属性文件输入流
			prop.load(fis);// 将属性文件流装载到Properties对象中
			fis.close();// 关闭流
			// 修改sitename的属性值
			prop.setProperty(keyname, keyvalue);
			// 文件输出流
			FileOutputStream fos = new FileOutputStream(propertyFilePath);
			// 将Properties集合保存到流中
			prop.store(fos, "Copyright (c) Boxcode Studio");
			fos.close();// 关闭流
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		System.out.println(PropertiesUtil.getPropertyValue("pathUrl"));
	}

}
