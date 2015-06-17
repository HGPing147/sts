$(function() {
	var accessToken = Base.getCookieValueByName('accessToken');
	// 显示我发布的物品
	var showUserReses = function(pageNow, accessToken) {
		$.ajax({
			url : 'api/v1/reses/user/' + accessToken,
			type : 'GET',
			data : {
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
						var cataName = d.cataName;
						var resTitle = d.title;
						var resCost = d.cost;
						var resCreatetime = d.createtime.split('.')[0];
						var resPutstime = d.putstime == null ? '-' : d.putstime.split('.')[0];
						var resStatus = d.status === 1 ? '待审核' : d.status === 0 ? '已下架' : '已发布';
						var putsdownBtn = d.status === 2 || d.status === 1 ? '下架' : '';
						var res = '<tr reses-id="' + resId + '"><td>' + cataName + '</td>	<td>' + resTitle + '</td><td>￥' + resCost + '</td><td>' + resCreatetime + '</td><td>'
								+ resPutstime + '</td><td>' + resStatus + '</td><td><span class="putsdown">' + putsdownBtn + '</span></td></tr>';
						resesArr.push(res);
					});
					$('.own-reses tbody').html(resesArr.join(' '));
					// 分页组件
					Base.pagination(pageNow, pages, rows, function(pageCurrent) {
						console.log(pageCurrent + '====================');
						showUserReses(pageCurrent, accessToken);// 显示用户下的分页商品
					});
					putsdown(accessToken);// 下架
				} else {
					$('.own-reses tbody').html('没有商品数据！');
					$('.pagination').hide();
				}
			},
			error : function() {
				$('.own-reses tbody').html('');
				$('.pagination').hide();
				alert('获取商品异常！');
			}
		});
	};

	/**
	 * 下架
	 */
	function putsdown(accessToken) {
		$('.putsdown').unbind('click').bind('click', function() {
			if (confirm("确定要下架此商品吗？")) {
				var resId = $(this).parents('tr').attr('reses-id');
				$.ajax({
					url : 'api/v1/reses/' + resId + '/putsdown',
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
							showUserReses(1, accessToken);// 显示用户下的商品信息
							alert('商品下架成功!');
						} else if (statusCode === 400) {
							alert(data.error_message);
						} else if (statusCode === 500) {
							alert(data.error_message);
						}
					},
					error : function() {
						alert('商品下架异常!');
					}
				});
			}
		});
	}
	showUserReses(1, accessToken);// 显示用户下的商品信息
})