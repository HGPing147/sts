package cn.com.secbuy.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * @ClassName: BaseDAO
 * @Description: TODO(基础DAO类)
 * @author fan
 * @date 2015年3月1日 下午2:24:45
 * @version V1.0
 */
public class BaseDAO {
	@Autowired
	protected JdbcTemplate jdbcTemplate;
}
