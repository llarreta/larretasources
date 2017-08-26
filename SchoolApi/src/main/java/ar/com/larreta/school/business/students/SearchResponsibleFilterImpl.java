package ar.com.larreta.school.business.students;

import java.io.Serializable;

import org.springframework.stereotype.Component;

import ar.com.larreta.mystic.exceptions.PersistenceException;
import ar.com.larreta.mystic.query.Select;
import ar.com.larreta.school.messages.SearchResponsibleData;
import ar.com.larreta.stepper.LoadBusiness;
import ar.com.larreta.stepper.exceptions.BusinessException;
import ar.com.larreta.stepper.impl.StepImpl;

@Component(SearchResponsibleFilterImpl.NAME)
public class SearchResponsibleFilterImpl extends StepImpl {

	public static final String NAME = "SearchResponsibleFilterImpl";
	
	@Override
	public Serializable execute(Serializable source, Serializable target, Object... args)
			throws BusinessException, PersistenceException {
		LoadBusiness loadBusiness = (LoadBusiness) source;
		SearchResponsibleData search = (SearchResponsibleData) args[0];
		
		Select select = loadBusiness.getSelect();
		select.addWhereLike("name", search.getName());
		select.addWhereLike("surname", search.getSurname());

		return null;	}

}
