package ar.com.larreta.rest.business.impl;

import java.io.Serializable;

import org.apache.commons.beanutils.PropertyUtils;
import org.slf4j.Logger;

import ar.com.larreta.annotations.Log;
import ar.com.larreta.persistence.model.Entity;
import ar.com.larreta.rest.exceptions.BusinessException;

public abstract class CallCreateBusinessListener extends CallAnotherBusinessListener {

	public static final String CREATE_ERROR_MESSAGE = "Ocurrio un error invocando a CreateBusiness";
	private static @Log Logger LOG;	
	
	@Override
	public Serializable process(Serializable source, Serializable target, Object... args) throws BusinessException{
			try {
				Serializable id = super.process(source, target, args);
				if (isExecuteAvaiable()){
					Entity entity = (Entity) target;
					if (entity!=null){
						entity.setId((Long) id);
					}
					PropertyUtils.setProperty(json, "id", id);
			}
			return id;
		} catch (Exception e){
			LOG.error(CREATE_ERROR_MESSAGE, e);
			throw new BusinessException();
		}
	}

}
