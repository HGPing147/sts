package cn.com.secbuy.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.stereotype.Repository;

import cn.com.secbuy.dao.BaseDAO;
import cn.com.secbuy.dao.ResCatagoryDAO;
import cn.com.secbuy.pojo.ResCatagory;

/**
 * @ClassName: ResCatagoryDAOImpl
 * @Description: TODO(商品分类数据访问接口实现)
 * @author fan
 * @date 2015年4月4日 下午7:03:52
 * @version V1.0
 */
@Repository("ResCatagoryDAO")
public class ResCatagoryDAOImpl extends BaseDAO implements ResCatagoryDAO {

	@Override
	public List<ResCatagory> findResCatagory() {
		String sql = "select c.id,c.name from rescatagory c";
		final List<ResCatagory> list = new ArrayList<ResCatagory>(10);
		this.jdbcTemplate.query(sql, new RowCallbackHandler() {

			@Override
			public void processRow(ResultSet rs) throws SQLException {
				ResCatagory cata = new ResCatagory();
				cata.setId(rs.getInt("id"));
				cata.setName(rs.getString("name"));
				list.add(cata);
			}

		});
		return list;
	}

	@Override
	public boolean addReCatagory(ResCatagory resCata) {
		String sql = "insert into rescatagory(name) values(?)";
		Object[] args = new Object[] { resCata.getName() };
		int result = this.jdbcTemplate.update(sql, args);
		return result == 1;
	}

	@Override
	public boolean delResCatagory(Integer resCataId) {
		String sql = "delete from rescatagory  where id=?";
		Object[] args = new Object[] { resCataId };
		int result = this.jdbcTemplate.update(sql, args);
		return result == 1;
	}

	@Override
	public boolean updateResCatagory(ResCatagory resCata) {
		String sql = "update rescatagory c set c.name=? where c.id=?";
		Object[] args = new Object[] { resCata.getName(), resCata.getId() };
		int result = this.jdbcTemplate.update(sql, args);
		return result == 1;
	}

	@Override
	public ResCatagory findResCatagoryByCataId(Integer cataId) {
		String sql = "select c.id,c.name from rescatagory c where c.id=?";
		final ResCatagory cata = new ResCatagory();
		Integer[] args = { cataId };
		this.jdbcTemplate.query(sql, args, new RowCallbackHandler() {
			@Override
			public void processRow(ResultSet rs) throws SQLException {
				cata.setId(rs.getInt("id"));
				cata.setName(rs.getString("name"));
			}
		});
		return cata;
	}

}
