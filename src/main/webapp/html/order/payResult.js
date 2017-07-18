
  $(function() {
	var params = vge.urlparse(location.href);
   var requestParam = getRequestParam();
	requestParam.commandinfo.orderid = params.orderid;
	requestParam.commandinfo.opendid = params.opendid;
	requestParam.commandinfo.addressid = params.addressid;
	requestParam.commandinfo.products = params.products;
	$("#opendid").val(params.opendid);
	$("#orderid").val(params.orderid);
	$("#addressid").val(params.addressid);
	wx.ready(function() { //隐藏微信分享
        wx.hideOptionMenu();
    });
	var url = vge.japi+'/payaction/payResult.do';
	// post请求
	vge.ajxpost(url, JSON.stringify(requestParam), 100000, function(data) {
		try {
			var jo = JSON.parse(data);
            if (jo.result.businesscode == 0) {
            	   $("#memberid").val(jo.obj.member);
            	   $("#groupid").val(jo.obj.groupBuy);
            	     $("#select1").html("");
            	     var select1 = "";
            	     select1+="<li><span>订单编号：</span><em>"+jo.obj.code+"</em></li>";
            	     select1+="<li><span>交易时间：</span><em>"+jo.obj.lastModified+"</em></li>";
            	     select1+="<li><span>支付方式：</span><em>微信支付</em></li>";
            	     select1+="<li><span>支付金额：</span><em class='redcolor'>￥"+jo.obj.total+"</em></li>";
				     $("#select1").append(select1);
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
  
  function viewOrder(){
	  var orderId=$("#orderid").val();
	  location.href=vge.webcontext+'/html/order/order_view.html?orderid='+orderId+"&v="+Math.random();
  }
  
  
  function viewTcodeId(){
	  var memberId=$("#memberid").val();
	  var groupId=$("#groupid").val();
	  location.href=vge.webcontext+'/html/member/my_qrcode.html?memberid='+memberId+"&groupid="+groupId+"&v="+Math.random();
  }
  
