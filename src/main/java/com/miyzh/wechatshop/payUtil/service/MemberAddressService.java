package com.miyzh.wechatshop.payUtil.service;

import java.util.List;

import com.miyzh.wechatshop.payUtil.bean.ShopMemberAddress;

public interface MemberAddressService {

	public void create(ShopMemberAddress shopMemberAddress);

	public void delete(String id);

	public void update(ShopMemberAddress shopMemberAddress);

	public List<ShopMemberAddress> findByMemberId(Long memberId,String isdefind,String addressId);

	public List<ShopMemberAddress> getAll();
	
	public List<ShopMemberAddress> findById(String addressId);
	
	public void updateByIsDefault(String memberId);
}
