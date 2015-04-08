package cn.com.secbuy.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.stereotype.Repository;

import cn.com.secbuy.dao.BaseDAO;
import cn.com.secbuy.dao.ResDAO;
import cn.com.secbuy.dto.ResDTO;
import cn.com.secbuy.dto.ContactDTO;
import cn.com.secbuy.pojo.Res;

/**
 * @ClassName: ResDAOImpl
 * @Description: TODO(商品DAO接口实现操作底层细节)
 * @author fan
 * @date 2015年3月3日 下午11:43:26
 * @version V1.0
 */
@Repository("ResDAO")
public class ResDAOImpl extends BaseDAO implements ResDAO {

	@Override
	public List<ResDTO> findLimitedReses(long pageNow, int pageSize, String key, Integer cataId, Integer userId, Integer status) {
		StringBuffer sql = new StringBuffer(
				"select r.id,r.title,r.name,r.resimageurl,r.cost,r.createtime,r.putstime,r.digest,r.status,r.userid,c.name as cataname from res r ");
		sql.append("left join rescatagory c on r.cataid=c.id  where 1=1 ");
		if (status != null) {
			sql.append("and status=" + status + " ");
		}
		if (!StringUtils.isEmpty(key)) {
			sql.append("and r.name like '%" + key + "%'" + " ");
		}
		if (cataId != null) {
			sql.append("and r.cataid=" + cataId + " ");
		}
		if (userId != null) {
			sql.append("and r.userid=" + userId + " ");
		}
		sql.append("order by r.createtime desc ");
		sql.append("limit ?,?");
		long offset = (pageNow - 1) * pageSize;
		int limit = pageSize;
		Object[] args = new Object[] { offset, limit };
		final List<ResDTO> list = new ArrayList<ResDTO>();
		this.jdbcTemplate.query(sql.toString(), args, new RowCallbackHandler() {

			@Override
			public void processRow(ResultSet rs) throws SQLException {
				ResDTO res = new ResDTO();
				res.setCataName(rs.getString("cataname"));
				res.setCost(rs.getDouble("cost"));
				res.setId(rs.getInt("id"));
				res.setName(rs.getString("name"));
				res.setTitle(rs.getString("title"));
				res.setCreatetime(rs.getString("createtime"));
				res.setPutstime(rs.getString("putstime"));
				res.setDigest(rs.getInt("digest"));
				res.setResImageUrl(rs.getString("resimageurl"));
				res.setStatus(rs.getInt("status"));
				res.setUserId(rs.getInt("userid"));
				list.add(res);
			}
		});
		return list;
	}

	@Override
	public long findResRows(String key, Integer cataId, Integer userId, Integer status) {
		StringBuffer sql = new StringBuffer("select count(r.id) from res r where 1=1 ");
		if (status != null) {
			sql.append("and status=" + status + " ");
		}
		if (!StringUtils.isEmpty(key)) {
			sql.append("and r.name like '%" + key + "%'" + " ");
		}
		if (cataId != null) {
			sql.append("and r.cataid=" + cataId + " ");
		}
		if (userId != null) {
			sql.append("and r.userid=" + userId + " ");
		}
		return this.jdbcTemplate.queryForLong(sql.toString());
	}

	@Override
	public List<ResDTO> findLimitedCataReses(long pageNow, int pageSize, String key, Integer cataId, Integer status) {
		StringBuffer sql = new StringBuffer(
				"select r.id,r.name,r.resimageurl,r.cost,r.putstime,r.digest,r.status,r.userid,c.name as cataname from res r ");
		sql.append("left join rescatagory c on r.cataid=c.id  where 1=1 ");
		if (status != null) {
			sql.append("and status=" + status + " ");
		}
		if (!StringUtils.isEmpty(key)) {
			sql.append("and r.name like '%" + key + "%'" + " ");
		}
		if (cataId != null) {
			sql.append("and r.cataid=" + cataId + " ");
		}
		sql.append("order by r.createtime desc ");
		sql.append("limit ?,?");
		long offset = (pageNow - 1) * pageSize;
		int limit = pageSize;
		Object[] args = new Object[] { offset, limit };
		final List<ResDTO> list = new ArrayList<ResDTO>();
		this.jdbcTemplate.query(sql.toString(), args, new RowCallbackHandler() {

			@Override
			public void processRow(ResultSet rs) throws SQLException {
				ResDTO res = new ResDTO();
				res.setCataName(rs.getString("cataname"));
				res.setCost(rs.getDouble("cost"));
				res.setId(rs.getInt("id"));
				res.setName(rs.getString("name"));
				res.setPutstime(rs.getString("putstime"));
				res.setDigest(rs.getInt("digest"));
				res.setResImageUrl(rs.getString("resimageurl"));
				res.setStatus(rs.getInt("status"));
				res.setUserId(rs.getInt("userid"));
				list.add(res);
			}
		});
		return list;
	}

