package com.miyzh.wechatshop.groupbuy.report;

/**
 * <pre>
 * the GroupbuyPreview class for
 * 团购预览请求参数bean
 * </pre>
 *
 * @author zhangtao 2016年7月11日
 */
public class GroupbuyPreview {
	
	/** openid .*/
	private String openid;
	/** 团购主键 .*/
	private String groupbuyid;
	/** 推荐人id .*/
	private String recommenduserid;
	/** 商品id .*/
	private String itemid;

	public String getOpenid() {
		return openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}

	public String getGroupbuyid() {
		return groupbuyid;
	}

	public void setGroupbuyid(String groupbuyid) {
		this.groupbuyid = groupbuyid;
	}

	public String getRecommenduserid() {
		return recommenduserid;
	}

	public void setRecommenduserid(String recommenduserid) {
		this.recommenduserid = recommenduserid;
	}

	public String getItemid() {
		return itemid;
	}

	public void setItemid(String itemid) {
		this.itemid = itemid;
	}

}
