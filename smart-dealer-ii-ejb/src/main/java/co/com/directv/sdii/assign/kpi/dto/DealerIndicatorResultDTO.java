package co.com.directv.sdii.assign.kpi.dto;

import java.util.Date;

import co.com.directv.sdii.model.pojo.Country;
import co.com.directv.sdii.model.pojo.Dealer;
import co.com.directv.sdii.model.pojo.KpiConfiguration;
import co.com.directv.sdii.model.vo.KpiResultVO;

/**
 * Encapsula la informacicón de resultado del cálculo de un indicador
 * @author jjimenezh
 * @version 1.0,  &nbsp; 26-may-2011 15:11:46
 */
public class DealerIndicatorResultDTO extends DealerIndicatorDTO {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6910879270487616531L;
	/**
	 * fecha de inicio del cálculo del indicador
	 */
	private java.util.Date startDate;
	/**
	 * fecha de finalización de calculo del indicador
	 */
	private java.util.Date endDate;
	/**
	 * valor del cálculo del indicador
	 */
	private Double indicatorValue;
	
	/**
	 * identificador de la configuración
	 */
	private Long indicatorConfigurationId;

	public Long getIndicatorConfigurationId() {
		return indicatorConfigurationId;
	}

	public void setIndicatorConfigurationId(Long indicatorConfigurationId) {
		this.indicatorConfigurationId = indicatorConfigurationId;
	}

	public DealerIndicatorResultDTO(){

	}

	public DealerIndicatorResultDTO(Long countryId,
			Long serviceSuperCategoryId, int dayCount, Long zoneTypeId,
			Long indicatorId, Long indicatorTypeId, Long dealerId, Date startDate, Date endDate,
			Double indicatorValue) {
		super(countryId, serviceSuperCategoryId, dayCount, zoneTypeId, indicatorId,
				indicatorTypeId, dealerId);
		this.startDate = startDate;
		this.endDate = endDate;
		this.indicatorValue = indicatorValue;
	}
	

	public java.util.Date getStartDate() {
		return startDate;
	}

	public void setStartDate(java.util.Date startDate) {
		this.startDate = startDate;
	}

	public java.util.Date getEndDate() {
		return endDate;
	}

	public void setEndDate(java.util.Date endDate) {
		this.endDate = endDate;
	}

	public Double getIndicatorValue() {
		return indicatorValue;
	}

	public void setIndicatorValue(Double indicatorValue) {
		this.indicatorValue = indicatorValue;
	}

	public KpiResultVO toKpiResultVO() {
		KpiResultVO kpiResultVO = new KpiResultVO();
		kpiResultVO.setCountry( new Country( getCountryId() ) );
		kpiResultVO.setDealer(new Dealer( getDealerId() ));
		kpiResultVO.setKpiConfiguration( new KpiConfiguration( getIndicatorConfigurationId() ) ); 
		kpiResultVO.setResult( getIndicatorValue() );
		kpiResultVO.setResultDate( getEndDate() );
		return kpiResultVO;
	}
	
}