$(function() {
	// 登陆按钮点击跳转到url
	$('button.account').unbind('click').bind('click', function() {
		window.location.href = 'sign-in.html';
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
		var $repassword = $('.repassword input');
		var repassword = $repassword.val();
		if (repassword !== password) {
			$repassword.focus();
			alert('两次密码输入不一致！');
			return;
		}
		var $email = $('.email input');
		var email = $email.val();
		var regm = /^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/;
		if (email == '' || !email.match(regm)) {
			alert('邮箱不匹配！');
			$email.focus();
			return;
		}
		var $name = $('.name input');
		var name = $name.val();
		if (name == '') {
			$name.focus();
			alert('真实姓名不能为空！');
		}
		var sex = $('.sex input[name=sex]:checked').val();
		var $contact = $('.contact input');
		var contact = $contact.val();
		if (contact == '') {
			$contact.focus();
			alert('联系方式不能为空！');
		}
		$.ajax({
			url : 'api/v1/user/register',
			type : 'POST',
			data : {
				nickName : user,
				userMail : email,
				password : password,
				name : name,
				sex : sex,
				contact : contact
			},
			dataType : 'json',
			beforeSend : function(XHR) {
			},
			success : function(data) {
				var statusCode = data.status_code;
				if (statusCode === 200) {
					window.location.href = 'sign-in.html';
				} else if (statusCode === 400) {
					alert(data.error_message);
				} else if (statusCode === 500) {
					alert(data.error_message);
				}
			},
			error : function() {
				alert('注册异常！');
			}
		});
	});
})