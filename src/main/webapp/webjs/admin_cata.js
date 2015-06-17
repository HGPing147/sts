$(function() {
	// 添加cata
	var cataNew = function() {
		$('.cata-new').unbind('click').bind('click', function() {
			$('.cataPop .am-popup-title').text('新增信息');
			$('.cataPop .add').show();
			$('.cataPop .update').hide();
			$('.cataForm input').val('');
			$('.cataForm textarea').val('');
		})
		$('.cataPop .add').unbind('click').bind('click', function() {
			var cataName = $('.cataForm input:eq(1)').val();
			$.ajax({
				url : 'api/v1/catagories/new',
				type : 'POST',
				data : {
					name : cataName,
				},
				dataType : 'json',
				success : function(data) {
					if (data.status_code === 200) {
						$('.cataPop .am-close').click();
						showCatas();
					} else {
						alert(error_message);
					}
				},
				error : function() {
					alert('修改分类异常！');
				}
			});
		});
	};
	// 显示分类信息
	var showCatas = function() {
		$.ajax({
			url : 'api/v1/catagories',
			type : 'GET',
			dataType : 'json',
			success : function(data) {
				var catas = data.data;
				if (catas.length > 0) {
					var catasArr = [];
					$.each(catas, function(i, d) {
						var cataId = d.id;
						var cataName = d.name;
						var editBtn = '<a class="am-btn am-btn-default am-btn-xs cata-edit" data-am-modal="{target: \'#my-popup\'}">'
								+ '<span class="am-icon-pencil-square-o"></span> 修改</a>';
						var delBtn = '<a class="am-btn am-btn-default am-btn-xs am-text-danger am-hide-sm-only cata-del"><span class="am-icon-trash-o"></span> 删除</a>';
						var item = '<tr cata-id="' + cataId + '"><td>' + cataId + '</td><td>' + cataName + '</td><td><div class="am-btn-toolbar">'
								+ '<div class="am-btn-group am-btn-group-xs">' + editBtn + delBtn + '</div></div></td></tr>';
						catasArr.push(item);
					});
					$('.cata-table tbody').html(catasArr.join(''));
					delCata();// 删除版块
					editCata();// 修改版块
				} else {
					$('.cata-table tbody').html('');
				}
			},
			error : function() {
				alert('获取分类数据异常！');
				$('.cata-table tbody').html('');
			}
		});
	};

	/**
	 * 删除分类
	 */
	function delCata() {
		// 绑定删除事件
		$('.cata-del').unbind('click').bind('click', function() {
			if (confirm("确定要删除数据吗？")) {
				var self = $(this);
				var cataId = self.parents('tr').attr('cata-id');
				$.ajax({
					url : 'api/v1/catagories/' + cataId,
					type : 'DELETE',
					dataType : 'json',
					success : function(data) {
						if (data.status_code === 200) {
							showCatas();
						} else {
							alert(data.error_message);
						}
					},
					error : function() {
						alert('删除分类异常！');
					}
				});
			}
		});
	}
	/**
	 * 修改分类
	 */
	function editCata() {
		$('.cata-edit').unbind('click').bind('click', function() {
			$('.cataPop .am-popup-title').text('修改信息');
			$('.cataPop .add').hide();
			$('.cataPop .update').show();
			var cataId = $(this).parents('tr').attr('cata-id');
			$.ajax({
				url : 'api/v1/catagories/' + cataId,
				type : 'GET',
				dataType : 'json',
				success : function(data) {
					if (data.status_code === 200) {
						var cataName = data.data.name;
						$('.cataForm input:eq(0)').val(cataId);
						$('.cataForm input:eq(1)').val(cataName);
					}
				},
				error : function() {
					alert('获取分类信息异常！');
				}
			});
		});
		$('.cataPop .update').unbind('click').bind('click', function() {
			var cataId = $('.cataForm input:eq(0)').val();
			var cataName = $('.cataForm input:eq(1)').val();
			$.ajax({
				url : 'api/v1/catagories/' + cataId,
				type : 'POST',
				data : {
					cataId : cataId,
					name : cataName
				},
				dataType : 'json',
				success : function(data) {
					if (data.status_code === 200) {
						$('.cataPop .am-close').click();
						showCatas();
					} else {
						alert(data.error_message);
					}
				},
				error : function() {
					alert('修改分类异常！');
				}
			});
		});
	}
	cataNew();
	showCatas();
})