$(function () {
	wx.ready(function() { //隐藏微信分享
        wx.hideOptionMenu();
    });
	var params = vge.urlparse(location.href);
	if(params.openid != undefined){
		$("#open_id").val(params.openid);
	}else{
		showAlert("获取openId失败");
		return false;
	}
	
	//推荐人id
	var recommedId = params.recommenduserid;
	$("#recommed_id").val(recommedId);
	
	
	$("#register").click(function(){
    	$("#register").attr("href",vge.webcontext +"/html/register/register.html?openid="+ $("#open_id").val()+ "&v="+Math.random());
	})

    $("#validate_cliniccode").click(function () {
        var clinicCode = $("#clinic_code").val();
        if (clinicCode != "") {
            var requestParam = getRequestParam();
            requestParam.commandinfo.cliniccode = clinicCode;
            var url = vge.webcontext + '/clinic/ydbUserInfo.do';
            // post请求
            vge.ajxpost(url, JSON.stringify(requestParam), 10000, function (data) {
                try {
                    var jo = JSON.parse(data);
                    if (jo.result.businesscode == 0) {
                        $("#ydb_login_name").val(jo.reply[0].ydbloginname);
                    } else {
                        showAlert(jo.result.msg);                    
                    }
                } catch (e) {
                    showAlert(e);
                }
            }, function (e) {
                showAlert(e);
            });
        } else {
            showAlert("请输入诊所编码");
        }
    })


    $("#bingding_clinic").submit(function () {
    	
    	var valiFlag = $("#bingding_clinic").valid();
        if (valiFlag) {
            var requestParam = getRequestParam();
            var registerObj = new Object();
            requestParam.commandinfo.register = registerObj;
            requestParam.commandinfo.ydbloginname = $("#ydb_login_name").val();
            requestParam.commandinfo.cliniccode = $("#clinic_code").val();
            requestParam.commandinfo.ydbloginpassword = $("#ydb_login_password").val();
            requestParam.commandinfo.register.openid = $("#open_id").val();
            requestParam.commandinfo.register.password = $("#password").val();
            requestParam.commandinfo.register.phonenum = $("#phone").val();
            requestParam.commandinfo.register.vericode = $("#code").val();
            if(typeof($("#recommed_id").val()) != "undefined"){
            	requestParam.commandinfo.register.recommedid = $("#recommed_id").val();
            }

            var validateCodeUrl = vge.webcontext + '/clinic/oldUserBingdingClinic.do';
            // post请求
            vge.ajxpost(validateCodeUrl, JSON.stringify(requestParam), 100000, function (data) {
                try {
                    var jo = JSON.parse(data);
                    if (jo.result.code == 0 && jo.result.businesscode == 0) {
                        window.location = vge.webcontext + "/html/register/register_suuccess.html?openid="+ $("#open_id").val()+ "&v="+Math.random();
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
          var url = vge.webcontext + '/register/phoneSendVeriCode.do';
          // post请求
          vge.ajxpost(url, JSON.stringify(requestParam), 1000, function (data) {
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



