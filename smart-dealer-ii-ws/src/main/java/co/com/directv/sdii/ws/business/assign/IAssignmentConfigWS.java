/**
 * Creado 10/06/2011 15:39:31
 */
package co.com.directv.sdii.ws.business.assign;

import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.model.dto.collection.RequestCollectionInfoDTO;
import co.com.directv.sdii.model.pojo.collection.DealerBuildingResponse;
import co.com.directv.sdii.model.pojo.collection.DealerCoverageResponse;
import co.com.directv.sdii.model.pojo.collection.PostalCodeResponse;
import co.com.directv.sdii.model.pojo.collection.RequestCollectionInfo;
import co.com.directv.sdii.model.pojo.collection.SaleChannelResponse;
import co.com.directv.sdii.model.vo.CoverageTypeVO;
import co.com.directv.sdii.model.vo.DealerBuldingVO;
import co.com.directv.sdii.model.vo.DealerCoverageVO;
import co.com.directv.sdii.model.vo.DealerCustomerTypesWoutPcVO;
import co.com.directv.sdii.model.vo.DealerDetailVO;
import co.com.directv.sdii.model.vo.DealerServiceSubCategoryVO;
import co.com.directv.sdii.model.vo.DealerServiceSubCategoryWithPcVO;
import co.com.directv.sdii.model.vo.DealerWeekCapacityVO;
import co.com.directv.sdii.model.vo.KpiCalculationTypeVO;
import co.com.directv.sdii.model.vo.KpiConfigurationVO;
import co.com.directv.sdii.model.vo.SaleChanelVO;
import co.com.directv.sdii.model.vo.ServiceAreWarrantyVO;
import co.com.directv.sdii.model.vo.ServiceHourVO;
import co.com.directv.sdii.model.vo.ServiceSuperCategoryVO;
import co.com.directv.sdii.model.vo.ServiceTypeVO;
import co.com.directv.sdii.model.vo.ServiceTypeWarrantyVO;
import co.com.directv.sdii.model.vo.SkillConfigurationVO;
import co.com.directv.sdii.model.vo.SkillEvaluationTypeVO;
import co.com.directv.sdii.model.vo.SkillModeTypeVO;
import co.com.directv.sdii.ws.model.dto.GetSaleChannelsByFiltersRequestDTO;

