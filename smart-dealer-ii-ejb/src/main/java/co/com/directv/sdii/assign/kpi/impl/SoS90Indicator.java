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
 * (Revisitas por asistencias técnicas): determina la proporción de Work
 * Orders atendidas por la compañía que han generado una Work Order de asistencia
 * técnica.
 * 
 * Donde:
 * SoS90 = Proporción de Work Orders atendidas por la compañía instaladora que han
 * generado asistencias técnicas.
 * SoSi = Work Order de la categoría de servicios asistencia técnica, que se
 * encuentre en cualquier estado y cuya fecha de creación está entre 1 y n días
 * atrás de la fecha de cálculo del indicador y en donde se encuentre otra WO de
 * cualquier categoría de servicio en estado atendida o finalizada por la compañía
 * instaladora a la que se le está realizando el cálculo del indicador, en donde
 * la fecha de atención de la segunda está en el rango de 90 días atrás de la
 * fecha de creación de la WO de asistencia técnica
 * @author jjimenezh
 * @version 1.0,  &nbsp; 31-may-2011 9:04:58
 */
public class SoS90Indicator extends DealerIndicator {
	
	private final static Logger log = UtilsBusiness.getLog4J(SoS90Indicator.class);

	public SoS90Indicator(){

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
		log.debug("== Inicia calculateIndicator/SoS90Indicator ==");
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
				response.setIndicatorValue( AssignmentFacadeLocator.getInstance().getCoreAssignmentFacade().calculateSoS90Indicator(dealerIndicatorDto,response.getStartDate(),response.getEndDate()) );
				//Se almacena el kpiResult
				saveKpiResult(dealerIndicatorDto,response);
			}
			
			return response;
		}catch (Throwable e) {
			log.debug("== Error al tratar de ejecutar la operación calculateIndicator/SoS90Indicator ==", e);
			throw this.manageException(e);
		} finally{
			log.debug("== Termina calculateIndicator/SoS90Indicator ==");
		}
	}
}