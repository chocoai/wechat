package com.miyzh.wechatshop.wechat.service.impl;

import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.Calendar;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.miyzh.framework.aliyunoss.OssClientComponent;
import com.miyzh.framework.util.InputStreamToByteUtil;
import com.miyzh.framework.util.PropertiesUtil;
import com.miyzh.wechatshop.user.bean.ChannelConcernBean;
import com.miyzh.wechatshop.user.bean.ThirdUserInfoBean;
import com.miyzh.wechatshop.user.service.IChannelConcernService;
import com.miyzh.wechatshop.user.service.IThirdUserInfoService;
import com.miyzh.wechatshop.wechat.bean.RequestInfo;
import com.miyzh.wechatshop.wechat.bean.TextMessage;
import com.miyzh.wechatshop.wechat.bean.WctUserInfo;
import com.miyzh.wechatshop.wechat.service.ISubscribeService;
import com.miyzh.wechatshop.wechat.service.IWeChatService;
import com.miyzh.wechatshop.wechat.service.WctAccessTokenService;
import com.miyzh.wechatshop.wechat.service.WctUserInfoService;

/**
 * 文件名： SubscribeServiceImpl.java<br>
 * 描述: 订阅推送消息<br>
 * 修改人: wumingdi<br>
 * 修改时间： 2016年7月12日 <br>
 */
@Service("SubscribeServiceImpl")
public class SubscribeServiceImpl implements ISubscribeService, IWeChatService {

	private Logger log = Logger.getLogger(this.getClass());
	private static final String OOS_PATH = PropertiesUtil
			.getPropertyValue("aliyun.oss.myzh.wenwen.external.hostname");
	// 阿里云oss上传文件路径
	private final String OOS_UOLOAD_FILE_PATH = PropertiesUtil
			.getPropertyValue("aliyun.oss.filepath");

	@Autowired
	private IThirdUserInfoService thirdUserInfoService;

	/** 渠道关注 **/
	@Autowired
	private IChannelConcernService channelConcernService;

	/** 微信公众号唯一票据信息 **/
	@Autowired
	private WctAccessTokenService wctAccessTokenService;

	/** 微信用户详细信息 **/
	@Autowired
	private WctUserInfoService wctUserInfoService;

	@Override
	public Object subscribe(RequestInfo req) {

		TextMessage message = null;
		try {
			// 微信id
			String openId = req.getFromUserName();

			ThirdUserInfoBean thirdUserInfoBean = thirdUserInfoService
					.findByOpenId(openId, null);

			// 第三方账户
			ThirdUserInfoBean thirdUserInfo = new ThirdUserInfoBean();
			thirdUserInfo.setOpenId(openId);
			thirdUserInfo.setAccountType(1);
			thirdUserInfo.setIfFollow(1);

			if (null != thirdUserInfoBean) {

				// 更新
				thirdUserInfoService.updateThirdUserInfo(thirdUserInfo);

			} else {

				thirdUserInfo.setIfBinding(0);

				// 获取用户头像及unionId
				String appId = PropertiesUtil.getPropertyValue("app_id");
				String appSec = PropertiesUtil.getPropertyValue("app_secret");
				String accessToken = wctAccessTokenService.toAccessToken(appId,
						appSec);
				WctUserInfo wctUserInfo = wctUserInfoService.getWctUserInfo(
						openId, accessToken);
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
						filePath = filePath.replace("UUID", UUID.randomUUID()
								.toString());
						filePath = filePath.replace("EXT", "jpg");

						boolean flag = OssClientComponent.uploadFileOrdinary(
								filePath, InputStreamToByteUtil.input2byte(is));
						if (flag) {
							thirdUserInfo.setHeadUrl(OOS_PATH + filePath);
						}

					}
					thirdUserInfo.setUnionId(unionId);
				}

				// 新增
				thirdUserInfoService.addThirdUserInfo(thirdUserInfo);

			}

			// 事件KEY值，qrscene_为前缀，后面为二维码的参数值
			String eventKey = req.getEventKey();
			if (null != eventKey && StringUtils.isNotBlank(eventKey)
					&& eventKey.indexOf("qrscene_") == 0) {
				eventKey = eventKey.replaceAll("qrscene_", "");
				if (StringUtils.isNotBlank(eventKey)) {

					/** 渠道关注 **/
					Integer concernType = 1;

					ChannelConcernBean channelConcernBean = channelConcernService
							.queryChannelConcern(openId, concernType);
					if (null == channelConcernBean) {
						channelConcernBean = new ChannelConcernBean();
						channelConcernBean.setConcernType(concernType);
						channelConcernBean.setOpenId(openId);
						channelConcernBean.setChannelKey(eventKey);
						channelConcernBean.setId(UUID.randomUUID().toString());
						channelConcernService
								.addChannelConcern(channelConcernBean);
					}
				}
			}

			message = new TextMessage();
			message.setContent(PropertiesUtil
					.getPropertyValue("wechat_subscribe"));
		} catch (Exception e) {
			log.error("关注异常", e);
		}

		return message;
	}

	@Override
	public Object handler(RequestInfo req) {
		// TODO Auto-generated method stub
		return this.subscribe(req);
	}

}
