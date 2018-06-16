package com.duanhang.ServiceImpl;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.duanhang.Service.HRMService;
import com.duanhang.dao.UserDao;
import com.duanhang.domain.User;

import util.tag.PageModel;

/**
 * 人事管理服务接口实现类
 * 
 * @author duanhang
 *
 */
@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
@Service("HRMService")
public class HRMServiceImpl implements HRMService {
	/**
	 * 自动注入持久层Dao对象
	 */
	@Autowired
//	@Qualifier("userDao")
	private UserDao userDao;
	
//	@Autowired
//	private DeptDao deptDao;
//	
//	@Autowired
//	private EmployeeDao employeeDao;
//	
//	@Autowired
//	private JobDao jobDao;
//	
//	@Autowired
//	private NoticeDao noticeDao;
//	
//	@Autowired
//	private DocumentDao documentDao;
//	

	/**
	 * 用户登录实现
	 */
@Transactional(readOnly=true)
	@Override
	public User login(String loginname, String password) {
		return userDao.selectByLoginnameAndPassword(loginname,password);
	}
/**
 * 根据用户id删除用户
 */
@Override
public void removeUserById(int idd) {
	userDao.deleteById(idd);
}


/**
 * HrmServiceImpl接口findUser方法实现
 * 
 * @param user
 * @param pageModel
 * @return
 */
@Transactional(readOnly = true)
@Override
public List<User> findUser(User user, PageModel pageModel) {
	// 当前需要分页的总数据条数
	HashMap<String, Object> params = new HashMap<>();
	params.put("user", user);
	int recordCount = userDao.count(params);
	pageModel.setRecordCount(recordCount);
	if (recordCount > 0) {
		// 开始分页查询数据：查询第几页的数据
		params.put("pageModel", pageModel);
	}
	/**
	 * 分页查询
	 */
	List<User> users = userDao.selectByPage(params);
	return users;

}
}
