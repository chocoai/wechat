package com.miyzh.wechatshop.usercenter.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.miyzh.clinic.api.ClinicService;
import com.miyzh.clinic.api.ClinicUserService;
import com.miyzh.clinic.enums.ChannelType;
import com.miyzh.clinic.enums.ClinicRegSourceType;
import com.miyzh.clinic.vo.ClinicUserCertificateStatus;
import com.miyzh.clinic.vo.ClinicVo;
import com.miyzh.cp.code.VerifyCodeService;
import com.miyzh.cp.sms.SmsRequest;
import com.miyzh.cp.sms.SmsResponse;
import com.miyzh.cp.sms.SmsService;
import com.miyzh.cp.sms.SmsSignName;
import com.miyzh.cp.sms.SmsTemplateType;
import com.miyzh.framework.base.Constant;
import com.miyzh.framework.util.MoibleUtil;
import com.miyzh.migrate.api.Yideb1xClinicService;
import com.miyzh.migrate.api.Yideb1xUserService;
import com.miyzh.migrate.vo.Yideb1xUserAuthRequest;
import com.miyzh.migrate.vo.Yideb1xUserAuthResponse;
import com.miyzh.ucenter.api.UnifiedUserService;
import com.miyzh.ucenter.enums.UserRegSourceType;
import com.miyzh.ucenter.vo.UserVo;
import com.miyzh.wechatshop.payUtil.service.AddressService;
import com.miyzh.wechatshop.usercenter.bean.UserCenterResultBean;

/**
 * 文件名： UserCenterService.java<br>
 * 描述: 用户中心接口<br>
 * 修改人: wumingdi<br>
 * 修改时间： 2016年7月10日 <br>
 */
@Service("userCenterService")
public class UserCenterService {

	protected Log log = LogFactory.getLog(getClass());

	@Autowired
	private ClinicUserService clinicUserService;

	/** 医德帮诊所服务 **/
	@Autowired
	private Yideb1xClinicService yideb1xClinicService;

	/** 医德帮用户服务 **/
	@Autowired
	private Yideb1xUserService yideb1xUserService;

	/** 基础通用服务 **/
	@Autowired
	private VerifyCodeService verifyCodeService;

	/** 统一用户服务 **/
	@Autowired
	private UnifiedUserService unifiedUserService;

	/** 诊所信息服务 **/
	@Autowired
	private ClinicService clinicService;

	/** 统一短信服务 **/
	@Autowired
	private SmsService smsService;

	/** 地区 **/
	@Autowired
	private AddressService addressService;

	// 验证码有效时间
	private final long validateTime = 180L;

