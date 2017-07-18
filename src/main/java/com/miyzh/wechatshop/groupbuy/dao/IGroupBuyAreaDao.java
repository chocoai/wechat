package com.miyzh.wechatshop.groupbuy.dao;

import java.util.List;

import com.miyzh.wechatshop.groupbuy.bean.GroupBuyArea;

public interface IGroupBuyAreaDao {
	
	public List<GroupBuyArea> queryAreaByGroupBuyId(String groupbuyId);

}