	@Override
	public List<ResDTO> findLimitedUserReses(long pageNow, int pageSize, String key, Integer userId, Integer status) {
		StringBuffer sql = new StringBuffer(
				"select r.id,r.name,r.resimageurl,r.cost,r.putstime,r.digest,r.status,r.userid,c.name as cataname from res r ");
		sql.append("left join rescatagory c on r.cataid=c.id  where 1=1 ");
		if (status != null) {
			sql.append("and status=" + status + " ");
		}
		if (!StringUtils.isEmpty(key)) {
			sql.append("and r.name like '%" + key + "%'" + " ");
		}
		if (userId != null) {
			sql.append("and r.userid=" + userId + " ");
		}
		sql.append("order by r.createtime desc ");
		sql.append("limit ?,?");
		long offset = (pageNow - 1) * pageSize;
		int limit = pageSize;
		Object[] args = new Object[] { offset, limit };
		final List<ResDTO> list = new ArrayList<ResDTO>();
		this.jdbcTemplate.query(sql.toString(), args, new RowCallbackHandler() {

			@Override
			public void processRow(ResultSet rs) throws SQLException {
				ResDTO res = new ResDTO();
				res.setCataName(rs.getString("cataname"));
				res.setCost(rs.getDouble("cost"));
				res.setId(rs.getInt("id"));
				res.setName(rs.getString("name"));
				res.setPutstime(rs.getString("putstime"));
				res.setDigest(rs.getInt("digest"));
				res.setResImageUrl(rs.getString("resimageurl"));
				res.setStatus(rs.getInt("status"));
				res.setUserId(rs.getInt("userid"));
				list.add(res);
			}

		});
		return list;
	}

	@Override
	public List<ResDTO> findNewestReses() {
		String sql = "select r.id,r.title,r.name,r.resimageurl,r.cost,r.putstime,r.digest,r.status,r.userid,c.name as cataname from res r "
				+ "left join rescatagory c on r.cataid=c.id  where 1=1 and r.status=2 order by r.putstime desc limit 0,12";
		final List<ResDTO> list = new ArrayList<ResDTO>();
		this.jdbcTemplate.query(sql, new RowCallbackHandler() {

			@Override
			public void processRow(ResultSet rs) throws SQLException {
				ResDTO res = new ResDTO();
				res.setCataName(rs.getString("cataname"));
				res.setCost(rs.getDouble("cost"));
				res.setId(rs.getInt("id"));
				res.setTitle(rs.getString("title"));
				res.setName(rs.getString("name"));
				res.setPutstime(rs.getString("putstime"));
				res.setDigest(rs.getInt("digest"));
				res.setResImageUrl(rs.getString("resimageurl"));
				res.setStatus(rs.getInt("status"));
				res.setUserId(rs.getInt("userid"));
				list.add(res);
			}
		});
		return list;
	}

	@Override
	public List<ResDTO> findDigestReses(int digest) {
		String sql = "select r.id,r.name,r.resimageurl,r.cost,r.putstime,r.digest,r.status,r.userid,c.name as cataname from res r "
				+ "left join rescatagory c on r.cataid=c.id  where 1=1 and r.status=2 and r.digest=?  limit 0,12";
		final List<ResDTO> list = new ArrayList<ResDTO>();
		Integer[] args = { digest };
		this.jdbcTemplate.query(sql, args, new RowCallbackHandler() {

			@Override
			public void processRow(ResultSet rs) throws SQLException {
				ResDTO res = new ResDTO();
				res.setCataName(rs.getString("cataname"));
				res.setCost(rs.getDouble("cost"));
				res.setId(rs.getInt("id"));
				res.setName(rs.getString("name"));
				res.setPutstime(rs.getString("putstime"));
				res.setDigest(rs.getInt("digest"));
				res.setResImageUrl(rs.getString("resimageurl"));
				res.setStatus(rs.getInt("status"));
				res.setUserId(rs.getInt("userid"));
				list.add(res);
			}
		});
		return list;
	}

