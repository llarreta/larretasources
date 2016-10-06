package ar.com.larreta.commons.security;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;

public class AnonymousAuthenticationToken
		extends UserAuthenticationToken implements AuthenticationToken{

	public static final String ANONYMOUS = "Anonymous";

	public AnonymousAuthenticationToken(Collection<? extends GrantedAuthority> authorities) {
		super(ANONYMOUS, ANONYMOUS, authorities);
	}

}
