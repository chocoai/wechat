package com.miyzh.wechatshop.user.action;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.miyzh.framework.base.Constant;
import com.miyzh.framework.base.action.BaseAction;
import com.miyzh.framework.base.action.reply.BaseReplyResult;
import com.miyzh.wechatshop.payUtil.service.MemberAddressService;
import com.miyzh.wechatshop.payUtil.service.TCodeByMemberService;
import com.miyzh.wechatshop.register.report.RegisterReport;
import com.miyzh.wechatshop.register.service.IRegisterService;
import com.miyzh.wechatshop.user.bean.ThirdUserInfoBean;
import com.miyzh.wechatshop.user.bean.UserBean;
import com.miyzh.wechatshop.user.report.UserReplyReport;
import com.miyzh.wechatshop.user.report.UserReport;
import com.miyzh.wechatshop.user.service.IThirdUserInfoService;
import com.miyzh.wechatshop.user.service.IUserService;
import com.miyzh.wechatshop.user.tasktriggers.ThirdUserInfoTaskTriggers;
import com.miyzh.wechatshop.usercenter.bean.UserCenterResultBean;
import com.miyzh.wechatshop.usercenter.service.UserCenterService;

/**
 * 文件名： UserAction.java<br>
 * 描述: 用户<br>
 * 修改人: wumingdi<br>
 * 修改时间： 2016年7月13日 <br>
 */
@Controller
@RequestMapping("/userAction")
public class UserAction extends BaseAction {

	/** 用户 **/
	@Autowired
	private IUserService userService;

	/** 用户中心 **/
	@Autowired
	private UserCenterService userCenterService;

	/** 会员地址 **/
	@Autowired
	private MemberAddressService memberAddressService;

	/** 推广二维码 **/
	@Autowired
	private TCodeByMemberService tCodeByMemberService;

	/** 三方账户 **/
	@Autowired
	private IThirdUserInfoService thirdUserInfoService;

	@Autowired
	private ThirdUserInfoTaskTriggers thirdUserInfoTaskTriggers;

	/** 注册 **/
	@Autowired
	private IRegisterService registerService;

	/**
	 * 定时任务：更新微信用户信息
	 * 
	 * @throws Exception
	 */
	@RequestMapping("/updateWechatUserInfo")
	public void updateWechatUserInfo() throws Exception {
		thirdUserInfoTaskTriggers.updateWechatUserInfo();

	}

