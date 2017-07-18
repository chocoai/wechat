package com.miyzh.framework.base.action;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.PropertyFilter;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.serializer.SimplePropertyPreFilter;
import com.miyzh.framework.base.Constant;
import com.miyzh.framework.base.action.reply.BaseReplyReport;
import com.miyzh.framework.base.action.reply.BaseReplyResult;
import com.miyzh.framework.util.PropertiesUtil;
import com.miyzh.framework.util.UUIDTools;

/**
 * 文件名：BaseAction<br>
 * 版权：Copyright 2014 Digital Bay Technology Group. Co. Ltd. All Rights Reserved. <br>
 * 描述: 基础action<br>
 * 修改人: guchangpeng<br>
 * 修改时间：2014-02-26 20:59:52<br>
 * 修改内容：新增<br>
 */
public class BaseAction {

	protected Log log = LogFactory.getLog(getClass());
	private final static int CACHESIZE = 2048;
	/**
	 * 对返回类型不同的属性中的null，进行处理
	 */
	protected static final SerializerFeature[] NULLHANDLER = {
			SerializerFeature.WriteMapNullValue, // 输出空置字段<br/>
			SerializerFeature.WriteNullListAsEmpty, // list字段如果为null，输出为[]，而不是nullSerializerFeature.WriteNullNumberAsZero,
			SerializerFeature.WriteNullBooleanAsFalse, // 数值字段如果为null，输出为0，而不是nullSerializerFeature.WriteNullBooleanAsFalse,
			// //
			// Boolean字段如果为null，输出为false，而不是null
			SerializerFeature.WriteNullStringAsEmpty // 字符类型字段如果为null，输出为""，而不是null
	};

	/**
	 * 转换请求报文为json字符串
	 * 
	 * @param request
	 *            HttpServletRequest
	 * @param response
	 *            HttpServletResponse
	 * @return json字符串
	 * @throws IOException
	 */
	protected String parseRequestReportToString(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		StringBuffer sbBuffer = null;
		InputStream inputStream = null;
		try {
			sbBuffer = new StringBuffer();
			inputStream = request.getInputStream();
			byte[] b = new byte[CACHESIZE];
			for (int n; (n = inputStream.read(b)) != -1;) {
				sbBuffer.append(new String(b, 0, n, "UTF-8"));
			}

		} catch (Exception e) {
			log.error("In the method BaseAction<Entity> exists error!", e);
		} finally {
			if (inputStream != null) {
				inputStream.close();
			}
		}
		String jsonStr = sbBuffer.toString();
		log.info("========parseRequestReportToString============\n" + jsonStr);
		return jsonStr;
	}

	/**
	 * 获取服务器图片连接URL前缀
	 * 
	 * @param request
	 *            HttpServletRequest
	 * @param response
	 *            HttpServletResponse
	 * @return string字符串
	 */
	protected String pathPrifix(HttpServletRequest request) {
		// String pathPrifix =
		// "http://"+ip+":"+dk+""+webName+"/"+PropertiesUtil.getPropertyValue("image_path_prx");
		return PropertiesUtil.getPropertyValue("image_server_pre_url");
	}

	/**
	 * 得到JSONObject
	 * 
	 * @param jsonString
	 *            json字符串
	 * @return
	 */
	protected JSONObject getJsonObject(String jsonString) {
		if (StringUtils.isBlank(jsonString)) {
			return null;
		}
		log.debug("========requestReport============\n" + jsonString);
		JSONObject jsonObject = JSON.parseObject(jsonString);
		return jsonObject;
	}

	/**
	 * 过滤返回给客户端的对象中的属性
	 * 
	 * @param propertyArr
	 *            需要过滤的属性数组
	 * @return
	 */
	protected PropertyFilter filterProperty(final String[] propertyArr) {
		PropertyFilter filter = new PropertyFilter() {
			// 过滤不需要的字段
			public boolean apply(Object source, String name, Object value) {
				if (null != propertyArr && propertyArr.length > 0) {
					for (int i = 0, k = propertyArr.length; i < k; i++) {
						if (propertyArr[i].equals(name)) {
							return false;
						}
					}
				}
				return true;
			}
		};
		return filter;
	}

