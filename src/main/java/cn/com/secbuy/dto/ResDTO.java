package cn.com.secbuy.dto;

/**
 * @ClassName: ResDTO
 * @Description: TODO(商品数据传输对象)
 * @author fan
 * @date 2015年3月3日 下午10:56:42
 * @version V1.0
 */
public class ResDTO {
	private Integer id;// 序号
	private String name;// 名称
	private String title;// 标题
	private String description;// 描述
	private String resImageUrl;// 图片地址
	private Double cost;// 价格
	private String putstime;// 通过审核发布时间
	private Integer status;// 状态
	private String cataName;// 分类名称
	private String nickName;// 昵称
	private String sex;// 性别
	private String profileImageUrl;// 头像图片地址
	private String contact;// 联系方式

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getResImageUrl() {
		return resImageUrl;
	}

	public void setResImageUrl(String resImageUrl) {
		this.resImageUrl = resImageUrl;
	}

	public Double getCost() {
		return cost;
	}

	public void setCost(Double cost) {
		this.cost = cost;
	}

	public String getPutstime() {
		return putstime;
	}

	public void setPutstime(String putstime) {
		this.putstime = putstime;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getCataName() {
		return cataName;
	}

	public void setCataName(String cataName) {
		this.cataName = cataName;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getProfileImageUrl() {
		return profileImageUrl;
	}

	public void setProfileImageUrl(String profileImageUrl) {
		this.profileImageUrl = profileImageUrl;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

}
