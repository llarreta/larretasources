package ar.com.larreta.persistence.dao.impl;

import org.hibernate.Query;

import ar.com.larreta.persistence.dao.args.LoadArguments;

public class Or extends Where {
	
	private Where first;
	private Where second;

	public Or(LoadArguments args, Where first,  Where second) {
		super(args, null, "OR", null);
		this.first=first;
		this.second=second;
	}
	
	protected void addWhere(LoadArguments args, StringBuilder hql) {
		hql.append(LoadDAOImpl.VOID);
		hql.append("(");
		getFirst().addWhere(args, hql);
		hql.append(LoadDAOImpl.VOID);
		hql.append("OR");
		hql.append(LoadDAOImpl.VOID);
		getSecond().addWhere(args, hql);
		hql.append(")");
		hql.append(LoadDAOImpl.VOID);
	}
	
	public Where getFirst() {
		return first;
	}

	public void setFirst(Where first) {
		this.first = first;
	}

	public Where getSecond() {
		return second;
	}

	public void setSecond(Where second) {
		this.second = second;
	}

	@Override
	public void setQueryValue(Query query) {
		getFirst().setQueryValue(query);
		getSecond().setQueryValue(query);
	}
}
