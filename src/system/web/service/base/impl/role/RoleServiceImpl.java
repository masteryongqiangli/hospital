package system.web.service.base.impl.role;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.catalina.filters.AddDefaultCharsetFilter;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.NativeWebRequest;

import com.sun.org.apache.bcel.internal.generic.Select;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import system.core.service.impl.CommonServiceImpl;
import system.core.util.JSONHelper;
import system.core.util.ResourceUtil;
import system.web.dao.base.menu.MenuDaoI;
import system.web.dao.base.role.RoleDaoI;
import system.web.entity.base.Sys_Base_Menu;
import system.web.entity.base.Sys_Base_Role;
import system.web.entity.base.Sys_Base_Role_Menu;
import system.web.entity.base.Sys_Base_Role_User;
import system.web.service.base.role.RoleServiceI;

@Service("roleService")
@Transactional
public class RoleServiceImpl extends CommonServiceImpl implements RoleServiceI {
	@Autowired
	private RoleDaoI roleDaoImpl;
	@Autowired
	private MenuDaoI menuDaoImpl;

	@Override
	public JSONObject getDataGridJson(Map<String, String> parms) {
		return roleDaoImpl.getRoles(parms);
	}

	@Override
	public JSONObject getRoleMenus(Sys_Base_Role sys_Base_Role) {
		List<Sys_Base_Role_Menu> role_Menus = roleDaoImpl.getRoleMenus(sys_Base_Role);
		Map<String, String> map = new HashMap<>();
		List<Sys_Base_Menu> listtotal = menuDaoImpl.getAllMenuList(map);
		List<Sys_Base_Role_Menu> listinrole = new ArrayList<>();
		List<Sys_Base_Menu> listnotinrole = new ArrayList<>();

		for (Sys_Base_Role_Menu role_Menu : role_Menus) {
			listinrole.add(role_Menu);
		}
		for(Sys_Base_Menu sys_Base_Menu:listtotal){
			boolean flag=true;
			for(Sys_Base_Role_Menu inrole:listinrole){
				if (inrole.getMenu().getMenuId().equals(sys_Base_Menu.getMenuId())) {
					flag=false;
					break;
				}
			}
			if (flag) {
				listnotinrole.add(sys_Base_Menu);
			}
			
		}
		JSONObject jsonObject=new JSONObject();
		jsonObject.put("In", JSONHelper.parseListtToJSONArray(listinrole));
		jsonObject.put("NotIn", JSONHelper.parseListtToJSONArray(listnotinrole));
		return jsonObject;
	}

	@Override
	public void saveRoleMenus(Sys_Base_Role sys_Base_Role, String inRole) {
		roleDaoImpl.deleteRoleMenusbyRole(sys_Base_Role);
		Map<String, String> map = new HashMap<>();
		List<Sys_Base_Menu> listtotal = menuDaoImpl.getAllMenuList(map);
		List<Sys_Base_Menu> listinrole = new ArrayList<>();
		List<Sys_Base_Role_Menu> sys_Base_Role_Menus=new ArrayList<>();
		JSONArray jsonArray=JSONArray.fromObject(inRole);
		for(int i=0;i<jsonArray.size();i++){
			JSONObject obj=JSONObject.fromObject(jsonArray.get(i));
			for(Sys_Base_Menu sys_Base_Menu:listtotal){
				if (obj.getString("id").equals(sys_Base_Menu.getMenuId())&&!listinrole.contains(sys_Base_Menu)) {
					listinrole.add(sys_Base_Menu);
					Sys_Base_Role_Menu sys_Base_Role_Menu=new Sys_Base_Role_Menu();
					sys_Base_Role_Menu.setMenu(sys_Base_Menu);
					sys_Base_Role_Menu.setRole(sys_Base_Role);
					if (obj.get("addAuth")!=null) {
						sys_Base_Role_Menu.setAddAuth(obj.getBoolean("addAuth"));
						sys_Base_Role_Menu.setDelAuth(obj.getBoolean("delAuth"));
						sys_Base_Role_Menu.setUpdateAuth(obj.getBoolean("updateAuth"));
						sys_Base_Role_Menu.setLookAuth(obj.getBoolean("lookAuth"));
					}
					sys_Base_Role_Menus.add(sys_Base_Role_Menu);
					break;
				}
				}
		}
			 
			
		roleDaoImpl.batchsave(sys_Base_Role_Menus);
		// TODO Auto-generated method stub
		
	}

	@Override
	public JSONObject getListAuth(String url) {
		List<Sys_Base_Role_Menu> list=roleDaoImpl.getListAuth(ResourceUtil.getSys_User(), url);
		if (list.size()>0) {
			return JSONObject.fromObject(list.get(0));
		}else{
			return null;
		}
		
	}

	@Override
	public JSONArray getRoleList() {
		return JSONHelper.parseListtToJSONArray(roleDaoImpl.getAll(Sys_Base_Role.class));
	}

	 

}
