package com.miyzh.wechatshop.payUtil.service;


import com.miyzh.wechatshop.payUtil.bean.ProductInfo;
import com.miyzh.wechatshop.payUtil.toolBean.BeanTools;

public interface ProductService {

	public BeanTools findByParentId(String products, String groupID,String memberID,String addressId);
	public ProductInfo findByProductId(String productId,String groupId);
	
	public String findRecommendUserid(String groupId,String opendId,String recommenduserid);
}
