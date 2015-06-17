package cn.com.secbuy.dao;

import java.util.List;

import cn.com.secbuy.pojo.ResCatagory;

/**
 * @ClassName: ResCatagoryDAO
 * @Description: TODO(商品分类数据访问接口)
 * @author fan
 * @date 2015年4月4日 下午6:56:13
 * @version V1.0
 */
public interface ResCatagoryDAO {
	/**
	 * 查询商品分类
	 * 
	 * @return 商品集合
	 */
	public List<ResCatagory> findResCatagory();

	/**
	 * 添加商品分类
	 * 
	 * @param resCata
	 *            商品分类
	 * @return true 添加成功 false 添加失败
	 */
	public boolean addReCatagory(ResCatagory resCata);

	/**
	 * 删除指定Id的商品分类
	 * 
	 * @param resCataId
	 *            分类Id
	 * @return 删除成功 false 删除失败
	 */
	public boolean delResCatagory(Integer resCataId);

	/**
	 * 更新商品分类
	 * 
	 * @param resCata
	 *            商品分类
	 * @return 修改成功 false 修改失败
	 */
	public boolean updateResCatagory(ResCatagory resCata);

	/**
	 * 查询指定id的分类信息
	 * @param cataId
	 * @return
	 */
	public ResCatagory findResCatagoryByCataId(Integer cataId);
}