package co.com.directv.sdii.ws.business.stock;

import java.util.Date;
import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.model.dto.MovementElementDTO;
import co.com.directv.sdii.model.dto.MovementTypeClassDTO;
import co.com.directv.sdii.model.dto.QuantityWarehouseElementsDTO;
import co.com.directv.sdii.model.dto.SendEmailDTO;
import co.com.directv.sdii.model.dto.WareHouseElementClientFilterRequestDTO;
import co.com.directv.sdii.model.dto.WareHouseRequestDTO;
import co.com.directv.sdii.model.dto.WarehouseInfoResponseDTO;
import co.com.directv.sdii.model.dto.WarehouseInfoResponseDetailDTO;
import co.com.directv.sdii.model.dto.collection.CustomerElementsResponse;
import co.com.directv.sdii.model.dto.collection.QuantityWarehouseElementResponse;
import co.com.directv.sdii.model.dto.collection.RequestCollectionInfoDTO;
import co.com.directv.sdii.model.dto.collection.WarehouseElementStockDTO;
import co.com.directv.sdii.model.pojo.Dealer;
import co.com.directv.sdii.model.pojo.WarehouseElement;
import co.com.directv.sdii.model.pojo.WhElementSearchFilter;
import co.com.directv.sdii.model.pojo.collection.MovedElementSerializedResponse;
import co.com.directv.sdii.model.pojo.collection.MovementTypeResponse;
import co.com.directv.sdii.model.pojo.collection.RequestCollectionInfo;
import co.com.directv.sdii.model.pojo.collection.SerializedWhElementsByCriteriaPaginationResponse;
import co.com.directv.sdii.model.pojo.collection.WareHouseElementCustomerResponse;
import co.com.directv.sdii.model.pojo.collection.WareHouseElementHistoricalResponse;
import co.com.directv.sdii.model.pojo.collection.WareHouseElementResponse;
import co.com.directv.sdii.model.pojo.collection.WareHouseResponse;
import co.com.directv.sdii.model.pojo.collection.WareheouseTypeResponse;
import co.com.directv.sdii.model.vo.CrewVO;
import co.com.directv.sdii.model.vo.DealerVO;
import co.com.directv.sdii.model.vo.DocumentClassVO;
import co.com.directv.sdii.model.vo.ElementVO;
import co.com.directv.sdii.model.vo.MovementTypeVO;
import co.com.directv.sdii.model.vo.NotSerPartialRetirementVO;
import co.com.directv.sdii.model.vo.NotSerializedVO;
import co.com.directv.sdii.model.vo.RecordStatusVO;
import co.com.directv.sdii.model.vo.SerializedVO;
import co.com.directv.sdii.model.vo.UserVO;
import co.com.directv.sdii.model.vo.WHElementQtySummaryVO;
import co.com.directv.sdii.model.vo.WarehouseElementQuantityVO;
import co.com.directv.sdii.model.vo.WarehouseElementStockVO;
import co.com.directv.sdii.model.vo.WarehouseElementVO;
import co.com.directv.sdii.model.vo.WarehouseTypeVO;
import co.com.directv.sdii.model.vo.WarehouseVO;
import co.com.directv.sdii.model.vo.WhElementSearchFilterVO;
import co.com.directv.sdii.reports.dto.FilterSerializedElementDTO;
import co.com.directv.sdii.ws.model.dto.ResponseWareHouseIndicatorAlarmDTO;

/**
 * Servicio web que expone las operaciones relacionadas con Warehouse
 * 
 * Fecha de Creación: 28/06/2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @author jalopez <a href="mailto:jalopez@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 */
@WebService(name="WarehouseWS",targetNamespace="http://stock.business.ws.sdii.directv.com.co/")
public interface IWarehouseWS {
	/**
	 * UC_INV_104
	 * 
	 * Metodo: persiste la información de un Warehouse
	 * @param obj objeto que encapsula la información necesaria para construir el Warehouse,
	 * no debe venir asignada la propiedad id, de lo contrario se generará un error
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * a continuación los códigos de error:<br>
     * <code>sdii_CODE_constraint_violation</code> En caso que se viole una restricción definida en la persistencia<br>
     * <code>sdii_CODE_error_data</code> En caso que se viole una regla de datos de la persistencia<br>
     * <code>sdii_CODE_generic_jdbc</code> En caso de error genérico relacionado con el componente de conexión a la base de datos<br>
     * <code>sdii_CODE_jdbc_connection</code> En caso de error al tratar de conectarse con la base de datos<br>
     * <code>sdii_CODE_sql_grammar</code> En caso de error relacionado con la gramática para ejecutar el comando en la base de datos<br>
     * <code>sdii_CODE_error_service</code> En caso de error al tratar de obtener el valor de una propiedad o un error inesperado en la capa de acceso a datos<br>
     * <code>sdii_CODE_unknow_error</code> En caso de un error inesperado en la capa de negocio<br>
	 * <code>sdii_CODE_params_null_or_missed</code> En caso que no se encuentren los parámetros necesarios para la ejecución de la operación<br>
	 * @author jjimenezh
	 */
	@WebMethod(operationName="createWarehouse", action="createWarehouse")
	public void createWarehouse(@WebParam(name="objWarehouse")WarehouseVO objWarehouse) throws BusinessException;
	
	/**
	 * UC_INV_104
	 * 
	 * Metodo: actualiza la información de un Warehouse
	 * @param obj objeto que encapsula la información del Warehouse a actualizar,
	 * debe venir especificada la propiedad id, en caso que no venga, se generará un nuevo Warehouse
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * a continuación los códigos de error:<br>
     * <code>sdii_CODE_constraint_violation</code> En caso que se viole una restricción definida en la persistencia<br>
     * <code>sdii_CODE_error_data</code> En caso que se viole una regla de datos de la persistencia<br>
     * <code>sdii_CODE_generic_jdbc</code> En caso de error genérico relacionado con el componente de conexión a la base de datos<br>
     * <code>sdii_CODE_jdbc_connection</code> En caso de error al tratar de conectarse con la base de datos<br>
     * <code>sdii_CODE_sql_grammar</code> En caso de error relacionado con la gramática para ejecutar el comando en la base de datos<br>
     * <code>sdii_CODE_error_service</code> En caso de error al tratar de obtener el valor de una propiedad o un error inesperado en la capa de acceso a datos<br>
     * <code>sdii_CODE_unknow_error</code> En caso de un error inesperado en la capa de negocio<br>
	 * <code>sdii_CODE_params_null_or_missed</code> En caso que no se encuentren los parámetros necesarios para la ejecución de la operación<br>
	 * @author jjimenezh
	 */
	@WebMethod(operationName="updateWarehouse", action="updateWarehouse")
	public void updateWarehouse(@WebParam(name="objWarehouse")WarehouseVO objWarehouse) throws BusinessException;
	
	
	
	/**
	 * Metodo: Obtiene un Warehouse dado el identificador del mismo
	 * @param id identificador del Warehouse a ser consultado
	 * @return Warehouse con el id especificado, se lanza una exepción en caso que no se
	 * encuentre el Warehouse con el id, ver códigos de excepción.
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * a continuación los códigos de error:<br>
     * <code>sdii_CODE_constraint_violation</code> En caso que se viole una restricción definida en la persistencia<br>
     * <code>sdii_CODE_error_data</code> En caso que se viole una regla de datos de la persistencia<br>
     * <code>sdii_CODE_generic_jdbc</code> En caso de error genérico relacionado con el componente de conexión a la base de datos<br>
     * <code>sdii_CODE_jdbc_connection</code> En caso de error al tratar de conectarse con la base de datos<br>
     * <code>sdii_CODE_sql_grammar</code> En caso de error relacionado con la gramática para ejecutar el comando en la base de datos<br>
     * <code>sdii_CODE_error_service</code> En caso de error al tratar de obtener el valor de una propiedad o un error inesperado en la capa de acceso a datos<br>
     * <code>sdii_CODE_unknow_error</code> En caso de un error inesperado en la capa de negocio<br>
	 * <code>sdii_CODE_params_null_or_missed</code> En caso que no se encuentren los parámetros necesarios para la ejecución de la operación<br>
	 * <code>sdii_CODE_entity_not_found</code> En caso que no encuentre el tipo de dealer por el id especificado<br>
	 * @author jjimenezh
	 */
	@WebMethod(operationName="getWarehouseByID", action="getWarehouseByID")
	public WarehouseVO getWarehouseByID(@WebParam(name="id")Long id) throws BusinessException;
	
	/**
	 * Metodo: Obtiene todos los Warehouse almacenados en la persistencia
	 * @return lista con los Warehouse existentes, una lista vacia en caso que no exista ninguno.
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * a continuación los códigos de error:<br>
     * <code>sdii_CODE_constraint_violation</code> En caso que se viole una restricción definida en la persistencia<br>
     * <code>sdii_CODE_error_data</code> En caso que se viole una regla de datos de la persistencia<br>
     * <code>sdii_CODE_generic_jdbc</code> En caso de error genérico relacionado con el componente de conexión a la base de datos<br>
     * <code>sdii_CODE_jdbc_connection</code> En caso de error al tratar de conectarse con la base de datos<br>
     * <code>sdii_CODE_sql_grammar</code> En caso de error relacionado con la gramática para ejecutar el comando en la base de datos<br>
     * <code>sdii_CODE_error_service</code> En caso de error al tratar de obtener el valor de una propiedad o un error inesperado en la capa de acceso a datos<br>
     * <code>sdii_CODE_unknow_error</code> En caso de un error inesperado en la capa de negocio<br>
	 * <code>sdii_CODE_params_null_or_missed</code> En caso que no se encuentren los parámetros necesarios para la ejecución de la operación<br>
	 * @author jjimenezh
	 */
	@WebMethod(operationName="getAllWarehouses", action="getAllWarehouses", exclude=true)
	public List<WarehouseVO> getAllWarehouses() throws BusinessException;
	
	/**
	 * Método: Consulta todas las bodegas en cualquier estado
	 * @param countryId - identificacor del país
	 * @param code String código de la ubicación; nulo si no aplica
	 * @param requestCollInfo - WareHouseResponse datos de la paginación 
	 * @return WarehouseInfoResponseDetailDTO con las bodegas paginadas
	 * @throws BusinessException en caso de error al tratar de ejecutar la consulta de todas las Warehouse
	 */ 
	@WebMethod(operationName="getAllWarehousesByCountryId", action="getAllWarehousesByCountryId")
	public WarehouseInfoResponseDetailDTO  getAllWarehousesByCountryId(@WebParam(name="countryId")Long countryId, @WebParam(name="code")String code,@WebParam(name="requestCollInfo")RequestCollectionInfo requestCollInfo) throws BusinessException;
	
	/**
	 * Método: Consulta las bodegas segun los parametros
	 * @param countryId - identificacor del país
	 * @param bodegaTipoId String identificador del tipo de bodega; nulo si no aplica
	 * @param companiaId String identificador de la compania; nulo si no aplica
	 * @param sucursalId String identificador de la surcursal; nulo si no aplica
	 * @param cuadrillaId String identificador de la cuadrilla; nulo si no aplica
	 * @param requestCollInfo - WareHouseResponse datos de la paginación 
	 * @return WarehouseInfoResponseDetailDTO con las bodegas paginadas
	 * @throws BusinessException en caso de error al tratar de ejecutar la consulta 
	 * 
 	 * ialessan
	 * junio 2014
	 * Modificacion de servicos de pantalla ubicaciones, cuatro nuevos parametros.
	 * 
	 */ 
	@WebMethod(operationName="getWhByWhTypeDealerBranchCrewIds", action="getWhByWhTypeDealerBranchCrewIds")
	public WarehouseInfoResponseDetailDTO  getWhByWhTypeDealerBranchCrewIds( @WebParam(name="countryId")Long countryId, 
																				 @WebParam(name="bodegaTipoId")String warehouseTypeId,
																				 @WebParam(name="companiaId")String dealerId,
																				 @WebParam(name="sucursalId")String branchId,
																				 @WebParam(name="cuadrillaId")String crewId,																					@WebParam(name="requestCollInfo")
																				 RequestCollectionInfo requestCollInfo
	) throws BusinessException;
	
	/**
	 * Metodo: Obtiene todos los Warehouse almacenados en la persistencia dado el identificador del país
	 * @param RequestCollectionInfoDTO requestCollInfo, parametros para paginar.
	 * @return WareHouseResponse, lista con los Warehouse existentes, una lista vacia en caso que no exista ninguno.
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * a continuación los códigos de error:<br>
     * <code>sdii_CODE_constraint_violation</code> En caso que se viole una restricción definida en la persistencia<br>
     * <code>sdii_CODE_error_data</code> En caso que se viole una regla de datos de la persistencia<br>
     * <code>sdii_CODE_generic_jdbc</code> En caso de error genérico relacionado con el componente de conexión a la base de datos<br>
     * <code>sdii_CODE_jdbc_connection</code> En caso de error al tratar de conectarse con la base de datos<br>
     * <code>sdii_CODE_sql_grammar</code> En caso de error relacionado con la gramática para ejecutar el comando en la base de datos<br>
     * <code>sdii_CODE_error_service</code> En caso de error al tratar de obtener el valor de una propiedad o un error inesperado en la capa de acceso a datos<br>
     * <code>sdii_CODE_unknow_error</code> En caso de un error inesperado en la capa de negocio<br>
	 * <code>sdii_CODE_params_null_or_missed</code> En caso que no se encuentren los parámetros necesarios para la ejecución de la operación<br>
	 * @author jjimenezh
	 */
	@WebMethod(operationName="getWarehousesByCountryId", action="getWarehousesByCountryId")
	public WareHouseResponse getWarehousesByCountryId(@WebParam(name="countryId")Long countryId, @WebParam(name="requestCollInfo")RequestCollectionInfoDTO requestCollInfo) throws BusinessException;
	

	/**
	 * Metodo: Obtiene todos los Warehouse almacenados en la persistencia dado el identificador del país y la condición del dealer: vendedor|instalador
	 * @param RequestCollectionInfoDTO requestCollInfo, parametros para paginar.
	 * @param isSeller identifica si se trata de un dealer vendedor
	 * @param isInstaller identifica si se trata de un dealer instalador
	 * @return WarehouseInfoResponseDTO, lista con las ubicaciones existentes, una lista vacia en caso que no exista ninguno.
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * a continuación los códigos de error:<br>
     * <code>sdii_CODE_constraint_violation</code> En caso que se viole una restricción definida en la persistencia<br>
     * <code>sdii_CODE_error_data</code> En caso que se viole una regla de datos de la persistencia<br>
     * <code>sdii_CODE_generic_jdbc</code> En caso de error genérico relacionado con el componente de conexión a la base de datos<br>
     * <code>sdii_CODE_jdbc_connection</code> En caso de error al tratar de conectarse con la base de datos<br>
     * <code>sdii_CODE_sql_grammar</code> En caso de error relacionado con la gramática para ejecutar el comando en la base de datos<br>
     * <code>sdii_CODE_error_service</code> En caso de error al tratar de obtener el valor de una propiedad o un error inesperado en la capa de acceso a datos<br>
     * <code>sdii_CODE_unknow_error</code> En caso de un error inesperado en la capa de negocio<br>
	 * <code>sdii_CODE_params_null_or_missed</code> En caso que no se encuentren los parámetros necesarios para la ejecución de la operación<br>
	 * @author carlos lopez
	 */
	@WebMethod(operationName="getWarehousesByCountryIdAndDealerCondition", action="getWarehousesByCountryIdAndDealerCondition")
	public WarehouseInfoResponseDTO getWarehousesByCountryIdAndDealerCondition(@WebParam(name="countryId")Long countryId, @WebParam(name="requestCollInfo")RequestCollectionInfoDTO requestCollInfo,
				@WebParam(name="isSeller")String isSeller,@WebParam(name="isInstaller")String isInstaller) throws BusinessException;
	
	
	
	/**
	 * Metodo: Obtiene la información de un conjunto WarehouseVO por el identificador del dealer
	 * @param dealerId - Long identificador del dealer sobre el que se realizará la consulta
	 * @return List<WarehouseVO> Lista de las bodegas asociadas al dealer, una lista vacia en caso que no se encuentren bodegas
	 * asociadas al dealer
	 * @throws BusinessException en caso de error al tratar de ejecutar la consulta de Warehouse por código
	 * a continuación los códigos de error:<br>
     * <code>sdii_CODE_constraint_violation</code> En caso que se viole una restricción definida en la persistencia<br>
     * <code>sdii_CODE_error_data</code> En caso que se viole una regla de datos de la persistencia<br>
     * <code>sdii_CODE_generic_jdbc</code> En caso de error genérico relacionado con el componente de conexión a la base de datos<br>
     * <code>sdii_CODE_jdbc_connection</code> En caso de error al tratar de conectarse con la base de datos<br>
     * <code>sdii_CODE_sql_grammar</code> En caso de error relacionado con la gramática para ejecutar el comando en la base de datos<br>
     * <code>sdii_CODE_error_service</code> En caso de error al tratar de obtener el valor de una propiedad o un error inesperado en la capa de acceso a datos<br>
     * <code>sdii_CODE_unknow_error</code> En caso de un error inesperado en la capa de negocio<br>
	 * <code>sdii_CODE_params_null_or_missed</code> En caso que no se encuentren los parámetros necesarios para la ejecución de la operación<br>
	 * @author jjimenezh
	 */
	@WebMethod(operationName="getWarehousesByDealerId", action="getWarehousesByDealerId")
	public List<WarehouseVO> getWarehousesByDealerId(@WebParam(name="dealerId") Long dealerId)throws BusinessException;
	
	/**
	 * Metodo: Obtiene la información de un conjunto WarehouseVO por el identificador del dealer,
	 * consultando además de sus propias bodegas las bodegas que aparezcan como destino de sus propias bodegas compañía-sucursal,
	 * en la tabla de elementos de bodega
	 * @param dealerId - Long identificador del dealer sobre el que se realizará la consulta
	 * @return List<WarehouseVO> Lista de las bodegas asociadas al dealer, una lista vacia en caso que no se encuentren bodegas
	 * asociadas al dealer
	 * @throws BusinessException en caso de error al tratar de ejecutar la consulta de Warehouse por código
	 * a continuación los códigos de error:<br>
     * <code>sdii_CODE_constraint_violation</code> En caso que se viole una restricción definida en la persistencia<br>
     * <code>sdii_CODE_error_data</code> En caso que se viole una regla de datos de la persistencia<br>
     * <code>sdii_CODE_generic_jdbc</code> En caso de error genérico relacionado con el componente de conexión a la base de datos<br>
     * <code>sdii_CODE_jdbc_connection</code> En caso de error al tratar de conectarse con la base de datos<br>
     * <code>sdii_CODE_sql_grammar</code> En caso de error relacionado con la gramática para ejecutar el comando en la base de datos<br>
     * <code>sdii_CODE_error_service</code> En caso de error al tratar de obtener el valor de una propiedad o un error inesperado en la capa de acceso a datos<br>
     * <code>sdii_CODE_unknow_error</code> En caso de un error inesperado en la capa de negocio<br>
	 * <code>sdii_CODE_params_null_or_missed</code> En caso que no se encuentren los parámetros necesarios para la ejecución de la operación<br>
	 * @author jjimenezh
	 */
	@WebMethod(operationName="getWarehousesByDealerIdOwnAndRelated", action="getWarehousesByDealerIdOwnAndRelated")
	public List<WarehouseVO> getWarehousesByDealerIdOwnAndRelated(@WebParam(name="dealerId") Long dealerId, @WebParam(name="warehouseTypeId") Long warehouseTypeId)throws BusinessException;
	
	
	/**
	 * Metodo: persiste la información de un WarehouseType
	 * @param obj objeto que encapsula la información necesaria para construir el WarehouseType,
	 * no debe venir asignada la propiedad id, de lo contrario se generará un error
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * a continuación los códigos de error:<br>
     * <code>sdii_CODE_constraint_violation</code> En caso que se viole una restricción definida en la persistencia<br>
     * <code>sdii_CODE_error_data</code> En caso que se viole una regla de datos de la persistencia<br>
     * <code>sdii_CODE_generic_jdbc</code> En caso de error genérico relacionado con el componente de conexión a la base de datos<br>
     * <code>sdii_CODE_jdbc_connection</code> En caso de error al tratar de conectarse con la base de datos<br>
     * <code>sdii_CODE_sql_grammar</code> En caso de error relacionado con la gramática para ejecutar el comando en la base de datos<br>
     * <code>sdii_CODE_error_service</code> En caso de error al tratar de obtener el valor de una propiedad o un error inesperado en la capa de acceso a datos<br>
     * <code>sdii_CODE_unknow_error</code> En caso de un error inesperado en la capa de negocio<br>
	 * <code>sdii_CODE_params_null_or_missed</code> En caso que no se encuentren los parámetros necesarios para la ejecución de la operación<br>
	 * @author jjimenezh
	 */
	@WebMethod(operationName="createWarehouseType", action="createWarehouseType")
	public void createWarehouseType(@WebParam(name="objWarehouseType")WarehouseTypeVO objWarehouseType) throws BusinessException;
	
