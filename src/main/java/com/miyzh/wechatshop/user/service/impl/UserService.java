package com.miyzh.wechatshop.user.service.impl;

import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.miyzh.framework.util.CacheUtil;
import com.miyzh.framework.util.PropertiesUtil;
import com.miyzh.wechatshop.user.bean.CheckUserResult;
import com.miyzh.wechatshop.user.bean.UserBean;
import com.miyzh.wechatshop.user.dao.IUserDao;
import com.miyzh.wechatshop.user.service.IUserService;
import com.miyzh.wechatshop.usercenter.bean.UserCenterResultBean;
import com.miyzh.wechatshop.usercenter.service.UserCenterService;

@Service
public class UserService implements IUserService {

	@Autowired
	public IUserDao dao;

	/** 用户中心 **/
	@Autowired
	private UserCenterService userCenterService;

	// 查询积分接口地址
	private final String GET_INTEGRAL_INTERFACE_URL = PropertiesUtil
			.getPropertyValue("get_integral_interface_url");

	@Override
	public UserBean findByOpenId(String openId) {
		StringBuffer keyBuffer = CacheUtil.getCachekeyMethodPrx(
				CacheUtil.cacheKey.UserKey.KEY_USER_INFO, openId, Thread
						.currentThread().getStackTrace()[1].getMethodName(),
				new Object[] {});

		Object object = CacheUtil.getObjectValue(keyBuffer.toString());
		if (object == null) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("openId", openId);
			UserBean user = dao.findByMap(map);
			CacheUtil.put(keyBuffer.toString(), user);
			return user;
		}
		return (UserBean) object;
	}

	/**
	 * <pre>
	 * the checkUserType method for
	 * 检查用户级别
	 * </pre>
	 *
	 * @param openId
	 * @return
	 * @author zhangtao 2016年7月11日
	 */
	@SuppressWarnings("unchecked")
	@Override
	public CheckUserResult checkUserType(String openId) {
		String userType = "0";
		UserBean user = findByOpenId(openId);
		CheckUserResult result = new CheckUserResult();
		if (user != null && StringUtils.isNotEmpty(user.getUserId())) {
			userType = "1";
			// 如果诊所id不为空则获取状态,为空直接为未认证用户
			if (StringUtils.isNotEmpty(user.getClinicId())) {
				// 用户中心id
				String userCenterId = user.getUserId();

				UserCenterResultBean userCenterResultBean = userCenterService
						.clinicAuthResult(userCenterId, user.getClinicId());

				Map<String, Object> map = ((Map<String, Object>) userCenterResultBean
						.getResultObject());

				// 0（noCertificate）：注册用户；1（certificated）：认证用户；2（certificateWaiting）：待审核；3（certificateFailed）：审核不通过；

				String status = map.get("auditStatus") + "";

				if ("1".equals(status)) {
					userType = "2";
				} else {
					result.setStatus(status);
					if ("3".equals(status)) {
						// 如果审核失败则要获取失败原因
						result.setCertificateOpinion((String) map
								.get("certificateOpinion"));
					}
				}
			}
		}

		result.setUserType(userType);
		result.setUserBean(user);
		return result;
	}

	@Override
	public void addUser(UserBean userBean) throws Exception {

		dao.addUser(userBean);
	}

	@Override
	public UserBean findByClinicCode(String clinicCode) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("clinicCode", clinicCode);
		UserBean user = dao.findByMap(map);
		return user;
	}

	@Override
	public void updateUser(UserBean userBean) throws Exception {

		dao.update(userBean);
		CacheUtil.removeGroupByKey(CacheUtil.cacheKey.UserKey.KEY_USER_INFO
				+ "_" + userBean.getOpenId());
	}

	@Override
	public UserBean findByUserId(String userId) {
		StringBuffer keyBuffer = CacheUtil.getCachekeyMethodPrx(
				CacheUtil.cacheKey.UserKey.KEY_USER_INFO, userId, Thread
						.currentThread().getStackTrace()[1].getMethodName(),
				new Object[] {});

		Object object = CacheUtil.getObjectValue(keyBuffer.toString());
		if (object == null) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("userId", userId);
			UserBean user = dao.findByMap(map);
			CacheUtil.put(keyBuffer.toString(), user);
			return user;
		}
		return (UserBean) object;
	}

	@Override
	public UserBean findByPhoneNum(String phoneNum) {
		StringBuffer keyBuffer = CacheUtil.getCachekeyMethodPrx(
				CacheUtil.cacheKey.UserKey.KEY_USER_INFO, phoneNum, Thread
						.currentThread().getStackTrace()[1].getMethodName(),
				new Object[] {});

		Object object = CacheUtil.getObjectValue(keyBuffer.toString());
		if (object == null) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("phoneNum", phoneNum);
			UserBean user = dao.findByMap(map);
			CacheUtil.put(keyBuffer.toString(), user);
			return user;
		}
		return (UserBean) object;
	}

	public Integer getCredit(String userName, String clinicCode, String userId)
			throws ClientProtocolException, IOException {

		// 用户积分
		Integer integral = 0;
		try {
			StringBuffer url = new StringBuffer(GET_INTEGRAL_INTERFACE_URL);
			if (StringUtils.isNotBlank(userId)) {
				url.append("&").append("userId=" + userId);

			}
			if (StringUtils.isNotBlank(clinicCode)) {
				url.append("&").append("clinicCode=" + clinicCode);
			}

			URL convertUrl = new URL(url.toString());
			URI uri = new URI(convertUrl.getProtocol(), convertUrl.getHost(),
					convertUrl.getPath(), convertUrl.getQuery(), null);
			HttpClient client = HttpClients.createDefault();
			HttpGet get = new HttpGet(uri);
			get.addHeader("Content-Type", "application/json");
			HttpResponse response = client.execute(get);
			HttpEntity entity = response.getEntity();
			String resBody = IOUtils.toString(entity.getContent(), "UTF-8");
			JSONObject resBodyJSONObject = JSON.parseObject(resBody);
			String code = resBodyJSONObject.getString("rtnCode");
			if (StringUtils.isNotBlank(code) && code.equals("0")) {

				JSONObject data = (JSONObject) resBodyJSONObject.get("data");
				integral = Integer.parseInt(data.getString("allPoint"));

				if (null != integral && integral != 0) {
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("usercenterId", userId);
					map.put("clinicCode", clinicCode);
					map.put("useStatus", "0");

					// 待处理冲正积分
					Integer pendingReversePoint = dao
							.queryUserPendingPoint(map);
					integral = integral + pendingReversePoint;

					map.put("useStatus", "1");
					// 待处理消费积分
					Integer pendingCousumePoint = dao
							.queryUserPendingPoint(map);
					if (integral - pendingCousumePoint > 0) {
						integral = integral - pendingCousumePoint;
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return integral;

	}

	@Override
	public Integer queryCoreUserPhoneNumExits(String phoneNum) {
		return dao.queryCoreUserPhoneNumExits(phoneNum);
	}
}
