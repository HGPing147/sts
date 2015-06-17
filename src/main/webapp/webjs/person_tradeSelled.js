$(function() {
	var accessToken = Base.getCookieValueByName('accessToken');
	// 显示购置商品
	var showTradesSelled = function(pageNow, accessToken) {
		$.ajax({
			url : 'api/v1/trades/sell/' + accessToken,
			type : 'GET',
			data : {
				pageNow : pageNow == '' ? 1 : pageNow
			},
			dataType : 'json',
			beforeSend : function(XHR) {
			},
			success : function(data) {
				var selledTrades = data.data;
				var pages = data.page.pages;
				var rows = data.page.rows;
				if (selledTrades.length > 0) {
					var selledTradesArr = [];
					$.each(selledTrades, function(i, d) {
						var tradeId = d.id;
						var resName = d.title;
						var cost = d.cost;
						var buyer = d.buyerName;
						var buyerContact = d.buyerContact;
						var tradeTime = d.createtime.split('.')[0];
						var tradeStatus = d.status === 2 ? '撤回出售' : d.status === 3 ? '交易中...' : d.status === 4 ? '交易成功' : d.status === 4 ? '交易失败' : '-';
						var operateBtn = d.status === 3 ? '<span class="sell">卖出</span> <span class="back">撤回</span>' : '-';
						var trade = '	<tr trade-id="' + tradeId + '"><td>' + resName + '</td><td>￥' + cost + '</td><td>' + buyer + '</td><td>' + buyerContact + '</td><td>'
								+ tradeTime + '</td><td>' + tradeStatus + '</td><td>' + operateBtn + '</td></tr>';
						selledTradesArr.push(trade);
					});
					$('.trade-selled tbody').html(selledTradesArr.join(' '));
					// 分页
					pagination(pageNow, pages, rows, function(pageCurrent) {
						showTradesSelled(pageCurrent, accessToken);
					});
					sell(accessToken);// 卖出
					back(accessToken);// 撤回
				} else {
					$('.trade-selled tbody').html('没有交易数据！');
					$('.pagination.sell').hide();
				}
			},
			error : function() {
				$('.trade-selled tbody').html('');
				$('.pagination.sell').hide();
				alert('获取卖出交易异常！');
			}
		});
	};

	// 分页
	function pagination(pageNow, pages, rows, loadData) {
		$('.pagination.sell').show();
		$('.pagination.sell ul').html('');
		$('.pagination.sell ul').append('<li class="prev am-disabled" databind="prev"><a href="javascript:;">&#8592;</a></li>');
		$('.pagination.sell ul').append('<li class="next am-disabled" databind="next"><a href="javascript:;">&#8594;</a></li>');
		if (pageNow > 1) {
			$('.pagination.sell li:first').removeClass('am-disabled');
		}
		if (pageNow < pages) {
			$('.pagination.sell li:last').removeClass('am-disabled');
		}
		for (var i = 1; i <= pages; i++) {
			if (i == pageNow) {
				$('.pagination.sell li:last').before('<li class="curent" databind="' + i + '"><a href="javascript:;">' + i + '</a></li>');
			} else {
				$('.pagination.sell li:last').before('<li  databind="' + i + '"><a href="javascript:;">' + i + '</a></li>');
			}
		}
		$('.pagination.sell li').unbind('click').bind('click', function() {
			var databind = $(this).attr('databind');
			var pageNow = 1;
			// 上一页
			if (databind === 'prev') {
				if ($(this).hasClass('am-disabled')) {
					return;
				}
				$('.pagination.sell li').each(function() {
					if (!$(this).hasClass('am-disabled') && $(this).attr('databind') !== 'prev' && $(this).hasClass('curent')) {
						pageNow = parseInt($(this).attr('databind')) - 1;
					}
				});
			} else if (databind === 'next') {// 下一页
				if ($(this).hasClass('am-disabled')) {
					return;
				}
				$('.pagination.sell li').each(function() {
					if (!$(this).hasClass('am-disabled') && $(this).attr('databind') !== 'next' && $(this).hasClass('curent')) {
						pageNow = parseInt($(this).attr('databind')) + 1;
					}
				});
			} else {
				pageNow = databind;
			}
			loadData(pageNow);
		});
	}

	/**
	 * 卖出
	 */
	function sell(accessToken) {
		$('.sell').unbind('click').bind('click', function() {
			if (confirm("确定要卖出此商品吗？")) {
				var tradeId = $(this).parents('tr').attr('trade-id');
				$.ajax({
					url : 'api/v1/trades/' + tradeId + '/traded',
					type : 'POST',
					data : {
						accessToken : accessToken
					},
					dataType : 'json',
					beforeSend : function(XHR) {
					},
					success : function(data) {
						var statusCode = data.status_code;
						if (statusCode === 200) {
							showTradesSelled(1, accessToken);
							alert('商品卖出成功!');
						} else {
							alert(data.error_message);
						}
					},
					error : function() {
						alert('商品卖出异常!');
					}
				});
			}
		});
	}
	/**
	 * 撤回
	 */
	function back(accessToken) {
		$('.back').unbind('click').bind('click', function() {
			if (confirm("确定要撤回此商品吗？")) {
				var tradeId = $(this).parents('tr').attr('trade-id');
				$.ajax({
					url : 'api/v1/trades/' + tradeId + '/tradingBroken',
					type : 'POST',
					data : {
						accessToken : accessToken
					},
					dataType : 'json',
					beforeSend : function(XHR) {
					},
					success : function(data) {
						var statusCode = data.status_code;
						if (statusCode === 200) {
							showTradesSelled(1, accessToken);
							alert('商品撤回成功!');
						} else {
							alert(data.error_message);
						}
					},
					error : function() {
						alert('商品撤回异常!');
					}
				});
			}
		});
	}
	showTradesSelled(1, accessToken);
})