$(function () {
	wx.ready(function() { //隐藏微信分享
        wx.hideOptionMenu();
    });

    var params = vge.urlparse(location.href);
    if (params.openid != undefined) {
        $("#open_id").val(params.openid);

    } else {
    	showAlert("获取openId失败");
        return false;
    }



    $("#forget_password_bingding").submit(function () {
        var valiFlag = $("#forget_password_bingding").valid();
        if (valiFlag) {
            var requestParam = getRequestParam();
            requestParam.commandinfo.openid = $("#open_id").val();
            requestParam.commandinfo.password = $("#password").val();
            requestParam.commandinfo.phonenum = $("#phone").val();
            requestParam.commandinfo.vericode = $("#code").val();
            var validateCodeUrl = vge.webcontext + '/register/resetPassword.do';
            // post请求
            vge.ajxpost(validateCodeUrl, JSON.stringify(requestParam), 30000, function (data) {
                try {
                    var jo = JSON.parse(data);
                    if (jo.result.code == 0 && jo.result.businesscode == 0) {
                        window.location = vge.webcontext + "/html/forgetpassword/forgetpassword_suuccess.html?openid=" + $("#open_id").val() + "&v=" + Math.random();
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
        return false;
    });


    $("#sendCode").click(function () {
        var flag = validMoible();
        if (flag) {
            var requestParam = getRequestParam();
            requestParam.commandinfo.phonenum = $("#phone").val();
            requestParam.commandinfo.sendtype = "1";
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
