package ar.com.larreta.colegio.impl.saver;

import org.springframework.stereotype.Component;

import ar.com.larreta.colegio.domain.PaymentPlan;
import ar.com.larreta.screens.Input;
import ar.com.larreta.screens.Label;
import ar.com.larreta.screens.PanelGrid;
import ar.com.larreta.screens.impl.CreateScreen;
import ar.com.larreta.screens.impl.saver.ParametricEntitySaver;

@Component
public class PaymentPlanSaver extends ParametricEntitySaver {

	public PaymentPlanSaver(){
		super();
		createScreen.setInitActionListenerName("");
	}
	
	@Override
	public Class getABMClass() {
		return PaymentPlan.class;
	}

	@Override
	protected void makeBody(CreateScreen screen) {
		super.makeBody(screen);
	}
}
