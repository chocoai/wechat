package com.miyzh.wechatshop.groupbuy.service.impl;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.joda.time.Hours;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.miyzh.framework.util.CacheUtil;
import com.miyzh.wechatshop.groupbuy.bean.GroupBuy;
import com.miyzh.wechatshop.groupbuy.bean.GroupBuyArea;
import com.miyzh.wechatshop.groupbuy.bean.GroupBuyItem;
import com.miyzh.wechatshop.groupbuy.dao.IGroupBuyAreaDao;
import com.miyzh.wechatshop.groupbuy.dao.IWcGroupBuyDao;
import com.miyzh.wechatshop.groupbuy.dao.IWcGroupBuyItemDao;
import com.miyzh.wechatshop.groupbuy.report.GroupbuyPreview;
import com.miyzh.wechatshop.groupbuy.report.GroupbuyQueryResult;
import com.miyzh.wechatshop.groupbuy.service.IWcGroupBuyService;
import com.miyzh.wechatshop.user.bean.CheckUserResult;
import com.miyzh.wechatshop.user.bean.UserBean;
import com.miyzh.wechatshop.user.service.IUserService;
/**
 * 
 * @author liangpeichang
 *
 * date 2016年7月8日
 */
@Service
public class WcGroupBuyServiceImpl implements IWcGroupBuyService {
	
	private Logger log = Logger.getLogger(this.getClass());
	
	/** 团购预览页级别*/
	private static final String QUERY_LEVEL_PREVIEW = "query_level_preview";
	/** 团购预览页缓存*/
	private static final String QUERY_LEVEL_LIST = "query_level_list";
	/** 团购预览页缓存*/
	private static final String QUERY_LEVEL_INFO = "query_level_info";
	
	@Autowired
	private IWcGroupBuyDao iWcGroupBuyDao;
	
	@Autowired
	private IWcGroupBuyItemDao itemDao;
	
	@Autowired
	private IUserService userService;
	
	@Autowired
	private IGroupBuyAreaDao groupBuyAreaDao;
	
	@Override
	public Integer queryGroupbuyItemCount(String groupbuyId){
		StringBuffer keyBuffer = CacheUtil.getCachekeyMethodPrx(
				CacheUtil.cacheKey.GroupBuy.KEY_GROUPBUY_ITEM_COUNT, groupbuyId, Thread
						.currentThread().getStackTrace()[1].getMethodName(),
				new Object[] {});

		Object object = CacheUtil.getObjectValue(keyBuffer.toString());
		Integer itemCount = 0;
		if(object == null){
			itemCount = itemDao.queryCountByGroupBuyId(groupbuyId);
			CacheUtil.put(keyBuffer.toString(), itemCount);
			return itemCount;
		}else if((Integer)object == 0){
			itemCount = itemDao.queryCountByGroupBuyId(groupbuyId);
			CacheUtil.put(keyBuffer.toString(), itemCount);
			return itemCount;
		}
		return (Integer)object;
	}
	
	@Override
	public GroupbuyQueryResult queryGroupbuyItemDetail(GroupbuyPreview requestParam){
		GroupbuyQueryResult result = new GroupbuyQueryResult();
		
		result = this.queryGroupbuyDetail(requestParam);
		
		List<GroupBuyItem> newItemList = new ArrayList<GroupBuyItem>();
		
		if(result.getGroupbuy() != null){
			long itemId = Long.parseLong(requestParam.getItemid());
			for (GroupBuyItem groupBuyItem : result.getGroupbuy().getItemList()) {
				if(groupBuyItem.getId() == itemId){
					newItemList.add(groupBuyItem);
					break;
				}
			}
			result.getGroupbuy().setItemList(newItemList);
		}else{
			log.info("queryGroupbuyItemDetail 无团购对象!");
		}
		
		return result;
	}
	
