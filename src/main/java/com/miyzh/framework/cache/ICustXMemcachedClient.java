package com.miyzh.framework.cache;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.List;

/**
 * 文件名: IMemcachedClient.java<br>
 * 版权：Copyright 2014 Digital Bay Technology Group. Co. Ltd. All Rights Reserved. <br>
 * 描述: memcached接口 <br>
 * 修改人: guchangpeng <br>
 * 修改时间：2014-9-4 下午01:53:33 <br>
 * 修改内容：新增 <br>
 */
public interface ICustXMemcachedClient {

	/**
	 * 初始化marcache客户端配置信息
	 * 
	 * @return
	 */
	boolean isInitialized();

	/**
	 * 连接memcache服务端
	 * 
	 * @param addresses  InetSocketAddressList
	 * @throws IOException
	 */
	void initialize(List<InetSocketAddress> addresses) throws IOException;

	/**
	 * 向marcache中set值
	 * @param key
	 * @param secondsToExpire
	 * @param value
	 * @throws IOException
	 */
	void set(String key, int secondsToExpire, Object value);

	 void setAndEnsure(String key, int secondsToExpire, Object value)
			throws IOException;

	 Object get(String key);

	void delete(String key) throws IOException;

	 void append(String key, int secondsToExpire, Object value)
			throws IOException;

	 void prepend(String key, int secondsToExpire, Object value)
			throws IOException;

	 void update(String key, int secondsToExpire, Object value)
			throws IOException;
	/**
	 * 清除所有Memcache上的缓存
	 */
	void deleteAll();
	
	/**
	 * 根据正则表达式将数据从缓存中移除
	 * @param key 要删除的key
	 */
	void removeByRegex(String regex);
}