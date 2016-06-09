package ar.com.larreta.screens.impl.saver;

import ar.com.larreta.commons.AppManager;
import ar.com.larreta.screens.impl.CreateScreen;
import ar.com.larreta.screens.impl.MainScreen;
import ar.com.larreta.screens.services.ScreensService;
import ar.com.larreta.screens.services.impl.ScreenServiceImpl;

public abstract class ABMSaver {

	private ScreensService screensService = (ScreensService) AppManager.getInstance().getBean(ScreenServiceImpl.SCREEN_SERVICE);
	
	protected MainScreen mainScreen;
	protected CreateScreen createScreen;
	protected CreateScreen updateScreen;
	
	public ABMSaver(){}
	
	/**
	 * Guarda los screens que implementa
	 */
	public void save(){
		screensService.saveOrUpdate(mainScreen.getMe());
		screensService.saveOrUpdate(createScreen.getMe());
		screensService.saveOrUpdate(updateScreen.getMe());
	}
	
	
}
