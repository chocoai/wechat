/**
 * 请求参数
 */
function getRequestParam() {
	var requestParam = {
		"requesttime" : Date.now(),
		"commandtype" : "",
		"applicationtype" : "1",
		"commandinfo" : {}
	};
	return requestParam;
}

/**
 * 获取json长度
 * 
 * @param jsonData
 * @returns {Number}
 */
function getJsonLength(jsonData) {

	var jsonLength = 0;

	for ( var item in jsonData) {

		jsonLength++;

	}

	return jsonLength;

}

/**
 * 根据微信code获取openId
 * 
 * @param wechatCode
 *            微信code
 */
function getOpenIdByCode(wechatCode) {

	var requestParam = getRequestParam();
	requestParam.commandtype = "wctSnsapiBase";
	requestParam.commandinfo.code = wechatCode;
	var url = getRootPath();
	getRootPath += "/wctUserInfo/index.do";
	// post请求
	vge.ajxpost(javai, JSON.stringify(requestParam), 10000, function(data) {
		try {
			var jo = JSON.parse(data);
			if (jo.reply !== undefined) {
				return jo;
			}
		} catch (e) {
			alert(e);
		}
	}, function(e) {
		alert(e);
	});

	return null;

}

function sendRedirectByGetCode(paramUrl){
	var url = vge.wechatauthurl.replace("[redirectUrl]",encodeURI(paramUrl));
	window.location=url;
}
