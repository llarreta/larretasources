package ar.com.larreta.stepper.impl;

import java.io.Serializable;

import org.springframework.stereotype.Component;

import ar.com.larreta.mystic.exceptions.PersistenceException;
import ar.com.larreta.mystic.query.Select;
import ar.com.larreta.stepper.LoadBusiness;
import ar.com.larreta.stepper.exceptions.BusinessException;
import ar.com.larreta.stepper.messages.Body;

@Component(FirstResultBusinessListener.BUSINESS_LISTENER_NAME)
public class FirstResultBusinessListener extends StepImpl {

	public static final String FIRST_RESULT = "firstResult";
	public static final String BUSINESS_LISTENER_NAME = "LoadArgsFirstResultBusinessListener";

	@Override
	public Serializable execute(Serializable source, Serializable target, Object... args) throws BusinessException, PersistenceException{
		LoadBusiness loadBusiness = (LoadBusiness) source;
		Body loadBody = (Body) args[0];
		Select select = loadBusiness.getSelect();
		if ((loadBody!=null) && (select!=null)){
			Integer firstResult = (Integer) beanUtils.read(loadBody, FIRST_RESULT);
			if (firstResult!=null){
				select.setFirstResult(firstResult);
			}	
		}
		return null;
	}

}
