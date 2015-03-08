package cn.com.secbuy.pojo;

import java.io.Serializable;
import java.util.Date;

/**
 * @ClassName: BusinessDeal
 * @Description: TODO(交易领域对象)
 * @author fan
 * @date 2015年2月28日 下午10:06:34
 * @version V1.0
 */
public class Trade implements Serializable {
	private static final long serialVersionUID = 6408115243134394687L;

	private Integer id;// 序号
	private Integer res;// 商品
	private Integer seller;// 卖家
	private Integer buyer;// 买家
	private Date createtime;// 交易时间
	private Integer status;// 交易状态 4交易成功 3交易进行中 2交易失败

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getRes() {
		return res;
	}

	public void setRes(Integer res) {
		this.res = res;
	}

	public Integer getSeller() {
		return seller;
	}

	public void setSeller(Integer seller) {
		this.seller = seller;
	}

	public Integer getBuyer() {
		return buyer;
	}

	public void setBuyer(Integer buyer) {
		this.buyer = buyer;
	}

	public Date getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

}
