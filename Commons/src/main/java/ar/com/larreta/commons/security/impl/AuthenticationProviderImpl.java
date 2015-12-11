package ar.com.larreta.commons.security.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import ar.com.larreta.commons.domain.Role;
import ar.com.larreta.commons.domain.User;
import ar.com.larreta.commons.domain.UserState;
import ar.com.larreta.commons.security.AuthenticationProvider;
import ar.com.larreta.commons.security.UserAuthenticationToken;
import ar.com.larreta.commons.services.UserService;
import ar.com.larreta.commons.utils.Base64;

@Component
public class AuthenticationProviderImpl implements AuthenticationProvider {
	
	private static final String BAD_CREDENTIALS_EXCEPTION_MESSAGE = "Usuario o password incorrectos!";

	@Autowired
	private UserService userService;

	@Autowired
	private Base64 base64;

	public AuthenticationProviderImpl(){
		
	}
	
	public Authentication authenticate(Authentication toAuthenticate) throws AuthenticationException {
		
			if (!toAuthenticate.isAuthenticated()){
				String loginName = toAuthenticate.getName();
				String password = toAuthenticate.getCredentials().toString();
				
				User user = userService.getUserByNickWithProfiles(loginName);
	
				if (user==null){
					throw new BadCredentialsException(BAD_CREDENTIALS_EXCEPTION_MESSAGE);
				}
				
				if ((!base64.decrypt(user.getPassword()).equals(password)) || (!UserState.ACTIVE.equals(user.getUserState()))) {
					throw new BadCredentialsException(BAD_CREDENTIALS_EXCEPTION_MESSAGE);
				}
				
				Authentication authenticated = new UserAuthenticationToken(user, getGrantedAuthorities(user));
				
				SecurityContextHolder.getContext().setAuthentication(authenticated);
			}
			
	        return toAuthenticate;
	}

	/**
	 * Obtiene los permisos que tendra el usuario dentro de spring security
	 * @param user
	 * @return
	 */
	public List<GrantedAuthority> getGrantedAuthorities(User user) {
		List<GrantedAuthority> grantedAuths = new ArrayList<GrantedAuthority>();

		Collection<Role> roles = userService.getRoles(user);
		Iterator<Role> itRoles = roles.iterator();
		while (itRoles.hasNext()) {
			Role role = (Role) itRoles.next();
			grantedAuths.add(new SimpleGrantedAuthority(role.getDescription()));
		}
		return grantedAuths;
	}

	public boolean supports(Class<?> authentication) {
		return UserAuthenticationToken.class.isAssignableFrom(authentication) || (UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication));
	}

}
