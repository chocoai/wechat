$(function () {
	wx.ready(function() { //隐藏微信分享
        wx.hideOptionMenu();
    });
	var params = vge.urlparse(location.href);
	$("#open_id").val(params.openid);
	if(params.openid == ""){
		showAlert("获取openId失败");
		return false;
	}
	
    //加载省
    loadProvince();

    $("#clinic_add").click(function () {

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
        if (certificatecard == "") {
            showAlert("请上传医疗机构职业许可证");
            return false;
        }
        var certificatecardType = certificatecard.match(/^(.*)(\.)(.{1,8})$/)[3];
        certificatecardType=certificatecardType.toUpperCase();
        if(certificatecardType!="JPEG" && certificatecardType!="PNG" && certificatecardType!="JPG" && certificatecardType!="GIF"){
        	showAlert("医疗机构职业许可证格式错误");  
            return false;
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

        var formData = new FormData(); //构造空对象，下面用append 方法赋值。  
        formData.append("certificatecard", $("#certificatecard")[0].files[0]);

        if (doctorseniority != "") {
            formData.append("doctorseniority", $("#doctorseniority")[0].files[0]);
        }
        
		
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
                        if (jo.doctorseniority != undefined) {
                            $("#doctorseniorityurl").val(jo.doctorseniority);
                        }
                        clinicQualiRegister();
                    }
                },
                error: function (responseStr) {
                	$('.n_tankzz1').hide();
         			$('.n_jiaz').hide();
                    showAlert("文件上传失败");
                   
                }
        });

    });

})

function writeObj(obj){ 
 var description = ""; 
 for(var i in obj){ 
  var property=obj[i]; 
  description+=i+" = "+property+"\n"; 
 } 
 alert(description); 
} 

function clinicQualiRegister() {
    var requestParam = getRequestParam();
    requestParam.commandinfo.openid = $("#open_id").val();
    requestParam.commandinfo.phonenum = $("#phonenum").val();
    requestParam.commandinfo.clinicname = $("#clinic_name").val();
    requestParam.commandinfo.province = $("#province_select  option:selected").val();
    requestParam.commandinfo.city = $("#city_select  option:selected").val();
    requestParam.commandinfo.county = $("#county_select  option:selected").val();
    requestParam.commandinfo.clinicaddress = $("#clinicladdress").val();
    requestParam.commandinfo.certificatecardurl = $("#certificatecardurl").val();
    requestParam.commandinfo.doctorseniorityurl = $("#doctorseniorityurl").val();
    var url = vge.webcontext + '/clinic/clinicQualiRegister.do';
    // post请求
    vge.ajxpost(url, JSON.stringify(requestParam), 100000, function (data) {
        try {
            var jo = JSON.parse(data);
            if (jo.result.code == 0 && jo.result.businesscode == 0) {
                window.location = vge.webcontext + "/html/clinic/clinic_check.html?openid=" + $("#open_id").val() + "&v="+Math.random();
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
                var select = " <select  id='province_select' onchange='getResultCity(this.value)'> ";
                for (var i = 0; i < jo.objList.length; i++) {
                    select += "<option id='" + jo.objList[i].code + "' value='" + jo.objList[i].code + "'>&nbsp;" + jo.objList[i].name + "&nbsp;</option>";
                }
                select = select + "</select>";
                $("#province_li").append(select);
                getResultCity(jo.objList[0].code);
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
                var select2 = " <select  id='city_select' onchange='getResultCountry(this.value)'> ";
                for (var i = 0; i < jo.objList.length; i++) {
                    select2 += "<option id='" + jo.objList[i].code + "' value='" + jo.objList[i].code + "'>&nbsp;" + jo.objList[i].name + "&nbsp;</option>";
                }
                select2 = select2 + "</select>";
                $("#city_li").append(select2);
                getResultCountry(jo.objList[0].code);
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

