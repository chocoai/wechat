package com.miyzh.wechatshop.groupbuy.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.miyzh.framework.base.Constant;
import com.miyzh.framework.base.action.BaseAction;
import com.miyzh.framework.base.action.reply.BaseReplyBean;
import com.miyzh.framework.base.action.reply.BaseReplyResult;
import com.miyzh.wechatshop.groupbuy.bean.BrowseRecord;
import com.miyzh.wechatshop.groupbuy.bean.GroupBuy;
import com.miyzh.wechatshop.groupbuy.report.GroupbuyQueryResult;
import com.miyzh.wechatshop.groupbuy.report.RequestReportGroupbuyPreview;
import com.miyzh.wechatshop.groupbuy.service.IBrowseRecordService;
import com.miyzh.wechatshop.groupbuy.service.IWcGroupBuyService;

@Controller
@RequestMapping("/groupbuyAction")
public class GroupbuyAction extends BaseAction{
	
	private Logger log = Logger.getLogger(this.getClass());
	
	@Autowired
	private IWcGroupBuyService service;
	
	@Autowired
	private IBrowseRecordService browseRecordService;
	
	/**
	 * <pre>
	 * the queryGroupbuyList method for
	 * 正在疯抢
	 * </pre>
	 *
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * @author zhangtao 2016年7月11日
	 */
	@RequestMapping("/queryGroupbuyList")
	@ResponseBody
	public String queryGroupbuyList(HttpServletRequest request, HttpServletResponse response) throws Exception{
		String openId = null;
		BaseReplyBean<GroupBuy> reply = new BaseReplyBean<GroupBuy>();
		BaseReplyResult baseReplyResult = new BaseReplyResult();
		try {
			String jsonObjectStr = parseRequestReportToString(request, response);
			JSONObject jsonObject = getJsonObject(jsonObjectStr);
			openId = jsonObject.getJSONObject("commandinfo").getString("openid");
			List<GroupBuy> list = service.queryGroupBuyList(openId);
			baseReplyResult.setCode(Constant.ResultCode.SUCCESS);
			baseReplyResult.setBusinessCode(Constant.ResultCode.SUCCESS);
			reply.setResult(baseReplyResult);
			reply.setObjList(list);
		} catch (Exception e) {
			log.error("获取正在疯抢异常,openid->"+openId,e);
			baseReplyResult.setCode(Constant.ResultCode.FILURE);
			baseReplyResult.setBusinessCode(Constant.ResultCode.FILURE);
			reply.setResult(baseReplyResult);
		}
		responseClient(response, JSON.toJSONString(reply));
		return null;
	}
	/**
	 * 团购已经结束
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * @author liangpeichang 2016年7月18日
	 */
	@RequestMapping("/queryFinishGroupBuyList")
	@ResponseBody
	public String queryFinishGroupBuyList(HttpServletRequest request, HttpServletResponse response) throws Exception{
		String openId = null;
		BaseReplyBean<GroupBuy> reply = new BaseReplyBean<GroupBuy>();
		BaseReplyResult baseReplyResult = new BaseReplyResult();
		try {
			String jsonObjectStr = parseRequestReportToString(request, response);
			JSONObject jsonObject = getJsonObject(jsonObjectStr);
			openId = jsonObject.getJSONObject("commandinfo").getString("openid");
			List<GroupBuy> list = service.queryFinishGroupBuyList(openId);
			baseReplyResult.setCode(Constant.ResultCode.SUCCESS);
			baseReplyResult.setBusinessCode(Constant.ResultCode.SUCCESS);
			reply.setResult(baseReplyResult);
			reply.setObjList(list);
		} catch (Exception e) {
			log.error("获取团购异常,openid->"+openId,e);
			baseReplyResult.setCode(Constant.ResultCode.FILURE);
			baseReplyResult.setBusinessCode(Constant.ResultCode.FILURE);
			reply.setResult(baseReplyResult);
		}
		responseClient(response, JSON.toJSONString(reply));
		return null;
	}
	/**
	 * 团购敬请期待
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * @author liangpeichang 2016年7月18日
	 */
	@RequestMapping("/queryNoStartGroupBuyList")
	@ResponseBody
	public String queryNoStartGroupBuyList(HttpServletRequest request, HttpServletResponse response) throws Exception{
		String openId = null;
		BaseReplyBean<GroupBuy> reply = new BaseReplyBean<GroupBuy>();
		BaseReplyResult baseReplyResult = new BaseReplyResult();
		try {
			String jsonObjectStr = parseRequestReportToString(request, response);
			JSONObject jsonObject = getJsonObject(jsonObjectStr);
			openId = jsonObject.getJSONObject("commandinfo").getString("openid");
			List<GroupBuy> list = service.queryNoStartGroupBuyList(openId);
			baseReplyResult.setCode(Constant.ResultCode.SUCCESS);
			baseReplyResult.setBusinessCode(Constant.ResultCode.SUCCESS);
			reply.setResult(baseReplyResult);
			reply.setObjList(list);
		} catch (Exception e) {
			log.error("获取敬请期待异常,openid->"+openId,e);
			baseReplyResult.setCode(Constant.ResultCode.FILURE);
			baseReplyResult.setBusinessCode(Constant.ResultCode.FILURE);
			reply.setResult(baseReplyResult);
		}
		responseClient(response, JSON.toJSONString(reply));
		return null;
	}
	
