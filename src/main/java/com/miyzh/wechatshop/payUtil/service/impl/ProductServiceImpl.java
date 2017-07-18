package com.miyzh.wechatshop.payUtil.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.miyzh.framework.util.CacheUtil;
import com.miyzh.wechatshop.groupbuy.bean.GroupBuy;
import com.miyzh.wechatshop.groupbuy.service.IWcGroupBuyService;
import com.miyzh.wechatshop.payUtil.bean.Address;
import com.miyzh.wechatshop.payUtil.bean.ProductInfo;
import com.miyzh.wechatshop.payUtil.bean.ShopMemberAddress;
import com.miyzh.wechatshop.payUtil.dao.AddressDao;
import com.miyzh.wechatshop.payUtil.dao.MemberAddressDao;
import com.miyzh.wechatshop.payUtil.dao.ProductDao;
import com.miyzh.wechatshop.payUtil.service.AddressService;
import com.miyzh.wechatshop.payUtil.service.MemberAddressService;
import com.miyzh.wechatshop.payUtil.service.ProductService;
import com.miyzh.wechatshop.payUtil.toolBean.BeanTools;
import com.miyzh.wechatshop.payUtil.toolBean.UserBeanTools;
import com.miyzh.wechatshop.user.bean.ThirdUserInfoBean;

@Service("ProductService")
public class ProductServiceImpl implements ProductService {
	@Autowired
	private ProductDao productDao;
     
	@Autowired
	private MemberAddressService memberAddressService;

	@Autowired
	private IWcGroupBuyService iwcGroupBuyService;
	
	@Override
	public BeanTools findByParentId(String products,String groupID,String memberID,String addressId) {
		BeanTools beanTools=new BeanTools();
		List<ProductInfo> prInfosTmp=new ArrayList<>();
		String [] productList=products.split(",");   //多件商品拆分
		ProductInfo productInfoTmp=null;
		String [] product=null;
		double total=0;
		double count=0;
		for(int i=0;i<productList.length;i++){
			product=productList[i].split("@");   //商品ID与商品数量拆分
			StringBuffer keyBuffer = CacheUtil.getCachekeyMethodPrx(
					CacheUtil.cacheKey.product.KEY_PRODUCT, product[0], Thread
							.currentThread().getStackTrace()[1].getMethodName(),
					new Object[] {groupID});
			Object object = CacheUtil.getObjectValue(keyBuffer.toString());
			if (object == null) {
				productInfoTmp=new ProductInfo();
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("productId", product[0]);
				map.put("groupId", groupID);
				productInfoTmp=productDao.findByProductId(map);
				CacheUtil.put(keyBuffer.toString(), productInfoTmp);
				productInfoTmp.setProductCount(product[1]);
				total+=Double.valueOf(product[1])*Double.valueOf(productInfoTmp.getGroupPrice());
				count+=Double.valueOf(product[1]);  //商品数量
				prInfosTmp.add(productInfoTmp);
			}else{
				productInfoTmp=(ProductInfo) object;
				productInfoTmp.setProductCount(product[1]);
				total+=Double.valueOf(product[1])*Double.valueOf(productInfoTmp.getGroupPrice());
				count+=Double.valueOf(product[1]); //商品数量
				prInfosTmp.add(productInfoTmp);
			}
			
		}
		GroupBuy groupBuy=iwcGroupBuyService.findById(groupID);
		List<ShopMemberAddress> list=null;
		if(memberID!=null&&!"".equals(memberID)){
		list = memberAddressService.findByMemberId(Long.valueOf(memberID),"",addressId);
		if(list!=null&&list.size()>0){
			beanTools.setShopMemberAddress(list.get(0));
			beanTools.setFlag("1");
		}else{
			beanTools.setFlag("0");
		}
		}
		if(groupBuy.getMinBuyQuantity()>count){
			beanTools.setStatus("1");
			beanTools.setCount(String.valueOf(groupBuy.getMinBuyQuantity()));
		}else{
			beanTools.setStatus("2");
		}
		beanTools.setTotal(String.valueOf(total));
		beanTools.setProducts(products);
		beanTools.setGroupID(groupID);
		beanTools.setProductInfo(prInfosTmp);
		
		return beanTools;
	}

	@Override
	public ProductInfo findByProductId(String productId, String groupId) {
		ProductInfo	productInfoTmp = null;
		Map<String, Object> map1 = new HashMap<String, Object>();
		map1.put("productId", productId);
		map1.put("groupId", groupId);
		productInfoTmp = productDao.findByProductId(map1);
		return productInfoTmp;
	}

	@Override
	public String findRecommendUserid(String groupId, String opendId,String recommenduserid) {
		StringBuffer keyBuffer = CacheUtil.getCachekeyMethodPrx(
				CacheUtil.cacheKey.buyHistory.KEY_BUY, opendId+groupId, "findRecommendUserid",
				new Object[] {});
		Object object = CacheUtil.getObjectValue(keyBuffer.toString());
		UserBeanTools userBeanTools=null;
		if (object == null) {
			userBeanTools=new UserBeanTools();
			userBeanTools.setGroupID(groupId);
			userBeanTools.setOpendid(opendId);
			userBeanTools.setRecommenduserid(recommenduserid);
			CacheUtil.put(keyBuffer.toString(), userBeanTools);
		}
		userBeanTools= (UserBeanTools) object;
		
		return userBeanTools.getRecommenduserid();
		
	}



}
