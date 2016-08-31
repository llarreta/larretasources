package co.com.directv.sdii.persistence.dao.stock;

import java.util.List;

import javax.ejb.Local;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.model.dto.MovementTypeClassDTO;
import co.com.directv.sdii.model.pojo.MovementType;
import co.com.directv.sdii.model.pojo.collection.MovementTypeResponse;
import co.com.directv.sdii.model.pojo.collection.RequestCollectionInfo;

/**
 * 
 * Interface Local para la gestión del CRUD de la
 * Entidad MovementType
 * 
 * Fecha de Creación: Mar 8, 2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@Local
public interface MovementTypeDAOLocal {

	/**
	 * Metodo:  persiste la información de un MovementType
	 * @param obj objeto que encapsula la información de un MovementType
	 * @throws DAOServiceException en caso de error al ejecutar la creación de MovementType
	 * @throws DAOSQLException en caso de error al ejecutar la creación de MovementType
	 * @author jjimenezh
	 */
	public void createMovementType(MovementType obj) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: actualiza la información de un MovementType
	 * @param obj objeto que encapsula la información de un MovementType
	 * @throws DAOServiceException en caso de error al ejecutar la actualización de MovementType
	 * @throws DAOSQLException en caso de error al ejecutar la actualización de MovementType
	 * @author jjimenezh
	 */
	public void updateMovementType(MovementType obj) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: Borra de la persistencia la información de un MovementType
	 * @param obj información del MovementType a ser borrado
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la eliminación de MovementType
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la eliminación de MovementType
	 * @author jjimenezh
	 */
	public void deleteMovementType(MovementType obj) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: Obtiene la información de un MovementType por su identificador
	 * @param id identificador del MovementType a ser consultado
	 * @return objeto con la información del MovementType dado su identificador, nulo en caso que no se encuentre
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la consulta de MovementType por ID
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la consulta de MovementType por ID
	 * @author jjimenezh
	 */
	public MovementType getMovementTypeByID(Long id) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: Obtiene la información de todos los MovementType almacenados en la persistencia
	 * @return Lista con los MovementType existentes, una lista vacia en caso que no existan MovementType en el sistema
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la consulta de todos los MovementType
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la consulta de todos los MovementType
	 * @author jjimenezh
	 */
	public List<MovementType> getAllMovementTypes() throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: Obtiene la información de todos los MovementClass
	 * @return Lista con los MovementClass existentes
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la consulta de todos los MovementType
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la consulta de todos los MovementType
	 * @author cduarte
	 */
	public List<MovementTypeClassDTO> getAllMovementTypesClass() throws DAOServiceException, DAOSQLException;
    

	/**
	 * Metodo: Obtiene el tipo de modificación de acuerdo al código de la misma
	 * @param code código del tipo de modificación
	 * @return tipo de modificación con el código especificado
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la consulta de MovementType por código
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la consulta de MovementType por código
	 * @author jjimenezh
	 */
	public MovementType getMovementTypeByCode(String code)throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: Obtiene el tipo de modificación de acuerdo al nombre de la misma
	 * @param name nombre del tipo de modificación
	 * @return tipo de modificación con el nombre especificado
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la consulta de MovementType por nombre
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la consulta de MovementType por nombre
	 * @author jjimenezh
	 */
	public MovementType getMovementTypeByName(String name)throws DAOServiceException, DAOSQLException;

	/**
	 * Metodo: Obtiene el tipo de movimiento dado su estado
	 * @param codeStatus - String Estado del tipo de movimiento que se va a consultar
	 * @return List<MovementType> correspondiente al estado especificado; vacio en otro caso
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la consulta de MovementType por estado
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la consulta de MovementType por estado
	 * @author gfandino
	 */
	public List<MovementType> getMovementTypesStatus(String codeStatus)throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: Obtiene el tipo de movimiento dado su estado
	 * @param codeStatus - String Estado del tipo de movimiento que se va a consultar
	 * @param requestCollInfo - RequestCollectionInfo info de paginación
	 * @return MovementTypeResponse con la información de la consulta
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la consulta de MovementType por estado
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la consulta de MovementType por estado
	 * @author gfandino
	 */
	public MovementTypeResponse getMovementTypesStatus(String codeStatus,RequestCollectionInfo requestCollInfo)throws DAOServiceException, DAOSQLException;

	/**
	 * Método: Obtiene los tipos de movimiento en cualquier estado 
	 * @param code - String código del tipo de movimiento; nulo si no aplica
	 * @param requestCollInfo -  RequestCollectionInfo información de la paginación
	 * @return MovementTypeResponse
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la consulta de MovementType 
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la consulta de MovementType 
	 */
	public MovementTypeResponse getMovementTypesAllStatusPage(
			String code, RequestCollectionInfo requestCollInfo)throws DAOServiceException, DAOSQLException;

	/**
	 * Metodo: Obtiene todos los MovementType almacenados que esten activos y que correspondas a la clase de movimiento 
	 * enviada como parametro
	 * @param moveClass 
	 * @return lista con los MovementType existentes que concidan con el criterio de busqueda
	 * una lista vacia en caso que no exista ninguno.
	 * @throws DAOServiceException
	 * @throws DAOSQLException <tipo> <descripcion>
	 * @author
	 */
	public List<MovementType> getMovementTypesAtiveByClass(String moveClass)throws DAOServiceException, DAOSQLException;

}