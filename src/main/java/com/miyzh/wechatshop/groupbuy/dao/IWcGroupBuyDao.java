package com.miyzh.wechatshop.groupbuy.dao;

import java.util.List;
import java.util.Map;

import com.miyzh.framework.base.dao.IBaseDao;
import com.miyzh.wechatshop.groupbuy.bean.GroupBuy;

  
/**
 * 
 * @author liangpeichang
 *
 * date 2016年7月8日
 */
public interface IWcGroupBuyDao extends IBaseDao<GroupBuy>{ 
	
	/**
	 * <pre>
	 * the findNowGroupBuyInfo method for
	 * 查询正在疯抢的团购列表
	 * </pre>
	 *
	 * @return
	 * @author zhangtao 2016年7月9日
	 */
	public List<GroupBuy> findNowGroupBuyInfo(Map<String,Object> map);
	/**
	 * <pre>
	 * the findFinishGroupBuyInfo method for
	 * 查询已经结束的团购列表
	 * </pre>
	 *
	 * @return
	 * @author liangpeichang 2016年7月11日
	 */
	public List<GroupBuy> findFinishGroupBuyInfo(Map<String,Object> map);
	
	/**
	 * <pre>
	 * the findFinishGroupBuyInfo method for
	 * 查询未开始团购列表
	 * </pre>
	 *
	 * @return
	 * @author liangpeichang 2016年7月11日
	 */
	public List<GroupBuy> findNoStartGroupBuyInfo(Map<String,Object> map);
	
	
	public GroupBuy findByGroupId(String id);
}