	/**
	 * Metodo: actualiza la información de un WarehouseType
	 * @param obj objeto que encapsula la información del WarehouseType a actualizar,
	 * debe venir especificada la propiedad id, en caso que no venga, se generará un nuevo WarehouseType
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * a continuación los códigos de error:<br>
     * <code>sdii_CODE_constraint_violation</code> En caso que se viole una restricción definida en la persistencia<br>
     * <code>sdii_CODE_error_data</code> En caso que se viole una regla de datos de la persistencia<br>
     * <code>sdii_CODE_generic_jdbc</code> En caso de error genérico relacionado con el componente de conexión a la base de datos<br>
     * <code>sdii_CODE_jdbc_connection</code> En caso de error al tratar de conectarse con la base de datos<br>
     * <code>sdii_CODE_sql_grammar</code> En caso de error relacionado con la gramática para ejecutar el comando en la base de datos<br>
     * <code>sdii_CODE_error_service</code> En caso de error al tratar de obtener el valor de una propiedad o un error inesperado en la capa de acceso a datos<br>
     * <code>sdii_CODE_unknow_error</code> En caso de un error inesperado en la capa de negocio<br>
	 * <code>sdii_CODE_params_null_or_missed</code> En caso que no se encuentren los parámetros necesarios para la ejecución de la operación<br>
	 * @author jjimenezh
	 */
	@WebMethod(operationName="updateWarehouseType", action="updateWarehouseType")
	public void updateWarehouseType(@WebParam(name="objWarehouseType")WarehouseTypeVO objWarehouseType) throws BusinessException;
	
	
	
	/**
	 * Metodo: Obtiene un WarehouseType dado el identificador del mismo
	 * @param id identificador del WarehouseType a ser consultado
	 * @return WarehouseType con el id especificado, se lanza una exepción en caso que no se
	 * encuentre el WarehouseType con el id, ver códigos de excepción.
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * a continuación los códigos de error:<br>
     * <code>sdii_CODE_constraint_violation</code> En caso que se viole una restricción definida en la persistencia<br>
     * <code>sdii_CODE_error_data</code> En caso que se viole una regla de datos de la persistencia<br>
     * <code>sdii_CODE_generic_jdbc</code> En caso de error genérico relacionado con el componente de conexión a la base de datos<br>
     * <code>sdii_CODE_jdbc_connection</code> En caso de error al tratar de conectarse con la base de datos<br>
     * <code>sdii_CODE_sql_grammar</code> En caso de error relacionado con la gramática para ejecutar el comando en la base de datos<br>
     * <code>sdii_CODE_error_service</code> En caso de error al tratar de obtener el valor de una propiedad o un error inesperado en la capa de acceso a datos<br>
     * <code>sdii_CODE_unknow_error</code> En caso de un error inesperado en la capa de negocio<br>
	 * <code>sdii_CODE_params_null_or_missed</code> En caso que no se encuentren los parámetros necesarios para la ejecución de la operación<br>
	 * <code>sdii_CODE_entity_not_found</code> En caso que no encuentre el tipo de dealer por el id especificado<br>
	 * @author jjimenezh
	 */
	@WebMethod(operationName="getWarehouseTypeByID", action="getWarehouseTypeByID")
	public WarehouseTypeVO getWarehouseTypeByID(@WebParam(name="id")Long id) throws BusinessException;
	
	/**
	 * Metodo: Obtiene todos los WarehouseType almacenados en la persistencia
	 * @return lista con los WarehouseType existentes, una lista vacia en caso que no exista ninguno.
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * a continuación los códigos de error:<br>
     * <code>sdii_CODE_constraint_violation</code> En caso que se viole una restricción definida en la persistencia<br>
     * <code>sdii_CODE_error_data</code> En caso que se viole una regla de datos de la persistencia<br>
     * <code>sdii_CODE_generic_jdbc</code> En caso de error genérico relacionado con el componente de conexión a la base de datos<br>
     * <code>sdii_CODE_jdbc_connection</code> En caso de error al tratar de conectarse con la base de datos<br>
     * <code>sdii_CODE_sql_grammar</code> En caso de error relacionado con la gramática para ejecutar el comando en la base de datos<br>
     * <code>sdii_CODE_error_service</code> En caso de error al tratar de obtener el valor de una propiedad o un error inesperado en la capa de acceso a datos<br>
     * <code>sdii_CODE_unknow_error</code> En caso de un error inesperado en la capa de negocio<br>
	 * <code>sdii_CODE_params_null_or_missed</code> En caso que no se encuentren los parámetros necesarios para la ejecución de la operación<br>
	 * @author jjimenezh
	 */
	@WebMethod(operationName="getAllWarehouseTypes", action="getAllWarehouseTypes")
	public List<WarehouseTypeVO> getAllWarehouseTypes() throws BusinessException;
	
	/**
	 * Metodo: Obtiene la información de un WarehouseTypeVO por su código
	 * @param code - String Código del WarehouseTypeVO a ser consultado
	 * @return WarehouseTypeVO con la información del WarehouseTypeVO dado su código, nulo en caso que no se encuentre
	 * @throws BusinessException en caso de error al tratar de ejecutar la consulta de WarehouseType por Código
	 * <code>sdii_CODE_constraint_violation</code> En caso que se viole una restricción definida en la persistencia<br>
     * <code>sdii_CODE_error_data</code> En caso que se viole una regla de datos de la persistencia<br>
     * <code>sdii_CODE_generic_jdbc</code> En caso de error genérico relacionado con el componente de conexión a la base de datos<br>
     * <code>sdii_CODE_jdbc_connection</code> En caso de error al tratar de conectarse con la base de datos<br>
     * <code>sdii_CODE_sql_grammar</code> En caso de error relacionado con la gramática para ejecutar el comando en la base de datos<br>
     * <code>sdii_CODE_error_service</code> En caso de error al tratar de obtener el valor de una propiedad o un error inesperado en la capa de acceso a datos<br>
     * <code>sdii_CODE_unknow_error</code> En caso de un error inesperado en la capa de negocio<br>
	 * <code>sdii_CODE_params_null_or_missed</code> En caso que no se encuentren los parámetros necesarios para la ejecución de la operación<br>
	 * <code>sdii_CODE_entity_not_found</code> En caso que no encuentre el tipo de dealer por el id especificado<br>
	 * @author gfandino
	 */
	@WebMethod(operationName="getWarehouseTypeByCode", action="getWarehouseTypeByCode")
	public WarehouseTypeVO getWarehouseTypeByCode(@WebParam(name="code")String code) throws BusinessException;
	
	/**
	 * Metodo: persiste la información de un WarehouseElement
	 * @param obj objeto que encapsula la información necesaria para construir el WarehouseElement,
	 * no debe venir asignada la propiedad id, de lo contrario se generará un error
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * a continuación los códigos de error:<br>
     * <code>sdii_CODE_constraint_violation</code> En caso que se viole una restricción definida en la persistencia<br>
     * <code>sdii_CODE_error_data</code> En caso que se viole una regla de datos de la persistencia<br>
     * <code>sdii_CODE_generic_jdbc</code> En caso de error genérico relacionado con el componente de conexión a la base de datos<br>
     * <code>sdii_CODE_jdbc_connection</code> En caso de error al tratar de conectarse con la base de datos<br>
     * <code>sdii_CODE_sql_grammar</code> En caso de error relacionado con la gramática para ejecutar el comando en la base de datos<br>
     * <code>sdii_CODE_error_service</code> En caso de error al tratar de obtener el valor de una propiedad o un error inesperado en la capa de acceso a datos<br>
     * <code>sdii_CODE_unknow_error</code> En caso de un error inesperado en la capa de negocio<br>
	 * <code>sdii_CODE_params_null_or_missed</code> En caso que no se encuentren los parámetros necesarios para la ejecución de la operación<br>
	 * @author jjimenezh
	 */
	@WebMethod(operationName="createWarehouseElement", action="createWarehouseElement", exclude = true)
	public void createWarehouseElement(@WebParam(name="objWarehouseElement")WarehouseElementVO objWarehouseElement) throws BusinessException;
	
	/**
	 * Metodo: actualiza la información de un WarehouseElement
	 * @param obj objeto que encapsula la información del WarehouseElement a actualizar,
	 * debe venir especificada la propiedad id, en caso que no venga, se generará un nuevo WarehouseElement
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * a continuación los códigos de error:<br>
     * <code>sdii_CODE_constraint_violation</code> En caso que se viole una restricción definida en la persistencia<br>
     * <code>sdii_CODE_error_data</code> En caso que se viole una regla de datos de la persistencia<br>
     * <code>sdii_CODE_generic_jdbc</code> En caso de error genérico relacionado con el componente de conexión a la base de datos<br>
     * <code>sdii_CODE_jdbc_connection</code> En caso de error al tratar de conectarse con la base de datos<br>
     * <code>sdii_CODE_sql_grammar</code> En caso de error relacionado con la gramática para ejecutar el comando en la base de datos<br>
     * <code>sdii_CODE_error_service</code> En caso de error al tratar de obtener el valor de una propiedad o un error inesperado en la capa de acceso a datos<br>
     * <code>sdii_CODE_unknow_error</code> En caso de un error inesperado en la capa de negocio<br>
	 * <code>sdii_CODE_params_null_or_missed</code> En caso que no se encuentren los parámetros necesarios para la ejecución de la operación<br>
	 * @author jjimenezh
	 */
	@WebMethod(operationName="updateWarehouseElement", action="updateWarehouseElement", exclude = true)
	public void updateWarehouseElement(@WebParam(name="objWarehouseElement")WarehouseElementVO objWarehouseElement) throws BusinessException;
	
	/**
	 * Metodo: borra un WarehouseElement de la persistencia
	 * @param obj objeto que encapsula la información del WarehouseElement, solo se requiere la propiedad id
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * a continuación los códigos de error:<br>
     * <code>sdii_CODE_constraint_violation</code> En caso que se viole una restricción definida en la persistencia<br>
     * <code>sdii_CODE_error_data</code> En caso que se viole una regla de datos de la persistencia<br>
     * <code>sdii_CODE_generic_jdbc</code> En caso de error genérico relacionado con el componente de conexión a la base de datos<br>
     * <code>sdii_CODE_jdbc_connection</code> En caso de error al tratar de conectarse con la base de datos<br>
     * <code>sdii_CODE_sql_grammar</code> En caso de error relacionado con la gramática para ejecutar el comando en la base de datos<br>
     * <code>sdii_CODE_error_service</code> En caso de error al tratar de obtener el valor de una propiedad o un error inesperado en la capa de acceso a datos<br>
     * <code>sdii_CODE_unknow_error</code> En caso de un error inesperado en la capa de negocio<br>
	 * <code>sdii_CODE_params_null_or_missed</code> En caso que no se encuentren los parámetros necesarios para la ejecución de la operación<br>
	 * @author jjimenezh
	 */
	@WebMethod(operationName="deleteWarehouseElement", action="deleteWarehouseElement", exclude = true)
	public void deleteWarehouseElement(@WebParam(name="objWarehouseElement")WarehouseElementVO objWarehouseElement) throws BusinessException;
	
	/**
	 * Metodo: Obtiene un WarehouseElement dado el identificador del mismo
	 * @param id identificador del WarehouseElement a ser consultado
	 * @return WarehouseElement con el id especificado, se lanza una exepción en caso que no se
	 * encuentre el WarehouseElement con el id, ver códigos de excepción.
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * a continuación los códigos de error:<br>
     * <code>sdii_CODE_constraint_violation</code> En caso que se viole una restricción definida en la persistencia<br>
     * <code>sdii_CODE_error_data</code> En caso que se viole una regla de datos de la persistencia<br>
     * <code>sdii_CODE_generic_jdbc</code> En caso de error genérico relacionado con el componente de conexión a la base de datos<br>
     * <code>sdii_CODE_jdbc_connection</code> En caso de error al tratar de conectarse con la base de datos<br>
     * <code>sdii_CODE_sql_grammar</code> En caso de error relacionado con la gramática para ejecutar el comando en la base de datos<br>
     * <code>sdii_CODE_error_service</code> En caso de error al tratar de obtener el valor de una propiedad o un error inesperado en la capa de acceso a datos<br>
     * <code>sdii_CODE_unknow_error</code> En caso de un error inesperado en la capa de negocio<br>
	 * <code>sdii_CODE_params_null_or_missed</code> En caso que no se encuentren los parámetros necesarios para la ejecución de la operación<br>
	 * <code>sdii_CODE_entity_not_found</code> En caso que no encuentre el tipo de dealer por el id especificado<br>
	 * @author jjimenezh
	 */
	@WebMethod(operationName="getWarehouseElementByID", action="getWarehouseElementByID")
	public WarehouseElementVO getWarehouseElementByID(@WebParam(name="id")Long id) throws BusinessException;
	
	/**
	 * Metodo: Obtiene todos los WarehouseElement almacenados en la persistencia
	 * @return lista con los WarehouseElement existentes, una lista vacia en caso que no exista ninguno.
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * a continuación los códigos de error:<br>
     * <code>sdii_CODE_constraint_violation</code> En caso que se viole una restricción definida en la persistencia<br>
     * <code>sdii_CODE_error_data</code> En caso que se viole una regla de datos de la persistencia<br>
     * <code>sdii_CODE_generic_jdbc</code> En caso de error genérico relacionado con el componente de conexión a la base de datos<br>
     * <code>sdii_CODE_jdbc_connection</code> En caso de error al tratar de conectarse con la base de datos<br>
     * <code>sdii_CODE_sql_grammar</code> En caso de error relacionado con la gramática para ejecutar el comando en la base de datos<br>
     * <code>sdii_CODE_error_service</code> En caso de error al tratar de obtener el valor de una propiedad o un error inesperado en la capa de acceso a datos<br>
     * <code>sdii_CODE_unknow_error</code> En caso de un error inesperado en la capa de negocio<br>
	 * <code>sdii_CODE_params_null_or_missed</code> En caso que no se encuentren los parámetros necesarios para la ejecución de la operación<br>
	 * @author jjimenezh
	 */
	@WebMethod(operationName="getAllWarehouseElements", action="getAllWarehouseElements")
	public List<WarehouseElementVO> getAllWarehouseElements() throws BusinessException;
	
	/**
	 * Metodo: Obtiene todos los WarehouseElement que tengan la fecha de salida en null y cuyo estado de registro sea 'U' es decir último dados los criterios de filtrado así:<br>
	 * Debe venir especificado por lo menos un criterio de filtro<br>
	 * Si el campo warehouseId, dealerId, branchDealerId y crewId vienen especificados solo se consultará por warehouseId, los otros tres campos no se tendrán en cuenta<br> 
	 * Si el campo dealerId y branchDealerId vienen especificados solo se consultará por sucursal es decir por branchDealerId<br>
	 * Si el campo crewId, dealerId, branchDealerId vienen especificados solo se consultará por crewId y los otros dos filtros no se tendrán en cuenta<br>
	 * @param RequestCollectionInfo requestCollInfo, parametros para la paginacion
	 * @return WareHouseElementResponse, lista con los WarehouseElement que se encuentren aplicando el filtro, una lista vacia en caso que no exista ninguno con los criterios de filtro especificados.
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * a continuación los códigos de error:<br>
     * <code>sdii_CODE_constraint_violation</code> En caso que se viole una restricción definida en la persistencia<br>
     * <code>sdii_CODE_error_data</code> En caso que se viole una regla de datos de la persistencia<br>
     * <code>sdii_CODE_generic_jdbc</code> En caso de error genérico relacionado con el componente de conexión a la base de datos<br>
     * <code>sdii_CODE_jdbc_connection</code> En caso de error al tratar de conectarse con la base de datos<br>
     * <code>sdii_CODE_sql_grammar</code> En caso de error relacionado con la gramática para ejecutar el comando en la base de datos<br>
     * <code>sdii_CODE_error_service</code> En caso de error al tratar de obtener el valor de una propiedad o un error inesperado en la capa de acceso a datos<br>
     * <code>sdii_CODE_unknow_error</code> En caso de un error inesperado en la capa de negocio<br>
	 * <code>sdii_CODE_params_null_or_missed</code> En caso que no se encuentren los parámetros necesarios para la ejecución de la operación<br>
	 * @author jjimenezh
	 */
	@WebMethod(operationName="getWarehouseElementsByFilters", action="getWarehouseElementsByFilters")
	public WareHouseElementResponse getWarehouseElementsByFilters(
			@WebParam(name="wareHouseId") Long wareHouseId, 
			@WebParam(name="dealerId") Long dealerId, 
			@WebParam(name="branchDealerId") Long branchDealerId, 
			@WebParam(name="crewId") Long crewId, 
			@WebParam(name="warehouseTypeId") Long warehouseTypeId, 
			@WebParam(name="elementTypeCode") String elementTypeCode, 
			@WebParam(name="elementModelId") Long elementModelId,
			@WebParam(name="requestCollInfo") RequestCollectionInfo requestCollInfo) throws BusinessException;
	
	
	
	/**
	 * Metodo: Obtiene todos los WarehouseElement que tengan la fecha de salida en null y cuyo estado de registro sea 'U' es decir último dados los criterios de filtrado así:<br>
	 * Debe venir especificado por lo menos un criterio de filtro<br>
	 * Si el campo warehouseId, dealerId, branchDealerId y crewId vienen especificados solo se consultará por warehouseId, los otros tres campos no se tendrán en cuenta<br> 
	 * Si el campo dealerId y branchDealerId vienen especificados solo se consultará por sucursal es decir por branchDealerId<br>
	 * Si el campo crewId, dealerId, branchDealerId vienen especificados solo se consultará por crewId y los otros dos filtros no se tendrán en cuenta<br>
	 * @param RequestCollectionInfo requestCollInfo, parametros para la paginacion.
	 * @return lista con los WarehouseElement que se encuentren aplicando el filtro, una lista vacia en caso que no exista ninguno con los criterios de filtro especificados.
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * a continuación los códigos de error:<br>
     * <code>sdii_CODE_constraint_violation</code> En caso que se viole una restricción definida en la persistencia<br>
     * <code>sdii_CODE_error_data</code> En caso que se viole una regla de datos de la persistencia<br>
     * <code>sdii_CODE_generic_jdbc</code> En caso de error genérico relacionado con el componente de conexión a la base de datos<br>
     * <code>sdii_CODE_jdbc_connection</code> En caso de error al tratar de conectarse con la base de datos<br>
     * <code>sdii_CODE_sql_grammar</code> En caso de error relacionado con la gramática para ejecutar el comando en la base de datos<br>
     * <code>sdii_CODE_error_service</code> En caso de error al tratar de obtener el valor de una propiedad o un error inesperado en la capa de acceso a datos<br>
     * <code>sdii_CODE_unknow_error</code> En caso de un error inesperado en la capa de negocio<br>
	 * <code>sdii_CODE_params_null_or_missed</code> En caso que no se encuentren los parámetros necesarios para la ejecución de la operación<br>
	 * @author jjimenezh
	 */
	@WebMethod(operationName="getWarehouseElementsBySearchFilter", action="getWarehouseElementsBySearchFilter")
	public WareHouseElementResponse getWarehouseElementsBySearchFilter(@WebParam(name="whElementSearchFilterVO") WhElementSearchFilterVO whElementSearchFilterVO,@WebParam(name="requestCollInfo") RequestCollectionInfo requestCollInfo) throws BusinessException;
	
		
	/**
	 * Metodo: Obtiene los elementos de bodega que estén activos dados los códigos de tipo de elemento
	 * y los seriales.
	 * CU INV 45 Desvincular dos elementos serializados de una misma bodega
	 * @return Lista con los elementos serializados de la bodega que cumplan los criterios de filtro
	 * @param warehouseId identificador de la bodega en donde se devincularán los elementos
	 * @param elementTypeId1 código del tipo de elemento 1
	 * @param elementTypeId2 código del tipo de elemento 2
	 * @param serialEl1 
	 * @param serialEl2
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * a continuación los códigos de error:<br>
     * <code>sdii_CODE_constraint_violation</code> En caso que se viole una restricción definida en la persistencia<br>
     * <code>sdii_CODE_error_data</code> En caso que se viole una regla de datos de la persistencia<br>
     * <code>sdii_CODE_generic_jdbc</code> En caso de error genérico relacionado con el componente de conexión a la base de datos<br>
     * <code>sdii_CODE_jdbc_connection</code> En caso de error al tratar de conectarse con la base de datos<br>
     * <code>sdii_CODE_sql_grammar</code> En caso de error relacionado con la gramática para ejecutar el comando en la base de datos<br>
     * <code>sdii_CODE_error_service</code> En caso de error al tratar de obtener el valor de una propiedad o un error inesperado en la capa de acceso a datos<br>
     * <code>sdii_CODE_unknow_error</code> En caso de un error inesperado en la capa de negocio<br>
	 * <code>sdii_CODE_params_null_or_missed</code> En caso que no se encuentren los parámetros necesarios para la ejecución de la operación<br>
	 * @author jjimenezh
	 */
	@WebMethod(operationName="getWhElementsByWhIdAndElementTypeCodeAndSerials", action="getWhElementsByWhIdAndElementTypeCodeAndSerials")
	public List<WarehouseElementVO> getWhElementsByWhIdAndElementTypeCodeAndSerials(
			@WebParam(name="warehouseId") Long warehouseId,
			@WebParam(name="elementTypeCode1") Long elementTypeId1,
			@WebParam(name="elementTypeCode2") Long elementTypeId2,
			@WebParam(name="serialEl1") String serialEl1,
			@WebParam(name="serialEl2") String serialEl2) throws BusinessException;
	
