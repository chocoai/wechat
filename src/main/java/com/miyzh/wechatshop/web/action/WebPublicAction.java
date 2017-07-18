package com.miyzh.wechatshop.web.action;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.miyzh.framework.base.action.BaseAction;
import com.miyzh.framework.util.CacheUtil;
import com.miyzh.framework.util.DataDicmUtil;
import com.miyzh.framework.util.PropertiesUtil;
import com.miyzh.wechatshop.web.bean.WebPublic;

/**
 * 文件名: WebPublicAction.java<br>
 * 版权：Copyright 2014 Digital Bay Technology Group. Co. Ltd. All Rights Reserved. <br>
 * 描述: web端调用接口服务公共接口 <br>
 * 修改人: guchangpeng <br>
 * 修改时间：2014-04-25<br>
 * 修改内容：新增<br>
 */
@Controller
@RequestMapping("/web")
public class WebPublicAction extends BaseAction {

	@RequestMapping("/refreshCache")
	public ModelAndView refreshCache(WebPublic webPublic, Model model,
			HttpSession session) {
		String keysString = webPublic.getKeysStr();
		if (StringUtils.isNotBlank(keysString)) {
			CacheUtil.syncOperate(keysString.split(","), true);

			model.addAttribute("msg", "清除缓存成功");
		} else {
			model.addAttribute("msg", "删除的key不能为空");
		}
		return new ModelAndView(new RedirectView("../refresh.jsp"));
//		return "../refresh";

	}

	@RequestMapping("/rememoAllCache")
	public ModelAndView rememoAllCache(Model model) {
		CacheUtil.removeAll();
		Map<String,String> param = new HashMap<String,String>();
		param.put("msg", "清除所有缓存成功");
		return new ModelAndView(new RedirectView("../refresh.jsp"),param); 
//		return "../refresh";
	}

	@RequestMapping("/refreshProperty")
	public ModelAndView refreshProperty(WebPublic webPublic, Model model,
			HttpSession session) {
		String keyName = webPublic.getKeyname();
		String keyValue = webPublic.getKeyvalue();
		if (StringUtils.isNotBlank(keyName) && StringUtils.isNotBlank(keyValue)) {
			PropertiesUtil.updateProperties(keyName.trim(), keyValue.trim());
			model.addAttribute("msg2", "修改成功");
		} else {
			model.addAttribute("msg3", "请输入要修改的内容");
		}
		return new ModelAndView(new RedirectView("../refresh.jsp"));
//		return "../refresh";
	}

	/**
	 * 查询数据字典某一个值是否存在
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/index")
	@ResponseBody
	public String index(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String result = "";

		String categoryCode = request.getParameter("categorycode");
		String dataid = request.getParameter("dataid");
		String occurrenceValue = request.getParameter("value");
		if (StringUtils.isNotBlank(categoryCode)
				&& StringUtils.isNotBlank(dataid)) {
			String userMacListStr = DataDicmUtil.getDataDicmValue(categoryCode,
					dataid);
			if (StringUtils.isNotBlank(userMacListStr)) {
				List<String> userMacList = Arrays.asList(userMacListStr
						.split(","));
				if (!userMacList.contains(occurrenceValue)) {
					result = "failure";
				} else {
					result = "success";
				}
			}
		}
		responseClient(response, result);
		return null;
	}

}
