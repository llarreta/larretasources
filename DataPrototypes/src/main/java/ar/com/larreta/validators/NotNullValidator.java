package ar.com.larreta.validators;

import java.lang.annotation.Annotation;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

public class NotNullValidator implements ConstraintValidator<Annotation, Object> {

	@Autowired
	protected ServletContext context;
	
	@Autowired
	protected HttpServletRequest request;
	
	@Override
	public void initialize(Annotation annotation) {
		annotation.getClass();
	}

	@Override
	public boolean isValid(Object field, ConstraintValidatorContext context) {
		return false;
	}

}
