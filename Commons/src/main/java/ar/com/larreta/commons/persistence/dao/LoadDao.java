package ar.com.larreta.commons.persistence.dao;

import java.util.Collection;

import org.hibernate.Query;

import ar.com.larreta.commons.AppObject;
import ar.com.larreta.commons.persistence.CommonsSessionFactory;
import ar.com.larreta.commons.persistence.dao.args.CountArguments;
import ar.com.larreta.commons.persistence.dao.args.LoadArguments;

public interface LoadDao extends AppObject {
	public org.hibernate.SessionFactory getSessionFactory();
	public Collection load(Class type);
	public Collection load(LoadArguments args);
	public Long count(CountArguments args);
	public Query makeQuery(LoadArguments args);
	public StringBuilder makeHQL(LoadArguments args);
	public Query getQuery(LoadArguments args,	StringBuilder hql);
	public CommonsSessionFactory getCommonsSessionFactory();
	public void setCommonsSessionFactory(CommonsSessionFactory commonsSessionFactory);
}
