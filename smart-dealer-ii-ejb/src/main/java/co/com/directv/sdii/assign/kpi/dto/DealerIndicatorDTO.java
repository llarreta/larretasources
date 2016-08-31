package co.com.directv.sdii.assign.kpi.dto;

/**
 * define los atributos mas generales para los indicadores
 * @author jjimenezh
 * @version 1.0,  &nbsp; 26-may-2011 15:11:46
 */
public class DealerIndicatorDTO extends BaseDealerIndicatorDTO {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4635351559731826984L;

	/**
	 * identificador del dealer
	 */
	private Long dealerId;
	
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


	public DealerIndicatorDTO(){

	}
	
	
	public DealerIndicatorDTO(Long countryId, Long serviceSuperCategoryId,
			int dayCount, Long zoneTypeId, Long indicatorId,
			Long indicatorTypeId, Long dealerId) {
		super(countryId, serviceSuperCategoryId, dayCount, zoneTypeId, indicatorId,
				indicatorTypeId);
		this.dealerId = dealerId;
	}


	public Long getDealerId() {
		return dealerId;
	}

	public void setDealerId(Long dealerId) {
		this.dealerId = dealerId;
	}

	

	@Override
	public String toString() {
		return "DealerIndicatorDTO [dealerId=" + dealerId + ", getCountryId()="
				+ getCountryId() + ", getDayCount()=" + getDayCount()
				+ ", getIndicatorId()=" + getIndicatorId()
				+ ", getIndicatorTypeId()=" + getIndicatorTypeId()
				+ ", getServiceSuperCategoryId()="
				+ getServiceSuperCategoryId() + ", getZoneTypeId()="
				+ getZoneTypeId() + "]";
	}

}