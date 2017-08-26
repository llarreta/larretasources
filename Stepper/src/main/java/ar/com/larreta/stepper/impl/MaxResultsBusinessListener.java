package ar.com.larreta.stepper.impl;

import java.io.Serializable;

import org.springframework.stereotype.Component;

import ar.com.larreta.mystic.exceptions.PersistenceException;
import ar.com.larreta.mystic.query.Select;
import ar.com.larreta.stepper.LoadBusiness;
import ar.com.larreta.stepper.exceptions.BusinessException;
import ar.com.larreta.stepper.messages.Body;

@Component(MaxResultsBusinessListener.BUSINESS_LISTENER_NAME)
public class MaxResultsBusinessListener extends StepImpl  {
	
	public static final String MAX_RESULTS = "maxResults";
	public static final String BUSINESS_LISTENER_NAME = "LoadArgsMaxResultsBusinessListener";

	@Override
	public Serializable execute(Serializable source, Serializable target, Object... args) throws BusinessException, PersistenceException{
		LoadBusiness loadBusiness = (LoadBusiness) source;
		Body loadBody = (Body) args[0];
		Select select = loadBusiness.getSelect();
		if ((loadBody!=null) && (select!=null)){
			Integer maxResults = (Integer) beanUtils.read(loadBody, MAX_RESULTS);
			if (maxResults!=null){
				select.setMaxResults(maxResults);
			}	
		}
		return null;
	}
}
