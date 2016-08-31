package co.com.directv.sdii.facade.stock;

import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.dto.MovementTypeClassDTO;
import co.com.directv.sdii.model.pojo.collection.MovementTypeResponse;
import co.com.directv.sdii.model.pojo.collection.RequestCollectionInfo;
import co.com.directv.sdii.model.vo.MovementTypeVO;

/**
 * 
 * Interfaz de la fachada con las operaciones Tipo CRUD de la Entidad MovementType.
 * 
 * Fecha de Creación: jul 12, 2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@Local
public interface MovementTypeFacadeBeanLocal {

	/**
	 * Metodo:  Permite crear en el sistema un objeto MovementType
	 * @param obj - MovementTypeVO  objeto que encapsula la información de un MovementTypeVO
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author jjimenezh
	 */
	public void createMovementType(MovementTypeVO obj) throws BusinessException;
	
	/**
	 * Metodo: Permite actualizar en el sistema la información de un MovementType
	 * @param obj - MovementTypeVO  objeto que encapsula la información de un MovementTypeVO
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author jjimenezh
	 */
	public void updateMovementType(MovementTypeVO obj) throws BusinessException;
	
	/**
	 * Metodo: Permite borra la información de un MovementType
	 * @param obj - MovementTypeVO  información del MovementTypeVO a ser borrado
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author jjimenezh
	 */
	public void deleteMovementType(MovementTypeVO obj) throws BusinessException;
	
	
	/**
	 * Metodo: Permite consultar la información de un MovementType por su identificador
	 * @param id - Long identificador del MovementType a ser consultado
	 * @return objeto con la información del MovementTypeVO dado su identificador, nulo en caso que no se encuentre
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author jjimenezh
	 */
	public MovementTypeVO getMovementTypeByID(Long id) throws BusinessException;
	
	/**
	 * Metodo: Permite consultar la información de todos los MovementType almacenados en la persistencia
	 * @return List<MovementTypeVO> Lista con los MovementTypeVO existentes, una lista vacia en caso que no existan MovementTypeVO en el sistema
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author jjimenezh
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
	 * Metodo: obtiene el tipo de modificación dado el código
	 * @param code código del tipo de modificación
	 * @return tipo de modificación con el código especificado
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author jjimenezh
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
	 * @param code - String código del tipo de movimiento; nulo si no aplica
	 * @param requestCollInfo - RequestCollectionInfo información de la paginación
	 * @return MovementTypeResponse movimientos con la paginación
	 * @throws BusinessException Error cuando se realiza la consulta de los movimientos por cualquier estado
	 */
	public MovementTypeResponse getMovementTypesAllStatusPage(
			String code, RequestCollectionInfo requestCollInfo)throws BusinessException;

	/**
	 * Metodo: Obtiene todos los MovementType almacenados que esten activos y que correspondas a la clase de movimiento 
	 * enviada como parametro.
	 * Trae unicamente los tipos de movimento 
	 * que no son automaticos
	 * @param moveClass 
	 * @return lista con los MovementType existentes que concidan con el criterio de busqueda
	 * una lista vacia en caso que no exista ninguno.
	 * @throws BusinessException Error cuando se realiza la consulta de los movimientos por cualquier estado
	 * @author waguilera
	 */
	public List<MovementTypeVO> getMovementTypesAtiveByClass(String moveClass)throws BusinessException;

}
