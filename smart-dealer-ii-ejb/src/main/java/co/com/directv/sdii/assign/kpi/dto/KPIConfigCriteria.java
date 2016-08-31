package co.com.directv.sdii.assign.kpi.dto;

import java.io.Serializable;

/**
 * define los parámetros de búsqueda de la configuración de los indicadores
 * @author jjimenezh
 * @version 1.0,  &nbsp; 26-may-2011 15:11:47
 */
public class KPIConfigCriteria implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8597079457932633258L;
	/**
	 * identificador del país
	 */
	private Long countryId;
	/**
	 * identificador de la super categoría de servicio
	 */
	private Long serviceSuperCategoryId;
	
	/**
	 * identificador del tipo de zona
	 */
	private Long zoneTypeId;

	public KPIConfigCriteria(){

	}
	
	
	public KPIConfigCriteria(Long countryId, Long serviceSuperCategoryId, Long zoneTypeId) {
		super();
		this.countryId = countryId;
		this.serviceSuperCategoryId = serviceSuperCategoryId;
		this.zoneTypeId = zoneTypeId;
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


	public Long getZoneTypeId() {
		return zoneTypeId;
	}


	public void setZoneTypeId(Long zoneTypeId) {
		this.zoneTypeId = zoneTypeId;
	}


	public void finalize() throws Throwable {

	}
}