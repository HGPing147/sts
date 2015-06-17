$(function() {
	var showUsers = function(pageNow) {
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
				showUsers(pageNow);// 加载用户数据
			});
		};

		$.ajax({
			url : 'api/v1/admin/users',
			type : 'GET',
			data : {
				pageNow : pageNow == '' ? 1 : pageNow
			},
			dataType : 'json',
			success : function(data) {
				var rows = data.page.rows;// 获取记录数
				if (rows > 0) {
					var _data = data.data;
					var usersArr = [];
					var pages = data.page.pages;
					var rows = data.page.rows;
					$.each(_data, function(i, d) {
						var userId = d.id;
						var nickName = d.nickName;
						var userMail = d.userMail;
						var userName = d.name;
						var contact = d.contact;
						var operateBtn = '<a class="am-btn am-btn-default am-btn-xs am-text-danger  user-del"><span class="am-icon-trash-o"></span> 删除</a>';
						var item = ' <tr user-id="' + userId + '"><td>' + userId + '</td> <td>' + nickName + '</td> <td>' + userMail + '</td> <td>' + userName + '</td> <td>'
								+ contact + '</td> <td>' + operateBtn + '</td></tr>';
						usersArr.push(item);
					});
					$('.user-table tbody').html(usersArr.join(' '));
					$('.pagination').show();
					pagination(pageNow, pages, rows);
					delUser();
				} else {
					$('.user-table tbody').html('');
					$('.pagination').hide();
				}
			},
			error : function() {
				alert('获取用户数据异常！');
				$('.user-table tbody').html('');
				$('.pagination').hide();
			}
		});
	};

	/**
	 * 删除用户
	 */
	function delUser() {
		// 绑定删除事件
		$('.user-del').unbind('click').bind('click', function() {
			if (confirm("确定要删除数据吗？")) {
				var self = $(this);
				var userId = self.parents('tr').attr('user-id');
				$.ajax({
					url : 'api/v1/admin/users/' + userId,
					type : 'DELETE',
					dataType : 'json',
					success : function(data) {
						if (data.status_code === 200) {
							showUsers(1);
						} else {
							alert(error_message);
						}
					},
					error : function() {
						alert('删除用户异常！');
					}
				});
			}
		});
	}
	showUsers(1);
})