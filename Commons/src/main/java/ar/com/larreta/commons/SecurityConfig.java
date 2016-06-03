package ar.com.larreta.commons;

import java.util.Iterator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import ar.com.larreta.commons.domain.Security;
import ar.com.larreta.commons.domain.SecurityMatcher;
import ar.com.larreta.commons.security.AuthenticationProvider;
import ar.com.larreta.commons.services.SecurityService;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private AuthenticationProvider provider;
	
	@Autowired
	private SecurityService securityService;
	
	@Bean(name="authenticationManager")
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	public AuthenticationProvider getProvider() {
		return provider;
	}

	public void setProvider(AuthenticationProvider provider) {
		this.provider = provider;
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(provider);
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		Security security = securityService.getSecurityConfig();
		http.csrf().disable(); 
		
		if (security.getAvaiable()){
			http
			.formLogin()
				.loginPage(security.getLoginPage()).loginProcessingUrl(security.getLoginProcessingUrl())
				.defaultSuccessUrl(security.getDefaultSuccessUrl())
				.failureUrl(security.getFailureUrl())
				.usernameParameter(security.getUsernameParameter())
				.passwordParameter(security.getPasswordParameter());
		
			http.logout().logoutUrl(security.getLogoutUrl()).logoutSuccessUrl(security.getLogoutSuccessUrl()).deleteCookies(security.getDeleteCookies());
			
			if (security.getSecurityMatchers()!=null){
				Iterator<SecurityMatcher> matchers = security.getSecurityMatchers().iterator();
				while (matchers.hasNext()) {
					SecurityMatcher securityMatcher = (SecurityMatcher) matchers.next();
					securityMatcher.process(http);
				}
			}
		}
		
	}

}
