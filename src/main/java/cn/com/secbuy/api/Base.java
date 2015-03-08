package cn.com.secbuy.api;

import java.util.Date;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import cn.com.secbuy.util.DateConver;
import cn.com.secbuy.util.MD5Secure;

public class Base {
	protected static int OK = 200;// [GET]：服务器成功返回用户请求的数据，该操作是幂等的（Idempotent）
	protected static int CREATED = 201;// [POST/PUT/PATCH]：用户新建或修改数据成功
	protected static int DELETED = 204;// 用户删除数据成功
	protected static int INVALID_REQUEST = 400;// 用户发出的请求有错误，服务器没有进行新建或修改数据的操作，该操作是幂等的
	protected static int UNAUTHORIZED = 401;// 表示用户没有权限（令牌、用户名、密码错误）
	protected static int FORBIDDEN = 403;// 表示用户得到授权（与401错误相对），但是访问是被禁止的
	protected static int NOT_FOUND = 404;// 用户发出的请求针对的是不存在的记录，服务器没有进行操作，该操作是幂等的
	protected static int INTERNAL_SERVER_ERROR = 500;// 服务器发生错误，用户将无法判断发出的请求

	protected static String STATUS_CODE = "status_code";// HTT状态码
	protected static String ERROR_MESSAGE = "error_message";// 错误信息
	protected static String OK_MESSAGE = "ok_message";// 成功信息
	protected static String DATA = "data";// 返回数据信息
	protected static String PAGE = "page";// 返回页信息

	protected static String FILEURL = "/uploadFiles";// 返回页信息

	protected static String SESSION_USER = "session_user";// session用户保存名称

	protected static int COOKIE_MAXAGE = 7 * 24 * 60 * 60;// cookie保存一周
	protected static String COOKIE_NICKNAME = "nickName";// cookie昵称
	protected static String COOKIE_ACCESSTOKEN = "accessToken";// cookie登录令牌
	protected static String COOKIE_PROFILEIMAGEURL = "profileImageUrl";// cookie头像地址

	protected static int PUTSDOWN = 0;// 下架(商品)
	protected static int WAITING = 1;// 等待审核(商品)
	protected static int PUTSUP = 2;// 发布(商品)
	protected static int TRADING = 3;// 交易进行中(商品和交易)
	protected static int TRADED = 4;// 交易成功(交易)
	protected static int TRADINGBROKEN = 2;// 交易失败(交易)

	protected static int ALLER = 0;// 全部
	protected static int SELLER = 1;// 卖家
	protected static int BUYER = 2;// 买家

	/**
	 * 设置cookie（接口方法）
	 * 
	 * @param response
	 * @param name
	 *            cookie名字
	 * @param value
	 *            cookie值
	 * @param maxAge
	 *            cookie生命周期 以秒为单位
	 */
	protected void addCookie(HttpServletResponse response, String name, String value, int maxAge) {
		Cookie cookie = new Cookie(name, value);
		cookie.setPath("/");
		if (maxAge > 0) {
			cookie.setMaxAge(maxAge);
		}
		response.addCookie(cookie);
	}

	/**
	 * 生成accessid
	 * 
	 * @param mail
	 *            邮箱
	 * @param password
	 *            密码
	 * @return accessid 登录令牌
	 */
	protected String getAccessToken(String mail, String password, String timeStamp) {
		String accessToken = mail + "$$$" + password + "$$$" + timeStamp;
		return MD5Secure.secure(accessToken);
	}

	public static void main(String[] args) {
		String mail = "1161366196@qq.com";
		String password = "tang1119";
		String timeStamp = DateConver.getTimeStamp(new Date());
		String accessToken = new Base().getAccessToken(mail, password, timeStamp);
		System.out.println(accessToken);
	}
}
