package ar.com.larreta.persistence.dao;

import java.io.Serializable;
import java.util.Collection;

import ar.com.larreta.persistence.dao.args.LoadArguments;
import ar.com.larreta.persistence.model.impl.PersistenceEntityImpl;

public interface StandardDAO extends LoadDao {
	/**
	 * Obtiene una entidad de la base de datos del tipo entityClass y filtrando por el id
	 */
	public PersistenceEntityImpl getEntity(Class entityClass, Serializable id);
	/**
	 * Obtienen una entidad de la base de datos, filtrando por wheres y proyectando las propiedades pasadas por parametro
	 * Tener presente que si la consulta retorna mas de una entidad, entonces se retorna solo la primera
	 * @param entityClass
	 * @param properties
	 * @param wheres
	 * @return
	 */
	public PersistenceEntityImpl getEntity(LoadArguments args);
	/**
	 * Obtiene una entidad de la base de datos del tipo entityClass filtrando por el campo field y el valor value
	 * @param entityClass
	 * @param field
	 * @param value
	 * @return
	 */
	public PersistenceEntityImpl getEntity(Class entityClass, String field, Object value);
	/**
	 * Crea una nueva entidad en la base de datos
	 * @param entity
	 */
	public void save(PersistenceEntityImpl entity);
	/**
	 * Crea o Actualiza una coleccion de entidades en la base de datos segun corresponda
	 * @param entities
	 */
	public void saveOrUpdate(Collection entities);
	/**
	 * Actualiza una entidad en la base de datos
	 * @param entity
	 */
	public void update(PersistenceEntityImpl entity);
	/**
	 * Crea o Actualiza una entidad en la base de datos segun corresponda
	 * @param entity
	 */
	public void saveOrUpdate(PersistenceEntityImpl entity);
	/**
	 * Limpia la session de hibernate
	 */
	public void clear();
	/**
	 * Elimina de la base de datos una entidad
	 * @param entity
	 */
	public void delete(PersistenceEntityImpl entity);
	/**
	 * Retorna una propiedad de tipo collection de una entidad pasada por parametro
	 * Precaucion!!! no usar dentro de Whiles, ya que incrementa considerablemente su costo
	 * @param entity
	 * @param property
	 * @return
	 */
	public Collection getPropertyCollection(PersistenceEntityImpl entity, String property);
	/**
	 * Retorna una propiedad de tipo Entidad de una Entidad pasada por parametro
	 * Precaucion!!! no usar dentro de Whiles, ya que incrementa considerablemente su costo
	 * @param entity
	 * @param property
	 * @return
	 */
	public PersistenceEntityImpl getProperty(PersistenceEntityImpl entity, String property);
}