/**
 * Define las operaciones para el módulo de configuración del asignador
 * 
 * Fecha de Creación: 10/06/2011
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@WebService(name="AssignmentConfigWS",targetNamespace="http://assign.business.ws.sdii.directv.com.co/")
public interface IAssignmentConfigWS {

	/**
	 * Metodo: Obtiene la información de los canales de venta por los filtros especificados
	 * @param request Objeto que encapsula la información de los filtros
	 * @param requestCollectionInfo Encapsula la información de la petición de paginación
	 * @return SaleChannelResponse Objeto que encapsula en la lista de vos la información de los canales de venta que coinciden con los filtros seleccionados
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
	@WebMethod(operationName="getSaleChannelsByFilters", action="getSaleChannelsByFilters", exclude = false)
	public SaleChannelResponse getSaleChannelsByFilters(
			@WebParam(name="request") GetSaleChannelsByFiltersRequestDTO request, 
			@WebParam(name="requestCollectionInfo") RequestCollectionInfoDTO requestCollectionInfo)throws BusinessException;
	
	/**
	 * Metodo: Obtiene la información de un canal de venta dado su identificador
	 * @param saleChannelId Identificador del canal de venta
	 * @return SaleChanelVO Objeto que encapsula la información del canal de venta dado su identificador, nulo en caso que no exista
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
	@WebMethod(operationName="getSaleChannelById", action="getSaleChannelById", exclude = false)
	public SaleChanelVO getSaleChannelById(@WebParam(name="saleChannelId") Long saleChannelId)throws BusinessException;
	
	/**
	 * Metodo: borra la información de un canal de venta dado su identificador
	 * @param saleChannelId Identificador del canal de venta
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
	@WebMethod(operationName="deleteSaleChannelById", action="deleteSaleChannelById", exclude = false)
	public void deleteSaleChannelById(@WebParam(name="saleChannelId") Long saleChannelId)throws BusinessException;
	
	/**
	 * Metodo: crea la información de un canal de venta
	 * @param saleChannel objeto que encapsula la información del canal de venta, se requiere que la lista de instaladores y vendedores esten pobladas
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
	@WebMethod(operationName="createSaleChannel", action="createSaleChannel", exclude = false)
	public void createSaleChannel(@WebParam(name = "saleChannel") SaleChanelVO saleChannel)throws BusinessException;

	/**
	 * Metodo: actualiza la información de un canal de venta
	 * @param saleChannel objeto que encapsula la información del canal de venta, se requiere que la lista de instaladores y vendedores esten pobladas
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
	@WebMethod(operationName="updateSaleChannel", action="updateSaleChannel", exclude = false)
	public void updateSaleChannel(@WebParam(name = "saleChannel") SaleChanelVO saleChannel)throws BusinessException;
	
	
	
	/**
	 * Metodo: Obtiene todos los Building almacenados en la persistencia
	 * @param countryId identificador del país donde se encuentran ubicados los edificios
	 * @param postalCodeId identificador del código postal donde se encuentran ubicados los edificios
	 * @return lista con los Building existentes, una lista vacia en caso que no exista ninguno.
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
	@WebMethod(operationName="getAllServiceSuperCategories", action="getAllServiceSuperCategories", exclude = false)
	public List<ServiceSuperCategoryVO> getAllServiceSuperCategories()throws BusinessException;
	
	
	/**
	 * Metodo: Lista las configuraciones de KPI independientemente del estado para el país y
	 * la supercategoría especificada.
	 * Para los KPIs que no tienen configuración para algun tipo de zona, se incluye una configuración por defecto en estado
	 * inactivo.
	 * @param countryId identificador del país
	 * @param superCategoryId identificador de la supercategoría
	 * @return List<KpiConfigurationVO> lustado de las configuraciones de KPI que coinciden con los parámetros especificados
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author wjimenez
	 */
	@WebMethod(operationName="getKPIConfigurationsByCountryIdAndSupercategoryId", action="getKPIConfigurationsByCountryIdAndSupercategoryId", exclude = false)
	public List<KpiConfigurationVO> getKPIConfigurationsByCountryIdAndSupercategoryId(@WebParam(name="countryId")Long countryId, @WebParam(name="superCategoryId")Long superCategoryId) throws BusinessException;
	
	/**
	 * Metodo: persiste los cambios a un grupo de configuraciones de KPIs y reconfigura el job de cálculo de KPIs
	 * para que utilice los nuevos parámetros de ejecución.
	 * La actualización hace parte de una sola transacción, es decir, o se actualizan todos los elementos y se actualiza el job
	 * o no se actualizan ni los registros ni el job.
	 * @param kpiConfigurations listado de configuraciones de KPI que se desean actualizar
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author wjimenez
	 */
	@WebMethod(operationName="updateKPIConfigurations", action="updateKPIConfigurations", exclude = false)
	public void updateKPIConfigurations(@WebParam(name="newKpiConfigurations")List<KpiConfigurationVO> newKpiConfigurations) throws BusinessException;
	
	/**
	 * Metodo: Obtiene la información de todos los KpiCalculationTypeVO almacenados en la persistencia
	 * @return Lista con los KpiCalculationTypeVO existentes, una lista vacia en caso que no existan KpiCalculationTypeVO en el sistema
	 * @throws BusinessException en caso de error al tratar de ejecutar la operación
	 * @author
	 */
	@WebMethod(operationName="getAllKpiCalculationTypes", action="getAllKpiCalculationTypes", exclude = false)
	public List<KpiCalculationTypeVO> getAllKpiCalculationTypes() throws BusinessException;
	
	/**
	 * Metodo: Permite consultar las configuraciones de todas las habilidades filtradas de acuerdo
	 * a los parámetros
	 * @param countryId identificador del país por el que se quiere buscar
	 * @param serviceTypeId tipo de servicio por el que se quiere buscar
	 * @return listado de configuraciones de habilidades
	 * @author wjimenez
	 */
	@WebMethod(operationName="getSkillConfigurationsByCountryIdAndCategoryId", action="getSkillConfigurationsByCountryIdAndCategoryId", exclude = false)
	public List<SkillConfigurationVO> getSkillConfigurationsByCountryIdAndCategoryId(@WebParam(name="countryId")Long countryId, @WebParam(name="serviceTypeId")Long serviceTypeId) throws BusinessException;
	
	/**
	 * Metodo: Permite consultar la información de todos los SkillModeType almacenados en la persistencia
	 * @return List<SkillModeTypeVO> Lista con los SkillModeTypeVO existentes, una lista vacia en caso que no existan SkillModeTypeVO en el sistema
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author
	 */
	@WebMethod(operationName="getAllSkillModeTypes", action="getAllSkillModeTypes", exclude = false)
	public List<SkillModeTypeVO> getAllSkillModeTypes() throws BusinessException;
	
	/**
	 * Metodo: Permite consultar la información de todos los SkillEvaluationTypeVO almacenados en la persistencia
	 * @return List<SkillEvaluationTypeVO> Lista con los SkillEvaluationTypeVO existentes, una lista vacia en caso que no existan SkillEvaluationTypeVO en el sistema
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author
	 */
	@WebMethod(operationName="getAllSkillEvaluationTypes", action="getAllSkillEvaluationTypes", exclude = false)
	public List<SkillEvaluationTypeVO> getAllSkillEvaluationTypes() throws BusinessException;
	
	/**
	 * Metodo: persiste los cambios a un grupo de configuraciones de habilidades
	 * La actualización hace parte de una sola transacción, es decir, o se actualizan todos
	 * o no se actualiza ninguno.
	 * @param skillConfigurations listado de configuraciones de habilidades que se desean actualizar
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author wjimenez
	 */
	@WebMethod(operationName="updateSkillConfigurations", action="updateSkillConfigurations", exclude = false)
	public void updateSkillConfigurations(@WebParam(name="skillConfigurations")List<SkillConfigurationVO> skillConfigurations) throws BusinessException;
	
	/**
	 * Metodo: Obtiene el listado de todos los DealerCoverageVO que están activos y asociados
	 * a un código postal determinado
	 * @param postalCodeId código postal por el que se realizará la búsqueda
	 * @param isSeller determina si es un dealer vendedor
	 * @param isInstaller determina si es un dealer instalador
	 * @return List<DealerCoverageVO>
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author wjimenez
	 */
	@WebMethod(operationName="getDealersCoverageActiveByPostalCode", action="getDealersCoverageActiveByPostalCode", exclude = false)
	public List<DealerCoverageVO> getDealersCoverageActiveByPostalCode(@WebParam(name="postalCode")Long postalCodeId,
			@WebParam(name="isSeller")String isSeller,@WebParam(name="isInstaller")String isInstaller)throws BusinessException;

	/**
	 * Metodo: persiste los cambios realizados sobre un grupo de DealerCoverageVO
	 * @param dealersCoverage
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author wjimenez
	 */
	@WebMethod(operationName="updateDealersCoverage", action="updateDealersCoverage", exclude = false)
	public void updateDealersCoverage(@WebParam(name="dealersCoverage")List<DealerCoverageVO> dealersCoverage) throws BusinessException;

	/**
	 * Metodo: Obtiene todas las microzonas <code>PostalCode</code> en las que tiene
	 * cobertura el dealer especificado
	 * @param dealerId identificador del dealer por el que se realizará la búsqueda
	 * @return PostalCodeResponse listado de microzonas paginado
	 * @throws BusinessException en caso de un error en la ejecución de la operación
	 * @author wjimenez
	 */
	@WebMethod(operationName="getPostalCodesActiveByDealerId", action="getPostalCodesActiveByDealerId", exclude = false)
	public PostalCodeResponse getPostalCodesActiveByDealerId(@WebParam(name="dealerId")Long dealerId,
			@WebParam(name="requestCollectionInfo")RequestCollectionInfo requestCollectionInfo)
			throws BusinessException;

	/**
	 * Metodo: Obtiene todos los edificios para un código postal, y si el edificio ya
	 * tiene un dealer asociado para que lo atienda, se carga la información de este
	 * dealer
	 * @param postalCodeId
	 * @return DealerBuildingResponse
	 * @throws BusinessException en caso de error al tratar de ejecutar la operación
	 * @author wjimenez
	 */
	@WebMethod(operationName="getDealerBuildingsByPostalCodeId", action="getDealerBuildingsByPostalCodeId", exclude = false)
	public DealerBuildingResponse getDealerBuildingsByPostalCodeId(@WebParam(name="postalCodeId")Long postalCodeId,
			@WebParam(name="requestCollectionInfo")RequestCollectionInfo requestCollectionInfo) throws BusinessException;

	/**
	 * Metodo: persiste los cambios realizados sobre un grupo de DealerCoverageVO
	 * @param dealersCoverage
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author wjimenez
	 */
	@WebMethod(operationName="updateDealerBuildings", action="updateDealerBuildings", exclude = false)
	public void updateDealerBuildings(@WebParam(name="dealerBuildings")List<DealerBuldingVO> dealerBuildings) throws BusinessException;

	/**
	 * Metodo: Permite consultar la información de un DealerDetail por su identificador
	 * @param id - Long identificador del DealerDetail a ser consultado
	 * @return objeto con la información del DealerDetailVO dado su identificador, nulo en caso que no se encuentre
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author
	 */
	@WebMethod(operationName="getDealerDetailByDealerId", action="getDealerDetailByDealerId", exclude = false)
	public DealerDetailVO getDealerDetailByDealerId(@WebParam(name="dealerId")Long dealerId) throws BusinessException;

	 /**
     * Metodo: Obtiene una lista de jornadas activas dado el identificador de un país
     * @param countryId identificador del país
     * @return lista de jornadas que aplican a un país específico
     * @throws BusinessException en caso de error al tratar de obtener la lista de jornadas regionalizadas
     * @author jjimenezh agregado por control de cambios 2010-04-26
     */
	@WebMethod(operationName="getAllActiveServiceHoursByCountryId", action="getAllActiveServiceHoursByCountryId", exclude = false)
	public List<ServiceHourVO> getAllActiveServiceHoursByCountryId(@WebParam(name="countryId")Long countryId) throws BusinessException;

	/**
	 * Metodo: Obtiene la configuración de capacidad de una semana por los filtros
	 * @param dealerId identificador del dealer
	 * @return List<DealerWeekCapacityVO>
	 * @throws BusinessException en caso de error al tratar de ejecutar la operación
	 * @author wjimenez
	 */
	@WebMethod(operationName="getDealerWeekCapacityByDealerIdAndCountryId", action="getDealerWeekCapacityByDealerIdAndCountryId", exclude = false)
	public List<DealerWeekCapacityVO> getDealerWeekCapacityByDealerIdAndCountryId(@WebParam(name="dealerId")Long dealerId, @WebParam(name="countryId")Long countryId) throws BusinessException;
	
	/**
	 * Metodo: Obtiene todos los DealerCoverage del sistema que se encuentren activos correspondientes
	 * a un dealer específico
	 * @param dealerId identificador del Dealer
	 * @return List<DealerCoverageVO> listado de DealerCoverage activos
	 * @throws BusinessException en caso de un error en la ejecución de la operación
	 * @author wjimenez
	 */
	@WebMethod(operationName="getAllDealerCoverageActiveByDealerId", action="getAllDealerCoverageActiveByDealerId", exclude = false)
	public DealerCoverageResponse getAllDealerCoverageActiveByDealerId(@WebParam(name="dealerId")Long dealerId, 
																	   @WebParam(name="requestCollectionInfo")RequestCollectionInfo requestCollectionInfo
    ) throws BusinessException;

	/**
	 * Req-0096 - Requerimiento Consolidado Asignador
	 * Metodo: Obtiene todos los DealerConfCoverage del sistema correspondientes a un dealer específico
	 * @param dealerId identificador del Dealer
	 * @param customerCategoryId identificador de la categoría de cliente
	 * @param businessAreaId identificador del área de negocio
	 * @return List<DealerCoverageVO> listado de DealerCoverage activos
	 * @throws BusinessException en caso de un error en la ejecución de la operación
	 * @author ialessan
	 */
	@WebMethod(	operationName="getDealerConfCoverageByDealerIdCustomerCategoryIdAreaId", 
				action="getDealerConfCoverageByDealerIdCustomerCategoryIdAreaId", 
				exclude = false)
	public DealerCoverageResponse getDealerConfCoverageByDealerIdCustomerCategoryIdAreaId(
																	   @WebParam(name="dealerId")				Long dealerId, 
																	   @WebParam(name="cityId")					Long cityId,
																	   @WebParam(name="requestCollectionInfo")	RequestCollectionInfo requestCollectionInfo,
																	   @WebParam(name="customerCategoryId")		Long customerCategoryId,
																	   @WebParam(name="businessAreaId")			Long businessAreaId
																	   
    ) throws BusinessException;
	
	/**
	 * Metodo: Trae los códigos postales de una ciudad en los que un dealer no tiene configuracion de covertura 
	 * @param dealerId identificador del dealer
	 * @param cityId identificador de la ciudad
	 * @param requestCollectionInfo información de paginación
	 * @return PostalCodeResponse respuesta paginada
	 * @throws BusinessException en caso de un error en la ejecución de la operación
	 * @author wjimenez
	 */
	@WebMethod(operationName="getPostalCodesWithoutConfCoverageByDealerIdAndCityId", action="getPostalCodesWithoutConfCoverageByDealerIdAndCityId", exclude = false)
	public PostalCodeResponse getPostalCodesWithoutConfCoverageByDealerIdAndCityId(@WebParam(name="dealerId")Long dealerId, @WebParam(name="cityId")Long cityId,
			@WebParam(name="requestCollectionInfo")RequestCollectionInfo requestCollectionInfo,
			@WebParam(name="customerCategoryId")Long customerCategoryId,
			@WebParam(name="businessAreaId")Long businessAreaId) throws BusinessException;
	
	/**
	 * Req-0096 - Requerimiento Consolidado Asignador
	 * 
	 * Metodo: Obtiene la información de todos los customerTypeConfigurations existentes
	 * para una CustomerClass específica
	 * @param dealerId identificador del Dealer
	 * @param countryId
	 * @param customerClassId identificador de la clase de cliente con la que se realizará la búsqueda 
	 * @return List<DealerCustomerTypesWoutPc> Lista con los DealerCustomerTypesWoutPc correspondientes,
	 * una lista vacia en caso que no se encuentren registros 
	 * @throws BusinessException en caso de error al tratar de ejecutar la operación
	 * @author ialessan
	 */
	@WebMethod(operationName="getAllDealerCustomerTypesByCustomerCategoryId", action="getAllDealerCustomerTypesByCustomerCategoryId", exclude = false)
	public List<DealerCustomerTypesWoutPcVO> getAllDealerCustomerTypesByCustomerCategoryId
	(		@WebParam(name="dealerId")				Long dealerId, 
			@WebParam(name ="countryId") 			Long countryId, 
			@WebParam(name="customerCategoryId")	Long customerCategoryId,
			@WebParam(name="businessAreaId")	Long businessAreaId
			
     ) throws BusinessException;

	/**
	 * Metodo: Obtiene la información de todos los DealerCustomerTypesWoutPc existentes
	 * para un dealer específico
	 * @param dealerId identificador del dealer por el que se realizará la búsqueda
	 * @param countryId 
	 * @return List<DealerCustomerTypesWoutPc> Lista con los DealerCustomerTypesWoutPc correspondientes,
	 * una lista vacia en caso que no se encuentren registros 
	 * @throws BusinessException en caso de error al tratar de ejecutar la operación
	 * @author wjimenez
	 */
	@WebMethod(operationName="getAllDealerCustomerTypesWoutPcConfiguration", action="getAllDealerCustomerTypesWoutPcConfiguration", exclude = false)
	public List<DealerCustomerTypesWoutPcVO> getAllDealerCustomerTypesWoutPcConfiguration
	(@WebParam(name="dealerId")Long dealerId, @WebParam(name="countryId")Long countryId) throws BusinessException;
	
	/**
	 * Metodo: Consulta la cobertura por id de dealer, y codigo postal que se encuentre activa
	 * @param dealerId identificador de dealer 
	 * @param postalCodeId id de codigo postal
	 * @return DealerCoverage cubrimiento del dealer
	 * @throws DAOServiceException en caso de un error en la ejecución de la operación
	 * @throws DAOSQLException en caso de un error en la ejecución de la operación
	 * @author wjimenez
	 */
	@WebMethod(operationName="getDealerCoverageByDealerIdAndPostalCodeId", action="getDealerCoverageByDealerIdAndPostalCodeId", exclude = false)
	public DealerCoverageVO getDealerCoverageByDealerIdAndPostalCodeId(@WebParam(name="dealerId")Long dealerId, @WebParam(name="postalCodeId")Long postalCodeId) throws BusinessException;
	
	/**
	 * Metodo: Obtiene la configuración completa de todos los DealerServiceSubCategory existentes
	 * para un dealer específico. Esto significa que si un DealerServiceSubCategory no se ha configurado para
	 * una categoría específica, también se retornan estos registros para que se les agregue la configuración
	 * y se puedam persistir.
	 * @param dealerId identificador del dealer por el que se realizará la búsqueda
	 * @param countryId 
	 * @return List<DealerServiceSubCategory> Lista con los DealerServiceSubCategory correspondientes. 
	 * @throws BusinessException en caso de error al tratar de ejecutar la operación
	 * @author wjimenez
	 */
	@WebMethod(operationName="getAllDealerServiceSubCategoryConfiguration", action="getAllDealerServiceSubCategoryConfiguration", exclude = false)
	public List<DealerServiceSubCategoryVO> getAllDealerServiceSubCategoryConfiguration(@WebParam(name="dealerId")Long dealerId, @WebParam(name="countryId")Long countryId) throws BusinessException;

	/**
	 * Metodo: Crea o actualiza la configuración detallada de un dealer
	 * @param dealerDetail
	 * @param dealerWeekCapacities
	 * @param dealerCoverages
	 * @param dealerServiceSubCategories
	 * @param dealerCustomerTypesWoutPcs
	 * @throws BusinessException en caso de error al tratar de ejecutar la operación
	 * @author wjimenez
	 */
	@WebMethod(operationName="updateDealerConfiguration", action="updateDealerConfiguration", exclude = false)
	public void updateDealerConfiguration(
			@WebParam(name="dealerDetail")DealerDetailVO dealerDetail,
			@WebParam(name="dealerWeekCapacities")List<DealerWeekCapacityVO> dealerWeekCapacities,
			@WebParam(name="dealerCoverages")List<DealerCoverageVO> dealerCoverages,
			@WebParam(name="dealerServiceSubCategories")List<DealerServiceSubCategoryVO> dealerServiceSubCategories,
			@WebParam(name="dealerCustomerTypesWoutPcs")List<DealerCustomerTypesWoutPcVO> dealerCustomerTypesWoutPcs) throws BusinessException;
	/**
	 * Req-0096 - Requerimiento Consolidado Asignador
	 * 
	 * Metodo: Crea o actualiza la configuración detallada de un dealer desde Nueva Pantalla de Configuración de Dealers por Tipo de Cliente
	 *  
	 * @param dealerDetail
	 * @param dealerWeekCapacities
	 * @param dealerCoverages
	 * @param dealerServiceSubCategories
	 * @param dealerCustomerTypesWoutPcs
	 * @param userId
	 * @param dealerId
	 * @param customerCategoryId
	 * @param businessAreaId
	 * @throws BusinessException en caso de error al tratar de ejecutar la operación
	 * @author ialessan
	 */
	@WebMethod(	operationName="updateDealerByCustomerCategoryAndAreaConfiguration", 
				action="updateDealerByCustomerCategoryAndAreaConfiguration", 
				exclude = false)
	public void updateDealerByCustomerCategoryAndAreaConfiguration(
			@WebParam(name="dealerDetail")				DealerDetailVO 						dealerDetail			   ,
			@WebParam(name="dealerWeekCapacities")		List<DealerWeekCapacityVO> 			dealerWeekCapacities	   ,
			@WebParam(name="dealerCoverages")			List<DealerCoverageVO> 				dealerCoverages			   ,
			@WebParam(name="dealerServiceSubCategories")List<DealerServiceSubCategoryVO> 	dealerServiceSubCategories ,
			@WebParam(name="dealerCustomerTypesWoutPcs")List<DealerCustomerTypesWoutPcVO> 	dealerCustomerTypesWoutPcs ,
			@WebParam(name="userId") 					Long 								userIdLastChange	       , 
			@WebParam(name="dealerId")					Long 								dealerId 				   ,
			@WebParam(name="customerCategoryId")		Long 								customerCategoryId         ,
			@WebParam(name="businessAreaId")			Long 								businessAreaId
			) 
	 throws BusinessException;
	/**
	 * Metodo: obtiene todos los tipos de covertura configurados
	 * @return
	 * @throws BusinessException en caso de error al tratar de ejecutar la operación
	 * @author wjimenez
	 */
	@WebMethod(operationName="getAllCoverageTypes", action="getAllCoverageTypes", exclude = false)
	public List<CoverageTypeVO> getAllCoverageTypes() throws BusinessException;
	
	/**
	 * Metodo: Trae los códigos postales de una ciudad en los que un dealer no tiene covertura 
	 * @param dealerId identificador del dealer
	 * @param cityId identificador de la ciudad
	 * @param requestCollectionInfo información de paginación
	 * @return PostalCodeResponse respuesta paginada
	 * @throws BusinessException en caso de un error en la ejecución de la operación
	 * @author wjimenez
	 */
	@WebMethod(operationName="getPostalCodesWithoutCoverageByDealerIdAndCityId", action="getPostalCodesWithoutCoverageByDealerIdAndCityId", exclude = false)
	public PostalCodeResponse getPostalCodesWithoutCoverageByDealerIdAndCityId(@WebParam(name="dealerId")Long dealerId, @WebParam(name="cityId")Long cityId,
			@WebParam(name="requestCollectionInfo")RequestCollectionInfo requestCollectionInfo) throws BusinessException;

	 /**
     * Metodo: Obtiene el arbol completo de configuraciones de DealerServiceSubCategoryWithPc para un DealerCoverage
     * específico, empezando por la raiz que es de tipo ServiceTypeVO
     * @param dealerCoverageId
     * @return List<ServiceTypeVO>
     * @throws BusinessException en caso de error al tratar de ejecutar la operacion
     * @author wjimenez
     */
	@WebMethod(operationName="getDealerServiceSubCategoriesWithPcTree", action="getDealerServiceSubCategoriesWithPcTree", exclude = false)
	public List<ServiceTypeVO> getDealerServiceSubCategoriesWithPcTree(@WebParam(name="dealerCoverageId")Long dealerCoverageId) throws BusinessException;

	/**
	 * Metodo: persiste los cambios a un grupo de configuraciones de DealerServiceSubCategoriesWithPc
	 * La actualización hace parte de una sola transacción, es decir, o se actualizan todos los elementos
	 * o no se actualiza ninguno
	 * @param dealerServiceSubCategoriesWithPc
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author wjimenez
	 */
	@WebMethod(operationName="updateDealerServiceSubCategoriesWithPcConfiguration", action="updateDealerServiceSubCategoriesWithPcConfiguration", exclude = false)
	public void updateDealerServiceSubCategoriesWithPcConfiguration(@WebParam(name="dealerServiceSubCategoriesWithPc")List<DealerServiceSubCategoryWithPcVO> dealerServiceSubCategoriesWithPc) throws BusinessException;

	/**
     * Metodo: Obtiene el arbol completo de configuraciones de DealerServiceSubCategory para un Dealer
     * en un país específico, empezando por la raiz que es de tipo ServiceTypeVO
     * @param dealerId
     * @param countryId
     * @return List<ServiceTypeVO> estructura lista de ServiceTypeVO incluyendo
     * el listado internamente de DealerServiceSubCategoryVO
     * @throws BusinessException en caso de error al tratar de ejecutar la operacion
     * @author wjimenez
     */
	@WebMethod(operationName="getDealerServiceSubCategoriesTree", action="getDealerServiceSubCategoriesTree", exclude = false)
	public List<ServiceTypeVO> getDealerServiceSubCategoriesTree(@WebParam(name="dealerId")Long dealerId, @WebParam(name="countryId")Long countryId) throws BusinessException;

	/**
	 * Req-0096 - Requerimiento Consolidado Asignador
	 * 
     * Metodo: Obtiene el arbol completo de configuraciones de DealerServiceSubCategory para un Dealer, una Clase de Cliente y un Área de Negocio
     * en un país específico, empezando por la raiz que es de tipo ServiceTypeVO
     * @param dealerId
     * @param countryId
     * @param customerClassId
     * @param businessAreaId
     * @return List<ServiceTypeVO> estructura lista de ServiceTypeVO incluyendo
     * el listado internamente de DealerServiceSubCategoryVO
     * @throws BusinessException en caso de error al tratar de ejecutar la operacion
     * @author ialessan
     */
	@WebMethod(operationName="getDealerServiceSubCategoriesTreeByBusinessAreaId", action="getDealerServiceSubCategoriesTreeByBusinessAreaId", exclude = false)
	public List<ServiceTypeVO> getDealerServiceSubCategoriesTreeByBusinessAreaId( @WebParam(name="dealerId")Long dealerId,
																				  @WebParam(name="countryId")Long countryId , 
																				  @WebParam(name="businessAreaId")Long businessAreaId,
																				  @WebParam(name="customerCategoryId")Long customerCategoryId) 
    throws BusinessException;
	
	/**
	 * Metodo: obtiene la configuración de garantías para todos los tipos de servicio existentes
	 * en un país específico
	 * @param countryId identificador del país
	 * @return List<ServiceTypeWarranty> listado de garantías. Si un tipo de servicio no tiene garantía configurada,
	 * se incluye un ítem en la lista con el país y tipo de servicio asignados. 
	 * @throws BusinessException en caso de error al tratar de ejecutar la operación
	 * @author wjimenez
	 */
	@WebMethod(operationName="getServiceTypeWarrantiesConfigurationByCountryId", action="getServiceTypeWarrantiesConfigurationByCountryId", exclude = false)
	public List<ServiceTypeWarrantyVO> getServiceTypeWarrantiesConfigurationByCountryId(@WebParam(name="countryId")Long countryId) throws BusinessException;

	/**
	 * Metodo: actualiza o crea las parametrizaciones de garantías por tipo de servicio
	 * @param serviceTypeWarranties listado con las garantías que se desean crear o actualizar
	 * @throws BusinessException en caso de error al tratar de ejecutar la operación
	 * @author wjimenez
	 */
	@WebMethod(operationName="updateServiceTypeWarrantiesConfiguration", action="updateServiceTypeWarrantiesConfiguration", exclude = false)
	public void updateServiceTypeWarrantiesConfiguration(@WebParam(name="countryId")List<ServiceTypeWarrantyVO> serviceTypeWarranties) throws BusinessException;

	/**
	 * Metodo: ejecuta la actualización de la configuración de los dealerCoverage para la parametrización
	 * detallada de un dealer
	 * @param dealerCoverage
	 * @throws BusinessException en caso de error al tratar de ejecutar la operación
	 * @author wjimenez
	 */
	@WebMethod(operationName="updateDealerCoverageConfiguration", action="updateDealerCoverageConfiguration", exclude = false)
	public void updateDealerCoverageConfiguration(@WebParam(name="dealerCoverage")List<DealerCoverageVO> dealerCoverage)throws BusinessException;
	
	
	/**
	 * Req-0096 - Requerimiento Consolidado Asignador
	 * Metodo: ejecuta la actualización de la configuración de los dealerConfCoverage para la parametrización
	 * detallada de un dealer
	 * @param dealerCoverage
	 * @throws BusinessException en caso de error al tratar de ejecutar la operación
	 * @author ialessan
	 */
	@WebMethod(operationName="updateDealerConfCoverageConfiguration", action="updateDealerConfCoverageConfiguration", exclude = false)
	public void updateDealerConfCoverageConfiguration(@WebParam(name="dealerCoverages")			List<DealerCoverageVO> dealerCoverages,
													  @WebParam(name="dealerId")				Long dealerId, 
													  @WebParam(name="customerCategoryId")		Long customerCategoryId,
													  @WebParam(name="businessAreaId")			Long businessAreaId,
													  @WebParam(name="userId")					Long userIdLastChange
	)throws BusinessException;
		
	/**
	 * Metodo: obtiene una lista que solamente contiene el identificador del Service
	 * y el de ServiceTypeWarranty, buscando en las entidades ServiceAreWarranty por el
	 * identificador del serviceTypeWarranty
	 * @param serviceTypeWarrantyId identificador por el que se realiza la búsqueda
	 * @return Listado de ServiceAreWarranty que coincide con el criterio de búsqueda, pero que
	 * no contiene la estructura de información completa
	 * @throws BusinessException en caso de error al tratar de ejecutar la operación
	 * @author wjimenez
	 */
	@WebMethod(operationName="getServiceAreWarrantiesByServiceTypeWarrantyId", action="getServiceAreWarrantiesByServiceTypeWarrantyId", exclude = false)
	public List<ServiceAreWarrantyVO> getServiceAreWarrantiesByServiceTypeWarrantyId(@WebParam(name="serviceTypeWarrantyId")Long serviceTypeWarrantyId) throws BusinessException;

	/**
	 * Metodo: actualiza la configuración globlal de ServiceAreWarranty para un ServiceTypeWarranty específico.
	 * La configuración que se pasa como parámetro debe ser completa, ya que no se conservan configuraciones antigüas.
	 * @param serviceTypeWarrantyId identificador del ServiceTypeWarranty
	 * @param serviceAreWarranties listado de toda la configuración a guardar para el serviceTypeWarranty especificado 
	 * @throws BusinessException en caso de error al tratar de ejecutar la operación
	 * @author wjimenez
	 */
	@WebMethod(operationName="updateServiceAreWarrantiesConfiguration", action="updateServiceAreWarrantiesConfiguration", exclude = false)
	public void updateServiceAreWarrantiesConfiguration(@WebParam(name="serviceTypeWarrantyId")Long serviceTypeWarrantyId, @WebParam(name="serviceAreWarranties")List<ServiceAreWarrantyVO> serviceAreWarranties) throws BusinessException;

}
