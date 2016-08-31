
/**
 * Creado 26/05/2011 17:49:40
 */
package co.com.directv.sdii.facade.assign;

import java.util.List;

import javax.ejb.Remote;

import co.com.directv.sdii.assign.assignment.dto.SkillEvaluationDTO;
import co.com.directv.sdii.assign.kpi.dto.DealerIndicatorDTO;
import co.com.directv.sdii.assign.kpi.dto.DealerIndicatorResultDTO;
import co.com.directv.sdii.assign.schedule.dto.DealerWorkCapacityCriteria;
import co.com.directv.sdii.assign.schedule.dto.DealerWorkLoad;
import co.com.directv.sdii.common.enumerations.CodesBusinessEntityEnum;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.exceptions.PropertiesException;
import co.com.directv.sdii.exceptions.ScheduleException;
import co.com.directv.sdii.model.dto.KpiCalculateDTO;
import co.com.directv.sdii.model.pojo.WorkorderStatus;
import co.com.directv.sdii.model.vo.CountryVO;
import co.com.directv.sdii.model.vo.CustomerClassTypeVO;
import co.com.directv.sdii.model.vo.CustomerClassVO;
import co.com.directv.sdii.model.vo.CustomerTypeVO;
import co.com.directv.sdii.model.vo.DealerDetailVO;
import co.com.directv.sdii.model.vo.DealerVO;
import co.com.directv.sdii.model.vo.DealerWeekCapacityVO;
import co.com.directv.sdii.model.vo.KpiConfigurationVO;
import co.com.directv.sdii.model.vo.KpiResultVO;
import co.com.directv.sdii.model.vo.KpiTypeVO;
import co.com.directv.sdii.model.vo.KpiVO;
import co.com.directv.sdii.model.vo.PostalCodeVO;
import co.com.directv.sdii.model.vo.ServiceHourVO;
import co.com.directv.sdii.model.vo.ServiceSuperCategoryVO;
import co.com.directv.sdii.model.vo.ServiceTypeVO;
import co.com.directv.sdii.model.vo.SkillConfigurationVO;
import co.com.directv.sdii.model.vo.SkillModeTypeVO;
import co.com.directv.sdii.model.vo.SystemParameterVO;
import co.com.directv.sdii.model.vo.WorkOrderVO;
import co.com.directv.sdii.ws.model.dto.AssignRequestDTO;

/**
 * Define las operaciones que serán usadas por el submódulo de asignación en su núcleo
 * 
 * Fecha de Creación: 26/05/2011
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see     
 */
@Remote
public interface CoreAssignmentFacadeRemote {

	/**
	 * Metodo: Obtiene un dealer por el identificador
	 * @param dealerId identificador del dealer
	 * @return objeto con el dealer
	 * @throws BusinessException <tipo> <descripcion>
	 * @author
	 */
	public DealerVO getDealerById(Long dealerId, Long countryId)throws BusinessException;
	
	/**
	 * Metodo: Obtiene un dealer por el codigo
	 * @param dealerCode identificador del dealer
	 * @return objeto con el dealer
	 * @throws BusinessException <tipo> <descripcion>
	 * @author
	 */
	public DealerVO getDealerByDealerCode(Long dealerCode) throws BusinessException;
	
	/**
	 * Metodo: Obtiene un dealer por el codigo
	 * @param dealerCode
	 * @return
	 * @throws BusinessException <tipo> <descripcion>
	 * @author
	 */
	public Long getDealerIdByDealerCode(Long dealerCode) throws BusinessException;
	
	
	/**
	 * Metodo: Obtiene la super categoría de servicio por código de servicio
	 * @param serviceCode código del servicio 
	 * @return Super categoría de servicio por el código especificado, nulo en caso que no se encuentre
	 * @throws BusinessException En caso de error
	 * @author jjimenezh
	 */
	public ServiceSuperCategoryVO getServiceSuperCategoryByServiceCode(String serviceCode)throws BusinessException;
	
	/**
	 * Metodo: Obtiene la super categoría de servicio por código de servicio
	 * @param id id del servicio 
	 * @return Super categoría de servicio por el id especificado, nulo en caso que no se encuentre
	 * @throws BusinessException En caso de error
	 * @author cduarte
	 */
	public ServiceSuperCategoryVO getServiceSuperCategoryByID(Long id)throws BusinessException;

