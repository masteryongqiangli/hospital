package system.core.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.hibernate.loader.plan.exec.process.spi.ReturnReader;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import system.core.enums.LoginStateTypeEnum;
import system.web.entity.base.Sys_Base_Role;
import system.web.entity.base.Sys_Base_User;
import system.web.entity.base.Sys_User;


/**
 * 项目参数工具类
 * 
 */
public class ResourceUtil {
	/**
	 * 获得请求路径
	 * 
	 * @param request
	 * @return
	 */
	public static String getRequestPath(HttpServletRequest request) {
		String requestPath = request.getRequestURI()+ "?" + request.getQueryString();
		if (requestPath.indexOf("&") > -1) {// 去掉其他参数
			requestPath = requestPath.substring(0, requestPath.indexOf("&"));
		}
		requestPath = requestPath.substring(request.getContextPath().length() + 1);// 去掉项目路径
		return requestPath;
	}
	/**
	 * 获得springmvc下HttpServletRequest
	 * @return
	 */
	public static HttpServletRequest getRequest(){
		return  ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
	}
	
	/**
	 * 获得springmvc下HttpSession
	 * @return
	 */
	public static HttpSession Session(){
		return getRequest().getSession();
	}
	
	/**
	 * 获得当前用户
	 * @return
	 */
	public static Sys_User getSys_User(){
		return (Sys_User) getRequest().getSession().getAttribute(LoginStateTypeEnum.LOGIN_SUCCESS.getCode());
	}
	public static    List<Sys_Base_Role> getSys_UserRoles() {
		List<Sys_Base_Role> list =new ArrayList<>();
		String ids[]=getSys_User().getRoleIdList().split(",");
		for (int i = 0; i < ids.length; i++) {
			Sys_Base_Role role=new Sys_Base_Role();
			role.setRoleId(ids[i]);
			list.add(role);
		}
		return list;
	}
	public static List<String> getSys_UserRoleList() {
		return Arrays.asList(getSys_User().getRoleCodeList());
	}
}
