package com.duanhang.Service;

import java.util.List;

import com.duanhang.domain.User;

import util.tag.PageModel;

/**
 * 人事管理服务接口
 * @author duanhang
 *
 */
public interface HRMService {

	List<User> findUser(User user, PageModel pageModel);

	User login(String loginname, String password);

	void removeUserById(int idd);


	

}
