package co.com.directv.sdii.ws.business.allocator;

import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.xml.ws.soap.MTOM;

import co.com.directv.sdii.common.enumerations.CodesBusinessEntityEnum;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.exceptions.PropertiesException;
import co.com.directv.sdii.facade.allocator.AllocatorFacadeLocal;
import co.com.directv.sdii.facade.schedule.WoInfoEsbServiceFacadeLocal;
import co.com.directv.sdii.model.vo.CountryVO;
import co.com.directv.sdii.model.vo.CustomerVO;
import co.com.directv.sdii.model.vo.DealerVO;
import co.com.directv.sdii.model.vo.PostalCodeVO;
import co.com.directv.sdii.model.vo.ServiceHourVO;
import co.com.directv.sdii.model.vo.ServiceVO;
import co.com.directv.sdii.model.vo.WorkOrderVO;

/**
 * Servicio web que expone las operaciones de los web services de Core.
 * 
 * Fecha de Creación: 13/05/2010
 * 
 * @author Jimmy Vélez Muñoz
 * @version 1.0
 * 
 */
@MTOM
@WebService(name = "AllocatorWS")
@Stateless()
public class AllocatorWS {

	@EJB
	private AllocatorFacadeLocal allocatorFacade;

	@EJB
	private WoInfoEsbServiceFacadeLocal woInfoEsbServiceFacadeLocal;
	
//	/**
//	 * Método de negocio que evalúa que compañía es la que se debe asignar, para una work order
//	 * 
//	 * @param dealers lista con los dealers pre- candidatos para la asignación
//	 * @param wo orden de trabajo que será asignada a un dealer
//	 * @param agendaDate fecha en la que se encuentra el agendamiento de la work order
//	 * @param serviceHourCode código de la jornada en la que se está agendada la work order
//	 * @return Dealer al que se le realiza la asignación de la work order
//	 * @throws BusinessException en caso de error al realizar la asignación de dealer por rotación,,
//	 * a continuación los códigos de error:<br>
//     * <code>sdii_CODE_constraint_violation</code> En caso que se viole una restricción definida en la persistencia<br>
//     * <code>sdii_CODE_error_data</code> En caso que se viole una regla de datos de la persistencia<br>
//     * <code>sdii_CODE_generic_jdbc</code> En caso de error genérico relacionado con el componente de conexión a la base de datos<br>
//     * <code>sdii_CODE_jdbc_connection</code> En caso de error al tratar de conectarse con la base de datos<br>
//     * <code>sdii_CODE_sql_grammar</code> En caso de error relacionado con la gramática para ejecutar el comando en la base de datos<br>
//     * <code>sdii_CODE_error_service</code> En caso de error al tratar de obtener el valor de una propiedad o un error inesperado en la capa de acceso a datos<br>
//     * <code>sdii_CODE_unknow_error</code> En caso de un error inesperado en la capa de negocio<br>
//	 * <code>sdii_CODE_params_null_or_missed</code> En caso que no se encuentren los parámetros necesarios para la ejecución de la operación<br>
//	 */
//	@WebMethod(operationName = "asignarDealerPorRotacion", action = "asignarDealerPorRotacion")
//	public DealerVO asignarDealerPorRotacion(@WebParam(name = "AllocatorRequestDTO") AllocatorRequestDTO requestDto)
//			throws BusinessException {
//		if(requestDto.getDealerList() == null){
//			requestDto.setDealerList(new DealerListDTO(new ArrayList<DealerVO>()));
//		}
//		return allocatorFacade.asignarDealerPorRotacion(requestDto.getDealerList().getDealer(), requestDto.getWorkOrder(), requestDto.getAgendaDate(), requestDto.getServiceHour());
//	}

//	/**
//	 * Método de negocio que evalúa que compañías cumplen con las habilidades determinantes.
//	 * 
//	 * @param dealers lista con los dealers pre- candidatos para la evaluación
//	 * @param wo orden de trabajo que sobre la cual se realiza la evaluación
//	 * @param agendaDate fecha en la que se encuentra el agendamiento de la work order
//	 * @param serviceHourCode código de la jornada en la que se está agendada la work order
//	 * @return List<DealerVO> Lista con los dealers que pasan la evaluación de habilidades determinantes
//	 * necesarias para la prestación de los servicios de la work order
//	 * @throws BusinessException en caso de error al tratar de realizar la evaluación de habilidades,
//	 * a continuación los códigos de error:<br>
//     * <code>sdii_CODE_constraint_violation</code> En caso que se viole una restricción definida en la persistencia<br>
//     * <code>sdii_CODE_error_data</code> En caso que se viole una regla de datos de la persistencia<br>
//     * <code>sdii_CODE_generic_jdbc</code> En caso de error genérico relacionado con el componente de conexión a la base de datos<br>
//     * <code>sdii_CODE_jdbc_connection</code> En caso de error al tratar de conectarse con la base de datos<br>
//     * <code>sdii_CODE_sql_grammar</code> En caso de error relacionado con la gramática para ejecutar el comando en la base de datos<br>
//     * <code>sdii_CODE_error_service</code> En caso de error al tratar de obtener el valor de una propiedad o un error inesperado en la capa de acceso a datos<br>
//     * <code>sdii_CODE_unknow_error</code> En caso de un error inesperado en la capa de negocio<br>
//     * <code>sdii_CODE_params_null_or_missed</code> En caso que no se encuentren los parámetros necesarios para la ejecución de la operación<br>
//     * <code>sdii_CODE_system_parameter_not_found</code> En caso que no se encuentre el parámetro del sistema "sdii_CODE_sys_param_back_log"<br>
//     * 
//	 */
//	@WebMethod(operationName = "evaluarHabilidadesDeterminantes", action = "evaluarHabilidadesDeterminantes")
//	public DealerListDTO evaluarHabilidadesDeterminantes(
//			@WebParam(name = "AllocatorRequestDTO") AllocatorRequestDTO requestDto)
//			throws BusinessException {
//		if(requestDto.getDealerList() == null){
//			requestDto.setDealerList(new DealerListDTO(new ArrayList<DealerVO>()));
//		}
//		List<DealerVO> dealerList = allocatorFacade.evaluarHabilidadesDeterminantes(requestDto.getDealerList().getDealer(), requestDto.getWorkOrder(), requestDto.getAgendaDate(), requestDto.getServiceHour());
//		DealerListDTO result = new DealerListDTO(dealerList);
//		return result;
//		
//	}

//	/**
//	 * Método de negocio que evalúa que compañías cumplen con las habilidades eliminantes.
//	 * 
//	 * @param dealers lista con los dealers pre- candidatos para la evaluación
//	 * @param wo orden de trabajo que sobre la cual se realiza la evaluación
//	 * @param agendaDate fecha en la que se encuentra el agendamiento de la work order
//	 * @param serviceHourCode código de la jornada en la que se está agendada la work order
//	 * @return List<DealerVO> Lista con los dealers que pasan la evaluación de habilidades eliminantes
//	 * @throws BusinessException en caso de error al realizar la evaluación de habilidades eliminantes,
//	 * a continuación los códigos de error:<br>
//     * <code>sdii_CODE_constraint_violation</code> En caso que se viole una restricción definida en la persistencia<br>
//     * <code>sdii_CODE_error_data</code> En caso que se viole una regla de datos de la persistencia<br>
//     * <code>sdii_CODE_generic_jdbc</code> En caso de error genérico relacionado con el componente de conexión a la base de datos<br>
//     * <code>sdii_CODE_jdbc_connection</code> En caso de error al tratar de conectarse con la base de datos<br>
//     * <code>sdii_CODE_sql_grammar</code> En caso de error relacionado con la gramática para ejecutar el comando en la base de datos<br>
//     * <code>sdii_CODE_error_service</code> En caso de error al tratar de obtener el valor de una propiedad o un error inesperado en la capa de acceso a datos<br>
//     * <code>sdii_CODE_unknow_error</code> En caso de un error inesperado en la capa de negocio<br>
//	 * <code>sdii_CODE_params_null_or_missed</code> En caso que no se encuentren los parámetros necesarios para la ejecución de la operación<br>
//	 */
//	@WebMethod(operationName = "evaluarHabilidadesEliminantes", action = "evaluarHabilidadesEliminantes")
//	public DealerListDTO evaluarHabilidadesEliminantes(@WebParam(name = "AllocatorRequestDTO") AllocatorRequestDTO requestDto)
//			throws BusinessException {
//		List<DealerVO> dealerList = allocatorFacade.evaluarHabilidadesEliminantes(requestDto.getDealerList().getDealer(), requestDto.getWorkOrder(), requestDto.getAgendaDate(), requestDto.getServiceHour());
//		DealerListDTO result = new DealerListDTO(dealerList);
//		return result;
//	}

//	/**
//	 * Método de negocio que evalúa que compañías cumplen con las habilidades puntuables.
//	 * 
//	 * @param dealers lista con los dealers pre- candidatos para la evaluación
//	 * @param wo orden de trabajo que sobre la cual se realiza la evaluación
//	 * @param agendaDate fecha en la que se encuentra el agendamiento de la work order
//	 * @param serviceHourCode código de la jornada en la que se está agendada la work order
//	 * @return List<DealerVO> Lista con los dealers que pasan la evaluación de habilidades puntuables
//	 * @throws BusinessException en caso de error al realizar la evaluación de habilidades puntuables,
//	 * a continuación los códigos de error:<br>
//     * <code>sdii_CODE_constraint_violation</code> En caso que se viole una restricción definida en la persistencia<br>
//     * <code>sdii_CODE_error_data</code> En caso que se viole una regla de datos de la persistencia<br>
//     * <code>sdii_CODE_generic_jdbc</code> En caso de error genérico relacionado con el componente de conexión a la base de datos<br>
//     * <code>sdii_CODE_jdbc_connection</code> En caso de error al tratar de conectarse con la base de datos<br>
//     * <code>sdii_CODE_sql_grammar</code> En caso de error relacionado con la gramática para ejecutar el comando en la base de datos<br>
//     * <code>sdii_CODE_error_service</code> En caso de error al tratar de obtener el valor de una propiedad o un error inesperado en la capa de acceso a datos<br>
//     * <code>sdii_CODE_unknow_error</code> En caso de un error inesperado en la capa de negocio<br>
//	 * <code>sdii_CODE_params_null_or_missed</code> En caso que no se encuentren los parámetros necesarios para la ejecución de la operación<br>
//	 */
//	@WebMethod(operationName = "evaluarHabilidadesPuntuables", action = "evaluarHabilidadesPuntuables")
//	public DealerListDTO evaluarHabilidadesPuntuables(@WebParam(name = "AllocatorRequestDTO") AllocatorRequestDTO requestDto)
//			throws BusinessException {
//		List<DealerVO> dealerList = allocatorFacade.evaluarHabilidadesPuntuables(requestDto.getDealerList().getDealer(), requestDto.getWorkOrder(), requestDto.getAgendaDate(), requestDto.getServiceHour());
//		DealerListDTO result = new DealerListDTO(dealerList);
//		return result;
//	}
	
