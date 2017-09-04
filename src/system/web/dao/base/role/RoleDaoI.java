package system.web.dao.base.role;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;
import system.core.dao.BaseDaoI;
import system.web.entity.base.Sys_Base_Role;
import system.web.entity.base.Sys_Base_Role_Menu;
import system.web.entity.base.Sys_User;

public interface RoleDaoI extends BaseDaoI {
	 
	public JSONObject   getRoles(Map<String, String> parms);
	public List<Sys_Base_Role_Menu> getRoleMenus(Sys_Base_Role sys_Base_Role);
	public void deleteRoleMenusbyRole(Sys_Base_Role sys_Base_Role);
	public  List<Sys_Base_Role_Menu> getListAuth(Sys_User sys_User,String url);
	public <T> List<T> findByPropertyList(Class<T> entityClass, String propertyName, Collection<Sys_Base_Role> values);
}
