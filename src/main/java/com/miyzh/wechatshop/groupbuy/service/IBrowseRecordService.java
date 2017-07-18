package com.miyzh.wechatshop.groupbuy.service;

import com.miyzh.wechatshop.groupbuy.bean.BrowseRecord;

/**
 * <pre>
 * the IBrowseRecordService class for
 * 埋点服务类
 * </pre>
 *
 * @author zhangtao 2016年7月11日
 */
public interface IBrowseRecordService {
	
	/**
	 * <pre>
	 * the addBrowseRecord method for
	 * 增加埋点
	 * </pre>
	 *
	 * @param br
	 * @author zhangtao 2016年7月11日
	 */
	public void addBrowseRecord(BrowseRecord br);

}
