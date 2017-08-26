package ar.com.larreta.school.business.payments;

import java.io.Serializable;

import org.springframework.stereotype.Component;

import ar.com.larreta.mystic.exceptions.PersistenceException;
import ar.com.larreta.mystic.query.Select;
import ar.com.larreta.stepper.LoadBusiness;
import ar.com.larreta.stepper.exceptions.BusinessException;
import ar.com.larreta.stepper.impl.StepImpl;
import ar.com.larreta.stepper.messages.TargetedBody;

@Component(AddTargetInObligationStatus.STEP_NAME)
public class AddTargetInObligationStatusImpl extends StepImpl implements AddTargetInObligationStatus {

	@Override
	public Serializable execute(Serializable source, Serializable target, Object... args)
			throws BusinessException, PersistenceException {
		LoadBusiness loadBusiness = (LoadBusiness) source;
		TargetedBody body = (TargetedBody) args[0];
		Select select = loadBusiness.getSelect();
		
		select.addWhereEqual("student.id", body.getTarget());
		
		return null;
	}

}
