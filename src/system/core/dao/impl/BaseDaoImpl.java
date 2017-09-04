package system.core.dao.impl;

import java.io.Serializable;
import java.sql.BatchUpdateException;
import java.util.Collection;
import java.util.List;
import java.util.Properties;

import org.apache.catalina.ant.FindLeaksTask;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.hibernate.loader.plan.exec.process.spi.ReturnReader;
import org.hibernate.sql.Update;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.orm.hibernate4.LocalSessionFactoryBean;
import org.springframework.stereotype.Repository;

import com.sun.org.apache.xalan.internal.xsltc.DOM;
import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils.Commons;

import system.core.dao.BaseDaoI;
import system.web.entity.base.Sys_Base_DataDictionary;
@Repository
public class BaseDaoImpl implements BaseDaoI {
	 
	@Autowired
	@Qualifier("sessionFactory")
	private SessionFactory sessionFactory;
	public Session getSession() {
		// 事务必须是开启的(Required)，否则获取不到
		return sessionFactory.openSession();
	}

	@Override
	public <T> Serializable save(T entity) {
		
		Serializable id ;
		try {
			Session session=getSession();
			Transaction tran=session.beginTransaction();
			id =session.save(entity);
			tran.commit();
			session.flush();
			session.close();
		} catch (RuntimeException e) {
			throw e;
		}
		
		return id;
		// TODO Auto-generated method stub
	}
	
	
public <T> void batchsave(List<T> list) {
		
		Serializable id ;
		try {
			Session session=getSession();
			Transaction tran=session.beginTransaction();
			int i=0;
			 for(T t:list){
				 session.save(t);
				 if (i%25==0) {
					session.flush();
					session.clear();
				}
			 }
			 session.flush();
				tran.commit();
				session.close();
		} catch (RuntimeException e) {
			throw e;
		}
		
		// TODO Auto-generated method stub
	} 

	@Override
	public <T> void  update(T entity) {
		
		 
		try {
			Session session=getSession();
			Transaction tran=session.beginTransaction();
			session.saveOrUpdate(entity);
			tran.commit();
			session.flush();
			session.close();
		} catch (RuntimeException e) {
			throw e;
		}
		
		 
		// TODO Auto-generated method stub
	}
 
	@Override
	public <T> T get(Class<T> entityClass, Serializable id) {
	 
		T t=getSession().get(entityClass, id);
		 
			 return t;

		// TODO Auto-generated method stub
		 
	}
	 
	 
	public <T> List<T> getAll(Class<T> entityClass) {
		 
		 return  createCriteria(entityClass).list();
		 
		// TODO Auto-generated method stub
		 
	}
	public <T> List<T> findByPropertyIsNull(Class<T> entityClass, String propertyName) {
		   return  createCriteria(entityClass).add(Restrictions.isNull(propertyName)).list();
			// TODO Auto-generated method stub
		 
		}
	public <T> List<T> findByPropertyIsNotNull(Class<T> entityClass, String propertyName) {
		   return  createCriteria(entityClass).add(Restrictions.isNotNull(propertyName)).list();
			// TODO Auto-generated method stub
		 
		}
	@SuppressWarnings("unchecked")
	public <T> List<T> findByProperty(Class<T> entityClass, String propertyName, Object value) {
		   return  createCriteria(entityClass).add(Restrictions.eq(propertyName, value)).list();
			// TODO Auto-generated method stub
		 
		}
 

	/**
	 * 创建单一Criteria对象
	 *
	 * @param <T>
	 * @param entityClass
	 * @param criterions
	 * @return
	 */
	public  <T> Criteria createCriteria(Class<T> entityClass) {
		Criteria criteria = getSession().createCriteria(entityClass);
		return criteria;
	}
    @Override
	 public int doUpdateHql(Session session,Query queryupdate){
		 int ret=0;
		 try {
			 Transaction trans=session.beginTransaction();
			
			   ret=queryupdate.executeUpdate();
			 trans.commit();
		} catch (Exception e) {
			// TODO: handle exception
		}
		return  ret;
	 }

	@Override
	public void deleteByProperty(String tableName, String propertyName, String value) {
		Session session=this.getSession();
		  Transaction tran = session.beginTransaction() ;     
	        String sql = String.format("Delete FROM %s Where %s='%s'" ,tableName,propertyName,value);     
	        Query q = session.createSQLQuery(sql)   ;
	        q.executeUpdate() ;     
	        tran.commit() ;     
		// TODO Auto-generated method stub
	}
	 

	 
    
}
