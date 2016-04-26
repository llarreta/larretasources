package ar.com.larreta.commons.services.impl;

import java.util.Collection;
import java.util.Iterator;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ar.com.larreta.commons.domain.Security;
import ar.com.larreta.commons.persistence.dao.impl.LoadArguments;
import ar.com.larreta.commons.services.SecurityService;

@Service
@Transactional
public class SecurityServiceImpl extends StandardServiceImpl implements	SecurityService {

	/**
	 * Retorna la configuracion de seguridad para la aplicacion
	 * Se considera que siempre debe de existir la configuracion de seguridad en la base y nunca puede ser mas de una configuracion simultaneamente
	 * @return
	 */
	@Override
	public Security getSecurityConfig() {
		LoadArguments args = new LoadArguments(Security.class);
		args.addProjectedCollection("securityMatchers");
		Collection results = getDao().load(args);
		Iterator<Security> it = results.iterator();
		if (it.hasNext()){
			return it.next();
		}
		return null;
	}

}
