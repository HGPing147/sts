package cn.com.secbuy.security;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.com.secbuy.dao.UserDAO;
import cn.com.secbuy.dao.impl.UserDAOImpl;

/**
 * @ClassName: UserSecurity
 * @Description: TODO(用户权限控制)
 * @author fan
 * @date 2015年3月7日 下午7:52:29
 * @version V1.0
 */
public class UserSecurity implements Filter {
	private String[] ignoreURLInclude = new String[] { "login", "logout" };
	private UserDAO userDAO = new UserDAOImpl();

	@Override
	public void destroy() {

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		boolean next = false;
		// 判读提交方法是否需要权限
		String method = req.getMethod();
		if (method.trim().toUpperCase().equals("GET")) {
			next = true;
		}
		// 判读请求路劲是否需要权限
		if (!next) {
			String url = req.getRequestURI();
			for (String igUrl : ignoreURLInclude) {
				if (url.indexOf(igUrl) != -1) {
					next = true;
					break;
				}
			}
		}

		if (!next) {
			String accessToken = req.getParameter("access_token");
			boolean ifAuthed = userDAO.findRealUserByAccessid(accessToken);
			if (ifAuthed) {
				chain.doFilter(req, res);
			} else {
				String json = "{\"status_code\":\"401\",\"error_message\":\"请登录用户！\"}";
				res.setCharacterEncoding("UTF-8");
				res.setContentType("application/json; charset=utf-8");
				PrintWriter out = null;
				try {
					out = response.getWriter();
					out.append(json);
				} catch (IOException e) {
					e.printStackTrace();
				} finally {
					if (out != null) {
						out.close();
					}
				}
			}
		} else {
			chain.doFilter(request, response);
		}
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {

	}
}
