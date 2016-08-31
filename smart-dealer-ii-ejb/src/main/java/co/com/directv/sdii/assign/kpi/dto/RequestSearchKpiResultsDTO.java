package co.com.directv.sdii.assign.kpi.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Encapsula la informacicÃ³n de resultado del cÃ¡lculo de un indicador
 * @author jjimenezh
 * @version 1.0,  &nbsp; 26-may-2011 15:11:46
 */
public class RequestSearchKpiResultsDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6910879270487616531L;
	
	/**
	 * id de la ciudad
	 */
	private Long countryId;
	/**
	 * id del tipo de zona
	 */
	private Long zoneTypeId;
	/**
	 * id del servicio de la super categoria
	 */
	private Long serviceSuperCategoryId;
	/**
	 * lista de id de los dealers
	 */
	private List<Long> dealersId=null;
	/**
	 * lista de id de los dealers
	 */
	private List<Long> dealersBranchId=null;
	/**
	 * fecha de inicio
	 */
	private java.util.Date startDate;
	/**
	 * fecha de finalizaciÃ³n
	 */
	private java.util.Date endDate;
	
	public RequestSearchKpiResultsDTO() {
		super();
	}
	
	public RequestSearchKpiResultsDTO(Long countryId, Long zoneTypeId,
			Long serviceSuperCategoryId, List<Long> dealersId,List<Long> dealersBranchId, Date startDate,
			Date endDate) {
		super();
		this.countryId = countryId;
		this.zoneTypeId = zoneTypeId;
		this.serviceSuperCategoryId = serviceSuperCategoryId;
		this.dealersId = dealersId;
		this.dealersBranchId = dealersBranchId;
		this.startDate = startDate;
		this.endDate = endDate;
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
	public Long getZoneTypeId() {
		return zoneTypeId;
	}
	public void setZoneTypeId(Long zoneTypeId) {
		this.zoneTypeId = zoneTypeId;
	}

	public List<Long> getDealersId() {
		return dealersId;
	}

	public void setDealersId(List<Long> dealersId) {
		this.dealersId = dealersId;
	}

	public List<Long> getDealersBranchId() {
		return dealersBranchId;
	}

	public void setDealersBranchId(List<Long> dealersBranchId) {
		this.dealersBranchId = dealersBranchId;
	}	
	
		
}