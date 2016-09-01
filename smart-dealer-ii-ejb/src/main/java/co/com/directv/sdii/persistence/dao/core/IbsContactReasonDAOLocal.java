package co.com.directv.sdii.persistence.dao.core;

import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.model.pojo.IbsContactReason;

/**
 * Interface Local para la gestión del CRUD de la
 * Entidad IbsContactReason
 * 
 * Fecha de Creación: 29/11/2012
 * @author cduarte <a href="mailto:cduarte@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see     
 */
@Local
public interface IbsContactReasonDAOLocal {

	/**
	 * Metodo:  persiste la información de un IbsContactReason
	 * @param obj objeto que encapsula la información de un IbsContactReason
	 * @throws DAOServiceException en caso de error al ejecutar la operación
	 * @throws DAOSQLException en caso de error al ejecutar la operación
	 * @author
	 */
	public void createIbsContactReason(IbsContactReason obj) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: actualiza la información de un IbsContactReason
	 * @param obj objeto que encapsula la información de un IbsContactReason
	 * @throws DAOServiceException en caso de error al ejecutar la operación
	 * @throws DAOSQLException en caso de error al ejecutar la operación
	 * @author
	 */
	public void updateIbsContactReason(IbsContactReason obj) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: Borra de la persistencia la información de un IbsContactReason
	 * @param obj información del IbsContactReason a ser borrado
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la operación
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la operación
	 * @author
	 */
	public void deleteIbsContactReason(IbsContactReason obj) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: Obtiene la información de un IbsContactReason por su identificador
	 * @param id identificador del IbsContactReason a ser consultado
	 * @return objeto con la información del IbsContactReason dado su identificador, nulo en caso que no se encuentre
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la operación
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la operación
	 * @author
	 */
	public IbsContactReason getIbsContactReasonByID(Long id) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: Obtiene la información de un IbsContactReason por su codigo
	 * @param id identificador del IbsContactReason a ser consultado
	 * @return objeto con la información del IbsContactReason dado su identificador, nulo en caso que no se encuentre
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la operación
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la operación
	 * @author
	 */
	public IbsContactReason getIbsContactReasonByCode(String code,Long countryId) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: Obtiene la información de todos los IbsContactReason almacenados en la persistencia
	 * @return Lista con los IbsContactReason existentes, una lista vacia en caso que no existan IbsContactReason en el sistema
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la operación
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la operación
	 * @author
	 */
	public List<IbsContactReason> getAllIbsContactReasons(Long countryId) throws DAOServiceException, DAOSQLException;

}