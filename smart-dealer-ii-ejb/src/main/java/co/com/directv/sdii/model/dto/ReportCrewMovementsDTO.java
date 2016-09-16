package co.com.directv.sdii.model.dto;

import java.util.Date;

public class ReportCrewMovementsDTO {

	private static final long serialVersionUID = -6075621275834359839L;	

	private Long idCrew;
	private String dealerName;
	private String dealerMain;
	private String membersCrew;
	private String documentNumber;
	private String responsibleCrew;
	private String roleEmployeesCrew;
	private Date creationCrew;
	private Date crewActivationDate;
	private Date crewMofificationDate;
	private String newEmployee;
	private String documentNumberNewEmployee;
	private String newResponsibleCrew;
	private String rolNewEmployee;
	private String locationVinculateCode;
	private String statusCrew;
	private String plateVehicle;
	//private String nameDriverVehicle;
	private String brandVehicle;
	private String modelVehicle;
	private String colorVehicle;
	private String statusVehicle;
	private String typeVehicle;
	private Double loadCapacity;
	private Long peopleCapacity;
	private Date creationVehicleDate;
	
	/**
	 * Fecha y Hora Inicial
	*/
	private Date movementDateIn = null;

	/**
	 * Fecha y Hora Inicial
	*/
	private Date movementDateOut = null;
	
	public ReportCrewMovementsDTO(){
		super();
	}
	
	public ReportCrewMovementsDTO(Long idCrew, String dealerName,
			String dealerMain, String membersCrew, String documentNumber,
			String responsibleCrew, String roleEmployeesCrew,
			Date creationCrew, Date crewActivationDate,
			Date crewMofificationDate, String newEmployee,
			String documentNumberNewEmployee, String newResponsibleCrew,
			String rolNewEmployee, String locationVinculateCode,
			String statusCrew, String plateVehicle, //String nameDriverVehicle,
			String brandVehicle, String modelVehicle, String colorVehicle,
			String statusVehicle, String typeVehicle, Double loadCapacity,
			Long peopleCapacity, Date creationVehicleDate,
			Date movementDateIn, Date movementDateOut) {
		super();
		this.idCrew = idCrew;
		this.dealerName = dealerName;
		this.dealerMain = dealerMain;
		this.membersCrew = membersCrew;
		this.documentNumber = documentNumber;
		this.responsibleCrew = responsibleCrew;
		this.roleEmployeesCrew = roleEmployeesCrew;
		this.creationCrew = creationCrew;
		this.crewActivationDate = crewActivationDate;
		this.crewMofificationDate = crewMofificationDate;
		this.newEmployee = newEmployee;
		this.documentNumberNewEmployee = documentNumberNewEmployee;
		this.newResponsibleCrew = newResponsibleCrew;
		this.rolNewEmployee = rolNewEmployee;
		this.locationVinculateCode = locationVinculateCode;
		this.statusCrew = statusCrew;
		this.plateVehicle = plateVehicle;
		//this.nameDriverVehicle = nameDriverVehicle;
		this.brandVehicle = brandVehicle;
		this.modelVehicle = modelVehicle;
		this.colorVehicle = colorVehicle;
		this.statusVehicle = statusVehicle;
		this.typeVehicle = typeVehicle;
		this.loadCapacity = loadCapacity;
		this.peopleCapacity = peopleCapacity;
		this.creationVehicleDate = creationVehicleDate;
		this.movementDateIn = movementDateIn;
		this.movementDateOut = movementDateOut;
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

	public Date getCrewMofificationDate() {
		return crewMofificationDate;
	}

	public void setCrewMofificationDate(Date crewMofificationDate) {
		this.crewMofificationDate = crewMofificationDate;
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

	public String getPlateVehicle() {
		return plateVehicle;
	}

	public void setPlateVehicle(String plateVehicle) {
		this.plateVehicle = plateVehicle;
	}

	/*public String getNameDriverVehicle() {
		return nameDriverVehicle;
	}

	public void setNameDriverVehicle(String nameDriverVehicle) {
		this.nameDriverVehicle = nameDriverVehicle;
	}*/

	public String getBrandVehicle() {
		return brandVehicle;
	}

	public void setBrandVehicle(String brandVehicle) {
		this.brandVehicle = brandVehicle;
	}

	public String getModelVehicle() {
		return modelVehicle;
	}

	public void setModelVehicle(String modelVehicle) {
		this.modelVehicle = modelVehicle;
	}

	public String getColorVehicle() {
		return colorVehicle;
	}

	public void setColorVehicle(String colorVehicle) {
		this.colorVehicle = colorVehicle;
	}

	public String getStatusVehicle() {
		return statusVehicle;
	}

	public void setStatusVehicle(String statusVehicle) {
		this.statusVehicle = statusVehicle;
	}

	public String getTypeVehicle() {
		return typeVehicle;
	}

	public void setTypeVehicle(String typeVehicle) {
		this.typeVehicle = typeVehicle;
	}

	public Double getLoadCapacity() {
		return loadCapacity;
	}

	public void setLoadCapacity(Double loadCapacity) {
		this.loadCapacity = loadCapacity;
	}

	public Long getPeopleCapacity() {
		return peopleCapacity;
	}

	public void setPeopleCapacity(Long peopleCapacity) {
		this.peopleCapacity = peopleCapacity;
	}

	public Date getCreationVehicleDate() {
		return creationVehicleDate;
	}

	public void setCreationVehicleDate(Date creationVehicleDate) {
		this.creationVehicleDate = creationVehicleDate;
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