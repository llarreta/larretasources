package co.com.directv.sdii.assign.kpi;

import java.util.Date;

import co.com.directv.sdii.assign.assignment.AssignmentFacadeLocator;
import co.com.directv.sdii.assign.kpi.dto.DealerIndicatorDTO;
import co.com.directv.sdii.assign.kpi.dto.DealerIndicatorResultDTO;
import co.com.directv.sdii.common.enumerations.ErrorBusinessMessages;
import co.com.directv.sdii.common.util.UtilsBusiness;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.exceptions.KpiException;
import co.com.directv.sdii.facade.assign.CoreAssignmentFacadeRemote;
import co.com.directv.sdii.model.vo.KpiConfigurationVO;

/**
 * Define las operaciones necesarias para calcular un indicador de una compañía
 * instaladora dado un conjunto de variables.
 * @author jjimenezh
 * @version 1.0,  &nbsp; 26-may-2011 15:11:46
 */
public abstract class DealerIndicator {
	
	public KpiException manageException(Throwable exception){
		KpiException kpiException = new KpiException();
		if (exception instanceof  BusinessException){	
			kpiException = new KpiException( (BusinessException)exception );			
		} else{
			kpiException = new KpiException(ErrorBusinessMessages.UNKNOW_ERROR.getCode() ,exception.getMessage()+ " "+ UtilsBusiness.dateToString(new Date(), "yyyy/MM/dd HH:mm:ss"), exception);
		}
		return kpiException;
	}

	/**
	 * realiza el cálculo del indicador para una compañía instaladora
	 * 
	 * @param dealerIndicatorDto    información necesaria para realizar el cálculo del
	 * indicador
	 */
	public abstract DealerIndicatorResultDTO calculateIndicator(DealerIndicatorDTO dealerIndicatorDto) throws KpiException;
	
	/**
	 * almacena el resultado del kpi
	 * 
	 * @param DealerIndicatorResultDTO
	 * @param DealerIndicatorDTO
	 * @throws BusinessException 
	 */
	public void saveKpiResult(DealerIndicatorDTO request,DealerIndicatorResultDTO response) throws BusinessException{
		//Se almacena el kpiResult
		if(response != null) {
			CoreAssignmentFacadeRemote coreAssignmentFacadeRemote=AssignmentFacadeLocator.getInstance().getCoreAssignmentFacade();
			KpiConfigurationVO kpiConfigurationVO = coreAssignmentFacadeRemote.getActiveKpiConfigurationByCountrySuperCategoryKpiAndZoneType(
			                    		   request.getCountryId(), request.getServiceSuperCategoryId(), request.getIndicatorId(), request.getZoneTypeId());
			if(kpiConfigurationVO != null) {
				response.setIndicatorConfigurationId(kpiConfigurationVO.getId());
				coreAssignmentFacadeRemote.saveKpiResult(response);
			}
		}
	}

}