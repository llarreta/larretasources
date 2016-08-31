package co.com.directv.sdii.persistence.dao.stock;

import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.model.pojo.NotSerPartialRetirementId;

/**
 * 
 * Interface Local para la gestión del CRUD de la
 * Entidad NotSerPartialRetirementId
 * 
 * Fecha de Creación: Mar 8, 2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@Local
public interface NotSerPartialRetirementIdDAOLocal {

	/**
	 * Metodo:  persiste la información de un NotSerPartialRetirementId
	 * @param obj objeto que encapsula la información de un NotSerPartialRetirementId
	 * @throws DAOServiceException en caso de error al ejecutar la operación
	 * @throws DAOSQLException en caso de error al ejecutar la operación
	 * @author
	 */
	public void createNotSerPartialRetirementId(NotSerPartialRetirementId obj) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: actualiza la información de un NotSerPartialRetirementId
	 * @param obj objeto que encapsula la información de un NotSerPartialRetirementId
	 * @throws DAOServiceException en caso de error al ejecutar la operación
	 * @throws DAOSQLException en caso de error al ejecutar la operación
	 * @author
	 */
	public void updateNotSerPartialRetirementId(NotSerPartialRetirementId obj) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: Borra de la persistencia la información de un NotSerPartialRetirementId
	 * @param obj información del NotSerPartialRetirementId a ser borrado
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la operación
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la operación
	 * @author
	 */
	public void deleteNotSerPartialRetirementId(NotSerPartialRetirementId obj) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: Obtiene la información de un NotSerPartialRetirementId por su identificador
	 * @param id identificador del NotSerPartialRetirementId a ser consultado
	 * @return objeto con la información del NotSerPartialRetirementId dado su identificador, nulo en caso que no se encuentre
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la operación
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la operación
	 * @author
	 */
	public NotSerPartialRetirementId getNotSerPartialRetirementIdByID(Long id) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: Obtiene la información de todos los NotSerPartialRetirementId almacenados en la persistencia
	 * @return Lista con los NotSerPartialRetirementId existentes, una lista vacia en caso que no existan NotSerPartialRetirementId en el sistema
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la operación
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la operación
	 * @author
	 */
	public List<NotSerPartialRetirementId> getAllNotSerPartialRetirementIds() throws DAOServiceException, DAOSQLException;


}