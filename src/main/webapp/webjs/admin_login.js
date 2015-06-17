$(function() {
	$('.login').unbind('click').bind('click', function() {
		var $account = $('.admin-login-form input[name=userName]');
		var account = $account.val();
		if (account == '') {
			alert('用户名不能为空值！');
			$account.focus();
			return;
		}
		var $pwd = $('.admin-login-form input[name=pwd]');
		var pwd = $pwd.val();
		if (pwd == '') {
			alert('密码不能为空！');
			$pwd.focus();
			return;
		}
		$.ajax({
			url : 'api/v1/admin/login',
			type : 'POST',
			data : {
				userName : account,
				password : pwd
			},
			dataType : 'json',
			success : function(data) {
				if (data.status_code === 200) {
					window.location.href = 'admin-user.html';// 登陆成功，跳转到用户管理页面
				} else if (data.status_code === 404) {
					alert('用户名或者密码错误！');
				}
			},
			error : function() {
				alert('请求发生错误！');
			}
		});
	});
})