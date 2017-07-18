/**
 * <pre>
 * Copyright Digital Bay Technology Group. Co. Ltd.All Rights Reserved.
 * 
 * Original Author: 孙顺博
 * 
 * ChangeLog:
 * 2015-1-16 by 孙顺博 create
 * </pre>
 */
package com.miyzh.framework.base.action.request;

/**
 * <pre>
 * 客户端信息.
 * </pre>
 *
 * @author 孙顺博 2015-1-16
 */
public class ClientInfo {
	
	/** The edition id. */
	private String editionId;
	
	/** The soft language. */
	private String softLanguage;
	
	/** The platform id. */
	private String platformId;
	
	/** The appcationtype. */
	private String applicationtype;
	
	/**
	 * <pre>
	 * Gets the applicationtype.
	 * </pre>
	 *
	 * @author guchangpeng  2015-5-7
	 * @return the string
	 */
	public String getApplicationtype() {
		return applicationtype;
	}
	
	/**
	 * <pre>
	 * Sets the applicationtype.
	 * </pre>
	 *
	 * @author guchangpeng  2015-5-7
	 * @param applicationtype the applicationtype
	 */
	public void setApplicationtype(String applicationtype) {
		this.applicationtype = applicationtype;
	}

	/**
	 * <pre>
	 * Gets the edition id.
	 * </pre>
	 *
	 * @return the string
	 * @author 孙顺博 2015-1-16
	 */
	public String getEditionId() {
		return editionId;
	}
	
	/**
	 * <pre>
	 * Sets the edition id.
	 * </pre>
	 *
	 * @param editionId the edition id
	 * @author 孙顺博 2015-1-16
	 */
	public void setEditionId(String editionId) {
		this.editionId = editionId;
	}
	
	/**
	 * <pre>
	 * Gets the soft language.
	 * </pre>
	 *
	 * @return the string
	 * @author 孙顺博 2015-1-16
	 */
	public String getSoftLanguage() {
		return softLanguage;
	}
	
	/**
	 * <pre>
	 * Sets the soft language.
	 * </pre>
	 *
	 * @param softLanguage the soft language
	 * @author 孙顺博 2015-1-16
	 */
	public void setSoftLanguage(String softLanguage) {
		this.softLanguage = softLanguage;
	}
	
	/**
	 * <pre>
	 * Gets the platform id.
	 * </pre>
	 *
	 * @return the string
	 * @author 孙顺博 2015-1-16
	 */
	public String getPlatformId() {
		return platformId;
	}
	
	/**
	 * <pre>
	 * Sets the platform id.
	 * </pre>
	 *
	 * @param platformId the platform id
	 * @author 孙顺博 2015-1-16
	 */
	public void setPlatformId(String platformId) {
		this.platformId = platformId;
	}
}
