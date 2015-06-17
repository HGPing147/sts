$(function() {
	var accessToken = Base.getCookieValueByName('accessToken');
	// 显示推荐商品
	var showRecommendReses = function(accessToken) {
		$.ajax({
			url : 'api/v1/reses/recommend',
			type : 'GET',
			data : {
				accessToken : accessToken
			},
			dataType : 'json',
			beforeSend : function(XHR) {
			},
			success : function(data) {
				var recommendReses = data.data;
				var resesArr = [];
				if (recommendReses.length > 0) {
					$.each(recommendReses, function(i, d) {
						var resId = d.id;
						var resTitle = d.title;
						var resName = d.name;
						var resImageUrl = d.resImageUrl;
						var resCost = d.cost;
						var resPutstime = d.putstime;
						var resDigest = d.digest;
						var resStatus = d.status === 2 ? 'images/pro_selling.png' : d.status === 3 ? 'images/pro_trading.png' : d.status === 4 ? 'images/pro_selled.png' : '';
						var cataName = d.cataName;
						var userId = d.userId;
						var res = '<li class="" data-resId="' + resId + '" data-userId="' + userId + '">' + '<div class="grid_3 product">' + '<img class="sale" src="' + resStatus
								+ '" alt="Sale" />' + '<div class="prev">' + '<a href="javascript:;"><img src="' + resImageUrl + '" alt="" /></a>' + '</div>'
								+ '<h3 class="title">' + resTitle + '</h3>' + '<div class="cart">' + '<div class="price">' + '<div class="vert">' + '<div class="price_new">￥'
								+ resCost + '</div>' + '</div>' + '</div>' + '<a href="javascript:;" class="buy" title="购买"></a> '
								+ '<a href="javascript:;" class="link" title="联系" data-am-modal="{target: \'#doc-modal-2\', closeViaDimmer: 0, width: 400, height: 225}"></a> '
								+ '<a href="javascript:;" class="detail" title=" 详细信息" data-am-modal="{target: \'#doc-modal-1\', closeViaDimmer: 0, width: 400, height: 225}"></a>'
								+ '</div>' + '</div>' + '</li>';
						resesArr.push(res);
					});
					$('#next_c2').show();
					$('#prev_c2').show();
					$('.recommend .list_product').html(resesArr.join(' '));
					showResDetail(accessToken);
					showUserContact(accessToken);
					buy(accessToken);
				} else {
					$('#next_c2').hide();
					$('#prev_c2').hide();
					$('.recommend .list_product').html('');
				}
			},
			error : function() {
				$('#next_c2').hide();
				$('#prev_c2').hide();
				$('.recommend .list_product').html('');
				alert('加载推荐商品异常！');
			}
		});
	};

	/**
	 * 商品详情
	 */
	function showResDetail() {
		$('.detail').unbind('click').bind('click', function() {
			var resId = $(this).parents('li').attr('data-resId');
			$.ajax({
				url : 'api/v1/reses/' + resId,
				type : 'GET',
				data : {
					accessToken : accessToken
				},
				dataType : 'json',
				beforeSend : function(XHR) {
				},
				success : function(data) {
					if (data.status_code === 200) {
						var res = data.data;
						var resTitle = res.title;
						var resDesc = res.resDesc;
						$('.resDetail .am-modal-hd span').text(resTitle);
						$('.resDetail .am-modal-bd').html(resDesc);
					} else {
						$('.resDetail').hide();
					}
				},
				error : function() {
					$('.resDetail').hide();
					alert('获取详细信息异常！');
				}
			});
		});
	}

	/**
	 * 显示用户联系方式
	 */
	function showUserContact(accessToken) {
		$('.link').unbind('click').bind('click', function() {
			var resId = $(this).parents('li').attr('data-resId');
			$.ajax({
				url : 'api/v1/reses/contact/' + resId,
				type : 'GET',
				data : {
					accessToken : accessToken
				},
				dataType : 'json',
				beforeSend : function(XHR) {
				},
				success : function(data) {
					if (data.status_code === 200) {
						var userInfo = data.data;
						var sellerName = userInfo.sellerName;
						var contact = userInfo.contact;
						$('.userContact .am-modal-bd').html(sellerName + "的联系方式:  " + contact);
					} else {
						$('.userContact .am-modal-bd').html(data.error_message);
					}
				},
				error : function() {
					$('.userContact .am-modal-bd').html('获取用户联系方式异常！');
				}
			});
		});
	}

	/**
	 * 购买
	 */
	function buy(accessToken) {
		$('.buy').unbind('click').bind('click', function() {
			if (confirm('确定购置此商品!')) {
				var resId = $(this).parents('li').attr('data-resId');
				var userId = $(this).parents('li').attr('data-userId');
				$.ajax({
					url : 'api/v1/trades/trading',
					type : 'POST',
					data : {
						seller : userId,
						res : resId,
						accessToken : accessToken
					},
					dataType : 'json',
					beforeSend : function(XHR) {
					},
					success : function(data) {
						if (data.status_code === 200) {
							alert('购置成功！[' + data.ok_message + ']');
							location.reload();
						} else {
							alert(data.error_message);
						}
					},
					error : function() {
						alert('购置异常!');
					}
				});
			}
		});
	}
	showRecommendReses(accessToken);
})