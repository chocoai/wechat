package com.miyzh.wechatshop.payUtil.service;

import java.util.List;
import java.util.Map;

import com.miyzh.wechatshop.payUtil.bean.Address;

public interface AddressService {

	public List<Address> findByParentId(String parentId);

	/**
	 * 地区查询
	 * 
	 * @param map
	 *            keys: code keys:id
	 * @return
	 */
	public Address findByMap(String id, String code);

	/**
	 * 查询省、市、县
	 * 
	 * @param name
	 *            城市名称
	 * @return
	 */
	public Address findByName(String name);

	
	public Address findByAreaName(String areaCode) ;
}
