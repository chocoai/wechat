vge.register('vge', function(g) {
	var z = this;
	// 本地环境
	z.jport = 80;
	z.jhost = 'http://127.0.0.1';
	z.japi = z.jhost + ':' + z.jport + '/MIYZHInterface';
	z.imgsrv = z.jhost + ':' + z.jport + '/MIYZHInterface/images';
	z.alimgsrv = 'http://myzh-test.oss-cn-beijing.aliyuncs.com';
	z.pcimgsrv = '暂空';
	z.webhost = 'http://127.0.0.1';
	z.webcontext = z.webhost+"/MIYZHInterface";
	z.wechatappid = "wx5c18ba573fac02cc";
	z.wechatauthurl = "https://open.weixin.qq.com/connect/oauth2/authorize?appid="+z.wechatappid+"&redirect_uri=[redirectUrl]&response_type=code&scope=snsapi_base&state=123#wechat_redirect";
	
	
	z.registerurl="http://192.168.1.205:19180/registermobile/index.html";
	z.registerinterfacegace=z.webcontext+"/register/ydbRegister.do";
});
