package co.com.directv.sdii.persistence.dao.assign;

import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.model.pojo.ServiceSuperCategory;

/**
 * 
 * Interface Local para la gestión del CRUD de la
 * Entidad ServiceSuperCategory
 * 
 * Fecha de Creación: Mar 8, 2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@Local
public interface ServiceSuperCategoryDAOLocal {

	/**
	 * Metodo:  persiste la información de un ServiceSuperCategory
	 * @param obj objeto que encapsula la información de un ServiceSuperCategory
	 * @throws DAOServiceException en caso de error al ejecutar la operación
	 * @throws DAOSQLException en caso de error al ejecutar la operación
	 * @author
	 */
	public void createServiceSuperCategory(ServiceSuperCategory obj) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: actualiza la información de un ServiceSuperCategory
	 * @param obj objeto que encapsula la información de un ServiceSuperCategory
	 * @throws DAOServiceException en caso de error al ejecutar la operación
	 * @throws DAOSQLException en caso de error al ejecutar la operación
	 * @author
	 */
	public void updateServiceSuperCategory(ServiceSuperCategory obj) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: Borra de la persistencia la información de un ServiceSuperCategory
	 * @param obj información del ServiceSuperCategory a ser borrado
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la operación
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la operación
	 * @author
	 */
	public void deleteServiceSuperCategory(ServiceSuperCategory obj) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: Obtiene la información de un ServiceSuperCategory por su identificador
	 * @param id identificador del ServiceSuperCategory a ser consultado
	 * @return objeto con la información del ServiceSuperCategory dado su identificador, nulo en caso que no se encuentre
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la operación
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la operación
	 * @author
	 */
	public ServiceSuperCategory getServiceSuperCategoryByID(Long id) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: Obtiene la información de todos los ServiceSuperCategory almacenados en la persistencia
	 * @return Lista con los ServiceSuperCategory existentes, una lista vacia en caso que no existan ServiceSuperCategory en el sistema
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la operación
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la operación
	 * @author
	 */
	public List<ServiceSuperCategory> getAllServiceSuperCategorys() throws DAOServiceException, DAOSQLException;

	/**
	 * Metodo: Obtiene la super categoría de servicio por el código del servicio
	 * @param serviceCode código del servicio
	 * @return super categoría de servicio
	 * @throws DAOServiceException en caso de error al realizar la consulta
	 * @throws DAOSQLException en caso de error al realizar la consulta
	 * @author jjimenezh
	 */
	public ServiceSuperCategory getServiceSuperCategoryByServiceCode(String serviceCode)throws DAOServiceException, DAOSQLException;


}