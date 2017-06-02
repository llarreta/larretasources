package ar.com.larreta.validators;

import java.lang.annotation.Annotation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import ar.com.larreta.annotations.Log;
import ar.com.larreta.rest.business.ValidateParametricEntityBusiness;
import ar.com.larreta.rest.business.impl.ValidateParametricEntityData;
import ar.com.larreta.validators.annotations.ValidParametricEntity;

public class ValidParametricEntityValidator implements ConstraintValidator<Annotation, Object> {

	private static @Log Logger LOG;
	
	public static final String BAR = "/";

	@Autowired
	protected ValidateParametricEntityBusiness business;
	
	private ValidParametricEntity validParametricEntity;
	
	@Override
	public void initialize(Annotation annotation) {
		validParametricEntity = (ValidParametricEntity) annotation; 
	}

	@Override
	public boolean isValid(Object field, ConstraintValidatorContext context) {
		if (field==null){
			return Boolean.TRUE;
		}
		try {
			ValidateParametricEntityData data = new ValidateParametricEntityData(validParametricEntity.parametricEntity(), (Long) field);
			return (boolean) business.execute(data);
		} catch (Exception e){
			LOG.error("Ocurrio un error validando entidad parametrica", e);
		}
		return Boolean.FALSE;
	}


}
