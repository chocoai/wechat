package com.miyzh.framework.util;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.support.ApplicationObjectSupport;

import com.miyzh.framework.base.Constant;
import com.miyzh.framework.base.util.BeanFactoryUtil;
import com.miyzh.framework.cache.CustXMemcachedClient;

/**
 * 文件名:CacheUtil.java<br>
 * 版权：Copyright 2014 Digital Bay Technology Group. Co. Ltd. All Rights Reserved.<br>
 * 描述: 缓存工具类<br>
 */
public class CacheUtil extends ApplicationObjectSupport {
	private final static Log LOG = LogFactory.getLog(CacheUtil.class);
	/** 0:缓存永远不失效 **/
	public final static int SECONDS_TO_EXPIRE = 0;

	/*
	 * private static MirrorCache mirrorCache = (MirrorCache) BeanFactoryUtil
	 * .getbeanFromWebContext("mirrorCache");
	 */
	private static CustXMemcachedClient mirrorCachClient = (CustXMemcachedClient) BeanFactoryUtil
			.getbeanFromWebContext("custXMemcachedClient");

	/**
	 * 缓存key
	 * 
	 * @author guchangpeng
	 * @date 2014-07-15
	 */
	public static class cacheKey {

		/** 文件上传缓存 **/
		public static final String KEY_FILE_UPLOAD_STATUS = "Key_File_Upload_Status";
		/** 微信公众账号token：wctAccessToken **/
		public static final String KEY_WCTACCESSTOKEN = "wctAccessToken";
		/** 微信公众账号jstoken：wctAccessToken **/
		public static final String KEY_JSAPITICKET = "wctJsapiTicket";
		/** 微信公众账号jstoken：wctAccessToken **/
		public static final String KEY_JSAPITICKET_SIGNATURE = "wctJsapiTicket_Signature";
		/** 数据字典缓存key:sysDatadicM **/
		public static final String KEY_SYS_DATA_DICM = "sysDatadicM";
		/** 数据字典类型缓存key：sysDiccategoryInfo **/
		public static final String KEY_SYS_DICCATEGORY_INFO = "sysDiccategoryInfo";

		public static interface UserKey {

			/** 用户Bean缓存 */
			public static final String KEY_USER_INFO = "Key_User_Info";

			/** 第三方账户缓存 */
			public static final String KEY_THIRD_USER_INFO = "Key_Third_User_Info";

			/** 会员信息缓存 */
			public static final String KEY_SHOP_MEMBER_INFO = "Key_Shop_Member_Info";
			
		}

		public static interface GroupBuy {

			/** 正在疯抢团购列表缓存 */
			public static final String KEY_GROUPBUY_NOW_LIST = "Key_GroupBuy_Now_List";
			/** 已经结束团购列表缓存 */
			public static final String KEY_GROUPBUY_FINISH_LIST = "Key_GroupBuy_Finish_List";
			/** 未开始团购列表缓存 */
			public static final String KEY_GROUPBUY_NOSTART_LIST = "Key_GroupBuy_NoStart_List";
			/** 团购预览页缓存 */
			public static final String KEY_GROUPBUY_PREVIEW = "Key_GroupBuy_Preview";
			/** 团购详情页缓存 */
			public static final String KEY_GROUPBUY_DETAIL = "Key_GroupBuy_Detail";
			/** 团购商品数量 */
			public static final String KEY_GROUPBUY_ITEM_COUNT = "Key_GroupBuy_Item_Count";
			/** 团购信息 */
			public static final String KEY_GROUPBUY_INFO = "Key_GroupBuy_Info";
		}

		public static interface address {

			/** 地址缓存 */
			public static final String KEY_ADDRESS = "Key_Address";
		}

		public static interface product {

			/** 商品缓存 */
			public static final String KEY_PRODUCT = "Key_Product";
			
			/** 商品订单 */
			public static final String KEY_PRODUCTORDER = "Key_ProductOrder";
		}

		
		public static interface buyHistory {

			/** 购买推荐关系缓存 */
			public static final String KEY_BUYHISTORY = "Key_BuyHistory";
			
			/** 二维码信息缓存 */
			public static final String KEY_TCODE="Key_TCODE";
			
			/** 用户购买关系 */
			
			public static final String KEY_BUY = "Key_Buy";
			
		}
	}

