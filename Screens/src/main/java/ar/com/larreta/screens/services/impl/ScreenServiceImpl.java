package ar.com.larreta.screens.services.impl;

import java.util.Collection;
import java.util.Iterator;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import ar.com.larreta.commons.domain.Entity;
import ar.com.larreta.commons.domain.Security;
import ar.com.larreta.commons.persistence.dao.impl.Equal;
import ar.com.larreta.commons.persistence.dao.impl.LoadArguments;
import ar.com.larreta.commons.services.impl.StandardServiceImpl;
import ar.com.larreta.screens.Screen;
import ar.com.larreta.screens.services.ScreensService;

@Service(ScreenServiceImpl.SCREEN_SERVICE)
@Transactional
public class ScreenServiceImpl extends StandardServiceImpl implements ScreensService {

	public static final String SCREEN_SERVICE = "screenService";

	public Screen getScreen(Long id) {
		Screen screen = new Screen();
		screen.setId(id);
		return (Screen) getEntity(screen);
		
	}

}
