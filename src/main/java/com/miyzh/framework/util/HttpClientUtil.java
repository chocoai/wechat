package com.miyzh.framework.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

/**
 * 文件名：HttpClientUtil.java<br>
 * 版权：Copyright 2014 Digital Bay Technology Group. Co. Ltd. All Rights Reserved. <br>
 * 描述: HttpClientUtil帮助类<br>
 * 修改人: guchangpeng<br>
 * 修改时间：2014-07-05<br>
 * 修改内容：新增<br>
 */
public class HttpClientUtil {

	public static String clientUrl(String url) throws ClientProtocolException,
			IOException {
		HttpClient client = HttpClients.createDefault();
		HttpPost post = new HttpPost(url);
		post.addHeader("Content-Type", "application/json");
		HttpResponse response = client.execute(post);
		HttpEntity entity = response.getEntity();
		String resBody = IOUtils.toString(entity.getContent(), "GBK");
		return resBody;

	}

	/**
	 * <pre>
	 * Input stream2 string.
	 * </pre>
	 * 
	 * @author 孙顺博 2015-1-16
	 * @param is
	 *            the is
	 * @return the string
	 */
	public static String inputStream2String(InputStream is) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];
		int len = -1;
		try {
			while ((len = is.read(buffer)) != -1) {
				baos.write(buffer, 0, len);
				baos.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return baos.toString();
	}

	/**
	 * http请求
	 * 
	 * @param uri
	 * @param parameterStr
	 * @param request
	 * @return
	 */
	public static JSONObject httpReq(String uri, String parameterStr,
			HttpServletRequest request) {
		JSONObject jsonObject = null;
		CloseableHttpClient client = null;
		HttpPost post = null;
		try {
			client = HttpClients.createDefault();
			// client.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT,
			// 3000);// 连接时间
			// client.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT,
			// 3000);// 数据传输时间
			post = new HttpPost(uri);
			post.addHeader("Content-Type", "application/json");

			// post.addHeader("Content-Type", "image/jpeg");
			if (StringUtils.isNotBlank(parameterStr)) {
				byte[] data = parameterStr.getBytes("UTF-8");
				post.setEntity(new ByteArrayEntity(data));
			}
			if (request != null) {
				post.setEntity(new ByteArrayEntity(IOUtils.toByteArray(request
						.getInputStream())));
			}

			RequestConfig requestConfig = RequestConfig.custom()
					.setSocketTimeout(3000).setConnectTimeout(3000).build();
			post.setConfig(requestConfig);

			HttpResponse response = client.execute(post);
			HttpEntity entity = response.getEntity();
			String resBody = IOUtils.toString(entity.getContent(), "UTF-8");
			if (response.getStatusLine().getStatusCode() == 200
					&& StringUtils.isNotBlank(resBody)) {
				jsonObject = JSON.parseObject(resBody);
			}

		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (null != post) {
					post.abort();// 终止本次连接
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				if (null != client) {
					// client.getConnectionManager().shutdown();// 关闭
					client.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return jsonObject;
	}

	public static void main(String[] args) {
		// https://tcc.taobao.com/cc/json/mobile_tel_segment.htm?tel=13797010823

		// HttpClientUtil.httpReq(uri, parameterStr, request)
	}

}