	/**
	 * 得到cachekey方法前缀
	 * 
	 * @param key
	 *            cachekey
	 * @param openId
	 *            微信用户openId/app用户userkey/唯一标识号
	 * @param methodName
	 *            方法名
	 * @param parameters
	 *            参数类型
	 * @return 拼接的方法前缀cachekey
	 * @author guchangpeng
	 * @date 2014-07-15
	 */
	@SuppressWarnings({ "unchecked" })
	public static StringBuffer getCachekeyMethodPrx(String key, String id,
			String methodName, Object[] parameters) {
		StringBuffer sBuffer = new StringBuffer("");
		sBuffer.append(key).append(Constant.DBTSPLIT);
		// 对象唯一标识
		if (!StringUtils.isEmpty(id)) {
			sBuffer.append(id).append(Constant.DBTSPLIT);
		}
		// 方法名
		if (!StringUtils.isEmpty(methodName)) {
			sBuffer.append(methodName).append(Constant.DBTSPLIT);
		}

		StringBuffer paramBuffer = new StringBuffer("");
		if (null != parameters && parameters.length > 0) {
			try {
				for (Object parameter : parameters) {
					if (null != parameter) {
						if (parameter instanceof Map) {
							Map<String, Object> map = (Map<String, Object>) parameter;
							Iterator<Entry<String, Object>> iterator = map
									.entrySet().iterator();
							while (iterator.hasNext()) {
								Entry<String, Object> entry = iterator.next();
								// value为list时，存在空格
								if (entry.getValue() instanceof List) {
									paramBuffer.append(
											entry.getValue().toString()
													.replaceAll(" ", ""))
											.append(Constant.DBTSPLIT);
								} else {
									paramBuffer.append(entry.getValue())
											.append(Constant.DBTSPLIT);
								}

							}
						} else if (parameter instanceof List) {
							List<Object> objList = (List<Object>) parameter;
							for (Object object : objList) {
								paramBuffer.append(object).append(
										Constant.DBTSPLIT);
							}
						} else if (parameter instanceof String
								|| parameter instanceof Integer
								|| parameter instanceof Double
								|| parameter instanceof Float
								|| parameter instanceof Long
								|| parameter instanceof Boolean
								|| parameter instanceof Date) {
							paramBuffer.append(parameter).append(
									Constant.DBTSPLIT);
						} else {
							Field[] fields = parameter.getClass()
									.getDeclaredFields();
							Object fieldValueObject = null;
							for (Field field : fields) {
								/*
								 * if (Modifier.isStatic(field.getModifiers())
								 * || Modifier.isFinal(field.getModifiers())) {
								 * continue; }
								 */
								field.setAccessible(true);
								if (Modifier.isTransient(field.getModifiers())) {
									continue;
								}
								fieldValueObject = field.get(parameter);
								if (fieldValueObject != null
										&& StringUtils
												.isNotBlank(fieldValueObject
														.toString())) {
									paramBuffer.append(
											fieldValueObject.toString())
											.append(Constant.DBTSPLIT);
								}
							}
						}
					}
				}
			} catch (SecurityException e) {
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				e.printStackTrace();

			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}

		}
		return sBuffer.append(paramBuffer.toString().hashCode());
	}

	/**
	 * 同步缓存、并删除本服务缓存操作（app端做修改、添加、删除数据时，需要调用该方法）
	 * 
	 * @param key
	 *            只传cacheKey中key
	 * @param flag
	 *            同步方向 true：正向 false：逆向
	 * @author guchangpeng
	 * @date 2014-07-15
	 */
	public static void syncOperate(String[] keys, boolean flag) {
		try {
			StringBuffer keyBuffer = new StringBuffer();
			// 删除缓存
			if (null != keys && keys.length > 0) {
				for (String key : keys) {
					if (StringUtils.isNotBlank(key)) {
						keyBuffer.append(key).append(",");
						mirrorCachClient.removeByRegex(key);
					}
				}
			}
			// 同步平台
			if (flag) {
				/*
				 * StringBuffer urlBuffer = new StringBuffer(80);
				 * urlBuffer.append(PropertiesUtil
				 * .getPropertyValue("operator_platform_url"));
				 * urlBuffer.append(PropertiesUtil
				 * .getPropertyValue("interface_sysnc_platform_url"));
				 * urlBuffer.append("?removeType=").append("1");
				 * urlBuffer.append("&key=").append(
				 * keyBuffer.toString().substring(0,
				 * keyBuffer.toString().length() - 1));
				 * HttpClientUtil.httpReq(urlBuffer.toString(), "", null);
				 */
			}
		} catch (IllegalStateException e) {
			LOG.error(
					"In the method CacheUtil.syncOperate(String[] keys, boolean flag) exists error!",
					e);
		}
	}

