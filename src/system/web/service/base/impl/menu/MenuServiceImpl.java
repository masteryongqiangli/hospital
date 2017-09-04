package system.web.service.base.impl.menu;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.eclipse.jdt.internal.compiler.tool.EclipseCompiler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import system.core.annotation.Log;
import system.core.dao.impl.BaseDaoImpl;
import system.core.service.impl.CommonServiceImpl;
import system.web.dao.base.baseuser.BaseUserDaoI;
import system.web.dao.base.menu.MenuDaoI;
import system.web.entity.base.Sys_Base_DataDictionary;
import system.web.entity.base.Sys_Base_Menu;
import system.web.entity.base.Sys_Base_User;
import system.web.service.base.baseuser.BaseUserServiceI;
import system.web.service.base.menu.MenuServiceI;



/**
 * 
 * @author renrti
 *
 */
@Service("menuService")
@Transactional
public class MenuServiceImpl extends CommonServiceImpl implements MenuServiceI {
	@Autowired
    private MenuDaoI menuDaoImpl;

	@Override
	public JSONArray getUserMenuList() {
		JSONArray jsonArray=new JSONArray();
		List<Sys_Base_Menu> list=menuDaoImpl.getUserMenuList();
		for (int i = 0; i < list.size(); i++) {
			JSONObject jo1 = new JSONObject();
			if (list.get(i).getParent_menu()==null) {
				JSONArray children=new JSONArray();
				for (int j = i; j < list.size(); j++) {
					if (list.get(j).getParent_menu()!=null&&list.get(j).getParent_menu().equals(list.get(i))) {
						JSONObject jsonObject = new JSONObject();
						jsonObject.put("menuId", list.get(j).getMenuId());
						jsonObject.put("menuName",list.get(j).getMenuName());
						jsonObject.put("menuUrl",list.get(j).getMenuUrl());
						//jsonObject.put("iconPath",list.get(j).getIcon().getUploadFile().getPath());
						children.add(jsonObject);
					}
				}
				jo1.put("menuId", list.get(i).getMenuId());
				jo1.put("menuName",list.get(i).getMenuName());
				jo1.put("menuUrl",list.get(i).getMenuUrl());
				//jo1.put("iconPath",list.get(i).getIcon().getUploadFile().getPath());
				jo1.put("children", children);
				jsonArray.add(jo1);
			}
			
		}
		return jsonArray;
	}

	@Override
	public JSONObject getTreeGridJson(Map<String, String> parms) {
		JSONObject data = new JSONObject();
		JSONArray rows = new JSONArray();
		List<Sys_Base_Menu> list=menuDaoImpl.getAllMenuList(parms);
		for (int i = 0; i < list.size(); i++) {
			JSONObject child = new JSONObject();
			if (list.get(i).getParent_menu() == null) {
				JSONArray jsonArray = new JSONArray();
				int count = 1;
				for (int j = i; j < list.size(); j++) {
					if (list.get(j).getParent_menu() != null
							&& list.get(j).getParent_menu().equals(list.get(i))) {
						JSONObject jsonObject = new JSONObject();
						jsonObject.put("No", i + "" + count);
						jsonObject.put("MenuId", list.get(j).getMenuId());
						jsonObject.put("MenuName", list.get(j).getMenuName());
						jsonObject.put("MenuUrl", list.get(j).getMenuUrl());
						jsonObject.put("Level", "二级菜单");
						count++;
						jsonArray.add(jsonObject);
					}
				}
				child.put("No", i);
				child.put("MenuId", list.get(i).getMenuId());
				child.put("MenuName", list.get(i).getMenuName());
				child.put("MenuUrl", list.get(i).getMenuUrl());
				child.put("Level", "一级菜单");
				child.put("children", jsonArray);
				if (jsonArray.size() > 0) {
					child.put("state", "closed");
				}
				rows.add(child);
			}
		}
		data.put("total", rows.size());
		data.put("rows", rows);
		return data;
	}

	@Override
	public JSONArray getParentMenuList() {
		JSONArray jsonArray = new JSONArray();
		List<Sys_Base_Menu> list = menuDaoImpl.getParentMenuList();
		for (int i = 0; i < list.size(); i++) {
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("value", list.get(i).getMenuId());
			jsonObject.put("text", list.get(i).getMenuName());
			jsonArray.add(jsonObject);
		}
		return jsonArray;
	}
	@Override
	public boolean batchDelete(Sys_Base_Menu sys_Base_Menu) {
		return menuDaoImpl.batchDelete(sys_Base_Menu) > 0 ? true : false;
	}
}
