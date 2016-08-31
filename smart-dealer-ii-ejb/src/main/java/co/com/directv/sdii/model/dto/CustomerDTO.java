package co.com.directv.sdii.model.dto;

import java.io.Serializable;
import java.util.List;

import co.com.directv.sdii.model.vo.CustomerClassTypeVO;
import co.com.directv.sdii.model.vo.CustomerMediaContactVO;
import co.com.directv.sdii.model.vo.DocumentTypeVO;

/**
 * 
 * Data Transfer Object Customer
 * Objeto liviano, encapsula la informacion basica del 
 * cliente, cualquier modificacion de este objeto debe
 * ser verifiicado en el proyecto para determinar el impacto
 * sobre otros componentes. 
 * 
 * Fecha de Creación: 4/05/2011
 * @author jalopez <a href="mailto:jalopez@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
public class CustomerDTO implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6287143018842312729L;
	
	public static final String CUSTOMER_NAME_IS_MIGRATED = "CLIENTE MIGRADO";
	
	public static final String CUSTOMER_NAME_IS_NOT_MIGRATED = "CLIENTE NO MIGRADO";
	
	public CustomerDTO() {
		super();
	}
	
	private Long customerId;
	private String customerCode;
	private String firstNames;
	private String lastNames;
	private String documentNumber;
	private String address;
	private CustomerClassTypeVO type;
	private DocumentTypeVO documentType;
	private List<CustomerMediaContactVO> customerMediaContacts;
	private String legalRepresentative;
	private String isMigrated;
	private String isMigratedDescription;
	private String disclamer;
	private boolean showCustomerIsMigrated;
	
	private String stateCity;
	private String city;
	private String perimeter;
	private String product;
	
	
	
	public String getProduct() {
		return product;
	}
	public void setProduct(String product) {
		this.product = product;
	}
	public String getStateCity() {
		return stateCity;
	}
	public void setStateCity(String stateCity) {
		this.stateCity = stateCity;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getPerimeter() {
		return perimeter;
	}
	public void setPerimeter(String perimeter) {
		this.perimeter = perimeter;
	}
	public CustomerDTO(Long customerId, String customerCode, String firstNames,
			String lastNames, String documentNumber, String address,
			CustomerClassTypeVO type, DocumentTypeVO documentType,
			List<CustomerMediaContactVO> customerMediaContacts,
			String legalRepresentative, String isMigrated,
			String isMigratedDescription, String disclamer,
			boolean showCustomerIsMigrated) {
		super();
		this.customerId = customerId;
		this.customerCode = customerCode;
		this.firstNames = firstNames;
		this.lastNames = lastNames;
		this.documentNumber = documentNumber;
		this.address = address;
		this.type = type;
		this.documentType = documentType;
		this.customerMediaContacts = customerMediaContacts;
		this.legalRepresentative = legalRepresentative;
		this.isMigrated = isMigrated;
		this.isMigratedDescription = isMigratedDescription;
		this.disclamer = disclamer;
		this.showCustomerIsMigrated = showCustomerIsMigrated;
	}
	public String getCustomerCode() {
		return customerCode;
	}
	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}
	public String getFirstNames() {
		return firstNames;
	}
	public void setFirstNames(String firstNames) {
		this.firstNames = firstNames;
	}
	public String getLastNames() {
		return lastNames;
	}
	public void setLastNames(String lastNames) {
		this.lastNames = lastNames;
	}
	public CustomerClassTypeVO getType() {
		return type;
	}
	public void setType(CustomerClassTypeVO type) {
		this.type = type;
	}
	public String getDocumentNumber() {
		return documentNumber;
	}
	public void setDocumentNumber(String documentNumber) {
		this.documentNumber = documentNumber;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public DocumentTypeVO getDocumentType() {
		return documentType;
	}
	public void setDocumentType(DocumentTypeVO documentType) {
		this.documentType = documentType;
	}
	public List<CustomerMediaContactVO> getCustomerMediaContacts() {
		return customerMediaContacts;
	}
	public void setCustomerMediaContacts(
			List<CustomerMediaContactVO> customerMediaContacts) {
		this.customerMediaContacts = customerMediaContacts;
	}
	public Long getCustomerId() {
		return customerId;
	}
	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}
	public String getLegalRepresentative() {
		return legalRepresentative;
	}
	public void setLegalRepresentative(String legalRepresentative) {
		this.legalRepresentative = legalRepresentative;
	}
	public String getIsMigrated() {
		return isMigrated;
	}
	public void setIsMigrated(String isMigrated) {
		this.isMigrated = isMigrated;
	}
	public String getIsMigratedDescription() {
		return isMigratedDescription;
	}
	public void setIsMigratedDescription(String isMigratedDescription) {
		this.isMigratedDescription = isMigratedDescription;
	}
	public String getDisclamer() {
		return disclamer;
	}
	public void setDisclamer(String disclamer) {
		this.disclamer = disclamer;
	}
	public boolean isShowCustomerIsMigrated() {
		return showCustomerIsMigrated;
	}
	public void setShowCustomerIsMigrated(boolean showCustomerIsMigrated) {
		this.showCustomerIsMigrated = showCustomerIsMigrated;
	}	
	
}
