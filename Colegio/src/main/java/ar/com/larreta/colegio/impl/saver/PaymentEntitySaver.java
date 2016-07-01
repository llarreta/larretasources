package ar.com.larreta.colegio.impl.saver;

import org.springframework.stereotype.Component;

import ar.com.larreta.colegio.domain.PaymentEntity;
import ar.com.larreta.screens.impl.saver.ParametricEntitySaver;

@Component
public class PaymentEntitySaver extends ParametricEntitySaver {

	public PaymentEntitySaver() {
		super();
	}

	@Override
	public Class getABMClass() {
		return PaymentEntity.class;
	}


}
