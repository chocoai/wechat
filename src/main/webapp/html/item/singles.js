(function() {
	
	var params = vge.urlparse(location.href);
	var url = vge.japi+"/groupbuyAction/queryGroupbuyItemDetail.do"
	//拼装参数
	var req = getRequestParam();
	req.commandinfo.openid = params.openid;
	req.commandinfo.itemid = params.itemid;
	req.commandinfo.groupbuyid = params.groupbuyid;
	var reurl = vge.webcontext+'/html/groupbuy/detaillist.html?openid='+params.openid+'&groupbuyid='+params.groupbuyid;
	if(params.recommenduserid!=undefined){
		reurl = reurl+'&recommenduserid='+params.recommenduserid;
	}
	document.getElementById("returnbuy").setAttribute('href', reurl);
	
	vge.ajxpost(url, JSON.stringify(req), 10000, function(r) {
        // 从数据字典获取更新标记
        try {
            var jo = JSON.parse(r);
            if(jo.result.code == 0 && jo.obj != undefined){
            	//商品名称
            	document.getElementById("itemtitle").innerText = jo.obj.title;
            	document.getElementById("itemname").innerText = jo.obj.itemList[0].name;
            	//价格
            	document.getElementById("groupprice").innerText = "¥"+jo.obj.itemList[0].groupPrice;
            	//规则
            	document.getElementById("standard").innerText = jo.obj.itemList[0].standard;
            	//生产厂家
            	document.getElementById("manufacturer").innerText = jo.obj.itemList[0].manufacturer;
            	//批准文号
            	document.getElementById("approvenumber").innerText = jo.obj.itemList[0].approveNumber;
            	//商品详情图
            	document.getElementById("cdfined2").src = vge.alimgsrv+"/"+jo.obj.itemList[0].cdfined2;
            	//商品图
            	document.getElementById("cdfined3").src = vge.alimgsrv+"/"+jo.obj.itemList[0].cdfined3;
            	//月售
            	document.getElementById("salecount").src = jo.obj.cdfined3;
            }
        } catch (e) {
            alert(e);
        }
    }, function(e) {
        alert(e);
    });
	
})();