package ar.com.larreta.commons.controllers;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.web.session.HttpSessionEventPublisher;
import org.springframework.stereotype.Controller;

import ar.com.larreta.commons.AppManager;
import ar.com.larreta.commons.AppObject;
import ar.com.larreta.commons.AppObjectImpl;
import ar.com.larreta.commons.filters.URLsManager;
import ar.com.larreta.commons.services.UserService;
import ar.com.larreta.commons.services.impl.UserServiceImpl;

@Controller
public class HttpSessionEventPublicher extends HttpSessionEventPublisher {

	private static final String SPRING_SECURITY_CONTEXT = "SPRING_SECURITY_CONTEXT";
	
	private AppObject appObject = new AppObjectImpl(HttpSessionEventPublicher.class);

	@Override
	public void sessionDestroyed(HttpSessionEvent event) {
		
		HttpSession session = event.getSession(); 
		SecurityContext context = (SecurityContext) session.getAttribute(SPRING_SECURITY_CONTEXT);
		
		if (context!=null){
			Authentication authentication = context.getAuthentication();
	
			UserService userService = (UserService) AppManager.getInstance().getBean(UserServiceImpl.USER_SERVICE);
	 
	       	URLsManager.getInstance().cleanUser();
	       	
	       	if (userService!=null){
	       		userService.saveAccessEvent(authentication.getName(), "logout");
	       	} else {
	       		appObject.getLog().error("Precaucion! No pudo obtenerse userService.");
	       	}
		}
 		super.sessionDestroyed(event);

	}

}
