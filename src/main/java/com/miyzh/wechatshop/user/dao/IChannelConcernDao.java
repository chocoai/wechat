package com.miyzh.wechatshop.user.dao;

import java.util.Map;

import com.miyzh.framework.base.dao.IBaseDao;
import com.miyzh.wechatshop.user.bean.ChannelConcernBean;

/**
 * 文件名： IChannelConcernDao.java<br>
 * 描述: 渠道关注<br>
 * 修改人: wumingdi<br>
 * 修改时间： 2016年7月21日 <br>
 */
public interface IChannelConcernDao extends IBaseDao<ChannelConcernBean> {

	/**
	 * 查询渠道关注
	 * @param map keys:open_id
	 * @return
	 */
	public ChannelConcernBean findByMap(Map<String, Object> map);

}
