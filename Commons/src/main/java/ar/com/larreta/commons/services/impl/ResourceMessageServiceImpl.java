package ar.com.larreta.commons.services.impl;

import java.util.Collection;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.transaction.Transactional;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import ar.com.larreta.commons.domain.ResourceMessage;
import ar.com.larreta.commons.persistence.dao.args.LoadArguments;
import ar.com.larreta.commons.services.ResourceMessageService;
import ar.com.larreta.commons.threads.SaveThread;

@Service(ResourceMessageServiceImpl.RESOURCE_MESSAGE_SERVICE)
@Transactional
public class ResourceMessageServiceImpl extends LocaleServiceImpl implements ResourceMessageService {
	
	public Map<String, Map<String, Map<String, String>>> messages;

	public static final String RESOURCE_MESSAGE_SERVICE = "ResourceMessageService";

	public String getMessage(Locale locale, String key){
		try {
			if (StringUtils.isEmpty(key)){
				return key;
			}
			
			String country = locale.getCountry();
			String language = locale.getLanguage();
			
			String message = getMessageOnCache(key, country, language);
			
			 if (StringUtils.isEmpty(message)){
					LoadArguments args = new LoadArguments(ResourceMessage.class);
					args.addProjectedProperties("country")
						.addProjectedProperties("language")
						.addWhereEqual("country.abbreviation", country)
						.addWhereEqual("language.abbreviation", language)
						.addWhereEqual("key", key);
					Collection<ResourceMessage> resources = dao.load(args);
					
					if (resources!=null && !resources.isEmpty()){
						ResourceMessage resourceMessage = resources.iterator().next();
						message = resourceMessage.getTextString();
						
						if (!StringUtils.isEmpty(message)){
							putMessageOnCache(key, message, country, language);
						}
						return message;
					} else {
						ResourceMessage resourceMessage = new ResourceMessage();
						resourceMessage.setCountry(getCountry(country));
						resourceMessage.setLanguage(getLanguage(language));
						resourceMessage.setKey(key);
						resourceMessage.setTextString(key);
						SaveThread.addEntity(resourceMessage);
					}
					
					return key;
			 }
			return message;
		} catch (Exception e){
			getLog().error("Ocurrio un error en el getMessage", e);
		}
		return StringUtils.EMPTY;
	}



	public String getMessageOnCache(String key, String country, String language) {
		Map<String, String> messagesFromLanguage = getMessageCache(country, language);
		String message = messagesFromLanguage.get(key);
		return message;
	}

	public void deleteMessageOnCache(String key, String country, String language) {
		Map<String, String> messagesFromLanguage = getMessageCache(country, language);
		messagesFromLanguage.remove(key);
	}
	
	public void putMessageOnCache(String key, String text, String country, String language) {
		Map<String, String> messagesFromLanguage = getMessageCache(country, language);
		messagesFromLanguage.put(key, text);
	}

	private Map<String, String> getMessageCache(String country, String language) {
		if (messages==null){
			messages = new HashMap();
		}
		Map<String, Map<String, String>> messagesFromCountries =  messages.get(country);
		if (messagesFromCountries==null){
			messagesFromCountries = new HashMap();
			messages.put(country, messagesFromCountries);
		}
		 Map<String, String> messagesFromLanguage = messagesFromCountries.get(language);
		 if (messagesFromLanguage==null){
			 messagesFromLanguage = new HashMap();
			 messagesFromCountries.put(language, messagesFromLanguage);
		 }
		return messagesFromLanguage;
	}
	
}
