package ar.com.larreta.screens;

import java.lang.reflect.Constructor;

import javax.persistence.Basic;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang3.StringUtils;

import ar.com.larreta.commons.AppObject;
import ar.com.larreta.commons.AppObjectImpl;
import ar.com.larreta.commons.domain.ParametricEntity;
import ar.com.larreta.commons.persistence.dao.impl.Whereable;

/**
 * Representa el tipo de filtro que se puede aplicar sobre una columna 
 */
@Entity
@Table(name = "filterMatchMode")
@DiscriminatorColumn(name = "type")
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
public abstract class FilterMatchMode extends ParametricEntity implements Whereable {
	
	private AppObject appObject = new AppObjectImpl(FilterMatchMode.class);
	
	private String valueType;
	private Object value;

	@Basic
	public String getValueType() {
		return valueType;
	}

	public void setValueType(String valueType) {
		this.valueType = valueType;
	}
	
	@Transient
	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		try {
			if ((value!=null) && (valueType!=null)){
				Class type = getClass().getClassLoader().loadClass(valueType);
				Constructor constructor = type.getConstructor(value.getClass());
				value = constructor.newInstance(value);
			}
		} catch (Exception e){
			appObject.getLog().error("Ocurrio un error seteando valor", e);
		}
		this.value = value;
	}

}
