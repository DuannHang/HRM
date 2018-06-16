package com.duanhang.Controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.duanhang.Service.HRMService;
import com.duanhang.domain.User;

import util.common.HrmConstants;
import util.tag.PageModel;

/**
 * 处理用户请求控制器
 * 
 * @author duanhang
 *
 */
@Controller
public class UserController {
	// DI
	@Autowired
	@Qualifier("HRMService")
	private HRMService HRMService;

	/**
	 * 登录请求
	 * 
	 * @param loginname
	 * @param password
	 * @param session
	 * @param mv
	 * @return
	 */
	@RequestMapping(value = "/login")
	public ModelAndView login(@RequestParam("loginname") String loginname, @RequestParam("password") String password,
			HttpSession session) {
		ModelAndView mv = new ModelAndView();
		// 调用  业务逻辑组件  判断用户是否可以登录
		User user = HRMService.login(loginname, password);
		if (user != null) {
			// 将用户保存到HttpSession当中
			session.setAttribute(HrmConstants.USER_SESSION, user);
			// 客户端跳转到main页面
			mv.addObject(user);
			mv.setViewName("redirect:/main");
			
		} else {
			// 设置登录失败提示信息
			mv.addObject("message", "登录名或密码错误!请重新输入");
			// 服务器内部跳转到登录页面
			mv.setViewName("forward:/loginForm");
		}
		return mv;
	}

	@RequestMapping(value="/user/removeUser")
	public ModelAndView removeUser(String ids,ModelAndView mv) {
		String[] idArray = ids.split(",");
		for (String id : idArray) {
			int  idd= (int)Integer.parseInt(id);
//			根据id删除用户
			HRMService.removeUserById(idd);
		}
//		删除之后，重新查询了一遍所有用户表
		mv.setViewName("user/selectUser");
		return mv;
	}
	

	@RequestMapping(value = "user/selectUser")
	public String selectUser(Integer pageIndex, @ModelAttribute User user, Model model) {
		System.out.println("user=" + user);
		PageModel pageModel = new PageModel();
		if (pageIndex != null) {
			pageModel.setPageIndex(pageIndex);
		}
		List<User> users = HRMService.findUser(user, pageModel);
		model.addAttribute("users", users);
		model.addAttribute("pageModel", pageModel);
		return "user/user";

	}

	
}
