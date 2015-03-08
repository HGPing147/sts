package cn.com.secbuy.pojo;

import java.io.Serializable;

/**
 * @ClassName: ResCatagory
 * @Description: TODO(商品分类领域对象)
 * @author fan
 * @date 2015年2月28日 下午9:56:55
 * @version V1.0
 */
public class ResCatagory implements Serializable {
	private static final long serialVersionUID = 2683870320440516755L;

	private Integer id; // 序号
	private String name;// 分类名称

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

}
