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
 * Determina la proporción de Work Orders de recuperación que han sido
 * atendidas por la compañía instaladora.
 * 
 * Donde:
 * Ret = Proporción de Work Orders de recuperación que han sido atendidas por la
 * compañía instaladora.
 * WoRetAS = Work Order de recuperación cuya fecha de asignación a la compañía
 * está dentro de los 1 a n días anteriores a la fecha de cálculo del indicador.
 * WoRetT= Work Order de recuperación en estado terminada o finalizada cuya fecha
 * de atención está dentro de los 1 a n días anteriores a la fecha de cálculo del
 * indicador
 * @author jjimenezh
 * @version 1.0,  &nbsp; 31-may-2011 9:04:57
 */
public class RetrievalIndicator extends DealerIndicator {
	
	private final static Logger log = UtilsBusiness.getLog4J(RetrievalIndicator.class);

	public RetrievalIndicator(){

	}

	public void finalize() throws Throwable {

	}
	/**
	 * realiza el cálculo del indicador para una compañía instaladora
	 * 
	 * @param dealerIndicatorDto    información necesaria para realizar el cálculo del
	 * indicador
	 */
	public DealerIndicatorResultDTO calculateIndicator(DealerIndicatorDTO dealerIndicatorDto) throws KpiException{
		log.debug("== Inicia calculateIndicator/RetrievalIndicator ==");
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
				response.setIndicatorValue( AssignmentFacadeLocator.getInstance().getCoreAssignmentFacade().calculateRetrievalIndicator(dealerIndicatorDto,response.getStartDate(),response.getEndDate()) );
				//Se almacena el kpiResult
				saveKpiResult(dealerIndicatorDto,response);
			}
			
			return response;
		}catch (Throwable e) {
			log.debug("== Error al tratar de ejecutar la operación calculateIndicator/RetrievalIndicator ==", e);
			throw this.manageException(e);
		} finally{
			log.debug("== Termina calculateIndicator/RetrievalIndicator ==");
		}
	}
}