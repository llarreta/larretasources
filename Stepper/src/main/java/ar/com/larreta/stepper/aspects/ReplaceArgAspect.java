package ar.com.larreta.stepper.aspects;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import ar.com.larreta.mystic.exceptions.PersistenceException;
import ar.com.larreta.stepper.StepElement;
import ar.com.larreta.stepper.exceptions.BusinessException;

@Aspect
@Component
public class ReplaceArgAspect extends StepElement {

	@Before("execution(@ ar.com.larreta.stepper.aspects.ReplaceArg * *(..)) && @annotation(annotation)")
	public void execute(JoinPoint joinPoint, ReplaceArg annotation) throws BusinessException, PersistenceException {
		
		Object value = beanUtils.read(joinPoint.getArgs()[annotation.source()], annotation.reference());
		joinPoint.getArgs()[annotation.target()] = value;

	}
	
}
