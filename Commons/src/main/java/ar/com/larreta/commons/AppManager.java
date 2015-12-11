package ar.com.larreta.commons;

import java.util.ArrayList;
import java.util.Collection;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.stereotype.Component;

import ar.com.larreta.commons.controllers.SessionInitializer;
import ar.com.larreta.commons.exceptions.AppException;
import ar.com.larreta.commons.services.StandardService;
import ar.com.larreta.commons.utils.Base64;
import ar.com.larreta.commons.utils.MailSender;
import ar.com.larreta.commons.utils.SessionData;

@Component
public class AppManager extends AppObjectImpl{
	
	private static final String SERVICE = "Service";

	private static final String AUTHENTICATION_MANAGER = "authenticationManager";

	private static final String STANDARD_SERVICE = "standardService";

	private static final String PROPERTY_NAME_APP_SESSION_INITIALIZERS = "app.session.initializers";
	private static final String SESSION_DATA = "sessionData";

	private static AppManager INSTANCE;
	private static Boolean INITIALIZED = Boolean.FALSE;
	
	private String appURL;
	
	@Autowired
	@Qualifier (AUTHENTICATION_MANAGER)
	private AuthenticationManager authenticationManager;

	@Autowired
	private Base64 base64;
	
	@Autowired
	private ApplicationContext appContext;
	
	@Autowired
	private AppConfigData appConfigData;
	
	private MailSender mailSender;

	public AppConfigData getAppConfigData() {
		return appConfigData;
	}
	
	public String getAppURL() {
		return appURL;
	}

	public void setAppURL(String appURL) {
		this.appURL = appURL;
	}

	
	public MailSender getMailSender() {
		if (mailSender==null){
			mailSender = new MailSender(appConfigData);
		}
		return mailSender;
	}

	public SessionData getSessionData() {
		return (SessionData) AppManager.getInstance().getAppContext().getBean(SESSION_DATA);
	}
	
	/**
	 * Retorna las implmentaciones de inicializadores de session
	 * @return
	 */
	public Collection<SessionInitializer> getSessionsInitializers(){
		return getExecutableInstances(PROPERTY_NAME_APP_SESSION_INITIALIZERS);
	}
	
	public Collection getExecutableInstances(String property) {
		Collection initializers = new ArrayList();
		try{
			String initis = appConfigData.getProperty(property);
			String[] splitInitis = initis.split(",");
			Integer index = 0;
			
			while (index<splitInitis.length){
				try{
					String beanName = splitInitis[index];
					if (!StringUtils.EMPTY.equals(beanName.trim())){
						initializers.add(appContext.getBean(beanName));
					}
				} catch (Exception e){
					getLog().error(AppException.getStackTrace(e));
				}
				index++;
			}
		} catch (Exception e){
			getLog().error(AppException.getStackTrace(e));
		}
		return initializers;
	}

	public ApplicationContext getAppContext() {
		return appContext;
	}

	public void setAppContext(ApplicationContext appContext) {
		this.appContext = appContext;
	}

	public AppManager(){
		if (!INITIALIZED){
			INSTANCE = this;
		}
		INITIALIZED = Boolean.TRUE;
	}
	
	public StandardService getStandardService(){
		return (StandardService) getAppContext().getBean(STANDARD_SERVICE);
	}
	
	public Base64 getBase64() {
		return base64;
	}

	public void setBase64(Base64 base64) {
		this.base64 = base64;
	}

	public AuthenticationManager getAuthenticationManager() {
		return authenticationManager;
	}

	public void setAuthenticationManager(AuthenticationManager authenticationManager) {
		this.authenticationManager = authenticationManager;
	}

	
	public static AppManager getInstance(){
		return INSTANCE;
	}
	
	public Object getBean(String name){
		Object bean = null;
		if (getAppContext()!=null){
			bean = getAppContext().getBean(name);
		}
		return bean;
	}
	
	public StandardService getStandardService(Class entityClass){
		String name = entityClass.getSimpleName();
		name = name.substring(0, 1).toLowerCase() + name.substring(1);
		return (StandardService) AppManager.getInstance().getBean(name + SERVICE);
	}
	
	
	
}
