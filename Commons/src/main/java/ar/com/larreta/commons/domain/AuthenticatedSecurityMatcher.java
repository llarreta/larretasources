package ar.com.larreta.commons.domain;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;


@Entity
@Table(name = "securityMatcher")
@DiscriminatorValue(AuthenticatedSecurityMatcher.AUTHENTICATED)
public class AuthenticatedSecurityMatcher extends SecurityMatcher {

	public static final String AUTHENTICATED = "authenticated";

	@Transient
	@Override
	public void process(HttpSecurity http) throws Exception {
		http.authorizeRequests().antMatchers(getPattern()).authenticated();
	}

	@Transient
	@Override
	public String getSecurityMatcherType() {
		return AUTHENTICATED;
	}

}
