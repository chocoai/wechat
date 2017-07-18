$(function () {
	wx.ready(function() { //隐藏微信分享
        wx.hideOptionMenu();
    });
	
	   var params = vge.urlparse(location.href);
       if (params.openid == undefined) {
           var requestParamOpenid = getRequestParam();
           requestParamOpenid.commandinfo.code = params.code;
           if (params.code != undefined) {
               vge.ajxpost(vge.japi + '/wctUserInfo/getWctSnsapiBase.do', JSON.stringify(requestParamOpenid), 10000, function (data) {
                   var jo = JSON.parse(data);
                   if (jo.result.code == 0 && jo.reply != undefined) {
                       if (jo.reply.obj.openid != undefined) {
                           var paramArr = params.paramstr.split("@");
                           $("#clinic_code").val(paramArr[1]);
                    	   $("#user_centerid").val(paramArr[0]);
                    	   $("#open_id").val(jo.reply.obj.openid);
                    	   ydbUserInfo(paramArr[1]);
                       }else{
   		        		WeixinJSBridge.call('closeWindow');
   					}
                   } else {
                       alert("openid加载失败,失败原因->" + jo.result.msg + ",失败代码->" + jo.result.code + ",业务代码->" + jo.result.businesscode);
                   }
               }, function (t) {
                   alert(t);
               });
           } else {
               //跳转获取页面原参数
               var url = vge.webcontext + "/html/register/register_bingding.html?paramstr=" + params.userCenterId + "@" + params.clinicCode;
               sendRedirectByGetCode(url);
           }
       } else {
    	   $("#clinic_code").val(params.clinicCode);
    	   $("#user_centerid").val(params.userCenterId);
    	   $("#open_id").val(params.openid);
    	   ydbUserInfo(params.clinicCode);
       }
       
       $("#register_bingding").submit(function () {
    	   var valiFlag = $("#register_bingding").valid();
           if (valiFlag) {
	           	var requestParamOpenid = getRequestParam();
	            var registerObj = new Object();
	            requestParamOpenid.commandinfo.register = registerObj;
	           	requestParamOpenid.commandinfo.openid = $("#open_id").val();
	           	requestParamOpenid.commandinfo.cliniccode = $("#clinic_code").val();
	           	requestParamOpenid.commandinfo.userkey = $("#user_centerid").val();
	           	requestParamOpenid.commandinfo.register.phonenum = $("#phone").val();
	           	requestParamOpenid.commandinfo.register.vericode = $("#code").val();
	           	vge.ajxpost(vge.japi + '/clinic/scanOldUserBingdingClinic.do', JSON.stringify(requestParamOpenid), 10000, function (data) {
	               var jo = JSON.parse(data);
	               if (jo.result.code == 0 && jo.result.businesscode == 0) {
	            	   window.location = vge.webcontext + "/html/register/bingding_suuccess.html?openid=" + $("#open_id").val() + "&v=" + Math.random();
	               } else {
	                   showAlert(jo.result.msg);
	               }
	           }, function (t) {
	               showAlert(t);
	           });
           }
           return false;
       });
           

    $("#sendCode").click(function () {
        var flag = validMoible();
        if (flag) {
            var requestParam = getRequestParam();
            requestParam.commandinfo.phonenum = $("#phone").val();
            var url = vge.webcontext + '/register/phoneSendVeriCode.do';
            // post请求
            vge.ajxpost(url, JSON.stringify(requestParam), 30000, function (data) {
                try {
                    var jo = JSON.parse(data);
                    if (jo.result.businesscode == 0) {
                        showAlert("短信验证码发送成功");
                        timeShow();
                    } else {
                        showAlert(jo.result.msg);
                    }
                } catch (e) {
                    showAlert(e);
                }
            }, function (e) {
            	showAlert(e);
            });
        }

    });
})



/**
 * 根据诊所编码查询诊所信息
 * @param clinicCode	诊所编码
 */
function ydbUserInfo(clinicCode){
	 var requestParam = getRequestParam();
     requestParam.commandinfo.cliniccode = clinicCode;
     var url = vge.webcontext + '/clinic/ydbUserInfo.do';
     // post请求
     vge.ajxpost(url, JSON.stringify(requestParam), 30000, function (data) {
         try {
             var jo = JSON.parse(data);
             if (jo.result.businesscode == 0 && jo.reply[0] != undefined) {
            	 $("#phone").val(jo.reply[0].ydbphonenum);
             } 
         } catch (e) {
//             showAlert(e);
         }
     }, function (e) {
//     	showAlert(e);
     });
	
}