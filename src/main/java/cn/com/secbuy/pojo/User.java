package cn.com.secbuy.pojo;

import java.io.Serializable;
import java.util.Date;

/**
 * @ClassName: User
 * @Description: TODO(用户领域对象)
 * @author fan
 * @date 2015年2月28日 下午9:49:07
 * @version V1.0
 */
public class User implements Serializable {
	private static final long serialVersionUID = -7284986765624009907L;

	private Integer id;// 序号
	private String nickName;// 昵称
	private String userMail; // 邮箱
	private String password; // 密码
	private String name; // 姓名
	private String sex;// 性别
	private String contact;// 联系方式
	private Date lastUpdateTime;// 最后登陆更新时间
	private String accessid;// 访问令牌标识
	private Integer role;// 目前作为扩展字段，角色类型
	private Integer status;// 用户当前状态1开启，0关闭

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getUserMail() {
		return userMail;
	}

	public void setUserMail(String userMail) {
		this.userMail = userMail;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public Date getLastUpdateTime() {
		return lastUpdateTime;
	}

	public void setLastUpdateTime(Date lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}

	public String getAccessid() {
		return accessid;
	}

	public void setAccessid(String accessid) {
		this.accessid = accessid;
	}

	public Integer getRole() {
		return role;
	}

	public void setRole(Integer role) {
		this.role = role;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

}
