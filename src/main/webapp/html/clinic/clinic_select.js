$(function () {
	wx.ready(function() { //隐藏微信分享
        wx.hideOptionMenu();
    });
    var params = vge.urlparse(location.href);
    if (params.openid != undefined) {
        $("#open_id").val(params.openid);

    } else {
        alert("获取openId失败");
        return false;
    }




    var requestParam = getRequestParam();
    requestParam.commandinfo.openid = $("#open_id").val();
    var url = vge.webcontext + '/clinic/queryClinicInfoList.do';
    //post请求
    vge.ajxpost(url, JSON.stringify(requestParam), 100000, function (data) {
        try {
            var jo = JSON.parse(data);
            if (jo.result.code == 0 && jo.result.businesscode == 0) {
                var reple = jo.reply;
                var repleLength = getJsonLength(reple);

                var openId = $("#open_id").val();
                if (reple != undefined && reple != "") {

                    if (repleLength > 1) {
                        var context = "";
                        for (var i = 0; i < repleLength; i++) {
                            context += "<div class='Group_purchase')'><a onclick='newUserBingdingClinic(\"" + openId + "\", \"" + reple[i].clinicid + "\", \"" + reple[i].cliniccode + "\")'><dl><dt>诊所信息</dt><dd><P>诊所名称：<span>" + reple[i].clinicname + "</span></P> <P>诊所地址：<span>" + reple[i].clinicaddress + "</span></P><i></i></dd></dl> </a></div>";
                        }
                        $("#clinic_select").html(context);
                    } else {
                        newUserBingdingClinic(openId, reple[0].clinicid, reple[0].cliniccode);

                    }
                } else {
                    window.location = vge.webcontext + "/html/clinic/clinic_add.html?openid=" + openId + "&v=" + Math.random();
                }
            } else {
                alert(jo.result.msg);
            }
        } catch (e) {
            alert(e);
        }
    }, function (e) {
        alert(e);
    });
})



/**
 * 老用户绑定
 * @param openId 微信Id
 * @param clinicId	诊所id
 * @param clinicCode 诊所编码
 */
function newUserBingdingClinic(openId, clinicId, clinicCode) {
    var requestParam = getRequestParam();
    requestParam.commandinfo.cliniccode = clinicCode;
    requestParam.commandinfo.openid = openId;
    requestParam.commandinfo.clinicid = clinicId;
    var url = vge.webcontext + '/clinic/newUserBingdingClinic.do';
    // post请求
    vge.ajxpost(url, JSON.stringify(requestParam), 100000, function (data) {
        try {
            var jo = JSON.parse(data);
            if (jo.result.code == 0 && jo.result.businesscode == 0) {
            	getClinicType(openId);
            } else {
                alert(jo.result.msg);
            }
        } catch (e) {
            alert(e);
        }
    }, function (e) {
        alert(e);
    });

}


function getClinicType(openId){
	
    var requestParam = getRequestParam();
    requestParam.commandinfo.openid = openId;
    var url = vge.webcontext + '/clinic/getClinicType.do';
    // post请求
    vge.ajxpost(url, JSON.stringify(requestParam), 100000, function (data) {
        try {
            var jo = JSON.parse(data);
            if (jo.result.code == 0 && jo.result.businesscode == 0) {
            	
            	//状态，0：注册用户；1：认证用户；2：待审核；3：审核不通过； 
               var status = jo.reply[0].checkresult;
               if (status == 1) {
            	   //成功
                   window.location = vge.webcontext + "/html/clinic/clinic_success.html?openId=" + openId;
                	
                } else if (status == 2) {
                	//待审核
                	 window.location =vge.webcontext + "/html/clinic/clinic_check.html?openid=" + openId + "&v="+Math.random();
                } else if (status == 3) {
                	var clinicErrorUrl = vge.webcontext + "/html/clinic/clinic_error.html?openid=" + openId + "&certificateOpinion=" +jo.reply[0].checkMsg+ "&v="+Math.random();
                	clinicErrorUrl=encodeURI(clinicErrorUrl); 
                	window.location = clinicErrorUrl;
                }
              
            } else {
                alert(jo.result.msg);
            }
        } catch (e) {
            alert(e);
        }
    }, function (e) {
        alert(e);
    });
	
}