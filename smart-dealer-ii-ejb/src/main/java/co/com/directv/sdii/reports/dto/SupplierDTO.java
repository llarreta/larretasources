package co.com.directv.sdii.reports.dto;

public class SupplierDTO implements java.io.Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7327693270676268819L;
	
	private String supplierNit;
	private String supplierName;
	private String address;
	private String district;
	private String cityName;
	private String stateName;
	private String countryName;
	private String postalCode;
	private String fax;
	private String phoneNumber;
	private String contactName;
	private String isActive;
	private String supplierCode;
	
	public String getSupplierNit() {
		return supplierNit;
	}
	public void setSupplierNit(String supplierNit) {
		this.supplierNit = supplierNit;
	}
	public String getSupplierName() {
		return supplierName;
	}
	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
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
	public String getCityName() {
		return cityName;
	}
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	public String getStateName() {
		return stateName;
	}
	public void setStateName(String stateName) {
		this.stateName = stateName;
	}
	public String getCountryName() {
		return countryName;
	}
	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}
	public String getPostalCode() {
		return postalCode;
	}
	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}
	public String getFax() {
		return fax;
	}
	public void setFax(String fax) {
		this.fax = fax;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
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
	public void setSupplierCode(String supplierCode) {
		this.supplierCode = supplierCode;
	}
	public String getSupplierCode() {
		return supplierCode;
	}

}
