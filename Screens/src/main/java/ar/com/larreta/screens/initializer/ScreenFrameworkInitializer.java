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

import ar.com.larreta.screens.impl.saver.ABMSaver;
import ar.com.larreta.screens.impl.saver.CountrySaver;
import ar.com.larreta.screens.impl.saver.LanguageSaver;
import ar.com.larreta.screens.impl.saver.ResourceMessageSaver;

public class ScreenFrameworkInitializer extends GenericServlet {

	@Override
	public void init(ServletConfig config) throws ServletException {
		Collection<ABMSaver> savers = new ArrayList<ABMSaver>();
		savers.add(new CountrySaver());
		savers.add(new LanguageSaver());
		savers.add(new ResourceMessageSaver());
		
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
