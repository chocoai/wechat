package com.miyzh.framework.datadic.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.miyzh.framework.datadic.bean.Sysdatadicm;
import com.miyzh.framework.datadic.dao.ISysdatadicmDao;
import com.miyzh.framework.util.CacheUtil;

/**
 * 文件名：VpssysdatadicmService.java<br>
 * 版权：Copyright 2014 Digital Bay Technology Group. Co. Ltd. All Rights Reserved. <br>
 * 描述: 数据字典表<br>
 * 修改人: zhanglei<br>
 * 修改时间：2014-08-27 17:12:11<br>
 * 修改内容：新增<br>
 */
@SuppressWarnings("unchecked")
@Service("vpssysdatadicmService")
public class SysdatadicmService {
	@Autowired
	private ISysdatadicmDao iVpssysdatadicmDao;

	/**
	 * 通过categorycode查找一类数据
	 * 
	 * @param id
	 * @return
	 */
	public List<Sysdatadicm> findVpssysdatadicmByCategoryCode(
			Map<String, Object> map) {
		StringBuffer keyBuffer = CacheUtil.getCachekeyMethodPrx(
				CacheUtil.cacheKey.KEY_SYS_DATA_DICM, null, Thread
						.currentThread().getStackTrace()[1].getMethodName(),
				new Object[] { map });
		Object object = CacheUtil.getObjectValue(keyBuffer.toString());
		if (null == object) {
			object = iVpssysdatadicmDao.findVpssysdatadicmByCategoryCode(map);
			CacheUtil.put(keyBuffer.toString(), object);
		}
		return (List<Sysdatadicm>) object;
	}

	/**
	 * 通过数据键查询数据值
	 * 
	 * @param id
	 * @return
	 */
	public List<Sysdatadicm> findVpssysdatadicmByDataId(
			Map<String, Object> map) {
		return this.iVpssysdatadicmDao.findVpssysdatadicmByDataId(map);
	}

	/**
	 * 查询app端所有配置在数据字典中的数据
	 * 
	 * @return List<Vpssysdatadicm> 数据字典list
	 */
	public List<Sysdatadicm> queryAllAppInvoker() {
		// 此处不用缓存，因只有在启动环境时，添加缓存一次.
		return iVpssysdatadicmDao.queryAllAppInvoker();
	}

	/**
	 * 根据数据id，查询数据值
	 * 
	 * @param dataid
	 *            数据id
	 * @return 数据值
	 */
	public String findDataDicmValueByDataId(String dataid) {
		StringBuffer keyBuffer = CacheUtil.getCachekeyMethodPrx(
				CacheUtil.cacheKey.KEY_SYS_DATA_DICM, dataid, Thread
						.currentThread().getStackTrace()[1].getMethodName(),
				new Object[] { dataid });
		Object object = CacheUtil.getObjectValue(keyBuffer.toString());
		if (null == object) {
			object = iVpssysdatadicmDao.findDataDicmValueByDataId(dataid);
			CacheUtil.put(keyBuffer.toString(), object);
		}
		return object == null ? "" : object.toString();
	}

	/**
	 * 根据数据id，查询数据值
	 * 
	 * @param dataid
	 *            数据id
	 * @return 数据值
	 */
	public Sysdatadicm findDataDicmByDataId(String dataid) {
		StringBuffer keyBuffer = CacheUtil.getCachekeyMethodPrx(
				CacheUtil.cacheKey.KEY_SYS_DATA_DICM, dataid, Thread
						.currentThread().getStackTrace()[1].getMethodName(),
				new Object[] { dataid });
		Object object = CacheUtil.getObjectValue(keyBuffer.toString());
		if (null == object) {
			object = iVpssysdatadicmDao.findDataDicmByDataId(dataid);
			CacheUtil.put(keyBuffer.toString(), object);
		}
		return (Sysdatadicm) object;
	}

}
