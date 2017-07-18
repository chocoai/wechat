package com.miyzh.wechatshop.groupbuy.report;

import com.miyzh.wechatshop.groupbuy.bean.GroupBuy;

/**
 * <pre>
 * the GroupbuyQueryResult class for
 * 团购查询结果类
 * </pre>
 *
 * @author zhangtao 2016年7月11日
 */
public class GroupbuyQueryResult {
	
	/** 成功：0 */
	public static final int RESULT_SUCCESS = 0;
	/** 团购已结束:1 */
	public static final int RESULT_END = 1;
	/** 未注册无权限访问：2 */
	public static final int RESULT_NO_NOUSER_ROLE = 2;
	/** 未认证无权限访问：3 */
	public static final int RESULT_NO_REGUSER_ROLE = 3;
	/** 审核中用户无权访问：4 */
	public static final int RESULT_AUDITING_ROLE = 4;
	/** 审核不通过未认证无权限访问：5 */
	public static final int RESULT_NO_PASS_ROLE = 5;
	
	/** 返回结果 0-成功 1-团购已结束 2-无权限访问.*/
	private int result;
	/** 返回实体类 .*/
	private GroupBuy groupbuy;
	/** 失败原因 .*/
	private String certificateOpinion;

	public int getResult() {
		return result;
	}

	public void setResult(int result) {
		this.result = result;
	}

	public GroupBuy getGroupbuy() {
		return groupbuy;
	}

	public void setGroupbuy(GroupBuy groupbuy) {
		this.groupbuy = groupbuy;
	}

	public String getCertificateOpinion() {
		return certificateOpinion;
	}

	public void setCertificateOpinion(String certificateOpinion) {
		this.certificateOpinion = certificateOpinion;
	}

}
