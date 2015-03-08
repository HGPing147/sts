package cn.com.secbuy.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @ClassName: ResController
 * @Description: TODO(路由)
 * @author fan
 * @date 2015年3月7日 下午2:48:08
 * @version V1.0
 */
@Controller
public class Router {
	/**
	 * 登陆
	 * 
	 * @return
	 */
	@RequestMapping(value = "/login")
	public String toLogin() {
		return "login";
	}

	/**
	 * 注册
	 * 
	 * @return
	 */
	@RequestMapping(value = "/register")
	public String toRegister() {
		return "register";
	}

	/**
	 * 用户个人中心
	 * 
	 * @return
	 */
	@RequestMapping(value = "user/{userId}")
	public String toUserCenter() {
		return "user_center";
	}

	/**
	 * 用户修改页
	 * 
	 * @return
	 */
	@RequestMapping(value = "user/{userId}/modify")
	public String toUserModify() {
		return "user_modify";
	}

	/**
	 * 商品页
	 * 
	 * @return
	 */
	@RequestMapping(value = "/reses")
	public String toReses() {
		return "reses";
	}

	/**
	 * 商品详情页
	 * 
	 * @return
	 */
	@RequestMapping(value = "reses/{resId}")
	public String toResDetail() {
		return "res_detail";
	}

	/**
	 * 商品新增页
	 * 
	 * @return
	 */
	@RequestMapping(value = "reses/{resId}/new")
	public String toResNew() {
		return "res_new";
	}

	/**
	 * 商品修改页
	 * 
	 * @return
	 */
	@RequestMapping(value = "reses/{resId}/modify")
	public String toResModify() {
		return "res_modify";
	}
}
