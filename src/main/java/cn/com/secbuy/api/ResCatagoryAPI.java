package cn.com.secbuy.api;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.com.secbuy.pojo.ResCatagory;
import cn.com.secbuy.service.ResCatagoryService;
import cn.com.secbuy.service.ResService;

/**
 * @ClassName: ResCatagoryAPI
 * @Description: TODO(商品分类API)
 * @author fan
 * @date 2015年4月4日 下午7:15:55
 * @version V1.0
 */
@Controller
public class ResCatagoryAPI extends Base {
	@Autowired
	private ResCatagoryService resCatagoryService;
	@Autowired
	private ResService resService;
	private Map<String, Object> responseMap = null;// 返回json

	/**
	 * 获取所有商品分类
	 * 
	 * @return
	 */
	@RequestMapping(value = "/api/v1/catagories", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getResCatagories() {
		responseMap = new HashMap<String, Object>();
		List<ResCatagory> list = resCatagoryService.findResCatagory();
		responseMap.put(STATUS_CODE, OK);// 返回请求状态码
		responseMap.put(DATA, list);
		responseMap.put(OK_MESSAGE, "获取商品分类列表信息成功！");// 返回信息
		return responseMap;
	}

	/**
	 * 添加商品分类
	 * 
	 * @param resCatagory
	 *            商品分类信息
	 * @return
	 */
	@RequestMapping(value = "/api/v1/catagories/new", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> resCatagoryNew(ResCatagory resCatagory) {
		responseMap = new HashMap<String, Object>();
		if (resCatagory == null) {
			responseMap.put(STATUS_CODE, INVALID_REQUEST);// 返回错误请求状态码
			responseMap.put(ERROR_MESSAGE, "商品分类信息不能为空！");// 返回错误信息
			return responseMap;
		}
		boolean ifAdd = resCatagoryService.addReCatagory(resCatagory);
		if (ifAdd) {
			responseMap.put(STATUS_CODE, OK);// 返回请求状态码
			responseMap.put(OK_MESSAGE, "添加商品分类成功！");// 返回信息
		} else {
			responseMap.put(STATUS_CODE, INTERNAL_SERVER_ERROR);// 返回请求状态码
			responseMap.put(ERROR_MESSAGE, "添加商品分类失败！");// 返回信息
		}
		return responseMap;
	}

	/**
	 * 删除分类下的所有商品
	 * 
	 * @param cataId
	 *            分类Id
	 * @return
	 */
	@RequestMapping(value = "/api/v1/catagories/{cataId}", method = RequestMethod.DELETE)
	@ResponseBody
	public Map<String, Object> delResCatagory(@PathVariable String cataId) {
		responseMap = new HashMap<String, Object>();
		if (!StringUtils.isNumeric(cataId)) {
			responseMap.put(STATUS_CODE, INVALID_REQUEST);// 返回错误请求状态码
			responseMap.put(ERROR_MESSAGE, "商品分类序号必须是数字！");// 返回错误信息
			return responseMap;
		}
		boolean ifDel = resCatagoryService.delResCatagory(Integer.valueOf(cataId));// 删除商品分类
		if (ifDel) {
			resService.delResByCataId(Integer.valueOf(cataId));// 删除分类下所有商品
			responseMap.put(STATUS_CODE, OK);// 返回请求状态码
			responseMap.put(OK_MESSAGE, "删除商品分类成功！");// 返回信息
		} else {
			responseMap.put(STATUS_CODE, INTERNAL_SERVER_ERROR);// 返回请求状态码
			responseMap.put(ERROR_MESSAGE, "删除商品分类失败！");// 返回信息
		}
		return responseMap;
	}

	/**
	 * 修改商品分类
	 * 
	 * @param cataId
	 *            分类Id
	 * @param resCataUpdated
	 *            分类信息
	 * @return
	 */
	@RequestMapping(value = "/api/v1/catagories/{cataId}", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> resCatagoryEdit(@PathVariable String cataId, ResCatagory resCataUpdated) {
		responseMap = new HashMap<String, Object>();
		if (!StringUtils.isNumeric(cataId)) {
			responseMap.put(STATUS_CODE, INVALID_REQUEST);// 返回错误请求状态码
			responseMap.put(ERROR_MESSAGE, "商品分类序号必须是数字！");// 返回错误信息
			return responseMap;
		}
		if (resCataUpdated == null) {
			responseMap.put(STATUS_CODE, INVALID_REQUEST);// 返回错误请求状态码
			responseMap.put(ERROR_MESSAGE, "修改信息未提交！");// 返回错误信息
			return responseMap;
		}
		resCataUpdated.setId(Integer.valueOf(cataId));
		boolean ifUpdated = resCatagoryService.updateResCatagory(resCataUpdated);
		if (ifUpdated) {
			responseMap.put(STATUS_CODE, OK);// 返回请求状态码
			responseMap.put(OK_MESSAGE, " 修改商品分类成功！");// 返回信息
		} else {
			responseMap.put(STATUS_CODE, INTERNAL_SERVER_ERROR);// 返回请求状态码
			responseMap.put(ERROR_MESSAGE, "修改商品分类失败！");// 返回信息
		}
		return responseMap;
	}

	/**
	 * 获取商品分类
	 * 
	 * @return
	 */
	@RequestMapping(value = "/api/v1/catagories/{cataId}", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getResCatagoryByCataId(@PathVariable String cataId) {
		responseMap = new HashMap<String, Object>();
		if (!StringUtils.isNumeric(cataId)) {
			responseMap.put(STATUS_CODE, INVALID_REQUEST);// 返回错误请求状态码
			responseMap.put(ERROR_MESSAGE, "商品分类序号必须是数字！");// 返回错误信息
			return responseMap;
		}
		ResCatagory cata = resCatagoryService.findResCatagoryByCataId(Integer.valueOf(cataId));
		responseMap.put(STATUS_CODE, OK);// 返回请求状态码
		responseMap.put(DATA, cata);
		responseMap.put(OK_MESSAGE, "获取商品分类信息成功！");// 返回信息
		return responseMap;
	}

}
