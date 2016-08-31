package co.com.directv.sdii.assign.schedule;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import co.com.directv.sdii.assign.assignment.AssignmentFacadeLocator;
import co.com.directv.sdii.assign.assignment.AssignmentSkillCoordinator;
import co.com.directv.sdii.assign.assignment.dto.AssignmentResultDTO;
import co.com.directv.sdii.assign.assignment.dto.SkillEvaluationDTO;
import co.com.directv.sdii.assign.schedule.dto.DayServiceHourWorkLoad;
import co.com.directv.sdii.assign.schedule.dto.DayWorkLoad;
import co.com.directv.sdii.assign.schedule.dto.DealerWorkCapacityCriteria;
import co.com.directv.sdii.assign.schedule.dto.DealerWorkLoad;
import co.com.directv.sdii.assign.schedule.dto.ScheduleColorsConfig;
import co.com.directv.sdii.common.enumerations.CodesBusinessEntityEnum;
import co.com.directv.sdii.common.enumerations.ErrorBusinessMessages;
import co.com.directv.sdii.common.util.UtilsBusiness;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.exceptions.PropertiesException;
import co.com.directv.sdii.model.pojo.Dealer;
import co.com.directv.sdii.model.vo.DealerVO;
import co.com.directv.sdii.model.vo.ServiceHourVO;
import co.com.directv.sdii.model.vo.UserVO;

/**
 * se encarga de calcular la carga de trabajo para una fecha y jornada específicas
 * con el animo de realizar en paralelo el cálculo para minimizar el tiempo de
 * respuesta
 * 
 * Este observer construye y observa objetos del tipo DealerWorkLoadCalculator,
 * construirá uno por cada dealer que venga dentro de la lista que retorna el
 * assignmentSkillConfigurator
 * @author jjimenezh
 * @version 1.0,  &nbsp; 26-may-2011 15:11:46
 */
public class DateServiceHourWorkLoadCalculator extends Observable implements Runnable, Observer {

	private static Log logger = LogFactory.getLog(DateServiceHourWorkLoadCalculator.class);
	
	private List<DealerWorkLoad> dealersWorkLoad;
	/**
	 * Con cada uno de los resultados del cálculo de la carga de cada dealer agrega a este objeto dicho resultado
	 */
	private DayWorkLoad dayWorkLoad;
	
	private SkillEvaluationDTO evaluationData;
	
	private AssignmentSkillCoordinator assignmentSkillCoor;
	
	/**
	 * Número de calculadores de capacidad lanzados
	 */
	private int launchedCalculatorsCount;
	
	/**
	 * número de calculadores de capacidad que se han reportado como finalizados y han entregado su respuesta
	 */
	private int notifiedCalculatorsCount;
	
	private boolean forOnlyOneDealer;
	
	private DealerVO dealer;
	
	private BusinessException error;

	/**
	 * Constructor: <Descripcion>
	 * @param date2Calculate
	 * @param evaluationData
	 * @author
	 */
	public DateServiceHourWorkLoadCalculator(Date date2Calculate, SkillEvaluationDTO evaluationData, boolean forOlyOneDealer, DealerVO dealer){
		dayWorkLoad = new DayWorkLoad(date2Calculate);
		dealersWorkLoad = Collections.synchronizedList(new ArrayList<DealerWorkLoad>());
		launchedCalculatorsCount = 0;
		notifiedCalculatorsCount = 0;
		this.evaluationData = evaluationData;
		assignmentSkillCoor = AssignmentSkillCoordinator.getInstance();
		this.forOnlyOneDealer = forOlyOneDealer;
		this.dealer = dealer;
	}

	public void finalize() throws Throwable {

	}
	
	
	private void notifyEnding(){
		setChanged();
		notifyObservers();
	}

	/**
	 * Operación para la ejecución del hilo
	 */
	public void run(){
		doCalculations();
	}
	
