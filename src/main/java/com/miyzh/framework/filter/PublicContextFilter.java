/**
 * <pre>
 * Copyright Digital Bay Technology Group. Co. Ltd.All Rights Reserved.
 *
 * Original Author: 孙顺博
 *
 * ChangeLog:
 * 2015-1-16 by 孙顺博 create
 * </pre>
 */
package com.miyzh.framework.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;



/**
 * <pre>
 * 过滤器{存放请求信息}.
 * </pre>
 *
 * @author 孙顺博  2015-1-16
 */
public class PublicContextFilter  implements Filter {    
//	private static Log log=LogFactory.getLog(PublicContextFilter.class);
	
	/* (non-Javadoc)
 * @see javax.servlet.Filter#destroy()
 */
public void destroy() {
		
	}
	
	/* (non-Javadoc)
	 * @see javax.servlet.Filter#doFilter(javax.servlet.ServletRequest, javax.servlet.ServletResponse, javax.servlet.FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response,FilterChain chain) throws IOException, ServletException {	
		try{
			   ValueStackContext valueStackContext = ValueStackContext.createContext();		
			   valueStackContext.setRequest((HttpServletRequest)request);
			   valueStackContext.setResponse((HttpServletResponse)response);
			   valueStackContext.setSession(((HttpServletRequest)request).getSession());
			   chain.doFilter(request, response);
		}catch(Throwable e){
			e.printStackTrace();
		}finally{
		    	ValueStackContext.removeContext();
		}
	}


	/* (non-Javadoc)
	 * @see javax.servlet.Filter#init(javax.servlet.FilterConfig)
	 */
	public void init(FilterConfig filterConfig) throws ServletException {
		
	}

}