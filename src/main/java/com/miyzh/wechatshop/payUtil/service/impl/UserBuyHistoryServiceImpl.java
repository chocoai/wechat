package com.miyzh.wechatshop.payUtil.service.impl;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.miyzh.framework.util.CacheUtil;
import com.miyzh.wechatshop.payUtil.service.UserBuyHistoryService;
import com.miyzh.wechatshop.payUtil.toolBean.UserBeanTools;

@Service("userBuyHistoryService")
public class UserBuyHistoryServiceImpl implements UserBuyHistoryService {



	@Override
	public void saveUserBuyHistory(String groupId,String userKey,String openId) {
		
		StringBuffer keyBuffer = CacheUtil.getCachekeyMethodPrx(
				CacheUtil.cacheKey.buyHistory.KEY_BUY, openId+groupId,"findRecommendUserid",
				new Object[] {});
		Object object = CacheUtil.getObjectValue(keyBuffer.toString());
		UserBeanTools userBeanTools=null;
		 String recommenduserid="";
		if (object != null) {
			userBeanTools= (UserBeanTools) object;
			  recommenduserid=	userBeanTools.getRecommenduserid();
				try {
					CacheUtil.removeGroupByKey(CacheUtil.cacheKey.buyHistory.KEY_BUY+"_"+ openId+groupId);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

		}
		
	   if(!"".equals(recommenduserid)){
		StringBuffer keyBuffer1 = CacheUtil.getCachekeyMethodPrx(CacheUtil.cacheKey.buyHistory.KEY_BUYHISTORY, openId+groupId,
				"saveUserBuyHistory", new Object[] {});
		Object object1 = CacheUtil.getObjectValue(keyBuffer1.toString());
		if (object1 == null) {
			Map<String ,Object> map=new HashMap<>();
			map.put("recommenduserid", recommenduserid);
			map.put("userKey", userKey);
			map.put("groupId", groupId);
			map.put("openId", openId);
			CacheUtil.put(keyBuffer1.toString(), map);
		}
	   }
		
	}
	

	
}
