package business.controller;

import java.io.File;
import java.io.IOException;
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
import system.core.util.FileUtils;
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
	 * 列表页跳转
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "lookList")
	@Log(operationName = "查看用户列表", operationType = 0)
	public ModelAndView lookList(HttpServletRequest request) {
		return new ModelAndView("business/bloodEnter/bloodResultLook");
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
		/*request.setAttribute("districtData", bloodEnterService.getVillage());*/
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
		String msg="";
		Sys_User user = ResourceUtil.getSys_User();
		String village  = user.getVillage();
		sys_Base_bloodEnter.setBlooderDistrict(village);
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
	 * 导出单个文件
	 * @param request
	 * @param response
	 */
	@SuppressWarnings("static-access")
	@RequestMapping(params="exportWord")
	@Log(operationName="导出word",operationType=0)
	public void exportWord(HttpServletRequest request,HttpServletResponse response){
		String rootPath = request.getSession().getServletContext().getRealPath("/sysfile/");
		JSONObject returnString = makeFile(request.getParameter("bloodEnterId"),rootPath);
		String wordName = returnString.getString("wordName");
		String tempFilePath = returnString.getString("tempFilePath");
		FileUtils fileUtils = new FileUtils();
		try {
			fileUtils.downloadFile(wordName, tempFilePath, response);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 批量导出文件
	 * @param request
	 * @param response
	 * @throws IOException 
	 */
	@SuppressWarnings({"static-access" })
	@RequestMapping(params="batchExportWord")
	@Log(operationName="批量导出word",operationType=0)
	public void batchExportWord(HttpServletRequest request,HttpServletResponse response) throws IOException{
		Map<String, Object> dataMap = new HashMap<String, Object>();
		String[] batchBloodId = request.getParameter("bloodEnterId").split(",");
		for (int i = 0; i < batchBloodId.length; i++) {
			Map<String, Object> map = getOneData(batchBloodId[i]);
			dataMap.put(map.get("blooderName").toString(), map);
		}
		CreateWordUtil createWordUtil = new CreateWordUtil();
		String rootPath = request.getSession().getServletContext().getRealPath("/sysfile/");
		File file = new File(rootPath+"/transfile/");
		if (!file.exists()) {
			file.mkdir();
		}
		try {
			createWordUtil.batchCreateFile(rootPath, dataMap);
		} catch (Exception e) {
			e.printStackTrace();
		}
		FileUtils fileUtils = new FileUtils();
		String zipFilePath = rootPath+"/transfile/";
		String zipFileName = "批量血液化验报告.zip";
		fileUtils.makeZipFile(zipFilePath, zipFileName);
		fileUtils.downloadZipFile(zipFilePath, zipFileName, response);
	}
	/**
	 * 单纯生成文件
	 * @param request
	 * @return
	 */
	@SuppressWarnings("static-access")
	public JSONObject makeFile(String id,String rootPath){
		File file = new File(rootPath+"/transfile/");
		if (!file.exists()) {
			file.mkdir();
		}
		JSONObject jsonObject = bloodEnterService.getOneBlood(id);
		JSONObject jsonObject2 = JSONObject.fromObject(JSONArray.fromObject(jsonObject.get("data")).get(0));
		String wordName = jsonObject2.get("blooderName")+"的血液检验报告";
		Map<String, Object> dataMap = new HashMap<String, Object>();
		String[] k = {"bloodNumber","blooderName","blooderIdCard","bloodStartTime","bloodAriveTime","bloodCheckTime","blooderAge","blooderSex"
				,"bloodCheckHospital","printTime","ALB","ALP","ALT","AST","CK","CK_MB"
				,"CRE","DBIL","GGT","GLU","HBDH","HDL_C","LDH","LDL_C","TBIL","TC","TG","TP","UA","UREA","HbsAg"};
		for(int i=0;i<k.length;i++){
			if (i>=10) {
				dataMap.put(k[i].toLowerCase(), jsonObject2.get(k[i])==null?"":jsonObject2.get(k[i]));
			}else{
				dataMap.put(k[i], jsonObject2.get(k[i])==null?"":jsonObject2.get(k[i]));
			}
		}
		CreateWordUtil createWordUtil = new CreateWordUtil();
		String tempFilePath=rootPath+"/transfile/"+wordName+".doc";
		try {
			createWordUtil.CreateFileNoDoDownload(rootPath,wordName, dataMap);
		} catch (Exception e) {
			e.printStackTrace();
		}
		JSONObject jsonObject3 = new JSONObject();
		jsonObject3.put("wordName", wordName);
		jsonObject3.put("tempFilePath", tempFilePath);
		return jsonObject3;
	}
	
	@RequestMapping(params="getBloodNumber")
	@ResponseBody
	@Log(operationName="获取血样编号序号",operationType=0)
	public JSONObject getBloodNumber(HttpServletRequest request){
		String village = ResourceUtil.getSys_User().getVillage();
		JSONObject jsonObject = new JSONObject();
		String orderNumber = bloodEnterService.getBloodNumber(village);
		int order = orderNumber.equals("")?1:Integer.parseInt(orderNumber)+1;
		jsonObject.put("msg", order);
		return jsonObject;
	}
	@RequestMapping(params="doGoCheck")
	@ResponseBody
	@Log(operationName="送检",operationType=0)
	public JSONObject doGoCheck(HttpServletRequest request){
		JSONObject jsonObject = new JSONObject();
		if (bloodEnterService.doGoCheck(request.getParameter("array").split(","))) {
			jsonObject.put("msg", "送检成功");
		}else{
			jsonObject.put("msg", "送检失败");
		}
		return jsonObject;
	}
	
	
	/**
	 * 单纯生成文件
	 * @param request
	 * @return
	 */
	public Map<String, Object> getOneData(String id){
		JSONObject jsonObject = bloodEnterService.getOneBlood(id);
		JSONObject jsonObject2 = JSONObject.fromObject(JSONArray.fromObject(jsonObject.get("data")).get(0));
		Map<String, Object> dataMap = new HashMap<String, Object>();
		String[] k = {"bloodNumber","blooderName","blooderIdCard","bloodStartTime","bloodAriveTime","bloodCheckTime","blooderAge","blooderSex"
				,"bloodCheckHospital","printTime","ALB","ALP","ALT","AST","CK","CK_MB"
				,"CRE","DBIL","GGT","GLU","HBDH","HDL_C","LDH","LDL_C","TBIL","TC","TG","TP","UA","UREA","HbsAg"};
		for(int i=0;i<k.length;i++){
			if (i>=10) {
				dataMap.put(k[i].toLowerCase(), jsonObject2.get(k[i])==null?"":jsonObject2.get(k[i]));
			}else{
				dataMap.put(k[i], jsonObject2.get(k[i])==null?"":jsonObject2.get(k[i]));
			}
		}
		return dataMap;
	}
}
