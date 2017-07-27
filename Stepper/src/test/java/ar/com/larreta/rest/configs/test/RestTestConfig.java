package ar.com.larreta.rest.configs.test;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import ar.com.larreta.mystic.configs.PersistenceConfig;
import ar.com.larreta.stepper.configs.RestConfig;
@Configuration
@EnableAspectJAutoProxy
@EnableWebMvc
@ComponentScan(	
				basePackages = {"ar.com.larreta"},
				excludeFilters={
						@ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, value = {RestConfig.class, PersistenceConfig.class})
					  }
		
		)  // scan  only the packages needed
@PropertySource(value=RestConfig.CLASSPATH_APPLICATION_PROPERTIES /*, ignoreResourceNotFound=true*/)
public class RestTestConfig extends WebMvcConfigurerAdapter {

}
