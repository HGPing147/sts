$(function() {
	var accessToken = Base.getCookieValueByName('accessToken');
	var urlCataId = Base.getParam('goto').split('_')[1];// 获取分类Id
	var showReses = function(pageNow, cataId, accessToken) {
		$.ajax({
			url : 'api/v1/reses/cataroy/' + cataId,
			type : 'GET',
			data : {
				accessToken : accessToken,
				pageNow : pageNow == '' ? 1 : pageNow
			},
			dataType : 'json',
			beforeSend : function(XHR) {
			},
			success : function(data) {
				var reses = data.data;
				var pages = data.page.pages;
				var rows = data.page.rows;
				if (reses.length > 0) {
					var resesArr = [];
					$.each(reses, function(i, d) {
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
						var res = '<div class="grid_3 product" data-resId="' + resId + '" data-userId="' + userId + '">' + '<img class="sale" src="' + resStatus
								+ '" alt="Sale" />' + '<div class="prev">' + '<a href="javascript:;"><img src="' + resImageUrl + '" alt="" /></a>' + '</div>'
								+ '<h3 class="title">' + resTitle + '</h3>' + '<div class="cart">' + '<div class="price">' + '<div class="vert">' + '<div class="price_new">￥'
								+ resCost + '</div>' + '</div>' + '</div>' + '<a href="javascript:;" class="buy" title="购买"></a> '
								+ '<a href="javascript:;" class="link" title="联系" data-am-modal="{target: \'#doc-modal-2\', closeViaDimmer: 0, width: 400, height: 225}"></a> '
								+ '<a href="javascript:;" class="detail" title=" 详细信息" data-am-modal="{target: \'#doc-modal-1\', closeViaDimmer: 0, width: 400, height: 225}"></a>'
								+ '</div>' + '</div>';
						resesArr.push(res);
					});
					resesArr.push('<div class="clear"></div>');
					$('.grid_product').html(resesArr.join(' '));
					// 分页组件
					Base.pagination(pageNow, pages, rows, function(pageCurrent) {
						showReses(pageCurrent, urlCataId, accessToken);// 显示分类下的分页商品
					});
					showResDetail(accessToken);
					showUserContact(accessToken);
					buy(accessToken);
				} else {
					$('.grid_product').html('没有商品数据！');
					$('.pagination').hide();
				}
			},
			error : function() {
				$('.grid_product').html('');
				$('.pagination').hide();
				alert('获取商品异常！');
			}
		});
	};

	/**
	 * 商品详情
	 */
	function showResDetail(accessToken) {
		$('.detail').unbind('click').bind('click', function() {
			var resId = $(this).parent('div').parent('div').attr('data-resId');
			console.log(resId);
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
			var resId = $(this).parent('div').parent('div').attr('data-resId');
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
				var resId = $(this).parent('div').parent('div').attr('data-resId');
				var userId = $(this).parent('div').parent('div').attr('data-userId');
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
	showReses(1, urlCataId, accessToken);// 显示分类下的分页商品
});