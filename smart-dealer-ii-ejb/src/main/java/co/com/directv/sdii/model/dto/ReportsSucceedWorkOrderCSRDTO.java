package co.com.directv.sdii.model.dto;

import java.io.Serializable;
import java.util.Date;

public class ReportsSucceedWorkOrderCSRDTO implements Serializable{
    
	

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7411183078489659283L;

	/*
	 *  Código de WorkOrder
	 * */
    private	String woCode;	
	
	/*
	 * Fecha de proceso
	 * */
	private Date dateLastChange;	
	
	/*
	 * Fecha agendada
	 * */
	private Date scheduleDate;
	
	/*
	 * Nombre de usuario 
	 * */
	private String userName;
	
	/*
	 * Login de usuario
	 * */
    private String	login;
	
	/*
	 * Numero de código postal
	 * */
	private String postalCode;
	
	/* 
	 * Nombre de código postal
	 * */
	private String postalCodeName;
	
	/*
	 * Nombre de dealer
	 * */
	private String dealerName;

	/*
	 * Codigo de dealer
	 * */
	private String dealerCode;
	
	/*
	 * Servicio
	 * */
	private String service;

	/*
	 * IsScheduled
	 * */
	private String isScheduled;
	
	public ReportsSucceedWorkOrderCSRDTO(String woCode, String userName,
			String login, String postalCode, String postalCodeName,
			String dealerName, String service, String isScheduled) {
		super();
		this.woCode = woCode;
		this.userName = userName;
		this.login = login;
		this.postalCode = postalCode;
		this.postalCodeName = postalCodeName;
		this.dealerName = dealerName;
		this.service = service;
		this.isScheduled = isScheduled;
		
		if (woCode!=null) this.woCode = woCode;
		else this.woCode = "";
		
		if (userName!=null) this.userName=userName;
		else this.userName="";
		
		if (login!=null) this.login=login;
		else this.login = "";
	
		if (postalCode!=null) this.postalCode = postalCode;
		else this.postalCode ="";
		
		if (postalCodeName!=null) this.postalCodeName = postalCodeName;
		else this.postalCodeName = "";
		
		if (dealerName!=null) this.dealerName = dealerName;
		else this.dealerName = "";
		
		if (service!=null) this.service = service;
		else this.service = "";
		
	}
	
	public ReportsSucceedWorkOrderCSRDTO(){
		super();
	}
	
	

	public String getWoCode() {
		return woCode;
	}

	public void setWoCode(String woCode) {
		this.woCode = woCode;
	}

	public Date getDateLastChange() {
		return dateLastChange;
	}

	public void setDateLastChange(Date dateLastChange) {
		this.dateLastChange = dateLastChange;
	}

	public Date getScheduleDate() {
		return scheduleDate;
	}

	public void setScheduleDate(Date scheduleDate) {
		this.scheduleDate = scheduleDate;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	public String getPostalCodeName() {
		return postalCodeName;
	}

	public void setPostalCodeName(String postalCodeName) {
		this.postalCodeName = postalCodeName;
	}

	public String getDealerName() {
		return dealerName;
	}

	public void setDealerName(String dealerName) {
		this.dealerName = dealerName;
	}

	public String getService() {
		return service;
	}

	public void setService(String service) {
		this.service = service;
	}

	public String getDealerCode() {
		return dealerCode;
	}

	public void setDealerCode(String dealerCode) {
		this.dealerCode = dealerCode;
	}

	public String getIsScheduled() {
		return isScheduled;
	}

	public void setIsScheduled(String isScheduled) {
		this.isScheduled = isScheduled;
	}	
	
	
	

}
