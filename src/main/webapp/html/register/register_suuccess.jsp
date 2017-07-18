<%@ page language="java" contentType="text/html;charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<%
	String cpath = request.getContextPath();
%>
<html>
<head>
	<meta charset="utf-8">
	<meta http-equiv="Cache-Control" content="no-cache, no-store, must-revalidate" />
	<meta http-equiv="Pragma" content="no-cache" />
	<meta http-equiv="Expires" content="0" />
	<title>明医商城</title>
	<meta content="width=device-width,initial-scale=1.0,maximum-scale=1.0,user-scalable=no" name="viewport">
	<meta content="yes" name="apple-mobile-web-app-capable">
	<meta content="black" name="apple-mobile-web-app-status-bar-style">
	<meta content="telephone=no" name="format-detection">
	<meta content="email=no" name="format-detection">
	<link rel="stylesheet" type="text/css" href="../../static/css/reset.css?v=1">
	<link rel="stylesheet" type="text/css" href="../../static/css/alert.css?v=12">
</head>
<body class="bgcolor">

	<section class="attest_content">
		<article class="infor_success">
			<p class="fail_text"><img src="../../static/img/rz.png"></p>
			<h4>您已注册成功</h4>
			<p>祝您购物愉快</p>
			<a id="go_buy"><button class="blue_btn" onclick="">去买东西</button></a>
		</article>
		</section>
		<!--弹框提示-->
 	<div class="n_tankzz"></div> 
 	<div class="n_tank"> 
 		<div class="n_tankhead"><span>x</span></div> 
 		<div class="n_tankCont"> 
 			<span id="alert_msg"></span> 
 		</div>		 
 		<div class="n_tankbut"> 
 			<button class="n_tankbut_but">确认</button> 
 		</div> 
 	</div>	 
	<!--end 弹框提示-->
</body>
	<script src="../../static/js/jquery-1.8.2.min.js"></script>
	<script src="../../static/js/10core.js?v=1"></script>
	<script src="../../static/js/20ajx.js?v=1"></script>
	<script src="../../static/js/60webcfg.js?v=1"></script>
	<script src="../../static/js/common.js?v=1"></script>
	<script src="../../static/js/alert.js?v=1"></script>
	<script src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
	<script src="../../static/js/jsdk.js?v=1"></script>
	<script type="text/javascript">
	$(function () {
		wx.ready(function() { //隐藏微信分享
            wx.hideOptionMenu();
        });
	    var params = vge.urlparse(location.href);
	    if (params.openid != undefined) {
	    	$("#go_buy").attr("href",vge.webcontext+"/html/groupbuy/index.html?openid=" + params.openid+"&v="+ Math.random());

	    } else {
	    	showAlert("获取openId失败");
	        return false;
	    }
	    
	})
	</script>
</html>