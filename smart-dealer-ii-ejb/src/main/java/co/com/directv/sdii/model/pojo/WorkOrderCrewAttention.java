package co.com.directv.sdii.model.pojo;

import java.util.Date;

import co.com.directv.sdii.audit.Auditable;

public class WorkOrderCrewAttention implements java.io.Serializable,Auditable{

	private static final long serialVersionUID = -4187864903532259553L;
	
	private Long id;
	private String dealerName;
	private String dealerMain;
	private String woCode;
	private Long crewId;
	private String employeesCrew;
	private String responsibleCrew;
	private String documentNumber;
	private String ibsTechnicial;
	private Date attentionDate;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
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

}
