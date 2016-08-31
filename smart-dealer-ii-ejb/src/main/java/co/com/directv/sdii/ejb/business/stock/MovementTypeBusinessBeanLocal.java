package co.com.directv.sdii.ejb.business.stock;

import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.model.dto.MovementTypeClassDTO;
import co.com.directv.sdii.model.pojo.collection.MovementTypeResponse;
import co.com.directv.sdii.model.pojo.collection.RequestCollectionInfo;
import co.com.directv.sdii.model.vo.MovementTypeVO;

/**
 * 
 * Interfaz de las operaciones Tipo CRUD de la Entidad MovementType.
 * 
 * Fecha de Creación: Mar 8, 2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@Local
public interface MovementTypeBusinessBeanLocal {

	/**
	 * Metodo:  Persiste la información de un objeto MovementTypeVO
	 * @param obj objeto que encapsula la información de un MovementTypeVO
	 * @throws BusinessException en caso de error al tratar de ejecutar la creación de MovementType
	 * @author gfandino
	 */
	public void createMovementType(MovementTypeVO obj) throws BusinessException;
	
	/**
	 * Metodo: actualiza la información de un MovementTypeVO
	 * @param obj objeto que encapsula la información de un MovementTypeVO
	 * @throws BusinessException en caso de error al tratar de ejecutar la actualización de MovementType
	 * @author gfandino
	 */
	public void updateMovementType(MovementTypeVO obj) throws BusinessException;
	
	/**
	 * Metodo: Borra de la persistencia la información de un MovementTypeVO
	 * @param obj información del MovementTypeVO a ser borrado
	 * @throws BusinessException en caso de error al tratar de ejecutar la eliminación de MovementType
	 * @author gfandino
	 */
	public void deleteMovementType(MovementTypeVO obj) throws BusinessException;
	
	
	/**
	 * Metodo: Obtiene la información de un MovementTypeVO por su identificador
	 * @param id identificador del MovementTypeVO a ser consultado
	 * @return objeto con la información del MovementTypeVO dado su identificador, nulo en caso que no se encuentre
	 * @throws BusinessException en caso de error al tratar de ejecutar la consulta de MovementType por ID
	 * @author gfandino
	 */
	public MovementTypeVO getMovementTypeByID(Long id) throws BusinessException;
	
	/**
	 * Metodo: Obtiene la información de todos los MovementTypeVO almacenados en la persistencia
	 * @return Lista con los MovementTypeVO existentes, una lista vacia en caso que no existan MovementTypeVO en el sistema
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la operación
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la consulta de todos los MovementType
	 * @author gfandino
	 */
	public List<MovementTypeVO> getAllMovementTypes() throws BusinessException;
	
	/**
	 * Metodo: Metodo permite obtener todos los movementClass
	 * @return
	 * @throws BusinessException <tipo> <descripcion>
	 * @author cduarte
	 */
	public List<MovementTypeClassDTO> getAllMovementTypesClass() throws BusinessException;
	
	/**
	 * Metodo: Obtiene la información de un MovementTypeVO por su Código
	 * @param code - String código del MovementTypeVO a ser consultado
	 * @return MovementTypeVO con la información del MovementTypeVO dado su código, nulo en caso que no se encuentre
	 * @throws BusinessException en caso de error al tratar de ejecutar la consulta de MovementType por código
	 * @author gfandino
	 */
	public MovementTypeVO getMovementTypeByCode(String code) throws BusinessException;
	
	/**
	 * Metodo: Obtiene la información de todos los MovementTypeVO almacenados en la persistencia con estado activo
	 * @return Lista con los MovementTypeVO existentes, una lista vacia en caso que no existan MovementTypeVO en el sistema con estado activo
	 * @throws BusinessException Error cuando se realiza la consulta de los movimientos por estado inactivo
	 * @author gfandino
	 */
	public List<MovementTypeVO> getMovementTypesActiveStatus() throws BusinessException;
	
	/**
	 * Metodo: Obtiene la información de todos los MovementTypeVO almacenados en la persistencia con estado activo
	 * @param requestCollInfo - RequestCollectionInfo
	 * @return MovementTypeResponse con los datos de la consulta
	 * @throws BusinessException Error cuando se realiza la consulta de los movimientos por estado activo
	 * @author gfandino
	 */
	public MovementTypeResponse getMovementTypesActiveStatus(RequestCollectionInfo requestCollInfo) throws BusinessException;
	
	/**
	 * Metodo: Obtiene la información de todos los MovementTypeVO almacenados en la persistencia con estado inactivo
	 * @return Lista con los MovementTypeVO existentes, una lista vacia en caso que no existan MovementTypeVO en el sistema con estado inactivo
	 * @throws BusinessException Error cuando se realiza la consulta de los movimientos por estado inactivo
	 * @author gfandino
	 */
	public List<MovementTypeVO> getMovementTypesInActiveStatus() throws BusinessException;

	/**
	 * Método: Consulta los tipos de movimiento que se encuentren en cualquier estado
	 * @param code - String código de tipo de movimiento; nulo en caso que no aplique
	 * @param requestCollInfo - RequestCollectionInfo información de la paginación
	 * @return MovementTypeResponse movimientos con la paginación
	 * @throws BusinessException Error cuando se realiza la consulta de los movimientos por cualquier estado
	 */
	public MovementTypeResponse getMovementTypesAllStatusPage(
			String code, RequestCollectionInfo requestCollInfo)throws BusinessException;

	/**
	 * 
	 * Metodo: Obtiene todos los MovementType almacenados que esten activos y que correspondas a la clase de movimiento 
	 * enviada como parametro
	 * @param moveClass 
	 * @return lista con los MovementType existentes que concidan con el criterio de busqueda
	 * una lista vacia en caso que no exista ninguno.
	 * @author waguilera
	 */
	public List<MovementTypeVO> getMovementTypesAtiveByClass(String moveClass)throws BusinessException;

}
