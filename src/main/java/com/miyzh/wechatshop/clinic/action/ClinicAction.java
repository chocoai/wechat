package com.miyzh.wechatshop.clinic.action;

import java.io.IOException;
import java.util.ArrayList;
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
import com.miyzh.wechatshop.clinic.report.ClinicReplyReport;
import com.miyzh.wechatshop.clinic.report.ClinicReport;
import com.miyzh.wechatshop.clinic.report.ClinicRequestReport;
import com.miyzh.wechatshop.clinic.service.ClinicService;
import com.miyzh.wechatshop.payUtil.service.AddressService;
import com.miyzh.wechatshop.register.report.RegisterReport;
import com.miyzh.wechatshop.register.service.IRegisterService;
import com.miyzh.wechatshop.user.bean.UserBean;
import com.miyzh.wechatshop.user.service.IUserService;
import com.miyzh.wechatshop.usercenter.bean.UserCenterResultBean;
import com.miyzh.wechatshop.usercenter.service.UserCenterService;

/**
 * 文件名： ClinicAction.java<br>
 * 描述: 诊所<br>
 * 修改人: wumingdi<br>
 * 修改时间： 2016年7月12日 <br>
 */
@Controller
@RequestMapping("/clinic")
public class ClinicAction extends BaseAction {

	/** 用户中心接口 **/
	@Autowired
	private UserCenterService userCenterService;

	/** 用户信息 **/
	@Autowired
	private IUserService userService;

	/** 注册 **/
	@Autowired
	private IRegisterService registerService;

	/** 诊所 **/
	@Autowired
	private ClinicService clinicService;

	/** 地区 **/
	@Autowired
	private AddressService addressService;

