package ar.com.larreta.screens.services.impl;

import org.springframework.stereotype.Service;

import ar.com.larreta.commons.services.impl.StandardServiceImpl;
import ar.com.larreta.screens.Screen;
import ar.com.larreta.screens.services.ScreensService;

@Service(ScreenServiceImpl.SCREEN_SERVICE)
public class ScreenServiceImpl extends StandardServiceImpl implements ScreensService {

	public static final String SCREEN_SERVICE = "screenService";

	public Screen getScreen(Long id) {
		Screen screen = new Screen();
		screen.setId(id);
		return (Screen) getEntity(screen);
		
	}

}
