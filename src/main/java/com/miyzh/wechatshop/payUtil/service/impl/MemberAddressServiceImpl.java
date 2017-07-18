package com.miyzh.wechatshop.payUtil.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.miyzh.wechatshop.payUtil.bean.ShopMemberAddress;
import com.miyzh.wechatshop.payUtil.dao.MemberAddressDao;
import com.miyzh.wechatshop.payUtil.service.MemberAddressService;
import com.miyzh.wechatshop.usercenter.bean.UserCenterResultBean;
import com.miyzh.wechatshop.usercenter.service.AreaService;

@Service("memberAddressService")
public class MemberAddressServiceImpl implements MemberAddressService {
	@Autowired
	private MemberAddressDao memberAddressDao;
	@Autowired
	private AreaService areaService;


	public void create(ShopMemberAddress shopMemberAddress) {
		
		memberAddressDao.create(shopMemberAddress);
	}

	public void delete(String id) {
		memberAddressDao.deleteById(id);

	}

	public void update(ShopMemberAddress shopMemberAddress) {
		memberAddressDao.update(shopMemberAddress);

	}


	public List<ShopMemberAddress> getAll() {
		
		List<ShopMemberAddress> shopMemberAddresses = null;
		shopMemberAddresses= (List<ShopMemberAddress>) memberAddressDao.getAll();
		if (shopMemberAddresses != null && shopMemberAddresses.size() > 0) {
			for (int i = 0; i < shopMemberAddresses.size(); i++) {
				UserCenterResultBean areaResultp = areaService
						.queryNameByCode(shopMemberAddresses.get(i).getProvince());
				UserCenterResultBean areaResultc = areaService.queryNameByCode(shopMemberAddresses.get(i).getCity());
				UserCenterResultBean areaResulti = areaService.queryNameByCode(shopMemberAddresses.get(i).getCountry());
				if (areaResultp != null && areaResultc != null && areaResulti != null) {
					String provinceName = (String) areaResultp.getResultObject();
					String cityName = (String) areaResultc.getResultObject();
					String countryName = (String) areaResulti.getResultObject();
					shopMemberAddresses.get(i).setDetailaddress(provinceName + " " + cityName + " " + countryName + " "
							+ shopMemberAddresses.get(i).getDetailaddress());
				}
			}
		}
		return shopMemberAddresses;
		
	}

	@Override
	public List<ShopMemberAddress> findByMemberId(Long memberId, String isdefind,String addressId) {
		List<ShopMemberAddress> shopMemberAddresses = null;
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("memberId", memberId);
		map.put("isdefind", isdefind);
		map.put("addressId", addressId);
		shopMemberAddresses = memberAddressDao.findByMemberId(map);
		if (shopMemberAddresses != null && shopMemberAddresses.size() > 0) {
			for (int i = 0; i < shopMemberAddresses.size(); i++) {
				UserCenterResultBean areaResultp = areaService
						.queryNameByCode(shopMemberAddresses.get(i).getProvince());
				UserCenterResultBean areaResultc = areaService.queryNameByCode(shopMemberAddresses.get(i).getCity());
				UserCenterResultBean areaResulti = areaService.queryNameByCode(shopMemberAddresses.get(i).getCountry());
				if (areaResultp != null && areaResultc != null && areaResulti != null) {
					String provinceName = (String) areaResultp.getResultObject();
					String cityName = (String) areaResultc.getResultObject();
					String countryName = (String) areaResulti.getResultObject();
					shopMemberAddresses.get(i).setDetailaddress(provinceName + " " + cityName + " " + countryName + " "
							+ shopMemberAddresses.get(i).getDetailaddress());
				}
			}
		}
		return shopMemberAddresses;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ShopMemberAddress> findById(String addressId) {
		// TODO Auto-generated method stub
		List<ShopMemberAddress> shopMemberAddresses = null;
		shopMemberAddresses= (List<ShopMemberAddress>) memberAddressDao.findById(addressId);
		if (shopMemberAddresses != null && shopMemberAddresses.size() > 0) {
			for (int i = 0; i < shopMemberAddresses.size(); i++) {
				UserCenterResultBean areaResultp = areaService
						.queryNameByCode(shopMemberAddresses.get(i).getProvince());
				UserCenterResultBean areaResultc = areaService.queryNameByCode(shopMemberAddresses.get(i).getCity());
				UserCenterResultBean areaResulti = areaService.queryNameByCode(shopMemberAddresses.get(i).getCountry());
				if (areaResultp != null && areaResultc != null && areaResulti != null) {
					String provinceName = (String) areaResultp.getResultObject();
					String cityName = (String) areaResultc.getResultObject();
					String countryName = (String) areaResulti.getResultObject();
					shopMemberAddresses.get(i).setDetailaddress(provinceName + " " + cityName + " " + countryName + " "
							+ shopMemberAddresses.get(i).getDetailaddress());
				}
			}
		}
		return shopMemberAddresses;
		
	}

	@Override
	public void updateByIsDefault(String memberId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("memberId", memberId);
		memberAddressDao.updateByIsDault(map);
	}

}
