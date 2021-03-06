package system.web.controller.base.baseuser;

import java.text.ParseException;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import system.core.annotation.Log;
import system.core.controller.BaseController;
import system.core.enums.DataStateTypeEnum;
import system.core.util.DateUtils;
import system.core.util.IdcardUtils;
import system.core.util.Md5Util;
import system.core.util.QueryParmFormat;
import system.core.util.ResourceUtil;
import system.core.util.StringUtil;
import system.web.entity.base.Sys_Base_DataDictionary;
import system.web.entity.base.Sys_Base_User;
import system.web.entity.base.Sys_User;
import system.web.service.base.baseuser.BaseUserServiceI;

@Scope("prototype")
@RequestMapping("/baseUserController")
@Controller
public class BaseUserController extends BaseController {

	@Autowired
	BaseUserServiceI baseUserService;

	/**
	 * 列表页跳转
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "list")
	@Log(operationName = "查看用户列表", operationType = 0)
	public ModelAndView list(HttpServletRequest request) {
		return new ModelAndView("system/base/baseuser/user-list");
	}
	
	/**
	 * 获取datagrid数据
	 * 
	 * @param request
	 * @param msg
	 * @return
	 */
	@RequestMapping(params = "datagrid")
	@ResponseBody
	public JSONObject datagrid(HttpServletRequest request) {
		String userRole = ResourceUtil.getSys_User().getRoleCodeList();
		return baseUserService.getDataGridJson(QueryParmFormat.Format(request.getParameterMap()),userRole);
	}

	/**
	 * 用户角色管理
	 * 
	 * @param request
	 * @param sys_Base_DataDictionary
	 * @return
	 */
	@RequestMapping(params = "goUserRoles")
	@Log(operationName = "用户角色管理", operationType = 0)
	public ModelAndView goUserRoles(HttpServletRequest request,
			Sys_Base_User sys_Base_User) {
		if (sys_Base_User.getUserId() != null) {
			sys_Base_User = baseUserService.get(Sys_Base_User.class,
					sys_Base_User.getUserId());
		}
		request.setAttribute("userRoles",
				baseUserService.getuserRoles(sys_Base_User));
		request.setAttribute("user", sys_Base_User);
		return new ModelAndView("system/base/baseuser/user-roles");
	}

	/**
	 * 保存用户角色
	 * 
	 * @param request
	 * @param sys_Base_User
	 * @return
	 */
	@RequestMapping(params = "saveUserRoles")
	@ResponseBody
	@Log(operationName = "保存用户角色", operationType = 0)
	public JSONObject saveUserRoles(HttpServletRequest request,
			Sys_Base_User sys_Base_User) {
		JSONObject jsonObject = new JSONObject();
		String msg = "";
		try {
			String[] in = (request.getParameter("array")).split(",");
			baseUserService.saveUserRoles(sys_Base_User, in);
			msg = DataStateTypeEnum.SAVE_SUCCESS.getMessage();
		} catch (Exception e) {
			msg = DataStateTypeEnum.SAVE_ERROR.getMessage();
			e.printStackTrace();
		}
		jsonObject.put("msg", msg);
		return jsonObject;
	}

	@RequestMapping(params = "goUserinfo")
	@Log(operationName = "查看个人信息", operationType = 0)
	public ModelAndView goUserinfo(HttpServletRequest request) {
		request.setAttribute("user", ResourceUtil.getSys_User());
		request.setAttribute(
				"version",
				baseUserService.get(Sys_Base_User.class,
						ResourceUtil.getSys_User().getUserId()).getVersion());
		return new ModelAndView("system/base/baseuser/user-info");
	}

	/**
	 * 用户
	 * 
	 * @param request
	 * @param jsonObject
	 * @return
	 */
	@RequestMapping(params = "getBaseUserList")
	@ResponseBody
	public JSONArray getBaseUserList() {
		return baseUserService.getBaseUserList();
	}