	/**
	 * Metodo: Obtiene la jornada por el código especificado
	 * @param serviceHourCode código de la jornada a buscar
	 * @param countryId 
	 * @return Objeto con la información de la jornada, nulo en caso que no exista
	 * @throws BusinessException en caso de error
	 * @author jjimenezh
	 */
	public ServiceHourVO getServiceHourByCode(String serviceHourCode, Long countryId)throws BusinessException;

	/**
	 * Metodo: Obtiene el código postal por el código especificado
	 * @param postalCode código postal
	 * @param countryId identificador del país
	 * @return información del código postal con el código especificado en el país especificado, nulo en caso
	 * que no se encuentre
	 * @throws BusinessException En caso de error
	 * @author jjimenezh
	 */
	public PostalCodeVO getPostalCodeByCode(String postalCode, Long countryId)throws BusinessException;

	/**
	 * Metodo: Obtiene el tipo de cliente por el código especificado
	 * @param customerTypeCode código del tipo de cliente
	 * @return información del tipo de cliente, nulo en caso que no se encuentre
	 * @throws BusinessException en caso de error
	 * @author jjimenezh
	 */
	public CustomerClassTypeVO getCustomerClassTypeByCodeType(String customerTypeCode, Long countryId)throws BusinessException;
	
	public CustomerClassTypeVO getCustomerClassTypeByCodeClass(String customerClassCode, Long countryId)throws BusinessException;
	
	public CustomerClassVO getCustomerClassByCode(String customerClassCode, Long countryId)throws BusinessException;
	
	public CustomerClassTypeVO getCustomerClassTypeByCode(String customerClassCode, String customerClassTypeCode, Long countryId)throws BusinessException;

	/**
	 * Metodo: Obtiene la modalidad de ejecución de la habilidad por el código especificado
	 * @param executionMode modalidad de ejecución
	 * @return objeto que encapsula la información del modo de ejecución de la habilidad
	 * @throws BusinessException en caso de error
	 * @author jjimenezh
	 */
	public SkillModeTypeVO getSkillModeTypeByCode(String executionMode)throws BusinessException;

	
	/**
	 * Metodo: Obtiene el país por el código especificado
	 * @param countryIso2Code código del país
	 * @return país con el código especificado
	 * @throws BusinessException En caso de error
	 * @author jjimenez
	 */
	public CountryVO getCountryByCode(String countryIso2Code) throws BusinessException;
	
	/**
	 * Metodo: Obtiene el país por el código especificado
	 * @param countryId id del país
	 * @return país con el id especificado
	 * @throws BusinessException En caso de error
	 * @author cduarte
	 */
	public CountryVO getCountriesByID(Long countryId) throws BusinessException;
	
	/**
	 * Metodo: Obtiene el último valor calculado para un dealer para un indicador dado
	 * @param countryId identificador del país
	 * @param serviceSuperCategoryId identificador de la super categoría de servicio
	 * @param zoneTypeId identificador del tipo de zona
	 * @param indicatorId identificador del indicador
	 * @param dealerId identificador de la compañía
	 * @return último resultado calculado
	 * @throws BusinessException En caso de error
	 * @author jjimenezh
	 */
	public KpiResultVO getKpiResultByCriteria(Long countryId,
			Long serviceSuperCategoryId, Long zoneTypeId, Long indicatorId,
			Long dealerId)throws BusinessException;
	
	/**
	 * Metodo: Obtiene el código del dealer sin cobertura
	 * @param countryId identificador del país
	 * @return código del dealer sin cobertura
	 * @throws BusinessException en caso de error
	 * @author jjimenezh
	 */
	public long getDealerCodeWoutCoverage(Long countryId)throws BusinessException;
	
	/**
	 * Metodo: Obtiene un parámetro del sistema por el código de parámetro y el id del país
	 * @param parameterCode código del parámetro del sistema
	 * @param countryId identificador del país
	 * @return parámetro del sistema encontrado bajo el código y el país
	 * @throws BusinessException
	 * @author jjimenezh
	 */
	public SystemParameterVO getSystemParameterByCodeAndCountryId(String parameterCode, Long countryId)throws BusinessException;
	
	/**
	 * Metodo: Obtiene la definición de un KPI por su identificador
	 * @param indicatorId identificador del indicador
	 * @return Objeto con la información encapsulada del indicador
	 * @throws BusinessException En caso de error
	 * @author jjimenezh
	 */
	public KpiVO getKpiById(Long indicatorId)throws BusinessException;
	
