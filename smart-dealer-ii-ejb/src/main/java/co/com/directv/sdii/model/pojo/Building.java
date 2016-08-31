package co.com.directv.sdii.model.pojo;

import java.util.Date;
import java.util.Set;

/**
 * Building entity. @author MyEclipse Persistence Tools
 */

public class Building implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = -270152796826549315L;
	private Long id;
	private Country country;
	private Long code;
	private String name;
	private Date importDate;
	private Date lastUpdateDate;
	
	//Req-0096 - Requerimiento Consolidado Asignador / Automatizacion, Relacion de Edificios para Asignacion de Work Orders
    private Long dealerId;
    private String typeChange;
    
    private Set<BuildingAddresses> buildingAddresses;
	
	// Constructors

	/** default constructor */
	public Building() {
	}

	/**
	 * 
	 * @param country
	 * @param code
	 * @param name
	 * @param importDate
	 * @param lastUpdateDate
	 */
	public Building(Country country, Long code, String name, Date importDate, Date lastUpdateDate) {
		this.country = country;
		this.code = code;
		this.name = name;
		this.importDate = importDate;
		this.lastUpdateDate = lastUpdateDate;
	}

	// Property accessors

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Country getCountry() {
		return this.country;
	}

	public void setCountry(Country country) {
		this.country = country;
	}

	public Long getCode() {
		return this.code;
	}

	public void setCode(Long code) {
		this.code = code;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getImportDate() {
		return this.importDate;
	}

	public void setImportDate(Date importDate) {
		this.importDate = importDate;
	}

	public Date getLastUpdateDate() {
		return this.lastUpdateDate;
	}

	public void setLastUpdateDate(Date lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}

	public Long getDealerId() {
		return dealerId;
	}

	public void setDealerId(Long dealerId) {
		this.dealerId = dealerId;
	}

	public String getTypeChange() {
		return typeChange;
	}

	public void setTypeChange(String typeChange) {
		this.typeChange = typeChange;
	}

	public Set<BuildingAddresses> getBuildingAddresses() {
		return buildingAddresses;
	}

	public void setBuildingAddresses(Set<BuildingAddresses> buildingAddresses) {
		this.buildingAddresses = buildingAddresses;
	}
	
}