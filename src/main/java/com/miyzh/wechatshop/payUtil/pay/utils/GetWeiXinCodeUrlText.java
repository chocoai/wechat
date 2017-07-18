package com.miyzh.wechatshop.payUtil.pay.utils;

import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.Hashtable;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.miyzh.framework.aliyunoss.OssClientComponent;
import com.miyzh.framework.util.PropertiesUtil;
import com.miyzh.wechatshop.payUtil.pay.zxing.MatrixToImageWriter;

public class GetWeiXinCodeUrlText {
	public static String  GetCodeRequest = "openid=OPENID&recommenduserid=RECOMMENDUSERID&groupbuyid=GROUPBUYID#wechat_redirect";
    public static String getCodeRequest(String recommenduserid,String groupId,String openid){
    	String urlPost=PropertiesUtil.getPropertyValue("pathUrl")+"html/preview/lists.html?";
        String result = null;
        GetCodeRequest  = GetCodeRequest.replace("OPENID", openid);
        GetCodeRequest  = GetCodeRequest.replace("RECOMMENDUSERID",recommenduserid);
        GetCodeRequest = GetCodeRequest.replace("GROUPBUYID", groupId);
        result =urlPost+GetCodeRequest;
        return result;
    }
    public static String urlEnodeUTF8(String str){
        String result = str;
        try {
            result = URLEncoder.encode(str,"UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
    
    
    public static void getWeiXinCodeUrl(String path,String codeUrlText) {
        int width = 300; // 二维码图片宽度  
        int height = 300; // 二维码图片高度  
        String format = "png";// 二维码的图片格式  
         
        Hashtable<EncodeHintType, String> hints = new Hashtable<EncodeHintType, String>();  
        hints.put(EncodeHintType.CHARACTER_SET, "utf-8");   // 内容所使用字符集编码  
          
        BitMatrix bitMatrix = null;
		try {
			bitMatrix = new MultiFormatWriter().encode(codeUrlText, BarcodeFormat.QR_CODE, width, height, hints);
		} catch (WriterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
        // 生成二维码  
        File outputFile = new File(path);  
        try {
			MatrixToImageWriter.writeToFile(bitMatrix, format, outputFile);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
    }  

}
