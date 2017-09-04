package system.web.dao.base.impl.baseuser;

import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import net.sf.json.JSONObject;
import sun.security.action.GetBooleanAction;
import system.core.dao.impl.BaseDaoImpl;
import system.core.util.CriteriaPageUtil;
import system.core.util.ResourceUtil;
import system.core.util.StringUtil;
import system.web.dao.base.baseuser.BaseUserDaoI;
import system.web.entity.base.Sys_Base_Menu;
import system.web.entity.base.Sys_Base_Role;
import system.web.entity.base.Sys_Base_Role_Menu;
import system.web.entity.base.Sys_Base_Role_User;
import system.web.entity.base.Sys_Base_User;
import system.web.entity.base.Sys_User;
@Repository
public class BaseUserDaoImpl extends BaseDaoImpl implements BaseUserDaoI{
	@Override
	public Sys_Base_User login(Sys_Base_User sys_Base_User) {
		List <Sys_Base_User> sys_Base_Users=findByProperty(Sys_Base_User.class, "userName", sys_Base_User.getUserName())  ;
		 if (sys_Base_Users.size()>0)  {
			return sys_Base_Users.get(0);
		}else{
			return null;
		}
		// TODO Auto-generated method stub
		
	}
	
	public  JSONObject getUsers(Map<String, String> parms) {
		Criteria criteria = super.createCriteria(Sys_Base_User.class);
		criteria.add(Restrictions.eq("state", 1));
		return CriteriaPageUtil.getPageJson(criteria, parms);
	}
	
	public JSONObject getSysUsers(Map<String, String> parms){
		StringBuffer sql = getSysUserSql();
		if (StringUtil.isNotEmpty(parms.get("userName"))) {
			sql.append(" and a.userName like '%"+parms.get("userName")+"%'");
		}
		if (StringUtil.isNotEmpty(parms.get("realName"))) {
			sql.append(" and a.realName like '%"+parms.get("realName")+"%'");
		}
		 
		SQLQuery query = this.getSession().createSQLQuery(sql.toString());
		
		query.addEntity(Sys_User.class);
		
		return CriteriaPageUtil.getPageJson(query, parms);
	}
 
	public List getSys_UserByUserId(String userId){
		StringBuffer sql = getSysUserSql();
		sql.append(" and a.userId ='"+userId+"'");
		SQLQuery query = this.getSession().createSQLQuery(sql.toString());
		
		query.addEntity(Sys_User.class);
		return query.list();
	}
	public List getSys_UserByUserName(String userName){
		StringBuffer sql = getSysUserSql();
		sql.append(" and a.userName ='"+userName+"'");
		SQLQuery query = this.getSession().createSQLQuery(sql.toString());
		
		query.addEntity(Sys_User.class);
		return query.list();
	}
	public List<Sys_Base_Role_User> getuserRoles(Sys_Base_User sys_Base_User) {
		 
		return super.createCriteria(Sys_Base_Role_User.class).add(Restrictions.eq("baseuser", sys_Base_User)).list();
	}
	 public void deleteRoleUsersbyUser(Sys_Base_User sys_Base_User){
		   String hql=" delete Sys_Base_Role_User where baseuser =:baseuser";
			 Session session=getSession() ;
			Query  query=session.createQuery(hql);
			query.setParameter("baseuser",sys_Base_User);
			  super.doUpdateHql(session,query);
	   }
	public StringBuffer getSysUserSql(){
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT a.userId, a.userName, a.idCardNumber, a.gener, d.text as village,CONVERT(varchar(100), a.birthDate, 23) AS birthDate ,");
		sql.append( " CASE WHEN a.birthDate IS NULL THEN 0 ELSE year(GETDATE()) - LEFT(a.birthDate, 4) END AS age,");
		sql.append( " a.realName, a.email, a.phone, ");
		sql.append( " STUFF(( SELECT ',' + roleId FROM Sys_Base_Role WHERE roleId = c.roleId ), 1, 1, '') AS roleIdList ,");
		sql.append( " STUFF(( SELECT ',' + roleCode FROM Sys_Base_Role WHERE roleId = c.roleId ), 1, 1, '') AS roleCodeList, ");
		sql.append( " STUFF(( SELECT ',' + roleName FROM Sys_Base_Role WHERE roleId = c.roleId ), 1, 1, '') AS roleNameList ");
		sql.append( " FROM Sys_Base_User a ");
		sql.append( " LEFT JOIN Sys_Base_Role_User b ON a.userId = b.userId ");
		sql.append( " LEFT JOIN Sys_Base_Role c ON b.roleId = c.roleId");
		sql.append(" left join Sys_Base_DataDictionary d on d.dataDicId = a.village_dataDicId where 1=1 and a.state=1");
		
		return sql;
	}

	@Override
	public List<Map<String, Object>> getHomeData() {
		// TODO Auto-generated method stub
		StringBuffer sql = new StringBuffer();
		sql.append(" DECLARE @maxDate date;    ");
		sql.append(" DECLARE @minDate date; ");
		sql.append(" set @maxDate=( select max(CONVERT(varchar(100),  creatDate , 23)) maxDate from  Sys_Buss_Evaluation   );");
		sql.append(" set @minDate=( select min(CONVERT(varchar(100),  creatDate , 23)) minDate from  Sys_Buss_Evaluation   );");
		sql.append(" SELECT count (*) as count, CONVERT(varchar(100),  creatDate , 23) as  date,  @maxDate  as maxDate,@minDate as minDate,DATEDIFF(dy,@minDate ,@maxDate)+1 as days"); 
		sql.append(" , evalType  ");
		sql.append(" FROM  Sys_Buss_Evaluation       GROUP BY CONVERT(varchar(100),  creatDate , 23),evalType   ORDER by  evalType ");
		Session session=this.getSession();
		SQLQuery query = session.createSQLQuery(sql.toString());
		 List<Map<String, Object>>datas= query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
		 session.close();
		return datas;
	}
}