	/**
	 * Metodo: Realiza el cálculo de la capacidad total del día para las compañías que cumplen las habilidades para prestar un
	 * servicio a un cliente
	 * @author jjimenezh
	 */
	private void doCalculations() {
		//Se obtienen los dealers que cumplen con las habilidades
		if(logger.isDebugEnabled()){
			logger.debug("Se inicia la ejecución del cálculo de la carga para el día: " + dayWorkLoad.getDate());
		}
		try {
			List<DealerVO> dealers = new ArrayList<DealerVO>();
			String traceAssignment = "";
			
			if(! forOnlyOneDealer){
				AssignmentResultDTO assignmentResult = assignmentSkillCoor.doSkillEvaluations(evaluationData); 
				dealers = assignmentResult.getSelectedDealers();
				if(assignmentResult!=null && evaluationData.getWorkOrderCSRId()!=null ){
					traceAssignment = assignmentResult.getTraceAssignmentInformation();
					AssignmentFacadeLocator.getInstance().getAssignmetFacade().traceAssignmentWorkOrderCSR(evaluationData.getWorkOrderCSRId(), traceAssignment);
				}
			}else{
				dealers.add(dealer);
			}
			
			//Validando si no hay respuesta o se respondo con el dealer sin cobertura:
			if(dealers == null || dealers.isEmpty()){
				notifyEnding();
				return;
			}else if(dealers.size() == 1 && dealers.get(0).getDealerCode().longValue() == AssignmentFacadeLocator.getInstance().getCoreAssignmentFacade().getDealerCodeWoutCoverage(evaluationData.getCountryId())){
				notifyEnding();
				return;
			}
			
			DealerWorkLoadCalculator woCalculator;
			DealerWorkCapacityCriteria dwcCriteria;
			List<Runnable> calculators = new ArrayList<Runnable>();
			
			UtilsBusiness.assertNotNull(evaluationData.getUserId(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
			UserVO userVO=AssignmentFacadeLocator.getInstance().getSecurityFacade().getUserById(evaluationData.getUserId());
			
			Date userDate=UtilsBusiness.getCurrentDateByTimeZoneKey(userVO.getSdiiTimeZone().getTimeZoneKey(), new Date());
			Calendar actualTime = Calendar.getInstance();
			actualTime.setTime(userDate);
			Calendar serviceHourStartTime = Calendar.getInstance();
			
			Long lockPriorCalendar=0L;
			String strLockPriorCalendar= AssignmentFacadeLocator.getInstance().getCoreAssignmentFacade().getSystemParameterByCodeAndCountryId(CodesBusinessEntityEnum.CODE_SYS_PARAM_LOCK_PRIOR_CALENDAR.getCodeEntity(), evaluationData.getCountryId()).getValue();
			if( strLockPriorCalendar != null &&  !strLockPriorCalendar.trim().equals("") ){
				try{
					lockPriorCalendar=Long.parseLong(strLockPriorCalendar);
					if(lockPriorCalendar.doubleValue()>24)
						lockPriorCalendar=24L;
				}catch (Exception e) {
					lockPriorCalendar=0L;
				}
				
			}
			
			boolean isTheFirtsDay = isTheSameDateAsToday(actualTime, this.dayWorkLoad.getDate());
			int hourMinuteActual=0,hourMinuteService=0;
			int hourMinuteActualWithLockPriorCalendar=0;
			//Por cada dealer que cumple la habilidad se calculará su capacidad
			for (Dealer dealer : dealers) {
				List<ServiceHourVO> activeSh = getActiveServiceHours(evaluationData.getCountryId());
				
				for (ServiceHourVO serviceHour : activeSh) {
					if(isTheFirtsDay){
						if(userVO.getDealer()==null){
							serviceHourStartTime.setTime(serviceHour.getInitTime());
							//Si la hora actual es mayor que la hora de inicio de la jornada no se calculará la carga para esa compañía
							
							hourMinuteActual=60*actualTime.get(Calendar.HOUR_OF_DAY) + actualTime.get(Calendar.MINUTE);
							hourMinuteService=60*serviceHourStartTime.get(Calendar.HOUR_OF_DAY) + serviceHourStartTime.get(Calendar.MINUTE);
							hourMinuteActualWithLockPriorCalendar = hourMinuteActual + (int)(lockPriorCalendar.doubleValue()*60);
							if(hourMinuteActual > hourMinuteService){
								continue;
							}else if(hourMinuteActualWithLockPriorCalendar > hourMinuteService && userVO.getDealer()==null){
								continue;
							}
						}else{
							serviceHourStartTime.setTime(serviceHour.getEndTime());
							//Si la hora actual es mayor que la hora de inicio de la jornada no se calculará la carga para esa compañía
							
							hourMinuteActual=60*actualTime.get(Calendar.HOUR_OF_DAY) + actualTime.get(Calendar.MINUTE);
							hourMinuteService=60*serviceHourStartTime.get(Calendar.HOUR_OF_DAY) + serviceHourStartTime.get(Calendar.MINUTE);
							hourMinuteActualWithLockPriorCalendar = hourMinuteActual + (int)(lockPriorCalendar.doubleValue()*60);
							if(hourMinuteActual > hourMinuteService){
								continue;
							}
						}
					}
					
					dwcCriteria = new DealerWorkCapacityCriteria(dayWorkLoad.getDate(), dealer.getId(), serviceHour.getId(), evaluationData.getServiceSupercategoyId(), evaluationData.getCountryId());
					woCalculator = new DealerWorkLoadCalculator(dwcCriteria);
					woCalculator.addObserver(this);
					incrementLaunched();
					calculators.add(woCalculator);
				}
			}
			/*
			ThreadManager threadManager = new ThreadManager();
			threadManager.executeThreadsAndWait(calculators);
			//*/
			///*
			for (Runnable runnable : calculators) {
				runnable.run();
				if(error != null){
					throw error;
				}
			}
			//*/
			
		} catch (BusinessException e) {
			error = e;
			e.printStackTrace();
			logger.error("Error al realizar el cálculo de la carga para el día: " + dayWorkLoad.getDate(),e);
			notifyEnding();
		} catch (PropertiesException e) {
			e.printStackTrace();
			logger.error("Error al realizar el cálculo de la carga para el día: " + dayWorkLoad.getDate(),e);
			notifyEnding();
		}finally{
			if(logger.isDebugEnabled()){
				logger.debug("Finaliza la ejecución del cálculo de la carga para el día: " + dayWorkLoad.getDate());
			}
		}
	}

	public static void main(String... args) {
		Calendar c = Calendar.getInstance();
		System.out.println(c.getTimeZone());
		System.out.println(c.getTime());
	}
	
	private boolean isTheSameDateAsToday(Calendar actualTime, Date date2Evaluate) {
		Date actualDate = UtilsBusiness.dateTo12am(actualTime.getTime());
		Date date2EvalWoutTime = UtilsBusiness.dateTo12am(date2Evaluate);
		return actualDate.equals(date2EvalWoutTime);
	}

	private List<ServiceHourVO> getActiveServiceHours(Long countryId) throws BusinessException {
		List<ServiceHourVO> result = AssignmentFacadeLocator.getInstance().getCoreAssignmentFacade().getActiveServiceHoursByCountryId(countryId);
		return result;
	}
	
	private ServiceHourVO getServiceHourById(Long serviceHourId) throws BusinessException{
		ServiceHourVO sh = AssignmentFacadeLocator.getInstance().getCoreAssignmentFacade().getServiceHourById(serviceHourId);
		return sh;
	}

	private synchronized void incrementLaunched(){
		launchedCalculatorsCount++;
	}
	
	private synchronized void incrementNotified(){
		notifiedCalculatorsCount++;
	}
	
	/**
	 * operaci�n para notificar el cambio de un observable que ha cambiado
	 * 
	 * @param observable    objeto que ha modificado su estado
	 */
	@Override
	public void update(Observable o, Object arg) {
		DealerWorkLoadCalculator wlCalc = (DealerWorkLoadCalculator)o;
		updateObserver(wlCalc);
	}
	
	private synchronized void updateObserver(DealerWorkLoadCalculator wlCalc){
		if(wlCalc.getError() != null){
			error = wlCalc.getError();
			notifyEnding();
		}else{
			addDealerWorkLoad2List(wlCalc.getDealerWorkLoad());
			
		}
		incrementNotified();
		//Si todos los observer han terminado de ejecutar su trabajo:
		if(launchedCalculatorsCount == notifiedCalculatorsCount){
			//Calculando la agenda para el día:
			generateDayWorkLoad();
			notifyEnding();
		}
	}
	
	/**
	 * Metodo: Si el cálculo se está llevando a cabo para varias compañías no se debe permitir 
	 *         que la sobreasignación de una compañía afecte las agendas de las demás
	 * @param dealerWorkLoad <tipo> <descripcion>
	 * @author
	 */
	private void addDealerWorkLoad2List(DealerWorkLoad dealerWorkLoad) {

		logger.debug("Inicia el proceso addDealerWorkLoad2List/DateServiceHourWorkLoadCalculator ");
		try {
			if(! forOnlyOneDealer || (forOnlyOneDealer && this.evaluationData.isCSR())){
				BigDecimal big = getMaxCapacityByServiceSuperCategoryAndCountryIdAndMaxCapacity(dealerWorkLoad.getDealerWorkCapacity().getCapacityCriteria().getSuperCategoryServiceId(),
																								dealerWorkLoad.getDealerWorkCapacity().getCapacityCriteria().getCountryId(),
																								dealerWorkLoad.getDealerWorkCapacity().getMaxCapacity());
				if(dealerWorkLoad.getDealerWorkCapacity() != null){
					if( big.intValue() < dealerWorkLoad.getUsedCapacity()){
						dealerWorkLoad.setUsedCapacity(big.intValue());
					}
				}
			}
			dealersWorkLoad.add(dealerWorkLoad);
		} catch (BusinessException e) {
			e.printStackTrace();
			logger.error("Error en el proceso addDealerWorkLoad2List/DateServiceHourWorkLoadCalculator ", e);
		}finally{
			logger.debug("Se finaliza el proceso addDealerWorkLoad2List/DateServiceHourWorkLoadCalculator");
		}
	}
	
	private BigDecimal getMaxCapacityByServiceSuperCategoryAndCountryIdAndMaxCapacity(Long serviceSuperCategoryByID,Long countryId,int maxCapacity) throws BusinessException{
		
		int scale = 0;
		ScheduleColorsConfig colorsConfig = AssignmentFacadeLocator.getInstance().getAllocatorFacade().loadScheduleColorsConfig(serviceSuperCategoryByID,countryId);
		double dMaxCapacity = (new BigDecimal(maxCapacity).multiply(new BigDecimal(colorsConfig.getScheduleLimit()))).doubleValue();
		return UtilsBusiness.removeDecimal(dMaxCapacity,scale,RoundingMode.HALF_UP);
		
	}
	

	private void generateDayWorkLoad() {
		if(logger.isDebugEnabled()){
			logger.debug("Se inicia el proceso de generación de la carga de trabajo para el " + dayWorkLoad.getDate());
		}
		try {
			Map<Long, DayServiceHourWorkLoad> shWoLoads = new HashMap<Long, DayServiceHourWorkLoad>();
			DayServiceHourWorkLoad shWoLoad;
			for (DealerWorkLoad dealerWl : dealersWorkLoad) {
				Long shId = dealerWl.getDealerWorkCapacity().getCapacityCriteria().getServiceHourId();
				shWoLoad = shWoLoads.get(shId);
				if(shWoLoad == null){
					shWoLoad = buildDayServiceHourWorkLoad(dealerWl);
					shWoLoads.put(shId, shWoLoad);
				}else{
					shWoLoad.setMaxCapacity(shWoLoad.getMaxCapacity() + dealerWl.getDealerWorkCapacity().getMaxCapacity());
					shWoLoad.setUsedCapacity(shWoLoad.getUsedCapacity() + dealerWl.getUsedCapacity());
				}
			}
			
			Set<Long> shWoLoadsKeys = shWoLoads.keySet();
			for (Long shId : shWoLoadsKeys) {
				shWoLoad = shWoLoads.get(shId);
				dayWorkLoad.getDayShWorkLoadList().add(shWoLoad);
			}
			
		} catch (BusinessException e) {
			e.printStackTrace();
			logger.error("Error en el proceso de generación de la carga de trabajo para el: " + dayWorkLoad.getDate(), e);
		}finally{
			if(org.jfree.util.Log.isDebugEnabled()){
				logger.debug("Se finaliza el proceso de generación de la carga de trabajo para el: " + dayWorkLoad.getDate());
			}
		}
	}

	private DayServiceHourWorkLoad buildDayServiceHourWorkLoad(
			DealerWorkLoad dealerWl) throws BusinessException {
		DayServiceHourWorkLoad dayShWL = new DayServiceHourWorkLoad();
		dayShWL.setDate(dayWorkLoad.getDate());
		dayShWL.setMaxCapacity(dealerWl.getDealerWorkCapacity().getMaxCapacity());
		ServiceHourVO sh = getServiceHourById(dealerWl.getDealerWorkCapacity().getCapacityCriteria().getServiceHourId());
		UtilsBusiness.assertNotNull(sh, ErrorBusinessMessages.SERVICE_HOUR_BY_COUNTRY_NOT_FOUND.getCode(), ErrorBusinessMessages.SERVICE_HOUR_BY_COUNTRY_NOT_FOUND.getMessage());
		dayShWL.setServiceHourStartTime(sh.getInitTime());
		dayShWL.setServiceHourEndTime(sh.getEndTime());
		dayShWL.setServiceHourId(dealerWl.getDealerWorkCapacity().getCapacityCriteria().getServiceHourId());
		dayShWL.setServiceSuperCategoryId(dealerWl.getDealerWorkCapacity().getCapacityCriteria().getSuperCategoryServiceId());
		dayShWL.setUsedCapacity(dealerWl.getUsedCapacity());
		return dayShWL;
	}

	public List<DealerWorkLoad> getDealersWorkLoad() {
		return dealersWorkLoad;
	}

	public void setDealersWorkLoad(List<DealerWorkLoad> dealersWorkLoad) {
		this.dealersWorkLoad = dealersWorkLoad;
	}

	public DayWorkLoad getDayWorkLoad() {
		return dayWorkLoad;
	}

	public void setDayWorkLoad(DayWorkLoad dayWorkLoad) {
		this.dayWorkLoad = dayWorkLoad;
	}

	public AssignmentSkillCoordinator getAssignmentSkillCoor() {
		return assignmentSkillCoor;
	}

	public void setAssignmentSkillCoor(
			AssignmentSkillCoordinator assignmentSkillCoor) {
		this.assignmentSkillCoor = assignmentSkillCoor;
	}

	public BusinessException getError() {
		return error;
	}
	
}