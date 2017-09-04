package system.web.dao.base.impl.menu;

import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.context.annotation.EnableLoadTimeWeaving;
import org.springframework.stereotype.Repository;

import system.core.dao.impl.BaseDaoImpl;
import system.core.util.ResourceUtil;
import system.core.util.StringUtil;
import system.web.dao.base.menu.MenuDaoI;
import system.web.entity.base.Sys_Base_DataDictionary;
import system.web.entity.base.Sys_Base_Menu;
import system.web.entity.base.Sys_Base_Role;

@Repository
public class MenuDaoImpl extends BaseDaoImpl implements MenuDaoI {

	@SuppressWarnings("unchecked")
	@Override
	public <T> List<Sys_Base_Menu> getUserMenuList() {
		StringBuffer sql =new StringBuffer(); 
		sql.append(" SELECT DISTINCT a.* FROM Sys_Base_Menu a ");
		sql.append(" LEFT JOIN  sys_Base_Role_Menu b ON a.menuId=b.menuId ");
		sql.append(" LEFT JOIN Sys_Base_Role c ON b.roleId=c.roleId ");
		sql.append(" LEFT JOIN Sys_Base_Role_User d ON c.roleId=d.roleId ");
		sql.append(" LEFT JOIN Sys_Base_User e ON d.userId=e.userId WHERE a.state=1 and  e.userId='");
		sql.append( ResourceUtil.getSys_User().getUserId() + "' order by a.parent_menu_menuId,a.orderNum");
		SQLQuery query = this.getSession().createSQLQuery(sql.toString());
		query.addEntity(Sys_Base_Menu.class);
		return query.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> List<Sys_Base_Menu> getAllMenuList(Map<String, String> parms) {
		Criteria criteria = super.createCriteria(Sys_Base_Menu.class);
		 criteria.add(Restrictions.eq("state", 1));
		 
		 criteria .addOrder(Order.asc("parent_menu")).addOrder(Order.asc("orderNum"));
		return criteria.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> List<Sys_Base_Menu> getParentMenuList() {
		return  super.createCriteria(Sys_Base_Menu.class).add(Restrictions.isNull("parent_menu")).add(Restrictions.eq("state", 1)).list();
	}
	public int  batchDelete(Sys_Base_Menu sys_Base_Menu){
		String hql=" update Sys_Base_Menu s set s.state=0 where s.parent_menu =:parent_menu or s.menuId =:menuId ";
		 Session session=getSession() ;
		Query  query=session.createQuery(hql);
		query.setParameter("parent_menu",sys_Base_Menu.getParent_menu());
		query.setParameter("menuId",sys_Base_Menu.getMenuId());
		return super.doUpdateHql(session,query);
	}
}
