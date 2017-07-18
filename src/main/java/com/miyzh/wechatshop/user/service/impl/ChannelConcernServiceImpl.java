package com.miyzh.wechatshop.user.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.miyzh.wechatshop.user.bean.ChannelConcernBean;
import com.miyzh.wechatshop.user.dao.IChannelConcernDao;
import com.miyzh.wechatshop.user.service.IChannelConcernService;

@Service("ChannelConcernServiceImpl")
public class ChannelConcernServiceImpl implements IChannelConcernService {

	@Autowired
	private IChannelConcernDao channelConcernDao;

	@Override
	public void addChannelConcern(ChannelConcernBean channelConcernBean)
			throws Exception {
		channelConcernDao.create(channelConcernBean);
	}

	@Override
	public ChannelConcernBean queryChannelConcern(String openId,
			Integer concernType) {

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("openId", openId);
		map.put("concernType", concernType);

		return channelConcernDao.findByMap(map);
	}

}
