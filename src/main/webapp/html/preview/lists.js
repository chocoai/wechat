(function() {
	
	var params = vge.urlparse(location.href);
	if(params.openid == undefined){
		var requestParamOpenid = getRequestParam();
		requestParamOpenid.commandinfo.code = params.code;
		if(params.code != undefined){
			vge.ajxpost(vge.japi+'/wctUserInfo/getWctSnsapiBase.do',JSON.stringify(requestParamOpenid),10000,function(data){
				var jo = JSON.parse(data);
				if(jo.result.code == 0 && jo.reply != undefined){
					if(jo.reply.obj.openid != undefined){
						var paramArr = params.paramstr.split("@");
						var url = vge.webcontext + "/html/preview/lists.html?openid="+jo.reply.obj.openid+"&groupbuyid="+paramArr[0];
						if(paramArr[1]!= undefined){
	            			url = url + "&recommenduserid="+paramArr[1];
	            		}
						window.location= url;
//						doCheck(params.groupbuyid,jo.reply.obj.openid);
					}
				}else{
					alert("openid加载失败,失败原因->"+jo.result.msg+",失败代码->"+jo.result.code+",业务代码->"+jo.result.businesscode);
				}
			}, function(t) {
				alert(t);
			});
		}else{
			//跳转获取页面原参数
			var url = vge.webcontext + "/html/preview/lists.html?paramstr="+params.groupbuyid+"@"+params.recommenduserid;
			sendRedirectByGetCode(url);
			//alert("为在微信打开!code为空,不允许使用");
		}
	}
	else{
		doCheck(params.groupbuyid,params.openid);
	}
	
	
	function doCheck(groupbuyid,openid){
		var url = vge.japi+"/groupbuyAction/queryGroupbuyItemCount.do"
		//拼装参数
		var req = getRequestParam();
		req.commandinfo.groupbuyid = groupbuyid;
		req.commandinfo.openid = openid;
		
		vge.ajxpost(url, JSON.stringify(req), 10000, function(r) {
			wx.ready(function() { //隐藏微信分享
	            wx.hideOptionMenu();
	        });
	        try {
	            var jo = JSON.parse(r);
//	            alert(r);
	            if(jo.result.code == 0 && jo.obj != undefined){
	            	if(jo.obj == 1){
	            		var url = vge.webcontext+"/html/preview/single.html?openid="+params.openid+"&groupbuyid="+params.groupbuyid+"&v="+Math.random();
	            		if(params.recommenduserid!= undefined){
	            			url = url + "&recommenduserid="+params.recommenduserid;
	            		}
	            		window.location=url;
	            	}else if(jo.obj < 1){
	            		alert("页面加载异常,该团购无商品");
	            	}else{
	            		doIndex(openid,groupbuyid)
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
	}
	
	function doIndex(openid,groupbuyid){
		
		var params = vge.urlparse(location.href);
		var url = vge.japi+"/groupbuyAction/queryGroupbuyPreview.do"
		//拼装参数
		var req = getRequestParam();
		req.commandinfo.openid = openid;
		req.commandinfo.recommenduserid = params.recommenduserid;
		req.commandinfo.groupbuyid = groupbuyid;
		
		vge.ajxpost(url, JSON.stringify(req), 10000, function(r) {
	        // 从数据字典获取更新标记
	        try {
	            var jo = JSON.parse(r);
//	            alert(r);
	            if(jo.result.code == 0 && jo.obj != undefined && jo.result.businesscode != 0){
	            	if(jo.result.businesscode == 2){
	            		$("#rega").attr('href',vge.webcontext+"/html/login/login.html?openid="+openid+"&recommenduserid="+params.recommenduserid+"&v="+Math.random());
	            		$("#rega").html('<div class="LiHdFloat2">我要注册</div>')
	            		$(".LiXiangxiA").attr('href',vge.webcontext+"/html/login/login.html?openid="+openid+"&recommenduserid="+params.recommenduserid+"&v="+Math.random());
	            		$(".LiXiangxiA").text("详细内容请注册后查阅");
	            	}else if(jo.result.businesscode == 3){
	            		$("#rega").attr('href',vge.webcontext+"/html/clinic/clinic_disclaimer.html?openid="+openid+"&recommenduserid="+params.recommenduserid+"&v="+Math.random());
	            		$("#rega").html('<div class="LiHdFloat2">我要认证</div>')
	            		$(".LiXiangxiA").attr('href',vge.webcontext+"/html/clinic/clinic_disclaimer.html?openid="+openid+"&recommenduserid="+params.recommenduserid+"&v="+Math.random());
	            		$(".LiXiangxiA").text("详细内容请认证后查阅");
	            	}else if(jo.result.businesscode == 4){
	            		$("#rega").html('<div class="LiHdFloat2">团购已结束</div>')
	            		$(".LiXiangxiA").text("团购已结束");
	            	}else if(jo.result.businesscode == 5){
	            		$("#rega").attr('href',"#");
	            		$("#rega").html('<div class="LiHdFloat2">认证审核中,请耐心等待</div>')
	            		$(".LiXiangxiA").attr('href',"#");
	            		$(".LiXiangxiA").text("认证审核中,审核通过后查阅");
	            	}else if(jo.result.businesscode == 6){
	            		var url = vge.webcontext + "/html/clinic/clinic_error.html?openid=" + openid + "&certificateOpinion=" +jo.result.msg+ "&v="+Math.random();
	                	url = encodeURI(url)
	            		$("#rega").attr('href',url);
	            		$("#rega").html('<div class="LiHdFloat2">认证失败,请重新提交认证资料</div>')
	            		$(".LiXiangxiA").attr('href',url);
	            		$(".LiXiangxiA").text("我要重新认证");
	            	}
	            	
	            	//头信息
	            	$(".LiHdH3").html(jo.obj.title);
	            	var address="不限区域";
					if(jo.obj.countryName!=undefined){
						address = jo.obj.countryName;
					}
	            	$(".LiHdQu").html(address);
	            	$(".LiHdJdS").html(jo.obj.schedule);
	            	$(".LiHdJinduSpan").width(jo.obj.schedule);
	            	$("#fnTimeCountDown_0").fnTimeCountDown(jo.obj.endDateForJs);
	            	//明细
	            	var _html = '';
	            	var detailurl = '';
	            	var cdfined3 = '';
	            	for(var i=0;i<jo.obj.itemList.length;i++){
	            		cdfined3 = jo.obj.cdfined3;
	            		if(cdfined3 == undefined){
	            			cdfined3 = "0";
	            		}
	                	_html+='<div class="LiHdTup">'+
	                				'<div class="LiHdLeft">'+
	                					'<a href="#"><img src="'+vge.alimgsrv+"/"+jo.obj.itemList[i].cdfined3+'" /></a>'+
	                				'</div>'+
	                				'<div class="LiHdRight">'+
	                					'<div class="LiHdRtH4">'+jo.obj.itemList[i].name+'</div>'+
	                					'<div class="LiHdRtPz">'+jo.obj.itemList[i].description+'</div>'+
	                					'<div class="LiHdRtSc">本次共售出'+cdfined3+'份</div>'+
	                					
	                					'<div class="LiHdRtJg">	'+			
	                						
	                					'</div>'+
	                				'</div>'+
	                			'</div>';
	            	}

	            	document.getElementById('LiHdSpin').innerHTML = _html;
	            	
	            }else if( jo.result.businesscode == 0){
	            	var url = vge.webcontext+"/html/groupbuy/detaillist.html?openid="+params.openid+"&groupbuyid="+params.groupbuyid+"&v="+Math.random();
	            	if(params.recommenduserid!=undefined){
	            		url = url + "&recommenduserid=" + params.recommenduserid;
	            	}
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
		
	}
	
})();