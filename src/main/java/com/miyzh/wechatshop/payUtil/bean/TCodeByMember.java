package com.miyzh.wechatshop.payUtil.bean;



import com.miyzh.framework.base.bean.BaseBean;

public class TCodeByMember extends BaseBean {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private long id;
	private long groupId;
	private String groupName;
	private long memberId;
	private String groupStartdate;
	private String groupEnddate;
	private String urlPath;
	private String urlText;
	private String fileName;
	/** 是否有效 0 未结束   1 已经结束**/
	private  String  groupIfEnd;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getGroupId() {
		return groupId;
	}
	public void setGroupId(long groupId) {
		this.groupId = groupId;
	}
	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	public long getMemberId() {
		return memberId;
	}
	public void setMemberId(long memberId) {
		this.memberId = memberId;
	}
	public String getUrlPath() {
		return urlPath;
	}
	public void setUrlPath(String urlPath) {
		this.urlPath = urlPath;
	}
	public String getGroupStartdate() {
		return groupStartdate;
	}
	public void setGroupStartdate(String groupStartdate) {
		this.groupStartdate = groupStartdate;
	}
	public String getGroupEnddate() {
		return groupEnddate;
	}
	public void setGroupEnddate(String groupEnddate) {
		this.groupEnddate = groupEnddate;
	}
	public String getGroupIfEnd() {
		return groupIfEnd;
	}
	public void setGroupIfEnd(String groupIfEnd) {
		this.groupIfEnd = groupIfEnd;
	}
	public String getUrlText() {
		return urlText;
	}
	public void setUrlText(String urlText) {
		this.urlText = urlText;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
	
	
}
