package ar.com.larreta.validators.annotations;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import ar.com.larreta.validators.NotNullValidator;

@Target({ FIELD })
@Retention(RUNTIME)
@Constraint(validatedBy = NotNullValidator.class)
@Documented
public @interface  NotNull {
	
	public String message() default "{validator.notnull}";
	public Class<?>[] groups() default {};
	public Class<? extends Payload>[] payload() default {};
	
	public String [] avaiableActions();
	
}
