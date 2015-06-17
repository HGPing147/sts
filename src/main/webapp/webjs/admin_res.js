$(function() {
	var showReses = function(pageNow) {
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
				showReses(pageNow);// 加载数据
			});
		};
		$.ajax({
			url : 'api/v1/admin/reses',
			type : 'GET',
			data : {
				pageNow : pageNow == '' ? 1 : pageNow
			},
			dataType : 'json',
			success : function(data) {
				var rows = data.page.rows;// 获取记录数
				if (rows > 0) {
					var _data = data.data;
					var resArr = [];
					var pages = data.page.pages;
					var rows = data.page.rows;
					$.each(_data, function(i, d) {
						var resId = d.id;
						var resName = d.title == null ? '-' : d.title.length > 8 ? d.title.substring(0, 8) + '...' : d.title;
						var resDesc = d.resDesc == null ? '-' : d.resDesc.length > 8 ? d.resDesc.substring(0, 8) + '...' : d.resDesc;
						var createTime = d.createtime.split('.')[0];
						var putstime = d.putstime == null ? '-' : d.putstime;
						var digest = d.digest === 1 ? '是' : '否';
						var status = d.status === 0 ? '下架' : d.status === 1 ? '待审核' : d.status === 2 ? ' 发布' : d.status === 3 ? '正在交易中' : d.status === 4 ? '交易成功' : '-';
						var cataName = d.cataName;
						var recommendBtn = d.status === 2 && d.digest === 0 ? '<a class="am-btn am-btn-default am-btn-xs res-recomm">推荐</a>' : '';
						var unrecommendBtn = d.status === 2 && d.digest === 1 ? '<a class="am-btn am-btn-default am-btn-xs res-unrecomm">撤回推荐</a>' : '';
						var putsupBtn = d.status === 1 ? '<a class="am-btn am-btn-default am-btn-xs res-putsup">发布</a>' : '';
						var item = '<tr res-id="' + resId + '"><td>' + resId + '</td><td>' + resName + '</td><td>' + resDesc + '</td><td>' + createTime + '</td><td>' + putstime
								+ '</td><td>' + digest + '</td><td>' + status + '</td><td>' + cataName + '</td><td><div class="am-btn-toolbar">'
								+ '<div class="am-btn-group am-btn-group-xs">' + recommendBtn + unrecommendBtn + putsupBtn + '</div></div></td></tr>';
						resArr.push(item);
					});
					$('.res-table tbody').html(resArr.join(' '));
					$('.pagination').show();
					pagination(pageNow, pages, rows);
					putsup();// 发布商品
					recommend();// 推荐商品
					unrecommend();// 撤回推荐
				} else {
					$('.res-table tbody').html('');
					$('.pagination').hide();
				}
			},
			error : function() {
				alert('获取商品数据异常！');
				$('.res-table tbody').html('');
				$('.pagination').hide();
			}
		});
	};

	/**
	 * 强烈推荐
	 */
	function recommend() {
		$('.res-recomm').unbind('click').bind('click', function() {
			if (confirm("确定推荐此商品吗？")) {
				var self = $(this);
				var resId = self.parents('tr').attr('res-id');
				$.ajax({
					url : 'api/v1/reses/' + resId + '/recommend',
					type : 'POST',
					dataType : 'json',
					success : function(data) {
						if (data.status_code === 200) {
							showReses(1);
						} else {
							alert(data.error_message);
						}
					},
					error : function() {
						alert('推荐商品功能异常！');
					}
				});
			}
		});
	}

	/**
	 * 强烈推荐
	 */
	function unrecommend() {
		$('.res-unrecomm').unbind('click').bind('click', function() {
			if (confirm("确定撤回推荐此商品吗？")) {
				var self = $(this);
				var resId = self.parents('tr').attr('res-id');
				$.ajax({
					url : 'api/v1/reses/' + resId + '/unrecommend',
					type : 'POST',
					dataType : 'json',
					success : function(data) {
						if (data.status_code === 200) {
							showReses(1);
						} else {
							alert(data.error_message);
						}
					},
					error : function() {
						alert('撤回推荐商品功能异常！');
					}
				});
			}
		});
	}

	/**
	 * 审核通过
	 */
	function putsup() {
		$('.res-putsup').unbind('click').bind('click', function() {
			if (confirm("确定审核通过将商品发布吗？")) {
				var self = $(this);
				var resId = self.parents('tr').attr('res-id');
				$.ajax({
					url : 'api/v1/reses/' + resId + '/putsup',
					type : 'POST',
					dataType : 'json',
					success : function(data) {
						if (data.status_code === 200) {
							showReses(1);
						} else {
							alert(data.error_message);
						}
					},
					error : function() {
						alert('审核通过商品发布功能异常！');
					}
				});
			}
		});
	}
	showReses(1);
})