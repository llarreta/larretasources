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
import ar.com.larreta.commons.domain.ParametricEntity;
import ar.com.larreta.commons.services.StandardService;
import ar.com.larreta.commons.threads.SaveThread;
import ar.com.larreta.screens.SaverExecuted;
import ar.com.larreta.screens.ScreenUtils;
import ar.com.larreta.screens.impl.HomeScreen;
import ar.com.larreta.screens.impl.saver.ABMSaver;

public class ScreenFrameworkInitializer extends GenericServlet {

	@Override
	public void init(ServletConfig config) throws ServletException {
		final StandardService service = AppManager.getInstance().getStandardService();
		HomeScreen homeScreen = (HomeScreen) AppManager.getInstance().getBean(HomeScreen.HOME_SCREEN);
		SaverExecuted saverExecuted = new SaverExecuted();
		saverExecuted.setDescription(homeScreen.getClass().getName());

		if (service.getEntity(saverExecuted, ParametricEntity.DESCRIPTION)==null){
			homeScreen.initialize();
			service.saveOrUpdate(homeScreen);
			SaveThread.addEntity(saverExecuted);
		}
		
		Collection<ABMSaver> savers = ScreenUtils.getSavers();
		Iterator<ABMSaver> it = savers.iterator();
		
		while (it.hasNext()) {
			ABMSaver abmSaver = (ABMSaver) it.next();
			saverExecuted = new SaverExecuted();
			saverExecuted.setDescription(abmSaver.getClass().getName());
			if (service.getEntity(saverExecuted, ParametricEntity.DESCRIPTION)==null){
				abmSaver.save();	
				SaveThread.addEntity(saverExecuted);
			}
		}
	}
	
	@Override
	public void service(ServletRequest req, ServletResponse res) throws ServletException, IOException {
		// No hacemos nada
	}

}
