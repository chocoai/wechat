package com.miyzh.wechatshop.payUtil.dao;


import com.miyzh.framework.base.dao.IBaseDao;
import com.miyzh.wechatshop.payUtil.bean.ShopSendLog;

public interface ShopSendLogDao extends IBaseDao<ShopSendLog> {

	 public  long saveSendLog(ShopSendLog sendLog);
}
