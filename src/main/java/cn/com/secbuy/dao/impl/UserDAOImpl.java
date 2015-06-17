package cn.com.secbuy.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.stereotype.Repository;

import cn.com.secbuy.dao.BaseDAO;
import cn.com.secbuy.dao.UserDAO;
import cn.com.secbuy.dto.UserDTO;
import cn.com.secbuy.pojo.User;

/**
 * @ClassName: UserDAOImpl
 * @Description: TODO(用户DAO接口实现)
 * @author fan
 * @date 2015年3月1日 下午3:15:22
 * @version V1.0
 */
@Repository("UserDAO")
public class UserDAOImpl extends BaseDAO implements UserDAO {

	@Override
	public UserDTO findUserByAccountAndPwd(String account, String pwd) {
		String sql = "select u.accessid,u.contact,u.id,u.name,u.nickname,u.sex,u.status,u.usermail from user u "
				+ "where (u.nickname=? or u.usermail=?) and u.password=? and u.status=1";
		final UserDTO user = new UserDTO();
		Object[] args = new Object[] { account, account, pwd };
		this.jdbcTemplate.query(sql, args, new RowCallbackHandler() {

			@Override
			public void processRow(ResultSet rs) throws SQLException {
				user.setAccessid(rs.getString("accessid"));
				user.setContact(rs.getString("contact"));
				user.setId(rs.getInt("id"));
				user.setName(rs.getString("name"));
				user.setNickName(rs.getString("nickname"));
				user.setSex(rs.getString("sex"));
				user.setStatus(rs.getInt("status"));
				user.setUserMail(rs.getString("usermail"));
			}
		});
		return user;
	}

	@Override
	public UserDTO findUserByAccessid(String accessid) {
		String sql = "select u.accessid,u.contact,u.id,u.name,u.nickname,u.sex,u.status,u.usermail from user u "
				+ "where u.accessid=? and u.status=1";
		final UserDTO user = new UserDTO();
		Object[] args = new Object[] { accessid };
		this.jdbcTemplate.query(sql, args, new RowCallbackHandler() {

			@Override
			public void processRow(ResultSet rs) throws SQLException {
				user.setAccessid(rs.getString("accessid"));
				user.setContact(rs.getString("contact"));
				user.setId(rs.getInt("id"));
				user.setName(rs.getString("name"));
				user.setNickName(rs.getString("nickname"));
				user.setSex(rs.getString("sex"));
				user.setStatus(rs.getInt("status"));
				user.setUserMail(rs.getString("usermail"));
			}
		});
		return user;
	}

	@Override
	public boolean findRealUserByAccessid(String accessid) {
		String sql = "select count(u.id) from user u where u.accessid=? and and u.status=1";
		Object[] args = new Object[] { accessid };
		long result = this.jdbcTemplate.queryForLong(sql, args);
		return result == 1;
	}

	@Override
	public boolean addUser(User user) {
		String sql = "insert into user(nickname,usermail,password,name,sex,contact,role,status) values(?,?,?,?,?,?,0,1)";
		String nickname = user.getNickName();
		String usermail = user.getUserMail();
		String password = user.getPassword();
		String name = user.getName();
		String sex = user.getSex();
		String contact = user.getContact();
		Object[] args = new Object[] { nickname, usermail, password, name, sex, contact };
		int result = this.jdbcTemplate.update(sql, args);
		return result == 1;
	}

	@Override
	public boolean addAccessid(Integer id, String accessid) {
		String sql = "update user u set u.accessid=? where u.id=?";
		Object[] args = new Object[] { accessid, id };
		int result = this.jdbcTemplate.update(sql, args);
		return result == 1;
	}

	@Override
	public UserDTO findUserByID(Integer id) {
		String sql = "select u.accessid,u.contact,u.id,u.name,u.nickname,u.sex,u.status,u.usermail from user u where id=? and u.status=1";
		final UserDTO user = new UserDTO();
		Object[] args = new Object[] { id };
		this.jdbcTemplate.query(sql, args, new RowCallbackHandler() {

			@Override
			public void processRow(ResultSet rs) throws SQLException {
				user.setAccessid(rs.getString("accessid"));
				user.setContact(rs.getString("contact"));
				user.setId(rs.getInt("id"));
				user.setName(rs.getString("name"));
				user.setNickName(rs.getString("nickname"));
				user.setSex(rs.getString("sex"));
				user.setStatus(rs.getInt("status"));
				user.setUserMail(rs.getString("usermail"));
			}
		});
		return user;
	}

	@Override
	public boolean updateUserPwd(Integer id, String newPwd) {
		String sql = "update user u set u.password=? where u.id=?";
		Object[] args = new Object[] { newPwd, id };
		int result = this.jdbcTemplate.update(sql, args);
		return result == 1;
	}

	@Override
	public boolean updateUser(User user) {
		String sql = "update user u set u.nickname=?,u.usermail=?,u.name=?,u.sex=?,u.contact=? where u.id=?";
		String nickname = user.getNickName();
		String usermail = user.getUserMail();
		String name = user.getName();
		String sex = user.getSex();
		String contact = user.getContact();
		Integer id = user.getId();
		Object[] args = new Object[] { nickname, usermail, name, sex, contact, id };
		int result = this.jdbcTemplate.update(sql, args);
		return result == 1;
	}

	@Override
	public boolean updateLastLoginTime(Date date, Integer id) {
		String sql = "update user u set u.lastupdatetime=? where u.id=?";
		Object[] args = new Object[] { date, id };
		int result = this.jdbcTemplate.update(sql, args);
		return result == 1;
	}

	@Override
	public List<UserDTO> findLimitedUsers(long pageNow, int pageSize, String key) {
		StringBuilder sql = new StringBuilder("select u.contact,u.id,u.name,u.nickname,u.sex,u.status,u.usermail from user u where 1=1 ");
		if (key != null && !"".equals(key)) {
			sql.append("and u.nickname like'%" + key + "%'");
		}
		sql.append("limit ?,?");
		long offset = (pageNow - 1) * pageSize;
		int limit = pageSize;
		Object[] args = new Object[] { offset, limit };
		final List<UserDTO> list = new ArrayList<UserDTO>();
		this.jdbcTemplate.query(sql.toString(), args, new RowCallbackHandler() {

			@Override
			public void processRow(ResultSet rs) throws SQLException {
				UserDTO user = new UserDTO();
				user.setContact(rs.getString("contact"));
				user.setId(rs.getInt("id"));
				user.setName(rs.getString("name"));
				user.setNickName(rs.getString("nickname"));
				user.setSex(rs.getString("sex"));
				user.setStatus(rs.getInt("status"));
				user.setUserMail(rs.getString("usermail"));
				list.add(user);
			}
		});
		return list;
	}

	@Override
	public long findUserRows(String key) {
		StringBuilder sql = new StringBuilder("select count(u.id) from user u where 1=1 ");
		if (key != null && !"".equals(key)) {
			sql.append("and u.nickname like'%" + key + "%'");
		}
		return this.jdbcTemplate.queryForLong(sql.toString());
	}

	@Override
	public boolean delUser(Integer userId) {
		String sql = "delete from user where id=?";
		Object[] args = new Object[] { userId };
		int result = this.jdbcTemplate.update(sql, args);
		return result == 1;
	}

}