	/**
	 * Caso de Uso ADS - 01 - Consultar Fechas de Disponibilidad  de Servicio.
	 * Caso de Uso ADS - 16 - Visualizar Fechas de Disponibilidad  de Servicio.
	 * 
	 * La finalidad de este método es brindar al modulo de captura 
	 * el conjunto de Fechas y Jornadas disponibles 
	 * para la prestación de un conjunto determinado de servicios 
	 * a un cliente especifico.
	 * @author Leonardo Cardozo Cadavid - lcardozo@intergrupo.com
	 * @param customer objeto que encapsula la información del cliente
	 * @param postalCode código postal en donde se prestarán los servicios
	 * @param services lista de servicios a prestarse al cliente
	 * @param date fecha en la cual se planea prestar los servicios al cliente
	 * @param buildingCode código del edificio del cliente.
	 * @return List<ServiceAvailabilityVO> Lista con la disponibilidad de servicios en la fecha y con las jornadas
	 * @throws BusinessException en caso de error al tratar de consultar la disponibilidad para la prestación de servicios
	 * a continuación los códigos de error:<br>
     * <code>sdii_CODE_constraint_violation</code> En caso que se viole una restricción definida en la persistencia<br>
     * <code>sdii_CODE_error_data</code> En caso que se viole una regla de datos de la persistencia<br>
     * <code>sdii_CODE_generic_jdbc</code> En caso de error genérico relacionado con el componente de conexión a la base de datos<br>
     * <code>sdii_CODE_jdbc_connection</code> En caso de error al tratar de conectarse con la base de datos<br>
     * <code>sdii_CODE_sql_grammar</code> En caso de error relacionado con la gramática para ejecutar el comando en la base de datos<br>
     * <code>sdii_CODE_error_service</code> En caso de error al tratar de obtener el valor de una propiedad o un error inesperado en la capa de acceso a datos<br>
     * <code>sdii_CODE_unknow_error</code> En caso de un error inesperado en la capa de negocio<br>
     * <code>sdii_CODE_params_null_or_missed</code> En caso que no se encuentren los parámetros necesarios para la ejecución de la operación<br>
     * <code>sdii_CODE_date_or_hour_range_invalid</code> En caso que se consulte una fecha anterior a la fecha actual<br>
     * <code>sdii_CODE_valid_customer_not_found</code> No se encontró un cliente con el código de cliente especificado en el parámetro customer<br>
     * <code>sdii_CODE_system_parameter_not_found</code> No se encontró el parámetro del sistema que especifica el número de dias hacia adelante que se consulta la disponibilidad<br>
     * <code>sdii_CODE_service_hour_by_country_not_found</code> No se encontraron jornadas en el país especificado<br>
     * <code>sdii_CODE_dealer_service_capacity_not_found</code> No se encontraron capacidades de servicio de dealers en el código postal, en las fechas especificadas<br>
	 * 
	 * @author jjimenezh
	 */
	/*
	@WebMethod(operationName = "getServiceAvailabilityDates", action = "getServiceAvailabilityDates", exclude=false)
	public List<ServiceAvailabilityVO> getServiceAvailabilityDates(
			@WebParam(name = "customer") CustomerVO customer, 
			@WebParam(name = "postalCode") PostalCodeVO postalCode, 
			@WebParam(name = "services") List<ServiceVO> services, 
			@WebParam(name = "date") Date date, 
			@WebParam(name = "buildingCode") String buildingCode, 
			@WebParam(name = "country") CountryVO country,
			@WebParam(name = "userId") Long userId) throws BusinessException{
		Map<Date, List<ServiceHourVO>> serviceAvMap =  allocatorFacade.getServiceAvailabilityDates(customer, postalCode, services, date, buildingCode, country, userId);
		List<ServiceAvailabilityVO> result = new ArrayList<ServiceAvailabilityVO>();
		
		Set<Date> keySet = serviceAvMap.keySet();
		
		for (Date serviceAvDate : keySet) {
			ServiceAvailabilityVO servAv = new ServiceAvailabilityVO(serviceAvDate, serviceAvMap.get(serviceAvDate));
			result.add(servAv);
			
		}
		return result;
	}*/
	
