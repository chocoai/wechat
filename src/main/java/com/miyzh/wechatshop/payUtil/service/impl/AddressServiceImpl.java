package com.miyzh.wechatshop.payUtil.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.miyzh.framework.util.CacheUtil;
import com.miyzh.wechatshop.payUtil.bean.Address;
import com.miyzh.wechatshop.payUtil.dao.AddressDao;
import com.miyzh.wechatshop.payUtil.service.AddressService;
import com.miyzh.wechatshop.usercenter.bean.UserCenterResultBean;
import com.miyzh.wechatshop.usercenter.service.AreaService;

@Service("addressService")
public class AddressServiceImpl implements AddressService {
	@Autowired
	private AddressDao addressDao;
	@Autowired
	private AreaService areaService;

	@SuppressWarnings("unchecked")
	@Override
	public List<Address> findByParentId(String parentId) {
		List<Address> addresses = null;
        if(parentId!=null&&!"".equals(parentId)){
        }else{
        	parentId="00";
        }
		UserCenterResultBean areaResultBean=areaService.queryAreaList(parentId);
		if(areaResultBean!=null){
			if("0".equals(areaResultBean.getResultCode())){
				addresses=(List<Address>) areaResultBean.getResultObject();
			}
		}
		return addresses;
	}

	
	
	//不使用
	@Override
	public Address findByName(String name) {

		StringBuffer keyBuffer = CacheUtil.getCachekeyMethodPrx(
				CacheUtil.cacheKey.address.KEY_ADDRESS, name, Thread
						.currentThread().getStackTrace()[1].getMethodName(),
				new Object[] {});

		Object object = CacheUtil.getObjectValue(keyBuffer.toString());
		if (object == null) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("name", name);
			Address addressBean = addressDao.findAddress(map);
			CacheUtil.put(keyBuffer.toString(), addressBean);
			return addressBean;
		}
		return (Address) object;
	}

	
	//不适用
	@Override
	public Address findByMap(String id, String code) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		map.put("code", code);
		return addressDao.findByMap(map);

	}
	
	public Address findByAreaName(String areaCode) {
		Address address = null;
		UserCenterResultBean areaResultBean=areaService.queryAreaNameByCode(areaCode);
		if(areaResultBean!=null){
			if("0".equals(areaResultBean.getResultCode())){
				address=(Address) areaResultBean.getResultObject();
			}
		}
		return address;
	}

}
