package com.miyzh.wechatshop.wechat.action;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.JAXBException;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.miyzh.wechatshop.wechat.bean.RequestInfo;
import com.miyzh.wechatshop.wechat.service.IWeChatCommonService;
import com.miyzh.wechatshop.wechat.util.WeixinMessageUtils;
import com.miyzh.wechatshop.wechat.util.WeixinSignatureUtils;

@Controller
@RequestMapping("/messageAction")
public class MessageAction {

	private Logger log = Logger.getLogger(this.getClass());

	@Autowired
	private IWeChatCommonService server;

	/**
	 * <pre>
	 * 微信通用接收消息.
	 * </pre>
	 * 
	 * @param model
	 *            the model
	 * @throws JAXBException
	 *             the jAXB exception
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 * @author 张涛 2014-4-28
	 */
	@RequestMapping("/revice")
	public void revice(HttpServletRequest request, HttpServletResponse response)
			throws JAXBException, IOException {
		String reqMethod = request.getMethod() == null ? "" : request
				.getMethod();
		// 若为Get请求则验证开发者
		if ("GET".equals(reqMethod.toUpperCase().trim())) {
			String temp = validate(request);
			PrintWriter out = response.getWriter();
			if (temp != null) {
				out.print(temp);
			}
			out.close();
			out = null;
		} else { // POST请求则处理服务
			InputStream is = request.getInputStream();
			// 转换消息为Objectle
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			RequestInfo info = null;
			try {
				byte[] b = new byte[1000];
				int i = 0;
				while ((i = is.read(b)) > -1) {
					baos.write(b, 0, i);
				}
				// 将微信发送的消息转换为类
				// 另一种方式：Map<String, String> requestMap =
				// WeixinMessageUtils.parseXml(request.getInputStream());
				info = WeixinMessageUtils.unmarshal(RequestInfo.class,
						new String(baos.toByteArray(), "UTF-8"));
			} catch (IOException e) {
				e.printStackTrace();
				throw e;
			} finally {
				try {
					is.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			// 处理
			String xml;
			try {
				xml = server.commonHandler(info);
				// 返回微信消息
				PrintWriter out = response.getWriter();
				if (xml != null) {
					out.print(xml);
				}
				out.close();
				out = null;
			} catch (Exception e) {
				// TODO Auto-generated catch block
				log.error("MessageAction.revice", e);
			}

		}

	}

	/**
	 * <pre>
	 * 微信开发者验证.
	 * </pre>
	 * 
	 * @param req
	 *            the req
	 * @return the string
	 * @author 张涛 2014-4-28
	 */
	private String validate(HttpServletRequest req) {
		String flag = null;
		// 微信加密签名
		String signature = req.getParameter("signature");
		// 时间戳
		String timestamp = req.getParameter("timestamp");
		// 随机数
		String nonce = req.getParameter("nonce");
		// 随机字符串
		String echostr = req.getParameter("echostr");

		log.debug("signature: " + signature + ", timestamp: " + timestamp
				+ ", nonce: " + nonce + ", echostr: " + echostr);

		if (WeixinSignatureUtils.checkSignature(signature, timestamp, nonce)) {
			flag = echostr;
		}
		return flag;
	}

}
