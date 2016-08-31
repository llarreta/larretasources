package co.com.directv.sdii.assign.schedule;

import java.util.Arrays;
import java.util.Observable;

import org.apache.log4j.Logger;

import co.com.directv.sdii.assign.assignment.AssignmentFacadeLocator;
import co.com.directv.sdii.assign.schedule.dto.DealerWorkCapacity;
import co.com.directv.sdii.assign.schedule.dto.DealerWorkCapacityCriteria;
import co.com.directv.sdii.assign.schedule.dto.DealerWorkLoad;
import co.com.directv.sdii.common.enumerations.ErrorBusinessMessages;
import co.com.directv.sdii.common.util.UtilsBusiness;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.exceptions.ScheduleException;

/**
 * Clase que encapsula la l�gica del c�lculo de la carga de trabajo que tiene un
 * dealer en una fecha y jornada para una super categor�a de servicio
 * @author jjimenezh
 * @version 1.0,  &nbsp; 26-may-2011 15:11:46
 */
public class DealerWorkLoadCalculator extends Observable implements Runnable {

	private DealerWorkCapacityLoader dealerWCapacityLr = new DealerWorkCapacityLoaderImpl();
	
	private final static Logger log = UtilsBusiness.getLog4J(DealerWorkLoadCalculator.class);
	
	/**
	 * criterio de cálculo de capacidad del dealer
	 */
	private DealerWorkCapacityCriteria dealerWorkCapacityCriteria;
	
	private DealerWorkLoad dealerWorkLoad;
	
	private BusinessException error;

	public DealerWorkLoadCalculator(DealerWorkCapacityCriteria dealerWorkCapacityCriteria) {
		super();
		this.dealerWorkCapacityCriteria = dealerWorkCapacityCriteria;
	}


	public void finalize() throws Throwable {

	}
	

	/**
	 * calcula carga de trabajo que tiene una compañía dados los criterios de búsqueda.
	 * 
	 * su forma de funcionamiento será la siguiente:
	 * 1. Invoca la operación loadCapacity del dealerWCapacityLr.
	 * 2. consulta la carga de trabajo que tiene la compañía instaladora agendada para
	 * esa fecha y jornada (count de work orders con fecha y jornada de agendamiento)
	 * 3. Construye el objeto WorkLoad con toda la información necesaria.
	 * 
	 * @param dealerWorkCapacityCriteria
	 * @throws ScheduleException 
	 */
	private DealerWorkLoad calculateWorkLoad(DealerWorkCapacityCriteria dealerWCCriteria) throws BusinessException{
		DealerWorkCapacity dealerWC;
		try {
			dealerWC = dealerWCapacityLr.loadCapacity(dealerWCCriteria);
		} catch (BusinessException e) {
			throw e;
		}
		int usedCapacity = AssignmentFacadeLocator.getInstance().getCoreAssignmentFacade().getDealerUsedCapacity(dealerWCCriteria);
		DealerWorkLoad result = new DealerWorkLoad(usedCapacity, dealerWC);
		AssignmentFacadeLocator.getInstance().getCoreAssignmentFacade().saveDealerCapacity(result);
		return result;
	}

	/**
	 * Operación para la ejecución del hilo
	 */
	public void run(){
		//do changes
		try {
			dealerWorkLoad = calculateWorkLoad(dealerWorkCapacityCriteria);
		} catch (Throwable e) {
			if(e instanceof BusinessException){
				error = (BusinessException)e;
			}else{
				error = new BusinessException(ErrorBusinessMessages.INVALID_PARAMETER.getCode(), ErrorBusinessMessages.INVALID_PARAMETER.getMessage(), Arrays.asList("Ejecutando el cálculo de la capacidad de la compañía instaladora", "dealerId" ,"" +dealerWorkCapacityCriteria.getDealerId() ));
			}
			log.error("Error ejecutando el hilo de DealerWorkLoadCalculator", e);
		}
		setChanged();
		notifyObservers();
	}


	public DealerWorkLoad getDealerWorkLoad() {
		return dealerWorkLoad;
	}


	public BusinessException getError() {
		return error;
	}
}