package co.com.directv.sdii.persistence.dao.stock;

import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.model.pojo.WarehouseElementStock;
import co.com.directv.sdii.model.pojo.collection.RequestCollectionInfo;
import co.com.directv.sdii.model.pojo.collection.WarehouseElemetStockResponse;

/**
 * 
 * Interface Local para la gestión del CRUD de la
 * Entidad WarehouseElementStock
 * 
 * Fecha de Creación: Mar 8, 2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@Local
public interface WarehouseElementStockDAOLocal {

	/**
	 * Metodo:  persiste la información de un WarehouseElementStock
	 * @param obj objeto que encapsula la información de un WarehouseElementStock
	 * @throws DAOServiceException en caso de error al ejecutar la operación
	 * @throws DAOSQLException en caso de error al ejecutar la operación
	 * @author jjimenezh
	 */
	public void createWarehouseElementStock(WarehouseElementStock obj) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: actualiza la información de un WarehouseElementStock
	 * @param obj objeto que encapsula la información de un WarehouseElementStock
	 * @throws DAOServiceException en caso de error al ejecutar la operación
	 * @throws DAOSQLException en caso de error al ejecutar la operación
	 * @author jjimenezh
	 */
	public void updateWarehouseElementStock(WarehouseElementStock obj) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: Borra de la persistencia la información de un WarehouseElementStock
	 * @param obj información del WarehouseElementStock a ser borrado
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la operación
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la operación
	 * @author jjimenezh
	 */
	public void deleteWarehouseElementStock(WarehouseElementStock obj) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: Obtiene la información de un WarehouseElementStock por su identificador
	 * @param id identificador del WarehouseElementStock a ser consultado
	 * @return objeto con la información del WarehouseElementStock dado su identificador, nulo en caso que no se encuentre
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la operación
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la operación
	 * @author jjimenezh
	 */
	public WarehouseElementStock getWarehouseElementStockByID(Long id) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: Obtiene la información de todos los WarehouseElementStock almacenados en la persistencia
	 * @return Lista con los WarehouseElementStock existentes, una lista vacia en caso que no existan WarehouseElementStock en el sistema
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la operación
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la operación
	 * @author jjimenezh
	 */
	public List<WarehouseElementStock> getAllWarehouseElementStocks() throws DAOServiceException, DAOSQLException;

	/**
	 * Metodo: Obtiene la información de inventario de elementos en las diferentes bodegas dado el
	 * código del elemento
	 * @param code código del elemento
	 * @return lista de inventario de elelemntos en bodega dado el código de elemento, una lista vacia en caso que
	 * no se encuentre inventario en ninguna bodega
	 * @throws DAOServiceException en caso de error al ejecutaro la operación
	 * @throws DAOSQLException en caso de error al ejecutaro la operación
	 * @author jjimenezh
	 */
	public List<WarehouseElementStock> getWarehouseElementStockByElementCode(String code)throws DAOServiceException, DAOSQLException;

	/**
	 * Metodo: Obtiene la información de configuración de stock de un elemento dado el identificador del
	 * elemento, el id de la compañía dueña de la bodega y el id de la bodega
	 * @param elementId identificador del elemento
	 * @param dealerId identificador de la compañía dueña de la bodega
	 * @param warehouseId identificador de la bodega
	 * @return Lista con las configuraciones de stock de ese elemento en esa bodega de esa compañía, una lista vacia en caso que
	 * no exista registro del elemento
	 * @throws DAOServiceException en caso de error al ejecutaro la operación
	 * @throws DAOSQLException en caso de error al ejecutaro la operación
	 * @author jjimenezh
	 */
	public List<WarehouseElementStock> getWarehEleStockByEleTypeIdAndDealerIdAndWarehId(Long elementId, Long dealerId, Long warehouseId)throws DAOServiceException, DAOSQLException;

	/**
	 * Metodo: Obtiene una lista con los stock de un tipo de elemento 
	 * @param elementTypeCode código de tipo de elemento
	 * @param dealerCode código de la compañía
	 * @param warehouseCode código de la bodega
	 * @param RequestCollectionInfo requestCollInfo, parametros para paginar.
	 * @return WarehouseElemetStockResponse, Lista con los stock de los elementos, una lista vacia en caso que no se se encuentren stock
	 * de los tipos de elementos con los criterios especificados
	 * @throws DAOServiceException en caso de error al ejecutaro la operación
	 * @throws DAOSQLException en caso de error al ejecutaro la operación
	 * @author jjimenezh
	 */
	public WarehouseElemetStockResponse getWarehElemStockByElemTypeCodeOrDealerCodeOrWhCode(
			String elementTypeCode, Long dealerId, String warehouseCode, Long countryId, RequestCollectionInfo requestCollInfo, Long dealerBranchId)throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: Obtiene una lista de objetos WarehouseElementStock asociados a un WareHouse
	 * @param warehouseId el identificador de la bodega
	 * @return lista de objetos WarehouseElementStock asociados a un WareHouse
	 * @throws DAOServiceException en caso de error al ejecutar la operacion
	 * @throws DAOSQLException en caso de error al ejecutar la operacion
	 * @author garciniegas
	 */
	public List<WarehouseElementStock> getWarehouseElementStockByWareHouseId( Long warehouseId )throws DAOServiceException, DAOSQLException;
	
}