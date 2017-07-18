package com.miyzh.wechatshop.user.service;


import com.miyzh.wechatshop.user.bean.ShopMemberBean;

/**
 * 文件名： IShopMemberService.java<br>
 * 描述: 会员信息<br>
 * 修改人: wumingdi<br>
 * 修改时间： 2016年7月9日 <br>
 */
public interface IShopMemberService {

	/**
	 * 添加会员信息
	 * 
	 * @param shopMemberBean
	 */
	public void addShopMember(ShopMemberBean shopMemberBean) throws Exception;


}
