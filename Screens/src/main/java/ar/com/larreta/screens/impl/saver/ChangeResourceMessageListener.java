package ar.com.larreta.screens.impl.saver;

import ar.com.larreta.commons.AppManager;
import ar.com.larreta.commons.domain.Entity;
import ar.com.larreta.commons.domain.ResourceMessage;
import ar.com.larreta.commons.services.ResourceMessageService;
import ar.com.larreta.commons.services.impl.ResourceMessageServiceImpl;
import ar.com.larreta.screens.impl.ScreenListener;

public class ChangeResourceMessageListener implements ScreenListener {

	public void execute(Entity entity) {
		ResourceMessage resourceMessage = (ResourceMessage) entity;
		ResourceMessageService service = (ResourceMessageService) AppManager.getInstance().getBean(ResourceMessageServiceImpl.RESOURCE_MESSAGE_SERVICE);
		service.putMessageOnCache(resourceMessage.getKey(), resourceMessage.getTextString(), 
				resourceMessage.getCountry().getAbbreviation(), resourceMessage.getLanguage().getAbbreviation());
	}

}
