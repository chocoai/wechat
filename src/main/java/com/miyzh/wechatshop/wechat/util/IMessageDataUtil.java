package com.miyzh.wechatshop.wechat.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.stereotype.Component;



/**
 * 传入URL取得数据
 * 
 * @param url
 * @return
 * @throws IOException
 */
@Component
public class IMessageDataUtil {

	public String getData(String methodName) throws IOException,
			DocumentException {
		URL yahoo;
		BufferedReader in = null;
		yahoo = new URL("http://10.10.2.231:96/WSWechat.asmx/" + methodName);
		in = new BufferedReader(new InputStreamReader(yahoo.openStream(),
				"utf-8"));
		String inputLine;
		String returnData = null;

		while ((inputLine = in.readLine()) != null) {
			returnData = inputLine;
		}
		if (returnData != null) {
			Document document = DocumentHelper.parseText(returnData);
			// 得到xml根元素
			Element root = document.getRootElement();
			System.out.println("返回的数据转换" + root.getStringValue());
			String data = root.getStringValue();

			return data;
		}

		in.close();

		return null;

	}
}
