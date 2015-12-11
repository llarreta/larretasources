package ar.com.larreta.commons.domain;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;

@Entity
@Table(name = "securityMatcher")
@DiscriminatorValue("permitAll")
public class PermitAllSecurityMatcher extends SecurityMatcher {

	@Transient
	@Override
	public void process(HttpSecurity http) throws Exception {
		http.authorizeRequests().antMatchers(getPattern()).permitAll();
	}
	
}
