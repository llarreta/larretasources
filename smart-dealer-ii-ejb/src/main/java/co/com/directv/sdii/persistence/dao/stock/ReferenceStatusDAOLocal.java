package co.com.directv.sdii.persistence.dao.stock;

import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.model.pojo.ReferenceStatus;

/**
 * 
 * Interface Local para la gestión del CRUD de la
 * Entidad ReferenceStatus
 * 
 * Fecha de Creación: Mar 8, 2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@Local
public interface ReferenceStatusDAOLocal {

	/**
	 * Metodo:  persiste la información de un ReferenceStatus
	 * @param obj objeto que encapsula la información de un ReferenceStatus
	 * @throws DAOServiceException en caso de error al ejecutar la creación de ReferenceStatus
	 * @throws DAOSQLException en caso de error al ejecutar la creación de ReferenceStatus
	 * @author gfandino
	 */
	public void createReferenceStatus(ReferenceStatus obj) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: actualiza la información de un ReferenceStatus
	 * @param obj objeto que encapsula la información de un ReferenceStatus
	 * @throws DAOServiceException en caso de error al ejecutar la actualización de ReferenceStatus
	 * @throws DAOSQLException en caso de error al ejecutar la actualización de ReferenceStatus
	 * @author gfandino
	 */
	public void updateReferenceStatus(ReferenceStatus obj) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: Borra de la persistencia la información de un ReferenceStatus
	 * @param obj información del ReferenceStatus a ser borrado
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la eliminación de ReferenceStatus
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la eliminación de ReferenceStatus
	 * @author gfandino
	 */
	public void deleteReferenceStatus(ReferenceStatus obj) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: Obtiene la información de un ReferenceStatus por su identificador
	 * @param id identificador del ReferenceStatus a ser consultado
	 * @return objeto con la información del ReferenceStatus dado su identificador, nulo en caso que no se encuentre
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la consulta de ReferenceStatus por ID
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la consulta de ReferenceStatus por ID
	 * @author gfandino
	 */
	public ReferenceStatus getReferenceStatusByID(Long id) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: Obtiene la información de todos los ReferenceStatus almacenados en la persistencia
	 * @return Lista con los ReferenceStatus existentes, una lista vacia en caso que no existan ReferenceStatus en el sistema
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la consulta de todos los ReferenceStatus
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la consulta de todos los ReferenceStatus
	 * @author gfandino
	 */
	public List<ReferenceStatus> getAllReferenceStatuss() throws DAOServiceException, DAOSQLException;

	/**
	 * Metodo: Obtiene la información de un ReferenceStatus por su código
	 * @param refStatusCode - String Código del ReferenceStatus a ser consultado
	 * @return ReferenceStatus con la información del ReferenceStatus dado su código, nulo en caso que no se encuentre
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la consulta de ReferenceStatus por código
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la consulta de ReferenceStatus por código
	 * @author gfandino
	 */
	public ReferenceStatus getReferenceByCode(String refStatusCode)throws DAOServiceException, DAOSQLException;


}