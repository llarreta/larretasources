package co.com.directv.sdii.persistence.dao.stock;

import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.model.dto.WarehouseInfoResponseDTO;
import co.com.directv.sdii.model.dto.WarehouseInfoResponseDetailDTO;
import co.com.directv.sdii.model.pojo.Crew;
import co.com.directv.sdii.model.pojo.Dealer;
import co.com.directv.sdii.model.pojo.Warehouse;
import co.com.directv.sdii.model.pojo.WarehouseType;
import co.com.directv.sdii.model.pojo.collection.RequestCollectionInfo;
import co.com.directv.sdii.model.pojo.collection.WareHouseResponse;
import co.com.directv.sdii.model.vo.WarehouseVO;

/**
 * 
 * Interface Local para la gestión del CRUD de la
 * Entidad Warehouse
 * 
 * Fecha de Creación: Mar 8, 2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@Local
public interface WarehouseDAOLocal {

	/**
	 * Metodo:  persiste la información de un Warehouse
	 * @param obj objeto que encapsula la información de un Warehouse
	 * @throws DAOServiceException en caso de error al ejecutar la creación de Warehouse
	 * @throws DAOSQLException en caso de error al ejecutar la creación de Warehouse
	 * @author gfandino
	 */
	public Warehouse createWarehouse(Warehouse obj) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: actualiza la información de un Warehouse
	 * @param obj objeto que encapsula la información de un Warehouse
	 * @throws DAOServiceException en caso de error al ejecutar la actualización de Warehouse
	 * @throws DAOSQLException en caso de error al ejecutar la actualización de Warehouse
	 * @author gfandino
	 */
	public Warehouse updateWarehouse(Warehouse obj) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: Borra de la persistencia la información de un Warehouse
	 * @param obj información del Warehouse a ser borrado
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la eliminación de Warehouse
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la eliminación de Warehouse
	 * @author gfandino
	 */
	public void deleteWarehouse(Warehouse obj) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: Obtiene la información de un Warehouse por su identificador
	 * @param id identificador del Warehouse a ser consultado
	 * @return objeto con la información del Warehouse dado su identificador, nulo en caso que no se encuentre
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la consulta de Warehouse por ID
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la consulta de Warehouse por ID
	 * @author gfandino
	 */
	public Warehouse getWarehouseByID(Long id) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: Obtiene las bodegas por el crewId
	 * @author Leonardo Cardozo Cadavid - lcardozo@intergrupo.com
	 * @param crewId
	 * @return List<Warehouse>
	 * @throws DAOServiceException
	 * @throws DAOSQLException
	 */
	public List<Warehouse> getWarehousesByCrewId(Long crewId) throws DAOServiceException, DAOSQLException;
	/**
	 * Metodo: Obtiene las bodegas por el dealerId
	 * @author Leonardo Cardozo Cadavid - lcardozo@intergrupo.com
	 * @param dealerId
	 * @return List<Warehouse>
	 * @throws DAOServiceException
	 * @throws DAOSQLException
	 */
	public List<Warehouse> getWarehousesByDealerId(Long dealerId) throws DAOServiceException, DAOSQLException;
	/**
	 * Metodo: Obtiene las bodegas por el customerId
	 * @author Leonardo Cardozo Cadavid - lcardozo@intergrupo.com
	 * @param customerId
	 * @return List<Warehouse>
	 * @throws DAOServiceException
	 * @throws DAOSQLException
	 */
	public List<Warehouse> getWarehousesByCustomerId(Long customerId) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: Obtiene la información de todos los Warehouse almacenados en la persistencia
	 * @return Lista con los Warehouse existentes, una lista vacia en caso que no existan Warehouse en el sistema
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la consulta de todos los  Warehouse
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la consulta de todos los  Warehouse
	 * @author gfandino
	 */
	public List<Warehouse> getAllWarehouses() throws DAOServiceException, DAOSQLException;

	/**
	 * Metodo: Obtiene la información de Warehouse almacenados en la persistencia correspondiente al código
	 * @param code - String código de Warehouse
	 * @param country - Long identificador del pa�s
	 * @return Warehouse correspondiente al código especificado. Nulo en otro caso
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la consulta Warehouse por código
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la consulta Warehouse por código
	 * @author gfandino
	 */
	public Warehouse getWarehouseByCodeAndByCountry(String code,Long country)throws DAOServiceException, DAOSQLException;

	/**
	 * Metodo: Obtiene la información de Warehouse almacenados en la persistencia correspondiente a una cuadrilla y un tipo de bodega
	 * @param crewId - Long cuadrilla a la cula pertenece la bodega
	 * @param whTypeId - Long tipo de bodega a consultar
	 * @return List<Warehouse> Lista de las bodegas del tipo especificado asociadas a la cuadrillla; vacio en otro caso
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la consulta Warehouse por cuadrilla y tipo bodega
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la consulta Warehouse por cuadrilla y tipo bodega
	 * @author gfandino
	 */
	public List<Warehouse> getWhByCrewAndWhType(Long crewId,
			Long whTypeId)throws DAOServiceException, DAOSQLException;

	/**
	 * Metodo: Obtiene las bodegas asociadas a un dealer así como las bodegas que hayan sido
	 * asignadas como destino por algún elemento de la tabla warehouse_elements
	 * @param dealerId identificador del dealer sobre el que se realizará la consulta de bodegas
	 * @param warehouseTypeId identificador del tipo de bodega sobre el que se realizará la consulta
	 * @return Lista de bodegas asociadas al dealer tanto propias como definidas como destino
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la consulta Warehouse por cuadrilla y tipo bodega
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la consulta Warehouse por cuadrilla y tipo bodega
	 * @author jjimenezh
	 */
	public List<Warehouse> getWarehousesByDealerIdOwnAndRelated(Long dealerId, Long warehouseTypeId)throws DAOServiceException, DAOSQLException;
	
	
	/** 
	 * Metodo: Consulta las Warehouse por identificador de Dealer y Crew
	 * @param dealerId Identificador del Dealer
	 * @param crewId Identificador del Crew
	 * @return List<Warehouse> Lista de warehouse consultadas
	 * @throws DAOServiceException
	 * @throws DAOSQLException 
	 * @author jforero 05/08/2010
	 */
	public List<Warehouse> getWarehousesByDealerIdAndCrewId(Long dealerId, Long crewId) throws DAOServiceException, DAOSQLException ;

	
	/**
	 * Metodo: Obtiene las bodegas filtradas por codigo de bodega,codigo de compa�ia,codigo
	 * de sucursal, codigo de cuadrilla y codigo para tipo de bodega
	 * @param wareHouseId identificador de la bodega
	 * @param companyId identificador de la compa�ia
	 * @param branchId identificador de la sucursal
	 * @param crewId identificador de la cuadrilla
	 * @param wareHouseTypeId identificador del tipo de bodega
	 * @param RequestCollectionInfo requestCollInfo, parametros para la paginacion.
	 * @return WareHouseResponse, Lista de bodegas producto filtro
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la consulta Warehouse por cuadrilla y tipo bodega
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la consulta Warehouse por cuadrilla y tipo bodega
	 * @author garciniegas
	 */
	public WareHouseResponse getWarehousesByComplexFilter( Warehouse wareHouseId,Dealer companyId,Dealer branchId,Crew crewId,WarehouseType wareHouseTypeId, RequestCollectionInfo requestCollInfo )throws DAOServiceException, DAOSQLException;

	/**
	 * Metodo: Obtiene las bodegas asociadas al filtro enviado por id de bodega, id de dealer, id de sucursal,
	 * id de cuadrilla, id de tipo de bodega
	 * @param wareHouseId Long ID de la bodega
	 * @param dealerId Long ID de dealer
	 * @param branchId Long ID de la sucursal
	 * @param crewId Long ID de la cuadrilla
	 * @param wareHouseTypeId Long ID del tipo de la bodega
	 * @param RequestCollectionInfo requestCollInfo, parametros para la paginacion.
	 * @return WarehouseElemetStockResponse, List<Warehouse> Lista con las bodegas que cumplen con el filtro
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la consulta Warehouse por id de bodega,
	 * id de dealer, id de sucursal, id de cuadrilla, id de tipo de bodega
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la consulta Warehouse por id de bodega,
	 * id de dealer, id de sucursal, id de cuadrilla, id de tipo de bodega
	 * @author jnova
	 */
	public WareHouseResponse getWarehousesByAdjustNotSerElemCriteria(Long wareHouseId , Long dealerId , Long branchId , Long crewId , Long wareHouseTypeId, RequestCollectionInfo requestCollInfo) throws DAOServiceException, DAOSQLException;

	/**
	 * Metodo: Obtiene la lista de bodegas por país en estado activo
	 * @param countryId identificador del país de la bodega
	 * @param RequestCollectionInfo requestCollInfo, parametros para la paginacion
	 * @return WareHouseResponse, lista con las bodegas del país
	 * @throws DAOServiceException en caso de error al consultar las bodegas del país
	 * @throws DAOSQLException en caso de error al consultar las bodegas del país
	 * @author jjimenezh
	 */
	public WareHouseResponse getWarehousesByCountryId(Long countryId, RequestCollectionInfo requestCollInfo) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: Obtiene la lista de bodegas por país en estado activo y condicion del dealer: vendedor|instalador
	 * @param countryId identificador del país de la bodega
	 * @param RequestCollectionInfo requestCollInfo, parametros para la paginacion
	 * @param isSeller determina si se trata de un dealer vendedor
	 * @param isInstaller determina si se trata de un dealer instalador
	 * @return WarehouseInfoResponseDTO, DTO con el detalle de ubicaciones de dealeres instaladores
	 * @throws DAOServiceException en caso de error al consultar las bodegas del país
	 * @throws DAOSQLException en caso de error al consultar las bodegas del país
	 * @author clopez
	 */
	public WarehouseInfoResponseDTO getWarehousesByCountryIdAndDealerCondition(Long countryId, RequestCollectionInfo requestCollInfo,
			String isSeller, String isInstaller) throws DAOServiceException, DAOSQLException;

	
	/**
	 * Método: Obtiene la lista de bodegas en cualqueir estado
	 * @param countryId - identificador del país de las bodegas
	 * @param requestCollInfo - RequestCollectionInfo información de la paginación
	 * @param code - String con el código de la ubicación ; nulo si no apica 
	 * @return WarehouseInfoResponseDetailDTO con la información paginada
	 * @throws DAOServiceException  en caso de error al consultar las bodegas del país
	 * @throws DAOSQLException en caso de error al consultar las bodegas del país
	 * @author gfandino	
	 */
	public WarehouseInfoResponseDetailDTO getAllWarehousesByCountryId(	Long countryId, 
																		String code, 
																		RequestCollectionInfo requestCollInfo
																	  
    ) throws DAOServiceException, DAOSQLException;

	
	/**
	 * 
  	 * ialessan
	 * junio 2014
	 * Modificacion de servicos de pantalla ubicaciones, cuatro nuevos parametros.
     *
	 * Método: Obtiene una lista de bodegas segun los parametros 
	 * @param countryId - identificador del país de las bodegas
	 * @param warehouseTypeId - identificador del tipo de las bodegas
	 * @param dealerId - identificador de la compania de las bodegas
	 * @param branchId - identificador de la sucursal de las bodegas 
	 * @param crewCodeId - identificador de la cuadrilla las bodegas 
	 * @param requestCollInfo - RequestCollectionInfo información de la paginación
	 * @param code - String con el código de la ubicación ; nulo si no apica 
	 * @return WarehouseInfoResponseDetailDTO con la información paginada
	 * @throws DAOServiceException  en caso de error al consultar las bodegas 
	 * @throws DAOSQLException en caso de error al consultar las bodegas del país
	 * @author ialessan	
	 */
	public WarehouseInfoResponseDetailDTO getWhByWhTypeDealerBranchCrewIds(  Long countryId, 
																				 Long warehouseTypeId, 
																				 Long dealerId, 
																				 Long branchId,
																				 Long crewCodeId , 
																				 RequestCollectionInfo requestCollInfo
	) throws DAOServiceException, DAOSQLException;

	/**
	 * Metodo: Obtiene la bodega de ajuste asociada a un dealer
	 * @param dealerId Long ID del dealer
	 * @return Warehouse Bodega de ajuste asociada al dealer
	 * @throws DAOServiceException En caso que ocurra una excepcion en la consulta de la bodega
	 * @throws DAOSQLException En caso que ocurra una excepcion en la consulta de la bodega
	 */
	public Warehouse getAdjustWarehouseByDealerId(Long dealerId) throws DAOServiceException, DAOSQLException;

	/**
	 * Metodo: Obtiene la ubicacion especifica para determinado dealerm del tipo consultado y que esté activa
	 * @param dealerId identificador del dealer dueño de la bodega
	 * @param whTypeCode código del tipo de bodega
	 * @return Lista con las bodegas del dealer y del tipo especificados
	 * @throws DAOServiceException en caso de error al ejecutar la operación
	 * @throws DAOSQLException en caso de error al ejecutar la operación
	 * @author jjimenezh
	 */
	public Warehouse getWarehousesByDealerIdAndWhTypeCode(Long dealerId,
			String whTypeCode)throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: Obtiene la lista de bodegas de acuerdo al codigo del dealer y el código de tipo
	 * de bodega
	 * @param dealerId identificador del dealer dueño de la bodega
	 * @param whTypeCode código del tipo de bodega
	 * @return Lista con las bodegas del dealer y del tipo especificados
	 * @throws DAOServiceException en caso de error al ejecutar la operación
	 * @throws DAOSQLException en caso de error al ejecutar la operación
	 * @author jnova
	 */
	public List<Warehouse> getWarehousesByDealerCodeAndWhTypeCode(Long dealerCode,
			String whTypeCode)throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: Obtiene la lista de bodegas de acuerdo al identificador del dealer y el código de tipo
	 * de bodega
	 * @param dealerId identificador del dealer dueño de la bodega
	 * @param whTypeCode código del tipo de bodega
	 * @param withBranch badera que indica si se buscan tambien sucursales
	 * @return Lista con las bodegas del dealer y del tipo especificados
	 * @throws DAOServiceException en caso de error al ejecutar la operación
	 * @throws DAOSQLException en caso de error al ejecutar la operación
	 * @author cduarte
	 */
	public List<Warehouse> getWarehousesByDealerIdAndWhTypeCodeWithBranch(Long dealerId,
            String whTypeCode,
            boolean withBranch) throws DAOServiceException, DAOSQLException;

	/**
	 * Metodo: Consulta las bodegas asociadas a un tipo de dealer por país
	 * @param dealerType - String código del tipo de dealer
	 * @param country - Long identificador del país a consultar
	 * @return List<Warehouse>
	 * @throws DAOServiceException en caso de error al ejecutar la consulta por tipo de dealer y país
	 * @throws DAOSQLException en caso de error al ejecutar la por tipo de dealer y país
	 * @author gfandino
	 */
	public List<Warehouse> getWhByDealerTypeAndCountry(String dealerType, Long country)throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: Obtiene la bodega de devolucion de una cuadrilla
	 * @param crewId Id de la cuadrilla de la que se esta buscando la bodega
	 * @param countryId Id del pais donde se va a buscar la bodega
	 * @return Warehouse
	 * @throws DAOServiceException
	 * @throws DAOSQLException
	 * @author jnova
	 */
	public Warehouse getCrewWarehouseByTypeAndCountry(Long crewId, String warehouseType, Long countryId) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: Obtiene la bodega asociada a un cliente
	 * @param customerId Id del cliente que se desea obtener la bodega
	 * @param countryId Id del pais donde se va a buscar la bodega
	 * @return Warehouse
	 * @throws DAOServiceException
	 * @throws DAOSQLException
	 * @author jnova
	 */
	public Warehouse getCustomerWarehouseByCountry(Long customerId, Long countryId) throws DAOServiceException, DAOSQLException;
	
	/**
	 * 
	 * Metodo: Obtiene la bodega asociada a un cliente por codigo ibs de cliente y codigo de pais
	 * @param customerIbsCode
	 * @param countryId
	 * @return Warehouse
	 * @throws DAOServiceException
	 * @throws DAOSQLException <tipo> <descripcion>
	 * @author jnova
	 */
	public Warehouse getCustomerWarehouseByCountry(String customerIbsCode, String countryCode) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: Obtiene la lista de bodegas de acuerdo al identificador del dealer y el código de tipo
	 * de bodega y el pais del dealer
	 * CU INV 04
	 * @param dealerId identificador del dealer dueño de la bodega
	 * @param Long country, id del pais
	 * @param whTypeCode código del tipo de bodega
	 * @return Lista con las bodegas del dealer y del tipo especificados
	 * @throws DAOServiceException en caso de error al ejecutar la operación
	 * @throws DAOSQLException en caso de error al ejecutar la operación
	 * @author jalopez
	 */
	public List<Warehouse> getWhByDealerTypeAndWhTypeCodeAndCountry(String dealerType, Long country, String whTypeCode) throws DAOServiceException, DAOSQLException;

	/**
	 * Método: Obtiene la lista de bodegas del país que se encuentran en estado determinado
	 * @param countryId - Long Código del país
	 * @param dealerId - Long Identificador del dealer (-1 si no aplica)
	 * @param warehouseStatus - String código del estado
	 * @return List<Warehouse> que están asociadas al país y se encuentran en estado determinado
	 * @throws DAOServiceException en caso de error consutando las bodegas por país y estado
	 * @throws DAOSQLException en caso de error consutando las bodegas por país y estado
	 * @author gfandino
	 * 
	 */
	public List<Warehouse> getWarehousesByCountryIdAndStatus(Long countryId,
			Long dealerId, String warehouseStatus)throws DAOServiceException, DAOSQLException;
	
	
	
	/**
	 * Método: Obtiene la lista de bodegas del país que se encuentran en estado determinado
	 * @param countryId - Long Código del país
	 * @param warehouseStatus - String código del estado
	 * @param requestCollInfo - RequestCollectionInfo manejo de paginación
	 * @return WareheouseResponse con la información de resultado de la consulta
	 * @throws DAOServiceException en caso de error consutando las bodegas por país y estado
	 * @throws DAOSQLException en caso de error consutando las bodegas por país y estado
	 * @author gfandino
	 */
	public WareHouseResponse getWarehousesByCountryIdAndStatus(Long countryId,
			String warehouseStatus,RequestCollectionInfo requestCollInfo)throws DAOServiceException, DAOSQLException;

	/**
	 * Método: Retorna la ubicación de transito de la ubicación ingresada
	 * @param whSource - WarehouseVO ubicación de la cual se obtendrá la ubicación transito
	 * @return Warehouse correspondiente a la ubicación ingresada
	 * @throws DAOServiceException en caso de error consultando la ubicación de transito
	 * @throws DAOSQLException en caso de error consultando la ubicación de transito
	 * @author gfandino
	 */
	public Warehouse getWhTransitDealerOrCrew(WarehouseVO whSource)throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: Metodo: Obtiene la información de las bodegas que corresponden a una cuadrilla o a un dealer y un tipo de bodega
	 * @param dealerId - Long identificador del dealer
	 * @param crewId - Long cuadrilla a la cual se le consultarán las bodegas
	 * @param whTypeId - Long Tipo de cuadrilla que se va a consultar
	 * @return List<Warehouse> Lista de las bodegas del tipo especificado asociadas a la cuadrillla o a un dealer; vacio en otro caso
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la consulta Warehouse por cuadrilla y tipo bodega
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la consulta Warehouse por cuadrilla y tipo bodega
	 * @author jnova
	 */
	public List<Warehouse> getWhByCrewAndDealerAndWhType(Long dealerId, Long crewId, Long whTypeId)throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: Metodo: Obtiene la información de las bodegas que corresponden a una cuadrilla o a un dealer y un tipo de bodega
	 * @param dealerId - Long identificador del dealer
	 * @param crewId - Long cuadrilla a la cual se le consultarán las bodegas
	 * @param whTypeCode - Code Tipo de bodega que se va a consultar
	 * @return List<Warehouse> Lista de las bodegas del tipo especificado asociadas a la cuadrillla o a un dealer; vacio en otro caso
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la consulta Warehouse por cuadrilla y tipo bodega
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la consulta Warehouse por cuadrilla y tipo bodega
	 * @author jnova
	 */
	public List<Warehouse> getWhByCrewAndDealerAndWhType(Long dealerId, Long crewId,String whTypeCode) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: Metodo: Obtiene la información de las bodegas que corresponden a una cuadrilla o a un dealer y un tipo de bodega
	 * @param dealerId - Long identificador del dealer
	 * @param crewId - Long cuadrilla a la cual se le consultarán las bodegas
	 * @param whTypeId - Long Tipo de cuadrilla que se va a consultar
	 * @return List<Warehouse> Lista de las bodegas del tipo especificado asociadas a la cuadrillla o a un dealer; vacio en otro caso
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la consulta Warehouse por cuadrilla y tipo bodega
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la consulta Warehouse por cuadrilla y tipo bodega
	 * @author jnova
	 */
	public List<Warehouse> getWhByCrewAndDealerAndWhTypeNotVirtual(Long dealerId, Long crewId, Long whTypeId)throws DAOServiceException, DAOSQLException;

	/**
	 * 
	 * Metodo: Encargado de consultar las bodegas segun la cuadrilla y el tipo de Bodega.
	 * @param crewId
	 * @param whTypeCode
	 * @return
	 * @throws DAOServiceException
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la consulta Warehouse por cuadrilla y tipo bodega
	 * @author hcorredor
	 */
	public List<Warehouse> getWhByCrewAndWhTypeCode(Long crewId, String whTypeCode) throws DAOServiceException, DAOSQLException;

	/**
	 * Metodo: Consulta las bodegas por dealer, pais y tipo de bodega
	 * @param dealerId
	 * @param country
	 * @param whTypeCode
	 * @return List<Warehouse> Lista de las bodegas que cumplen con los filtros especificados; vacio en otro caso
	 * @throws DAOServiceException en caso deun error al ejecutar la operacion
	 * @throws DAOSQLException en caso deun error al ejecutar la operacion
	 * @author jnova
	 */
	public List<Warehouse> getWhByDealerIdAndWhTypeCodeAndCountryCode(Long dealerId, String countryCode, String whTypeCode) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: Consulta las bodegas por dealer, pais y tipo de bodega
	 * @param dealerId
	 * @param country
	 * @param whTypeCode
	 * @return List<Warehouse> Lista de las bodegas que cumplen con los filtros especificados; vacio en otro caso
	 * @throws DAOServiceException en caso deun error al ejecutar la operacion
	 * @throws DAOSQLException en caso deun error al ejecutar la operacion
	 * @author wjimenez
	 */
	public List<Warehouse> getWhByDealerIdAndWhTypeCodeAndCountry(Long dealerId, Long country, String whTypeCode) throws DAOServiceException, DAOSQLException;
	
	/**
	 * 
	 * Metodo: Retorna una bodega del dealer segun el tipo.
	 * @param dealerId Long
	 * @param String warehouseType
	 * @return Warehouse
	 * @throws DAOServiceException
	 * @throws DAOSQLException <tipo> <descripcion>
	 * @author jalopez
	 */
	public Warehouse getWarehouseTypeByDealerId(Long dealerId,String warehouseType) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: Obtiene la información de un Warehouse por su código
	 * @param id identificador del Warehouse a ser consultado
	 * @return objeto con la información del Warehouse dado su identificador, nulo en caso que no se encuentre
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la consulta de Warehouse por ID
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la consulta de Warehouse por ID
	 * @author waguilera
	 */
	public Warehouse getWarehouseBySerialCode(String warehouseCode) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: Obtiene el Warehouse almacenados en la persistencia que coincida con alguno de los creiterios de busqueda
	 * @param id identificador del Warehouse a ser consultado
	 * @return objeto con la información del Warehouse dado su identificador, nulo en caso que no se encuentre
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la consulta de Warehouse por ID
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la consulta de Warehouse por ID
	 * @author 
	 */
	public Warehouse getWarehouseByDealerIDIBSCodeDocumentCustomer(Long warehouseId, String codeIBS, String  documentCustomer) throws DAOServiceException, DAOSQLException;
	
	
	/**
	 * Metodo: Obtiene una lista de los id de los Warehouse almacenados en la persistencia
	 * @param dealerID opcional. identificador del dealer por el que se quiere hacer la búsqueda. Solo tendrá efecto si se indica searchSingleDealer = true
	 * @param countryID identificador del país por el que se quiere buscar
	 * @param searchSingleDealer indica si se debe usar el dealerID para filtrar la búsqueda
	 * @param includeCrews indica si se deben retornar las bodegas con cuadrillas
	 * @param includeBranches indica si se deben retornar las sucursales
	 * @return listado de bodegas que coinciden con los filtros indicados
	 * @throws DAOServiceException
	 * @throws DAOSQLException
	 * @author wjimenez
	 */
	public List<Long> getWarehousesByCountryIdAndOptionalDealerId(
			Long dealerID, Long countryID, boolean searchSingleDealer, boolean includeCrews, boolean includeBranches, boolean includeCrewsForASingleDealer) throws DAOServiceException,
			DAOSQLException;
	
	
	/**
	 * 
	 * Metodo: Obtiene una ubicación en funcion al código enviando
	 * y los parametros
	 * @param codeWarehouse
	 * @param countryID
	 * @return
	 * @throws DAOServiceException
	 * @throws DAOSQLException <tipo> <descripcion>
	 * @author
	 */
	public Warehouse getWarehouseByCountryIdAndCodeAndNotVirtualAndNotCustomer(
			String codeWarehouse, Long countryID) throws DAOServiceException,
			DAOSQLException;
	
	/**
	 * Metodo: Permite obtener la cantidad de bodegas disponibles de las crew
	 * para validar antes de mover de las cuadrillas a las bodegas
	 * @param dealerId
	 * @param whTypeCode
	 * @return
	 * @throws DAOServiceException
	 * @throws DAOSQLException <tipo> <descripcion>
	 * @author cduarte
	 */
	public Long getQuantityWarehouseCrewByDealersAndWarehouseType(List<Long> dealerId,List<String> whTypeCode) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: Permite obtener la cantidad de bodega disponibles 
	 * para validar antes de mover de las cuadrillas a las bodegas
	 * @param dealerId
	 * @param whTypeCode
	 * @return
	 * @throws DAOServiceException
	 * @throws DAOSQLException <tipo> <descripcion>
	 * @author cduarte
	 */
	public Long getQuantityWarehouseDealerByDealersAndWarehouseType(List<Long> dealerId,List<String> whTypeCode) throws DAOServiceException, DAOSQLException;	
	
	/**
	 * 
	 * @param code El codigo que se quiere verificar
	 * @return verifica si ya exdiste el WH_CODE ya existe antes de ingresarlo
	 * @throws DAOServiceException
	 * @throws DAOSQLException
	 */
	public boolean existWhCode(String code) throws DAOServiceException, DAOSQLException ;
	
	
	/**
	 * Trae todas las bodegas no virtuales para una cuadrilla
	 * @param crewId
	 * @return
	 * @throws DAOServiceException
	 * @throws DAOSQLException
	 */
	public List<Warehouse> getNoVirtualWarehousesByCrewId(Long crewId) throws DAOServiceException, DAOSQLException ;
	
	/**
	 * Método encargado de obtener los warehouse a partir de los identificadores,
	 * genera el nombre de la ubicación en la misma consulta
	 * @param warehouseIds
	 * @param countryID
	 * @return
	 * @throws DAOServiceException
	 * @throws DAOSQLException
	 * @author waguilera
	 */
	public List<WarehouseVO> getWarehouseByIds(List<Long> warehouseIds,Long countryID) throws DAOServiceException, DAOSQLException;
	
	public Warehouse getWarehouseByCountryIdAndCodeAndNotVirtual(
			String codeWarehouse, Long countryID) throws DAOServiceException,
			DAOSQLException ;

	/**
	 * Método encargado de obtener los warehouse a partir de un wh_code 
	 * @param whCode
	 * @param country
	 * @return lista de bodegas que coinciden con el wh_code informado
	 * @throws DAOServiceException
	 * @throws DAOSQLException
	 * @author clopez
	 */
    public List<Warehouse> getWarehouseByWhCode(String whCode, Long country) throws DAOServiceException, DAOSQLException ;

}

