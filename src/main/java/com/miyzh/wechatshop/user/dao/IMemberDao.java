package com.miyzh.wechatshop.user.dao;

import java.util.Map;

import com.miyzh.framework.base.dao.IBaseDao;
import com.miyzh.wechatshop.user.bean.MemberBean;

/**
 * 文件名： IMemberDao.java<br>
 * 描述: 用户管理会员<br>
 * 修改人: wumingdi<br>
 * 修改时间： 2016年7月9日 <br>
 */
public interface IMemberDao extends IBaseDao<MemberBean> {

	/**
	 * 添加用户管理会员
	 * 
	 * @param memberBean
	 * @return 主键
	 */
	public int addMember(MemberBean memberBean);

	/**
	 * 查询用户管理会员
	 * 
	 * @param map
	 * @return
	 */
	public MemberBean findMemberInfo(Map<String, Object> map);

}
