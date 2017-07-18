package com.miyzh.wechatshop.clinic.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.miyzh.framework.base.Constant;
import com.miyzh.framework.util.PropertiesUtil;
import com.miyzh.wechatshop.clinic.report.ClinicReport;
import com.miyzh.wechatshop.clinic.service.ClinicService;
import com.miyzh.wechatshop.payUtil.service.AddressService;
import com.miyzh.wechatshop.user.bean.UserBean;
import com.miyzh.wechatshop.user.service.IUserService;
import com.miyzh.wechatshop.usercenter.bean.UserCenterResultBean;
import com.miyzh.wechatshop.usercenter.service.UserCenterService;

@Service("clinicServiceImpl")
public class ClinicServiceImpl implements ClinicService {

	// 阿里云oss上传文件路径
	private final String OOS_DOWN_FILE_PATH = PropertiesUtil
			.getPropertyValue("aliyun.oss.myzh.wenwen.external.hostname");

	protected Log log = LogFactory.getLog(getClass());

	/** 用户中心接口 **/
	@Autowired
	private UserCenterService userCenterService;

	/** 用户 **/
	@Autowired
	private IUserService userService;

	/** 地区 **/
	@Autowired
	private AddressService addressService;

	@Override
	public void executePerfectedClinicInfo(ClinicReport clinicReport)
			throws Exception {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("clinicId", clinicReport.getClinicid());
		paramMap.put("clinicNature", clinicReport.getClinicnature());
		paramMap.put("divisionDescription",
				clinicReport.getDivisiondescription());
		paramMap.put("clinicDivision", clinicReport.getClinicdivision());
		paramMap.put("clinicScale", clinicReport.getClinicscale());
		paramMap.put("clinicArea", clinicReport.getClinicarea());
		paramMap.put("clinicScope", clinicReport.getClinicscope());
		paramMap.put("uid", clinicReport.getUserkey());
		// 完善诊所信息
		userCenterService.modifyClinic(paramMap, false);
		paramMap.put("userName", clinicReport.getRegister().getUsername());
		paramMap.put("sex", clinicReport.getRegister().getSex());
		// 完善用户信息
		userCenterService.modifyUser(paramMap);

		UserBean userBean = new UserBean();
		userBean.setUserId(clinicReport.getUserkey());
		userBean.setUserName(clinicReport.getRegister().getUsername());
		userBean.setSex(clinicReport.getRegister().getSex());
		userBean.setOpenId(clinicReport.getOpenid());
		userService.updateUser(userBean);

	}

	@Override
	public String executeClinicQualiRegister(ClinicReport clinicReport)
			throws Exception {
		// 诊所ID
		String clinicId = "";
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("clinicName", clinicReport.getClinicname());
		map.put("clinicMobile", clinicReport.getPhonenum());
		map.put("province", clinicReport.getProvince());
		map.put("city", clinicReport.getCity());
		map.put("district", clinicReport.getCounty());
		map.put("clinicAddress", clinicReport.getClinicaddress());
		map.put("clcCertificateCardPic", OOS_DOWN_FILE_PATH + "/"
				+ clinicReport.getCertificatecardurl());
		if (StringUtils.isNotBlank(clinicReport.getDoctorseniorityurl())) {
			map.put("registerUserCertificateCardPic", OOS_DOWN_FILE_PATH + "/"
					+ clinicReport.getDoctorseniorityurl());
		}
		UserCenterResultBean userCenterResultBean = userCenterService
				.registerClinic(clinicReport.getUserkey(), map);
		clinicId = userCenterResultBean.getResultObject().toString();
		return clinicId;
	}

	@Override
	public void updateClinicQualiRegister(ClinicReport clinicReport)
			throws Exception {

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("uid", clinicReport.getUserkey());
		map.put("clinicId", clinicReport.getClinicid());
		map.put("clinicName", clinicReport.getClinicname());
		map.put("clinicMobile", clinicReport.getPhonenum());
		map.put("province", clinicReport.getProvince());
		map.put("city", clinicReport.getCity());
		map.put("district", clinicReport.getCounty());
		map.put("clinicAddress", clinicReport.getClinicaddress());

		if (clinicReport.getCertificatecardurl().indexOf("http") != -1) {

			map.put("clcCertificateCardPic",
					clinicReport.getCertificatecardurl());

		} else {
			map.put("clcCertificateCardPic", OOS_DOWN_FILE_PATH + "/"
					+ clinicReport.getCertificatecardurl());

		}
		if (StringUtils.isNotBlank(clinicReport.getDoctorseniorityurl())) {

			if (clinicReport.getDoctorseniorityurl().indexOf("http") != -1) {

				map.put("registerUserCertificateCardPic",
						clinicReport.getDoctorseniorityurl());

			} else {
				map.put("registerUserCertificateCardPic", OOS_DOWN_FILE_PATH
						+ "/" + clinicReport.getDoctorseniorityurl());

			}

		}
		UserCenterResultBean userCenterResultBean = userCenterService
				.modifyClinic(map, true);

		if (!userCenterResultBean.getResultCode().equals(
				Constant.ResultCode.SUCCESS)) {
			throw new Exception("修改诊所信息失败");

		}

	}
}
