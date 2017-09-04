package system.web.service.base.role;

import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import system.core.service.CommonServiceI;
import system.web.entity.base.Sys_Base_Role;

public interface RoleServiceI extends CommonServiceI {
	
	
	public JSONObject getDataGridJson(Map<String, String> parms);
	
	public JSONObject getRoleMenus(Sys_Base_Role sys_Base_Role);
	
	
	public void saveRoleMenus(Sys_Base_Role sys_Base_Role,String  inRole);
	public JSONObject getListAuth(String url);
	public JSONArray getRoleList();
}
