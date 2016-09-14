package ar.com.larreta.commons.security;

import ar.com.larreta.commons.domain.User;

public interface AuthenticationToken {
	SessionInfo getInfo();
	User getUser();
}
