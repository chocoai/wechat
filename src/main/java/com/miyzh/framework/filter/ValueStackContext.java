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

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;



/**
 * <pre>
 * 值栈类{存放请求信息}.
 * </pre>
 *
 * @author 孙顺博  2015-1-16
 */
public class ValueStackContext {
	    
    	/** The value stack context thread local. */
    	private static ThreadLocal<ValueStackContext> valueStackContextThreadLocal = new ThreadLocal<ValueStackContext>();	
		
	    /** 值栈中存储的对象的值. */
	    private  Map<Object,Object> valueStackContainer=new HashMap<Object,Object>();
		
        /**
         * Instantiates a new value stack context.
         *
         * @author 孙顺博  2015-1-16
         */
        private ValueStackContext(){
        	    
        }
        
        /**
         * 创建基于线程的值栈.
         *
         * @return the value stack context
         * @author 孙顺博  2015-1-16
         */
		public  static ValueStackContext createContext(){
			ValueStackContext valueStackContext = new ValueStackContext();
			valueStackContextThreadLocal.set(valueStackContext);
			return valueStackContext;
		}
		
		/**
		 * 当调用束后将对象从值栈中移走，以及时释放内存.
		 *
		 * @author 孙顺博  2015-1-16
		 */
		public  static void removeContext(){
			valueStackContextThreadLocal.remove();
		}
		
		/**
		 * 只能Servlet调用线程中使用，如果想在子线程中使用，请先获取值，然后传递给子线程
		 * 值栈上线文不支持跨线程使用.
		 *
		 * @return the value stack context
		 * @author 孙顺博  2015-1-16
		 */
		public static ValueStackContext getValueStackContext(){
			ValueStackContext valueStackContext = valueStackContextThreadLocal.get();
			return valueStackContext;
		}
		
		/**
		 * 将某次的调用结果放入到值栈中，方便上层使用.
		 *
		 * @param key the key
		 * @param value the value
		 * @return true, if successful
		 * @author 孙顺博  2015-1-16
		 */
		public  boolean push(Object key,Object value){
			   valueStackContainer.put(key, value);
			   return true;
		}
		
		
		/**
		 * 从值栈中取得对象.
		 *
		 * @param key the key
		 * @return the object
		 * @author 孙顺博  2015-1-16
		 */
		public Object get(Object key){
			    Object value = this.valueStackContainer.get(key);
			    return value;
		}
				
		
		
		/**
		 * <pre>
		 * Sets the request.
		 * </pre>
		 *
		 * @param request the request
		 * @author 孙顺博  2015-1-16
		 */
		public  void setRequest(HttpServletRequest request) {
			valueStackContainer.put("request", request);
		}

		/**
		 * <pre>
		 * Gets the request.
		 * </pre>
		 *
		 * @return the http servlet request
		 * @author 孙顺博  2015-1-16
		 */
		public  HttpServletRequest getRequest() {
			Object request = valueStackContainer.get("request");
			return (HttpServletRequest)request;			
		}
		
		/**
		 * <pre>
		 * Sets the response.
		 * </pre>
		 *
		 * @param response the response
		 * @author 孙顺博  2015-1-16
		 */
		public  void setResponse(HttpServletResponse response) {
			valueStackContainer.put("response", response);
		}

		/**
		 * <pre>
		 * Gets the response.
		 * </pre>
		 *
		 * @return the http servlet response
		 * @author 孙顺博  2015-1-16
		 */
		public  HttpServletResponse getResponse() {		
			Object response = valueStackContainer.get("response");
			return (HttpServletResponse)response;
		}

		/**
		 * <pre>
		 * Sets the session.
		 * </pre>
		 *
		 * @param session the session
		 * @author 孙顺博  2015-1-16
		 */
		public void setSession(HttpSession session){
			   valueStackContainer.put("session", session);
		}
		
		/**
		 * <pre>
		 * Gets the session.
		 * </pre>
		 *
		 * @return the http session
		 * @author 孙顺博  2015-1-16
		 */
		public HttpSession getSession(){
			   Object session = valueStackContainer.get("session");
			   return (HttpSession)session;
		}
		
}
