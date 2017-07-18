package com.miyzh.wechatshop.payUtil.dao;

import java.util.List;
import java.util.Map;

import com.miyzh.framework.base.dao.IBaseDao;
import com.miyzh.wechatshop.payUtil.bean.Address;

public interface AddressDao extends IBaseDao<Address> {

	/**
	 * 地区查询
	 * 
	 * @param map
	 *            keys: code keys:id
	 * @return
	 */
	public Address findByMap(Map<String, Object> map);

	/****
	 * 地区查询
	 * 
	 * @param map
	 * @return
	 */

	public List<Address> findByParentId(Map<String, Object> map);

	/**
	 * 查询省、市、县
	 * 
	 * @param map
	 *            key:name 城市名称
	 * @return
	 */
	public Address findAddress(Map<String, Object> map);
}
