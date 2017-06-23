package ar.com.larreta.persistence.dao.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import ar.com.larreta.persistence.aspects.InjectId;
import ar.com.larreta.persistence.dao.StandardDAO;
import ar.com.larreta.persistence.dao.args.LoadArguments;
import ar.com.larreta.persistence.exceptions.CantBuildQueryException;
import ar.com.larreta.persistence.model.Entity;

/**
 * DAO con las funcionalidades basicas resueltas
 * Grabar, actualizar, borrar y buscar 
 */
@Repository(StandardDAOImpl.STANDAR_DAO)
public class StandardDAOImpl extends LoadDAOImpl implements StandardDAO{

	public static final String STANDAR_DAO = "standarDAO";
	public static final String MAIN = "M";
	public static final String PROPERTY = "P";

	/**
	 * Obtiene una entidad de la base de datos del tipo entityClass y filtrando por el id
	 * @throws CantBuildQueryException 
	 * @throws UnreportedEntityException 
	 */
	public Entity getEntity(Class entityClass, Serializable id) throws CantBuildQueryException{
		LoadArguments args = new LoadArguments(entityClass);
		args.addWhereEqual("id", id);
		return getEntity(args);
	}

	/**
	 * Obtienen una entidad de la base de datos, filtrando por wheres y proyectando las propiedades pasadas por parametro
	 * Tener presente que si la consulta retorna mas de una entidad, entonces se retorna solo la primera
	 * @param entityClass
	 * @param properties
	 * @param wheres
	 * @return
	 * @throws CantBuildQueryException 
	 */
	public Entity getEntity(LoadArguments args) throws CantBuildQueryException {
		Entity entity = null;
		Collection<Entity> result = load(args);
		if ((result!=null) && (!result.isEmpty())){
			entity = result.iterator().next();
		}
		
		return entity;
	}
	
	/**
	 * Obtiene una entidad de la base de datos del tipo entityClass filtrando por el campo field y el valor value
	 * @param entityClass
	 * @param field
	 * @param value
	 * @return
	 * @throws CantBuildQueryException 
	 * @throws UnreportedEntityException 
	 */
	public Entity getEntity(Class entityClass, String field, Object value) throws CantBuildQueryException{
		LoadArguments args = new LoadArguments(entityClass);
		args.addWhereEqual(field, value);
		return getEntity(args);
	}

	/**
	 * Crea una nueva entidad en la base de datos
	 * @param entity
	 */
	@InjectId
	public void save(Entity entity){
		//No colocar el ExecutionStatisticsDirector aqui porque se volveria recursivo ya que se invoca desde el mismo SaveThread
		getSessionFactory().getCurrentSession().save(entity);
	}
	
	/**
	 * Crea o Actualiza una coleccion de entidades en la base de datos segun corresponda
	 * @param entities
	 */
	public void saveOrUpdate(Collection entities){
		if (entities!=null){
			Iterator it = entities.iterator();
			while (it.hasNext()) {
				saveOrUpdate((Entity)it.next());
			}
		}
	}
	
	/**
	 * Actualiza una entidad en la base de datos
	 * @param entity
	 */
	public void update(Entity entity){
		getSessionFactory().getCurrentSession().update(entity);
	}
	
	/**
	 * Crea o Actualiza una entidad en la base de datos segun corresponda
	 * @param entity
	 */
	public void saveOrUpdate(Entity entity){
		getSessionFactory().getCurrentSession().saveOrUpdate(entity);
	}

	/**
	 * Limpia la session de hibernate
	 */
	public void clear(){
		getSessionFactory().getCurrentSession().clear();
	}

	/**
	 * Elimina de la base de datos una entidad
	 * @param entity
	 */
	public void delete(Entity entity){
		getSessionFactory().getCurrentSession().delete(entity);
	}
	
	/**
	 * Retorna una propiedad de tipo collection de una entidad pasada por parametro
	 * Precaucion!!! no usar dentro de Whiles, ya que incrementa considerablemente su costo
	 * @param entity
	 * @param property
	 * @return
	 */
	public Collection getPropertyCollection(Entity entity, String property){
		if ((entity==null)||(property==null)){
			return new ArrayList();
		}
		StringBuilder hql = new StringBuilder();
		hql.append(SELECT).append(VOID).append(PROPERTY).append(VOID);
		hql.append(FROM).append(VOID).append(MainEntity.getEntityName(entity.getClass())).append(VOID).append(MAIN).append(VOID);
		hql.append(INNER_JOIN).append(VOID).append(MAIN).append(DOT).append(property).append(VOID).append(PROPERTY).append(VOID);
		hql.append(WHERE).append(VOID).append(MAIN).append(DOT).append(ID).append(EQUAL).append(TWO_POINTS).append(ID);
		
		Query query = getSessionFactory().getCurrentSession().createQuery(hql.toString());
		query.setLong(ID, entity.getId());
		Collection result = query.list();
		return result;
	}
	
	/**
	 * Retorna una propiedad de tipo Entidad de una Entidad pasada por parametro
	 * Precaucion!!! no usar dentro de Whiles, ya que incrementa considerablemente su costo
	 * @param entity
	 * @param property
	 * @return
	 */
	public Entity getProperty(Entity entity, String property){
		if ((entity==null)||(property==null)){
			return null;
		}
		StringBuilder hql = new StringBuilder();
		hql.append(SELECT).append(VOID).append(MAIN).append(DOT).append(property).append(VOID);
		hql.append(FROM).append(VOID).append(MainEntity.getEntityName(entity.getClass())).append(VOID).append(MAIN).append(VOID);
		hql.append(WHERE).append(VOID).append(MAIN).append(DOT).append(ID).append(EQUAL).append(TWO_POINTS).append(ID);
		
		Query query = getSessionFactory().getCurrentSession().createQuery(hql.toString());
		query.setLong(ID, entity.getId());
		Entity result = (Entity) query.uniqueResult();
		return result;
	}	
	
}