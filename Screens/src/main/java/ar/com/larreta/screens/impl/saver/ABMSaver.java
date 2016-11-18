package ar.com.larreta.screens.impl.saver;

import ar.com.larreta.commons.AppManager;
import ar.com.larreta.commons.AppObjectImpl;
import ar.com.larreta.screens.impl.CommonsScreen;
import ar.com.larreta.screens.impl.CreateScreen;
import ar.com.larreta.screens.impl.MainScreen;
import ar.com.larreta.screens.impl.UpdateScreen;
import ar.com.larreta.screens.services.ScreensService;
import ar.com.larreta.screens.services.impl.ScreenServiceImpl;

public abstract class ABMSaver extends AppObjectImpl{

	protected MainScreen mainScreen;
	protected CreateScreen createScreen;
	protected CreateScreen updateScreen;
	
	public ABMSaver(){
		mainScreen = new MainScreen(getABMClass()) {
			@Override
			protected void makeColumns() {
				ABMSaver.this.makeColumn(this);
			}
		};
		
		createScreen = new CreateScreen(getABMClass()) {
			@Override
			protected void makeBody() {
				ABMSaver.this.makeBody(this);
			}
		};
		
		updateScreen = new UpdateScreen(getABMClass()) {
			@Override
			protected void makeBody() {
				ABMSaver.this.makeBody(this);
			}
		};
	}
	
	public abstract Class getABMClass();
	
	/**
	 * Guarda los screens que implementa
	 */
	public void save(){
		save(mainScreen, "main");
		save(createScreen, "create");
		save(updateScreen, "update");
	}

	protected void save(CommonsScreen screen, String operation) {
		if (screen!=null){
			try {
				screen.initialize();
				getScreenService().saveOrUpdate(screen);
				getLog().screen("Grabando " + operation + "(ID:" + screen.getId() + ") :" + screen.getEntityClass());
			} catch (Exception e){
				getLog().screenError("Ocurrio un error guardando la pantalla " + operation + "(ID:" + screen.getId() + ") :" + screen.getEntityClass(), e);
			}
		} else {
			getLog().screen("No se grabo pantalla para:" + operation);
		}
	}
	
	public ScreensService getScreenService() {
		return (ScreensService) AppManager.getInstance().getBean(ScreenServiceImpl.SCREEN_SERVICE);
	}
	
	protected abstract void makeBody(CreateScreen screen);
	protected abstract void makeColumn(MainScreen screen);
	
}
