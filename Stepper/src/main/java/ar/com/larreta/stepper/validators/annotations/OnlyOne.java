package ar.com.larreta.stepper.validators.annotations;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import ar.com.larreta.stepper.validators.OnlyOneValidator;

@Target({ FIELD })
@Retention(RUNTIME)
@Constraint(validatedBy = OnlyOneValidator.class)
@Documented
public @interface OnlyOne {

	public String message();
	public Class<?>[] groups() default {};
	public Class<? extends Payload>[] payload() default {};

	public String field();
	public String linkedField();
	public boolean required() default false;
	
}
