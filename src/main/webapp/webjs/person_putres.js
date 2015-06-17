$(function() {
	$('#uploadImg').unbind('click').bind('click', function() {
		$.ajaxFileUpload({
			url : 'api/v1/reses/pic/new',// 用于文件上传的服务器端请求地址
			secureuri : false,
			type : "POST",
			fileElementId : 'resFile',
			dataType : 'json',// 返回值类型 一般设置为json
			success : function(data, status) {// 服务器成功响应处理函数
				var resImgUrl = data.data;
				$('.pro_pic_pre img').attr('src', resImgUrl);
			},
			error : function(data, status, e) {// 服务器响应失败处理函数
				alert(e);
			}
		});
	});
})