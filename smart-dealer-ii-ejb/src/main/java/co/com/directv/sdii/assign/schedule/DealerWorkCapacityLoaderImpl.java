package co.com.directv.sdii.assign.schedule;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.Calendar;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import co.com.directv.sdii.assign.assignment.AssignmentFacadeLocator;
import co.com.directv.sdii.assign.schedule.dto.DealerWorkCapacity;
import co.com.directv.sdii.assign.schedule.dto.DealerWorkCapacityCriteria;
import co.com.directv.sdii.common.enumerations.ErrorBusinessMessages;
import co.com.directv.sdii.common.util.UtilsBusiness;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.facade.assign.CoreAssignmentFacadeRemote;
import co.com.directv.sdii.model.vo.CountryVO;
import co.com.directv.sdii.model.vo.DealerVO;
import co.com.directv.sdii.model.vo.DealerWeekCapacityVO;
import co.com.directv.sdii.model.vo.ServiceHourVO;
import co.com.directv.sdii.model.vo.ServiceSuperCategoryVO;

/**
 * implementación del mecanismo de cargue de la capacidad de una compañía instaladora 
 * con base en la fecha (día de la semana), la jornada y la super categoría de 
 * servicio consulta la información persistida en dealers_week_capacity
 * @author jjimenezh
 * @version 1.0,  &nbsp; 26-may-2011 15:11:46
 */
public class DealerWorkCapacityLoaderImpl implements DealerWorkCapacityLoader {

	private static Log logger = LogFactory.getLog(DealerWorkCapacityLoaderImpl.class);
	
	public DealerWorkCapacityLoaderImpl(){

	}

