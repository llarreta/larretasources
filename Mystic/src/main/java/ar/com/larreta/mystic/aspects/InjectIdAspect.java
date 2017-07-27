package ar.com.larreta.mystic.aspects;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ar.com.larreta.mystic.model.Entity;
import ar.com.larreta.tools.UniqueIDGenerator;

@Aspect
@Component
public class InjectIdAspect {

	@Autowired
	private UniqueIDGenerator idGenerator;
	
	@Before("execution(@ ar.com.larreta.mystic.aspects.InjectId * *(..)) && @annotation(injectIdAnnotation)")
	public void execute(JoinPoint joinPoint, InjectId injectIdAnnotation) {
		Entity entity = (Entity) joinPoint.getArgs()[0];
		entity.setId(idGenerator.nextId());
	}

	
}
