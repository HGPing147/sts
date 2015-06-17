$(function() {
	var accessToken = Base.getCookieValueByName('accessToken');
	// 显示购置商品
	var showTradesBought = function(pageNow, accessToken) {
		$.ajax({
			url : 'api/v1/trades/buy/' + accessToken,
			type : 'GET',
			data : {
				pageNow : pageNow == '' ? 1 : pageNow
			},
			dataType : 'json',
			beforeSend : function(XHR) {
			},
			success : function(data) {
				var boughtTrades = data.data;
				var pages = data.page.pages;
				var rows = data.page.rows;
				if (boughtTrades.length > 0) {
					var boughtTradesArr = [];
					$.each(boughtTrades, function(i, d) {
						var resName = d.title;
						var cost = d.cost;
						var seller = d.sellerName;
						var sellerContact = d.serllerContact;
						var tradeTime = d.createtime.split('.')[0];
						var tradeStatus = d.status === 2 ? '卖家撤回' : d.status === 3 ? '交易中...' : d.status === 4 ? '交易成功' : d.status === 4 ? '交易失败' : '-';
						var trade = '	<tr><td>' + resName + '</td><td>￥' + cost + '</td><td>' + seller + '</td><td>' + sellerContact + '</td><td>' + tradeTime + '</td><td>'
								+ tradeStatus + '</td></tr>';
						boughtTradesArr.push(trade);
					});
					$('.trade-bought tbody').html(boughtTradesArr.join(' '));
					// 分页组件
					pagination(pageNow, pages, rows, function(pageCurrent) {
						showTradesBought(pageCurrent, accessToken);// 显示用户下的分页商品
					});
				} else {
					$('.trade-bought tbody').html('没有交易数据！');
					$('.pagination.buy').hide();
				}
			},
			error : function() {
				$('.trade-bought tbody').html('');
				$('.pagination.buy').hide();
				alert('获取购置交易异常！');
			}
		});
	};

	// 分页
	function pagination(pageNow, pages, rows, loadData) {
		$('.pagination.buy').show();
		$('.pagination.buy ul').html('');
		$('.pagination.buy ul').append('<li class="prev am-disabled" databind="prev"><a href="javascript:;">&#8592;</a></li>');
		$('.pagination.buy ul').append('<li class="next am-disabled" databind="next"><a href="javascript:;">&#8594;</a></li>');
		if (pageNow > 1) {
			$('.pagination.buy li:first').removeClass('am-disabled');
		}
		if (pageNow < pages) {
			$('.pagination.buy li:last').removeClass('am-disabled');
		}
		for (var i = 1; i <= pages; i++) {
			if (i == pageNow) {
				$('.pagination.buy li:last').before('<li class="curent" databind="' + i + '"><a href="javascript:;">' + i + '</a></li>');
			} else {
				$('.pagination.buy li:last').before('<li  databind="' + i + '"><a href="javascript:;">' + i + '</a></li>');
			}
		}
		$('.pagination.buy li').unbind('click').bind('click', function() {
			var databind = $(this).attr('databind');
			var pageNow = 1;
			// 上一页
			if (databind === 'prev') {
				if ($(this).hasClass('am-disabled')) {
					return;
				}
				$('.pagination.buy li').each(function() {
					if (!$(this).hasClass('am-disabled') && $(this).attr('databind') !== 'prev' && $(this).hasClass('curent')) {
						pageNow = parseInt($(this).attr('databind')) - 1;
					}
				});
			} else if (databind === 'next') {// 下一页
				if ($(this).hasClass('am-disabled')) {
					return;
				}
				$('.pagination.buy li').each(function() {
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
	showTradesBought(1, accessToken);
})