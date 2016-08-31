package co.com.directv.sdii.reports.dto;

import java.util.Date;

public class SerializedDTO implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1300370510673117471L;
	
	private String elementTypeName;
	private String elementModelName;
	private String serialCode;
	private String ird;
	private String linkedSerialCode;
	private String elementStatusName;
	private Date registrationDate;
	
	public String getElementTypeName() {
		return elementTypeName;
	}
	public void setElementTypeName(String elementTypeName) {
		this.elementTypeName = elementTypeName;
	}
	public String getElementModelName() {
		return elementModelName;
	}
	public void setElementModelName(String elementModelName) {
		this.elementModelName = elementModelName;
	}
	public String getSerialCode() {
		return serialCode;
	}
	public void setSerialCode(String serialCode) {
		this.serialCode = serialCode;
	}
	public String getIrd() {
		return ird;
	}
	public void setIrd(String ird) {
		this.ird = ird;
	}
	public String getLinkedSerialCode() {
		return linkedSerialCode;
	}
	public void setLinkedSerialCode(String linkedSerialCode) {
		this.linkedSerialCode = linkedSerialCode;
	}
	public String getElementStatusName() {
		return elementStatusName;
	}
	public void setElementStatusName(String elementStatusName) {
		this.elementStatusName = elementStatusName;
	}
	public Date getRegistrationDate() {
		return registrationDate;
	}
	public void setRegistrationDate(Date registrationDate) {
		this.registrationDate = registrationDate;
	}

}