	/**
	 * Caso de Uso ADS - 05 - Compañías que cumplen Habilidades Puntuables.
	 * 
	 * La finalidad de este método es determinar, dado un conjunto de companías instaladoras, 
	 * y de acuerdo a las habilidades puntuables establecidas en el sistema, 
	 * cuales tienen el mayor valor de puntuación.
	 * 
	 * @param dealers lista con los dealers pre-seleccionados para la evaluación
	 * @param customer información del cliente asociado a la work order
	 * @param postalCode código postal en el que se prestarán los servicios de la work order
	 * @param services lista de servicios asociados a la work order
	 * @param date fecha en la que se prestarán los servicios de la work order
	 * @param serviceHour jornada en la que se realizará la atención de la work order
	 * @param buildingCode código del edificio
	 * @return List<DealerVO> lista con los dealers que cumplen las habilidades puntuables
	 * @throws BusinessException en caso de error al tratar de realiza la evaluación de habilidades puntuables
	 * a continuación los códigos de error:<br>
     * <code>sdii_CODE_constraint_violation</code> En caso que se viole una restricción definida en la persistencia<br>
     * <code>sdii_CODE_error_data</code> En caso que se viole una regla de datos de la persistencia<br>
     * <code>sdii_CODE_generic_jdbc</code> En caso de error genérico relacionado con el componente de conexión a la base de datos<br>
     * <code>sdii_CODE_jdbc_connection</code> En caso de error al tratar de conectarse con la base de datos<br>
     * <code>sdii_CODE_sql_grammar</code> En caso de error relacionado con la gramática para ejecutar el comando en la base de datos<br>
     * <code>sdii_CODE_error_service</code> En caso de error al tratar de obtener el valor de una propiedad o un error inesperado en la capa de acceso a datos<br>
     * <code>sdii_CODE_unknow_error</code> En caso de un error inesperado en la capa de negocio<br>
	 * <code>sdii_CODE_params_null_or_missed</code> En caso que no se encuentren los parámetros necesarios para la ejecución de la operación<br>
	 * <code>sdii_CODE_insufficient_dealer_qty</code> En caso que la lista de dealers que se pasa como parámetro tenga uno o menos dealers<br>
	 */
	/*
	@WebMethod(operationName = "getSkillsRatesDealers", action = "getSkillsRatesDealers", exclude=true)
	public List<DealerVO> getSkillsRatesDealers(
			@WebParam(name = "dealers") List<DealerVO> dealers,
			@WebParam(name = "customer") CustomerVO customer,
			@WebParam(name = "postalCode") PostalCodeVO postalCode,
			@WebParam(name = "services") List<ServiceVO> services,
			@WebParam(name = "date") Date date,
			@WebParam(name = "serviceHour") ServiceHourVO serviceHour,
			@WebParam(name = "buildingCode") String buildingCode) throws BusinessException{
		return allocatorFacade.getSkillsRatesDealers(dealers, customer, postalCode, services, date, serviceHour, buildingCode);
	}*/
	
