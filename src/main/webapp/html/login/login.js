$(function () {
    wx.ready(function () { //隐藏微信分享
        wx.hideOptionMenu();
    });

    var params = vge.urlparse(location.href);
    if (params.openid != undefined) {
        $("#open_id").val(params.openid);

    } else {
        showAlert("获取openId失败");
        return false;
    }

    $("#register_btn").click(function () {
        var requestParam = getRequestParam();
        requestParam.commandinfo.openid = $("#open_id").val();
        var url = vge.webcontext + '/userAction/queryByUserId.do';
        //根据openId 获取unionId
        // post请求
        vge.ajxpost(url, JSON.stringify(requestParam), 100000, function (data) {
            try {
                var jo = JSON.parse(data);
                if (jo.result.code == 0 && jo.result.businesscode == 0) {
                	ifBingDing(jo.reply.unionid);
                } else {
                    showAlert(jo.result.msg);
                }
            } catch (e) {
                showAlert(e);
            }
        }, function (e) {
            showAlert(e);
        });

    })
    $("#forget_password_btn").click(function () {
        window.location = vge.webcontext + "/html/forgetpassword/forgetpassword.html?openid=" + $("#open_id").val() + "&v=" + Math.random();
    })

    $("#login_btn").click(function () {
        //手机号
        var phone_num = $("#phone_num_id").val();
        if (phone_num == null || phone_num == "") {
            showAlert("手机号不能为空");
            return false;
        }
        var reg = /(1[3-9]\d{9}$)/;
        if (!reg.test(phone_num)) {
            showAlert("手机号格式不正确");
            return false;
        }
        //密码
        var password = $("#password_id").val();
        if (password == null || password == "") {
            showAlert("密码不能为空");
            return false;
        }
        var requestParam = getRequestParam();
        requestParam.commandinfo.openid = $("#open_id").val();
        requestParam.commandinfo.password = password;
        requestParam.commandinfo.phonenum = phone_num;
        var url = vge.webcontext + '/userAction/loginBingDing.do';
        // post请求
        vge.ajxpost(url, JSON.stringify(requestParam), 30000, function (data) {
            try {
                var jo = JSON.parse(data);
                if (jo.result.code == 0 && jo.result.businesscode == 0) {
                    window.location = vge.webcontext + "/html/login/login_suuccess.html?openid=" + $("#open_id").val() + "&v=" + Math.random();
                } else {
                    showAlert(jo.result.msg);
                }
            } catch (e) {
                showAlert(e);
            }
        }, function (e) {
            showAlert(e);
        });
    });
})


function ifBingDing(unionId){
    var requestParam = getRequestParam();
    requestParam.commandinfo.unionid = unionId;
    var url = vge.webcontext + '/userAction/ifBingDing.do';
    // post请求
    vge.ajxpost(url, JSON.stringify(requestParam), 100000, function (data) {
        try {
            var jo = JSON.parse(data);
            if (jo.result.code == 0 && jo.result.businesscode == 0) {
            	 window.location = vge.registerurl + "?unionId=" + unionId + "&backURL=" + vge.registerinterfacegace+"&refmessage=2&filingChannel=2&regSource=2";
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