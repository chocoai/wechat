package com.miyzh.wechatshop.user.service;

import java.util.List;

import com.miyzh.wechatshop.user.bean.ThirdUserInfoBean;

/**
 * 文件名： IThirdUserInfoService.java<br>
 * 描述: 第三方账户表<br>
 * 修改人: wumingdi<br>
 * 修改时间： 2016年7月9日 <br>
 */
public interface IThirdUserInfoService {

	/**
	 * 查询第三方账户
	 * 
	 * @param openId
	 *            微信ID
	 * @param phoneNum
	 *            手机号
	 * @return
	 */
	public ThirdUserInfoBean findByOpenId(String openId, String phoneNum);

	/**
	 * 查询第三方账户
	 * 
	 * @param uniodId
	 *            微信ID
	 * @return
	 */
	public ThirdUserInfoBean findByUniodId(String uniodId);

	/**
	 * 添加第三方账户
	 * 
	 * @param thirdUserInfoBean
	 */
	public void addThirdUserInfo(ThirdUserInfoBean thirdUserInfoBean)
			throws Exception;

	/**
	 * 修改第三方账户
	 * 
	 * @param thirdUserInfoBean
	 */
	public void updateThirdUserInfo(ThirdUserInfoBean thirdUserInfoBean)
			throws Exception;

	public ThirdUserInfoBean findByOpenIdKey(String openId, String usercenterKey);

	/**
	 * 查询unionid和headurl为空记录
	 * 
	 * @return
	 */
	public List<ThirdUserInfoBean> findImperfectInfo();

}
