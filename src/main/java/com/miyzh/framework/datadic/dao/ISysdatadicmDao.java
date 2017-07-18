package com.miyzh.framework.datadic.dao;

import java.util.List;
import java.util.Map;

import com.miyzh.framework.base.dao.IBaseDao;
import com.miyzh.framework.datadic.bean.Sysdatadicm;

/**
 * 文件名:IVpssysdatadicmDao.java<br>
 * 版权：Copyright 2014 Digital Bay Technology Group. Co. Ltd. All Rights Reserved. <br>
 * 描述: 数据字典表<br>
 * 修改人: zhanglei<br>
 * 修改时间：2014-08-27 17:12:11<br>
 * 修改内容：新增<br>
 */
public interface ISysdatadicmDao extends IBaseDao<Sysdatadicm> {
	/**
	 * 通过categoryKey查找一类数据
	 * 
	 * @param id
	 * @return
	 */
	public List<Sysdatadicm> findVpssysdatadicmByCategoryKey(
			String categoryCode);

	/**
	 * 通过categorycode查找一类数据
	 * 
	 * @param id
	 * @return
	 */
	public List<Sysdatadicm> findVpssysdatadicmByCategoryCode(
			Map<String, Object> map);
	/**
	 * 通过数据键查询数据值
	 * 
	 * @param id
	 * @return
	 */
	public List<Sysdatadicm> findVpssysdatadicmByDataId(
			Map<String, Object> map);
	/**
	 * 查询app端所有配置在数据字典中的数据 
	 * @return  List<Vpssysdatadicm> 数据字典list
	 */
	public List<Sysdatadicm> queryAllAppInvoker();
	/**
	 * 根据数据id，查询数据值
	 * @param dataid  数据id
	 * @return 数据值
	 */
	public String findDataDicmValueByDataId(String dataid);
	/**
	 * 根据数据id，查询数据值
	 * @param dataid  数据id
	 */
	public Sysdatadicm findDataDicmByDataId(String dataid);
	
}