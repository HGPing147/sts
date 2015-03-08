package cn.com.secbuy.constant;

/**
 * @ClassName: StatusCode
 * @Description: TODO(HTTP状态常量)
 * @author fan
 * @date 2015年3月1日 下午9:26:55
 * @version V1.0
 */
public class StatusCode {
	public static int OK = 200;// [GET]：服务器成功返回用户请求的数据，该操作是幂等的（Idempotent）
	public static int CREATED = 201;// [POST/PUT/PATCH]：用户新建或修改数据成功
	public static int DELETED = 204;// 用户删除数据成功
	public static int INVALID_REQUEST = 400;// 用户发出的请求有错误，服务器没有进行新建或修改数据的操作，该操作是幂等的
	public static int UNAUTHORIZED = 401;// 表示用户没有权限（令牌、用户名、密码错误）
	public static int FORBIDDEN = 403;// 表示用户得到授权（与401错误相对），但是访问是被禁止的
	public static int NOT_FOUND = 404;// 用户发出的请求针对的是不存在的记录，服务器没有进行操作，该操作是幂等的
	public static int INTERNAL_SERVER_ERROR = 500;// 服务器发生错误，用户将无法判断发出的请求是否成功
}
