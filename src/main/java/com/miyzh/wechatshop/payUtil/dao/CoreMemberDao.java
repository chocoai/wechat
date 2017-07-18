package com.miyzh.wechatshop.payUtil.dao;

import com.miyzh.framework.base.dao.IBaseDao;
import com.miyzh.wechatshop.payUtil.bean.CoreMember;
public interface CoreMemberDao extends IBaseDao<CoreMember> {

	/****
	 * 用户信息查询
	 * @param map
	 * @return
	 */
	public CoreMember findByMemberId(String memberId);
}
