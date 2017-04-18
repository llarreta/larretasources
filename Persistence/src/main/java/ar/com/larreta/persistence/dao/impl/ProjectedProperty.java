package ar.com.larreta.persistence.dao.impl;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.StringUtils;

import ar.com.larreta.persistence.dao.args.LoadArguments;
import ar.com.larreta.persistence.model.Entity;

public class ProjectedProperty extends QueryElement {

	private ProjectedCollection projectedCollection;
	
	public ProjectedProperty(LoadArguments args, String name){
		setArgs(args);
		setName(name);
		args.addLeftJoin(name);
	}
	
	public ProjectedProperty(LoadArguments args, String name, Boolean left){
		setArgs(args);
		setName(name);
		if(left){
			args.addLeftJoin(name);
		}else{
			args.addInnerJoin(name);
		}
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
		try {
			PropertyUtils.setProperty(toSet, getShortName(), value);
		} catch (IllegalArgumentException e){
			// Si ocurre esta excepcion es muy probable que se intente setear una collection oculta en una property normal
			if (projectedCollection==null){
				projectedCollection = new ProjectedCollection(getArgs(), getName(), HashSet.class);
			}
			projectedCollection.setValue(toSet, value);
		}
	}

}
