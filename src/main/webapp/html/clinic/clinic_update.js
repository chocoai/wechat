$(function () {
	var params = vge.urlparse(location.href);
	if(params.openid != undefined){
		$("#open_id").val(params.openid);
		
	}else{
		showAlert("获取openId失败");
		return false;
	}
	wx.ready(function() { //隐藏微信分享
        wx.hideOptionMenu();
    });

    //加载省
    loadProvince();

    //加载值
    loadValue();
    $("#clinic_update").click(function () {

        var clinic_name = $("#clinic_name").val();
        if (clinic_name == "") {
        	showAlert("请输入诊所名称");
            return false;
        }
        var phonenum = $("#phonenum").val();
        if (phonenum == "") {
        	showAlert("请输入联系电话");
            return false;
        }
        var reg = /(1[3-9]\d{9}$)/;
        if (!reg.test(phonenum)) {
        	showAlert("手机号格式不正确");
            return false;
        }
        var province = $("#province_select  option:selected").val();
        if (province == "") {
        	showAlert("请选择省");
            return false;
        }
        var city = $("#city_select  option:selected").val();
        if (city == "") {
        	showAlert("请选择市");
            return false;
        }
        var county = $("#county_select  option:selected").val();
        if (county == "") {
        	showAlert("请选择区/县");
            return false;
        }
        var clinicladdress = $("#clinicladdress").val();
        if (clinicladdress == "") {
        	showAlert("请输入详细地址");
            return false;
        }

        var certificatecard = $("#certificatecard").val();
        if (certificatecard == "" && $("#certificatecardurl").val() == "") {
        	showAlert("请上传医疗机构职业许可证");
            return false;
        }
        
        if(certificatecard != ""){
	        var certificatecardType = certificatecard.match(/^(.*)(\.)(.{1,8})$/)[3];
	        certificatecardType=certificatecardType.toUpperCase();
	        if(certificatecardType!="JPEG" && certificatecardType!="PNG" && certificatecardType!="JPG" && certificatecardType!="GIF"){
	        	showAlert("医疗机构职业许可证格式错误");  
	            return false;
	        }
        }
        
        var doctorseniority = $("#doctorseniority").val();
        
        if(doctorseniority != ""){
        	var doctorseniorityType = doctorseniority.match(/^(.*)(\.)(.{1,8})$/)[3];
        	doctorseniorityType=doctorseniorityType.toUpperCase();
        	if(doctorseniorityType!="JPEG" && doctorseniorityType !="PNG" && doctorseniorityType !="JPG" && doctorseniorityType !="GIF"){
            	showAlert("职业医师资格证格式错误");  
                return false;
            }
        }


        var flag = false;


        var formData = new FormData(); //构造空对象，下面用append 方法赋值。  
        if ($("#certificatecardurl").val() == "") {
            formData.append("certificatecard", $("#certificatecard")[0].files[0]);
            flag = true;
        }

        if ($("#doctorseniority").val() != "" && $("#doctorseniorityurl").val() == "") {
            formData.append("doctorseniority", $("#doctorseniority")[0].files[0]);
            flag = true;
        }

        if (flag) {
        	//加载层的显示
    		$('.n_tankzz1').show();
    		$('.n_jiaz').show();
    		
            $.ajax({
                url: vge.webcontext + '/uploadFile/uploadPicture.do',
                type: 'POST',
                data: formData,
                /**   
                 * 必须false才会避开jQuery对 formdata 的默认处理
                 * XMLHttpRequest会对 formdata 进行正确的处理
                 */
                processData: false,
                /**   
                 *必须false才会自动加上正确的Content-Type
                 */
                contentType: false,
                success: function (data) {
                        var jo = JSON.parse(data);
                        if (jo.code == 0) {
                            $("#certificatecardurl").val(jo.certificatecard);
                            if (jo.doctorseniority != undefined && jo.doctorseniority != "" && jo.doctorseniority != null) {
                                $("#doctorseniorityurl").val(jo.doctorseniority);
                            }
                        }

                        updateClinicQualiRegister();
                    },
                    error: function (responseStr) {
                    	$('.n_tankzz1').hide();
                 		$('.n_jiaz').hide();
                        showAlert("文件上传失败");
                    }
            });
        } else {
            updateClinicQualiRegister();

        }




    });

})

function updateClinicQualiRegister() {
    var requestParam = getRequestParam();
    requestParam.commandinfo.openid = $("#open_id").val();
    requestParam.commandinfo.clinicid = $("#clinic_id").val();
    requestParam.commandinfo.phonenum = $("#phonenum").val();
    requestParam.commandinfo.clinicname = $("#clinic_name").val();
    requestParam.commandinfo.province = $("#province_select  option:selected").val();
    requestParam.commandinfo.city = $("#city_select  option:selected").val();
    requestParam.commandinfo.county = $("#county_select  option:selected").val();
    requestParam.commandinfo.clinicaddress = $("#clinicladdress").val();
    requestParam.commandinfo.certificatecardurl = $("#certificatecardurl").val();
    requestParam.commandinfo.doctorseniorityurl = $("#doctorseniorityurl").val();
    
    
    var url = vge.webcontext + '/clinic/updateClinicQualiRegister.do';
    // post请求
    vge.ajxpost(url, JSON.stringify(requestParam), 100000, function (data) {
        try {
            var jo = JSON.parse(data);
            if (jo.result.code == 0 && jo.result.businesscode == 0) {
                window.location = vge.webcontext + "/html/clinic/clinic_check.html";
            } else {
            	 $('.n_tankzz1').hide();
         		$('.n_jiaz').hide();
                showAlert(jo.result.msg);
            }
        } catch (e) {
        	 $('.n_tankzz1').hide();
     		$('.n_jiaz').hide();
            showAlert(e);
        }
    }, function (e) {
    	 $('.n_tankzz1').hide();
 		$('.n_jiaz').hide();
        showAlert(e);
    });

}

