package com.miyzh.wechatshop.web.bean;


import java.util.List;

/**
 * 文件名: WebPublic.java<br>
 * 版权：Copyright 2014 Digital Bay Technology Group. Co. Ltd. All Rights Reserved. <br>
 * 描述: web端调用接口实体类<br>
 * 修改人: guchangpeng<br>
 * 修改时间：2014-04-25<br>
 * 修改内容：新增<br>
 */
public class WebPublic {
	/** 活动名称 */
	private String activityName;
	/** 卡号 */
	private String cardNum;
	/** 卡密 */
	private String cardPwd;
	/*** 商品名称 */
	private String goodsName;
	/** 快递名称 **/
	private String express;
	/** 手机号 */
	private String mobile;

	/** 删除类型 **/
	private String removeType;
	/** 缓存keylist **/
	private List<String> cacheKeyList;

	/** 界面上用到**/
	private String keysStr;
	
	/** 界面上用到**/
	private String keyname;
	
	/** 界面上用到**/
	private String keyvalue;
	
	
	public String getKeysStr() {
		return keysStr;
	}
	public void setKeysStr(String keysStr) {
		this.keysStr = keysStr;
	}
	public String getRemoveType() {
		return removeType;
	}
	public void setRemoveType(String removeType) {
		this.removeType = removeType;
	}
	public List<String> getCacheKeyList() {
		return cacheKeyList;
	}
	public void setCacheKeyList(List<String> cacheKeyList) {
		this.cacheKeyList = cacheKeyList;
	}
	public String getCardPwd() {
		return cardPwd;
	}
	public void setCardPwd(String cardPwd) {
		this.cardPwd = cardPwd;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getActivityName() {
		return activityName;
	}
	public void setActivityName(String activityName) {
		this.activityName = activityName;
	}
	public String getCardNum() {
		return cardNum;
	}
	public void setCardNum(String cardNum) {
		this.cardNum = cardNum;
	}
	public String getGoodsName() {
		return goodsName;
	}
	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}
	public String getExpress() {
		return express;
	}
	public void setExpress(String express) {
		this.express = express;
	}
	public String getKeyname() {
		return keyname;
	}
	public void setKeyname(String keyname) {
		this.keyname = keyname;
	}
	public String getKeyvalue() {
		return keyvalue;
	}
	public void setKeyvalue(String keyvalue) {
		this.keyvalue = keyvalue;
	}
	
	
}