	/**
	 * Metodo: Obtiene las configuraciones de habilidades dados los criterios de filtro
	 * @param countryCode código del país
	 * @param serviceTypeCode código del tipo de servicio
	 * @param skillModeTypeCode código del modo de ejecución de las habilidades
	 * @return Lista con las configuraciones de ejecución de las habilidades
	 * @throws BusinessException En caso de error al realizar la consulta
	 * @author jjimenezh
	 */
	public List<SkillConfigurationVO> getSkillConfigurationsByCriteria(String countryCode, String serviceTypeCode, String skillModeTypeCode)throws BusinessException;
	
	/**
	 * Metodo: Obtiene las jornadas activas por 
	 * @param countryId identificador del pais
	 * @return Lista con jornadas activas en el país
	 * @throws BusinessException
	 * @author jjimenezh
	 */
	public List<ServiceHourVO> getActiveServiceHoursByCountryId(Long countryId) throws BusinessException;
	
	
	/**
	 * Metodo: Obtiene una jornada por identificador
	 * @param serviceHourId identificador de la jornada
	 * @return información de la jornada dado el identificador
	 * @throws BusinessException en caso de error
	 * @author jjimenezh
	 */
	public ServiceHourVO getServiceHourById(Long serviceHourId) throws BusinessException;
	
	/**
	 * Metodo: Obtiene la información del detalle de un dealer dado el identificador del mismo
	 * @param dealerId identificador del dealer
	 * @return información de detalle del dealer
	 * @throws BusinessException En caso de error al consultar la información de detalle del dealer
	 * @author jjimenezh
	 */
	public DealerDetailVO getDealerDetailByDealerId(Long dealerId) throws BusinessException;
	
	/**
	 * Metodo: Consulta la cantidad de técnicos activos que tiene una compañía instaladora
	 * @param dealerId identificador de la compañía instaladora
	 * @return cantidad de técnicos activos que tiene la compañía instaladora
	 * @throws BusinessException En caso de error
	 * @author jjimenezh
	 */
	public Long getActiveDealerTechniciansQty(Long dealerId) throws BusinessException;
	
	/**
	 * Metodo: Obtiene la configuración de capacidad de una semana por los filtros
	 * especificados
	 * @param countryId identificador del país
	 * @param dealerId identificador de la compañía instaladora
	 * @param serviceHourId identificador de la jornada
	 * @param serviceSuperCategoryId identificador de la super categoría
	 * @return Información de la capacidad configurada para la semana tipo por jornada y super categoría de servicio
	 * @throws BusinessException
	 * @author jjimenezh
	 */
	public DealerWeekCapacityVO getWeekCapacityByDealerIdAndServiceHourAndServiceSuperCat(Long countryId, Long dealerId, Long serviceHourId, Long serviceSuperCategoryId)throws BusinessException;


	/**
	 * Metodo: Obtiene el tipo de servicio dado el código del servicio
	 * @param serviceCode cpodigo del servicio
	 * @return objeto con la información de la categoría de servicio
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author jjimenezh
	 */
	public ServiceTypeVO getServiceTypeByServiceCode(String serviceCode)throws BusinessException;
	
	/**
	 * Metodo: <Descripcion>
	 * @param countryId
	 * @return
	 * @throws BusinessException
	 * @throws PropertiesException <tipo> <descripcion>
	 * @author
	 */
	public List<DealerVO> getActiveDealersByCountryId(Long countryId) throws BusinessException, PropertiesException;

	
	/**
	 * Metodo: Obtiene los dealer que estan activos y que tienen configuracion en dealer_details
	 * @param countryId
	 * @return
	 * @throws BusinessException
	 * @throws PropertiesException <tipo> <descripcion>
	 * @author
	 */
	public List<DealerVO> getDealersForGenerateKpis(Long countryId) throws BusinessException, PropertiesException;


	/**
	 * Metodo: Obtiene las compañías que atienden una categoría de servicio de un servicio asociado
	 * @param countryCode código del país
	 * @param serviceCode código del servicio
	 * @return Lista de compañías que atienden la categoría del servicio especificado
	 * @throws BusinessException en caso de error en la consulta
	 * @author
	 */
	public List<DealerVO> getDealersWhoAttendServiceCategory(String countryCode, String serviceCode)throws BusinessException;
	
	/**
	 * Metodo: Obtiene las compañías que atienden una subcategoría de servicio de un servicio asociado
	 * @param countryCode código del país
	 * @param serviceCode código del servicio
	 * @return Lista de compañías que atienden la categoría del servicio especificado
	 * @throws BusinessException en caso de error en la consulta
	 * @author
	 */
	public List<DealerVO> getDealersWhoAttendServiceSubCategory(String countryCode, String serviceCode)throws BusinessException;
	
