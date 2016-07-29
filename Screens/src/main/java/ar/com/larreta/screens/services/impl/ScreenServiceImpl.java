package ar.com.larreta.screens.services.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import ar.com.larreta.commons.services.impl.StandardServiceImpl;
import ar.com.larreta.screens.Screen;
import ar.com.larreta.screens.services.ScreensService;

@Service(ScreenServiceImpl.SCREEN_SERVICE)
public class ScreenServiceImpl extends StandardServiceImpl implements ScreensService {

	public static final String SCREEN_SERVICE = "screenService";
	
	//FIXME: Tener la precaucion de que este mapa no sobrecargue la memoria // Revisar porque falla por concurrencia este hash 
	private Map<Long, Screen> screens = new HashMap<Long, Screen>();

	public Screen getScreen(Long id) {
		Screen screen = null;
		synchronized (ScreenServiceImpl.class) {
			screen = screens.get(id);
			if (screen==null){
				screen = new Screen();
				screen.setId(id);
				screen = (Screen) getEntity(screen);
				if (screen==null){
					getLog().info("No se encontro Screen(" + id + ").");
				}
				screens.put(id, screen);
			}
		}
		return screen;
		
	}

}
