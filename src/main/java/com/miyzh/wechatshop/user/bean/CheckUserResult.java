package com.miyzh.wechatshop.user.bean;

/**
 * <pre>
 * the CheckUserResult class for
 * 检查用户级别结果页
 * </pre>
 *
 * @author zhangtao 2016年7月11日
 */
public class CheckUserResult {
	
	/** 用户级别.0-未注册 1-未认证 2-已认证*/
	private String userType;
	/** 用户基础类.*/
	private UserBean userBean;
	/** 审核状态.0（noCertificate）：注册用户；2（certificateWaiting）：待审核；3（certificateFailed）：审核不通过*/
	private String status;
	/** 审核失败原因.*/
	private String certificateOpinion;

	/**
	 * <pre>
	 * the getUserType method for
	 * </pre>
	 *
	 * @return 0-未注册 1-未认证 2-已认证
	 * @author zhangtao 2016年7月11日
	 */
	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public UserBean getUserBean() {
		return userBean;
	}

	public void setUserBean(UserBean userBean) {
		this.userBean = userBean;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getCertificateOpinion() {
		return certificateOpinion;
	}

	public void setCertificateOpinion(String certificateOpinion) {
		this.certificateOpinion = certificateOpinion;
	}

}
