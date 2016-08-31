package co.com.directv.sdii.model.pojo;

import java.util.HashSet;
import java.util.Set;

import co.com.directv.sdii.audit.Auditable;


/**
 * Customer entity. @author MyEclipse Persistence Tools
 */

public class Customer implements java.io.Serializable,Auditable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 1849283245668538401L;
	private Long id;
	private CustomerTitle customerTitle;
	private PostalCode postalCode;
	private String customerCode;
	private String firstName;
	private String lastName;
	private Long documentTypeId;
	private String documentNumber;
	private Long addressCode;
	private Long addressType;
	private String customeraddress;
	private String aditionalIndications;
	private String neighborhood;
	private String extraIndications;
	private String legalRepresentative;
	private Country country;
	private CustomerClassType custType;
	private Set<CustomerMediaContact> customerMediaContacts = new HashSet<CustomerMediaContact>(0);
	private String isMigrated;
	
	//Req-0096 - Requerimiento Consolidado Asignador / Automatizacion, Relacion de Edificios para Asignacion de Work Orders
	private Building building;
	
	private Set<CustomerAddresses> customerAddresses;
	/*Paquete de programcion del cliente*/
	private String product;

	// Constructors

	/** default constructor */
	public Customer() {
	}
	
	

	public Customer(Long id) {
		super();
		this.id = id;
	}



	/** c constructor */
	public Customer(CustomerTitle customerTitle, PostalCode postalCode,
			String customerCode, String firstName, String lastName,
			Long documentTypeId, String documentNumber, Long addressCode,
			Long addressType, String customeraddress, String neighborhood) {
		this.customerTitle = customerTitle;
		this.postalCode = postalCode;
		this.customerCode = customerCode;
		this.firstName = firstName;
		this.lastName = lastName;
		this.documentTypeId = documentTypeId;
		this.documentNumber = documentNumber;
		this.addressCode = addressCode;
		this.addressType = addressType;
		this.customeraddress = customeraddress;
		this.neighborhood = neighborhood;
	}
	
	/** minimal constructor */
	public Customer(CustomerTitle customerTitle, PostalCode postalCode,
			String customerCode, String firstName, String lastName,
			Long documentTypeId, String documentNumber, Long addressCode,
			Long addressType, String customeraddress, String neighborhood,Country country, CustomerClassType custType) {
		this.customerTitle = customerTitle;
		this.postalCode = postalCode;
		this.customerCode = customerCode;
		this.firstName = firstName;
		this.lastName = lastName;
		this.documentTypeId = documentTypeId;
		this.documentNumber = documentNumber;
		this.addressCode = addressCode;
		this.addressType = addressType;
		this.customeraddress = customeraddress;
		this.neighborhood = neighborhood;
		this.country = country;
		this.custType = custType;
	}
	
	/** full constructor */
	public Customer(CustomerTitle customerTitle, PostalCode postalCode,
			String customerCode, String firstName, String lastName,
			Long documentTypeId, String documentNumber, Long addressCode,
			Long addressType, String customeraddress, String neighborhood,Country country, 
			CustomerClassType custType,Set<CustomerMediaContact> customerMediaContacts) {
		this.customerTitle = customerTitle;
		this.postalCode = postalCode;
		this.customerCode = customerCode;
		this.firstName = firstName;
		this.lastName = lastName;
		this.documentTypeId = documentTypeId;
		this.documentNumber = documentNumber;
		this.addressCode = addressCode;
		this.addressType = addressType;
		this.customeraddress = customeraddress;
		this.neighborhood = neighborhood;
		this.country = country;
		this.custType = custType;
		this.customerMediaContacts = customerMediaContacts;
	}
	
	public Customer(Long id, CustomerTitle customerTitle,
			PostalCode postalCode, String customerCode, String firstName,
			String lastName, Long documentTypeId, String documentNumber,
			Long addressCode, Long addressType, String customeraddress,
			String aditionalIndications, String neighborhood,
			String extraIndications, String legalRepresentative,
			Country country, CustomerClassType custType) {
		super();
		this.id = id;
		this.customerTitle = customerTitle;
		this.postalCode = postalCode;
		this.customerCode = customerCode;
		this.firstName = firstName;
		this.lastName = lastName;
		this.documentTypeId = documentTypeId;
		this.documentNumber = documentNumber;
		this.addressCode = addressCode;
		this.addressType = addressType;
		this.customeraddress = customeraddress;
		this.aditionalIndications = aditionalIndications;
		this.neighborhood = neighborhood;
		this.extraIndications = extraIndications;
		this.legalRepresentative = legalRepresentative;
		this.country = country;
		this.custType = custType;
	}

	// Property accessors

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public CustomerTitle getCustomerTitle() {
		return this.customerTitle;
	}

	public void setCustomerTitle(CustomerTitle customerTitle) {
		this.customerTitle = customerTitle;
	}

	public PostalCode getPostalCode() {
		return this.postalCode;
	}

	public void setPostalCode(PostalCode postalCode) {
		this.postalCode = postalCode;
	}

	public String getCustomerCode() {
		return this.customerCode;
	}

	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}

	public String getFirstName() {
		return this.firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return this.lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Long getDocumentTypeId() {
		return this.documentTypeId;
	}

	public void setDocumentTypeId(Long documentTypeId) {
		this.documentTypeId = documentTypeId;
	}

	public String getDocumentNumber() {
		return this.documentNumber;
	}

	public void setDocumentNumber(String documentNumber) {
		this.documentNumber = documentNumber;
	}

	public Long getAddressCode() {
		return this.addressCode;
	}

	public void setAddressCode(Long addressCode) {
		this.addressCode = addressCode;
	}

	public Long getAddressType() {
		return this.addressType;
	}

	public void setAddressType(Long addressType) {
		this.addressType = addressType;
	}

	public String getCustomeraddress() {
		return this.customeraddress;
	}

	public void setCustomeraddress(String customeraddress) {
		this.customeraddress = customeraddress;
	}

	public String getAditionalIndications() {
		return this.aditionalIndications;
	}

	public void setAditionalIndications(String aditionalIndications) {
		this.aditionalIndications = aditionalIndications;
	}

	public String getNeighborhood() {
		return this.neighborhood;
	}

	public void setNeighborhood(String neighborhood) {
		this.neighborhood = neighborhood;
	}

	public String getExtraIndications() {
		return this.extraIndications;
	}

	public void setExtraIndications(String extraIndications) {
		this.extraIndications = extraIndications;
	}

	public String getLegalRepresentative() {
		return this.legalRepresentative;
	}

	public void setLegalRepresentative(String legalRepresentative) {
		this.legalRepresentative = legalRepresentative;
	}

	public Country getCountry() {
		return country;
	}

	public void setCountry(Country country) {
		this.country = country;
	}

	public CustomerClassType getCustType() {
		return custType;
	}

	public void setCustType(CustomerClassType custType) {
		this.custType = custType;
	}
	public Set<CustomerMediaContact> getCustomerMediaContacts() {
		return this.customerMediaContacts;
	}

	public void setCustomerMediaContacts(Set<CustomerMediaContact> customerMediaContacts) {
		this.customerMediaContacts = customerMediaContacts;
	}

	public String getIsMigrated() {
		return isMigrated;
	}

	public void setIsMigrated(String isMigrated) {
		this.isMigrated = isMigrated;
	}

	public Building getBuilding() {
		return building;
	}

	public void setBuilding(Building building) {
		this.building = building;
	}
	
	public Set<CustomerAddresses> getCustomerAddresses() {
		return customerAddresses;
	}

	public void setCustomerAddresses(Set<CustomerAddresses> customerAddresses) {
		this.customerAddresses = customerAddresses;
	}
	
	public void setProduct(String product){
		this.product=product;
	}
	
	public String getProduct(){
		return this.product;
	}

	@Override
	public String toString() {
		return "Customer [customerCode=" + customerCode + ", id=" + id + "]";
	}
}