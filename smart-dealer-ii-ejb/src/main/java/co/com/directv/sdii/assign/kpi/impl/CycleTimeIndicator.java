package co.com.directv.sdii.assign.kpi.impl;

import java.util.Calendar;

import co.com.directv.sdii.assign.assignment.AssignmentFacadeLocator;
import co.com.directv.sdii.assign.kpi.DealerIndicator;
import co.com.directv.sdii.assign.kpi.dto.DealerIndicatorDTO;
import co.com.directv.sdii.assign.kpi.dto.DealerIndicatorResultDTO;
import co.com.directv.sdii.common.util.UtilsBusiness;
import co.com.directv.sdii.exceptions.KpiException;

import org.apache.log4j.Logger;

/**
 * Métrica que permite determinar el tiempo promedio de atención de una compañía
 * instaladora, estará dado por la siguiente fórmula:
 * 
 * Donde CT = Cycle time en días para un dealer específico.
 * FT = Fecha de finalización de una Work Order atendida por el dealer.
 * FAS = La primera fecha de asignación de la Work Order al dealer al que se le
 * está calculando el cycle time
 * WoT = Work Order de la categoría de servicio que se desea calcular el indicador,
 * en estado atendida o finalizada en una fecha entre 1 y n días atrás por el
 * dealer al que se le realiza el cálculo
 * @author jjimenezh
 * @version 1.0,  &nbsp; 31-may-2011 9:04:55
 */
public class CycleTimeIndicator extends DealerIndicator {
	
	private final static Logger log = UtilsBusiness.getLog4J(CycleTimeIndicator.class);

	public CycleTimeIndicator(){

	}

	public void finalize() throws Throwable {

	}
	/**
	 * realiza el cálculo del indicador para una compañía instaladora
	 * 
	 * @param dealerIndicatorDto    información necesaria para realizar el cálculo del
	 * indicador
	 * @author jnova
	 */
	public DealerIndicatorResultDTO calculateIndicator(DealerIndicatorDTO dealerIndicatorDto) throws KpiException{
		log.debug("== Inicia calculateIndicator/CycleTimeIndicator ==");
		try{
			DealerIndicatorResultDTO response = null;
			
			if( dealerIndicatorDto != null ){
				response = UtilsBusiness.copyObject(DealerIndicatorResultDTO.class, dealerIndicatorDto);
				//Codigo para agregar filtro de fecha de finalizacion de WO
				Calendar startDate = null;
				startDate = Calendar.getInstance();
				startDate.roll(Calendar.DAY_OF_YEAR, dealerIndicatorDto.getDayCount()*-1);
				response.setStartDate( UtilsBusiness.dateTo12am( startDate.getTime() ) );
				response.setEndDate( UtilsBusiness.fechaActual() );
				response.setIndicatorValue( AssignmentFacadeLocator.getInstance().getCoreAssignmentFacade().calculateCycleTimeIndicator(dealerIndicatorDto,response.getStartDate(),response.getEndDate()) );
				//Se almacena el kpiResult
				saveKpiResult(dealerIndicatorDto,response);
			}
			
			return response;
		}catch (Throwable e) {
			log.debug("== Error al tratar de ejecutar la operación calculateIndicator/CycleTimeIndicator ==", e);
			throw this.manageException(e);
		} finally{
			log.debug("== Termina calculateIndicator/CycleTimeIndicator ==");
		}
	}
}