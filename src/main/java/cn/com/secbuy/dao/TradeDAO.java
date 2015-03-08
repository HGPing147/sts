package cn.com.secbuy.dao;

import java.util.List;

import cn.com.secbuy.dto.TradeDTO;
import cn.com.secbuy.pojo.Trade;

/**
 * @ClassName: TradeDAO
 * @Description: TODO(交易DAO接口，定义交易领域对象与数据库操作)
 * @author fan
 * @date 2015年3月5日 上午12:04:20
 * @version V1.0
 */
public interface TradeDAO {
	/**
	 * 查询交易记录(指定记录数)
	 * 
	 * @param pageNow
	 *            当前页
	 * @param pageSize
	 *            每页大小
	 * @return
	 */
	public List<TradeDTO> findLimitedTrades(long pageNow, int pageSize);

	/**
	 * 查询交易总记录数
	 * 
	 * @return
	 */
	public long findTradeRows();

	/**
	 * 查询用户下交易记录(指定记录数)
	 * 
	 * @param pageNow
	 *            当前页
	 * @param pageSize
	 *            每页大小
	 * @param flag
	 *            用户标识1卖家2买家
	 * @return
	 */
	public List<TradeDTO> findLimitedUserTrades(long pageNow, int pageSize, Integer userId, int flag);

	/**
	 * 查询用户下交易总记录数
	 * 
	 * @param userId
	 *            用户序号
	 * @param flag
	 *            用户卖家买家标识
	 * @return
	 */
	public long findTradeRows(Integer userId, int flag);

	/**
	 * 查询该序号下的交易记录数
	 * 
	 * @param id
	 *            交易序号
	 * @return
	 */
	public boolean findRealTrade(Integer id);

	/**
	 * 查询指定序号的交易记录
	 * 
	 * @param id
	 *            交易序号
	 * @return
	 */
	public Trade findTradeByID(Integer id);

	/**
	 * 添加交易记录
	 * 
	 * @param trade
	 *            交易信息
	 * @return
	 */
	public boolean addTrade(Trade trade);

	/**
	 * 删除交易记录
	 * 
	 * @param id
	 *            序号
	 * @return
	 */
	public boolean delTrade(Integer id);

	/**
	 * 批量删除交易记录
	 * 
	 * @param ids
	 *            序号数组
	 * @return
	 */
	public boolean batchDelTrades(Integer[] ids);

	/**
	 * 修改交易状态
	 * 
	 * @param resId
	 * @param status
	 * @return
	 */
	public boolean updateTradeStatusByResID(Integer resId, int status);

}
