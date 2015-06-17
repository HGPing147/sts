$(function() {
	var accessToken = Base.getCookieValueByName('accessToken');
	var urlCataId = Base.getParam('goto').split('_')[1];// 获取分类Id
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
			var catagoriesArr = [ '<li><a href="index.html">首页</a></li>' ];
			if (catagories.length > 0) {
				$.each(catagories, function(i, d) {
					var _cataId = parseInt(urlCataId);
					var cataId = d.id;
					var cataName = d.name;
					if (_cataId === cataId) {
						catagoriesArr.push('<li class="curent" databind="' + cataId + '"><a href="cata-reses.html?goto=res_' + cataId + '">' + cataName + '</a></li>');
						$('.page_title').text(cataName);
					} else {
						catagoriesArr.push('<li databind="' + cataId + '"><a href="cata-reses.html?goto=res_' + cataId + '">' + cataName + '</a></li>');
					}
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