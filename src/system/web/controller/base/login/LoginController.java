package system.web.controller.base.login;

import java.io.IOException;
import java.text.ParseException;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.xml.parsers.ParserConfigurationException;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import system.core.annotation.LogEnter;
import system.core.annotation.LogLeave;
import system.core.aspect.SystemLogAspect;
import system.core.controller.BaseController;
import system.core.enums.LoginStateTypeEnum;
import system.core.util.Md5Util;
import system.core.util.ReadXmlUtil;
import system.core.util.ResourceUtil;
import system.core.util.SystemStopUtil;
import system.web.entity.base.Sys_Base_User;
import system.web.entity.base.Sys_User;
import system.web.service.base.baseuser.BaseUserServiceI;
import system.web.service.base.role.RoleServiceI;

@Scope("prototype")
@RequestMapping("/loginController")
@Controller
public class LoginController extends BaseController {
	@Autowired
	public BaseUserServiceI baseUserService;
	@Autowired
	public RoleServiceI roleService;
	@Resource
	public SystemLogAspect systemLogAspect;

	/**
	 * 检查用户权限
	 * 
	 * @param request
	 * @param sys_Base_User
	 * @return
	 */
	@RequestMapping(params = "checkuser", method = RequestMethod.POST)
	@ResponseBody
	@LogEnter(operationName = "用户登录", operationType = 0)
	public JSONObject checkuser(HttpServletRequest request,
			Sys_Base_User sys_Base_User) {
		JSONObject jsonObject = new JSONObject();
		Sys_Base_User loginUser = baseUserService.login(sys_Base_User);
		if (loginUser != null) {
			String xmlPath = request.getSession().getServletContext()
					.getRealPath("sysfile/sysRuntime.xml");
			SystemStopUtil stopUtil = new SystemStopUtil();
			if (loginUser.getUserName().equals("sa")
					|| (!loginUser.getUserName().equals("sa") && stopUtil
							.systemStop(xmlPath))) {
				if (loginUser.getPassword().equals(
						Md5Util.EncoderByMd5(sys_Base_User.getPassword()))) {
					// 登录成功
					try {
						Sys_User sys_User = baseUserService
								.getSysUserByUserId(loginUser.getUserId());
						request.getSession().setAttribute(
								LoginStateTypeEnum.LOGIN_SUCCESS.getCode(),
								sys_User);
					} catch (Exception e) {
						e.printStackTrace();
						// TODO: handle exception
					}
					jsonObject.put("loginState",
							LoginStateTypeEnum.LOGIN_SUCCESS);
				} else {
					// 密码不正确
					jsonObject.put("loginState",
							LoginStateTypeEnum.PASSWORD_ERROR);
				}
			} else {
				jsonObject.put("loginState", LoginStateTypeEnum.LOGIN_OUTTIME);
			}
		} else {// 用户不存在
			jsonObject.put("loginState", LoginStateTypeEnum.NOT_USER);
		}
		return jsonObject;
	}

	@RequestMapping(params = "login")
	public ModelAndView login(HttpServletRequest request) {

		Sys_User sys_Base_User = ResourceUtil.getSys_User();
		if (sys_Base_User != null) {
			request.setAttribute("baseUser", sys_Base_User);
			return new ModelAndView("system/base/main/main");
		} else {
			return new ModelAndView("system/base/login/login");
		}

	}

	@RequestMapping(params = "logout")
	@LogLeave(operationName = "用户注销", operationType = 0)
	public ModelAndView logout(HttpServletRequest request) {
		baseUserService.logout();
		ModelAndView modelAndView = new ModelAndView(new RedirectView(
				"loginController.do?login"));
		return modelAndView;
	}

	@RequestMapping(params = "goHome")
	public ModelAndView goHome(HttpServletRequest request) {
		try {
			request.setAttribute("obj", baseUserService.getHomeData());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new ModelAndView("system/base/main/home");
	}

	@RequestMapping(params = "systemTime")
	public ModelAndView systemTime(HttpServletRequest request) throws ParserConfigurationException, SAXException, IOException {
		ReadXmlUtil readXmlUtil = new ReadXmlUtil();
		Document document = readXmlUtil.readXmlUtil(request.getSession().getServletContext().getRealPath("sysfile/sysRuntime.xml"));
		request.setAttribute("stopDate", document.getElementsByTagName("year").item(0).getFirstChild().getNodeValue()+"-"+
				document.getElementsByTagName("month").item(0).getFirstChild().getNodeValue()+"-"+
				document.getElementsByTagName("day").item(0).getFirstChild().getNodeValue());
		return new ModelAndView("system/base/systemTime/systemTime");
	}
}