	@RequestMapping(params = "saveUserinfo")
	@ResponseBody
	@Log(operationName = "保存个人信息", operationType = 0)
	public JSONObject saveUserinfo(HttpServletRequest request,
			Sys_Base_User sys_Base_User, Sys_Base_DataDictionary dataDictionary) {
		JSONObject jsonObject = new JSONObject();
		String idCardNumber = sys_Base_User.getIdCardNumber();
		Sys_User nowUser = ResourceUtil.getSys_User();
		String userRole = nowUser.getRoleCodeList();
		if ("sa".equals(userRole)) {
			sys_Base_User.setDistrict(dataDictionary);
		}else if ("manager".equals(userRole)) {
			sys_Base_User.setTown(dataDictionary);
		}else if ("yy".equals(userRole)) {
			sys_Base_User.setVillage(dataDictionary);
		}
		if (IdcardUtils.validateCard(idCardNumber)) {
			sys_Base_User.setGener(IdcardUtils.getGenderByIdCard(idCardNumber));
			try {
				sys_Base_User.setBirthDate(DateUtils.parseDate(
						IdcardUtils.getBirthByIdCard(idCardNumber),
						"yyyy-MM-dd"));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		if (StringUtil.isNotEmpty(sys_Base_User.getUserId())) {
			Sys_Base_User user = baseUserService.get(Sys_Base_User.class,
					sys_Base_User.getUserId());
			if (StringUtil.isEmpty(sys_Base_User.getPassword())) {
				sys_Base_User.setPassword(user.getPassword());
			} else {

				sys_Base_User.setPassword(Md5Util.EncoderByMd5(sys_Base_User
						.getPassword()));
			}
			try {
				baseUserService.update(sys_Base_User);
				jsonObject.put("msg",
						DataStateTypeEnum.SAVE_SUCCESS.getMessage());
			} catch (Exception e) {
				jsonObject
						.put("msg", DataStateTypeEnum.SAVE_ERROR.getMessage());
			}
		} else {
			try {
				sys_Base_User.setPassword(Md5Util.EncoderByMd5(sys_Base_User
						.getPassword()));
				baseUserService.save(sys_Base_User);
				jsonObject.put("msg",
						DataStateTypeEnum.ADD_SUCCESS.getMessage());
			} catch (Exception e) {
				jsonObject.put("msg", DataStateTypeEnum.ADD_ERROR.getMessage());
			}
		}
		return jsonObject;

	}

	/**
	 * 删除用户
	 * 
	 * @param request
	 * @param Sys_Base_Role
	 * @param jsonObject
	 * @return
	 */
	@RequestMapping(params = "doDelete")
	@ResponseBody
	@Log(operationName = "删除用户", operationType = 0)
	public JSONObject doDelete(HttpServletRequest request,
			Sys_Base_User sys_Base_User) {
		JSONObject jsonObject = new JSONObject();
		sys_Base_User = baseUserService.get(Sys_Base_User.class,
				sys_Base_User.getUserId());

		try {
			sys_Base_User.setState(0);
			baseUserService.update(sys_Base_User);
			jsonObject
					.put("msg", DataStateTypeEnum.DELETE_SUCCESS.getMessage());
		} catch (Exception e) {
			jsonObject.put("msg", DataStateTypeEnum.DELETE_ERROR.getMessage());
		}
		return jsonObject;
	}

	/**
	 * 新增或更新页面跳转
	 * 
	 * @param request
	 * @param sys_Base_DataDictionary
	 * @return
	 */
	@RequestMapping(params = "goAddorUpdate")
	@ResponseBody
	public ModelAndView goAddorUpdate(HttpServletRequest request,
			Sys_Base_User sys_Base_User) {
		if (sys_Base_User.getUserId() != null) {
			sys_Base_User = baseUserService.get(Sys_Base_User.class,
					sys_Base_User.getUserId());
		}
		request.setAttribute("user", sys_Base_User);
		JSONObject jsonObject = new JSONObject();
		/* 管理员增加乡镇卫生院的用户 0代表超级管理员 1代表区管理员，2代表镇医院 */
		String userRole = ResourceUtil.getSys_User().getRoleCodeList();
		int flag=0;
		if ("sa".equals(userRole)) {
			flag = 0;
			jsonObject=baseUserService.getSelects(flag,"");
		} else if ("manager".equals(userRole)) {
			flag = 1;
			String district = ResourceUtil.getSys_User().getDistrict();
			jsonObject=baseUserService.getSelects(flag,district);
		}else if("yy".endsWith(userRole)){
			flag = 2;
			String town = ResourceUtil.getSys_User().getTown();
			jsonObject=baseUserService.getSelects(flag,town);
		}
		request.setAttribute("flag", flag);
		request.setAttribute("selects", jsonObject);
		return new ModelAndView("system/base/baseuser/user-addorupdate");
	}

	/**
	 * 查看页面跳转
	 * 
	 * @param request
	 * @param sys_Base_DataDictionary
	 * @return
	 */
	@RequestMapping(params = "goLook")
	@Log(operationName = "查看用户", operationType = 0)
	public ModelAndView goLook(HttpServletRequest request,
			Sys_Base_User sys_Base_User) {
		if (sys_Base_User.getUserId() != null) {
			sys_Base_User = baseUserService.get(Sys_Base_User.class,
					sys_Base_User.getUserId());
		}
		try {
			if (sys_Base_User.getBirthDate() != null) {
				request.setAttribute("birthDate", sys_Base_User.getBirthDate()
						.toString().substring(0, 10));
				request.setAttribute("age", IdcardUtils
						.getAgeByIdCard(sys_Base_User.getIdCardNumber()));
			}
		} catch (Exception e) {
		}
		request.setAttribute("user", sys_Base_User);
		return new ModelAndView("system/base/baseuser/user-detail");
	}

	/**
	 * 删除用户
	 * 
	 * @param request
	 * @param Sys_Base_Role
	 * @param jsonObject
	 * @return
	 */
	@RequestMapping(params = "userInfoValid")
	@ResponseBody
	@Log(operationName = "验证用户信息", operationType = 0)
	public boolean userInfoValid(HttpServletRequest request) {
		boolean flag = false;
		try {
			int type = Integer.parseInt(request.getParameter("type"));
			String value = "";
			switch (type) {
			case 0:// 验证用户是否存在
				value = request.getParameter("userName");
				if ("".equals(request.getParameter("userId"))) {
					if (baseUserService.getSysUserByUserName(value) == null)
						flag = true;
				} else {
					flag = true;
				}
				break;
			case 1:// 验证身份证号
				value = request.getParameter("idCardNumber");
				if (IdcardUtils.validateCard(value))
					flag = true;
				break;
			default:
				break;
			}
		} catch (Exception e) {
		}
		return flag;
	}

	@RequestMapping(params = "getIdCardInfo")
	@ResponseBody
	@Log(operationName = "获取身份证信息", operationType = 0)
	public JSONObject getIdCardInfo(HttpServletRequest request) {
		JSONObject jsonObject = new JSONObject();
		boolean state = false;
		try {
			String idCardNumber = request.getParameter("idCardNumber");
			if (IdcardUtils.validateCard(idCardNumber)) {
				state = true;
				String gener = IdcardUtils.getGenderByIdCard(idCardNumber);
				jsonObject.put("birthDate",
						IdcardUtils.getBirthByIdCard(idCardNumber));
				jsonObject.put("gener", gener);
				jsonObject.put("age", IdcardUtils.getAgeByIdCard(idCardNumber));
			}
		} catch (Exception e) {
		}
		jsonObject.put("state", state);
		return jsonObject;
	}
	
	@RequestMapping(params="submitDate")
	@ResponseBody
	public JSONObject submitDate(HttpServletRequest request){
		JSONObject jsonObject = new JSONObject();
		String changeDate = request.getParameter("changeDate");
		if (baseUserService.submitDate(changeDate,request.getSession().getServletContext().getRealPath("sysfile/sysRuntime.xml"))) {
			jsonObject.put("msg", "修改成功");
		}else{
			jsonObject.put("msg", "修改失败");
		}
		return jsonObject;
	}
	
	@RequestMapping(params="changePswd")
	@ResponseBody
	public JSONObject changePswd(HttpServletRequest request){
		JSONObject jsonObject = new JSONObject();
		String password = request.getParameter("rePassword");
		String userID = request.getParameter("id");
		if (baseUserService.changePswd(userID,password) ){
			jsonObject.put("msg", "修改成功");
		}else{
			jsonObject.put("msg", "修改失败");
		}
		return jsonObject;
	}
}
