package ar.com.larreta.validators.annotations;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import org.apache.commons.lang3.StringUtils;

import ar.com.larreta.validators.FormatValidator;

@Target({ FIELD })
@Retention(RUNTIME)
@Constraint(validatedBy = FormatValidator.class)
@Documented
public @interface  Format {
	
	public String message();
	public Class<?>[] groups() default {};
	public Class<? extends Payload>[] payload() default {};
	
	public String pattern() default StringUtils.EMPTY;
	public FormatType formatType() default FormatType.STRING;

	public enum FormatType {
		STRING,
		DATE
	}
	
}