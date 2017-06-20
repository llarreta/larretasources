package ar.com.larreta.validators;

import java.lang.annotation.Annotation;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import ar.com.larreta.validators.annotations.Format;

@Transactional
public class FormatValidator implements ConstraintValidator<Annotation, Object> {

	@Autowired
	protected ServletContext context;
	
	@Autowired
	protected HttpServletRequest request;

	private Format format;
	
	@Autowired
	private FormatValidatorFactory factory;
	
	@Override
	public void initialize(Annotation annotation) {
		format = (Format) annotation; 
		factory.setPattern(format.formatType(), format.pattern());
	}

	@Override
	public boolean isValid(Object field, ConstraintValidatorContext context) {
		return factory.process(format.formatType(), (String) field);
	}

}
