package com.miyzh.framework.util;

import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Author liugang
 * @time 2016-3-30 下午11:26:36
 * @Description 属性文件解析工具类
 */
public class ConfigResolver {
	
	private static final Logger logger=LoggerFactory.getLogger(ConfigResolver.class);
	private Properties props = new Properties();
	
	public ConfigResolver(String configFileName){
		try {
			props.load(Thread.currentThread().getContextClassLoader()
					.getResourceAsStream(configFileName));
		} catch (Exception e) {
			logger.error("系统异常：",e);
		}
	}

	public String getProperty(String key) {
		return props.getProperty(key).trim();
	}

	public void setProperty(String key, String value) {
		props.setProperty(key, value);
	}

}
