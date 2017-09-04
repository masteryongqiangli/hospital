package system.web.controller.base.datadictionary;

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
import system.core.interceptors.DateConvertEditor;
import system.core.util.QueryParmFormat;
import system.core.util.ResourceUtil;
import system.web.dao.base.datadictionary.DataDictionaryDaoI;
import system.web.dao.base.impl.datadictionary.DataDictionaryDaoImpl;
import system.web.entity.base.Sys_Base_DataDictionary;
import system.web.entity.base.Sys_Base_User;
import system.web.service.base.datadictionary.DataDictionaryServiceI;
import system.web.service.base.impl.datadictionary.DataDictionaryServiceImpl;

@Scope("prototype")
@RequestMapping("/dataDictionaryController")
@Controller
public class DataDictionaryController extends BaseController{
	@Autowired
	private DataDictionaryServiceI dataDictionaryService;
	/**
	 * 列表页跳转
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "list")
	@Log(operationName="查看数据字典列表",operationType=0)
	public ModelAndView list(HttpServletRequest request) {
		return new ModelAndView("system/base/datadictionary/datadic-list");
	}
    /**
     * 新增或更新页面修改
     * @param request
     * @param sys_Base_DataDictionary
     * @return
     */
	@RequestMapping(params = "goAddorUpdate")
	public ModelAndView goAddorUpdate(HttpServletRequest request, Sys_Base_DataDictionary sys_Base_DataDictionary) {
		if (sys_Base_DataDictionary.getDataDicId()!=null) {
			sys_Base_DataDictionary=dataDictionaryService.get(Sys_Base_DataDictionary.class, sys_Base_DataDictionary.getDataDicId());
		}
		request.setAttribute("datadic",sys_Base_DataDictionary );
		return new ModelAndView("system/base/datadictionary/datadic-addorupdate");
	}
	
	/**
     * 查看页面跳转
     * @param request
     * @param sys_Base_DataDictionary
     * @return
     */
	@RequestMapping(params = "goLook")
	@Log(operationName="查看数据字典",operationType=0)
	public ModelAndView goLook(HttpServletRequest request, Sys_Base_DataDictionary sys_Base_DataDictionary) {
		if (sys_Base_DataDictionary.getDataDicId()!=null) {
			sys_Base_DataDictionary=dataDictionaryService.get(Sys_Base_DataDictionary.class, sys_Base_DataDictionary.getDataDicId());
		}
		request.setAttribute("datadic",sys_Base_DataDictionary );
		request.setAttribute("parent", dataDictionaryService.get(Sys_Base_DataDictionary.class, sys_Base_DataDictionary.getParent_DataDictionary()).getText());
		return new ModelAndView("system/base/datadictionary/datadic-detail");
	}
	/**
	 * 获取treegrid数据
	 * @param request
	 * @param sys_Base_DataDictionary
	 * @param msg
	 * @return
	 */
	@RequestMapping(params = "treegrid")
	@ResponseBody
	public JSONObject treegrid(HttpServletRequest request) {
	 
		return dataDictionaryService.getTreeGridJson(QueryParmFormat.Format(request.getParameterMap()));
	}
	/**
	 * 保存数据字典
	 * 
	 * @param request
	 * @param sys_Base_User
	 * @return
	 */
	@RequestMapping(params = "doAddorUpdate")
	@ResponseBody
	@Log(operationName="保存或新增数据字典",operationType=0)
	public JSONObject  doAddorUpdate(HttpServletRequest request, Sys_Base_DataDictionary sys_Base_DataDictionary ) {
		JSONObject jsonObject=new JSONObject();
		String msg="";
		String parent_id=request.getParameter("parent_id");
		if (parent_id.equals("")) {
			sys_Base_DataDictionary.setParent_DataDictionary(null);
		}else{
			sys_Base_DataDictionary.setParent_DataDictionary(parent_id);
		}
		
		if (sys_Base_DataDictionary.getDataDicId()==null||"".equals(sys_Base_DataDictionary.getDataDicId())) {
			try {
				dataDictionaryService.save(sys_Base_DataDictionary);
				msg= DataStateTypeEnum.ADD_SUCCESS.getMessage();
			} catch (Exception e) {
				msg=DataStateTypeEnum.ADD_ERROR.getMessage();
				// TODO: handle exception
			}
		}else{
			try {
				dataDictionaryService.update(sys_Base_DataDictionary);
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
	 * 删除数据字典
	 * @param request
	 * @param sys_Base_DataDictionary
	 * @param jsonObject
	 * @return
	 */
	@RequestMapping(params = "doDelete")
	@ResponseBody
	@Log(operationName="删除数据字典",operationType=0)
	 public JSONObject doDelete(HttpServletRequest request, Sys_Base_DataDictionary sys_Base_DataDictionary){
		 JSONObject jsonObject=new  JSONObject();
		 sys_Base_DataDictionary=dataDictionaryService.get(Sys_Base_DataDictionary.class, sys_Base_DataDictionary.getDataDicId());
	
		 if (sys_Base_DataDictionary.getParent_DataDictionary()==null) {
			 try {
				 dataDictionaryService.batchDelete(sys_Base_DataDictionary);
				 jsonObject.put("msg",  DataStateTypeEnum.DELETE_SUCCESS.getMessage());
			} catch (Exception e) {
				jsonObject.put("msg",  DataStateTypeEnum.DELETE_ERROR.getMessage());
				// TODO: handle exception
			}
		}else{
			 try {
				 sys_Base_DataDictionary.setState(0);
				 dataDictionaryService.update(sys_Base_DataDictionary);
				 jsonObject.put("msg",  DataStateTypeEnum.DELETE_SUCCESS.getMessage());
			} catch (Exception e) {
				jsonObject.put("msg",  DataStateTypeEnum.DELETE_ERROR.getMessage());
				// TODO: handle exception
			}
		}
			
		
		 return jsonObject;
		 
	 }
	/**
	 * 获取一级字典分类
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "getParentDataDicList")
	@ResponseBody
	public JSONArray getParentDataDicList(HttpServletRequest request){
		return dataDictionaryService.getParentDataDicList();
	}
}
