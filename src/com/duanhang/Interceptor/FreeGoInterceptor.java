package com.duanhang.Interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.duanhang.domain.User;

import util.common.HrmConstants;
/**
 * 判断用户权限的springmvc拦截器
 * @author duanhang
 *
 */
public class FreeGoInterceptor implements HandlerInterceptor {
	/** 定义不需要拦截的请求 */
	private static final String[] IGNORE_URI = {"/loginForm","login","/404"};
	
	
	@Override
	public void afterCompletion(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {		
	}

	@Override
	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, ModelAndView arg3)
			throws Exception {
	}
	/** 
     * preHandle方法是进行处理器拦截用的，该方法将在Controller处理之前进行调用，
     * 当preHandle的返回值为false的时候整个请求就结束了。 
     * 如果preHandle的返回值为true，则会继续执行postHandle和afterCompletion。
     */
	@Override
	public boolean preHandle(HttpServletRequest req, HttpServletResponse resp, Object handler) throws Exception {
//		开启拦截，默认用户没有登录
		boolean flag = false;
//		得到访问req地址
		String servletPath = req.getServletPath();
//		如果servletPath是非拦截请求地址，则flag=true，放行；
//		反之，都拦截
		for (String s : IGNORE_URI) {
			if (servletPath.contains(s)) {
				flag=true;
				break;
			}
		}
//		不是放行地址时，拦截处理
		if (!flag) {
//			拿到session中的user
			User user = (User) req.getSession().getAttribute(HrmConstants.USER_SESSION);
//			如果没有登录，则user=null
			if (user==null) {
				req.setAttribute("message", "该用户还没有登录哦！");
//				跳转到login界面
				req.getRequestDispatcher(HrmConstants.LOGIN).forward(req, resp);
				return flag;
			}else {
				flag=true;
			}
		}
			return flag;
		
		
		
		
	}

}