	/**
	 * Caso de Uso ADS - 09 - Evaluar Habilidad Capacidad.
	 * 
	 * La finalidad de este método es definir las compañías instaladoras 
	 * que tiene capacidad disponible para atender un servicio 
	 * para una localidad o zona determinada en una fecha jornada determinada.
	 *  
	 * @param dealers lista de dealers pre-seleccionados para realizarle la evaluación de habilidad capacidad
	 * @param customer información del cliente asociado a la work order que se desea atender
	 * @param postalCode información del código postal en donde se realizará la prestación de los servicios
	 * @param services Lista con los servicios que serán prestados en la orden de trabajo
	 * @param date fecha en la que se realizará la prestación de los servicios al cliente
	 * @param serviceHour jornada en la que se llevará a cabo la atención de los servicios de la work order
	 * @param buildingCode código del edificio en donde se realizará la prestación de los servicios
	 * @return List<DealerVO> lista con los dealers que pasaron la evaluación de habilidad capacidad 
	 * @throws BusinessException en caso de error al realizar la evaluación
	 * a continuación los códigos de error:<br>
     * <code>sdii_CODE_constraint_violation</code> En caso que se viole una restricción definida en la persistencia<br>
     * <code>sdii_CODE_error_data</code> En caso que se viole una regla de datos de la persistencia<br>
     * <code>sdii_CODE_generic_jdbc</code> En caso de error genérico relacionado con el componente de conexión a la base de datos<br>
     * <code>sdii_CODE_jdbc_connection</code> En caso de error al tratar de conectarse con la base de datos<br>
     * <code>sdii_CODE_sql_grammar</code> En caso de error relacionado con la gramática para ejecutar el comando en la base de datos<br>
     * <code>sdii_CODE_error_service</code> En caso de error al tratar de obtener el valor de una propiedad o un error inesperado en la capa de acceso a datos<br>
     * <code>sdii_CODE_unknow_error</code> En caso de un error inesperado en la capa de negocio<br>
	 * <code>sdii_CODE_params_null_or_missed</code> En caso que no se encuentren los parámetros necesarios para la ejecución de la operación<br>
	 * <code>sdii_CODE_dealer_is_not_allocable</code> En caso que se encuentre dentro de la lista de parámetros un dealer al que no se le permite asignación de work orders<br>
	 * <code>sdii_CODE_system_parameter_not_found</code> En caso que no se encuentre el parámetro del sistema "sdii_CODE_sys_param_back_log"<br>
	 * 
	 */
	/*
	@WebMethod(operationName = "getDealersByCapacityAndPostalCode", action = "getDealersByCapacityAndPostalCode", exclude=true)
	public List<DealerVO> getDealersByCapacityAndPostalCode(
			@WebParam(name = "dealers") List<DealerVO> dealers,
			@WebParam(name = "customer") CustomerVO customer,
			@WebParam(name = "postalCode") PostalCodeVO postalCode,
			@WebParam(name = "services") List<ServiceVO> services,
			@WebParam(name = "date") Date date,
			@WebParam(name = "serviceHour") ServiceHourVO serviceHour,
			@WebParam(name = "buildingCode") String buildingCode) throws BusinessException{
		return allocatorFacade.getDealersByCapacityAndPostalCode(dealers, customer, postalCode, services, date, serviceHour, buildingCode);
	}*/
	