	/**
	 * Metodo: Obtiene una lista de las cantidades totales de los elementos en bodegas
	 * de acuerdo con los criterios de búsqueda
	 * @param dealerId identificador de la compañía propietaria de la bodega
	 * @param warehouseId identificador de la bodega
	 * @param crewId identificador de la cuadrilla dueña de la bodega
	 * @param warehouseTypeId identificador del tipo de bodega
	 * @param elementTypeId identificador del tipo de elemento
	 * @param elementModelId identificador del modelo de elemento
	 * @param initialDate fecha inicial de la búsqueda
	 * @param finalDate fecha final de la búsqueda
	 * @return Lista con las estadísticas de cantidades de elementos en bodega
	 * @throws BusinessException En caso de error al consultar la información de cantidades del elemento
	 * a continuación los códigos de error:<br>
     * <code>sdii_CODE_constraint_violation</code> En caso que se viole una restricción definida en la persistencia<br>
     * <code>sdii_CODE_error_data</code> En caso que se viole una regla de datos de la persistencia<br>
     * <code>sdii_CODE_generic_jdbc</code> En caso de error genérico relacionado con el componente de conexión a la base de datos<br>
     * <code>sdii_CODE_jdbc_connection</code> En caso de error al tratar de conectarse con la base de datos<br>
     * <code>sdii_CODE_sql_grammar</code> En caso de error relacionado con la gramática para ejecutar el comando en la base de datos<br>
     * <code>sdii_CODE_error_service</code> En caso de error al tratar de obtener el valor de una propiedad o un error inesperado en la capa de acceso a datos<br>
     * <code>sdii_CODE_unknow_error</code> En caso de un error inesperado en la capa de negocio<br>
	 * <code>sdii_CODE_params_null_or_missed</code> En caso que no se encuentren los parámetros necesarios para la ejecución de la operación<br>
	 * @author jjimenezh
	 */
	@WebMethod(operationName="getWhElementQuantitySummariesByFilters", action="getWhElementQuantitySummariesByFilters")
	public List<WHElementQtySummaryVO> getWhElementQuantitySummariesByFilters(
			@WebParam(name="dealerId") Long dealerId, 
			@WebParam(name="warehouseId") Long warehouseId, 
			@WebParam(name="crewId") Long crewId, 
			@WebParam(name="warehouseTypeId") Long warehouseTypeId, 
			@WebParam(name="elementTypeId") Long elementTypeId, 
			@WebParam(name="elementModelId") Long elementModelId, 
			@WebParam(name="initialDate") Date initialDate, 
			@WebParam(name="finalDate") Date finalDate)throws BusinessException;
	
	/**
	 * Metodo: Obtiene una lista de las cantidades totales de los elementos en bodegas
	 * de acuerdo con los criterios de búsqueda
	 * @param QuantityWarehouseElementsDTO criterios de búsqueda
	 * @return QuantityWarehouseElementResponse Lista con las estadísticas de cantidades de elementos en bodega
	 * @throws BusinessException En caso de error al consultar la información de cantidades del elemento
	 * a continuación los códigos de error:<br>
     * <code>sdii_CODE_constraint_violation</code> En caso que se viole una restricción definida en la persistencia<br>
     * <code>sdii_CODE_error_data</code> En caso que se viole una regla de datos de la persistencia<br>
     * <code>sdii_CODE_generic_jdbc</code> En caso de error genérico relacionado con el componente de conexión a la base de datos<br>
     * <code>sdii_CODE_jdbc_connection</code> En caso de error al tratar de conectarse con la base de datos<br>
     * <code>sdii_CODE_sql_grammar</code> En caso de error relacionado con la gramática para ejecutar el comando en la base de datos<br>
     * <code>sdii_CODE_error_service</code> En caso de error al tratar de obtener el valor de una propiedad o un error inesperado en la capa de acceso a datos<br>
     * <code>sdii_CODE_unknow_error</code> En caso de un error inesperado en la capa de negocio<br>
	 * <code>sdii_CODE_params_null_or_missed</code> En caso que no se encuentren los parámetros necesarios para la ejecución de la operación<br>
	 * @author cduarte
	 */
	@WebMethod(operationName="getQuantityWarehouseElementsSummariesByFilters", action="getQuantityWarehouseElementsSummariesByFilters")
	public QuantityWarehouseElementResponse getQuantityWarehouseElementsSummariesByFilters(
			@WebParam(name="quantityWarehouseElementsDTO") QuantityWarehouseElementsDTO quantityWarehouseElementsDTO,@WebParam(name="requestCollInfo")RequestCollectionInfoDTO requestCollInfo)throws BusinessException;
	
	/**
	 * Metodo: Obtiene una lista de las cantidades detallada de los elementos en bodegas
	 * de acuerdo con los criterios de búsqueda
	 * @param QuantityWarehouseElementsDTO criterios de búsqueda
	 * @return QuantityWarehouseElementResponse Lista con las estadísticas de cantidades de elementos en bodega
	 * @throws BusinessException En caso de error al consultar la información de cantidades del elemento
	 * a continuación los códigos de error:<br>
     * <code>sdii_CODE_constraint_violation</code> En caso que se viole una restricción definida en la persistencia<br>
     * <code>sdii_CODE_error_data</code> En caso que se viole una regla de datos de la persistencia<br>
     * <code>sdii_CODE_generic_jdbc</code> En caso de error genérico relacionado con el componente de conexión a la base de datos<br>
     * <code>sdii_CODE_jdbc_connection</code> En caso de error al tratar de conectarse con la base de datos<br>
     * <code>sdii_CODE_sql_grammar</code> En caso de error relacionado con la gramática para ejecutar el comando en la base de datos<br>
     * <code>sdii_CODE_error_service</code> En caso de error al tratar de obtener el valor de una propiedad o un error inesperado en la capa de acceso a datos<br>
     * <code>sdii_CODE_unknow_error</code> En caso de un error inesperado en la capa de negocio<br>
	 * <code>sdii_CODE_params_null_or_missed</code> En caso que no se encuentren los parámetros necesarios para la ejecución de la operación<br>
	 * @author cduarte
	 */
	@WebMethod(operationName="getQuantityWarehouseElementsDetailsByFilters", action="getQuantityWarehouseElementsDetailsByFilters")
	public QuantityWarehouseElementResponse getQuantityWarehouseElementsDetailsByFilters(
			@WebParam(name="quantityWarehouseElementsDTO") QuantityWarehouseElementsDTO quantityWarehouseElementsDTO,@WebParam(name="requestCollInfo")RequestCollectionInfoDTO requestCollInfo)throws BusinessException;
	
	/**
	 * Metodo: Obtiene un objeto que encapsula la información de la canntidad de elementos en una bodega<br>
	 * CU- INV 47 Consultar  las cantidades existentes de cada  elemento en una bodega
	 * @param warehouseId identificador de la bodega
	 * @param elementTypeId tipo de elemento
	 * @param elementModelId modelo del elemento
	 * @return Objeto que encapsula la información de cantidades de un elemento en la base de datos
	 * @throws BusinessException En caso de error al ejecutar la operación
	 * a continuación los códigos de error:<br>
     * <code>sdii_CODE_constraint_violation</code> En caso que se viole una restricción definida en la persistencia<br>
     * <code>sdii_CODE_error_data</code> En caso que se viole una regla de datos de la persistencia<br>
     * <code>sdii_CODE_generic_jdbc</code> En caso de error genérico relacionado con el componente de conexión a la base de datos<br>
     * <code>sdii_CODE_jdbc_connection</code> En caso de error al tratar de conectarse con la base de datos<br>
     * <code>sdii_CODE_sql_grammar</code> En caso de error relacionado con la gramática para ejecutar el comando en la base de datos<br>
     * <code>sdii_CODE_error_service</code> En caso de error al tratar de obtener el valor de una propiedad o un error inesperado en la capa de acceso a datos<br>
     * <code>sdii_CODE_unknow_error</code> En caso de un error inesperado en la capa de negocio<br>
	 * <code>sdii_CODE_params_null_or_missed</code> En caso que no se encuentren los parámetros necesarios para la ejecución de la operación<br>
	 * @author jjimenezh
	 */
	@WebMethod(operationName="getCurrentQuantityInWarehouseByElementType", action="getCurrentQuantityInWarehouseByElementType")
	public WarehouseElementQuantityVO getCurrentQuantityInWarehouseByElementType(
			@WebParam(name="warehouseId") Long warehouseId, 
			@WebParam(name = "elementTypeId") Long elementTypeId, 
			@WebParam(name = "elementModelId") Long elementModelId)throws BusinessException;
	
	/**
	 * Metodo: Obtiene los elementos no serializados de la bodega de control de calidad del dealer especificado
	 * CU INV 11 Registrar control de calidad de elementos NO serializados
	 * @param dealerId identificador del dealer
	 * @return Lista con los elementos de una bodega
	 * @throws BusinessException En caso de error al ejecutar la operación,
	 * a continuación los códigos de error:<br>
     * <code>sdii_CODE_constraint_violation</code> En caso que se viole una restricción definida en la persistencia<br>
     * <code>sdii_CODE_error_data</code> En caso que se viole una regla de datos de la persistencia<br>
     * <code>sdii_CODE_generic_jdbc</code> En caso de error genérico relacionado con el componente de conexión a la base de datos<br>
     * <code>sdii_CODE_jdbc_connection</code> En caso de error al tratar de conectarse con la base de datos<br>
     * <code>sdii_CODE_sql_grammar</code> En caso de error relacionado con la gramática para ejecutar el comando en la base de datos<br>
     * <code>sdii_CODE_error_service</code> En caso de error al tratar de obtener el valor de una propiedad o un error inesperado en la capa de acceso a datos<br>
     * <code>sdii_CODE_unknow_error</code> En caso de un error inesperado en la capa de negocio<br>
	 * <code>sdii_CODE_params_null_or_missed</code> En caso que no se encuentren los parámetros necesarios para la ejecución de la operación<br>
	 * @author jjimeenzh
	 */
	@WebMethod(operationName="getQualityControlWhNotSerElementsByDealerId", action="getQualityControlWhNotSerElementsByDealerId")
	public List<WarehouseElementVO> getQualityControlWhNotSerElementsByDealerId(@WebParam(name="dealerId")Long dealerId)throws BusinessException;
	
	/**
	 * Metodo: Permite registrar el movimiento de elementos serializados a una bodega
	 * @param sourceWhId
	 * @param targetWsId
	 * @param serializedList
	 * @param movTypeCodeE
	 * @param movTypeCodeS
	 * @throws BusinessException
	 * <code>sdii_CODE_constraint_violation</code> En caso que se viole una restricción definida en la persistencia<br>
     * <code>sdii_CODE_error_data</code> En caso que se viole una regla de datos de la persistencia<br>
     * <code>sdii_CODE_generic_jdbc</code> En caso de error genérico relacionado con el componente de conexión a la base de datos<br>
     * <code>sdii_CODE_jdbc_connection</code> En caso de error al tratar de conectarse con la base de datos<br>
     * <code>sdii_CODE_sql_grammar</code> En caso de error relacionado con la gramática para ejecutar el comando en la base de datos<br>
     * <code>sdii_CODE_error_service</code> En caso de error al tratar de obtener el valor de una propiedad o un error inesperado en la capa de acceso a datos<br>
     * <code>sdii_CODE_unknow_error</code> En caso de un error inesperado en la capa de negocio<br>
	 * <code>sdii_CODE_params_null_or_missed</code> En caso que no se encuentren los parámetros necesarios para la ejecución de la operación<br>
	 * <code>sdii_CODE_warehouse_element_target_are_repeated</code> En caso que no el destino sea igual a la bodega actual<br>
	 * 
	 * @author Jimmy Vélez Muñoz
	 */
	@WebMethod(operationName="moveSerializedElementsToWareHouse", action="moveSerializedElementsToWareHouse")
	public void moveSerializedElementsToWareHouse(@WebParam(name = "sourceWhId") Long sourceWhId, @WebParam(name = "targetWsId") Long targetWsId, @WebParam(name="serializedList")List<SerializedVO> serializedList, @WebParam(name="movTypeCodeE")String movTypeCodeE, @WebParam(name="movTypeCodeS")String movTypeCodeS) throws BusinessException;
	
	/**
	 * Metodo: Permite separar dos objetos vinculados dado el identificador de los elementos en la bodega
	 * Caso de uso INV 45 Desvincular dos elementos serializados de una misma bodega
	 * @param warehouseId identificador de la bodega
	 * @param whElementMasterId identificador del elemento de bodega maestro
	 * @param whElementLinkedId identificador del elemento de bodega detalle
	 * @throws BusinessException En caso de error al ejecutar la operación o que los elementos no estén vinculados<br>
	 * a continuación los códigos de error:<br>
     * <code>sdii_CODE_constraint_violation</code> En caso que se viole una restricción definida en la persistencia<br>
     * <code>sdii_CODE_error_data</code> En caso que se viole una regla de datos de la persistencia<br>
     * <code>sdii_CODE_generic_jdbc</code> En caso de error genérico relacionado con el componente de conexión a la base de datos<br>
     * <code>sdii_CODE_jdbc_connection</code> En caso de error al tratar de conectarse con la base de datos<br>
     * <code>sdii_CODE_sql_grammar</code> En caso de error relacionado con la gramática para ejecutar el comando en la base de datos<br>
     * <code>sdii_CODE_error_service</code> En caso de error al tratar de obtener el valor de una propiedad o un error inesperado en la capa de acceso a datos<br>
     * <code>sdii_CODE_unknow_error</code> En caso de un error inesperado en la capa de negocio<br>
	 * <code>sdii_CODE_params_null_or_missed</code> En caso que no se encuentren los parámetros necesarios para la ejecución de la operación<br>
	 * @author jjimenezh
	 */
	@WebMethod(operationName="separateLinkedSerializedElementsInWh", action="separateLinkedSerializedElementsInWh")
	public void separateLinkedSerializedElementsInWh(
			@WebParam(name="warehouseId") Long warehouseId,
			@WebParam(name="whElementMasterId") Long whElementMasterId, 
			@WebParam(name="whElementLinkedId") Long whElementLinkedId) throws BusinessException;
	
	/**
	 * Metodo: persiste la información de un WarehouseElementStock
	 * @param obj objeto que encapsula la información necesaria para construir el WarehouseElementStock,
	 * no debe venir asignada la propiedad id, de lo contrario se generará un error
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * a continuación los códigos de error:<br>
     * <code>sdii_CODE_constraint_violation</code> En caso que se viole una restricción definida en la persistencia<br>
     * <code>sdii_CODE_error_data</code> En caso que se viole una regla de datos de la persistencia<br>
     * <code>sdii_CODE_generic_jdbc</code> En caso de error genérico relacionado con el componente de conexión a la base de datos<br>
     * <code>sdii_CODE_jdbc_connection</code> En caso de error al tratar de conectarse con la base de datos<br>
     * <code>sdii_CODE_sql_grammar</code> En caso de error relacionado con la gramática para ejecutar el comando en la base de datos<br>
     * <code>sdii_CODE_error_service</code> En caso de error al tratar de obtener el valor de una propiedad o un error inesperado en la capa de acceso a datos<br>
     * <code>sdii_CODE_unknow_error</code> En caso de un error inesperado en la capa de negocio<br>
	 * <code>sdii_CODE_params_null_or_missed</code> En caso que no se encuentren los parámetros necesarios para la ejecución de la operación<br>
	 * @author jjimenezh
	 */
	@WebMethod(operationName="createWarehouseElementStock", action="createWarehouseElementStock")
	public void createWarehouseElementStock(@WebParam(name="objWarehouseElementStock")WarehouseElementStockVO objWarehouseElementStock) throws BusinessException;
	
	/**
	 * Metodo: actualiza la información de un WarehouseElementStock
	 * @param obj objeto que encapsula la información del WarehouseElementStock a actualizar,
	 * debe venir especificada la propiedad id, en caso que no venga, se generará un nuevo WarehouseElementStock
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * a continuación los códigos de error:<br>
     * <code>sdii_CODE_constraint_violation</code> En caso que se viole una restricción definida en la persistencia<br>
     * <code>sdii_CODE_error_data</code> En caso que se viole una regla de datos de la persistencia<br>
     * <code>sdii_CODE_generic_jdbc</code> En caso de error genérico relacionado con el componente de conexión a la base de datos<br>
     * <code>sdii_CODE_jdbc_connection</code> En caso de error al tratar de conectarse con la base de datos<br>
     * <code>sdii_CODE_sql_grammar</code> En caso de error relacionado con la gramática para ejecutar el comando en la base de datos<br>
     * <code>sdii_CODE_error_service</code> En caso de error al tratar de obtener el valor de una propiedad o un error inesperado en la capa de acceso a datos<br>
     * <code>sdii_CODE_unknow_error</code> En caso de un error inesperado en la capa de negocio<br>
	 * <code>sdii_CODE_params_null_or_missed</code> En caso que no se encuentren los parámetros necesarios para la ejecución de la operación<br>
	 * @author jjimenezh
	 */
	@WebMethod(operationName="updateWarehouseElementStock", action="updateWarehouseElementStock")
	public void updateWarehouseElementStock(@WebParam(name="objWarehouseElementStock")WarehouseElementStockVO objWarehouseElementStock) throws BusinessException;
	
	/**
	 * Metodo: borra un WarehouseElementStock de la persistencia
	 * @param obj objeto que encapsula la información del WarehouseElementStock, solo se requiere la propiedad id
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * a continuación los códigos de error:<br>
     * <code>sdii_CODE_constraint_violation</code> En caso que se viole una restricción definida en la persistencia<br>
     * <code>sdii_CODE_error_data</code> En caso que se viole una regla de datos de la persistencia<br>
     * <code>sdii_CODE_generic_jdbc</code> En caso de error genérico relacionado con el componente de conexión a la base de datos<br>
     * <code>sdii_CODE_jdbc_connection</code> En caso de error al tratar de conectarse con la base de datos<br>
     * <code>sdii_CODE_sql_grammar</code> En caso de error relacionado con la gramática para ejecutar el comando en la base de datos<br>
     * <code>sdii_CODE_error_service</code> En caso de error al tratar de obtener el valor de una propiedad o un error inesperado en la capa de acceso a datos<br>
     * <code>sdii_CODE_unknow_error</code> En caso de un error inesperado en la capa de negocio<br>
	 * <code>sdii_CODE_params_null_or_missed</code> En caso que no se encuentren los parámetros necesarios para la ejecución de la operación<br>
	 * @author jjimenezh
	 */
	@WebMethod(operationName="deleteWarehouseElementStock", action="deleteWarehouseElementStock")
	public void deleteWarehouseElementStock(@WebParam(name="objWarehouseElementStock")WarehouseElementStockVO objWarehouseElementStock) throws BusinessException;
	
