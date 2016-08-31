package co.com.directv.sdii.model.dto;

import java.util.Date;
import java.io.Serializable;

/**
 * 
 * 
 * Fecha de Creación: 2012/10/02
 * @author martlago/carlopez
 * @version 1.0
 * 
 * @see     
 */
public class ReportsSucceedWorkOrderFilterDTO implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4386205646510790830L;

	/*
	 * Codigo pais 
	 * */
	private String countryCode;

	/*
	 * Fechas Desde/Hasta WO 
	 * */
	private Date fromDate;
	private Date toDate;
	
	private boolean isFileResponse;
	private String fileName;
	
		
	public boolean isFileResponse() {
		return isFileResponse;
	}
	public void setFileResponse(boolean isFileResponse) {
		this.isFileResponse = isFileResponse;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getCountryCode() {
		return countryCode;
	}
	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}
	public Date getFromDate() {
		return fromDate;
	}
	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}
	public Date getToDate() {
		return toDate;
	}
	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}

}