	/**
	 * <pre>
	 * the queryGroupbuyPreview method for
	 * 团购预览页
	 * </pre>
	 *
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * @author zhangtao 2016年7月11日
	 */
	@RequestMapping("/queryGroupbuyPreview")
	@ResponseBody
	public String queryGroupbuyPreview(HttpServletRequest request, HttpServletResponse response) throws Exception{
		BaseReplyBean<GroupBuy> reply = new BaseReplyBean<GroupBuy>();
		BaseReplyResult baseReplyResult = new BaseReplyResult();
		RequestReportGroupbuyPreview requestParam = null;
		try {
			String jsonObjectStr = parseRequestReportToString(request, response);
			JSONObject jsonObject = getJsonObject(jsonObjectStr);
			requestParam = JSON.toJavaObject(jsonObject, RequestReportGroupbuyPreview.class);
			
			GroupbuyQueryResult result = service.queryGroupbuyPreview(requestParam.getCommandinfo());
			
			//埋点
			BrowseRecord br = new BrowseRecord();
			br.setOpenId(requestParam.getCommandinfo().getOpenid());
			br.setRecommedUserKey(requestParam.getCommandinfo().getRecommenduserid());
			br.setGrouponKey(requestParam.getCommandinfo().getGroupbuyid());
			br.setBrowseType("1");
			br.setBrowseDate(LocalDate.now().toString("yyyy-MM-dd"));
			browseRecordService.addBrowseRecord(br);
			
			if(GroupbuyQueryResult.RESULT_SUCCESS == result.getResult()){
				baseReplyResult.setCode(Constant.ResultCode.SUCCESS);
				baseReplyResult.setBusinessCode(Constant.ResultCode.SUCCESS);
				reply.setResult(baseReplyResult);
			}else if(GroupbuyQueryResult.RESULT_NO_NOUSER_ROLE == result.getResult()){
				baseReplyResult.setCode(Constant.ResultCode.SUCCESS);
				baseReplyResult.setBusinessCode(Constant.ResultCode.RESULT_TWO);
				reply.setResult(baseReplyResult);
				reply.setObj(result.getGroupbuy());
			}else if(GroupbuyQueryResult.RESULT_NO_REGUSER_ROLE == result.getResult()){
				baseReplyResult.setCode(Constant.ResultCode.SUCCESS);
				baseReplyResult.setBusinessCode(Constant.ResultCode.RESULT_THREE);
				reply.setResult(baseReplyResult);
				reply.setObj(result.getGroupbuy());
			}else if(GroupbuyQueryResult.RESULT_END == result.getResult()){
				baseReplyResult.setCode(Constant.ResultCode.SUCCESS);
				baseReplyResult.setBusinessCode(Constant.ResultCode.RESULT_FOURE);
				reply.setResult(baseReplyResult);
				reply.setObj(result.getGroupbuy());
			}else if(GroupbuyQueryResult.RESULT_AUDITING_ROLE == result.getResult()){
				baseReplyResult.setCode(Constant.ResultCode.SUCCESS);
				baseReplyResult.setBusinessCode(Constant.ResultCode.RESUlT_FIVE);
				reply.setResult(baseReplyResult);
			}else if(GroupbuyQueryResult.RESULT_NO_PASS_ROLE == result.getResult()){
				baseReplyResult.setCode(Constant.ResultCode.SUCCESS);
				baseReplyResult.setBusinessCode(Constant.ResultCode.RESULT_SIX);
				baseReplyResult.setMsg(result.getCertificateOpinion());
				reply.setResult(baseReplyResult);
			}
			
		} catch (Exception e) {
			log.error("获取团购预览页异常,"+requestParam,e);
			baseReplyResult.setCode(Constant.ResultCode.FILURE);
			baseReplyResult.setBusinessCode(Constant.ResultCode.FILURE);
			reply.setResult(baseReplyResult);
		}
		responseClient(response, JSON.toJSONString(reply));
		return null;
	}
	
