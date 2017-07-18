package com.miyzh.wechatshop.wechat.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.miyzh.wechatshop.wechat.bean.WctAccessToken;
import com.miyzh.wechatshop.wechat.dao.IWctAccessTokenDao;

/**
 * 文件名:WctAccessTokenImpl.java<br>
 * 版权：Copyright 2014 Digital Bay Technology Group. Co. Ltd. All Rights Reserved. <br>
 * 描述: 微信公众号唯一票据信息<br>
 * 修改人: guchangpeng<br>
 * 修改时间：2014-07-03 17:57:34<br>
 * 修改内容：新增<br>
 */
@Repository("wctAccessTokenDao")
public class WctAccessTokenDao {
	@Autowired
	private IWctAccessTokenDao iWctAccessTokenDao;

	public WctAccessToken findWctAccessTokenById(String id) {
		WctAccessToken WctAccessToken = this.iWctAccessTokenDao.findById(id);
		return WctAccessToken;
	}

	public void addWctAccessToken(WctAccessToken wctAccessToken) {
		this.iWctAccessTokenDao.create(wctAccessToken);
	}

	public void updateWctAccessToken(WctAccessToken wctAccessToken) {
		this.iWctAccessTokenDao.update(wctAccessToken);
	}

	public void deleteWctAccessTokenByArr(String[] ids) {
		this.iWctAccessTokenDao.delete(ids);
	}

	public void deleteWctAccessTokenById(Long id) {
		this.iWctAccessTokenDao.deleteById("" + id);
	}

	/**
	 * 根据appid查询公众号唯一票据
	 * 
	 * @param appid
	 *            appid
	 * @return WctAccessToken 对象
	 */
	public WctAccessToken queryWctAccessTokenByAppid(String appid) {
		return this.iWctAccessTokenDao.queryWctAccessTokenByAppid(appid);
	}
}
