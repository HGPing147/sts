package cn.com.secbuy.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

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
		String sql = "select u.accessid,u.contact,u.id,u.name,u.nickname,u.profileimageurl,u.sex,u.status,u.usermail from user u "
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
				user.setProfileimageurl(rs.getString("profileimageurl"));
				user.setSex(rs.getString("sex"));
				user.setStatus(rs.getInt("status"));
				user.setUserMail(rs.getString("usermail"));
			}
		});
		return user;
	}

	@Override
	public UserDTO findUserByAccessid(String accessid) {
		String sql = "select u.accessid,u.contact,u.id,u.name,u.nickname,u.profileimageurl,u.sex,u.status,u.usermail from user u "
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
				user.setProfileimageurl(rs.getString("profileimageurl"));
				user.setSex(rs.getString("sex"));
				user.setStatus(rs.getInt("status"));
				user.setUserMail(rs.getString("usermail"));
			}
		});
		return user;
	}

	@Override
	public boolean addUser(User user) {
		String sql = "insert into user(nickname,usermail,password,name,sex,contact,profileimageurl,role,status) values(?,?,?,?,?,?,?,?,0,1)";
		String nickname = user.getNickName();
		String usermail = user.getUserMail();
		String password = user.getPassword();
		String name = user.getName();
		String sex = user.getSex();
		String contact = user.getContact();
		String profileimageurl = user.getProfileImageUrl();
		Object[] args = new Object[] { nickname, usermail, password, name, sex, contact, profileimageurl };
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
		String sql = "select u.accessid,u.contact,u.id,u.name,u.nickname,u.profileimageurl,u.sex,u.status,u.usermail from user u "
				+ "where id=? and u.status=1";
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
				user.setProfileimageurl(rs.getString("profileimageurl"));
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
		String sql = "update user u set u.nickname=?,u.usermail=?,u.password=?,u.name=?,u.sex=?,u.contact=?,u.profileimageurl=? where u.id=?";
		String nickname = user.getNickName();
		String usermail = user.getUserMail();
		String password = user.getPassword();
		String name = user.getName();
		String sex = user.getSex();
		String contact = user.getContact();
		String profileimageurl = user.getProfileImageUrl();
		Integer id = user.getId();
		Object[] args = new Object[] { nickname, usermail, password, name, sex, contact, profileimageurl, id };
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
}
