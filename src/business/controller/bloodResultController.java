package business.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import business.entity.Sys_Base_bloodEnter;
import business.entity.Sys_Base_bloodResult;
import business.service.bloodResultService;
import system.core.annotation.Log;
import system.core.controller.BaseController;
import system.core.enums.DataStateTypeEnum;
import system.core.util.QueryParmFormat;
import system.core.util.ResourceUtil;
import system.web.entity.base.Sys_Base_User;
import system.web.entity.base.Sys_User;

@Scope("prototype")
@RequestMapping("bloodResultController")
@Controller

public class bloodResultController extends BaseController{

	@Autowired
	bloodResultService bloodResultService;
	
	/**
	 * 跳转查看血样检验结果页面
	 * @param request
	 * @return
	 */
	@RequestMapping(params="list")
	@Log(operationName="查看血样检验结果列表",operationType=0)
	public ModelAndView list(HttpServletRequest request){
 		return new ModelAndView("business/bloodResult/bloodResultList");
	}
	/**
	 * 获取血样录入历史记录
	 * @param request
	 * @return
	 */
	@RequestMapping(params="getBloodResultList")
	@ResponseBody
	@Log(operationName="查询血样",operationType=0)
	public JSONObject getBloodResultList(HttpServletRequest request){
		return bloodResultService.getBloodResultList(QueryParmFormat.Format(request.getParameterMap()));
	}
	
	/**
	 * 新增血样
	 * @param request
	 * @param bloodEnterEntity
	 * @return
	 */
	@RequestMapping(params="saveBloodResultinfo")
	@ResponseBody
	@Log(operationName="新增血样",operationType=0)
	public JSONObject saveBloodResultinfo(HttpServletRequest request,Sys_Base_bloodResult sys_Base_bloodResult){
		JSONObject jsonObject=new JSONObject();
		String msg="";
		sys_Base_bloodResult.setId(request.getParameter("bloodResultId"));
		sys_Base_bloodResult.setBloodEnterId(request.getParameter("bId"));
		sys_Base_bloodResult.setBloodNumber(request.getParameter("bloodNumber"));
		try {
			bloodResultService.update(sys_Base_bloodResult);
			msg=DataStateTypeEnum.SAVE_SUCCESS.getMessage();
		} catch (Exception e) {
			msg=DataStateTypeEnum.SAVE_ERROR.getMessage();
			e.printStackTrace();
		}
		jsonObject.put("msg", msg);
		return jsonObject;
	}
	/**
	 * 导入数据
	 * @param request
	 */
	@RequestMapping(params="bloodResultFile")
	@Log(operationName="导入数据",operationType=0)
	@ResponseBody
	public JSONObject bloodResultFile(@RequestParam("resultFile") MultipartFile file){
		JSONObject jsonObject = new JSONObject();
		bloodResultService.readExcel(file);
		return jsonObject;
	}
	/**
	 * 跳转到新增或者修改页面
	 * @param request
	 * @return
	 */
	@RequestMapping(params="goAddorUpdate")
	@Log(operationName="跳转新增录入血样",operationType=0)
	@ResponseBody
	public ModelAndView goAddorUpdate(HttpServletRequest request,Sys_Base_bloodResult sys_Base_bloodResult ){
		sys_Base_bloodResult=bloodResultService.get(Sys_Base_bloodResult.class, request.getParameter("resultId"));
		JSONObject jsonObject = bloodResultService.getBloodInfo(sys_Base_bloodResult.getBloodEnterId());
		JSONObject jsonObject2 = JSONObject.fromObject(JSONArray.fromObject(jsonObject.get("data")).get(0));
		request.setAttribute("blooderName", jsonObject2.get("blooderName"));
		request.setAttribute("bloodNumber", jsonObject2.get("bloodNumber"));
		request.setAttribute("result", sys_Base_bloodResult);
		return new ModelAndView("business/bloodResult/bloodResult-addorupdate");
	}
	
	@RequestMapping(params = "doDelete")
	@ResponseBody
	@Log(operationName="删除用户",operationType=0)
	 public JSONObject doDelete(HttpServletRequest request){
		 JSONObject jsonObject=new  JSONObject();
		if (bloodResultService.doDelete(request.getParameter("resultId"))) {
			jsonObject.put("msg",  DataStateTypeEnum.DELETE_SUCCESS.getMessage());
		} else{
			jsonObject.put("msg",  DataStateTypeEnum.DELETE_ERROR.getMessage());
		}
		 return jsonObject;
	 }
	/**
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(params="getResultId")
	@ResponseBody
	@Log(operationName="chaxun",operationType=0)
	public JSONObject getResultId(HttpServletRequest request){
		JSONObject jsonObject= new JSONObject();
		jsonObject.put("msg", bloodResultService.getResultId(request.getParameter("bloodId")));
		return jsonObject;
	}
}