	/**
	 * Caso de Uso ADS - 02 - Asignar Compañía Instaladora.
	 * 
	 * La finalidad de este método es asignar una compañía instaladora 
	 * en base a una fecha y jornada previamente establecidas.
	 * 
	 * @param workOrder - CustomerVO información del cliente asociado a la work order que se desea atender
	 * @return DealerVO Retorna el dealer seleccionado para realizar la asignación
	 * @throws BusinessException en caso de error al tratar de asignar la orden de trabajo
	 * a continuación los códigos de error:<br>
     * <code>sdii_CODE_constraint_violation</code> En caso que se viole una restricción definida en la persistencia<br>
     * <code>sdii_CODE_error_data</code> En caso que se viole una regla de datos de la persistencia<br>
     * <code>sdii_CODE_generic_jdbc</code> En caso de error genérico relacionado con el componente de conexión a la base de datos<br>
     * <code>sdii_CODE_jdbc_connection</code> En caso de error al tratar de conectarse con la base de datos<br>
     * <code>sdii_CODE_sql_grammar</code> En caso de error relacionado con la gramática para ejecutar el comando en la base de datos<br>
     * <code>sdii_CODE_error_service</code> En caso de error al tratar de obtener el valor de una propiedad o un error inesperado en la capa de acceso a datos<br>
     * <code>sdii_CODE_unknow_error</code> En caso de un error inesperado en la capa de negocio<br>
     * <code>sdii_CODE_params_null_or_missed</code> En caso que no se encuentren los parámetros necesarios para la ejecución de la operación<br>
     * <code>sdii_CODE_one_or_more_dealer_qty</code> En caso que se encuentre mas de un dealer que cumple las habilidades puntuables<br>
     * <code>sdii_CODE_none_dealer_qty</code> En caso que no se encuentre ningún dealer que cumple con las habilidades evaluadas<br>
	 * 
	 * @author gfandino
	 */
	@WebMethod(operationName = "assignDealer", action = "assignDealer")
	public DealerVO assignDealer(@WebParam(name = "workOrder")WorkOrderVO workOrder) throws BusinessException{
		return allocatorFacade.assignDealer(workOrder,null);
	}
	
	/**
	 * Caso de Uso ADS - 04 - Compañías que cumplen Habilidades Eliminantes.
	 * 
	 * La finalidad de este método es determinar, dado un conjunto de compañías instaladoras, 
	 * cuales tienen las habilidades eliminantes establecidas en el sistema.
	 * 
	 * @param dealers lista de dealers pre-seleccionados para realizarle la evaluación habilidades eliminantes
	 * @param customer información del cliente asociado a la work order que se desea atender
	 * @param postalCode información del código postal en donde se realizará la prestación de los servicios
	 * @param services Lista con los servicios que serán prestados en la orden de trabajo
	 * @param date fecha en la que se realizará la prestación de los servicios al cliente
	 * @param serviceHour jornada en la que se llevará a cabo la atención de los servicios de la work order
	 * @param buildingCode código del edificio en donde se realizará la prestación de los servicios
	 * @param country - Country País en el que se llevará a cabo la prestación de los servicios
	 * @return List<DealerVO> lista con los dealers que cumplen las habilidades eliminantes
	 * @throws BusinessException En caso de error al establecer las compañías que cumplen habilidades eliminantes
	 * a continuación los códigos de error:<br>
     * <code>sdii_CODE_constraint_violation</code> En caso que se viole una restricción definida en la persistencia<br>
     * <code>sdii_CODE_error_data</code> En caso que se viole una regla de datos de la persistencia<br>
     * <code>sdii_CODE_generic_jdbc</code> En caso de error genérico relacionado con el componente de conexión a la base de datos<br>
     * <code>sdii_CODE_jdbc_connection</code> En caso de error al tratar de conectarse con la base de datos<br>
     * <code>sdii_CODE_sql_grammar</code> En caso de error relacionado con la gramática para ejecutar el comando en la base de datos<br>
     * <code>sdii_CODE_error_service</code> En caso de error al tratar de obtener el valor de una propiedad o un error inesperado en la capa de acceso a datos<br>
     * <code>sdii_CODE_unknow_error</code> En caso de un error inesperado en la capa de negocio<br>
	 * <code>sdii_CODE_params_null_or_missed</code> En caso que no se encuentren los parámetros necesarios para la ejecución de la operación<br>
	 * 
	 * @author gfandino
	 */
	/*
	@WebMethod(operationName = "getEliminanteSkillsDealers", action = "getEliminanteSkillsDealers", exclude=true)
	public List<DealerVO> getEliminanteSkillsDealers(
			@WebParam(name = "dealers")List<DealerVO> dealers, 
			@WebParam(name = "customer")CustomerVO customer, 
			@WebParam(name = "postalCode")PostalCodeVO postalCode, 
			@WebParam(name = "services")List<ServiceVO> services, 
			@WebParam(name = "date")Date date, 
			@WebParam(name = "serviceHour")ServiceHourVO serviceHour, 
			@WebParam(name = "buildingCode")String buildingCode,
			@WebParam(name = "country")CountryVO country) throws BusinessException{
		return allocatorFacade.getEliminanteSkillsDealers(dealers, customer, postalCode, services, date, serviceHour, buildingCode,country);
	}*/
	
