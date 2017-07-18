(function() {
	
	var params = vge.urlparse(location.href);
	var url = vge.japi+"/groupbuyAction/queryGroupbuyDetail.do"
	//拼装参数
	var req = getRequestParam();
	req.commandinfo.openid = params.openid;
	req.commandinfo.recommenduserid = params.recommenduserid;
	req.commandinfo.groupbuyid = params.groupbuyid;
	
	var minBuyQuantity = 0;
	var minBuyMoney = 0;
	
	var t = $(".LiHdRtSlInput");
	
	if(params.openid == undefined){
		return;
	}
	
	//原价
	function setTotal(){ 
		var s=0; 
		var m=0;
		$(".LiHdSpin").each(function(){ 
			s+=parseInt($(this).find('.LiHdRtSlInput').val())*parseFloat($(this).find('.numMani').text()); //计算价格
			m+=parseInt($(this).find('.LiHdRtSlInput').val());//计算数量
		}); 
		$(".ListZjia").html(s.toFixed(2)); 
		$(".numShul").html(m.toFixed(0)); 
	} 
	
	$("#buycountbutton").click(function(){		
		var buycount ="";
		var count= 0 ;
		var money = 0;
		$(".LiHdSpin").each(function(){ 
			if($(this).find('.LiHdRtSlInput').val() > 0){
				buycount = buycount+$(this).find('.LiHdRtSlInput').next().val()+"@"+$(this).find('.LiHdRtSlInput').val()+",";
				count+=$(this).find('.LiHdRtSlInput').val()*1;
				money+=parseInt($(this).find('.LiHdRtSlInput').val())*parseFloat($(this).find('.numMani').text());
			}
		});
		
		if(buycount == ""){
			alert("请选择商品");
			return;
		}
		count = count*1;
		if(minBuyQuantity!=0 && minBuyQuantity!=undefined){
			if(count < minBuyQuantity){
				alert("此团购购买数量不能低于"+minBuyQuantity+"个");
				return;
			}
		}
		
		if(minBuyMoney!=0 && minBuyMoney!=undefined){
			money = money.toFixed(2);
			if(money < minBuyMoney){
				alert("此团购购买金额不能低于"+minBuyMoney+"元");
				return;
			}
		}
		
		
		buycount = buycount.substring(0,buycount.length-1);
		var url = vge.webcontext+"/html/order/order_creta.html?openid="+params.openid+"&groupid="+params.groupbuyid+"&products="+buycount+"&v="+Math.random();
		if(params.recommenduserid != undefined){
			url = url+"&recommenduserid="+params.recommenduserid;
		}
		window.location=url;
	})
	
	vge.ajxpost(url, JSON.stringify(req), 10000, function(r) {
		wx.ready(function() { //隐藏微信分享
            wx.hideOptionMenu();
        });
		
        // 从数据字典获取更新标记
        try {
            var jo = JSON.parse(r);
            var address="不限区域";
//            alert(r);
            if(jo.result.code == 0 && jo.obj != undefined &&(jo.result.businesscode == 0 || jo.result.businesscode == 4)){
            	//头信息
            	address="不限区域";
				if(jo.obj.countryName!=undefined){
					address = jo.obj.countryName;
				}
            	$(".LiHdQu").html(address);
            	$(".LiHdJdS").html(jo.obj.schedule);
            	$(".LiHdJinduSpan").width(jo.obj.schedule);
            	$("#fnTimeCountDown_0").fnTimeCountDown(jo.obj.endDateForJs);
            	
            	$("#groupbuytitle").html(jo.obj.title);
            	
            	minBuyQuantity = jo.obj.minBuyQuantity;
            	minBuyMoney = jo.obj.minBuyQuantity;
            	
            	//明细
            	var _html = '';
            	var detailurl = '';
            	var cdfined3 = "";
            	for(var i=0;i<jo.obj.itemList.length;i++){
            		detailurl = vge.webcontext+'/html/item/singles.html?openid='+params.openid+'&itemid='+jo.obj.itemList[i].id+'&groupbuyid='+jo.obj.id;
                	if(params.recommenduserid != undefined){
                		detailurl = detailurl+'&recommenduserid='+params.recommenduserid;
                	}
                	cdfined3 = jo.obj.cdfined3;
                	if(cdfined3 == undefined){
                		cdfined3 = "0";
                	}
            		_html += '<div class="LiHdSpin">'+
            					'<div class="LiHdTup">'+
            						'<div class="LiHdLeft">'+
            							'<a href="'+detailurl+'"><img src="'+vge.alimgsrv+"/"+jo.obj.itemList[i].cdfined3+'" /></a>'+
            						'</div>'+
            						'<div class="LiHdRight">'+
            							'<div class="LiHdRtH4">'+jo.obj.itemList[i].name+'</div>'+
            							'<div class="LiHdRtPz">'+jo.obj.itemList[i].description+'</div>'+
            							'<div class="LiHdRtSc">售出'+cdfined3+'份</div>	'+
            							
            							'<div class="LiHdRtJg">'+
            								'<div class="LiHdRtDanj">￥<span class="numMani">'+jo.obj.itemList[i].groupPrice+'</span></div>'+
            								'<div class="LiHdRtSl">'+
            									'<span class="LiHdRtSlJian">-</span>'+
            									'<input class="LiHdRtSlInput" readonly="true" maxlength="3" value="0" />'+
            									'<input type="hidden" value="'+jo.obj.itemList[i].productId+'" />'+
            									'<span class="LiHdRtSlJia">+</span>'+
            								'</div>'+
            							'</div>'+
            						'</div>'+
            					'</div>	'	+		
            			'</div>';
            	}

            	document.getElementById('listId').innerHTML = _html;
            	
            	$(".LiHdRtSlJia").click(function(){		
            		
            		$(this).siblings(".LiHdRtSlInput").val(parseInt($(this).siblings(".LiHdRtSlInput").val())+1)		
            		setTotal(); 
            	});
            	
            	$(".LiHdRtSlJian").click(function(){
            		var input=$(this).siblings(".LiHdRtSlInput").val()
            		if(input>0){
            		 $(this).siblings(".LiHdRtSlInput").val(parseInt($(this).siblings(".LiHdRtSlInput").val())-1)        
                     setTotal(); 
                   }
            	});
            	
            	setTotal();
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
	
})();