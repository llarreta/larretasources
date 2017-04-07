package ar.com.larreta.rest.configs;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collection;

import javax.validation.Validator;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import ar.com.larreta.annotations.Log;

@Configuration
@EnableAspectJAutoProxy
@EnableWebMvc
@ComponentScan(basePackages = {
		"ar.com.larreta"})  // scan  only the packages needed
@PropertySource(RestConfig.CLASSPATH_APPLICATION_PROPERTIES)
public class RestConfig extends WebMvcConfigurerAdapter  {

	public static final String CLASSPATH_DOMAINS_ENABLED 		= "classpath:domains.enabled";
	public static final String CLASSPATH_APPLICATION_PROPERTIES = "classpath:application.properties";

	private static @Log Logger LOG;
	
	@Value("${app.enviroment}")
	private String enviroment;
	
	@Autowired
	private Validator validator;
	
	@Autowired
	public ResourceLoader resourceLoader;
	

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		try {
			Resource resource = resourceLoader.getResource(CLASSPATH_DOMAINS_ENABLED);
			InputStreamReader reader = new InputStreamReader(resource.getInputStream());
			BufferedReader bufferedReader = new BufferedReader(reader);
			
			Collection<String> domains = new ArrayList<String>();
			while (bufferedReader.ready()){
				domains.add(bufferedReader.readLine());
			}
			registry.addMapping("/**").allowedOrigins(domains.stream().toArray(String[]::new));
		} catch (Exception e){
			LOG.error("Ocurrio un error estableciendo dominios habilitados");
		}
	}
	
    @Bean
    public MethodValidationPostProcessor methodValidationPostProcessor() {
        MethodValidationPostProcessor processor = new MethodValidationPostProcessor();
        processor.setValidator(validator);
        return processor;
    }
     
    @Bean
    public Validator validator() {
        return new LocalValidatorFactoryBean();
    } 
	
}
