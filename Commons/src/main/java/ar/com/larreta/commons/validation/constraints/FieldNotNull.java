/**
 * 
 */
package ar.com.larreta.commons.validation.constraints;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import javax.validation.Constraint;
import javax.validation.Payload;

import ar.com.larreta.commons.validation.FieldNotNullValidation;

/**
 * @author ignacio.m.larreta
 *
 */
@Documented
@Constraint(validatedBy = FieldNotNullValidation.class)
@Target({METHOD, FIELD, ANNOTATION_TYPE})
@Retention(RUNTIME)
public @interface FieldNotNull {
	String fieldId() default "";
	String formId() default "";
	String key() default "El campo no puede ser nulo";
	String message() default "";
	Class<?>[] groups() default {};
	Class<? extends Payload>[] payload() default {};
	boolean ignoreCase() default false;
}
