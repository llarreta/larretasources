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
 * 
 * Determina la puntualidad de una compañía instaladora en la atención de los
 * servicios al cliente.
 * 
 * Donde
 * OT = Indicador de puntualidad del dealer al que se le realiza el cálculo en un
 * periodo de tiempo en días dado por n
 * WoT = Work Order de la categoría de servicio que se desea calcular el indicador,
 * que ha sido atendida en una fecha entre 1 y n días atrás.
 * WooT = Work order on time = Work Order de la categoría de servicio que se desea
 * calcular el indicador que ha sido atendida en una fecha entre 1 y n días atrás.
 * 
 * 
 * Donde
 * FT =  fecha de atención
 * P = parámetro del sistema en horas por país
 * FAG= fecha de la primer agenda realizada al cliente y asignada al dealer
 * Si el resultado de este cálculo es <= 0 horas, será sumada en la ecuación del
 * indicador On time.
 * @author jjimenezh
 * @version 1.0,  &nbsp; 31-may-2011 9:04:56
 */
public class OnTimeIndicator extends DealerIndicator {
	
	private final static Logger log = UtilsBusiness.getLog4J(OnTimeIndicator.class);

	public OnTimeIndicator(){

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
		log.debug("== Inicia calculateIndicator/OnTimeIndicator ==");
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
				response.setIndicatorValue( AssignmentFacadeLocator.getInstance().getCoreAssignmentFacade().calculateOnTimeIndicator(dealerIndicatorDto,response.getStartDate(),response.getEndDate()) );
				//Se almacena el kpiResult
				saveKpiResult(dealerIndicatorDto,response);
			}
			
			return response;
		}catch (Throwable e) {
			log.debug("== Error al tratar de ejecutar la operación calculateIndicator/OnTimeIndicator ==", e);
			throw this.manageException(e);
		} finally{
			log.debug("== Termina calculateIndicator/OnTimeIndicator ==");
		}
	}
}