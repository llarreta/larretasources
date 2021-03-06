package ar.com.larreta.stepper.impl;

import java.io.Serializable;

import org.apache.commons.beanutils.PropertyUtils;
import org.slf4j.Logger;

import ar.com.larreta.annotations.Log;
import ar.com.larreta.mystic.exceptions.PersistenceException;
import ar.com.larreta.mystic.model.Entity;
import ar.com.larreta.stepper.exceptions.BusinessException;

public abstract class CallCreateBusinessListener extends CallAnotherBusinessListener {

	public static final String CREATE_ERROR_MESSAGE = "Ocurrio un error invocando a CreateBusiness";
	private static @Log Logger LOG;	
	
	@Override
	public Serializable execute(Serializable source, Serializable target, Object... args) throws BusinessException, PersistenceException{
			try {
				Serializable id = super.execute(source, target, args);
				if (isExecuteAvaiable(source, target, args)){
					Entity entity = (Entity) target;
					if (entity!=null){
						entity.setId((Long) id);
					}
					PropertyUtils.setProperty(source, "id", id);
			}
			return id;
		} catch (Exception e){
			LOG.error(CREATE_ERROR_MESSAGE, e);
			throw new BusinessException();
		}
	}

}
