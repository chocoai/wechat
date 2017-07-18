package com.miyzh.framework.base.web;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
/**
 * 文件名: StartUpServlet.java<br>
 * 版权：Copyright 2014 Digital Bay Technology Group. Co. Ltd. All Rights Reserved. <br>
 * 描述: 启动容器后的servlet <br>
 * 修改人: guchangpeng <br>
 * 修改时间：2014-10-20 下午07:28:50 <br>
 * 修改内容：新增 <br>
 */
public class StartUpServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	public static WebApplicationContext webApplicationContext = null;
	/**
	 * DBT FrameWork Start
	 */
	public void init() throws ServletException {
		ServletContext application = getServletContext();
		webApplicationContext = WebApplicationContextUtils
				.getWebApplicationContext(application);
	}
	
}