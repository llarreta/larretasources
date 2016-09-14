package ar.com.larreta.commons.utils;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import ar.com.larreta.commons.domain.User;
import ar.com.larreta.commons.security.AuthenticationToken;
import ar.com.larreta.commons.security.UserAuthenticationToken;

public class SessionUtils {
	public static User getActualUser(){
		AuthenticationToken authenticationToken = getAuthentication();
		if (authenticationToken!=null){
			return getAuthentication().getUser();	
		}
		return null;
	}

	public static AuthenticationToken getAuthentication() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication instanceof AuthenticationToken) {
			return (AuthenticationToken) authentication;
		}
		if (authentication instanceof AnonymousAuthenticationToken) {
			AnonymousAuthenticationToken anonymous = (AnonymousAuthenticationToken) authentication;
			ar.com.larreta.commons.security.AnonymousAuthenticationToken anonymousAuthenticationToken = 
					new ar.com.larreta.commons.security.AnonymousAuthenticationToken("anonymous", anonymous.getPrincipal(), anonymous.getAuthorities());
			SecurityContextHolder.getContext().setAuthentication(anonymousAuthenticationToken);
			return anonymousAuthenticationToken;
		}
		return null;
	}
}
