package cn.com.secbuy.pojo;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * @ClassName: Res
 * @Description: TODO(商品信息领域对象)
 * @author fan
 * @date 2015年2月28日 下午9:59:21
 * @version V1.0
 */
public class Res implements Serializable {
	private static final long serialVersionUID = 7393306767422020280L;

	private Integer id;// 序号
	private String name;// 名称
	private String title;// 标题
	private String description;// 描述
	private String resImageUrl;// 图片地址
	private Double cost;// 价格
	private Date createtime;// 提交时间
	private Date updatetime;// 修改时间
	private String putstime;// 通过审核发布时间
	private Integer status;// 状态 0下架 1待审核 2发布 3正在交易
	private Integer cataId;// 分类
	private Integer userId;// 卖家

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

	public Date getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	public Date getUpdatetime() {
		return updatetime;
	}

	public void setUpdatetime(Date updatetime) {
		this.updatetime = updatetime;
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

	public Integer getCataId() {
		return cataId;
	}

	public void setCataId(Integer cataId) {
		this.cataId = cataId;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}
}
