/**
 * Creado 14/06/2011 09:51:05
 */
package co.com.directv.sdii.assign.assignment;

import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.assign.assignment.dto.SkillEvaluationDTO;
import co.com.directv.sdii.assign.assignment.skill.exception.AssignmentSkillException;
import co.com.directv.sdii.assign.kpi.DealerIndicator;
import co.com.directv.sdii.assign.kpi.dto.DealerIndicatorDTO;
import co.com.directv.sdii.assign.schedule.dto.DayServiceHourWorkLoad;
import co.com.directv.sdii.assign.schedule.dto.DealerWorkLoadCapacityCriteriaDTO;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.exceptions.PropertiesException;
import co.com.directv.sdii.model.dto.WorkOrderCSRDTO;
import co.com.directv.sdii.model.vo.DealerVO;
import co.com.directv.sdii.ws.model.dto.AssignRequestDTO;
import co.com.directv.sdii.ws.model.dto.AssignResponseDTO;
import co.com.directv.sdii.ws.model.dto.AssignScheduleRequestDTO;
import co.com.directv.sdii.ws.model.dto.AssignScheduleResponseDTO;

/**
 * Define las operaciones de asignación 
 * 
 * Fecha de Creación: 14/06/2011
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see     
 */
@Local
public interface AssignmentFacade {

	/**
	 * Metodo: Evalúa los parámetros de entrada y selecciona un dealer
	 * @param request información con los parámetros de entrada
	 * @return información de respuesta con la compañía seleccionada
	 * @throws BusinessException en caso de error
	 * @author jjimenezh
	 */
	public AssignResponseDTO assignDealer(AssignRequestDTO request)throws BusinessException;
	
	/**
	 * Metodo: Calcula la agenda dados los parámetros de entrada
	 * @param request información de entrada
	 * @return información de respuesta con los parámetros de entrada
	 * @throws BusinessException en caso de error
	 * @author jjimenezh
	 */
	public AssignScheduleResponseDTO getSchedule(AssignScheduleRequestDTO request) throws BusinessException;
	
	/**
	 * Metodo: Calcula la agenda dados los parámetros de entrada WorkOrder y fecha de agendamiento
	 * @param request información de entrada
	 * @return información de respuesta con los parámetros de entrada
	 * @throws BusinessException en caso de error
	 * @throws PropertiesException
	 */
	public AssignScheduleResponseDTO getScheduleCSR(AssignScheduleRequestDTO request) throws BusinessException, PropertiesException;
	
	/**
	 * Metodo: Obtiene Los datos de la WO de ibs
	 * @param request información de entrada
	 * @return información de respuesta con los parámetros de entrada
	 * @throws BusinessException en caso de error
	 * @throws PropertiesException
	 */
	public AssignRequestDTO getPreScheduleCSR(AssignScheduleRequestDTO request) throws BusinessException, PropertiesException;
	
	
	/**
	 * Metodo: Realiza la evaluación de una habilidad específica dado los parámetros y su código
	 * @param parameters parámetros para llevar a cabo la evaluación de la habilidad
	 * @param skillCode código de la habilidad 
	 * @return lista de las compañías que cumplen la habilidad
	 * @throws AssignmentSkillException En caso de error al realizar la evaluación de la habilidad
	 * @author jjimenezh
	 */
	public List<DealerVO> doSkillEvaluation(SkillEvaluationDTO parameters, String skillCode)throws AssignmentSkillException;

	/**
     * Metodo: Realiza el cálculo de un KPI para un dealer y lo persiste.
     * Este método crea una nueva transacción en cada llamado.
     * @param dealerIndicator indicador a calcular
     * @param dealerIndicatorDto dto que encapsula información del dealer
     * @throws BusinessException
     * @author wjimenez
     */
	public void calculateAndPersistDealerIndicator(DealerIndicator dealerIndicator,
			DealerIndicatorDTO dealerIndicatorDto) throws BusinessException;
	
	
	/**
	 * Metodo: Permite agendar una workOrder consultada por el CSR
	 * @param request
	 * @throws BusinessException <tipo> <descripcion>
	 * @author
	 */
	public void agendaWorkOrdersCSR(WorkOrderCSRDTO request)throws BusinessException;
	
	/**
	 * Metodo: Permite almacenar la traza de asignador de una workOrderCSR
	 * @param id
	 * @param trace
	 * @throws BusinessException <tipo> <descripcion>
	 * @author
	 */
	public void traceAssignmentWorkOrderCSR(Long id, String trace) throws BusinessException;
	
	/**
	 * Metodo: Permite consultar si se agendan las wo se sobreagenda el dealer
	 * @param dayServiceHourWorkLoad
	 * @param quantityWorkOrder
	 * @return
	 * @throws BusinessException <tipo> <descripcion>
	 * @author
	 */
	public DealerWorkLoadCapacityCriteriaDTO checkAboveScheduledCSR(DayServiceHourWorkLoad dayServiceHourWorkLoad,int quantityWorkOrder) throws BusinessException;
	
}
