package ar.com.larreta.commons.initializer;

import java.io.IOException;

import javax.servlet.GenericServlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public class AppInitializer extends GenericServlet {
	
	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);

		ServletContext context = config.getServletContext();
        String contextPath = context.getContextPath();
        System.setProperty("contextPath", contextPath);

	}

	@Override
	public void service(ServletRequest req, ServletResponse res) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
	}



}
