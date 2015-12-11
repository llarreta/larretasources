package ar.com.larreta.commons.security;

import java.util.Collection;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import ar.com.larreta.commons.domain.User;

public class UserAuthenticationToken extends UsernamePasswordAuthenticationToken {
	
	private User user;
	
	public UserAuthenticationToken(User user, Collection<? extends GrantedAuthority> authorities){
		super(user.getNick(), user.getPassword(), authorities);
		this.user = user;
	}

	public User getUser() {
		return user;
	}	
}
