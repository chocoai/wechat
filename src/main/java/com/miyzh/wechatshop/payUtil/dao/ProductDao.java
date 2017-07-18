package com.miyzh.wechatshop.payUtil.dao;

import java.util.Map;

import com.miyzh.framework.base.dao.IBaseDao;
import com.miyzh.wechatshop.payUtil.bean.ProductInfo;

public interface ProductDao extends IBaseDao<ProductInfo> {

	/****
	 * 订单清单商品查询
	 * @param map
	 * @return
	 */
	
	public ProductInfo findByProductId(Map<String,Object> map);
}