	/**
	 * 查询诊所认证状态
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/getClinicType")
	@ResponseBody
	public String getClinicType(HttpServletRequest request,
			HttpServletResponse response) throws IOException {

		ClinicReplyReport clinicReplyReport = getClinicReplyReport();
		try {
			// 请求参数
			String jsonString = parseRequestReportToString(request, response);
			ClinicRequestReport clinicRequestReport = getRequestReport(jsonString);
			ClinicReport clinicRequestParam = clinicRequestReport
					.getCommandinfo();

			// openID
			String openId = clinicRequestParam.getOpenid();

			// 根据openid获取用户信息
			UserBean userBean = userService.findByOpenId(openId);
			if (null == userBean || StringUtils.isBlank(openId)) {
				clinicReplyReport.getResult().setBusinessCode(
						Constant.ResultCode.RESULT_TWO);
				clinicReplyReport.getResult().setMsg("请求参数不完整");
				resReply(response, clinicReplyReport);
				return null;
			}

			// 诊所id
			String clinicId = userBean.getClinicId();
			if (StringUtils.isBlank(clinicId)) {
				clinicReplyReport.getResult().setBusinessCode(
						Constant.ResultCode.RESULT_THREE);
				clinicReplyReport.getResult().setMsg("该用户还未绑定诊所");
				resReply(response, clinicReplyReport);
				return null;
			}

			// 诊所认证结果
			UserCenterResultBean userCenterResultBean = userCenterService
					.clinicAuthResult(userBean.getUserId(), clinicId);

			if (null != userCenterResultBean
					&& Constant.ResultCode.SUCCESS.equals(userCenterResultBean
							.getResultCode())) {
				Map<String, Object> map = (Map<String, Object>) userCenterResultBean
						.getResultObject();
				ClinicReport clinicReport = new ClinicReport();

				if (null != map.get("auditStatus")
						&& StringUtils.isNotBlank("" + map.get("auditStatus"))) {
					// 审核状态
					int auditStatus = Integer.parseInt(""
							+ map.get("auditStatus"));
					clinicReport.setCheckresult(auditStatus);
				}
				if (null != map.get("certificateOpinion")
						&& StringUtils.isNotBlank(""
								+ map.get("certificateOpinion"))) {
					// 失败原因
					String certificateOpinion = ("" + map
							.get("certificateOpinion"));
					clinicReport.setCheckMsg(certificateOpinion);
				}

				List<ClinicReport> reply = new ArrayList<ClinicReport>();
				reply.add(clinicReport);
				clinicReplyReport.setReply(reply);

			} else {
				throw new Exception("调用接口异常");
			}

		} catch (Exception e) {
			replyError(response);
			log.error(
					"In the method ClinicAction.queryClinicCheckResult(HttpServletRequest request,HttpServletResponse response) exists error!",
					e);
			return null;
		}
		resReply(response, clinicReplyReport);
		return null;

	}

	/**
	 * 修改诊所认证
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("/updateClinicQualiRegister")
	@ResponseBody
	public String updateClinicQualiRegister(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		ClinicReplyReport clinicReplyReport = getClinicReplyReport();
		try {
			// 请求参数
			String jsonString = parseRequestReportToString(request, response);
			ClinicRequestReport clinicRequestReport = getRequestReport(jsonString);
			ClinicReport clinicRequestParam = clinicRequestReport
					.getCommandinfo();
			// 根据openid获取用户信息
			UserBean userBean = userService.findByOpenId(clinicRequestParam
					.getOpenid());
			if (null != userBean
					&& StringUtils.isNotBlank(clinicRequestParam.getOpenid())
					&& StringUtils.isNotBlank(clinicRequestParam
							.getClinicname())
					&& StringUtils.isNotBlank(clinicRequestParam.getProvince())
					&& StringUtils.isNotBlank(clinicRequestParam.getCity())
					&& StringUtils.isNotBlank(clinicRequestParam.getCounty())
					&& StringUtils.isNotBlank(clinicRequestParam
							.getClinicaddress())
					&& StringUtils.isNotBlank(clinicRequestParam
							.getCertificatecardurl())
					&& StringUtils.isNotBlank(clinicRequestParam.getPhonenum())
					&& StringUtils.isNotBlank(clinicRequestParam.getClinicid())) {

				clinicRequestParam.setUserkey(userBean.getUserId());
				// 诊所ID
				clinicService.updateClinicQualiRegister(clinicRequestParam);

			} else {
				clinicReplyReport.getResult().setBusinessCode(
						Constant.ResultCode.RESULT_TWO);
				clinicReplyReport.getResult().setMsg("请求参数不完整");
			}

		} catch (Exception e) {
			replyError(response);
			log.error(
					"In the method ClinicAction.updateClinicQualiRegister(HttpServletRequest request,HttpServletResponse response) exists error!",
					e);
			return null;
		}
		resReply(response, clinicReplyReport);

		return null;

	}

	/**
	 * 新用户绑定诊所
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("/newUserBingdingClinic")
	@ResponseBody
	public String newUserBingdingClinic(HttpServletRequest request,
			HttpServletResponse response) throws IOException {

		ClinicReplyReport clinicReplyReport = getClinicReplyReport();
		try {
			// 请求参数
			String jsonString = parseRequestReportToString(request, response);
			ClinicRequestReport clinicRequestReport = getRequestReport(jsonString);
			ClinicReport clinicRequestParam = clinicRequestReport
					.getCommandinfo();
			// openID
			String openId = clinicRequestParam.getOpenid();
			// 诊所id
			String clinicId = clinicRequestParam.getClinicid();
			// 诊所编码
			String clinicCode = clinicRequestParam.getCliniccode();

			// 根据openid获取用户信息
			UserBean userBean = userService.findByOpenId(openId);
			if (null != userBean && null != openId
					&& StringUtils.isNotBlank(openId) && null != clinicId
					&& StringUtils.isNotBlank(clinicId) && null != clinicCode
					&& StringUtils.isNotBlank(clinicCode)) {
				userBean.setClinicId(clinicId);
				userBean.setClinicCode(clinicCode);
				userService.updateUser(userBean);

			} else {
				clinicReplyReport.getResult().setBusinessCode(
						Constant.ResultCode.RESULT_TWO);
				clinicReplyReport.getResult().setMsg("请求参数不完整");
			}

		} catch (Exception e) {
			replyError(response);
			log.error(
					"In the method ClinicAction.newUserBingdingClinic(HttpServletRequest request,HttpServletResponse response) exists error!",
					e);
			return null;
		}
		resReply(response, clinicReplyReport);
		return null;

	}

	/**
	 * 查询诊所信息列表
	 *
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/queryClinicInfoList")
	@ResponseBody
	public String queryClinicInfoList(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		ClinicReplyReport clinicReplyReport = getClinicReplyReport();
		try {
			// 请求参数
			String jsonString = parseRequestReportToString(request, response);
			ClinicRequestReport clinicRequestReport = getRequestReport(jsonString);
			ClinicReport clinicRequestParam = clinicRequestReport
					.getCommandinfo();

			// openID
			String openId = clinicRequestParam.getOpenid();

			// 根据openid获取用户信息
			UserBean userBean = userService.findByOpenId(openId);
			if (null != userBean && StringUtils.isNotBlank(openId)) {

				// 根据诊所id查询诊所信息
				UserCenterResultBean clinicResultBean = userCenterService
						.queryClinicList(userBean.getUserId());

				if (null != clinicResultBean
						&& null != clinicResultBean.getResultObject()) {

					List<Map<String, Object>> listMap = (List<Map<String, Object>>) clinicResultBean
							.getResultObject();
					List<ClinicReport> reply = new ArrayList<ClinicReport>();
					for (Map<String, Object> map : listMap) {
						ClinicReport clinicReport = new ClinicReport();

						clinicReport.setClinicname("" + map.get("clinicName"));
						clinicReport.setClinicaddress(""
								+ map.get("clinicAddress"));
						clinicReport.setClinicid("" + map.get("clinicId"));
						clinicReport.setCliniccode("" + map.get("clinicCode"));
						reply.add(clinicReport);
					}

					clinicReplyReport.setReply(reply);

				}

			} else {
				clinicReplyReport.getResult().setBusinessCode(
						Constant.ResultCode.RESULT_TWO);
				clinicReplyReport.getResult().setMsg("请求参数不完整");
			}

		} catch (Exception e) {
			replyError(response);
			log.error(
					"In the method ClinicAction.queryClinicCheckResult(HttpServletRequest request,HttpServletResponse response) exists error!",
					e);
			return null;
		}
		resReply(response, clinicReplyReport);
		return null;

	}

	/**
	 * 查询诊所信息
	 *
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/queryClinicInfo")
	@ResponseBody
	public String queryClinicInfo(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		ClinicReplyReport clinicReplyReport = getClinicReplyReport();
		try {
			// 请求参数
			String jsonString = parseRequestReportToString(request, response);
			ClinicRequestReport clinicRequestReport = getRequestReport(jsonString);
			ClinicReport clinicRequestParam = clinicRequestReport
					.getCommandinfo();

			// openID
			String openId = clinicRequestParam.getOpenid();

			// 根据openid获取用户信息
			UserBean userBean = userService.findByOpenId(openId);
			if (null != userBean && StringUtils.isNotBlank(openId)) {

				ClinicReport clinicReport = new ClinicReport();

				// 诊所id
				String clinicId = userBean.getClinicId();
				// 根据诊所id查询诊所信息
				UserCenterResultBean clinicResultBean = userCenterService
						.queryClinicbyClinicId(clinicId);

				if (clinicResultBean.getResultCode().equals(
						Constant.ResultCode.SUCCESS)
						&& null != clinicResultBean.getResultObject()) {
					Map<String, Object> clinicMap = (Map<String, Object>) clinicResultBean
							.getResultObject();

					clinicReport.setClinicid(clinicId);
					clinicReport.setPhonenum("" + clinicMap.get("phonenum"));
					clinicReport
							.setClinicname("" + clinicMap.get("clinicname"));

					if (null != clinicMap.get("province")
							&& StringUtils.isNotBlank(""
									+ clinicMap.get("province"))) {
						clinicReport
								.setProvince("" + clinicMap.get("province"));
					}
					if (null != clinicMap.get("city")
							&& StringUtils.isNotBlank(""
									+ clinicMap.get("city"))) {
						clinicReport.setCity("" + clinicMap.get("city"));
					}

					if (null != clinicMap.get("county")
							&& StringUtils.isNotBlank(""
									+ clinicMap.get("county"))) {
						clinicReport.setCounty("" + clinicMap.get("county"));
					}
					clinicReport.setClinicaddress(""
							+ clinicMap.get("clinicaddress"));
					clinicReport.setCertificatecardurl(""
							+ clinicMap.get("certificatecardurl"));
					clinicReport.setDoctorseniorityurl(""
							+ clinicMap.get("doctorseniorityurl"));
					// 经营范围，1：中医；2：西医；3：中西医结合；
					if (null != clinicMap.get("clinicscope")
							&& StringUtils.isNotBlank(""
									+ clinicMap.get("clinicscope"))) {
						clinicReport.setClinicscope(Integer.parseInt(""
								+ clinicMap.get("clinicscope")));
					}
					// 诊所人员规模，1：1~3人；2：4~6人；3：7~9人；4：10人以上；
					if (null != clinicMap.get("clinicscale")
							&& StringUtils.isNotBlank(""
									+ clinicMap.get("clinicscale"))) {
						clinicReport.setClinicscale(Integer.parseInt(""
								+ clinicMap.get("clinicscale")));
					}
					// 诊所经营性质，0：私营；1：公立；2：混营；
					if (null != clinicMap.get("clinicnature")
							&& StringUtils.isNotBlank(""
									+ clinicMap.get("clinicnature"))) {
						clinicReport.setClinicnature(Integer.parseInt(""
								+ clinicMap.get("clinicnature")));
					}
					// 诊所面积，0：100平方米以下；
					// 1：100~200平方米；2：200~400平方米；3：400~600平方米；4：600~800平方米；5：800平方米以上；
					if (null != clinicMap.get("clinicarea")
							&& StringUtils.isNotBlank(""
									+ clinicMap.get("clinicarea"))) {
						clinicReport.setClinicarea(Integer.parseInt(""
								+ clinicMap.get("clinicarea")));
					}
					// 所属科别，0：全科；1：专科；
					if (null != clinicMap.get("clinicdivision")
							&& StringUtils.isNotBlank(""
									+ clinicMap.get("clinicdivision"))) {
						clinicReport.setClinicdivision(Integer.parseInt(""
								+ clinicMap.get("clinicdivision")));
					}
					clinicReport.setDivisiondescription(""
							+ clinicMap.get("divisiondescription"));
				}

				// 用户主键
				String userKey = userBean.getUserId();

				// 根据用户主键查询用户信息
				UserCenterResultBean userResultBean = userCenterService
						.queryUserByUserId(userKey);

				if (clinicResultBean.getResultCode().equals(
						Constant.ResultCode.SUCCESS)
						&& null != userResultBean.getResultObject()) {
					Map<String, Object> userMap = (Map<String, Object>) userResultBean
							.getResultObject();

					clinicReport.getRegister().setUsername(
							"" + userMap.get("username"));
					if (null != userMap.get("sex")
							&& StringUtils.isNotBlank("" + userMap.get("sex"))) {

						clinicReport.getRegister().setSex(
								Integer.parseInt("" + userMap.get("sex")));
					}
				}

				List<ClinicReport> reply = new ArrayList<ClinicReport>();
				reply.add(clinicReport);
				clinicReplyReport.setReply(reply);

			} else {
				clinicReplyReport.getResult().setBusinessCode(
						Constant.ResultCode.RESULT_TWO);
				clinicReplyReport.getResult().setMsg("请求参数不完整");
			}

		} catch (Exception e) {
			replyError(response);
			log.error(
					"In the method ClinicAction.queryClinicCheckResult(HttpServletRequest request,HttpServletResponse response) exists error!",
					e);
			return null;
		}
		resReply(response, clinicReplyReport);
		return null;

	}

	/**
	 * 诊所认证
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("/clinicQualiRegister")
	@ResponseBody
	public String clinicQualiRegister(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		ClinicReplyReport clinicReplyReport = getClinicReplyReport();
		try {
			// 请求参数
			String jsonString = parseRequestReportToString(request, response);
			ClinicRequestReport clinicRequestReport = getRequestReport(jsonString);
			ClinicReport clinicRequestParam = clinicRequestReport
					.getCommandinfo();
			// 根据openid获取用户信息
			UserBean userBean = userService.findByOpenId(clinicRequestParam
					.getOpenid());
			if (null != userBean
					&& StringUtils.isNotBlank(clinicRequestParam.getOpenid())
					&& StringUtils.isNotBlank(clinicRequestParam
							.getClinicname())
					&& StringUtils.isNotBlank(clinicRequestParam.getProvince())
					&& StringUtils.isNotBlank(clinicRequestParam.getCity())
					&& StringUtils.isNotBlank(clinicRequestParam.getCounty())
					&& StringUtils.isNotBlank(clinicRequestParam
							.getClinicaddress())
					&& StringUtils.isNotBlank(clinicRequestParam
							.getCertificatecardurl())
					&& StringUtils.isNotBlank(clinicRequestParam.getPhonenum())) {

				clinicRequestParam.setUserkey(userBean.getUserId());
				if (StringUtils.isBlank(userBean.getClinicId())) {
					// 诊所ID
					String clinicId = clinicService
							.executeClinicQualiRegister(clinicRequestParam);

					if (StringUtils.isNotBlank(clinicId)) {
						userBean.setClinicId(clinicId);
						userBean.setOpenId(clinicRequestParam.getOpenid());
						userService.updateUser(userBean);
					} else {
						clinicReplyReport.getResult().setBusinessCode(
								Constant.ResultCode.FILURE);
						clinicReplyReport.getResult().setMsg("认证失败");
					}
				} else {
					clinicReplyReport.getResult().setBusinessCode(
							Constant.ResultCode.RESULT_THREE);
					clinicReplyReport.getResult().setMsg("该用户已经认证诊所");
				}

			} else {
				clinicReplyReport.getResult().setBusinessCode(
						Constant.ResultCode.RESULT_TWO);
				clinicReplyReport.getResult().setMsg("请求参数不完整");
			}

		} catch (Exception e) {
			replyError(response);
			log.error(
					"In the method ClinicAction.clinicQualiRegister(HttpServletRequest request,HttpServletResponse response) exists error!",
					e);
			return null;
		}
		resReply(response, clinicReplyReport);

		return null;

	}

	/**
	 * 完善信息
	 *
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("/perfectedClinicInfo")
	@ResponseBody
	public String perfectedInfo(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		ClinicReplyReport clinicReplyReport = getClinicReplyReport();
		try {
			// 请求参数
			String jsonString = parseRequestReportToString(request, response);
			ClinicRequestReport clinicRequestReport = getRequestReport(jsonString);
			ClinicReport clinicRequestParam = clinicRequestReport
					.getCommandinfo();
			// 根据openid获取用户信息
			UserBean userBean = userService.findByOpenId(clinicRequestParam
					.getOpenid());

			if (null != userBean
					&& StringUtils.isNotBlank(clinicRequestParam.getOpenid())) {

				clinicRequestParam.setClinicid(userBean.getClinicId());
				clinicRequestParam.setUserkey(userBean.getUserId());
				// 完善诊所信息
				clinicService.executePerfectedClinicInfo(clinicRequestParam);
			} else {
				clinicReplyReport.getResult().setBusinessCode(
						Constant.ResultCode.RESULT_TWO);
				clinicReplyReport.getResult().setMsg("请求参数不完整");
			}

		} catch (Exception e) {
			replyError(response);
			log.error(
					"In the method ClinicAction.perfectedClinicInfo(HttpServletRequest request,HttpServletResponse response) exists error!",
					e);
			return null;
		}
		resReply(response, clinicReplyReport);

		return null;
	}

	/**
	 * 老用户绑定诊所
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/oldUserBingdingClinic")
	@ResponseBody
	public String oldUserBingdingClinic(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		ClinicReplyReport clinicReplyReport = getClinicReplyReport();
		try {
			// 请求参数
			String jsonString = parseRequestReportToString(request, response);
			ClinicRequestReport clinicRequestReport = getRequestReport(jsonString);
			ClinicReport clinicRequestParam = clinicRequestReport
					.getCommandinfo();
			// 诊所编码
			String clinicCode = clinicRequestParam.getCliniccode();
			// 医德帮登录名
			String ydbLoginName = clinicRequestParam.getYdbloginname();
			// 医德帮登录密码
			String ydbLoginPassword = clinicRequestParam.getYdbloginpassword();
			// 手机号
			String phoneNum = clinicRequestParam.getRegister().getPhonenum();
			// 验证码
			String vericode = clinicRequestParam.getRegister().getVericode();
			// openId
			String openId = clinicRequestParam.getRegister().getOpenid();
			if (StringUtils.isNotBlank(ydbLoginName)
					&& StringUtils.isNotBlank(ydbLoginPassword)
					&& StringUtils.isNotBlank(phoneNum)
					&& StringUtils.isNotBlank(clinicRequestParam.getRegister()
							.getOpenid())
					&& StringUtils.isNotBlank(clinicRequestParam.getRegister()
							.getPassword()) && StringUtils.isNotBlank(vericode)

					&& StringUtils.isNotBlank(openId)) {

				// 校验验证码
				UserCenterResultBean userCenterResultBean = userCenterService
						.validateVeriCode(phoneNum, vericode);
				clinicReplyReport.getResult().setBusinessCode(
						userCenterResultBean.getResultCode());
				clinicReplyReport.getResult().setMsg(
						userCenterResultBean.getResultMsg());

				// 验证码正确
				if (Constant.ResultCode.SUCCESS.equals(userCenterResultBean
						.getResultCode())) {
					// 根据手机号查询用户信息
					UserBean userBean = userService.findByPhoneNum(phoneNum);
					if (null == userBean) {
						// 验证医德帮老用户信息并返回id
						UserCenterResultBean ydbUserCenterResultBean = userCenterService
								.ydbValidateInfo(clinicCode, ydbLoginName,
										ydbLoginPassword, phoneNum,
										clinicRequestParam.getRegister()
												.getPassword());

						if (null != ydbUserCenterResultBean
								&& null != ydbUserCenterResultBean
										.getResultObject()) {

							// 医德帮老用户信息
							Map<String, Object> ydbInfoMap = (Map<String, Object>) ydbUserCenterResultBean
									.getResultObject();

							RegisterReport registerReport = clinicRequestParam
									.getRegister();
							registerReport.setCliniccode(clinicRequestParam
									.getCliniccode());

							// 诊所id
							registerReport.setClinicid(""
									+ ydbInfoMap.get("clinicId"));

							registerReport.setUserId(""
									+ ydbInfoMap.get("userKey"));

							// 绑定并注册
							String userKey = registerService
									.executeBasicRegister(registerReport);
							if (StringUtils.isNotBlank(userKey)) {
								ClinicReport clinicReport = new ClinicReport();
								clinicReport.setUserkey(userKey);
								List<ClinicReport> reply = new ArrayList<ClinicReport>();
								reply.add(clinicReport);
								clinicReplyReport.setReply(reply);
							} else {
								// 失败
								clinicReplyReport.getResult().setBusinessCode(
										Constant.ResultCode.FILURE);
								clinicReplyReport.getResult().setMsg("失败");
							}

						} else {
							clinicReplyReport.getResult().setBusinessCode(
									Constant.ResultCode.RESULT_FOURE);
							clinicReplyReport.getResult().setMsg("医德帮信息不匹配");
						}

					} else {
						// 已经注册
						clinicReplyReport.getResult().setBusinessCode(
								Constant.ResultCode.RESUlT_FIVE);
						clinicReplyReport.getResult().setMsg("该手机号已经注册");
					}
				}
			} else {
				clinicReplyReport.getResult().setBusinessCode(
						Constant.ResultCode.RESULT_TWO);
				clinicReplyReport.getResult().setMsg("请求参数不完整");
			}
		} catch (Exception e) {
			replyError(response);
			log.error(
					"In the method ClinicAction.bingdingClinic(HttpServletRequest request,HttpServletResponse response) exists error!",
					e);
			return null;
		}
		resReply(response, clinicReplyReport);
		return null;

	}

	/**
	 * 扫码老用户绑定诊所
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/scanOldUserBingdingClinic")
	@ResponseBody
	public String scanOldUserBingdingClinic(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		ClinicReplyReport clinicReplyReport = getClinicReplyReport();
		try {
			// 请求参数
			String jsonString = parseRequestReportToString(request, response);
			ClinicRequestReport clinicRequestReport = getRequestReport(jsonString);
			ClinicReport clinicRequestParam = clinicRequestReport
					.getCommandinfo();
			// 诊所编码
			String clinicCode = clinicRequestParam.getCliniccode();
			// 用户中心id
			String userCenterId = clinicRequestParam.getUserkey();
			// openId
			String openId = clinicRequestParam.getOpenid();
			// 手机号
			String phoneNum = clinicRequestParam.getRegister().getPhonenum();
			// 验证码
			String vericode = clinicRequestParam.getRegister().getVericode();

			if (StringUtils.isBlank(phoneNum) || StringUtils.isBlank(vericode)
					|| StringUtils.isBlank(clinicCode)
					|| StringUtils.isBlank(userCenterId)
					|| StringUtils.isBlank(openId)) {
				clinicReplyReport.getResult().setBusinessCode(
						Constant.ResultCode.RESULT_TWO);
				clinicReplyReport.getResult().setMsg("请求参数不完整");
				resReply(response, clinicReplyReport);
				return null;
			}

			// 根据手机号查询用户信息
			UserBean userBean = userService.findByPhoneNum(phoneNum);
			if (null != userBean) {
				// 已经注册
				clinicReplyReport.getResult().setBusinessCode(
						Constant.ResultCode.RESUlT_FIVE);
				clinicReplyReport.getResult().setMsg("该手机号已经注册");
				resReply(response, clinicReplyReport);
				return null;

			}

			// 校验验证码
			UserCenterResultBean validateVeriCodeResultBean = userCenterService
					.validateVeriCode(phoneNum, vericode);

			// 验证码
			if (!Constant.ResultCode.SUCCESS.equals(validateVeriCodeResultBean
					.getResultCode())) {
				clinicReplyReport.getResult().setBusinessCode(
						validateVeriCodeResultBean.getResultCode());
				clinicReplyReport.getResult().setMsg(
						validateVeriCodeResultBean.getResultMsg());
				resReply(response, clinicReplyReport);
				return null;
			}

			// 判断诊所
			if (null != userService.findByClinicCode(clinicCode)) {
				clinicReplyReport.getResult().setBusinessCode(
						Constant.ResultCode.RESULT_THREE);
				clinicReplyReport.getResult().setMsg("该诊所已被其它用户绑定");
				resReply(response, clinicReplyReport);
				return null;
			}
			// 判断用户
			if (null != userService.findByUserId(userCenterId)) {
				clinicReplyReport.getResult().setBusinessCode(
						Constant.ResultCode.RESULT_THREE);
				clinicReplyReport.getResult().setMsg("该用户已经绑定");
				resReply(response, clinicReplyReport);
				return null;
			}

			// 医德帮-根据诊所编码查询用户信息
			UserCenterResultBean userCenterResultBean = userCenterService
					.ydbGetUserByClinicCode(clinicCode);

			// 验证诊所
			if (null == userCenterResultBean
					|| null == userCenterResultBean.getResultObject()) {
				clinicReplyReport.getResult().setBusinessCode(
						Constant.ResultCode.RESULT_THREE);
				clinicReplyReport.getResult().setMsg("未找到该诊所编码对应的信息");
				resReply(response, clinicReplyReport);
				return null;

			}
			// 医德帮用户信息
			Map<String, String> map = (Map<String, String>) userCenterResultBean
					.getResultObject();

			// 验证医德帮信息
			UserCenterResultBean ydbUserCenterResultBean = userCenterService
					.ydbValidateInfo(clinicCode, map.get("ydbLoginName"),
							map.get("ydbLoginPassword"), phoneNum,
							map.get("ydbLoginPassword"));
			// 验证老用户登录信息
			if (null == ydbUserCenterResultBean
					|| null == ydbUserCenterResultBean.getResultObject()) {
				clinicReplyReport.getResult().setBusinessCode(
						Constant.ResultCode.RESULT_FOURE);
				clinicReplyReport.getResult().setMsg("诊所信息不正确");
				resReply(response, clinicReplyReport);
				return null;
			}
			// 医德帮老用户信息
			Map<String, String> ydbInfoMap = (Map<String, String>) ydbUserCenterResultBean
					.getResultObject();
			RegisterReport registerReport = new RegisterReport();
			// 诊所id
			registerReport.setClinicid(ydbInfoMap.get("clinicId"));
			// 诊所编码
			registerReport.setCliniccode(clinicCode);
			// 手机号
			registerReport.setPhonenum(phoneNum);
			// 用户中心id
			registerReport.setUserId(userCenterId);
			// 微信id
			registerReport.setOpenid(openId);
			// 医德帮老用户密码
			registerReport.setPassword(map.get("ydbLoginPassword"));
			// 绑定并注册
			String userKey = registerService
					.executeBasicRegister(registerReport);
			if (StringUtils.isBlank(userKey)) {
				// 失败
				clinicReplyReport.getResult().setBusinessCode(
						Constant.ResultCode.FILURE);
				clinicReplyReport.getResult().setMsg("失败");
			}
		} catch (Exception e) {
			replyError(response);
			log.error(
					"In the method ClinicAction.scanOldUserBingdingClinic(HttpServletRequest request,HttpServletResponse response) exists error!",
					e);
			return null;
		}
		resReply(response, clinicReplyReport);
		return null;

	}

	/**
	 * 特殊绑定诊所
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/specialBingdingClinic")
	@ResponseBody
	public String specialBingdingClinic(HttpServletRequest request,
			HttpServletResponse response) throws IOException {

		ClinicReplyReport clinicReplyReport = getClinicReplyReport();
		try {
			String openId = request.getParameter("openId");
			String phoneNum = request.getParameter("phoneNum");
			String clinicCode = request.getParameter("clinicCode");
			String userCenterId = request.getParameter("userCenterId");
			String password = request.getParameter("password");

			if (StringUtils.isBlank(openId) || StringUtils.isBlank(phoneNum)
					|| StringUtils.isBlank(clinicCode)
					|| StringUtils.isBlank(userCenterId)) {
				clinicReplyReport.getResult().setBusinessCode(
						Constant.ResultCode.RESULT_THREE);
				clinicReplyReport.getResult().setMsg("请求参数不完整");
			}
			// 医德帮-根据诊所编码查询用户信息
			UserCenterResultBean userCenterResultBean = userCenterService
					.ydbGetUserByClinicCode(clinicCode);

			// 验证诊所
			if (null == userCenterResultBean
					|| null == userCenterResultBean.getResultObject()) {
				clinicReplyReport.getResult().setBusinessCode(
						Constant.ResultCode.RESULT_THREE);
				clinicReplyReport.getResult().setMsg("未找到该诊所编码对应的信息");
				resReply(response, clinicReplyReport);
				return null;

			}
			// 医德帮用户信息
			Map<String, String> map = (Map<String, String>) userCenterResultBean
					.getResultObject();

			// 验证医德帮信息
			UserCenterResultBean ydbUserCenterResultBean = userCenterService
					.ydbValidateInfo(clinicCode, map.get("ydbLoginName"),
							map.get("ydbLoginPassword"), phoneNum,
							password);
			// 验证老用户登录信息
			if (null == ydbUserCenterResultBean
					|| null == ydbUserCenterResultBean.getResultObject()) {
				clinicReplyReport.getResult().setBusinessCode(
						Constant.ResultCode.RESULT_FOURE);
				clinicReplyReport.getResult().setMsg("诊所信息不正确");
				resReply(response, clinicReplyReport);
				return null;
			}
			// 医德帮老用户信息
			Map<String, String> ydbInfoMap = (Map<String, String>) ydbUserCenterResultBean
					.getResultObject();
			RegisterReport registerReport = new RegisterReport();
			// 诊所id
			registerReport.setClinicid(ydbInfoMap.get("clinicId"));
			// 诊所编码
			registerReport.setCliniccode(clinicCode);
			// 手机号
			registerReport.setPhonenum(phoneNum);
			// 用户中心id
			registerReport.setUserId(userCenterId);
			// 微信id
			registerReport.setOpenid(openId);
			// 医德帮老用户密码
			registerReport.setPassword(password);
			// 绑定并注册
			String userKey = registerService
					.executeBasicRegister(registerReport);
			if (StringUtils.isBlank(userKey)) {
				// 失败
				clinicReplyReport.getResult().setBusinessCode(
						Constant.ResultCode.FILURE);
				clinicReplyReport.getResult().setMsg("失败");
			}
		} catch (Exception e) {
			replyError(response);
			log.error(
					"In the method ClinicAction.specialBingdingClinic(HttpServletRequest request,HttpServletResponse response) exists error!",
					e);
			return null;
		}
		resReply(response, clinicReplyReport);
		return null;

	}

	/**
	 * 医德帮用户信息
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/ydbUserInfo")
	@ResponseBody
	public String ydbUserInfo(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		ClinicReplyReport clinicReplyReport = getClinicReplyReport();
		try {
			// 请求参数
			String jsonString = parseRequestReportToString(request, response);
			JSONObject requestJsonObject = getJsonObject(jsonString);
			// 诊所编码
			String clinicCode = requestJsonObject.getJSONObject("commandinfo")
					.getString("cliniccode");
			if (StringUtils.isNotBlank(clinicCode)) {

				UserCenterResultBean userCenterResultBean = userCenterService
						.ydbGetUserByClinicCode(clinicCode);

				if (null != userCenterResultBean
						&& null != userCenterResultBean.getResultObject()) {

					Map<String, Object> map = (Map<String, Object>) userCenterResultBean
							.getResultObject();

					ClinicReport clinicReport = new ClinicReport();
					clinicReport.setYdbloginname("" + map.get("ydbLoginName"));
					clinicReport.setYdbphonenum(""
							+ map.get("ydbLoginPhoneNum"));

					List<ClinicReport> reply = new ArrayList<ClinicReport>();
					reply.add(clinicReport);
					clinicReplyReport.setReply(reply);

				} else {

					clinicReplyReport.getResult().setBusinessCode(
							Constant.ResultCode.RESULT_THREE);
					clinicReplyReport.getResult().setMsg("未找到该诊所编码对应的信息");

				}

			} else {
				clinicReplyReport.getResult().setBusinessCode(
						Constant.ResultCode.FILURE);
				clinicReplyReport.getResult().setMsg("请求参数不完整");
			}
		} catch (Exception e) {
			replyError(response);
			log.error(
					"In the method ClinicAction.ydbUserInfo(HttpServletRequest request,HttpServletResponse response) exists error!",
					e);
			return null;
		}
		resReply(response, clinicReplyReport);
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
			ClinicReplyReport clinicReplyReport) throws IOException {
		if (clinicReplyReport == null) {
			replyIdentifyError(response);
			return;
		}
		clinicReplyReport.setReplytime(String.valueOf(System
				.currentTimeMillis()));// replyTime
		String replyReport = JSON.toJSONString(clinicReplyReport);
		responseClient(response, replyReport);
	}

	/**
	 * 获取返回对象
	 * 
	 * @return
	 */
	private ClinicReplyReport getClinicReplyReport() {
		ClinicReplyReport clinicReplyReport = new ClinicReplyReport();
		BaseReplyResult result = new BaseReplyResult();
		result.setCode(Constant.ResultCode.SUCCESS);
		result.setBusinessCode(Constant.ResultCode.SUCCESS);
		clinicReplyReport.setResult(result);
		return clinicReplyReport;
	}

	/**
	 * 把字符串解析成java对象
	 * 
	 * @param jsonString
	 *            需要解析的json字符串
	 * @return String 报文中commandType值
	 */
	private ClinicRequestReport getRequestReport(String jsonString) {
		JSONObject jsonObject = getJsonObject(jsonString);
		return (ClinicRequestReport) JSON.toJavaObject(jsonObject,
				ClinicRequestReport.class);
	}

}
