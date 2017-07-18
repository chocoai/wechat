$(function() {
	var params = vge.urlparse(location.href);
	var openid=params.openid;
	if(params.openid == undefined){
		var requestParamOpenid = getRequestParam();
		requestParamOpenid.commandinfo.code = params.code;
		if(params.code != undefined){
			vge.ajxpost(vge.japi+'/wctUserInfo/getWctSnsapiBase.do',JSON.stringify(requestParamOpenid),10000,function(data){
				var jo = JSON.parse(data);
				if(jo.result.code == 0 && jo.reply != undefined){
					if(jo.reply.obj.openid != undefined){
						window.location= vge.webcontext + "/html/order/order_list.html?openid="+jo.reply.obj.openid;
					}
				}else{
					alert("openid加载失败,失败原因->"+jo.result.msg+",失败代码->"+jo.result.code+",业务代码->"+jo.result.businesscode);
				}
			}, function(t) {
				alert(t);
			});
		}else{
			//跳转获取页面原参数
//			var urlparams = url.substr(url.indexOf('?') + 1);
			alert("为在微信打开!code为空,不允许使用");
		}
	}else{
	     alert(openid);	
             doIndex(openid);
	}  });


	function doIndex(openid){
		var requestParam=getRequestParam();
		$("#openid").val(openid);
		requestParam.commandinfo.openid =openid;
		requestParam.commandinfo.status = null;
		requestParam.commandinfo.paymenStatus = "1";
		requestParam.commandinfo.shippingStatus = null;

		var url = vge.japi+'/payaction/queryOrderList.do';
		// post请求
		vge.ajxpost(url, JSON.stringify(requestParam), 100000, function(data) {
			try {
				var jo = JSON.parse(data);
	            if (jo.result.businesscode == 0) {
									$("#orderList").html("");
									var select = "";
									for (var i = 0; i < jo.objList.length; i++) {
										select += "<div class='To_be_paid'>";
										if(jo.objList[i].status=='1' && jo.objList[i].paymentStatus=='1' ){
										    select += "<div class='To_be_paid_t'><span></span>待支付</div>";
										}else if(jo.objList[i].paymentStatus=='2' && jo.objList[i].shippingStatus=='1'){
											select += "<div class='To_be_paid_t'><span></span>待发货</div>";
										}else if(jo.objList[i].status=='2' && jo.objList[i].shippingStatus=='2'){
											select += "<div class='To_be_paid_t'><span></span>待收货</div>";
										}else if(jo.objList[i].status=='4' ){
											select += "<div class='To_be_paid_t'><span></span>已完成</div>";
										}
										select += "<dl class='To_be_paid_title'>";
										select += "<dt>订单编号："+jo.objList[i].code+"</dt>";
										if(jo.objList[i].paymentStatus=='1'&&jo.objList[i].status=='1'){
										    select += "<a onclick='orderPay("+jo.objList[i].orderId+")'> <dd>付款</dd> </a>";
										}else if(jo.objList[i].paymentStatus=='2'&&jo.objList[i].shippingStatus=='1'){
// 											select += "<dd></dd>";
										}else if(jo.objList[i].status=='2'&&jo.objList[i].shippingStatus=='2'){
											select += "<dd>确认收货</dd>";
										}else if(jo.objList[i].status=='4'){
// 											select += "<dd></dd>";
										}
										select += "</dl>";
										select += "<a onclick='viewOrder("+jo.objList[i].orderId+")'>";
										for (var j = 0; j < jo.objList[i].shopOrderItem.length; j++) {
										select += " <ul class='To_be_paid_list'>";
										select += " <li>";
										select += "  <dl>";
										select += "   <dt><img src='"+vge.alimgsrv+"/"+jo.objList[i].shopOrderItem[j].cdfined3+"'/><span>"+jo.objList[i].shopOrderItem[j].name+"</span></dt>";
										select += "    <dd>";
										select += "      <p>￥"+jo.objList[i].shopOrderItem[j].salePrice+"</p>";
										select += "      <p>x "+jo.objList[i].shopOrderItem[j].count+"</p>";
										select += "    </dd>";
										select += "   </dl>";
										select += " </li>";
										select += " </ul>";
										}
										select += "</a>";
										select += "  <div class='Total'>总计：<span>￥"+jo.objList[i].total+"</span></div>	";
										select += "</div>";

									}
									$("#orderList").append(select);
								} else if(jo.result.businesscode == 3){
									alert("未注册");
									location.href=vge.webcontext+'/html/login/login.html?openid='+openid;
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
   
  function queryList(status,paystatus,spingstatus){
	   var requestParam = getRequestParam();
		requestParam.commandinfo.openid = $("#openid").val();
		requestParam.commandinfo.status = status;
		requestParam.commandinfo.paymenStatus = paystatus;
		requestParam.commandinfo.shippingStatus = spingstatus;
        var openid=$("#openid").val();
		var url = vge.japi+'/payaction/queryOrderList.do';
		// post请求
		vge.ajxpost(url, JSON.stringify(requestParam), 100000, function(data) {
			try {
				var jo = JSON.parse(data);
	            if (jo.result.businesscode == 0) {
									$("#orderList").html("");
									var select = "";
									for (var i = 0; i < jo.objList.length; i++) {
										select += "<div class='To_be_paid'>";
										if(jo.objList[i].status=='1' && jo.objList[i].paymentStatus=='1' ){
										    select += "<div class='To_be_paid_t'><span></span>待支付</div>";
										}else if(jo.objList[i].paymentStatus=='2' && jo.objList[i].shippingStatus=='1'){
											select += "<div class='To_be_paid_t'><span></span>待发货</div>";
										}else if(jo.objList[i].status=='2' && jo.objList[i].shippingStatus=='2'){
											select += "<div class='To_be_paid_t'><span></span>待收货</div>";
										}else if(jo.objList[i].status=='4' ){
											select += "<div class='To_be_paid_t'><span></span>已完成</div>";
										}
										select += "<dl class='To_be_paid_title'>";
										select += "<dt>订单编号："+jo.objList[i].code+"</dt>";
										if(jo.objList[i].paymentStatus=='1'&&jo.objList[i].status=='1'){
										    select += "<dd>付款</dd>";
										}else if(jo.objList[i].paymentStatus=='2'&&jo.objList[i].shippingStatus=='1'){
//											select += "<dd></dd>";
										}else if(jo.objList[i].status=='2'&&jo.objList[i].shippingStatus=='2'){
											select += "<dd>确认收货</dd>";
										}else if(jo.objList[i].status=='4'){
//											select += "<dd></dd>";
										}
										select += "</dl>";
										select += "<a onclick='viewOrder("+jo.objList[i].orderId+")'>";
										for (var j = 0; j < jo.objList[i].shopOrderItem.length; j++) {
										select += " <ul class='To_be_paid_list'>";
										select += " <li>";
										select += "  <dl>";
										select += "   <dt><img src='"+vge.alimgsrv+"/"+jo.objList[i].shopOrderItem[j].cdfined3+"'><span>"+jo.objList[i].shopOrderItem[j].name+"</span></dt>";
										select += "    <dd>";
										select += "      <p>￥"+jo.objList[i].shopOrderItem[j].salePrice+"</p>";
										select += "      <p>x "+jo.objList[i].shopOrderItem[j].count+"</p>";
										select += "    </dd>";
										select += "   </dl>";
										select += " </li>";
										select += " </ul>";
										}
										select += "</a>";
										select += "  <div class='Total'>总计：<span>￥"+jo.objList[i].total+"</span></div>	";
										select += "</div>";

									}
									$("#orderList").append(select);
								} else if(jo.result.businesscode == 3){
									alert("未注册");
									location.href=vge.webcontext+'/html/login/login.html?openid='+openid;
								} else {
									alert(jo.result.msg);
								}
							} catch (e) {
								alert(e);
							}
						}, function(e) {
							alert(e);
						});

	}
   
  function viewOrder(orderId){
	  var openId=$("#openid").val();
	   location.href='order_view.html?orderid='+orderId+"&openid="+openId;
  }
   
  
  function orderPay(d){
	  var openId=$("#openid").val();
	  location.href=vge.webcontext+'/html/order/order_pay.html?opendid='+openId+"&orderid="+d;
}
   
