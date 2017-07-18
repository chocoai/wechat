package com.miyzh.wechatshop.register.service;

import com.miyzh.wechatshop.register.report.RegisterReport;

/**
 * 文件名： IRegisterService.java<br>
 * 描述: 注册<br>
 * 修改人: wumingdi<br>
 * 修改时间： 2016年7月9日 下午4:43:42 <br>
 */
public interface IRegisterService {

	/**
	 * 普通注册
	 * 
	 * @param registerReport
	 * @return
	 */
	public String executeBasicRegister(RegisterReport registerReport)
			throws Exception;

	/**
	 * 医德帮用户注册
	 * 
	 * @param registerReport
	 * @return
	 * @throws Exception
	 */
	public void executeYdbUserRegister(String userCenterKey, String phoneNum,
			String password, String openId, String clinicId, String clinicCode,
			String unionId) throws Exception;

}