	/**
	 * 根据unionId查询用户信息
	 * 
	 * @param unionId
	 * @return
	 */
	public UserCenterResultBean getUserByUnionId(String unionId) {
		UserCenterResultBean userCenterResultBean = new UserCenterResultBean();
		userCenterResultBean.setResultCode(Constant.ResultCode.SUCCESS);
		userCenterResultBean.setResultMsg("成功");
		try {

			UserVo userVo = unifiedUserService.getUserByWeiXinUnionId(unionId);
			if (null != userVo) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("uid", userVo.getUid());
				userCenterResultBean.setResultObject(map);
			}
		} catch (Exception e) {
			log.error(
					"In the method UserCenterService.getUserByUnionId(String unionId) exists error!",
					e);
			userCenterResultBean.setResultCode(Constant.ResultCode.FILURE);
			userCenterResultBean.setResultMsg("网络繁忙请稍后再试");
		}
		return userCenterResultBean;

	}

	/**
	 * 判断手机号是否存在
	 * 
	 * @param phoneNum
	 * @return
	 */
	public UserCenterResultBean existByMobile(String phoneNum) {
		UserCenterResultBean userCenterResultBean = new UserCenterResultBean();
		userCenterResultBean.setResultCode(Constant.ResultCode.SUCCESS);
		userCenterResultBean.setResultMsg("成功");
		try {
			boolean flag = unifiedUserService.existByMobile(phoneNum);
			userCenterResultBean.setResultObject(flag);

		} catch (Exception e) {
			log.error(
					"In the method UserCenterService.modifyPasswordByMobile(String phoneNum) exists error!",
					e);
			userCenterResultBean.setResultCode(Constant.ResultCode.FILURE);
			userCenterResultBean.setResultMsg("网络繁忙请稍后再试");
		}
		return userCenterResultBean;

	}

	/**
	 * 用户中心绑定微信
	 * 
	 * @param usercenterKey
	 *            用户ID
	 * @param unionId
	 * @return
	 */
	public UserCenterResultBean bindUserWeiXin(String usercenterKey,
			String unionId) {
		UserCenterResultBean userCenterResultBean = new UserCenterResultBean();
		userCenterResultBean.setResultCode(Constant.ResultCode.SUCCESS);
		userCenterResultBean.setResultMsg("成功");
		try {
			boolean flag = unifiedUserService.bindUserWeiXin(usercenterKey,
					null, unionId);
			userCenterResultBean.setResultObject(flag);

		} catch (Exception e) {
			log.error(
					"In the method UserCenterService.modifyPasswordByMobile(String usercenterKey , String unionId) exists error!",
					e);
			userCenterResultBean.setResultCode(Constant.ResultCode.FILURE);
			userCenterResultBean.setResultMsg("网络繁忙请稍后再试");
		}
		return userCenterResultBean;

	}

	/**
	 * 根据手机号修改用户密码
	 * 
	 * @param phoneNum
	 *            手机号
	 * @param password
	 *            密码
	 * @return
	 */
	public UserCenterResultBean modifyPasswordByMobile(String phoneNum,
			String password) {
		UserCenterResultBean userCenterResultBean = new UserCenterResultBean();
		userCenterResultBean.setResultCode(Constant.ResultCode.SUCCESS);
		userCenterResultBean.setResultMsg("成功");
		try {

			boolean flag = unifiedUserService.modifyPasswordByMobile(phoneNum,
					password);

			userCenterResultBean.setResultObject(flag);

		} catch (Exception e) {
			log.error(
					"In the method UserCenterService.modifyPasswordByMobile(String phoneNum , String password) exists error!",
					e);
			userCenterResultBean.setResultCode(Constant.ResultCode.FILURE);
			userCenterResultBean.setResultMsg("网络繁忙请稍后再试");
		}
		return userCenterResultBean;

	}

	/**
	 * 验证用户账户信息
	 * 
	 * @param phoneNum
	 *            手机号
	 * @param password
	 *            密码
	 * @return
	 */
	public UserCenterResultBean validateUserAccountInfo(String phoneNum,
			String password) {
		UserCenterResultBean userCenterResultBean = new UserCenterResultBean();
		userCenterResultBean.setResultCode(Constant.ResultCode.SUCCESS);
		userCenterResultBean.setResultMsg("成功");
		try {
			boolean flag = unifiedUserService.doAuth(phoneNum, password);
			userCenterResultBean.setResultObject(flag);

		} catch (Exception e) {
			log.error(
					"In the method UserCenterService.validateUserAccountInfo(String phoneNum , String password) exists error!",
					e);
			userCenterResultBean.setResultCode(Constant.ResultCode.FILURE);
			userCenterResultBean.setResultMsg("网络繁忙请稍后再试");
		}
		return userCenterResultBean;
	}

	/**
	 * 根据手机号在医德帮验证是否存在
	 * 
	 * @param phoneNum
	 * @return
	 */
	public UserCenterResultBean ydbExistByMobile(String phoneNum) {

		UserCenterResultBean userCenterResultBean = new UserCenterResultBean();
		userCenterResultBean.setResultCode(Constant.ResultCode.SUCCESS);
		userCenterResultBean.setResultMsg("成功");
		try {
			boolean flag = yideb1xUserService.existByMobile(phoneNum);
			userCenterResultBean.setResultObject(flag);

		} catch (Exception e) {
			log.error(
					"In the method UserCenterService.ydbExistByMobile(String phoneNum) exists error!",
					e);
			userCenterResultBean.setResultCode(Constant.ResultCode.FILURE);
			userCenterResultBean.setResultMsg("网络繁忙请稍后再试");
		}
		return userCenterResultBean;
	}

	/**
	 * 查询诊所信息列表
	 * 
	 * @param userId
	 *            用户Id
	 * @return
	 */
	public UserCenterResultBean queryClinicList(String userId) {

		UserCenterResultBean userCenterResultBean = new UserCenterResultBean();
		userCenterResultBean.setResultCode(Constant.ResultCode.SUCCESS);
		userCenterResultBean.setResultMsg("成功");
		try {

			List<ClinicVo> clinicVoList = clinicUserService
					.getUserClinicList(userId);
			List<Map<String, Object>> listMap = new ArrayList<Map<String, Object>>();

			if (!clinicVoList.isEmpty()) {

				for (ClinicVo clinicVo : clinicVoList) {
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("clinicName", clinicVo.getClinicName());
					map.put("clinicAddress", clinicVo.getClinicAddress());
					map.put("clinicId", clinicVo.getId());
					map.put("clinicCode", clinicVo.getClinicId());
					// 诊所认证状态 0：否；1：是
					map.put("isCertificateAuthenticated",
							clinicVo.getIsCertificateAuthenticated());
					listMap.add(map);

				}
				userCenterResultBean.setResultObject(listMap);
			}

		} catch (Exception e) {
			log.error(
					"In the method UserCenterService.queryClinicList(String userId) exists error!",
					e);
			userCenterResultBean.setResultCode(Constant.ResultCode.FILURE);
			userCenterResultBean.setResultMsg("网络繁忙请稍后再试");
		}
		return userCenterResultBean;

	}

	/**
	 * 验证医德帮信息
	 * 
	 * @param clinicCode
	 *            诊所编码
	 * @param ydbLoginName
	 *            医德帮登录名
	 * @param ydbLoginPassword
	 *            医德帮登录密码
	 * @param password
	 *            新密码
	 * @return
	 */
	public UserCenterResultBean ydbValidateInfo(String clinicCode,
			String ydbLoginName, String ydbLoginPassword, String moible,
			String password) {
		UserCenterResultBean userCenterResultBean = new UserCenterResultBean();
		userCenterResultBean.setResultCode(Constant.ResultCode.SUCCESS);
		userCenterResultBean.setResultMsg("成功");
		try {

			// param 1：诊所编码；2：登录名；3：密码；4：手机号；
			Yideb1xUserAuthRequest yideb1xUserAuthRequest = new Yideb1xUserAuthRequest();

			yideb1xUserAuthRequest.setMobile(moible);
			yideb1xUserAuthRequest.setYideb1xClinicCode(clinicCode);
			yideb1xUserAuthRequest.setYideb1xUserName(ydbLoginName);
			yideb1xUserAuthRequest.setYideb1xPassword(ydbLoginPassword);
			yideb1xUserAuthRequest.setPassword(password);

			Yideb1xUserAuthResponse yideb1xUserAuthResponse = yideb1xUserService
					.doAuth(yideb1xUserAuthRequest);

			if (StringUtils.isNotBlank(yideb1xUserAuthResponse.getCid())
					&& StringUtils.isNotBlank(yideb1xUserAuthResponse.getUid())) {

				Map<String, String> map = new HashMap<String, String>();
				map.put("clinicId", yideb1xUserAuthResponse.getCid());
				map.put("userKey", yideb1xUserAuthResponse.getUid());
				userCenterResultBean.setResultObject(map);
			}

		} catch (Exception e) {
			log.error(
					"In the method UserCenterService.ydbValidateInfo(String clinicCode,String ydbLoginName, String ydbLoginPassword , String password) exists error!",
					e);
			userCenterResultBean.setResultCode(Constant.ResultCode.FILURE);
			userCenterResultBean.setResultMsg("网络繁忙请稍后再试");
		}
		return userCenterResultBean;

	}

	/**
	 * 医德帮-根据诊所编码获取诊所信息
	 * 
	 * @param clinicCode
	 *            诊所编码
	 * @return
	 */
	public UserCenterResultBean ydbGetClinicByClinicCode(String clinicCode) {
		UserCenterResultBean userCenterResultBean = new UserCenterResultBean();
		userCenterResultBean.setResultCode(Constant.ResultCode.SUCCESS);
		userCenterResultBean.setResultMsg("成功");
		try {

			userCenterResultBean.setResultObject(yideb1xClinicService
					.getClinicId(clinicCode));

		} catch (Exception e) {
			log.error(
					"In the method UserCenterService.ydbGetClinicByClinicCode(String clinicCode) exists error!",
					e);
			userCenterResultBean.setResultCode(Constant.ResultCode.FILURE);
			userCenterResultBean.setResultMsg("网络繁忙请稍后再试");
		}
		return userCenterResultBean;

	}

	/**
	 * 医德帮-根据诊所编码查询用户信息
	 * 
	 * @param clinicCode
	 *            诊所编码
	 * @return
	 */
	public UserCenterResultBean ydbGetUserByClinicCode(String clinicCode) {
		UserCenterResultBean userCenterResultBean = new UserCenterResultBean();
		userCenterResultBean.setResultCode(Constant.ResultCode.SUCCESS);
		userCenterResultBean.setResultMsg("成功");
		try {

			UserVo userVo = yideb1xUserService.getUserByClinicCode(clinicCode);

			if (null != userVo) {

				Map<String, String> resutlMap = new HashMap<String, String>();
				// 医德帮用户登录帐号
				resutlMap.put("ydbLoginName", userVo.getLoginName());
				// 医德帮用户手机号
				resutlMap.put("ydbLoginPhoneNum", userVo.getMobile());
				// 医德帮用户密码
				resutlMap.put("ydbLoginPassword", userVo.getPassword());

				userCenterResultBean.setResultObject(resutlMap);
			}
		} catch (Exception e) {
			log.error(
					"In the method UserCenterService.ydbGetUserByClinicCode(String clinicCode) exists error!",
					e);
			userCenterResultBean.setResultCode(Constant.ResultCode.FILURE);
			userCenterResultBean.setResultMsg("网络繁忙请稍后再试");
		}
		return userCenterResultBean;

	}

	/**
	 * 诊所认证结果
	 * 
	 * @param userId
	 *            用户主键
	 * @param clinicId
	 *            诊所id
	 * @return
	 */
	public UserCenterResultBean clinicAuthResult(String userId, String clinicId) {

		UserCenterResultBean userCenterResultBean = new UserCenterResultBean();
		userCenterResultBean.setResultCode(Constant.ResultCode.SUCCESS);
		userCenterResultBean.setResultMsg("成功");
		try {

			ClinicUserCertificateStatus clinicUserCertificateStatus = clinicUserService
					.getCertificateStatus(userId, clinicId, ChannelType.miyShop);

			if (null != clinicUserCertificateStatus) {
				Map<String, Object> map = new HashMap<String, Object>();
				// 审核状态
				// 0（noCertificate）：注册用户；1（certificated）：认证用户；2（certificateWaiting）：待审核；3（certificateFailed）：审核不通过；
				map.put("auditStatus", clinicUserCertificateStatus
						.getCertificateStatus().getCode());
				// 审核意见
				map.put("certificateOpinion",
						clinicUserCertificateStatus.getCertificateOpinion());
				userCenterResultBean.setResultObject(map);
			}

		} catch (Exception e) {
			log.error(
					"In the method UserCenterService.isCertificated(String clinicId) exists error!",
					e);
			userCenterResultBean.setResultCode(Constant.ResultCode.FILURE);
			userCenterResultBean.setResultMsg("网络繁忙请稍后再试");
		}
		return userCenterResultBean;

	}

	/**
	 * 用户认证结果
	 * 
	 * @param userId
	 *            用户id
	 * @param clinicId
	 *            诊所id
	 * @return
	 */
	public UserCenterResultBean userAuthResult(String userId, String clinicId) {
		UserCenterResultBean userCenterResultBean = new UserCenterResultBean();
		userCenterResultBean.setResultCode(Constant.ResultCode.SUCCESS);
		userCenterResultBean.setResultMsg("成功");
		try {

			boolean flag = clinicUserService.isCertificatedUser(userId,
					clinicId, ChannelType.miyShop);

			userCenterResultBean.setResultObject(flag);

		} catch (Exception e) {
			log.error(
					"In the method UserCenterService.isCertificated(String clinicId) exists error!",
					e);
			userCenterResultBean.setResultCode(Constant.ResultCode.FILURE);
			userCenterResultBean.setResultMsg("网络繁忙请稍后再试");
		}
		return userCenterResultBean;

	}

	/**
	 * 注册诊所信息
	 * 
	 * @param userId
	 *            用户主键
	 * @param paramMap
	 * @return
	 */
	public UserCenterResultBean registerClinic(String userId,
			Map<String, Object> paramMap) {

		UserCenterResultBean userCenterResultBean = new UserCenterResultBean();
		userCenterResultBean.setResultCode(Constant.ResultCode.SUCCESS);
		userCenterResultBean.setResultMsg("成功");
		try {

			// 地址前缀
			// String addressPrefixed = "";

			ClinicVo clinicVo = new ClinicVo();
			clinicVo.setUid(userId);
			clinicVo.setClinicName("" + paramMap.get("clinicName"));
			clinicVo.setClinicMobile("" + paramMap.get("clinicMobile"));
			if (null != paramMap.get("province")
					&& StringUtils.isNotBlank("" + paramMap.get("province"))) {
				// 根据地区id查询地区信息
				// Address address = addressService.findByAreaName(
				// "" + paramMap.get("province"));
				clinicVo.setProvince("" + paramMap.get("province"));
				// addressPrefixed += address.getName();
			}

			if (null != paramMap.get("city")
					&& StringUtils.isNotBlank("" + paramMap.get("city"))) {
				// 根据地区id查询地区信息
				// Address address = addressService.findByAreaName(
				// "" + paramMap.get("city"));
				clinicVo.setCity("" + paramMap.get("city"));
				// addressPrefixed += address.getName();
			}

			if (null != paramMap.get("district")
					&& StringUtils.isNotBlank("" + paramMap.get("district"))) {
				// 根据地区id查询地区信息
				// Address address = addressService.findByAreaName(
				// "" + paramMap.get("district"));
				clinicVo.setDistrict("" + paramMap.get("district"));
				// addressPrefixed += address.getName();
			}
			clinicVo.setClinicAddress("" + paramMap.get("clinicAddress"));
			clinicVo.setRegSource(ClinicRegSourceType.miyShop.getCode());
			clinicVo.setClcCertificateCardPic(""
					+ paramMap.get("clcCertificateCardPic"));
			clinicVo.setRegisterUserCertificateCardPic(""
					+ paramMap.get("registerUserCertificateCardPic"));
			clinicVo.setClcCertificateCard("WECHAT_12345678");
			ClinicVo resultClinicVo = clinicService.register(clinicVo);
			userCenterResultBean.setResultObject(resultClinicVo.getId());

		} catch (Exception e) {
			log.error(
					"In the method UserCenterService.registerClinic(String userId,Map<String, Object> paramMap) exists error!",
					e);
			userCenterResultBean.setResultCode(Constant.ResultCode.FILURE);
			userCenterResultBean.setResultMsg("网络繁忙请稍后再试");
		}
		return userCenterResultBean;

	}

	/**
	 * 修改诊所信息
	 * 
	 * @param paramMap
	 * @param isNeedCertificate
	 *            是否需要审核
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public UserCenterResultBean modifyClinic(Map<String, Object> paramMap,
			boolean isNeedCertificate) {

		UserCenterResultBean userCenterResultBean = new UserCenterResultBean();
		userCenterResultBean.setResultCode(Constant.ResultCode.SUCCESS);
		userCenterResultBean.setResultMsg("成功");
		try {

			// 地址前缀
			// String addressPrefixed = "";
			ClinicVo clinicVo = new ClinicVo();

			// 由于前台没有传诊所认证信息
			UserCenterResultBean clinicInfo = queryClinicbyClinicId(""
					+ paramMap.get("clinicId"));

			if (null != clinicInfo && null != clinicInfo.getResultObject()) {
				Map<String, Object> clinicMap = (Map<String, Object>) clinicInfo
						.getResultObject();

				if (null != paramMap.get("uid")
						&& StringUtils.isNotBlank("" + paramMap.get("uid"))) {
					clinicVo.setUid("" + paramMap.get("uid"));
				}
				if (null != paramMap.get("clinicName")
						&& StringUtils.isNotBlank(""
								+ paramMap.get("clinicName"))) {
					clinicVo.setClinicName("" + paramMap.get("clinicName"));
				} else {
					clinicVo.setClinicName("" + clinicMap.get("clinicname"));

				}
				if (null != paramMap.get("clinicMobile")
						&& StringUtils.isNotBlank(""
								+ paramMap.get("clinicMobile"))) {
					clinicVo.setClinicMobile("" + paramMap.get("clinicMobile"));
				} else {
					clinicVo.setClinicMobile("" + clinicMap.get("phonenum"));

				}
				if (null != paramMap.get("province")
						&& StringUtils
								.isNotBlank("" + paramMap.get("province"))) {
					// 根据地区id查询地区信息
					// Address address = addressService.findByAreaName(
					// "" + paramMap.get("province"));
					clinicVo.setProvince("" + paramMap.get("province"));
					// addressPrefixed += address.getName();
				} else if (null != clinicMap.get("province")
						&& StringUtils
								.isNotBlank("" + paramMap.get("province"))) {
					clinicVo.setProvince("" + clinicMap.get("province"));
				}

				if (null != paramMap.get("city")
						&& StringUtils.isNotBlank("" + paramMap.get("city"))) {
					// 根据地区id查询地区信息
					// Address address = addressService.findByAreaName(
					// "" + paramMap.get("city"));
					clinicVo.setCity("" + paramMap.get("province"));
					// addressPrefixed += address.getName();
				} else if (null != clinicMap.get("city")
						&& StringUtils.isNotBlank("" + paramMap.get("city"))) {
					clinicVo.setCity("" + clinicMap.get("city"));

				}

				if (null != paramMap.get("district")
						&& StringUtils
								.isNotBlank("" + paramMap.get("district"))) {
					// 根据地区id查询地区信息
					// Address address = addressService.findByAreaName(
					// "" + paramMap.get("district"));
					clinicVo.setDistrict("" + paramMap.get("district"));
					// addressPrefixed += address.getName();
				} else if (null != clinicMap.get("district")
						&& StringUtils
								.isNotBlank("" + paramMap.get("district"))) {
					clinicVo.setDistrict("" + clinicMap.get("district"));

				}

				if (null != paramMap.get("clinicAddress")
						&& StringUtils.isNotBlank(""
								+ paramMap.get("clinicAddress"))
						&& ("" + paramMap.get("clinicAddress"))
								.equals(("" + clinicMap.get("clinicaddress")))) {
					clinicVo.setClinicAddress(""
							+ paramMap.get("clinicAddress"));
				} else {
					clinicVo.setClinicAddress(""
							+ clinicMap.get("clinicaddress"));
				}
				clinicVo.setRegSource(ClinicRegSourceType.miyShop.getCode());
				if (null != paramMap.get("clcCertificateCardPic")
						&& StringUtils.isNotBlank(""
								+ paramMap.get("clcCertificateCardPic"))) {
					clinicVo.setClcCertificateCardPic(""
							+ paramMap.get("clcCertificateCardPic"));
				} else {
					clinicVo.setClcCertificateCardPic(""
							+ clinicMap.get("certificatecardurl"));
				}

				if (null != paramMap.get("registerUserCertificateCardPic")
						&& StringUtils.isNotBlank(""
								+ paramMap
										.get("registerUserCertificateCardPic"))) {
					clinicVo.setRegisterUserCertificateCardPic(""
							+ paramMap.get("registerUserCertificateCardPic"));
				} else {
					clinicVo.setRegisterUserCertificateCardPic(""
							+ clinicMap.get("doctorseniorityurl"));
				}
				if (null != paramMap.get("clinicId")
						&& StringUtils
								.isNotBlank("" + paramMap.get("clinicId"))) {
					clinicVo.setId("" + paramMap.get("clinicId"));
				}

				if (null != paramMap.get("divisionDescription")
						&& StringUtils.isNotBlank(""
								+ paramMap.get("divisionDescription"))) {
					clinicVo.setDivisionDescription(""
							+ paramMap.get("divisionDescription"));
				}

				if (null != paramMap.get("clinicDivision")
						&& StringUtils.isNotBlank(""
								+ paramMap.get("clinicDivision"))) {
					clinicVo.setClinicDivision(Integer.parseInt(""
							+ paramMap.get("clinicDivision")));

				}
				if (null != paramMap.get("clinicScope")
						&& StringUtils.isNotBlank(""
								+ paramMap.get("clinicScope"))) {
					clinicVo.setClinicScope(Integer.parseInt(""
							+ paramMap.get("clinicScope")));

				}
				if (null != paramMap.get("clinicNature")
						&& StringUtils.isNotBlank(""
								+ paramMap.get("clinicNature"))) {
					clinicVo.setClinicNature(Integer.parseInt(""
							+ paramMap.get("clinicNature")));

				}
				if (null != paramMap.get("clinicArea")
						&& StringUtils.isNotBlank(""
								+ paramMap.get("clinicArea"))) {
					clinicVo.setClinicArea(Integer.parseInt(""
							+ paramMap.get("clinicArea")));

				}
				if (null != paramMap.get("clinicScale")
						&& StringUtils.isNotBlank(""
								+ paramMap.get("clinicScale"))) {
					clinicVo.setClinicScale(Integer.parseInt(""
							+ paramMap.get("clinicScale")));

				}
			}
			clinicVo.setClcCertificateCard("WECHAT_12345678");
			clinicService.modifyClinic(clinicVo, ChannelType.miyShop,
					isNeedCertificate);

		} catch (Exception e) {
			log.error(
					"In the method UserCenterService.registerClinic(String userId,Map<String, Object> paramMap) exists error!",
					e);
			userCenterResultBean.setResultCode(Constant.ResultCode.FILURE);
			userCenterResultBean.setResultMsg("网络繁忙请稍后再试");
		}
		return userCenterResultBean;

	}

	/**
	 * 根据诊所id查询诊所信息
	 * 
	 * @param clinicId
	 *            诊所id
	 * @return
	 */
	public UserCenterResultBean queryClinicbyClinicId(String clinicId) {
		UserCenterResultBean userCenterResultBean = new UserCenterResultBean();
		userCenterResultBean.setResultCode(Constant.ResultCode.SUCCESS);
		userCenterResultBean.setResultMsg("成功");
		try {

			ClinicVo clinicVo = clinicService.getClinicById(clinicId);
			if (null != clinicVo) {
				Map<String, Object> map = new HashMap<String, Object>();

				map.put("phonenum", clinicVo.getClinicMobile());
				map.put("clinicname", clinicVo.getClinicName());
				map.put("province", clinicVo.getProvince());
				map.put("city", clinicVo.getCity());
				map.put("county", clinicVo.getDistrict());
				map.put("clinicaddress", clinicVo.getClinicAddress());
				map.put("certificatecardurl",
						clinicVo.getClcCertificateCardPic());
				map.put("doctorseniorityurl",
						clinicVo.getRegisterUserCertificateCardPic());
				map.put("clinicscope", clinicVo.getClinicScope());
				map.put("clinicscale", clinicVo.getClinicScale());
				map.put("tgCode", clinicVo.getReferenceId()); // 推广码
				map.put("clinicarea", clinicVo.getClinicArea());
				map.put("clinicnature", clinicVo.getClinicNature());
				map.put("clinicCode", clinicVo.getClinicId());
				map.put("clinicdivision", clinicVo.getClinicDivision());
				map.put("divisiondescription",
						clinicVo.getDivisionDescription());

				userCenterResultBean.setResultObject(map);
			}

		} catch (Exception e) {
			log.error(
					"In the method UserCenterService.queryClinicbyClinicId(String clinicId) exists error!",
					e);
			userCenterResultBean.setResultCode(Constant.ResultCode.FILURE);
			userCenterResultBean.setResultMsg("网络繁忙请稍后再试");
		}
		return userCenterResultBean;

	}

	/**
	 * 注册用户接口
	 * 
	 * @param paramMap
	 *            key:mobile（手机号）; key:password（密码）;
	 * @return
	 */
	public UserCenterResultBean registerUser(Map<String, Object> paramMap) {

		UserCenterResultBean userCenterResultBean = new UserCenterResultBean();
		userCenterResultBean.setResultCode(Constant.ResultCode.SUCCESS);
		userCenterResultBean.setResultMsg("成功");
		try {
			UserVo userVo = new UserVo();
			userVo.setMobile("" + paramMap.get("mobile"));
			userVo.setPassword("" + paramMap.get("password"));
			userVo.setRegSource(UserRegSourceType.miyShop.getCode());

			// 用户注册接口
			userVo = unifiedUserService.register(userVo);
			userCenterResultBean.setResultObject(userVo.getUid());
		} catch (Exception e) {
			log.error(
					"In the method UserCenterService.registerUser(Map<String, Object> paramMap) exists error!",
					e);
			userCenterResultBean.setResultCode(Constant.ResultCode.FILURE);
			userCenterResultBean.setResultMsg("网络繁忙请稍后再试");
		}
		return userCenterResultBean;
	}

	/**
	 * 修改用户接口
	 * 
	 * @param paramMap
	 *            key:mobile（手机号）; key:password（密码）;
	 * @return
	 */
	public UserCenterResultBean modifyUser(Map<String, Object> paramMap) {

		UserCenterResultBean userCenterResultBean = new UserCenterResultBean();
		userCenterResultBean.setResultCode(Constant.ResultCode.SUCCESS);
		userCenterResultBean.setResultMsg("成功");

		try {

			UserVo userVo = new UserVo();
			userVo.setRegSource(UserRegSourceType.miyShop.getCode());
			userVo.setUid("" + paramMap.get("uid"));
			userVo.setUserName("" + paramMap.get("userName"));
			userVo.setSex(Integer.parseInt("" + paramMap.get("sex")));

		} catch (Exception e) {
			log.error(
					"In the method UserCenterService.modifyUser(Map<String, Object> paramMap) exists error!",
					e);
			userCenterResultBean.setResultCode(Constant.ResultCode.FILURE);
			userCenterResultBean.setResultMsg("网络繁忙请稍后再试");
		}
		return userCenterResultBean;
	}

	/**
	 * 根据用户id查询用户信息接口
	 *
	 * @param user_id
	 *            （用户主键）
	 * @return
	 */
	public UserCenterResultBean queryUserByUserId(String userId) {

		UserCenterResultBean userCenterResultBean = new UserCenterResultBean();
		userCenterResultBean.setResultCode(Constant.ResultCode.SUCCESS);
		userCenterResultBean.setResultMsg("成功");

		try {

			UserVo userVo = unifiedUserService.getUserByUid(userId);
			if (null != userVo) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("sex", userVo.getSex());
				map.put("username", userVo.getUserName());
				userCenterResultBean.setResultObject(map);
			}

		} catch (Exception e) {
			log.error(
					"In the method UserCenterService.queryUserByUserId(String userId) exists error!",
					e);
			userCenterResultBean.setResultCode(Constant.ResultCode.FILURE);
			userCenterResultBean.setResultMsg("网络繁忙请稍后再试");
		}

		return userCenterResultBean;
	}

	/**
	 * 根据用户id查询用户信息接口
	 *
	 * @param user_id
	 *            （用户主键）
	 * @return
	 */
	public UserCenterResultBean queryUserByPhone(String phoneNum,
			String password) {

		UserCenterResultBean userCenterResultBean = new UserCenterResultBean();
		userCenterResultBean.setResultCode(Constant.ResultCode.SUCCESS);
		userCenterResultBean.setResultMsg("成功");

		try {

			UserVo userVo = unifiedUserService.getUserByIdentity(phoneNum,
					password);
			if (null != userVo) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("sex", userVo.getSex());
				map.put("username", userVo.getUserName());
				map.put("uid", userVo.getUid());
				userCenterResultBean.setResultObject(map);
			}

		} catch (Exception e) {
			log.error(
					"In the method UserCenterService.queryUserByUserId(String userId) exists error!",
					e);
			userCenterResultBean.setResultCode(Constant.ResultCode.FILURE);
			userCenterResultBean.setResultMsg("网络繁忙请稍后再试");
		}

		return userCenterResultBean;
	}

	/**
	 * 校验验证码接口
	 * 
	 * @param phoneNum
	 * @param vericode
	 * @return
	 */
	public UserCenterResultBean validateVeriCode(String phoneNum,
			String vericode) {

		UserCenterResultBean userCenterResultBean = new UserCenterResultBean();
		userCenterResultBean.setResultCode(Constant.ResultCode.SUCCESS);
		userCenterResultBean.setResultMsg("成功");
		try {

			// 手机号获取验证码接口
			boolean flag = verifyCodeService
					.checkVerifyCode(phoneNum, vericode);
			if (!flag) {
				userCenterResultBean
						.setResultCode(Constant.ResultCode.RESULT_THREE);
				userCenterResultBean.setResultMsg("验证码不正确");
			}

		} catch (Exception e) {
			log.error(
					"In the method UserCenterService.validateVeriCode(String phoneNum,String vericode) exists error!",
					e);
			userCenterResultBean.setResultCode(Constant.ResultCode.FILURE);
			userCenterResultBean.setResultMsg("网络繁忙请稍后再试");
		}

		return userCenterResultBean;

	}

	/**
	 * 手机号发送验证码
	 * 
	 * @param phoneNum
	 *            手机号
	 * @return
	 */
	public UserCenterResultBean phoneSendVeriCode(String phoneNum,
			SmsTemplateType smsTemplateType) {

		UserCenterResultBean userCenterResultBean = new UserCenterResultBean();
		userCenterResultBean.setResultCode(Constant.ResultCode.SUCCESS);
		userCenterResultBean.setResultMsg("成功");
		try {
			if (MoibleUtil.isPhoneNum(phoneNum)) {

				// 生成验证码
				String verifyCode = verifyCodeService.generateVerifyCode(
						phoneNum, 4, validateTime);
				log.info("手机号：" + phoneNum + "，验证码：" + verifyCode);
				System.out.println("手机号：" + phoneNum + "，验证码：" + verifyCode);
				if (null != verifyCode && StringUtils.isNotBlank(verifyCode)) {
					SmsRequest request = new SmsRequest();
					request.setSignName(SmsSignName.Miyzh);
					request.setReceiver(phoneNum);
					request.setSmsTemplate(smsTemplateType);
					// SmsTemplateType.MiyShopRegisterApply;
					// request.setSmsTemplate(SmsTemplateType.MiyShopRegisterApply);
					request.setContent("");
					request.addSmsParam("code", verifyCode);
					request.addSmsParam("maxMinute", "" + (validateTime / 60));
					request.setSmsType("normal");
					SmsResponse response = smsService.sendSms(request);
					if (!response.getErrorCode().equals(
							Constant.ResultCode.SUCCESS)) {
						throw new Exception("发送短息异常");

					}

				} else {
					throw new Exception("verifyCode is null");
				}

			} else {
				// 手机号码格式不正确
				userCenterResultBean
						.setResultCode(Constant.ResultCode.RESULT_THREE);
				userCenterResultBean.setResultMsg("手机号码格式不正确");

			}

		} catch (Exception e) {
			log.error(
					"In the method UserCenterService.phoneSendVeriCode(String phoneNum) exists error!",
					e);
			userCenterResultBean.setResultCode(Constant.ResultCode.FILURE);
			userCenterResultBean.setResultMsg("网络繁忙请稍后再试");
		}

		return userCenterResultBean;

	}
}