	/**
	 * Caso de Uso ADS - 08 - Evaluar Habilidad Tipo de Servicio.
	 * 
	 * La finalidad de este método es definir las compañías instaladoras 
	 * que prestan unos servicios específicos para un código postal dado.
	 * 
	 * @param dealers lista de dealers pre-seleccionados para realizarle la evaluación habilidad tipo servicio
	 * @param customer información del cliente asociado a la work order que se desea atender
	 * @param postalCode información del código postal en donde se realizará la prestación de los servicios
	 * @param services Lista con los servicios que serán prestados en la orden de trabajo
	 * @param date fecha en la que se realizará la prestación de los servicios al cliente
	 * @param serviceHour jornada en la que se llevará a cabo la atención de los servicios de la work order
	 * @param buildingCode código del edificio en donde se realizará la prestación de los servicios
	 * @return List<DealerVO> lista con los dealers que cumplen la habilidad tipo servicio
	 * @throws BusinessException En caso de error al tratar de evaluar la habilidad tipo servicio,
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
	 * @author jvelezm
	 */
	/*
	@WebMethod(operationName = "getDealersByServicesAndPostalCode", action = "getDealersByServicesAndPostalCode", exclude=true)
	public List<DealerVO> getDealersByServicesAndPostalCode(
			@WebParam(name = "dealers")List<DealerVO> dealers, 
			@WebParam(name = "customer")CustomerVO customer, 
			@WebParam(name = "postalCode")PostalCodeVO postalCode, 
			@WebParam(name = "services")List<ServiceVO> services, 
			@WebParam(name = "date")Date date, 
			@WebParam(name = "serviceHour")ServiceHourVO serviceHour, 
			@WebParam(name = "buildingCode")String buildingCode) throws BusinessException{
		return allocatorFacade.getDealersByServicesAndPostalCode(dealers, customer, postalCode, services, date, serviceHour, buildingCode);
	}*/
	
	/**
	 * Caso de Uso ADS - 10 - Evaluar Habilidad Tipo de Cliente.
	 * 
	 * La finalidad de este método es definir las compañías instaladoras 
	 * que pueden brindar atención a un tipo determinado de cliente en una zona determinada.
	 * 
	 * @param dealers lista de dealers pre-seleccionados para realizarle la evaluación habilidad tipo cliente
	 * @param customer información del cliente asociado a la work order que se desea atender
	 * @param postalCode información del código postal en donde se realizará la prestación de los servicios
	 * @param services Lista con los servicios que serán prestados en la orden de trabajo
	 * @param date fecha en la que se realizará la prestación de los servicios al cliente
	 * @param serviceHour jornada en la que se llevará a cabo la atención de los servicios de la work order
	 * @param buildingCode código del edificio en donde se realizará la prestación de los servicios
	 * @return List<DealerVO> lista con los dealers que cumplen la habilidad tipo cliente
	 * @throws BusinessException En caso de error al tratar de evaluar la habilidad tipo cliente,
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
	/*
	@WebMethod(operationName = "getDealersByCustomerTypeAndPostalCode", action = "getDealersByCustomerTypeAndPostalCode", exclude=true)
	public List<DealerVO> getDealersByCustomerTypeAndPostalCode(
			@WebParam(name = "dealers")List<DealerVO> dealers, 
			@WebParam(name = "customer")CustomerVO customer, 
			@WebParam(name = "postalCode")PostalCodeVO postalCode, 
			@WebParam(name = "services")List<ServiceVO> services, 
			@WebParam(name = "date")Date date, 
			@WebParam(name = "serviceHour")ServiceHourVO serviceHour, 
			@WebParam(name = "buildingCode")String buildingCode) throws BusinessException{
		return allocatorFacade.getDealersByCustomerTypeAndPostalCode(dealers, customer, postalCode, services, date, serviceHour, buildingCode);
	}*/
	