	@Override
	public GroupbuyQueryResult queryGroupbuyDetail(GroupbuyPreview requestParam){
		GroupbuyQueryResult result = new GroupbuyQueryResult();
		
		CheckUserResult checkUserResult = userService.checkUserType(requestParam.getOpenid());
		
		GroupBuy groupBuy = this.queryGroupBuyByRole(checkUserResult.getUserType(),requestParam.getGroupbuyid(),QUERY_LEVEL_INFO);
		
		if(groupBuy!=null){
			//验证团购时间是否符合
			LocalDate today = new LocalDate();
			LocalDate startTime = new LocalDate(groupBuy.getStartTime());
			LocalDate endTime = new LocalDate(groupBuy.getEndTime());
			if((today.isAfter(startTime) || today.isEqual(startTime))
					&& (today.isBefore(endTime) || today.isEqual(endTime))){
				result.setResult(GroupbuyQueryResult.RESULT_SUCCESS);
			}else{
				result.setResult(GroupbuyQueryResult.RESULT_END);
			}
			
			result.setGroupbuy(groupBuy);
		}else{
			//无权访问
			if("0".equals(checkUserResult.getUserType())){
				result.setResult(GroupbuyQueryResult.RESULT_NO_NOUSER_ROLE);
			}else if("1".equals(checkUserResult.getUserType())){
				//判断用户是哪种状态,0-未提交 2-审核中 3-审核不通过
				if("0".equals(checkUserResult.getStatus())){
					result.setResult(GroupbuyQueryResult.RESULT_NO_REGUSER_ROLE);
				}else if("2".equals(checkUserResult.getStatus())){
					result.setResult(GroupbuyQueryResult.RESULT_AUDITING_ROLE);
				}else if("3".equals(checkUserResult.getStatus())){
					result.setResult(GroupbuyQueryResult.RESULT_NO_PASS_ROLE);
					result.setCertificateOpinion(checkUserResult.getCertificateOpinion());
				}else{
					result.setResult(GroupbuyQueryResult.RESULT_NO_REGUSER_ROLE);
				}
			}
		}
		
		return result;
	}
	
	@Override
	public GroupbuyQueryResult queryGroupbuyPreview(GroupbuyPreview requestParam){
		GroupbuyQueryResult result = new GroupbuyQueryResult();
		
		CheckUserResult checkUserResult = userService.checkUserType(requestParam.getOpenid());
		
		GroupBuy groupBuy = this.queryGroupBuyByRole(checkUserResult.getUserType(),requestParam.getGroupbuyid(),QUERY_LEVEL_PREVIEW);
		
		int visibleLevel = -1;
		int userTypeLevel = Integer.parseInt(checkUserResult.getUserType());
		if(groupBuy.getDetailVisibleLevel()!=null){
			visibleLevel = Integer.parseInt(groupBuy.getDetailVisibleLevel());
		}
		
		if(userTypeLevel>=visibleLevel){
			//通过验证直接跳转
			result.setResult(GroupbuyQueryResult.RESULT_SUCCESS);
		}else{
			//不通过验证则判断状态
			//验证团购时间是否符合
			LocalDate today = new LocalDate();
			LocalDate startTime = new LocalDate(groupBuy.getStartTime());
			LocalDate endTime = new LocalDate(groupBuy.getEndTime());
			if((today.isAfter(startTime) || today.isEqual(startTime))
					&& (today.isBefore(endTime) || today.isEqual(endTime))){
				//无权访问
				if("0".equals(checkUserResult.getUserType())){
					result.setResult(GroupbuyQueryResult.RESULT_NO_NOUSER_ROLE);
				}else if("1".equals(checkUserResult.getUserType())){
					//判断用户是哪种状态,0-未提交 2-审核中 3-审核不通过
					if("0".equals(checkUserResult.getStatus())){
						result.setResult(GroupbuyQueryResult.RESULT_NO_REGUSER_ROLE);
					}else if("2".equals(checkUserResult.getStatus())){
						result.setResult(GroupbuyQueryResult.RESULT_AUDITING_ROLE);
					}else if("3".equals(checkUserResult.getStatus())){
						result.setResult(GroupbuyQueryResult.RESULT_NO_PASS_ROLE);
						result.setCertificateOpinion(checkUserResult.getCertificateOpinion());
					}else {
						result.setResult(GroupbuyQueryResult.RESULT_NO_REGUSER_ROLE);
					}
				}
			}else{
				result.setResult(GroupbuyQueryResult.RESULT_END);
			}
			result.setGroupbuy(groupBuy);
		}
		
		return result;
	}
	