	/**
	 * Metodo: Obtiene la información de todos los Dealer que atienden la sucategoria del servicio 'serviceCode' en la zona 
	 * postal cuyo codigo postal es 'postalCode' del pais con codigo 'countryCode'
	 * @param countryCode
	 * @param serviceCode
	 * @param postalCode
	 * @return Lista con los Dealers que cumplan el proposito de este metodo
	 * @throws BusinessException en caso de error al tratar de ejecutar la operación
	 */
	public List<DealerVO> getDealersWhoAttendServiceSubCategoryWithCoverage(String countryCode, String serviceCode, String postalCode) throws BusinessException;
	
	/**
	 * Metodo: A partir de los filtros pasados por parametros calcula el indicador de CyvleTime por dealer
	 * @param dealerIndicatorDto filtro para realizar el calculo
	 * @param java.util.Date startDate Fecha de inicio por la cual se va a filtrar la fecha de atencion
	 * @param java.util.Date endDate  Fecha de inicio por la cual se va a filtrar la fecha de atencion
	 * @return Double valor del indicador calculado a partir del filtro enviado
	 * @throws BusinessException En caso de error al calcular el indicador
	 * @author jnova
	 */
	public Double calculateCycleTimeIndicator(DealerIndicatorDTO dealerIndicatorDto,java.util.Date startDate,java.util.Date endDate) throws BusinessException;
	
	/**
	 * Metodo: A partir de los filtros pasados por parametros calcula el indicador de OnTime por dealer
	 * @param dealerIndicatorDto filtro para realizar el calculo
	 * @param java.util.Date startDate Fecha de inicio por la cual se va a filtrar la fecha de atencion
	 * @param java.util.Date endDate  Fecha de inicio por la cual se va a filtrar la fecha de atencion
	 * @return Double valor del indicador calculado a partir del filtro enviado
	 * @throws BusinessException En caso de error al calcular el indicador
	 * @author jnova
	 */
	public Double calculateOnTimeIndicator(DealerIndicatorDTO dealerIndicatorDto,java.util.Date startDate,java.util.Date endDate) throws BusinessException;
	
	/**
	 * Metodo: A partir de los filtros pasados por parametros calcula el indicador de BackLog dias por dealer
	 * @param dealerIndicatorDto filtro para realizar el calculo
	 * @param java.util.Date startDate Fecha de inicio por la cual se va a filtrar la fecha de atencion
	 * @param java.util.Date endDate  Fecha de inicio por la cual se va a filtrar la fecha de atencion
	 * @return Double valor del indicador calculado a partir del filtro enviado
	 * @throws BusinessException En caso de error al calcular el indicador
	 * @author jnova
	 */
	public Double calculateBackLogInDaysIndicator(DealerIndicatorDTO dealerIndicatorDto,java.util.Date startDate,java.util.Date endDate) throws BusinessException;
	
	
	/**
	 * Metodo: A partir de los filtros, obtiene las compañías que atienden el tipo de cliente
	 * @param countryIso2Code código del país
	 * @param customerTypeCode código del tipo de cliente
	 * @return Lista de compañías que atienden el tipo de cliente
	 * @author waltuzarra
	 * @throws BusinessException
	 */
	public List<DealerVO> getDealersWhoAttendCustomerTypeWoutCoverage(String countryIso2Code, Long customerClassTypeId) throws BusinessException;
	
	/**
	 * 
	 * Metodo: A partir de los filtros pasados por parametros calcula el indicador de SoS90
	 * @param dealerIndicatorDto filtro para realizar el calculo
	 * @param java.util.Date startDate Fecha de inicio por la cual se va a filtrar la fecha de atencion
	 * @param java.util.Date endDate  Fecha de inicio por la cual se va a filtrar la fecha de atencion
	 * @return Double valor del indicador calculado a partir del filtro enviado
	 * @throws BusinessException En caso de error al calcular el indicador
	 * @author jnova
	 */
	public Double calculateSoS90Indicator(DealerIndicatorDTO dealerIndicatorDto,java.util.Date startDate,java.util.Date endDate) throws BusinessException;
	
