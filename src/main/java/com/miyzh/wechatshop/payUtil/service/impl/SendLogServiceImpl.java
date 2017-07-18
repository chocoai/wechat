package com.miyzh.wechatshop.payUtil.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.miyzh.wechatshop.payUtil.bean.ShopSendLog;
import com.miyzh.wechatshop.payUtil.dao.ShopSendLogDao;
import com.miyzh.wechatshop.payUtil.service.SendLogService;

@Service("sendLogService")
public class SendLogServiceImpl implements SendLogService {
 
	
	@Autowired
	private ShopSendLogDao shopSendLogDao;

	@Override
	public Long saveSendLog(ShopSendLog sendLog) {
		// TODO Auto-generated method stub
		long l=shopSendLogDao.saveSendLog(sendLog);
		return sendLog.getId();
	}
	

	
}
