package com.miyzh.wechatshop.groupbuy.report;

import com.miyzh.framework.base.action.request.BaseRequestReport;

public class RequestReportGroupbuyPreview extends BaseRequestReport  {

	private GroupbuyPreview commandinfo;

	public GroupbuyPreview getCommandinfo() {
		return commandinfo;
	}

	public void setCommandinfo(GroupbuyPreview commandinfo) {
		this.commandinfo = commandinfo;
	}
	
	public String toString(){
		if(commandinfo!=null){
			return "openid->"+commandinfo.getOpenid()+",groupbuyid->"+commandinfo.getGroupbuyid()+",userid->"+commandinfo.getRecommenduserid();
		}else{
			return "null";
		}
	}
}
