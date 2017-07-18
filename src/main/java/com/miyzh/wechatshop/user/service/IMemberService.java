package com.miyzh.wechatshop.user.service;

import com.miyzh.wechatshop.user.bean.MemberBean;

/**
 * 文件名： IMemberService.java<br>
 * 描述: 用户管理会员<br>
 * 修改人: wumingdi<br>
 * 修改时间： 2016年7月9日 <br>
 */
public interface IMemberService {

	/**
	 * 添加用户管理会员
	 * 
	 * @param memberBean
	 * @return 主键
	 */
	public int addMember(MemberBean memberBean) throws Exception;

	/**
	 * 根据userCenterID查询信息
	 * 
	 * @param userCenterId
	 * @return
	 */
	public MemberBean findMemberInfo(String userCenterId);

}
