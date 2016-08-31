package co.com.directv.sdii.model.pojo;

import java.io.Serializable;

/**
 * Clase encargada de ser el mapeo en objetos de la tabla CUSTOMER_ADDRESSES
 * @author aharker
 *
 */
public class CustomerAddresses implements Serializable{

	private Long id;
	private Long customerId;
	private String streetName;
	private PostalCode postalCode;
	private String streetSufix;
	private String neighborhood;
	private String careOfName;
	private String extraIndications;
	private String addressCode;
	private AddressType addressType;
	
	private String streetNrLast;
	private String streetNrFirst;

	private String active;
	
	public CustomerAddresses(Long customerId, String streetName,
			PostalCode postalCode, String streetSufix, String neighborhood,
			String careOfName, String extraIndications, String addressCode,
			AddressType addressType, Long id, String streetNrLast, String streetNrFirst) {
		super();
		this.id=id;
		this.customerId = customerId;
		this.streetName = streetName;
		this.postalCode = postalCode;
		this.streetSufix = streetSufix;
		this.neighborhood = neighborhood;
		this.careOfName = careOfName;
		this.extraIndications = extraIndications;
		this.addressCode = addressCode;
		this.addressType = addressType;
		this.streetNrFirst=streetNrFirst;
		this.streetNrLast=streetNrLast;
	}
	public CustomerAddresses(CustomerAddresses copy) {
		super();
		this.id=copy.id;
		this.customerId = copy.customerId;
		this.streetName = copy.streetName;
		this.postalCode = copy.postalCode;
		this.streetSufix = copy.streetSufix;
		this.neighborhood = copy.neighborhood;
		this.careOfName = copy.careOfName;
		this.extraIndications = copy.extraIndications;
		this.addressCode = copy.addressCode;
		this.addressType = copy.addressType;
	}
	public CustomerAddresses() {
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
	public String getActive() {
		return active;
	}
	public void setActive(String active) {
		this.active = active;
	}
	public Long getCustomerId() {
		return customerId;
	}
	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
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
	public String getStreetSufix() {
		return streetSufix;
	}
	public void setStreetSufix(String streetSufix) {
		this.streetSufix = streetSufix;
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
	
}
