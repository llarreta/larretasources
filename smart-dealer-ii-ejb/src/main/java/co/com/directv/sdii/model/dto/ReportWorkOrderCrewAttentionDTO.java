package co.com.directv.sdii.model.dto;

import java.util.Date;

public class ReportWorkOrderCrewAttentionDTO {

	private static final long serialVersionUID = -6075621275834359839L;
	
	private String dealerName;
	private String dealerMain;
	private String woCode;
	private Long crewId;
	private String employeesCrew;
	private String responsibleCrew;
	private String documentNumber;
	private String ibsTechnicial;
	private Date attentionDate;
	
	/**
	 * Fecha y Hora Inicial
	*/
	private Date movementDateIn = null;

	/**
	 * Fecha y Hora Inicial
	*/
	private Date movementDateOut = null;

	
	public ReportWorkOrderCrewAttentionDTO() {
		super();
	}
	
	public ReportWorkOrderCrewAttentionDTO(String dealerName,
			String dealerMain, String woCode, Long crewId,
			String employeesCrew, String responsibleCrew,
			String documentNumber, String ibsTechnicial, Date attentionDate,
			Date movementDateIn, Date movementDateOut) {
		super();
		this.dealerName = dealerName;
		this.dealerMain = dealerMain;
		this.woCode = woCode;
		this.crewId = crewId;
		this.employeesCrew = employeesCrew;
		this.responsibleCrew = responsibleCrew;
		this.documentNumber = documentNumber;
		this.ibsTechnicial = ibsTechnicial;
		this.attentionDate = attentionDate;
		this.movementDateIn = movementDateIn;
		this.movementDateOut = movementDateOut;
	}

	public String getDealerName() {
		return dealerName;
	}


	public void setDealerName(String dealerName) {
		this.dealerName = dealerName;
	}


	public String getDealerMain() {
		return dealerMain;
	}


	public void setDealerMain(String dealerMain) {
		this.dealerMain = dealerMain;
	}


	public String getWoCode() {
		return woCode;
	}


	public void setWoCode(String woCode) {
		this.woCode = woCode;
	}


	public Long getCrewId() {
		return crewId;
	}


	public void setCrewId(Long crewId) {
		this.crewId = crewId;
	}


	public String getEmployeesCrew() {
		return employeesCrew;
	}


	public void setEmployeesCrew(String employeesCrew) {
		this.employeesCrew = employeesCrew;
	}


	public String getResponsibleCrew() {
		return responsibleCrew;
	}


	public void setResponsibleCrew(String responsibleCrew) {
		this.responsibleCrew = responsibleCrew;
	}


	public String getDocumentNumber() {
		return documentNumber;
	}


	public void setDocumentNumber(String documentNumber) {
		this.documentNumber = documentNumber;
	}


	public String getIbsTechnicial() {
		return ibsTechnicial;
	}


	public void setIbsTechnicial(String ibsTechnicial) {
		this.ibsTechnicial = ibsTechnicial;
	}


	public Date getAttentionDate() {
		return attentionDate;
	}


	public void setAttentionDate(Date attentionDate) {
		this.attentionDate = attentionDate;
	}

	
	public Date getMovementDateIn() {
		return movementDateIn;
	}

	public void setMovementDateIn(Date movementDateIn) {
		this.movementDateIn = movementDateIn;
	}

	public Date getMovementDateOut() {
		return movementDateOut;
	}

	public void setMovementDateOut(Date movementDateOut) {
		this.movementDateOut = movementDateOut;
	}

	
}