	/**
	 * Metodo: Obtiene un WarehouseElementStock dado el identificador del mismo
	 * @param id identificador del WarehouseElementStock a ser consultado
	 * @return WarehouseElementStock con el id especificado, se lanza una exepción en caso que no se
	 * encuentre el WarehouseElementStock con el id, ver códigos de excepción.
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * a continuación los códigos de error:<br>
     * <code>sdii_CODE_constraint_violation</code> En caso que se viole una restricción definida en la persistencia<br>
     * <code>sdii_CODE_error_data</code> En caso que se viole una regla de datos de la persistencia<br>
     * <code>sdii_CODE_generic_jdbc</code> En caso de error genérico relacionado con el componente de conexión a la base de datos<br>
     * <code>sdii_CODE_jdbc_connection</code> En caso de error al tratar de conectarse con la base de datos<br>
     * <code>sdii_CODE_sql_grammar</code> En caso de error relacionado con la gramática para ejecutar el comando en la base de datos<br>
     * <code>sdii_CODE_error_service</code> En caso de error al tratar de obtener el valor de una propiedad o un error inesperado en la capa de acceso a datos<br>
     * <code>sdii_CODE_unknow_error</code> En caso de un error inesperado en la capa de negocio<br>
	 * <code>sdii_CODE_params_null_or_missed</code> En caso que no se encuentren los parámetros necesarios para la ejecución de la operación<br>
	 * <code>sdii_CODE_entity_not_found</code> En caso que no encuentre el tipo de dealer por el id especificado<br>
	 * @author jjimenezh
	 */
	@WebMethod(operationName="getWarehouseElementStockByID", action="getWarehouseElementStockByID")
	public WarehouseElementStockVO getWarehouseElementStockByID(@WebParam(name="id")Long id) throws BusinessException;
	
	
	
	
	/**
	 * Metodo: Obtiene todos los WarehouseElementStock almacenados en la persistencia
	 * @return lista con los WarehouseElementStock existentes, una lista vacia en caso que no exista ninguno.
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * a continuación los códigos de error:<br>
     * <code>sdii_CODE_constraint_violation</code> En caso que se viole una restricción definida en la persistencia<br>
     * <code>sdii_CODE_error_data</code> En caso que se viole una regla de datos de la persistencia<br>
     * <code>sdii_CODE_generic_jdbc</code> En caso de error genérico relacionado con el componente de conexión a la base de datos<br>
     * <code>sdii_CODE_jdbc_connection</code> En caso de error al tratar de conectarse con la base de datos<br>
     * <code>sdii_CODE_sql_grammar</code> En caso de error relacionado con la gramática para ejecutar el comando en la base de datos<br>
     * <code>sdii_CODE_error_service</code> En caso de error al tratar de obtener el valor de una propiedad o un error inesperado en la capa de acceso a datos<br>
     * <code>sdii_CODE_unknow_error</code> En caso de un error inesperado en la capa de negocio<br>
	 * <code>sdii_CODE_params_null_or_missed</code> En caso que no se encuentren los parámetros necesarios para la ejecución de la operación<br>
	 * @author jjimenezh
	 */
	@WebMethod(operationName="getAllWarehouseElementStocks", action="getAllWarehouseElementStocks")
	public List<WarehouseElementStockVO> getAllWarehouseElementStocks() throws BusinessException;
	
	
	/**
	 * Metodo: Obtiene un WarehouseElementStock dado el código del tipo de elemento o el código de dealer o
	 * el código de la bodega
	 * @param elementTypeCode código de tipo de elemento
	 * @param dealerCode código de compañía
	 * @param warehouseCode  código de bodega
	 * @return lista con los stock de los elementos dados los criterios de consulta, 
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * a continuación los códigos de error:<br>
     * <code>sdii_CODE_constraint_violation</code> En caso que se viole una restricción definida en la persistencia<br>
     * <code>sdii_CODE_error_data</code> En caso que se viole una regla de datos de la persistencia<br>
     * <code>sdii_CODE_generic_jdbc</code> En caso de error genérico relacionado con el componente de conexión a la base de datos<br>
     * <code>sdii_CODE_jdbc_connection</code> En caso de error al tratar de conectarse con la base de datos<br>
     * <code>sdii_CODE_sql_grammar</code> En caso de error relacionado con la gramática para ejecutar el comando en la base de datos<br>
     * <code>sdii_CODE_error_service</code> En caso de error al tratar de obtener el valor de una propiedad o un error inesperado en la capa de acceso a datos<br>
     * <code>sdii_CODE_unknow_error</code> En caso de un error inesperado en la capa de negocio<br>
	 * <code>sdii_CODE_params_null_or_missed</code> En caso que no se encuentren los parámetros necesarios para la ejecución de la operación<br>
	 * @author jjimenezh
	 */
	@WebMethod(operationName="getWarehElemStockByElemTypeCodeOrDealerCodeOrWhCode", action="getWarehElemStockByElemTypeCodeOrDealerCodeOrWhCode")
	public WarehouseElementStockDTO getWarehElemStockByElemTypeCodeOrDealerCodeOrWhCode(@WebParam(name="elementTypeCode")String elementTypeCode, @WebParam(name="dealerId")Long dealerId, @WebParam(name="warehouseCode")String warehouseCode, @WebParam(name="countryId")Long countryId, @WebParam(name="requestCollInfo") RequestCollectionInfoDTO requestCollInfo, @WebParam(name="dealerBranchId") Long dealerBranchId) throws BusinessException;
	
	/**
	 * Metodo: persiste la información de un NotSerPartialRetirement
	 * @param obj objeto que encapsula la información necesaria para construir el NotSerPartialRetirement,
	 * no debe venir asignada la propiedad id, de lo contrario se generará un error
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * a continuación los códigos de error:<br>
     * <code>sdii_CODE_constraint_violation</code> En caso que se viole una restricción definida en la persistencia<br>
     * <code>sdii_CODE_error_data</code> En caso que se viole una regla de datos de la persistencia<br>
     * <code>sdii_CODE_generic_jdbc</code> En caso de error genérico relacionado con el componente de conexión a la base de datos<br>
     * <code>sdii_CODE_jdbc_connection</code> En caso de error al tratar de conectarse con la base de datos<br>
     * <code>sdii_CODE_sql_grammar</code> En caso de error relacionado con la gramática para ejecutar el comando en la base de datos<br>
     * <code>sdii_CODE_error_service</code> En caso de error al tratar de obtener el valor de una propiedad o un error inesperado en la capa de acceso a datos<br>
     * <code>sdii_CODE_unknow_error</code> En caso de un error inesperado en la capa de negocio<br>
	 * <code>sdii_CODE_params_null_or_missed</code> En caso que no se encuentren los parámetros necesarios para la ejecución de la operación<br>
	 * @author jjimenezh
	 */
	@WebMethod(operationName="createNotSerPartialRetirement", action="createNotSerPartialRetirement", exclude = true)
	public void createNotSerPartialRetirement(@WebParam(name="objNotSerPartialRetirement")NotSerPartialRetirementVO objNotSerPartialRetirement) throws BusinessException;
	
	/**
	 * Metodo: actualiza la información de un NotSerPartialRetirement
	 * @param obj objeto que encapsula la información del NotSerPartialRetirement a actualizar,
	 * debe venir especificada la propiedad id, en caso que no venga, se generará un nuevo NotSerPartialRetirement
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * a continuación los códigos de error:<br>
     * <code>sdii_CODE_constraint_violation</code> En caso que se viole una restricción definida en la persistencia<br>
     * <code>sdii_CODE_error_data</code> En caso que se viole una regla de datos de la persistencia<br>
     * <code>sdii_CODE_generic_jdbc</code> En caso de error genérico relacionado con el componente de conexión a la base de datos<br>
     * <code>sdii_CODE_jdbc_connection</code> En caso de error al tratar de conectarse con la base de datos<br>
     * <code>sdii_CODE_sql_grammar</code> En caso de error relacionado con la gramática para ejecutar el comando en la base de datos<br>
     * <code>sdii_CODE_error_service</code> En caso de error al tratar de obtener el valor de una propiedad o un error inesperado en la capa de acceso a datos<br>
     * <code>sdii_CODE_unknow_error</code> En caso de un error inesperado en la capa de negocio<br>
	 * <code>sdii_CODE_params_null_or_missed</code> En caso que no se encuentren los parámetros necesarios para la ejecución de la operación<br>
	 * @author jjimenezh
	 */
	@WebMethod(operationName="updateNotSerPartialRetirement", action="updateNotSerPartialRetirement", exclude = true)
	public void updateNotSerPartialRetirement(@WebParam(name="objNotSerPartialRetirement")NotSerPartialRetirementVO objNotSerPartialRetirement) throws BusinessException;
	
	/**
	 * Metodo: borra un NotSerPartialRetirement de la persistencia
	 * @param obj objeto que encapsula la información del NotSerPartialRetirement, solo se requiere la propiedad id
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * a continuación los códigos de error:<br>
     * <code>sdii_CODE_constraint_violation</code> En caso que se viole una restricción definida en la persistencia<br>
     * <code>sdii_CODE_error_data</code> En caso que se viole una regla de datos de la persistencia<br>
     * <code>sdii_CODE_generic_jdbc</code> En caso de error genérico relacionado con el componente de conexión a la base de datos<br>
     * <code>sdii_CODE_jdbc_connection</code> En caso de error al tratar de conectarse con la base de datos<br>
     * <code>sdii_CODE_sql_grammar</code> En caso de error relacionado con la gramática para ejecutar el comando en la base de datos<br>
     * <code>sdii_CODE_error_service</code> En caso de error al tratar de obtener el valor de una propiedad o un error inesperado en la capa de acceso a datos<br>
     * <code>sdii_CODE_unknow_error</code> En caso de un error inesperado en la capa de negocio<br>
	 * <code>sdii_CODE_params_null_or_missed</code> En caso que no se encuentren los parámetros necesarios para la ejecución de la operación<br>
	 * @author jjimenezh
	 */
	@WebMethod(operationName="deleteNotSerPartialRetirement", action="deleteNotSerPartialRetirement", exclude = true)
	public void deleteNotSerPartialRetirement(@WebParam(name="objNotSerPartialRetirement")NotSerPartialRetirementVO objNotSerPartialRetirement) throws BusinessException;
	
	/**
	 * Metodo: Obtiene un NotSerPartialRetirement dado el identificador del mismo
	 * @param id identificador del NotSerPartialRetirement a ser consultado
	 * @return NotSerPartialRetirement con el id especificado, se lanza una exepción en caso que no se
	 * encuentre el NotSerPartialRetirement con el id, ver códigos de excepción.
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * a continuación los códigos de error:<br>
     * <code>sdii_CODE_constraint_violation</code> En caso que se viole una restricción definida en la persistencia<br>
     * <code>sdii_CODE_error_data</code> En caso que se viole una regla de datos de la persistencia<br>
     * <code>sdii_CODE_generic_jdbc</code> En caso de error genérico relacionado con el componente de conexión a la base de datos<br>
     * <code>sdii_CODE_jdbc_connection</code> En caso de error al tratar de conectarse con la base de datos<br>
     * <code>sdii_CODE_sql_grammar</code> En caso de error relacionado con la gramática para ejecutar el comando en la base de datos<br>
     * <code>sdii_CODE_error_service</code> En caso de error al tratar de obtener el valor de una propiedad o un error inesperado en la capa de acceso a datos<br>
     * <code>sdii_CODE_unknow_error</code> En caso de un error inesperado en la capa de negocio<br>
	 * <code>sdii_CODE_params_null_or_missed</code> En caso que no se encuentren los parámetros necesarios para la ejecución de la operación<br>
	 * <code>sdii_CODE_entity_not_found</code> En caso que no encuentre el tipo de dealer por el id especificado<br>
	 * @author jjimenezh
	 */
	@WebMethod(operationName="getNotSerPartialRetirementByID", action="getNotSerPartialRetirementByID", exclude = true)
	public NotSerPartialRetirementVO getNotSerPartialRetirementByID(@WebParam(name="id")Long id) throws BusinessException;
	
	/**
	 * Metodo: Obtiene todos los NotSerPartialRetirement almacenados en la persistencia
	 * @return lista con los NotSerPartialRetirement existentes, una lista vacia en caso que no exista ninguno.
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * a continuación los códigos de error:<br>
     * <code>sdii_CODE_constraint_violation</code> En caso que se viole una restricción definida en la persistencia<br>
     * <code>sdii_CODE_error_data</code> En caso que se viole una regla de datos de la persistencia<br>
     * <code>sdii_CODE_generic_jdbc</code> En caso de error genérico relacionado con el componente de conexión a la base de datos<br>
     * <code>sdii_CODE_jdbc_connection</code> En caso de error al tratar de conectarse con la base de datos<br>
     * <code>sdii_CODE_sql_grammar</code> En caso de error relacionado con la gramática para ejecutar el comando en la base de datos<br>
     * <code>sdii_CODE_error_service</code> En caso de error al tratar de obtener el valor de una propiedad o un error inesperado en la capa de acceso a datos<br>
     * <code>sdii_CODE_unknow_error</code> En caso de un error inesperado en la capa de negocio<br>
	 * <code>sdii_CODE_params_null_or_missed</code> En caso que no se encuentren los parámetros necesarios para la ejecución de la operación<br>
	 * @author jjimenezh
	 */
	@WebMethod(operationName="getAllNotSerPartialRetirements", action="getAllNotSerPartialRetirements")
	public List<NotSerPartialRetirementVO> getAllNotSerPartialRetirements() throws BusinessException;
	
	/**
	 * Metodo: persiste la información de un MovementType
	 * @param obj objeto que encapsula la información necesaria para construir el MovementType,
	 * no debe venir asignada la propiedad id, de lo contrario se generará un error
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * a continuación los códigos de error:<br>
     * <code>sdii_CODE_constraint_violation</code> En caso que se viole una restricción definida en la persistencia<br>
     * <code>sdii_CODE_error_data</code> En caso que se viole una regla de datos de la persistencia<br>
     * <code>sdii_CODE_generic_jdbc</code> En caso de error genérico relacionado con el componente de conexión a la base de datos<br>
     * <code>sdii_CODE_jdbc_connection</code> En caso de error al tratar de conectarse con la base de datos<br>
     * <code>sdii_CODE_sql_grammar</code> En caso de error relacionado con la gramática para ejecutar el comando en la base de datos<br>
     * <code>sdii_CODE_error_service</code> En caso de error al tratar de obtener el valor de una propiedad o un error inesperado en la capa de acceso a datos<br>
     * <code>sdii_CODE_unknow_error</code> En caso de un error inesperado en la capa de negocio<br>
	 * <code>sdii_CODE_params_null_or_missed</code> En caso que no se encuentren los parámetros necesarios para la ejecución de la operación<br>
	 * @author jjimenezh
	 */
	@WebMethod(operationName="createMovementType", action="createMovementType")
	public void createMovementType(@WebParam(name="objMovementType")MovementTypeVO objMovementType) throws BusinessException;
	
	/**
	 * Metodo: actualiza la información de un MovementType
	 * @param obj objeto que encapsula la información del MovementType a actualizar,
	 * debe venir especificada la propiedad id, en caso que no venga, se generará un nuevo MovementType
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * a continuación los códigos de error:<br>
     * <code>sdii_CODE_constraint_violation</code> En caso que se viole una restricción definida en la persistencia<br>
     * <code>sdii_CODE_error_data</code> En caso que se viole una regla de datos de la persistencia<br>
     * <code>sdii_CODE_generic_jdbc</code> En caso de error genérico relacionado con el componente de conexión a la base de datos<br>
     * <code>sdii_CODE_jdbc_connection</code> En caso de error al tratar de conectarse con la base de datos<br>
     * <code>sdii_CODE_sql_grammar</code> En caso de error relacionado con la gramática para ejecutar el comando en la base de datos<br>
     * <code>sdii_CODE_error_service</code> En caso de error al tratar de obtener el valor de una propiedad o un error inesperado en la capa de acceso a datos<br>
     * <code>sdii_CODE_unknow_error</code> En caso de un error inesperado en la capa de negocio<br>
	 * <code>sdii_CODE_params_null_or_missed</code> En caso que no se encuentren los parámetros necesarios para la ejecución de la operación<br>
	 * @author jjimenezh
	 */
	@WebMethod(operationName="updateMovementType", action="updateMovementType")
	public void updateMovementType(@WebParam(name="objMovementType")MovementTypeVO objMovementType) throws BusinessException;
	
	/**
	 * Metodo: borra un MovementType de la persistencia
	 * @param obj objeto que encapsula la información del MovementType, solo se requiere la propiedad id
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * a continuación los códigos de error:<br>
     * <code>sdii_CODE_constraint_violation</code> En caso que se viole una restricción definida en la persistencia<br>
     * <code>sdii_CODE_error_data</code> En caso que se viole una regla de datos de la persistencia<br>
     * <code>sdii_CODE_generic_jdbc</code> En caso de error genérico relacionado con el componente de conexión a la base de datos<br>
     * <code>sdii_CODE_jdbc_connection</code> En caso de error al tratar de conectarse con la base de datos<br>
     * <code>sdii_CODE_sql_grammar</code> En caso de error relacionado con la gramática para ejecutar el comando en la base de datos<br>
     * <code>sdii_CODE_error_service</code> En caso de error al tratar de obtener el valor de una propiedad o un error inesperado en la capa de acceso a datos<br>
     * <code>sdii_CODE_unknow_error</code> En caso de un error inesperado en la capa de negocio<br>
	 * <code>sdii_CODE_params_null_or_missed</code> En caso que no se encuentren los parámetros necesarios para la ejecución de la operación<br>
	 * @author jjimenezh
	 */
	@WebMethod(operationName="deleteMovementType", action="deleteMovementType", exclude = true)
	public void deleteMovementType(@WebParam(name="objMovementType")MovementTypeVO objMovementType) throws BusinessException;
	
	/**
	 * Metodo: Obtiene un MovementType dado el identificador del mismo
	 * @param id identificador del MovementType a ser consultado
	 * @return MovementType con el id especificado, se lanza una exepción en caso que no se
	 * encuentre el MovementType con el id, ver códigos de excepción.
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * a continuación los códigos de error:<br>
     * <code>sdii_CODE_constraint_violation</code> En caso que se viole una restricción definida en la persistencia<br>
     * <code>sdii_CODE_error_data</code> En caso que se viole una regla de datos de la persistencia<br>
     * <code>sdii_CODE_generic_jdbc</code> En caso de error genérico relacionado con el componente de conexión a la base de datos<br>
     * <code>sdii_CODE_jdbc_connection</code> En caso de error al tratar de conectarse con la base de datos<br>
     * <code>sdii_CODE_sql_grammar</code> En caso de error relacionado con la gramática para ejecutar el comando en la base de datos<br>
     * <code>sdii_CODE_error_service</code> En caso de error al tratar de obtener el valor de una propiedad o un error inesperado en la capa de acceso a datos<br>
     * <code>sdii_CODE_unknow_error</code> En caso de un error inesperado en la capa de negocio<br>
	 * <code>sdii_CODE_params_null_or_missed</code> En caso que no se encuentren los parámetros necesarios para la ejecución de la operación<br>
	 * <code>sdii_CODE_entity_not_found</code> En caso que no encuentre el tipo de movimiento por el código especificado<br>
	 * @author jjimenezh
	 */
	@WebMethod(operationName="getMovementTypeByID", action="getMovementTypeByID")
	public MovementTypeVO getMovementTypeByID(@WebParam(name="id")Long id) throws BusinessException;
	
