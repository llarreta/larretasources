package ar.com.larreta.commons.services;

import java.util.Locale;

public interface ResourceMessageService extends LocaleService {
	String getMessage(Locale locale, String key);
	String getMessageOnCache(String key, String country, String language);
	void putMessageOnCache(String key, String text, String country, String language);
	void deleteMessageOnCache(String key, String country, String language);
}
