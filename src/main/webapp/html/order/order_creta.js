



$(function() {
	
	var params = vge.urlparse(location.href);
   var requestParam = getRequestParam();
	requestParam.commandinfo.openid = params.openid;
	requestParam.commandinfo.groupid = params.groupid;
	requestParam.commandinfo.products = params.products;
	requestParam.commandinfo.addressid = params.addressid;
	if(params.recommenduserid == undefined){
	 requestParam.commandinfo.recommenduserid = params.recommenduserid;
	  $("#recommenduserid").val(params.recommenduserid);
	  var recommenduserid=params.recommenduserid;
	}else{
		 $("#recommenduserid").val("");
		  var recommenduserid="";
	}
	$("#groupid").val(params.groupid);
	$("#openid").val(params.openid);
	$("#products").val(params.products);
	$("#addressid").val(params.addressid);
	
	wx.ready(function() { //隐藏微信分享
        wx.hideOptionMenu();
    });
	var openid=params.openid;
	
	var url = vge.japi+'/payaction/payDetail.do';
	// post请求
	vge.ajxpost(url, JSON.stringify(requestParam), 100000, function(data) {
		try {
			var jo = JSON.parse(data);
            if (jo.result.businesscode == 0) {
            	if(jo.obj.status=='2'){
            	     $("#productList").html("");
            	     $("#select1").html("");
            	     $("#select2").html("");
            	     $("#memberid").val(jo.obj.memberid);
								var select = "";
								var select1 = "";
								var select2 = "";
								if(jo.obj.flag=='1'){
									 $("#addressid").val(jo.obj.shopMemberAddress.id);
									select1 += "<a onclick='queryAddressList();'>"; 
								   select1 += "<div class='QfukName'><span>"+jo.obj.shopMemberAddress.username+"</span><span class='QfukNaSpan2'>"+jo.obj.shopMemberAddress.tel+"</span></div>";
								   select1 += "<div class='QfukDiz'><img src='../../static/img/dizi_03.jpg' /><span class='QfukDiSpan'>收货地址：</span><span>"+jo.obj.shopMemberAddress.detailaddress+"</span></div>";
								   select1 += "</a>";  
								}else{
                                		select1 += "<div >"; 
                                		select1 += "<a onclick='addAddress();'><div class='XzdBotDiz'>新增地址</div></a>"; 
                                		select1 += "</div>"; 
                                   }
								select2 += "总计:<span>￥</span><span>"+jo.obj.total+"</span>";
								  $("#total").val(jo.obj.total);
								for (var i = 0; i < jo.obj.productInfo.length; i++) {
									select += "<div class='QfuShangp'> ";
									select += "<div class='QfuGp'> ";
									select += "	<div class='QfuGpImg'><img src='"+vge.alimgsrv+"/"+jo.obj.productInfo[i].productUrl+"'/></div> ";
									select += "	<div class='QfuGpCpin'>"+jo.obj.productInfo[i].productName+"</div> ";
									select += "	<div class='QfuGpSl'> ";
									select += "		<p>￥"+jo.obj.productInfo[i].groupPrice+"</p> ";
										select += "		<p>x"+jo.obj.productInfo[i].productCount+"</p> ";
											select += "	</div> ";
												select += "</div> ";
													select += "</div> ";
								}
								$("#productList").append(select);
								$("#select1").append(select1);
								$("#select2").append(select2);
            	}else{
            		alert("此团购购买数量不能低于:"+jo.obj.count+"个");
            		$(".n_tankzz1").show();
            		$(".n_jiaz").show();
            	}
							}else if(jo.result.businesscode == 2){
								alert("未注册");
								location.href=vge.webcontext+'/html/login/login.html?openid='+openid+"&recommenduserid="+recommenduserid+"&v="+Math.random();
							} else {
								alert(jo.result.msg);
							}
						} catch (e) {
							alert(e);
						}
					}, function(e) {
						alert(e);
					});

});


//新增 
function addAddress(){
	 var memberid=$("#memberid").val();
	 var groupid=$("#groupid").val();
	 var openid=$("#openid").val();
	 var products=$("#products").val();
	 location.href=vge.webcontext+'/html/address/add.html?flag=1&memberid='+memberid+"&groupid="+groupid+"&openid="+openid+"&products="+products+"&v="+Math.random();
}

//地址查询
function queryAddressList(){
	var memberid=$("#memberid").val();
	var groupid=$("#groupid").val();
	var openid=$("#openid").val();
	var products=$("#products").val();
	location.href=vge.webcontext+'/html/address/address_list.html?flag=1&memberid='+memberid+"&groupid="+groupid+"&openid="+openid+"&products="+products+"&v="+Math.random();
}

function orderSave(){
	$(".n_tankzz1").show();
	$(".n_jiaz").show();
	 var openid=$("#openid").val();
     var total=$("#total").val();
	 var memberid=$("#memberid").val();
	 var addressid=$("#addressid").val();
	 var groupid=$("#groupid").val();
	 var products=$("#products").val();
	 if(addressid==""){
		 $(".n_tankzz1").hide();
			$(".n_jiaz").hide();
		 alert("请填写地址");
		 
		 return false;
	 }
	 
	   var requestParam = getRequestParam();
		requestParam.commandinfo.total = total;
		requestParam.commandinfo.memberid = memberid;
		requestParam.commandinfo.groupid = groupid;
		requestParam.commandinfo.products = products;
		requestParam.commandinfo.addressid = addressid;
		requestParam.commandinfo.openid = openid;
		var url = vge.japi+'/payaction/orderSave.do';
		// post请求
		
		vge.ajxpost(url, JSON.stringify(requestParam), 100000, function(data) {
			try {
				var jo = JSON.parse(data);
				if (jo.result.businesscode == 0) {
					location.href=vge.webcontext+'/html/order/order_pay.html?opendid='+openid+"&orderid="+jo.obj.orderId+"&products="+products+"&addressid="+addressid+"&v="+Math.random();
				}
				else if((jo.result.businesscode == 2)){
					$(".n_tankzz1").hide();
					$(".n_jiaz").hide();
					alert("请填写地址");
					return false;
				}else if((jo.result.businesscode == 3)){
					$(".n_tankzz1").hide();
					$(".n_jiaz").hide();
					alert("未注册");
					location.href=vge.webcontext+'/html/login/login.html?openid='+openid+"&v="+Math.random();
				}else{
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

