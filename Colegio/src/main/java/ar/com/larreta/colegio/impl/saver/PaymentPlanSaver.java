package ar.com.larreta.colegio.impl.saver;

import org.springframework.stereotype.Component;

import ar.com.larreta.colegio.domain.PaymentPlan;
import ar.com.larreta.screens.Button;
import ar.com.larreta.screens.CSSDiv;
import ar.com.larreta.screens.PanelGrid;
import ar.com.larreta.screens.impl.CreateScreen;
import ar.com.larreta.screens.impl.saver.ParametricEntitySaver;
import ar.com.larreta.screens.validators.Validator;

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
		
		CSSDiv div = new CSSDiv(12);
		screen.getTargetObject().add(0, div);
		
		div.addLabel(0, "app.colegio.obligacion");
		
		CSSDiv div2 = new CSSDiv(12);
		div2.addInput(0, "app.colegio.obligacion.descripcion", "prueba1");
		div2.addInput(1, "app.colegio.obligacion.descripcion", "prueba2");

		div.add(1, div2);

		screen.getSearchMap().find(Button.class);
	}
}