/**
 * 加载值
 */
function loadValue() {
    var requestParam = getRequestParam();
    requestParam.commandinfo.openid = $("#open_id").val();

    var url = vge.webcontext + '/clinic/queryClinicInfo.do';
    // post请求
    vge.ajxpost(url, JSON.stringify(requestParam), 100000, function (data) {
        try {
            var jo = JSON.parse(data);
            if (jo.result.code == 0 && jo.result.businesscode == 0 && jo.reply[0] != undefined) {
            	
            	
                if (jo.reply[0].clinicid != undefined && jo.reply[0].clinicid != "" && jo.reply[0].clinicid != "null") {
                    $("#clinic_id").val(jo.reply[0].clinicid);
                }
                if (jo.reply[0].clinicname != undefined && jo.reply[0].clinicname != "" && jo.reply[0].clinicname != "null") {
                    $("#clinic_name").val(jo.reply[0].clinicname);
                }
                if (jo.reply[0].phonenum != undefined && jo.reply[0].phonenum != "" && jo.reply[0].phonenum != "null") {
                    $("#phonenum").val(jo.reply[0].phonenum);
                }
                if (jo.reply[0].clinicaddress != undefined && jo.reply[0].clinicaddress != "" && jo.reply[0].clinicaddress != "null") {
                    $("#clinicladdress").val(jo.reply[0].clinicaddress);
                }

                if (jo.reply[0].certificatecardurl != undefined && jo.reply[0].certificatecardurl != "" && jo.reply[0].certificatecardurl != "null") {
                    $("#certificatecardurl").val(jo.reply[0].certificatecardurl);
                    $("#certificatecard_img").attr("src", jo.reply[0].certificatecardurl);
                }

                if (jo.reply[0].doctorseniorityurl != undefined && jo.reply[0].doctorseniorityurl != "" && jo.reply[0].doctorseniorityurl != "null") {
                    $("#doctorseniorityurl").val(jo.reply[0].doctorseniorityurl);
                    $("#doctorseniority_img").attr("src", jo.reply[0].doctorseniorityurl);
                }
                //省字段
           	 	$("#province_select").val(jo.reply[0].province);
                getResultCity(jo.reply[0].province);

                //市字段
                $("#city_select").val(jo.reply[0].city);
                getResultCountry(jo.reply[0].city);
                //区县字段
                $("#county_select").val(jo.reply[0].county);
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

function loadProvince() {
    var requestParam = getRequestParam();
    requestParam.commandinfo.parentid = null;
    var url = vge.webcontext + '/memberaddress/queryPcity.do';
    // post请求
    vge.ajxpost(url, JSON.stringify(requestParam), 100000, function (data) {
        try {
            var jo = JSON.parse(data);
            if (jo.result.code == 0 && jo.result.businesscode == 0) {
                $("#province_li").html("");
                var select = " <select  id='province_select' onchange='getResultCity(this.value,null)'> ";
                for (var i = 0; i < jo.objList.length; i++) {
                    select += "<option id='" + jo.objList[i].code + "' value='" + jo.objList[i].code + "'>&nbsp;" + jo.objList[i].name + "&nbsp;</option>";
                }
                select = select + "</select>";
                $("#province_li").append(select);
                getResultCity(jo.objList[0].code , null);
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


//获得城市
function getResultCity(d) {
    var requestParam = getRequestParam();
    requestParam.commandinfo.parentid = d;
    var url = vge.webcontext + '/memberaddress/queryPcity.do';
    // post请求
    vge.ajxpost(url, JSON.stringify(requestParam), 100000, function (data) {
        try {
            var jo = JSON.parse(data);
            if (jo.result.code == 0 && jo.result.businesscode == 0) {
                $("#city_li").html("");
                var select2 = " <select  id='city_select' onchange='getResultCountry(this.value , null)'> ";
                for (var i = 0; i < jo.objList.length; i++) {
                    select2 += "<option id='" + jo.objList[i].code + "' value='" + jo.objList[i].code + "'>&nbsp;" + jo.objList[i].name + "&nbsp;</option>";
                }
                select2 = select2 + "</select>";
                
                $("#city_li").append(select2);
                getResultCountry(jo.objList[0].code , null);
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



//获得县、区
function getResultCountry(a) {
    var requestParam = getRequestParam();
    requestParam.commandinfo.parentid = a;
    var url = vge.webcontext + '/memberaddress/queryPcity.do';
    // post请求
    vge.ajxpost(url, JSON.stringify(requestParam), 100000, function (data) {
        try {
            var jo = JSON.parse(data);
            if (jo.result.code == 0 && jo.result.businesscode == 0) {
                $("#county_li").html("");
                var select3 = " <select  id='county_select' > ";
                for (var i = 0; i < jo.objList.length; i++) {
                    select3 += "<option id='" + jo.objList[i].code + "' value='" + jo.objList[i].code + "'>&nbsp;" + jo.objList[i].name + "&nbsp;</option>";
                }
                select3 = select3 + "</select>";
                $("#county_li").append(select3);
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
