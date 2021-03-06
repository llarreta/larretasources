package ar.com.larreta.stepper.aspects;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import ar.com.larreta.mystic.exceptions.PersistenceException;
import ar.com.larreta.stepper.Step;
import ar.com.larreta.stepper.StepElement;
import ar.com.larreta.stepper.exceptions.BusinessException;

@Aspect
@Component
public class AroundCallStepAspect extends StepElement {
	@Around("execution(@ ar.com.larreta.stepper.aspects.AroundCallStep * *(..)) && @annotation(annotation)")
	public void execute(JoinPoint joinPoint, AroundCallStep annotation) throws BusinessException, PersistenceException {
		Collection<String> steps = Arrays.asList(annotation.steps());
		Iterator<String> it = steps.iterator();
		while (it.hasNext()) {
			String stepName = (String) it.next();
			Step step = (Step) applicationContext.getBean(stepName);
			step.execute((Serializable) joinPoint.getThis(), null, joinPoint.getArgs());			
		}
	}
}
