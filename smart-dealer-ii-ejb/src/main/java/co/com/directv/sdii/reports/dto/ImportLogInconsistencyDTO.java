package co.com.directv.sdii.reports.dto;

import java.util.Date;

//Modificado para Requerimiento: CC057
public class ImportLogInconsistencyDTO implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3024653844940173497L;
	
	private String element;
	private String inconsistencyType;
	private String obsInconsistency;
	private String userCreate;
	private Date dateIn;
	private String status;
	private String inconsistencyResponse;
	private String elementTypeCode;
	
	
	public String getElementTypeCode() {
		return elementTypeCode;
	}
	public void setElementTypeCode(String elementTypeCode) {
		this.elementTypeCode = elementTypeCode;
	}
	public String getElement() {
		return element;
	}
	public void setElement(String element) {
		this.element = element;
	}
	public String getInconsistencyType() {
		return inconsistencyType;
	}
	public void setInconsistencyType(String inconsistencyType) {
		this.inconsistencyType = inconsistencyType;
	}
	public String getObsInconsistency() {
		return obsInconsistency;
	}
	public void setObsInconsistency(String obsInconsistency) {
		this.obsInconsistency = obsInconsistency;
	}
	public String getUserCreate() {
		return userCreate;
	}
	public void setUserCreate(String userCreate) {
		this.userCreate = userCreate;
	}
	public Date getDateIn() {
		return dateIn;
	}
	public void setDateIn(Date dateIn) {
		this.dateIn = dateIn;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getInconsistencyResponse() {
		return inconsistencyResponse;
	}
	public void setInconsistencyResponse(String inconsistencyResponse) {
		this.inconsistencyResponse = inconsistencyResponse;
	}
	

}
