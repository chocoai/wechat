(function() {
	var params = vge.urlparse(location.href);
	var openid=params.openid;
	if(params.openid == undefined){
		var requestParamOpenid = getRequestParam();
		requestParamOpenid.commandinfo.code = params.code;
		if(params.code != undefined){
			vge.ajxpost(vge.japi+'/wctUserInfo/getWctSnsapiBase.do',JSON.stringify(requestParamOpenid),10000,function(data){
				var jo = JSON.parse(data);
//				alert(data);
				if(jo.result.code == 0 && jo.reply != undefined){
//					alert("data->"+jo.reply.obj.openid);
					if(jo.reply.obj.openid != undefined){
//						alert(vge.webcontext + "/html/groupbuy/index.html?openid="+jo.reply.obj.openid);
						window.location= vge.webcontext + "/html/groupbuy/index.html?openid="+jo.reply.obj.openid+"&v="+Math.random();
//						doIndex(jo.reply.obj.openid);
					}else{
		        		WeixinJSBridge.call('closeWindow');
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
			alert("请在微信打开!code为空,不允许使用");
			WeixinJSBridge.call('closeWindow');
		}
	}else{
		doIndex(openid);
	}
	
	function doIndex(openid){
		var requestParam=getRequestParam();
		requestParam.commandtype="post";
		requestParam.commandinfo.openid=openid;
		var queryGroupbuyList=document.getElementById("queryGroupbuyList");
		vge.ajxpost(vge.japi+'/groupbuyAction/queryGroupbuyList.do',JSON.stringify(requestParam),10000,function(data){
			wx.ready(function() { //隐藏微信分享
                wx.hideOptionMenu();
            });
			try{
				var jo = JSON.parse(data);
				if(jo.result.code=="0"&&jo.result.businesscode=="0"){
					var objList=jo.objList;
					var html="";
					for(var i=0;i<objList.length;i++){
						var goUrl=vge.webcontext + "/html/groupbuy/groupbuydetail.html?openid="+openid+"&groupbuyid="+objList[i].id+"&v="+Math.random();
						var minGroupMember=objList[i].minGroupMember;
						var cdfined3=objList[i].cdfined3;
						if(objList[i].minGroupMember==undefined){
							minGroupMember=0;
						}
						if(objList[i].cdfined3==undefined){
							cdfined3=0;
						}
						//团购进度Math.round(objList[i].minGroupMember / (objList[i].cdfined3) * 10000) / 100.00 + "%"
						var groupplan=minGroupMember <= 0 ? "0%" : (Math.round(cdfined3 / minGroupMember * 100) + "%");
						if(parseFloat(groupplan)>parseFloat("100%")){
							groupplan="100%";
						}
						var address="不限区域";
						if(objList[i].countryName!=undefined){
							address = objList[i].countryName;
						}
						
						//节省金额
						var sparedollar=objList[i].cheapmoney;
						var item=getItem();
						var h=item.replace("[title]",objList[i].title).replace("[minGroupMember]",minGroupMember).replace("[cdfined3]",cdfined3).replace("[wtImgurl]",vge.alimgsrv+"/"+objList[i].wtImgurl).replace("[description]",objList[i].description).replace("[groupplan]",groupplan).replace("[restTime]",objList[i].restTime).replace("[sparedollar]",sparedollar).replace("[ban]",groupplan).replace("[address]",address).replace("[goUrl]",goUrl).replace("[imgGoUrl]",goUrl).replace("[descUrlGo]",goUrl);
						html+=h;
					}
					if(objList.length<=0){
						return;
					}
					queryGroupbuyList.innerHTML=html;
				}else{
					alert("网络请求超时了,请重试!");
				}
			}catch(e){
				alert(e);
			}
		}, function(t) {
			alert(t);
		});
	}
	
	
	
	

	function getItem(){
		var html="<div class=\"n_one \"><div class=\"NoneH1\"><span class=\"NoneS1\"></span><span class=\"NoneS2\">[title]</span><span class=\"NoneS3\">[address]</span></div>"
				+"<div class=\"NoneCon\"><div class=\"NoneSpin\"><div class=\"NoneHst\">火速团购</div><div class=\"NoneSpinTg\">团购人数 <span class=\"NoneSpRen\">[cdfined3]</span>/[minGroupMember]</div><a href=\"[imgGoUrl]\"><img src=\"[wtImgurl]\"/></a></div>"

				+"<div class=\"NoneWen\"><a href=\"[descUrlGo]\" class=\"NoneWenCan\">[description]</a></div><!--进度条--><div class=\"NoneJd\"><span class=\"NoneSpan1\">团购进度<span class=\"NoneSpan1S\">[groupplan]</span></span><span class=\"NoneSpan2\">剩余天数 <span class=\"NoneSpan2S\">[restTime]</span></span></div><div class=\"NoneJdt\"><span style=\"width: [ban];\" class=\"NoneJdtSpan\"></span></div>"
				+"<div class=\"NoneTuan\"><div class=\"NoneTuanJe\">节省金额</div><div class=\"NoneTuanQian\">[sparedollar] 元</div><a href=\"[goUrl]\"><div class=\"NoneTuanGou\">立即团购</div></a></div></div></div>";
		return html;
	}
})();
