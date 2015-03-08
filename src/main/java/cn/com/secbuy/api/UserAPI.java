package cn.com.secbuy.api;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.com.secbuy.dto.UserDTO;
import cn.com.secbuy.pojo.User;
import cn.com.secbuy.service.UserService;
import cn.com.secbuy.util.DateConver;
import cn.com.secbuy.util.MD5Secure;

/**
 * @ClassName: UserController
 * @Description: TODO(用户API)
 * @author fan
 * @date 2015年3月1日 下午9:23:49
 * @version V1.0
 */
@Controller
public class UserAPI extends Base {
	@Autowired
	private UserService userService;
	private Map<String, Object> responseMap = null;// 返回json

	/**
	 * 登录
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/api/v1/user/login", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> login(HttpServletRequest request, HttpServletResponse response) {
		String account = request.getParameter("account");// 获取账号
		String password = request.getParameter("password");// 获取密码
		responseMap = new HashMap<String, Object>();
		if (StringUtils.isEmpty(account)) {
			responseMap.put(STATUS_CODE, INVALID_REQUEST);// 返回错误请求状态码
			responseMap.put(ERROR_MESSAGE, "用户名不能为空！");// 返回错误信息
			return responseMap;
		}
		if (StringUtils.isEmpty(password)) {
			responseMap.put(STATUS_CODE, INVALID_REQUEST);// 返回错误请求状态码
			responseMap.put(ERROR_MESSAGE, "密码不能为空！");// 返回错误信息
			return responseMap;
		}
		UserDTO user = userService.findUserByAccountAndPwd(account, MD5Secure.secure(password));// 数据库比对用户名密码
		if (user != null && user.getId() != null) {
			Date now = new Date();
			userService.updateLastLoginTime(now, user.getId());// 更新用户最后登录时间
			request.getSession().setAttribute(SESSION_USER, user);// 保存服务器session
			String nickName = user.getNickName();// 昵称
			this.addCookie(response, COOKIE_NICKNAME, nickName, COOKIE_MAXAGE);// Cookie保存昵称
			String accessToken = this.getAccessToken(user.getUserMail(), password, DateConver.getTimeStamp(now));// 生成登录令牌
			userService.addAccessid(user.getId(), accessToken);// 保存令牌
			this.addCookie(response, COOKIE_ACCESSTOKEN, accessToken, COOKIE_MAXAGE);// Cookie保存登录令牌
			String profileImageUrl = user.getProfileimageurl();// 头像地址
			this.addCookie(response, COOKIE_PROFILEIMAGEURL, profileImageUrl, COOKIE_MAXAGE);// Cookie保存头像地址
			responseMap.put(STATUS_CODE, OK);// 返回验证错误状态码
			responseMap.put(OK_MESSAGE, "登录成功！");// 返回错误信息
		} else {
			responseMap.put(STATUS_CODE, UNAUTHORIZED);// 返回验证错误状态码
			responseMap.put(ERROR_MESSAGE, "用户名或密码不正确！");// 返回错误信息
		}
		return responseMap;
	}

	/**
	 * 用户注册
	 * 
	 * @param user
	 *            用户信息
	 * @return
	 */
	@RequestMapping(value = "/api/v1/user/register", method = RequestMethod.PUT)
	@ResponseBody
	public Map<String, Object> register(User user) {
		responseMap = new HashMap<String, Object>();
		if (user == null) {
			responseMap.put(STATUS_CODE, INVALID_REQUEST);// 返回错误请求状态码
			responseMap.put(ERROR_MESSAGE, "用户不能为空！");// 返回错误信息
			return responseMap;
		}
		user.setPassword(MD5Secure.secure(user.getPassword()));// 密码加密
		boolean result = userService.addUser(user);// 数据存入
		if (result) {
			responseMap.put(STATUS_CODE, OK);// 返回验证错误状态码
			responseMap.put(OK_MESSAGE, "注册成功！");// 返回错误信息
		} else {
			responseMap.put(STATUS_CODE, INTERNAL_SERVER_ERROR);// 返回验证错误状态码
			responseMap.put(ERROR_MESSAGE, "服务端发生错误!注册功能异常！");// 返回错误信息
		}
		return responseMap;
	}

