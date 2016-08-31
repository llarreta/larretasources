package co.com.directv.sdii.model.pojo;

import java.io.Serializable;
import java.sql.Blob;
import java.sql.Timestamp;
import java.util.Set;

/**
 * Entidad del evento del procesamiento en paralelo de core y asignador
 * @author Aharker
 *
 */
public class WoInfoEsbService implements Serializable{

	private Long id;
	private WoInfoEsbParentDate woInfoEsbParentDate;
	private Set<WoInfoEsbServiceLog> woInfoEsbServiceLogs;
	private Blob responseXml;
	private WoInfoEsbState stateWoInfo;
	private WoInfoEsbType woInfoEsbType;
	private String woCode;
	private String customerCode;
	private Long tryNumbers;
	private Timestamp creationDate;
	private Timestamp lastDateProccess;
	private Timestamp ibsCreationDate;
	private Long numIbsCreationDate;
	
	public WoInfoEsbService(Long id, WoInfoEsbParentDate woInfoEsbParentDate,
			Set<WoInfoEsbServiceLog> woInfoEsbServiceLogs, Blob responseXml,
			WoInfoEsbState stateWoInfo,
			WoInfoEsbType woInfoEsbType, String woCode,
			String customerCode, Long tryNumbers, Timestamp creationDate) {
		super();
		this.id = id;
		this.woInfoEsbParentDate = woInfoEsbParentDate;
		this.woInfoEsbServiceLogs = woInfoEsbServiceLogs;
		this.responseXml = responseXml;
		this.stateWoInfo = stateWoInfo;
		this.woInfoEsbType = woInfoEsbType;
		this.woCode = woCode;
		this.customerCode = customerCode;
		this.tryNumbers = tryNumbers;
		this.creationDate = creationDate;
	}
	
	public WoInfoEsbService(Long id, WoInfoEsbParentDate woInfoEsbParentDate,
			Set<WoInfoEsbServiceLog> woInfoEsbServiceLogs, Blob responseXml,
			WoInfoEsbState stateWoInfo, WoInfoEsbType woInfoEsbType,
			String woCode, String customerCode, Long tryNumbers,
			Timestamp creationDate, Timestamp lastDateProccess) {
		super();
		this.id = id;
		this.woInfoEsbParentDate = woInfoEsbParentDate;
		this.woInfoEsbServiceLogs = woInfoEsbServiceLogs;
		this.responseXml = responseXml;
		this.stateWoInfo = stateWoInfo;
		this.woInfoEsbType = woInfoEsbType;
		this.woCode = woCode;
		this.customerCode = customerCode;
		this.tryNumbers = tryNumbers;
		this.creationDate = creationDate;
		this.lastDateProccess = lastDateProccess;
	}

	public Timestamp getLastDateProccess() {
		return lastDateProccess;
	}

	public void setLastDateProccess(Timestamp lastDateProccess) {
		this.lastDateProccess = lastDateProccess;
	}

	public WoInfoEsbService() {
		super();
	}	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public WoInfoEsbParentDate getWoInfoEsbParentDate() {
		return woInfoEsbParentDate;
	}
	public void setWoInfoEsbParentDate(WoInfoEsbParentDate woInfoEsbParentDate) {
		this.woInfoEsbParentDate = woInfoEsbParentDate;
	}
	public Set<WoInfoEsbServiceLog> getWoInfoEsbServiceLogs() {
		return woInfoEsbServiceLogs;
	}
	public void setWoInfoEsbServiceLogs(
			Set<WoInfoEsbServiceLog> woInfoEsbServiceLogs) {
		this.woInfoEsbServiceLogs = woInfoEsbServiceLogs;
	}
	public Blob getResponseXml() {
		return responseXml;
	}
	public void setResponseXml(Blob responseXml) {
		this.responseXml = responseXml;
	}
	public WoInfoEsbState getStateWoInfo() {
		return stateWoInfo;
	}
	public void setStateWoInfo(WoInfoEsbState stateWoInfo) {
		this.stateWoInfo = stateWoInfo;
	}
	public WoInfoEsbType getWoInfoEsbType() {
		return woInfoEsbType;
	}
	public void setWoInfoEsbType(
			WoInfoEsbType woInfoEsbType) {
		this.woInfoEsbType = woInfoEsbType;
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
	public Long getTryNumbers() {
		return tryNumbers;
	}
	public void setTryNumbers(Long tryNumbers) {
		this.tryNumbers = tryNumbers;
	}
	public Timestamp getCreationDate() {
		return creationDate;
	}
	public void setCreationDate(Timestamp creationDate) {
		this.creationDate = creationDate;
	}

	public Timestamp getIbsCreationDate() {
		return ibsCreationDate;
	}

	public void setIbsCreationDate(Timestamp ibsCreationDate) {
		this.ibsCreationDate = ibsCreationDate;
	}

	public Long getNumIbsCreationDate() {
		return numIbsCreationDate;
	}

	public void setNumIbsCreationDate(Long numIbsCreationDate) {
		this.numIbsCreationDate = numIbsCreationDate;
	}
}
