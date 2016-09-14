package ar.com.larreta.commons.security;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;

import ar.com.larreta.commons.domain.User;

public class AnonymousAuthenticationToken
		extends org.springframework.security.authentication.AnonymousAuthenticationToken implements AuthenticationToken{

	private SessionInfo info = new SessionInfo();

	public AnonymousAuthenticationToken(String key, Object principal, Collection<? extends GrantedAuthority> authorities) {
		super(key, principal, authorities);
	}
	
	public SessionInfo getInfo() {
		return info;
	}
	
	@Override
	public User getUser() {
		return null;
	}

}
