package co.com.directv.sdii.assign.kpi.impl;
import java.util.Calendar;

import org.apache.log4j.Logger;

import co.com.directv.sdii.assign.assignment.AssignmentFacadeLocator;
import co.com.directv.sdii.assign.kpi.DealerIndicator;
import co.com.directv.sdii.assign.kpi.dto.DealerIndicatorDTO;
import co.com.directv.sdii.assign.kpi.dto.DealerIndicatorResultDTO;
import co.com.directv.sdii.common.util.UtilsBusiness;
import co.com.directv.sdii.exceptions.KpiException;

/**
 * Número de días que le tomará a una compañía instaladora dada su capacidad
 * de atención diaria, atender las Work Orders que tiene asignadas.
 * 
 * BklD = Back Log día de una compañía instaladora
 * WoA = Work Order de la categoría de servicio que se desea calcular el indicador,
 * asignada a la compañía instaladora en una fecha entre 1 y n días atrás y en un
 * estado diferente de atendida, finalizada y cancelada.
 * DC = Capacidad diaria de atención de la compañía instaladora
 * @author jjimenezh
 * @version 1.0,  &nbsp; 31-may-2011 9:04:55
 */
public class BackLogInDaysIndicator extends DealerIndicator {
	
	private final static Logger log = UtilsBusiness.getLog4J(BackLogInDaysIndicator.class);

	public BackLogInDaysIndicator(){

	}

	public void finalize() throws Throwable {

	}
	/**
	 * realiza el cálculo del indicador para una compañía instaladora
	 * 
	 * @param dealerIndicatorDto    información necesaria para realizar el cálculo del
	 * indicador
	 * @throws KpiException
	 * @author jnova
	 */
	public DealerIndicatorResultDTO calculateIndicator(DealerIndicatorDTO dealerIndicatorDto) throws KpiException{
		log.debug("== Inicia calculateIndicator/BackLogInDaysIndicator ==");
		try{
			DealerIndicatorResultDTO response = null;
			
			if( dealerIndicatorDto != null ){
				response = UtilsBusiness.copyObject(DealerIndicatorResultDTO.class, dealerIndicatorDto);
				//Codigo para agregar filtro de fecha de finalizacion de WO
				Calendar startDate = null;
				startDate = Calendar.getInstance();
				startDate.roll(Calendar.DAY_OF_YEAR, dealerIndicatorDto.getDayCount() * -1);
				response.setStartDate( UtilsBusiness.dateTo12am( startDate.getTime() ) );
				response.setEndDate( UtilsBusiness.fechaActual() );
				response.setIndicatorValue( AssignmentFacadeLocator.getInstance().getCoreAssignmentFacade().calculateBackLogInDaysIndicator(dealerIndicatorDto,response.getStartDate(),response.getEndDate()) );
				//Se almacena el kpiResult
				saveKpiResult(dealerIndicatorDto,response);
			}
			
			return response;
		}catch (Throwable e) {
			log.debug("== Error al tratar de ejecutar la operación calculateIndicator/BackLogInDaysIndicator ==", e);
			throw this.manageException(e);
		} finally{
			log.debug("== Termina calculateIndicator/BackLogInDaysIndicator ==");
		}
	}
}