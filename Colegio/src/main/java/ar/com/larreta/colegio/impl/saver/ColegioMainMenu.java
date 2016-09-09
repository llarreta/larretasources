package ar.com.larreta.colegio.impl.saver;

import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

import ar.com.larreta.colegio.domain.Course;
import ar.com.larreta.colegio.domain.Division;
import ar.com.larreta.colegio.domain.DocumentType;
import ar.com.larreta.colegio.domain.Level;
import ar.com.larreta.colegio.domain.PaymentDirection;
import ar.com.larreta.colegio.domain.PaymentEntity;
import ar.com.larreta.colegio.domain.Product;
import ar.com.larreta.colegio.domain.ProductGroup;
import ar.com.larreta.colegio.domain.Responsible;
import ar.com.larreta.colegio.domain.Student;
import ar.com.larreta.colegio.domain.Year;
import ar.com.larreta.commons.AppManager;
import ar.com.larreta.screens.MenuItem;
import ar.com.larreta.screens.SubMenu;
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
		
		SubMenu colegioConfig = new SubMenu("app.colegioConfigMenu", "");
		colegioConfig.add(index++, new MenuItem("app.colegio.documentType", 		APP_SCREEN_URL + getMainScreenClass(DocumentType.class), 		MENU_ITEM_DEFAULT_STYLE));
		colegioConfig.add(index++, new MenuItem("app.colegio.division", 			APP_SCREEN_URL + getMainScreenClass(Division.class), 			MENU_ITEM_DEFAULT_STYLE));
		colegioConfig.add(index++, new MenuItem("app.colegio.level", 				APP_SCREEN_URL + getMainScreenClass(Level.class), 				MENU_ITEM_DEFAULT_STYLE));
		colegioConfig.add(index++, new MenuItem("app.colegio.year", 				APP_SCREEN_URL + getMainScreenClass(Year.class), 				MENU_ITEM_DEFAULT_STYLE));
		colegioConfig.add(index++, new MenuItem("app.colegio.paymentDirection", 	APP_SCREEN_URL + getMainScreenClass(PaymentDirection.class), 	MENU_ITEM_DEFAULT_STYLE));
		colegioConfig.add(index++, new MenuItem("app.colegio.paymentEntity", 		APP_SCREEN_URL + getMainScreenClass(PaymentEntity.class), 		MENU_ITEM_DEFAULT_STYLE));
		colegioConfig.add(index++, new MenuItem("app.colegio.productGroup", 		APP_SCREEN_URL + getMainScreenClass(ProductGroup.class), 		MENU_ITEM_DEFAULT_STYLE));
		colegioConfig.add(index++, new MenuItem("app.colegio.course", 				APP_SCREEN_URL + getMainScreenClass(Course.class), 				MENU_ITEM_DEFAULT_STYLE));
		colegioConfig.add(index++, new MenuItem("app.colegio.product",				APP_SCREEN_URL + getMainScreenClass(Product.class), 			MENU_ITEM_DEFAULT_STYLE));
		add(index++, colegioConfig);

		add(index++, 				new MenuItem("app.colegio.responsible", 		APP_SCREEN_URL + getMainScreenClass(Responsible.class), 		MENU_ITEM_DEFAULT_STYLE));
		add(index++, 				new MenuItem("app.colegio.student", 			APP_SCREEN_URL + getMainScreenClass(Student.class), 			MENU_ITEM_DEFAULT_STYLE));
		
	}

}
