package co.com.directv.sdii.persistence.dao.stock;

import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.model.pojo.WarehouseType;
import co.com.directv.sdii.model.pojo.collection.RequestCollectionInfo;
import co.com.directv.sdii.model.pojo.collection.WareheouseTypeResponse;

/**
 * 
 * Interface Local para la gestión del CRUD de la
 * Entidad WarehouseType
 * 
 * Fecha de Creación: Mar 8, 2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@Local
public interface WarehouseTypeDAOLocal {

	/**
	 * Metodo:  persiste la información de un WarehouseType
	 * @param obj objeto que encapsula la información de un WarehouseType
	 * @throws DAOServiceException en caso de error al ejecutar la creación de WarehouseType
	 * @throws DAOSQLException en caso de error al ejecutar la creación de WarehouseType
	 * @author gfandino
	 */
	public void createWarehouseType(WarehouseType obj) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: actualiza la información de un WarehouseType
	 * @param obj objeto que encapsula la información de un WarehouseType
	 * @throws DAOServiceException en caso de error al ejecutar la actualización de WarehouseType
	 * @throws DAOSQLException en caso de error al ejecutar la actualización de WarehouseType
	 * @author gfandino
	 */
	public void updateWarehouseType(WarehouseType obj) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: Borra de la persistencia la información de un WarehouseType
	 * @param obj información del WarehouseType a ser borrado
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la eliminación de WarehouseType
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la eliminación de WarehouseType
	 * @author gfandino
	 */
	public void deleteWarehouseType(WarehouseType obj) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: Obtiene la información de un WarehouseType por su identificador
	 * @param id identificador del WarehouseType a ser consultado
	 * @return objeto con la información del WarehouseType dado su identificador, nulo en caso que no se encuentre
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la operación
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la operación
	 * @author gfandino
	 */
	public WarehouseType getWarehouseTypeByID(Long id) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: Obtiene la información de todos los WarehouseType almacenados en la persistencia
	 * @return Lista con los WarehouseType existentes, una lista vacia en caso que no existan WarehouseType en el sistema
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la consulta de todos los WarehouseType
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la consulta de todos los WarehouseType
	 * @author gfandino
	 */
	public List<WarehouseType> getAllWarehouseTypes() throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: Obtiene la información de todos los WarehouseType almacenados en la persistencia dada una paginación
	 * @param code  - String código del tipo de bodega; nulo si  no aplica
	 * @param requestCollInfo - RequestCollectionInfo
	 * @return Lista con los WarehouseType existentes, una lista vacia en caso que no existan WarehouseType en el sistema
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la consulta de todos los WarehouseType
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la consulta de todos los WarehouseType
	 * @author gfandino
	 * 
	 */
	public WareheouseTypeResponse getAllWarehouseTypes(
			String code, RequestCollectionInfo requestCollInfo)throws DAOServiceException, DAOSQLException;

	/**
	 * Metodo: Obtiene la información de WarehouseType almacenados en la persistencia por su código
	 * @param code - String código de warehouseType
	 * @return WarehouseType correspondiente al código especificado. Nulo en caso contrario
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la consulta de WarehouseType por código
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la consulta de WarehouseType por código
	 * @author gfandino
	 */
	public WarehouseType getWarehouseTypeByCode(String code)throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: Obtiene la información de WarehouseType almacenados en la persistencia por su nombre
	 * @param name - String código de warehouseType
	 * @return WarehouseType correspondiente al nombre especificado. Nulo en caso contrario
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la consulta de WarehouseType por nombre
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la consulta de WarehouseType por nombre
	 * @author gfandino
	 */
	public WarehouseType getWarehouseTypeByName(String name)throws DAOServiceException, DAOSQLException;

	
	/**
	 * Método: Permite consultar los tipos de bodegta que se encuentran en el estado especificado
	 * @param status - String estado del tipo de bodega
	 * @return  List<WarehouseType> correspondiente a los tipos de bodega que se encuentran en el estado especificado
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la consulta de WarehouseType por estado
	 * @throws DAOSQLExceptionen caso de error al tratar de ejecutar la consulta de WarehouseType por estado
	 */
	public List<WarehouseType> getWarehouseTypeByStatus(String status)throws DAOServiceException, DAOSQLException;
	
	/**
	 * Método: Permite consultar los tipos de bodegta que se encuentran en el estado especificado
	 * @param status - String estado del tipo de bodega
	 * @param requestCollInfo - RequestCollectionInfo con la info de paginación
	 * @return WareHouseElementResponse con la lista resultado
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la consulta de WarehouseType por estado
	 * @throws DAOSQLExceptionen caso de error al tratar de ejecutar la consulta de WarehouseType por estado
	 */
	public WareheouseTypeResponse getWarehouseTypeByStatus(String status,RequestCollectionInfo requestCollInfo )throws DAOServiceException, DAOSQLException;

	/**
	 * Metodo: Obtiene la información de todos los WarehouseType activos
	 * @return Lista con los WarehouseType existentes, una lista vacia en caso que no existan WarehouseType en el sistema
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la consulta de todos los WarehouseType
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la consulta de todos los WarehouseType
	 * @author waguilera
	 */
	public List<WarehouseType> getAllWarehouseTypesActive() throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: Obtiene la información de todos los WarehouseType activos de un dealer especifico
	 * @return Lista con los WarehouseType existentes, una lista vacia en caso que no existan WarehouseType en el sistema
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la consulta de todos los WarehouseType
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la consulta de todos los WarehouseType
	 * @author waguilera
	 */
	public List<WarehouseType> getAllWarehouseTypesByDealerID(Long dealerId) throws DAOServiceException, DAOSQLException;

	/**
	 * Metodo: Obtiene la información de todos los WarehouseType activos y no virtuales
	 * @return Lista con los WarehouseType correspondientes, una lista vacia en caso que no existan WarehouseType en el sistema
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la consulta de todos los WarehouseType
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la consulta de todos los WarehouseType
	 * @author wjimenez
	 */
	public List<WarehouseType> getAllWarehouseTypesActiveAndNotVirtual()
			throws DAOServiceException, DAOSQLException;
	

}