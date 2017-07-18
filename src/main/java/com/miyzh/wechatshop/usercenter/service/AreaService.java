package com.miyzh.wechatshop.usercenter.service;

import java.util.ArrayList;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.miyzh.cp.dict.NationCodeService;
import com.miyzh.cp.vo.NationCode;
import com.miyzh.framework.base.Constant;
import com.miyzh.wechatshop.payUtil.bean.Address;
import com.miyzh.wechatshop.usercenter.bean.UserCenterResultBean;


@Service("areaService")
public class AreaService {

	protected Log log = LogFactory.getLog(getClass());

	@Autowired
	private NationCodeService nationCodeService;



	/**
	 * 根据区域查询下级区域信息接口
	 *
	 * @param areaId
	 *            （区域编码）
	 * @return
	 */
	public UserCenterResultBean queryAreaList(String areaId) {

		UserCenterResultBean areaResultBean = new UserCenterResultBean();
		areaResultBean.setResultCode(Constant.ResultCode.SUCCESS);
		areaResultBean.setResultMsg("成功");

		try {

			List<NationCode> areaVo = nationCodeService.getNationList(areaId,false);
			List<Address> lists = new ArrayList<>();
			Address address=null;
			if (!areaVo.isEmpty()) {
				for (NationCode areaVoTmp : areaVo) {
					address=new Address();
					address.setCode(areaVoTmp.getCode());
					address.setName(areaVoTmp.getName());
					lists.add(address);
				}
				areaResultBean.setResultObject(lists);
			}

		} catch (Exception e) {
			log.error(
					"In the method NationCodeService.getNationList(String areaId) exists error!",
					e);
			areaResultBean.setResultCode(Constant.ResultCode.FILURE);
			areaResultBean.setResultMsg("网络繁忙请稍后再试");
		}

		return areaResultBean;
	}
	
	
	
	/**
	 * 根据区域编码查询区域对象信息接口
	 *
	 * @param areaId
	 *            （区域编码）
	 * @return
	 */
	public UserCenterResultBean queryAreaNameByCode(String areaCode) {

		UserCenterResultBean areaResultBean = new UserCenterResultBean();
		areaResultBean.setResultCode(Constant.ResultCode.SUCCESS);
		areaResultBean.setResultMsg("成功");

		try {

			NationCode areaVo = nationCodeService.getNationByCode(areaCode);

			if (null != areaVo) {
				Address address=new Address();
				address.setCode(areaVo.getCode());
				address.setName(areaVo.getName());
				areaResultBean.setResultObject(address);
			}

		} catch (Exception e) {
			log.error("In the method NationCodeService.getNationByCode(String areaCode) exists error!", e);
			areaResultBean.setResultCode(Constant.ResultCode.FILURE);
			areaResultBean.setResultMsg("网络繁忙请稍后再试");
		}

		return areaResultBean;
	}

	
	/**
	 * 根据区域编码查询区域名称信息接口
	 *
	 * @param areaId
	 *            （区域编码）
	 * @return
	 */
	public UserCenterResultBean queryNameByCode(String areaCode) {

		UserCenterResultBean areaResultBean = new UserCenterResultBean();
		areaResultBean.setResultCode(Constant.ResultCode.SUCCESS);
		areaResultBean.setResultMsg("成功");

		try {

			String  areaName = nationCodeService.getNameByCode(areaCode);

			if (null != areaName) {
				areaResultBean.setResultObject(areaName);
			}

		} catch (Exception e) {
			log.error("In the method NationCodeService.getNameByCode(String areaCode) exists error!", e);
			areaResultBean.setResultCode(Constant.ResultCode.FILURE);
			areaResultBean.setResultMsg("网络繁忙请稍后再试");
		}

		return areaResultBean;
	}

	
	
	
}
