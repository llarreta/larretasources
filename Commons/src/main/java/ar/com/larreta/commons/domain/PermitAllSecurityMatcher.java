package ar.com.larreta.commons.domain;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;

@Entity
@Table(name = "securityMatcher")
@DiscriminatorValue(PermitAllSecurityMatcher.PERMIT_ALL)
public class PermitAllSecurityMatcher extends SecurityMatcher {

	public static final String PERMIT_ALL = "permitAll";

	@Transient
	@Override
	public void process(HttpSecurity http) throws Exception {
		http.authorizeRequests().antMatchers(getPattern()).permitAll();
	}

	@Transient
	@Override
	public String getSecurityMatcherType() {
		return PERMIT_ALL;
	}
	
}
