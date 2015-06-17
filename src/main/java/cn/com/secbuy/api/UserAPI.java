package cn.com.secbuy.api;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.com.secbuy.dto.ResDTO;
import cn.com.secbuy.dto.UserDTO;
import cn.com.secbuy.pojo.User;
import cn.com.secbuy.service.UserService;
import cn.com.secbuy.util.DateConver;
import cn.com.secbuy.util.MD5Secure;
import cn.com.secbuy.util.Pagination;

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
	public Map<String, Object> login(String account, String password, HttpServletRequest request, HttpServletResponse response) {
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
			this.addCookie(response, COOKIE_NICKNAME, this.encodeUTF8(nickName), COOKIE_MAXAGE);// Cookie保存昵称
			String accessToken = this.getAccessToken(user.getUserMail(), password, DateConver.getTimeStamp(now));// 生成登录令牌
			userService.addAccessid(user.getId(), accessToken);// 保存令牌
			this.addCookie(response, COOKIE_ACCESSTOKEN, accessToken, COOKIE_MAXAGE);// Cookie保存登录令牌
			responseMap.put(STATUS_CODE, OK);// 返回验证错误状态码
			responseMap.put(OK_MESSAGE, "登录成功！");// 返回错误信息
		} else {
			responseMap.put(STATUS_CODE, UNAUTHORIZED);// 返回验证错误状态码
			responseMap.put(ERROR_MESSAGE, "用户名或密码不正确！");// 返回错误信息
		}
		return responseMap;
	}

	/**
	 * 注销
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/api/v1/user/logout", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> logout(HttpServletRequest request, HttpServletResponse response) {
		responseMap = new HashMap<String, Object>();
		this.clearCookies(request, response);
		request.getSession().removeAttribute(SESSION_USER);
		responseMap.put(STATUS_CODE, OK);// 返回状态码
		responseMap.put(OK_MESSAGE, "注销成功！");// 返回信息
		return responseMap;
	}

	/**
	 * 用户注册
	 * 
	 * @param user
	 *            用户信息
	 * @return
	 */
	@RequestMapping(value = "/api/v1/user/register", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> register(String nickName, String userMail, String password, String name, String sex, String contact) {
		responseMap = new HashMap<String, Object>();
		User user = new User();
		Date now = new Date();
		user.setAccessid(this.getAccessToken(userMail, password, DateConver.getTimeStamp(now)));
		user.setContact(contact);
		user.setLastUpdateTime(now);
		user.setName(name);
		user.setNickName(nickName);
		user.setPassword(MD5Secure.secure(password));// 密码加密
		user.setSex(sex);
		user.setUserMail(userMail);
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
	@RequestMapping(value = "/api/v1/user/{accessToken}", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> showUserInfo(@PathVariable String accessToken) {
		responseMap = new HashMap<String, Object>();
		if (StringUtils.isEmpty(accessToken)) {
			responseMap.put(STATUS_CODE, INVALID_REQUEST);// 返回错误请求状态码
			responseMap.put(ERROR_MESSAGE, "用户令牌不能为空！");// 返回错误信息
			return responseMap;
		}
		UserDTO user = userService.findUserByAccessid(accessToken);
		if (user != null && user.getId() != null) {
			responseMap.put(STATUS_CODE, OK);// 返回验证错误状态码
			responseMap.put(DATA, user);// 返回错误信息
			responseMap.put(OK_MESSAGE, "获取用户信息成功！");// 返回错误信息
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
	public Map<String, Object> modifyUser(@PathVariable String userId, User updatedUser, HttpServletRequest request, HttpServletResponse response) {
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
			System.out.println(updatedUser.getNickName() + "===");
			this.updateCookie(request, response, COOKIE_NICKNAME, this.encodeUTF8(updatedUser.getNickName()), COOKIE_MAXAGE);
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
	@RequestMapping(value = "/api/v1/user/{userId}/edit-password", method = RequestMethod.POST)
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
		boolean result = userService.updateUserPwd(Integer.valueOf(userId), MD5Secure.secure(password));
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

	/**
	 * 管理员登陆
	 * 
	 * @param userName
	 * @param password
	 * @return
	 */
	@RequestMapping(value = "/api/v1/admin/login", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> adminLogin(String userName, String password) {
		responseMap = new HashMap<String, Object>();
		Properties prop = new Properties();
		InputStream in = UserAPI.class.getClassLoader().getResourceAsStream("/admin.properties");
		String adminName = "";
		String adminPassword = "";
		try {
			prop.load(in);
			adminName = prop.getProperty("adminName").trim();
			adminPassword = prop.getProperty("adminPassword").trim();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		// 验证用户名密码
		if (adminName.equals(userName.trim()) && adminPassword.equals(password.trim())) {
			responseMap.put(STATUS_CODE, OK);
			responseMap.put(OK_MESSAGE, "验证成功！");
		} else {
			responseMap.put(STATUS_CODE, NOT_FOUND);
			responseMap.put(ERROR_MESSAGE, "验证失败！");
		}
		return responseMap;
	}

	/**
	 * 获取用户
	 * 
	 * @param pageNow
	 *            当前页
	 * @param key
	 *            关键字
	 * @return 用户集合
	 */
	@RequestMapping(value = "/api/v1/admin/users", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getUsers(String pageNow, String key) {
		responseMap = new HashMap<String, Object>();
		if (!StringUtils.isNumeric(pageNow)) {
			responseMap.put(STATUS_CODE, INVALID_REQUEST);// 返回错误请求状态码
			responseMap.put(ERROR_MESSAGE, "页码必须是数字！");// 返回错误信息
			return responseMap;
		}
		long rows = userService.findUserRows(key);// 获取总记录数
		if (rows > 0) {
			List<UserDTO> users = userService.findLimitedUsers(Long.valueOf(pageNow), Pagination.PAGESIZE, key);
			Pagination page = new Pagination(Long.valueOf(pageNow), rows);// 分页信息
			responseMap.put(DATA, users);
			responseMap.put(PAGE, page);
		} else {
			Pagination page = new Pagination(0, 0);// 分页信息
			responseMap.put(DATA, new ArrayList<ResDTO>());
			responseMap.put(PAGE, page);
		}
		return responseMap;
	}

	/**
	 * 删除用户
	 * 
	 * @param userId
	 *            用户序号
	 * @return
	 */
	@RequestMapping(value = "/api/v1/admin/users/{userId}", method = RequestMethod.DELETE)
	@ResponseBody
	public Map<String, Object> delUser(@PathVariable String userId) {
		responseMap = new HashMap<String, Object>();
		if (!StringUtils.isNumeric(userId)) {
			responseMap.put(STATUS_CODE, INVALID_REQUEST);// 返回错误请求状态码
			responseMap.put(ERROR_MESSAGE, "用户Id必须是数字！");// 返回错误信息
			return responseMap;
		}
		boolean ifDeleted = userService.delUser(Integer.valueOf(userId));// 删除
		if (ifDeleted) {
			responseMap.put(STATUS_CODE, OK);
			responseMap.put(OK_MESSAGE, "删除用户成功！");
		} else {
			responseMap.put(STATUS_CODE, INTERNAL_SERVER_ERROR);// 返回请求状态码
			responseMap.put(ERROR_MESSAGE, "删除功能异常！");// 返回信息
		}
		return responseMap;
	}
}
