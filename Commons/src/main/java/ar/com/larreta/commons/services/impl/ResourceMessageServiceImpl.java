package ar.com.larreta.commons.services.impl;

import java.util.Collection;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.transaction.Transactional;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import ar.com.larreta.commons.domain.ResourceMessage;
import ar.com.larreta.commons.persistence.dao.impl.LoadArguments;
import ar.com.larreta.commons.services.ResourceMessageService;

@Service(ResourceMessageServiceImpl.RESOURCE_MESSAGE_SERVICE)
@Transactional
public class ResourceMessageServiceImpl extends StandardServiceImpl implements ResourceMessageService {
	
	public Map<String, Map<String, Map<String, String>>> messages;

	public static final String RESOURCE_MESSAGE_SERVICE = "ResourceMessageService";

	public String getMessage(Locale locale, String key){
		String country = locale.getCountry();
		String language = locale.getLanguage();
		
		String message = getMessageOnCache(key, country, language);
		
		 if (StringUtils.isEmpty(message)){
				LoadArguments args = new LoadArguments(ResourceMessage.class);
				args.addWhereEqual("country", country).addWhereEqual("language", language).addWhereEqual("key", key);
				Collection<ResourceMessage> resources = dao.load(args);
				if (resources!=null && !resources.isEmpty()){
					ResourceMessage resourceMessage = resources.iterator().next();
					message = resourceMessage.getTextMessage();
					if (!StringUtils.isEmpty(message)){
						putMessageOnCache(key, message, country, language);
					}
					return message;
				}	
				return key;
		 }

		return message;
	}



	private String getMessageOnCache(String key, String country, String language) {
		Map<String, String> messagesFromLanguage = getMessageCache(country, language);
		String message = messagesFromLanguage.get(key);
		return message;
	}
	
	private void putMessageOnCache(String key, String text, String country, String language) {
		Map<String, String> messagesFromLanguage = getMessageCache(country, language);
		messagesFromLanguage.put(key, text);
	}

	private Map<String, String> getMessageCache(String country, String language) {
		if (messages==null){
			messages = new HashMap<>();
		}
		Map<String, Map<String, String>> messagesFromCountries =  messages.get(country);
		if (messagesFromCountries==null){
			messagesFromCountries = new HashMap<>();
			messages.put(country, messagesFromCountries);
		}
		 Map<String, String> messagesFromLanguage = messagesFromCountries.get(language);
		 if (messagesFromLanguage==null){
			 messagesFromLanguage = new HashMap<>();
			 messagesFromCountries.put(language, messagesFromLanguage);
		 }
		return messagesFromLanguage;
	}
	
}
