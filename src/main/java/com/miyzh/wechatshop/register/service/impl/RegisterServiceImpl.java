package com.miyzh.wechatshop.register.service.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.miyzh.framework.util.Md5PwdEncoder;
import com.miyzh.wechatshop.payUtil.service.AddressService;
import com.miyzh.wechatshop.register.report.RegisterReport;
import com.miyzh.wechatshop.register.service.IRegisterService;
import com.miyzh.wechatshop.user.bean.ChannelConcernBean;
import com.miyzh.wechatshop.user.bean.MemberBean;
import com.miyzh.wechatshop.user.bean.ShopMemberBean;
import com.miyzh.wechatshop.user.bean.ThirdUserInfoBean;
import com.miyzh.wechatshop.user.bean.UserBean;
import com.miyzh.wechatshop.user.service.IChannelConcernService;
import com.miyzh.wechatshop.user.service.IMemberService;
import com.miyzh.wechatshop.user.service.IShopMemberService;
import com.miyzh.wechatshop.user.service.IThirdUserInfoService;
import com.miyzh.wechatshop.user.service.IUserService;
import com.miyzh.wechatshop.usercenter.bean.UserCenterResultBean;
import com.miyzh.wechatshop.usercenter.service.UserCenterService;

/**
 * 文件名：RegisterServiceImpl.java<br>
 * 描述:注册<br>
 * 修改人: wumingdi<br>
 * 修改时间： 2016年7月9日 下午4:27:49 <br>
 */
@Service("registerService")
public class RegisterServiceImpl implements IRegisterService {

	protected Log log = LogFactory.getLog(getClass());

	/** 渠道关注 **/
	@Autowired
	private IChannelConcernService channelConcernService;

	/** 用户中心接口 **/
	@Autowired
	private UserCenterService userCenterService;

	/** 第三方账户 **/
	@Autowired
	private IThirdUserInfoService thirdUserInfoService;

	/** 用户管理会员 **/
	@Autowired
	private IMemberService memberService;

	/** 用户 **/
	@Autowired
	private IUserService userService;

	/** 省、市、县 **/
	@Autowired
	private AddressService adressService;

	/** 会员信息 **/
	@Autowired
	private IShopMemberService shopMemberService;

	/** 站点ID **/
	private final String WEBSITEID = "1";

	/** PC用户ID **/
	private final int PC_USER_ID = 4;

	@Override
	public void executeYdbUserRegister(String userCenterKey, String phoneNum,
			String password, String openId, String clinicId, String clinicCode,
			String unionId) throws Exception {
		try {
			/** 第三方账户 start **/
			ThirdUserInfoBean thirdUserInfoBean = new ThirdUserInfoBean();
			ThirdUserInfoBean thirdBean = new ThirdUserInfoBean();
			thirdBean.setUsercenterKey(userCenterKey);
			thirdBean.setOpenId(openId);
			thirdBean.setIfBinding(1);
			thirdBean.setAccountType(1);
			thirdBean.setIfFollow(1);
			// 更新
			thirdUserInfoService.updateThirdUserInfo(thirdBean);
			/** 第三方账户 end **/

			/** 用户 start **/
			UserBean userBean = new UserBean();
			// 诊所编码
			if (StringUtils.isNotBlank(clinicCode) && clinicCode.length() > 6) {
				userBean.setClinicCode(clinicCode);
				userBean.setUserAreaCode(clinicCode.substring(0, 6));
			}
			if (StringUtils.isNotBlank(clinicId)) {
				userBean.setClinicId(clinicId);
			}
			userBean.setPhoneNum(phoneNum);
			if (null != password && StringUtils.isNotBlank(password)) {
				userBean.setPassword(Md5PwdEncoder.encodePassword(password));
			}
			userBean.setLoginName(phoneNum);
			userBean.setUserId(userCenterKey);
			userService.addUser(userBean);
			/** 用户 end **/

			// 根据usercenterID查询会员信息
			MemberBean memberBeanResult = memberService
					.findMemberInfo(userCenterKey);
			if (null == memberBeanResult) {
				/** 用户管理会员 start **/
				MemberBean memberBean = new MemberBean();
				memberBean.setActivationCode("");
				memberBean.setIsActive(1);
				memberBean.setIsDisabled(0);
				memberBean.setWebsiteId(WEBSITEID);
				memberBean.setUsercenterId(userCenterKey);
				memberBean.setUserId(PC_USER_ID);

				memberService.addMember(memberBean);
				/** 用户管理会员 end **/

				/** 会员信息start **/
				ShopMemberBean shopMemberBean = new ShopMemberBean();
				shopMemberBean.setMemberId(memberBean.getMemberId());
				shopMemberBean.setWebsiteId(WEBSITEID);
				shopMemberBean.setGroupId("4");
				shopMemberService.addShopMember(shopMemberBean);
				/** 会员信息end **/
			}

			/** 用户中心绑定微信id **/
			unionId = thirdUserInfoBean.getUnionId();
			if (null != unionId && StringUtils.isNotBlank(unionId)) {
				userCenterService.bindUserWeiXin(userCenterKey, unionId);
			}

		} catch (Exception e) {
			log.error(
					"RegisterServiceImpl.executeYdbUserRegister(String userCenterKey, String phoneNum,String password, String openId, String clinicId, String clinicCode)",
					e);
			throw new Exception();
		}
	}

