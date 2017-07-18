package com.miyzh.wechatshop.payUtil.bean;

import java.util.Date;

import com.miyzh.framework.base.bean.BaseBean;

public class CoreMember extends BaseBean {
	
	   /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String memberId;
	    
	    private Date createTime;
	    private Boolean disabled;
		private Boolean active;
		private String activationCode;
	    private Long userId;
	    private Long website;
	
	    private String username;
	    private String email;
	    private String password;
	    private Long loginCount;
	    private String registerIp;
	    private Date lastLoginTime;
	    private String lastLoginIp;
	    private Date currentLoginTime;
	    private String currentLoginIp;
	    private String resetKey;
	    private String resetPwd;
	    private String ucenterId;
	    private String clinicCode;
	    private String userType;
	    private String clinicName;
	    private String doctorName;
	    private String doctorPhone;
	    private String tgCode;
		public Date getCreateTime() {
			return createTime;
		}
		public void setCreateTime(Date createTime) {
			this.createTime = createTime;
		}
		public Boolean getDisabled() {
			return disabled;
		}
		public void setDisabled(Boolean disabled) {
			this.disabled = disabled;
		}
		public Boolean getActive() {
			return active;
		}
		public void setActive(Boolean active) {
			this.active = active;
		}
		public String getActivationCode() {
			return activationCode;
		}
		public void setActivationCode(String activationCode) {
			this.activationCode = activationCode;
		}
		public Long getUserId() {
			return userId;
		}
		public void setUserId(Long userId) {
			this.userId = userId;
		}
		public Long getWebsite() {
			return website;
		}
		public void setWebsite(Long website) {
			this.website = website;
		}
		public String getUsername() {
			return username;
		}
		public void setUsername(String username) {
			this.username = username;
		}
		public String getEmail() {
			return email;
		}
		public void setEmail(String email) {
			this.email = email;
		}
		public String getPassword() {
			return password;
		}
		public void setPassword(String password) {
			this.password = password;
		}
		public Long getLoginCount() {
			return loginCount;
		}
		public void setLoginCount(Long loginCount) {
			this.loginCount = loginCount;
		}
		public String getRegisterIp() {
			return registerIp;
		}
		public void setRegisterIp(String registerIp) {
			this.registerIp = registerIp;
		}
		public Date getLastLoginTime() {
			return lastLoginTime;
		}
		public void setLastLoginTime(Date lastLoginTime) {
			this.lastLoginTime = lastLoginTime;
		}
		public String getLastLoginIp() {
			return lastLoginIp;
		}
		public void setLastLoginIp(String lastLoginIp) {
			this.lastLoginIp = lastLoginIp;
		}
		public Date getCurrentLoginTime() {
			return currentLoginTime;
		}
		public void setCurrentLoginTime(Date currentLoginTime) {
			this.currentLoginTime = currentLoginTime;
		}
		public String getCurrentLoginIp() {
			return currentLoginIp;
		}
		public void setCurrentLoginIp(String currentLoginIp) {
			this.currentLoginIp = currentLoginIp;
		}
		public String getResetKey() {
			return resetKey;
		}
		public void setResetKey(String resetKey) {
			this.resetKey = resetKey;
		}
		public String getResetPwd() {
			return resetPwd;
		}
		public void setResetPwd(String resetPwd) {
			this.resetPwd = resetPwd;
		}
		public String getUcenterId() {
			return ucenterId;
		}
		public void setUcenterId(String ucenterId) {
			this.ucenterId = ucenterId;
		}
		public String getClinicCode() {
			return clinicCode;
		}
		public void setClinicCode(String clinicCode) {
			this.clinicCode = clinicCode;
		}
		public String getUserType() {
			return userType;
		}
		public void setUserType(String userType) {
			this.userType = userType;
		}
		public String getClinicName() {
			return clinicName;
		}
		public void setClinicName(String clinicName) {
			this.clinicName = clinicName;
		}
		public String getDoctorName() {
			return doctorName;
		}
		public void setDoctorName(String doctorName) {
			this.doctorName = doctorName;
		}
		public String getDoctorPhone() {
			return doctorPhone;
		}
		public void setDoctorPhone(String doctorPhone) {
			this.doctorPhone = doctorPhone;
		}
		public String getTgCode() {
			return tgCode;
		}
		public void setTgCode(String tgCode) {
			this.tgCode = tgCode;
		}
		public String getMemberId() {
			return memberId;
		}
		public void setMemberId(String memberId) {
			this.memberId = memberId;
		}
	    
	    
	    
	    
}
