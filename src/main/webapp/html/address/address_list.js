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
		var flag= params.flag;
	   var requestParam = getRequestParam();
		requestParam.commandinfo.openid = params.openid;
		var url = vge.japi+'/memberaddress/queryAddressList.do';
		// post请求
		vge.ajxpost(url, JSON.stringify(requestParam), 100000, function(data) {
			try {
				var jo = JSON.parse(data);
				if (jo.result.businesscode == 0) {
					$("#memberid").val(jo.args);
					$("#addressList").html("");
					var select="";
					for(var i=0;i<jo.objList.length;i++){
						if(flag==1){
						select+="<a onclick='checkAddress("+jo.objList[i].id+")'>";
						}
						select+="<div class='Xzdz'>";
						select+="<div class='XzdzCon'>"
						select+="<div class='XzConHe'><span class='XzConHeName'>"+jo.objList[i].username+"</span><span class='XzConHeTel'>"+jo.objList[i].tel+"</span></div>";
						if(jo.objList[i].default=='1'){
						select+="<div class='XzConDz'><span class='XzConDzSpan'>默认</span>"+jo.objList[i].detailaddress+"</div>";
						
						}else{
							select+="<div class='XzConDz'>"+jo.objList[i].detailaddress+"</div>";

						}
						select+="<input type='hidden' id='addressid' value='"+jo.objList[i].id+"' />";
						
						select+="</div>";
						select+="</div>";
						if(flag==1){
						select+="</a>";
						}
					  
					}
					$("#addressList").append(select);
				}else if(jo.result.businesscode == 2){
					alert("未注册");
					var openid=$("#openid").val();
					location.href=vge.webcontext+'/html/login/login.html?openid='+openid+"&v="+Math.random();
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
   
   //新增地址
   function addAddress(){
	    var memberid=$("#memberid").val();
	    var groupid=$("#groupid").val();
     	var openid=$("#openid").val();
     	var products=$("#products").val();
     	var flag=$("#flag").val();
	    location.href='add.html?memberid='+memberid+"&openid="+openid+"&groupid="+groupid+"&products="+products+"&flag="+flag+"&v="+Math.random();
   }
   
   //选择地址
    function checkAddress(d){
    	var memberid=$("#memberid").val();
    	var groupid=$("#groupid").val();
    	var openid=$("#openid").val();
    	var products=$("#products").val();
    	location.href=vge.webcontext+'/html/order/order_creta.html?&openid='+openid+"&groupid="+groupid+"&products="+products+"&addressid="+d+"&v="+Math.random();

   }
   