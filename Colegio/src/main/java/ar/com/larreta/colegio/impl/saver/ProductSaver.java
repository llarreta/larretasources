package ar.com.larreta.colegio.impl.saver;

import org.springframework.stereotype.Component;

import ar.com.larreta.colegio.domain.Product;
import ar.com.larreta.colegio.domain.ProductGroup;
import ar.com.larreta.screens.impl.CreateScreen;
import ar.com.larreta.screens.impl.MainScreen;
import ar.com.larreta.screens.impl.saver.ParametricEntitySaver;
import ar.com.larreta.screens.validators.Validator;

@Component
public class ProductSaver extends ParametricEntitySaver {

	public ProductSaver() {
		super();
		mainScreen.setLazyProperties("productGroup");
	}

	@Override
	public Class getABMClass() {
		return Product.class;
	}

	@Override
	protected void makeBody(CreateScreen screen) {
		super.makeBody(screen);
		Integer index = 1;
		index = screen.addCombo(index, "app.colegio.productGroup", 		"productGroup", 		ProductGroup.class.getName(), Validator.REQUIRED);
	}

	@Override
	protected void makeColumn(MainScreen screen) {
		super.makeColumn(screen);
		screen.getTable().addColumn(1, screen.getColumnWithLabelProperty("productGroup", 		"app.colegio.productGroup", 		"tableElement.productGroup", 		"40%"));
	}


}
