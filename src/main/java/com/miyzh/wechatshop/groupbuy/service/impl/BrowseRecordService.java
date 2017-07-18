package com.miyzh.wechatshop.groupbuy.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.miyzh.wechatshop.groupbuy.bean.BrowseRecord;
import com.miyzh.wechatshop.groupbuy.dao.IBrowseRecordDao;
import com.miyzh.wechatshop.groupbuy.service.IBrowseRecordService;

/**
 * <pre>
 * the BrowseRecordService class for
 * 埋点服务类
 * </pre>
 *
 * @author zhangtao 2016年7月11日
 */
@Service
public class BrowseRecordService implements IBrowseRecordService {
	
	@Autowired
	private IBrowseRecordDao dao;

	@Override
	public void addBrowseRecord(BrowseRecord br) {
		dao.create(br);
	}

}