	@Override
	public String executeBasicRegister(RegisterReport registerReport)
			throws Exception {
		// 用户主键
		String userCenterKey = "";
		try {
			// 手机号
			String phoneNum = registerReport.getPhonenum();
			// 密码
			String password = registerReport.getPassword();
			// 该方法老用户和新用户故加判断
			userCenterKey = registerReport.getUserId();
			if (StringUtils.isBlank(userCenterKey)) {

				Map<String, Object> paramMap = new HashMap<String, Object>();
				paramMap.put("mobile", phoneNum);
				paramMap.put("password", password);
				// 注册
				UserCenterResultBean registerResultBean = userCenterService
						.registerUser(paramMap);
				userCenterKey = (String) registerResultBean.getResultObject();
			}

			if (StringUtils.isNotBlank(userCenterKey)) {
				/** 第三方账户 start **/
				ThirdUserInfoBean thirdUserInfoBean = thirdUserInfoService
						.findByOpenId(registerReport.getOpenid(), null);
				ThirdUserInfoBean thirdBean = new ThirdUserInfoBean();
				thirdBean.setUsercenterKey(userCenterKey);
				thirdBean.setOpenId(registerReport.getOpenid());
				thirdBean.setIfBinding(1);
				thirdBean.setAccountType(1);
				thirdBean.setIfFollow(1);
				if (null == thirdUserInfoBean) {
					// 新增
					thirdUserInfoService.addThirdUserInfo(thirdBean);
				} else {
					// 更新
					thirdUserInfoService.updateThirdUserInfo(thirdBean);
				}
				/** 第三方账户 end **/

				/** 用户 start **/
				UserBean userBean = new UserBean();
				// 诊所编码
				String clinicCode = registerReport.getCliniccode();
				if (StringUtils.isNotBlank(clinicCode)
						&& clinicCode.length() > 6) {
					userBean.setClinicCode(clinicCode);
					userBean.setUserAreaCode(clinicCode.substring(0, 6));
				}
				// else {
				// 区域名称
				// String areaName = MoibleUtil.getMobileAttribution(phoneNum);
				// 区域信息
				// Address addressBean = adressService.findByName(areaName);
				// if (null != addressBean) {
				// userBean.setUserAreaCode("" + addressBean.getCode());
				// }

				// }
				if (StringUtils.isNotBlank(registerReport.getClinicid())) {
					userBean.setClinicId(registerReport.getClinicid());

				}

				userBean.setPhoneNum(phoneNum);
				userBean.setPassword(Md5PwdEncoder.encodePassword(password));
				userBean.setLoginName(phoneNum);
				userBean.setUserId(userCenterKey);
				userService.addUser(userBean);
				/** 用户 end **/

				// 根据usercenterID查询会员信息
				MemberBean memberBeanResult = memberService
						.findMemberInfo(userCenterKey);
				if (null == memberBeanResult) {
					/** 用户管理会员 start **/
					MemberBean memberBean = new MemberBean();
					memberBean.setActivationCode("");
					memberBean.setIsActive(1);
					memberBean.setIsDisabled(0);
					memberBean.setWebsiteId(WEBSITEID);
					memberBean.setUsercenterId(userCenterKey);
					memberBean.setUserId(PC_USER_ID);

					memberService.addMember(memberBean);
					/** 用户管理会员 end **/

					/** 会员信息start **/
					ShopMemberBean shopMemberBean = new ShopMemberBean();
					shopMemberBean.setMemberId(memberBean.getMemberId());
					shopMemberBean.setWebsiteId(WEBSITEID);
					shopMemberBean.setGroupId("4");
					shopMemberService.addShopMember(shopMemberBean);
					/** 会员信息end **/
				}

				/** 推荐关系 start **/
				// 推荐人ID
				String recommedId = registerReport.getRecommedid();
				if (StringUtils.isNotBlank(recommedId)) {
					/** 关注类型,1-G码 2-T码 **/
					Integer concernType = 2;
					ChannelConcernBean channelConcernBean = new ChannelConcernBean();
					channelConcernBean.setId(UUID.randomUUID().toString());
					channelConcernBean.setUserKey(userCenterKey);
					channelConcernBean.setRecommedUserKey(recommedId);
					channelConcernBean.setConcernType(concernType);
					channelConcernBean.setOpenId(registerReport.getOpenid());
					channelConcernService.addChannelConcern(channelConcernBean);
				}
				/** 推荐关系 end **/
				/** 用户中心绑定微信id **/
				String unionId = thirdUserInfoBean.getUnionId();
				if (null != unionId && StringUtils.isNotBlank(unionId)) {
					userCenterService.bindUserWeiXin(userCenterKey, unionId);
				}
			}

		} catch (Exception e) {
			log.error("RegisterServiceImpl.executeBasicRegister", e);
			throw new Exception();
		}

		return userCenterKey;
	}

