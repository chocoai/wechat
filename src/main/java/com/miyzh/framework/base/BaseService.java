package com.miyzh.framework.base;
/**
 * 文件名: AbstractBaseBean.java<br>
 * 版权：Copyright 2014 Digital Bay Technology Group. Co. Ltd. All Rights Reserved. <br>
 * 描述: 业务规则抽象/基础类 <br>
 * 修改人: guchangpeng <br>
 * 修改时间：2014-9-28 上午11:29:12 <br>
 * 修改内容：新增 <br>
 */
public abstract class BaseService {

	/**
	 * 得到总页数
	 * 
	 * @param count
	 *            每次取的记录数
	 * @param total
	 *            数据库中总记录数
	 * @return int 总页数
	 */
	protected int getTotalPage(int count, int total) {
		int totalPage = 1;// 总页数
		if (total > count) {
			if (total % count == 0) {
				totalPage = total / count;
			} else {
				totalPage = total / count + 1;
			}
		}
		return totalPage;
	}
}