	/**
	 * 
	 * Metodo: A partir de los filtros pasados por parametros calcula el indicador de Aging
	 * @param dealerIndicatorDto filtro para realizar el calculo
	 * @param java.util.Date startDate Fecha de inicio por la cual se va a filtrar la fecha de atencion
	 * @param java.util.Date endDate  Fecha de inicio por la cual se va a filtrar la fecha de atencion
	 * @return Double valor del indicador calculado a partir del filtro enviado
	 * @throws BusinessException En caso de error al calcular el indicador
	 * @author jnova
	 */
	public Double calculateAgingIndicator(DealerIndicatorDTO dealerIndicatorDto,java.util.Date startDate,java.util.Date endDate) throws BusinessException;

	/**
	 * 
	 * Metodo: A partir de los filtros pasados por parametros calcula el indicador de Retrieval por dealer
	 * @param dealerIndicatorDto filtro para realizar el calculo
	 * @param java.util.Date startDate Fecha de inicio por la cual se va a filtrar la fecha de atencion
	 * @param java.util.Date endDate  Fecha de inicio por la cual se va a filtrar la fecha de atencion
	 * @return Double valor del indicador calculado a partir del filtro enviado
	 * @throws BusinessException En caso de error al calcular el indicador
	 * @author jnova
	 */
	public Double calculateRetrievalIndicator(DealerIndicatorDTO dealerIndicatorDto,java.util.Date startDate,java.util.Date endDate) throws BusinessException;
	
	/**
	 * 
	 * Metodo: Obtiene las configuraciones de KPI para un tipo de cálculo específigo
	 * @param string código del tipo de cálculo
	 * @return lista de configuraciones KPI con un tipo de cálculo específico
	 * @throws BusinessException en caso de error en la consulta
	 * @author wjimenez
	 */
	public List<KpiConfigurationVO> getKPIConfigurationsByCalculationTypeCode(String calculationTypeCode)throws BusinessException;
	
	/**
	 * Metodo: Obtiene las configuraciones de KPI para un tipo de cálculo específigo
	 * @param calculationTypeCode string código del tipo de cálculo
	 * @param countryId
	 * @return
	 * @throws BusinessException <tipo> <descripcion>
	 * @author
	 */
	public List<KpiCalculateDTO> getKpiCalculateDTOByCalculationTypeCode(String calculationTypeCode,Long countryId) throws BusinessException;
	
	/**
	 * Metodo: Obtiene los WorkOrders asociados con los parametros countryIso2Code, ibsCustomerCode, 
	 * postalCode, scheduleDate, addressCode y address
	 * @param countryIso2Code Código ISO2 del pais
	 * @param ibsCustomerCode Código IBS del cliente
	 * @param postalCode Código postal donde se va a ejecutar la WO    
	 * @param scheduleDate Fecha para la que se planeo la ejecucion de la WO
	 * @param addressCode 
	 * @param address Direccion donde se va a ejecutar la WO 
	 * @return
	 * @throws BusinessException
	 */
	public DealerVO otherServices2CustmerSkill(String countryIso2Code, String ibsCustomerCode, String postalCode, java.util.Date scheduleDate, String addressCode, String address)
	   throws BusinessException ;

	/**
	 * 
	 * Metodo: Calcula la cantidad de work orders que tiene pendientes de atender (asignadas y en estado agendado, reagendado, pendiente) 
	 * en la super categoría de servicio en la fecha y jornada especificadas
	 * @param dealerWCCriteria citerios para realizar el calculo
	 * @return Cantidad de WO que cumplen con el criterio
	 * @throws ScheduleException <tipo> <descripcion>
	 * @author jnova
	 */
	public int getDealerUsedCapacity(DealerWorkCapacityCriteria dealerWCCriteria) throws ScheduleException;
	
	/**
	 * 
	 * Metodo: Persiste la capacidad de un dealer por pais, super categoria y service_hour 
	 * @param dealerCapacity Objeto que contiene la informacion para persistir
	 * @throws ScheduleException <tipo> <descripcion>
	 * @author jnova
	 */
	public void saveDealerCapacity(DealerWorkLoad dealerCapacity) throws ScheduleException;

	/**
	 * 
	 * Metodo: Persiste el resultado de la ejecución de un indicador
	 * @param dealerIndicatorResultDTO
	 * @throws BusinessException en caso de error en la iserción
	 * @author
	 */
	public void saveKpiResult(DealerIndicatorResultDTO dealerIndicatorResultDTO) throws BusinessException;

