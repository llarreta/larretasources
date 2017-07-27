package ar.com.larreta.mystic.aspects;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;

import ar.com.larreta.annotations.Log;

@Aspect
@Component
public class RetryAspect {

	private static @Log Logger LOG;
	
	@Around("execution(@ ar.com.larreta.mystic.aspects.Retry * *(..)) && @annotation(retryAnnotation)")
	public Object execute(ProceedingJoinPoint joinPoint, Retry retryAnnotation) throws Throwable {
		Integer count = retryAnnotation.count();
		Integer index = 1;
		Object returned = null;
		do {
			try {
				LOG.info("Ejecutando metodo " + joinPoint.getSignature() + " con reintentos. (" + index + "/" + count + ")");
				returned = joinPoint.proceed();
				break;
			} catch (Throwable e) {
				index++;
				if ((index>retryAnnotation.count())){
					throw e;
				}
				LOG.debug("Reintentando");
			}
		} while (index<=retryAnnotation.count());
		LOG.info("Ejecucion exitosa del metodo " + joinPoint.getSignature() + " en reintento (" + index + "/" + count + ")");
		return returned;
	}
	
}
