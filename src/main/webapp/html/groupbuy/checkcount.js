(function() {
	
	var params = vge.urlparse(location.href);
	var url = vge.japi+"/groupbuyAction/queryGroupbuyItemCount.do"
	//拼装参数
	var req = getRequestParam();
	req.commandinfo.groupbuyid = params.groupbuyid;
	
	vge.ajxpost(url, JSON.stringify(req), 10000, function(r) {
        try {
            var jo = JSON.parse(r);
//            alert(r);
            if(jo.result.code == 0 && jo.obj != undefined){
            	if(jo.obj == 1){
            		var url = vge.webcontext+"/html/groupbuy/detailsingle.html?openid="+params.openid+"&groupbuyid="+params.groupbuyid+"&v="+Math.random();
            		if(params.recommenduserid!= undefined){
            			url = url + "&recommenduserid="+params.recommenduserid;
            		}
            		window.location=url;
            	}else if(jo.obj < 1){
            		alert("页面加载异常,该团购无商品");
            	}
            }else{
            	alert("页面加载异常,错误代码->"+jo.result.businesscode);
            }
        } catch (e) {
            alert(e);
        }
    }, function(e) {
        alert(e);
    });
	
})();