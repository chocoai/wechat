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
 * 基础请求报文信息.
 * </pre>
 *
 */
public class BaseRequestReport {

	/** The protocol. */
	private String protocol = "";

	/** The command type. */
	private String commandtype = "";

	/** The request time. */
	private String requesttime = "";

	private String applicationType = "";

	public String getApplicationType() {
		return applicationType;
	}

	public void setApplicationType(String applicationType) {
		this.applicationType = applicationType;
	}

	public String getCommandtype() {
		return commandtype;
	}

	public void setCommandtype(String commandtype) {
		this.commandtype = commandtype;
	}

	public String getRequesttime() {
		return requesttime;
	}

	public void setRequesttime(String requesttime) {
		this.requesttime = requesttime;
	}

	/**
	 * <pre>
	 * Gets the protocol.
	 * </pre>
	 *
	 * @return the string
	 */
	public String getProtocol() {
		return protocol;
	}

	/**
	 * <pre>
	 * Sets the protocol.
	 * </pre>
	 *
	 * @param protocol
	 *            the protocol
	 */
	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}

}
