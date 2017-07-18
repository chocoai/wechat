package com.miyzh.framework.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

/**
 * 文件名： GeocodingUtil.java<br>
 * 版权： Copyright 2014 Digital Bay Technology Group. Co. Ltd. All Rights Reserved. <br>
 * 描述： 百度地图坐标帮助类<br>
 * 修改人： HaoQi<br>
 * 修改时间： 2014-07-28<br>
 * 修改内容： 新增<br>
 */
public class GeocodingUtil {
	
	/**
	 * 根据经纬度，返回具体的地址信息
	 * @param latitude 纬度
	 * @param longitude 经度
	 * @return
	 *  province=北京市<br/>
	 *  city=北京市<br/>
	 *  district=东城区<br/>
	 *  street=贡院西街<br/>
	 *  street_number=8-1号<br/>
	 */
	public static Map<String, String> getRealAddress(String latitude, String longitude) {
		Map<String, String> map = new HashMap<String, String>();
		try {
			URL resjson = new URL(PropertiesUtil.getPropertyValue("baidu_geocoder_url")
					+ "&ak=" + PropertiesUtil.getPropertyValue("baidu_access_key")
					+ "&location="+latitude+","+longitude);
			
			BufferedReader in = new BufferedReader(new InputStreamReader(
					resjson.openStream(), "UTF-8"));
			String res;
			StringBuilder sb = new StringBuilder("");
			while ((res = in.readLine()) != null) {
				sb.append(res.trim());
			}
			in.close();
			String str = sb.toString();
			JSONObject resultObject = JSON.parseObject(str);
			Integer status = resultObject.getInteger("status");
			if(status != null && status == 0){
				JSONObject addressObject = resultObject.getJSONObject("result").getJSONObject("addressComponent");
				Set<String> addressKeySet = addressObject.keySet();
				for (String key : addressKeySet) {
					map.put(key, addressObject.get(key).toString());
				}
			}
		} catch (Exception e) {
			// 为了不影响主业务流程，现将异常注释  add by jiquanwei 20160613
//			e.printStackTrace();
		}
		return map;
	}
	
	/**
	 * 把 GPS设备获取的角度坐标 转换成 百度经纬度坐标
	 * @param latitude 纬度
	 * @param longitude 经度
	 * @return String[纬度, 经度]
	 */
	public static String[] geoConv(String latitude, String longitude){
		try {
			URL resjson = new URL(PropertiesUtil.getPropertyValue("baidu_geoconv_url")
					+ "&ak=" + PropertiesUtil.getPropertyValue("baidu_access_key")
					+ "&coords="+longitude+","+latitude);

			BufferedReader in = new BufferedReader(new InputStreamReader(
					resjson.openStream()));
			String res;
			StringBuilder sb = new StringBuilder("");
			while ((res = in.readLine()) != null) {
				sb.append(res.trim());
			}
			in.close();
			String str = sb.toString();
			JSONObject resultObject = JSON.parseObject(str);
			Integer status = resultObject.getInteger("status");
			if(status != null && status == 0){
				JSONArray resultArray = resultObject.getJSONArray("result");
				JSONObject result = resultArray.getJSONObject(0);
				
				return new String[]{result.getString("y"), result.getString("x")};
			}
		} catch (Exception e) {
			// 为了不影响主业务流程，现将抛异常注释  add by jiquanwei 20160613
//			e.printStackTrace();
		}
		
		return null;
	}
	
	/**
	 * 根据经纬度，返回具体的地址信息
	 * @param latitude 纬度
	 * @param longitude 经度
	 * @param ifLocationConv 是否要转换经纬度，微信都必须为true
	 * @return
	 *  province=北京市<br/>
	 *  city=北京市<br/>
	 *  district=东城区<br/>
	 *  street=贡院西街<br/>
	 *  street_number=8-1号<br/>
	 */
	public static Map<String, String> getRealAddress(String latitude, String longitude, boolean ifLocationConv) {
		Map<String, String> map = new HashMap<String, String>();
		try {
			if(StringUtils.isEmpty(latitude)
					|| StringUtils.isEmpty(longitude)){
				return map;
			}
			if(ifLocationConv){
				String[] location = geoConv(latitude, longitude);
				if(location != null && location.length == 2){
					latitude = location[0];
					longitude = location[1];
				}else{
					return map;
				}
			}
			map = getRealAddress(latitude, longitude);
		} catch (Exception e) {
			// 为了不影响主业务流程，现将抛异常注释  add by jiquanwei 20160613
//			e.printStackTrace();
		}
		return map;
	}

	public static void main(String args[]) {
		//测试数据
		String latitude = "39.9084";
		String longitude = "116.42454";
		
		Map<String, String> map = getRealAddress(latitude, longitude, true);
		Set<String> mspKeys = map.keySet();
		for (String key : mspKeys) {
			System.out.println(key + "=" + map.get(key));
		}
	}
}