	/**
	 * Metodo: Obtiene todos los MovementType almacenados en la persistencia
	 * @return lista con los MovementType existentes, una lista vacia en caso que no exista ninguno.
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * a continuación los códigos de error:<br>
     * <code>sdii_CODE_constraint_violation</code> En caso que se viole una restricción definida en la persistencia<br>
     * <code>sdii_CODE_error_data</code> En caso que se viole una regla de datos de la persistencia<br>
     * <code>sdii_CODE_generic_jdbc</code> En caso de error genérico relacionado con el componente de conexión a la base de datos<br>
     * <code>sdii_CODE_jdbc_connection</code> En caso de error al tratar de conectarse con la base de datos<br>
     * <code>sdii_CODE_sql_grammar</code> En caso de error relacionado con la gramática para ejecutar el comando en la base de datos<br>
     * <code>sdii_CODE_error_service</code> En caso de error al tratar de obtener el valor de una propiedad o un error inesperado en la capa de acceso a datos<br>
     * <code>sdii_CODE_unknow_error</code> En caso de un error inesperado en la capa de negocio<br>
	 * <code>sdii_CODE_params_null_or_missed</code> En caso que no se encuentren los parámetros necesarios para la ejecución de la operación<br>
	 * @author jjimenezh
	 */
	@WebMethod(operationName="getAllMovementTypes", action="getAllMovementTypes")
	public List<MovementTypeVO> getAllMovementTypes() throws BusinessException;
	
	/**
	 * Metodo: Obtiene un MovementType dado el código del mismo
	 * @param code código del MovementType a ser consultado
	 * @return MovementType con el código especificado, nulo en caso que no se encuentre
	 * tipo de movimiento con el código especificado
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * a continuación los códigos de error:<br>
     * <code>sdii_CODE_constraint_violation</code> En caso que se viole una restricción definida en la persistencia<br>
     * <code>sdii_CODE_error_data</code> En caso que se viole una regla de datos de la persistencia<br>
     * <code>sdii_CODE_generic_jdbc</code> En caso de error genérico relacionado con el componente de conexión a la base de datos<br>
     * <code>sdii_CODE_jdbc_connection</code> En caso de error al tratar de conectarse con la base de datos<br>
     * <code>sdii_CODE_sql_grammar</code> En caso de error relacionado con la gramática para ejecutar el comando en la base de datos<br>
     * <code>sdii_CODE_error_service</code> En caso de error al tratar de obtener el valor de una propiedad o un error inesperado en la capa de acceso a datos<br>
     * <code>sdii_CODE_unknow_error</code> En caso de un error inesperado en la capa de negocio<br>
	 * <code>sdii_CODE_params_null_or_missed</code> En caso que no se encuentren los parámetros necesarios para la ejecución de la operación<br>
	 * @author jjimenezh
	 */
	@WebMethod(operationName="getMovementTypeByCode", action="getMovementTypeByCode")
	public MovementTypeVO getMovementTypeByCode(@WebParam(name="code")String code) throws BusinessException;
	
	/**
	 * Metodo: persiste la información de un RecordStatus
	 * @param obj objeto que encapsula la información necesaria para construir el RecordStatus,
	 * no debe venir asignada la propiedad id, de lo contrario se generará un error
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * a continuación los códigos de error:<br>
     * <code>sdii_CODE_constraint_violation</code> En caso que se viole una restricción definida en la persistencia<br>
     * <code>sdii_CODE_error_data</code> En caso que se viole una regla de datos de la persistencia<br>
     * <code>sdii_CODE_generic_jdbc</code> En caso de error genérico relacionado con el componente de conexión a la base de datos<br>
     * <code>sdii_CODE_jdbc_connection</code> En caso de error al tratar de conectarse con la base de datos<br>
     * <code>sdii_CODE_sql_grammar</code> En caso de error relacionado con la gramática para ejecutar el comando en la base de datos<br>
     * <code>sdii_CODE_error_service</code> En caso de error al tratar de obtener el valor de una propiedad o un error inesperado en la capa de acceso a datos<br>
     * <code>sdii_CODE_unknow_error</code> En caso de un error inesperado en la capa de negocio<br>
	 * <code>sdii_CODE_params_null_or_missed</code> En caso que no se encuentren los parámetros necesarios para la ejecución de la operación<br>
	 * @author jjimenezh
	 */
	@WebMethod(operationName="createRecordStatus", action="createRecordStatus", exclude = true)
	public void createRecordStatus(@WebParam(name="objRecordStatus")RecordStatusVO objRecordStatus) throws BusinessException;
	
	/**
	 * Metodo: actualiza la información de un RecordStatus
	 * @param obj objeto que encapsula la información del RecordStatus a actualizar,
	 * debe venir especificada la propiedad id, en caso que no venga, se generará un nuevo RecordStatus
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * a continuación los códigos de error:<br>
     * <code>sdii_CODE_constraint_violation</code> En caso que se viole una restricción definida en la persistencia<br>
     * <code>sdii_CODE_error_data</code> En caso que se viole una regla de datos de la persistencia<br>
     * <code>sdii_CODE_generic_jdbc</code> En caso de error genérico relacionado con el componente de conexión a la base de datos<br>
     * <code>sdii_CODE_jdbc_connection</code> En caso de error al tratar de conectarse con la base de datos<br>
     * <code>sdii_CODE_sql_grammar</code> En caso de error relacionado con la gramática para ejecutar el comando en la base de datos<br>
     * <code>sdii_CODE_error_service</code> En caso de error al tratar de obtener el valor de una propiedad o un error inesperado en la capa de acceso a datos<br>
     * <code>sdii_CODE_unknow_error</code> En caso de un error inesperado en la capa de negocio<br>
	 * <code>sdii_CODE_params_null_or_missed</code> En caso que no se encuentren los parámetros necesarios para la ejecución de la operación<br>
	 * @author jjimenezh
	 */
	@WebMethod(operationName="updateRecordStatus", action="updateRecordStatus", exclude = true)
	public void updateRecordStatus(@WebParam(name="objRecordStatus")RecordStatusVO objRecordStatus) throws BusinessException;
	
	/**
	 * Metodo: borra un RecordStatus de la persistencia
	 * @param obj objeto que encapsula la información del RecordStatus, solo se requiere la propiedad id
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * a continuación los códigos de error:<br>
     * <code>sdii_CODE_constraint_violation</code> En caso que se viole una restricción definida en la persistencia<br>
     * <code>sdii_CODE_error_data</code> En caso que se viole una regla de datos de la persistencia<br>
     * <code>sdii_CODE_generic_jdbc</code> En caso de error genérico relacionado con el componente de conexión a la base de datos<br>
     * <code>sdii_CODE_jdbc_connection</code> En caso de error al tratar de conectarse con la base de datos<br>
     * <code>sdii_CODE_sql_grammar</code> En caso de error relacionado con la gramática para ejecutar el comando en la base de datos<br>
     * <code>sdii_CODE_error_service</code> En caso de error al tratar de obtener el valor de una propiedad o un error inesperado en la capa de acceso a datos<br>
     * <code>sdii_CODE_unknow_error</code> En caso de un error inesperado en la capa de negocio<br>
	 * <code>sdii_CODE_params_null_or_missed</code> En caso que no se encuentren los parámetros necesarios para la ejecución de la operación<br>
	 * @author jjimenezh
	 */
	@WebMethod(operationName="deleteRecordStatus", action="deleteRecordStatus", exclude = true)
	public void deleteRecordStatus(@WebParam(name="objRecordStatus")RecordStatusVO objRecordStatus) throws BusinessException;
	
	/**
	 * Metodo: Obtiene un RecordStatus dado el identificador del mismo
	 * @param id identificador del RecordStatus a ser consultado
	 * @return RecordStatus con el id especificado, se lanza una exepción en caso que no se
	 * encuentre el RecordStatus con el id, ver códigos de excepción.
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * a continuación los códigos de error:<br>
     * <code>sdii_CODE_constraint_violation</code> En caso que se viole una restricción definida en la persistencia<br>
     * <code>sdii_CODE_error_data</code> En caso que se viole una regla de datos de la persistencia<br>
     * <code>sdii_CODE_generic_jdbc</code> En caso de error genérico relacionado con el componente de conexión a la base de datos<br>
     * <code>sdii_CODE_jdbc_connection</code> En caso de error al tratar de conectarse con la base de datos<br>
     * <code>sdii_CODE_sql_grammar</code> En caso de error relacionado con la gramática para ejecutar el comando en la base de datos<br>
     * <code>sdii_CODE_error_service</code> En caso de error al tratar de obtener el valor de una propiedad o un error inesperado en la capa de acceso a datos<br>
     * <code>sdii_CODE_unknow_error</code> En caso de un error inesperado en la capa de negocio<br>
	 * <code>sdii_CODE_params_null_or_missed</code> En caso que no se encuentren los parámetros necesarios para la ejecución de la operación<br>
	 * <code>sdii_CODE_entity_not_found</code> En caso que no encuentre el tipo de dealer por el id especificado<br>
	 * @author jjimenezh
	 */
	@WebMethod(operationName="getRecordStatusByID", action="getRecordStatusByID", exclude = true)
	public RecordStatusVO getRecordStatusByID(@WebParam(name="id")Long id) throws BusinessException;
	
	/**
	 * Metodo: Obtiene todos los RecordStatus almacenados en la persistencia
	 * @return lista con los RecordStatus existentes, una lista vacia en caso que no exista ninguno.
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * a continuación los códigos de error:<br>
     * <code>sdii_CODE_constraint_violation</code> En caso que se viole una restricción definida en la persistencia<br>
     * <code>sdii_CODE_error_data</code> En caso que se viole una regla de datos de la persistencia<br>
     * <code>sdii_CODE_generic_jdbc</code> En caso de error genérico relacionado con el componente de conexión a la base de datos<br>
     * <code>sdii_CODE_jdbc_connection</code> En caso de error al tratar de conectarse con la base de datos<br>
     * <code>sdii_CODE_sql_grammar</code> En caso de error relacionado con la gramática para ejecutar el comando en la base de datos<br>
     * <code>sdii_CODE_error_service</code> En caso de error al tratar de obtener el valor de una propiedad o un error inesperado en la capa de acceso a datos<br>
     * <code>sdii_CODE_unknow_error</code> En caso de un error inesperado en la capa de negocio<br>
	 * <code>sdii_CODE_params_null_or_missed</code> En caso que no se encuentren los parámetros necesarios para la ejecución de la operación<br>
	 * @author jjimenezh
	 */
	@WebMethod(operationName="getAllRecordStatuss", action="getAllRecordStatuss")
	public List<RecordStatusVO> getAllRecordStatuss() throws BusinessException;
	
	/**
	 * Metodo: Permite consultar el historial de elementos de un cliente por bodega
	 * Inv_135 Consultar los elementos de bodega
	 * @param WhElementSearchFilter - Filtro de busqueda
	 * @param RequestCollectionInfo requestCollInfo, parametros para la paginacion
	 * @return WareHouseElementResponse, List<WarehouseElementVO> correspondiente al filtro introducido por el cliente, vacio en otro caso
	 * @throws BusinessException en caso de error al tratar de ejecutar la consulta 
	 * <code>sdii_CODE_constraint_violation</code> En caso que se viole una restricción definida en la persistencia<br>
     * <code>sdii_CODE_error_data</code> En caso que se viole una regla de datos de la persistencia<br>
     * <code>sdii_CODE_generic_jdbc</code> En caso de error genérico relacionado con el componente de conexión a la base de datos<br>
     * <code>sdii_CODE_jdbc_connection</code> En caso de error al tratar de conectarse con la base de datos<br>
     * <code>sdii_CODE_sql_grammar</code> En caso de error relacionado con la gramática para ejecutar el comando en la base de datos<br>
     * <code>sdii_CODE_error_service</code> En caso de error al tratar de obtener el valor de una propiedad o un error inesperado en la capa de acceso a datos<br>
     * <code>sdii_CODE_unknow_error</code> En caso de un error inesperado en la capa de negocio<br>
	 * <code>sdii_CODE_params_null_or_missed</code> En caso que no se encuentren los parámetros necesarios para la ejecución de la operación<br>
	 * @author mrugeles
	 */
	@WebMethod(operationName="getWarehouseElementsByWarehouse", action="getWarehouseElementsByWarehouse")
	public QuantityWarehouseElementResponse getWarehouseElementsByWarehouse(@WebParam(name="whElementSearchFilter")WhElementSearchFilter whElementSearchFilter, @WebParam(name="requestCollInfo") RequestCollectionInfo requestCollInfo)throws BusinessException;
	
	
	/**
	 * Operación encargada de consultar las existencias de elementos en la
	 * ubicación del cliente
	 * 
	 * @param whElementSearchFilter
	 * @param requestCollInfo
	 * @return
	 * @throws BusinessException
	 * @author waguilera
	 */
	@WebMethod(operationName="getWarehouseElementsByWarehouseCustomerActual", action="getWarehouseElementsByWarehouseCustomerActual")
	public WareHouseElementCustomerResponse getWarehouseElementsByWarehouseCustomerActual(@WebParam(name="request")WareHouseElementClientFilterRequestDTO request,
			@WebParam(name="requestCollInfo")RequestCollectionInfo requestCollInfo) throws BusinessException;
	
	/**
	 * Metodo: Permite consultar los elementos que un cliente tenia instalados
	 * Inv_21 Consultar el historial de  los elementos  en un cliente
	 * @param code - String código del cliente (IBS) que se va a consultar
	 * @param documentType - Long identificador del tipo de identificación del cliente
	 * @param documentNumber - String Número de identificación del cliente
	 * @param RequestCollectionInfo requestCollInfo, parametros para la paginacion.
	 * @return List<HistoryElementsOfCustomerDTO> lista de elementos existentes en la bodega del cliente,
	 * lista vacia en caso que no se encuentren elementos activos en la bodega del cliente
	 * @throws BusinessException en caso de error al tratar de ejecutar la consulta de historial por cliente
	 * <code>sdii_CODE_constraint_violation</code> En caso que se viole una restricción definida en la persistencia<br>
     * <code>sdii_CODE_error_data</code> En caso que se viole una regla de datos de la persistencia<br>
     * <code>sdii_CODE_generic_jdbc</code> En caso de error genérico relacionado con el componente de conexión a la base de datos<br>
     * <code>sdii_CODE_jdbc_connection</code> En caso de error al tratar de conectarse con la base de datos<br>
     * <code>sdii_CODE_sql_grammar</code> En caso de error relacionado con la gramática para ejecutar el comando en la base de datos<br>
     * <code>sdii_CODE_error_service</code> En caso de error al tratar de obtener el valor de una propiedad o un error inesperado en la capa de acceso a datos<br>
     * <code>sdii_CODE_unknow_error</code> En caso de un error inesperado en la capa de negocio<br>
	 * <code>sdii_CODE_params_null_or_missed</code> En caso que no se encuentren los parámetros necesarios para la ejecución de la operación<br>
	 * @author cduarte
	 */
	@WebMethod(operationName="getHistoryElementsByCustomerIBSCodeDocTypeIdAndDocNumber", action="getHistoryElementsByCustomerIBSCodeDocTypeIdAndDocNumber")
	public CustomerElementsResponse getElementsByCustomerIBSCodeDocTypeIdAndDocNumber(@WebParam(name="customerIbsCode")String customerIbsCode, @WebParam(name="documentTypeId")Long documentTypeId,@WebParam(name="customerDocumentNumber")String customerDocumentNumber, @WebParam(name="countryId")Long countryId, @WebParam(name="requestCollInfo")RequestCollectionInfo requestCollInfo)throws BusinessException;
	
	/**
	 * Metodo: Obtiene la información de las bodegas que corresponden a una cuadrilla y un tipo de bodega
	 * Inv_25 Comparar Inventario Físico de una Bodega  VS  Inventario en SmartDealer
	 * @param crewId - Long cuadrilla a la cual se le consultarán las bodegas
	 * @param whTypeId - Long Tipo de cuadrilla que se va a consultar
	 * @return List<WarehouseVO> al tipo de bodega de la cuadrilla; vacia en otro caso
	 * @throws BusinessException en caso de error al tratar de ejecutar la consulta de Warehouse por cuadrilla y tipo de bodega
	 * <code>sdii_CODE_constraint_violation</code> En caso que se viole una restricción definida en la persistencia<br>
     * <code>sdii_CODE_error_data</code> En caso que se viole una regla de datos de la persistencia<br>
     * <code>sdii_CODE_generic_jdbc</code> En caso de error genérico relacionado con el componente de conexión a la base de datos<br>
     * <code>sdii_CODE_jdbc_connection</code> En caso de error al tratar de conectarse con la base de datos<br>
     * <code>sdii_CODE_sql_grammar</code> En caso de error relacionado con la gramática para ejecutar el comando en la base de datos<br>
     * <code>sdii_CODE_error_service</code> En caso de error al tratar de obtener el valor de una propiedad o un error inesperado en la capa de acceso a datos<br>
     * <code>sdii_CODE_unknow_error</code> En caso de un error inesperado en la capa de negocio<br>
	 * <code>sdii_CODE_params_null_or_missed</code> En caso que no se encuentren los parámetros necesarios para la ejecución de la operación<br>
	 * @author gfandino
	 */
	@WebMethod(operationName="getWhByCrewAndWhType", action="getWhByCrewAndWhType")
	public List<WarehouseVO> getWhByCrewAndWhType(@WebParam(name="crewId")Long crewId,@WebParam(name="whTypeId")Long whTypeId)throws BusinessException;
	
	/**
	 * Metodo: Permite validar el inventario de elementos serializados
	 * Inv_25 Comparar Inventario Físico de una Bodega  VS  Inventario en SmartDealer
	 * @param whID - Identificador de la bodega
	 * @param serializadosFisicos - List<SerializedVO> Elementos físicos serializados que se van a validar
	 * @param Long countryId - id del pais de la Bodega.
	 * @return List<SerializedVO> Lista de elementos serializos comparados entre Físico y SDII
	 * @throws BusinessException en caso de error al tratar de ejecutar la validación de SerializedVO de una bodega
	 * <code>sdii_CODE_constraint_violation</code> En caso que se viole una restricción definida en la persistencia<br>
     * <code>sdii_CODE_error_data</code> En caso que se viole una regla de datos de la persistencia<br>
     * <code>sdii_CODE_generic_jdbc</code> En caso de error genérico relacionado con el componente de conexión a la base de datos<br>
     * <code>sdii_CODE_jdbc_connection</code> En caso de error al tratar de conectarse con la base de datos<br>
     * <code>sdii_CODE_sql_grammar</code> En caso de error relacionado con la gramática para ejecutar el comando en la base de datos<br>
     * <code>sdii_CODE_error_service</code> En caso de error al tratar de obtener el valor de una propiedad o un error inesperado en la capa de acceso a datos<br>
     * <code>sdii_CODE_unknow_error</code> En caso de un error inesperado en la capa de negocio<br>
	 * <code>sdii_CODE_params_null_or_missed</code> En caso que no se encuentren los parámetros necesarios para la ejecución de la operación<br>
	 * @author gfandino
	 */
	@WebMethod(operationName="validateSerializedInventory", action="validateSerializedInventory")
	public List<SerializedVO> validateSerializedInventory(@WebParam(name="whID")Long whID,@WebParam(name="serializadosFisicos")List<SerializedVO> serializadosFisicos,@WebParam(name="countryId")Long countryId)throws BusinessException;
	
