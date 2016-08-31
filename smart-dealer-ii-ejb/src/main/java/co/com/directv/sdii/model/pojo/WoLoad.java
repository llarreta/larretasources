/**
 * Creado 3/12/2010 18:14:32
 */
package co.com.directv.sdii.model.pojo;

import java.io.Serializable;
import java.util.Date;

import co.com.directv.sdii.rules.BusinessRequired;

/**
 * Encapsula la información de los resultados del cargue de una WO.
 * 
 * Fecha de Creación: 10/02/2011
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
public class WoLoad implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -228443085333202110L;
	
	private Long id;
	
	@BusinessRequired
	private Date initDate;
	
	private Date endDate;
	
	@BusinessRequired
	private Long woCount;
	
	private Long woSuccessCount;
	
	private Long woErrorCount;

	private Long woWarningCount;
	
	@BusinessRequired(isComplexType=true, fieldNameRequired="id")
	private Country country;

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

	public Long getWoCount() {
		return woCount;
	}

	public void setWoCount(Long woCount) {
		this.woCount = woCount;
	}

	public Long getWoSuccessCount() {
		return woSuccessCount;
	}

	public void setWoSuccessCount(Long woSuccessCount) {
		this.woSuccessCount = woSuccessCount;
	}

	public Long getWoErrorCount() {
		return woErrorCount;
	}

	public void setWoErrorCount(Long woErrorCount) {
		this.woErrorCount = woErrorCount;
	}

	public Country getCountry() {
		return country;
	}

	public void setCountry(Country country) {
		this.country = country;
	}

	public WoLoad(Long id, Date initDate, Date endDate, Long woCount,
			Long woSuccessCount, Long woErrorCount, Country country) {
		super();
		this.id = id;
		this.initDate = initDate;
		this.endDate = endDate;
		this.woCount = woCount;
		this.woSuccessCount = woSuccessCount;
		this.woErrorCount = woErrorCount;
		this.country = country;
	}
	
	public WoLoad(Date initDate, Long woCount, Country country) {
		super();
		this.initDate = initDate;
		this.woCount = woCount;
		this.country = country;
	}

	public WoLoad() {
		super();
	}

	public Long getWoWarningCount() {
		return woWarningCount;
	}

	public void setWoWarningCount(Long woWarningCount) {
		this.woWarningCount = woWarningCount;
	}
	
}
