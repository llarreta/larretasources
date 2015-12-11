package ar.com.larreta.commons.aspects;

import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

import ar.com.larreta.commons.AppObjectImpl;

@Configuration
@EnableAspectJAutoProxy(proxyTargetClass=true)
@DependsOn("appConfig")
public class AppAspect  extends AppObjectImpl{
	/*@Bean
	public DefaultAdvisorAutoProxyCreator getDefaultAdvisorAutoProxyCreator(){
		DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
		defaultAdvisorAutoProxyCreator.setProxyTargetClass(Boolean.TRUE);
		return defaultAdvisorAutoProxyCreator;
	} */

}
