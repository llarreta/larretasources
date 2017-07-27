package ar.com.larreta.rest.configs.test;

import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.hibernate4.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import ar.com.larreta.tools.Base64;

@Configuration
@EnableTransactionManagement
public class PersistenceTestConfig {
	
	@Bean
	public LocalSessionFactoryBean sessionFactory(){
		return Mockito.mock(LocalSessionFactoryBean.class);
	}
	
	@Bean
	public Base64 Base64(){
		return new Base64();
	}
}
