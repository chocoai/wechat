$(function(){
	$(".n_tankhead span").click(function(){
		$('.n_tankzz').hide();
		$('.n_tank').hide();
	})
	$(".n_tankbut_but").click(function(){
		$('.n_tankzz').hide();
		$('.n_tank').hide();
	})
})


function showAlert(msg){
	$("#alert_msg").html("");
	$("#alert_msg").html(msg);
	$('.n_tankzz').show();
	$('.n_tank').show();
	
}

/*发送验证码手机为空提示消失*/
$(function(){
	$("#phone").keyup(function(){
	$(".error_text").hide();
	
	}) 

})