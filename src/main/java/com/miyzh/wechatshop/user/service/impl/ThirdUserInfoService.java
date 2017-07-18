package com.miyzh.wechatshop.user.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.miyzh.framework.util.CacheUtil;
import com.miyzh.wechatshop.user.bean.ThirdUserInfoBean;
import com.miyzh.wechatshop.user.dao.IThirdUserInfoDao;
import com.miyzh.wechatshop.user.service.IThirdUserInfoService;

@Service("thirdUserInfoService")
public class ThirdUserInfoService implements IThirdUserInfoService {

	@Autowired
	private IThirdUserInfoDao thirdUserInfoDao;

	@Override
	public ThirdUserInfoBean findByOpenId(String openId, String phoneNum) {

		StringBuffer keyBuffer = CacheUtil.getCachekeyMethodPrx(
				CacheUtil.cacheKey.UserKey.KEY_THIRD_USER_INFO, openId, Thread
						.currentThread().getStackTrace()[1].getMethodName(),
				new Object[] { phoneNum });

		Object object = CacheUtil.getObjectValue(keyBuffer.toString());

		if (object == null) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("openId", openId);
			ThirdUserInfoBean thirdUserInfoBean = thirdUserInfoDao
					.findByMap(map);
			CacheUtil.put(keyBuffer.toString(), thirdUserInfoBean);
			return thirdUserInfoBean;
		}
		return (ThirdUserInfoBean) object;
	}

	@Override
	public void addThirdUserInfo(ThirdUserInfoBean thirdUserInfoBean)
			throws Exception {
		thirdUserInfoDao.create(thirdUserInfoBean);

	}

	@Override
	public void updateThirdUserInfo(ThirdUserInfoBean thirdUserInfoBean)
			throws Exception {

		thirdUserInfoDao.update(thirdUserInfoBean);
		CacheUtil
				.removeGroupByKey(CacheUtil.cacheKey.UserKey.KEY_THIRD_USER_INFO
						+ "_" + thirdUserInfoBean.getOpenId());
	}

	@Override
	public ThirdUserInfoBean findByOpenIdKey(String openId, String usercenterKey) {
		StringBuffer keyBuffer = CacheUtil.getCachekeyMethodPrx(
				CacheUtil.cacheKey.UserKey.KEY_THIRD_USER_INFO, openId, Thread
						.currentThread().getStackTrace()[1].getMethodName(),
				new Object[] {});

		Object object = CacheUtil.getObjectValue(keyBuffer.toString());

		if (object == null) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("openId", openId);
			map.put("usercenterKey", usercenterKey);
			ThirdUserInfoBean thirdUserInfoBean = thirdUserInfoDao
					.findByOpenIdKey(map);
			CacheUtil.put(keyBuffer.toString(), thirdUserInfoBean);
			return thirdUserInfoBean;
		}
		return (ThirdUserInfoBean) object;
	}

	@Override
	public List<ThirdUserInfoBean> findImperfectInfo() {
		return thirdUserInfoDao.findImperfectInfo();
	}

	@Override
	public ThirdUserInfoBean findByUniodId(String uniodId) {
		StringBuffer keyBuffer = CacheUtil.getCachekeyMethodPrx(
				CacheUtil.cacheKey.UserKey.KEY_THIRD_USER_INFO, uniodId, Thread
						.currentThread().getStackTrace()[1].getMethodName(),
				new Object[] { uniodId });

		Object object = CacheUtil.getObjectValue(keyBuffer.toString());

		if (object == null) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("unionId", uniodId);
			ThirdUserInfoBean thirdUserInfoBean = thirdUserInfoDao
					.findByMap(map);
			CacheUtil.put(keyBuffer.toString(), thirdUserInfoBean);
			return thirdUserInfoBean;
		}
		return (ThirdUserInfoBean) object;
	}

}
