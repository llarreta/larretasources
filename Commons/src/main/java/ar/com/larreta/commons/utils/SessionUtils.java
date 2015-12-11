package ar.com.larreta.commons.utils;

import org.springframework.security.core.context.SecurityContextHolder;

import ar.com.larreta.commons.domain.User;
import ar.com.larreta.commons.security.UserAuthenticationToken;

public class SessionUtils {
	public static User getActualUser(){
		UserAuthenticationToken authenticationToken = getAuthentication();
		if (authenticationToken!=null){
			return getAuthentication().getUser();	
		}
		return null;
	}

	public static UserAuthenticationToken getAuthentication() {
		if (SecurityContextHolder.getContext().getAuthentication() instanceof UserAuthenticationToken) {
			return (UserAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
		}
		return null;
	}
}
