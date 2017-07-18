package com.miyzh.wechatshop.register.action;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.miyzh.cp.sms.SmsTemplateType;
import com.miyzh.framework.base.Constant;
import com.miyzh.framework.base.action.BaseAction;
import com.miyzh.framework.base.action.reply.BaseReplyResult;
import com.miyzh.wechatshop.register.report.RegisterReplyReport;
import com.miyzh.wechatshop.register.report.RegisterReport;
import com.miyzh.wechatshop.register.report.RegisterRequestReport;
import com.miyzh.wechatshop.register.service.IRegisterService;
import com.miyzh.wechatshop.user.bean.ThirdUserInfoBean;
import com.miyzh.wechatshop.user.bean.UserBean;
import com.miyzh.wechatshop.user.service.IThirdUserInfoService;
import com.miyzh.wechatshop.user.service.IUserService;
import com.miyzh.wechatshop.usercenter.bean.UserCenterResultBean;
import com.miyzh.wechatshop.usercenter.service.UserCenterService;

/**
 * 文件名： RegisterAction.java<br>
 * 描述: 注册<br>
 * 修改人: wumingdi<br>
 * 修改时间： 2016年7月13日 <br>
 */
@Controller
@RequestMapping("/register")
public class RegisterAction extends BaseAction {

	/** 用户中心接口 **/
	@Autowired
	private UserCenterService userCenterService;

	/** 注册 **/
	@Autowired
	private IRegisterService registerService;

	/** 用户信息 **/
	@Autowired
	private IUserService userService;

	@Autowired
	private IThirdUserInfoService thirdUserInfoService;

	/**
	 * 医德帮用户注册
	 * 
	 * @param uniodId
	 * @param userCenterKey
	 *            用户主键
	 * @param phoneNum
	 *            手机号
	 * @param password
	 *            密码
	 * @param clinicId
	 *            诊所id
	 * @param clinicCode
	 *            诊所编码
	 * @return
	 */
	@RequestMapping("/ydbRegister")
	public ModelAndView ydbRegister(String unionId, String userCenterKey,
			String phoneNum, String password, String clinicId, String clinicCode) {
		Map<String, Object> param = new HashMap<String, Object>();
		try {
			ThirdUserInfoBean thirdUserInfoBean = thirdUserInfoService
					.findByUniodId(unionId);
			param.put("openid", thirdUserInfoBean.getOpenId());
			// if (null != unionId && StringUtils.isNotBlank(unionId)) {
			// ThirdUserInfoBean thirdUserInfoBean = thirdUserInfoService
			// .findByUniodId(unionId);
			// String openId = thirdUserInfoBean.getOpenId();
			// registerService.executeYdbUserRegister(userCenterKey, phoneNum,
			// password, openId, clinicId, clinicCode, unionId);
			// param.put("openId", openId);
			// }
		} catch (Exception e) {
			log.error(
					"In the method RegisterAction.ydbRegister(String uniodId, String userCenterKey,String phoneNum, String password, String clinicId, String clinicCode) exists error!",
					e);
		}

		return new ModelAndView(new RedirectView("../html/login/login.html"), param);

	}

