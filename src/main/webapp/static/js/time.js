var countdown=60; 
$("#phone").keydown(function(event) {
	$(".error_text").css({"display":"none"});
});
function timeShow(){
	if (countdown == 0) { 
		$("#sendCode").removeAttr('disabled');    
		$("#sendCode").val("重新获取验证码");
		$("#sendCode").css({"color":"#fff","backgroundColor":"#06c1ae","borderColor":"#06c1ae"});
		countdown = 60; 
		return;
	} else { 
		$("#sendCode").attr("disabled","disabled");
		$("#sendCode").val("重新发送(" + countdown + ")");
		$("#sendCode").css({"color":"#ccc","backgroundColor":"#fff","borderColor":"#dfdfdf"});
		countdown--; 
	} 
	setTimeout(function() { 
		timeShow() }
		,1000) 
}

function validMoible() { 
	var telVal=$("#phone").val();
	var reg=/(1[3-9]\d{9}$)/;
	if(!telVal){
		$(".code_input").append('<span class="error_text" style="display:block">手机号不能为空</span>')
		return false;
	}
	if (!reg.test(telVal)) {
		$(".code_input").append('<span class="error_text" style="display:block">手机号格式不对</span>')
		return false;
	}
	
	return true;
}
