package co.com.directv.sdii.model.dto;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

import co.com.directv.sdii.model.pojo.Country;
import co.com.directv.sdii.model.pojo.collection.RequestCollectionInfo;

/**
 * Permite encapsular los filtros para invocar el servicio CustomerInquiriesByCriteria
 * 
 * Fecha de Creación: 26/11/2012
 * @author cduarte <a href="mailto:cduarte@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see     
 */
public class CustomerInquiriesByCriteriaIBSDTO extends RequestCollectionInfo implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6287143018842312729L;
	
	private Country country;
	private Date beginDate;
	private Calendar endDate;
	private String workOrderIdGreaterThan;
	private Long userId;
	
	public CustomerInquiriesByCriteriaIBSDTO(Country country,
			                                 Date beginDate, 
			                                 Calendar endDate, 
			                                 String workOrderIdGreaterThan,
			                                 int pageIndex,
			                                 int pageSize,
			                                 Long userId) {
		
		super();
		this.country = country;
		this.beginDate = beginDate;
		this.endDate = endDate;
		this.workOrderIdGreaterThan = workOrderIdGreaterThan;
		this.setPageIndex(pageIndex);
		this.setPageSize(pageSize);
		this.userId = userId;
		
	}
	
	public Country getCountry() {
		return country;
	}

	public void setCountry(Country country) {
		this.country = country;
	}

	public Date getBeginDate() {
		return beginDate;
	}
	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}
	public Calendar getEndDate() {
		return endDate;
	}
	public void setEndDate(Calendar endDate) {
		this.endDate = endDate;
	}
	public String getWorkOrderIdGreaterThan() {
		return workOrderIdGreaterThan;
	}
	public void setWorkOrderIdGreaterThan(String workOrderIdGreaterThan) {
		this.workOrderIdGreaterThan = workOrderIdGreaterThan;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}
}
