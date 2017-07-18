package com.miyzh.framework.datadic.dao;
  
import java.util.List;

import com.miyzh.framework.base.dao.IBaseDao;
import com.miyzh.framework.datadic.bean.SysDicCategroyInfo;
/**
* 文件名:ISysDicCategroyInfoDao.java<br>
* 版权：Copyright 2014 Digital Bay Technology Group. Co. Ltd. All Rights Reserved. <br>
* 描述: 数据字典类型信息<br>
* 修改人: guchangpeng<br>
* 修改时间：2014-10-21 10:39:16<br>
* 修改内容：新增<br>
*/
public interface ISysDicCategroyInfoDao extends IBaseDao<SysDicCategroyInfo>
{ 
	/**
	 * 查询所有app端调用的数据字典类型list
	 * @return List<SysDicCategroyInfo> 所有app端调用的数据字典类型
	 */
	public List<SysDicCategroyInfo> queryAllAppInvokerCategroyInfo();
}