package ar.com.larreta.commons.persistence.dao.impl;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.beanutils.PropertyUtils;

import ar.com.larreta.commons.domain.Entity;
import ar.com.larreta.commons.exceptions.AppException;
import ar.com.larreta.commons.persistence.dao.args.LoadArguments;

public class ProjectedCollection extends ProjectedProperty {

	private Class type;
	private Set<Long> entitiesInitialized = new HashSet<Long>();
	
	public Class getType() {
		return type;
	}

	public void setType(Class type) {
		this.type = type;
	}
	
	public Collection newInstance(){
		try {
			return (Collection) type.newInstance();
		} catch (Exception e) {
			getLog().error(AppException.getStackTrace(e));
		} 
		return new ArrayList();
	}

	public ProjectedCollection(LoadArguments args, String name, Class type) {
		this(args, name, type, Boolean.FALSE);
	}

	public ProjectedCollection(LoadArguments args, String name, Class type, Boolean left) {
		super(args, name);
		this.type = type;
		if (left){
			args.addLeftJoin(name);
		} else {
			args.addInnerJoin(name);
		}
	}
	
	public Boolean initialize(Entity entity){
		Boolean init = entitiesInitialized.contains(entity.getId());
		entitiesInitialized.add(entity.getId());
		return !init;
	}

	@Override
	public void setValue(Entity toSet, Object value) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException{
		if (initialize(toSet)){
			PropertyUtils.setProperty(toSet, getShortName(), newInstance());
		}
		Collection collection = (Collection) PropertyUtils.getProperty(toSet, getShortName());
		collection.add(value);
	}

	@Override
	public String getSymbolicName() {
		if (symbolicName == null) {
			symbolicName = args.getSymbol(name);
			mainReference=Boolean.FALSE;
		}
		return symbolicName;
	}

	
}
