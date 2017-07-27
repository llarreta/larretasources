package ar.com.larreta.stepper.validators;

import java.lang.annotation.Annotation;
import java.util.Collection;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import ar.com.larreta.stepper.validators.annotations.Size;

@Transactional
public class SizeValidator implements ConstraintValidator<Annotation, Object> {

	public static final String BAR = "/";

	@Autowired
	protected ServletContext context;
	
	@Autowired
	protected HttpServletRequest request;
	
	private Size size;
	
	@Override
	public void initialize(Annotation annotation) {
		size = (Size) annotation; 
	}

	@Override
	public boolean isValid(Object field, ConstraintValidatorContext context) {
		return  ((field!=null)&&(field instanceof Collection) && (((Collection) field).size()>=size.mayorOrEqual()));
	}

}
