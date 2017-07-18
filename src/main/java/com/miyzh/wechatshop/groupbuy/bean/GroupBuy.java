package com.miyzh.wechatshop.groupbuy.bean;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.google.common.collect.Lists;

public class GroupBuy implements Serializable {
	private static final long serialVersionUID = 5366983010252189612L;
	private Long id;
	/** 团购期数.*/
	private Integer period;
	/** 团购起始日期.*/
	private Date startTime;
	/** 团购终止日期.*/
	private Date endTime;
	private String lastEndTime;// endTime+1天，计时用
	/** 团购标题.*/
	private String title;
	private String description;
	/** 组团最低需要数量.*/
	private Integer minGroupQuantity = 0;
	/** 组团最低需要的会员数*/
	private Integer minGroupMember = 0;
	/** 每个会员需要最少购买的数量（团购商品）*/
	private Integer minBuyQuantity = 0;
	/** 每个会员需要最少购买的金额（团购商品）*/
	private Double minBuyMoney;
	/** 团购总人数*/
	private Integer totalGroupNumber = 0;
	/** 团购状态是否有效*/
	private String state;
	private Date createTime;
	private Date updateTime;
	/** 逻辑删除标识*/
	private String delFlag;
	/** 大海报图片*/
	private String posterUrl;
	/** 二维码图片*/
	private String twoBarCodeUrl;
	/** 小海报图片*/
	private String smallPosterUrl;
	private Integer viewCount = 0;
	/** 团购展示图.*/
	private Integer cdfined1;
	private Integer cdfined2;
	/** 团购人数.*/
	private String cdfined3;
	private String cdfined4;
	/** 团购发起方*/
	private String sponsors;
	/** 非团购发起方是否可见 */
	private String isVisible;
	/** 非团购区域是否可点 */
	private String isClick;
	/** 是否限制参团区域*/
	private String isRestrictArea;
	/** 团购剩余天数*/
	private Integer remainderDay;
	/** 省份区域限制* */
	private String restrictProvince;
	/** 区县域限制 *  */
	private String restrictCountry;
	/** 团购进度*/
	private String groupCondition;
	private String groupBuyProgress;
	/** 成团说明 */
	private String groupDescription;
	/** 团购包邮 */
	private String isPostage;
	private boolean loginMemberIsClick = true;
	/** 概览页可见级别*/
	private String overviewVisibleLevel;
	/** 预览页可见级别*/
	private String previewVisibleLevel;
	/** 详情页可见级别*/
	private String detailVisibleLevel;
	/** 剩余时间.*/
	private String restTime;
	/** 微信团购列表图 */
	private String wtImgurl;
	/** 团购进度 */
	private String schedule;
	/** 节省金额 */
	private String cheapmoney;
	/** 剩余开始时间.*/
	private String restStartTime;
	/** 限制区域名称.*/
	private String countryName;
	/** 商品信息.*/
	private List<GroupBuyItem> itemList = Lists.newArrayList();// 团购商品表
	/** js结束时间.*/
	private String endDateForJs;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getPeriod() {
		return period;
	}

