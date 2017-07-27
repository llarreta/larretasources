package ar.com.larreta.stepper.validators.annotations;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import org.apache.commons.lang3.StringUtils;

import ar.com.larreta.mystic.model.Entity;
import ar.com.larreta.stepper.validators.ExistValidator;

@Target({ FIELD })
@Retention(RUNTIME)
@Constraint(validatedBy = ExistValidator.class)
@Documented
public @interface Exist {
	
	public String message();
	public Class<?>[] groups() default {};
	public Class<? extends Payload>[] payload() default {};
	
	public Class entityType();
	public String field() default StringUtils.EMPTY;
	public String linkedField() default Entity.ID;
	
}
