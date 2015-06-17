package cn.com.secbuy.dto;

/**
 * @ClassName: TradeDTO
 * @Description: TODO(交易信息DTO传输对象)
 * @author fan
 * @date 2015年3月5日 上午12:06:06
 * @version V1.0
 */
public class TradeDTO {
	private Integer id;
	private String title;// 标题
	private Double cost;// 价格
	private String sellerName; // 卖家 姓名
	private String serllerContact;// 卖家联系方式
	private String buyerName; // 买家姓名
	private String buyerContact;// 买家联系方式
	private String createtime;// 交易时间
	private Integer status;// 交易状态 4交易成功 3交易进行中 2交易失败

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Double getCost() {
		return cost;
	}

	public void setCost(Double cost) {
		this.cost = cost;
	}

	public String getSellerName() {
		return sellerName;
	}

	public void setSellerName(String sellerName) {
		this.sellerName = sellerName;
	}

	public String getSerllerContact() {
		return serllerContact;
	}

	public void setSerllerContact(String serllerContact) {
		this.serllerContact = serllerContact;
	}

	public String getBuyerName() {
		return buyerName;
	}

	public void setBuyerName(String buyerName) {
		this.buyerName = buyerName;
	}

	public String getBuyerContact() {
		return buyerContact;
	}

	public void setBuyerContact(String buyerContact) {
		this.buyerContact = buyerContact;
	}

	public String getCreatetime() {
		return createtime;
	}

	public void setCreatetime(String createtime) {
		this.createtime = createtime;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

}
