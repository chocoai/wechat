(function() {
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
						window.location= vge.webcontext + "/html/groupbuy/groupbuy_nostart.html?openid="+jo.reply.obj.openid+"&v="+Math.random();
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
			alert("为在微信打开!code为空,不允许使用");
		}
	}else{
		doIndex(openid);
	}
	
	
	function doIndex(openid){
		wx.ready(function() { //隐藏微信分享
            wx.hideOptionMenu();
        });
		var requestParam=getRequestParam();
		requestParam.commandtype="post";
		requestParam.commandinfo.openid=openid;
		var queryNoStartGroupBuyList=document.getElementById("queryNoStartGroupBuyList");
		vge.ajxpost(vge.japi+'/groupbuyAction/queryNoStartGroupBuyList.do',JSON.stringify(requestParam),10000,function(data){
			try{
				var jo = JSON.parse(data);
				if(jo.result.code=="0"&&jo.result.businesscode=="0"){
					var objList=jo.objList;
					var html="";
					for(var i=0;i<objList.length;i++){
						var minGroupMember=objList[i].minGroupMember;
						var cdfined3=objList[i].cdfined3;
						if(objList[i].minGroupMember==undefined){
							minGroupMember=0;
						}
						if(objList[i].cdfined3==undefined){
							cdfined3=0;
						}
						var address="不限区域";
						if(objList[i].countryName!=undefined){
							address = objList[i].countryName;
						}
						var item=getItem();
						var h=item.replace("[title]",objList[i].title).replace("[minGroupMember]",minGroupMember).replace("[cdfined3]",cdfined3).replace("[wtImgurl]",vge.alimgsrv+"/"+objList[i].wtImgurl).replace("[description]",objList[i].description).replace("[address]",address).replace("[restStartTime]",objList[i].restStartTime);
						html+=h;
					}
					if(objList.length<=0){
						return;
					}
					queryNoStartGroupBuyList.innerHTML=html;
				}else{
					alert("请求失败");
				}
			}catch(e){
				alert(e);
			}
		}, function(t) {
			alert(t);
		});
	}
	
	
	
	

	function getItem(){
		var html="<div class=\"n_one \">"
					+"<div class=\"NoneH1\">"
						+"<span class=\"NoneS1\"></span>"
						+"<span class=\"NoneS2\">[title]</span>"
						+"<span class=\"NoneS3\">[address]</span>"
					+"</div>"
					+"<div class=\"NoneCon\">"
					+"	<div class=\"NoneSpin\">"
							+"<div class=\"NoneHst\">火速团购</div>"
							+"<div class=\"NoneSpinTg\">团购人数 <span class=\"NoneSpRen\">[cdfined3]</span>/[minGroupMember]</div>"
							+"<a href=\"#\"><img src=\"[wtImgurl]\"/></a>"
						+"</div>"
						+"<div class=\"NoneWen\"><a href=\"#\" class=\"NoneWenCan\">[description]</a></div>"
						+"<div class=\"NoneJd\"><span class=\"NoneSpan2\"  style=\"float:none\">距离团购开始 <span class=\"NoneSpan2S\">[restStartTime]</span></span></div>"
						+"<div class=\"waitBtn\">敬请期待</div>"
					+"</div>"
		    	+"</div>";
		return html;
	}
})();
