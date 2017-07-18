<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>刷新缓存</title>
<style type="text/css">
body {
	font-family: "Open Sans", "Helvetica Neue", Helvetica, Arial, sans-serif;
	color: #555;
	font-size: 13px;
}

#content {
	background: #fff;
	margin-left: 250px;
	overflow: visible;
	padding-bottom: 30px;
	min-height: 100%
}

.active_board_table {
	width: 100%;
	margin: 5px auto;
}

.active_board_table .ab_left {
	width: 17%;
	text-align: right;
	margin: 6px;
	padding: 6px;
}

.active_board_table .ab_left label,.active_board_table .ac_left label {
	margin: 1px;
	padding: 5px;
}

.active_board_table .ab_main {
	width: 83%;
	margin: 6px;
	padding: 6px;
}

.active_board_table .content p {
	margin: 5px;
	padding: 5px;
}

.active_board_table .content .form-control,.active_board_table .ac_content .form-control,.active_board_table .content textarea
	{
	width: 62%;
	float: left;
}

.btn-primary {
	color: #fff;
	background-color: #428bca;
	border-color: #357ebd
}

.btn-primary:hover,.btn-primary:focus,.btn-primary:active,.btn-primary.active,.open .dropdown-toggle.btn-primary
	{
	color: #fff;
	background-color: #3276b1;
	border-color: #285e8e
}

.btn-primary:active,.btn-primary.active,.open .dropdown-toggle.btn-primary
	{
	background-image: none
}

.btn-primary.disabled,.btn-primary[disabled],fieldset[disabled] .btn-primary,.btn-primary.disabled:hover,.btn-primary[disabled]:hover,fieldset[disabled] .btn-primary:hover,.btn-primary.disabled:focus,.btn-primary[disabled]:focus,fieldset[disabled] .btn-primary:focus,.btn-primary.disabled:active,.btn-primary[disabled]:active,fieldset[disabled] .btn-primary:active,.btn-primary.disabled.active,.btn-primary[disabled].active,fieldset[disabled] .btn-primary.active
	{
	background-color: #428bca;
	border-color: #357ebd
}

.validate_tips {
	display: block;
	width: 30%;
	height: 30px;
	float: left;
	padding: 5px;
}
</style>
<script type="text/javascript">
function refreshAllCache(){
	var pathPrx="<%=basePath%>";
	location.href = pathPrx + "/web/rememoAllCache.do";
}

function refreshProperty(){
	var pathPrx="<%=basePath%>";
	location.href = pathPrx + "/web/refreshProperty.do";
}
</script>
</head>