	/**
	 * 向客户端响应数据
	 * 
	 * @param response
	 * @param str
	 * @throws IOException
	 */
	protected void responseClient(HttpServletResponse response,
			String replyReportStr){
		// catch out的IO异常,并finally关闭,防止内存泄露. by 张涛 2016-1-26
		OutputStream out = null;
		try {
			out = response.getOutputStream();
			if (StringUtils.isBlank(replyReportStr)) {

				out.write("!!!null response string".getBytes("utf-8"));
				out.flush();
				out.close();
				return;
			}
			log.debug("========replyReport============\n" + replyReportStr);
			byte[] bResponseToClient = new byte[0];
			bResponseToClient = replyReportStr.getBytes("UTF-8");// 不压缩
			if (bResponseToClient.length == 0 || bResponseToClient == null) {
				response.setStatus(404);
			} else {
				response.setContentLength(bResponseToClient.length);
			}
			response.setHeader("Server", "JT APPServer/2.0");
			response.setHeader("Access-Control-Allow-Origin", "*");
			response.setContentType("application/octet-stream");
			// OutputStream out = response.getOutputStream();
			if (bResponseToClient != null) {
				int iLength = bResponseToClient.length;
				/*
				 * byte[] bHeader = "flystm!".getBytes("utf-8");
				 * out.write(bHeader, 0, bHeader.length); iLength +=
				 * bHeader.length;
				 */
				response.setContentLength(iLength);
				out.write(bResponseToClient, 0, bResponseToClient.length);
			}
			out.flush();
			// out.close();
		} catch (IOException e) {
			log.debug("out IO异常!", e);
		} catch (Exception e) {
			log.error(e);
		} finally {
			if (out != null) {
				try {
					out.close();
				} catch (Exception e2) {
					log.error(e2);
				}
			}
		}
	}

	/**
	 * 回复接口不识别报文
	 * 
	 * @param response
	 * @param baseReplyReport
	 * @throws IOException
	 */
	protected void replyIdentifyError(HttpServletResponse response)
			throws IOException {
		BaseReplyReport baseReplyReport = new BaseReplyReport();
		BaseReplyResult result = new BaseReplyResult();
		result.setCode("-1");
		result.setMsg("identify error!");
		baseReplyReport.setResult(result);
		baseReplyReport
				.setReplytime(String.valueOf(System.currentTimeMillis()));
		String replyReportStr = JSON.toJSONString(baseReplyReport);
		responseClient(response, replyReportStr);
	}

	/**
	 * 回复错误报文
	 * 
	 * @param response
	 * @param baseReplyReport
	 * @throws IOException
	 */
	protected void replyError(HttpServletResponse response) throws IOException {
		BaseReplyReport baseReplyReport = new BaseReplyReport();
		BaseReplyResult result = new BaseReplyResult();
		result.setCode(Constant.ResultCode.FILURE);
		result.setMsg("网络繁忙请稍后再试");
		baseReplyReport.setResult(result);
		baseReplyReport
				.setReplytime(String.valueOf(System.currentTimeMillis()));
		String replyReportStr = JSON.toJSONString(baseReplyReport);
		responseClient(response, replyReportStr);
	}

	protected SimplePropertyPreFilter filterPro(String[] strArr) {
		// 过滤属性，只写需要的属性，或者用其他的构造方法
		SimplePropertyPreFilter filter = new SimplePropertyPreFilter(strArr);
		return filter;
	}

	/**
	 * 得到总页数
	 * 
	 * @param count
	 *            每次取的记录数
	 * @param total
	 *            数据库中总记录数
	 * @return int 总页数
	 */
	protected int getTotalPage(int count, int total) {
		int totalPage = 1;// 总页数
		if (total > count) {
			if (total % count == 0) {
				totalPage = total / count;
			} else {
				totalPage = total / count + 1;
			}
		}
		return totalPage;
	}

	/**
	 * 生成主键
	 * 
	 * @return
	 */
	public String getUUID() {
		return UUIDTools.getInstance().getUUID();
	}
}