	/**
	 * 
	 * Metodo: Obtiene el resultado de un KPI para un dealer
	 * @param dealerIndicatorDto
	 * @return KpiResultVO con el resultado del cálculo del KPI
	 * @throws BusinessException en caso de error en la consulta del resultado del KPI
	 * @author
	 */
	public KpiResultVO getDealerIndicatorResult(DealerIndicatorDTO dealerIndicatorDto) throws BusinessException;
	
	/**
	 * 
	 * Metodo: Consulta la capacidad del dealer a partir del detalle
	 * @param dealerId 
	 * @return
	 * @throws BusinessException <tipo> <descripcion>
	 * @author jnova
	 */
	public Long getDealerDayCapacity(Long dealerId) throws BusinessException;
	
	/**
	 * 
	 * Metodo: Dada una lista de dealers, retorna la que tiene menor backlog
	 * @param parameters SkillEvaluationDTO parametros para calcular el skill
	 * @return Lista que contiene el dealer con el backlog menor
	 * @throws BusinessException <tipo> <descripcion>
	 * @author jnova
	 */
	public List<DealerVO> getDealersForBalancedCapacitySkill(SkillEvaluationDTO parameters) throws BusinessException;
	
	/**
	 * 
	 * Metodo: Dada una lista de dealers, retorna la compañia que cumpla con la condicion que el backlog por dia sea inferior a la meta
	 * o en caso que esto no ocurra, la compañia que mas tiene prioridad de acuerdo al cubrimiento de la compañia
	 * @param parameters SkillEvaluationDTO parametros para calcular el skill
	 * @return Lista de compañias donde se encuentra la que cumple con las condiciones de prioridad
	 * @throws BusinessException <tipo> <descripcion>
	 * @author jnova
	 */
	public List<DealerVO> getDealersForPrioritizedCapacitySkill(SkillEvaluationDTO parameters) throws BusinessException;
	
	/**
	 * 
	 * Metodo: A partir de los parametros obtiene los dealers que cumplen con la habilidad de dealer vende instala
	 * @param parameters
	 * @return Lista de compañias que cumplen con las condiciones de la habilidad
	 * @throws BusinessException <tipo> <descripcion>
	 * @author jnova
	 */
	public List<DealerVO> getDealersForSaleAndInstallSameDealerSkill(SkillEvaluationDTO parameters) throws BusinessException;
	
	/**
	 * 
	 * Metodo: A partir de los parametro obtiene los dealers que cumplen con la habilidad vende instala asociado
	 * @param parameters
	 * @return Lista de compañias que cumplen con las condiciones de la habilidad
	 * @throws BusinessException <tipo> <descripcion>
	 * @author jnova
	 */
	public List<DealerVO> getDealersForSaleAndInstallDealerAssociatedSkill(SkillEvaluationDTO parameters) throws BusinessException;
	
	/**
	 * 
	 * Metodo: A partir de los parametro obtiene los dealers que cumplen con la habilidad par o impar
	 * a partir de codigo de cliente
	 * @param parameters
	 * @return Lista de comañias que cumple con la habilidad
	 * @throws BusinessException <tipo> <descripcion>
	 * @author jnova
	 */
	public List<DealerVO> getDealersForEvenOrOddCustomerCodeSkill(SkillEvaluationDTO parameters) throws BusinessException;
	
	/**
	 * 
	 * Metodo: A partir de los parametro obtiene los dealers que cumplen con la habilidad kpi skill
	 * @param parameters
	 * @return Lista de comañias que cumple con la habilidad
	 * @throws BusinessException <tipo> <descripcion>
	 * @author jnova
	 */
	public List<DealerVO> getDealersForKpiSkill(SkillEvaluationDTO parameters) throws BusinessException;

	/**
	 * Metodo: Obtiene los tipos de KPI registrados en el sistema
	 * @return lista con los tipos de kpi
	 * @throws BusinessException en caso de error
	 * @author jjimenezh
	 */
	public List<KpiTypeVO> getActiveKpiTypes()throws BusinessException;

	/**
	 * Metodo: Obtiene una lista de las configuraciones de KPI activas por los parametros definidos
	 * @param countryId identificador del país
	 * @param serviceSuperCategoryId identificador de la super categoría de servicio
	 * @param zoneTypeId identificador del tipo de zona
	 * @return Lista con la configuración de los KPi
	 * @throws BusinessException En caso de error al cargar la configuración
	 * @author jjimenezh
	 */
	public List<KpiConfigurationVO> getKPIConfigurationsByCountryAndServiceSuperCategoryAndZoneType(
			Long countryId, Long serviceSuperCategoryId, Long zoneTypeId)throws BusinessException;

