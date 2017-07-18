package com.miyzh.wechatshop.clinic.service;

import com.miyzh.wechatshop.clinic.report.ClinicReport;

/**
 * 文件名： ClinicService.java<br>
 * 描述: 诊所<br>
 * 修改人: wumingdi<br>
 * 修改时间： 2016年7月12日 <br>
 */
public interface ClinicService {

	/**
	 * 完善诊所信息
	 * 
	 * @param clinicReport
	 * @return
	 */
	public void executePerfectedClinicInfo(ClinicReport clinicReport)
			throws Exception;

	/**
	 * 认证诊所
	 * 
	 * @param clinicReport
	 * @return
	 * @throws Exception
	 */
	public String executeClinicQualiRegister(ClinicReport clinicReport)
			throws Exception;

	/**
	 * 修改认证诊所
	 * 
	 * @param clinicReport
	 * @return
	 * @throws Exception
	 */
	public void updateClinicQualiRegister(ClinicReport clinicReport)
			throws Exception;

}
