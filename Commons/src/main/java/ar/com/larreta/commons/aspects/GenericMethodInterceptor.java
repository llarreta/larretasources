package ar.com.larreta.commons.aspects;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

import ar.com.larreta.commons.AppObjectImpl;

@Component
public class GenericMethodInterceptor extends AppObjectImpl implements MethodInterceptor  {

	@Override
	public Object invoke(MethodInvocation invocation) throws Throwable {
		final StopWatch stopWatch = new StopWatch(invocation.getMethod().toGenericString());
        stopWatch.start("invocation.proceed()");

        try {
            getLog().info("~~~~~~~~ START METHOD {} ~~~~~~~~ \n" + invocation.getMethod().toGenericString());
            return invocation.proceed();
        } finally {
            stopWatch.stop();
            getLog().info(stopWatch.prettyPrint());
            getLog().info("~~~~~~~~ END METHOD {} ~~~~~~~~ \n" + invocation.getMethod().toGenericString());
        }
	}

}
