package com.miyzh.wechatshop.clinic.report;

import java.io.Serializable;

import com.miyzh.wechatshop.register.report.RegisterReport;

/**
 * 文件名： ClinicReport.java<br>
 * 描述: 诊所<br>
 * 修改人: wumingdi<br>
 * 修改时间： 2016年7月15日 <br>
 */
public class ClinicReport implements Serializable {

	private static final long serialVersionUID = 2099643813354677250L;

	private String openid;

	/** 医德帮用户登录名 **/
	private String ydbloginname;
	/** 医德帮用户手机号 **/
	private String ydbphonenum;

	/** 诊所编码 **/
	private String cliniccode;

	/** 诊所ID **/
	private String clinicid;

	/** 医德帮登录密码 **/
	private String ydbloginpassword;

	/** 用户主键 **/
	private String userkey;

	/** 经营范围：1中医2西医3中西医结合 **/
	private Integer clinicscope;

	/** 诊所人员规模1-1~3人，2-4~6人,3-7~9人，4-10人以上 **/
	private Integer clinicscale;

	/** 诊所经营性质0私营1公立2混营 **/
	private Integer clinicnature;

	/**
	 * 诊所面积 0-100平方米以下， 1-100~200平方米， 2-200~400平方米， 3-400~600平方米， 4-600~800平方米，
	 * 5-800平方米以上
	 **/
	private Integer clinicarea;

	/** 所属科别：0全科1专科 **/
	private Integer clinicdivision;

	/** 科别描述 **/
	private String divisiondescription;

	/** 审核结果，0（noCertificate）：注册用户；1（certificated）：认证用户；2（certificateWaiting）：待审核；3（certificateFailed）：审核不通过； **/
	private Integer checkresult;
	
	/** 审核失败结果**/
	private String checkMsg;

	/** 诊所名称 **/
	private String clinicname;

	/** 省 **/
	private String province;
	/** 市 **/
	private String city;
	/** 区/县 **/
	private String county;
	/** 详细地址 **/
	private String clinicaddress;
	/** 医疗机构执业许可证URL **/
	private String certificatecardurl;
	/** 执业医师资格证url **/
	private String doctorseniorityurl;

	/** 手机号 **/
	private String phonenum;

	/** 注册对象 **/
	private RegisterReport register = new RegisterReport();
	
	

	public String getCheckMsg() {
		return checkMsg;
	}

	public void setCheckMsg(String checkMsg) {
		this.checkMsg = checkMsg;
	}

	public String getOpenid() {
		return openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}

	public String getPhonenum() {
		return phonenum;
	}

	public void setPhonenum(String phonenum) {
		this.phonenum = phonenum;
	}

	public String getClinicname() {
		return clinicname;
	}

	public void setClinicname(String clinicname) {
		this.clinicname = clinicname;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCounty() {
		return county;
	}

	public void setCounty(String county) {
		this.county = county;
	}

	
	

	public String getClinicaddress() {
		return clinicaddress;
	}

	public void setClinicaddress(String clinicaddress) {
		this.clinicaddress = clinicaddress;
	}

	public String getCertificatecardurl() {
		return certificatecardurl;
	}

	public void setCertificatecardurl(String certificatecardurl) {
		this.certificatecardurl = certificatecardurl;
	}

	public String getDoctorseniorityurl() {
		return doctorseniorityurl;
	}

	public void setDoctorseniorityurl(String doctorseniorityurl) {
		this.doctorseniorityurl = doctorseniorityurl;
	}

	public Integer getClinicscope() {
		return clinicscope;
	}

	public void setClinicscope(Integer clinicscope) {
		this.clinicscope = clinicscope;
	}

	public Integer getClinicscale() {
		return clinicscale;
	}

	public void setClinicscale(Integer clinicscale) {
		this.clinicscale = clinicscale;
	}

	public Integer getClinicnature() {
		return clinicnature;
	}

	public void setClinicnature(Integer clinicnature) {
		this.clinicnature = clinicnature;
	}

	public Integer getClinicarea() {
		return clinicarea;
	}

	public void setClinicarea(Integer clinicarea) {
		this.clinicarea = clinicarea;
	}

	public Integer getClinicdivision() {
		return clinicdivision;
	}

	public void setClinicdivision(Integer clinicdivision) {
		this.clinicdivision = clinicdivision;
	}

	public String getDivisiondescription() {
		return divisiondescription;
	}

	public void setDivisiondescription(String divisiondescription) {
		this.divisiondescription = divisiondescription;
	}

	public Integer getCheckresult() {
		return checkresult;
	}

	public void setCheckresult(Integer checkresult) {
		this.checkresult = checkresult;
	}

	public String getClinicid() {
		return clinicid;
	}

	public void setClinicid(String clinicid) {
		this.clinicid = clinicid;
	}

	public String getUserkey() {
		return userkey;
	}

	public void setUserkey(String userkey) {
		this.userkey = userkey;
	}

	public String getCliniccode() {
		return cliniccode;
	}

	public void setCliniccode(String cliniccode) {
		this.cliniccode = cliniccode;
	}

	public String getYdbloginpassword() {
		return ydbloginpassword;
	}

	public void setYdbloginpassword(String ydbloginpassword) {
		this.ydbloginpassword = ydbloginpassword;
	}

	public RegisterReport getRegister() {
		return register;
	}

	public void setRegister(RegisterReport register) {
		this.register = register;
	}

	public String getYdbloginname() {
		return ydbloginname;
	}

	public void setYdbloginname(String ydbloginname) {
		this.ydbloginname = ydbloginname;
	}

	public String getYdbphonenum() {
		return ydbphonenum;
	}

	public void setYdbphonenum(String ydbphonenum) {
		this.ydbphonenum = ydbphonenum;
	}

}
