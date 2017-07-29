package ar.com.larreta.stepper.aspects;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Retention(RUNTIME)
@Target(METHOD)
@Documented
public @interface ReplaceArg {

	public int source();
	public int target();
	public String reference();
	
}
