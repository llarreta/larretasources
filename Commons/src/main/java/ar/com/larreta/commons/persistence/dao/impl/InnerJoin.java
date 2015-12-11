package ar.com.larreta.commons.persistence.dao.impl;

public class InnerJoin extends JoinedEntity {

	public InnerJoin(LoadArguments args, String property) {
		super(args, StandardDAOImpl.INNER_JOIN, property);
	}

}