	/**
	 * 判断用户是否绑定
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/ifBingDing")
	@ResponseBody
	public String ifUserBingDing(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		UserReplyReport userReplyReport = getUserReplyReport();
		try {
			// 请求参数
			String jsonString = parseRequestReportToString(request, response);
			JSONObject requestJsonObject = getJsonObject(jsonString);
			// 微信ID
			String unionid = requestJsonObject.getJSONObject("commandinfo")
					.getString("unionid");
			if (null == unionid || StringUtils.isBlank(unionid)) {
				userReplyReport.getResult().setBusinessCode(
						Constant.ResultCode.RESULT_TWO);
				userReplyReport.getResult().setMsg("请求参数不完整");
				resReply(response, userReplyReport);
				return null;
			}

			UserCenterResultBean userCenterResultBean = userCenterService
					.getUserByUnionId(unionid);
			if (null != userCenterResultBean
					&& !userCenterResultBean.getResultCode().equals(
							Constant.ResultCode.SUCCESS)) {
				userReplyReport.getResult().setBusinessCode(
						userCenterResultBean.getResultCode());
				userReplyReport.getResult().setMsg(
						userCenterResultBean.getResultMsg());
				resReply(response, userReplyReport);
				return null;
			}

			Map<String, Object> map = (Map<String, Object>) userCenterResultBean
					.getResultObject();
			if (null != map) {
				userReplyReport.getResult().setBusinessCode(
						Constant.ResultCode.RESULT_THREE);
				userReplyReport.getResult().setMsg("该用户已经注册");
				resReply(response, userReplyReport);
				return null;
			}
		} catch (Exception e) {
			replyError(response);
			log.error(
					"In the method UserAction.loginBingDing(HttpServletRequest request,HttpServletResponse response) exists error!",
					e);
		}
		resReply(response, userReplyReport);
		return null;

	}

	/**
	 * 登录绑定
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/loginBingDing")
	@ResponseBody
	public String loginBingDing(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		UserReplyReport userReplyReport = getUserReplyReport();
		try {
			// 请求参数
			String jsonString = parseRequestReportToString(request, response);
			JSONObject requestJsonObject = getJsonObject(jsonString);
			// 微信ID
			String openId = requestJsonObject.getJSONObject("commandinfo")
					.getString("openid");
			// 手机号
			String phoneNum = requestJsonObject.getJSONObject("commandinfo")
					.getString("phonenum");
			// 密码
			String password = requestJsonObject.getJSONObject("commandinfo")
					.getString("password");

			if (null == openId || StringUtils.isBlank(openId)) {
				userReplyReport.getResult().setBusinessCode(
						Constant.ResultCode.RESULT_TWO);
				userReplyReport.getResult().setMsg("请求参数不完整");
				resReply(response, userReplyReport);
				return null;
			}
			if (null == phoneNum || StringUtils.isBlank(phoneNum)) {
				userReplyReport.getResult().setBusinessCode(
						Constant.ResultCode.RESULT_TWO);
				userReplyReport.getResult().setMsg("请求参数不完整");
				resReply(response, userReplyReport);
				return null;
			}
			if (null == password || StringUtils.isBlank(password)) {
				userReplyReport.getResult().setBusinessCode(
						Constant.ResultCode.RESULT_TWO);
				userReplyReport.getResult().setMsg("请求参数不完整");
				resReply(response, userReplyReport);
				return null;
			}
			UserCenterResultBean validateUserInfo = userCenterService
					.validateUserAccountInfo(phoneNum, password);
			if (null == validateUserInfo
					|| !validateUserInfo.getResultCode().equals(
							Constant.ResultCode.SUCCESS)
					|| !(boolean) validateUserInfo.getResultObject()) {
				userReplyReport.getResult().setBusinessCode(
						Constant.ResultCode.RESULT_THREE);
				userReplyReport.getResult().setMsg("手机号密码不符");
				resReply(response, userReplyReport);
				return null;
			}
			// 根据手机号查询用户信息
			UserCenterResultBean userInfo = userCenterService.queryUserByPhone(
					phoneNum, password);
			Map<String, Object> userInfoMap = (Map<String, Object>) userInfo
					.getResultObject();
			// 根据用户ID查询诊所信息
			UserCenterResultBean clinicInfo = userCenterService
					.queryClinicList(userInfoMap.get("uid").toString());
			List<Map<String, Object>> clinicInfoMap = (List<Map<String, Object>>) clinicInfo
					.getResultObject();

			RegisterReport registerReport = new RegisterReport();
			if (null != clinicInfoMap && !clinicInfoMap.isEmpty()) {

				if (clinicInfoMap.size() == 1) {
					// 诊所id
					registerReport.setClinicid(clinicInfoMap.get(0)
							.get("clinicId").toString());

					// 诊所编码
					registerReport.setCliniccode(clinicInfoMap.get(0).get(
							"clinicCode") != null ? clinicInfoMap.get(0)
							.get("clinicCode").toString() : "");
				}

			}

			// 手机号
			registerReport.setPhonenum(phoneNum);
			// 用户中心id
			registerReport.setUserId(userInfoMap.get("uid").toString());
			// 微信id
			registerReport.setOpenid(openId);
			// 医德帮老用户密码
			registerReport.setPassword(password);
			// 绑定并注册
			String userKey = registerService
					.executeBasicRegister(registerReport);
			if (StringUtils.isBlank(userKey)) {
				// 失败
				userReplyReport.getResult().setBusinessCode(
						Constant.ResultCode.FILURE);
				userReplyReport.getResult().setMsg("失败");
			}

		} catch (Exception e) {
			replyError(response);
			log.error(
					"In the method UserAction.loginBingDing(HttpServletRequest request,HttpServletResponse response) exists error!",
					e);
		}
		resReply(response, userReplyReport);

		return null;

	}

	/**
	 * 会员信息
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/getUserType")
	@ResponseBody
	public String getUserType(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		UserReplyReport userReplyReport = getUserReplyReport();
		try {
			// 请求参数
			String jsonString = parseRequestReportToString(request, response);
			JSONObject requestJsonObject = getJsonObject(jsonString);
			// 微信ID
			String openId = requestJsonObject.getJSONObject("commandinfo")
					.getString("openid");

			if (StringUtils.isNotBlank(openId)) {
				UserReport returnUserReport = new UserReport();
				UserBean userBean = userService.findByOpenId(openId);
				if (null != userBean) {
					String userId = userBean.getUserId();
					// 诊所ID
					String clinicId = userBean.getClinicId();
					if (StringUtils.isNotBlank(clinicId)) {

						// 诊所认证结果
						UserCenterResultBean userCenterResultBean = userCenterService
								.clinicAuthResult(userId, clinicId);

						if (null != userCenterResultBean
								&& Constant.ResultCode.SUCCESS
										.equals(userCenterResultBean
												.getResultCode())) {
							Map<String, Object> map = (Map<String, Object>) userCenterResultBean
									.getResultObject();

							if (null != map.get("auditStatus")
									&& StringUtils.isNotBlank(""
											+ map.get("auditStatus"))) {

								int auditStatus = Integer.parseInt(""
										+ map.get("auditStatus"));

								if (auditStatus == 0
										&& StringUtils.isNotBlank(clinicId)) {
									// 注册并且未提交诊所资料
									returnUserReport.setUsertype(5);
								} else if (auditStatus == 0) {
									// 注册用户
									returnUserReport.setUsertype(1);
								} else if (auditStatus == 1) {
									// 认证通过
									returnUserReport.setUsertype(4);
								} else if (auditStatus == 3) {
									// 审核不通过
									returnUserReport.setUsertype(3);
									// 审核意见
									returnUserReport.setCertificateOpinion(""
											+ map.get("certificateOpinion"));
								} else if (auditStatus == 2) {
									// 待审核
									returnUserReport.setUsertype(2);
								}
							}

						} else {
							throw new Exception("调用接口异常");
						}

					} else {
						// 未认证
						returnUserReport.setUsertype(1);
					}
				} else {

					// 未注册
					returnUserReport.setUsertype(0);

				}

				userReplyReport.setReply(returnUserReport);

			} else {
				userReplyReport.getResult().setBusinessCode(
						Constant.ResultCode.RESULT_TWO);
				userReplyReport.getResult().setMsg("请求参数不完整");
			}
		} catch (Exception e) {
			replyError(response);
			log.error(
					"In the method UserAction.getUserType(HttpServletRequest request,HttpServletResponse response) exists error!",
					e);
		}
		resReply(response, userReplyReport);

		return null;

	}

	/**
	 * 会员信息
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/showUserInfo")
	@ResponseBody
	public String showUserInfo(HttpServletRequest request,
			HttpServletResponse response) throws IOException {

		UserReplyReport userReplyReport = getUserReplyReport();

		try {
			// 请求参数
			String jsonString = parseRequestReportToString(request, response);
			JSONObject requestJsonObject = getJsonObject(jsonString);
			// 微信ID
			String openId = requestJsonObject.getJSONObject("commandinfo")
					.getString("openid");
			UserBean userBean = userService.findByOpenId(openId);
			// 用户ID
			String userId = userBean.getUserId();

			if (StringUtils.isNotBlank(openId)
					&& StringUtils.isNotBlank(userId)) {

				UserReport userReport = new UserReport();

				// 三方账户信息
				ThirdUserInfoBean thirdUserInfoBean = thirdUserInfoService
						.findByOpenIdKey(openId, userId);
				if (null != thirdUserInfoBean) {
					// 地址总数
					userReport.setAddresscount(memberAddressService
							.findByMemberId(
									Long.parseLong(thirdUserInfoBean
											.getMemberID()), null, "").size());

					// 我的推广二维码总数
					userReport.setQrcodecount(tCodeByMemberService
							.findByMemberId(
									Long.parseLong(thirdUserInfoBean
											.getMemberID())).size());

				}

				// 用户头像
				userReport.setHeadurl(userBean.getHeadUrl());

				// 用户手机号
				userReport.setPhonenum(userBean.getPhoneNum());

				// 诊所认证状态
				UserCenterResultBean userCenterResultBean = userCenterService
						.clinicAuthResult(userId, userBean.getClinicId());
				if (null != userCenterResultBean
						&& null != userCenterResultBean.getResultObject()) {
					Map<String, Object> map = (Map<String, Object>) userCenterResultBean
							.getResultObject();

					if (StringUtils.isNotBlank(userBean.getClinicId())
							&& Integer.parseInt("" + map.get("auditStatus")) == 0) {
						// 状态
						userReport.setStatus(5);

					} else {
						// 状态
						userReport.setStatus(Integer.parseInt(""
								+ map.get("auditStatus")));
					}

					// 审核意见
					userReport.setCertificateOpinion(""
							+ map.get("certificateOpinion"));

				}

				// 积分
				userReport.setCredit(userService.getCredit("",
						userBean.getClinicCode(), userId));

				userReplyReport.setReply(userReport);

			} else {
				userReplyReport.getResult().setBusinessCode(
						Constant.ResultCode.RESULT_TWO);
				userReplyReport.getResult().setMsg("请求参数不完整");
			}
		} catch (Exception e) {
			replyError(response);
			log.error(
					"In the method UserAction.showUserInfo(HttpServletRequest request,HttpServletResponse response) exists error!",
					e);
		}
		resReply(response, userReplyReport);
		return null;

	}

	/**
	 * 根据openId查询
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("/queryByUserId")
	@ResponseBody
	public String queryByUserId(HttpServletRequest request,
			HttpServletResponse response) throws IOException {

		UserReplyReport userReplyReport = getUserReplyReport();

		try {
			UserReport userReport = new UserReport();
			// 请求参数
			String jsonString = parseRequestReportToString(request, response);
			JSONObject requestJsonObject = getJsonObject(jsonString);
			// 微信ID
			String openId = requestJsonObject.getJSONObject("commandinfo")
					.getString("openid");
			ThirdUserInfoBean thirdUserInfoBean = thirdUserInfoService
					.findByOpenId(openId, "");
			userReport.setUnionid(thirdUserInfoBean.getUnionId());
			userReplyReport.setReply(userReport);
		} catch (Exception e) {
			replyError(response);
			log.error(
					"In the method UserAction.queryByUserId(HttpServletRequest request,HttpServletResponse response) exists error!",
					e);
		}
		resReply(response, userReplyReport);
		return null;

	}

	/**
	 * 回复报文公用方法
	 *
	 * @param response
	 * @param userReplyReport
	 * @throws IOException
	 */
	private void resReply(HttpServletResponse response,
			UserReplyReport userReplyReport) throws IOException {
		if (userReplyReport == null) {
			replyIdentifyError(response);
			return;
		}
		userReplyReport
				.setReplytime(String.valueOf(System.currentTimeMillis()));// replyTime
		String replyReport = JSON.toJSONString(userReplyReport);
		responseClient(response, replyReport);
	}

	/**
	 * 获取返回对象
	 * 
	 * @return
	 */
	private UserReplyReport getUserReplyReport() {
		UserReplyReport userReplyReport = new UserReplyReport();
		BaseReplyResult result = new BaseReplyResult();
		result.setCode(Constant.ResultCode.SUCCESS);
		result.setBusinessCode(Constant.ResultCode.SUCCESS);
		userReplyReport.setResult(result);
		return userReplyReport;
	}

}
