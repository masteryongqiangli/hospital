package system.web.service.base.datadictionary;

import java.io.Serializable;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import system.core.service.CommonServiceI;
import system.web.entity.base.Sys_Base_DataDictionary;
import system.web.entity.base.Sys_Base_User;

public interface DataDictionaryServiceI extends CommonServiceI {
	/**
	 * 获取一级字典分类
	 * @return
	 */
	public  JSONArray getParentDataDicList();
	/**
	 * 获取treegrid数据
	 * @param parms
	 * @return
	 */
	  public JSONObject getTreeGridJson(Map<String, String> parms);
	boolean batchDelete(Sys_Base_DataDictionary sys_Base_DataDictionary);
 
}
