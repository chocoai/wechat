package com.miyzh.wechatshop.payUtil.pay.utils;


import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.net.ssl.SSLContext;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.params.ClientPNames;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContexts;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

import com.miyzh.wechatshop.payUtil.pay.utils.http.HttpClientConnectionManager;



public class GetWxOrderno
{
  public static DefaultHttpClient httpclient;

  static
  {
    httpclient = new DefaultHttpClient();
    httpclient = (DefaultHttpClient)HttpClientConnectionManager.getSSLInstance(httpclient);
  }


  /**
   *description:获取预支付id
   *@param url
   *@param xmlParam
   *@return
   * @author weifb
   * @see
   */
  public static String getPayNo(String url,String xmlParam){
	  DefaultHttpClient client = new DefaultHttpClient();
	  client.getParams().setParameter(ClientPNames.ALLOW_CIRCULAR_REDIRECTS, true);
	  HttpPost httpost= HttpClientConnectionManager.getPostMethod(url);
	  String prepay_id = "";
     try {
		 httpost.setEntity(new StringEntity(xmlParam, "UTF-8"));
		 HttpResponse response = httpclient.execute(httpost);
	     String jsonStr = EntityUtils.toString(response.getEntity(), "UTF-8");
	    if(jsonStr.indexOf("FAIL")!=-1){
	    	return prepay_id;
	    }
	    Map map = doXMLParse(jsonStr);
	    prepay_id  = (String) map.get("prepay_id");
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	return prepay_id;
  }
  
  
  /**
   *description:获取退款结果
   *@param url
   *@param xmlParam
   *@return
   * @author weifb
 * @throws Exception 
   * @see
   */
  public static String getRefundResult(String url,String xmlParam) throws Exception{
	
	  KeyStore keyStore  = KeyStore.getInstance("PKCS12");
//      FileInputStream instream = new FileInputStream(new File("/shop_project/tomcat-mysc-1.0/webapps/ROOT/WEB-INF/config/weifubing.p12"));//P12文件目录
      FileInputStream instream = new FileInputStream(new File("/shop_project/tomcat_shop/webapps/ROOT/WEB-INF/config/weifubing.p12"));//P12文件目录
//      FileInputStream instream = new FileInputStream(new File("e:/weifubing.p12"));//P12文件目录

	  
	  try {
      	/**
      	 * 此处要改
      	 * */
          keyStore.load(instream, "1332506201".toCharArray());//这里写密码..默认是你的MCHID
      } finally {
          instream.close();
      }

      // Trust own CA and all self-signed certs
      /**
  	 * 此处要改
  	 * */
      SSLContext sslcontext = SSLContexts.custom()
              .loadKeyMaterial(keyStore, "1332506201".toCharArray())//这里也是写密码的  
              .build();
      // Allow TLSv1 protocol only
      SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
              sslcontext,
              new String[] { "TLSv1" },
              null,
              SSLConnectionSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);
      CloseableHttpClient httpclient = HttpClients.custom()
              .setSSLSocketFactory(sslsf)
              .build();
	  
	  DefaultHttpClient client = new DefaultHttpClient();
	  client.getParams().setParameter(ClientPNames.ALLOW_CIRCULAR_REDIRECTS, true);
	  HttpPost httpost= HttpClientConnectionManager.getPostMethod(url);
	  String refundResult = "";
     try {
		 httpost.setEntity(new StringEntity(xmlParam, "UTF-8"));
		 HttpResponse response = httpclient.execute(httpost);
	     String jsonStr = EntityUtils.toString(response.getEntity(), "UTF-8");
	    if(jsonStr.indexOf("FAIL")!=-1){
	    	return refundResult;
	    }
	    Map map = doXMLParse(jsonStr);
	    refundResult  = (String) map.get("refund_id");
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	return refundResult;
  }
  
  
  
  
  
  
  /**
   *description:获取扫码支付连接
   *@param url
   *@param xmlParam
   *@return
   * @author weifb
   * @see
   */
  public static String getCodeUrl(String url,String xmlParam){
	  DefaultHttpClient client = new DefaultHttpClient();
	  client.getParams().setParameter(ClientPNames.ALLOW_CIRCULAR_REDIRECTS, true);
	  HttpPost httpost= HttpClientConnectionManager.getPostMethod(url);
	  String code_url = "";
     try {
		 httpost.setEntity(new StringEntity(xmlParam, "UTF-8"));
		 HttpResponse response = httpclient.execute(httpost);
	     String jsonStr = EntityUtils.toString(response.getEntity(), "UTF-8");
	    if(jsonStr.indexOf("FAIL")!=-1){
	    	return code_url;
	    }
	    Map map = doXMLParse(jsonStr);
	    code_url  = (String) map.get("code_url");
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	return code_url;
  }
  
  
  /**
	 * 解析xml,返回第一级元素键值对。如果第一级元素有子节点，则此节点的值是子节点的xml数据。
	 * @param strxml
	 * @return
	 * @throws JDOMException
	 * @throws IOException
	 */
	public static Map doXMLParse(String strxml) throws Exception {
		if(null == strxml || "".equals(strxml)) {
			return null;
		}
		
		Map m = new HashMap();
		InputStream in = String2Inputstream(strxml);
		SAXBuilder builder = new SAXBuilder();
		Document doc = builder.build(in);
		Element root = doc.getRootElement();
		List list = root.getChildren();
		Iterator it = list.iterator();
		while(it.hasNext()) {
			Element e = (Element) it.next();
			String k = e.getName();
			String v = "";
			List children = e.getChildren();
			if(children.isEmpty()) {
				v = e.getTextNormalize();
			} else {
				v = getChildrenText(children);
			}
			
			m.put(k, v);
		}
		
		//关闭流
		in.close();
		
		return m;
	}
	/**
	 * 获取子结点的xml
	 * @param children
	 * @return String
	 */
	public static String getChildrenText(List children) {
		StringBuffer sb = new StringBuffer();
		if(!children.isEmpty()) {
			Iterator it = children.iterator();
			while(it.hasNext()) {
				Element e = (Element) it.next();
				String name = e.getName();
				String value = e.getTextNormalize();
				List list = e.getChildren();
				sb.append("<" + name + ">");
				if(!list.isEmpty()) {
					sb.append(getChildrenText(list));
				}
				sb.append(value);
				sb.append("</" + name + ">");
			}
		}
		
		return sb.toString();
	}
  public static InputStream String2Inputstream(String str) {
		return new ByteArrayInputStream(str.getBytes());
	}
  
}