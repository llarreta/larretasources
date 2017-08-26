package ar.com.larreta.stepper.impl;

import java.io.Serializable;

import org.springframework.stereotype.Component;

import ar.com.larreta.mystic.exceptions.PersistenceException;
import ar.com.larreta.mystic.query.Select;
import ar.com.larreta.stepper.RelatedTargetBusiness;
import ar.com.larreta.stepper.exceptions.BusinessException;
import ar.com.larreta.stepper.messages.RelatedBody;

@Component(RelatedBusinessImpl.NAME)
public class RelatedBusinessImpl extends StepImpl {
	
	public static final String NAME = "RelatedBusinessImpl";

	@Override
	public Serializable execute(Serializable source, Serializable target, Object... args)
			throws BusinessException, PersistenceException {
		RelatedTargetBusiness loadBusiness = (RelatedTargetBusiness) source;
		RelatedBody loadBody = (RelatedBody) args[0];
		
		Select select = loadBusiness.getSelect();
		select.addWhereEqual(loadBusiness.getRelatedProperty(), loadBody.getIdRelated());

		return null;
	}

}
