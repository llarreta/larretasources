package ar.com.larreta.commons.persistence.dao.args;

import ar.com.larreta.commons.persistence.dao.impl.Max;
import ar.com.larreta.commons.persistence.exceptions.UnreportedEntityException;

public class MaxArguments extends LoadArguments {

	public MaxArguments(Class type, String field) throws UnreportedEntityException {
		super(new Max(type, field));
		this.getMainEntity().setLoadArguments(this);
	}

}
