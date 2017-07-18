package com.miyzh.wechatshop.uploadfile;

import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.miyzh.framework.aliyunoss.OssClientComponent;
import com.miyzh.framework.base.Constant;
import com.miyzh.framework.base.action.BaseAction;
import com.miyzh.framework.util.InputStreamToByteUtil;
import com.miyzh.framework.util.PropertiesUtil;

/**
 * 文件名： UploadFileAction.java<br>
 * 描述: 上传文件<br>
 * 修改人: wumingdi<br>
 * 修改时间： 2016年7月12日 <br>
 */
@Controller
@RequestMapping("/uploadFile")
public class UploadFileAction extends BaseAction {
	// 阿里云oss上传文件路径
	private final String OOS_UOLOAD_FILE_PATH = PropertiesUtil
			.getPropertyValue("aliyun.oss.filepath");

	/**
	 * 上传图片
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping("/uploadPicture")
	@ResponseBody
	public String uploadPicture(HttpServletRequest request,
			HttpServletResponse response) {

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("code", Constant.ResultCode.FILURE);

		DiskFileItemFactory factory = new DiskFileItemFactory();
		ServletFileUpload upload = new ServletFileUpload(factory);
		// 时间
		Calendar calendar = Calendar.getInstance();
		try {
			List items = upload.parseRequest(request);// 上传文件解析
			Iterator itr = items.iterator();
			while (itr.hasNext()) {
				FileItem item = (FileItem) itr.next();
				if (!item.isFormField()) {
					
					// 文件路径
					String filePath = OOS_UOLOAD_FILE_PATH;
					filePath = filePath.replace("YYYY",
							"" + calendar.get(Calendar.YEAR));
					filePath = filePath.replace("MM",
							"" + (calendar.get(Calendar.MONTH) + 1));
					filePath = filePath.replace("DD", "" + calendar.get(Calendar.DATE));

					// 文件名后缀
					String fileSuffix = item.getName();

					int index = fileSuffix.lastIndexOf(".");
					if (index != -1) {
						fileSuffix = fileSuffix.substring(index + 1);
					}
					filePath = filePath.replace("UUID", UUID.randomUUID().toString());
					filePath = filePath.replace("EXT", fileSuffix);
					boolean flag = OssClientComponent.uploadFileOrdinary(filePath,
							InputStreamToByteUtil.input2byte(item
									.getInputStream()));
					if (flag) {
						jsonObject.put("code", Constant.ResultCode.SUCCESS);
						jsonObject.put(item.getFieldName(), filePath);
					}

				}
			}
		} catch (Exception e) {
			log.error(
					"In the method UploadFileAction.uploadPicture(HttpServletRequest request,HttpServletResponse response) exists error!",
					e);
		}

		responseClient(response, jsonObject.toJSONString());

		return "";
	}
}
