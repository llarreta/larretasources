package ar.com.larreta.persistence.dao;

import java.util.Collection;

import org.hibernate.Query;
import org.springframework.orm.hibernate4.LocalSessionFactoryBean;

import ar.com.larreta.persistence.dao.args.CountArguments;
import ar.com.larreta.persistence.dao.args.LoadArguments;
import ar.com.larreta.persistence.dao.impl.QueryMaked;
import ar.com.larreta.persistence.exceptions.CantBuildQueryException;

public interface LoadDao {
	public org.hibernate.SessionFactory getSessionFactory();
	public Collection load(Class type) throws CantBuildQueryException ;
	public Collection load(LoadArguments args) throws CantBuildQueryException ;
	public Long count(CountArguments args) throws CantBuildQueryException ;
	public QueryMaked makeQuery(LoadArguments args)  throws CantBuildQueryException ;
	public StringBuilder makeHQL(LoadArguments args);
	public Query getQuery(LoadArguments args,	StringBuilder hql) throws CantBuildQueryException ;
	public LocalSessionFactoryBean getCommonsSessionFactory();
	public void setCommonsSessionFactory(LocalSessionFactoryBean commonsSessionFactory);
}
