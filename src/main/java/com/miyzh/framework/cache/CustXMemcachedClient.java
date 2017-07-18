package com.miyzh.framework.cache;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeoutException;
import java.util.regex.Pattern;

import net.rubyeye.xmemcached.KeyIterator;
import net.rubyeye.xmemcached.MemcachedClient;
import net.rubyeye.xmemcached.XMemcachedClient;
import net.rubyeye.xmemcached.XMemcachedClientBuilder;
import net.rubyeye.xmemcached.exception.MemcachedException;
import net.rubyeye.xmemcached.utils.AddrUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.miyzh.framework.util.PropertiesUtil;

/**
 * 文件名: XmemcachedClient.java<br>
 * 版权：Copyright 2014 Digital Bay Technology Group. Co. Ltd. All Rights Reserved. <br>
 * 描述: 定制memcached接口实现类 <br>
 * 修改人: guchangpeng <br>
 * 修改时间：2014-9-4 下午02:00:42 <br>
 * 修改内容：新增 <br>
 */
@SuppressWarnings("deprecation")
@Component("custXMemcachedClient")
public class CustXMemcachedClient extends XMemcachedClientBuilder implements
		ICustXMemcachedClient {

	private final static Log LOG = LogFactory
			.getLog(CustXMemcachedClient.class);
	private List<InetSocketAddress> inetsocketaddressList;
	@Autowired
	@Qualifier("memcachedClient")
	private MemcachedClient memcachedClient;

	public CustXMemcachedClient() {
		// 获取
		/*
		 * Map<InetSocketAddress, AuthInfo> authInfo = getAuthInfoMap(); if
		 * (null != authInfo && !authInfo.isEmpty()) {
		 * Iterator<InetSocketAddress> iterator = authInfo.keySet().iterator();
		 * while (iterator.hasNext()) {
		 * this.inetsocketaddressList.add(iterator.next());
		 * 
		 * } }
		 */
		// todo
		inetsocketaddressList = AddrUtil.getAddresses(PropertiesUtil
				.getPropertyValue("memcache_inetsocketaddress"));
	}

	/**
	 * 初始化memcached客户端
	 */
	public boolean isInitialized() {
		return memcachedClient != null;
	}

	/**
	 * 连接memcache服务端
	 * 
	 * @param List
	 *            <InetSocketAddress>
	 */
	public void initialize(List<InetSocketAddress> addresses)
			throws IOException {
		memcachedClient = new XMemcachedClient(addresses);
	}

	/**
	 * 加入memcache缓存
	 * 
	 * @param key
	 * @param secondsToExpire
	 *            过期时间
	 * @param value
	 */
	public void set(String key, int secondsToExpire, Object value) {
		try {
			if (null == value) {
				return;
			}
			memcachedClient.set(key, secondsToExpire, value);
		} catch (Exception e) {
			LOG.error("Failed to set value on memcachedClient! " + "(key:"
					+ key + ",secondsToExpire:" + secondsToExpire + ",value:"
					+ value + ")", e);
		}
	}

	/**
	 * 加入memcache缓存并检测是否放入成功
	 * 
	 * @param key
	 * @param secondsToExpire
	 *            过期时间
	 * @param value
	 */
	public void setAndEnsure(String key, int secondsToExpire, Object value)
			throws IOException {
		try {
			set(key, secondsToExpire, value);
			Object cached = (Object) memcachedClient.get(key);
			if (cached == null) {
				String failedMessage = "Failed to set value on memcachedClient! "
						+ "(key:"
						+ key
						+ ",secondsToExpire:"
						+ secondsToExpire
						+ ",value:" + value + ")";
				throw new IOException(failedMessage);
			}
		} catch (Exception e) {
			String failedMessage = "Failed to set value on memcachedClient! "
					+ "(key:" + key + ",secondsToExpire:" + secondsToExpire
					+ ",value:" + value + ")";
			throw new IOException(failedMessage, e);
		}
	}

	/**
	 * 取出缓存
	 * 
	 * @param key
	 */
	public Object get(String key) {
		try {
			return memcachedClient.get(key);
		} catch (Exception e) {
			LOG.error("Failed to get value on memcachedClient! (key:" + key
					+ ")", e);
		}
		return null;
	}

	/**
	 * 删除缓存
	 * 
	 * @param key
	 */
	public void delete(String key) throws IOException {
		try {
			memcachedClient.delete(key);
		} catch (Exception e) {
			String failedMessage = "Failed to delete value on memcachedClient! (key:"
					+ key + ")";
			throw new IOException(failedMessage, e);
		}
	}

	/**
	 * 在现有数据后加入memcache缓存
	 * 
	 * @param key
	 * @param secondsToExpire
	 *            过期时间
	 * @param value
	 */
	public void append(String key, int secondsToExpire, Object value)
			throws IOException {
		try {
			memcachedClient.append(key, value, secondsToExpire);
		} catch (Exception e) {
			String failedMessage = "Failed to append value on memcachedClient! "
					+ "(key:"
					+ key
					+ ",secondsToExpire:"
					+ secondsToExpire
					+ ",value:" + value + ")";
			throw new IOException(failedMessage, e);
		}
	}

	/**
	 * 在现有数据前加入memcache缓存
	 * 
	 * @param key
	 * @param secondsToExpire
	 *            过期时间
	 * @param value
	 */
	public void prepend(String key, int secondsToExpire, Object value)
			throws IOException {
		try {
			memcachedClient.prepend(key, value, secondsToExpire);
		} catch (Exception e) {
			String failedMessage = "Failed to prepend value on memcachedClient! "
					+ "(key:"
					+ key
					+ ",secondsToExpire:"
					+ secondsToExpire
					+ ",value:" + value + ")";
			throw new IOException(failedMessage, e);
		}
	}

	/**
	 * 更新缓存
	 * 
	 * @param key
	 * @param secondsToExpire
	 *            过期时间
	 * @param value
	 */
	public void update(String key, int secondsToExpire, Object value) {
		try {
			memcachedClient.replace(key, secondsToExpire, value);
		} catch (Exception e) {
			String failedMessage = "Failed to replace value on memcachedClient! "
					+ "(key:"
					+ key
					+ ",secondsToExpire:"
					+ secondsToExpire
					+ ",value:" + value + ")";
			LOG.error(failedMessage, e);
		}
	}

	public void deleteAll() {
		try {
			if (null != inetsocketaddressList
					&& !inetsocketaddressList.isEmpty()) {
				for (InetSocketAddress inetSocketAddress : inetsocketaddressList) {
					memcachedClient.flushAll(inetSocketAddress);
				}
			}
		} catch (TimeoutException e) {
			LOG.error(
					"In the method CustXMemcachedClient.deleteAll() exists error",
					e);
		} catch (InterruptedException e) {
			LOG.error(
					"In the method CustXMemcachedClient.deleteAll() exists error",
					e);
		} catch (MemcachedException e) {
			LOG.error(
					"In the method CustXMemcachedClient.deleteAll() exists error",
					e);
		}
	}

	public void removeByRegex(String regex) {
		try {
			// 迭代的只是一个keys的快照，因此可以在迭代时删除
			Pattern p = Pattern.compile(regex);
			if (null != inetsocketaddressList
					&& !inetsocketaddressList.isEmpty()) {
				String cacheKey = null;// 缓存key
				for (InetSocketAddress inetSocketAddress : inetsocketaddressList) {
					KeyIterator keyIterator = memcachedClient
							.getKeyIterator(inetSocketAddress);
					if (null != keyIterator) {
						while (keyIterator.hasNext()) {
							cacheKey = (String) keyIterator.next();
							if (p.matcher(cacheKey).find()) {
								memcachedClient.delete(cacheKey);
								LOG.debug("从缓存中移除对象Keys：【" + cacheKey + "】");
							}

						}
					}
				}
			}

		} catch (MemcachedException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (TimeoutException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 遍历缓存key
	 */
	public List<String> foreachByRegex(String regex) {
		List<String> keyList = new ArrayList<String>();
		try {
			// 迭代的只是一个keys的快照，因此可以在迭代时删除
			Pattern p = Pattern.compile(regex);
			if (null != inetsocketaddressList
					&& !inetsocketaddressList.isEmpty()) {
				String cacheKey = null;// 缓存key
				for (InetSocketAddress inetSocketAddress : inetsocketaddressList) {
					KeyIterator keyIterator = memcachedClient
							.getKeyIterator(inetSocketAddress);
					if (null != keyIterator) {
						while (keyIterator.hasNext()) {
							cacheKey = (String) keyIterator.next();
							if (p.matcher(cacheKey).find()) {
								keyList.add(cacheKey);// 放到key列表
							}
						}
					}
				}
			}
		} catch (MemcachedException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (TimeoutException e) {
			e.printStackTrace();
		}
		return keyList;
	}

	/**
	 * 遍历缓存key列表取对应值
	 */
	public Map<String, Object> queryValueByKey(List<String> keyList) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (null != keyList && keyList.size() > 0) {
			for (int i = 0, len = keyList.size(); i < len; i++) {
				map.put(keyList.get(i), get(keyList.get(i)));
			}
		}
		return map;
	}
}