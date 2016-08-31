package co.com.directv.sdii.persistence.dao.stock;

import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.model.pojo.AdjustmentModification;

/**
 * Interface Local para la gestión del CRUD de la
 * Entidad AdjustmentModification
 * 
 * Fecha de Creación: 28/11/2012
 * @author cduarte <a href="mailto:cduarte@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see     
 */
@Local
public interface AdjustmentModificationDAOLocal {

	/**
	 * Metodo:  persiste la información de un AdjustmentModification
	 * @param obj objeto que encapsula la información de un AdjustmentModification
	 * @throws DAOServiceException en caso de error al ejecutar la operación
	 * @throws DAOSQLException en caso de error al ejecutar la operación
	 * @author
	 */
	public void createAdjustmentModification(AdjustmentModification obj) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: actualiza la información de un AdjustmentModification
	 * @param obj objeto que encapsula la información de un AdjustmentModification
	 * @throws DAOServiceException en caso de error al ejecutar la operación
	 * @throws DAOSQLException en caso de error al ejecutar la operación
	 * @author
	 */
	public void updateAdjustmentModification(AdjustmentModification obj) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: Borra de la persistencia la información de un AdjustmentModification
	 * @param obj información del AdjustmentModification a ser borrado
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la operación
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la operación
	 * @author
	 */
	public void deleteAdjustmentModification(AdjustmentModification obj) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: Obtiene la información de un AdjustmentModification por su identificador
	 * @param id identificador del AdjustmentModification a ser consultado
	 * @return objeto con la información del AdjustmentModification dado su identificador, nulo en caso que no se encuentre
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la operación
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la operación
	 * @author
	 */
	public AdjustmentModification getAdjustmentModificationByID(Long id) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: Obtiene la información de todos los AdjustmentModification almacenados en la persistencia
	 * @return Lista con los AdjustmentModification existentes, una lista vacia en caso que no existan AdjustmentModification en el sistema
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la operación
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la operación
	 * @author
	 */
	public List<AdjustmentModification> getAllAdjustmentModifications() throws DAOServiceException, DAOSQLException;

	/**
	 * Metodo: permite consultar todos los AdjustmentModification de un ajuste
	 * @param adjID
	 * @return
	 * @throws DAOServiceException
	 * @throws DAOSQLException <tipo> <descripcion>
	 * @author
	 */
	public List<AdjustmentModification> getAdjustmentModificationsByAdjustmentID(
			Long adjID)throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: Obtiene una lista de objetos en donde viene las modificaciones a un ajuste junto con los usuario que las realizaron
	 * @param adjID Long ID de la remision
	 * @return List<Object[]> Lista en donde en cada posicion esta la modificacion junto con el usuario
	 * @throws DAOServiceException En caso de que ocurra un error consulta las modificaiones o los usuarios
	 * @throws DAOSQLException En caso de que ocurra un error consulta las modificaiones o los usuarios
	 * @author jnova
	 */
	public List<Object[]> getAdjustmentModificationsAndUsersModificationsByAdjustmentID(Long adjID)throws DAOServiceException, DAOSQLException;

}