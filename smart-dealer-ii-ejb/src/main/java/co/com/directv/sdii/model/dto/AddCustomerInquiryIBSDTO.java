package co.com.directv.sdii.model.dto;

import java.io.Serializable;
import java.util.Date;

import co.com.directv.sdii.model.pojo.Country;
import co.com.directv.sdii.model.pojo.collection.RequestCollectionInfo;

/**
 * Permite encapsular los filtros para invocar el servicio AddCustomerInquiry
 * 
 * Fecha de Creación: 14/10/2014
 * @author cduarte <a href="mailto:ssanabri@everis.com">e-mail</a>
 * @version 5.2.0
 * 
 * @see     
 */
public class AddCustomerInquiryIBSDTO extends RequestCollectionInfo implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4837789414601689223L;
	
	private Country country;
	private String systemId;
	private String customerCode;
	private Date creationDate;
	private String description;
	private String contactStatus;
	private String ibsUser;
	private Long categoryIbsCode;
	private String inputMethod;
	
	
	public AddCustomerInquiryIBSDTO(Country country,
									String systemId,
									String customerCode,
									Date creationDate,
									String description,
									String contactStatus,
									String ibsUser,
									Long categoryIbsCode,
									String inputMethod) {
		
		super();
		this.country = country;
		this.systemId = systemId;
		this.customerCode = customerCode;
		this.creationDate = creationDate;
		this.description = description;
		this.contactStatus = contactStatus;
		this.ibsUser = ibsUser;
		this.categoryIbsCode = categoryIbsCode;
		this.inputMethod = inputMethod;
		
	}

	public Country getCountry() {
		return country;
	}

	public void setCountry(Country country) {
		this.country = country;
	}

	public String getSystemId() {
		return systemId;
	}

	public void setSystemId(String systemId) {
		this.systemId = systemId;
	}

	public String getCustomerCode() {
		return customerCode;
	}

	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date createDate) {
		this.creationDate = createDate;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getContactStatus() {
		return contactStatus;
	}

	public void setContactStatus(String contactStatus) {
		this.contactStatus = contactStatus;
	}

	public String getIbsUser() {
		return ibsUser;
	}

	public void setIbsUser(String ibsUser) {
		this.ibsUser = ibsUser;
	}

	public Long getCategoryIbsCode() {
		return categoryIbsCode;
	}

	public void setCategoryIbsCode(Long categoryIbsCode) {
		this.categoryIbsCode = categoryIbsCode;
	}

	public String getInputMethod() {
		return inputMethod;
	}

	public void setInputMethod(String inputMethod) {
		this.inputMethod = inputMethod;
	}
	
}