	public void finalize() throws Throwable {

	}
	/**
	 * carga la capacidad de una compañía instaladora
	 * 
	 * @param dealerWorkCapacityCriteria    filtro de búsqueda para la capacidad de
	 * una compañía
	 * @throws BusinessException 
	 */
	public DealerWorkCapacity loadCapacity(DealerWorkCapacityCriteria dealerWorkCapacityCriteria) throws BusinessException{
		/*
		 * para realizar esta operación:
		 * 1. Consultar la información de la tabla dealer_details para obtener la capacidad diaria de la compañía 
		 *   (puede ser en términos de cantidad de WO por técnico o por compañía) si está dada en número de WO por técnico, 
		 *   consultar la cantidad de técnicos activos que tiene la compañía y multiplicar los dos valores
		 * 2. consultar la configuración de la tabla dealers_week_capacity con pais, dealer, jornada y super categoría de servicio
		 * 3. dada la fecha identificar el día de la semana al que se refiere y realizar el cálculo dada la ponderación que el usuario 
		 *    asignó en la columna correspondiente al día contra la capacidad diaria de la compañía
		 * 4. asignar la capacidad máxima
		 */
		if(logger.isDebugEnabled()){
			logger.debug("Se inicia la consulta de la capacidad disponible del dealer con id: " + dealerWorkCapacityCriteria.getDealerId());
		}
		try {
			UtilsBusiness.assertNotNull(dealerWorkCapacityCriteria, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
			
			//Realizando 1:
			//jnova se modifica para hacer llamado que realiza directamente el calculo para filtrar tanto por empleados como cuadrillas activas
			
			Long dailyDealerCapacity = AssignmentFacadeLocator.getInstance().getCoreAssignmentFacade().getDealerDayCapacity( dealerWorkCapacityCriteria.getDealerId() );
			if( dailyDealerCapacity == null || dailyDealerCapacity.longValue() <= 0 ){
				/* IN375115
				logger.error("No se ha configurado la cantidad de work orders que la compañía " 
						   + "puede realizar al día ni la cantidad de wo que cada técnico de esa compañía" 
						   + " puede realizar al día en la tabla dealer_details, una de las dos debe ser configurada");
				*/
			}
			/*DealerDetailVO dealerDetail = CoreAssignmentFacadeLocator.getInstance().getAssignmentFacade().getDealerDetailByDealerId(dealerWorkCapacityCriteria.getDealerId());
			//Si el usuario configuró capacidad de atención de work orders diaria de toda la compañía:
			if(dealerDetail.getCompanyWoQtyDay() != null && dealerDetail.getCompanyWoQtyDay().longValue() > 0){
				dailyDealerCapacity = dealerDetail.getCompanyWoQtyDay();
			}else if(dealerDetail.getTechnicianWoQtyDay() != null && dealerDetail.getTechnicianWoQtyDay().longValue() > 0){
				Long technicianDealerQty = CoreAssignmentFacadeLocator.getInstance().getAssignmentFacade().getActiveDealerTechniciansQty(dealerWorkCapacityCriteria.getDealerId());
				dailyDealerCapacity = technicianDealerQty * dealerDetail.getTechnicianWoQtyDay();
			}else{
				logger.error("No se ha configurado la cantidad de work orders que la compañía " +
						"puede realizar al día ni la cantidad de wo que cada técnico de esa compañía" +
						" puede realizar al día en la tabla dealer_details, una de las dos debe ser configurada");
			}*/
			
			
			//Realizando 2:
			DealerWeekCapacityVO dealerWC = AssignmentFacadeLocator.getInstance().getCoreAssignmentFacade().getWeekCapacityByDealerIdAndServiceHourAndServiceSuperCat(dealerWorkCapacityCriteria.getCountryId(), dealerWorkCapacityCriteria.getDealerId(), dealerWorkCapacityCriteria.getServiceHourId(), dealerWorkCapacityCriteria.getSuperCategoryServiceId());
			if(dealerWC == null){
				
				CoreAssignmentFacadeRemote coreAssignmentFacade = AssignmentFacadeLocator.getInstance().getCoreAssignmentFacade();
				DealerVO dealerVO = coreAssignmentFacade.getDealerById(dealerWorkCapacityCriteria.getDealerId(), dealerWorkCapacityCriteria.getCountryId());
				CountryVO countryVO  = coreAssignmentFacade.getCountriesByID(dealerWorkCapacityCriteria.getCountryId());
				ServiceSuperCategoryVO serviceSuperCategoryVO  = coreAssignmentFacade.getServiceSuperCategoryByID(dealerWorkCapacityCriteria.getSuperCategoryServiceId());
				ServiceHourVO serviceHourVO  = coreAssignmentFacade.getServiceHourById(dealerWorkCapacityCriteria.getServiceHourId());
				
				throw new BusinessException(ErrorBusinessMessages.DEALER_DOES_NOT_HAVE_WEEK_CONFIG.getCode(), 
						                    ErrorBusinessMessages.DEALER_DOES_NOT_HAVE_WEEK_CONFIG.getMessage(),
						                    Arrays.asList(new String[]{"" + dealerVO.getDealerName(), "" 
						                    		                      + countryVO.getCountryName(), "" 
						                    		                      + serviceSuperCategoryVO.getName() , "" 
						                    		                      + serviceHourVO.getServiceHoursName()}));
			}
			
			//Realizando 3:
			Calendar cal = Calendar.getInstance();
			cal.setTime(dealerWorkCapacityCriteria.getCapacityDate());
			int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
			double dayOfWeekWeighting = getDayOfWeekWeighting(dealerWC, dayOfWeek);
			
			//Realizando 4:
			int maxCapacity=(new BigDecimal(dailyDealerCapacity * dayOfWeekWeighting).divide(new BigDecimal(100), RoundingMode.HALF_DOWN)).intValue();

			DealerWorkCapacity result = new DealerWorkCapacity(maxCapacity, dealerWorkCapacityCriteria);
			return result;
		} catch (BusinessException e) {
			e.printStackTrace();
			logger.error("Error al consultar de la capacidad disponible del dealer con id: " + dealerWorkCapacityCriteria.getDealerId(), e);
			throw e;
		}finally{
			if(logger.isDebugEnabled()){
				logger.debug("Se finaliza la consulta de la capacidad disponible del dealer con id: " + dealerWorkCapacityCriteria.getDealerId());
			}
		}
	}

	/**
	 * Metodo: Obtiene la ponderación configurada por la compañía
	 * para la super categoría de servicio, jornada y día de la semana
	 * @param dealerWC configuración de ponderaciones por día de la semana
	 * @param dayOfWeek día de la semana
	 * @return ponderación del porcentaje que le corresponde al día de la 
	 * semana y jornada del total de las WO que puede atender la compañía
	 * un número entre 0 y 100
	 * @author jjimenezh
	 */
	private double getDayOfWeekWeighting(DealerWeekCapacityVO dealerWC,	int dayOfWeek) {
		if(dealerWC == null){
			return 0D;
		}
		double weighting = 0D;
		switch (dayOfWeek) {
		case Calendar.MONDAY:
			weighting = dealerWC.getMonCapacity();
			break;
		case Calendar.TUESDAY:
			weighting = dealerWC.getTueCapacity();
			break;
		case Calendar.WEDNESDAY:
			weighting = dealerWC.getWedCapacity();
			break;
		case Calendar.THURSDAY:
			weighting = dealerWC.getThuCapacity();
			break;
		case Calendar.FRIDAY:
			weighting = dealerWC.getFriCapacity();
			break;
		case Calendar.SATURDAY:
			weighting = dealerWC.getSatCapacity();
			break;
		case Calendar.SUNDAY:
			weighting = dealerWC.getSunCapacity();
			break;
		default:
			break;
		}
		return weighting;
	}
}