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
	private String resDesc;// 描述
	private String resImageUrl;// 图片地址
	private Double cost;// 价格
	private String createtime;// 创建时间
	private String putstime;// 通过审核发布时间
	private int digest;// 0普通商品 1推荐商品
	private Integer status;// 状态
	private String cataName;// 分类名称
	private Integer userId;// 用户Id

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

	public String getResDesc() {
		return resDesc;
	}

	public void setResDesc(String resDesc) {
		this.resDesc = resDesc;
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

	public String getCreatetime() {
		return createtime;
	}

	public void setCreatetime(String createtime) {
		this.createtime = createtime;
	}

	public String getPutstime() {
		return putstime;
	}

	public void setPutstime(String putstime) {
		this.putstime = putstime;
	}

	public int getDigest() {
		return digest;
	}

	public void setDigest(int digest) {
		this.digest = digest;
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

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

}
