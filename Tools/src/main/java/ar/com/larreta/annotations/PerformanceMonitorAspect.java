package ar.com.larreta.annotations;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class PerformanceMonitorAspect {

	private static @Log Logger LOG;
	
	public PerformanceMonitorAspect() {
		super();
	}

	@Around("execution(@ ar.com.larreta.annotations.PerformanceMonitor * *(..)) && @annotation(performanceMonitor)")
	public Object execute(ProceedingJoinPoint joinPoint, PerformanceMonitor performanceMonitor) throws Throwable {
		Object toReturn = null;
		LOG.debug("Antes de:" + joinPoint.getSignature().getName());
		try {
			toReturn = joinPoint.proceed();
		} catch (Throwable e) {
			LOG.error("Ocurrio un error intentando continuar con:" + joinPoint.getSignature().getName(), e);
			throw e;
		}
		LOG.debug("Despues de:" + joinPoint.getSignature().getName());
		return toReturn;
	}
	
}
