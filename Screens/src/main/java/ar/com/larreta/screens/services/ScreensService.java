package ar.com.larreta.screens.services;

import ar.com.larreta.commons.services.StandardService;
import ar.com.larreta.screens.Screen;

public interface ScreensService extends StandardService{
	public Screen getScreen(Long id);
}