	/**
	 * <pre>
	 * the queryGroupbuyItemCount method for
	 * 获取团购商品数量
	 * </pre>
	 *
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * @author zhangtao 2016年7月11日
	 */
	@RequestMapping("/queryGroupbuyItemCount")
	@ResponseBody
	public String queryGroupbuyItemCount(HttpServletRequest request, HttpServletResponse response) throws Exception{
		BaseReplyBean<Integer> reply = new BaseReplyBean<Integer>();
		BaseReplyResult baseReplyResult = new BaseReplyResult();
		String groupbuyId = null;
		try {
			String jsonObjectStr = parseRequestReportToString(request, response);
			JSONObject jsonObject = getJsonObject(jsonObjectStr);
			groupbuyId = jsonObject.getJSONObject("commandinfo").getString("groupbuyid");
			
			Integer count = service.queryGroupbuyItemCount(groupbuyId);
			
			baseReplyResult.setCode(Constant.ResultCode.SUCCESS);
			baseReplyResult.setBusinessCode(Constant.ResultCode.SUCCESS);
			reply.setResult(baseReplyResult);
			reply.setObj(count);
		} catch (Exception e) {
			log.error("获取团购详情页异常,groupbuyId->"+groupbuyId,e);
			baseReplyResult.setCode(Constant.ResultCode.FILURE);
			baseReplyResult.setBusinessCode(Constant.ResultCode.FILURE);
			reply.setResult(baseReplyResult);
		}
		responseClient(response, JSON.toJSONString(reply));
		return null;
	}
	
	/**
	 * <pre>
	 * the queryGroupbuyPreview method for
	 * 团购详情页
	 * </pre>
	 *
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * @author zhangtao 2016年7月11日
	 */
	@RequestMapping("/queryGroupbuyDetail")
	@ResponseBody
	public String queryGroupbuyDetail(HttpServletRequest request, HttpServletResponse response) throws Exception{
		BaseReplyBean<GroupBuy> reply = new BaseReplyBean<GroupBuy>();
		BaseReplyResult baseReplyResult = new BaseReplyResult();
		RequestReportGroupbuyPreview requestParam = null;
		try {
			String jsonObjectStr = parseRequestReportToString(request, response);
			JSONObject jsonObject = getJsonObject(jsonObjectStr);
			requestParam = JSON.toJavaObject(jsonObject, RequestReportGroupbuyPreview.class);
			
			GroupbuyQueryResult result = service.queryGroupbuyDetail(requestParam.getCommandinfo());
			
			//埋点
			BrowseRecord br = new BrowseRecord();
			br.setOpenId(requestParam.getCommandinfo().getOpenid());
			br.setRecommedUserKey(requestParam.getCommandinfo().getRecommenduserid());
			br.setGrouponKey(requestParam.getCommandinfo().getGroupbuyid());
			br.setBrowseType("2");
			br.setBrowseDate(LocalDate.now().toString("yyyy-MM-dd"));
			browseRecordService.addBrowseRecord(br);
			
			if(GroupbuyQueryResult.RESULT_SUCCESS == result.getResult()){
				baseReplyResult.setCode(Constant.ResultCode.SUCCESS);
				baseReplyResult.setBusinessCode(Constant.ResultCode.SUCCESS);
				reply.setResult(baseReplyResult);
				reply.setObj(result.getGroupbuy());
			}else if(GroupbuyQueryResult.RESULT_NO_NOUSER_ROLE == result.getResult()){
				baseReplyResult.setCode(Constant.ResultCode.SUCCESS);
				baseReplyResult.setBusinessCode(Constant.ResultCode.RESULT_TWO);
				reply.setResult(baseReplyResult);
			}else if(GroupbuyQueryResult.RESULT_NO_REGUSER_ROLE == result.getResult()){
				baseReplyResult.setCode(Constant.ResultCode.SUCCESS);
				baseReplyResult.setBusinessCode(Constant.ResultCode.RESULT_THREE);
				reply.setResult(baseReplyResult);
			}else if(GroupbuyQueryResult.RESULT_END == result.getResult()){
				baseReplyResult.setCode(Constant.ResultCode.SUCCESS);
				baseReplyResult.setBusinessCode(Constant.ResultCode.RESULT_FOURE);
				reply.setResult(baseReplyResult);
				reply.setObj(result.getGroupbuy());
			}else if(GroupbuyQueryResult.RESULT_AUDITING_ROLE == result.getResult()){
				baseReplyResult.setCode(Constant.ResultCode.SUCCESS);
				baseReplyResult.setBusinessCode(Constant.ResultCode.RESUlT_FIVE);
				reply.setResult(baseReplyResult);
			}else if(GroupbuyQueryResult.RESULT_NO_PASS_ROLE == result.getResult()){
				baseReplyResult.setCode(Constant.ResultCode.SUCCESS);
				baseReplyResult.setBusinessCode(Constant.ResultCode.RESULT_SIX);
				baseReplyResult.setMsg(result.getCertificateOpinion());
				reply.setResult(baseReplyResult);
			}
			
		} catch (Exception e) {
			log.error("获取团购详情页异常,"+requestParam,e);
			baseReplyResult.setCode(Constant.ResultCode.FILURE);
			baseReplyResult.setBusinessCode(Constant.ResultCode.FILURE);
			reply.setResult(baseReplyResult);
		}
		responseClient(response, JSON.toJSONString(reply));
		return null;
	}
	
