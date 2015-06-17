package cn.com.secbuy.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.stereotype.Repository;

import cn.com.secbuy.dao.BaseDAO;
import cn.com.secbuy.dao.TradeDAO;
import cn.com.secbuy.dto.TradeDTO;
import cn.com.secbuy.pojo.Trade;

/**
 * @ClassName: TradeDAOImpl
 * @Description: TODO(交易DAO接口实现)
 * @author fan
 * @date 2015年3月5日 下午8:38:04
 * @version V1.0
 */
@Repository("TradeDAO")
public class TradeDAOImpl extends BaseDAO implements TradeDAO {

	@Override
	public List<TradeDTO> findLimitedTrades(long pageNow, int pageSize) {
		StringBuffer sql = new StringBuffer("select ");
		sql.append("t.id,t.createtime,t.status,");
		sql.append("r.title as restitle,r.cost,r.putstime,");
		sql.append("s.name as sellername,s.contact as sellercontact,");
		sql.append("b.name as buyername,b.contact as buyercontact" + " ");
		sql.append("from trade t" + " ");
		sql.append("left join res r on t.res=r.id left join user s on t.seller=s.id left join user b on t.buyer=b.id where 1=1" + " ");
		sql.append("order by t.createtime desc" + " ");
		sql.append("limit ?,? ");
		Object[] args = new Object[] { (pageNow - 1) * pageSize, pageSize };
		final List<TradeDTO> list = new ArrayList<TradeDTO>();
		this.jdbcTemplate.query(sql.toString(), args, new RowCallbackHandler() {

			@Override
			public void processRow(ResultSet rs) throws SQLException {
				TradeDTO trade = new TradeDTO();
				trade.setBuyerContact(rs.getString("buyercontact"));
				trade.setBuyerName(rs.getString("buyername"));
				trade.setCost(rs.getDouble("cost"));
				trade.setCreatetime(rs.getDate("createtime") + " " + rs.getTime("createtime"));
				trade.setId(rs.getInt("id"));
				trade.setSellerName(rs.getString("sellername"));
				trade.setSerllerContact(rs.getString("sellercontact"));
				trade.setStatus(rs.getInt("status"));
				trade.setTitle(rs.getString("restitle"));
				list.add(trade);
			}
		});
		return list;
	}

	@Override
	public long findTradeRows() {
		String sql = "select count(t.id) from trade t ";
		return this.jdbcTemplate.queryForLong(sql);
	}

	@Override
	public List<TradeDTO> findLimitedUserTrades(long pageNow, int pageSize, Integer userId, int flag) {
		StringBuffer sql = new StringBuffer("select ");
		sql.append("t.id,t.createtime,t.status,");
		sql.append("r.title as restitle,r.cost,r.putstime,");
		sql.append("s.name as sellername,s.contact as sellercontact,");
		sql.append("b.name as buyername,b.contact as buyercontact" + " ");
		sql.append("from trade t" + " ");
		sql.append("left join res r on t.res=r.id left join user s on t.seller=s.id left join user b on t.buyer=b.id where 1=1" + " ");
		if (flag == 1) {
			sql.append("and t.seller=?" + " "); // 卖家
		}
		if (flag == 2) {
			sql.append("and t.buyer=?" + " ");// 买家
		}
		sql.append("order by t.createtime desc" + " ");
		sql.append("limit ?,? ");
		Object[] args = new Object[] { userId, (pageNow - 1) * pageSize, pageSize };
		final List<TradeDTO> list = new ArrayList<TradeDTO>();
		this.jdbcTemplate.query(sql.toString(), args, new RowCallbackHandler() {

			@Override
			public void processRow(ResultSet rs) throws SQLException {
				TradeDTO trade = new TradeDTO();
				trade.setBuyerContact(rs.getString("buyercontact"));
				trade.setBuyerName(rs.getString("buyername"));
				trade.setCost(rs.getDouble("cost"));
				trade.setCreatetime(rs.getDate("createtime") + " " + rs.getTime("createtime"));
				trade.setId(rs.getInt("id"));
				trade.setSellerName(rs.getString("sellername"));
				trade.setSerllerContact(rs.getString("sellercontact"));
				trade.setStatus(rs.getInt("status"));
				trade.setTitle(rs.getString("restitle"));
				list.add(trade);
			}
		});
		return list;
	}

	@Override
	public long findTradeRows(Integer userId, int flag) {
		StringBuffer sql = new StringBuffer("select count(t.id) from trade t where 1=1 ");
		if (flag == 1) {
			sql.append("and t.seller=?" + " ");
		}
		if (flag == 2) {
			sql.append("and t.buyer=?" + " ");
		}
		Object[] args = new Object[] { userId };
		return this.jdbcTemplate.queryForLong(sql.toString(), args);
	}

	@Override
	public Trade findTradeByID(Integer id) {
		String sql = "select t.id,t.res,t.seller,t.buyer,t.status from trade t where t.id=?";
		final Trade trade = new Trade();
		Object[] args = new Object[] { id };
		this.jdbcTemplate.query(sql.toString(), args, new RowCallbackHandler() {

			@Override
			public void processRow(ResultSet rs) throws SQLException {
				trade.setBuyer(rs.getInt("buyer"));
				trade.setId(rs.getInt("id"));
				trade.setRes(rs.getInt("res"));
				trade.setSeller(rs.getInt("seller"));
				trade.setStatus(rs.getInt("status"));
			}
		});
		return trade;
	}

	@Override
	public boolean findRealTrade(Integer id) {
		String sql = "select count(t.id) from trade t where t.id=?";
		Object[] args = new Object[] { id };
		int result = this.jdbcTemplate.queryForInt(sql, args);
		return result == 1;
	}

	@Override
	public boolean addTrade(Trade trade) {
		String sql = "insert into trade(res,seller,buyer,createtime,status) values(?,?,?,?,?)";
		Object[] args = new Object[] { trade.getRes(), trade.getSeller(), trade.getBuyer(), trade.getCreatetime(), trade.getStatus() };
		int result = this.jdbcTemplate.update(sql, args);
		return result == 1;
	}

	@Override
	public boolean delTrade(Integer id) {
		String sql = "delete from trade where id=?";
		Object[] args = new Object[] { id };
		int result = this.jdbcTemplate.update(sql, args);
		return result == 1;
	}

	@Override
	public boolean batchDelTrades(Integer[] ids) {
		String sql = "delete from trade t where t.id in (?)";
		Object[] args = new Object[] { ids };
		int result = this.jdbcTemplate.update(sql, args);
		return result == ids.length;
	}

	@Override
	public boolean updateTradeStatusByTradeID(Integer tradeId, int status) {
		String sql = "update trade t set t.status=? where t.id=?";
		Object[] args = new Object[] { status, tradeId };
		int result = this.jdbcTemplate.update(sql, args);
		return result == 1;
	}
}
