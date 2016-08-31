package co.com.directv.sdii.ejb.business.stock;

import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.model.dto.collection.RequestCollectionInfoDTO;
import co.com.directv.sdii.model.dto.collection.WarehouseElementStockDTO;
import co.com.directv.sdii.model.pojo.WarehouseElementStock;
import co.com.directv.sdii.model.vo.WarehouseElementStockVO;
import co.com.directv.sdii.ws.model.dto.ResponseWareHouseIndicatorAlarmDTO;

/**
 * 
 * Interfaz de las operaciones Tipo CRUD de la Entidad WarehouseElementStock.
 * 
 * Fecha de Creación: Mar 8, 2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@Local
public interface WarehouseElementStockBusinessBeanLocal {

	/**
	 * Metodo:  Persiste la información de un objeto WarehouseElementStockVO
	 * @param obj objeto que encapsula la información de un WarehouseElementStockVO
	 * @throws DAOServiceException en caso de error al ejecutar la operación
	 * @throws DAOSQLException en caso de error al ejecutar la operación
	 * @author
	 */
	public void createWarehouseElementStock(WarehouseElementStockVO obj) throws BusinessException;
	
	/**
	 * Metodo: actualiza la información de un WarehouseElementStockVO
	 * @param obj objeto que encapsula la información de un WarehouseElementStockVO
	 * @throws DAOServiceException en caso de error al ejecutar la operación
	 * @throws DAOSQLException en caso de error al ejecutar la operación
	 * @author
	 */
	public void updateWarehouseElementStock(WarehouseElementStockVO obj) throws BusinessException;
	
	/**
	 * Metodo: Borra de la persistencia la información de un WarehouseElementStockVO
	 * @param obj información del WarehouseElementStockVO a ser borrado
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la operación
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la operación
	 * @author
	 */
	public void deleteWarehouseElementStock(WarehouseElementStockVO obj) throws BusinessException;
	
	
	/**
	 * Metodo: Obtiene la información de un WarehouseElementStockVO por su identificador
	 * @param id identificador del WarehouseElementStockVO a ser consultado
	 * @return objeto con la información del WarehouseElementStockVO dado su identificador, nulo en caso que no se encuentre
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la operación
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la operación
	 * @author
	 */
	public WarehouseElementStockVO getWarehouseElementStockByID(Long id) throws BusinessException;
	
	/**
	 * Metodo: Obtiene la información de todos los WarehouseElementStockVO almacenados en la persistencia
	 * @return Lista con los WarehouseElementStockVO existentes, una lista vacia en caso que no existan WarehouseElementStockVO en el sistema
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la operación
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la operación
	 * @author
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
	
	/**
	 * Metodo: Permite detectar cuando hay insuficiencia por stock para poder lanzar una alarma por 
	 * stock insuficiente de elementos
	 * @param warehouse la bodega a verificar
	 * @param user el usuario involucrado
	 * @return un boolean indicando si la alarma de verificacionde stock insuficiente se disparo
	 * @throws BusinessException En caso de error al tratar de ejecutar la operacion
	 * @author garciniegas
	 */
	public boolean checkShortcomingInWarehouse( Long warehouse,Long user )throws BusinessException;
	
	/**
	 * Metodo: Permite detectar cuando hay insuficiencia por stock para poder lanzar una alarma por 
	 * stock insuficiente de elementos
	 * @param dealerId las bodegas del dealer a verificar 
	 * @param warehouseType el tipo de bodega a verificar
	 * @param user el usuario involucrado
	 * @return un boolean indicando si la alarma de verificacionde stock insuficiente se disparo
	 * @throws BusinessException En caso de error al tratar de ejecutar la operacion
	 * @author cduarte
	 */
	public List<ResponseWareHouseIndicatorAlarmDTO> checkWarehouseIndicatorAlarmByDealerIdWarehouseTypeAndUser( Long dealerId,String warehouseType,Long user )throws BusinessException;
	
	/**
	 * Metodo: Obtiene una lista de objetos WarehouseElementStock asociados a un WareHouse
	 * @param warehouseId el identificador de la bodega
	 * @return lista de objetos WarehouseElementStock asociados a un WareHouse
	 * @throws BusinessException En caso de error al tratar de ejecutar la operacion
	 * @author garciniegas
	 */
	public List<WarehouseElementStock> getWarehouseElementStockByWareHouseId( Long warehouseId )throws BusinessException;

}
