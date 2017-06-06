package ar.com.larreta.validators;

import java.lang.annotation.Annotation;
import java.util.Collection;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import ar.com.larreta.annotations.Log;
import ar.com.larreta.validators.annotations.OnlyOne;

public class OnlyOneValidator implements ConstraintValidator<Annotation, Object> {
	
	private static @Log Logger LOG;	
	
	@Autowired
	protected ServletContext context;
	
	@Autowired
	protected HttpServletRequest request;
	
	private OnlyOne onlyOne;
	
	@Override
	public void initialize(Annotation annotation) {
		onlyOne = (OnlyOne) annotation; 
	}

	@Override
	public boolean isValid(Object object, ConstraintValidatorContext context) {
		try {
			Object fieldValue = BeanUtils.getProperty(object, onlyOne.field());
			Object linkedFieldValue = BeanUtils.getProperty(object, onlyOne.linkedField());
			
			Boolean fieldValueIsNull = (fieldValue==null) || ((fieldValue instanceof String) && StringUtils.isEmpty((String) fieldValue));
			Boolean linkedFieldValueIsNull = (linkedFieldValue==null) || ((linkedFieldValue instanceof String) && StringUtils.isEmpty((String) linkedFieldValue));
			
			return (
					(!onlyOne.required() && (fieldValueIsNull && linkedFieldValueIsNull)) || 
					(onlyOne.required() && (fieldValueIsNull && !linkedFieldValueIsNull)) || 
					(onlyOne.required() && (!fieldValueIsNull && linkedFieldValueIsNull)) 
					);
		} catch (Exception e){
			LOG.error("Ocurrio un error validando onlyOne", e);
		}
		return Boolean.FALSE;
	}
}
