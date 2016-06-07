package ar.com.larreta.screens.initializer;

import java.io.IOException;

import javax.servlet.GenericServlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import ar.com.larreta.commons.AppManager;
import ar.com.larreta.screens.impl.CountryCreateScreen;
import ar.com.larreta.screens.impl.CountryMainScreen;
import ar.com.larreta.screens.services.ScreensService;
import ar.com.larreta.screens.services.impl.ScreenServiceImpl;

public class ScreenFrameworkInitializer extends GenericServlet {

	@Override
	public void init(ServletConfig config) throws ServletException {
		ScreensService screensService = (ScreensService) AppManager.getInstance().getBean(ScreenServiceImpl.SCREEN_SERVICE);
		screensService.saveOrUpdate(new CountryMainScreen().getMe());
		screensService.saveOrUpdate(new CountryCreateScreen().getMe());
	}
	
	@Override
	public void service(ServletRequest req, ServletResponse res) throws ServletException, IOException {
		// No hacemos nada
	}

}
