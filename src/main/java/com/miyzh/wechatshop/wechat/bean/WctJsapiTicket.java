package com.miyzh.wechatshop.wechat.bean;

import java.io.Serializable;

import com.miyzh.framework.base.Constant;
import com.miyzh.framework.util.DateUtil;

public class WctJsapiTicket implements Serializable {

	private static final long serialVersionUID = 6540195292064334978L;
	/** 主键 */
	private String id;
	/** TOKEN */
	private String jsapiTicket;
	/** 过期时间 */
	private String expiretime;
	
	/** 返回消息代码 0-成功 */
	private String errcode;
	/** 返回消息 */
	private String errmsg;
	/** 微信返回票根 */
	private String ticket;
	/** 微信过期时间 */
	private String expires_in;
	
	private String appid;
	
	/** 创建日期 */
	private transient String credate = DateUtil.getDateTime();
	/** 创建人 */
	private transient String creuser;
	/** 修改时间 */
	private transient String updatetime = DateUtil.getDateTime();
	/** 修改人 */
	private transient String updateuser;
	/** 0未删除 1删除 */
	private transient String deleteflag = Constant.DbDelFlag.noDel;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getJsapiTicket() {
		return jsapiTicket;
	}
	public void setJsapiTicket(String jsapiTicket) {
		this.jsapiTicket = jsapiTicket;
	}
	public String getExpiretime() {
		return expiretime;
	}
	public void setExpiretime(String expiretime) {
		this.expiretime = expiretime;
	}
	public String getErrcode() {
		return errcode;
	}
	public void setErrcode(String errcode) {
		this.errcode = errcode;
	}
	public String getErrmsg() {
		return errmsg;
	}
	public void setErrmsg(String errmsg) {
		this.errmsg = errmsg;
	}
	public String getTicket() {
		return ticket;
	}
	public void setTicket(String ticket) {
		this.ticket = ticket;
	}
	public String getExpires_in() {
		return expires_in;
	}
	public void setExpires_in(String expires_in) {
		this.expires_in = expires_in;
	}
	public String getAppid() {
		return appid;
	}
	public void setAppid(String appid) {
		this.appid = appid;
	}
	public String getCredate() {
		return credate;
	}
	public void setCredate(String credate) {
		this.credate = credate;
	}
	public String getCreuser() {
		return creuser;
	}
	public void setCreuser(String creuser) {
		this.creuser = creuser;
	}
	public String getUpdatetime() {
		return updatetime;
	}
	public void setUpdatetime(String updatetime) {
		this.updatetime = updatetime;
	}
	public String getUpdateuser() {
		return updateuser;
	}
	public void setUpdateuser(String updateuser) {
		this.updateuser = updateuser;
	}
	public String getDeleteflag() {
		return deleteflag;
	}
	public void setDeleteflag(String deleteflag) {
		this.deleteflag = deleteflag;
	}

}
