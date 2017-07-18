package com.miyzh.wechatshop.user.service;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;

import com.miyzh.wechatshop.user.bean.CheckUserResult;
import com.miyzh.wechatshop.user.bean.UserBean;

public interface IUserService {

	/**
	 * 获取积分
	 * 
	 * @param userName
	 * @param clinicCode
	 * @param userId
	 * @return
	 */
	public Integer getCredit(String userName, String clinicCode, String userId)
			throws ClientProtocolException, IOException;

	/**
	 * <pre>
	 * the findByOpenId method for
	 * 通过openid查询注册用户
	 * </pre>
	 *
	 * @param openId
	 *            openid
	 * @return
	 * @author zhangtao 2016年7月9日
	 */
	public UserBean findByOpenId(String openId);

	/**
	 * 通过诊所编码查询用户
	 * 
	 * @param clinicCode
	 *            诊所编码
	 * @return
	 */
	public UserBean findByClinicCode(String clinicCode);

	/**
	 * 通过用户主键查询用户
	 * 
	 * @param userId
	 *            用户主键
	 * @return
	 */
	public UserBean findByUserId(String userId);

	/**
	 * 通过手机号查询用户
	 * 
	 * @param phoneNum
	 *            手机号
	 * @return
	 */
	public UserBean findByPhoneNum(String phoneNum);

	/**
	 * <pre>
	 * the checkUserType method for
	 * 检查用户级别
	 * </pre>
	 *
	 * @param openId
	 * @return
	 * @author zhangtao 2016年7月11日
	 */
	public CheckUserResult checkUserType(String openId);

	/**
	 * 添加用户
	 * 
	 * @param memberBean
	 */
	public void addUser(UserBean userBean) throws Exception;

	/**
	 * 修改用户
	 * 
	 * @param userBean
	 */
	public void updateUser(UserBean userBean) throws Exception;

	/**
	 * 根据手机号查询是否老用户
	 * 
	 * @param phoneNum
	 *            手机号
	 * @return 0:不是老用户，1：老用户
	 */
	public Integer queryCoreUserPhoneNumExits(String phoneNum);

}
