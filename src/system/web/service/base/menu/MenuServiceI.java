package system.web.service.base.menu;


import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import system.core.service.CommonServiceI;
import system.web.entity.base.Sys_Base_Menu;


public interface MenuServiceI extends CommonServiceI {
	public JSONArray getUserMenuList();
	public JSONObject getTreeGridJson(Map<String, String> parms);
	/**
	 * 获取一级字典分类
	 * @return
	 */
	public  JSONArray getParentMenuList();
	
	public boolean batchDelete(Sys_Base_Menu sys_Base_Menu);
}
