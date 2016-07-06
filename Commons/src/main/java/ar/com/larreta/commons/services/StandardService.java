package ar.com.larreta.commons.services;

import java.util.Collection;
import java.util.Map;

import org.hibernate.criterion.Order;

import ar.com.larreta.commons.AppObject;
import ar.com.larreta.commons.domain.Entity;
import ar.com.larreta.commons.persistence.dao.StandardDAO;
import ar.com.larreta.commons.persistence.dao.args.CountArguments;
import ar.com.larreta.commons.services.args.ServiceInfo;

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
	public Entity getEntity(Class entityType, Long id);
	public Entity getEntity(Entity entity);
	public Entity getEntity(Entity entity, Collection<String> properties);
	public Entity getEntity(Entity entity, Collection<String> properties, Collection<String> projectedCollections);
	public Boolean isExist(Entity entity, String field);
	public Entity getEntity(Entity entity, String field);
	public Collection load(Class entityType);
	public Collection load(Class entityType, Integer firstResult, Integer maxResults, Order order, Map<String, Object> filters);
	public Collection load(Class entityType, Integer firstResult, Integer maxResults, Order order, Map<String, Object> filters, Collection<String> lazyProperties);
	public ServiceInfo loadServiceInfo(Class entityType);
	public ServiceInfo loadServiceInfo(Class entityType, Integer firstResult, Integer maxResults, Order order, Map<String, Object> filters);
	public ServiceInfo loadServiceInfo(Class entityType, Integer firstResult, Integer maxResults, Order order, Map<String, Object> filters, Collection<String> lazyProperties);
	public Long count(Class entityType);
	public Long count(CountArguments arguments);
	public Long getMaxId(Class type);
}
