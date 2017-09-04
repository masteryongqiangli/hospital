package system.core.service.impl;

import java.io.Serializable;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import system.core.dao.BaseDaoI;
import system.core.service.CommonServiceI;
@Service("commonService")
public class CommonServiceImpl implements CommonServiceI {
	 @Autowired
	public BaseDaoI baseDaoImpl;
	  
	@Override
	public <T> Serializable save(T entity) {
		baseDaoImpl.save(entity);
		return null;
	}

	@Override
	public <T> T get(Class<T> entityClass, Serializable id) {
		return baseDaoImpl.get(entityClass, id);
		// TODO Auto-generated method stub
	}
	@Override
	public <T> List<T> findByProperty(Class<T> entityClass, String propertyName, Object value ) {
		return baseDaoImpl.findByProperty(entityClass, propertyName, value);
		// TODO Auto-generated method stub
	}

	@Override
	public <T> Serializable update(T entity) {
		baseDaoImpl.update(entity);
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T> void batchsave(List<T> list) {
		 baseDaoImpl.batchsave(list);
	}

	@Override
	public void deleteByProperty(String tableName, String propertyName, String value) {
		baseDaoImpl.deleteByProperty(tableName, propertyName, value);
		
	}

 
}
