

$(function() {
	var params = vge.urlparse(location.href);
	$("#orderid").val(params.orderid);
	$("#opendid").val(params.openid);
	
   var requestParam = getRequestParam();
	requestParam.commandinfo.orderid = params.orderid;
	wx.ready(function() { //隐藏微信分享
        wx.hideOptionMenu();
    });
	var url = vge.japi+'/payaction/orderView.do';
	// post请求
	vge.ajxpost(url, JSON.stringify(requestParam), 100000, function(data) {
		try {
							var jo = JSON.parse(data);
							if (jo.result.businesscode == 0) {
								$("#select1").html("");
								$("#select2").html("");
								$("#select3").html("");
								$("#select4").html("");
								$("#select5").html("");
								var select1 = "";
								var select2 = "";
								var select3 = "";
								var select4 = "";
								var select5 = ""
								select1 += "<span></span>";
								select2 += "<span>订单编号：</span><span>"
										+ jo.obj.code + "</span>";
								select3 += "	<p><span>" + jo.obj.receiveName
										+ "</span> <span>"
										+ jo.obj.receiveMobile + "</span></p>";
								select3 += "	<p>" + jo.obj.receiveAddress
										+ "</p>";
								select4 += "	<span>订单总计</span><span class='daiJiage'>￥"
										+ jo.obj.total + "</span>";

								if (jo.obj.status == '1'
										&& jo.obj.paymentStatus == '1') {
									select1 += "<span>待付款</span>";
									select5 += "<button class='daibut' onclick='orderPay()'>付款</button>";
								} else if (jo.obj.paymentStatus == '2'
										&& jo.obj.shippingStatus == '1' && jo.obj.status!='8') {
									select1 += "<span>待发货</span>";
								} else if (jo.obj.status == '2'
										&& jo.obj.shippingStatus == '2') {
									select1 += "<span>待收货</span>";
									select5 += "<button class='daibut' onclick='orderConfirm()' >确认收货</button>";
								} else if (jo.obj.status == '4') {
									select1 += "<span>已完成</span>";
								}else if (jo.obj.status == '8') {
									select1 += "<span>已取消</span>";
								}

								select1 += "<span>" + jo.obj.createTime
										+ "</span>";
								$("#select1").append(select1);
								$("#select2").append(select2);
								$("#select3").append(select3);
								$("#select4").append(select4);
								$("#select5").append(select5);
								$("#orderView").html("");
								var select = "";
								for (var i = 0; i < jo.obj.shopOrderItem.length; i++) {
									select += "<div class='daiSpin'> ";
									select += "<div class='daiSpinImg'><img src='"
											+ vge.alimgsrv
											+ "/"
											+ jo.obj.shopOrderItem[i].cdfined3
											+ "'/></div>	";
									select += "<div class='daiSpinn'>					";
									select += "" + jo.obj.shopOrderItem[i].name
											+ "  ";
									select += "</div>";
									select += "<div class='daiSpJge'>";
									select += "	<p>￥<span>"
											+ jo.obj.shopOrderItem[i].salePrice
											+ "</span></p>";
									select += "	<p>x<span>"
											+ jo.obj.shopOrderItem[i].count
											+ "</span></p>";
									select += "</div>				";
									select += "</div>";

								}
								$("#orderView").append(select);
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

function orderPay(){
	  var orderId=$("#orderid").val();
	  var openId=$("#opendid").val();
	  location.href=vge.webcontext+'/html/order/order_pay.html?opendid='+openId+"&orderid="+orderId+"&v="+Math.random();
}

function orderConfirm(){
	var orderId=$("#orderid").val();
	  var openId=$("#opendid").val();
		var requestParam = getRequestParam();
		requestParam.commandinfo.orderid = orderId;
		requestParam.commandinfo.opendid = openId;
		var url = vge.japi + '/payaction/orderConfirm.do';
		// post请求
		vge.ajxpost(url, JSON.stringify(requestParam), 100000, function(data) {
			try {
				var jo = JSON.parse(data);
				if (jo.result.businesscode == 0) {
					alert("已成功");
					location.href=vge.webcontext+'/html/order/order_list.html?openid='+openId+"&v="+Math.random();
				} else if (jo.result.businesscode == 2) {
					alert("未注册");
					location.href=vge.webcontext+'/html/login/login.html?openid='+openId+"&v="+Math.random();
				}else {
					alert(jo.result.msg);
				}
			} catch (e) {
				alert(e);
			}
		}, function(e) {
			alert(e);
		});

	}


