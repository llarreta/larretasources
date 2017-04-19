package ar.com.larreta.rest.business.impl;

import java.io.Serializable;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import ar.com.larreta.annotations.Log;
import ar.com.larreta.rest.business.ValidateParametricEntityBusiness;

@Service(ValidateParametricEntityBusiness.BUSINESS_NAME)
@Transactional
public class ValidateParametricEntityBusinessImpl extends BusinessImpl implements ValidateParametricEntityBusiness {

	private static @Log Logger LOG;
	
	@Override
	public Serializable execute(Serializable input) {
		ValidateParametricEntityData data = (ValidateParametricEntityData) input;
		try {
			return (standardDAO.getEntity(Class.forName(data.getParametricEntity()), data.getId())!=null);
		} catch (Exception e){
			LOG.error("Ocurrio un error validando valor para entidad parametrica", e);
			return Boolean.FALSE;
		}
	}

}