	/**
	 * <pre>
	 * the queryGroupbuyPreview method for
	 * 商品详情页
	 * </pre>
	 *
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * @author zhangtao 2016年7月11日
	 */
	@RequestMapping("/queryGroupbuyItemDetail")
	@ResponseBody
	public String queryGroupbuyItemDetail(HttpServletRequest request, HttpServletResponse response) throws Exception{
		BaseReplyBean<GroupBuy> reply = new BaseReplyBean<GroupBuy>();
		BaseReplyResult baseReplyResult = new BaseReplyResult();
		RequestReportGroupbuyPreview requestParam = null;
		try {
			String jsonObjectStr = parseRequestReportToString(request, response);
			JSONObject jsonObject = getJsonObject(jsonObjectStr);
			requestParam = JSON.toJavaObject(jsonObject, RequestReportGroupbuyPreview.class);
			
			GroupbuyQueryResult result = service.queryGroupbuyItemDetail(requestParam.getCommandinfo());
			
			if(GroupbuyQueryResult.RESULT_SUCCESS == result.getResult()){
				baseReplyResult.setCode(Constant.ResultCode.SUCCESS);
				baseReplyResult.setBusinessCode(Constant.ResultCode.SUCCESS);
				reply.setResult(baseReplyResult);
				reply.setObj(result.getGroupbuy());
			}else if(GroupbuyQueryResult.RESULT_NO_NOUSER_ROLE == result.getResult()){
				baseReplyResult.setCode(Constant.ResultCode.SUCCESS);
				baseReplyResult.setBusinessCode(Constant.ResultCode.RESULT_TWO);
				reply.setResult(baseReplyResult);
				reply.setObj(result.getGroupbuy());
			}else if(GroupbuyQueryResult.RESULT_NO_REGUSER_ROLE == result.getResult()){
				baseReplyResult.setCode(Constant.ResultCode.SUCCESS);
				baseReplyResult.setBusinessCode(Constant.ResultCode.RESULT_THREE);
				reply.setResult(baseReplyResult);
				reply.setObj(result.getGroupbuy());
			}else if(GroupbuyQueryResult.RESULT_END == result.getResult()){
				baseReplyResult.setCode(Constant.ResultCode.SUCCESS);
				baseReplyResult.setBusinessCode(Constant.ResultCode.RESULT_FOURE);
				reply.setResult(baseReplyResult);
				reply.setObj(result.getGroupbuy());
			}
			
		} catch (Exception e) {
			log.error("获取团购详情页异常,"+requestParam,e);
			baseReplyResult.setCode(Constant.ResultCode.FILURE);
			baseReplyResult.setBusinessCode(Constant.ResultCode.FILURE);
			reply.setResult(baseReplyResult);
		}
		responseClient(response, JSON.toJSONString(reply));
		return null;
	}

}
