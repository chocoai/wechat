package com.miyzh.wechatshop.user.dao;

import java.util.Map;

import com.miyzh.framework.base.dao.IBaseDao;
import com.miyzh.wechatshop.user.bean.UserBean;

/**
 * <pre>
 * the IUserDao class for
 * </pre>
 *
 * @author zhangtao 2016年7月9日
 */
public interface IUserDao extends IBaseDao<UserBean> {

	/**
	 * <pre>
	 * the findByOpenId method for
	 * 通过openid查询注册用户
	 * </pre>
	 *
	 * @param map
	 *            key:openId
	 * @return
	 * @author zhangtao 2016年7月9日
	 */
	public UserBean findByMap(Map<String, Object> map);

	/**
	 * 添加用户
	 * 
	 * @param memberBean
	 */
	public void addUser(UserBean userBean);

	/**
	 * 查询用户待处理积分
	 * 
	 * @param map
	 *            key: usercenterId（ not null） 用户中心id<br />
	 *            key: clinicCode（not null） 诊所编码 <br />
	 *            key: useStatus（not null） 处理状态，0：待冲正；1：待消费
	 * @return
	 */
	public Integer queryUserPendingPoint(Map<String, Object> map);

	/**
	 * 根据手机号查询是否老用户
	 * 
	 * @param phoneNum
	 *            手机号
	 * @return 0:不是老用户，1：老用户
	 */
	public Integer queryCoreUserPhoneNumExits(String phoneNum);

}
