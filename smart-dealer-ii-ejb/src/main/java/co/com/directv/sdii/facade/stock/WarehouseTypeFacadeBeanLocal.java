package co.com.directv.sdii.facade.stock;

import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.pojo.collection.RequestCollectionInfo;
import co.com.directv.sdii.model.pojo.collection.WareheouseTypeResponse;
import co.com.directv.sdii.model.vo.WarehouseTypeVO;

/**
 * 
 * Interfaz de la fachada con las operaciones Tipo CRUD de la Entidad WarehouseType.
 * 
 * Fecha de Creación: jul 12, 2010
 * @author gfandino <a href="mailto:gfandino@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@Local
public interface WarehouseTypeFacadeBeanLocal {

	/**
	 * Metodo:  Permite crear en el sistema un objeto WarehouseType
	 * @param obj - WarehouseTypeVO  objeto que encapsula la información de un WarehouseTypeVO
	 * @throws BusinessException en caso de error al ejecutar la creación de WarehouseType
	 * @author gfandino
	 */
	public void createWarehouseType(WarehouseTypeVO obj) throws BusinessException;
	
	/**
	 * Metodo: Permite actualizar en el sistema la información de un WarehouseType
	 * @param obj - WarehouseTypeVO  objeto que encapsula la información de un WarehouseTypeVO
	 * @throws BusinessException en caso de error al ejecutar la actualización de WarehouseType
	 * @author gfandino
	 */
	public void updateWarehouseType(WarehouseTypeVO obj) throws BusinessException;
	
	/**
	 * Metodo: Permite borra la información de un WarehouseType
	 * @param obj - WarehouseTypeVO  información del WarehouseTypeVO a ser borrado
	 * @throws BusinessException en caso de error al ejecutar la eliminación de WarehouseType
	 * @author gfandino
	 */
	public void deleteWarehouseType(WarehouseTypeVO obj) throws BusinessException;
	
	
	/**
	 * Metodo: Permite consultar la información de un WarehouseType por su identificador
	 * @param id - Long identificador del WarehouseType a ser consultado
	 * @return objeto con la información del WarehouseTypeVO dado su identificador, nulo en caso que no se encuentre
	 * @throws BusinessException en caso de error al ejecutar la consulta de WarehouseType por ID
	 * @author gfandino
	 */
	public WarehouseTypeVO getWarehouseTypeByID(Long id) throws BusinessException;
	
	/**
	 * Metodo: Permite consultar la información de todos los WarehouseType almacenados en la persistencia
	 * @return List<WarehouseTypeVO> Lista con los WarehouseTypeVO existentes, una lista vacia en caso que no existan WarehouseTypeVO en el sistema
	 * @throws BusinessException en caso de error al ejecutar la consulta de todos los WarehouseType
	 * @author gfandino
	 */
	public List<WarehouseTypeVO> getAllWarehouseTypes() throws BusinessException;
	
	/**
	 * Metodo: Permite consultar la información de todos los WarehouseType almacenados en la persistencia
	 * @param requestCollInfo - RequestCollectionInfo con información de la paginación
	 * @param code - String código del tipo de bodega; nulo si no aplica
	 * @return WareheouseTypeResponse Lista con los WarehouseTypeVO existentes, una lista vacia en caso que no existan WarehouseTypeVO en el sistema
	 * @throws BusinessException en caso de error al ejecutar la consulta de todos los WarehouseType
	 * @author gfandino
	 * 
	 */
	public WareheouseTypeResponse getAllWarehouseTypes(String code, RequestCollectionInfo requestCollInfo) throws BusinessException;
	
	/**
	 * Metodo: Obtiene la información de un WarehouseTypeVO por su código
	 * @param code - String Código del WarehouseTypeVO a ser consultado
	 * @return WarehouseTypeVO con la información del WarehouseTypeVO dado su código, nulo en caso que no se encuentre
	 * @throws BusinessException en caso de error al tratar de ejecutar la consulta de WarehouseType por Código
	 * @author gfandino
	 */
	public WarehouseTypeVO getWarehouseTypeByCode(String code) throws BusinessException;
	
	/**
	 * Método: Obtiene los tipos de bodega que se encuentran en estado activo
	 * @return List<WarehouseTypeVO> que se encuentran en estado activo
	 * @throws BusinessException en caso de error al tratar de ejecutar la consulta de WarehouseType activas
	 * @author gfandino
	 */
	public  List<WarehouseTypeVO> getWarehouseTypeActive()throws BusinessException;
	
	/**
	 * Método: Obtiene los tipos de bodega que se encuentran en estado activo
	 * @param requestCollInfo - Info para paginación
	 * @return WareheouseTypeResponse con la información de las listas consultadas
	 * @throws BusinessException en caso de error al tratar de ejecutar la consulta de WarehouseType activas
	 * @author gfandino
	 */
	public WareheouseTypeResponse getWarehouseTypeActive(RequestCollectionInfo requestCollInfo)throws BusinessException;
	
	
	
	
	/**
	 * Método: Obtiene los tipos de bodega que se encuentran en estado no activo
	 * @return List<WarehouseTypeVO> que se encuentran en estado no activo
	 * @throws BusinessException en caso de error al tratar de ejecutar la consulta de WarehouseType no activas
	 * @author gfandino
	 */
	public  List<WarehouseTypeVO> getWarehouseTypeNoActive()throws BusinessException;

	/**
	 * Metodo: Permite consultar la información de todos los WarehouseType almacenados en la persistencia
	 * que estan activos
	 * @return List<WarehouseTypeVO> Lista con los WarehouseTypeVO existentes, una lista vacia en caso que no existan WarehouseTypeVO en el sistema
	 * @throws BusinessException en caso de error al ejecutar la consulta de todos los WarehouseType
	 * @author waguilera
	 */
	public List<WarehouseTypeVO> getAllWarehouseTypesActive() throws BusinessException;
	
	/**
	 * Metodo: Obtiene la información de todos los WarehouseType activos de un dealer especifico
	 * que estan activos
	 * @return List<WarehouseTypeVO> Lista con los WarehouseTypeVO existentes, una lista vacia en caso que no existan WarehouseTypeVO en el sistema
	 * @throws BusinessException en caso de error al ejecutar la consulta de todos los WarehouseType
	 * @author waguilera
	 */
	public List<WarehouseTypeVO> getAllWarehouseTypesByDealerID(Long dealerId) throws BusinessException;
	
	/**
	 * Metodo: Obtiene la información de todos los WarehouseType activos y no virtuales
	 * @return Lista con los WarehouseType correspondientes, una lista vacia en caso que no existan WarehouseType en el sistema
	 * @throws BusinessException en caso de error al tratar de ejecutar la consulta de todos los WarehouseType
	 * @author wjimenez
	 */
	public List<WarehouseTypeVO> getAllWarehouseTypesActiveAndNotVirtual() throws BusinessException;

}
