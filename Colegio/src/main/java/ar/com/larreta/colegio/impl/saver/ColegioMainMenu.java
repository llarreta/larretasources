package ar.com.larreta.colegio.impl.saver;

import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

import ar.com.larreta.colegio.domain.Course;
import ar.com.larreta.colegio.domain.Division;
import ar.com.larreta.colegio.domain.Level;
import ar.com.larreta.colegio.domain.PaymentDirection;
import ar.com.larreta.colegio.domain.PaymentEntity;
import ar.com.larreta.colegio.domain.ProductGroup;
import ar.com.larreta.colegio.domain.Year;
import ar.com.larreta.commons.AppManager;
import ar.com.larreta.screens.MenuItem;
import ar.com.larreta.screens.impl.MainMenu;

@Component
@DependsOn(AppManager.APP_MANAGER)
public class ColegioMainMenu extends MainMenu {

	public ColegioMainMenu() {
		super();
	}

	@Override
	public void initialize() {
		super.initialize();
		add(index++, new MenuItem("app.colegio.division", APP_SCREEN_URL + getMainScreenClass(Division.class), MENU_ITEM_DEFAULT_STYLE));
		add(index++, new MenuItem("app.colegio.level", APP_SCREEN_URL + getMainScreenClass(Level.class), MENU_ITEM_DEFAULT_STYLE));
		add(index++, new MenuItem("app.colegio.year", APP_SCREEN_URL + getMainScreenClass(Year.class), MENU_ITEM_DEFAULT_STYLE));
		add(index++, new MenuItem("app.colegio.paymentDirection", APP_SCREEN_URL + getMainScreenClass(PaymentDirection.class), MENU_ITEM_DEFAULT_STYLE));
		add(index++, new MenuItem("app.colegio.paymentEntity", APP_SCREEN_URL + getMainScreenClass(PaymentEntity.class), MENU_ITEM_DEFAULT_STYLE));
		add(index++, new MenuItem("app.colegio.productGroup", APP_SCREEN_URL + getMainScreenClass(ProductGroup.class), MENU_ITEM_DEFAULT_STYLE));
		add(index++, new MenuItem("app.colegio.course", APP_SCREEN_URL + getMainScreenClass(Course.class), MENU_ITEM_DEFAULT_STYLE));

	}

}
