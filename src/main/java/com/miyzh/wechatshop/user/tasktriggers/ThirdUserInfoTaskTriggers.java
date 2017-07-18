package com.miyzh.wechatshop.user.tasktriggers;

import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.miyzh.framework.aliyunoss.OssClientComponent;
import com.miyzh.framework.util.InputStreamToByteUtil;
import com.miyzh.framework.util.PropertiesUtil;
import com.miyzh.wechatshop.user.bean.ThirdUserInfoBean;
import com.miyzh.wechatshop.user.service.IThirdUserInfoService;
import com.miyzh.wechatshop.wechat.bean.WctUserInfo;
import com.miyzh.wechatshop.wechat.service.WctAccessTokenService;
import com.miyzh.wechatshop.wechat.service.WctUserInfoService;

/**
 * 文件名： ThirdUserInfoTaskTriggers.java<br>
 * 描述: 三方账户定时任务<br>
 * 修改人: wumingdi<br>
 * 修改时间： 2016年8月9日 <br>
 */
@Service("thirdUserInfoTaskTriggers")
public class ThirdUserInfoTaskTriggers {

	private Logger log = Logger.getLogger(this.getClass());

	@Autowired
	private IThirdUserInfoService thirdUserInfoService;

	/** 微信公众号唯一票据信息 **/
	@Autowired
	private WctAccessTokenService wctAccessTokenService;

	/** 微信用户详细信息 **/
	@Autowired
	private WctUserInfoService wctUserInfoService;

	// 阿里云oss上传文件路径
	private final String OOS_UOLOAD_FILE_PATH = PropertiesUtil
			.getPropertyValue("aliyun.oss.filepath");

	private static final String OOS_PATH = PropertiesUtil
			.getPropertyValue("aliyun.oss.myzh.wenwen.external.hostname");

	/**
	 * 更新微信用户信息
	 * 
	 * @throws Exception
	 */
	public void updateWechatUserInfo() throws Exception {

		// 查询unionid和headurl为空记录
		List<ThirdUserInfoBean> thirdUserInfoBeanList = thirdUserInfoService
				.findImperfectInfo();
		if (!thirdUserInfoBeanList.isEmpty()) {

			// 获取用户头像及unionId
			String appId = PropertiesUtil.getPropertyValue("app_id");
			String appSec = PropertiesUtil.getPropertyValue("app_secret");
			String accessToken = wctAccessTokenService.toAccessToken(appId,
					appSec);

			for (ThirdUserInfoBean thirdUserInfoBean : thirdUserInfoBeanList) {
				try {
					String openId = thirdUserInfoBean.getOpenId();
					WctUserInfo wctUserInfo = wctUserInfoService
							.getWctUserInfo(openId, accessToken);
					if (null != wctUserInfo) {
						String headUrl = wctUserInfo.getHeadimgurl();
						String unionId = wctUserInfo.getUnionid();
						if (StringUtils.isNotBlank(headUrl)) {
							URL url = new URL(headUrl);
							// 打开连接
							URLConnection con = url.openConnection();
							// 输入流
							InputStream is = con.getInputStream();
							// 时间
							Calendar calendar = Calendar.getInstance();
							// 文件路径
							String filePath = OOS_UOLOAD_FILE_PATH;
							filePath = filePath.replace("YYYY",
									"" + calendar.get(Calendar.YEAR));
							filePath = filePath.replace("MM",
									"" + (calendar.get(Calendar.MONTH) + 1));
							filePath = filePath.replace("DD",
									"" + calendar.get(Calendar.DATE));
							filePath = filePath.replace("UUID", UUID
									.randomUUID().toString());
							filePath = filePath.replace("EXT", "jpg");

							boolean flag = OssClientComponent
									.uploadFileOrdinary(filePath,
											InputStreamToByteUtil
													.input2byte(is));
							if (flag) {
								thirdUserInfoBean.setHeadUrl(OOS_PATH
										+ filePath);
							}
						}
						thirdUserInfoBean.setUnionId(unionId);
						// 更新
						thirdUserInfoService
								.updateThirdUserInfo(thirdUserInfoBean);
					}
				} catch (Exception e) {
					log.error(
							"In the method ThirdUserInfoTaskTriggers.updateWechatUserInfo() exists error!",
							e);
				}

			}

		}

	}

}