<body>
	<form class="listForm" method="post"
		action="<%=basePath%>/web/refreshCache.do">
		<table class="active_board_table">
			<tr>
				<td class="ab_left"><label class="title">
						缓存key(多个key以,分开)： </label></td>
				<td class="ab_main" colspan="3">
					<div class="content">
						<textarea id="explan" maxlength="4000" name="keysStr"
							class="form-control col-md-2 closeBtn"
							style="width: 400px; height: 100px;"></textarea>
						<label id="logoPath_span_id" class="validate_tips"
							style="color: #e25856;"> ${msg} </label>
					</div>
				</td>

			</tr>
			<tr>
				<td class="ab_left">&nbsp;</td>
				<td class="ab_main" colspan="3">
					<div class="content">
						<input type="submit" value="清除" id="btn_sumbit_persion"
							class="btn-primary"> &nbsp;&nbsp;  <!-- <input type="button"
							value="一键清除所有缓存" id="btn_sumbit_persion" class="btn-primary"
							onclick="refreshAllCache();">   -->
					</div>
				</td>
			</tr>
		</table>
	</form>
	<hr>
	<form class="listForm2" method="post"
		action="<%=basePath%>/web/refreshProperty.do">
		<table class="active_board_table">
			<tr>
				<td class="ab_left"><label class="title">配置文件键名： </label></td>
				<td class="ab_main" colspan="3">
					<div class="content">
						<input  type="text" name="keyname" id = "keyname"
							class="form-control col-md-2 closeBtn"
							style="width: 400px; height: 30px;"/>
						<label id="logoPath_span_id" class="validate_tips"
							style="color: #e25856;"> ${msg2} </label>
					</div>
				</td>
			</tr>
			<tr>
				<td class="ab_left"><label class="title"> 配置文件键值： </label></td>
				<td class="ab_main" colspan="3">
					<div class="content">
						<input  type="text"  name="keyvalue" id="keyvalue"
							class="form-control col-md-2 closeBtn"
							style="width: 400px; height: 30px;"/>

						<label id="logoPath_span_id" class="validate_tips"
							style="color: #e25856;"> ${msg3} </label>
					</div>
				</td>
			</tr>
			<tr>
				<td class="ab_left">&nbsp;</td>
				<td class="ab_main" colspan="3">
					<div class="content">
						<input type="submit" value="清除" id="btn_sumbit_persion"
							class="btn-primary"> &nbsp;&nbsp; <input type="submit"
							value="修改" id="btn_sumbit_persion" class="btn-primary"
							>
					</div>
				</td>
			</tr>
		</table>
	</form>
	<hr>
	<form action="<%=basePath%>/web/addCache.do" method="post" class="form3">
		<table class="active_board_table">
			<tr>
				<td class="ab_left"><label class="title">缓存key</label></td>
				<td>
					<div class="content">
						<input  type="text"  name="keyname" id="keyname"
							class="form-control col-md-2 closeBtn"
							style="width: 400px; height: 30px;"/>
					</div>
				</td>
			</tr>
			<tr>
				<td class="ab_left"><label class="title">
						缓存value[多个key以,分开]： </label></td>
				<td class="ab_main" colspan="3">
					<div class="content">
						<textarea id="explan" maxlength="4000" name="keyvalue"
							class="form-control col-md-2 closeBtn"
							style="width: 400px; height: 100px;"></textarea>
						<label id="logoPath_span_id" class="validate_tips"
							style="color: #e25856;"> ${msg4} </label>
					</div>
				</td>

			</tr>
			<tr>
				<td class="ab_left">&nbsp;</td>
				<td class="ab_main" colspan="3">
					<div class="content">
						<input type="submit" value="增加" id="btn_sumbit_persion"
							class="btn-primary">  
					</div>
				</td>
			</tr>
		</table>
		
	</form>
	<hr>
	<h4>清除黑名单计数</h4>
	<form action="<%=basePath%>/web/removeBlacklistCounts.do" method="post" class="form3">
		<table class="active_board_table">
			<tr>
				<td class="ab_left"><label class="title">
						缓存key[多个key以,分开]： </label></td>
				<td class="ab_main" colspan="3">
					<div class="content">
						<textarea id="explan" maxlength="4000" name="keyvalue"
							class="form-control col-md-2 closeBtn"
							style="width: 400px; height: 100px;" placeholder="格式为:用户账号或手机号_日期" ></textarea>
						<label id="logoPath_span_id" class="validate_tips"
							style="color: #e25856;"> ${msg6} </label>
					</div>
				</td>

			</tr>
			<tr>
				<td class="ab_left">&nbsp;</td>
				<td class="ab_main" colspan="3">
					<div class="content">
						<input type="submit" value="移除" id="btn_sumbit_persion"
							class="btn-primary">  
					</div>
				</td>
			</tr>
		</table>
		
	</form>
	<hr>
	<h4>仅适用缓存值为list时,清除其中一个值</h4>
	<form action="<%=basePath%>/web/removeCacheContain.do" method="post" class="form3">
		<table class="active_board_table">
			<tr>
				<td class="ab_left"><label class="title">缓存key</label></td>
				<td>
					<div class="content">
						<input  type="text"  name="keyname" id="keyname"
							class="form-control col-md-2 closeBtn"
							style="width: 400px; height: 30px;"/>
					</div>
				</td>
			</tr>
			<tr>
				<td class="ab_left"><label class="title">
						缓存value[多个key以,分开]： </label></td>
				<td class="ab_main" colspan="3">
					<div class="content">
						<textarea id="explan" maxlength="4000" name="keyvalue"
							class="form-control col-md-2 closeBtn"
							style="width: 400px; height: 100px;"></textarea>
						<label id="logoPath_span_id" class="validate_tips"
							style="color: #e25856;"> ${msg5} </label>
					</div>
				</td>

			</tr>
			<tr>
				<td class="ab_left">&nbsp;</td>
				<td class="ab_main" colspan="3">
					<div class="content">
						<input type="submit" value="移除" id="btn_sumbit_persion"
							class="btn-primary">  
					</div>
				</td>
			</tr>
		</table>
		
	</form>
	
</body>
</html>
