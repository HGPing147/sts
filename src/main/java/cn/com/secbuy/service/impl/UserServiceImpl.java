package cn.com.secbuy.service.impl;

import org.springframework.stereotype.Service;

import cn.com.secbuy.dao.impl.UserDAOImpl;
import cn.com.secbuy.service.UserService;

/**
 * @ClassName: UserServiceImpl
 * @Description: TODO(用户业务接口实现)
 * @author fan
 * @date 2015年3月1日 下午9:18:47
 * @version V1.0
 */
@Service("UserService")
public class UserServiceImpl extends UserDAOImpl implements UserService {

}
