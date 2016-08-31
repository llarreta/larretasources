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
 * Establece el número promedio de días que han pasado desde que se asignaron las
 * Work Orders a la compañía instaladora y estas no han sido realizadas,
 * finalizadas o canceladas.
 * Donde:
 * FAc: Fecha actual (es decir la fecha y hora en la que se realiza el cálculo del
 * KPI.
 * FAsWoA = Fecha de asignación de la Work Order de la categoría de servicio que
 * se desea calcular el indicador, asignada a la compañía instaladora en una fecha
 * entre 1 y n días atras y en un estado diferente de atendida, finalizada y
 * cancelada.
 * WoA: Work order de la categoría de servicio en la que se está calculando el
 * indicador asignada a la compañía instaladora en estado diferente de atendida,
 * finalizada y cancelada
 * @author jjimenezh
 * @version 1.0,  &nbsp; 31-may-2011 9:04:53
 */
public class AgingIndicator extends DealerIndicator {
	
	private final static Logger log = UtilsBusiness.getLog4J(AgingIndicator.class);

	public AgingIndicator(){

	}

	public void finalize() throws Throwable {

	}

	/**
	 * realiza el cálculo del indicador para una compañía instaladora
	 * 
	 * @param dealerIndicatorDto información necesaria para realizar el cálculo del indicador
	 * @throws KpiException
	 */
	@Override
	public DealerIndicatorResultDTO calculateIndicator(DealerIndicatorDTO dealerIndicatorDto) throws KpiException{
		log.debug("== Inicia calculateIndicator/AgingIndicator ==");
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
				response.setIndicatorValue( AssignmentFacadeLocator.getInstance().getCoreAssignmentFacade().calculateAgingIndicator(dealerIndicatorDto,response.getStartDate(),response.getEndDate()) );
				//Se almacena el kpiResult
				saveKpiResult(dealerIndicatorDto,response);
			}
			return response;
		}catch (Throwable e) {
			log.debug("== Error al tratar de ejecutar la operación calculateIndicator/AgingIndicator ==", e);
			throw this.manageException(e);
		} finally{
			log.debug("== Termina calculateIndicator/AgingIndicator ==");
		}
	}
}