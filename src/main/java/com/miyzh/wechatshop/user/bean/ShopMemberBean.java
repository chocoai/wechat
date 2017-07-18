package com.miyzh.wechatshop.user.bean;

import java.io.Serializable;

/**
 * 文件名： ShopMemberBean.java<br>
 * 描述: 会员信息<br>
 * 修改人: wumingdi<br>
 * 修改时间： 2016年7月9日 下午11:33:02 <br>
 */
public class ShopMemberBean implements Serializable {

	private static final long serialVersionUID = 8979457539055186847L;
	private int memberId;
	/** 会员组ID **/
	private String groupId;
	/** 站点ID **/
	private String websiteId;
	
	


	public int getMemberId() {
		return memberId;
	}

	public void setMemberId(int memberId) {
		this.memberId = memberId;
	}

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public String getWebsiteId() {
		return websiteId;
	}

	public void setWebsiteId(String websiteId) {
		this.websiteId = websiteId;
	}

}
