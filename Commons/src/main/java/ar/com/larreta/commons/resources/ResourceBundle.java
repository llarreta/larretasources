package ar.com.larreta.commons.resources;

import java.util.Enumeration;
import java.util.Locale;

import javax.faces.component.UIViewRoot;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import ar.com.larreta.commons.AppManager;
import ar.com.larreta.commons.services.ResourceMessageService;
import ar.com.larreta.commons.services.impl.ResourceMessageServiceImpl;

public class ResourceBundle extends java.util.ResourceBundle {
	
	private Locale locale;
	private ResourceMessageService service;
	
	public ResourceBundle() {
		super();
		refreshLocale();
		AppManager.getInstance().setResourceBundle(this);
	}

	private void refreshLocale() {
		UIViewRoot viewRoot = FacesContext.getCurrentInstance().getViewRoot();
		if (viewRoot!=null){
			locale = viewRoot.getLocale();
		}
		ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
		if (externalContext!=null && locale == null){
			locale = externalContext.getRequestLocale();
		}
		locale = getService().validate(locale);
		FacesContext.getCurrentInstance().getViewRoot().setLocale(locale);
		FacesContext.getCurrentInstance().getApplication().setDefaultLocale(getService().getDefaultLocale());
	}

	@Override
	protected Object handleGetObject(String key) {
		return getService().getMessage(locale, key);
	}

	private ResourceMessageService getService() {
		if (service==null){
			service = (ResourceMessageService) AppManager.getInstance().getBean(ResourceMessageServiceImpl.RESOURCE_MESSAGE_SERVICE);
		}
		return service;
	}

	@Override
	public Enumeration<String> getKeys() {
		return null;
	}

}
