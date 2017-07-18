vge.register('vge', function(g) {
	var z = this;
	// 线上
	z.jport = 80;
	z.jhost = 'http://shop.miyzh.com';
	z.japi = z.jhost + ':' + z.jport + '/MIYZHInterface';
	z.imgsrv = z.jhost + ':' + z.jport + '/MIYZHInterface/images';
	z.alimgsrv = 'http://myzh-ucenter.oss-cn-beijing.aliyuncs.com';
	z.pcimgsrv = '暂空';
	z.webhost = 'http://shop.miyzh.com';
	z.webcontext = z.webhost+"/MIYZHInterface";
	z.wechatappid = "wxf4a7649a7bf71c11";
	z.wechatauthurl = "https://open.weixin.qq.com/connect/oauth2/authorize?appid="+z.wechatappid+"&redirect_uri=[redirectUrl]&response_type=code&scope=snsapi_base&state=123#wechat_redirect";
});
