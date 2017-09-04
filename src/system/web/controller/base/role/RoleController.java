package system.web.controller.base.role;

import java.awt.Menu;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.el.lang.ELArithmetic.DoubleDelegate;
import org.hibernate.QueryParameterException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.servlet.ModelAndView;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import system.core.annotation.Log;
import system.core.controller.BaseController;
import system.core.enums.DataStateTypeEnum;
import system.core.util.QueryParmFormat;
import system.web.entity.base.Sys_Base_Role;
import system.web.service.base.role.RoleServiceI;

@Scope("prototype")
@RequestMapping("/roleController")
@Controller
public class RoleController extends BaseController{
	@Autowired
	private RoleServiceI roleService;
	/**
	 * 列表页跳转
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "list")
	@Log(operationName="查看角色列表",operationType=0)
	public ModelAndView list(HttpServletRequest request) {
		return new ModelAndView("system/base/role/role-list");
	}
    /**
     * 新增或更新页面跳转
     * @param request
     * @param sys_Base_DataDictionary
     * @return
     */
	@RequestMapping(params = "goRoleUsers")
	@Log(operationName="角色用户管理",operationType=0)
	public ModelAndView goRoleUsers(HttpServletRequest request, Sys_Base_Role sys_Base_Role) {
		if (sys_Base_Role.getRoleId()!=null) {
			sys_Base_Role=roleService.get(Sys_Base_Role.class, sys_Base_Role.getRoleId());
		}
		request.setAttribute("role",sys_Base_Role );
		return new ModelAndView("system/base/role/role-users");
	}
	
	/**
     * 新增或更新页面修改
     * @param request
     * @param sys_Base_DataDictionary
     * @return
     */
	@RequestMapping(params = "goAddorUpdate")
	public ModelAndView goAddorUpdate(HttpServletRequest request, Sys_Base_Role sys_Base_Role) {
		if (sys_Base_Role.getRoleId()!=null) {
			sys_Base_Role=roleService.get(Sys_Base_Role.class, sys_Base_Role.getRoleId());
		}
		request.setAttribute("role",sys_Base_Role );
		
		return new ModelAndView("system/base/role/role-addorupdate");
	}
	/**
     * 查看页面调转
     * @param request
     * @param sys_Base_DataDictionary
     * @return
     */
	@RequestMapping(params = "goLook")
	@Log(operationName="查看角色",operationType=0)
	public ModelAndView goLook(HttpServletRequest request, Sys_Base_Role sys_Base_Role) {
		if (sys_Base_Role.getRoleId()!=null) {
			sys_Base_Role=roleService.get(Sys_Base_Role.class, sys_Base_Role.getRoleId());
		}
		request.setAttribute("role",sys_Base_Role );
		
		return new ModelAndView("system/base/role/role-detail");
	}
	/**
	 * 获取datagrid数据
	 * @param request
	 * @param msg
	 * @return
	 */
	@RequestMapping(params = "datagrid")
	@ResponseBody
	public JSONObject datagrid(HttpServletRequest request) {
	 
		return roleService.getDataGridJson(QueryParmFormat.Format(request.getParameterMap()));
	}
	
	/**
	 * 保存角色
	 * 
	 * @param request
	 * @param sys_Base_User
	 * @return
	 */
	@RequestMapping(params = "doAddorUpdate")
	@ResponseBody
	@Log(operationName="保存或新增角色",operationType=0)
	public JSONObject  doAddorUpdate(HttpServletRequest request, Sys_Base_Role sys_Base_Role) {
		JSONObject jsonObject=new JSONObject();
		String msg="";
		if (sys_Base_Role.getRoleId()==null||"".equals(sys_Base_Role.getRoleId())) {
			try {
				roleService.save(sys_Base_Role);
				msg= DataStateTypeEnum.ADD_SUCCESS.getMessage();
			} catch (Exception e) {
				msg=DataStateTypeEnum.ADD_ERROR.getMessage();
				// TODO: handle exception
			}
		}else{
			try {
				 roleService.update(sys_Base_Role);
				 msg=DataStateTypeEnum.SAVE_SUCCESS.getMessage();
			} catch (Exception e) {
				msg=DataStateTypeEnum.SAVE_ERROR.getMessage();
				// TODO: handle exception
			}
		}
		jsonObject.put("msg", msg);
		return jsonObject;
	}
	/**
     * 角色菜单管理
     * @param request
     * @param sys_Base_DataDictionary
     * @return
     */
	@RequestMapping(params = "goRoleMenus")
	@Log(operationName="角色菜单管理",operationType=0)
	public ModelAndView goRoleMenus(HttpServletRequest request, Sys_Base_Role sys_Base_Role) {
		if (sys_Base_Role.getRoleId()!=null) {
			sys_Base_Role=roleService.get(Sys_Base_Role.class, sys_Base_Role.getRoleId());
		}
		request.setAttribute("rolemenus",roleService.getRoleMenus(sys_Base_Role) );
		request.setAttribute("role",sys_Base_Role );
		return new ModelAndView("system/base/role/role-menus"); 
	}
	/**
	 * 保存角色菜单
	 * 
	 * @param request
	 * @param sys_Base_User
	 * @return
	 */
	@RequestMapping(params = "saveRoleMenus")
	@ResponseBody
	@Log(operationName="保存角色菜单",operationType=0)
	public JSONObject  saveRoleMenus(HttpServletRequest request, Sys_Base_Role sys_Base_Role) {
		JSONObject jsonObject=new JSONObject();
		String msg="";
		try {
			String array= request.getParameter("array");
			 roleService.saveRoleMenus(sys_Base_Role, array);
			msg=DataStateTypeEnum.SAVE_SUCCESS.getMessage();
		} catch (Exception e) {
			msg=DataStateTypeEnum.SAVE_ERROR.getMessage();
			// TODO: handle exception
		}
		
		jsonObject.put("msg", msg);
		return jsonObject;
	}
	/**
	 * 删除角色
	 * @param request
	 * @param Sys_Base_Role
	 * @param jsonObject
	 * @return
	 */
	@RequestMapping(params = "doDelete")
	@ResponseBody
	@Log(operationName="删除角色",operationType=0)
	 public JSONObject doDelete(HttpServletRequest request, Sys_Base_Role sys_Base_Role){
		 JSONObject jsonObject=new  JSONObject();
	   sys_Base_Role=roleService.get(Sys_Base_Role.class, sys_Base_Role.getRoleId());
		
			 try {
				 sys_Base_Role.setState(0);
				 roleService.update(sys_Base_Role);
				 jsonObject.put("msg",  DataStateTypeEnum.DELETE_SUCCESS.getMessage());
			} catch (Exception e) {
				jsonObject.put("msg",  DataStateTypeEnum.DELETE_ERROR.getMessage());
				// TODO: handle exception
			}
		
			
		
		 return jsonObject;
		 
	 }
	
	/**
	 * 角色下拉
	 * @param request
	 * @param jsonObject
	 * @return
	 */
	@RequestMapping(params = "getRoleList")
	@ResponseBody
	public JSONArray getRoleList() {
		return roleService.getRoleList();
	}
}
