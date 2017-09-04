package system.core.service;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

public interface CommonServiceI {

	/**
	 * 保存实体
	 * @param entity
	 * @return
	 */
	public <T> Serializable save(T entity);
	
    public <T> void batchsave(List<T> list)  ;
	/**
	 * 保存实体
	 * @param entity
	 * @return
	 */
	public <T> Serializable update(T entity);
 
	/**
	 * 根据Id获取对象。
	 */
	public <T> T get(Class<T> entityClass, final Serializable id) ;
	
 
	public <T> List<T> findByProperty(Class<T> entityClass, String propertyName, Object value);
	
	 
	/**
	 * 根据属性获取对象列表。
	 */
	public void deleteByProperty(String tableName, String propertyName, String value);
	/**
	 * 根据属性获取对象列表。
	 */
}
