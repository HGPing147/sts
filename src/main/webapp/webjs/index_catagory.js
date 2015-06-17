$(function() {
	var accessToken = Base.getCookieValueByName('accessToken');
	$.ajax({
		url : 'api/v1/catagories',
		type : 'GET',
		data : {
			accessToken : accessToken
		},
		dataType : 'json',
		beforeSend : function(XHR) {
		},
		success : function(data) {
			var catagories = data.data;
			var catagoriesArr = [ '<li class="curent"><a href="index.html">首页</a></li>' ];
			if (catagories.length > 0) {
				$.each(catagories, function(i, d) {
					var cataId = d.id;
					var cataName = d.name;
					catagoriesArr.push('<li databind="' + cataId + '"><a href="cata-reses.html?goto=res_' + cataId + '">' + cataName + '</a></li>');
				});
			}
			$('.catagories ul').html(catagoriesArr.join(' '));
		},
		error : function() {
			alert('加载商品分类异常！');
			$('.catagories ul').html(catagoriesArr.join(' '));
		}
	});
})