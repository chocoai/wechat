package com.miyzh.framework.base.util;

import com.miyzh.framework.base.web.StartUpServlet;

public class BeanFactoryUtil {

	    /**
	     * 从servlet中获取bean
	     * @param beanname
	     * @return
	     */
	    public static Object getbeanFromWebContext(String beanname){
	    		Object bean = StartUpServlet.webApplicationContext.getBean(beanname);
	    		return bean;
	    }
	
}
