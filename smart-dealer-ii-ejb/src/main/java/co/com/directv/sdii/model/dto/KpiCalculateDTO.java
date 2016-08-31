package co.com.directv.sdii.model.dto;

import java.io.Serializable;

/**
 * Data Transfer Object para transportar objetos a la cola entre el modulo
 * de CORE y ser procesada con Inventarios.
 * 
 * Fecha de Creación: 14/02/2012
 * @author cduarte <a href="mailto:cduarte@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see     
 */
public class KpiCalculateDTO implements Serializable {

	private static final long serialVersionUID = 5281149691490940693L;
	
	/**
	 * Declaracion de atributos Privados 
	 */
	
	private String kpiCode;
	private Long countryId;
	private Long dayCount;
	private Long dealerId;
	private Long indicatorId;
	private Long indicatorTypeId;
	private Long serviceSuperCategoryId;
	private Long zoneTypeId;
	private Long indicatorConfigurationId;
	private String frecuencyExpression;
	
	public KpiCalculateDTO() {
		super();
	}
	
	public KpiCalculateDTO(String kpiCode, Long countryId, Long dayCount,Long indicatorId, Long indicatorTypeId,
			Long serviceSuperCategoryId, Long zoneTypeId,
			Long indicatorConfigurationId,String frecuencyExpression) {
		super();
		this.kpiCode = kpiCode;
		this.countryId = countryId;
		this.dayCount = dayCount;
		this.indicatorId = indicatorId;
		this.indicatorTypeId = indicatorTypeId;
		this.serviceSuperCategoryId = serviceSuperCategoryId;
		this.zoneTypeId = zoneTypeId;
		this.indicatorConfigurationId = indicatorConfigurationId;
		this.frecuencyExpression=frecuencyExpression;
	}
	
	public String getKpiCode() {
		return kpiCode;
	}
	
	public void setKpiCode(String kpiCode) {
		this.kpiCode = kpiCode;
	}
	
	public Long getCountryId() {
		return countryId;
	}
	
	public void setCountryId(Long countryId) {
		this.countryId = countryId;
	}
	
	public Long getDayCount() {
		return dayCount;
	}

	public void setDayCount(Long dayCount) {
		this.dayCount = dayCount;
	}

	public Long getDealerId() {
		return dealerId;
	}
	
	public void setDealerId(Long dealerId) {
		this.dealerId = dealerId;
	}
	
	public Long getIndicatorId() {
		return indicatorId;
	}
	
	public void setIndicatorId(Long indicatorId) {
		this.indicatorId = indicatorId;
	}
	
	public Long getIndicatorTypeId() {
		return indicatorTypeId;
	}
	
	public void setIndicatorTypeId(Long indicatorTypeId) {
		this.indicatorTypeId = indicatorTypeId;
	}
	
	public Long getServiceSuperCategoryId() {
		return serviceSuperCategoryId;
	}
	
	public void setServiceSuperCategoryId(Long serviceSuperCategoryId) {
		this.serviceSuperCategoryId = serviceSuperCategoryId;
	}
	
	public Long getZoneTypeId() {
		return zoneTypeId;
	}
	
	public void setZoneTypeId(Long zoneTypeId) {
		this.zoneTypeId = zoneTypeId;
	}
	
	public Long getIndicatorConfigurationId() {
		return indicatorConfigurationId;
	}
	
	public void setIndicatorConfigurationId(Long indicatorConfigurationId) {
		this.indicatorConfigurationId = indicatorConfigurationId;
	}
	
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getFrecuencyExpression() {
		return frecuencyExpression;
	}

	public void setFrecuencyExpression(String frecuencyExpression) {
		this.frecuencyExpression = frecuencyExpression;
	}
	
}
