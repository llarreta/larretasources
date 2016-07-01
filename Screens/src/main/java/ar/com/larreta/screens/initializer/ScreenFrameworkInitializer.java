package ar.com.larreta.screens.initializer;

import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;

import javax.servlet.GenericServlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import ar.com.larreta.commons.AppManager;
import ar.com.larreta.screens.ScreenUtils;
import ar.com.larreta.screens.impl.HomeScreen;
import ar.com.larreta.screens.impl.saver.ABMSaver;

public class ScreenFrameworkInitializer extends GenericServlet {

	@Override
	public void init(ServletConfig config) throws ServletException {
		HomeScreen homeScreen = (HomeScreen) AppManager.getInstance().getBean("homeScreen");
		homeScreen.initialize();
		AppManager.getInstance().getStandardService().saveOrUpdate(homeScreen);
		
		Collection<ABMSaver> savers = ScreenUtils.getSavers();
		Iterator<ABMSaver> it = savers.iterator();
		
		while (it.hasNext()) {
			ABMSaver abmSaver = (ABMSaver) it.next();
			abmSaver.save();
		}
	}
	
	@Override
	public void service(ServletRequest req, ServletResponse res) throws ServletException, IOException {
		// No hacemos nada
	}

}
