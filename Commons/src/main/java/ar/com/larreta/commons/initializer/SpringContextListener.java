package ar.com.larreta.commons.initializer;

import javax.servlet.ServletContextEvent;

import org.springframework.web.context.ContextLoaderListener;

public class SpringContextListener extends ContextLoaderListener {

	@Override
	public void contextInitialized(ServletContextEvent event) {
		System.setProperty("contextPath",event.getServletContext().getContextPath());
		super.contextInitialized(event);
	}

}
