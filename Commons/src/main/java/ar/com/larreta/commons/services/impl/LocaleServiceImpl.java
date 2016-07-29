package ar.com.larreta.commons.services.impl;

import java.util.Collection;
import java.util.Locale;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import ar.com.larreta.commons.AppManager;
import ar.com.larreta.commons.domain.Country;
import ar.com.larreta.commons.domain.Entity;
import ar.com.larreta.commons.domain.Language;
import ar.com.larreta.commons.persistence.dao.args.LoadArguments;
import ar.com.larreta.commons.persistence.dao.impl.Equal;
import ar.com.larreta.commons.services.LocaleService;

@Service
@Transactional
public class LocaleServiceImpl extends StandardServiceImpl implements LocaleService {

	/**
	 * Valida si el locale existe para la aplicacion actual
	 * De lo contrario retorna el locale por default
	 * @param locale
	 * @return
	 */
	public Locale validate(Locale locale){
		Country country = getCountry(locale.getCountry());
		Language language = getLanguage(locale.getLanguage());
		if (country!=null && language!=null){
			return locale;
		}
		
		return getDefaultLocale();
	}

	public Locale getDefaultLocale() {
		Locale defaultLocale = new Locale(AppManager.getInstance().getAppConfigData().getDefaultLanguage(), 
										  AppManager.getInstance().getAppConfigData().getDefaultCountry());
		return defaultLocale;
	}
	
	@Override
	public Country getCountry(String abbreviation) {
		return (Country) getEntity(Country.class, abbreviation); 
	}

	@Override
	public Language getLanguage(String abbreviation) {
		return (Language) getEntity(Language.class, abbreviation);
	}
	
	private Entity getEntity(Class entityClass, String abbreviation) {
		try {
			LoadArguments arguments = new LoadArguments(entityClass);
			Equal equal = new Equal(arguments, "abbreviation", abbreviation);
			arguments.addWhere(equal);
			Collection result = dao.load(arguments);
			if (result!=null && result.size()>0){
				return (Entity) result.iterator().next();
			}
		} catch (Exception e){
			getLog().error("Ocurrio un error en el getEntity", e);
		}
		return null;
	}

}
