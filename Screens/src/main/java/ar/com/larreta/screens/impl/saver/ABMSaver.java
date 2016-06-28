package ar.com.larreta.screens.impl.saver;

import ar.com.larreta.commons.AppManager;
import ar.com.larreta.screens.impl.CommonsScreen;
import ar.com.larreta.screens.impl.CreateScreen;
import ar.com.larreta.screens.impl.MainScreen;
import ar.com.larreta.screens.services.ScreensService;
import ar.com.larreta.screens.services.impl.ScreenServiceImpl;

public abstract class ABMSaver {

	protected MainScreen mainScreen;
	protected CreateScreen createScreen;
	protected CreateScreen updateScreen;
	
	public ABMSaver(){}
	
	/**
	 * Guarda los screens que implementa
	 */
	public void save(){
		save(mainScreen);
		save(createScreen);
		save(updateScreen);
	}

	protected void save(CommonsScreen screen) {
		if (screen!=null){
			getScreenService().saveOrUpdate(screen.getMe());
		}
	}
	
	public ScreensService getScreenService() {
		return (ScreensService) AppManager.getInstance().getBean(ScreenServiceImpl.SCREEN_SERVICE);
	}
	
}
