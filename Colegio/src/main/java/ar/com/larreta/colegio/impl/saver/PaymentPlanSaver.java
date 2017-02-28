package ar.com.larreta.colegio.impl.saver;

import org.springframework.stereotype.Component;

import ar.com.larreta.colegio.domain.PaymentPlan;
import ar.com.larreta.colegio.domain.ProductGroup;
import ar.com.larreta.screens.impl.CreateScreen;
import ar.com.larreta.screens.impl.saver.ParametricEntitySaver;

@Component
public class PaymentPlanSaver extends ParametricEntitySaver {

	public PaymentPlanSaver(){
		super();
	}

	@Override
	public Class getABMClass() {
		return PaymentPlan.class;
	}

	@Override
	protected void makeBody(CreateScreen screen) {
		super.makeBody(screen);

		screen.addNewBody();
		screen.getTargetObject().addLabel(0, "app.colegio.obligacion");
		
		screen.addNewBody();
		screen.getTargetObject().addInput(		0, 	"app.colegio.obligacion.descripcion", 	"obligationDescription");
		screen.getTargetObject().addCalendar(	1, 	"app.colegio.obligacion.date", 			"obligationDate");
		screen.getTargetObject().addCombo(		2, 	"app.colegio.obligacion.productGroup", 	"productGroup", 	ProductGroup.class.getName());
	}
}
