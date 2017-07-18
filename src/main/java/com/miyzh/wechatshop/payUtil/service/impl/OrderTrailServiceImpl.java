package com.miyzh.wechatshop.payUtil.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.miyzh.wechatshop.payUtil.bean.ShopOrderTrail;
import com.miyzh.wechatshop.payUtil.bean.ShopSendLog;
import com.miyzh.wechatshop.payUtil.dao.ShopOrderTrailDao;
import com.miyzh.wechatshop.payUtil.dao.ShopSendLogDao;
import com.miyzh.wechatshop.payUtil.service.OrderTrailService;
import com.miyzh.wechatshop.payUtil.service.SendLogService;

@Service("orderTrailService")
public class OrderTrailServiceImpl implements OrderTrailService {
 
	
	@Autowired
	private ShopOrderTrailDao shopOrderTrailDao;


	@Override
	public void creta(ShopOrderTrail shopOrderTrail) {
		// TODO Auto-generated method stub
		shopOrderTrailDao.create(shopOrderTrail);
	}
	

	
}
