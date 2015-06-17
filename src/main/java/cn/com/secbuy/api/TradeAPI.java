package cn.com.secbuy.api;

import java.util.ArrayList;
import java.util.Date;
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

import cn.com.secbuy.dto.ResDTO;
import cn.com.secbuy.dto.TradeDTO;
import cn.com.secbuy.dto.UserDTO;
import cn.com.secbuy.pojo.Trade;
import cn.com.secbuy.service.ResService;
import cn.com.secbuy.service.TradeService;
import cn.com.secbuy.service.UserService;
import cn.com.secbuy.util.Pagination;

/**
 * @ClassName: TradeController
 * @Description: TODO(交易API)
 * @author fan
 * @date 2015年3月7日 上午11:33:31
 * @version V1.0
 */
@Controller
public class TradeAPI extends Base {
	@Autowired
	private TradeService tradeService;
	@Autowired
	private ResService resService;
	@Autowired
	private UserService userService;
	private Map<String, Object> responseMap = null;// 返回json

	/**
	 * 获取所有交易记录
	 * 
	 * @param pageNow
	 *            当前页码
	 * @return
	 */
	@RequestMapping(value = "/api/v1/trades", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> showAllTrades(String pageNow) {
		responseMap = new HashMap<String, Object>();
		if (!StringUtils.isNumeric(pageNow)) {
			responseMap.put(STATUS_CODE, INVALID_REQUEST);// 返回错误请求状态码
			responseMap.put(ERROR_MESSAGE, "页码必须是数字！");// 返回错误信息
			return responseMap;
		}
		long rows = tradeService.findTradeRows();// 获取数据库总记录数
		if (rows > 0) {
			List<TradeDTO> trades = tradeService.findLimitedTrades(Long.valueOf(pageNow), Pagination.PAGESIZE);
			System.out.println(trades.size());
			Pagination page = new Pagination(Long.valueOf(pageNow), rows);// 分页信息
			responseMap.put(DATA, trades);
			responseMap.put(PAGE, page);
		} else {
			Pagination page = new Pagination(0, 0);// 分页信息
			responseMap.put(DATA, new ArrayList<ResDTO>());
			responseMap.put(PAGE, page);
		}
		responseMap.put(STATUS_CODE, OK);// 返回请求状态码
		responseMap.put(OK_MESSAGE, "获取交易列表信息成功！");// 返回信息
		return responseMap;
	}

	/**
	 * 获取卖出交易记录
	 * 
	 * @param userId
	 *            用户序号
	 * @param pageNow
	 *            当前页码
	 * @return
	 */
	@RequestMapping(value = "/api/v1/trades/sell/{accessToken}", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> showSellTrades(@PathVariable String accessToken, String pageNow) {
		responseMap = new HashMap<String, Object>();
		if (!StringUtils.isNumeric(pageNow)) {
			responseMap.put(STATUS_CODE, INVALID_REQUEST);// 返回错误请求状态码
			responseMap.put(ERROR_MESSAGE, "页码必须是数字！");// 返回错误信息
			return responseMap;
		}
		UserDTO user = userService.findUserByAccessid(accessToken);
		if (user == null || user.getId() == null) {
			responseMap.put(STATUS_CODE, INVALID_REQUEST);// 返回错误请求状态码
			responseMap.put(ERROR_MESSAGE, "用户不存在！");// 返回错误信息
			return responseMap;
		}
		long rows = tradeService.findTradeRows(user.getId(), SELLER);// 获取卖出交易总记录数
		if (rows != 0) {
			List<TradeDTO> trades = tradeService.findLimitedUserTrades(Long.valueOf(pageNow), Pagination.PAGESIZE, user.getId(), SELLER); // 获取卖出交易数据
			Pagination page = new Pagination(Long.valueOf(pageNow), rows);// 分页信息
			responseMap.put(DATA, trades);
			responseMap.put(PAGE, page);
		} else {
			Pagination page = new Pagination(0, 0);// 分页信息
			responseMap.put(DATA, new ArrayList<ResDTO>());
			responseMap.put(PAGE, page);
		}
		responseMap.put(STATUS_CODE, OK);// 返回请求状态码
		responseMap.put(OK_MESSAGE, "获取交易列表信息成功！");// 返回信息
		return responseMap;
	}

	/**
	 * 获取买下交易记录
	 * 
	 * @param userId
	 * @param pageNow
	 * @return
	 */
	@RequestMapping(value = "/api/v1/trades/buy/{accessToken}", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> showBuyTrades(@PathVariable String accessToken, String pageNow) {
		responseMap = new HashMap<String, Object>();
		if (!StringUtils.isNumeric(pageNow)) {
			responseMap.put(STATUS_CODE, INVALID_REQUEST);// 返回错误请求状态码
			responseMap.put(ERROR_MESSAGE, "页码必须是数字！");// 返回错误信息
			return responseMap;
		}
		UserDTO user = userService.findUserByAccessid(accessToken);
		if (user == null || user.getId() == null) {
			responseMap.put(STATUS_CODE, INVALID_REQUEST);// 返回错误请求状态码
			responseMap.put(ERROR_MESSAGE, "用户不存在！");// 返回错误信息
			return responseMap;
		}
		long rows = tradeService.findTradeRows(user.getId(), BUYER);// 获取买下交易总记录数
		if (rows != 0) {
			List<TradeDTO> trades = tradeService.findLimitedUserTrades(Long.valueOf(pageNow), Pagination.PAGESIZE, user.getId(), BUYER); // 获取买下交易数据
			Pagination page = new Pagination(Long.valueOf(pageNow), rows);// 分页信息
			responseMap.put(DATA, trades);
			responseMap.put(PAGE, page);
		} else {
			Pagination page = new Pagination(0, 0);// 分页信息
			responseMap.put(DATA, new ArrayList<ResDTO>());
			responseMap.put(PAGE, page);
		}
		responseMap.put(STATUS_CODE, OK);// 返回请求状态码
		responseMap.put(OK_MESSAGE, "获取交易列表信息成功！");// 返回信息
		return responseMap;
	}

	/**
	 * 删除已经交易的交易记录
	 * 
	 * @param tradeId
	 * @return
	 */
	@RequestMapping(value = "/api/v1/trades/{tradeId}", method = RequestMethod.DELETE)
	@ResponseBody
	public Map<String, Object> delTrade(@PathVariable String tradeId) {
		responseMap = new HashMap<String, Object>();
		if (!StringUtils.isNumeric(tradeId)) {
			responseMap.put(STATUS_CODE, INVALID_REQUEST);// 返回错误请求状态码
			responseMap.put(ERROR_MESSAGE, "交易序号必须为数字！");// 返回错误信息
			return responseMap;
		}
		boolean isReal = tradeService.findRealTrade(Integer.valueOf(tradeId));// 查询数据库是否存在该序号下的交易记录
		if (!isReal) {
			responseMap.put(STATUS_CODE, NOT_FOUND);// 返回错误请求状态码
			responseMap.put(ERROR_MESSAGE, "交易记录不存在！");// 返回错误信息
			return responseMap;
		}
		boolean result = tradeService.delTrade(Integer.valueOf(tradeId));// 数据库中删除记录
		if (result) {
			responseMap.put(STATUS_CODE, OK);// 返回请求状态码
			responseMap.put(OK_MESSAGE, "删除交易记录成功！");// 返回信息
		} else {
			responseMap.put(STATUS_CODE, INTERNAL_SERVER_ERROR);// 返回请求状态码
			responseMap.put(ERROR_MESSAGE, "服务端发生错误,删除功能异常！");// 返回信息
		}
		return responseMap;
	}

	/**
	 * 交易(正在交易中)
	 * 
	 * @param trade
	 *            交易信息
	 * @return
	 */
	@RequestMapping(value = "/api/v1/trades/trading", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> trading(Trade trade, String accessToken) {
		responseMap = new HashMap<String, Object>();
		if (trade == null) {
			responseMap.put(STATUS_CODE, INVALID_REQUEST);// 返回错误请求状态码
			responseMap.put(ERROR_MESSAGE, "交易信息不能为空！");// 返回错误信息
			return responseMap;
		}
		ResDTO res = resService.findResByID(trade.getRes());// 获取商品信息
		// 判断商品是否存在
		if (res.getId() == null) {
			responseMap.put(STATUS_CODE, NOT_FOUND);// 返回错误请求状态码
			responseMap.put(ERROR_MESSAGE, "交易商品不存在！");// 返回错误信息
			return responseMap;
		}
		UserDTO user = userService.findUserByAccessid(accessToken);// 获取用户信息
		if (user == null || user.getId() == null || user.getId() == res.getUserId()) {
			responseMap.put(STATUS_CODE, INVALID_REQUEST);// 返回错误请求状态码
			responseMap.put(ERROR_MESSAGE, "你不能购置此商品!请检测是否登陆或者是否是自己发布的商品!");// 返回错误信息
			return responseMap;
		}
		// 判断商品是否处于可交易状态
		if (res.getStatus() != PUTSUP) {
			responseMap.put(STATUS_CODE, INVALID_REQUEST);// 返回错误请求状态码
			responseMap.put(ERROR_MESSAGE, "交易商品未在出售状态!");// 返回错误信息
			return responseMap;
		}
		trade.setBuyer(user.getId());
		trade.setCreatetime(new Date());// 设置交易时间
		trade.setStatus(TRADING);// 设置交易中状态
		boolean resultTrading1 = tradeService.addTrade(trade);
		boolean resultTrading2 = resService.updateResStatus(res.getId(), TRADING);// 修改商品状态为正在交易
		if (resultTrading1 && resultTrading2) {
			responseMap.put(STATUS_CODE, OK);// 返回请求状态码
			responseMap.put(OK_MESSAGE, "商品正在处于交易中!");// 返回信息
		} else {
			responseMap.put(STATUS_CODE, INTERNAL_SERVER_ERROR);// 返回请求状态码
			responseMap.put(ERROR_MESSAGE, "服务端发生错误,添加功能异常！");// 返回信息
		}
		return responseMap;
	}

	/**
	 * 交易(交易成功)
	 * 
	 * @param tradeId
	 * @return
	 */
	@RequestMapping(value = "/api/v1/trades/{tradeId}/traded", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> tradingSuccess(@PathVariable String tradeId) {
		responseMap = new HashMap<String, Object>();
		if (!StringUtils.isNumeric(tradeId)) {
			responseMap.put(STATUS_CODE, INVALID_REQUEST);// 返回错误请求状态码
			responseMap.put(ERROR_MESSAGE, "交易序号必须为数字！");// 返回错误信息
			return responseMap;
		}
		Trade trade = tradeService.findTradeByID(Integer.valueOf(tradeId));// 查询数据库该序号下的交易记录
		if (trade.getId() == null) {
			responseMap.put(STATUS_CODE, NOT_FOUND);// 返回错误请求状态码
			responseMap.put(ERROR_MESSAGE, "交易记录不存在！");// 返回错误信息
			return responseMap;
		}
		if (trade.getStatus() != TRADING) {
			responseMap.put(STATUS_CODE, NOT_FOUND);// 返回错误请求状态码
			responseMap.put(ERROR_MESSAGE, "不在交易中！");// 返回错误信息
			return responseMap;
		}
		boolean resultTraded = tradeService.updateTradeStatusByTradeID(Integer.valueOf(tradeId), TRADED);// 交易成功
		boolean resultPutsdown = resService.updateResStatus(trade.getRes(), PUTSDOWN);// 下架商品
		System.out.println("resultTraded :" + resultTraded);
		System.out.println("resultPutsdown :" + resultPutsdown);
		if (resultTraded && resultPutsdown) {
			responseMap.put(STATUS_CODE, OK);// 返回请求状态码
			responseMap.put(OK_MESSAGE, "交易成功！");// 返回信息
		} else {
			responseMap.put(STATUS_CODE, INTERNAL_SERVER_ERROR);// 返回请求状态码
			responseMap.put(ERROR_MESSAGE, "服务端发生错误,交易功能异常！");// 返回信息
		}
		return responseMap;
	}

	/**
	 * 交易(交易失败)
	 * 
	 * @param tradeId
	 * @return
	 */
	@RequestMapping(value = "/api/v1/trades/{tradeId}/tradingBroken", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> tradingFail(@PathVariable String tradeId) {
		responseMap = new HashMap<String, Object>();
		if (!StringUtils.isNumeric(tradeId)) {
			responseMap.put(STATUS_CODE, INVALID_REQUEST);// 返回错误请求状态码
			responseMap.put(ERROR_MESSAGE, "交易序号必须为数字！");// 返回错误信息
			return responseMap;
		}
		Trade trade = tradeService.findTradeByID(Integer.valueOf(tradeId));// 查询数据库该序号下的交易记录
		if (trade.getId() == null) {
			responseMap.put(STATUS_CODE, NOT_FOUND);// 返回错误请求状态码
			responseMap.put(ERROR_MESSAGE, "交易记录不存在！");// 返回错误信息
			return responseMap;
		}
		if (trade.getStatus() != TRADING) {
			responseMap.put(STATUS_CODE, NOT_FOUND);// 返回错误请求状态码
			responseMap.put(ERROR_MESSAGE, "不在交易中！");// 返回错误信息
			return responseMap;
		}
		boolean resultTradingBroken = tradeService.updateTradeStatusByTradeID(Integer.valueOf(tradeId), TRADINGBROKEN);// 交易失败
		boolean resultPutsup = resService.updateResStatus(trade.getRes(), PUTSUP);// 重新发布商品
		if (resultTradingBroken && resultPutsup) {
			responseMap.put(STATUS_CODE, OK);// 返回请求状态码
			responseMap.put(OK_MESSAGE, "交易失败！");// 返回信息
		} else {
			responseMap.put(STATUS_CODE, INTERNAL_SERVER_ERROR);// 返回请求状态码
			responseMap.put(ERROR_MESSAGE, "服务端发生错误,交易功能异常！");// 返回信息
		}
		return responseMap;
	}
}
