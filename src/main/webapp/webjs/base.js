var Base = {};
/**
 * 获取cookie名下的值
 */
Base.getCookieValueByName = function(name) {
	var value = '';
	var cookies = document.cookie.split(';');
	for (var i = 0; i < cookies.length; i++) {
		var cookieName = cookies[i].split("=")[0];// cookie名称
		var cookieValue = cookies[i].split("=")[1];// cookie值
		if (name === $.trim(cookieName)) {
			value = cookieValue;
			break;
		}
	}
	return value;
};
/**
 * 添加cookie
 */
Base.addCookie = function(name, value, hours) {// 添加cookie
	var str = name + "=" + escape(value);
	if (hours > 0) {// 为0时不设定过期时间，浏览器关闭时cookie自动消失
		var date = new Date();
		var ms = hours * 60 * 60 * 1000;
		date.setTime(date.getTime() + ms);
		str += "; expires=" + date.toGMTString();
	}
	document.cookie = str;
}
/**
 * 删除指定名称的cookie
 */
Base.delCookieByName = function(name) {
	var date = new Date();
	date.setTime(date.getTime() - 1);// 为了删除指定名称的cookie，可以将其过期时间设定为一个过去的时间
	document.cookie = name + "=a; expires=" + date.toGMTString();
};
/**
 * 设置cookie
 */
Base.setCookieByName = function(name, value) {
	document.cookie = name + "=" + value;
}
/**
 * 解码
 */
Base.decodeValue = function(param) {
	var value = decodeURIComponent(param);
	return value;
}

/**
 * 编码
 */
Base.encodeValue = function(param) {
	var value = encodeURIComponent(param);
	return value;
}

/**
 * 获取url参数
 */
Base.getParam = function(name) {
	var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
	var r = window.location.search.substr(1).match(reg);
	if (r != null) {
		return unescape(r[2]);
	}
	return null;
}

// 分页
Base.pagination = function(pageNow, pages, rows, loadData) {
	$('.pagination').show();
	$('.pagination ul').html('');
	$('.pagination ul').append('<li class="prev am-disabled" databind="prev"><a href="javascript:;">&#8592;</a></li>');
	$('.pagination ul').append('<li class="next am-disabled" databind="next"><a href="javascript:;">&#8594;</a></li>');
	if (pageNow > 1) {
		$('.pagination li:first').removeClass('am-disabled');
	}
	if (pageNow < pages) {
		$('.pagination li:last').removeClass('am-disabled');
	}
	for (var i = 1; i <= pages; i++) {
		if (i == pageNow) {
			$('.pagination li:last').before('<li class="curent" databind="' + i + '"><a href="javascript:;">' + i + '</a></li>');
		} else {
			$('.pagination li:last').before('<li  databind="' + i + '"><a href="javascript:;">' + i + '</a></li>');
		}
	}
	$('.pagination li').unbind('click').bind('click', function() {
		var databind = $(this).attr('databind');
		var pageNow = 1;
		// 上一页
		if (databind === 'prev') {
			if ($(this).hasClass('am-disabled')) {
				return;
			}
			$('.pagination li').each(function() {
				if (!$(this).hasClass('am-disabled') && $(this).attr('databind') !== 'prev' && $(this).hasClass('curent')) {
					pageNow = parseInt($(this).attr('databind')) - 1;
				}
			});
		} else if (databind === 'next') {// 下一页
			if ($(this).hasClass('am-disabled')) {
				return;
			}
			$('.pagination li').each(function() {
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
