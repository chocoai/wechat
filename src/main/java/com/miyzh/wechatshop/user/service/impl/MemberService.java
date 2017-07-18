package com.miyzh.wechatshop.user.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.miyzh.wechatshop.user.bean.MemberBean;
import com.miyzh.wechatshop.user.dao.IMemberDao;
import com.miyzh.wechatshop.user.service.IMemberService;

@Service("memberService")
public class MemberService implements IMemberService {

	@Autowired
	private IMemberDao memberDao;

	@Override
	public int addMember(MemberBean memberBean) throws Exception{

		return memberDao.addMember(memberBean);

	}

	@Override
	public MemberBean findMemberInfo(String userCenterId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userCenterId", userCenterId);
		return memberDao.findMemberInfo(map);
	}
}
