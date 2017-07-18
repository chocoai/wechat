$(function(){
	
	var params = vge.urlparse(location.href);
	var url = vge.japi+"/groupbuyAction/queryGroupbuyDetail.do"
	//拼装参数
	var req = getRequestParam();
	req.commandinfo.openid = params.openid;
	req.commandinfo.recommenduserid = params.recommenduserid;
	req.commandinfo.groupbuyid = params.groupbuyid;
	
	var minBuyQuantity = 0;
	var minBuyMoney = 0;
	
	if(params.openid == undefined){
		return;
	}
	
	vge.ajxpost(url, JSON.stringify(req), 10000, function(r) {
		wx.ready(function() { //隐藏微信分享
            wx.hideOptionMenu();
        });
        // 从数据字典获取更新标记
        try {
            var jo = JSON.parse(r);
            if(jo.result.code == 0 && jo.obj != undefined && (jo.result.businesscode == 0 || jo.result.businesscode == 4)){
            	
            	$(".SinJdH4").html(jo.obj.title);
            	var address="不限区域";
				if(jo.obj.countryName!=undefined){
					address = jo.obj.countryName;
				}
				minBuyQuantity = jo.obj.minBuyQuantity;
            	minBuyMoney = jo.obj.minBuyQuantity;
            	$(".SinDiqu").html(address);
            	//进度条数据 注百分比格式
            	$(".SinJdtiaoSp").width(jo.obj.schedule);
            	$(".SinJdBaifen").html(jo.obj.schedule);
            	//商品名称
            	$(".SinSlH4").html(jo.obj.itemList[0].name);
            	//售卖数量
            	$(".sinnum").html(jo.obj.cdfined3);
            	//价格
            	$(".numMani").html(jo.obj.itemList[0].groupPrice);
            	$("#fnTimeCountDown_0").fnTimeCountDown(jo.obj.endDateForJs);
            	//商品详情图
            	document.getElementById("cdfined2").src = vge.alimgsrv+"/"+jo.obj.itemList[0].cdfined2;
            	//商品图
            	document.getElementById("cdfined3").src = vge.alimgsrv+"/"+jo.obj.itemList[0].cdfined3;
            	//规则
            	document.getElementById("standard").innerText = jo.obj.itemList[0].standard;
            	//生产厂家
            	document.getElementById("manufacturer").innerText = jo.obj.itemList[0].manufacturer;
            	//批准文号
            	document.getElementById("approvenumber").innerText = jo.obj.itemList[0].approveNumber;
            	//商品名称
            	document.getElementById("itemname").innerText = jo.obj.itemList[0].name;
            	//商品id
            	document.getElementById("itemid").value = jo.obj.itemList[0].productId;
            }else if( jo.result.businesscode == 2){
            	//2:未注册用户无权限查看
            	var url = vge.webcontext+"/html/login/login.html?openid="+params.openid+"&v="+Math.random();
            	if(params.recommenduserid!=undefined){
            		url = url + "&recommenduserid=" + params.recommenduserid;
            	}
            	window.location = url;
            }else if( jo.result.businesscode == 3){
            	//3:未认证用户无权查看
            	window.location = vge.webcontext+"/html/clinic/clinic_disclaimer.html?openid="+params.openid+"&v="+Math.random();
            }else if( jo.result.businesscode == 5){
            	//5:待审核用户
            	window.location = vge.webcontext+"/html/clinic/clinic_check.html?openid="+params.openid+"&v="+Math.random();
            }else if( jo.result.businesscode == 6){
            	//6:审核不通过用户
            	var url = vge.webcontext + "/html/clinic/clinic_error.html?openid=" + params.openid + "&certificateOpinion=" +jo.result.msg+ "&v="+Math.random();
            	url = encodeURI(url)
            	window.location = url;
            }else{
            	alert("页面加载失败,code->"+jo.result.code+",业务代码->"+jo.result.businesscode);
            }
        } catch (e) {
            alert(e);
        }
    }, function(e) {
        alert(e);
    });
	
	var t = $(".LiHdRtSlInput");
	
	$(".LiHdRtSlJia").click(function(){		
		
		$(this).siblings(".LiHdRtSlInput").val(parseInt($(this).siblings(".LiHdRtSlInput").val())+1)		
		setTotal(); 
		
	})
	
	$(".LiHdRtSlJian").click(function(){
		var input=$(this).siblings(".LiHdRtSlInput").val()
		if(input>0){
		 $(this).siblings(".LiHdRtSlInput").val(parseInt($(this).siblings(".LiHdRtSlInput").val())-1)        
         setTotal(); 
       
       }
	})
	
	//立即抢购
	$(".LiFloatBut").click(function(){
		var count = document.getElementById("count").value;
		if(count == 0){
			alert("请选择要购买的商品数量");
			return;
		}
		var itemid = document.getElementById("itemid").value;
		var buycount = itemid + "@" + count;
		
		count = count*1;
		if(minBuyQuantity!=0 && minBuyQuantity!=undefined){
			if(count < minBuyQuantity){
				alert("此团购购买数量不能低于"+minBuyQuantity+"个");
				return;
			}
		}
		
		if(minBuyMoney!=0 && minBuyMoney!=undefined){
			var numMani = $(".numMani").html()*1;
			var money = count*numMani;
			if(money < minBuyMoney){
				alert("此团购购买金额不能低于"+minBuyMoney+"元");
				return;
			}
		}
		
		var url = vge.webcontext+"/html/order/order_creta.html?openid="+params.openid+"&groupid="+params.groupbuyid+"&products="+buycount+"&v="+Math.random();
		if(params.recommenduserid != undefined){
			url = url+"&recommenduserid="+params.recommenduserid;
		}
		window.location=url;
	})
	
	function setTotal(){
		var s=0; 
		var m=0;
		$(".SinFa").each(function(){ 
			s+=parseInt($(this).find('.LiHdRtSlInput').val())*parseFloat($(this).find('.numMani').text()); //计算价格
		
			m+=parseInt($(this).find('.LiHdRtSlInput').val());//计算数量
		}); 
		$(".ListZjia").html(s.toFixed(2)); 
		$(".numShul").html(m.toFixed(0)); 
	}
	
})

