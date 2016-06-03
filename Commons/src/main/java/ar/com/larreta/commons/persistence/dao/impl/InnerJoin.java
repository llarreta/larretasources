package ar.com.larreta.commons.persistence.dao.impl;

import ar.com.larreta.commons.persistence.dao.args.LoadArguments;

public class InnerJoin extends JoinedEntity {

	public InnerJoin(LoadArguments args, String property) {
		super(args, StandardDAOImpl.INNER_JOIN, property);
	}

}
