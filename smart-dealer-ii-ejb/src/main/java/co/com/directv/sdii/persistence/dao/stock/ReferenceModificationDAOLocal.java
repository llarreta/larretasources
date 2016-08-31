package co.com.directv.sdii.persistence.dao.stock;

import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.model.pojo.ReferenceModification;

/**
 * 
 * Interface Local para la gestión del CRUD de la
 * Entidad ReferenceModification
 * 
 * Fecha de Creación: Mar 8, 2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@Local
public interface ReferenceModificationDAOLocal {

	/**
	 * Metodo:  persiste la información de un ReferenceModification
	 * @param obj objeto que encapsula la información de un ReferenceModification
	 * @throws DAOServiceException en caso de error al ejecutar la operación
	 * @throws DAOSQLException en caso de error al ejecutar la operación
	 * @author
	 */
	public void createReferenceModification(ReferenceModification obj) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: actualiza la información de un ReferenceModification
	 * @param obj objeto que encapsula la información de un ReferenceModification
	 * @throws DAOServiceException en caso de error al ejecutar la operación
	 * @throws DAOSQLException en caso de error al ejecutar la operación
	 * @author
	 */
	public void updateReferenceModification(ReferenceModification obj) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: Borra de la persistencia la información de un ReferenceModification
	 * @param obj información del ReferenceModification a ser borrado
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la operación
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la operación
	 * @author
	 */
	public void deleteReferenceModification(ReferenceModification obj) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: Obtiene la información de un ReferenceModification por su identificador
	 * @param id identificador del ReferenceModification a ser consultado
	 * @return objeto con la información del ReferenceModification dado su identificador, nulo en caso que no se encuentre
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la operación
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la operación
	 * @author
	 */
	public ReferenceModification getReferenceModificationByID(Long id) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: Obtiene la información de todos los ReferenceModification almacenados en la persistencia
	 * @return Lista con los ReferenceModification existentes, una lista vacia en caso que no existan ReferenceModification en el sistema
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la operación
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la operación
	 * @author
	 */
	public List<ReferenceModification> getAllReferenceModifications() throws DAOServiceException, DAOSQLException;

	public List<ReferenceModification> getReferenceModificationsByReferenceID(
			Long refID)throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: Obtiene una lista de objetos en donde viene las modificaciones a una remision junto con los usuario que las realizaron
	 * @param refID Long ID de la remision
	 * @return List<Object[]> Lista en donde en cada posicion esta la modificacion junto con el usuario
	 * @throws DAOServiceException En caso de que ocurra un error consulta las modificaiones o los usuarios
	 * @throws DAOSQLException En caso de que ocurra un error consulta las modificaiones o los usuarios
	 * @author jnova
	 */
	public List<Object[]> getReferenceModificationsAndUsersModificationsByReferenceID(Long refID)throws DAOServiceException, DAOSQLException;

}