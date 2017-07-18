package com.miyzh.framework.base;

/**
 * 文件名: Constant.java<br>
 * 版权：Copyright 2014 Digital Bay Technology Group. Co. Ltd. All Rights Reserved. <br>
 */
public class Constant {
	public final static String DBTSPLIT = "_";
	/** 多个参数分隔符 **/
	public final static String THREEPAR_SPLIT_CHART = ",";

	/**
	 * 删除操作标识
	 */
	public static interface DbDelFlag {
		public final static String noDel = "0";
		public final static String del = "1";
	}

	
	
	/**
	 * 公众号信息
	 */
	public static interface WXSERVER{

		/** appid */
		static final String APPID = "wxf4a7649a7bf71c11";

		static final String APPSECRET = "95e56ee77c5a276c7e348dda3298b118";


	}
	
	
	/**
	 * 账户类型
	 */
	public static interface accountType {
		public final static String WECHAT = "1";
	}

	/**
	 * 状态码：code
	 */
	public static interface ResultCode {
		/** 成功：0 */
		public static final String SUCCESS = "0";
		/** 失败:1 */
		public static final String FILURE = "1";
		/** 业务状态码：2 */
		public static final String RESULT_TWO = "2";
		/** 业务状态码：3 */
		public static final String RESULT_THREE = "3";
		/** 业务状态码：4 */
		public static final String RESULT_FOURE = "4";
		/** 业务状态吗：5 */
		public static final String RESUlT_FIVE = "5";
		/** 业务状态吗：6 */
		public static final String RESULT_SIX = "6";
		/** 业务状态码:7 */
		public static final String RESULT_SEVEN = "7";
		/** 业务编码：8 */
		public static final String RESULT_EIGHT = "8";
		/** 业务编码：9 **/
		public static final String RESULT_NINE = "9";
		/** 业务编码：10 **/
		public static final String RESULT_TEN = "10";
	}

	/**
	 * 业务类型：方法名
	 */
	public static interface CommandType {

		/** 命令类型：commandType */
		public static final String COMMAND_TYPE = "commandtype";

		/**
		 * 微信
		 */
		public static interface WECHAT {

			/** 获取微信公众号token：getAccessToken */
			static final String WCT_GETACCESSTOKEN = "getAccessToken";

			/** 强制获取微信公众号token **/
			static final String WCT_COMPULSORY_GETTOKEN = "compulsoryGetToken";

			/** 获取微信公众号用户信息：wctSnsapiBase */
			static final String WCT_SNSAPI_BASE = "wctSnsapiBase";

			/** 获取微信公众号用户信息：wctSnsapiUserinfo */
			static final String WCT_SNSAPI_USERINFO = "wctSnsapiUserinfo ";

		}

		/**
		 * 注册
		 */
		public static interface REGISTER {

			/** 手机发送验证码：phoneSendVeriCode */
			static final String PHONE_SEND_VERICODE = "phoneSendVeriCode";

			/** 校验验证码：validVeriCode */
			static final String VALIDATE_VERICODE = "validVeriCode";

			/** 普通注册：basicRegister */
			static final String BASIC_REGISTER = "basicRegister";

		}

		

	}

}
