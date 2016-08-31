
/**
 * Creado 10/06/2011 16:21:52
 */
package co.com.directv.sdii.ws.business.assign;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

import co.com.directv.sdii.assign.schedule.dto.DayServiceHourWorkLoad;
import co.com.directv.sdii.assign.schedule.dto.DealerWorkLoadCapacityCriteriaDTO;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.exceptions.PropertiesException;
import co.com.directv.sdii.model.dto.WorkOrderCSRDTO;
import co.com.directv.sdii.ws.model.dto.AssignRequestDTO;
import co.com.directv.sdii.ws.model.dto.AssignScheduleRequestDTO;
import co.com.directv.sdii.ws.model.dto.AssignScheduleResponseDTO;

/**
 * Define las operaciones para la generación de la agenda para los dealers
 * 
 * Fecha de Creación: 10/06/2011
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see     
 */
@WebService(name="ScheduleWS",targetNamespace="http://assign.business.ws.sdii.directv.com.co/")
public interface IScheduleWS {

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
	@WebMethod(operationName="getSchedule", action="getSchedule", exclude = false)
	public AssignScheduleResponseDTO getSchedule(@WebParam(name="request")AssignScheduleRequestDTO request)throws BusinessException;
	
	/**
	 * Metodo: Obtiene la disponibilidad de agenda para el proceso de agendamiento del CSR
	 * @param request
	 * @return
	 * @throws BusinessException
	 * @throws PropertiesException <tipo> <descripcion>
	 * @author
	 */
	@WebMethod(operationName="getScheduleCSR", action="getScheduleCSR", exclude = false)
	public AssignScheduleResponseDTO getScheduleCSR(@WebParam(name="request")AssignScheduleRequestDTO request)throws BusinessException, PropertiesException;
	
	/**
	 * Metodo: Obtiene Los datos de la WO de ibs
	 * @param request
	 * @return
	 * @throws BusinessException
	 * @throws PropertiesException <tipo> <descripcion>
	 * @author
	 */
	@WebMethod(operationName="getPreScheduleCSR", action="getPreScheduleCSR", exclude = false)
	public AssignRequestDTO getPreScheduleCSR(@WebParam(name="request")AssignScheduleRequestDTO request)throws BusinessException, PropertiesException;
	
	/**
	 * 
	 * Metodo: Realiza el agendamiento del CSR
	 * @param List<TrayWOManagmentDTO> Lista de objetos que contienen informacion necesaria para el agendamiento masivo
	 * @throws BusinessException <tipo> <descripcion>
	 * @author cduarte
	 */
	@WebMethod(operationName = "agendaWorkOrdersCSR", action = "agendaWorkOrdersCSR")
	public void agendaWorkOrdersCSR(@WebParam(name = "request") WorkOrderCSRDTO request) throws BusinessException;
	
	/**
	 * Metodo: Permite consultar si se agendan las wo se sobreagenda el dealer
	 * @param dayServiceHourWorkLoad
	 * @param quantityWorkOrder
	 * @return
	 * @throws BusinessException <tipo> <descripcion>
	 * @author
	 */
	@WebMethod(operationName = "checkAboveScheduledCSR", 
			   action = "checkAboveScheduledCSR")
	public DealerWorkLoadCapacityCriteriaDTO checkAboveScheduledCSR(@WebParam(name = "dayServiceHourWorkLoad") DayServiceHourWorkLoad dayServiceHourWorkLoad,@WebParam(name = "quantityWorkOrder")  int quantityWorkOrder) throws BusinessException;
	
	
}
