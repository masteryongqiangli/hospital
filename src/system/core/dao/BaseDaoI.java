package system.core.dao;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import com.sun.java_cup.internal.runtime.virtual_parse_stack;



public interface BaseDaoI {
	/**
	 * 保存实体
	 * @param entity
	 * @return
	 */
	public <T> Serializable save(T entity);
	public <T> void batchsave(List<T> list);
	public <T> void  update( T   entity);
	
	public <T> List<T> getAll(Class<T> entityClass);
	/**
	 * 根据Id获取对象。
	 */
	public <T> T get(Class<T> entityClass, final Serializable id) ;
	
	public <T> List<T> findByProperty(Class<T> entityClass, String propertyName, Object value);
	/**
	 * 根据属性获取对象列表。
	 */
	public <T> List<T> findByPropertyIsNull(Class<T> entityClass, String propertyName);
	public <T> List<T> findByPropertyIsNotNull(Class<T> entityClass, String propertyName);
	
	public void deleteByProperty(String tableName, String propertyName, String value);


	public int doUpdateHql(Session session, Query queryupdate);

	 



	
}