	@Override
	public ResDTO findResByID(Integer id) {
		String sql = "select r.id,r.name,r.title,r.description,r.resimageurl,r.cost,r.putstime,r.digest,r.status,r.userid,c.name as cataname from res r "
				+ "left join rescatagory c on r.cataid=c.id  where 1=1 and r.id=?";
		Object[] args = new Object[] { id };
		final ResDTO res = new ResDTO();
		this.jdbcTemplate.query(sql, args, new RowCallbackHandler() {

			@Override
			public void processRow(ResultSet rs) throws SQLException {
				res.setCataName(rs.getString("cataname"));
				res.setCost(rs.getDouble("cost"));
				res.setId(rs.getInt("id"));
				res.setName(rs.getString("name"));
				res.setTitle(rs.getString("title"));
				res.setResDesc(rs.getString("description"));
				res.setPutstime(rs.getString("putstime"));
				res.setDigest(rs.getInt("digest"));
				res.setResImageUrl(rs.getString("resimageurl"));
				res.setStatus(rs.getInt("status"));
				res.setUserId(rs.getInt("userid"));
			}
		});
		return res;
	}

	@Override
	public ContactDTO findSellerContactByID(Integer id) {
		String sql = "select u.contact,u.name from res r left join user u on r.userid=u.id where 1=1 and r.id=?";
		final ContactDTO contact = new ContactDTO();
		Object[] args = new Object[] { id };
		this.jdbcTemplate.query(sql, args, new RowCallbackHandler() {

			@Override
			public void processRow(ResultSet rs) throws SQLException {
				contact.setContact(rs.getString("contact"));
				contact.setSellerName(rs.getString("name"));
			}
		});
		return contact;
	}

	@Override
	public boolean addRes(Res res) {
		String sql = "insert into res(title,name,description,resimageurl,cost,createtime,status,cataid,userid) values(?,?,?,?,?,?,?,?,?)";
		Object[] args = new Object[] { res.getTitle(), res.getName(), res.getDescription(), res.getResImageUrl(), res.getCost(), res.getCreatetime(),
				res.getStatus(), res.getCataId(), res.getUserId() };
		int result = this.jdbcTemplate.update(sql, args);
		return result == 1;
	}

	@Override
	public boolean findRealRes(Integer id) {
		String sql = "select count(r.id) from res where r.id=?";
		Object[] args = new Object[] { id };
		int result = this.jdbcTemplate.queryForInt(sql, args);
		return result == 1;
	}

	@Override
	public boolean delRes(Integer id) {
		String sql = "delete from res r where r.id=?";
		Object[] args = new Object[] { id };
		int result = this.jdbcTemplate.update(sql, args);
		return result == 1;
	}

	@Override
	public boolean delResByCataId(Integer cataId) {
		String sql = "delete from res r where r.cataid=?";
		Object[] args = new Object[] { cataId };
		int result = this.jdbcTemplate.update(sql, args);
		return result > 0;
	}

	@Override
	public boolean batchDelReses(Integer[] ids) {
		String sql = "delete from res r where r.id in (?)";
		Object[] args = new Object[] { ids };
		int result = this.jdbcTemplate.update(sql, args);
		return result == ids.length;
	}

	@Override
	public boolean updateRes(Res res) {
		String sql = "update res r set r.title=?,r.name=?,r.description=?,r.resimageurl=?,r.cost=?,r.updatetime=?,r.status=?,r.cataid=? where r.id=?";
		Object[] args = new Object[] { res.getTitle(), res.getName(), res.getDescription(), res.getResImageUrl(), res.getCost(), res.getUpdatetime(),
				res.getStatus(), res.getCataId(), res.getId() };
		int result = this.jdbcTemplate.update(sql, args);
		return result == 1;
	}

	@Override
	public boolean updateResStatus(Integer id, int status) {
		String sql = "update res r set r.status=? where r.id=?";
		Object[] args = new Object[] { status, id };
		int result = this.jdbcTemplate.update(sql, args);
		return result == 1;
	}

	@Override
	public boolean updateResPutstimeAndStatus(Integer id, String putstime, int status) {
		String sql = "update res r set r.putstime=?,r.status=? where r.id=?";
		Object[] args = new Object[] { putstime, status, id };
		int result = this.jdbcTemplate.update(sql, args);
		return result == 1;
	}
}
