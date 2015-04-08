package cn.com.secbuy.api;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import cn.com.secbuy.dto.ContactDTO;
import cn.com.secbuy.dto.ResDTO;
import cn.com.secbuy.dto.UserDTO;
import cn.com.secbuy.pojo.Res;
import cn.com.secbuy.service.ResService;
import cn.com.secbuy.service.UserService;
import cn.com.secbuy.util.DateConver;
import cn.com.secbuy.util.Pagination;

/**
 * @ClassName: ResController
 * @Description: TODO(商品API)
 * @author fan
 * @date 2015年3月3日 下午10:48:00
 * @version V1.0
 */
@Controller
public class ResAPI extends Base {
	@Autowired
	private ResService resService;
	@Autowired
	private UserService userService;
	private Map<String, Object> responseMap = null;// 返回json

	/**
	 * 获取商品列表信息
	 * 
	 * @param pageNow
	 *            当前页
	 * @param key
	 *            查询关键字
	 * @return
	 */
	@RequestMapping(value = "/api/v1/reses", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getReses(String pageNow, String key) {
		responseMap = new HashMap<String, Object>();
		if (!StringUtils.isNumeric(pageNow)) {
			responseMap.put(STATUS_CODE, INVALID_REQUEST);// 返回错误请求状态码
			responseMap.put(ERROR_MESSAGE, "页码必须是数字！");// 返回错误信息
			return responseMap;
		}
		long rows = resService.findResRows(key, null, null, PUTSUP);// 查询总记录数
		if (rows != 0) {
			List<ResDTO> reses = resService.findLimitedReses(Long.valueOf(pageNow), Pagination.PAGESIZE, key, null, null, PUTSUP);// 一页数据
			Pagination page = new Pagination(Long.valueOf(pageNow), rows);// 分页信息
			responseMap.put(DATA, reses);
			responseMap.put(PAGE, page);
		} else {
			Pagination page = new Pagination(0, 0);// 分页信息
			responseMap.put(DATA, new ArrayList<ResDTO>());
			responseMap.put(PAGE, page);
		}
		responseMap.put(STATUS_CODE, OK);// 返回请求状态码
		responseMap.put(OK_MESSAGE, "获取商品列表信息成功！");// 返回信息
		return responseMap;
	}

	/**
	 * 获取分类下的商品列表信息
	 * 
	 * @param cataId
	 *            分类序号
	 * @param pageNow
	 *            当前页
	 * @param key
	 *            查询关键字
	 * @return
	 */
	@RequestMapping(value = "/api/v1/reses/cataroy/{cataId}", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getCataReses(@PathVariable String cataId, String pageNow, String key) {
		responseMap = new HashMap<String, Object>();
		if (!StringUtils.isNumeric(cataId)) {
			responseMap.put(STATUS_CODE, INVALID_REQUEST);// 返回错误请求状态码
			responseMap.put(ERROR_MESSAGE, "分类序号必须是数字！");// 返回错误信息
			return responseMap;
		}
		if (!StringUtils.isNumeric(pageNow)) {
			responseMap.put(STATUS_CODE, INVALID_REQUEST);// 返回错误请求状态码
			responseMap.put(ERROR_MESSAGE, "页码必须是数字！");// 返回错误信息
			return responseMap;
		}
		long rows = resService.findResRows(key, Integer.valueOf(cataId), null, PUTSUP);// 查询总记录数
		if (rows != 0) {
			List<ResDTO> reses = resService.findLimitedReses(Long.valueOf(pageNow), Pagination.PAGESIZE, key, Integer.valueOf(cataId), null, PUTSUP);// 一页数据
			Pagination page = new Pagination(Long.valueOf(pageNow), rows);// 分页信息
			responseMap.put(DATA, reses);
			responseMap.put(PAGE, page);
		} else {
			Pagination page = new Pagination(0, 0);// 分页信息
			responseMap.put(DATA, new ArrayList<ResDTO>());
			responseMap.put(PAGE, page);
		}
		responseMap.put(STATUS_CODE, OK);// 返回请求状态码
		responseMap.put(OK_MESSAGE, "获取分类下商品列表信息成功！");// 返回信息
		return responseMap;
	}

	/**
	 * 获取用户下的商品列表信息
	 * 
	 * @param userId
	 *            用户序号
	 * @param pageNow
	 *            当前页
	 * @param key
	 *            查询关键字
	 * @return
	 */
	@RequestMapping(value = "/api/v1/reses/user/{accessToken}", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getUserReses(@PathVariable String accessToken, String pageNow, String key) {
		responseMap = new HashMap<String, Object>();
		if (!StringUtils.isNumeric(pageNow)) {
			responseMap.put(STATUS_CODE, INVALID_REQUEST);// 返回错误请求状态码
			responseMap.put(ERROR_MESSAGE, "页码必须是数字！");// 返回错误信息
			return responseMap;
		}
		UserDTO user = userService.findUserByAccessid(accessToken);// 获取用户信息
		if (user == null || user.getId() == null) {
			responseMap.put(STATUS_CODE, NOT_FOUND);// 返回错误请求状态码
			responseMap.put(ERROR_MESSAGE, "用户记录不存在!");// 返回错误信息
			return responseMap;
		}
		long rows = resService.findResRows(key, null, user.getId(), null);// 查询总记录数
		if (rows != 0) {
			List<ResDTO> reses = resService.findLimitedReses(Long.valueOf(pageNow), Pagination.PAGESIZE, key, null, user.getId(), null);// 一页数据
			Pagination page = new Pagination(Long.valueOf(pageNow), rows);// 分页信息
			responseMap.put(DATA, reses);
			responseMap.put(PAGE, page);
		} else {
			Pagination page = new Pagination(0, 0);// 分页信息
			responseMap.put(DATA, new ArrayList<ResDTO>());
			responseMap.put(PAGE, page);
		}
		responseMap.put(STATUS_CODE, OK);// 返回请求状态码
		responseMap.put(OK_MESSAGE, "获取用户下商品列表信息成功！");// 返回信息
		return responseMap;
	}

	/**
	 * 获取最新的商品信息
	 * 
	 * @return
	 */
	@RequestMapping(value = "/api/v1/reses/newest", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getNewestReses() {
		responseMap = new HashMap<String, Object>();
		List<ResDTO> reses = resService.findNewestReses();// 获取最新的12条商品信息
		responseMap.put(DATA, reses);
		responseMap.put(STATUS_CODE, OK);// 返回请求状态码
		responseMap.put(OK_MESSAGE, "获取最新商品列表信息成功！");// 返回信息
		return responseMap;
	}

	/**
	 * 获取推荐商品
	 * 
	 * @return
	 */
	@RequestMapping(value = "/api/v1/reses/recommend", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getRecommendReses() {
		responseMap = new HashMap<String, Object>();
		List<ResDTO> reses = resService.findDigestReses(RECOMMEND);// 获取12条digest商品信息
		responseMap.put(DATA, reses);
		responseMap.put(STATUS_CODE, OK);// 返回请求状态码
		responseMap.put(OK_MESSAGE, "获取最新商品列表信息成功！");// 返回信息
		return responseMap;
	}

	/**
	 * 获取商品详情
	 * 
	 * @param id
	 *            商品序号
	 * @return
	 */
	@RequestMapping(value = "/api/v1/reses/{resId}", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getRes(@PathVariable String resId) {
		responseMap = new HashMap<String, Object>();
		if (!StringUtils.isNumeric(resId)) {
			responseMap.put(STATUS_CODE, INVALID_REQUEST);// 返回错误请求状态码
			responseMap.put(ERROR_MESSAGE, "商品序号必须是数字！");// 返回错误信息
			return responseMap;
		}
		ResDTO res = resService.findResByID(Integer.valueOf(resId));
		if (res != null && res.getId() != null) {
			responseMap.put(STATUS_CODE, OK);// 返回错误请求状态码
			responseMap.put(DATA, res);// 返回数据
			responseMap.put(OK_MESSAGE, "获取商品信息成功！");// 返回信息
		} else {
			responseMap.put(STATUS_CODE, NOT_FOUND);// 返回请求状态码
			responseMap.put(OK_MESSAGE, "商品信息不存在！");// 返回信息
		}
		return responseMap;
	}

	/**
	 * 获取卖家联系信息
	 * 
	 * @param id
	 *            商品序号
	 * @return
	 */
	@RequestMapping(value = "/api/v1/reses/contact/{resId}", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getSellerContact(@PathVariable String resId) {
		responseMap = new HashMap<String, Object>();
		if (!StringUtils.isNumeric(resId)) {
			responseMap.put(STATUS_CODE, INVALID_REQUEST);// 返回错误请求状态码
			responseMap.put(ERROR_MESSAGE, "商品序号必须是数字！");// 返回错误信息
			return responseMap;
		}
		ContactDTO contact = resService.findSellerContactByID(Integer.valueOf(resId));
		if (contact != null && contact.getContact() != null) {
			responseMap.put(STATUS_CODE, OK);// 返回请求状态码
			responseMap.put(DATA, contact);// 返回数据
			responseMap.put(OK_MESSAGE, "获取联系信息成功！");// 返回信息
		} else {
			responseMap.put(STATUS_CODE, NOT_FOUND);// 返回请求状态码
			responseMap.put(OK_MESSAGE, "联系信息不存在！");// 返回信息
		}
		return responseMap;
	}

	/**
	 * 上传商品图片
	 * 
	 * @param resFile
	 *            文件
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/api/v1/reses/pic/new", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> resPictrueNew(@RequestParam MultipartFile resFile, HttpServletRequest request) {
		responseMap = new HashMap<String, Object>();
		String fileURL = request.getSession().getServletContext().getRealPath(FILEURL);// 获取文件路径
		File filePath = new File(fileURL);
		if (!filePath.exists()) {
			filePath.mkdirs();
		}
		String fileName = resFile.getOriginalFilename();// 获取文件名
		String extName = fileName.substring(fileName.lastIndexOf("."));// 截取文件后缀名
		fileName = UUID.randomUUID().toString().substring(0, 18) + extName;// UUID文件名称,区分文件
		File targetFile = new File(fileURL, fileName);// 创建文件对象
		boolean isUploaded = this.copyResFile(resFile, targetFile);// 上传文件
		if (isUploaded) {
			responseMap.put(STATUS_CODE, OK);// 返回请求状态码
			responseMap.put(DATA, "uploadFiles/" + fileName);// 返回文件路径
			responseMap.put(OK_MESSAGE, "文件上传成功！");// 返回信息
		} else {
			responseMap.put(STATUS_CODE, INTERNAL_SERVER_ERROR);// 返回错误请求状态码
			responseMap.put(ERROR_MESSAGE, "文件上传失败！");// 返回错误信息
		}
		return responseMap;
	}

	/**
	 * 新增商品
	 * 
	 * @param res
	 * @param request
	 *            商品信息
	 * @return
	 */
	@RequestMapping(value = "/api/v1/reses/new", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> newRes(Res res, String accessToken) {
		responseMap = new HashMap<String, Object>();
		if (res == null) {
			responseMap.put(STATUS_CODE, INVALID_REQUEST);// 返回错误请求状态码
			responseMap.put(ERROR_MESSAGE, "商品信息不能为空！");// 返回错误信息
			return responseMap;
		}
		UserDTO user = userService.findUserByAccessid(accessToken);// 获取用户信息
		if (user == null || user.getId() == null) {
			responseMap.put(STATUS_CODE, NOT_FOUND);// 返回错误请求状态码
			responseMap.put(ERROR_MESSAGE, "用户记录不存在!");// 返回错误信息
			return responseMap;
		}
		Date now = new Date();
		res.setName("");
		res.setCreatetime(now);// 创建时间
		res.setUpdatetime(now);// 更新时间
		res.setStatus(WAITING);// 状态
		res.setUserId(user.getId());// 用户Id
		boolean isResPersisted = resService.addRes(res);// 添加数据库
		if (isResPersisted) {
			responseMap.put(STATUS_CODE, OK);// 返回请求状态码
			responseMap.put(OK_MESSAGE, "添加商品成功！");// 返回信息
		} else {
			responseMap.put(STATUS_CODE, INTERNAL_SERVER_ERROR);// 返回请求状态码
			responseMap.put(ERROR_MESSAGE, "添加商品数据失败！");// 返回信息
		}
		return responseMap;
	}

	/**
	 * 上传文件
	 * 
	 * @param resFile
	 *            源文件
	 * @param request
	 *            目标文件
	 * @return
	 */
	private boolean copyResFile(MultipartFile resFile, File targetFile) {
		try {
			FileUtils.copyInputStreamToFile(resFile.getInputStream(), targetFile);// copy文件到指定目录
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 删除商品
	 * 
	 * @param id
	 *            商品序号
	 * @return
	 */
	@RequestMapping(value = "/api/v1/reses/{resId}", method = RequestMethod.DELETE)
	@ResponseBody
	public Map<String, Object> delRes(@PathVariable String resId) {
		responseMap = new HashMap<String, Object>();
		if (!StringUtils.isNumeric(resId)) {
			responseMap.put(STATUS_CODE, INVALID_REQUEST);// 返回错误请求状态码
			responseMap.put(ERROR_MESSAGE, "商品序号必须是数字！");// 返回错误信息
			return responseMap;
		}
		boolean isReal = resService.findRealRes(Integer.valueOf(resId));
		if (!isReal) {
			responseMap.put(STATUS_CODE, INVALID_REQUEST);// 返回错误请求状态码
			responseMap.put(ERROR_MESSAGE, "要删除的商品不存在！");// 返回错误信息
			return responseMap;
		}
		boolean result = resService.delRes(Integer.valueOf(resId));
		if (result) {
			responseMap.put(STATUS_CODE, OK);// 返回请求状态码
			responseMap.put(OK_MESSAGE, "删除商品成功！");// 返回信息
		} else {
			responseMap.put(STATUS_CODE, INTERNAL_SERVER_ERROR);// 返回请求状态码
			responseMap.put(ERROR_MESSAGE, "服务端发生错误,删除功能异常！");// 返回信息
		}
		return responseMap;
	}

	/**
	 * 修改商品信息
	 * 
	 * @param resId
	 *            商品序号
	 * @param resUpdated
	 *            修改后的商品信息
	 * @return
	 */
	@RequestMapping(value = "/api/v1/reses/{resId}", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> modifyRes(@PathVariable String resId, Res resUpdated) {
		responseMap = new HashMap<String, Object>();
		if (!StringUtils.isNumeric(resId)) {
			responseMap.put(STATUS_CODE, INVALID_REQUEST);// 返回错误请求状态码
			responseMap.put(ERROR_MESSAGE, "商品序号必须是数字！");// 返回错误信息
			return responseMap;
		}
		if (resUpdated == null) {
			responseMap.put(STATUS_CODE, INVALID_REQUEST);// 返回错误请求状态码
			responseMap.put(ERROR_MESSAGE, "修改信息未提交！");// 返回错误信息
			return responseMap;
		}
		boolean isReal = resService.findRealRes(Integer.valueOf(resId));
		if (!isReal) {
			responseMap.put(STATUS_CODE, INVALID_REQUEST);// 返回错误请求状态码
			responseMap.put(ERROR_MESSAGE, "要修改的商品不存在！");// 返回错误信息
			return responseMap;
		}
		resUpdated.setId(Integer.valueOf(resId));
		resUpdated.setUpdatetime(new Date());
		boolean result = resService.updateRes(resUpdated);
		if (result) {
			responseMap.put(STATUS_CODE, OK);// 返回请求状态码
			responseMap.put(OK_MESSAGE, "修改商品成功！");// 返回信息
		} else {
			responseMap.put(STATUS_CODE, INTERNAL_SERVER_ERROR);// 返回请求状态码
			responseMap.put(ERROR_MESSAGE, "服务端发生错误,修改功能异常！");// 返回信息
		}
		return responseMap;
	}

	/**
	 * 发布商品
	 * 
	 * @param resId
	 *            商品序号
	 * @return
	 */
	@RequestMapping(value = "/api/v1/reses/{resId}/putsup", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> putsupRes(@PathVariable String resId) {
		responseMap = new HashMap<String, Object>();
		if (!StringUtils.isNumeric(resId)) {
			responseMap.put(STATUS_CODE, INVALID_REQUEST);// 返回错误请求状态码
			responseMap.put(ERROR_MESSAGE, "商品序号必须是数字！");// 返回错误信息
			return responseMap;
		}
		boolean isReal = resService.findRealRes(Integer.valueOf(resId));
		if (!isReal) {
			responseMap.put(STATUS_CODE, INVALID_REQUEST);// 返回错误请求状态码
			responseMap.put(ERROR_MESSAGE, "要修改的商品不存在！");// 返回错误信息
			return responseMap;
		}
		String putstime = DateConver.converDateToYYYYMMDDHHMMSS(new Date());// 转换时间格式
		boolean result = resService.updateResPutstimeAndStatus(Integer.valueOf(resId), putstime, PUTSUP);// 发布
		if (result) {
			responseMap.put(STATUS_CODE, OK);// 返回请求状态码
			responseMap.put(OK_MESSAGE, "商品审核通过,发布商品！");// 返回信息
		} else {
			responseMap.put(STATUS_CODE, INTERNAL_SERVER_ERROR);// 返回请求状态码
			responseMap.put(ERROR_MESSAGE, "服务端发生错误,发布功能异常！");// 返回信息
		}
		return responseMap;
	}

	/**
	 * 下架商品
	 * 
	 * @param resId
	 *            商品序号
	 * @return
	 */
	@RequestMapping(value = "/api/v1/reses/{resId}/putsdown", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> putsDownRes(@PathVariable String resId) {
		responseMap = new HashMap<String, Object>();
		if (!StringUtils.isNumeric(resId)) {
			responseMap.put(STATUS_CODE, INVALID_REQUEST);// 返回错误请求状态码
			responseMap.put(ERROR_MESSAGE, "商品序号必须是数字！");// 返回错误信息
			return responseMap;
		}
		ResDTO res = resService.findResByID(Integer.valueOf(resId));
		if (res == null || PUTSUP != res.getStatus()) {
			responseMap.put(STATUS_CODE, INVALID_REQUEST);// 返回错误请求状态码
			responseMap.put(ERROR_MESSAGE, "该商品不在发布状态中,无法进行下架！");// 返回错误信息
			return responseMap;
		}
		boolean result = resService.updateResStatus(Integer.valueOf(resId), PUTSDOWN);// 发布
		if (result) {
			responseMap.put(STATUS_CODE, OK);// 返回请求状态码
			responseMap.put(OK_MESSAGE, "下架商品成功！");// 返回信息
		} else {
			responseMap.put(STATUS_CODE, INTERNAL_SERVER_ERROR);// 返回请求状态码
			responseMap.put(ERROR_MESSAGE, "服务端发生错误,下架功能异常！");// 返回信息
		}
		return responseMap;
	}

}
