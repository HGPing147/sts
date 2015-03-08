package cn.com.secbuy.dao;

import java.util.List;

import cn.com.secbuy.dto.ResDTO;
import cn.com.secbuy.dto.ContactDTO;
import cn.com.secbuy.pojo.Res;

/**
 * @ClassName: ResDAO
 * @Description: TODO(商品DAO接口，定义商品领域对象与数据库操作)
 * @author fan
 * @date 2015年3月3日 下午10:51:58
 * @version V1.0
 */
public interface ResDAO {
	/**
	 * 查询商品信息(指定记录数)
	 * 
	 * @param pageNow
	 *            当前页
	 * @param pageSize
	 *            每页大小
	 * @param key
	 *            查询字段
	 * @param cateId
	 *            分类序号
	 * @param userId
	 *            用户序号
	 * @return
	 */
	public List<ResDTO> findLimitedReses(long pageNow, int pageSize, String key, Integer cataId, Integer userId);

	/**
	 * 查询商品总记录数
	 * 
	 * @param key
	 *            查询字段
	 * @param cataId
	 *            分类序号
	 * @param userId
	 *            用户序号
	 * @return
	 */
	public long findResRows(String key, Integer cataId, Integer userId);

	/**
	 * 查询分类下商品信息(指定记录数)
	 * 
	 * @param pageNow
	 *            当前页
	 * @param pageSize
	 *            每页大小
	 * @param key
	 *            查询字段
	 * @param cateId
	 *            分类序号
	 * @return
	 */
	public List<ResDTO> findLimitedCataReses(long pageNow, int pageSize, String key, Integer cateId);

	/**
	 * 查询用户下商品信息(指定记录数)
	 * 
	 * @param pageNow
	 *            当前页
	 * @param pageSize
	 *            每页大小
	 * @param key
	 *            查询字段
	 * @param userId
	 *            用户序号
	 * @return
	 */
	public List<ResDTO> findLimitedUserReses(long pageNow, int pageSize, String key, Integer userId);

	/**
	 * 查询指定序号的商品信息
	 * 
	 * @param id
	 *            指定序号
	 * @return
	 */
	public ResDTO findResByID(Integer id);

	/**
	 * 查询商品用户真实姓名和联系方式
	 * 
	 * @param id
	 *            指定序号
	 * @return
	 */
	public ContactDTO findSellerContactByID(Integer id);

	/**
	 * 添加商品记录
	 * 
	 * @param res
	 *            商品信息
	 * @return
	 */
	public boolean addRes(Res res);

	/**
	 * 查询指定序号商品是否存在
	 * 
	 * @param id
	 * @return
	 */
	public boolean findRealRes(Integer id);

	/**
	 * 删除商品
	 * 
	 * @param id
	 *            指定序号
	 * @return
	 */
	public boolean delRes(Integer id);

	/**
	 * 批量删除商品
	 * 
	 * @param ids
	 *            指定序号集合
	 * @return
	 */
	public boolean batchDelReses(Integer[] ids);

	/**
	 * 修改商品信息
	 * 
	 * @param id
	 *            指定序号
	 * @return
	 */
	public boolean updateRes(Res res);

	/**
	 * 修改商品状态
	 * 
	 * @param id
	 *            指定序号
	 * @param status
	 *            状态值
	 * @return
	 */
	public boolean updateResStatus(Integer id, int status);

	/**
	 * 修改发布时间和商品状态
	 * 
	 * @param id
	 * @param status
	 * @return
	 */
	public boolean updateResPutstimeAndStatus(Integer id, String putstime, int status);

}
