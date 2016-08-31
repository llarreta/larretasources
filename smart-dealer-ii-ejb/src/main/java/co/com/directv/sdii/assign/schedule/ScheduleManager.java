package co.com.directv.sdii.assign.schedule;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import co.com.directv.sdii.assign.assignment.AssignmentFacadeLocator;
import co.com.directv.sdii.assign.assignment.dto.SkillEvaluationDTO;
import co.com.directv.sdii.assign.schedule.dto.DayServiceHourWorkLoad;
import co.com.directv.sdii.assign.schedule.dto.DayWorkLoad;
import co.com.directv.sdii.assign.schedule.dto.ScheduleColorsConfig;
import co.com.directv.sdii.assign.schedule.dto.WorkLoad;
import co.com.directv.sdii.common.enumerations.CodesBusinessEntityEnum;
import co.com.directv.sdii.common.enumerations.ErrorBusinessMessages;
import co.com.directv.sdii.common.util.UtilsBusiness;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.exceptions.PropertiesException;
import co.com.directv.sdii.facade.assign.CoreAssignmentFacadeRemote;
import co.com.directv.sdii.model.pojo.WorkOrder;
import co.com.directv.sdii.model.pojo.WorkorderStatus;
import co.com.directv.sdii.model.vo.DealerVO;
import co.com.directv.sdii.model.vo.SystemParameterVO;
import co.com.directv.sdii.model.vo.WorkOrderVO;
import co.com.directv.sdii.ws.model.dto.AssignRequestDTO;
import co.com.directv.sdii.ws.model.dto.AssignScheduleRequestDTO;

/**
 * Se encarga de generar los artefactos que servirán para mostrar la agenda de los
 * dealers que tienen capacidad de atención durante ciertos días
 * 
 * Se establece como restricción que las Giras no se tendrán en cuenta para
 * mostrar la agenda.
 * 
 * Para consultar la agenda disponible de días:
 * 1. Se deben ejecutar las habilidades configuradas para agenda en el modo de
 * agenda.
 * 2. Con las compañías seleccionadas se debe ejecutar un ciclo que itere sobre
 * los n días configurados para el cálculo de la agenda y por cada día se debe
 * iterar sobre las jornadas activas en el país, para con cada compañía basado en
 * la configuración de capacidad disponible por día por super categoría de
 * servicio que coincida con los datos de entrada o de consulta, se consulta la
 * cantidad de Work Orders que tiene agendadas la compañía para la fecha y jornada
 * que se está evaluando y si ese número contra la ponderación de las que puede
 * realizar por día para esa super categoría está en el rango de umbrales configurados
 * por color (se mostrará con color la agenda de esa compañía)
 * 3. Luego de calcular las agendas de todas las compañías se unirán y de acuerdo
 * al porcentaje de colores, se mostrará un solo color para esa casilla de dia
 * jornada.
 * @author jjimenezh
 * @version 1.0,  &nbsp; 26-may-2011 15:11:48
 */
public final class ScheduleManager {

	private static ScheduleManager mySelf;
	
	private ScheduleManager(){
		
	}
	
	public static ScheduleManager getInstance(){
		if(mySelf == null){
			mySelf = new ScheduleManager();
		}
		return mySelf;
	}

	public void finalize() throws Throwable {

	}
	
	/**
	 * consulta la información de agenda para un dealer específico, se asume que el
	 * dealer puede prestar el servicio debido a que la Work Order ya le ha sido
	 * asignada
	 * 
	 * @param skillEvaluationDto    información para realizar el cálculo de la agenda
	 * disponible
	 * @param startDate    fecha de inicio de la consulta de la agenda para el dealer
	 * @param dealerId    identificador de la compañía instaladora
	 * @throws BusinessException 
	 */
	public WorkLoad getScheduleForOneDealer(SkillEvaluationDTO skillEvaluationDto, java.util.Date startDate, Long dealerId) throws BusinessException{

		//Se chequea si el dealer es permanente
		checkDealersInMicrozoneWithTypePerm(dealerId,
							                skillEvaluationDto.getExecutionMode(),
							                skillEvaluationDto.getPostalCode(), 
							                skillEvaluationDto.getCountryIso2Code());

		ScheduleAssembler scheduleAssembler = new ScheduleAssembler();
		Long countryId = skillEvaluationDto.getCountryId();
		int dayCountToGenerateSchedule = getDayCountToGenerateSchedule(countryId,startDate);
		WorkLoad result = scheduleAssembler.assembleScheduleForOneDealer(dealerId, skillEvaluationDto, startDate, dayCountToGenerateSchedule);
		assignColors2WorkLoad(result, skillEvaluationDto.getCountryId(), skillEvaluationDto.getServiceSupercategoyId(),skillEvaluationDto.isCSR());
		return result;
	}
	
