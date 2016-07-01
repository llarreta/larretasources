package ar.com.larreta.colegio.impl.saver;

import org.springframework.stereotype.Component;

import ar.com.larreta.colegio.domain.ProductGroup;
import ar.com.larreta.screens.impl.saver.ParametricEntitySaver;

@Component
public class ProductGroupSaver extends ParametricEntitySaver {

	public ProductGroupSaver() {
		super();
	}

	@Override
	public Class getABMClass() {
		return ProductGroup.class;
	}


}