	// @Override
	// public String executeYdbUserRegister(RegisterReport registerReport)
	// throws Exception {
	// // 用户主键
	// String userCenterKey = "";
	// try {
	// // 手机号
	// String phoneNum = registerReport.getPhonenum();
	// // 密码
	// String password = registerReport.getPassword();
	// userCenterKey = registerReport.getUserId();
	// if (StringUtils.isNotBlank(userCenterKey)) {
	// /** 第三方账户 start **/
	// ThirdUserInfoBean thirdUserInfoBean = thirdUserInfoService
	// .findByOpenId(registerReport.getOpenid(), null);
	// ThirdUserInfoBean thirdBean = new ThirdUserInfoBean();
	// thirdBean.setUsercenterKey(userCenterKey);
	// thirdBean.setOpenId(registerReport.getOpenid());
	// thirdBean.setIfBinding(1);
	// thirdBean.setAccountType(1);
	// thirdBean.setIfFollow(1);
	// if (null == thirdUserInfoBean) {
	// // 新增
	// thirdUserInfoService.addThirdUserInfo(thirdBean);
	// } else {
	// // 更新
	// thirdUserInfoService.updateThirdUserInfo(thirdBean);
	// }
	// /** 第三方账户 end **/
	//
	// /** 用户 start **/
	// UserBean userBean = new UserBean();
	// // 诊所编码
	// String clinicCode = registerReport.getCliniccode();
	// if (StringUtils.isNotBlank(clinicCode)
	// && clinicCode.length() > 6) {
	// userBean.setClinicCode(clinicCode);
	// userBean.setUserAreaCode(clinicCode.substring(0, 6));
	// } else {
	// // 区域名称
	// String areaName = MoibleUtil.getMobileAttribution(phoneNum);
	// // 区域信息
	// Address addressBean = adressService.findByName(areaName);
	// if (null != addressBean) {
	// userBean.setUserAreaCode("" + addressBean.getCode());
	// }
	//
	// }
	// if (StringUtils.isNotBlank(registerReport.getClinicid())) {
	// userBean.setClinicId(registerReport.getClinicid());
	//
	// }
	// userBean.setPhoneNum(phoneNum);
	// userBean.setPassword(Md5PwdEncoder.encodePassword(password));
	// userBean.setLoginName(phoneNum);
	// userBean.setUserId(userCenterKey);
	// userService.addUser(userBean);
	// /** 用户 end **/
	//
	// /** 用户管理会员 start **/
	// MemberBean memberBean = new MemberBean();
	// memberBean.setActivationCode("");
	// memberBean.setIsActive(1);
	// memberBean.setIsDisabled(0);
	// memberBean.setWebsiteId(WEBSITEID);
	// memberBean.setUsercenterId(userCenterKey);
	// memberBean.setUserId(PC_USER_ID);
	//
	// memberService.addMember(memberBean);
	// /** 用户管理会员 end **/
	//
	// /** 推荐关系 start **/
	// // 推荐人ID
	// String recommedId = registerReport.getRecommedid();
	// if (StringUtils.isNotBlank(recommedId)) {
	// /** 关注类型,1-G码 2-T码 **/
	// Integer concernType = 2;
	// ChannelConcernBean channelConcernBean = new ChannelConcernBean();
	// channelConcernBean.setId(UUID.randomUUID().toString());
	// channelConcernBean.setUserKey(userCenterKey);
	// channelConcernBean.setRecommedUserKey(recommedId);
	// channelConcernBean.setConcernType(concernType);
	// channelConcernBean.setOpenId(registerReport.getOpenid());
	// channelConcernService.addChannelConcern(channelConcernBean);
	// }
	// /** 推荐关系 end **/
	//
	// /** 会员信息start **/
	// ShopMemberBean shopMemberBean = new ShopMemberBean();
	// shopMemberBean.setMemberId(memberBean.getMemberId());
	// shopMemberBean.setWebsiteId(WEBSITEID);
	// shopMemberBean.setGroupId("4");
	// shopMemberService.addShopMember(shopMemberBean);
	// /** 会员信息end **/
	//
	// }
	//
	// } catch (Exception e) {
	// log.error(e);
	// throw new Exception();
	// }
	//
	// return userCenterKey;
	// }

}
