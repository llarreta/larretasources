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
import ar.com.larreta.commons.AppObject;
import ar.com.larreta.commons.AppObjectImpl;
import ar.com.larreta.commons.domain.ParametricEntity;
import ar.com.larreta.commons.threads.SaveThread;
import ar.com.larreta.screens.SaverExecuted;
import ar.com.larreta.screens.Screen;
import ar.com.larreta.screens.ScreenUtils;
import ar.com.larreta.screens.impl.ErrorScreen;
import ar.com.larreta.screens.impl.HomeScreen;
import ar.com.larreta.screens.impl.saver.ABMSaver;

public class ScreenFrameworkInitializer extends GenericServlet {

	private AppObject appObject = new AppObjectImpl(ScreenFrameworkInitializer.class);
	
	@Override
	public void init(ServletConfig config) throws ServletException {
		try {
			saveScreen((HomeScreen) AppManager.getInstance().getBean(HomeScreen.HOME_SCREEN));
			saveScreen((ErrorScreen) AppManager.getInstance().getBean(ErrorScreen.ERROR_SCREEN));
			
			Collection<ABMSaver> savers = ScreenUtils.getSavers();
			Iterator<ABMSaver> it = savers.iterator();
			
			while (it.hasNext()) {
				ABMSaver abmSaver = (ABMSaver) it.next();
				SaverExecuted saverExecuted = new SaverExecuted();
				saverExecuted.setDescription(abmSaver.getClass().getName());
				if (AppManager.getInstance().getStandardService().getEntity(saverExecuted, ParametricEntity.DESCRIPTION)==null){
					try {
						abmSaver.save();	
						SaveThread.addEntity(saverExecuted);
					} catch (Exception e){
						appObject.getLog().error("Ocurrio un error guardando el saver:" + abmSaver.getClass(), e);
					}
				}
			}
		} catch (Exception e){
			appObject.getLog().error("Ocurrio un error inicializando ScreenFrameworkInitializer", e);
		}
	}

	private void saveScreen(Screen screen) {
		SaverExecuted saverExecuted = new SaverExecuted();
		saverExecuted.setDescription(screen.getClass().getName());

		if (AppManager.getInstance().getStandardService().getEntity(saverExecuted, ParametricEntity.DESCRIPTION)==null){
			screen.initialize();
			AppManager.getInstance().getStandardService().saveOrUpdate(screen);
			SaveThread.addEntity(saverExecuted);
		}
	}
	
	@Override
	public void service(ServletRequest req, ServletResponse res) throws ServletException, IOException {
		// No hacemos nada
	}

}