	/**
	 * <pre>
	 * 根据用户权限\团定义ID查询团信息.
	 * </pre>
	 *
	 * @param userType
	 * @param groupBuyId
	 * @param queryLevel
	 * @return
	 * @author zhangtao 2016年7月23日
	 */
	private GroupBuy queryGroupBuyByRole(String userType,String groupBuyId,String queryLevel){
		StringBuffer keyBuffer = CacheUtil.getCachekeyMethodPrx(
				CacheUtil.cacheKey.GroupBuy.KEY_GROUPBUY_INFO, groupBuyId, Thread
						.currentThread().getStackTrace()[1].getMethodName(),
				new Object[] {queryLevel});

		Object object = CacheUtil.getObjectValue(keyBuffer.toString());
		GroupBuy groupBuy = (GroupBuy)object;
		int visibleLevel = -1;
		int userTypeLevel = Integer.parseInt(userType);
		if(groupBuy == null){
			groupBuy = this.findById(groupBuyId);
			//获取限制地区名称
			if("1".equals(groupBuy.getIsRestrictArea())){
				List<GroupBuyArea> groupBuyArealist = groupBuyAreaDao.queryAreaByGroupBuyId(groupBuyId);
				StringBuilder area = new StringBuilder();
				for (GroupBuyArea groupBuyArea : groupBuyArealist) {
					area.append(groupBuyArea.getCountryName()).append("/");
				}
				if(!"".equals(area)){
					area.deleteCharAt(area.length()-1);
				}
				groupBuy.setCountryName(area.toString());
			}
			// 2016/08/27 00:00:00
			groupBuy.setEndDateForJs(new DateTime(groupBuy.getEndTime()).toString("yyyy/MM/dd")+" 23:59:59");
			//转换可见级别
			if(QUERY_LEVEL_PREVIEW.equals(queryLevel)){
				if(groupBuy.getPreviewVisibleLevel()!=null){
					visibleLevel = Integer.parseInt(groupBuy.getPreviewVisibleLevel());
				}
				List<GroupBuyItem> itemList = itemDao.findByGroupBuyId(groupBuyId);
				for (GroupBuyItem groupBuyItem : itemList) {
					groupBuyItem.setSalePrice(null);
					groupBuyItem.setGroupPrice(null);
				}
				groupBuy.setItemList(itemList);
			}else if(QUERY_LEVEL_INFO.equals(queryLevel)){
				if(groupBuy.getDetailVisibleLevel()!=null){
					visibleLevel = Integer.parseInt(groupBuy.getDetailVisibleLevel());
				}
				List<GroupBuyItem> itemList = itemDao.findByGroupBuyId(groupBuyId);
				groupBuy.setItemList(itemList);
			}else if(QUERY_LEVEL_LIST.equals(queryLevel)){
				if(groupBuy.getOverviewVisibleLevel()!=null){
					visibleLevel = Integer.parseInt(groupBuy.getOverviewVisibleLevel());
				}
			}
			
			//计算进度
			BigDecimal count = new BigDecimal(groupBuy.getCdfined3()==null?"0":groupBuy.getCdfined3());
			BigDecimal totalCount = new BigDecimal(groupBuy.getMinGroupMember()==null?0:groupBuy.getMinGroupMember());
			if(!totalCount.equals(BigDecimal.ZERO)){
				groupBuy.setSchedule(count.divide(totalCount, 2, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(100)).intValue()+"%");
			}else{
				groupBuy.setSchedule("0%");
			}
			
			CacheUtil.put(keyBuffer.toString(), groupBuy);
			
		}
		//转译剩余时间
		groupBuy.setRestTime(getRestTime(groupBuy.getEndTime(),1));
		//转换可见级别
		if(QUERY_LEVEL_PREVIEW.equals(queryLevel)){
			if(groupBuy.getPreviewVisibleLevel()!=null){
				visibleLevel = Integer.parseInt(groupBuy.getPreviewVisibleLevel());
			}
		}else if(QUERY_LEVEL_INFO.equals(queryLevel)){
			if(groupBuy.getDetailVisibleLevel()!=null){
				visibleLevel = Integer.parseInt(groupBuy.getDetailVisibleLevel());
			}
		}else if(QUERY_LEVEL_LIST.equals(queryLevel)){
			if(groupBuy.getOverviewVisibleLevel()!=null){
				visibleLevel = Integer.parseInt(groupBuy.getOverviewVisibleLevel());
			}
		}
		if(userTypeLevel<visibleLevel){//如果用户级别大于等于可见级别则可见 0:未注册 1-已注册 2-已认证
			if(!QUERY_LEVEL_PREVIEW.equals(queryLevel)){
				//如果不是预览页级别不可用,则不返回信息
				groupBuy = null;
			}
		}
		
		return groupBuy;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<GroupBuy> queryGroupBuyList(String openId){
		//判断是否注册用户
		CheckUserResult result = userService.checkUserType(openId);
		String userType = result.getUserType();
		
		LocalDate today = new LocalDate();
		
		StringBuffer keyBuffer = CacheUtil.getCachekeyMethodPrx(
				CacheUtil.cacheKey.GroupBuy.KEY_GROUPBUY_NOW_LIST, openId, Thread
						.currentThread().getStackTrace()[1].getMethodName(),
				new Object[] {today});

		Object object = CacheUtil.getObjectValue(keyBuffer.toString());
		
		List<GroupBuy> list = (List<GroupBuy>)object;
		
		if(list == null || list.isEmpty()){
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("today", today.toString("yyyy-MM-dd"));
			map.put("overviewVisibleLevel", userType);
			//如果用户为认证以上的用户,则查询区域
			if("2".equals(userType)){
				String countryCode = result.getUserBean().getUserAreaCode();
				map.put("countryCode", countryCode);
			}
			list = iWcGroupBuyDao.findNowGroupBuyInfo(map);
			CacheUtil.put(keyBuffer.toString(), list);
		}
		//获取剩余天数
		for (GroupBuy groupBuy : list) {
			groupBuy.setRestTime(getRestTime(groupBuy.getEndTime(),1));
		}
		return list;
	}
	/**
	 * desc 查询已经结束的团
	 * @param openId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<GroupBuy> queryFinishGroupBuyList(String openId){
		//判断是否注册用户
		CheckUserResult checkUserResult = userService.checkUserType(openId);
		UserBean user = checkUserResult.getUserBean();
		String userType = checkUserResult.getUserType();
		String today = new LocalDate().toString("yyyy-MM-dd");
		
		StringBuffer keyBuffer = CacheUtil.getCachekeyMethodPrx(
				CacheUtil.cacheKey.GroupBuy.KEY_GROUPBUY_FINISH_LIST, openId, Thread
						.currentThread().getStackTrace()[1].getMethodName(),
				new Object[] {today});

		Object object = CacheUtil.getObjectValue(keyBuffer.toString());
		
		List<GroupBuy> list = (List<GroupBuy>)object;
		
		if(list == null || list.isEmpty()){
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("today", today);
			map.put("overviewVisibleLevel", userType);
			//如果用户为注册以上的用户,则查询区域
			if("2".equals(userType)){
				String countryCode = user.getUserAreaCode();
				map.put("countryCode", countryCode);
			}
			list = iWcGroupBuyDao.findFinishGroupBuyInfo(map);
			CacheUtil.put(keyBuffer.toString(), list);
		}
		
		return list;
	}
	/**
	 * desc 查询未开始的团购信息
	 * @param openId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<GroupBuy> queryNoStartGroupBuyList(String openId){
		//判断是否注册用户
		CheckUserResult checkUserResult = userService.checkUserType(openId);
		UserBean user = checkUserResult.getUserBean();
		String userType = checkUserResult.getUserType();
		String today = new LocalDate().toString("yyyy-MM-dd");
		
		StringBuffer keyBuffer = CacheUtil.getCachekeyMethodPrx(
				CacheUtil.cacheKey.GroupBuy.KEY_GROUPBUY_NOSTART_LIST, openId, Thread
						.currentThread().getStackTrace()[1].getMethodName(),
				new Object[] {today});

		Object object = CacheUtil.getObjectValue(keyBuffer.toString());
		
		List<GroupBuy> list = (List<GroupBuy>)object;
		
		if(list == null || list.isEmpty()){
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("today", today);
			map.put("overviewVisibleLevel", userType);
			//如果用户为注册以上的用户,则查询区域
			if("2".equals(userType)){
				String countryCode = user.getUserAreaCode();
				map.put("countryCode", countryCode);
			}
			list = iWcGroupBuyDao.findNoStartGroupBuyInfo(map);
			CacheUtil.put(keyBuffer.toString(), list);
		}
		//获取距离开始的时间
		for (GroupBuy groupBuy : list) {
			groupBuy.setRestStartTime(getRestTime(groupBuy.getStartTime(),0));
		}
		return list;
	}
	
	@Override
	public GroupBuy findById(String id) {
		return iWcGroupBuyDao.findByGroupId(id);
	}
	@Override
	public List<GroupBuy> getList(GroupBuy groupBuy) {
		return null;
	}
	
	/**
	 * <pre>
	 * the getRestTime method for
	 * 获取剩余时间
	 * </pre>
	 *
	 * @param endTime
	 * @return
	 * @author zhangtao 2016年7月14日
	 */
	private String getRestTime(Date endTime,int type) {
		DateTime today = new DateTime();
		LocalDate endDay = new LocalDate(endTime);
		DateTimeFormatter format = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
		DateTime endDateTime = null;
		if(type == 1){
			endDateTime = DateTime.parse(endDay.toString("yyyy-MM-dd")+" 23:59:59",format);
		}else{
			endDateTime = DateTime.parse(endDay.toString("yyyy-MM-dd")+" 00:00:00",format);
		}
		int restHourCount = Hours.hoursBetween(today,endDateTime).getHours();
		int restDay = restHourCount/24;
		int restHour = restHourCount%24;
		StringBuffer sb = new StringBuffer();
		sb.append(restDay).append("天").append(restHour).append("小时");
		return sb.toString();
	}

}
