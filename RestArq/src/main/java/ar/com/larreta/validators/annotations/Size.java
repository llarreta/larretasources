package ar.com.larreta.validators.annotations;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import ar.com.larreta.validators.SizeValidator;

@Target({ FIELD })
@Retention(RUNTIME)
@Constraint(validatedBy = SizeValidator.class)
@Documented
public @interface  Size {
	
	public String message();
	public Class<?>[] groups() default {};
	public Class<? extends Payload>[] payload() default {};
	
	public int mayorOrEqual() default 0;
	
}
