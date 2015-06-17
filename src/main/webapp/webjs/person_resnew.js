$(function() {
	var accessToken = Base.getCookieValueByName('accessToken');
	// 加载分类
	var showCataList = function(accessToken) {
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
				var catagoriesArr = [];
				if (catagories.length > 0) {
					$.each(catagories, function(i, d) {
						var cataId = d.id;
						var cataName = d.name;
						catagoriesArr.push('<option value="' + cataId + '">' + cataName + '</option>');
					});
				}
				$('.catagoryList select').html(catagoriesArr.join(' '));
			},
			error : function() {
				alert('加载商品分类异常！');
				$('.catagoryList select').html('');
			}
		});
	};

	// 添加商品
	var resNew = function(accessToken) {
		// 登陆绑定
		$('.putsResForm .submit input[type=submit]').unbind('click').bind('click', function() {
			var $title = $('.putsResForm .title input');
			var title = $title.val();
			if (title == '') {
				$title.focus();
				alert('物品名称不能为空!');
				return;
			}
			var $price = $('.putsResForm .price input');
			var price = $price.val();
			if (price == '') {
				$price.focus();
				alert('物品价格不能为空!');
				return;
			}
			var cataId = $('.putsResForm select option:selected').val();
			var $desc = $('.putsResForm .desc textarea');
			var desc = $desc.val();
			var resImageUrl = $('.pro_pic_pre img').attr('src');
			$.ajax({
				url : 'api/v1/reses/new',
				type : 'POST',
				data : {
					title : title,
					description : desc,
					cost : price,
					cataId : cataId,
					resImageUrl : resImageUrl,
					accessToken : accessToken
				},
				dataType : 'json',
				beforeSend : function(XHR) {
				},
				success : function(data) {
					var statusCode = data.status_code;
					if (statusCode === 200) {
						$('.putsResForm .title input').val('');
						$('.putsResForm .price input').val('');
						$('.putsResForm select option:eq(0)').attr('selected', true);
						$('.putsResForm .desc textarea').val('');
						$('.pro_pic_pre img').attr('src', '');
						$('#textfield').val('');
						alert('添加商品成功!');
					} else if (statusCode === 400) {
						alert(data.error_message);
					} else if (statusCode === 401) {
						alert(data.error_message);
					} else if (statusCode === 500) {
						alert(data.error_message);
					}
				},
				error : function() {
					alert('添加商品异常！');
				}
			});
		});
	};
	showCataList(accessToken);
	resNew(accessToken);
})