package ar.com.larreta.colegio.impl.saver;

import org.springframework.stereotype.Component;

import ar.com.larreta.colegio.domain.PaymentDirection;
import ar.com.larreta.screens.impl.saver.ParametricEntitySaver;

@Component
public class PaymentDirectionSaver extends ParametricEntitySaver {

	public PaymentDirectionSaver() {
		super();
	}

	@Override
	public Class getABMClass() {
		return PaymentDirection.class;
	}

}
