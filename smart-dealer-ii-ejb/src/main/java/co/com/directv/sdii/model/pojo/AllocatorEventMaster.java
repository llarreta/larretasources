package co.com.directv.sdii.model.pojo;

import java.io.Serializable;
import java.util.Date;

import co.com.directv.sdii.rules.BusinessRequired;

/**
 * Encapsula la información de los resultados de la asignacion de una work order
 * 
 * Fecha de Creación: 29/03/2012
 * @author cduarte <a href="mailto:cduarte@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see     
 */
public class AllocatorEventMaster implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -228443085333202110L;
	
	private Long id;
	
	@BusinessRequired
	private Date initDate;
	
	private Date endDate;
	
	@BusinessRequired
	private Long total;
	
	private Long successCount;
	
	private Long errorCount;

	private Long warningCount;
	
	//@BusinessRequired(isComplexType=true, fieldNameRequired="id")
	private Country country;
	
	public AllocatorEventMaster() {
		super();
	}
	
	public AllocatorEventMaster(Long id, Date initDate, Long total,Long countryId) {
		super();
		this.id = id;
		this.initDate = initDate;
		this.total = total;
		if(countryId!=null){
			country = new Country();
			country.setId(countryId);
		}
	}
	
	public AllocatorEventMaster(Long id, Date initDate, Date endDate,
			Long total, Long successCount, Long errorCount, Long warningCount,
			Country country) {
		super();
		this.id = id;
		this.initDate = initDate;
		this.endDate = endDate;
		this.total = total;
		this.successCount = successCount;
		this.errorCount = errorCount;
		this.warningCount = warningCount;
		this.country = country;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getInitDate() {
		return initDate;
	}

	public void setInitDate(Date initDate) {
		this.initDate = initDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Long getTotal() {
		return total;
	}

	public void setTotal(Long total) {
		this.total = total;
	}

	public Long getSuccessCount() {
		return successCount;
	}

	public void setSuccessCount(Long successCount) {
		this.successCount = successCount;
	}

	public Long getErrorCount() {
		return errorCount;
	}

	public void setErrorCount(Long errorCount) {
		this.errorCount = errorCount;
	}

	public Long getWarningCount() {
		return warningCount;
	}

	public void setWarningCount(Long warningCount) {
		this.warningCount = warningCount;
	}

	public Country getCountry() {
		return country;
	}

	public void setCountry(Country country) {
		this.country = country;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
}
