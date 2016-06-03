package ar.com.larreta.commons.persistence.dao.impl;

import ar.com.larreta.commons.persistence.dao.args.LoadArguments;

public class LeftJoin extends JoinedEntity {

	public LeftJoin(LoadArguments args, String property) {
		super(args, StandardDAOImpl.LEFT_JOIN, property);
	}

}
