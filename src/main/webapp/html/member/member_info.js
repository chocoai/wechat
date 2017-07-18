$(function () {
	wx.ready(function() { //隐藏微信分享
        wx.hideOptionMenu();
    });
	var params = vge.urlparse(location.href);
	if(params.openid != undefined){
		alert("");
		$("#open_id").val(params.openid);
		showMemberInfo(params.openid);
		
	}else{
		showAlert("获取openId失败");
		return false;
	}
	
	
	
})


/**
 * 会员信息
 */
function showMemberInfo(openId) {

    var requestParam = getRequestParam();
    requestParam.commandinfo.openid = openId;
    var url = vge.webcontext + '/userAction/showUserInfo.do';
    // post请求
    vge.ajxpost(url, JSON.stringify(requestParam), 100000, function (data) {
        try {
            var jo = JSON.parse(data);
            if (jo.result.code == 0 && jo.result.businesscode == 0) {
                $("#phonenum").html(jo.reply.phonenum.substring(0, 3) + "＊＊＊＊" + jo.reply.phonenum.substring(8, 11));
                
                if (jo.reply.headurl != undefined) {
                    $("#headurl").attr("src", jo.reply.headurl);
                }
                
                var credit = jo.reply.credit;
                if(credit != 0){
                	$("#credit").html(credit+"分");
                }
                else{
                	$("#credit").html("未开通");
                }
                $("#addresscount_url").attr("href",vge.webcontext + "/html/address/address_list.html?flag=2&openid="+openId+ "&v="+Math.random());
                $("#addresscount").html(jo.reply.addresscount + "个地址");
                
               	if(jo.reply.qrcodecount > 0){
               		$("#qrcodecount_url").attr("href",vge.webcontext + "/html/member/qrcode_list.html?openid="+openId+ "&v="+Math.random());
               	}               
                $("#qrcodecount").html(jo.reply.qrcodecount + "个二维码");

                //状态，0：注册用户；1：认证用户；2：待审核；3：审核不通过； 
                var status = jo.reply.status;
                if (status == 0) {
                	//跳转认证成功
                    $("#status").html("注册用户");
                    $("#status_url").attr("href",vge.webcontext + "/html/clinic/clinic_disclaimer.html?openid=" + openId+ "&v="+Math.random());
                } else if (status == 1) {
                	 $("#status").html("认证用户");
                	 $("#status_url").attr("href",vge.webcontext + "/html/clinic/clinic_success.html?openid=" + openId+ "&v="+Math.random());
                	 $("#perfected_clinic_info_url").attr("href", vge.webcontext + "/html/member/member_perfected.html?openid=" + openId+ "&v="+Math.random());
                     $("#perfected").show();
                } else if (status == 2) {
                    $("#status").html("待审核");
                } else if (status == 3) {
                	var clinicErrorUrl = vge.webcontext + "/html/clinic/clinic_error.html?openid=" + openId + "&certificateOpinion=" +jo.reply.certificateOpinion+ "&v="+Math.random();
                	clinicErrorUrl=encodeURI(clinicErrorUrl); 
                	//跳转审核失败
                	$("#status").html("审核失败");
                	$("#status_url").attr("href",clinicErrorUrl);
                }
                else if (status == 5) {
	            	//未上传证书
	            	$("#status").html("上传诊所信息");
	            	$("#status_url").attr("href",vge.webcontext + "/html/clinic/clinic_update.html?openid=" + openId + "&v=" + Math.random());
	            }
                
            } else {
            	showAlert(jo.result.msg);
            }
        } catch (e) {
        	alert("2");
        	showAlert(e);
        }
    }, function (e) {
    	showAlert(e);
    });
}