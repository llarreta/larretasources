package ar.com.larreta.colegio.impl.saver;

import org.springframework.stereotype.Component;

import ar.com.larreta.screens.MenuItem;
import ar.com.larreta.screens.impl.MainMenu;

@Component
public class ColegioMainMenu extends MainMenu {

	public ColegioMainMenu() {
		super();
	}

	@Override
	public void initialize() {
		super.initialize();
		add(index++, new MenuItem("app.colegio.division", APP_SCREEN_URL + ColegioIds.DIVISION_MAIN, MENU_ITEM_DEFAULT_STYLE));
	}

}
