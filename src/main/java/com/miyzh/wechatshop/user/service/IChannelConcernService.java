package com.miyzh.wechatshop.user.service;

import com.miyzh.wechatshop.user.bean.ChannelConcernBean;

/**
 * 文件名： IChannelConcernService.java<br>
 * 描述: 渠道关注<br>
 * 修改人: wumingdi<br>
 * 修改时间： 2016年7月21日 <br>
 */
public interface IChannelConcernService {

	/**
	 * 添加渠道关注
	 * 
	 * @param channelConcernBean
	 */
	public void addChannelConcern(ChannelConcernBean channelConcernBean) throws Exception;

	/**
	 * 查询渠道关注
	 * 
	 * @param openId
	 *            微信主键
	 * @param concernType
	 *            渠道类型
	 * @return
	 */
	public ChannelConcernBean queryChannelConcern(String openId,
			Integer concernType);

}
