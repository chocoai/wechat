 $(function() {
		var params = vge.urlparse(location.href);
		$("#memberid").val(params.memberid);
		$("#groupid").val(params.groupid);
		$("#openid").val(params.openid);
		$("#products").val(params.products);
		$("#flag").val(params.flag);
		wx.ready(function() { //隐藏微信分享
            wx.hideOptionMenu();
        });
		$(".n_tankzz1").show();
    	$(".n_jiaz").show();
	   var requestParam = getRequestParam();
		requestParam.commandinfo.parentid = null;
		var url = vge.japi+'/memberaddress/queryPcity.do';
		// post请求
		vge.ajxpost(url, JSON.stringify(requestParam), 100000, function(data) {
			try {
				var jo = JSON.parse(data);
				if (jo.result.businesscode == 0) {
					$("#itemprovince").html("");
					var select1=" <select  id='provinceId' onchange='getResultCity(this.value)'> ";
					for(var i=0;i<jo.objList.length;i++){
						select1+="<option id='"+jo.objList[i].code+"' value='"+jo.objList[i].code+"'>&nbsp;"+jo.objList[i].name+"&nbsp;</option>";
					}
					select1=select1+"</select>";
					$("#itemprovince").append(select1);
					getResultCity(jo.objList[0].code);
				}else if(jo.result.businesscode == 2){
					alert("网络异常,请求失败");
				}else{
					alert(jo.result.msg);
				}
			} catch (e) {
				alert(e);
			}
		}, function(e) {
			alert(e);
		});
	   
	});
   

 //获得城市
 function getResultCity(d){
	 $(".n_tankzz1").show();
 	$(".n_jiaz").show();
 	$("#itemcity").html("");
  	$("#itemarea").html("");
  	 var requestParam = getRequestParam();
		requestParam.commandinfo.parentid = d;
		var url = vge.japi+'/memberaddress/queryPcity.do';
		// post请求
		vge.ajxpost(url, JSON.stringify(requestParam), 100000, function(data) {
			try {
				var jo = JSON.parse(data);
				if (jo.result.businesscode == 0) {
					var select2=" <select  id='cityId' onchange='getResultCountry(this.value)'> ";
					for(var i=0;i<jo.objList.length;i++){
						select2+="<option id='"+jo.objList[i].code+"' value='"+jo.objList[i].code+"'>&nbsp;"+jo.objList[i].name+"&nbsp;</option>";
					}
					select2=select2+"</select>";
          			$("#itemcity").append(select2);
          			getResultCountry(jo.objList[0].code);
				}
				else{
					alert(jo.result.msg);
				}
			} catch (e) {
				alert(e);
			}
		}, function(e) {
			alert(e);
		});
 }
 
 
 //获得县、区
 function getResultCountry(a){
		$("#itemarea").html("");
	 var requestParam = getRequestParam();
		requestParam.commandinfo.parentid = a;
		var url = vge.japi+'/memberaddress/queryPcity.do';
		// post请求
		vge.ajxpost(url, JSON.stringify(requestParam), 100000, function(data) {
			try {
				var jo = JSON.parse(data);
				if (jo.result.businesscode == 0) {
					var select3=" <select  id='countryId' > ";
					for(var i=0;i<jo.objList.length;i++){
						select3+="<option id='"+jo.objList[i].code+"' value='"+jo.objList[i].code+"'>&nbsp;"+jo.objList[i].name+"&nbsp;</option>";
					}
					select3=select3+"</select>";
       			$("#itemarea").append(select3);
       			$(".n_tankzz1").hide();
				$(".n_jiaz").hide();
				}
				else{
					$(".n_tankzz1").hide();
					$(".n_jiaz").hide();
					alert(jo.result.msg);
				}
			} catch (e) {
				alert(e);
			}
		}, function(e) {
			alert(e);
		});
 }
   
 //地址保存
   function saveAddress(){
	   var groupid=$("#groupid").val();
    	var openid=$("#openid").val();
    	var products=$("#products").val();
    	var flag=$("#flag").val();
    	$(".n_tankzz1").show();
    	$(".n_jiaz").show();
	 
	 var isDefault="";
	   var provinceId=$("#provinceId").val();
		 var cityId=$("#cityId").val();
		 var countryId=$("#countryId").val();
	 var username=$("#username").val();
	 var tel=$("#tel").val();
	 var detailaddress=$("#detailaddress").val();
	 var memberid=$("#memberid").val();
	 if(username==""){
		 $(".n_tankzz1").hide();
			$(".n_jiaz").hide();
		 alert("收货人名称不能为空");
		 return false;
	 }else if(tel==""){
		 $(".n_tankzz1").hide();
			$(".n_jiaz").hide();
		 alert("联系电话不能为空");
		 return false;
	 }else if(detailaddress==""){
		 $(".n_tankzz1").hide();
			$(".n_jiaz").hide();
		 alert("详细地址不能为空");
		 return false;
	 }
	 if($("input[type='checkbox']").is(':checked')){
		 isDefault="1"
	 }else{
		 isDefault="0";
	 }
	 
	   var requestParam = getRequestParam();
		requestParam.commandinfo.memberid = memberid;
		requestParam.commandinfo.provinceid = provinceId;
		requestParam.commandinfo.cityid = cityId;
		requestParam.commandinfo.countryid = countryId;
		requestParam.commandinfo.username = username;
		requestParam.commandinfo.tel = tel;
		requestParam.commandinfo.isdefault = isDefault;
		requestParam.commandinfo.detailaddress =detailaddress;
		var url = vge.japi+'/memberaddress/saveAddress.do';
		// post请求
		vge.ajxpost(url, JSON.stringify(requestParam), 100000, function(data) {
			try {
				var jo = JSON.parse(data);
				if (jo.result.businesscode == 0) {
					if(flag==1){
						location.href=vge.webcontext+'/html/order/order_creta.html?&openid='+openid+"&groupid="+groupid+"&products="+products+"&addressid="+jo.obj.id+"&v="+Math.random();
					}else{
						location.href=vge.webcontext+'/html/address/address_list.html?flag='+flag+"&openid="+openid+"&v="+Math.random();
					}
				}
				else{
					$(".n_tankzz1").hide();
					$(".n_jiaz").hide();
					alert(jo.result.msg);
				}
			} catch (e) {
				alert(e);
			}
		}, function(e) {
			alert(e);
		});
   }
	