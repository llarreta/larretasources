package ar.com.larreta.screens.initializer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import javax.servlet.GenericServlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import ar.com.larreta.commons.AppManager;
import ar.com.larreta.screens.impl.HomeScreen;
import ar.com.larreta.screens.impl.saver.ABMSaver;
import ar.com.larreta.screens.impl.saver.CountrySaver;
import ar.com.larreta.screens.impl.saver.LanguageSaver;
import ar.com.larreta.screens.impl.saver.ProfileSaver;
import ar.com.larreta.screens.impl.saver.ResourceMessageSaver;
import ar.com.larreta.screens.impl.saver.RoleSaver;
import ar.com.larreta.screens.impl.saver.SecurityMatcherSaver;
import ar.com.larreta.screens.impl.saver.SecuritySaver;
import ar.com.larreta.screens.impl.saver.UserSaver;

public class ScreenFrameworkInitializer extends GenericServlet {

	@Override
	public void init(ServletConfig config) throws ServletException {
		
		AppManager.getInstance().getStandardService().saveOrUpdate(new HomeScreen().getMe());
		
		Collection<ABMSaver> savers = new ArrayList<ABMSaver>();
		savers.add(new CountrySaver());
		savers.add(new LanguageSaver());
		savers.add(new ResourceMessageSaver());
		savers.add(new RoleSaver());
		savers.add(new ProfileSaver());
		savers.add(new UserSaver());
		savers.add(new SecuritySaver());
		savers.add(new SecurityMatcherSaver());
		
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
