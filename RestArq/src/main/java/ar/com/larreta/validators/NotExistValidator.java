package ar.com.larreta.validators;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import ar.com.larreta.annotations.Log;
import ar.com.larreta.rest.business.ExistBusiness;
import ar.com.larreta.rest.business.impl.ExistData;
import ar.com.larreta.validators.annotations.NotExist;

public class NotExistValidator implements ConstraintValidator<Annotation, Object> {

	private static @Log Logger LOG;
	
	public static final String BAR = "/";

	@Autowired
	protected ExistBusiness business;
	
	private NotExist notExist;
	
	@Override
	public void initialize(Annotation annotation) {
		notExist = (NotExist) annotation; 
	}

	@Override
	public boolean isValid(Object field, ConstraintValidatorContext context) {
		try {
			Object value = getValue(field);
			if (value==null){
				return Boolean.TRUE;
			}
			
			return !((Boolean) business.execute(new ExistData(notExist.entityType(), notExist.linkedField(), getValue(field))));
		} catch (Exception e){
			LOG.error("Ocurrio un error validando entidad parametrica", e);
		}
		return Boolean.FALSE;
	}

	private Object getValue(Object field)
			throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		Object value = field;
		if (!StringUtils.isEmpty(notExist.field())){
			value = PropertyUtils.getProperty(field, notExist.field());
		}
		return value;
	}


}
