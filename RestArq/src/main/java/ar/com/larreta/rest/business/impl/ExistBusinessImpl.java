package ar.com.larreta.rest.business.impl;

import java.io.Serializable;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import ar.com.larreta.annotations.Log;
import ar.com.larreta.persistence.dao.args.LoadArguments;
import ar.com.larreta.persistence.dao.impl.Equal;
import ar.com.larreta.rest.business.ExistBusiness;

@Service(ExistBusiness.BUSINESS_NAME)
@Transactional
public class ExistBusinessImpl extends BusinessImpl implements ExistBusiness {

	private static @Log Logger LOG;
	
	@Override
	public Serializable execute(Serializable input) {
		ExistData data = (ExistData) input;
		try {
			LoadArguments args = new LoadArguments(data.getEntityType());
			args.addWhere(new Equal(args, data.getLinkedField(), data.getValue()));
			return (standardDAO.getEntity(args)!=null);
		} catch (Exception e){
			LOG.error("Ocurrio un error validando existencia", e);
			return Boolean.FALSE;
		}
	}

}