	/**
	 * Metodo: Permite validar el inventario de elementos no serializados
	 * Inv_25 Comparar Inventario Físico de una Bodega  VS  Inventario en SmartDealer
	 * @param whID - Identificador de la bodega
	 * @param noSerializadosFisicos - List<NotSerializedVO> Elementos físicos no serializados que se van a validar
	 * @return List<NotSerializedVO> Lista de elementos serializos comparados entre Físico y SDII
	 * @throws BusinessException en caso de error al tratar de ejecutar la validación de NotSerializedVO de una bodega
	 * <code>sdii_CODE_constraint_violation</code> En caso que se viole una restricción definida en la persistencia<br>
     * <code>sdii_CODE_error_data</code> En caso que se viole una regla de datos de la persistencia<br>
     * <code>sdii_CODE_generic_jdbc</code> En caso de error genérico relacionado con el componente de conexión a la base de datos<br>
     * <code>sdii_CODE_jdbc_connection</code> En caso de error al tratar de conectarse con la base de datos<br>
     * <code>sdii_CODE_sql_grammar</code> En caso de error relacionado con la gramática para ejecutar el comando en la base de datos<br>
     * <code>sdii_CODE_error_service</code> En caso de error al tratar de obtener el valor de una propiedad o un error inesperado en la capa de acceso a datos<br>
     * <code>sdii_CODE_unknow_error</code> En caso de un error inesperado en la capa de negocio<br>
	 * <code>sdii_CODE_params_null_or_missed</code> En caso que no se encuentren los parámetros necesarios para la ejecución de la operación<br>
	 * <code>sdii_CODE_element_not_exist</code> En caso que no se encuentre un elemento no serializado<br>
	 * @author gfandino
	 */
	@WebMethod(operationName="validateNotSerializedInventory", action="validateNotSerializedInventory")
	public List<NotSerializedVO> validateNotSerializedInventory(@WebParam(name="whID")Long whID,@WebParam(name="noSerializadosFisicos")List<NotSerializedVO> noSerializadosFisicos,@WebParam(name="countryId")Long countryId)throws BusinessException;
	
	
	/**
	 * Metodo: Obtiene los registros de todos los objetos NotSerialized propios de un WareHouse
	 * @return Lista con los registros de todos los objetos NotSerialized propios de un WareHouse
	 * @throws BusinessException en caso de error al tratar de ejecutar la tarea
	 * @author garciniegas
	 */
	
	@WebMethod(operationName="getNotSerializedsByWareHouseId", action="getNotSerializedsByWareHouseId")
	public List<NotSerializedVO> getNotSerializedsByWareHouseId( @WebParam(name="warehouseId")WarehouseVO warehouseId ) throws BusinessException;
    
    /**
	 * Metodo: Obtiene las bodegas filtradas por codigo de bodega,codigo de compa?ia,codigo
	 * de sucursal, codigo de cuadrilla y codigo para tipo de bodega
	 * @param wareHouseId identificador de la bodega
	 * @param companyId identificador de la compa?ia
	 * @param branchId identificador de la sucursal
	 * @param crewId identificador de la cuadrilla
	 * @param wareHouseTypeId identificador del tipo de bodega
	 * @param RequestCollectionInfo requestCollInfo, parametros para la paginacion.
	 * @return WareHouseResponse, Lista de bodegas producto filtro
	 * @throws BusinessException en caso de error al tratar de ejecutar la tarea
	 * @author garciniegas
	 */
	
    @WebMethod(operationName="getWarehousesByComplexFilter",action="getWarehousesByComplexFilter")
	public WareHouseResponse getWarehousesByComplexFilter( @WebParam(name="wareHouseId")WarehouseVO wareHouseId,
		   @WebParam(name="companyId")DealerVO companyId,@WebParam(name="branchId")DealerVO branchId,@WebParam(name="crewId")CrewVO crewId,
		   @WebParam(name="wareHouseTypeId")WarehouseTypeVO wareHouseTypeId, @WebParam(name="requestCollInfo") RequestCollectionInfo requestCollInfo )throws BusinessException;
    
	
	
	
	/**
	 * Metodo: Permite enviar correo de notificacion dependiendo del tipo de novedad
	 * CU INV 08
	 * @param sendEmailDTO Informacion necesaria para enviar correo de notificacion de novedad
	 * @throws BusinessException
	 * <code>sdii_CODE_constraint_violation</code> En caso que se viole una restricción definida en la persistencia<br>
     * <code>sdii_CODE_error_data</code> En caso que se viole una regla de datos de la persistencia<br>
     * <code>sdii_CODE_generic_jdbc</code> En caso de error genérico relacionado con el componente de conexión a la base de datos<br>
     * <code>sdii_CODE_jdbc_connection</code> En caso de error al tratar de conectarse con la base de datos<br>
     * <code>sdii_CODE_sql_grammar</code> En caso de error relacionado con la gramática para ejecutar el comando en la base de datos<br>
     * <code>sdii_CODE_error_service</code> En caso de error al tratar de obtener el valor de una propiedad o un error inesperado en la capa de acceso a datos<br>
     * <code>sdii_CODE_unknow_error</code> En caso de un error inesperado en la capa de negocio<br>
	 * <code>sdii_CODE_params_null_or_missed</code> En caso que no se encuentren los parámetros necesarios para la ejecución de la operación<br>
	 * @author jnova
	 */
	@WebMethod(operationName="sendEmailByEmailTypeCode", action="sendEmailByEmailTypeCode")
	public void sendEmailByEmailTypeCode(@WebParam(name="SendEmailDTO")SendEmailDTO sendEmailDTO, @WebParam(name = "countryId") Long countryId)throws BusinessException;
	
	/**
	 * Metodo: Permite filtrar las bodegas para realizar ajustes a los elementos NO serializados
	 * CU INV 26
	 * @param wareHouseId Long ID de la bodega
	 * @param dealerId Long ID del dealer
	 * @param branchId Long ID de la sucursal
	 * @param crewId Long ID de la cuadrilla
	 * @param wareHouseTypeId Long ID del tipo de bodega
	 * @param RequestCollectionInfo requestCollInfo, parametros para paginacion.
	 * @return WareHouseResponse, List<WarehouseVO> Lista con las bodegas que cumplen con el filtro y de donde se
	 * pueden realizar ajustes a elementos NO serializados
	 * @throws BusinessException En caso de error al consultar la informacion de las bodegas
	 * <code>sdii_CODE_constraint_violation</code> En caso que se viole una restricción definida en la persistencia<br>
     * <code>sdii_CODE_error_data</code> En caso que se viole una regla de datos de la persistencia<br>
     * <code>sdii_CODE_generic_jdbc</code> En caso de error genérico relacionado con el componente de conexión a la base de datos<br>
     * <code>sdii_CODE_jdbc_connection</code> En caso de error al tratar de conectarse con la base de datos<br>
     * <code>sdii_CODE_sql_grammar</code> En caso de error relacionado con la gramática para ejecutar el comando en la base de datos<br>
     * <code>sdii_CODE_error_service</code> En caso de error al tratar de obtener el valor de una propiedad o un error inesperado en la capa de acceso a datos<br>
     * <code>sdii_CODE_unknow_error</code> En caso de un error inesperado en la capa de negocio<br>
	 * @author jnova
	 */
	@WebMethod(operationName="getWarehousesByAdjustNotSerElemCriteria", action="getWarehousesByAdjustNotSerElemCriteria")
	public WareHouseResponse getWarehousesByAdjustNotSerElemCriteria(@WebParam(name="warehouseId") Long warehouseId , 
			@WebParam(name="dealerId") Long dealerId , @WebParam(name="branchId") Long branchId , 
			@WebParam(name="crewId") Long crewId , @WebParam(name="wareHouseTypeId") Long wareHouseTypeId, @WebParam(name="requestCollInfo") RequestCollectionInfo requestCollInfo)throws BusinessException;
	
	/**
	 * Metodo: Permite consultar un elemento de una bodega por bodega, por id de elemento 
	 * y si es o no serializado
	 * @param warehouseId Long Id de la bodega - Obligatorio
	 * @param isSerialized String Indica si es o no serializado 
	 * @param elementId Long Id del elemento, es opcional
	 * @param RequestCollectionInfo requestCollInfo, parametros para la paginacion
	 * @return WareHouseElementResponse, List<WarehouseElementVO> Lista con los elementos que cumplen con el filtro
	 * @throws BusinessException Error cuando se realiza la consulta de los elementos
	 * <code>sdii_CODE_constraint_violation</code> En caso que se viole una restricción definida en la persistencia<br>
     * <code>sdii_CODE_error_data</code> En caso que se viole una regla de datos de la persistencia<br>
     * <code>sdii_CODE_generic_jdbc</code> En caso de error genérico relacionado con el componente de conexión a la base de datos<br>
     * <code>sdii_CODE_jdbc_connection</code> En caso de error al tratar de conectarse con la base de datos<br>
     * <code>sdii_CODE_sql_grammar</code> En caso de error relacionado con la gramática para ejecutar el comando en la base de datos<br>
     * <code>sdii_CODE_error_service</code> En caso de error al tratar de obtener el valor de una propiedad o un error inesperado en la capa de acceso a datos<br>
     * <code>sdii_CODE_unknow_error</code> En caso de un error inesperado en la capa de negocio<br>
	 * @author jnova
	 */
	@WebMethod(operationName="getWhElementsByCriteria", action="getWhElementsByCriteria")
	public WareHouseElementResponse getWhElementsByCriteria(@WebParam(name="warehouseId")Long warehouseId , @WebParam(name="isSerialized")String isSerialized , @WebParam(name="elementId")Long elementId, @WebParam(name="requestCollInfo")RequestCollectionInfo requestCollInfo)throws BusinessException;
	
	
	/**
	 * Metodo: Permite realizar el movimiento masivo de elementos no serializados entre bodegas
	 * @param moveObject El objeto que encapsula toda la informacion requerida en el movimiento masivo
	 * @throws BusinessException Error cuando se realiza la consulta de los elementos
	 * @author garciniegas 
	 */
	@WebMethod(operationName="massiveMovementOfNotSerializedElementsBetweenWareHouse",action="massiveMovementOfNotSerializedElementsBetweenWareHouse")
	public void massiveMovementOfNotSerializedElementsBetweenWareHouse( 
			@WebParam(name="wareHouseSource")WarehouseVO wareHouseSource,@WebParam(name="wareHouseTarget")WarehouseVO wareHouseTarget,
			@WebParam(name="listObjectToMove")List<ElementVO> listObjectToMove,@WebParam(name="userId") Long userId )throws BusinessException;
	
	 /**
	 * Metodo: Obtiene los registros de todos los objetos Serialized asociados a un WareHouse
	 * @return Lista con los registros de todos los objetos Serialized propios de un WareHouse
	 * @throws BusinessException en caso de error al tratar de ejecutar la tarea
	 * @author garciniegas
	 */
	@WebMethod(operationName="getSerializedsByWareHouseId",action="getSerializedsByWareHouseId")
	public List<SerializedVO> getSerializedsByWareHouseId( @WebParam(name="warehouseId")WarehouseVO warehouseId ) throws BusinessException;
	
	
	
	
	/**
	 * Metodo: Permite realizar el movimiento masivo de elementos serializados entre bodegas con su elemento vinculado si lo tiene
	 * @param moveObject El objeto que encapsula toda la informacion requerida en el movimiento masivo
	 * @throws BusinessException Error cuando se realiza la consulta de los elementos
	 * @author garciniegas 
	 */
	@WebMethod(operationName="massiveMovementOfSerializedAndLinkedElementsBetweenWareHouse",action="massiveMovementOfSerializedAndLinkedElementsBetweenWareHouse")
	public void massiveMovementOfSerializedAndLinkedElementsBetweenWareHouse( 
			@WebParam(name="wareHouseSource")WarehouseVO wareHouseSource,
			@WebParam(name="wareHouseTarget")WarehouseVO wareHouseTarget,
			@WebParam(name="listObjectToMove")List<ElementVO> listObjectToMove,
			@WebParam(name="userId")Long userId)throws BusinessException;
	
	
	/**
	 * Metodo: Permite recuperar un objeto serializado por medio de su id de Element
	 * @param elementId El objeto ElementVO que encapsula el identificador asociado
	 * @throws BusinessException Error cuando se realiza la consulta de los elementos
	 * @author garciniegas 
	 */
	@WebMethod(operationName="getSerializedByElementId",action="getSerializedByElementId")
	public SerializedVO getSerializedByElementId( @WebParam(name="elementId")ElementVO elementId )throws BusinessException;
		/**
	 * Metodo: Obtiene la información de todos los MovementTypeVO almacenados en la persistencia con estado activo
	 * @return Lista con los MovementTypeVO existentes, una lista vacia en caso que no existan MovementTypeVO en el sistema con estado activo
	 * @throws BusinessException Error cuando se realiza la consulta de los movimientos por estado activo
	 * <code>sdii_CODE_constraint_violation</code> En caso que se viole una restricción definida en la persistencia<br>
     * <code>sdii_CODE_error_data</code> En caso que se viole una regla de datos de la persistencia<br>
     * <code>sdii_CODE_generic_jdbc</code> En caso de error genérico relacionado con el componente de conexión a la base de datos<br>
     * <code>sdii_CODE_jdbc_connection</code> En caso de error al tratar de conectarse con la base de datos<br>
     * <code>sdii_CODE_sql_grammar</code> En caso de error relacionado con la gramática para ejecutar el comando en la base de datos<br>
     * <code>sdii_CODE_error_service</code> En caso de error al tratar de obtener el valor de una propiedad o un error inesperado en la capa de acceso a datos<br>
     * <code>sdii_CODE_unknow_error</code> En caso de un error inesperado en la capa de negocio<br>
	 * @author gfandino
	 */
	@WebMethod(operationName="getMovementTypesActiveStatus",action="getMovementTypesActiveStatus")
	public List<MovementTypeVO> getMovementTypesActiveStatus() throws BusinessException;
	
	/**
	 * Metodo: Obtiene la información de todos los MovementTypeVO almacenados en la persistencia con estado activo
	 * @param requestCollInfo - RequestCollectionInfo
	 * @return MovementTypeResponse con los datos de la consulta
	 * @throws BusinessException Error cuando se realiza la consulta de los movimientos por estado activo
	 * <code>sdii_CODE_constraint_violation</code> En caso que se viole una restricción definida en la persistencia<br>
     * <code>sdii_CODE_error_data</code> En caso que se viole una regla de datos de la persistencia<br>
     * <code>sdii_CODE_generic_jdbc</code> En caso de error genérico relacionado con el componente de conexión a la base de datos<br>
     * <code>sdii_CODE_jdbc_connection</code> En caso de error al tratar de conectarse con la base de datos<br>
     * <code>sdii_CODE_sql_grammar</code> En caso de error relacionado con la gramática para ejecutar el comando en la base de datos<br>
     * <code>sdii_CODE_error_service</code> En caso de error al tratar de obtener el valor de una propiedad o un error inesperado en la capa de acceso a datos<br>
     * <code>sdii_CODE_unknow_error</code> En caso de un error inesperado en la capa de negocio<br>
	 * @author gfandino
	 */
	@WebMethod(operationName="getMovementTypesActiveStatusPage",action="getMovementTypesActiveStatusPage")
	public MovementTypeResponse getMovementTypesActiveStatusPage( @WebParam(name="requestCollInfo")RequestCollectionInfo requestCollInfo) throws BusinessException;
	
	/**
	 * Metodo: Obtiene la información de todos los MovementTypeVO almacenados en la persistencia 
	 * @param requestCollInfo - RequestCollectionInfo
	 * @param code - String código tipo de movimiento; nulo si no aplica
	 * @return MovementTypeResponse con los datos de la consulta
	 * @throws BusinessException Error cuando se realiza la consulta de los movimientos por estado activo
	 * <code>sdii_CODE_constraint_violation</code> En caso que se viole una restricción definida en la persistencia<br>
     * <code>sdii_CODE_error_data</code> En caso que se viole una regla de datos de la persistencia<br>
     * <code>sdii_CODE_generic_jdbc</code> En caso de error genérico relacionado con el componente de conexión a la base de datos<br>
     * <code>sdii_CODE_jdbc_connection</code> En caso de error al tratar de conectarse con la base de datos<br>
     * <code>sdii_CODE_sql_grammar</code> En caso de error relacionado con la gramática para ejecutar el comando en la base de datos<br>
     * <code>sdii_CODE_error_service</code> En caso de error al tratar de obtener el valor de una propiedad o un error inesperado en la capa de acceso a datos<br>
     * <code>sdii_CODE_unknow_error</code> En caso de un error inesperado en la capa de negocio<br>
	 * @author gfandino
	 */
	@WebMethod(operationName="getMovementTypesAllStatusPage",action="getMovementTypesAllStatusPage")
	public MovementTypeResponse getMovementTypesAllStatusPage(@WebParam(name="code")String code, @WebParam(name="requestCollInfo")RequestCollectionInfo requestCollInfo) throws BusinessException;
	
	/**
	 * Metodo: Metodo permite obtener todos los movementClass 
	 * @return List<MovementTypeClassDTO> lista de movementClass
	 * @throws BusinessException Error cuando se realiza la consulta de los movimientos por estado activo
	 * <code>sdii_CODE_constraint_violation</code> En caso que se viole una restricción definida en la persistencia<br>
     * <code>sdii_CODE_error_data</code> En caso que se viole una regla de datos de la persistencia<br>
     * <code>sdii_CODE_generic_jdbc</code> En caso de error genérico relacionado con el componente de conexión a la base de datos<br>
     * <code>sdii_CODE_jdbc_connection</code> En caso de error al tratar de conectarse con la base de datos<br>
     * <code>sdii_CODE_sql_grammar</code> En caso de error relacionado con la gramática para ejecutar el comando en la base de datos<br>
     * <code>sdii_CODE_error_service</code> En caso de error al tratar de obtener el valor de una propiedad o un error inesperado en la capa de acceso a datos<br>
     * <code>sdii_CODE_unknow_error</code> En caso de un error inesperado en la capa de negocio<br>
	 * @author cduarte
	 */
	@WebMethod(operationName="getAllMovementTypesClass",action="getAllMovementTypesClass")
	public List<MovementTypeClassDTO> getAllMovementTypesClass() throws BusinessException;
	
	/**
	 * Metodo: Obtiene la información de todos los MovementTypeVO almacenados en la persistencia con estado inactivo
	 * @return Lista con los MovementTypeVO existentes, una lista vacia en caso que no existan MovementTypeVO en el sistema con estado inactivo
	 * @throws BusinessException Error cuando se realiza la consulta de los movimientos por estado inactivo
	 * <code>sdii_CODE_constraint_violation</code> En caso que se viole una restricción definida en la persistencia<br>
     * <code>sdii_CODE_error_data</code> En caso que se viole una regla de datos de la persistencia<br>
     * <code>sdii_CODE_generic_jdbc</code> En caso de error genérico relacionado con el componente de conexión a la base de datos<br>
     * <code>sdii_CODE_jdbc_connection</code> En caso de error al tratar de conectarse con la base de datos<br>
     * <code>sdii_CODE_sql_grammar</code> En caso de error relacionado con la gramática para ejecutar el comando en la base de datos<br>
     * <code>sdii_CODE_error_service</code> En caso de error al tratar de obtener el valor de una propiedad o un error inesperado en la capa de acceso a datos<br>
     * <code>sdii_CODE_unknow_error</code> En caso de un error inesperado en la capa de negocio<br>
	 * @author gfandino
	 */
	@WebMethod(operationName="getMovementTypesInActiveStatus",action="getMovementTypesInActiveStatus",exclude=true)
	public List<MovementTypeVO> getMovementTypesInActiveStatus() throws BusinessException;
	
	/**
	 * Metodo: Regresa una lista de objetos WareHouseElementHistoricalDTO la cual representa un historico de los
	 * movimientos realizados al objeto serializado
	 * @param dealerId
	 * @param elementModelId
	 * @param RequestCollectionInfo requestCollInfo, parametros para la paginacion
	 * @return WareHouseElementHistoricalResponse, una lista de objetos WareHouseElementHistoricalDTO
	 * @throws BusinessException En caso de error al ejecutar la operacion
	 * @author garciniegas 
	 */
	@WebMethod(operationName="getWareHouseElementHistoricalForSerializedElement",action="getWareHouseElementHistoricalForSerializedElement")
	public WareHouseElementHistoricalResponse getWareHouseElementHistoricalForSerializedElement( @WebParam(name="serializedId")Long serializedId, @WebParam(name="requestCollInfo") RequestCollectionInfo requestCollInfo )throws BusinessException;
	
