package system.web.dao.base.menu;

import java.util.List;
import java.util.Map;

import system.core.dao.BaseDaoI;
import system.web.entity.base.Sys_Base_DataDictionary;
import system.web.entity.base.Sys_Base_Menu;

public interface MenuDaoI extends BaseDaoI {
	public  <T> List<Sys_Base_Menu> getUserMenuList();
	
	public <T>List<Sys_Base_Menu> getAllMenuList(Map<String, String> parms);
	public <T> List<Sys_Base_Menu> getParentMenuList() ;
	public int  batchDelete(Sys_Base_Menu sys_Base_Menu);
}
