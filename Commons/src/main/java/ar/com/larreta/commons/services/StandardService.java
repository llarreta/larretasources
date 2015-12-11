package ar.com.larreta.commons.services;

import java.util.Collection;
import java.util.Map;

import org.hibernate.criterion.Order;

import ar.com.larreta.commons.AppObject;
import ar.com.larreta.commons.domain.Entity;
import ar.com.larreta.commons.exceptions.NotImplementedException;
import ar.com.larreta.commons.persistence.dao.StandardDAO;

public interface StandardService extends AppObject {
	
	public StandardDAO getDao();
	public void setDao(StandardDAO dao);
	public void save(Entity entity);
	public void update(Entity entity);
	public void delete(Entity entity);
	public void saveOrUpdate(Entity entity);
	public void saveOrUpdate(Collection entities);
	public void saveIfNotExist(Entity entity, String field);
	public Boolean isExist(Entity entity);
	public Entity getEntity(Entity entity);
	public Boolean isExist(Entity entity, String field);
	public Entity getEntity(Entity entity, String field);
	public Collection load(Class entityType);
	public Collection load(Class entityType, Integer firstResult, Integer maxResults, Order order, Map<String, Object> filters);
	public Long count(Class entityType);
	/*public Collection load() throws NotImplementedException;
	public Collection load(Integer firstResult, Integer maxResults, Order order, Map<String, Object> filters) throws NotImplementedException;
	public Long count() throws NotImplementedException;*/
	//public Class getEntityClass() throws NotImplementedException;
	public Long getMaxId(Class type);
}
