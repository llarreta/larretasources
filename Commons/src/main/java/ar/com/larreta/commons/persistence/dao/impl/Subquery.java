package ar.com.larreta.commons.persistence.dao.impl;

import java.util.Iterator;

import org.hibernate.Query;

import ar.com.larreta.commons.AppManager;
import ar.com.larreta.commons.persistence.dao.args.LoadArguments;

public abstract class Subquery extends Where {
	
	private LoadArguments argsSub;
	
	public Subquery(LoadArguments args, String field, String operator, LoadArguments argsSub) {
		super(args, field, operator, null);
		this.argsSub = argsSub;
	}
	
	protected StringBuilder getSubquery(){
		StandardDAOImpl standardDAO = (StandardDAOImpl) AppManager.getInstance().getBean(StandardDAOImpl.STANDAR_DAO);
		return standardDAO.makeHQL(argsSub);
	}

	@Override
	public String getPostOperatorSection() {
		return "(" + getSubquery() + ")";
	}

	@Override
	public void setQueryValue(Query query) {
		if (argsSub.getWheres()!=null){
			Iterator<Where> it = argsSub.getWheres().iterator();
			while (it.hasNext()) {
				Where where = (Where) it.next();
				where.setQueryValue(query);
			}
		}
	}
	
}
