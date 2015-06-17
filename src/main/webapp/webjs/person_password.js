$(function() {
	var accessToken = Base.getCookieValueByName('accessToken');
	/**
	 * 用户信息修改
	 */
	var editPwd = function() {
		$('.userPwd .submit input').unbind('click').bind('click', function() {
			var password = $('.userPwd .password input').val();
			var repassword = $('.userPwd .repassword input').val();
			if (password == '' || repassword == '') {
				alert('存在未填写项！');
				return;
			}
			if (password !== repassword) {
				alert('两次输入不一致！');
				return;
			}
			var userId = $('.userInfo').attr('user-id');
			$.ajax({
				url : 'api/v1/user/' + userId + '/edit-password',
				type : 'POST',
				dataType : 'json',
				data : {
					accessToken : accessToken,
					password : password
				},
				beforeSend : function(XHR) {
				},
				success : function(data) {
					var statusCode = data.status_code;
					if (statusCode === 200) {
						alert('修改成功！重新登陆后生效！');
					} else if (statusCode === 400) {
						alert(error_message);
					} else if (statusCode === 404) {
						alert(error_message);
					}
				},
				error : function() {
					alert('修改密码异常！');
				}
			});
		});
	};
	editPwd();
})