	/**
	 * Caso de Uso ADS - 12 - Evaluar Habilidad Atendió Ultimo Servicio.
	 * 
	 * La finalidad de este método es definir para un servicio 
	 * en un cliente existente si la compañía que realizo el último servicio 
	 * realizará la atención de este servicio.
	 * 
	 * @param dealers lista de dealers pre-seleccionados para realizarle la evaluación habilidad atendió último servicio
	 * @param customer información del cliente asociado a la work order que se desea atender
	 * @param postalCode información del código postal en donde se realizará la prestación de los servicios
	 * @param services Lista con los servicios que serán prestados en la orden de trabajo
	 * @param date fecha en la que se realizará la prestación de los servicios al cliente
	 * @param serviceHour jornada en la que se llevará a cabo la atención de los servicios de la work order
	 * @param buildingCode código del edificio en donde se realizará la prestación de los servicios
	 * @return List<DealerVO> lista con los dealers que cumplen la habilidad atendió último servicio
	 * @throws BusinessException En caso de error al tratar de evaluar la habilidad atendió último servicio,
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
	@WebMethod(operationName = "getDealerByLastServiceAttend", action = "getDealerByLastServiceAttend", exclude=true)
	public List<DealerVO> getDealerByLastServiceAttend(
			@WebParam(name = "dealers")List<DealerVO> dealers, 
			@WebParam(name = "customer")CustomerVO customer,
			@WebParam(name = "postalCode")PostalCodeVO postalCode, 
			@WebParam(name = "services")List<ServiceVO> services, 
			@WebParam(name = "date")Date date, 
			@WebParam(name = "serviceHour")ServiceHourVO serviceHour, 
			@WebParam(name = "buildingCode")String buildingCode) throws BusinessException{
		return allocatorFacade.getDealerByLastServiceAttend(dealers, customer, postalCode, services, date, serviceHour, buildingCode);
	}
	
	/**
	 * Obtiene el parametro de los dias de capacidad
	 * @param country Obtiene la capacidad de días para un país determinado
	 * @return Long capacidad de días para el país especificado
	 * @throws BusinessException en caso de error al consultar la capacidad de días para el país,
	 * a continuación los códigos de error:<br>
     * <code>sdii_CODE_constraint_violation</code> En caso que se viole una restricción definida en la persistencia<br>
     * <code>sdii_CODE_error_data</code> En caso que se viole una regla de datos de la persistencia<br>
     * <code>sdii_CODE_generic_jdbc</code> En caso de error genérico relacionado con el componente de conexión a la base de datos<br>
     * <code>sdii_CODE_jdbc_connection</code> En caso de error al tratar de conectarse con la base de datos<br>
     * <code>sdii_CODE_sql_grammar</code> En caso de error relacionado con la gramática para ejecutar el comando en la base de datos<br>
     * <code>sdii_CODE_error_service</code> En caso de error al tratar de obtener el valor de una propiedad o un error inesperado en la capa de acceso a datos<br>
     * <code>sdii_CODE_unknow_error</code> En caso de un error inesperado en la capa de negocio<br>
	 * <code>sdii_CODE_params_null_or_missed</code> En caso que no se encuentren los parámetros necesarios para la ejecución de la operación<br>
	 */
	@WebMethod(operationName = "getCapacityDaysParam", action = "getCapacityDaysParam")
	public long getCapacityDaysParam(
			@WebParam(name = "country") CountryVO country) throws BusinessException{
		Long days = allocatorFacade.getCapacityDaysParam(country);
		return days!=null?days.longValue():-1;
	}
	
	
	/**
	 * Caso de Uso ADS - 15 - Evaluar Habilidad Atiende Edificio.
	 * 
	 * La finalidad de este método es asignar para un servicio la compañía instaladora 
	 * que realizó el último servicio a ese edificio.
	 * 
	 * @param dealers lista de dealers pre-seleccionados para realizarle la evaluación habilidad atiende edificio
	 * @param customer información del cliente asociado a la work order que se desea atender
	 * @param postalCode información del código postal en donde se realizará la prestación de los servicios
	 * @param services Lista con los servicios que serán prestados en la orden de trabajo
	 * @param date fecha en la que se realizará la prestación de los servicios al cliente
	 * @param serviceHour jornada en la que se llevará a cabo la atención de los servicios de la work order
	 * @param buildingCode código del edificio en donde se realizará la prestación de los servicios
	 * @return List<DealerVO> lista con los dealers que cumplen la habilidad atiende edificio
	 * @throws BusinessException En caso de error al tratar de evaluar la habilidad atiende edificio,
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
	@WebMethod(operationName = "getDealerByBuildingAttend", action = "getDealerByBuildingAttend", exclude=true)
	public DealerVO getDealerByBuildingAttend(
			@WebParam(name = "dealers")List<DealerVO> dealers, 
			@WebParam(name = "customer")CustomerVO customer,
			@WebParam(name = "postalCode")PostalCodeVO postalCode, 
			@WebParam(name = "services")List<ServiceVO> services, 
			@WebParam(name = "date")Date date, 
			@WebParam(name = "serviceHour")ServiceHourVO serviceHour, 
			@WebParam(name = "buildingCode")String buildingCode) throws BusinessException{
		return allocatorFacade.getDealerByBuildingAttend(dealers, customer, postalCode, services, date, serviceHour, buildingCode);
	}
	
	/**
	 * Caso de Uso ADS - 06 - Asignación Por Rotación con porcentaje de participación.
	 * 
	 * La finalidad de este método es determinar, dado un conjunto de compañías instaladoras, 
	 * y de acuerdo a un porcentaje de participación en la zona, 
	 * cuales cual es la compañía que debe prestar el servicio.
	 * 
	 * @param dealers lista de dealers pre-seleccionados para realizarle la asignación por rotación con porcentaje de participación
	 * @param customer información del cliente asociado a la work order que se desea atender
	 * @param postalCode información del código postal en donde se realizará la prestación de los servicios
	 * @param services Lista con los servicios que serán prestados en la orden de trabajo
	 * @param date fecha en la que se realizará la prestación de los servicios al cliente
	 * @param serviceHour jornada en la que se llevará a cabo la atención de los servicios de la work order
	 * @param buildingCode código del edificio en donde se realizará la prestación de los servicios
	 * @return List<DealerVO> lista con los dealers que cumplen la asignación por rotación con porcentaje de participación
	 * @throws BusinessException En caso de error al tratar de evaluar la asignación por rotación con porcentaje de participación
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
	/*
	@WebMethod(operationName = "getDealersByRotation", action = "getDealersByRotation", exclude=true)
	public List<DealerVO> getDealersByRotation(
			@WebParam(name = "dealers") List<DealerVO> dealers, 
			@WebParam(name = "customer") CustomerVO customer, 
			@WebParam(name = "postalCode") PostalCodeVO postalCode, 
			@WebParam(name = "services") List<ServiceVO> services, 
			@WebParam(name = "date") Date date, 
			@WebParam(name = "serviceHour") ServiceHourVO serviceHour, 
			@WebParam(name = "buildingCode") String buildingCode) throws BusinessException{
		return allocatorFacade.getDealersByRotation(dealers, customer, postalCode, services, date, serviceHour, buildingCode);
	}*/
	
//	/**
//	 * Metodo: Realiza las validaciones previas a la asignación de un work order así:<br>
//	 * 1. La fecha ingresada debe ser válida e igual o posterior a la fecha del día siguiente<br>
//	 * 2. La jornada debe corresponder a una jornada registrada en el sistema<br>
//	 * 3. Los servicios deben corresponder a servicios registrados en el sistema<br>
//	 * 4. El Código Postal debe venir en la estructura de códigos correcta<br>
//	 * 5. Validar que la work order pueda cambiar a estado "asignada" <br>
//	 * @param wo objeto con la información de la work order solo es necesario el id
//	 * @param agendaDate fecha de agendamiento de la work order
//	 * @param serviceHour jornada en la que se realizará la atención de la work order solo es necesario el id
//	 * @throws BusinessException En caso de error al realizar las validaciones.
//	 * a continuación los códigos de error:<br>
//     * <code>sdii_CODE_constraint_violation</code> En caso que se viole una restricción definida en la persistencia<br>
//     * <code>sdii_CODE_error_data</code> En caso que se viole una regla de datos de la persistencia<br>
//     * <code>sdii_CODE_generic_jdbc</code> En caso de error genérico relacionado con el componente de conexión a la base de datos<br>
//     * <code>sdii_CODE_jdbc_connection</code> En caso de error al tratar de conectarse con la base de datos<br>
//     * <code>sdii_CODE_sql_grammar</code> En caso de error relacionado con la gramática para ejecutar el comando en la base de datos<br>
//     * <code>sdii_CODE_error_service</code> En caso de error al tratar de obtener el valor de una propiedad o un error inesperado en la capa de acceso a datos<br>
//     * <code>sdii_CODE_unknow_error</code> En caso de un error inesperado en la capa de negocio<br>
//	 * <code>sdii_CODE_params_null_or_missed</code> En caso que no se encuentren los parámetros necesarios para la ejecución de la operación<br>
//	 * @author jjimenezh
//	 */
//	@WebMethod(operationName = "doWorkorderValidations", action = "doWorkorderValidations")
//	public void doWorkorderValidations(
//			@WebParam(name = "workOrder") WorkOrderVO wo, 
//			@WebParam(name = "agendaDate") Date agendaDate, 
//			@WebParam(name = "serviceHour") ServiceHourVO serviceHour)throws BusinessException{
//		allocatorFacade.doWorkorderValidations(wo, agendaDate, serviceHour);
//	}
	
	
	@WebMethod(operationName = "changeProcessStatus2WorkOrders", action = "changeProcessStatus2WorkOrders", exclude=true)
	public void changeProcessStatus2WorkOrders(
			@WebParam(name = "workOrders") List<WorkOrderVO> workOrders, String processStatusCode)throws BusinessException{
		allocatorFacade.changeProcessStatus2WorkOrders(workOrders, processStatusCode);
	}
	
	@WebMethod(operationName = "changeProcessStatus2WorkOrder", action = "changeProcessStatus2WorkOrder", exclude=true)
	public void changeProcessStatus2WorkOrder(
			@WebParam(name = "workOrder") WorkOrderVO workOrder, String processStatusCode)throws BusinessException{
		allocatorFacade.changeProcessStatus2WorkOrder(workOrder, processStatusCode);
	}
	
	@WebMethod(operationName = "allocWorkOrders", action = "allocWorkOrders")
	public void allocWorkOrders(@WebParam(name = "countryId") Long countryId)throws BusinessException{
		//try {
			allocatorFacade.allocWorkOrders(countryId);
			/*woInfoEsbServiceFacadeLocal.processCoreAndAllocator(countryId, 
					CodesBusinessEntityEnum.SDII_WO_INFO_ESB_ALLOCATOR.getCodeEntity());*/
		/*} catch (PropertiesException e) {
			throw new BusinessException(e.getMessage(), e);
		}*/
	}
	
	
}
