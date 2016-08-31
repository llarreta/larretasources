/**
 * Creado 12/07/2010 10:33:05
 */
package co.com.directv.sdii.model.vo;

import java.io.Serializable;

import co.com.directv.sdii.assign.kpi.dto.DealerIndicatorConfigDTO;
import co.com.directv.sdii.assign.kpi.dto.DealerIndicatorDTO;
import co.com.directv.sdii.model.pojo.KpiConfiguration;

/**
 * Objeto que encapsula la información de un KpiConfiguration
 * 
 * Fecha de Creación: 12/07/2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.model.pojo.KpiConfiguration    
 */
public class KpiConfigurationVO extends KpiConfiguration implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6748572223710800868L;
	
	/**
	 * 
	 * Metodo: transforma un KpiConfigurationVO en un DealerIndicatorDTO
	 * para ser usado en el módulo de asignación
	 * @return DealerIndicatorDTO VO transformado
	 * @author wjimenez
	 */
	public DealerIndicatorDTO toDealerIndicatorDTO() {
		DealerIndicatorDTO dealerIndicatorDTO =  new DealerIndicatorDTO();
		dealerIndicatorDTO.setCountryId( getCountry().getId() );
		dealerIndicatorDTO.setDayCount( getDayCount().intValue() );
		//dealerIndicatorDTO.setDealerId( ... ); este atributo debe asignarse cuando se quiera calcular un indicador para un dealer específico
		dealerIndicatorDTO.setIndicatorId( getKpi().getId() );
		dealerIndicatorDTO.setIndicatorTypeId( getKpi().getKpiType().getId() );
		dealerIndicatorDTO.setServiceSuperCategoryId( getServiceSuperCategory().getId() );
		dealerIndicatorDTO.setZoneTypeId( getZoneTypes().getId() );
		
		dealerIndicatorDTO.setIndicatorConfigurationId( getId() );
		
		return dealerIndicatorDTO;
	}

	public DealerIndicatorConfigDTO toDealerIndicatorConfigDTO() {
		DealerIndicatorConfigDTO dealerIndicatorConfig = new DealerIndicatorConfigDTO();
		dealerIndicatorConfig.setCalculateFrecuency(this.getFrecuencyExpression());
		dealerIndicatorConfig.setCalculateTypeCode(this.getKpiCalculationType().getCode());
		dealerIndicatorConfig.setCountryId(this.getCountry().getId());
		dealerIndicatorConfig.setDayCount(this.getDayCount().intValue());
		dealerIndicatorConfig.setGoal(this.getGoal());
		dealerIndicatorConfig.setIndicatorId(this.getKpi().getId());
		dealerIndicatorConfig.setIndicatorTypeId(this.getKpi().getKpiType().getId());
		dealerIndicatorConfig.setIsActive(this.getStatus());
		dealerIndicatorConfig.setServiceSuperCategoryId(this.getServiceSuperCategory().getId());
		dealerIndicatorConfig.setWeighting(this.getWeighting());
		dealerIndicatorConfig.setZoneTypeId(this.getZoneTypes().getId());
		return dealerIndicatorConfig;
	}

}
