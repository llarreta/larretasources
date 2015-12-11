package ar.com.larreta.commons.persistence.dao.impl;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.StringUtils;

import ar.com.larreta.commons.domain.Entity;

public class ProjectedProperty extends QueryElement {

	public ProjectedProperty(LoadArguments args, String name){
		setArgs(args);
		setName(name);
	}
	
	public String getShortName(){
		Integer index = name.lastIndexOf(StandardDAOImpl.DOT);
		if (index>=0){
			return name.substring(index+1);
		}
		return name;
	}
	
	public String getPrefix(){
		Integer index = name.lastIndexOf(StandardDAOImpl.DOT);
		if (index>=0){
			return name.substring(0,index);
		}
		return StringUtils.EMPTY;
	}
	
	/**
	 * Retonra vacio ya que en este caso no necesita joinnear nada
	 * @return
	 */
	public String getInnerJoin(MainEntity mainEntity){
		return StringUtils.EMPTY;
	}
	
	@Override
	public int hashCode() {
		return name.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof ProjectedProperty) {
			ProjectedProperty projected = (ProjectedProperty) obj;
			return name.equals(projected.getName());
		}
		return Boolean.FALSE;
	}	

	public Boolean initialize(Entity entity){
		return Boolean.FALSE;
	}
	
	public Collection newInstance(){
		return new ArrayList();
	}
	
	public void setValue(Entity toSet, Object value) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException{
		PropertyUtils.setProperty(toSet, getShortName(), value);
	}

}
