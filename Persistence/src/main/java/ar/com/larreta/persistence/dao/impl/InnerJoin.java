package ar.com.larreta.persistence.dao.impl;

import ar.com.larreta.persistence.dao.args.LoadArguments;

public class InnerJoin extends JoinedEntity {

	public InnerJoin(LoadArguments args, String property) {
		super(args, StandardDAOImpl.INNER_JOIN, property);
	}

}
