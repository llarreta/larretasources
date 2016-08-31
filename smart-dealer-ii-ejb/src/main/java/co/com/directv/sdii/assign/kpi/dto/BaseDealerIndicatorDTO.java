package co.com.directv.sdii.assign.kpi.dto;

import java.io.Serializable;

/**
 * encapsula las variables que pueden manejarse para el c�lculo de los indicadores
 * @author jjimenezh
 * @version 1.0,  &nbsp; 26-may-2011 15:11:45
 */
public class BaseDealerIndicatorDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1796707183894420929L;
	/**
	 * identificador del pa�s al que pertenece la compa��a instaladora
	 */
	private Long countryId;
	/**
	 * identificador de la super categor�a de servicio para la que se calcular� el KPI
	 */
	private Long serviceSuperCategoryId;
	/**
	 * n�mero de d�as a tener en cuenta en el c�lculo del indicador
	 */
	private int dayCount;
	/**
	 * identificador del tipo de zona sobre la que se realizar� el c�lculo del
	 * indicador
	 */
	private Long zoneTypeId;
	/**
	 * identificador del indicador que se calcular�
	 */
	private Long indicatorId;
	/**
	 * identificador del tipo de indicador (se han identificado 2 : +1 y -1 es decir
	 * uno cuyo resultado mayor es positivo y otro suyo mayor resultado es negativo
	 */
	private Long indicatorTypeId;

	public BaseDealerIndicatorDTO(){

	}
	
	public BaseDealerIndicatorDTO(Long countryId, Long serviceSuperCategoryId,
			int dayCount, Long zoneTypeId, Long indicatorId,
			Long indicatorTypeId) {
		super();
		this.countryId = countryId;
		this.serviceSuperCategoryId = serviceSuperCategoryId;
		this.dayCount = dayCount;
		this.zoneTypeId = zoneTypeId;
		this.indicatorId = indicatorId;
		this.indicatorTypeId = indicatorTypeId;
	}



	public Long getCountryId() {
		return countryId;
	}



	public void setCountryId(Long countryId) {
		this.countryId = countryId;
	}



	public Long getServiceSuperCategoryId() {
		return serviceSuperCategoryId;
	}



	public void setServiceSuperCategoryId(Long serviceSuperCategoryId) {
		this.serviceSuperCategoryId = serviceSuperCategoryId;
	}



	public int getDayCount() {
		return dayCount;
	}



	public void setDayCount(int dayCount) {
		this.dayCount = dayCount;
	}



	public Long getZoneTypeId() {
		return zoneTypeId;
	}



	public void setZoneTypeId(Long zoneTypeId) {
		this.zoneTypeId = zoneTypeId;
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

}