	private void checkDealersInMicrozoneWithTypePerm(Long dealerId,
			                                         String executionMode,
			                                         String postalCode,
			                                         String countryIso2Code) throws BusinessException {
		
		//Se consulta si el dealer es permanente
		boolean checkDealerPerm = AssignmentFacadeLocator.getInstance().getCoreAssignmentFacade().checkDealersInMicrozoneWithTypePerm(postalCode, 
		        																						                              countryIso2Code,
																										                              dealerId);
		//El dealer debe ser permanente
		if(!checkDealerPerm){
			throw new BusinessException(ErrorBusinessMessages.ALLOCATOR_AL049.getCode(), ErrorBusinessMessages.ALLOCATOR_AL049.getMessage(new String[]{"" + postalCode}),Arrays.asList(new String[]{"" + postalCode}));
		}
	}
	
	/**
	 * se encarga de consultar la agenda para las compañías instaladoras de acuerdo
	 * con la capacidad de atender ciertos servicios a para un cliente específico
	 * 
	 * @param skillEvaluationDto    información del cliente para realizar la
	 * evaluación de la disponibilidad de atención en las compañías instaladoras
	 * activas
	 * @param startDate    fecha de inicio para consultar la agenda
	 * @throws BusinessException En caso de error al realizar el cálculo
	 */
	public WorkLoad getScheduleForAllDealers(SkillEvaluationDTO skillEvaluationDto, java.util.Date startDate) throws BusinessException{
		ScheduleAssembler scheduleAssembler = new ScheduleAssembler();
		Long countryId = skillEvaluationDto.getCountryId();
		int dayCountToGenerateSchedule = getDayCountToGenerateSchedule(countryId,startDate);
		WorkLoad result = scheduleAssembler.assembleScheduleForAllDealers(skillEvaluationDto, startDate, dayCountToGenerateSchedule);
		assignColors2WorkLoad(result, skillEvaluationDto.getCountryId(), skillEvaluationDto.getServiceSupercategoyId(),skillEvaluationDto.isCSR());
		
		return result;
	}

	/**
	 * Metodo encargado de consumir los servicios para la informacion de IBS
	 * de workorder
	 * 
	 * @param request
	 * @return assignRequestDTO
	 * @throws BusinessException En caso de error al realizar el proceso
	 */
	public AssignRequestDTO getAssignRequestForWorkOrder(AssignScheduleRequestDTO request,Long countryId) throws BusinessException, PropertiesException{
		AssignRequestDTO assignRequestDTO;
		CoreAssignmentFacadeRemote coreAssignmentFacade = AssignmentFacadeLocator.getInstance().getCoreAssignmentFacade();
		if(request.getStartingDate() != null 
		   && request.getServiceCharacteristics().getWoCode() != null 
		   && request.getServiceCharacteristics().getWoCode().length() > 0){
			
			assignRequestDTO = AssignmentFacadeLocator.getInstance().getManageWorkForceServiceBroker().getInfoCustomerWorkOrdersById(request.getServiceCharacteristics(), countryId);
			String codeDealerDummy=coreAssignmentFacade.getSystemParameterByCodeAndCountryId(CodesBusinessEntityEnum.SYSTEM_PARAM_DEALER_DUMMY_CODE.getCodeEntity(), countryId).getValue();
			
			Long actualStatusIbsWorkOrder = assignRequestDTO.getActualStatusId();
			WorkorderStatus workorderStatus = coreAssignmentFacade.getWorkorderStatusByIBS6StatusCode(String.valueOf(actualStatusIbsWorkOrder));
			if(workorderStatus!=null){
				assignRequestDTO.setActualStatusId(workorderStatus.getId());
				assignRequestDTO.setActualStatusCode(workorderStatus.getWoStateCode());
				assignRequestDTO.setActualStatusName(workorderStatus.getWoStateName());
				if(workorderStatus.getWoStateCode().equalsIgnoreCase(CodesBusinessEntityEnum.WORKORDER_STATUS_SCHEDULED.getCodeEntity())){
					WorkOrder workOrderPojo = coreAssignmentFacade.getWorkOrderByCode(request.getServiceCharacteristics().getWoCode());
					if(workOrderPojo==null){
						throw new BusinessException(ErrorBusinessMessages.ALLOCATOR_AL048.getCode(),ErrorBusinessMessages.ALLOCATOR_AL048.getMessage());
					}
						
				}
			}
			
			
			
			if(assignRequestDTO.getDealerIdIBS()!= null && !assignRequestDTO.getDealerIdIBS().equals(Long.parseLong(codeDealerDummy)))
				assignRequestDTO.setDealerId(coreAssignmentFacade.getDealerIdByDealerCode(assignRequestDTO.getDealerIdIBS()));
			
			DealerVO dealer = coreAssignmentFacade.getDealerByDealerCode(Long.parseLong(assignRequestDTO.getIbsSaleDealerCode()));
			if(dealer!=null){
				assignRequestDTO.setIbsSaleDealerName(dealer.getDealerName());
			}
			
		}else{
			assignRequestDTO = request.getServiceCharacteristics();
		}
		filterInfoWorkOrder(assignRequestDTO);
		
		if(!assignRequestDTO.isExternalSystem()){
			coreAssignmentFacade.sendMailCsr(assignRequestDTO, countryId);
		}

		return assignRequestDTO;
	}

