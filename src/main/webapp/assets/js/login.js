$().ready(function() {
	/**登录验证**/
	$("#login-form").validate({
		errorPlacement: function(error, element){},
		rules: {
			username: "required",
			password: {
				required: true,
				minlength: 5
			},
		},
		submitHandler:function(form){
            $.ajax({
        		dataType : "json",
        		url :  basePath + '/login',  
        		type : "post", 
        		data : $("#login-form").serialize(), 
        		success : function(data) {
        			if(data.success){
        				window.location.href = basePath + '/index';
        			} else {
        				$.alert(data.msg, 'Warning!');
        			}
        		}
        	});
            return false; //阻止form提交
        }
	});
});
function submitForm(){
    $('#login-form').submit();
}
function clearForm(){
    $('#login-form')[0].reset();
}
//回车登录
function enterlogin(){
    if (event.keyCode == 13){
        event.returnValue=false;
        event.cancel = true;
        $('#login-form').submit();
    }
}