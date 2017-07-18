$(function(){
	
	var params = vge.urlparse(location.href);
	var url = vge.japi+"/groupbuyAction/queryGroupbuyPreview.do"
	//拼装参数
	var req = getRequestParam();
	req.commandinfo.openid = params.openid;
	req.commandinfo.recommenduserid = params.recommenduserid;
	req.commandinfo.groupbuyid = params.groupbuyid;
	
	vge.ajxpost(url, JSON.stringify(req), 10000, function(r) {
		wx.ready(function() { //隐藏微信分享
            wx.hideOptionMenu();
        });
        // 从数据字典获取更新标记
        try {
            var jo = JSON.parse(r);
//            alert(r);
            if(jo.result.code == 0 && jo.obj != undefined && jo.result.businesscode != 0 ){
            	if(jo.result.businesscode == 2){
            		$("#rega").attr('href',vge.webcontext+"/html/login/login.html?openid="+params.openid+"&recommenduserid="+params.recommenduserid+"&v="+Math.random());
            		$("#rega").html('<div class="SinFloat">我要注册</div>')
            		$(".LiXiangxiA").attr('href',vge.webcontext+"/html/login/login.html?openid="+params.openid+"&recommenduserid="+params.recommenduserid+"&v="+Math.random());
            		$(".LiXiangxiA").text("详细内容请注册后查阅");
            	}else if(jo.result.businesscode == 3){
            		$("#rega").attr('href',vge.webcontext+"/html/clinic/clinic_disclaimer.html?openid="+params.openid+"&recommenduserid="+params.recommenduserid+"&v="+Math.random());
            		$("#rega").html('<div class="SinFloat">我要认证</div>')
            		$(".LiXiangxiA").attr('href',vge.webcontext+"/html/clinic/clinic_disclaimer.html?openid="+params.openid+"&recommenduserid="+params.recommenduserid+"&v="+Math.random());
            		$(".LiXiangxiA").text("详细内容请认证后查阅");
            	}else if(jo.result.businesscode == 4){
            		$("#rega").html('<div class="SinFloat">团购已结束</div>');
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
            	
            	$(".SinBanenr").append("<img src='"+vge.alimgsrv+"/"+jo.obj.itemList[0].cdfined3+"'/>");
            	var address="不限区域";
				if(jo.obj.countryName!=undefined){
					address = jo.obj.countryName;
				}
            	$(".SinJdH4").html(address);
            	$(".SinDiqu").html(address);
            	$(".SinJdBaifen").html(jo.obj.schedule);
            	$(".SinJdtiaoSp").width(jo.obj.schedule);
            	$("#fnTimeCountDown_0").fnTimeCountDown(jo.obj.endDateForJs);
            	$(".SinSpxImg").append("<img src='"+vge.alimgsrv+"/"+jo.obj.itemList[0].cdfined2+"'/>");
            	
            	var _html = '';
            	_html+='<div class="SinSpxName">名称：<span>'+jo.obj.itemList[0].name+'</span></div>'
            			+'<div class="SinSpxName">规格：<span>'+jo.obj.itemList[0].standard+'</span></div>'
            			+'<div class="SinSpxName">生产厂家：<span>'+jo.obj.itemList[0].manufacturer+'</span></div>'
            			+'<div class="SinSpxName">批准文号：<span>'+jo.obj.itemList[0].approveNumber+'</span></div>';
            			
            	document.getElementById('SinSpxNa').innerHTML = _html;
            	
            }else if( jo.result.businesscode == 0){
            	var url = vge.webcontext+"/html/groupbuy/detailsingle.html?openid="+params.openid+"&groupbuyid="+params.groupbuyid+"&v="+Math.random();
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
	
	
	
	
})