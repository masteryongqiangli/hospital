package system.web.dao.base.impl.datadictionary;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import system.core.dao.impl.BaseDaoImpl;
import system.core.util.ResourceUtil;
import system.core.util.StringUtil;
import system.web.dao.base.datadictionary.DataDictionaryDaoI;
import system.web.entity.base.Sys_Base_DataDictionary;
import system.web.entity.base.Sys_Base_Menu;

@Repository
public class DataDictionaryDaoImpl extends BaseDaoImpl implements DataDictionaryDaoI {
	@SuppressWarnings("unchecked")
	public <T> List<Sys_Base_DataDictionary> getParentDataDicList() {
		return  super.createCriteria(Sys_Base_DataDictionary.class).add(Restrictions.isNull("parent_DataDictionary")).add(Restrictions.eq("state", 1)).list();
	}

	@SuppressWarnings("unchecked")
	public <T> List<Sys_Base_DataDictionary> getAllDataDicList(Map<String, String> parms) {
		Criteria criteria = super.createCriteria(Sys_Base_DataDictionary.class);
		if (StringUtil.isNotEmpty(parms.get("code"))) {
			criteria.add(Restrictions.like("code", "%"+parms.get("code")+"%"));
		}
		if (StringUtil.isNotEmpty(parms.get("text"))) {
			criteria.add(Restrictions.like("text", "%"+parms.get("text")+"%"));
		}
		if (StringUtil.isNotEmpty(parms.get("parent_id"))) {
		
			 criteria.add(Restrictions.or( Restrictions.eq("parent_DataDictionary", parms.get("parent_id")),Restrictions.eq("dataDicId", parms.get("parent_id"))));
		 
		}
		return criteria.add(Restrictions.eq("state", 1)).addOrder(Order.asc("parent_DataDictionary"))
				.addOrder(Order.asc("orderNum")).list();
	}
	@Override
	public int  batchDelete(Sys_Base_DataDictionary sys_Base_DataDictionary){
		String hql=" update Sys_Base_DataDictionary s set s.state=0 where s.parent_DataDictionary =:parent_DataDictionary or s.dataDicId =:dataDicId ";
		 Session session=getSession() ;
		Query  query=session.createQuery(hql);
		query.setParameter("parent_DataDictionary",sys_Base_DataDictionary.getDataDicId() );
		query.setParameter("dataDicId",sys_Base_DataDictionary.getDataDicId() );
		return super.doUpdateHql(session,query);
	}
	
	 public Map<String,List<Sys_Base_DataDictionary>> getSelects(String [] codes){
		 Map<String,List<Sys_Base_DataDictionary>> selects=new HashMap<>();
		 StringBuffer sql =new StringBuffer(); 
			sql.append(" SELECT  b.*,a.code as parentCode  FROM Sys_Base_DataDictionary a ");
			sql.append( "LEFT join  Sys_Base_DataDictionary b ON b.parent_DataDictionary=a.dataDicId");
			sql.append( " where a.state=1 AND b.dataDicId IS  not NULL  ");
			if (codes.length>0) {
				sql.append( "AND  (      ");
				 for (int i = 0; i < codes.length; i++) {
					if (i==0) {
						sql.append( "   a.code ='"+codes[i]+"'    ");
					}else{
						sql.append( "   or  a.code ='"+codes[i]+"'     ");
					}
				}
				 sql.append( ") ");
			}
		
			sql.append("order by b.orderNum,a.orderNum");
			SQLQuery query = this.getSession().createSQLQuery(sql.toString());
			 List<Map<String, Object>>datas= query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
		      for(String code :codes){
		    	  List<Sys_Base_DataDictionary> dataDictionaries=new ArrayList<>();
		    	   for(Map<String, Object> select : datas){
		    		   if (code.equals((String)select.get("parentCode"))) {
		    			   Sys_Base_DataDictionary sys_Base_DataDictionary=new Sys_Base_DataDictionary();
		    			   sys_Base_DataDictionary.setDataDicId((String)select.get("dataDicId"));
		    			   sys_Base_DataDictionary.setCode((String)select.get("code"));
		    			   sys_Base_DataDictionary.setText((String)select.get("text"));;
		    			   sys_Base_DataDictionary.setOrderNum((int)select.get("orderNum"));
		    			   sys_Base_DataDictionary.setState( (int)select.get("state"));
		    			   sys_Base_DataDictionary.setVersion( (int)select.get("version"));
		    			   dataDictionaries.add(sys_Base_DataDictionary);
					}
		    	   }
		    	   selects.put(code, dataDictionaries);
		      }
	    	return selects;
	    }
}