	public void setPeriod(Integer period) {
		this.period = period;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public String getLastEndTime() {
		return lastEndTime;
	}

	public void setLastEndTime(String lastEndTime) {
		this.lastEndTime = lastEndTime;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getMinGroupQuantity() {
		return minGroupQuantity;
	}

	public void setMinGroupQuantity(Integer minGroupQuantity) {
		this.minGroupQuantity = minGroupQuantity;
	}

	public Integer getMinGroupMember() {
		return minGroupMember;
	}

	public void setMinGroupMember(Integer minGroupMember) {
		this.minGroupMember = minGroupMember;
	}

	public Integer getMinBuyQuantity() {
		return minBuyQuantity;
	}

	public void setMinBuyQuantity(Integer minBuyQuantity) {
		this.minBuyQuantity = minBuyQuantity;
	}

	public Double getMinBuyMoney() {
		return minBuyMoney;
	}

	public void setMinBuyMoney(Double minBuyMoney) {
		this.minBuyMoney = minBuyMoney;
	}

	public Integer getTotalGroupNumber() {
		return totalGroupNumber;
	}

	public void setTotalGroupNumber(Integer totalGroupNumber) {
		this.totalGroupNumber = totalGroupNumber;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getDelFlag() {
		return delFlag;
	}

	public void setDelFlag(String delFlag) {
		this.delFlag = delFlag;
	}

	public String getPosterUrl() {
		return posterUrl;
	}

	public void setPosterUrl(String posterUrl) {
		this.posterUrl = posterUrl;
	}

	public String getTwoBarCodeUrl() {
		return twoBarCodeUrl;
	}

	public void setTwoBarCodeUrl(String twoBarCodeUrl) {
		this.twoBarCodeUrl = twoBarCodeUrl;
	}

	public String getSmallPosterUrl() {
		return smallPosterUrl;
	}

	public void setSmallPosterUrl(String smallPosterUrl) {
		this.smallPosterUrl = smallPosterUrl;
	}

	public Integer getViewCount() {
		return viewCount;
	}

	public void setViewCount(Integer viewCount) {
		this.viewCount = viewCount;
	}

	public Integer getCdfined1() {
		return cdfined1;
	}

	public void setCdfined1(Integer cdfined1) {
		this.cdfined1 = cdfined1;
	}

	public Integer getCdfined2() {
		return cdfined2;
	}

	public void setCdfined2(Integer cdfined2) {
		this.cdfined2 = cdfined2;
	}

	public String getCdfined3() {
		return cdfined3;
	}

	public void setCdfined3(String cdfined3) {
		this.cdfined3 = cdfined3;
	}

	public String getCdfined4() {
		return cdfined4;
	}

	public void setCdfined4(String cdfined4) {
		this.cdfined4 = cdfined4;
	}

	public String getSponsors() {
		return sponsors;
	}

	public void setSponsors(String sponsors) {
		this.sponsors = sponsors;
	}

	public String getIsVisible() {
		return isVisible;
	}

	public void setIsVisible(String isVisible) {
		this.isVisible = isVisible;
	}

	public String getIsClick() {
		return isClick;
	}

	public void setIsClick(String isClick) {
		this.isClick = isClick;
	}

	public String getIsRestrictArea() {
		return isRestrictArea;
	}

	public void setIsRestrictArea(String isRestrictArea) {
		this.isRestrictArea = isRestrictArea;
	}

	public Integer getRemainderDay() {
		return remainderDay;
	}

	public void setRemainderDay(Integer remainderDay) {
		this.remainderDay = remainderDay;
	}

	public String getRestrictProvince() {
		return restrictProvince;
	}

	public void setRestrictProvince(String restrictProvince) {
		this.restrictProvince = restrictProvince;
	}

	public String getRestrictCountry() {
		return restrictCountry;
	}

	public void setRestrictCountry(String restrictCountry) {
		this.restrictCountry = restrictCountry;
	}

	public String getGroupCondition() {
		return groupCondition;
	}

	public void setGroupCondition(String groupCondition) {
		this.groupCondition = groupCondition;
	}

	public String getGroupBuyProgress() {
		return groupBuyProgress;
	}

	public void setGroupBuyProgress(String groupBuyProgress) {
		this.groupBuyProgress = groupBuyProgress;
	}

	public String getGroupDescription() {
		return groupDescription;
	}

	public void setGroupDescription(String groupDescription) {
		this.groupDescription = groupDescription;
	}

	public String getIsPostage() {
		return isPostage;
	}

	public void setIsPostage(String isPostage) {
		this.isPostage = isPostage;
	}

	public boolean isLoginMemberIsClick() {
		return loginMemberIsClick;
	}

	public void setLoginMemberIsClick(boolean loginMemberIsClick) {
		this.loginMemberIsClick = loginMemberIsClick;
	}

	public String getOverviewVisibleLevel() {
		return overviewVisibleLevel;
	}

	public void setOverviewVisibleLevel(String overviewVisibleLevel) {
		this.overviewVisibleLevel = overviewVisibleLevel;
	}

	public String getPreviewVisibleLevel() {
		return previewVisibleLevel;
	}

	public void setPreviewVisibleLevel(String previewVisibleLevel) {
		this.previewVisibleLevel = previewVisibleLevel;
	}

	public String getDetailVisibleLevel() {
		return detailVisibleLevel;
	}

	public void setDetailVisibleLevel(String detailVisibleLevel) {
		this.detailVisibleLevel = detailVisibleLevel;
	}

	public List<GroupBuyItem> getItemList() {
		return itemList;
	}

	public void setItemList(List<GroupBuyItem> itemList) {
		this.itemList = itemList;
	}

	public String getRestTime() {
		return restTime;
	}

	public void setRestTime(String restTime) {
		this.restTime = restTime;
	}

	public String getWtImgurl() {
		return wtImgurl;
	}

	public void setWtImgurl(String wtImgurl) {
		this.wtImgurl = wtImgurl;
	}

	public String getSchedule() {
		return schedule;
	}

	public void setSchedule(String schedule) {
		this.schedule = schedule;
	}

	public String getCheapmoney() {
		return cheapmoney;
	}

	public void setCheapmoney(String cheapmoney) {
		this.cheapmoney = cheapmoney;
	}

	public String getRestStartTime() {
		return restStartTime;
	}

	public void setRestStartTime(String restStartTime) {
		this.restStartTime = restStartTime;
	}

	public String getCountryName() {
		return countryName;
	}

	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}

	public String getEndDateForJs() {
		return endDateForJs;
	}

	public void setEndDateForJs(String endDateForJs) {
		this.endDateForJs = endDateForJs;
	}
	
}