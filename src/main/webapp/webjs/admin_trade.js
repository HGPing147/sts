$(function() {
	var showTrades = function(pageNow) {
		// 分页
		var pagination = function(pageNow, pages, rows) {
			$('.am-pagination').html('');
			$('.am-pagination').append('<li class="am-disabled" databind="prev"><a href="javascript:;">&laquo;</a></li>');
			$('.am-pagination').append('<li class="am-disabled" databind="next"><a href="javascript:;">&raquo;</a></li>');
			if (pageNow > 1) {
				$('.am-pagination li:first').removeClass('am-disabled');
			}
			if (pageNow < pages) {
				$('.am-pagination li:last').removeClass('am-disabled');
			}
			for (var i = 1; i <= pages; i++) {
				if (i == pageNow) {
					$('.am-pagination li:last').before('<li databind="' + i + '"><a href="javascript:;">' + i + '</a></li>');
				} else {
					$('.am-pagination li:last').before('<li class="am-disabled" databind="' + i + '"><a href="javascript:;">' + i + '</a></li>');
				}
			}
			// 分页
			$('.am-pagination li').unbind('click').bind('click', function() {
				var databind = $(this).attr('databind');
				var pageNow = 1;
				// 上一页
				if (databind === 'prev') {
					if ($(this).hasClass('am-disabled')) {
						return;
					}
					$('.am-pagination li').each(function() {
						if (!$(this).hasClass('am-disabled') && $(this).attr('databind') !== 'prev') {
							pageNow = parseInt($(this).attr('databind')) - 1;
						}
					});
				} else if (databind === 'next') {// 下一页
					if ($(this).hasClass('am-disabled')) {
						return;
					}
					$('.am-pagination li').each(function() {
						if (!$(this).hasClass('am-disabled') && $(this).attr('databind') !== 'next') {
							pageNow = parseInt($(this).attr('databind')) + 1;
						}
					});
				} else {
					pageNow = databind;
				}
				showTrades(pageNow);// 加载数据
			});
		};
		$.ajax({
			url : 'api/v1/trades',
			type : 'GET',
			data : {
				pageNow : pageNow == '' ? 1 : pageNow
			},
			dataType : 'json',
			success : function(data) {
				var rows = data.page.rows;// 获取记录数
				if (rows > 0) {
					var _data = data.data;
					var tradeArr = [];
					var pages = data.page.pages;
					var rows = data.page.rows;
					$.each(_data, function(i, d) {
						var tradeId = d.id;
						var resName = d.title == null ? '-' : d.title.length > 8 ? d.title.substring(0, 8) + '...' : d.title;
						var seller = d.sellerName;
						var sellerContact = d.serllerContact;
						var buyer = d.buyerName;
						var buyerContact = d.buyerContact;
						var createtime = d.createtime;
						var tradeStatus = d.status === 2 ? '卖家撤回' : d.status === 3 ? '交易中...' : d.status === 4 ? '交易成功' : d.status === 4 ? '交易失败' : '-';
						var tradeDelBtn = '<a class="am-btn am-btn-default am-btn-xs am-text-danger  trade-del"><span class="am-icon-trash-o"></span> 删除</a>';
						var item = '<tr trade-id="' + tradeId + '"><td>' + tradeId + '</td><td>' + resName + '</td><td>' + seller + '</td><td>' + sellerContact + '</td><td>'
								+ buyer + '</td><td>' + buyerContact + '</td><td>' + createtime + '</td><td>' + tradeStatus + '</td><td>' + tradeDelBtn + '</td></tr>';
						tradeArr.push(item);
					});
					$('.trade-table tbody').html(tradeArr.join(' '));
					$('.pagination').show();
					pagination(pageNow, pages, rows);
					delTrade();
				} else {
					$('.trade-table tbody').html('');
					$('.pagination').hide();
				}
			},
			error : function() {
				alert('获取交易数据异常！');
				$('.trade-table tbody').html('');
				$('.pagination').hide();
			}
		});
	};

	/**
	 * 删除交易记录
	 */
	function delTrade() {
		// 绑定删除事件
		$('.trade-del').unbind('click').bind('click', function() {
			if (confirm("确定要删除数据吗？")) {
				var self = $(this);
				var tradeId = self.parents('tr').attr('trade-id');
				$.ajax({
					url : 'api/v1/trades/' + tradeId,
					type : 'DELETE',
					dataType : 'json',
					success : function(data) {
						if (data.status_code === 200) {
							showTrades(1);
						} else {
							alert(data.error_message);
						}
					},
					error : function() {
						alert('删除交易记录异常！');
					}
				});
			}
		});
	}
	showTrades(1);
})