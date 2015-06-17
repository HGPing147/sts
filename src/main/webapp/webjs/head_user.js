$(function() {
	var accessToken = Base.getCookieValueByName('accessToken');
	if (accessToken == '') {
		$('.logined').hide();
		$('.unlogin').show();
		return;
	}
	$('.logined').show();
	$('.unlogin').hide();
	var userNick = Base.decodeValue(Base.getCookieValueByName('nickName'));// 用户昵称
	$('.logined a:eq(0)').text(userNick);
	$('.logined a:eq(0)').attr('href', 'person-user.html');
	// 退出绑定事件
	$('.logined a:eq(1)').unbind('click').bind('click', function() {
		if (confirm("确定要注销吗？")) {
			$.ajax({
				url : 'api/v1/user/logout',
				type : 'GET',
				data : {
					accessToken : accessToken
				},
				dataType : 'json',
				beforeSend : function(XHR) {
				},
				success : function(data) {
					var statusCode = data.status_code;
					if (statusCode === 200) {
						window.location.href = 'sign-in.html';
					}
				},
				error : function() {
					alert('注销用户异常！');
				}
			});
		}
	})
})