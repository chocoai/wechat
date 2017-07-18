package com.miyzh.wechatshop.groupbuy.service;

import java.util.List;

import com.miyzh.wechatshop.groupbuy.bean.GroupBuy;
import com.miyzh.wechatshop.groupbuy.report.GroupbuyPreview;
import com.miyzh.wechatshop.groupbuy.report.GroupbuyQueryResult;

/**
 * 
 * @author liangpeichang
 *
 * date 2016年7月8日
 */

public interface IWcGroupBuyService {
	public   GroupBuy  findById(String id);
	public   List<GroupBuy> getList(GroupBuy groupBuy);
	/**
	 * <pre>
	 * the queryGroupBuyList method for
	 * 正在疯抢
	 * </pre>
	 *
	 * @param openId
	 * @return
	 * @author zhangtao 2016年7月11日
	 */
	public List<GroupBuy> queryGroupBuyList(String openId);
	
	/**
	 * <pre>
	 * the toGroupbuyPreview method for
	 * 团购预览页
	 * </pre>
	 *
	 * @param requestParam
	 * @return
	 * @author zhangtao 2016年7月11日
	 */
	public GroupbuyQueryResult queryGroupbuyPreview(GroupbuyPreview requestParam);
	/**
	 * <pre>
	 * the queryGroupbuyDetail method for
	 * 团购详情页
	 * </pre>
	 *
	 * @param requestParam
	 * @return
	 * @author zhangtao 2016年7月11日
	 */
	public GroupbuyQueryResult queryGroupbuyDetail(GroupbuyPreview requestParam);
	
	/**
	 * <pre>
	 * the queryGroupbuyDetail method for
	 * 商品详情页
	 * </pre>
	 *
	 * @param requestParam
	 * @return
	 * @author zhangtao 2016年7月11日
	 */
	public GroupbuyQueryResult queryGroupbuyItemDetail(GroupbuyPreview requestParam);
	
	/**
	 * <pre>
	 * the queryGroupbuyItemCount method for
	 * 获取团购商品数量
	 * </pre>
	 *
	 * @param groupbuyId
	 * @return
	 * @author zhangtao 2016年7月18日
	 */
	public Integer queryGroupbuyItemCount(String groupbuyId);
	/**
	 * <pre>
	 * the queryGroupbuyDetail method for
	 * 团购结束页
	 * </pre>
	 *
	 * @param requestParam
	 * @return
	 * @author liangpeichang 2016年7月18日
	 */
	public List<GroupBuy> queryFinishGroupBuyList(String openId);
	/**
	 * <pre>
	 * the queryGroupbuyDetail method for
	 * 敬请期待页
	 * </pre>
	 *
	 * @param requestParam
	 * @return
	 * @author liangpeichang 2016年7月18日
	 */
	public List<GroupBuy> queryNoStartGroupBuyList(String openId);

}
