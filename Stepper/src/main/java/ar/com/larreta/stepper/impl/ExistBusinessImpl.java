package ar.com.larreta.stepper.impl;

import java.io.Serializable;
import java.util.Collection;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import ar.com.larreta.annotations.Log;
import ar.com.larreta.mystic.exceptions.PersistenceException;
import ar.com.larreta.mystic.query.Query;
import ar.com.larreta.mystic.query.Select;
import ar.com.larreta.stepper.ExistBusiness;
import ar.com.larreta.stepper.exceptions.BusinessException;

@Service(ExistBusiness.BUSINESS_NAME)
@Transactional
public class ExistBusinessImpl extends StepImpl implements ExistBusiness {

	private static @Log Logger LOG;
	
	@Override
	public Serializable execute(Serializable source, Serializable target, Object... args) throws BusinessException, PersistenceException{
		ExistData data = (ExistData) source;
		try {
			Query query = applicationContext.getBean(Select.class);
			query.addMainEntityProjection(data.getEntityType().getName());
			query.addWhereEqual(data.getLinkedField(), (Serializable) data.getValue());
			Collection result = query.execute();
			return (result!=null && result.size()>0);
		} catch (Exception e){
			LOG.error("Ocurrio un error validando existencia", e);
			return Boolean.FALSE;
		}
	}

}
