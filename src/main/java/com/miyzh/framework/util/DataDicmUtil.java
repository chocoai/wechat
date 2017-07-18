package com.miyzh.framework.util;

import com.miyzh.framework.base.Constant;
import com.miyzh.framework.base.util.BeanFactoryUtil;
import com.miyzh.framework.datadic.service.SysdatadicmService;

/**
 * 文件名: DataDicmUtil.java<br>
 * 版权：Copyright 2014 Digital Bay Technology Group. Co. Ltd. All Rights Reserved. <br>
 * 描述: 数据字典util类 <br>
 * 修改人: guchangpeng <br>
 * 修改时间：2014-10-21 上午11:22:48 <br>
 * 修改内容：新增 <br>
 */
public class DataDicmUtil {

	private static SysdatadicmService vpssysdatadicmService = (SysdatadicmService) BeanFactoryUtil
			.getbeanFromWebContext("vpssysdatadicmService");

	/***
	 * 数据字典类型code
	 */
	public static interface dataDicCategory {

		/** 微信消息 **/
		static final String CATEGROY_CODE_WECHAT_MSG = "wechat_msg";

		/** 红包消息提醒配置【用户抢到红包后，模板消息配置】 **/
		public static final String CATEGROY_GIFTSPACK_MSGREMIND_CONFIG = "giftspack_msgremind_config";
	}

	/***
	 * 数据字典dataID
	 */
	public static class dataDic {

		/**
		 * 红包消息提醒 2015-11-26 张涛因模板消息加入
		 */
		public static interface giftspack_msgremind_config {
			/** 模板消息头部颜色 **/
			public static final String MSGREMIND_TOPCOLOR = "msgremind_topcolor";
			/** 模板消息内容 **/
			public static final String MSGREMIND_DATA_FIRST_VALUE = "msgremind_data_first_value";
			/** 模板消息内容字体颜色 **/
			public static final String MSGREMIND_DATA_FIRST_COLOR = "msgremind_data_first_color";
			/** 青啤v积分，模板消息详情url **/
			public static final String MSGREMIND_TSINGTAO_VINTEGRAL_URL = "msgremind_tsingtao_vintegral_url";
			/** 青啤实物奖，模板消息详情url **/
			public static final String MSGREMIND_TSINGTAO_REALPRIZE_URL = "msgremind_tsingtao_realprize_url";
			/** 青啤虚拟券，模板消息详情url **/
			public static final String MSGREMIND_TSINGTAO_VIRTUALCARD_URL = "msgremind_tsingtao_virtualcard_url";
			/** 自营红包，模板消息详情url **/
			public static final String MSGREMIND_SELFGIFT_URL = "msgremind_selfgift_url";
			/** 模板消息模板id **/
			public static final String MSGREMIND_TEMPLATE_ID = "msgremind_template_id";
			/** 自营红包红包名称 **/
			public static final String MSGREMIND_SELFGIF_AWARDSNAME = "msgremind_selfgif_awardsname";
			/** 红包对应的企业名称 **/
			public static final String MSGREMIND_COMPANYNAME = "msgremind_companyname";
		}

		/**
		 * 微信客服消息
		 */
		public static interface wechat_msg {
			/** 成功上传小票客服消息:upload_receipt_serivce_msg **/
			public static final String UPLOAD_RECEIPT_SERIVCE_MSG = "upload_receipt_serivce_msg";
		}

	}

	/**
	 * 根据数据字典类型和字典key，查询数据值
	 * 
	 * @param categroycode
	 *            字典类型code
	 * @param dataid
	 *            字典key
	 * @return 数据值
	 */
	public static String getDataDicmValue(String categroycode, String dataid) {
		StringBuilder keyBuffer = new StringBuilder(30);
		keyBuffer.append(categroycode).append(Constant.DBTSPLIT).append(dataid);
		Object object = CacheUtil.getObjectValue(keyBuffer.toString());
		if (null == object) {
			object = vpssysdatadicmService.findDataDicmValueByDataId(dataid);
			CacheUtil.put(keyBuffer.toString(), object);
		}
		return (String) object;
	}
}
