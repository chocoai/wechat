package com.miyzh.wechatshop.user.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.miyzh.wechatshop.user.bean.ShopMemberBean;
import com.miyzh.wechatshop.user.dao.IShopMemberDao;
import com.miyzh.wechatshop.user.service.IShopMemberService;

@Service("shopMemberService")
public class ShopMemberService implements IShopMemberService {

	@Autowired
	private IShopMemberDao shopMemberDao;

	@Override
	public void addShopMember(ShopMemberBean shopMemberBean) throws Exception {

		shopMemberDao.create(shopMemberBean);
	}

}
