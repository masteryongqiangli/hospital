package system.web.controller.base.menu;

import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import system.core.annotation.Log;
import system.core.controller.BaseController;
import system.core.enums.DataStateTypeEnum;
import system.core.enums.LoginStateTypeEnum;
import system.core.service.CommonServiceI;
import system.core.util.QueryParmFormat;
import system.core.util.ResourceUtil;
import system.web.entity.base.Sys_Base_DataDictionary;
import system.web.entity.base.Sys_Base_Menu;
import system.web.entity.base.Sys_Base_User;
import system.web.service.base.baseuser.BaseUserServiceI;
import system.web.service.base.menu.MenuServiceI;
@Scope("prototype")
@RequestMapping("/menuController")
@Controller
public class MenuController extends BaseController{
	@Autowired
	public MenuServiceI menuService;
	/**
	 * 列表页跳转
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "list")
	@Log(operationName="查看菜单列表",operationType=0)
	public ModelAndView list(HttpServletRequest request) {
		return new ModelAndView("system/base/menu/menu-list");
	}
	 /**
     * 新增或更新页面修改
     * @param request
     * @param sys_Base_Menu
     * @return
     */
	@RequestMapping(params = "goAddorUpdate")
	public ModelAndView goAddorUpdate(HttpServletRequest request,Sys_Base_Menu   sys_Base_Menu){
		if (sys_Base_Menu.getMenuId()!=null) {
			sys_Base_Menu=menuService.get(Sys_Base_Menu.class,sys_Base_Menu.getMenuId());
		}
		request.setAttribute("menu",sys_Base_Menu );
		return new ModelAndView("system/base/menu/menu-addorupdate");
	}
	
	 /**
     * 查看页面跳转
     * @param request
     * @param sys_Base_Menu
     * @return
     */
	@RequestMapping(params = "goLook")
	@Log(operationName="查看菜单",operationType=0)
	public ModelAndView goLook(HttpServletRequest request,Sys_Base_Menu   sys_Base_Menu){
		if (sys_Base_Menu.getMenuId()!=null) {
			sys_Base_Menu=menuService.get(Sys_Base_Menu.class,sys_Base_Menu.getMenuId());
		}
		request.setAttribute("menu",sys_Base_Menu );
		return new ModelAndView("system/base/menu/menu-detail");
	}
	/**
	 * 获取treegrid数据
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "treegrid")
	@ResponseBody
	public JSONObject treegrid(HttpServletRequest request) {
	 
		return menuService.getTreeGridJson(QueryParmFormat.Format(request.getParameterMap()));
	}
	/**
	 * 保存菜单
	 * 
	 * @param request
	 * @param sys_Base_Menu
	 * @return
	 */
	@RequestMapping(params = "doAddorUpdate")
	@ResponseBody
	@Log(operationName="保存或新增菜单",operationType=0)
	public JSONObject  doAddorUpdate(HttpServletRequest request, Sys_Base_Menu   sys_Base_Menu ) {
		JSONObject jsonObject=new JSONObject();
		String msg="";
				sys_Base_Menu.setParent_menu(menuService.get(Sys_Base_Menu.class, request.getParameter("parent_id")));
		 
		if (sys_Base_Menu.getMenuId()==null||"".equals(sys_Base_Menu.getMenuId())) {
			try {
				menuService.save(sys_Base_Menu);
				msg= DataStateTypeEnum.ADD_SUCCESS.getMessage();
			} catch (Exception e) {
				msg=DataStateTypeEnum.ADD_ERROR.getMessage();
				// TODO: handle exception
			}
		}else {
			try {
				menuService.update(sys_Base_Menu);
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
	 * 删除菜单
	 * @param request
	 * @param sys_Base_DataDictionary
	 * @param jsonObject
	 * @return
	 */
	@RequestMapping(params = "doDelete")
	@ResponseBody
	@Log(operationName="删除菜单",operationType=0)
	 public JSONObject doDelete(HttpServletRequest request, Sys_Base_Menu sys_Base_Menu){
		 JSONObject jsonObject=new  JSONObject();
		 request.getParameter("menuId");
		 sys_Base_Menu=menuService.get(Sys_Base_Menu.class, sys_Base_Menu.getMenuId());
	
		 if (sys_Base_Menu.getParent_menu()==null) {
			 try { 
				 menuService.batchDelete(sys_Base_Menu);
				 jsonObject.put("msg",  DataStateTypeEnum.DELETE_SUCCESS.getMessage());
			} catch (Exception e) {
				jsonObject.put("msg",  DataStateTypeEnum.DELETE_ERROR.getMessage());
				// TODO: handle exception
			}
		}else{
			 try {
				 sys_Base_Menu.setState(0);
				 menuService.update(sys_Base_Menu);
				 jsonObject.put("msg",  DataStateTypeEnum.DELETE_SUCCESS.getMessage());
			} catch (Exception e) {
				jsonObject.put("msg",  DataStateTypeEnum.DELETE_ERROR.getMessage());
				// TODO: handle exception
			}
		}
			
		
		 return jsonObject;
		 
	 }
	/**
	 * 获取一级菜单
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "getParentMenuList")
	@ResponseBody
	public JSONArray getParentMenuList(HttpServletRequest request){
		return menuService.getParentMenuList();
	}
	 /**
	  * 获取用户菜单
	  * @param request
	  * @return
	  */
	@RequestMapping(params="getUserMenuList", method = RequestMethod.POST)
	@ResponseBody
	public JSONArray getUserMenuList(HttpServletRequest request) {
		return menuService.getUserMenuList();
	}
}
