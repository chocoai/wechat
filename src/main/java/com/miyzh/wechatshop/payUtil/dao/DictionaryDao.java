package com.miyzh.wechatshop.payUtil.dao;


import com.miyzh.framework.base.dao.IBaseDao;
import com.miyzh.wechatshop.payUtil.bean.Dictionary;

public interface DictionaryDao extends IBaseDao<Dictionary> {

	public Dictionary findByTypeId(String typeId);
}
