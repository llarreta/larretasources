package ar.com.larreta.screens.services.impl;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

import javax.persistence.Transient;

import org.apache.commons.beanutils.PropertyUtils;
import org.hibernate.criterion.Order;

import ar.com.larreta.commons.AppObjectImpl;
import ar.com.larreta.commons.domain.Entity;
import ar.com.larreta.commons.persistence.dao.StandardDAO;
import ar.com.larreta.commons.persistence.dao.args.CountArguments;
import ar.com.larreta.commons.persistence.dao.args.LoadArguments;
import ar.com.larreta.commons.services.args.ServiceInfo;
import ar.com.larreta.screens.ScreenUtils;
import ar.com.larreta.screens.services.ExpressionService;

public class ExpressionServiceImpl extends AppObjectImpl implements ExpressionService {
	
	private String expression;

	public String getExpression() {
		return expression;
	}

	public void setExpression(String expression) {
		this.expression = ScreenUtils.generateExpression(expression);
	}
	
	@Transient
	public Collection getExpressionValue(){
		return (Collection) ScreenUtils.evaluate(expression);
	}
	

	@Override
	public StandardDAO getDao() {
		return null;
	}

	@Override
	public void setDao(StandardDAO dao) {
	}

	@Override
	public void save(Entity entity) {
		getExpressionValue().add(entity);
	}

	@Override
	public void update(Entity entity) {
		save(entity);
	}

	@Override
	public void delete(Entity entity) {
		getExpressionValue().remove(entity);
	}

	@Override
	public void saveOrUpdate(Entity entity) {
		save(entity);
	}

	@Override
	public void saveOrUpdate(Collection entities) {
		getExpressionValue().addAll(entities);
		
	}

	@Override
	public void saveIfNotExist(Entity entity, String field) {
		if (!isExist((Entity) entity, field)){
			save(entity);
		}
	}

	@Override
	public Boolean isExist(Entity entity) {
		return getExpressionValue().contains(entity);
	}

	@Override
	public Entity getEntity(Class entityType, Long id) {
		Collection entities = getExpressionValue();
		if (entities!=null){
			Iterator it = entities.iterator();
			while (it.hasNext()) {
				Entity entity = (Entity) it.next();
				if ((entity!=null) && (entity.getId().equals(id)) && entity.getClass().equals(entityType)){
					return entity;
				} 
			}
		}
		return null;
	}

	@Override
	public Entity getEntity(Entity entity) {
		Collection entities = getExpressionValue();
		if (entities!=null){
			try{
				Iterator it = entities.iterator();
				while (it.hasNext()) {
					Entity tmp = (Entity) it.next();
					if ((tmp!=null) && (tmp.getId().equals(entity.getId()))){
						return tmp;
					}
				}
			} catch (Exception e){
				getLog().error("Ocurrio un error obteniendo entidad", e);
			}
		}
		return null;	
	}

	@Override
	public Entity getEntity(Entity entity, Collection<String> properties) {
		return getEntity(entity);
	}

	@Override
	public Entity getEntity(Entity entity, Collection<String> properties, Collection<String> projectedCollections) {
		return getEntity(entity);
	}

	@Override
	public Boolean isExist(Entity entity, String field) {
		return (getEntity(entity, field)!=null);
	}

	@Override
	public Entity getEntity(Entity entity, String field) {
		Collection entities = getExpressionValue();
		if (entities!=null){
			try{
				Object value = PropertyUtils.getProperty(entity, field);
				Iterator it = entities.iterator();
				while (it.hasNext()) {
					Entity tmp = (Entity) it.next();
					if (tmp!=null) {
						try {
							Object tmpValue = PropertyUtils.getProperty(tmp, field);
							if ((value!=null) && (value.equals(tmpValue))){
								return tmp;
							}
						} catch (Exception e){
							getLog().error("Ocurrio un error obteniendo entidad", e);
						}
					} 
				}
			} catch (Exception e){
				getLog().error("Ocurrio un error obteniendo entidad", e);
			}
		}
		return null;	
	}

	@Override
	public Collection load(Class entityType) {
		return getExpressionValue();
	}

	@Override
	public Collection load(Class entityType, Integer firstResult, Integer maxResults, Order order,
			Map<String, Object> filters) {
		return getExpressionValue();
	}

	@Override
	public Collection load(Class entityType, Integer firstResult, Integer maxResults, Order order,
			Map<String, Object> filters, Collection<String> lazyProperties) {
		return getExpressionValue();
	}

	@Override
	public ServiceInfo loadServiceInfo(Class entityType) {
		try {
			LoadArguments arguments = new LoadArguments(entityType);
			Collection data = load(entityType);
			return new ServiceInfo(arguments, data);
		} catch (Exception e){
			getLog().error("Ocurrio un error en el loadServiceInfo", e);
		}
		return null;
	}

	@Override
	public ServiceInfo loadServiceInfo(Class entityType, Integer firstResult, Integer maxResults, Order order,
			Map<String, Object> filters) {
		return loadServiceInfo(entityType);
	}

	@Override
	public ServiceInfo loadServiceInfo(Class entityType, Integer firstResult, Integer maxResults, Order order,
			Map<String, Object> filters, Collection<String> lazyProperties) {
		return loadServiceInfo(entityType);
	}

	@Override
	public Long count(Class entityType) {
		Collection value = getExpressionValue();
		return new Long(value.size());
	}

	@Override
	public Long count(CountArguments arguments) {
		Collection value = getExpressionValue();
		return new Long(value.size());
	}

	@Override
	public Long getMaxId(Class type) {
		return null;
	}
}
