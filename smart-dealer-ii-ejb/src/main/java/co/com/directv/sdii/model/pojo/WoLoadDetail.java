/**
 * Creado 3/12/2010 18:14:32
 */
package co.com.directv.sdii.model.pojo;

import java.io.Serializable;
import java.util.Date;

/**
 * Encapsula la información de los resultados del cargue de una WO.
 * 
 * Fecha de Creación: 10/02/2011
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
public class WoLoadDetail implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -228443085333202110L;
	
	private Long id;
	
	private String woCode;
	
	private String customerCode;
	
	private String errorCode;
	
	private String errorMessage;
	
	private Date loadDate;
	
	private String recordStatus;
	
	private Country country;
	
	private WoLoad woLoad;
	
	private WorkorderStatus workorderStatusPrevius;
	
	private WorkorderStatus workorderStatusNew;
	
	private Ibs6Status ibs6Status;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getWoCode() {
		return woCode;
	}

	public void setWoCode(String woCode) {
		this.woCode = woCode;
	}

	public String getCustomerCode() {
		return customerCode;
	}

	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public Date getLoadDate() {
		return loadDate;
	}

	public void setLoadDate(Date loadDate) {
		this.loadDate = loadDate;
	}

	public String getRecordStatus() {
		return recordStatus;
	}

	public void setRecordStatus(String recordStatus) {
		this.recordStatus = recordStatus;
	}

	public Country getCountry() {
		return country;
	}

	public void setCountry(Country country) {
		this.country = country;
	}

	public WoLoad getWoLoad() {
		return woLoad;
	}

	public void setWoLoad(WoLoad woLoad) {
		this.woLoad = woLoad;
	}

	public WoLoadDetail(Long id, String woCode, String customerCode,
			String errorCode, String errorMessage, Date loadDate,
			String recordStatus, Country country, WoLoad woLoad) {
		super();
		this.id = id;
		this.woCode = woCode;
		this.customerCode = customerCode;
		this.errorCode = errorCode;
		this.errorMessage = errorMessage;
		this.loadDate = loadDate;
		this.recordStatus = recordStatus;
		this.country = country;
		this.woLoad = woLoad;
	}
	
	

	public WoLoadDetail(String woCode, String customerCode, String errorCode,
			String errorMessage, Date loadDate, Country country, WoLoad woLoad) {
		super();
		this.woCode = woCode;
		this.customerCode = customerCode;
		this.errorCode = errorCode;
		this.errorMessage = errorMessage;
		this.loadDate = loadDate;
		this.country = country;
		this.woLoad = woLoad;
	}

	public WoLoadDetail() {
		super();
	}

	public WorkorderStatus getWorkorderStatusPrevius() {
		return workorderStatusPrevius;
	}

	public void setWorkorderStatusPrevius(WorkorderStatus workorderStatusPrevius) {
		this.workorderStatusPrevius = workorderStatusPrevius;
	}

	public WorkorderStatus getWorkorderStatusNew() {
		return workorderStatusNew;
	}

	public void setWorkorderStatusNew(WorkorderStatus workorderStatusNew) {
		this.workorderStatusNew = workorderStatusNew;
	}

	public Ibs6Status getIbs6Status() {
		return ibs6Status;
	}

	public void setIbs6Status(Ibs6Status ibs6Status) {
		this.ibs6Status = ibs6Status;
	}

}
