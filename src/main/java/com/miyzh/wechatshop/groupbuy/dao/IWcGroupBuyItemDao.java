package com.miyzh.wechatshop.groupbuy.dao;

import java.util.List;

import com.miyzh.wechatshop.groupbuy.bean.GroupBuyItem;

public interface IWcGroupBuyItemDao {
	
	/**
	 * <pre>
	 * the findByGroupBuyId method for
	 * 根据团购ID查询商品明细
	 * </pre>
	 *
	 * @param groupBuyId
	 * @return
	 * @author zhangtao 2016年7月11日
	 */
	public List<GroupBuyItem> findByGroupBuyId(String groupBuyId);
	
	/**
	 * <pre>
	 * the queryCountByGroupBuyId method for
	 * 根据团购ID查询商品数量
	 * </pre>
	 *
	 * @param groupBuyId
	 * @return
	 * @author zhangtao 2016年7月18日
	 */
	public Integer queryCountByGroupBuyId(String groupBuyId);
}
