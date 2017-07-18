vge.register('vge', function(g) {
	var z = this;
	// 测试环境
	z.jport = 80;
	z.jhost = 'http://sc.miyzh.com';
	z.japi = z.jhost + ':' + z.jport + '/MIYZHInterface';
	z.imgsrv = z.jhost + ':' + z.jport + '/MIYZHInterface/images';
	z.alimgsrv = 'http://myzh-test.oss-cn-beijing.aliyuncs.com';
	z.pcimgsrv = '暂空';
	z.webhost = 'http://sc.miyzh.com';
	z.webcontext = z.webhost+"/MIYZHInterface";
	z.wechatappid = "wx5c18ba573fac02cc";
	z.wechatauthurl = "https://open.weixin.qq.com/connect/oauth2/authorize?appid="+z.wechatappid+"&redirect_uri=[redirectUrl]&response_type=code&scope=snsapi_base&state=123#wechat_redirect";
});
