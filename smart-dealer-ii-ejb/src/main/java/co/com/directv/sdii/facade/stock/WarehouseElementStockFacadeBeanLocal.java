package co.com.directv.sdii.facade.stock;

import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.dto.collection.RequestCollectionInfoDTO;
import co.com.directv.sdii.model.dto.collection.WarehouseElementStockDTO;
import co.com.directv.sdii.model.vo.WarehouseElementStockVO;

/**
 * 
 * Interfaz de la fachada con las operaciones Tipo CRUD de la Entidad WarehouseElementStock.
 * 
 * Fecha de Creación: jul 12, 2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@Local
public interface WarehouseElementStockFacadeBeanLocal {

	/**
	 * Metodo:  Permite crear en el sistema un objeto WarehouseElementStock
	 * @param obj - WarehouseElementStockVO  objeto que encapsula la información de un WarehouseElementStockVO
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author jjimenezh
	 */
	public void createWarehouseElementStock(WarehouseElementStockVO obj) throws BusinessException;
	
	/**
	 * Metodo: Permite actualizar en el sistema la información de un WarehouseElementStock
	 * @param obj - WarehouseElementStockVO  objeto que encapsula la información de un WarehouseElementStockVO
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author jjimenezh
	 */
	public void updateWarehouseElementStock(WarehouseElementStockVO obj) throws BusinessException;
	
	/**
	 * Metodo: Permite borra la información de un WarehouseElementStock
	 * @param obj - WarehouseElementStockVO  información del WarehouseElementStockVO a ser borrado
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author jjimenezh
	 */
	public void deleteWarehouseElementStock(WarehouseElementStockVO obj) throws BusinessException;
	
	
	/**
	 * Metodo: Permite consultar la información de un WarehouseElementStock por su identificador
	 * @param id - Long identificador del WarehouseElementStock a ser consultado
	 * @return objeto con la información del WarehouseElementStockVO dado su identificador, nulo en caso que no se encuentre
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author jjimenezh
	 */
	public WarehouseElementStockVO getWarehouseElementStockByID(Long id) throws BusinessException;
	
	/**
	 * Metodo: Permite consultar la información de todos los WarehouseElementStock almacenados en la persistencia
	 * @return List<WarehouseElementStockVO> Lista con los WarehouseElementStockVO existentes, una lista vacia en caso que no existan WarehouseElementStockVO en el sistema
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author jjimenezh
	 */
	public List<WarehouseElementStockVO> getAllWarehouseElementStocks() throws BusinessException;

	/**
	 * Metodo: Obtiene un stock de elemento en bodega por el código de elemento
	 * @param code código del elemento de bodega por el cual se consultará el stock
	 * @return Lista de stock del elemento en las diferentes bodegas, una lista vacia
	 * en caso que no se encuentre stock del elemento en las diferentes bodegas
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author jjimenezh
	 */
	public List<WarehouseElementStockVO> getWarehouseElementStockByElementCode(String code) throws BusinessException;

	/**
	 * Metodo: Obtiene una lista con los stock de un tipo de elemento 
	 * @param elementTypeCode código de tipo de elemento
	 * @param dealerCode código de la compañía
	 * @param warehouseCode código de la bodega
	 * @param RequestCollectionInfoDTO requestCollInfo, parametros para la paginacion
	 * @return WarehouseElementStockDTO, Lista con los stock de los elementos, una lista vacia en caso que no se se encuentren stock
	 * de los tipos de elementos con los criterios especificados
	 * @throws BusinessException En caso de error al tratar de ejecutar la operación
	 * @author jjimenezh
	 */
	public WarehouseElementStockDTO getWarehElemStockByElemTypeCodeOrDealerCodeOrWhCode(
			String elementTypeCode, Long dealerId, String warehouseCode, Long countryId, RequestCollectionInfoDTO requestCollInfo, Long dealerBranchId)throws BusinessException;

}