	/**
	 * Metodo: Regresa una lista de objetos WareHouseElementHistoricalDTO la cual representa un historico de los
	 * movimientos realizados al objeto serializado
	 * @param linkedOrSerialCode
	 * @return WareHouseElementHistoricalDTO, una lista de objetos WareHouseElementHistoricalDTO
	 * @throws BusinessException En caso de error al ejecutar la operacion
	 * @author cduarte 
	 */
	@WebMethod(operationName="getMovedWareHouseElementSerializedByLinkedOrSerialCode",action="getMovedWareHouseElementSerializedByLinkedOrSerialCode")
	public MovedElementSerializedResponse getMovedWareHouseElementSerializedByLinkedOrSerialCode(@WebParam(name="linkedOrSerialCode")String linkedOrSerialCode,@WebParam(name="countryId")Long countryId,@WebParam(name="requestCollInfo")RequestCollectionInfo requestCollInfo)throws BusinessException;
	
	
	/**
	 * Metodo: Permite consultar los elementos serializados de una bodega dependiento del tipo del elemento y
	 * del modelo del elemento. En este caso la cantidad es opcional. 
	 * y si es o no serializado
	 * @param warehouseId Long Id de la bodega - Obligatorio
	 * @param typeId Long Id del tipo del elemento serializado - Obligatorio
	 * @param modelId Long Id del modelo del elemento serializado - Obligatorio
	 * @param requestCollectionInfo
	 * @return SerializedWhElementsByCriteriaPaginationResponse Lista con los elementos que cumplen con el filtro
	 * @throws BusinessException Error cuando se realiza la consulta de los elementos
	 * <code>sdii_CODE_constraint_violation</code> En caso que se viole una restricción definida en la persistencia<br>
     * <code>sdii_CODE_error_data</code> En caso que se viole una regla de datos de la persistencia<br>
     * <code>sdii_CODE_generic_jdbc</code> En caso de error genérico relacionado con el componente de conexión a la base de datos<br>
     * <code>sdii_CODE_jdbc_connection</code> En caso de error al tratar de conectarse con la base de datos<br>
     * <code>sdii_CODE_sql_grammar</code> En caso de error relacionado con la gramática para ejecutar el comando en la base de datos<br>
     * <code>sdii_CODE_error_service</code> En caso de error al tratar de obtener el valor de una propiedad o un error inesperado en la capa de acceso a datos<br>
     * <code>sdii_CODE_unknow_error</code> En caso de un error inesperado en la capa de negocio<br>
	 * @author jnova
	 */
	@WebMethod(operationName="getSerializedWhElementsByCriteria", action="getSerializedWhElementsByCriteria")
	public SerializedWhElementsByCriteriaPaginationResponse getSerializedWhElementsByCriteria(@WebParam(name="idWh")Long warehouseId , @WebParam(name="typeId")Long typeId, @WebParam(name="modelId")Long modelId, @WebParam(name = "requestCollectionInfo") RequestCollectionInfo requestCollectionInfo)throws BusinessException;
	
	/**
	 * Metodo: Permite detectar cuando hay insuficiencia por stock para poder lanzar una alarma por 
	 * stock insuficiente de elementos
	 * @param warehouse la bodega a verificar
	 * @param user el usuario involucrado
	 * @return un boolean indicando si la alarma de verificacionde stock insuficiente se disparo
	 * @throws BusinessException En caso de error al tratar de ejecutar la operacion
	 * @author garciniegas
	 */
	@WebMethod(operationName="checkShortcomingInWarehouse", action="checkShortcomingInWarehouse")
	public boolean checkShortcomingInWarehouse( @WebParam(name="warehouse")Long warehouse,
			                                    @WebParam(name="user")Long user )throws BusinessException;
	
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
	@WebMethod(operationName="checkWarehouseIndicatorAlarmByDealerIdWarehouseTypeAndUser", action="checkWarehouseIndicatorAlarmByDealerIdWarehouseTypeAndUser")
	public List<ResponseWareHouseIndicatorAlarmDTO> checkWarehouseIndicatorAlarmByDealerIdWarehouseTypeAndUser(  @WebParam(name="dealerId") Long dealerId,
			 																							   @WebParam(name="warehouseType") String warehouseType,
			 																							   @WebParam(name="user") Long user )throws BusinessException;
	
	/**
	 * Metodo: Permite consultar los elementos serialalizados en bodega de calidad del dealer asociado de un tipo de elemento
	 * @param dealer - DealerVO que va a ser consultado. Id en cero si no hace parte del filtro
	 * @param elementType - Long Que va a ser consultado. Id en cero si no hace parte del filtro
	 * @param serialCode - Long Que va a ser consultado. Cadena vacia (tamaño 0) si no hace parte del filtro
	 * @return  List<WarehouseElementVO> correspondiente a los filtros especificados
	 * @throws BusinessException En caso de erro al consultar los elementos de bodegas especificadas.
	 *  <code>sdii_CODE_constraint_violation</code> En caso que se viole una restricción definida en la persistencia<br>
     * <code>sdii_CODE_error_data</code> En caso que se viole una regla de datos de la persistencia<br>
     * <code>sdii_CODE_generic_jdbc</code> En caso de error genérico relacionado con el componente de conexión a la base de datos<br>
     * <code>sdii_CODE_jdbc_connection</code> En caso de error al tratar de conectarse con la base de datos<br>
     * <code>sdii_CODE_sql_grammar</code> En caso de error relacionado con la gramática para ejecutar el comando en la base de datos<br>
     * <code>sdii_CODE_error_service</code> En caso de error al tratar de obtener el valor de una propiedad o un error inesperado en la capa de acceso a datos<br>
     * <code>sdii_CODE_unknow_error</code> En caso de un error inesperado en la capa de negocio<br>
	 * @author gfadnino
	 */
	@WebMethod(operationName="getWhouseSerializedElementsByElementTypeAndQAWh", action="getWhouseSerializedElementsByElementTypeAndQAWh")
	public List<WarehouseElementVO> getWhouseSerializedElementsByElementTypeAndQAWh(@WebParam(name="dealer")DealerVO dealer,
			@WebParam(name="elementType")co.com.directv.sdii.model.vo.ElementTypeVO elementType, @WebParam(name="serialCode")String serialCode) throws BusinessException;
	
	/**
	 * Metodo: Permite obtener los movimientos parciales de elementos no serializados a partir del id del elemento
	 * @param elementId Long id del elemento
	 * @return List<NotSerPartialRetirement> Lista con de movimientos parciales de un elemento
	 * @throws BusinessException En caso de error realizando la consulta
	 * @author garciniegas
	 */
	@WebMethod(operationName="getNotSerPartialRetirementByElementId", action="getNotSerPartialRetirementByElementId")
	public List<NotSerPartialRetirementVO> getNotSerPartialRetirementByElementId(@WebParam(name="elementId")Long elementId)  throws BusinessException;
	
	
	

	/**
	 * UC_INV_14
	 * 
	 * Metodo: Realiza el movimiento de elementos serializados entre bodegas
	 * @param ElementMovementDTO dto
	 * @throws BusinessException
	 * 
	 * <code>sdii_CODE_constraint_violation</code> En caso que se viole una restricción definida en la persistencia<br>
     * <code>sdii_CODE_error_data</code> En caso que se viole una regla de datos de la persistencia<br>
     * <code>sdii_CODE_generic_jdbc</code> En caso de error genérico relacionado con el componente de conexión a la base de datos<br>
     * <code>sdii_CODE_jdbc_connection</code> En caso de error al tratar de conectarse con la base de datos<br>
     * <code>sdii_CODE_sql_grammar</code> En caso de error relacionado con la gramática para ejecutar el comando en la base de datos<br>
     * <code>sdii_CODE_error_service</code> En caso de error al tratar de obtener el valor de una propiedad o un error inesperado en la capa de acceso a datos<br>
     * <code>sdii_CODE_unknow_error</code> En caso de un error inesperado en la capa de negocio<br>
	 * <code>sdii_CODE_params_null_or_missed</code> En caso que no se encuentren los parámetros necesarios para la ejecución de la operación<br>
	 * 
	 * @author Jimmy Vélez Muñoz
	 * @author jnova
	 */
	@WebMethod(operationName="moveSerializedElementsBetweenWareHouses", action="moveSerializedElementsBetweenWareHouses")
	public void moveSerializedElementsBetweenWareHouses(@WebParam(name = "dto") MovementElementDTO dto) throws BusinessException;
	
	/**
	 * Método: Obtiene los tipos de bodega que se encuentran en estado activo
	 * @return List<WarehouseTypeVO> que se encuentran en estado activo
	 * @throws BusinessException en caso de error al tratar de ejecutar la consulta de WarehouseType activas
	 * <br>
	 * a continuación los códigos de error:<br>
     * <code>sdii_CODE_constraint_violation</code> En caso que se viole una restricción definida en la persistencia<br>
     * <code>sdii_CODE_error_data</code> En caso que se viole una regla de datos de la persistencia<br>
     * <code>sdii_CODE_generic_jdbc</code> En caso de error genérico relacionado con el componente de conexión a la base de datos<br>
     * <code>sdii_CODE_jdbc_connection</code> En caso de error al tratar de conectarse con la base de datos<br>
     * <code>sdii_CODE_sql_grammar</code> En caso de error relacionado con la gramática para ejecutar el comando en la base de datos<br>
     * <code>sdii_CODE_error_service</code> En caso de error al tratar de obtener el valor de una propiedad o un error inesperado en la capa de acceso a datos<br>
     * <code>sdii_CODE_unknow_error</code> En caso de un error inesperado en la capa de negocio<br>
	 * <code>sdii_CODE_params_null_or_missed</code> En caso que no se encuentren los parámetros necesarios para la ejecución de la operación<br>
	 * @author gfandino
	 */
	@WebMethod(operationName="getWarehouseTypeActive", action="getWarehouseTypeActive")
	public  List<WarehouseTypeVO> getWarehouseTypeActive()throws BusinessException;
	
	/**
	 * Método: Obtiene los tipos de bodega que se encuentran en estado activo
	 * @param requestCollInfo - Info para paginación
	 * @return WareheouseTypeResponse con la información de las listas consultadas
	 * @throws BusinessException en caso de error al tratar de ejecutar la consulta de WarehouseType activas
	 * @author gfandino
	 */
	@WebMethod(operationName="getWarehouseTypeActiveByPage", action="getWarehouseTypeActiveByPage")
	public WareheouseTypeResponse getWarehouseTypeActivePage(@WebParam(name="requestCollInfo")RequestCollectionInfo requestCollInfo)throws BusinessException;
	
	/**
	 * Método: Obtiene los tipos de bodega que se encuentran en todos los estados
	 * @param requestCollInfo - Info para paginación
	 * @param code - String código del tipo de bodega; nulo si no aplica
	 * @return WareheouseTypeResponse con la información de las listas consultadas
	 * @throws BusinessException en caso de error al tratar de ejecutar la consulta de WarehouseType activas
	 * @author gfandino
	 */
	@WebMethod(operationName="getWarehouseTypePage", action="getWarehouseTypePage")
	public WareheouseTypeResponse getWarehouseTypePage(@WebParam(name="code")String code,@WebParam(name="requestCollInfo")RequestCollectionInfo requestCollInfo)throws BusinessException;
	
	
	/**
	 * Método: Obtiene las bodegas del país que se encuentran en estado activo
	 * @param countryId - identificación del país
	 * @param dealerId - identificador del dealer (-1 si no aplica)
	 * @return List<WarehouseVO>
	 * @throws BusinessException en caso de error al tratar de ejecutar la consulta de las bodegas activas
	 *  <code>sdii_CODE_constraint_violation</code> En caso que se viole una restricción definida en la persistencia<br>
     * <code>sdii_CODE_error_data</code> En caso que se viole una regla de datos de la persistencia<br>
     * <code>sdii_CODE_generic_jdbc</code> En caso de error genérico relacionado con el componente de conexión a la base de datos<br>
     * <code>sdii_CODE_jdbc_connection</code> En caso de error al tratar de conectarse con la base de datos<br>
     * <code>sdii_CODE_sql_grammar</code> En caso de error relacionado con la gramática para ejecutar el comando en la base de datos<br>
     * <code>sdii_CODE_error_service</code> En caso de error al tratar de obtener el valor de una propiedad o un error inesperado en la capa de acceso a datos<br>
     * <code>sdii_CODE_unknow_error</code> En caso de un error inesperado en la capa de negocio<br>
	 * <code>sdii_CODE_params_null_or_missed</code> En caso que no se encuentren los parámetros necesarios para la ejecución de la operación<br>
	 * @author gfandino
	 */
	@WebMethod(operationName="getWarehousesByCountryIdAndActive", action="getWarehousesByCountryIdAndActive")
	public List<WarehouseVO> getWarehousesByCountryIdAndActive(@WebParam(name="countryId")Long countryId,@WebParam(name="dealerId")Long dealerId) throws BusinessException;
	
	/**
	 * Método: Obtiene las bodegas del país que se encuentran en estado activo paginado
	 * @param countryId - String identificación del país
	 * @param requestCollInfo - RequestCollectionInfo información de la paginación
	 * @return WareheouseResponse con la información de la consulta
	 * @throws BusinessException en caso de error al tratar de ejecutar la consulta de las bodegas activas
	 *  <code>sdii_CODE_constraint_violation</code> En caso que se viole una restricción definida en la persistencia<br>
     * <code>sdii_CODE_error_data</code> En caso que se viole una regla de datos de la persistencia<br>
     * <code>sdii_CODE_generic_jdbc</code> En caso de error genérico relacionado con el componente de conexión a la base de datos<br>
     * <code>sdii_CODE_jdbc_connection</code> En caso de error al tratar de conectarse con la base de datos<br>
     * <code>sdii_CODE_sql_grammar</code> En caso de error relacionado con la gramática para ejecutar el comando en la base de datos<br>
     * <code>sdii_CODE_error_service</code> En caso de error al tratar de obtener el valor de una propiedad o un error inesperado en la capa de acceso a datos<br>
     * <code>sdii_CODE_unknow_error</code> En caso de un error inesperado en la capa de negocio<br>
	 * <code>sdii_CODE_params_null_or_missed</code> En caso que no se encuentren los parámetros necesarios para la ejecución de la operación<br>
	 * @author gfandino
	 */
	@WebMethod(operationName="getWarehousesByCountryIdAndActivePage", action="getWarehousesByCountryIdAndActivePage")
	public WareHouseResponse getWarehousesByCountryIdAndActivePage(@WebParam(name="countryId")Long countryId,@WebParam(name="requestCollInfo")RequestCollectionInfo requestCollInfo) throws BusinessException;
	
	
	
	
	


	/**
	 * UC_INV_12
	 * 
	 * Método: Permite mover un elemento no serializado entre bodega de origen y bodega de destino.
	 * 
	 * @param ElementMovementDTO dto
	 * @throws BusinessException
	 * 
	 * @author Jimmy Vélez Muñoz
	 * @author jnova
	 */
	@WebMethod(operationName="moveNotSerializedElementBetweenWareHouses", action="moveNotSerializedElementBetweenWareHouses")
	public void moveNotSerializedElementBetweenWareHouses(@WebParam(name = "dto") MovementElementDTO dto) throws BusinessException;

	/**
	 * Metodo: UC Inv11, retorna los elementos no serializados de la Bodega de Calidad, segun el dealerId (Operador Logistico.)
	 *
	 * @param dealerId
	 * @return
	 * @throws BusinessException
	 * 
	 * @author hcorredor
	 */
	@WebMethod(operationName="getWarehouseQANOTSerializedElements", action="getWarehouseQANOTSerializedElements")
	public WareHouseElementResponse getWarehouseQANOTSerializedElements(@WebParam(name = "dealerId") Long dealerId,
			                                                         @WebParam(name="requestCollInfo")RequestCollectionInfo requestCollInfo)
			throws BusinessException;

	/**
	 * Metodo: unión de movimientos de elementos no serializados de bodega de calidad
	 * a bodega de disponibles y de devoluciones
	 * @param elementsAvailables
	 * @param elementsReturns
	 * @param dealer
	 * @throws BusinessException
	 * @author wjimenez
	 */
	@WebMethod(operationName="moveNotSerializedElementBetweenWareHouseQuality", action="moveNotSerializedElementBetweenWareHouseQuality")
	public void moveNotSerializedElementBetweenWareHouseQuality(
			@WebParam(name = "elementsAvailables")List<ElementVO> elementsAvailables,
			@WebParam(name = "elementsReturns")List<ElementVO> elementsReturns, @WebParam(name = "dealer")Dealer dealer, @WebParam(name = "userVO")UserVO user)
			throws BusinessException;
	
	/**
	 * Metodo: UC Inv11, Mueve elemetos no serializados de la Bodega de Calidad a la de Disponibles
	 * @param elementIds
	 * @param quantity
	 * @throws BusinessException
	 * @author hcorredor
	 */
	@WebMethod(operationName="moveNotSerializedElementBetweenWareHouseQualityAvailable", action="moveNotSerializedElementBetweenWareHouseQualityAvailable")
	public void moveNotSerializedElementBetweenWareHouseQualityAvailable(@WebParam(name = "elementsVO") List<ElementVO> elementsVO, @WebParam(name = "dealer")Dealer dealer) throws BusinessException;

	/**
	 * 
	 * @param elementsVO
	 * @param dealerId
	 * @throws BusinessException
	 */
	@WebMethod(operationName="moveNotSerializedElementBetweenWareHouseQualityReturns", action="moveNotSerializedElementBetweenWareHouseQualityReturns")
	public void moveNotSerializedElementBetweenWareHouseQualityReturns(@WebParam(name = "elementsVO")List<ElementVO> elementsVO, @WebParam(name = "dealer")Dealer dealer) throws BusinessException;

	/**
	 * 
	 * @param dealer
	 * @param elementType
	 * @param serialCode
	 * @param requestCollInfo
	 * @return
	 * @throws BusinessException
	 */
	@WebMethod(operationName="getWarehouseQASerializedElements", action="getWarehouseQASerializedElements")
	public WareHouseElementResponse getWarehouseQASerializedElements(
			@WebParam(name = "dealer")Dealer dealer,
			@WebParam(name = "elementType")String elementType, 
			@WebParam(name = "serialCode")String serialCode,
			@WebParam(name = "requestCollInfo") RequestCollectionInfo requestCollInfo) throws BusinessException;

	/**
	 * 
	 * @param elementsVO
	 * @param dealer
	 */
	@WebMethod(operationName="moveSerializedElementBetweenWareHouseQualityAvailable", action="moveSerializedElementBetweenWareHouseQualityAvailable")
	public void moveSerializedElementBetweenWareHouseQualityAvailable(
			@WebParam(name = "elementsVO")List<ElementVO> elementsVO, 
			@WebParam(name = "dealer")Dealer dealer, 
			@WebParam(name = "userId") Long userId) throws BusinessException;

	/**
	 * 
	 * @param elementsVO
	 * @param dealer
	 * @throws BusinessException
	 */
	@WebMethod(operationName="moveSerializedElementBetweenWareHouseQualityReturn", action="moveSerializedElementBetweenWareHouseQualityReturn")
	public void moveSerializedElementBetweenWareHouseQualityReturn(
			@WebParam(name = "elementsVO")List<ElementVO> elementsVO,
			@WebParam(name = "dealer") Dealer dealer, 
			@WebParam(name = "userId") Long userId) throws BusinessException;

	
	/**
	 * 
	 * @param objWarehouse
	 * @return
	 * @throws BusinessException
	 */
	@WebMethod(operationName="createWarehouseTransit", action="createWarehouseTransit")
	public WarehouseVO createWarehouseTransit(@WebParam(name = "objWarehouse") WarehouseVO objWarehouse)
			throws BusinessException;

	/**
	 * 
	 * Metodo: Servicio encargado de mover los elementos de las  cuadrillas a la 
	 * bodega de disponibles de la compania.
	 * @param companys
	 * @throws BusinessException <tipo> <descripcion>
	 * @author
	 */
	@WebMethod(operationName="moveElementsFromWHCrewToWHCompany", action="moveElementsFromWHCrewToWHCompany")
	public void moveElementsFromWHCrewToWHCompany(@WebParam(name = "companies") List<DealerVO> companies, @WebParam(name = "user") UserVO user)
			throws BusinessException;
	
