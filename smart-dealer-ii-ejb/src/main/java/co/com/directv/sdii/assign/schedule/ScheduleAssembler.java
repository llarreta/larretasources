package co.com.directv.sdii.assign.schedule;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import co.com.directv.sdii.assign.assignment.AssignmentFacadeLocator;
import co.com.directv.sdii.assign.assignment.dto.SkillEvaluationDTO;
import co.com.directv.sdii.assign.schedule.dto.DayWorkLoad;
import co.com.directv.sdii.assign.schedule.dto.WorkLoad;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.vo.DealerVO;

/**
 * Se encarga de ensamblar con base en:
 * 
 * 1. Lista de compañías a consultar agenda.
 * 2. Fecha inicial de la consulta.
 * 3. Número de días a consultar agenda.
 * 4. Super categoría de servicio.
 * 
 * Una lista de información por día y jornada con la capacidad de atención actual
 * para las compañías recibidas como parámetro.
 * 
 * Este observer construye y observa objetos del tipo
 * DateServiceHourWorkLoadCalculator para ensablar con los resultados de todos el
 * WorkLoad, construirá uno por cada fecha y jornada
 * @author jjimenezh
 * @version 1.0,  &nbsp; 26-may-2011 15:11:48
 */
public class ScheduleAssembler implements Observer {

	private WorkLoad workLoad;
	private List<DayWorkLoad> daysWorkLoad;
	private List<Runnable> daySHCalculators;
	private BusinessException error;

	public ScheduleAssembler(){
		daySHCalculators = new ArrayList<Runnable>();
		daysWorkLoad = new ArrayList<DayWorkLoad>();
	}

	public void finalize() throws Throwable {

	}
	
	/**
	 * se encarga de ensamblar la agenda de disponibilidad para un dealer espec�fico,
	 * en este caso no evaluar� habilidades, solo evaluar� capacidad en jornada y
	 * fecha, debido a que esta funcionalidad ser� usada por las compa��as para
	 * agendar una WO que ya les ha sido asignada.
	 * 
	 * @param dealerId    identificador de la compa��a sobre la que se requiere
	 * realizar la consulta
	 * @param skillEvalDto    par�metros de evaluaci�n para la agenda
	 * @param startDate    fecha de inicio del c�lculo de la agenda
	 * @param dayCount    cantidad de d�as a ser tenidos en cuenta en el c�lculo
	 * @throws BusinessException 
	 */
	public WorkLoad assembleScheduleForOneDealer(Long dealerId, SkillEvaluationDTO skillEvalDto, java.util.Date startDate, int dayCount) throws BusinessException{
		DealerVO dealer = AssignmentFacadeLocator.getInstance().getCoreAssignmentFacade().getDealerById(dealerId, skillEvalDto.getCountryId());
		return assembleSchedule(skillEvalDto, startDate, dayCount, true, dealer);
	}
	
	private WorkLoad assembleSchedule(SkillEvaluationDTO skillEvalDto, java.util.Date startDate, int dayCount, boolean forOnlyOneDealer, DealerVO dealer) throws BusinessException{
		Date endDate = calculateEndDate(startDate, dayCount);
		workLoad = new WorkLoad(startDate, endDate, dayCount, daysWorkLoad);
		
		//Construyendo los objetos que calcularán la carga de trabajo diaria
		buildDayWorkLoadCalculators(skillEvalDto, startDate, dayCount, forOnlyOneDealer, dealer);
		
		
		//Iterando sobre los objetos construidos para correrlos en hilos separados
		///*
		for (Runnable dshcalc : daySHCalculators) {
			dshcalc.run();
			if(error != null){
				throw error;
			}
		}
		//*/
		/*
		ThreadManager threadManager = new ThreadManager();
		threadManager.executeThreadsAndWait(daySHCalculators);
		//*/
		//Retornando la carga de trabajo calculada
		if(error != null){
			throw error;
		}
		return workLoad;
	}
	
	/**
	 * construye la información de la agenda disponible dados los par�metros
	 * 
	 * @param skillEvalDto    información necesaria para la evaluación de habilidades
	 * @param startDate    fecha de inicio de la evaluación
	 * @param dayCount    número de días sobre los que se realizará la evaluación
	 * @throws BusinessException 
	 */
	public WorkLoad assembleScheduleForAllDealers(SkillEvaluationDTO skillEvalDto, java.util.Date startDate, int dayCount) throws BusinessException{
		return assembleSchedule(skillEvalDto, startDate, dayCount, false, null);
	}

	/**
	 * Metodo: Construye los calculadores de carga de trabajo por
	 * cada día de los consultados a partir de la fecha de inicio
	 * @param skillEvalDto información de la evaluación de la habilidad
	 * @param startDate fecha de inicio de la consulta de carga de trabajo
	 * @param dayCount número de días a calcular
	 * @author jjimenezh
	 */
	private void buildDayWorkLoadCalculators(SkillEvaluationDTO skillEvalDto, Date startDate, int dayCount, boolean forOnlyOneDealer, DealerVO dealer) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(startDate);
		Date date2Calculate;
		
		DateServiceHourWorkLoadCalculator calc = null;
		for (int i = 0; i < dayCount; i++) {
			date2Calculate = cal.getTime();
			calc = new DateServiceHourWorkLoadCalculator(date2Calculate, skillEvalDto, forOnlyOneDealer, dealer);
			calc.addObserver(this);
			daySHCalculators.add(calc);
			cal.add(Calendar.DAY_OF_YEAR, 1);
		}
	}

	/**
	 * Metodo:Calcula una fecha de fin a partir de una fecha de inicio
	 * @param startDate fecha de inicio del cálculo
	 * @param dayCount número de días a calcular
	 * @return fecha de inicio + número de días
	 * @author jjimenezh
	 */
	private Date calculateEndDate(Date startDate, int dayCount) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(startDate);
		cal.add(Calendar.DAY_OF_YEAR, dayCount - 1);
		Date endDate = cal.getTime();
		return endDate;
	}

	/* (non-Javadoc)
	 * @see java.util.Observer#update(java.util.Observable, java.lang.Object)
	 */
	@Override
	public void update(Observable o, Object arg) {
		DateServiceHourWorkLoadCalculator workLoadCalc = (DateServiceHourWorkLoadCalculator)o;
		notifyThreadEnding(workLoadCalc);
	}
	
	/**
	 * Metodo: Se notifica la finalización de uno de los observables
	 * @param workLoadCalc Objeto que realizó el cálculo de la carga de trabajo para el día
	 * @author jjimenezh
	 */
	public synchronized void notifyThreadEnding(DateServiceHourWorkLoadCalculator workLoadCalc){
		if(workLoadCalc.getError() != null){
			error = workLoadCalc.getError();
			return;
		}
		DayWorkLoad threadResult = workLoadCalc.getDayWorkLoad();
		daysWorkLoad.add(threadResult);
	}
}