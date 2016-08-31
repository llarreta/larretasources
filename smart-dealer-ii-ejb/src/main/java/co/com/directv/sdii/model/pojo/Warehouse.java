package co.com.directv.sdii.model.pojo;



/**
 * Warehouse entity. @author MyEclipse Persistence Tools
 */

public class Warehouse implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 3446587899946281309L;
	private Long id;
	private WarehouseType warehouseType;
	private Dealer dealerId;
	private Crew crewId;
	private Customer customerId;
	private Country country;
	private String whResponsible;
	private String responsibleEmail;
	private String whAddress;
	private String whCode;
	//gfandino-01/06/2011 Se adiciona por incluir estado de Warehouse INV104 v2.2
	private String isActive;
	// Constructors

	/** default constructor */
	public Warehouse() {
	}
	
	public Warehouse(Long id) {
		this.id = id;
	}

	/** minimal constructor */
	public Warehouse(WarehouseType warehouseType, String whResponsible,
			String whCode) {
		this.warehouseType = warehouseType;
		this.whResponsible = whResponsible;
		this.whCode = whCode;
	}

	/** full constructor */
	public Warehouse(WarehouseType warehouseType,
			Dealer dealerId, Crew crewId, Customer customerId, Country country, String whResponsible,
			String responsibleEmail, String whAddress, String whCode) {
		this.warehouseType = warehouseType;
		this.dealerId = dealerId;
		this.crewId = crewId;
		this.customerId = customerId;
		this.country = country;
		this.whResponsible = whResponsible;
		this.responsibleEmail = responsibleEmail;
		this.whAddress = whAddress;
		this.whCode = whCode;
	}

	// Property accessors

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public WarehouseType getWarehouseType() {
		return this.warehouseType;
	}

	public void setWarehouseType(WarehouseType warehouseType) {
		this.warehouseType = warehouseType;
	}

	public Dealer getDealerId() {
		return this.dealerId;
	}

	public void setDealerId(Dealer dealerId) {
		this.dealerId = dealerId;
	}

	public Crew getCrewId() {
		return this.crewId;
	}

	public void setCrewId(Crew crewId) {
		this.crewId = crewId;
	}

	public Customer getCustomerId() {
		return this.customerId;
	}

	public void setCustomerId(Customer customerId) {
		this.customerId = customerId;
	}

	public String getWhResponsible() {
		return this.whResponsible;
	}

	public void setWhResponsible(String whResponsible) {
		this.whResponsible = whResponsible;
	}

	public String getResponsibleEmail() {
		return this.responsibleEmail;
	}

	public void setResponsibleEmail(String responsibleEmail) {
		this.responsibleEmail = responsibleEmail;
	}

	public String getWhAddress() {
		return this.whAddress;
	}

	public void setWhAddress(String whAddress) {
		this.whAddress = whAddress;
	}

	public String getWhCode() {
		return this.whCode;
	}

	public void setWhCode(String whCode) {
		this.whCode = whCode;
	}

	public Country getCountry() {
		return country;
	}

	public void setCountry(Country country) {
		this.country = country;
	}
		

	public String getIsActive() {
		return isActive;
	}

	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}

	@Override
	public String toString() {
		return "Warehouse [id=" + id + ", whCode=" + whCode + "]";
	}
}