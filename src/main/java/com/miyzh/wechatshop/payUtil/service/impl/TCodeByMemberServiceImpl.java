package com.miyzh.wechatshop.payUtil.service.impl;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.miyzh.framework.aliyunoss.OssClientComponent;
import com.miyzh.framework.util.CacheUtil;
import com.miyzh.wechatshop.groupbuy.bean.GroupBuy;
import com.miyzh.wechatshop.groupbuy.dao.IWcGroupBuyDao;
import com.miyzh.wechatshop.payUtil.bean.TCodeByMember;
import com.miyzh.wechatshop.payUtil.dao.TCodeByMemberDao;
import com.miyzh.wechatshop.payUtil.pay.utils.FileNameUtils;
import com.miyzh.wechatshop.payUtil.pay.utils.GetWeiXinCodeUrlText;
import com.miyzh.wechatshop.payUtil.pay.utils.TenpayUtil;
import com.miyzh.wechatshop.payUtil.service.TCodeByMemberService;
import com.miyzh.wechatshop.user.bean.ThirdUserInfoBean;
import com.miyzh.wechatshop.user.service.IThirdUserInfoService;

@Service("TCodeByMemberService")
public class TCodeByMemberServiceImpl implements TCodeByMemberService {
	
	@Autowired
	private IThirdUserInfoService thirdUserInfoService;
	
	@Autowired 
	private TCodeByMemberDao tCodeByMemberDao;
	
	@Autowired
	private IWcGroupBuyDao iWcGroupBuyDao;
	

	@Override
	public List<TCodeByMember> findByMemberId(Long memberId) {
		// TODO Auto-generated method stub
		return  tCodeByMemberDao.findByMemberId(memberId);
	}

	@Override
	public TCodeByMember findByMemberIdGroupId(Long memberId, Long groupId) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("memberId", memberId);
		map.put("groupId", groupId);
		return  tCodeByMemberDao.findByMemberIdGroupId(map);
	}

	
	
	@Override
	public void saveTCode(String openId, String groupId,String orderId) {
		TCodeByMember tCodeByMember = null;
		ThirdUserInfoBean thirdUser = thirdUserInfoService.findByOpenIdKey(openId, "");
		if (thirdUser != null&&thirdUser.getMemberID()!=null) {
			tCodeByMember = findByMemberIdGroupId(Long.valueOf(thirdUser.getMemberID()), Long.valueOf(groupId));
			if (tCodeByMember == null) {
				tCodeByMember = new TCodeByMember();
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				GroupBuy groupBuy = iWcGroupBuyDao.findByGroupId(String.valueOf(groupId));
				
				tCodeByMember.setMemberId(Long.valueOf(thirdUser.getMemberID()));
				tCodeByMember.setGroupId(Long.valueOf(groupId));
				tCodeByMember.setGroupName(groupBuy.getTitle());
				tCodeByMember.setGroupStartdate(sdf.format(groupBuy.getStartTime()));
				tCodeByMember.setGroupEnddate(sdf.format(groupBuy.getEndTime()));
				String codeUrlText = GetWeiXinCodeUrlText.getCodeRequest(thirdUser.getUsercenterKey(), groupId, "");
//				String real = servletContext.getRealPath("");
				DateFormat df = new SimpleDateFormat("yyyy/MM/dd");
				String QRdateDir = df.format(new Date());
				// 创建目录
//				File root = new File(real, "shop/" + QRdateDir);
//				if (!root.exists()) {
//					root.mkdirs();
//				}
				String uuid = UUID.randomUUID().toString();
				String fileName = uuid + ".png";
				// 保存为临时文件
//				File tempFile = new File(root, fileName);
//				GetWeiXinCodeUrlText.getWeiXinCodeUrl(root + "/" + fileName, codeUrlText);
				String aliFilePath = "shop/" + QRdateDir;
				tCodeByMember.setUrlPath(aliFilePath);
				tCodeByMember.setFileName(fileName);
				tCodeByMember.setUrlText(codeUrlText);
				StringBuffer keyBuffer1 = CacheUtil.getCachekeyMethodPrx(CacheUtil.cacheKey.buyHistory.KEY_TCODE, openId+groupId+orderId,
						"saveTCode", new Object[] {  });
				Object object1 = CacheUtil.getObjectValue(keyBuffer1.toString());
				if (object1 == null) {
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("memberId", tCodeByMember.getMemberId());
					map.put("groupId", tCodeByMember.getGroupId());
					map.put("groupName", tCodeByMember.getGroupName());
					map.put("groupStartdate", tCodeByMember.getGroupStartdate());
					map.put("groupEnddate", tCodeByMember.getGroupEnddate());
					map.put("urlPath", tCodeByMember.getUrlPath());
					map.put("fileName", tCodeByMember.getFileName());
					map.put("urlText", tCodeByMember.getUrlText());
					CacheUtil.put(keyBuffer1.toString(), map);
				}
				
				
			}
		}

	}

	@Override
	public TCodeByMember findById(Long id) {
		return  tCodeByMemberDao.findById(id);
	}

	

}
