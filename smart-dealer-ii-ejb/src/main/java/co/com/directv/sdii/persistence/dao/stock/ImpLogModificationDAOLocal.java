package co.com.directv.sdii.persistence.dao.stock;

import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.model.pojo.ImpLogModification;

/**
 * 
 * Interface Local para la gestión del CRUD de la
 * Entidad ImpLogModification
 * 
 * Fecha de Creación: Mar 8, 2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@Local
public interface ImpLogModificationDAOLocal {

	/**
	 * Metodo:  persiste la información de un ImpLogModification
	 * @param obj objeto que encapsula la información de un ImpLogModification
	 * @throws DAOServiceException en caso de error al ejecutar la operación
	 * @throws DAOSQLException en caso de error al ejecutar la operación
	 * @author
	 */
	public void createImpLogModification(ImpLogModification obj) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: actualiza la información de un ImpLogModification
	 * @param obj objeto que encapsula la información de un ImpLogModification
	 * @throws DAOServiceException en caso de error al ejecutar la operación
	 * @throws DAOSQLException en caso de error al ejecutar la operación
	 * @author
	 */
	public void updateImpLogModification(ImpLogModification obj) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: Borra de la persistencia la información de un ImpLogModification
	 * @param obj información del ImpLogModification a ser borrado
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la operación
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la operación
	 * @author
	 */
	public void deleteImpLogModification(ImpLogModification obj) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: Obtiene la información de un ImpLogModification por su identificador
	 * @param id identificador del ImpLogModification a ser consultado
	 * @return objeto con la información del ImpLogModification dado su identificador, nulo en caso que no se encuentre
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la operación
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la operación
	 * @author
	 */
	public ImpLogModification getImpLogModificationByID(Long id) throws DAOServiceException, DAOSQLException;
	
	/**
	 * 
	 * Metodo: Retorna listado del objeto ImpLogModification, segun un identificador del importLog
	 * @param id
	 * @return
	 * @throws DAOServiceException
	 * @throws DAOSQLException <tipo> <descripcion>
	 * @author
	 */
	public List<ImpLogModification> getImpLogModificationByImportLogID(Long id) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: Obtiene la información de todos los ImpLogModification almacenados en la persistencia
	 * @return Lista con los ImpLogModification existentes, una lista vacia en caso que no existan ImpLogModification en el sistema
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la operación
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la operación
	 * @author
	 */
	public List<ImpLogModification> getAllImpLogModifications() throws DAOServiceException, DAOSQLException;

	/**
	 * 
	 * Metodo: Borra registro de importacion de la tabla 'imp_log_modificactios'
	 * @param impLogID
	 * @throws DAOServiceException
	 * @throws DAOSQLException <tipo> <descripcion>
	 * @author
	 */
	void deleteImpLogModificationByImportLogId(Long impLogID) throws DAOServiceException, DAOSQLException;

	/**
	 * Obtiene las modificaciones de import log por su estado
	 * @param id
	 * @param statusId
	 * @return
	 * @throws DAOServiceException
	 * @throws DAOSQLException
	 */
	public List<ImpLogModification> getImpLogModificationByImportLogIDAndStatusId(Long id, Long statusId) throws DAOServiceException, DAOSQLException;
}