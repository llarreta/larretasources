/**
 * Creado 12/07/2010 10:33:05
 */
package co.com.directv.sdii.model.vo;

import java.io.Serializable;
import java.util.Date;

import co.com.directv.sdii.assign.kpi.dto.DealerIndicatorResultDTO;
import co.com.directv.sdii.common.util.UtilsBusiness;
import co.com.directv.sdii.model.pojo.KpiResult;

/**
 * Objeto que encapsula la información de un KpiResult
 * 
 * Fecha de Creación: 12/07/2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.model.pojo.KpiResult    
 */
public class KpiResultVO extends KpiResult implements Serializable {

	public DealerIndicatorResultDTO toDealerIndicatorResultDTO() {
		
		DealerIndicatorResultDTO dealerIndicatorResultDTO = new DealerIndicatorResultDTO();
		
		int dayCount = getKpiConfiguration().getDayCount().intValue();
		Date startDate = UtilsBusiness.addDate(getResultDate(), -1 * dayCount);
		
		dealerIndicatorResultDTO.setCountryId(getCountry().getId());
		dealerIndicatorResultDTO.setDayCount( dayCount ); 
		dealerIndicatorResultDTO.setDealerId(getDealer().getId());
		dealerIndicatorResultDTO.setEndDate( getResultDate() );
		dealerIndicatorResultDTO.setIndicatorId(getKpiConfiguration().getKpi().getId());
		dealerIndicatorResultDTO.setIndicatorTypeId(getKpiConfiguration().getKpi().getKpiType().getId());
		dealerIndicatorResultDTO.setIndicatorValue(getResult());
		dealerIndicatorResultDTO.setServiceSuperCategoryId(getKpiConfiguration().getServiceSuperCategory().getId());
		
		dealerIndicatorResultDTO.setStartDate( startDate );
		
		return dealerIndicatorResultDTO;
		
	}
	
}
