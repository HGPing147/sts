$(function() {
	var accessToken = Base.getCookieValueByName('accessToken');
	/**
	 * 显示用户信息
	 */
	var showUserInfo = function(accessToken) {
		$.ajax({
			url : 'api/v1/user/' + accessToken,
			type : 'GET',
			dataType : 'json',
			beforeSend : function(XHR) {
			},
			success : function(data) {
				var statusCode = data.status_code;
				if (statusCode === 200) {
					var userInfo = data.data;
					$('.userInfo .user input').val(userInfo.nickName);
					$('.userInfo .email input').val(userInfo.userMail);
					$('.userInfo .name input').val(userInfo.name);
					$('input:radio[value=' + userInfo.sex + ']').attr('checked', true);
					$('.userInfo .contact input').val(userInfo.contact);
					$('.userInfo').attr('user-id', userInfo.id);
				} else if (statusCode === 400) {
					alert(error_message);
				} else if (statusCode === 404) {
					alert(error_message);
				}
			},
			error : function() {
				alert('获取用户信息异常！');
			}
		});
	};

	/**
	 * 用户信息修改
	 */
	var editUser = function() {
		$('.userInfo .submit input').unbind('click').bind('click', function() {
			var userId = $('.userInfo').attr('user-id');
			var nickName = $('.userInfo .user input').val();
			var userMail = $('.userInfo .email input').val();
			var name = $('.userInfo .name input').val();
			var sex = $('.userInfo .sex input[name=sex]:checked').val();
			var contact = $('.userInfo .contact input').val();
			if (nickName == '' || userMail == '' || name == '' || sex == '' || contact == '') {
				alert('存在未填项！');
				return false;
			}
			$.ajax({
				url : 'api/v1/user/' + userId,
				type : 'POST',
				dataType : 'json',
				data : {
					nickName : nickName,
					userMail : userMail,
					name : name,
					sex : sex,
					contact : contact,
					accessToken : accessToken
				},
				beforeSend : function(XHR) {
				},
				success : function(data) {
					var statusCode = data.status_code;
					if (statusCode === 200) {
						$('.logined a:eq(0)').text(nickName);
						showUserInfo(accessToken);
						alert('修改成功！如果修改用户名，重新登陆后生效！');
					} else if (statusCode === 400) {
						alert(error_message);
					} else if (statusCode === 404) {
						alert(error_message);
					}
				},
				error : function() {
					alert('修改用户信息异常！');
				}
			});
		});
	};
	showUserInfo(accessToken);
	editUser();
})