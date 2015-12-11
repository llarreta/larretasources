package ar.com.larreta.commons.persistence.dao.impl;

public class LeftJoin extends JoinedEntity {

	public LeftJoin(LoadArguments args, String property) {
		super(args, StandardDAOImpl.LEFT_JOIN, property);
	}

}