	/**
	 * 显示用户个人信息
	 * 
	 * @param userId
	 *            用户序号
	 * @return
	 */
	@RequestMapping(value = "/api/v1/user/{userId}", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> showUserInfo(@PathVariable String userId) {
		responseMap = new HashMap<String, Object>();
		if (!StringUtils.isNumeric(userId)) {
			responseMap.put(STATUS_CODE, INVALID_REQUEST);// 返回错误请求状态码
			responseMap.put(ERROR_MESSAGE, "用户id不能为空！");// 返回错误信息
			return responseMap;
		}
		UserDTO user = userService.findUserByID(Integer.valueOf(userId));// 查询用户信息
		if (user != null && user.getId() != null) {
			responseMap.put(STATUS_CODE, OK);// 返回验证错误状态码
			responseMap.put(DATA, user);// 返回错误信息
			responseMap.put(OK_MESSAGE, "注册成功！");// 返回错误信息
		} else {
			responseMap.put(STATUS_CODE, NOT_FOUND);// 返回404未找到记录状态码
			responseMap.put(ERROR_MESSAGE, "用户记录不存在！");// 返回错误信息
		}
		return responseMap;
	}

	/**
	 * 修改用户信息
	 * 
	 * @param userId
	 *            用户id
	 * @param updatedUser
	 *            用户修改后信息
	 * @return
	 */
	@RequestMapping(value = "/api/v1/user/{userId}", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> modifyUser(@PathVariable String userId, User updatedUser) {
		responseMap = new HashMap<String, Object>();
		if (!StringUtils.isNumeric(userId)) {
			responseMap.put(STATUS_CODE, INVALID_REQUEST);// 返回错误请求状态码
			responseMap.put(ERROR_MESSAGE, "用户id不能为空！");// 返回错误信息
			return responseMap;
		}
		if (updatedUser == null) {
			responseMap.put(STATUS_CODE, INVALID_REQUEST);// 返回错误请求状态码
			responseMap.put(ERROR_MESSAGE, "修改用户不能为空！");// 返回错误信息
			return responseMap;
		}
		updatedUser.setId(Integer.valueOf(userId));
		boolean result = userService.updateUser(updatedUser);
		if (result) {
			responseMap.put(STATUS_CODE, OK);// 返回验证错误状态码
			responseMap.put(OK_MESSAGE, "修改成功！");// 返回错误信息
		} else {
			responseMap.put(STATUS_CODE, INTERNAL_SERVER_ERROR);// 返回验证错误状态码
			responseMap.put(ERROR_MESSAGE, "服务端发生错误!修改功能异常！");// 返回错误信息
		}
		return responseMap;
	}

	/**
	 * 修改密码
	 * 
	 * @param userId
	 *            用户序号
	 * @param password
	 *            新密码
	 * @return
	 */
	@RequestMapping(value = "/api/v1/user/{userId}", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> modifyPassword(@PathVariable String userId, String password) {
		responseMap = new HashMap<String, Object>();
		if (!StringUtils.isNumeric(userId)) {
			responseMap.put(STATUS_CODE, INVALID_REQUEST);// 返回错误请求状态码
			responseMap.put(ERROR_MESSAGE, "用户id不能为空！");// 返回错误信息
			return responseMap;
		}
		if (StringUtils.isEmpty(password)) {
			responseMap.put(STATUS_CODE, INVALID_REQUEST);// 返回错误请求状态码
			responseMap.put(ERROR_MESSAGE, "修改密码不能为空！");// 返回错误信息
			return responseMap;
		}
		boolean result = userService.updateUserPwd(Integer.valueOf(userId), password);
		if (result) {
			responseMap.put(STATUS_CODE, OK);// 返回验证错误状态码
			responseMap.put(OK_MESSAGE, "修改密码成功！");// 返回错误信息
		} else {
			responseMap.put(STATUS_CODE, INTERNAL_SERVER_ERROR);// 返回验证错误状态码
			responseMap.put(ERROR_MESSAGE, "服务端发生错误!修改功能异常！");// 返回错误信息
		}
		return responseMap;
	}

	/**
	 * 获取用户accessToken
	 * 
	 * @param account
	 *            账号
	 * @param password
	 *            密码
	 * @return
	 */
	@RequestMapping(value = "/api/v1/user/token", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> showUserAccessToken(String account, String password) {
		responseMap = new HashMap<String, Object>();
		if (StringUtils.isEmpty(account)) {
			responseMap.put(STATUS_CODE, INVALID_REQUEST);// 返回错误请求状态码
			responseMap.put(ERROR_MESSAGE, "用户名不能为空！");// 返回错误信息
			return responseMap;
		}
		if (StringUtils.isEmpty(password)) {
			responseMap.put(STATUS_CODE, INVALID_REQUEST);// 返回错误请求状态码
			responseMap.put(ERROR_MESSAGE, "密码不能为空！");// 返回错误信息
			return responseMap;
		}
		UserDTO user = userService.findUserByAccountAndPwd(account, MD5Secure.secure(password));
		if (user != null && user.getId() != null) {
			if (user.getAccessid() != null) {
				responseMap.put("access_token", user.getAccessid());
			} else {
				String accessToken = this.getAccessToken(user.getUserMail(), password, DateConver.getTimeStamp(new Date()));
				userService.addAccessid(user.getId(), accessToken);// 保存令牌
				responseMap.put("access_token", accessToken);
			}
			responseMap.put(STATUS_CODE, OK);// 返回状态码
			responseMap.put(OK_MESSAGE, "获取用户accessToken成功！");// 返回信息
		} else {
			responseMap.put(STATUS_CODE, NOT_FOUND);// 返回404未找到记录状态码
			responseMap.put(ERROR_MESSAGE, "用户记录不存在！");// 返回错误信息
		}
		return responseMap;
	}
}
