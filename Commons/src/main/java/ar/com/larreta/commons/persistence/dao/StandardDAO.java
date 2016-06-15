package ar.com.larreta.commons.persistence.dao;

import java.io.Serializable;
import java.util.Collection;

import ar.com.larreta.commons.domain.Entity;
import ar.com.larreta.commons.persistence.dao.args.LoadArguments;
import ar.com.larreta.commons.persistence.exceptions.UnreportedEntityException;

public interface StandardDAO extends LoadDao {
	/**
	 * Obtiene una entidad de la base de datos del tipo entityClass y filtrando por el id
	 */
	public Entity getEntity(Class entityClass, Serializable id) throws UnreportedEntityException;
	/**
	 * Obtienen una entidad de la base de datos, filtrando por wheres y proyectando las propiedades pasadas por parametro
	 * Tener presente que si la consulta retorna mas de una entidad, entonces se retorna solo la primera
	 * @param entityClass
	 * @param properties
	 * @param wheres
	 * @return
	 */
	public Entity getEntity(LoadArguments args);
	/**
	 * Obtiene una entidad de la base de datos del tipo entityClass filtrando por el campo field y el valor value
	 * @param entityClass
	 * @param field
	 * @param value
	 * @return
	 */
	public Entity getEntity(Class entityClass, String field, Object value)  throws UnreportedEntityException;
	/**
	 * Crea una nueva entidad en la base de datos
	 * @param entity
	 */
	public void save(Entity entity);
	/**
	 * Crea o Actualiza una coleccion de entidades en la base de datos segun corresponda
	 * @param entities
	 */
	public void saveOrUpdate(Collection entities);
	/**
	 * Actualiza una entidad en la base de datos
	 * @param entity
	 */
	public void update(Entity entity);
	/**
	 * Crea o Actualiza una entidad en la base de datos segun corresponda
	 * @param entity
	 */
	public void saveOrUpdate(Entity entity);
	/**
	 * Limpia la session de hibernate
	 */
	public void clear();
	/**
	 * Elimina de la base de datos una entidad
	 * @param entity
	 */
	public void delete(Entity entity);
	/**
	 * Retorna una propiedad de tipo collection de una entidad pasada por parametro
	 * Precaucion!!! no usar dentro de Whiles, ya que incrementa considerablemente su costo
	 * @param entity
	 * @param property
	 * @return
	 */
	public Collection getPropertyCollection(Entity entity, String property);
	/**
	 * Retorna una propiedad de tipo Entidad de una Entidad pasada por parametro
	 * Precaucion!!! no usar dentro de Whiles, ya que incrementa considerablemente su costo
	 * @param entity
	 * @param property
	 * @return
	 */
	public Entity getProperty(Entity entity, String property);
}
