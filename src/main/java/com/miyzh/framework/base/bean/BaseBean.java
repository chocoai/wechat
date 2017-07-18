package com.miyzh.framework.base.bean;

import java.io.Serializable;

import com.miyzh.framework.base.Constant;
import com.miyzh.framework.util.DateUtil;

/**
 * 文件名: BaseBean.java<br>
 * 版权：Copyright 2014 Digital Bay Technology Group. Co.Ltd. All Rights Reserved.<br>
 * 描述: 基础bean，公用bean<br>
 * 修改人: 谷长鹏<br>
 * 修改时间：2014-02-21 18:50:05<br>
 * 修改内容：新增<br>
 */
public class BaseBean implements Serializable {

	private static final long serialVersionUID = 1L;
	/** 顺序号 */
	private transient String sequenceno;
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
	private String environment;
	/** 备注 */
	private String remarks;

	public String getEnvironment() {
		return environment;
	}

	public void setEnvironment(String environment) {
		this.environment = environment;
	}

	public String getSequenceno() {
		return sequenceno;
	}

	public void setSequenceno(String sequenceno) {
		this.sequenceno = sequenceno;
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

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
}