	/**
	 * 为缓存区域增加缓存对象
	 * 
	 * @param key
	 * @param value
	 */
	public static void put(String key, Object value) {
		// mirrorCachClient.set(key, SECONDS_TO_EXPIRE, value);
		setCacheValueCommon(key, value, SECONDS_TO_EXPIRE);
	}

	/**
	 * 增加指定有效期内的缓存
	 * 
	 * @param key
	 * @param value
	 * @param expireTime
	 */
	public static void put(String key, Object value, int epireTime) {
		setCacheValueCommon(key, value, epireTime);
	}

	@SuppressWarnings("unchecked")
	private static void setCacheValueCommon(String keyName, Object obj,
			int expireTime) {
		String[] keys = keyName.split(Constant.DBTSPLIT);
		String entityName = "";
		String id = "";
		if (keys != null && keys.length > 1) {
			entityName = keys[0];
			id = keys[1];
		}
		// 维护entityName_id对应的一组缓存key
		if (!CheckUtil.isEmpty(id)) {
			String idCacheKey = entityName + Constant.DBTSPLIT + id;
			Object object = mirrorCachClient.get(idCacheKey);
			List<String> idValueList = null;
			if (!CheckUtil.isEmpty(object)) {
				idValueList = (List<String>) object;
				if (!CheckUtil.isEmpty(idValueList)) {
					if (!idValueList.contains(keyName)) {
						idValueList.add(keyName);
						mirrorCachClient.set(idCacheKey, expireTime,
								idValueList);
					}
				} else {
					idValueList = new ArrayList<String>();
					idValueList.add(keyName);
					mirrorCachClient.set(idCacheKey, expireTime, idValueList);
				}
			} else {
				idValueList = new ArrayList<String>();
				idValueList.add(keyName);
				mirrorCachClient.set(idCacheKey, expireTime, idValueList);
			}
		}
		// 基础存放
		mirrorCachClient.set(keyName, expireTime, obj);

	}

	/**
	 * 删除一组缓存key
	 * 
	 * @param keyName
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public static void removeGroupByKey(String keyName) throws Exception {
		String[] keys = keyName.split(Constant.DBTSPLIT);
		String entityName = "";
		String id = "";
		if (keys != null && keys.length > 1) {
			entityName = keys[0];
			id = keys[1];
		}
		if (!CheckUtil.isEmpty(entityName) && !CheckUtil.isEmpty(id)) {
			String idCacheKey = entityName + Constant.DBTSPLIT + id;
			Object object = mirrorCachClient.get(idCacheKey);
			if (!CheckUtil.isEmpty(object)) {
				List<String> idValueList = (List<String>) object;
				if (!CheckUtil.isEmpty(idValueList)) {
					for (String key : idValueList) {
						mirrorCachClient.delete(key);
					}
				}
			}
		}
	}

	/**
	 * 更新缓存
	 * 
	 * @param key
	 * @param value
	 */
	public static void update(String key, Object value) {
		mirrorCachClient.update(key, SECONDS_TO_EXPIRE, value);
	}

	/**
	 * 得到指定key对应的缓存对象
	 * 
	 * @param key
	 */
	public static Object getObjectValue(String key) {
		return mirrorCachClient.get(key);
	}

	/**
	 * 移除当前缓存区域的所有对象
	 */
	public static void removeAll() {
		mirrorCachClient.deleteAll();
	}

	/**
	 * 正则查找所有的缓存key(即sql中的like)
	 * 
	 * @author gucp
	 */
	public static List<String> foreachByRegex(String keyRegex) {
		return mirrorCachClient.foreachByRegex(keyRegex);
	}

	/**
	 * 正则查找所有的缓存key(即sql中的like)-value
	 * 
	 * @author gucp
	 */
	public static Map<String, Object> queryValueByKey(String keyRegex) {
		return mirrorCachClient.queryValueByKey(foreachByRegex(keyRegex));
	}

	/**
	 * 根据具体key删除缓存
	 * 
	 * @param key
	 * @throws IOException
	 */
	public static void removeByKey(String key) throws IOException {
		mirrorCachClient.delete(key);
	}
}