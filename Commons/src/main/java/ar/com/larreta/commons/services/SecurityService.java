package ar.com.larreta.commons.services;

import ar.com.larreta.commons.domain.Security;


public interface SecurityService extends StandardService {
	
	/**
	 * Retorna la configuracion de seguridad para la aplicacion
	 * Se considera que siempre debe de existir la configuracion de seguridad en la base y nunca puede ser mas de una configuracion simultaneamente
	 * @return
	 */
	public Security getSecurityConfig();

}
