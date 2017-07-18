package com.miyzh.wechatshop.user.dao;

import java.util.List;
import java.util.Map;

import com.miyzh.framework.base.dao.IBaseDao;
import com.miyzh.wechatshop.user.bean.ThirdUserInfoBean;

/**
 * 文件名： IThirdUserInfoDao.java<br>
 * 描述: 第三方账户表<br>
 * 修改人: wumingdi<br>
 * 修改时间： 2016年7月9日 <br>
 */
public interface IThirdUserInfoDao extends IBaseDao<ThirdUserInfoBean> {

	/**
	 * 查询第三方账户
	 * 
	 * @param map
	 *            key：openId 微信ID key：phoneNum 手机号
	 * 
	 * @returnz
	 */
	public ThirdUserInfoBean findByMap(Map<String, Object> map);

	public ThirdUserInfoBean findByOpenIdKey(Map<String, Object> map);

	/**
	 * 查询unionid和headurl为空记录
	 * 
	 * @return
	 */
	public List<ThirdUserInfoBean> findImperfectInfo();

}
