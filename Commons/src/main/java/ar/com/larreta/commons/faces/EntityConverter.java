package ar.com.larreta.commons.faces;

import java.util.HashMap;
import java.util.Map;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import ar.com.larreta.commons.AppManager;
import ar.com.larreta.commons.AppObjectImpl;
import ar.com.larreta.commons.domain.Entity;
import ar.com.larreta.commons.exceptions.NoEntityClassFoundException;
import ar.com.larreta.commons.services.StandardService;

public class EntityConverter extends AppObjectImpl implements Converter  {
	
	@Autowired
	@Qualifier("standardService")
	private StandardService standardService;

	private Class entityClass;
	
	private Map<Class, ValueAsObject> valuesAsObjects;
	private Map<Class, ValueAsString> valuesAsStrings;
	
	public EntityConverter(){
		valuesAsObjects = new HashMap<Class, ValueAsObject>();
		valuesAsObjects.put(Entity.class, new EntityValueAsObject());
		valuesAsObjects.put(Object.class, new StandardValueAsObject());
		valuesAsObjects.put(String.class, new EntityValueAsObject());
		
		valuesAsStrings = new HashMap<Class, ValueAsString>();
		valuesAsStrings.put(Entity.class, new EntityValueAsString());
		valuesAsStrings.put(Object.class, new StandardValueAsString());
	}
	
	public ValueAsObject getValueAsObject(Object key){
		ValueAsObject toReturn = null;
		if (key instanceof Class) {
			Class type = (Class) key;

			while ((toReturn=valuesAsObjects.get(type))==null){
				type = type.getSuperclass();
			}
		}
		return toReturn;
	}
	
	public ValueAsString getValueAsString(Object key){
		ValueAsString toReturn = null;
		if (key instanceof Class) {
			Class type = (Class) key;

			while ((toReturn=valuesAsStrings.get(type))==null){
				type = type.getSuperclass();
			}
		}
		return toReturn;
	}
	
	public Class getEntityClass() {
		return entityClass;
	}

	public void setEntityClass(Object entityClass) {
		this.entityClass = entityClass.getClass();
	}
	
	public void setEntityClass(Class entityClass) {
		this.entityClass = entityClass;
	}

	public Entity getEntity() throws InstantiationException, IllegalAccessException, NoEntityClassFoundException{
		if (entityClass!=null){
			return (Entity) entityClass.newInstance();
		}
		throw new NoEntityClassFoundException();		
	}

	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		return getValueAsObject(value.getClass()).getAsObject(this, context, component, value);
	}

	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if (value==null){
			return null;
		}
		return getValueAsString(value.getClass()).getAsString(this, context, component, value);
	}

	
	public StandardService getStandardService() {
		if (standardService==null){
			standardService = AppManager.getInstance().getStandardService();
		}
		return standardService;
	}

	public void setStandardService(StandardService standardService) {
		this.standardService = standardService;
	}
}