	/**
	 * Metodo: Obtiene la información de las bodegas que corresponden a una cuadrilla o a un dealer y un tipo de bodega
	 * CU 31
	 * @param dealerId - Long identificador del dealer
	 * @param crewId - Long cuadrilla a la cual se le consultarán las bodegas
	 * @param whTypeId - Long Tipo de cuadrilla que se va a consultar
	 * @return List<WarehouseVO> al tipo de bodega de la cuadrilla o del dealer; vacia en otro caso
	 * @throws BusinessException en caso de error al tratar de ejecutar la consulta de Warehouse por cuadrilla y tipo de bodega
	 * <code>sdii_CODE_constraint_violation</code> En caso que se viole una restricción definida en la persistencia<br>
     * <code>sdii_CODE_error_data</code> En caso que se viole una regla de datos de la persistencia<br>
     * <code>sdii_CODE_generic_jdbc</code> En caso de error genérico relacionado con el componente de conexión a la base de datos<br>
     * <code>sdii_CODE_jdbc_connection</code> En caso de error al tratar de conectarse con la base de datos<br>
     * <code>sdii_CODE_sql_grammar</code> En caso de error relacionado con la gramática para ejecutar el comando en la base de datos<br>
     * <code>sdii_CODE_error_service</code> En caso de error al tratar de obtener el valor de una propiedad o un error inesperado en la capa de acceso a datos<br>
     * <code>sdii_CODE_unknow_error</code> En caso de un error inesperado en la capa de negocio<br>
	 * <code>sdii_CODE_params_null_or_missed</code> En caso que no se encuentren los parámetros necesarios para la ejecución de la operación<br>
	 * @author jnova
	 */
	@WebMethod(operationName="getWhByCrewAndDealerAndWhType", action="getWhByCrewAndDealerAndWhType")
	public List<WarehouseVO> getWhByCrewAndDealerAndWhType(@WebParam(name="dealerId")Long dealerId,@WebParam(name="crewId")Long crewId,@WebParam(name="whTypeId")Long whTypeId)throws BusinessException;
	
	/**
	 * 
	 * Metodo: Obtiene los elementos de 
	 * @param warehouseId
	 * @param serial
	 * @param elementTypeId
	 * @param quantity
	 * @return
	 * @throws BusinessException <tipo> <descripcion>
	 * @author
	 */
	@WebMethod(operationName="getWarehouseElementBySerialOrElementType", action="getWarehouseElementBySerialOrElementType")
	public WarehouseElementVO getWarehouseElementBySerialOrElementType(
			@WebParam(name="warehouseId")Long warehouseId, @WebParam(name="serial")String serial, @WebParam(name="elementTypeId")Long elementTypeId, @WebParam(name="quantity")Double quantity)
			throws BusinessException;
	
	/**
	 * Metodo: Obtiene todos los MovementType almacenados que esten activos y que correspondas a la clase de movimiento 
	 * enviada como parametro
	 * @param moveClass 
	 * @return lista con los MovementType existentes que concidan con el criterio de busqueda
	 * una lista vacia en caso que no exista ninguno. Trae unicamente los tipos de movimento 
	 * que no son automaticos
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * a continuación los códigos de error:<br>
     * <code>sdii_CODE_constraint_violation</code> En caso que se viole una restricción definida en la persistencia<br>
     * <code>sdii_CODE_error_data</code> En caso que se viole una regla de datos de la persistencia<br>
     * <code>sdii_CODE_generic_jdbc</code> En caso de error genérico relacionado con el componente de conexión a la base de datos<br>
     * <code>sdii_CODE_jdbc_connection</code> En caso de error al tratar de conectarse con la base de datos<br>
     * <code>sdii_CODE_sql_grammar</code> En caso de error relacionado con la gramática para ejecutar el comando en la base de datos<br>
     * <code>sdii_CODE_error_service</code> En caso de error al tratar de obtener el valor de una propiedad o un error inesperado en la capa de acceso a datos<br>
     * <code>sdii_CODE_unknow_error</code> En caso de un error inesperado en la capa de negocio<br>
	 * <code>sdii_CODE_params_null_or_missed</code> En caso que no se encuentren los parámetros necesarios para la ejecución de la operación<br>
	 * @author waguilera
	 */
	@WebMethod(operationName="getMovementTypesAtiveByClass", action="getMovementTypesAtiveByClass")
	public List<MovementTypeVO> getMovementTypesAtiveByClass(String moveClass) throws BusinessException;

	/**
	 * 
	 * Metodo: <Descripcion>
	 * @param dealerId
	 * @param warehouseType
	 * @return
	 * @throws BusinessException <tipo> <descripcion>
	 * @author
	 */
	@WebMethod(operationName="getWarehouseTypeByDealerId", action="getWarehouseTypeByDealerId")
	public WarehouseVO getWarehouseTypeByDealerId(@WebParam(name="dealerId") Long dealerId, @WebParam(name="warehouseType")String warehouseType)
			throws BusinessException;
	
	
	/**
	 * Metodo: Obtiene todos los Warehouse almacenados en la persistencia que esten activos
	 * @return lista con los Warehouse existentes, una lista vacia en caso que no exista ninguno.
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * a continuación los códigos de error:<br>
     * <code>sdii_CODE_constraint_violation</code> En caso que se viole una restricción definida en la persistencia<br>
     * <code>sdii_CODE_error_data</code> En caso que se viole una regla de datos de la persistencia<br>
     * <code>sdii_CODE_generic_jdbc</code> En caso de error genérico relacionado con el componente de conexión a la base de datos<br>
     * <code>sdii_CODE_jdbc_connection</code> En caso de error al tratar de conectarse con la base de datos<br>
     * <code>sdii_CODE_sql_grammar</code> En caso de error relacionado con la gramática para ejecutar el comando en la base de datos<br>
     * <code>sdii_CODE_error_service</code> En caso de error al tratar de obtener el valor de una propiedad o un error inesperado en la capa de acceso a datos<br>
     * <code>sdii_CODE_unknow_error</code> En caso de un error inesperado en la capa de negocio<br>
	 * <code>sdii_CODE_params_null_or_missed</code> En caso que no se encuentren los parámetros necesarios para la ejecución de la operación<br>
	 * @author waguilera
	 */
	@WebMethod(operationName="getAllWarehouseTypesActive", action="getAllWarehouseTypesActive", exclude=false)
	public List<WarehouseTypeVO> getAllWarehouseTypesActive() throws BusinessException;
	
	/**
	 * Metodo: Obtiene todos los Warehouse almacenados en la persistencia que esten activos y no sean virtuales
	 * @return lista con los Warehouse correspondientes, una lista vacia en caso que no exista ninguno.
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * a continuación los códigos de error:<br>
     * <code>sdii_CODE_constraint_violation</code> En caso que se viole una restricción definida en la persistencia<br>
     * <code>sdii_CODE_error_data</code> En caso que se viole una regla de datos de la persistencia<br>
     * <code>sdii_CODE_generic_jdbc</code> En caso de error genérico relacionado con el componente de conexión a la base de datos<br>
     * <code>sdii_CODE_jdbc_connection</code> En caso de error al tratar de conectarse con la base de datos<br>
     * <code>sdii_CODE_sql_grammar</code> En caso de error relacionado con la gramática para ejecutar el comando en la base de datos<br>
     * <code>sdii_CODE_error_service</code> En caso de error al tratar de obtener el valor de una propiedad o un error inesperado en la capa de acceso a datos<br>
     * <code>sdii_CODE_unknow_error</code> En caso de un error inesperado en la capa de negocio<br>
	 * <code>sdii_CODE_params_null_or_missed</code> En caso que no se encuentren los parámetros necesarios para la ejecución de la operación<br>
	 * @author wjimenez
	 */
	@WebMethod(operationName="getAllWarehouseTypesActiveAndNotVirtual", action="getAllWarehouseTypesActiveAndNotVirtual", exclude=false)
	public List<WarehouseTypeVO> getAllWarehouseTypesActiveAndNotVirtual() throws BusinessException;
	
	/**
	 * Metodo: Obtiene el Warehouse almacenados en la persistencia que coincida con alguno de los creiterios de busqueda
	 * @return lista con los Warehouse existentes, una lista vacia en caso que no exista ninguno.
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * a continuación los códigos de error:<br>
     * <code>sdii_CODE_constraint_violation</code> En caso que se viole una restricción definida en la persistencia<br>
     * <code>sdii_CODE_error_data</code> En caso que se viole una regla de datos de la persistencia<br>
     * <code>sdii_CODE_generic_jdbc</code> En caso de error genérico relacionado con el componente de conexión a la base de datos<br>
     * <code>sdii_CODE_jdbc_connection</code> En caso de error al tratar de conectarse con la base de datos<br>
     * <code>sdii_CODE_sql_grammar</code> En caso de error relacionado con la gramática para ejecutar el comando en la base de datos<br>
     * <code>sdii_CODE_error_service</code> En caso de error al tratar de obtener el valor de una propiedad o un error inesperado en la capa de acceso a datos<br>
     * <code>sdii_CODE_unknow_error</code> En caso de un error inesperado en la capa de negocio<br>
	 * <code>sdii_CODE_params_null_or_missed</code> En caso que no se encuentren los parámetros necesarios para la ejecución de la operación<br>
	 * @author waguilera
	 */
	@WebMethod(operationName="getWarehouseByDealerIDIBSCodeDocumentCustomer", action="getWarehouseByDealerIDIBSCodeDocumentCustomer", exclude=false)
	public WarehouseVO getWarehouseByDealerIDIBSCodeDocumentCustomer(@WebParam(name="warehouseId") Long warehouseId,@WebParam(name="codeIBS") String codeIBS, @WebParam(name="documentCustomer")String  documentCustomer) throws BusinessException;
	
	/**
	 * Metodo: Obtiene los elementos de un cliente, con la posibilidad de filtrar por el serial
	 * @param customerId identificador del cliente
	 * @param serial serial del elemento que se desea buscar (opcional)
	 * @param startDate fecha de inicio para la búsqueda por fecha de movimiento del elemento al sitio del cliente
	 * @param endDate fecha fin para la búsqueda por fecha de movimiento del elemento al sitio del cliente
	 * @return listado de elementos en bodega del cliente
	 * @throws BusinessException
	 * @author wjimenez
	 */
	@WebMethod(operationName="getWarehouseElementsByCustomerIdSerialAndDatesRange", action="getWarehouseElementsByCustomerIdSerialAndDatesRange")
	public CustomerElementsResponse getWarehouseElementsByCustomerIdSerialAndDatesRange(
			@WebParam(name="request")WareHouseElementClientFilterRequestDTO request, @WebParam(name="reqCollInfo")RequestCollectionInfo reqCollInfo)
			throws BusinessException;

	/**
	 * 
	 * Metodo: Método util para saber si un elemento a bodega de transito.
	 * @param elementId
	 * @param whType
	 * @return
	 * @throws DAOServiceException
	 * @throws DAOSQLException <tipo> <descripcion>
	 * @author
	 * @throws BusinessException 
	 */
	@WebMethod(operationName="getWhElementByElementIdAndNotWhType", action="getWhElementByElementIdAndNotWhType")
	public List<WarehouseElement> getWhElementByElementIdAndNotWhType(@WebParam(name = "elementId") Long elementId,
			@WebParam(name = "whType") String whType) throws DAOServiceException, DAOSQLException, BusinessException;
	
	

	/**
	 * Metodo: Obtiene todos los Warehouse almacenados en la persistencia que esten activos
	 * @return lista con los Warehouse existentes, una lista vacia en caso que no exista ninguno.
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * a continuación los códigos de error:<br>
     * <code>sdii_CODE_constraint_violation</code> En caso que se viole una restricción definida en la persistencia<br>
     * <code>sdii_CODE_error_data</code> En caso que se viole una regla de datos de la persistencia<br>
     * <code>sdii_CODE_generic_jdbc</code> En caso de error genérico relacionado con el componente de conexión a la base de datos<br>
     * <code>sdii_CODE_jdbc_connection</code> En caso de error al tratar de conectarse con la base de datos<br>
     * <code>sdii_CODE_sql_grammar</code> En caso de error relacionado con la gramática para ejecutar el comando en la base de datos<br>
     * <code>sdii_CODE_error_service</code> En caso de error al tratar de obtener el valor de una propiedad o un error inesperado en la capa de acceso a datos<br>
     * <code>sdii_CODE_unknow_error</code> En caso de un error inesperado en la capa de negocio<br>
	 * <code>sdii_CODE_params_null_or_missed</code> En caso que no se encuentren los parámetros necesarios para la ejecución de la operación<br>
	 * @author waguilera
	 */
	@WebMethod(operationName="getAllWarehouseTypesByDealerID", action="getAllWarehouseTypesByDealerID", exclude=false)
	public List<WarehouseTypeVO> getAllWarehouseTypesByDealerID(@WebParam(name="dealerId")Long dealerId) throws BusinessException;
	
	/**
	 * 
	 * Metodo: Obtiene una lista Warehouse almacenados en la persistencia
	 * @param dealerID opcional. identificador del dealer por el que se quiere hacer la búsqueda. Solo tendrá efecto si se indica searchSingleDealer = true
	 * @param countryID identificador del país por el que se quiere buscar
	 * @param userId identificador del usuario que solicita la operación para poder filtrar de acuerdo al tipo de rol
	 * @param searchSourceWh indica si se debe buscar bodegas de origen. Si es falso, se buscan bodegas destino
	 * @return listado de bodegas origen o destino pertienentes para el usuario que consulta
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * a continuación los códigos de error:<br>
     * <code>sdii_CODE_constraint_violation</code> En caso que se viole una restricción definida en la persistencia<br>
     * <code>sdii_CODE_error_data</code> En caso que se viole una regla de datos de la persistencia<br>
     * <code>sdii_CODE_generic_jdbc</code> En caso de error genérico relacionado con el componente de conexión a la base de datos<br>
     * <code>sdii_CODE_jdbc_connection</code> En caso de error al tratar de conectarse con la base de datos<br>
     * <code>sdii_CODE_sql_grammar</code> En caso de error relacionado con la gramática para ejecutar el comando en la base de datos<br>
     * <code>sdii_CODE_error_service</code> En caso de error al tratar de obtener el valor de una propiedad o un error inesperado en la capa de acceso a datos<br>
     * <code>sdii_CODE_unknow_error</code> En caso de un error inesperado en la capa de negocio<br>
	 * <code>sdii_CODE_params_null_or_missed</code> En caso que no se encuentren los parámetros necesarios para la ejecución de la operación<br>
	 * @author wjimenez
	 */
	@WebMethod(operationName="getWarehouseByDealerIdCountryIdAndUserId", action="getWarehouseByDealerIdCountryIdAndUserId", exclude=false)
	public List<WarehouseVO> getWarehouseByDealerIdCountryIdAndUserId(
			@WebParam(name="dealerId")Long dealerID, @WebParam(name="countryID")Long countryID,@WebParam(name="userId") Long userId, @WebParam(name="searchSourceWh")boolean searchSourceWh)
			throws BusinessException;
	
	/**
	 * Metodo: Permite obtener una lista de las bodegas segun el dealer y el tipo de bodega
	 * @param dealerId id de dealer para filtrar las bodegas
	 * @param whTypeCode codigo del tipo de bodega
	 * @return Lista de WarehouseVO
	 * @throws BusinessException <tipo> <descripcion>
	 * @author cduarte
	 */
	@WebMethod(operationName="getWarehousesByDealerIdAndWhTypeCodeWithBranch", action="getWarehousesByDealerIdAndWhTypeCodeWithBranch", exclude=false)
	public List<WarehouseVO> getWarehousesByDealerIdAndWhTypeCodeWithBranch(@WebParam(name="dealerId")Long dealerId,@WebParam(name="whTypeCode")String whTypeCode,@WebParam(name="withBranch")boolean withBranch)  throws BusinessException;

	/**
	 * Metodo: obtiene el elemento serializado o no serializado y valida que el objeto no se encuentre
	 * en una inconsistencia abierta de tipo mas elementos físicos dentro de la remisión
	 * @param refId
	 * @param warehouseId
	 * @param serial
	 * @param elementTypeId
	 * @param quantity
	 * @return
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author wjimenez
	 */
	@WebMethod(operationName="getWarehouseElementValidatingExistenceInMoreElementsRefInc", action="getWarehouseElementValidatingExistenceInMoreElementsRefInc", exclude=false)
	public WarehouseElementVO getWarehouseElementValidatingExistenceInMoreElementsRefInc(
			@WebParam(name="refId")Long refId, @WebParam(name="warehouseId") Long warehouseId, @WebParam(name="serial")String serial, @WebParam(name="elementTypeId")Long elementTypeId,
			@WebParam(name="quantity")Double quantity,@WebParam(name="countryId") Long countryId) throws BusinessException;
	
	/**
	 * 
	 * Metodo: Operación encargada de consultar elementos serializados en estado LAST según los filtros enviados
	 * @param wareHouseRequestDTO
	 * @return
	 * @throws BusinessException <tipo> <descripcion>
	 * @author
	 */	
	@WebMethod(operationName="getSerializedElementsLastByCriteria", action="getSerializedElementsLastByCriteria", exclude=false)
	public WareHouseElementResponse getSerializedElementsLastByCriteria(
		   @WebParam(name="wareHouseRequestDTO")WareHouseRequestDTO wareHouseRequestDTO) throws BusinessException;
	
	/**
	 * 
	 * Metodo: Operación encargada de consultar elementos no serializados en estado LAST según los filtros enviados
	 * @param wareHouseRequestDTO
	 * @return
	 * @throws BusinessException <tipo> <descripcion>
	 * @author
	 */
	@WebMethod(operationName="getNotSerializedElementsLastByCriteria", action="getNotSerializedElementsLastByCriteria", exclude=false)
	public WareHouseElementResponse getNotSerializedElementsLastByCriteria(
		   @WebParam(name="wareHouseRequestDTO")WareHouseRequestDTO wareHouseRequestDTO ) throws BusinessException;
	
	/**
	 * Metodo: permite consultar todos los documentClass
	 * @return
	 * @throws BusinessException <tipo> <descripcion>
	 * @author
	 */
	@WebMethod(operationName="getAllDocumentClass", action="getAllDocumentClass")
	public List<DocumentClassVO> getAllDocumentClass() throws BusinessException;
	
	
	
	/**
	 * Metodo: Obtiene todos los WarehouseElement que tengan la fecha de salida en null y cuyo estado de registro sea 'U' es decir último dados los criterios de filtrado así:<br>
	 * Debe venir especificado por lo menos un criterio de filtro<br>
	 * Si el campo warehouseId, dealerId, branchDealerId y crewId vienen especificados solo se consultará por warehouseId, los otros tres campos no se tendrán en cuenta<br> 
	 * Si el campo dealerId y branchDealerId vienen especificados solo se consultará por sucursal es decir por branchDealerId<br>
	 * Si el campo crewId, dealerId, branchDealerId vienen especificados solo se consultará por crewId y los otros dos filtros no se tendrán en cuenta<br>
	 * @param RequestCollectionInfo requestCollInfo, parametros para la paginacion
	 * @param isSerialize boolean Indica si ese consulta elemento serializado o no serializado
	 * @return WareHouseElementResponse, lista con los WarehouseElement que se encuentren aplicando el filtro, una lista vacia en caso que no exista ninguno con los criterios de filtro especificados.
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * a continuación los códigos de error:<br>
     * <code>sdii_CODE_constraint_violation</code> En caso que se viole una restricción definida en la persistencia<br>
     * <code>sdii_CODE_error_data</code> En caso que se viole una regla de datos de la persistencia<br>
     * <code>sdii_CODE_generic_jdbc</code> En caso de error genérico relacionado con el componente de conexión a la base de datos<br>
     * <code>sdii_CODE_jdbc_connection</code> En caso de error al tratar de conectarse con la base de datos<br>
     * <code>sdii_CODE_sql_grammar</code> En caso de error relacionado con la gramática para ejecutar el comando en la base de datos<br>
     * <code>sdii_CODE_error_service</code> En caso de error al tratar de obtener el valor de una propiedad o un error inesperado en la capa de acceso a datos<br>
     * <code>sdii_CODE_unknow_error</code> En caso de un error inesperado en la capa de negocio<br>
	 * <code>sdii_CODE_params_null_or_missed</code> En caso que no se encuentren los parámetros necesarios para la ejecución de la operación<br>
	 * @author waguilera
	 */
	@WebMethod(operationName="getWarehouseElementsByFiltersAndIsSerializedLast", action="getWarehouseElementsByFiltersAndIsSerialized")
	public WareHouseElementResponse getWarehouseElementsByFiltersAndIsSerializedLast(
			@WebParam(name="filterSerializedElement") FilterSerializedElementDTO filterSerializedElement,
			@WebParam(name="requestCollInfo") RequestCollectionInfo requestCollInfo) throws BusinessException;
	
}
