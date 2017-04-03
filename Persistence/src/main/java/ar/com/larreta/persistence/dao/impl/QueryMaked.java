package ar.com.larreta.persistence.dao.impl;

import java.io.Serializable;
import java.util.Collection;

import org.hibernate.Query;

public class QueryMaked implements Serializable {

	private String hql;
	private Collection values;
	private Query query;
	
	public QueryMaked(String hql, Collection values, Query query){
		this.hql = hql;
		this.values = values;
		this.query = query;
	}
	
	public String getHql() {
		return hql;
	}
	public void setHql(String hql) {
		this.hql = hql;
	}
	public Collection getValues() {
		return values;
	}
	public void setValues(Collection values) {
		this.values = values;
	}
	public Query getQuery() {
		return query;
	}
	public void setQuery(Query query) {
		this.query = query;
	}
	
	
	
}
