package cn.com.secbuy.dto;

/**
 * @ClassName: UserDTO
 * @Description: TODO(用户数据传输对象)
 * @author fan
 * @date 2015年3月1日 下午3:19:37
 * @version V1.0
 */
public class UserDTO {
	private Integer id;// 序号
	private String accessid;// 访问令牌标识
	private String nickName;// 昵称
	private String userMail; // 邮箱
	private String name; // 姓名
	private String sex;// 性别
	private String profileimageurl;// 头像图片地址
	private String contact;// 联系方式
	private Integer status;// 用户当前状态1开启，0关闭

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getAccessid() {
		return accessid;
	}

	public void setAccessid(String accessid) {
		this.accessid = accessid;
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

	public String getProfileimageurl() {
		return profileimageurl;
	}

	public void setProfileimageurl(String profileimageurl) {
		this.profileimageurl = profileimageurl;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

}
