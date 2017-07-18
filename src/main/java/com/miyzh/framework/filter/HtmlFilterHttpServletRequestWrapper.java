/**
 * <pre>
 * Copyright Digital Bay Technology Group. Co. Ltd.All Rights Reserved.
 *
 * Original Author: 孙顺博
 *
 * ChangeLog:
 * 2015-2-6 by 孙顺博 create
 * </pre>
 */
package com.miyzh.framework.filter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
			
/**
 * <pre>
 * 请求特殊字符过滤器(防止xss攻击和sql注入).
 * 注意：根据应用特性，此类功能属于简化过滤，如果要求严格，需要增加各种过滤条件
 * </pre>
 *
 * @author 孙顺博  2015-1-16
 */	
public class HtmlFilterHttpServletRequestWrapper extends
		HttpServletRequestWrapper {

	/**
	 * Instantiates a new html filter http servlet request wrapper.
	 *
	 * @author 孙顺博  2015-1-16
	 * @param request the request
	 */
	public HtmlFilterHttpServletRequestWrapper(HttpServletRequest request) {
		super(request);
	}
	/*
	 (non-Javadoc)
	 * @see javax.servlet.ServletRequestWrapper#getParameter(java.lang.String)
	 
	public String getParameter(String name){
		String value=super.getParameter(name);
		return clean(value);
	}
	
	 (non-Javadoc)
	 * @see javax.servlet.ServletRequestWrapper#getParameterValues(java.lang.String)
	 
	public String[] getParameterValues(String name){
		String []values=super.getParameterValues(name);
		if(values==null || values.length==0){
			return values;
		}
		String []newValues=new String[values.length];
		for (int i = 0; i < values.length; i++) {
			newValues[i]=clean(values[i]);
		}

		
		return newValues;
	}
	
	
	 (non-Javadoc)
	 * @see javax.servlet.ServletRequestWrapper#getParameterMap()
	 
	public Map<?,?> getParameterMap(){
		Map <?,?>map=super.getParameterMap();
		if(map.isEmpty()){
			return map;
		}
		Iterator<?> iter= map.keySet().iterator();
		while(iter.hasNext()){
			String key=(String)iter.next();
			Object value=map.get(key);
			if(value.getClass().getSimpleName().equals("String")){
				value=this.clean((String)value);
			}
		}
		return map;
	}
	
	*//**
	 * <pre>
	 * 过滤处理.
	 * </pre>
	 *
	 * @author 孙顺博  2015-2-6
	 * @param value the value
	 * @return the string
	 *//*
	private String clean(String value){
		value=cleanXSS(value);//xss过滤
		value=cleanSql(value);//sql过滤
		return value;
	}

	 *//**
 	 * xss过滤.
 	 *
 	 * @author 孙顺博  2015-2-6
 	 * @param value the value
 	 * @return the string
 	 *//*
    private String cleanXSS(String value) {
    	value = value.replaceAll("\"", "\\\"");
		value = value.replaceAll("\'", "\\\'");
        value = value.replaceAll("\\(", "(").replaceAll("\\)", ")");
        value = value.replaceAll("eval\\((.*)\\)", "");
        value = value.replaceAll("[\\\"\\\'][\\s]*javascript:(.*)[\\\"\\\']",
                "\"\"");
        value = value.replaceAll("script", "");
        return value;
    }
    
    *//**
     * sql过滤.
     *
     * @author 孙顺博  2015-2-6
     * @param str the str
     * @return the string
     *//*
	private String cleanSql(String str){
        String badStr = "exec |execute |insert |select |delete |update |drop |% |chr |mid |master |truncate |count |declare |or |union |order by |like ";
        String[] badStrs = badStr.split("\\|");
        for (int i = 0; i < badStrs.length; i++) {
            if (str.indexOf(badStrs[i]) >= 0) {
            	str = str.replace(badStrs[i],"");
            }
        }
        return str;
    }
	*/
}
