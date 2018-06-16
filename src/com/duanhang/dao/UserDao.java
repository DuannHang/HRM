package com.duanhang.dao;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.springframework.stereotype.Repository;

import com.duanhang.Provider.UserDynaSqlProvider;
import com.duanhang.domain.User;

import util.common.HrmConstants;

/**
 * @Description: UserMapper接口
 * @author duanhang
 *
 */
@Repository(value="userDao")
public interface UserDao {
	// 根据参数查询用户总数
	@SelectProvider(type = UserDynaSqlProvider.class, method = "count")
	Integer count(HashMap<String, Object> params);

	// 动态分页查询
	@SelectProvider(type = UserDynaSqlProvider.class, method = "selectWhitParam")
	List<User> selectByPage(HashMap<String, Object> params);

	/**
	 * 用户登录：根据帐号和密码查询用户是否存在
	 * 
	 * @param loginname
	 * @param password
	 * @return
	 */
	@Select("select * from " + HrmConstants.USERTABLE + " where loginname=#{loginname} and password=#{password}")
	User selectByLoginnameAndPassword(@Param("loginname") String loginname, @Param("password") String password);

	@Delete("delete from " + HrmConstants.USERTABLE + " where id=#{id}")
	void deleteById(int idd);



}
