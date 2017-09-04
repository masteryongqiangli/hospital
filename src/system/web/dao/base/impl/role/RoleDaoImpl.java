package system.web.dao.base.impl.role;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import net.sf.json.JSONObject;
import system.core.dao.impl.BaseDaoImpl;
import system.core.util.CriteriaPageUtil;
import system.core.util.StringUtil;
import system.web.dao.base.baseuser.BaseUserDaoI;
import system.web.dao.base.role.RoleDaoI;
import system.web.entity.base.Sys_Base_Menu;
import system.web.entity.base.Sys_Base_Role;
import system.web.entity.base.Sys_Base_Role_Menu;
import system.web.entity.base.Sys_Base_User;
import system.web.entity.base.Sys_User;

@Repository
public class RoleDaoImpl extends BaseDaoImpl implements RoleDaoI {

	@Override
	public JSONObject getRoles(Map<String, String> parms) {
		Criteria criteria = super.createCriteria(Sys_Base_Role.class);
		criteria.add(Restrictions.eq("state", 1));
		if (StringUtil.isNotEmpty(parms.get("roleCode"))) {
			criteria.add(Restrictions.like("roleCode","%"+ parms.get("roleCode")+"%"));
		}
		if (StringUtil.isNotEmpty(parms.get("roleName"))) {
			criteria.add(Restrictions.like("roleName","%"+ parms.get("roleName")+"%"));
		}
		return CriteriaPageUtil.getPageJson(criteria, parms);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Sys_Base_Role_Menu> getRoleMenus(Sys_Base_Role sys_Base_Role) {

		return super.createCriteria(Sys_Base_Role_Menu.class).add(Restrictions.eq("role", sys_Base_Role)).list();
	}

	public void deleteRoleMenusbyRole(Sys_Base_Role sys_Base_Role) {
		String hql = " delete Sys_Base_Role_Menu where role =:role";
		Session session = getSession();
		Query query = session.createQuery(hql);
		query.setParameter("role", sys_Base_Role);
		super.doUpdateHql(session, query);
	}

	@Override
	public  List<Sys_Base_Role_Menu> getListAuth(Sys_User sys_User,String url) {
		 StringBuffer sql =new StringBuffer(); 
			sql.append(" SELECT d.* FROM Sys_Base_User   a ");
			sql.append( " LEFT JOIN  Sys_Base_Role_User b ON a.userId=b.userId ");
			sql.append( " LEFT JOIN Sys_Base_Role c ON b.roleId=c.roleId");
			sql.append( " LEFT JOIN Sys_Base_Role_Menu d ON c.roleId=d.roleId ");
			sql.append( " LEFT JOIN Sys_Base_Menu e ON d.menuId=e.menuId ");
			sql.append( " where a.userId='"+sys_User.getUserId()+"' and e.menuUrl like'"+url+"%'");
			SQLQuery query = this.getSession().createSQLQuery(sql.toString()).addEntity(Sys_Base_Role_Menu.class);
			return   query.list();
			 
	}

	@Override
	public <T> List<T> findByPropertyList(Class<T> entityClass, String propertyName, Collection<Sys_Base_Role> values) {
		return   createCriteria(entityClass).add(Restrictions.in(propertyName, values)).list();
	}

}
