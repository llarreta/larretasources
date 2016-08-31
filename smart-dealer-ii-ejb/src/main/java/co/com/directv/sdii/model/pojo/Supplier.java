package co.com.directv.sdii.model.pojo;

import co.com.directv.sdii.audit.Auditable;



/**
 * Supplier entity. @author MyEclipse Persistence Tools
 */

public class Supplier implements java.io.Serializable,Auditable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = -3489577179918655622L;
	private Long id;
	private String supplierCode;
	private String supplierName;
	private String supplierNit;
	private String address;
	private String district;
	private Country countryId;
	private PostalCode postalCode;
	private String phoneNumber;
	private String fax;
	private String contactName;
	private String isActive;
	private Country countryIdHSP;
	
	
	
	
	// Constructors

	/** default constructor */
	public Supplier() {
	}

	/** minimal constructor */
	public Supplier(String supplierCode, String supplierName, Country countryId) {
		this.supplierCode = supplierCode;
		this.supplierName = supplierName;
		this.countryId = countryId;
	}

	/** full constructor */
	public Supplier(String supplierCode, String supplierName,
			String supplierNit, Country countryId,String address,
			String district,State state,City city,PostalCode postalCode,
			String phoneNumber,String fax,String contactName,String isActive,
			Country countryIdHSP
			) {
		this.supplierCode = supplierCode;
		this.supplierName = supplierName;
		this.supplierNit = supplierNit;
		this.countryId = countryId;
		this.address=address;
		this.district=district;
		this.postalCode=postalCode;
		this.phoneNumber=phoneNumber;
		this.fax=fax;
		this.contactName=contactName;
		this.isActive=isActive;
		this.countryIdHSP = countryIdHSP;
	}

	// Property accessors

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getSupplierCode() {
		return this.supplierCode;
	}

	public void setSupplierCode(String supplierCode) {
		this.supplierCode = supplierCode;
	}

	public String getSupplierName() {
		return this.supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	public String getSupplierNit() {
		return this.supplierNit;
	}

	public void setSupplierNit(String supplierNit) {
		this.supplierNit = supplierNit;
	}

	public Country getCountryId() {
		return this.countryId;
	}

	public void setCountryId(Country countryId) {
		this.countryId = countryId;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

		
	public PostalCode getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(PostalCode postalCode) {
		this.postalCode = postalCode;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getContactName() {
		return contactName;
	}

	public void setContactName(String contactName) {
		this.contactName = contactName;
	}

	public String getIsActive() {
		return isActive;
	}

	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}

	@Override
	public String toString() {
		return "Supplier [id=" + id + ", supplierCode=" + supplierCode
				+ ", supplierNit=" + supplierNit + "]";
	}
	
	public Country getCountryIdHSP() {
		return this.countryIdHSP;
	}

	public void setCountryIdHSP(Country countryIdHSP) {
		this.countryIdHSP = countryIdHSP;
	}
	
}