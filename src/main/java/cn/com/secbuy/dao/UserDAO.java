package cn.com.secbuy.dao;

import java.util.Date;

import cn.com.secbuy.dto.UserDTO;
import cn.com.secbuy.pojo.User;

/**
 * @ClassName: UserDAO
 * @Description: TODO(用户DAO接口，定义用户领域对象与数据库操作)
 * @author fan
 * @date 2015年3月1日 下午2:26:43
 * @version V1.0
 */
public interface UserDAO {
	/**
	 * 根据账号密码查询指定的用户信息
	 * 
	 * @param account
	 *            账号
	 * @param pwd
	 *            密码
	 * @return User 用户信息
	 */
	public UserDTO findUserByAccountAndPwd(String account, String pwd);

	/**
	 * 查询拥有accessid登陆标识的用户
	 * 
	 * @param accessid
	 *            登陆标识
	 * @return User 用户信息
	 */
	public UserDTO findUserByAccessid(String accessid);

	/**
	 * 查询拥有accessid登陆标识用户是否存在
	 * 
	 * @param accessid
	 *            登陆标识
	 * @return
	 */
	public boolean findRealUserByAccessid(String accessid);

	/**
	 * 保存一个用户
	 * 
	 * @param user
	 *            用户信息
	 * @return true保存成功 false 保存失败
	 */
	public boolean addUser(User user);

	/**
	 * 
	 * @param id
	 * @param accessid
	 * @return
	 */
	public boolean addAccessid(Integer id, String accessid);

	/**
	 * 查询指定序号的用户信息
	 * 
	 * @param id
	 *            用户序号
	 * @return User 用户信息
	 */
	public UserDTO findUserByID(Integer id);

	/**
	 * 修改用户密码
	 * 
	 * @param id
	 *            用户序号
	 * @param newPwd
	 *            新密码
	 * @return true修改成功 false 修改失败
	 */
	public boolean updateUserPwd(Integer id, String newPwd);

	/**
	 * 修改用户信息
	 * 
	 * @param user
	 *            用户信息
	 * @return true修改成功 false 修改失败
	 */
	public boolean updateUser(User user);

	/**
	 * 更新用户最后登录时间
	 * 
	 * @param date
	 *            最后登录时间
	 * @param id
	 *            用户id
	 * @return
	 */
	public boolean updateLastLoginTime(Date date, Integer id);
}