	/**
	 * Metodo: Metodo encargado de validar la informacion
	 * y generar los errores en caso que no se deba realizar el proceso.
	 * @param assignRequestDTO 
	 * @throws BusinessException 
	 * @throws PropertiesException <tipo> <descripcion>
	 * @author
	 */
	private void filterInfoWorkOrder(AssignRequestDTO assignRequestDTO) throws BusinessException, PropertiesException{
		
		if(!assignRequestDTO.getActualStatusCode().equalsIgnoreCase(CodesBusinessEntityEnum.WORKORDER_STATUS_ACTIVE.getCodeEntity()) 
		    && !assignRequestDTO.getActualStatusCode().equalsIgnoreCase(CodesBusinessEntityEnum.WORKORDER_STATUS_ASSIGN.getCodeEntity()) 
		    && !assignRequestDTO.getActualStatusCode().equalsIgnoreCase(CodesBusinessEntityEnum.WORKORDER_STATUS_SCHEDULED.getCodeEntity()) 
		    && !assignRequestDTO.getActualStatusCode().equalsIgnoreCase(CodesBusinessEntityEnum.WORKORDER_STATUS_RESCHEDULED.getCodeEntity()) 
		    && !assignRequestDTO.getActualStatusCode().equalsIgnoreCase(CodesBusinessEntityEnum.WORKORDER_STATUS_REASSIGN.getCodeEntity())){
			
			//Si el estado en IBS es N-Pendiente/R-Pendiente verifico en HSP, si en HSP es N-Pendiente sigo, si es R-Pendiente lanza exception.
			if(assignRequestDTO.getWoCode() != null 
					&& ( assignRequestDTO.getActualStatusCode().equalsIgnoreCase(CodesBusinessEntityEnum.WORKORDER_STATUS_PENDING.getCodeEntity())
					|| assignRequestDTO.getActualStatusCode().equalsIgnoreCase(CodesBusinessEntityEnum.WORKORDER_STATUS_REJECTED.getCodeEntity())) ){				
				WorkOrderVO woVO = AssignmentFacadeLocator.getInstance().getCoreAssignmentFacade().getWorkOrderByCode(assignRequestDTO.getWoCode());
				if(woVO != null && 
						woVO.getWorkorderStatusByActualStatusId().getWoStateCode().equalsIgnoreCase(CodesBusinessEntityEnum.WORKORDER_STATUS_PENDING.getCodeEntity())){
					return;
				}
			}
			
			
	    	Object[] params = new Object[1];		
			params[0] = assignRequestDTO.getWoCode();
			
			throw new BusinessException(
					  ErrorBusinessMessages.WORKORDER_NOT_ACTIVE.getCode(),
					  ErrorBusinessMessages.WORKORDER_NOT_ACTIVE.getMessage(params), 
					      UtilsBusiness.getParametersWS(params));
			
		}
		
	}

