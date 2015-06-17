$(function() {
	// 注册按钮点击跳转到url
	$('button.account').unbind('click').bind('click', function() {
		window.location.href = 'sign-up.html';
		return;
	});

	// 登陆绑定
	$('.submit input[type=submit]').unbind('click').bind('click', function() {
		var $user = $('.user input');
		var user = $user.val();
		if (user == '') {
			$user.focus();
			alert('用户名不能为空！');
			return;
		}
		var $password = $('.password input');
		var password = $password.val();
		if (password == '') {
			$password.focus();
			alert('密码不能为空！');
			return;
		}
		$.ajax({
			url : 'api/v1/user/login',
			type : 'POST',
			data : {
				account : user,
				password : password
			},
			dataType : 'json',
			beforeSend : function(XHR) {
			},
			success : function(data) {
				var statusCode = data.status_code;
				if (statusCode === 200) {
					window.location.href = 'index.html';
				} else if (statusCode === 400) {
					alert(data.error_message);
				} else if (statusCode === 401) {
					alert(data.error_message);
				}
			},
			error : function() {
				alert('登陆异常！');
			}
		});
	});
})