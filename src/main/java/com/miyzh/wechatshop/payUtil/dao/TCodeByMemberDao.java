package com.miyzh.wechatshop.payUtil.dao;


import java.util.List;
import java.util.Map;

import com.miyzh.framework.base.dao.IBaseDao;
import com.miyzh.wechatshop.payUtil.bean.TCodeByMember;

public interface TCodeByMemberDao extends IBaseDao<TCodeByMember> {

	public long save(TCodeByMember tcByMember);
	
	public List <TCodeByMember> findByMemberId(Long MemberId);
	
	public TCodeByMember findByMemberIdGroupId(Map<String,Object> map);
	public TCodeByMember findById(Long  id);
}
