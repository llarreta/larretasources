package co.com.directv.sdii.model.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * DTO, enfocada a los reportes de fallos de procesamiento en paralelo de core y asignador
 * @author Aharker
 *
 */
public class WoInfoEsbServiceReportDTO implements Serializable{

	private String woCode;
	private String customerCode;
	private String errorMessage;
	private Date dateProcess;
	private Long tryNumber;
	private Long id;
	
	public WoInfoEsbServiceReportDTO(String woCode, String customerCode,
			String errorMessage, Date dateProcess, Long tryNumber, Long id) {
		super();
		this.woCode = woCode;
		this.customerCode = customerCode;
		this.errorMessage = errorMessage;
		this.dateProcess = dateProcess;
		this.tryNumber = tryNumber;
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public WoInfoEsbServiceReportDTO(String woCode, String customerCode,
			String errorMessage, Long tryNumber) {
		super();
		this.woCode = woCode;
		this.customerCode = customerCode;
		this.errorMessage = errorMessage;
		this.tryNumber = tryNumber;
	}

	public WoInfoEsbServiceReportDTO(String woCode, String customerCode,
			String errorMessage) {
		super();
		this.woCode = woCode;
		this.customerCode = customerCode;
		this.errorMessage = errorMessage;
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
	public String getErrorMessage() {
		return errorMessage;
	}
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	public Date getDateProcess() {
		return dateProcess;
	}
	public void setDateProcess(Date dateProcess) {
		this.dateProcess = dateProcess;
	}
	public Long getTryNumber() {
		return tryNumber;
	}
	public void setTryNumber(Long tryNumber) {
		this.tryNumber = tryNumber;
	}
	
}
