package ar.com.larreta.screens.services.impl;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Service;

import ar.com.larreta.commons.services.impl.StandardServiceImpl;
import ar.com.larreta.screens.Screen;
import ar.com.larreta.screens.services.ScreensService;

@Service(ScreenServiceImpl.SCREEN_SERVICE)
public class ScreenServiceImpl extends StandardServiceImpl implements ScreensService {

	public static final String SCREEN_SERVICE = "screenService";
	
	//FIXME: Tener la precaucion de que este mapa no sobrecargue la memoria
	private Map<Long, Screen> screens = new ConcurrentHashMap<Long, Screen>();

	public Screen getScreen(Long id) {
		Screen screen = screens.get(id);
		if (screen==null){
			screen = new Screen();
			screen.setId(id);
			screen = (Screen) getEntity(screen);
			screens.put(id, screen);
		}
		return screen;
		
	}

}
