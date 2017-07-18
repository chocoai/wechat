

  $(function() {
	var params = vge.urlparse(location.href);
   var requestParam = getRequestParam();
	requestParam.commandinfo.orderid = params.orderid;
	requestParam.commandinfo.opendid = params.opendid;
	$("#products").val(params.products);
	$("#opendid").val(params.opendid);
	$("#orderid").val(params.orderid);
	$("#addressid").val(params.addressid);
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
            	     $("#select1").html("");
            	     $("#select2").html("");
            	     var select1 = "￥"+jo.obj.total+"";
					 var select2 = "合计：￥<span>"+jo.obj.total+"</span>";
					 var select3 = "<img  src='"+vge.alimgsrv+"/"+jo.obj.piceUrl+"'/>";
					 var select4 = jo.obj.groupName;
					 
            	     $("#total").val(jo.obj.total);
            	     
				     $("#select1").append(select1);
				     $("#select2").append(select2);
				     $("#select3").append(select3);
				     $("#select4").append(select4);
				     
							}  else {
								alert(jo.result.msg);
							}
						} catch (e) {
							alert(e);
						}
					}, function(e) {
						alert(e);
					});

}); 
  
  
  
	//微信支付方法（点击按键调用）
	function pay() {
		if (typeof WeixinJSBridge == "undefined") {
			if (document.addEventListener) {
				document.addEventListener('WeixinJSBridgeReady',
						onBridgeReady, false);
			} else if (document.attachEvent) {
				document.attachEvent('WeixinJSBridgeReady',
						onBridgeReady);
				document.attachEvent('onWeixinJSBridgeReady',
						onBridgeReady);
			}
		} else {
			onBridgeReady();
		}
	}


//微信支付h5方法
function onBridgeReady() {
	// 获取appId、timeStamp、nonceStr、package、signType、paySign
	$(".n_tankzz1").show();
	$(".n_jiaz").show();
	
	var openId = $("#opendid").val()
	var total = $("#total").val();
	var orderId =$("#orderid").val();
	var products =$("#products").val();
	var addressid =$("#addressid").val();

	var appId;
	var timeStamp;
	var nonceStr;
	var signType;
	var packAge;
	var paySign;
	var requestParam = getRequestParam();
	requestParam.commandinfo.openid = openId;
	requestParam.commandinfo.total = total;
	requestParam.commandinfo.orderid = orderId;
	var url = vge.japi+'/payaction/orderPay.do';
	// post请求
	vge.ajxpost(url, JSON.stringify(requestParam), 100000, function(data) {
		try {
			var jo = JSON.parse(data);
            if (jo.result.businesscode == 0) {
            	var pageId=JSON.parse(jo.obj.pageId);
             	appId = pageId["appId"];
    			timeStamp = pageId["timeStamp"];
    			nonceStr = pageId["nonceStr"];
    			signType = pageId["signType"];
    			packAge = pageId["package"];
    			paySign = pageId["paySign"];
    			WeixinJSBridge.invoke('getBrandWCPayRequest', {
    				"appId" : appId, // 公众号名称，由商户传入
    				"timeStamp" : timeStamp, // 时间戳，自1970年以来的秒数
    				"nonceStr" : nonceStr, // 随机串
    				"package" : packAge,
    				"signType" : signType, // 微信签名方式:
    				"paySign" : paySign
    			// 微信签名
    			},
            
    			function(res) {
    				if (res.err_msg == "get_brand_wcpay_request:ok") {
    					
    					location.href=vge.webcontext+'/html/order/payResult.html?opendid='+openId+"&orderid="+orderId+"&products="+products+"&addressid="+addressid+"&v="+Math.random();
    				}else if(res.err_msg == "get_brand_wcpay_request:cancel"){
    					$(".n_tankzz1").hide();
    					$(".n_jiaz").hide();
    					alert("取消成功");
    				}else if(res.err_msg == "get_brand_wcpay_request:fail"){
    					$(".n_tankzz1").hide();
    					$(".n_jiaz").hide();
    					alert("支付失败");
    				}
    			});
            	}else if(jo.result.businesscode == 2){
            		$(".n_tankzz1").hide();
					$(".n_jiaz").hide();
					alert("未注册");
					location.href=vge.webcontext+'/html/login/login.html?openid='+openId+"&v="+Math.random();
				} else if(jo.result.businesscode == 3){
            		$(".n_tankzz1").hide();
					$(".n_jiaz").hide();
					alert("订单已支付");
					location.href=vge.webcontext+'/html/order/order_list.html?openid='+openId+"&v="+Math.random();
				}else {
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
  
  
