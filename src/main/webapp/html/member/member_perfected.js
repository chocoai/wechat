$(function () {

	wx.ready(function() { //隐藏微信分享
        wx.hideOptionMenu();
    });
	var params = vge.urlparse(location.href);
	
	if(params.openid == ""){
		showAlert("获取openId失败");
		return false;
	}
	else{
		$("#open_id").val(params.openid);
	}


    //加载值
    loadValue();

    $(".sex_input>div").on('touchstart', function (e) {
        $(this).children('input').attr('checked', true).siblings('em').addClass('current');
        $(this).siblings('div').children('em').removeClass('current').siblings('input').attr('checked', false);
    });
    $(".fl_list li,.fk_list li,.five_list li,.ks_list li").on('touchstart', function (event) {
        $(this).addClass('selected').siblings('li').removeClass('selected');
    });

    $("#clinic_scope li").click(function () {
        $("#clinic_scope_value").val($(this).val());
    })
    $("#clinic_nature li").click(function () {
        $("#clinic_nature_value").val($(this).val());
    })
    $("#clinic_division li").click(function () {
        $("#clinic_division_value").val($(this).val());
    })
    $("#clinic_scale li").click(function () {
        $("#clinic_scale_value").val($(this).val());
    })
    $("#clinic_area li").click(function () {
        $("#clinic_area_value").val($(this).val());
    })


    $("#member_perfected").submit(function () {

        var requestParam = getRequestParam();
        var registerObj = new Object();
        requestParam.commandinfo.register = registerObj;
        requestParam.commandinfo.openid = $("#open_id").val();
        requestParam.commandinfo.register.username = $("#name").val();
        requestParam.commandinfo.register.sex = $(".current").html();
        requestParam.commandinfo.clinicscope = $("#clinic_scope_value").val();
        requestParam.commandinfo.clinicscale = $("#clinic_scale_value").val();
        requestParam.commandinfo.clinicnature = $("#clinic_nature_value").val();
        requestParam.commandinfo.clinicarea = $("#clinic_area_value").val();
        requestParam.commandinfo.clinicdivision = $("#clinic_division_value").val();
        requestParam.commandinfo.divisiondescription = $("#division_description").val();

        var url = vge.webcontext + '/clinic/perfectedClinicInfo.do';

        // post请求
        vge.ajxpost(url, JSON.stringify(requestParam), 100000, function (data) {
            try {
                var jo = JSON.parse(data);
                if (jo.result.code == 0 && jo.result.businesscode == 0) {
                    //跳到用户信息
					window.location = vge.webcontext+"/html/member/member_info.html?openid="+$("#open_id").val();
                } else {
                    showAlert(jo.result.msg);
                }
            } catch (e) {
                showAlert(e);
            }
        }, function (e) {
        	showAlert(e);
        });


        return false;
    });



})


function loadValue() {
    var requestParam = getRequestParam();
    requestParam.commandinfo.openid = $("#open_id").val();

    var url = vge.webcontext + '/clinic/queryClinicInfo.do';

    // post请求
    vge.ajxpost(url, JSON.stringify(requestParam), 100000, function (data) {
        try {

            var jo = JSON.parse(data);
            if (jo.result.code == 0 && jo.result.businesscode == 0) {


                if (jo.reply[0].register.username != undefined && jo.reply[0].register.username != "" && jo.reply[0].register.username != "null" && jo.reply[0].register.username != null) {
                    $("#name").val(jo.reply[0].register.username);
                }

                if (jo.reply[0].register.sex == 2) {
                    $("#sex_2").addClass("current");
                    $("#sex_1").removeClass("current");
                }

                if (jo.reply[0].divisiondescription != undefined && jo.reply[0].divisiondescription != "" && jo.reply[0].divisiondescription != "null" && jo.reply[0].divisiondescription != null) {
                    $("#division_description").val(jo.reply[0].divisiondescription);
                }

                //诊所业务类型
                $("#clinic_scope li").each(function () { //
                        var liValue = $(this).val();
                        if (liValue == jo.reply[0].clinicscope) {
                            $(this).addClass('selected')
                            $("#clinic_scope_value").val(jo.reply[0].clinicscope);
                            return false;
                        }
                    })
                //诊所经营性质
                $("#clinic_nature li").each(function () { //
                    var liValue = $(this).val();
                    if (liValue == jo.reply[0].clinicnature) {
                        $(this).addClass('selected')
                        $("#clinic_nature_value").val(jo.reply[0].clinicnature);
                        return false;
                    }
                })

                //诊所业务范围
                $("#clinic_division li").each(function () { //
                    var liValue = $(this).val();
                    if (liValue == jo.reply[0].clinicdivision) {
                        $(this).addClass('selected')
                        $("#clinic_division_value").val(jo.reply[0].clinicdivision);
                        return false;
                    }
                })

                //诊所人员规模
                $("#clinic_scale li").each(function () { //
                    var liValue = $(this).val();
                    if (liValue == jo.reply[0].clinicscale) {
                        $(this).addClass('selected')
                        $("#clinic_scale_value").val(jo.reply[0].clinicscale);
                        return false;
                    }
                })

                //诊所建筑面积
                $("#clinic_area li").each(function () { //
                    var liValue = $(this).val();
                    if (liValue == jo.reply[0].clinicarea) {
                        $(this).addClass('selected')
                        $("#clinic_area_value").val(jo.reply[0].clinicarea);
                        return false;
                    }
                })
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