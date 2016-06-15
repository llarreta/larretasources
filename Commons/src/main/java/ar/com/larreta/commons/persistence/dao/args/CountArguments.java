package ar.com.larreta.commons.persistence.dao.args;

import ar.com.larreta.commons.persistence.dao.impl.Count;
import ar.com.larreta.commons.persistence.exceptions.UnreportedEntityException;

public class CountArguments extends LoadArguments {

	public CountArguments(Class type) throws UnreportedEntityException {
		super(new Count(type));
		this.getMainEntity().setLoadArguments(this);
	}

}
