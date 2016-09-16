package co.com.directv.sdii.model.pojo;

import java.util.Date;

import co.com.directv.sdii.audit.Auditable;

public class CrewMovement implements java.io.Serializable,Auditable{

	private static final long serialVersionUID = -4187864903532259553L;
	private Long id;
	private Long idCrew;
	private String dealerName;
	private String dealerMain;
	private String membersCrew;
	private String documentNumber;
	private String responsibleCrew;
	private String roleEmployeesCrew;
	private Date creationCrew;
	private Date crewActivationDate;
	private Date crewMoficationDate;
	private String newEmployee;
	private String documentNumberNewEmployee;
	private String newResponsibleCrew;
	private String rolNewEmployee;
	private String locationVinculateCode;
	private String statusCrew;
	private Vehicle idVehicle;
	private User userId;
	
	public CrewMovement() {
		
	}
	
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}	
	public Long getIdCrew() {
		return idCrew;
	}

	public void setIdCrew(Long idCrew) {
		this.idCrew = idCrew;
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

	public String getMembersCrew() {
		return membersCrew;
	}

	public void setMembersCrew(String membersCrew) {
		this.membersCrew = membersCrew;
	}

	public String getDocumentNumber() {
		return documentNumber;
	}

	public void setDocumentNumber(String documentNumber) {
		this.documentNumber = documentNumber;
	}

	public String getResponsibleCrew() {
		return responsibleCrew;
	}

	public void setResponsibleCrew(String responsibleCrew) {
		this.responsibleCrew = responsibleCrew;
	}

	public String getRoleEmployeesCrew() {
		return roleEmployeesCrew;
	}

	public void setRoleEmployeesCrew(String roleEmployeesCrew) {
		this.roleEmployeesCrew = roleEmployeesCrew;
	}

	public Date getCreationCrew() {
		return creationCrew;
	}

	public void setCreationCrew(Date creationCrew) {
		this.creationCrew = creationCrew;
	}

	public Date getCrewActivationDate() {
		return crewActivationDate;
	}

	public void setCrewActivationDate(Date crewActivationDate) {
		this.crewActivationDate = crewActivationDate;
	}

	public Date getCrewMoficationDate() {
		return crewMoficationDate;
	}

	public void setCrewMoficationDate(Date crewMoficationDate) {
		this.crewMoficationDate = crewMoficationDate;
	}

	public String getNewEmployee() {
		return newEmployee;
	}

	public void setNewEmployee(String newEmployee) {
		this.newEmployee = newEmployee;
	}

	public String getDocumentNumberNewEmployee() {
		return documentNumberNewEmployee;
	}

	public void setDocumentNumberNewEmployee(String documentNumberNewEmployee) {
		this.documentNumberNewEmployee = documentNumberNewEmployee;
	}

	public String getNewResponsibleCrew() {
		return newResponsibleCrew;
	}

	public void setNewResponsibleCrew(String newResponsibleCrew) {
		this.newResponsibleCrew = newResponsibleCrew;
	}

	public String getRolNewEmployee() {
		return rolNewEmployee;
	}

	public void setRolNewEmployee(String rolNewEmployee) {
		this.rolNewEmployee = rolNewEmployee;
	}

	public String getLocationVinculateCode() {
		return locationVinculateCode;
	}

	public void setLocationVinculateCode(String locationVinculateCode) {
		this.locationVinculateCode = locationVinculateCode;
	}

	public String getStatusCrew() {
		return statusCrew;
	}

	public void setStatusCrew(String statusCrew) {
		this.statusCrew = statusCrew;
	}

	public Vehicle getIdVehicle() {
		return idVehicle;
	}

	public void setIdVehicle(Vehicle idVehicle) {
		this.idVehicle = idVehicle;
	}

	public User getUserId() {
		return userId;
	}

	public void setUserId(User userId) {
		this.userId = userId;
	}
	
}
