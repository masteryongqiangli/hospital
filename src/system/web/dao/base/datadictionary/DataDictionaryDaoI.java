package system.web.dao.base.datadictionary;

import java.util.List;
import java.util.Map;

import system.core.dao.BaseDaoI;
import system.web.entity.base.Sys_Base_DataDictionary;

public interface DataDictionaryDaoI extends BaseDaoI {
	 public  <T> List<Sys_Base_DataDictionary> getParentDataDicList();
	 public  <T> List<Sys_Base_DataDictionary> getAllDataDicList(Map<String, String> parms);
	public int batchDelete(Sys_Base_DataDictionary sys_Base_DataDictionary);
	 public Map<String,List<Sys_Base_DataDictionary>> getSelects(String [] codes);
}
