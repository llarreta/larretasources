package ar.com.larreta.mystic.query;

import java.io.Serializable;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import ar.com.larreta.mystic.model.Entity;
import ar.com.larreta.tools.Const;

@Component @Scope(Const.PROTOTYPE)
public class Persister extends Query {

	public void save(Entity entity){
		sessionFactory.getCurrentSession().save(entity);
	}
	
	public void saveOrUpdate(Entity entity){
		sessionFactory.getCurrentSession().saveOrUpdate(entity);
	}
	
	public void update(Entity entity){
		sessionFactory.getCurrentSession().update(entity);
	}
	
	public void delete(Entity entity){
		sessionFactory.getCurrentSession().delete(entity);
	}
	
	public Entity get(Class type, Serializable id){
		return (Entity) sessionFactory.getCurrentSession().get(type, id);
	}
	
	
}