	/**
	 * Metodo: Le asigna los colores al resultado de la evaluación de la carga de trabajo
	 * @param workLoad objeto que encapsula la respuesta de la carga de trabajo
	 * @author jjimenezh
	 * @throws BusinessException 
	 */
	private void assignColors2WorkLoad(WorkLoad workLoad, 
			                           Long countryId,
			                           Long serviceSuperCategoryByID,
			                           boolean isCSR) throws BusinessException {
		List<DayWorkLoad> dayWls = workLoad.getDayWorkLoads();
		if(dayWls == null || dayWls.isEmpty()){
			return;
		}
		
		ScheduleColorsConfig colorsConfig = AssignmentFacadeLocator.getInstance().getAllocatorFacade().loadScheduleColorsConfig(serviceSuperCategoryByID,countryId);
		
		for (DayWorkLoad dayWorkLoad : dayWls) {
			List<DayServiceHourWorkLoad> dayShWls = dayWorkLoad.getDayShWorkLoadList();
			if(dayShWls == null || dayShWls.isEmpty()){
				continue;
			}
			Double result = 0D;
			for (DayServiceHourWorkLoad dayServiceHourWorkLoad : dayShWls) {
				if(dayServiceHourWorkLoad.getMaxCapacity() == 0){
					result = 1D;
				}else{
					result = (new BigDecimal(dayServiceHourWorkLoad.getUsedCapacity()).divide(new BigDecimal(dayServiceHourWorkLoad.getMaxCapacity()), 2, RoundingMode.HALF_UP)).doubleValue();
				}
				
				calculateMaxCapacityCSR(dayServiceHourWorkLoad,
						                isCSR,
						                colorsConfig.getScheduleLimit());
				if(!isCSR){
					if(result < colorsConfig.getScheduleLimit()){
						dayServiceHourWorkLoad.setColor(colorsConfig.getAvailableColor());
					}else{
						dayServiceHourWorkLoad.setColor(colorsConfig.getUnavailableColor());
					}
				}else{
					if(dayServiceHourWorkLoad.getUsedCapacity() >= dayServiceHourWorkLoad.getMaxCapacity()){
						dayServiceHourWorkLoad.setColor(colorsConfig.getUnavailableColor());
					}else{
						dayServiceHourWorkLoad.setColor(colorsConfig.getAvailableColor());
					}
				}
				
			}
		}
	}
	
	/**
	 * Metodo: Se setea el calculo real y Si es CSR calcula el porcentaje para la visualizacion de la agenda
	 * @param dayServiceHourWorkLoad <tipo> <descripcion>
	 * @author
	 */
	private void calculateMaxCapacityCSR(DayServiceHourWorkLoad dayServiceHourWorkLoad,boolean isCSR,Double scheduleLimit){
		double maxCapacity = 0d;
		int scale = 0;
		dayServiceHourWorkLoad.setActualCalculation(scheduleLimit);
		if(isCSR){
			
			maxCapacity = (new BigDecimal(dayServiceHourWorkLoad.getMaxCapacity()).multiply(new BigDecimal(scheduleLimit))).doubleValue();
			BigDecimal big = UtilsBusiness.removeDecimal(maxCapacity,scale,RoundingMode.HALF_UP);
			dayServiceHourWorkLoad.setMaxCapacity(big.intValue());
			
		}
	}

	/**
	 * Metodo: Obtiene el número de días configurado para consultar la agenda de disponibilidad de servicios
	 * @param countryId identificador del país
	 * @return número de días a tener en cuenta para la consulta
	 * @throws BusinessException en caso de error al obtener el número de días
	 * @author jjimenezh
	 */
	private int getDayCountToGenerateSchedule(Long countryId,java.util.Date startDate) throws BusinessException{
		String parameterCode = null;
		try {
			
			Calendar calendarioActual = Calendar.getInstance(); 
			calendarioActual.setTime(startDate); 
			parameterCode = CodesBusinessEntityEnum.CODIGO_SYSTEM_PARAM_MCAPACIDAD.getCodeEntity();
			SystemParameterVO systemParameter = AssignmentFacadeLocator.getInstance().getCoreAssignmentFacade().getSystemParameterByCodeAndCountryId(parameterCode,countryId);
			UtilsBusiness.assertNotNull(systemParameter, ErrorBusinessMessages.SYSTEM_PARAMETER_DOESNT_EXIST.getCode(), ErrorBusinessMessages.SYSTEM_PARAMETER_DOESNT_EXIST.getMessage() + " Error al obtener la propiedad del código del parámetro del sistema con el nombre: PARAM_CAPACITY_DAYS para el país con id: " + countryId );
			int dayCount = Integer.parseInt(systemParameter.getValue());
			int diaSemana = calendarioActual.get(Calendar.DAY_OF_WEEK); 
			if(diaSemana==1)
				diaSemana=7;
			else
				diaSemana--;
			
			if((8-diaSemana)<dayCount)
				dayCount=8-diaSemana;
			return dayCount;
		} catch (NumberFormatException e) {
			e.printStackTrace();
			throw new BusinessException(ErrorBusinessMessages.INVALID_NUMBER.getCode(),ErrorBusinessMessages.INVALID_NUMBER.getMessage() + " No se pudo convertir el valor de un parámetro del sistema con el código: " + parameterCode + " en el país con id: " + countryId);
		} catch (PropertiesException e) {
			e.printStackTrace();
			throw new BusinessException(ErrorBusinessMessages.SYSTEM_PARAMETER_DOESNT_EXIST.getCode(),ErrorBusinessMessages.SYSTEM_PARAMETER_DOESNT_EXIST.getMessage() + " Error al obtener la propiedad del código del parámetro del sistema con el nombre: PARAM_CAPACITY_DAYS" );
		}
	}
	
}