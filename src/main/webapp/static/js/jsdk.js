// 初始化 微信JS-SDK
(function(){
    "use strict";
	var wxjsapi=[
		'onMenuShareTimeline',
		'onMenuShareAppMessage',
		'hideMenuItems',
		'closeWindow',
		'showMenuItems',
		'scanQRCode',
		'chooseImage',
		'previewImage',
		'uploadImage',
		'downloadImage',
		'startRecord',
		'playVoice',
		'stopRecord',
		'translateVoice',
		'onVoiceRecordEnd',
		'uploadVoice'
	];

    function wxjsinit() {
	    // 从服务器获取 signature
    	var requestParamJs = getRequestParam();
    	requestParamJs.commandinfo.url = location.href;
    	requestParamJs.commandinfo.appid = vge.wechatappid;
    	
    	vge.ajxpost(vge.japi+'/jsapiTicketAction/getJsapiTicket.do',JSON.stringify(requestParamJs),10000,function(data){
    		try{
    			var jo = JSON.parse(data);
    			if(jo.result.code == 0 && jo.obj != undefined){
    				if(jo.obj.timestamp!=undefined){
    					wx.config({
    		                debug: false,
    		                appId: vge.wechatappid,
    		                timestamp: jo.obj.timestamp,
    		                nonceStr: jo.obj.noncestr,
    		                signature: jo.obj.signature,
    		                jsApiList: wxjsapi
    		            });
//    					wx.error(function (res) {
//    			            alert(res.errMsg);
//    		            });
    				}
    			}else{
    				alert("js认证加载失败,失败原因->"+jo.result.msg+",失败代码->"+jo.result.code+",业务代码->"+jo.result.businesscode);
    			}
            }catch(e){
	            alert(e);
            }
    		
		}, function(t) {
			alert(t);
		});
    }

    function shareFriend(share_img, share_desc, share_title, share_url, callback) {
		//微信分享的数据
		var shareData = {
			title: share_title,
			desc: share_desc,
			link: share_url,
			imgUrl: share_img,
			success: function(res) {
                if(callback !== undefined) callback();
			}
		};
        
        wx.ready(function() {
		    wx.onMenuShareAppMessage(shareData);
		    wx.onMenuShareTimeline(shareData);
        });
	}

    window.set_wxshare = shareFriend;
    window.ini_wxshare = wxjsinit;
    wxjsinit();
})();
