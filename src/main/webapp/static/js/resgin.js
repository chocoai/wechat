
/*-------注册验证-----------*/
$().ready(function() {
	$(".register_form").validate({
		rules: {
			mobile:{
				required: true,
				rangelength:[11,11],
				digits: "只能输入整数"
			},
			password: {
				required: true,
				rangelength:[8,20]
			},
			confirm_password: {
				required: true,
				equalTo: "#password",   
				rangelength:[8,20]
			},
			code:{
				required: true,
				maxlength:4,
				minlength:4
			},
			user:{
				required: true,
				digits: "只能输入整数"
			},
			ydbpwd:{
				required: true,
				minlength:1,
			}
		},
		messages: {
			mobile:{
				required: "请输入手机号",
				rangelength: jQuery.format("请输入正确的手机号"),
			},
			password: {
				required: "请输入密码",
				rangelength: jQuery.format("密码在8~20个字符之间"),
			},
			code:{
				required: "请输入验证码",
				rangelength: jQuery.format("数字4个字符之间"),
			},
			confirm_password: {
				required: "请输入确认密码",
				rangelength: jQuery.format("密码在8~20个字符之间"),
				equalTo: "两次输入密码不一致"
			},
			user:{
				required: "请输入诊所ID",
				rangelength: jQuery.format("请输入正确的诊所ID"),
			},
			ydbpwd:{
				required: "请输入诊所密码",
			}
		}
	});
});