	/**
	 * 重置密码
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("/resetPassword")
	@ResponseBody
	public String resetPassword(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		RegisterReplyReport registerReplyReport = getRegisterReplyReport();
		try {
			String jsonString = parseRequestReportToString(request, response);
			RegisterRequestReport registerRequestReport = getRequestReport(jsonString);
			RegisterReport resiterRequestParam = registerRequestReport
					.getCommandinfo();
			// 验证请求参数
			if (null == resiterRequestParam
					|| StringUtils.isBlank(resiterRequestParam.getPhonenum())
					|| StringUtils.isBlank(resiterRequestParam.getOpenid())
					|| StringUtils.isBlank(resiterRequestParam.getVericode())
					|| StringUtils.isBlank(resiterRequestParam.getPassword())) {
				registerReplyReport.getResult().setBusinessCode(
						Constant.ResultCode.RESULT_TWO);
				registerReplyReport.getResult().setMsg("请求参数不完整");
				resReply(response, registerReplyReport);
				return null;
			}

			// 手机号
			String phoneNum = resiterRequestParam.getPhonenum();

			// 验证码
			String vericode = resiterRequestParam.getVericode();
			// 校验验证码
			UserCenterResultBean userCenterResultBean = userCenterService
					.validateVeriCode(phoneNum, vericode);
			registerReplyReport.getResult().setBusinessCode(
					userCenterResultBean.getResultCode());
			registerReplyReport.getResult().setMsg(
					userCenterResultBean.getResultMsg());
			// 验证码错误
			if (!Constant.ResultCode.SUCCESS.equals(userCenterResultBean
					.getResultCode())) {
				registerReplyReport.getResult().setBusinessCode(
						Constant.ResultCode.RESULT_THREE);
				registerReplyReport.getResult().setMsg("验证码错误");
				resReply(response, registerReplyReport);
				return null;
			}

			userCenterResultBean = userCenterService.modifyPasswordByMobile(
					phoneNum, resiterRequestParam.getPassword());
			// 修改失败
			if (!Constant.ResultCode.SUCCESS.equals(userCenterResultBean
					.getResultCode())
					|| !(boolean) userCenterResultBean.getResultObject()) {
				registerReplyReport.getResult().setBusinessCode(
						Constant.ResultCode.RESULT_FOURE);
				registerReplyReport.getResult().setMsg("修改失败");
				resReply(response, registerReplyReport);
				return null;
			}

		} catch (Exception e) {
			replyError(response);
			log.error(
					"In the method RegisterAction.resetPassword(HttpServletRequest request,HttpServletResponse response) exists error!",
					e);
		}
		resReply(response, registerReplyReport);

		return null;

	}

	/**
	 * 普通注册
	 * 
	 * @param resiterRequestParam
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/basicRegister")
	@ResponseBody
	public String basicRegister(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		RegisterReplyReport registerReplyReport = getRegisterReplyReport();
		RegisterReport registerReport = new RegisterReport();
		try {
			String jsonString = parseRequestReportToString(request, response);
			RegisterRequestReport registerRequestReport = getRequestReport(jsonString);
			RegisterReport resiterRequestParam = registerRequestReport
					.getCommandinfo();
			// 验证请求参数
			if (null == resiterRequestParam
					|| StringUtils.isBlank(resiterRequestParam.getPhonenum())
					|| StringUtils.isBlank(resiterRequestParam.getOpenid())
					|| StringUtils.isBlank(resiterRequestParam.getVericode())
					|| StringUtils.isBlank(resiterRequestParam.getPassword())) {
				registerReplyReport.getResult().setBusinessCode(
						Constant.ResultCode.RESULT_TWO);
				registerReplyReport.getResult().setMsg("请求参数不完整");
				resReply(response, registerReplyReport);
				return null;
			}

			// 手机号
			String phoneNum = resiterRequestParam.getPhonenum();

			// 验证码
			String vericode = resiterRequestParam.getVericode();
			// 校验验证码
			UserCenterResultBean userCenterResultBean = userCenterService
					.validateVeriCode(phoneNum, vericode);
			registerReplyReport.getResult().setBusinessCode(
					userCenterResultBean.getResultCode());
			registerReplyReport.getResult().setMsg(
					userCenterResultBean.getResultMsg());
			// 验证码错误
			if (!Constant.ResultCode.SUCCESS.equals(userCenterResultBean
					.getResultCode())) {
				resReply(response, registerReplyReport);
				return null;
			}

			// 根据手机号查询用户信息
			UserBean userBean = userService.findByPhoneNum(phoneNum);
			if (null != userBean) {
				// 已经注册
				registerReplyReport.getResult().setBusinessCode(
						Constant.ResultCode.RESULT_FOURE);
				registerReplyReport.getResult().setMsg("该手机号已经注册");
				resReply(response, registerReplyReport);
				return null;
			}

			// 验证是否老用户
			if (userService.queryCoreUserPhoneNumExits(phoneNum) > 0) {
				// 老用户
				registerReplyReport.getResult().setBusinessCode(
						Constant.ResultCode.RESUlT_FIVE);
				registerReplyReport.getResult().setMsg("您是老用户，请用医德帮用户入口进行绑定！");
				resReply(response, registerReplyReport);
				return null;
			}

			// 验证是否医德帮老用户
			UserCenterResultBean ydbExistResultBean = userCenterService
					.ydbExistByMobile(phoneNum);
			// 医德帮老用户
			if (!Constant.ResultCode.SUCCESS.equals(ydbExistResultBean
					.getResultCode())
					|| (Boolean) ydbExistResultBean.getResultObject()) {
				// 老用户
				registerReplyReport.getResult().setBusinessCode(
						Constant.ResultCode.RESUlT_FIVE);
				registerReplyReport.getResult().setMsg("您是老用户，请用医德帮用户入口进行绑定！");
				resReply(response, registerReplyReport);
				return null;
			}

			// 注册
			String userKey = registerService
					.executeBasicRegister(resiterRequestParam);

			if (StringUtils.isBlank(userKey)) {
				// 失败
				registerReplyReport.getResult().setBusinessCode(
						Constant.ResultCode.FILURE);
				registerReplyReport.getResult().setMsg("注册失败，请稍后再试");
				resReply(response, registerReplyReport);
				return null;

			}
			registerReport.setUserId(userKey);
		} catch (Exception e) {
			replyError(response);
			log.error(
					"In the method RegisterAction.basicRegister(HttpServletRequest request,HttpServletResponse response) exists error!",
					e);
			return null;
		}
		registerReplyReport.setReply(registerReport);
		resReply(response, registerReplyReport);
		return null;
	}

	/**
	 * 校验手机验证码
	 * 
	 * @param resiterRequestParam
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/validateVeriCode")
	@ResponseBody
	public String validateVeriCode(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		RegisterReplyReport registerReplyReport = new RegisterReplyReport();
		BaseReplyResult result = new BaseReplyResult();
		result.setCode(Constant.ResultCode.SUCCESS);
		result.setBusinessCode(Constant.ResultCode.SUCCESS);

		try {
			String jsonString = parseRequestReportToString(request, response);
			JSONObject requestJsonObject = getJsonObject(jsonString);
			RegisterRequestReport registerRequestReport = JSON.toJavaObject(
					requestJsonObject, RegisterRequestReport.class);
			RegisterReport resiterRequestParam = registerRequestReport
					.getCommandinfo();

			if (null == resiterRequestParam
					|| StringUtils
							.isNotBlank(resiterRequestParam.getPhonenum())
					|| StringUtils
							.isNotBlank(resiterRequestParam.getVericode())) {

				// 手机号
				String phoneNum = resiterRequestParam.getPhonenum();
				// 验证码
				String vericode = resiterRequestParam.getVericode();
				UserCenterResultBean userCenterResultBean = userCenterService
						.validateVeriCode(phoneNum, vericode);

				if (null == userCenterResultBean
						|| StringUtils.isBlank(userCenterResultBean
								.getResultCode())
						|| !Constant.ResultCode.SUCCESS
								.equals(userCenterResultBean.getResultCode())) {

					result.setBusinessCode(userCenterResultBean.getResultCode());
					result.setMsg(userCenterResultBean.getResultMsg());

				}

			} else {
				result.setBusinessCode(Constant.ResultCode.RESULT_TWO);
				result.setMsg("请求参数不完整");
			}
		} catch (Exception e) {
			replyError(response);
			log.error(
					"In the method RegisterAction.validateVeriCode(HttpServletRequest request,HttpServletResponse response) exists error!",
					e);
			return null;
		}

		registerReplyReport.setResult(result);
		resReply(response, registerReplyReport);

		return null;

	}

	/**
	 * 手机号发送验证码
	 * 
	 * @param resiterRequestParam
	 * @return
	 */
	@RequestMapping("/phoneSendVeriCode")
	@ResponseBody
	public String phoneSendVeriCode(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		RegisterReplyReport registerReplyReport = getRegisterReplyReport();
		try {
			// 请求参数
			String jsonString = parseRequestReportToString(request, response);
			JSONObject requestJsonObject = getJsonObject(jsonString);
			// 手机号
			String phoneNum = requestJsonObject.getJSONObject("commandinfo")
					.getString("phonenum");
			// 发送类型 0:注册；1：密码找回；
			String sendType = requestJsonObject.getJSONObject("commandinfo")
					.getString("sendtype");

			if (null == phoneNum || StringUtils.isBlank(phoneNum)) {
				registerReplyReport.getResult().setBusinessCode(
						Constant.ResultCode.FILURE);
				registerReplyReport.getResult().setMsg("请求参数不完整");
				resReply(response, registerReplyReport);
				return null;
			}

			if (sendType.equals("0")) {
				// 根据手机号查询用户信息
				UserBean userBean = userService.findByPhoneNum(phoneNum);
				if (null == userBean) {
					// 发送验证码
					UserCenterResultBean userCenterResultBean = userCenterService
							.phoneSendVeriCode(phoneNum,
									SmsTemplateType.MiyShopRegisterApply);

					registerReplyReport.getResult().setBusinessCode(
							userCenterResultBean.getResultCode());
					registerReplyReport.getResult().setMsg(
							userCenterResultBean.getResultMsg());

				} else {
					// 该手机号已经注册
					registerReplyReport.getResult().setBusinessCode(
							Constant.ResultCode.RESULT_FOURE);
					registerReplyReport.getResult().setMsg("该手机号已经注册");
				}
			} else if (sendType.equals("1")) {
				// 判断手机号是否注册
				UserCenterResultBean existByMobileFlag = userCenterService
						.existByMobile(phoneNum);
				if (null != existByMobileFlag
						&& (boolean) existByMobileFlag.getResultObject()) {
					// 发送验证码
					UserCenterResultBean userCenterResultBean = userCenterService
							.phoneSendVeriCode(phoneNum,
									SmsTemplateType.SimpleVerification);
					registerReplyReport.getResult().setBusinessCode(
							userCenterResultBean.getResultCode());
					registerReplyReport.getResult().setMsg(
							userCenterResultBean.getResultMsg());
				} else {
					// 该手机号已经注册
					registerReplyReport.getResult().setBusinessCode(
							Constant.ResultCode.RESULT_FOURE);
					registerReplyReport.getResult().setMsg("该手机号未注册");
				}
			}
		} catch (Exception e) {
			replyError(response);
			log.error(
					"In the method RegisterAction.phoneSendVeriCode(HttpServletRequest request,HttpServletResponse response) exists error!",
					e);
			return null;
		}
		resReply(response, registerReplyReport);
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
			RegisterReplyReport registerReplyReport) throws IOException {
		if (registerReplyReport == null) {
			replyIdentifyError(response);
			return;
		}
		registerReplyReport.setReplytime(String.valueOf(System
				.currentTimeMillis()));// replyTime
		String replyReport = JSON.toJSONString(registerReplyReport);
		responseClient(response, replyReport);
	}

	/**
	 * 获取返回对象
	 * 
	 * @return
	 */
	private RegisterReplyReport getRegisterReplyReport() {
		RegisterReplyReport registerReplyReport = new RegisterReplyReport();
		BaseReplyResult result = new BaseReplyResult();
		result.setCode(Constant.ResultCode.SUCCESS);
		result.setBusinessCode(Constant.ResultCode.SUCCESS);
		registerReplyReport.setResult(result);
		return registerReplyReport;
	}

	/**
	 * 把字符串解析成java对象
	 * 
	 * @param jsonString
	 *            需要解析的json字符串
	 * @return String 报文中commandType值
	 */
	private RegisterRequestReport getRequestReport(String jsonString) {
		JSONObject jsonObject = getJsonObject(jsonString);
		return (RegisterRequestReport) JSON.toJavaObject(jsonObject,
				RegisterRequestReport.class);
	}
}
