package com.miyzh.wechatshop.payUtil.dao;

import java.util.List;
import java.util.Map;

import com.miyzh.framework.base.dao.IBaseDao;
import com.miyzh.wechatshop.payUtil.bean.ShopMemberAddress;

public interface MemberAddressDao extends IBaseDao<ShopMemberAddress> {

	/****
	 * 用户地址信息查询
	 * 
	 * @param map
	 * @return
	 */
	
	public List<ShopMemberAddress> findByMemberId(Map<String, Object> map);
	
	public void updateByIsDault(Map<String, Object> map);
}
