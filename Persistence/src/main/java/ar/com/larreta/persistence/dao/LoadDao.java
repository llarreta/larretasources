package ar.com.larreta.persistence.dao;

import java.util.Collection;

import org.hibernate.Query;
import org.springframework.orm.hibernate4.LocalSessionFactoryBean;

import ar.com.larreta.persistence.dao.args.CountArguments;
import ar.com.larreta.persistence.dao.args.LoadArguments;
import ar.com.larreta.persistence.dao.impl.QueryMaked;

public interface LoadDao {
	public org.hibernate.SessionFactory getSessionFactory();
	public Collection load(Class type);
	public Collection load(LoadArguments args);
	public Long count(CountArguments args);
	public QueryMaked makeQuery(LoadArguments args);
	public StringBuilder makeHQL(LoadArguments args);
	public Query getQuery(LoadArguments args,	StringBuilder hql);
	public LocalSessionFactoryBean getCommonsSessionFactory();
	public void setCommonsSessionFactory(LocalSessionFactoryBean commonsSessionFactory);
}
