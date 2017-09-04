package business.controller;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.jeecgframework.core.util.CreateWordUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import business.entity.Sys_Base_bloodEnter;
import business.entity.Sys_Base_bloodEnter;
import business.entity.Sys_Base_bloodResult;
import business.service.bloodEnterService;
import sun.security.x509.CertAndKeyGen;
import system.core.annotation.Log;
import system.core.controller.BaseController;
import system.core.enums.DataStateTypeEnum;
import system.core.util.QueryParmFormat;
import system.core.util.ResourceUtil;
import system.web.entity.base.Sys_Base_User;
import system.web.entity.base.Sys_User;

@Scope("prototype")
@RequestMapping("bloodEnterController")
@Controller
public class bloodEnterController extends BaseController{
	@Autowired
	bloodEnterService bloodEnterService;
	
	/**
	 * 跳转list页面
	 * @param request
	 * @return
	 */
	@RequestMapping(params="list")
	@Log(operationName="查看血样录入列表",operationType=0)
	public ModelAndView list(HttpServletRequest request){
		return new ModelAndView("business/bloodEnter/bloodEnterList");
	}
	
	/**
	 * 获取血样录入历史记录
	 * @param request
	 * @return
	 */
	@RequestMapping(params="getBloodEnterList")
	@ResponseBody
	@Log(operationName="查询血样",operationType=0)
	public JSONObject getBloodEnterList(HttpServletRequest request){
		return bloodEnterService.getBloodEnterList(QueryParmFormat.Format(request.getParameterMap()));
	}
	
	/**
	 * 跳转到新增或者修改页面
	 * @param request
	 * @return
	 */
	@RequestMapping(params="goAddorUpdate")
	@Log(operationName="跳转新增录入血样",operationType=0)
	@ResponseBody
	public ModelAndView goAddorUpdate(HttpServletRequest request,Sys_Base_bloodEnter sys_Base_bloodEnter ){
		if (request.getParameter("bloodId")!=null) {
			sys_Base_bloodEnter=bloodEnterService.get(Sys_Base_bloodEnter.class, request.getParameter("bloodId"));
			request.setAttribute("bloodEnter", sys_Base_bloodEnter);
			request.setAttribute("bloodId", request.getParameter("bloodId"));
		}
		request.setAttribute("districtData", bloodEnterService.getVillage());
		Sys_User sys_User = ResourceUtil.getSys_User();
		request.setAttribute("villageNum", bloodEnterService.getvillageNum(sys_User.getVillage()));
		return new ModelAndView("business/bloodEnter/blood-addorupdate");
	}
	/**
	 * 新增血样
	 * @param request
	 * @param bloodEnterEntity
	 * @return
	 */
	@RequestMapping(params="saveBloodinfo")
	@ResponseBody
	@Log(operationName="新增血样",operationType=0)
	public JSONObject saveBloodinfo(HttpServletRequest request,Sys_Base_bloodEnter sys_Base_bloodEnter){
		JSONObject jsonObject=new JSONObject();
		String village = sys_Base_bloodEnter.getBlooderDistrict();
		String msg="";
		Sys_User user = ResourceUtil.getSys_User();
		sys_Base_bloodEnter.setBloodOperator(user.getRealName());
		try {
			if(request.getParameter("bloodId")==null||("").equals(request.getParameter("bloodId"))){
				bloodEnterService.save(sys_Base_bloodEnter);
			}else{
				sys_Base_bloodEnter.setId(request.getParameter("bloodId"));
				Sys_Base_bloodEnter sys_Base_bloodEnter1=bloodEnterService.get(Sys_Base_bloodEnter.class, sys_Base_bloodEnter.getId());
				sys_Base_bloodEnter1.setBlooderDistrict(village);
				bloodEnterService.update(sys_Base_bloodEnter1);
			}
			msg=DataStateTypeEnum.SAVE_SUCCESS.getMessage();
		} catch (Exception e) {
			msg=DataStateTypeEnum.SAVE_ERROR.getMessage();
			e.printStackTrace();
		}
		jsonObject.put("msg", msg);
		return jsonObject;
	}
	
	
	
	@RequestMapping(params="doDelete")
	@ResponseBody
	@Log(operationName="删除血样",operationType=0)
	public JSONObject doDelete(HttpServletRequest request){
		String msg="";
		if(bloodEnterService.doDelete(request.getParameter("bloodId"))){
			msg="删除成功";
		}else{
			msg="删除失败";
		}
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("msg", msg);
		return jsonObject;
	}
	
	/**
	 * 
	 * @param request
	 * @param response
	 */
	@SuppressWarnings("static-access")
	@RequestMapping(params="exportWord")
	@Log(operationName="导出word",operationType=0)
	public void exportWord(HttpServletRequest request,HttpServletResponse response){
		JSONObject jsonObject = bloodEnterService.getOneBlood(request.getParameter("bloodEnterId"));
		String templateName = "bloodResult.xml";
		JSONObject jsonObject2 = JSONObject.fromObject(JSONArray.fromObject(jsonObject.get("data")).get(0));
		String wordName = jsonObject2.get("blooderName")+"的血液检验报告";
		Map<String, Object> dataMap = new HashMap<String, Object>();
		String[] k = {"bloodNumber","blooderName","blooderIdCard","bloodStartTime","bloodAriveTime","bloodResultTime","ALB","ALP","ALT","AST","CK","CK_MB"
				,"CRE","DBIL","GGT","GLU","HBDH","HDL_C","LDH","LDL_C","TBIL","TC","TG","TP","UA","UREA"};
		for(int i=0;i<k.length;i++){
			dataMap.put(k[i], jsonObject2.get(k[i]));
		}
		CreateWordUtil createWordUtil = new CreateWordUtil();
		try {
			createWordUtil.CreateFile(request, response, templateName, wordName, dataMap);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
