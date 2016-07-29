package ar.com.larreta.commons.services;

import java.util.Locale;

import ar.com.larreta.commons.domain.Country;
import ar.com.larreta.commons.domain.Language;

public interface LocaleService extends StandardService {

	public Country getCountry(String abbreviation);
	public Language getLanguage(String abbreviation);
	public Locale validate(Locale locale);
	public Locale getDefaultLocale();
}
