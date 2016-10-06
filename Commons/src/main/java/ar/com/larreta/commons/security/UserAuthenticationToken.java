package ar.com.larreta.commons.security;

import java.util.Collection;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import ar.com.larreta.commons.domain.User;

public class UserAuthenticationToken extends UsernamePasswordAuthenticationToken implements AuthenticationToken {
	
	private User user;
	private SessionInfo info = new SessionInfo();
	
	public SessionInfo getInfo() {
		return info;
	}

	public UserAuthenticationToken(Object principal, Object credentials,
			Collection<? extends GrantedAuthority> authorities) {
		super(principal, credentials, authorities);
	}
	
	public UserAuthenticationToken(User user, Collection<? extends GrantedAuthority> authorities){
		this(user.getNick(), user.getPassword(), authorities);
		this.user = user;
	}

	public User getUser() {
		return user;
	}	
}
