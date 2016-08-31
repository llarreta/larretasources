package co.com.directv.sdii.persistence.dao.stock;

import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.model.pojo.ImpLogModificationType;

/**
 * 
 * Interface Local para la gestión del CRUD de la
 * Entidad ImpLogModificationType
 * 
 * Fecha de Creación: Mar 8, 2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@Local
public interface ImpLogModificationTypeDAOLocal {

	/**
	 * Metodo:  persiste la información de un ImpLogModificationType
	 * @param obj objeto que encapsula la información de un ImpLogModificationType
	 * @throws DAOServiceException en caso de error al ejecutar la operación
	 * @throws DAOSQLException en caso de error al ejecutar la operación
	 * @author jjimenezh
	 */
	public void createImpLogModificationType(ImpLogModificationType obj) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: actualiza la información de un ImpLogModificationType
	 * @param obj objeto que encapsula la información de un ImpLogModificationType
	 * @throws DAOServiceException en caso de error al ejecutar la operación
	 * @throws DAOSQLException en caso de error al ejecutar la operación
	 * @author jjimenezh
	 */
	public void updateImpLogModificationType(ImpLogModificationType obj) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: Borra de la persistencia la información de un ImpLogModificationType
	 * @param obj información del ImpLogModificationType a ser borrado
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la operación
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la operación
	 * @author jjimenezh
	 */
	public void deleteImpLogModificationType(ImpLogModificationType obj) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: Obtiene la información de un ImpLogModificationType por su identificador
	 * @param id identificador del ImpLogModificationType a ser consultado
	 * @return objeto con la información del ImpLogModificationType dado su identificador, nulo en caso que no se encuentre
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la operación
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la operación
	 * @author jjimenezh
	 */
	public ImpLogModificationType getImpLogModificationTypeByID(Long id) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: Obtiene la información de todos los ImpLogModificationType almacenados en la persistencia
	 * @return Lista con los ImpLogModificationType existentes, una lista vacia en caso que no existan ImpLogModificationType en el sistema
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la operación
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la operación
	 * @author jjimenezh
	 */
	public List<ImpLogModificationType> getAllImpLogModificationTypes() throws DAOServiceException, DAOSQLException;

	/**
	 * Metodo: Obtiene la información de todos los ImpLogModificationType almacenados en la persistencia
	 * @return Lista con los ImpLogModificationType existentes, una lista vacia en caso que no existan ImpLogModificationType en el sistema
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la operación
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la operación
	 * @author jjimenezh
	 */
	public List<ImpLogModificationType> getActiveImpLogModificationTypes() throws DAOServiceException, DAOSQLException;

	/**
	 * Metodo: Obtiene un tipo de modificación de import log por el código
	 * @param code código del tipo de modificación a ser consultado
	 * @return tipo de modificación de import log, nulo en caso que no se encuentre
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la operación
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la operación
	 * @author jjimenezh
	 */
	public ImpLogModificationType getImpLogModificationTypeByCode(String code)throws DAOServiceException, DAOSQLException;


}