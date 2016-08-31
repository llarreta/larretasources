package co.com.directv.sdii.model.pojo;

import java.io.Serializable;

/**
 * Req-0096 - Requerimiento Consolidado Asignador / Automatizacion, Relacion de Edificios para Asignacion de Work Orders
 * Clase encargada de ser el mapeo en objetos de la tabla BUILDING_ADDRESSES
 * @author ialessan
 *
 */
public class BuildingAddresses implements Serializable {

	private static final long serialVersionUID = 3702947424773809147L;
	
	private Long id;
	private Long buildingId;
	private String streetName;
	private PostalCode postalCode;
	private String streetSuffix;
	private String neighborhood;
	private String careOfName;
	private String extraIndications;
	private String addressCode;
	private AddressType addressType;
	
	private String streetNrLast;
	private String streetNrFirst;
	
	private String flatApartment;
	private String active;
	
	public BuildingAddresses(Long buildingId, String streetName,
			PostalCode postalCode, String streetSufix, String neighborhood,
			String careOfName, String extraIndications, String addressCode,
			AddressType addressType, Long id, String streetNrLast, String streetNrFirst, String flatApartment) {
		super();
		this.id=id;
		this.buildingId = buildingId;
		this.streetName = streetName;
		this.postalCode = postalCode;
		this.streetSuffix = streetSufix;
		this.neighborhood = neighborhood;
		this.careOfName = careOfName;
		this.extraIndications = extraIndications;
		this.addressCode = addressCode;
		this.addressType = addressType;
		this.streetNrFirst=streetNrFirst;
		this.streetNrLast=streetNrLast;
		this.flatApartment=flatApartment;
		
	}
	public BuildingAddresses(BuildingAddresses copy) {
		super();
		this.id=copy.id;
		this.buildingId = copy.buildingId;
		this.streetName = copy.streetName;
		this.postalCode = copy.postalCode;
		this.streetSuffix = copy.streetSuffix;
		this.neighborhood = copy.neighborhood;
		this.careOfName = copy.careOfName;
		this.extraIndications = copy.extraIndications;
		this.addressCode = copy.addressCode;
		this.addressType = copy.addressType;
	}
	public BuildingAddresses() {
		super();
	}	

	public String getStreetNrLast() {
		return streetNrLast;
	}
	public void setStreetNrLast(String streetNrLast) {
		this.streetNrLast = streetNrLast;
	}
	public String getStreetNrFirst() {
		return streetNrFirst;
	}
	public void setStreetNrFirst(String streetNrFirst) {
		this.streetNrFirst = streetNrFirst;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getBuildingId() {
		return buildingId;
	}
	public void setBuildingId(Long buildingId) {
		this.buildingId = buildingId;
	}
	public String getStreetName() {
		return streetName;
	}
	public void setStreetName(String streetName) {
		this.streetName = streetName;
	}
	public PostalCode getPostalCode() {
		return postalCode;
	}
	public void setPostalCode(PostalCode postalCode) {
		this.postalCode = postalCode;
	}
	public String getStreetSuffix() {
		return streetSuffix;
	}
	public void setStreetSuffix(String streetSuffix) {
		this.streetSuffix = streetSuffix;
	}
	public String getNeighborhood() {
		return neighborhood;
	}
	public void setNeighborhood(String neighborhood) {
		this.neighborhood = neighborhood;
	}
	public String getCareOfName() {
		return careOfName;
	}
	public void setCareOfName(String careOfName) {
		this.careOfName = careOfName;
	}
	public String getExtraIndications() {
		return extraIndications;
	}
	public void setExtraIndications(String extraIndications) {
		this.extraIndications = extraIndications;
	}
	public String getAddressCode() {
		return addressCode;
	}
	public void setAddressCode(String addressCode) {
		this.addressCode = addressCode;
	}
	public AddressType getAddressType() {
		return addressType;
	}
	public void setAddressType(AddressType addressType) {
		this.addressType = addressType;
	}
	public String getFlatApartment() {
		return flatApartment;
	}
	public void setFlatApartment(String flatApartment) {
		this.flatApartment = flatApartment;
	}
	public String getActive() {
		return active;
	}
	public void setActive(String active) {
		this.active = active;
	}
	
}
