package co.com.directv.sdii.assign.kpi;

import java.util.Calendar;
import java.util.Date;

import co.com.directv.sdii.assign.assignment.AssignmentFacadeLocator;
import co.com.directv.sdii.assign.kpi.dto.DealerIndicatorDTO;
import co.com.directv.sdii.assign.kpi.dto.DealerIndicatorResultDTO;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.exceptions.KpiException;
import co.com.directv.sdii.model.pojo.KpiConfiguration;
import co.com.directv.sdii.model.vo.KpiResultVO;

/**
 * Determina el valor de un indicador que se calcula con alguna frecuencia
 * consultando el valor obtenido por el c�lculo de la frecuencia mas reciente
 * dados los criterios de b�squeda.
 * @author jjimenezh
 * @version 1.0,  &nbsp; 26-may-2011 15:11:47
 */
public class FrecuencyDealerKPIValueFinder implements DealerKPIValueFinder {

	public FrecuencyDealerKPIValueFinder(){

	}

	public void finalize() throws Throwable {

	}
	/**
	 * encuentra el valor de un KPI dados ciertos criterios de b�squeda
	 * 
	 * @param diConfigDto información de la configuración de un KPI
	 */
	public DealerIndicatorResultDTO findDealerKPIValue(DealerIndicatorDTO diConfigDto)throws KpiException{
		try {
			KpiResultVO resultKpi = AssignmentFacadeLocator.getInstance().getCoreAssignmentFacade().getKpiResultByCriteria(diConfigDto.getCountryId(), diConfigDto.getServiceSuperCategoryId(), diConfigDto.getZoneTypeId(), diConfigDto.getIndicatorId(), diConfigDto.getDealerId());
			DealerIndicatorResultDTO result = buildDealerIndicatorResult(diConfigDto, resultKpi);
			return result;
		} catch (BusinessException e) {
			e.printStackTrace();
			throw new KpiException(e.getMessageCode(), e.getMessage());
		}
	}

	private DealerIndicatorResultDTO buildDealerIndicatorResult(
			DealerIndicatorDTO diConfigDto, KpiResultVO resultKpi) {
		
		//Si el resultado es nulo se devuelve el valor del KPI como cero (0)
		if(resultKpi == null){
			resultKpi = new KpiResultVO();
			resultKpi.setResultDate(new Date());
			resultKpi.setKpiConfiguration(new KpiConfiguration());
			resultKpi.setResult(0D);
		}
		
		DealerIndicatorResultDTO result = new DealerIndicatorResultDTO();
		result.setCountryId(diConfigDto.getCountryId());
		result.setDayCount(diConfigDto.getDayCount());
		result.setDealerId(diConfigDto.getDealerId());
		result.setEndDate(resultKpi.getResultDate());
		result.setIndicatorConfigurationId(resultKpi.getKpiConfiguration().getId());
		result.setIndicatorId(diConfigDto.getIndicatorId());
		result.setIndicatorTypeId(diConfigDto.getIndicatorTypeId());
		result.setIndicatorValue(resultKpi.getResult());
		result.setServiceSuperCategoryId(diConfigDto.getServiceSuperCategoryId());
		Calendar cal = Calendar.getInstance();
		cal.setTime(resultKpi.getResultDate());
		cal.roll(Calendar.DAY_OF_YEAR, diConfigDto.getDayCount() * -1);
		result.setStartDate(cal.getTime());
		result.setZoneTypeId(diConfigDto.getZoneTypeId());
		return result;
	}
}