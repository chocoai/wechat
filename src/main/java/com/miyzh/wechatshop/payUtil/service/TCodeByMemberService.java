package com.miyzh.wechatshop.payUtil.service;

import java.util.List;

import com.miyzh.wechatshop.payUtil.bean.TCodeByMember;

public interface TCodeByMemberService {
    public List<TCodeByMember>  findByMemberId(Long memberId);
    public void saveTCode(String openId,String groupId,String orderId);
    public TCodeByMember findByMemberIdGroupId(Long memberId,Long groupId);
    public TCodeByMember findById(Long id);
}
