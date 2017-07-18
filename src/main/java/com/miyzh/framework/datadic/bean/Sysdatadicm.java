package com.miyzh.framework.datadic.bean;

import com.miyzh.framework.base.bean.BaseBean;

/**
 * 文件名: Vpssysdatadicm.java<br>
 * 版权：Copyright 2014 Digital Bay Technology Group. Co. Ltd. All Rights Reserved.<br>
 * 描述: 数据字典表<br>
 * 修改人: zhanglei<br>
 * 修改时间：2014-08-27 17:12:12<br>
 * 修改内容：新增<br>
 */
public class Sysdatadicm extends BaseBean {

	/** **/
	private static final long serialVersionUID = 1L;
	/** 字典数据主键 */
	private String datadickey;
	/** 字典类型主键 */
	private String categorykey;
	/** 数据键 */
	private String dataid;
	/** 数据值 */
	private String datavalue;
	/** 数据值别名(缩写) */
	private String dataalias;
	/** 说明 */
	private String dataexplain;
	/** 排序号 */
	private int sequencenum;
	/** 类型编码 **/
	private String categorycode;
	/**
	 * 请求报文中当前页
	 */
	private transient int currentPage = 1;
	/**
	 * 请求报文中当前记录数
	 */
	private transient int count = 20;
	/** 手机充值金额 */
	private String changevalue;
	public String getDatadickey() {
		return datadickey;
	}
	public void setDatadickey(String datadickey) {
		this.datadickey = datadickey;
	}
	public String getCategorykey() {
		return categorykey;
	}
	public void setCategorykey(String categorykey) {
		this.categorykey = categorykey;
	}
	public String getDataalias() {
		return dataalias;
	}
	public void setDataalias(String dataalias) {
		this.dataalias = dataalias;
	}
	public String getDataexplain() {
		return dataexplain;
	}

	public String getDataid() {
		return dataid;
	}
	public void setDataid(String dataid) {
		this.dataid = dataid;
	}
	public String getDatavalue() {
		return datavalue;
	}
	public void setDatavalue(String datavalue) {
		this.datavalue = datavalue;
	}
	public void setDataexplain(String dataexplain) {
		this.dataexplain = dataexplain;
	}
	public int getSequencenum() {
		return sequencenum;
	}
	public void setSequencenum(int sequencenum) {
		this.sequencenum = sequencenum;
	}
	public String getCategorycode() {
		return categorycode;
	}
	public void setCategorycode(String categorycode) {
		this.categorycode = categorycode;
	}
	public int getCurrentPage() {
		return currentPage;
	}
	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public String getChangevalue() {
		return changevalue;
	}
	public void setChangevalue(String changevalue) {
		this.changevalue = changevalue;
	}

}
