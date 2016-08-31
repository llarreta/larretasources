package co.com.directv.sdii.assign.schedule.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * Encapsula la informaci�n de la capacidad de trabajos que puede realizar una
 * compa��a en una fecha, jornada y super categor�a de servicio
 * @author jjimenezh
 * @version 1.0,  &nbsp; 26-may-2011 15:11:46
 */
public class DealerWorkCapacityCriteria implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5079485808971557650L;

	/**
	 * fecha en la que el dealer cuenta con la capacidad
	 */
	private java.util.Date capacityDate;
	
	/**
	 * Identificador del país
	 */
	private Long countryId;
	
	/**
	 * identificador de la compa��a instaladora
	 */
	private Long dealerId;
	/**
	 * identificador de la jornada
	 */
	private Long serviceHourId;
	/**
	 * identificador de la super categor�a de servicio
	 */
	private Long superCategoryServiceId;

	public DealerWorkCapacityCriteria(){

	}
	
	
	
	public DealerWorkCapacityCriteria(Date capacityDate, Long dealerId,
			Long serviceHourId, Long superCategoryServiceId, Long countryId) {
		super();
		this.capacityDate = capacityDate;
		this.dealerId = dealerId;
		this.serviceHourId = serviceHourId;
		this.superCategoryServiceId = superCategoryServiceId;
		this.countryId = countryId;
	}



	public java.util.Date getCapacityDate() {
		return capacityDate;
	}



	public void setCapacityDate(java.util.Date capacityDate) {
		this.capacityDate = capacityDate;
	}



	public Long getDealerId() {
		return dealerId;
	}



	public void setDealerId(Long dealerId) {
		this.dealerId = dealerId;
	}



	public Long getServiceHourId() {
		return serviceHourId;
	}



	public void setServiceHourId(Long serviceHourId) {
		this.serviceHourId = serviceHourId;
	}



	public Long getSuperCategoryServiceId() {
		return superCategoryServiceId;
	}



	public void setSuperCategoryServiceId(Long superCategoryServiceId) {
		this.superCategoryServiceId = superCategoryServiceId;
	}


	public Long getCountryId() {
		return countryId;
	}



	public void setCountryId(Long countryId) {
		this.countryId = countryId;
	}



	public void finalize() throws Throwable {

	}
}