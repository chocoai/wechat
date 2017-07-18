package com.miyzh.wechatshop.wechat.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

/**
 * <pre>
 * 微信消息处理工具类.
 * </pre>
 * 
 * @author 张涛 2014-4-28
 */
public class WeixinMessageUtils {

	private static Logger logger = Logger.getLogger(WeixinMessageUtils.class);

	/**
	 * <pre>
	 * 将XML数据转换为Object对象.
	 * </pre>
	 * 
	 * @param <T>
	 *            the generic type
	 * @param clazz
	 *            the clazz
	 * @param xml
	 *            the xml
	 * @return the t
	 * @throws JAXBException
	 *             the jAXB exception
	 * @author 张涛 2014-4-28
	 */
	@SuppressWarnings("unchecked")
	public static <T> T unmarshal(Class<T> clazz, String xml)
			throws JAXBException {
		JAXBContext jc = JAXBContext.newInstance(clazz);
		ByteArrayInputStream bais;
		try {
			bais = new ByteArrayInputStream(xml.getBytes("UTF-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return null;
		}
		return (T) jc.createUnmarshaller().unmarshal(bais);
	}

	/**
	 * 解析微信发来的请求（XML）.
	 * 
	 * @param in
	 *            the in
	 * @return the map
	 * @throws Exception
	 *             the exception
	 * @author 张涛 2014-4-28
	 */
	public static Map<String, String> parseXml(InputStream in) throws Exception {
		// 将解析结果存储在HashMap中
		Map<String, String> map = new HashMap<String, String>();
		// 读取输入流
		SAXReader reader = new SAXReader();
		Document document = reader.read(in);
		if (!document.hasContent()) {
			logger.warn("不存在请求内容！");
		} else {
			logger.info("请求内容：" + document.asXML());
		}

		// 得到xml根元素
		Element root = document.getRootElement();
		// 得到根元素的所有子节点
		@SuppressWarnings("unchecked")
		List<Element> elementList = (List<Element>) root.elements();
		// 遍历所有子节点
		for (Element e : elementList) {
			map.put(e.getName(), e.getText());
		}
		// 释放资源
		// if (null != inputStream) {
		// inputStream.close();
		// }
		return map;
	}


	/**
	 * <pre>
	 * 将对象转换为XML.
	 * </pre>
	 *
	 * @param <T>
	 *            the generic type
	 * @param object
	 *            the object
	 * @return the string
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 * @author 张涛 2014-4-28
	 */
	public static <T> String getObjectToXml(T object) throws IOException {
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		try {
			JAXBContext context = JAXBContext.newInstance(object.getClass());
			// 将对象转变为xml Object------XML
			// 指定对应的xml文件
			Marshaller marshaller = context.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, false);// 是否格式化生成的xml串
			marshaller.setProperty(Marshaller.JAXB_FRAGMENT, false);// 是否省略xml头信息
			// 将对象转换为对应的XML文件
			marshaller.marshal(object, byteArrayOutputStream);
		} catch (JAXBException e) {
			e.printStackTrace();
		}
		// 转化为字符串返回
		String xmlContent = new String(byteArrayOutputStream.toByteArray(),
				"UTF-8");
		return xmlContent;
	}
}