	/**
	 * Metodo: Carga la configuración de un KPI para los parámetros dados
	 * @param countryCode código del país
	 * @param serviceSuperCategoryCode código de la super categoría de servicio
	 * @param zoneTypeCode código del tipo de zona
	 * @param kpiCode código del KPI
	 * @return configuración del KPi nulo en caso que no esté activa o no exista
	 * @throws BusinessException en caso de error
	 * @author jjimenezh
	 */
	public KpiConfigurationVO getKPIConfigurationsByCountryCodeAndServiceSuperCategoryCodeAndZoneTypeCodeAndKpiCode(
			String countryCode, String serviceSuperCategoryCode,
			String zoneTypeCode, String kpiCode)throws BusinessException;
	
	/**
	 * Metodo: Obtiene la configuración de un KPI por los criterios de búsqueda
	 * @param countryId identificador del país
	 * @param superCategoryId identificador de la super categoría de servicio
	 * @param zoneTypeId identificador del tipo de zona
	 * @param kpiCode código del KPI
	 * @return configuración del KPi, nulo en caso que no se encuentre o no se encuentre activa
	 * @throws BusinessException en caso de error
	 * @author jjimenezh
	 */
	public KpiConfigurationVO getActiveKpiConfigurationByCountrySuperCategoryKpiAndZoneTypeAndKpiCode(Long countryId, Long superCategoryId, Long zoneTypeId, String kpiCode)throws BusinessException;

	/**
	 * Metodo: Obtiene la configuración de un KPI por los criterios de búsqueda
	 * @param countryId identificador del país
	 * @param superCategoryId identificador de la super categoría de servicio
	 * @param kpiId código del KPI
	 * @param zoneTypeId identificador del tipo de zona
	 * @return configuración del KPi, nulo en caso que no se encuentre o no se encuentre activa
	 * @throws BusinessException en caso de error
	 * @author cduarte
	 */
	public KpiConfigurationVO getActiveKpiConfigurationByCountrySuperCategoryKpiAndZoneType(
			Long countryId, Long superCategoryId, Long kpiId,Long zoneTypeId) throws BusinessException;

	/**
	 * Metodo: Obtiene los dealers que puedan atender en una zona, teniendo en cuenta la
	 * modalidad de ejecucion
	 * @param executionMode String
	 * @param postalCode String 
	 * @param countryIso2Code String
	 * @param dealerId
	 * @return
	 * @throws BusinessException
	 * @author waltuzarra
	 */
	public List<DealerVO> getDealersInMicrozoneWithExMode(String executionMode, String postalCode, String countryIso2Code,Long dealerId)throws BusinessException;
	
	/**
	 * Metodo: Obtiene la cantidad de dealers que puedan atender en una zona, teniendo en cuenta la
	 * modalidad de ejecucion
	 * @param postalCode String
	 * @param countryIso2Code String
	 * @param dealerId
	 * @return
	 * @throws BusinessException <tipo> <descripcion>
	 * @author cduarte
	 */
	public boolean checkDealersInMicrozoneWithTypePerm(String postalCode, String countryIso2Code,Long dealerId)throws BusinessException;
	
	
	/**
	 * Metodo: Obtiene el dealer de la ultima WO en estado realizada o finalizada de un Cliente.
	 * @param ibsCustomerCode String, codigo del cliente a consultar las WO
	 * @param countryCode Codigo del pais
	 * @return List<DealerVO>, lista de dealers encontrados
	 * @author waltuzarra
	 * @throws BusinessException
	 */
	public List<DealerVO> getDealerFromLastWoFromCustomer(String ibsCustomerCode, Long countryCode) throws BusinessException;

