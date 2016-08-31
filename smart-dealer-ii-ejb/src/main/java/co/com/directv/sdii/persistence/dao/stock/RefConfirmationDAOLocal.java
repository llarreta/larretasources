package co.com.directv.sdii.persistence.dao.stock;

import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.model.pojo.RefConfirmation;

/**
 * 
 * Interface Local para la gestión del CRUD de la
 * Entidad RefConfirmation
 * 
 * Fecha de Creación: Mar 8, 2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@Local
public interface RefConfirmationDAOLocal {

	/**
	 * Metodo:  persiste la información de un RefConfirmation
	 * @param obj objeto que encapsula la información de un RefConfirmation
	 * @throws DAOServiceException en caso de error al ejecutar la operación
	 * @throws DAOSQLException en caso de error al ejecutar la operación
	 * @author
	 */
	public void createRefConfirmation(RefConfirmation obj) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: actualiza la información de un RefConfirmation
	 * @param obj objeto que encapsula la información de un RefConfirmation
	 * @throws DAOServiceException en caso de error al ejecutar la operación
	 * @throws DAOSQLException en caso de error al ejecutar la operación
	 * @author
	 */
	public void updateRefConfirmation(RefConfirmation obj) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: Borra de la persistencia la información de un RefConfirmation
	 * @param obj información del RefConfirmation a ser borrado
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la operación
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la operación
	 * @author
	 */
	public void deleteRefConfirmation(RefConfirmation obj) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: Obtiene la información de un RefConfirmation por su identificador
	 * @param id identificador del RefConfirmation a ser consultado
	 * @return objeto con la información del RefConfirmation dado su identificador, nulo en caso que no se encuentre
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la operación
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la operación
	 * @author
	 */
	public RefConfirmation getRefConfirmationByID(Long id) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: Obtiene la información de todos los RefConfirmation almacenados en la persistencia
	 * @return Lista con los RefConfirmation existentes, una lista vacia en caso que no existan RefConfirmation en el sistema
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la operación
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la operación
	 * @author
	 */
	public List<RefConfirmation> getAllRefConfirmations() throws DAOServiceException, DAOSQLException;

	/**
	 * Metodo: Obtiene las confirmaciones parciales asociadas a un elemento que esta asociado a una remision
	 * @param elementId Long ID del elemento del que se desean obtener las confirmaciones parciales
	 * @return List<RefConfirmation> Lista de confirmaciones parciales
	 * @throws DAOServiceException En caso de error en la consulta de las confirmaciones
	 * @throws DAOSQLException En caso de error en la consulta de las confirmaciones
	 * @author jnova
	 */
	public List<RefConfirmation> getConfirmationsByElementId(Long elementId) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: Cuenta las unidades confirmadas asociadas a un elemento
	 * @param elementID Long Id del elemento
	 * @return Double Cantidad confirmada entre todas las confirmaciones asociadas a un elemento
	 * @throws DAOServiceException En caso de error en la consula de la cantidad de confirmacion
	 * @throws DAOSQLException En caso de error en la consula de la cantidad de confirmacion
	 * @author jnova
	 */
	public Double countElementConfirmedQuatity(Long referenceElementID) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: Obtiene las confirmaciones parciales asociadas a una remision
	 * @param referenceId Long ID de la remision
	 * @return List<RefConfirmation> Lista de confirmaciones parciales
	 * @throws DAOServiceException En caso de error en la consulta de las confirmaciones
	 * @throws DAOSQLException En caso de error en la consulta de las confirmaciones
	 * @author garciniegas
	 */
	public List<RefConfirmation> getConfirmationsByReferenceId( Long referenceId ) throws DAOServiceException, DAOSQLException;

	
	/**
	 * Metodo: Obtiene las confirmaciones parciales asociadas a una remision y a un elemento en especifico
	 * @param referenceId Long ID de la remision
	 * @param elementId Long ID del elemento
	 * @return List<RefConfirmation> Lista de confirmaciones parciales
	 * @throws DAOServiceException En caso de error en la consulta de las confirmaciones
	 * @throws DAOSQLException En caso de error en la consulta de las confirmaciones
	 * @author garciniegas
	 */
	public List<RefConfirmation> getConfirmationsByReferenceIdAndElementId( Long referenceId,Long elementId ) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Obtiene las confirmaciones según el id del item
	 * @param referenceElementId
	 * @return
	 * @throws DAOServiceException
	 * @throws DAOSQLException
	 * @author aquevedo
	 */
	public List<RefConfirmation> getConfirmationsByReferenceElementId( Long referenceElementId ) throws DAOServiceException, DAOSQLException;
}