	/**
	 * Metodo:  1. Consultar La última Work order que se atendió al cliente, es decir que esté en estado 
	 * 		realizada o finalizada (Si no existe, se devuelve una lista vacía de dealers)<br>
	 * 2. Consultar la categoría de servicio del primer servicio asociado a esa Work Order (la consultada en el paso 1)
	 * 		en la base de datos la categoría del servicio se almacena en la tabla service_type<br>
	 * 3. Consultar si algunos de los servicios que se prestarán al cliente en esta oportunidad están configurados como que
	 * 		constituyen garantía de la categoría consultada en el paso 2, esto se almacena en la tabla
	 * 		services_are_warranties, si ninguno constituye garantía la habilidad devuelve una lista vacia de dealers<br>
	 * 4. Consultar el periodo de garantía de la categoría de servicio de esa WO (la consultada en el paso 1)
	 * 		en la tabla services_type_warranties<br>
	 * 5. Si la última WO consultada en el paso 1 tiene como fecha de atención o finalización una fecha superior
	 * 		a la fecha actual menos los días de periodo de garantía consultados en el paso 4, quiere decir
	 * 		que esa compañía debe atender la WO actual<br>
	 * 6. Si la compañía que atendió la WO del paso 1, está dentro de la lista de compañías que se recibieron, se
	 * 		devuelve una lista con solo esa compañía, si la compañía no está dentro de la lista que se recibe
	 * 		se devuelve una lista vacia.<br>
	 * 7. En caso que la habilidad reciba una lista vacía de compañías devolverá una lista
	 * 		con la compañía que se encontró en el paso 1.
	 * @param ibsCustomerCode
	 * @param countryCode
	 * @param List<String> serviceCode
	 * @return List<DealerVO>, lista de dealers 
	 * @author waltuzarra
	 * @throws BusinessException
	 */
	public List<DealerVO> getDealerFromWoWithWarranty(String ibsCustomerCode,
			Long countryCode,  List<String> serviceCode) throws BusinessException;

	/**
	 * Metodo: Si el edificio con codigo 'ibsBuildingCode' esta en la tabla BUILDINGS regresa los dealers que atienden ese edificio. 
	 *         Si no hay dealers activos que atiendan este edificio, regresa los dealers activos que atienden edificios cuya covertura esta asociada al 'postalCode'.  
	 *         Si el edificio con codigo 'ibsBuildingCode' NO ESTA en la tabla BUILDINGS, regresa los dealers activos que atienden edificios cuya covertura esta asociada al 'postalCode'.
	 * @param  countryCode 
     * @param ibsBuildingCode Identificador del edificio.
     * @param postalCode Zona postal del edificio donde se va a prestar el servicio.
     * @return Regresa los dealers que atienden ese edificio. 
     * @throws BusinessException
     * @author rdelarosa
     */
    public List<DealerVO> getDealersWhoAttendsABuildingOrAttendsBuildingsIntoPostalCode(String ibsBuildingCode, String postalCode, String countryCode) 
       throws BusinessException;	

    
    //Req-0096 - Requerimiento Consolidado Asignador / Automatizacion, Relacion de Edificios para Asignacion de Work Orders
    public List<DealerVO> getDealersWhoAttendsABuilding(Long buildingId) throws BusinessException;
    

    public WorkorderStatus getWorkorderStatusByIBS6StatusCode(String ibs6StatusCode) throws BusinessException;    

    /**
     * Metodo encargado de enviar correo con informe de errores
     * @param assignRequestDTO
     * @param countryId
     * */
    public void sendMailCsr(AssignRequestDTO assignRequestDTO, Long countryId);
    
    /**
     * Metodo: Consulta la informacion de las workOrder atendidas o finalizadas
     * @param countryId
     * @return
     * @throws BusinessException <tipo> <descripcion>
     * @author
     */
    public void sendWorkOrdersForLastDayReport(Long countryId) throws BusinessException;
    	
    /**
     * 
     * @param woCode
     * @return
     * @throws BusinessException
     */
    public WorkOrderVO getWorkOrderByCode(String woCode) throws BusinessException;
    
	/**
	 * Metodo: <br/>
	 * Busca los Dealers que tienen:<br/>
	 * 	<ul><li> cobertura para el área de negocio de la WO y la categoria de cliente del cliente de la WO. </li>
	 * 	<li> sub categoria de servicio para el área de negocio de la WO. </li>
	 *  <li> tipo de cliente para la categoría de cliente del cliente de la WO. </li></ul>
	 * @param customerCategoryId id de la categoria de negocio
	 * @param businesAreaId id del área de negocio
	 * @param customerCassTypeId id de la relacion entre class y type del customer
	 * @param serviceCategoryId id de la categoria de servicio de la wo
	 * @param postalCodeId id del codigo postal del customer
	 * @param  countryCode código del país
	 * @return Lista de compañías 
	 * @throws BusinessException en caso de error en la consulta
	 * @author ssanabri
	 */
    public List<DealerVO> getDealerFromDealerByCustomerType(Long customerCategoryId, Long businesAreaId, Long customerClassTypeId, 
    		Long serviceCategoryId, Long postalCodeId, Long countryCode) throws BusinessException;
    
}
